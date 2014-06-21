package info.acidflow.coverguess.datamodel.answer;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by paul on 01/06/14.
 */
public class UserAnswer extends AbstractAnswer {

    private boolean[] mPrefilledPositions;
    private ArrayList<String> mCharactersToGuess;

    public UserAnswer(String value) {
        super(value);
        mPrefilledPositions = new boolean[ value.length() ];
        mCharactersToGuess = new ArrayList<String>();
        initAnswer( value );
        initCharactersToGuess(value);
    }

    @Override
    public void initAnswer( String value ){
        int countCharactersToGuess = 0;
        int countPrefilled = 0;
        for(int i = 0; i < value.length(); i++ ){
            if( !Character.isLetterOrDigit( value.charAt(i) ) ){
                setCharAt(i, value.charAt(i));
                mPrefilledPositions[ i ] = true;
                countPrefilled++;
            }else{
                countCharactersToGuess++;
            }
        }
        if(countCharactersToGuess > 12 ){
            prefillOthers(value, countPrefilled, countCharactersToGuess - 12 );
        }
    }

    private void prefillOthers( String value, int countPrefilled, int remainingToFill ){
        //TODO
    }

    public void initCharactersToGuess(String value) {
        for( int i = 0; i < getAnswerLength(); i++ ){
            if(!mPrefilledPositions[i]){
                mCharactersToGuess.add( String.valueOf( value.charAt( i ) ) );
            }
        }
    }

    public ArrayList<String> getCharactersToGuess(){
        return mCharactersToGuess;
    }

    @Override
    public void removeCharAt(int position) {
        if( position < 0 || position > getAnswerLength() ){
            throw new IllegalArgumentException();
        }
        if( !mPrefilledPositions[ position ]){
            super.removeCharAt(position, '?' );
        }
    }

    @Override
    public void setCharAt(int position, char c) {
        if(position < 0 || position > getAnswerLength()){
            throw new IllegalArgumentException();
        }
        if( !mPrefilledPositions[ position ] ) {
            super.setCharAt( position, c );
        }
    }

    @Override
    public String getAnswerString() {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < getAnswerLength(); i++){
            if(getFilledPositions()[i]) {
                stringBuilder.append(getAnswer()[i]);
            }else{
                stringBuilder.append("?");
            }
        }
        return stringBuilder.toString();
    }

    public int getNextPosition(){
        for( int i = 0; i < getFilledPositions().length; i++ ){
            if(!getFilledPositions()[i]){
                return i;
            }
        }
        return getFilledPositions().length;
    }

    public int getPreviousPosition(){
        int currentPosition = getNextPosition();
        for(int i = currentPosition - 1; i >= 0; i--){
            if(getFilledPositions()[i] && !mPrefilledPositions[i] ){
                return i;
            }
        }
        return currentPosition;
    }
}
