package com.vivekbalachandra.ghostgame;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;


public class GhostActivity extends AppCompatActivity {
    private static final String User_tern = "User Tern";
    private static final String CPU_tern = "CPU Tern";
    Random random = new Random();
    String current;
    private FastDictionary fastDictionary;
    private String word;
    private TextView turn;
    private EditText input;
    private Button challenge;
    private Button reset;
    private boolean usertern =random.nextBoolean();;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fastDictionary = new FastDictionary(this);
        word = fastDictionary.getAnyWordStartingWith(" ");
        turn = (TextView) findViewById(R.id.status);
        input = (EditText) findViewById(R.id.input);
        challenge = (Button) findViewById(R.id.challenge);
        reset = (Button) findViewById(R.id.reset);
        Log.d("prfetch", "if " + word + " then it is persute of happiness");
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current = null;
                word = null;
                usertern = true;
                input.setText(null);
                onStart();
            }
        });
        challenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input.getText().toString() == null)
                    return;
                input.setText(word);
                usertern = true;
                onstart();
            }
        });
        onstart();


    }

    private void onstart() {

        if (usertern) {
            turn.setText(User_tern);
        } else {
            turn.setText(CPU_tern);
            computerTurn();
        }


    }

    private void computerTurn() {
        current = input.getText().toString().toLowerCase();
        char nextChar;
        //if (current == null)
        //{
          //  word=fastDictionary.getAnyWordStartingWith(" ");
       // }
        //user completed the word
        if (fastDictionary.isWord(current)) {
            setResult(false);
               }
        //first entry by comp or user
        else if (word == null) {
            word = fastDictionary.getGoodWordStartingWith(current);
            Log.d("wordsafternull", word + " ");
            if (word != null) {//genarally allwas true of alphabet but false for number
                nextChar = word.charAt(current.length());
                input.append(String.valueOf(nextChar));
            }
        }//search a word starting with given prefix
        else if (word.startsWith(current)) {
            nextChar = word.charAt(current.length());
            input.append(String.valueOf(nextChar));
           //comp completed the word user win
            if (word.matches(input.getText().toString())) {
                Log.d("match", word.matches(input.getText().toString()) + " ");
                setResult(true);
                Log.d("error", "its me0 causing the mall function" + word);
            }
            else{
                usertern = true;
                onstart();
            }

        }//if the current prefix do not starts with the word that is comps mind search for another word with current prefix
        else {
            String duplicate = word;
            word = fastDictionary.getGoodWordStartingWith(current);
           Log.d("wordsafter", word + " ");

            if (word == null) {
                setResult(false);


            }//if there are word starting with current prefix continue play
            else {
                nextChar = word.charAt(current.length());
                input.append(String.valueOf(nextChar));
                //if the computer find the new word and and completes the word user win
                if (current.length() + 1 == word.length()) {

                    setResult(true);
                } else {
                    usertern = true;
                    onstart();
                }
            }


        }

    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        super.onKeyUp(keyCode, event);
        usertern = false;
        computerTurn();
        return true;
    }

    private void setResult(boolean status) {

        if (status)
            turn.setText("User win");
        else
            turn.setText("Computer win");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                usertern = random.nextBoolean();
                current = null;
                word=null;
                input.setText(null);
                onstart();
            }
        }, 2000);

    }


}
