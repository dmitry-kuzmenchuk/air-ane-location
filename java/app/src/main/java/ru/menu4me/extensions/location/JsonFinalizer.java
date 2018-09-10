package ru.menu4me.extensions.location;

import android.util.Log;

public class JsonFinalizer {

    static String finalize(String json) {
        char ch;
        boolean done = false;
        String insert = "";
        String result = json;
        int level = 0;
        int position = 0;

        while(!done) {
            ch = result.charAt(position);
            if (position == result.length()) {
                done = true;
            }

            switch(ch) {
                case '{':
                    level++;
                    break;
                case '}':
                    level--;
                    break;
                case '"':
                    for (int i = 0; i < level; i++) {
                        insert += "\\";
                    }

                    String part1 = result.substring(0, position);
                    String part2 = result.substring(position, result.length());
                    result  = part1 + insert + part2;
                    Log.d(Constants.TAG, "result[iteration: " + position + "]: " + result);
                    position += level;
                    break;
            }

            insert = "";
            position++;
        }

        return result;
    }
}
