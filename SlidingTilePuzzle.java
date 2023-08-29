//Rehan Parwani
//September 19, 2022
//Mr. Paige
//Artificial Intelligence
//Puzzle #2

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.PriorityQueue;

public class SlidingTilePuzzle {
    
    public static Cost heuristic = Cost.L;
    public static Algorithm algorithm = Algorithm.BFS;
    
    private static class nodeComparator implements Comparator<Node> {
        public int compare(Node x, Node y) {
            if (x.cost() < y.cost()) {
                return -1;
            } else if (x.cost() > y.cost()){
                return 1;
            } else {
                return 0;
            }
        }
    }
    
    public static Object[] search(State initial) {
        
        int statesExplored = 0;
        int statesExpanded = 1;
        int branchingFactor = 0;

        PriorityQueue<Node> queue = new PriorityQueue<>(new nodeComparator());
        Node start = Node.find(initial);

        queue.offer(start);

        Node current;
        Node next;

        while(!queue.isEmpty()) {
            current = queue.poll();
            if (current.isGoal()) {
                statesExplored += 1;
                Object[] solution = new Object[] {current, new Statistics(statesExplored, statesExpanded, branchingFactor)};
                return solution;
            }
            if (!current.isExplored()) {
                current.explored(true);
                for(Action action: current.state()) {
                    next = Node.find(current.state().next(action));
                    if (queue.contains(next)) {
                        if (next.cost() < next.cost(current, action)) {
                            continue;
                        } 
                        else {
                            queue.remove(next);
                            next.update(current, action);
                            queue.offer(next);
                        }
                    } else if (next.isExplored()) {
                        if (next.cost() < next.cost(current, action)) {
                            continue;
                        } else {
                            next.explored(false);
                            next.update(current, action);
                            queue.offer(next);
                        }
                    } else {
                        next.update(current, action);
                        queue.offer(next);
                    }
                }
            }
        }

        return null;
    }

    public static Object[] bfs(State initial) {

        int statesExplored = 0;
        int statesExpanded = 1;
        int branchingFactor = 0;

        Queue<Node> queue = new LinkedList<>();
        Node start = Node.find(initial);

        queue.offer(start);

        Node current;
        Node next;

        while(!queue.isEmpty()) {
            current = queue.poll();
            if (current.isGoal()) {
                statesExplored += 1;
                Object[] solution = new Object[] {current, new Statistics(statesExplored, statesExpanded, branchingFactor)};
                return solution;
            }
            if (!current.isExplored()) {
                current.explored(true);
                statesExplored += 1;
                for(Action action: current.state()) {
                    next = Node.find(current.state().next(action));
                    statesExpanded += 1;
                    if (!next.isExplored()) {
                        branchingFactor += 1;
                        next.update(current, action);
                        queue.offer(next);
                    }
                }
            }
        }

        return null;
    }

    public static Object[] dfs(State initial) {
        int statesExplored = 0;
        int statesExpanded = 1;
        int branchingFactor = 0;

        Stack<Node> stack = new Stack<>();
        Node start = Node.find(initial);

        stack.push(start);

        Node current;
        Node next;

        while(!stack.isEmpty()) {
            current = stack.pop();
            if (current.isGoal()) {
                statesExplored += 1;
                Object[] solution = new Object[] {current, new Statistics(statesExplored, statesExpanded, branchingFactor)};
                return solution;
            }
            if (!current.isExplored()) {
                current.explored(true);
                statesExplored += 1;
                for(Action action: current.state()) {
                    next = Node.find(current.state().next(action));
                    statesExpanded += 1;
                    if (!next.isExplored()) {
                        branchingFactor += 1;
                        next.update(current, action);
                        stack.push(next);
                    }
                }
            }
        }

        return null;
    }

    public static Action[] solution(Node node) {
        Node rover = node;

        Stack<Action> actions = new Stack<>();

        while (rover.parent() != null) {
            actions.push(rover.action());
            rover = rover.parent();
        }

        Action[] solution = new Action[actions.size()];
        int index = 0;
        while(!actions.isEmpty()) {
            solution[index++] = actions.pop();
        }
        return solution;
    }
}
