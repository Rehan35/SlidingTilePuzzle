# SlidingTilePuzzle
Uses A-Star algorithmic approach with multiple allowed heuristics (L norms).

# Heuristics Used
- L0 Norm: This heuristic calculates the number of tiles in the wrong position.
- L1 Norm : Also known as Manhattan Distance, this heuristic calculates how many spaces to the right, left, up, and down a piece has to go to get to its optimal position.
- L2 Norm: Also known as Euclidean Distance, this heuristic calculates the coordinate distance between the current position of the piece and its correct position.

# Algorithms Used
- Breadth First Search\  
- Depth First Search\
- Greedy Best First Search\
- A Star algorithm

# Command Line Arguments
-size : indictaes the size of the square grid.\
-rows : indicates number of rows\
-cols : indicates number of columns\
-columns : indicates number of columns\
-a : indicates number of rows\
-b : indicates number of cols\
-up : perform the operation to move the tile below the empty position up if possible\
-down : perform the operation to move the tile below the empty position up if possible\
-left : perform the operation to move the tile below the empty position up if possible\
-right : perform the operation to move the tile below the empty position up if possible\
-stats : prints out the statistics of the algorithm used (solution depth, states expanded, states explored, and branching factor)\
-verbose : is a debugging tool used to print out different characteristics, such as game state after every move, number of moves made and different attributes of the game state as needed.

# Run on Terminal
javac Tester.java\
java Tester.java 2 3 6 8 1 4 5 7 -size 3 -stats\

This will create a board with the given configration of the tile (left to right for all n rows) of size 3x3 and prints the stats mentioned above.

# Output
Depending on the input, the default output would be the initial configuration and the final configuration with the number of steps needed and each step used to achieve the final configuration.\
If stats is an input, the statistics mentioned in the previous section will be printed
If verbose is an input, any debug information accounted for in the code will be printed.
