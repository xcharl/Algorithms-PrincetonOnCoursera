package edu.princeton.algorithms.priority_queues.assignment;

import edu.princeton.cs.algs4.In;

import java.util.ArrayList;

public class Board {
    private final int[][] tiles;
    private final int n;
    private Coord blankLoc;

    public Board(int[][] tiles) {
        this.tiles = getDeepCopy(tiles);
        this.n = tiles.length;

        for (var i = 0; i < tiles.length; i++) {
            for (var j = 0; j < tiles[i].length; j++) {
                if (tiles[i][j] == 0) {
                    this.blankLoc = new Coord(i, j);
                    break;
                }
            }

            if (this.blankLoc != null) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        var testFile = args[0];
        var input = new In(testFile);
        var dimension = input.readInt();
        var inputTiles = new int[dimension][dimension];

        for (var i = 0; i < dimension; i++) {
            for (var j = 0; j < dimension; j++) {
                inputTiles[i][j] = input.readInt();
            }
        }

        var board = new Board(inputTiles);
        System.out.println(board.toString());

        for (var neighbour : board.neighbors()) {
            System.out.println(neighbour.toString());
        }

        System.out.println("Hamming: " + board.hamming());
        System.out.println("Manhattan: " + board.manhattan());
        System.out.println("Is goal: " + board.isGoal());
        System.out.println("Twin: " + board.twin().toString());
    }

    public String toString() {
        var sb = new StringBuilder();
        sb.append(this.tiles.length);
        sb.append(System.lineSeparator());
        for (int[] tile : this.tiles) {
            for (var j = 0; j < tile.length; j++) {
                sb.append(" ").append(tile[j]);
            }

            sb.append(System.lineSeparator());
        }

        return sb.toString();
    }

    public int dimension() {
        return this.n;
    }

    public int hamming() {
        var hammingDist = 0;
        for (var i = 0; i < this.tiles.length; i++) {
            for (var j = 0; j < this.tiles[i].length; j++) {
                var actualTile = this.tiles[i][j];
                if (actualTile == 0) {
                    continue;
                }

                var expectedTile = (this.dimension() * i) + (j + 1);
                if (actualTile != expectedTile) {
                    hammingDist++;
                }
            }
        }

        return hammingDist;
    }

    public int manhattan() {
        var manhattanDist = 0;
        for (var i = 0; i < this.tiles.length; i++) {
            for (var j = 0; j < this.tiles[i].length; j++) {
                var tileNum = this.tiles[i][j] - 1;  // -1 for 0-based indexes in arrays.
                if (tileNum < 0) {
                    continue;  // The 0 'tile'.
                }

                var targetI = tileNum / this.dimension();
                var targetJ = tileNum % this.dimension();

                var tileDist = Math.abs(targetI - i) + Math.abs(targetJ - j);
                manhattanDist += tileDist;
            }
        }

        return manhattanDist;
    }

    public boolean isGoal() {
        return this.hamming() == 0;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null) {
            return false;
        }

        if (this.getClass() != other.getClass()) {
            return false;
        }

        var that = (Board) other;
        if (this.dimension() != that.dimension()) {
            return false;
        }

        for (var i = 0; i < this.dimension(); i++) {
            for (var j = 0; j < this.dimension(); j++) {
                if (this.tiles[i][j] != that.tiles[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    public Iterable<Board> neighbors() {
        var neighbours = new ArrayList<Board>(4);

        // Up - move square from above into blank position.
        if (this.blankLoc.getI() > 0) {
            var newBlankLoc = new Coord(this.blankLoc.getI() - 1, this.blankLoc.getJ());
            var newBoard = getNewBoard(newBlankLoc);
            neighbours.add(newBoard);
        }

        // Down
        if (this.blankLoc.getI() < this.dimension() - 1) {
            var newBlankLoc = new Coord(this.blankLoc.getI() + 1, this.blankLoc.getJ());
            var newBoard = getNewBoard(newBlankLoc);
            neighbours.add(newBoard);
        }

        // Left
        if (this.blankLoc.getJ() > 0) {
            var newBlankLoc = new Coord(this.blankLoc.getI(), this.blankLoc.getJ() - 1);
            var newBoard = getNewBoard(newBlankLoc);
            neighbours.add(newBoard);
        }

        // Right
        if (this.blankLoc.getJ() < this.dimension() - 1) {
            var newBlankLoc = new Coord(this.blankLoc.getI(), this.blankLoc.getJ() + 1);
            var newBoard = getNewBoard(newBlankLoc);
            neighbours.add(newBoard);
        }

        return neighbours;
    }

    public Board twin() {
        var newTiles = getDeepCopy(this.tiles);

        // Make sure we don't just move the blank square around.
        // Initial dimension guaranteed to be >= 2, so we can just hardcode the tiles to swap.
        if (this.blankLoc.getI() != 0) {
            var tile1 = new Coord(0, 0);
            var tile2 = new Coord(0, 1);
            swapTiles(newTiles, tile1, tile2);
        }
        else {
            var tile1 = new Coord(1, 0);
            var tile2 = new Coord(1, 1);
            swapTiles(newTiles, tile1, tile2);
        }

        return new Board(newTiles);
    }

    private Board getNewBoard(Coord newBlankLoc) {
        var newTiles = getDeepCopy(this.tiles);
        swapTiles(newTiles, this.blankLoc, newBlankLoc);
        return new Board(newTiles);
    }

    private static void swapTiles(int[][] tiles, Coord a, Coord b) {
        var temp = tiles[a.getI()][a.getJ()];
        tiles[a.getI()][a.getJ()] = tiles[b.getI()][b.getJ()];
        tiles[b.getI()][b.getJ()] = temp;
    }

    private static int[][] getDeepCopy(int[][] tiles) {
        var newTiles = new int[tiles.length][tiles.length];
        for (var i = 0; i < tiles.length; i++) {
            for (var j = 0; j < tiles[i].length; j++) {
                newTiles[i][j] = tiles[i][j];
            }
        }

        return newTiles;
    }

    private class Coord {
        private int i;
        private int j;

        public Coord(int i, int j) {
            this.i = i;
            this.j = j;
        }

        public int getI() {
            return this.i;
        }

        public int getJ() {
            return this.j;
        }
    }
}
