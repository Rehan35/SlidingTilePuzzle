import java.util.Iterator;

public class State implements Iterable<Action>{
    private final Board board;

    public State(Board board) {
        this.board = board;
    }

    public State next(Action action) {
        Board board = new Board(this.board, action.getDirection());
        return new State(board);
    }

    public boolean isGoal() {
        return this.board.isGoal();
    }

    public Board board() {
        return this.board;
    }

    public Action[] actions() {
        Direction[] directions = this.board.moves(this.board.empty());
        Action[] actions = new Action[directions.length];

        for (int i = 0; i < directions.length; i++) {
            actions[i] = new Action(directions[i]);
        }

        return actions;
    }

    public boolean equals(State other) {
        return this.hashCode() == other.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof State && this.equals((State) other);
    }

    @Override
    public int hashCode() {
        return this.board.hashCode();
    }

    @Override
    public Iterator<Action> iterator() {
        return new ActionIterator();
    }

    private class ActionIterator implements Iterator<Action> {
        private int index = 0;
        private Action[] actions = State.this.actions();

        public boolean hasNext() {
            return index < actions.length;
        }

        public Action next() {
            return actions[index++];
        }
    }
}
