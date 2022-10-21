package ca.cmpt276.cmpt276assignment3.model;

// cell
public class Unit {
    private boolean isTarget;
    private boolean isFound;
    private int clickTimes;

    public Unit()
    {
        isTarget = false;
        isFound = false;
        clickTimes = 0;
    }

    public void setIsTarget(boolean target) {
        isTarget = target;
    }

    public void setIsFound(boolean found) {
        isFound = found;
    }

    public boolean isTarget() {
        return isTarget;
    }

    public boolean isFound() {
        return isFound;
    }

    public int getClickTimes() {
        return clickTimes;
    }

    public void setClickTimes(int clickTimes) {
        this.clickTimes = clickTimes;
    }
}
