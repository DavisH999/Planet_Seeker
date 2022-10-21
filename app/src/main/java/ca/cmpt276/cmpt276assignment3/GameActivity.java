package ca.cmpt276.cmpt276assignment3;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;

import ca.cmpt276.cmpt276assignment3.model.BoolAndInt;
import ca.cmpt276.cmpt276assignment3.model.Game;

public class GameActivity extends AppCompatActivity {
    // game page

    Game instanceOfGame;
    private static int NUM_ROWS;
    private static int NUM_COLS;
    TableLayout tableLayout;
    TextView tv_found;
    TextView tv_scans;
    Button buttons[][];
    Vibrator vibrator;
    VibrationEffect oneShotVibrationEffect;
    SoundPool soundPool;
    int soundID;
    private String game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadGame();
        initial();
        initializeVibration();
        initializeSoundPool();
        setMyActionBar();
        populateButtons();
        // refresh page
        refreshPage();

    }


    void refreshPage()
    {
        // load page when first open or reopen

        for(int i = 0; i < NUM_ROWS; i++){
            for(int j = 0; j < NUM_COLS; j++){

//                Button button = buttons[i][j];

                Button button = buttons[i][j];

                int finalI = i;
                int finalJ = j;
                button.post(new Runnable() {
                    @Override
                    public void run() {
                        BoolAndInt record = instanceOfGame.getRecord(finalI, finalJ);
                        if(record == null)
                        {
                            // no action.
                        }
                        else
                        {
                            if (record.getClickTimes() == 0) {
                                // nothing
                            }
                            else if (record.getClickTimes() == 1)
                            {
                                if (record.getIsTarget() == true) {
                                    lockButtonSizes();
                                    showTarget(button);
                                } else {
                                    int numberOfHiddenTargets = record.getNumberOfTargets();
                                    button.setText(String.valueOf(numberOfHiddenTargets));
                                }
                            }
                            else if (record.getClickTimes() >= 2)
                            {
//                        2 must 1 do all
                                if (record.getIsTarget() == true) {
                                    lockButtonSizes();
                                    showTarget(button);
                                    int numberOfHiddenTargets = record.getNumberOfTargets();
                                    button.setText(String.valueOf(numberOfHiddenTargets));
                                } else {
                                    int numberOfHiddenTargets = record.getNumberOfTargets();
                                    button.setText(String.valueOf(numberOfHiddenTargets));
                                }
                            }
                        }
                    }
                });
            }
        }
        // update found and scans textview
        String found_Of_Targets = instanceOfGame.found_Of_Targets();
        String scan_used = instanceOfGame.scan_used();
        tv_found.setText(found_Of_Targets);
        tv_scans.setText(scan_used);
    }

    private void loadGame()
    {
//        instanceOfGame = Game.getInstanceOfGame();
        loadSavedGame();
        NUM_ROWS = instanceOfGame.getNUM_ROWS();
        NUM_COLS = instanceOfGame.getNUM_COLS();
        buttons = new Button[NUM_ROWS][NUM_COLS];
    }

    private void setMyActionBar() {
        ActionBar supportActionBar = getSupportActionBar();
        game = getString(R.string.game);
        supportActionBar.setTitle(game);
    }

    private void initial()
    {
        tableLayout = findViewById(R.id.tableForButtons);
        tv_found = findViewById(R.id.tv_found);
        tv_found.setTextColor(Color.WHITE);
        tv_scans = findViewById(R.id.tv_scansUsed);
        tv_scans.setTextColor(Color.WHITE);
    }

    private void populateButtons() {
        for(int row = 0; row < NUM_ROWS; row++){
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f
            ));
            tableLayout.addView(tableRow);

            for (int col = 0; col < NUM_COLS; col++) {
                Button button = new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f
                ));

                // make text not clip on small buttons.
                button.setPadding(0,0,0,0);
                // populate buttons list
                buttons[row][col] = button;

                final int finalRow = row;
                final int finalCol = col;

                button.setOnClickListener(v->onButtonClick(finalRow, finalCol));

                tableRow.addView(button);
            }
        }
    }

    private void onButtonClick(int row, int col) {
        Button button = buttons[row][col];

        // lock button sizes

        lockButtonSizes();

        gaming(row,col,button);

        refreshPage();

        gameIsOver();

        // save game after every click
        saveGame();

    }

    private void saveGame() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("shared_preferences",MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        String json = getJsonStringFromObject();
        edit.putString("savedGame",json);
        edit.apply();
    }

    private void loadSavedGame() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("shared_preferences",MODE_PRIVATE);
        String json = sharedPreferences.getString("savedGame", null);
        instanceOfGame = getObjectFromJson(json);
        if(instanceOfGame == null)
        {
            instanceOfGame = Game.getInstanceOfGame();
            initializeGameAfterLoadingSettings();
        }
    }


    private String getJsonStringFromObject() {
        Gson gson = new Gson();
        return gson.toJson(instanceOfGame);
    }

    private Game getObjectFromJson(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<Game>(){}.getType();
        return gson.fromJson(json, type);
    }

    private void gameIsOver() {
        if(instanceOfGame.getNumberOfTargetsFound() == instanceOfGame.getNUM_TARGETS())
        {
            FragmentManager Manager = getSupportFragmentManager();
            MessageFragment dialog = new MessageFragment();
            dialog.show(Manager,"MessageDialog");
            initializeGameAfterLoadingSettings();
        }
    }

    private void initializeGameAfterLoadingSettings() {
        int NUM_TARGETS = 0;
        String sizePanelsInstalled = OptionsActivity.getSizePanelsInstalled(this);
        if(sizePanelsInstalled.startsWith("4"))
        {
            NUM_ROWS = 4;
            NUM_COLS = 6;
        }
        if(sizePanelsInstalled.startsWith("5"))
        {
            NUM_ROWS = 5;
            NUM_COLS = 10;
        }
        if(sizePanelsInstalled.startsWith("6"))
        {
            NUM_ROWS = 6;
            NUM_COLS = 15;
        }
        String targetsPanelsInstalled = OptionsActivity.getTargetsPanelsInstalled(this);
        if(targetsPanelsInstalled.startsWith("6"))
        {
             NUM_TARGETS = 6;
        }
        if(targetsPanelsInstalled.startsWith("10"))
        {
            NUM_TARGETS = 10;
        }
        if(targetsPanelsInstalled.startsWith("15"))
        {
            NUM_TARGETS = 15;
        }
        if(targetsPanelsInstalled.startsWith("20"))
        {
            NUM_TARGETS = 20;
        }
        instanceOfGame.initial(NUM_ROWS,NUM_COLS,NUM_TARGETS);
    }

    private void gaming(int row, int col, Button button) {
        BoolAndInt click = instanceOfGame.click(row, col);
        int numberOfHiddenTargets = instanceOfGame.getNumberOfHiddenTargetsInARowAndAColumn(row, col);
        if(click == null)
        {
            // no action.
        }
        else
        {
            if(click.getClickTimes() == 1)
            {
                if(click.getIsTarget() == true)
                {
                    instanceOfGame.setTargetFound(row,col);
                    showTarget(button);
                    // play 1s vibrate
                    vibrator.vibrate(oneShotVibrationEffect);
                    // play sound
                    soundPool.play(soundID,1,1,1,0,1);
                }
                else
                {
                    button.setText(String.valueOf(numberOfHiddenTargets));
                }
            }
            else if(click.getClickTimes() == 2)
            {
                if(click.getIsTarget() == true)
                {
                    button.setText(String.valueOf(numberOfHiddenTargets));
                }
            }
            else
            {
                // no action.
            }
        }
    }

    private void initializeSoundPool() {
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_GAME)
                .build();
        soundPool = new SoundPool.Builder()
                .setMaxStreams(1)
                .setAudioAttributes(audioAttributes)
                .build();
        soundID = soundPool.load(this, R.raw.game_win, 1);
    }

    private void initializeVibration() {
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        oneShotVibrationEffect = VibrationEffect.createOneShot(1000, 100);
    }

    private void lockButtonSizes() {
        for(int row = 0; row < NUM_ROWS; row++){
            for(int col = 0; col < NUM_COLS; col++){
                Button button = buttons[row][col];

                int width = button.getWidth();
                button.setMinWidth(width);
                button.setMaxWidth(width);

                int height = button.getHeight();
                button.setMinHeight(height);
                button.setMaxHeight(height);
            }
        }
    }

    private void showTarget(Button button)
    {
        int newWidth = button.getWidth();
        int newHeight = button.getHeight();


        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.planet);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap,newWidth,newHeight,true);
        button.setBackground(new BitmapDrawable(getResources(),scaledBitmap));
    }

}