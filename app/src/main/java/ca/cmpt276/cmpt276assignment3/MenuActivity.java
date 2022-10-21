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
        btn_playGame.setText("Play Game");
        btn_options.setOnClickListener(v -> onOptionsClick());
        btn_options.setText("Options");
        btn_help.setOnClickListener(v -> onHelpClick());
        btn_help.setText("Help");
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
        supportActionBar.setTitle("Main Menu");
    }
}