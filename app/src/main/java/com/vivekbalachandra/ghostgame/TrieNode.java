package com.vivekbalachandra.ghostgame;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

/**
 * Trie node
 * Created by Vivek Balachandran on 11/11/2015.
 */
public class TrieNode {
    char mContent;
    boolean isEnd;
    LinkedList<TrieNode> children;
    int Rank;
    private ArrayList<String> wordList = new ArrayList<>();
    private ArrayList<TrieNode> rankHolder=new ArrayList<>();
    TrieNode(char c) {
        mContent = c;
        children = new LinkedList<TrieNode>();
        isEnd = false;
        Rank = new Random().nextInt(3);
    }

    TrieNode getChild(char c) {
        for (TrieNode child : children) {
            if (child.getChar() == c)
                return child;
        }
        return null;
    }

    char getChar() {
        return mContent;
    }

    void add(String word) {
        TrieNode t;
        //if(word.matches("null"))
        //  return ;
        char primechar;
        primechar = word.charAt(0);
        t = getChild(primechar);
        if (t == null) {
            //if there is no child  with char c add it to tree
            t = new TrieNode(primechar);
            children.add(t);
        }
        if (word.length() > 1) {
            t.add(word.substring(1));
        } else t.isEnd = true;
    }

    boolean isWord(String word) {

        TrieNode t = this;
        for (char c : word.toCharArray()) {
            if (t.getChild(c) == null)
                return false;
            else t = t.getChild(c);
        }
        if (t.isEnd)
            return true;

        return false;
    }


    public String getGoodWordStartingWith(String s) {
        StringBuilder sBuild = new StringBuilder(s);
        TrieNode t = this;
        int max = 1;
        String res = null;

        int i;
        TrieNode init;
        int index = 0;
        Random random = new Random();
        for (char c : s.toCharArray()) {
            t = t.getChild(c);
            if (t == null)
                return null;
        }
        init = t;
        while (true) {
            int j = t.children.size();
            t = t.children.get(random.nextInt(j));
            if (t == null)
                break;
            sBuild.append(t.getChar());
            if (t.isEnd) {

                wordList.add(sBuild.toString());
                t.Rank++;
                rankHolder.add(t);
                t = init;
                sBuild = new StringBuilder(s);
                if (wordList.size() >50||rankHolder.size()>50)
                    break;
            }
        }

         i=0;
        for (TrieNode w:rankHolder) {

            if (w.Rank > max) {
                max = w.Rank;
                Log.d("random", "@@@@@" + i + "^^^^" + w.Rank + "****");
                index = rankHolder.indexOf(w);
                i++;
            } else {
                Rank--;
                i++;
            }

        }

        /*for (i = 0; i < t.children.size(); i++) {
            String word=null;
            word=getAnyWordStartingWith(sBuild.toString());
            if(word != null)
            wordList.add(word);
        }*/

     /*   for(String word : wordList) {

            if (word.length()>max) {
               max =word.length();
                res=word;
            }

        }*/
        //i didn't why indexoutofbound happening
        res = wordList.get(index%wordList.size());
        wordList.clear();
        return res;

    }

    public String getAnyWordStartingWith(String s) {
        StringBuilder sBuild = new StringBuilder(s);
        Random random = new Random();
        TrieNode t = this;
        for (char c : s.toCharArray()) {
            t = t.getChild(c);
            if (t == null)
                return null;
        }
        while (true) {
            int i = t.children.size();
            //Log.d("****"+i+"****");
            t = t.children.get(random.nextInt(i));
            if (t == null)
                break;
            sBuild.append(t.getChar());
            if (t.isEnd) {
                return sBuild.toString();
            }
        }
        return null;
    }


}
