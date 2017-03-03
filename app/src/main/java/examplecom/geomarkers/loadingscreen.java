package examplecom.geomarkers;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by nikita on 22.02.17.
 */

public class loadingscreen extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loadingscreen);

        Integer[] MassIntro = new Integer[]{R.id.loading_status};
        for (Integer i = 0; i < MassIntro.length; i++) {
            TextView lay = (TextView) findViewById(MassIntro[i]);
            lay.setTypeface(Typeface.createFromAsset(getAssets(), "Intro.otf"));
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intentmass[] = {
                        new Intent(loadingscreen.this, MapActivity.class),
                        new Intent(loadingscreen.this, SecondActivity.class),
                        new Intent(loadingscreen.this, MainLayoutActivity.class)
                };
                for(int i = 0; i<3; i++){
                    startActivity(intentmass[i]);
                }

                startService(new Intent(loadingscreen.this, dome.class));

                finish();
            }
        }, SPLASH_TIME_OUT);



    }
}
