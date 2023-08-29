//Rehan Parwani
//September 19, 2022
//Mr. Paige
//Artificial Intelligence
//Puzzle #2

import java.util.ArrayList;

public class Tester {

    private static int[] toArray(ArrayList<Integer> list) {
        int[] result = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    public static void main(String[] args) {
        ArrayList<Direction> moves = new ArrayList<>();
        ArrayList<Integer> tiles = new ArrayList<>();
        String option = "";
        int columns = 4;
        int rows = 4;

        String solve = "";
        String heuristic = "L";
        boolean displayStats = false;
        boolean isVerbose = false;

        for (String arg : args) {
            switch (arg.toLowerCase()) {
                case "-size":
                case "-rows":
                case "-cols":
                case "-columns":
                    if (option.length() > 0) {
                        System.err.println("Missing value for option: " + option);
                    }
                    option = arg;
                    continue;

                case "up":
                case "down":
                case "left":
                case "right":
                    if (option.length() > 0) {
                        System.err.println("Missing value for option: " + option);
                    }
                    Direction move = Direction.valueOf(arg.toUpperCase());
                    moves.add(move);
                    continue;

                case "-bfs":
                case "-dfs":
                case "-ucs":
                case "-gbfs":
                case "-astar":
                case "-l0":
                case "-l1":
                case "-l2":
                case "-stats":
                case "-verbose":
                    option = arg;
                    break;
            }

            try {
                switch (option) {
                    case "-size":
                        rows = Integer.parseInt(arg);
                        columns = rows;
                        break;

                    case "-rows":
                        rows = Integer.parseInt(arg);
                        break;

                    case "-cols":
                    case "-columns":
                        columns = Integer.parseInt(arg);
                        break;

                    case "-bfs":
                    case "-dfs":
                    case "-ucs":
                    case "-gbfs":
                    case "-astar":
                        solve = arg.substring(1);
                        break;
                        
                    case "-L0":
                    case "-L1":
                    case "-L2":
                        heuristic = arg.substring(1);
                        break;

                    case "-stats":
                        displayStats = true;
                        break;

                    case "-verbose":
                        isVerbose = true;
                        break;

                    default:
                        int tile = Integer.parseInt(arg);
                        tiles.add(tile);
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid value for option " + option + ": " + arg);
            }
            option = "";
        }

        if (option.length() > 0) {
            System.err.println("Missing value for option: " + option);
        }

        Board board;
        if (tiles.size() > 0) {
            board = new Board(rows, columns, toArray(tiles));
        } else {
            board = new Board(rows, columns);
        }
        
        SlidingTilePuzzle.algorithm = Algorithm.valueOf(solve.toUpperCase());
        SlidingTilePuzzle.heuristic = Cost.valueOf(heuristic.toUpperCase());

        if (solve.equals("bfs")) {
            System.out.println();
            board.print();
            System.out.println();

            State state = new State(board);

            Object[] bfsSolution = SlidingTilePuzzle.bfs(state);
            Action[] solution = SlidingTilePuzzle.solution((Node) bfsSolution[0]);

            for (Action action : solution) {
                System.out.println(action.getDirection());
                state = state.next(action);
                if (isVerbose) {
                    System.out.println();
                    state.board().print();
                    System.out.println();
                }

            }

            if (displayStats) {
                //System.out.println("Solution Depth = " + SlidingTilePuzzle.steps((Node) bfsSolution[0]));
                System.out.println("States Expanded = " + ((Statistics) bfsSolution[1]).statesExpanded());
                System.out.println("States Explored = " + ((Statistics) bfsSolution[1]).statesExplored());
                //System.out.println("Branching Factor = " + String.format("%.1f", ((Statistics) bfsSolution[1]).branchingFactor(((Node) bfsSolution[0]).cost())));

                System.out.println();
            }
        } else if (solve.equals("dfs")) {
            System.out.println();
            board.print();
            System.out.println();

            State state = new State(board);

            Object[] dfsSolution = SlidingTilePuzzle.dfs(state);
            Action[] solution = SlidingTilePuzzle.solution((Node) dfsSolution[0]);

            for (Action action : solution) {
                System.out.println(action.getDirection());
                state = state.next(action);
                if (isVerbose) {
                    System.out.println();
                    state.board().print();
                    System.out.println();
                }
            }

            if (displayStats) {
                //System.out.println("Solution Depth = " + SlidingTilePuzzle.steps((Node) dfsSolution[0]));
                System.out.println("States Expanded = " + ((Statistics) dfsSolution[1]).statesExpanded());
                System.out.println("States Explored = " + ((Statistics) dfsSolution[1]).statesExplored());
                //System.out.println("Branching Factor = " + String.format("%.1f", ((Statistics) dfsSolution[1]).branchingFactor(((Node) dfsSolution[0]).cost())));

                System.out.println();
            }
        } else {
            
            if (SlidingTilePuzzle.heuristic == Cost.L) {
                SlidingTilePuzzle.heuristic = Cost.L0;
            }
            
            System.out.println();
            board.print();
            System.out.println();

            State state = new State(board);
            Object[] searchSolution = SlidingTilePuzzle.search(state);
            
            Action[] solution = SlidingTilePuzzle.solution((Node) searchSolution[0]);
            System.out.println(solution.length);
            
            for (Action action : solution) {
                System.out.println(action.getDirection());
                state = state.next(action);
                if (isVerbose) {
                    System.out.println();
                    state.board().print();
                    System.out.println();
                }
            }
        }
    }
}

