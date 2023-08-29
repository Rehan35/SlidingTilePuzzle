//Rehan Parwani
//September 19, 2022
//Mr. Paige
//Artificial Intelligence
//Puzzle #2

public class Statistics {

    private int statesExplored;
    private int statesExpanded;
    private int branchingFactor;

    public Statistics(int statesExplored, int statesExpanded, int branchingFactor) {
        this.statesExplored = statesExplored;
        this.statesExpanded = statesExpanded;
        this.branchingFactor = branchingFactor;
    }

    public int statesExplored() {
        return statesExplored;
    }

    public int statesExpanded() {
        return statesExpanded;
    }

    public double branchingFactor(int depth) {
        return (double) branchingFactor / (double) (depth + 1);
    }

}
