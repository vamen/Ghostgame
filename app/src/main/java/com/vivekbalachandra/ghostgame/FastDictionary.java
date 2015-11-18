package com.vivekbalachandra.ghostgame;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Vivek Balachandran on 11/11/2015.
 */
public class FastDictionary {

    private static final int MIN_WORD_LENGTH = 5;
    private final TrieNode root;
    private String line = null;

    Runnable addword=new Runnable() {
        @Override
        public void run() {
            root.add(line.trim());
        }
    };
    Thread thread=new Thread(addword);
    public FastDictionary(Context ctx) {
        root = new TrieNode(' ');
        InputStream is = ctx.getResources().openRawResource(R.raw.words);
        BufferedReader buff = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line=buff.readLine()) != null) {

                String word = line.trim();
                if (word.length() >= MIN_WORD_LENGTH)
                    thread.run();

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public boolean isWord(String word) {
        return root.isWord(word);
    }

    public String getAnyWordStartingWith(String prefix) {
       return root.getAnyWordStartingWith(prefix);

    }


    public String getGoodWordStartingWith(String prefix) {
        return root.getGoodWordStartingWith(prefix);
    }
}

