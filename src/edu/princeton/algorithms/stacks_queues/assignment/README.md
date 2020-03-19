## Stacks And Queues

Assignment specification [here](https://coursera.cs.princeton.edu/algs4/assignments/queues/specification.php).

#### Compiling/Running:

Windows - from **project root directory**:

    dir /s /b *.java > srcFiles.txt
    javac -cp "lib/*" -d "out" @srcFiles.txt
    echo A B C D E F G H | java -cp "out;lib/*" edu.princeton.algorithms.stacks_queues.assignment.Permutation 3