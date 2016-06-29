package examplecom.geomarkers;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainLayoutActivity extends AppCompatActivity implements View.OnClickListener {

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
    public void addMarker(View view){
        //Intent intObj = new Intent(this, SecondActivity.class);
        //startActivity(intObj);
        LinearLayout addbutton = (LinearLayout) findViewById(R.id.addbutton);
        addbutton.removeAllViews();
        LinearLayout layout = (LinearLayout)findViewById(R.id.others);
        LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout addbutton2 = new LinearLayout(this);
        addbutton2.setBottom(5);
        addbutton2.setClickable(true);
        addbutton2.setBackgroundResource(R.drawable.default_rectangle);
        addbutton2.setOnClickListener(this);
        addbutton2.setId(R.id.addbutton);
        addbutton2.setHorizontalGravity(View.TEXT_ALIGNMENT_CENTER);
        layout.addView(addbutton2);
        ImageView imgview = new ImageView(this);
        imgview.setBackgroundResource(R.mipmap.cross);
        addbutton2.addView(imgview);



    }
    protected void redrawButtonAdd(View view){
        LinearLayout addbutton = (LinearLayout) findViewById(R.id.addbutton);
        addbutton.removeAllViews();
        LinearLayout layout = (LinearLayout)findViewById(R.id.others);
        LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout addbutton2 = new LinearLayout(this);
        addbutton2.setBottom(5);
        addbutton2.setClickable(true);
        addbutton2.setBackgroundResource(R.drawable.default_rectangle);
        addbutton2.setOnClickListener(this);
        addbutton2.setId(R.id.addbutton);
        addbutton2.setHorizontalGravity(View.TEXT_ALIGNMENT_CENTER);
        layout.addView(addbutton2);
        ImageView imgview = new ImageView(this);
        imgview.setBackgroundResource(R.mipmap.cross);
        addbutton2.addView(imgview);
    }

    @Override
    public void onClick(View v) {
        LinearLayout layout = (LinearLayout)findViewById(R.id.others);
        LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout newlayout = new LinearLayout(this);
        newlayout.setBackgroundResource(R.drawable.default_rectangle);
        newlayout.setOrientation(LinearLayout.HORIZONTAL);
        newlayout.setBottom(5);
        layout.addView(newlayout);
        redrawButtonAdd(v);

    }
}
