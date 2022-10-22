package ca.cmpt276.cmpt276assignment3;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

//main page
public class MainActivity extends AppCompatActivity {

    ImageView iv_welcome;
    TextView tv_title;
    Button btn_goToMainMenu;
    private String planet_seeker_by_dongwei_han;
    private String go_to_main_menu;
    private String planet_seeker;
    private boolean jump = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setMyActionBar();
        initial();
        zoomAndFade();
        delay();
        btn_goToMainMenu.setOnClickListener(v -> onButtonClick());

    }

    private void onButtonClick() {
        jump = true;
        Intent intent = new Intent(this,MenuActivity.class);
        startActivity(intent);
        finish();
    }

    private void initial() {
        iv_welcome  = findViewById(R.id.iv_welcome);
        tv_title = findViewById(R.id.tv_title);
        btn_goToMainMenu = findViewById(R.id.btn_goToMainManu);

        planet_seeker_by_dongwei_han = getString(R.string.planet_seeker_by);
        tv_title.setText(planet_seeker_by_dongwei_han);
        tv_title.setTextColor(Color.WHITE);
        go_to_main_menu = getString(R.string.go_to_main_menu);
        btn_goToMainMenu.setText(go_to_main_menu);

    }

    private void setMyActionBar() {
        ActionBar supportActionBar = getSupportActionBar();
        planet_seeker = getString(R.string.planet_seeker);
        supportActionBar.setTitle(planet_seeker);
    }

    private void zoomAndFade(){
        Animation aniSlide_zoom = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_zoom);
        iv_welcome.startAnimation(aniSlide_zoom);
    }


    private void delay(){
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if(!jump) {
                    Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                    startActivity(intent);
                }
            }
        }, 8000);
    }

}