package ca.cmpt276.cmpt276assignment3;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

// help page
public class HelpActivity extends AppCompatActivity {
    public String INTRO;
    public String COURSE;
    public String AUTHOR;
    public String RES;
    private String HELP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        setMyActionBar();
        setText();
    }

    private void setText() {
        TextView tv_help = findViewById(R.id.tv_help);
        tv_help.setTextColor(Color.WHITE);
        INTRO = getString(R.string.intro);
        COURSE = getString(R.string.course);
        AUTHOR = getString(R.string.author);
        RES = getString(R.string.res);

        String info  = INTRO + "\n" + COURSE + "\n" + AUTHOR + "\n" + RES;
        tv_help.setText(info);
    }

    private void setMyActionBar() {
        ActionBar supportActionBar = getSupportActionBar();
        HELP = getString(R.string.help);
        supportActionBar.setTitle(HELP);

    }
}