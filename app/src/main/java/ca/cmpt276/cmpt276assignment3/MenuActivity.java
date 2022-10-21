package ca.cmpt276.cmpt276assignment3;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {
    // menu page

    Button btn_playGame;
    Button btn_options;
    Button btn_help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        setMyActionBar();
        initial();
        btn_playGame.setOnClickListener(v -> onPlayGameClick());
        String play_game = getString(R.string.play_game);
        btn_playGame.setText(play_game);
        btn_options.setOnClickListener(v -> onOptionsClick());
        String options = getString(R.string.options);
        btn_options.setText(options);
        btn_help.setOnClickListener(v -> onHelpClick());
        String help = getString(R.string.help);
        btn_help.setText(help);
    }

    private void onPlayGameClick() {
        Intent intent = new Intent(this,GameActivity.class);
        startActivity(intent);
    }

    private void onOptionsClick() {
        Intent intent = new Intent(this,OptionsActivity.class);
        startActivity(intent);
    }

    private void onHelpClick() {
        Intent intent = new Intent(this,HelpActivity.class);
        startActivity(intent);
    }


    private void initial() {
        btn_playGame = findViewById(R.id.btn_playGame);
        btn_options = findViewById(R.id.btn_options);
        btn_help = findViewById(R.id.btn_help);
    }

    private void setMyActionBar() {
        ActionBar supportActionBar = getSupportActionBar();
        String main_menu = getString(R.string.main_menu);
        supportActionBar.setTitle(main_menu);
    }
}