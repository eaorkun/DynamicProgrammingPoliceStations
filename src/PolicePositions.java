public class PolicePositions {
    private int position;
    private int n_subproblem;

    public PolicePositions(int position, int n_subproblem) {
        this.position = position;
        this.n_subproblem = n_subproblem;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getN_subproblem() {
        return n_subproblem;
    }

    public void setN_subproblem(int n_subproblem) {
        this.n_subproblem = n_subproblem;
    }
}
