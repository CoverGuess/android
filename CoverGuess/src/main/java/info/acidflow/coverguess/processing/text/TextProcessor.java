package info.acidflow.coverguess.processing.text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by paul on 10/03/14.
 */
public class TextProcessor {

    private TextProcessor(){

    }

    public static HashMap<Character, Integer> getLettersDistribution(String str){
        HashMap<Character, Integer> distribution = new HashMap<Character, Integer>();
        char[] strChars = str.toCharArray();
        for(int i = 0; i < strChars.length; i++){
            if(Character.isLetter(strChars[i])){
                if(distribution.get(strChars[i]) == null){
                    distribution.put(strChars[i], 1);
                }else{
                    distribution.put(strChars[i], distribution.get(strChars[i]) + 1);
                }
            }
        }
        return distribution;
    }

    public static List<String> getLettersList(String str){
        List<String> letters = new ArrayList<String>();
        char[] strChars = str.toCharArray();
        for(int i = 0; i < strChars.length; i++){
            if(Character.isLetter(strChars[i])){
                letters.add(String.valueOf(strChars[i]));
            }
        }
        return letters;
    }
}
