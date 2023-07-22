package com.riftech.a99namesofallah;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

public class MainActivity2 extends AppCompatActivity {

    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        RadioButton r1=(RadioButton) findViewById(R.id.radio_english);
        RadioButton r2=(RadioButton) findViewById(R.id.radio_bangla);
        RadioButton r3=(RadioButton) findViewById(R.id.radio_urdu);
        RadioButton r4=(RadioButton) findViewById(R.id.radio_arabic);


    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_english:
                //if (checked)

                    sharedPrefs = getSharedPreferences("sp_lang", MODE_PRIVATE);
                    editor = sharedPrefs.edit();
                    editor.putString("language", "English");
                    editor.apply();
                    go();
                    break;
            case R.id.radio_bangla:
                //if (checked)
                    sharedPrefs = getSharedPreferences("sp_lang", MODE_PRIVATE);
                    editor = sharedPrefs.edit();
                    editor.putString("language", "Bangla");
                    editor.apply();
                    go();
                    break;
            case R.id.radio_arabic:
                //if (checked)
                    sharedPrefs = getSharedPreferences("sp_lang", MODE_PRIVATE);
                    editor = sharedPrefs.edit();
                    editor.putString("language", "Arabic");
                    editor.apply();
                    go();
                    break;
            case R.id.radio_urdu:
                //if (checked)
                    sharedPrefs = getSharedPreferences("sp_lang", MODE_PRIVATE);
                    editor = sharedPrefs.edit();
                    editor.putString("language", "Urdu");
                    editor.apply();
                    go();
                    break;
            default:
                sharedPrefs = getSharedPreferences("sp_lang", MODE_PRIVATE);
                editor = sharedPrefs.edit();
                editor.putString("language", "English");
                editor.apply();
                go();
                break;
        }
    };

   private void go(){
       Intent intent = new Intent(getBaseContext(), MainActivity.class);
       startActivity(intent);
   }
}