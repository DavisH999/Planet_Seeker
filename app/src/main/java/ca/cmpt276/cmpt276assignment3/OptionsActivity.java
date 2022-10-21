package ca.cmpt276.cmpt276assignment3;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import ca.cmpt276.cmpt276assignment3.model.Game;

public class OptionsActivity extends AppCompatActivity {
    // option page

    RadioGroup rg_selectBoardSize;
    RadioGroup rg_selectNumberTargets;
    TextView tv_selectBoardSize;
    TextView tv_selectNumberTargets;
    int num_rows = 4, num_columns = 6,num_targets = 8;
    // default
    Game game;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        setMyActionBar();
        initial();
        createRadioGroupSize();
        createRadioGroupTargets();

    }

    private void saveGame() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("shared_preferences",MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        String json = getJsonStringFromObject();
        edit.putString("savedGame",json);
        edit.apply();
    }

    private String getJsonStringFromObject() {
        Gson gson = new Gson();
        return gson.toJson(game);
    }

    private void createRadioGroupSize() {
        String[] stringArray = getResources().getStringArray(R.array.size_of_board);
        for(int i = 0; i < stringArray.length; i++)
        {
            String str = stringArray[i];
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(str);
            radioButton.setTextColor(Color.WHITE);
            // set on click callbacks
            radioButton.setOnClickListener(v -> onSizeClick());
            rg_selectBoardSize.addView(radioButton);
            // checked saved button when relaunch this activity
            if(str.equals(getSizePanelsInstalled(this)))
            {
                radioButton.setChecked(true);
            }
        }
    }

    private void onSizeClick() {
        int checkedRadioButtonId = rg_selectBoardSize.getCheckedRadioButtonId();
        RadioButton checkedRadioButton = findViewById(checkedRadioButtonId);
        String checkedMessage = checkedRadioButton.getText().toString(); // chosen button
        if(checkedMessage.startsWith("4"))
        {
            num_rows = 4;
            num_columns = 6;
        }
        if(checkedMessage.startsWith("5"))
        {
            num_rows = 5;
            num_columns = 10;
        }
        if(checkedMessage.startsWith("6"))
        {
            num_rows = 6;
            num_columns = 15;
        }
        game.initial(num_rows,num_columns,num_targets);
        // save game
        saveGame();
        saveSizePanelsInstalled(checkedMessage);
    }

    private void saveSizePanelsInstalled(String checkMessage) {
        SharedPreferences sharedPreferences = this.getSharedPreferences("AppPrefsLeft", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("left_check",checkMessage);
        editor.apply();
    }

    static public String getSizePanelsInstalled(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("AppPrefsLeft", MODE_PRIVATE);
        // set default : firstStr
        String[] stringArray = context.getResources().getStringArray(R.array.size_of_board);
        String firstStr = stringArray[0];
        return sharedPreferences.getString("left_check",firstStr);
    }


    private void createRadioGroupTargets() {
        String[] stringArray = getResources().getStringArray(R.array.number_of_targets);
        for(int i = 0; i < stringArray.length; i++)
        {
            String str = stringArray[i];
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(str);
            radioButton.setTextColor(Color.WHITE);
            radioButton.setOnClickListener(v -> onTargetsClick());
            rg_selectNumberTargets.addView(radioButton);
            if(str.equals(getTargetsPanelsInstalled(this)))
            {
                radioButton.setChecked(true);
            }
        }
    }

    private void onTargetsClick() {
        int checkedRadioButtonId = rg_selectNumberTargets.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(checkedRadioButtonId);
        String checkedMessage = radioButton.getText().toString();
        if(checkedMessage.startsWith("6"))
        {
            num_targets = 6;
        }
        if(checkedMessage.startsWith("10"))
        {
            num_targets = 10;
        }
        if(checkedMessage.startsWith("15"))
        {
            num_targets = 15;
        }
        if(checkedMessage.startsWith("20"))
        {
            num_targets = 20;
        }
        Toast.makeText(this," "+num_targets,Toast.LENGTH_SHORT).show();
        game.initial(num_rows,num_columns,num_targets);
        saveGame();
        saveTargetsPanelsInstalled(checkedMessage);
    }

    private void saveTargetsPanelsInstalled(String checkedMessage) {
        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefsRight", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("right_check",checkedMessage);
        editor.apply();
    }

    public static String getTargetsPanelsInstalled(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("AppPrefsRight", MODE_PRIVATE);
        String[] stringArray = context.getResources().getStringArray(R.array.number_of_targets);
        String firstStr = stringArray[0];
        return sharedPreferences.getString("right_check",firstStr);
    }


    private void initial() {
        if(game == null) {
            game = Game.getInstanceOfGame();
        }
        rg_selectBoardSize = findViewById(R.id.rg_selectBoardSize);
        rg_selectNumberTargets = findViewById(R.id.rg_selectNumberTargets);
        tv_selectBoardSize = findViewById(R.id.tv_selectBoardSize);
        tv_selectBoardSize.setText("Select Board Size");
        tv_selectBoardSize.setTextColor(Color.WHITE);
        tv_selectNumberTargets = findViewById(R.id.tv_selectNumberTargets);
        tv_selectNumberTargets.setText("Select Number Planets");
        tv_selectNumberTargets.setTextColor(Color.WHITE);
    }


    private void setMyActionBar() {
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle("Options");
    }
}