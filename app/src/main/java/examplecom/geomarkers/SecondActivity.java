package examplecom.geomarkers;


        import android.graphics.Typeface;
        import android.support.v7.app.ActionBarActivity;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.widget.TextView;


public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Integer[] MassIntro = new Integer[]{R.id.mainText};
        for (Integer i=0; i < MassIntro.length; i++) {
            TextView lay = (TextView) findViewById(MassIntro[i]);
            lay.setTypeface(Typeface.createFromAsset(getAssets(), "Intro.otf"));
        }
        Integer[] MassRoboto = new Integer[]{R.id.title_text,R.id.description_text, R.id.map_text, R.id.alarm_text};
        for (Integer i=0; i < MassRoboto.length; i++) {
            TextView lay = (TextView) findViewById(MassRoboto[i]);
            lay.setTypeface(Typeface.createFromAsset(getAssets(), "Roboto.ttf"));
        }
    }
}