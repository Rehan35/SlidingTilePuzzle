//Rehan Parwani
//September 19, 2022
//Mr. Paige
//Artificial Intelligence
//Puzzle #2

import java.util.*;

public class Board {

    // An IMMUTABLE class that represents the state of the
    // board for an n x m sliding tile puzzle.  There are
    // two ways to indicate the position of a tile on the
    // board: first it can be a (row, column) pair; second
    // it can be an index into a n x m vector.  We will
    // use a one dimensional vector for state of the board
    // but it will be convenient to have functions that
    // go back and forth between the two representations
    // for the position of a tile.

    // Assertions
    //
    //     I strongly urge you to use assertions to validate
    //     the parameters passed to the methods in this class
    //     and have indicated how to do so for the first few
    //     methods.  You should enable assertions when running
    //     your code during development (the -ea option to java):
    //
    //         java -ea Puzzle -rows 4 -columns 4 ...
    //
    //     and AssertionError with be thrown whenever an assert
    //     statement fails.  When running your program as a
    //     finished product, omit the -ea option and assertions
    //     will not be checked so your program will run faster.


    private int[] board;
    private int rows;
    private int columns;


    public Board(int rows, int columns) {
        // Consruct a board in the solved configuration
        this.board = new int[rows * columns];
        this.rows = rows;
        this.columns = columns;
        
        for (int i = 0; i < this.board.length - 1; i++){
            this.board[i] = i + 1;
        }
        this.board[this.board.length - 1] = 0;
    }

    public Board(int rows, int columns, int[] board) {
        // Construct a board with a given configuration
        try {
            validate(rows, columns, board);
            
            this.board = new int[rows * columns];
            for (int i = 0; i < board.length; i++) {
                this.board[i] = board[i];
            }
            
            this.rows = rows;
            this.columns = columns;
            
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    public Board(Board other, Direction direction) {
        // Construct a board by moving the empty square
        // in a specified position from a given board
        // configuration
        this.rows = other.rows;
        this.columns = other.columns;

        this.board = new int[rows * columns];
        for (int i = 0; i < other.board.length; i++) {
            this.board[i] = other.board[i];
        }

        this.board = other.move(direction);
    }

    public int rows() {
        // Number of rows for this board
        return this.rows;
    }

    public int columns() {
        // Number of columns for this board
        return this.columns;
    }

    public int empty() {
        // Position of the empty square
        for (int i = 0; i < this.board.length; i++) {
            if (this.board[i] == 0) {
                return i;
            }
        }
        return -1;
    }

    public int get(int index) {
        // Contents of a particual position on the board
        assert isValidIndex(index);
        return this.board[index];
    }

    public int get(int row, int column) {
        // Contents of a particual position on the board
        assert isValidRow(row);
        assert isValidColumn(column);
        
        return this.board[index(row, column)];
    }
        
    public int index(int row, int column) {
        // Conversion from row/column to position
        return (row) * this.columns + column;
    }

    public int row(int index) {
        // Conversion from index to row
        //2*4 => 7
        return index/this.columns;
    }

    public int column(int index) {
        // Conversion from index to column
        return index%this.columns; 
    }

    public int position(int position, Direction direction) {
        // Position of the adjacent square in a given direction
        int row = row(position);
        int column = column(position);
        
        switch (direction) {
            case LEFT:
                return index(row, column - 1);
            case RIGHT:
                return index(row, column + 1);
            case UP:
                return index(row - 1, column);
            case DOWN:
                return index(row + 1, column);
        }
        
        return -1;
    }


    public Direction[] moves(int position) {
        // The list of possible moves from a given board position
        List<Direction> directions = new ArrayList<Direction>();
        for (Direction direction : Direction.values()) {
            switch (direction) {
                case LEFT:
                    if (isValidColumn(column(position) - 1)) {
                        directions.add(direction);
                    }
                    break;
                case RIGHT:
                    if (isValidColumn(column(position) + 1)) {
                        directions.add(direction);
                    }
                    break;
                case UP:
                    if (isValidRow(row(position) - 1)) {
                        directions.add(direction);
                    }
                    break;
                case DOWN:
                    if (isValidRow((row(position) + 1))) {
                        directions.add(direction);
                    }
                    break;
            }
        }
        Direction[] arr = new Direction[directions.size()];
        for (int i = 0; i < directions.size(); i++) {
            arr[i] = directions.get(i);
        }
        return arr;
    }


    private int[] move(Direction direction) {
        // Creates a new board array with the empty
        // square moved in the indicated direction
        int[] tempBoard = new int[this.board.length];
        tempBoard = new Board(this.rows, this.columns, this.board).board;

        int position = empty();

        int row = row(position);
        int column = column(position);

        switch (direction) {
            case LEFT:
                if (isValidColumn(column - 1)) {
                    int tempTile = get(row, column - 1);
                    tempBoard[index(row, column - 1)] = this.board[position];
                    tempBoard[position] = tempTile;
                }
                break;
            case RIGHT:
                if (isValidColumn(column + 1)) {
                    int tempTile = get(row, column + 1);
                    tempBoard[index(row, column + 1)] = this.board[position];
                    tempBoard[position] = tempTile;
                }
                break;
            case UP:
                if (isValidRow(row - 1)) {
                    int tempTile = get(row - 1, column);
                    tempBoard[index(row - 1, column)] = this.board[position];
                    tempBoard[position] = tempTile;
                }
                break;
            case DOWN:
                if (isValidRow(row + 1)) {
                    int tempTile = get(row + 1, column);
                    tempBoard[index(row + 1, column)] = this.board[position];
                    tempBoard[position] = tempTile;
                }
                break;
        }
        return tempBoard;
    }
        

    public boolean equals(Board other) {
        if (this.board.length == other.board.length) {
            for (int i = 0; i < this.board.length; i++) {
                if (this.board[i] != other.board[i]) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Board && this.equals((Board) other);
    }

    @Override
    public int hashCode() {
        // You WILL want a better hashCode function
        // hashCode and equals MUST be compatible
        //751 = 7*10 + 5 * 10 + 1
        int radix = this.rows * this.columns;
        int hash = this.board[0];
        for (int i = 1; i < radix; i++) {
            hash *= radix;
            hash += this.board[i];
        }
        return hash;
    }

    @Override
    public String toString() {
        String result = "";
        String separator = "";
        for (int i = 0; i < this.board.length; i++) {
            result += separator;
            result += String.format("%2d", this.board[i]);
            separator = " ";
        }
        return result;
    }

    public void print() {
        for (int row = 0; row < this.rows(); row++) {
            String line = "";
            if (row > 0) printRowSeparator();
            String separator = "";
            for(int col = 0; col < this.columns(); col++) {
                int tile = this.get(row, col);
                line += separator;
                if (tile > 0) {
                    line += String.format(" %2d ", this.get(row, col));
                } else {
                    line += "    ";
                }
                separator = "|";
            }
            System.out.println(line);
        }
    }

    private void printRowSeparator() {
        String line = "";
        String separator = "";
        for (int col = 0; col < this.columns(); col++) {
            line += separator;
            line += "----";
            separator = "+";
        }
        System.out.println(line);
    }

    private static int[] goal(int rows, int columns) {
        // All tiles are in ascending order
        // Empty square is in lower right corner
        return new Board(rows, columns).board;
    }

    public boolean isGoal() {
        for (int i = 0; i < this.board.length - 1; i++){
            if (this.board[i] != i + 1) {
                return false;
            }
        }
        return this.board[this.board.length - 1] == 0;
    }


    private boolean isValidRow(int row) {
        return row < rows() && row >= 0;
    }

    public boolean isValidColumn(int column) {
        return column < columns() && column >= 0;
    }

    public boolean isValidIndex(int index) {
        return index < this.board.length && index >= 0;
    }

    private void validate(int rows, int columns, int[] board) {
        // Check that size = rows * columns;
        // Check that each tile is valid:
        //    Tile values are in range 0 .. size-1
        //    There are no duplicate tiles
        if (board.length == rows * columns) {
            Set<Integer> setOfTiles = new HashSet<Integer>();
            for (int tile : board) {
                if (tile >= board.length || setOfTiles.contains(tile)) {
                    throw new IllegalArgumentException("Invalid board");
                } else {
                    setOfTiles.add(tile);
                }
            }
        } else {
            throw new IllegalArgumentException("Invalid board");
        }
    }
}
