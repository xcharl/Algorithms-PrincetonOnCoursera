## Stacks And Queues

Assignment specification [here](https://coursera.cs.princeton.edu/algs4/assignments/queues/specification.php).

#### Compiling/Running:

Windows - from **this folder** in Command Prompt:

    set libpath=../../../../../lib/*
    javac -cp "%libpath%" -d "out" Deque.java Permutation.java RandomizedQueue.java
    cd out
    echo Test1 Test2 Test3 Test4 | java -cp ".;%libpath%" Permutation 2
    