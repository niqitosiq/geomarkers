package examplecom.geomarkers;


        import android.content.Intent;
        import android.graphics.Typeface;
        import android.support.v7.app.ActionBarActivity;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.Switch;
        import android.widget.TextView;
        import android.widget.Toast;


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


    public void comeback(View v){

        Intent intObj = new Intent(this, MainLayoutActivity.class);
        startActivity(intObj);

    }


    public void okButton(View v){
        String nametextinsertvariable = "";
        String descriptiontextinsertvariable = "";//инициализация и обьявление переменных для передачи данных между классами
        Boolean switchinsertvariable = false;
        EditText nametext = (EditText)findViewById(R.id.title_edit);
        EditText descriptiontext = (EditText)findViewById(R.id.description_edit);//обьявляеем поля ввода и свитч
        Switch sw = (Switch) findViewById(R.id.alarm_switch);
        if (nametext.getText().toString().length() < 1 ){
            Toast toast = Toast.makeText(getApplicationContext(), //проверяем полность 1-ого поля ввода
                    "Заполните первое поле", Toast.LENGTH_SHORT);
            toast.show();
        }
        else{
            nametextinsertvariable = nametext.getText().toString();
            if (descriptiontext.getText().toString().length()<1){       //подготавливаем данные к отправке
                descriptiontextinsertvariable = null;


                switchinsertvariable = sw.isChecked();                                              //ПОФИКСИТЬ
                Intent intent = new Intent(this,MainLayoutActivity.class);                          //ЭТО
                intent.putExtra("nametext",nametextinsertvariable);                                 //ДЕРЬМО
                intent.putExtra("descriptiontext",descriptiontextinsertvariable);
                intent.putExtra("boolswitch", switchinsertvariable);
                startActivity(intent);//полетели
            }
            else{
                descriptiontextinsertvariable =  descriptiontext.getText().toString();

                switchinsertvariable = sw.isChecked();                                              //И
                Intent intent = new Intent(this,MainLayoutActivity.class);                          //ЭТО
                intent.putExtra("nametext",nametextinsertvariable);                                 //ТОЖЕ
                intent.putExtra("descriptiontext",descriptiontextinsertvariable);
                intent.putExtra("boolswitch", switchinsertvariable);
                startActivity(intent);//полетели
            }
        }



    }

}