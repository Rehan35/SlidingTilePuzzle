//Rehan Parwani
//September 19, 2022
//Mr. Paige
//Artificial Intelligence
//Puzzle #2

import java.util.Iterator;

public class Action{
    private Direction direction;

    public Action(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public static Action[] stpActions() {
        return new Action[] {new Action(Direction.UP), new Action(Direction.DOWN), new Action(Direction.RIGHT), new Action(Direction.LEFT)};
    }

    public int cost() {
        return 1;
    }
}
