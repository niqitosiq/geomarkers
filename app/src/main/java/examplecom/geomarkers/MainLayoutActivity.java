package examplecom.geomarkers;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);


        ImageView notepadicon = (ImageView) findViewById(R.id.notepadicon);
        notepadicon.setImageResource(R.drawable.list);

        Integer[] MassIntro = new Integer[]{R.id.TodoList};
        for (Integer i=0; i < MassIntro.length; i++) {
            TextView lay = (TextView) findViewById(MassIntro[i]);
            lay.setTypeface(Typeface.createFromAsset(getAssets(), "Intro.otf"));
        }
        Integer[] MassRoboto = new Integer[]{R.id.name};
        for (Integer i=0; i < MassRoboto.length; i++) {
            TextView lay = (TextView) findViewById(MassRoboto[i]);
            lay.setTypeface(Typeface.createFromAsset(getAssets(), "Roboto.ttf"));
        }
    }
    public void addmarker(View view){
        Intent intObj = new Intent(this, SecondActivity.class);
        startActivity(intObj);
    }


}
