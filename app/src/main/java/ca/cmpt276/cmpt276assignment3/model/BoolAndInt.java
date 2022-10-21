package ca.cmpt276.cmpt276assignment3.model;

// return object
public class BoolAndInt {
    private int clickTimes;
    private boolean isTarget;
    private int numberOfTargets;

    public BoolAndInt() {
        clickTimes = 0;
        isTarget = true;
        numberOfTargets = 0;
    }

    public int getClickTimes() {
        return clickTimes;
    }

    public void setClickTimes(int clickTimes) {
        this.clickTimes = clickTimes;
    }

    public boolean getIsTarget() {
        return isTarget;
    }

    public void setIsTarget(boolean target) {
        isTarget = target;
    }

    public int getNumberOfTargets() {
        return numberOfTargets;
    }

    public void setNumberOfTargets(int numberOfTargets) {
        this.numberOfTargets = numberOfTargets;
    }
}
