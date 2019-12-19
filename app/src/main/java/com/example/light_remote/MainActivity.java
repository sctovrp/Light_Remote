package com.example.light_remote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView speechToText;
    //FirebaseDatabase database = FirebaseDatabase.getInstance();
    //DatabaseReference myRef = database.getReference("message");
    DatabaseReference reff;
    Blynk_Test blynk;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        speechToText = (TextView) findViewById(R.id.speechToText);
        blynk = new Blynk_Test();
        reff = FirebaseDatabase.getInstance().getReference().child("Blynk_Test");
        Toast.makeText(MainActivity.this,"Firebase connection success", Toast.LENGTH_SHORT).show();
    }

    public void getSpeechInput(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        if (intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, 10);
        }else{
            Toast.makeText(this, "your device don't support Speech Input", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    speechToText.setText(result.get(0));
                    blynk.setCommand(result.get(0));
                    String fString = "turn on the light";
                    String SString = "turn off the light";
                    if (blynk.getCommand().equals(fString) || blynk.getCommand().equals("turn on the lights")){
                        Toast.makeText(this,"the light has turned on succesfully",Toast.LENGTH_LONG).show();
                        blynk.setInt(1);
                        reff.child("Int").setValue(blynk.getInt());
                    }
                    else if (blynk.getCommand().equals(SString) ||  blynk.getCommand().equals("turn off the lights")){
                        Toast.makeText(this,"the light has turned off succesfully",Toast.LENGTH_LONG).show();
                        blynk.setInt(0);
                        reff.child("Int").setValue(blynk.getInt());
                    } else {
                        Toast.makeText(this, "Sorry, i don't understand the command: "+blynk.getCommand(),Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }

    }


}
