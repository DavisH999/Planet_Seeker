package ca.cmpt276.cmpt276assignment3.model;

import java.util.ArrayList;
import java.util.Random;

// game logic
public class Game {
    private int NUM_ROWS;
    private int NUM_COLS;
    private int NUM_TARGETS;
    private int NUM_TARGETS_FOUND;
    private int NUM_SCANS_USED;

    public Unit[][] getUnits() {
        return units;
    }

    private Unit[][] units;
    private Random rand = new Random();
    private ArrayList<Integer> randomNumbers;


    public int getNUM_ROWS() {
        return NUM_ROWS;
    }

    public void setNUM_ROWS(int NUM_ROWS) {
        this.NUM_ROWS = NUM_ROWS;
    }

    public int getNUM_COLS() {
        return NUM_COLS;
    }

    public void setNUM_COLS(int NUM_COLS) {
        this.NUM_COLS = NUM_COLS;
    }

    public int getNUM_TARGETS() {
        return NUM_TARGETS;
    }

    public void setNUM_TARGETS(int NUM_TARGETS) {
        this.NUM_TARGETS = NUM_TARGETS;
    }


    private static Game instanceOfGame = null;

    public Game()
    {

    }

    public void initial(int num_rows, int num_columns, int num_targets){
        this.NUM_ROWS = num_rows;
        this.NUM_COLS = num_columns;
        this.NUM_TARGETS = num_targets;
        this.NUM_TARGETS_FOUND = 0;
        this.NUM_SCANS_USED = 0;

        units = new Unit[NUM_ROWS][NUM_COLS];
        for(int i = 0; i < NUM_ROWS; i++){
            for(int j = 0; j < NUM_COLS; j++) {
                units[i][j] = new Unit();
            }
        }
        setTargetRandomly();

    }

    public static Game getInstanceOfGame()
    {
        if(instanceOfGame == null)
        {
            instanceOfGame = new Game(); // default settings
        }
        return instanceOfGame;
    }

    public BoolAndInt click(int row, int column)
    {
        // add 1
        Unit unit = units[row][column];
        int clickTimes = unit.getClickTimes();
        units[row][column].setClickTimes(++clickTimes);

        BoolAndInt boolAndInt = new BoolAndInt();

        if(unit.getClickTimes() == 1)
        {
            if(unit.isTarget() == true)
            {
                int numberOfTargetsInARowAndAColumn = getNumberOfHiddenTargetsInARowAndAColumn(row, column);
                boolAndInt.setIsTarget(true);
                boolAndInt.setClickTimes(1);
                boolAndInt.setNumberOfTargets(numberOfTargetsInARowAndAColumn);

                units[row][column].setIsFound(true);
//                NUM_SCANS_USED++;
            }
            else
            {
                int numberOfTargetsInARowAndAColumn = getNumberOfHiddenTargetsInARowAndAColumn(row, column);
                boolAndInt.setIsTarget(false);
                boolAndInt.setClickTimes(1);
                boolAndInt.setNumberOfTargets(numberOfTargetsInARowAndAColumn);
                NUM_SCANS_USED++;
            }
        }
        else if(unit.getClickTimes() == 2)
        {
            if(unit.isTarget() == true)
            {
                int numberOfTargetsInARowAndAColumn = getNumberOfHiddenTargetsInARowAndAColumn(row, column);
                boolAndInt.setIsTarget(true);
                boolAndInt.setClickTimes(2);
                boolAndInt.setNumberOfTargets(numberOfTargetsInARowAndAColumn);
                NUM_SCANS_USED++;
            }
            else
            {
                return null; // means no action
            }
        }
        else
        {
            return null; // means no action
        }
        return boolAndInt;
    }

    public BoolAndInt getRecord(int row, int column)
    {
        Unit unit = units[row][column];

        BoolAndInt boolAndInt = new BoolAndInt();

        if(unit.getClickTimes() == 1)
        {
            if(unit.isTarget() == true)
            {
                int numberOfTargetsInARowAndAColumn = getNumberOfHiddenTargetsInARowAndAColumn(row, column);
                boolAndInt.setIsTarget(true);
                boolAndInt.setClickTimes(1);
                boolAndInt.setNumberOfTargets(numberOfTargetsInARowAndAColumn);
            }
            else
            {
                int numberOfTargetsInARowAndAColumn = getNumberOfHiddenTargetsInARowAndAColumn(row, column);
                boolAndInt.setIsTarget(false);
                boolAndInt.setClickTimes(1);
                boolAndInt.setNumberOfTargets(numberOfTargetsInARowAndAColumn);
            }
        }
        else if(unit.getClickTimes() >= 2)
        {
            if(unit.isTarget() == true)
            {
                // do 1 and 2 time
                int numberOfTargetsInARowAndAColumn = getNumberOfHiddenTargetsInARowAndAColumn(row, column);
                boolAndInt.setIsTarget(true);
                boolAndInt.setClickTimes(2);
                boolAndInt.setNumberOfTargets(numberOfTargetsInARowAndAColumn);
            }
            else
            {
                int numberOfTargetsInARowAndAColumn = getNumberOfHiddenTargetsInARowAndAColumn(row, column);
                boolAndInt.setIsTarget(false);
                boolAndInt.setClickTimes(2);
                boolAndInt.setNumberOfTargets(numberOfTargetsInARowAndAColumn);
            }
        }
        return boolAndInt;
    }

    public int getNumberOfTargetsFound()
    {
        return NUM_TARGETS_FOUND;
    }

    public String found_Of_Targets()
    {
        return "Found " + NUM_TARGETS_FOUND + " of " + NUM_TARGETS + " planets";
    }

    public String scan_used()
    {
        return "# Scans used: " + NUM_SCANS_USED;
    }

    public int getNumberOfHiddenTargetsInARowAndAColumn(int row, int column)
    {
        int num = 0;
        int numberOfHiddenTargetInARaw = getNumberOfHiddenTargetInARaw(row);
        int numberOfHiddenTargetInAColumns = getNumberOfHiddenTargetInAColumns(column);
        num = numberOfHiddenTargetInARaw + numberOfHiddenTargetInAColumns;
        Unit unit = units[row][column];
        if(unit.isTarget()==true && unit.isFound()==false)
        {
            num = num - 2;
        }
        return num;
    }

    public int getNumberOfHiddenTargetInARaw(int row)
    {
        int num = 0;
        for(int i = 0; i < NUM_COLS; i++)
        {
            Unit unit = units[row][i];
            if(unit.isTarget() == true && unit.isFound() == false)
            {
                num++;
            }
        }
        return num;
    }

    public int getNumberOfHiddenTargetInAColumns(int column)
    {
        int num = 0;
        for(int i = 0; i < NUM_ROWS; i++)
        {
            Unit unit = units[i][column];
            if(unit.isTarget() == true && unit.isFound() == false)
            {
                num++;
            }
        }
        return num;
    }

    public void setTargetFound(int row, int column)
    {
        NUM_TARGETS_FOUND++;
        units[row][column].setIsFound(true);
    }

    public void setTargetRandomly()
    {
        selectRandomNumbers(NUM_TARGETS);
        for(int i = 0; i < NUM_TARGETS; i++)
        {
            int r = randomNumbers.get(i);
            int row = r / NUM_COLS;
            int col = r % NUM_COLS;
            units[row][col].setIsTarget(true);
        }
    }

    void selectRandomNumbers(int numberOfTargets)
    {
        randomNumbers = new ArrayList<>();
        while(true) {
            int r = rand.nextInt(NUM_ROWS*NUM_COLS);
            if(!randomNumbers.contains(r))
            {
                randomNumbers.add(r);
            }
            if(randomNumbers.size() == numberOfTargets)
            {
                return;
            }
        }
    }
}
