package edu.princeton.algorithms.priority_queues.assignment;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private SearchNode finalNode;
    private boolean isSolvable;

    public Solver(Board originalBoard) {
        if (originalBoard == null) {
            throw new IllegalArgumentException();
        }

        var originalPq = new MinPQ<SearchNode>();

        var twinBoard = originalBoard.twin();
        var twinPq = new MinPQ<SearchNode>();

        var initialNode = new SearchNode(null, originalBoard, 0);
        originalPq.insert(initialNode);

        var initialTwinNode = new SearchNode(null, twinBoard, 0);
        twinPq.insert(initialTwinNode);

        while (true) {
            var currNode = originalPq.delMin();
            if (currNode.board.isGoal()) {
                this.finalNode = currNode;
                this.isSolvable = true;
                break;
            }

            var currTwinNode = twinPq.delMin();
            if (currTwinNode.board.isGoal()) {
                this.isSolvable = false;
                break;
            }

            this.insertNeighbours(originalPq, currNode);
            this.insertNeighbours(twinPq, currTwinNode);
        }
    }

    private void insertNeighbours(MinPQ<SearchNode> pq, SearchNode dequeuedNode) {
        var moves = dequeuedNode.moves + 1;
        for (var neighbourBoard : dequeuedNode.board.neighbors()) {

            // Don't re-add a previously dequeued board.
            if (dequeuedNode.previousNode != null
                    && neighbourBoard.equals(dequeuedNode.previousNode.board)) {
                continue;
            }

            var newNode = new SearchNode(dequeuedNode, neighbourBoard, moves);
            pq.insert(newNode);
        }
    }

    public boolean isSolvable() {
        return this.isSolvable;
    }

    public int moves() {
        return this.isSolvable ? this.finalNode.moves : -1;
    }

    public Iterable<Board> solution() {
        if (!this.isSolvable) {
            return null;
        }

        var currNode = this.finalNode;
        var boards = new Stack<Board>();
        while (currNode != null) {
            boards.push(currNode.board);
            currNode = currNode.previousNode;
        }

        return boards;
    }

    public static void main(String[] args) {
        var in = new In(args[0]);
        var n = in.readInt();
        var tiles = new int[n][n];

        for (var i = 0; i < n; i++) {
            for (var j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }

        var initial = new Board(tiles);
        var solver = new Solver(initial);

        if (!solver.isSolvable()) {
            StdOut.println("No solution possible");
        } else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }
    }

    private class SearchNode implements Comparable<SearchNode> {
        private SearchNode previousNode;
        private Board board;
        private int moves;
        private int manhattanDist;

        public SearchNode(SearchNode previousNode, Board board, int moves) {
            this.previousNode = previousNode;
            this.board = board;
            this.moves = moves;
            this.manhattanDist = board.manhattan();
        }

        public int getPriority() {
            return this.manhattanDist + this.moves;
        }

        public int compareTo(SearchNode that) {
            return this.getPriority() - that.getPriority();
        }
    }
}
