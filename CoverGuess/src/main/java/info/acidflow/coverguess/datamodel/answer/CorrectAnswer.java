package info.acidflow.coverguess.datamodel.answer;

/**
 * Created by paul on 01/06/14.
 */
public class CorrectAnswer extends AbstractAnswer {

    public CorrectAnswer(String value) {
        super( value );
        initAnswer( value );
    }

    @Override
    protected void initAnswer( String value ){
        for(int i = 0; i < value.length(); i++ ){
            setCharAt(i, value.charAt(i) );
        }
    }
}
