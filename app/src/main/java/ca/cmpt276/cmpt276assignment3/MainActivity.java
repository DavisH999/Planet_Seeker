package ca.cmpt276.cmpt276assignment3;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import ca.cmpt276.cmpt276assignment3.model.Game;

public class MainActivity extends AppCompatActivity {
    //main page

    ImageView iv_welcome;
    TextView tv_title;
    Button btn_goToMainMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setMyActionBar();
        initial();
        btn_goToMainMenu.setOnClickListener(v -> onButtonClick());

    }

    private void onButtonClick() {
        Intent intent = new Intent(this,MenuActivity.class);
        startActivity(intent);
        finish();
    }

    private void initial() {
        iv_welcome  = findViewById(R.id.iv_welcome);
        tv_title = findViewById(R.id.tv_title);
        btn_goToMainMenu = findViewById(R.id.btn_goToMainManu);

        tv_title.setText("Planet Seeker By Dongwei Han");
        tv_title.setTextColor(Color.WHITE);
        btn_goToMainMenu.setText("Go to main menu");

    }

    private void setMyActionBar() {
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle("Planet Seeker");
    }

}