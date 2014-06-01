package info.acidflow.coverguess.datamodel.answer;

import java.util.Arrays;

/**
 * Created by paul on 01/06/14.
 */
public abstract class AbstractAnswer {

    private Character[] mAnswer;
    private boolean[] mFilledPosition;

    protected AbstractAnswer( String value ){
        super();
        mAnswer = new Character[value.length()];
        mFilledPosition = new boolean[value.length()];
    }

    protected abstract void initAnswer( String value );

    public int getFirstNotFilledPosition(){
        for(int i = 0; i < mFilledPosition.length; i++){
            if(!mFilledPosition[i]){
                return i;
            }
        }
        return mFilledPosition.length;
    }

    public void setCharAt( int position, char c ){
        if( position > mAnswer.length ){
            throw new IllegalArgumentException();
        }
        mAnswer[ position ] = c;
        mFilledPosition[ position ] = true;
    }

    public void removeCharAt( int position ){
        removeCharAt( position, null );
    }

    protected void removeCharAt( int position , Character removeReplacement ){
        if( position > mAnswer.length || position < 0 ){
            throw new IllegalArgumentException();
        }
        mAnswer[ position ] = removeReplacement;
        mFilledPosition[ position ] = false;
    }

    public int getAnswerLength(){
        return mAnswer.length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof AbstractAnswer)) return false;

        AbstractAnswer that = (AbstractAnswer) o;

        if (!Arrays.equals(mAnswer, that.mAnswer)) return false;
        if (!Arrays.equals(mFilledPosition, that.mFilledPosition)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(mAnswer);
        result = 31 * result + Arrays.hashCode(mFilledPosition);
        return result;
    }

    public String getAnswerString(){
        StringBuilder stringBuilder = new StringBuilder();
        for( int i = 0; i < mAnswer.length; i++ ){
            stringBuilder.append( mAnswer[i] );
        }
        return stringBuilder.toString();
    }

    public boolean[] getFilledPositions(){
        return mFilledPosition;
    }

    public Character[] getAnswer(){
        return mAnswer;
    }
}
