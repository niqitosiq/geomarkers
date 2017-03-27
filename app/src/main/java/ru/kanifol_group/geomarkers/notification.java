package ru.kanifol_group.geomarkers;

import android.content.Context;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.os.Vibrator;

/**
 * Created by nikita on 27.03.17.
 *
 * Использование:
 * Инициализация активити с помощью Intent
 * Вызов метода EditData для изменения переменных.
 *
 */

public class notification extends AppCompatActivity {
    static public String name = "имя";
    static public String description = "описание";
    MediaPlayer player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Integer[] MassIntro = new Integer[]{R.id.TodoList, R.id.header};
        for (Integer i = 0; i < MassIntro.length; i++) {
            TextView lay = (TextView) findViewById(MassIntro[i]);
            lay.setTypeface(Typeface.createFromAsset(getAssets(), "Intro.otf"));
        }
        Integer[] MassRoboto = new Integer[]{R.id.Name, R.id.description, R.id.closedel,R.id.close};
        for (Integer i = 0; i < MassRoboto.length; i++) {
            TextView lay = (TextView) findViewById(MassRoboto[i]);
            lay.setTypeface(Typeface.createFromAsset(getAssets(), "Roboto.ttf"));
        }


        //Вызов вибрации
        Vibrator vibrate = (Vibrator)this.getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {100, 600, 200};
        vibrate.vibrate(pattern, 0);


        //Вызов стандартного звукового уведомления
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            player = MediaPlayer.create(this, notification);
            player.setLooping(true);
            player.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onResume(){
        super.onResume();
        TextView nametext = (TextView)findViewById(R.id.Name);
        nametext.setText(name);
        TextView descriptiontext = (TextView)findViewById(R.id.description);
        descriptiontext.setText(description);

    }
    public void EditData(String pub_name, String pub_description){
        name = pub_name;
        description = pub_description;
    }
    public void closedel(View v){
        Vibrator vibrate = (Vibrator)this.getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {100, 600, 200};
        vibrate.vibrate(pattern, -1);//Выключение вибрации
        player.stop();//остановка звука
        this.finish();//завершение активити
        //Реализация потом
    }
    public void close(View v){
        Vibrator vibrate = (Vibrator)this.getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {100, 600, 200};
        vibrate.vibrate(pattern, -1);//Выключение вибрации
        player.stop();//остановка звука
        this.finish();//завершение активити

    }
}
