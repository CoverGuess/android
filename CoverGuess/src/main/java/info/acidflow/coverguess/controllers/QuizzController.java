package info.acidflow.coverguess.controllers;

import java.util.List;

import info.acidflow.coverguess.datamodel.Album;

/**
 * Created by paul on 06/03/14.
 */
public class QuizzController {

    private static QuizzController mInstance;

    private List<Album> mQuizz;
    private int mQuizzPosition;

    private QuizzController(){
        super();
    }

    public static QuizzController getInstance(){
        if(mInstance == null){
            mInstance = new QuizzController();
        }
        return mInstance;
    }

    public void initialize(List<Album> quizz){
        mQuizzPosition = 0;
        mQuizz = quizz;
    }

    public void incrementQuizzPosition(){
        mQuizzPosition++;
    }

    public Album getCurrentAlbum(){
        return mQuizz.get(mQuizzPosition);
    }
}
