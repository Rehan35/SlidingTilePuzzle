//Rehan Parwani
//September 19, 2022
//Mr. Paige
//Artificial Intelligence
//Puzzle #2
import java.util.Comparator;
import java.util.HashMap;

public class Node {
    public static final HashMap<State, Node> nodes = new HashMap<>();

    private State state;
    private Node parent;
    private Action action;
    private double cost;
    
    private int pathCost;

    private boolean explored;

    private Node(State state) {
        this.state = state;
        this.parent = null;
        this.action = null;
        this.cost = 0.0;
        this.pathCost = 0;
        this.explored = false;
    }

    public static Node find(State state) {
        Node node = nodes.get(state);
        if (node == null) {
            node = new Node(state);
            nodes.put(state, node);
        }

        return node;
    }

    public State state() {
        return this.state;
    }

    public Node parent() {
        return this.parent;
    }

    public Action action() {
        return this.action;
    }

    public double cost() {
        return this.cost;
    }

    public boolean isExplored() {
        return this.explored;
    }

    public boolean isGoal() {
        return this.state.isGoal();
    }

    public void explored(boolean isExplored) {
        this.explored = isExplored;
    }
    
//    public int compareTo(Node b) {
//        if (this.cost > b.cost) {
//            return 1;
//        } else if (this.cost < b.cost) {
//            return -1;
//        }
//        return 0;
//    }
    
    public double heuristic() {
        double cost = 0;
        int size = state.board().rows() * state.board().columns();
        switch (SlidingTilePuzzle.heuristic) {
            case L:
                return 0.0;
            case L0:
                
                for (int i = 0; i < size - 1; i++) {
                    if (state.board().get(i) != i + 1) {
                        cost += 1;
                    }
                }
                if (state.board().get(size - 1) != 0) {
                    cost += 1;
                }
                return cost;
            case L1:
                for (int i = 0; i < size; i++) {
                    if (state.board().get(i) != i + 1) {
                        if (state.board().get(i) == 0) {
                            int rowDiff = state.board().row(i) - (state.board().rows() - 1);
                            int colDiff = state.board().column(i) - (state.board().columns() - 1);
                            cost += Math.abs(rowDiff) + 
                                Math.abs(colDiff);
                        } else {
                            int rowDiff = state.board().row(i) - state.board().row(state.board().get(i) - 1);
                            int colDiff = state.board().column(i) - state.board().column(state.board().get(i) - 1);
                            cost += Math.abs(rowDiff) + 
                                Math.abs(colDiff);
                        }
                    }
                }
                return cost;
            case L2:
                for (int i = 0; i < size; i++) {
                    if (state.board().get(i) != i + 1) {
                        if (state.board().get(i) == 0) {
                            int rowDiff = state.board().row(i) - (state.board().rows() - 1);
                            int colDiff = state.board().column(i) - (state.board().columns() - 1);
                            cost += Math.sqrt(Math.pow(rowDiff, 2) + 
                                Math.pow(colDiff, 2));
                        } else {
                            int rowDiff = state.board().row(i) - state.board().row(state.board().get(i) - 1);
                            int colDiff = state.board().column(i) - state.board().column(state.board().get(i) - 1);
                            cost += Math.sqrt(Math.pow(rowDiff, 2) + 
                                Math.pow(colDiff, 2));
                        }
                    }
                }
                return cost;
        }
        return 0.0;
    }
    
    public double cost(Node parent, Action action) {
        switch (SlidingTilePuzzle.algorithm) {
            case GBFS:
                System.out.println(heuristic());
                return heuristic();
            case BFS:
            case DFS:
            case UCS:
                return parent.pathCost + 1;
            case ASTAR:
                this.state.board().print();
                System.out.println(parent.pathCost + 1 + heuristic());
                System.out.println();
                return parent.pathCost + 1 + heuristic();
        }
        return 0.0;
    }
             

    public void update(Node parent, Action action) {
        this.parent = parent;
        this.action = action;
        this.cost = cost(parent, action);
        this.pathCost = parent.pathCost + 1;
        nodes.put(this.state, this);
    }
}
