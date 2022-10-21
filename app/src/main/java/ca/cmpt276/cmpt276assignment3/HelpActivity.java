package ca.cmpt276.cmpt276assignment3;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity {
    // help page

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
        String intro = "Planets seeker:\n" +
                "When playing the game, it is free to click cells to search for a planets. If you find a planet, which is great." +
                "If you click a cell not containing a planet, the cell will show you the number of hidden planets in that row and column." +
                "You can click a cell of a planet, the cell will show you the number of hidden planets in that row and column as well.\n";

        String course = "Course page:\n" +
                "https://opencoursehub.cs.sfu.ca/bfraser/grav-cms/cmpt276/home\n";

        String author =  "Author:\n" +
                "Written by Dongwei Han, a student taking CMPT276 at SFU.\n";

        String res = "Resources:\n" +
                "1. https://www.space.com/44-venus-second-planet-from-the-sun-brightest-planet-in-solar-system.html\n" +
                "2. https://www.mos.org/planetarium/explore-the-universe-live\n" +
                "3. https://www.freepik.com/free-vector/confetti-background-4th-july-holiday_9020808.htm#query=celebrate&position=0&from_view=keyword\n" +
                "4. https://freesound.org/people/MLaudio/sounds/615099/\n";

        String thanks = "Thank you so much for playing this game called planets seeker!\n";

        String info  = intro + "\n" + course + "\n" + author + "\n" + res + "\n"+ thanks;
        tv_help.setText(info);
    }

    private void setMyActionBar() {
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle("Help");
    }
}