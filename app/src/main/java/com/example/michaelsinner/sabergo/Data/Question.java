package com.example.michaelsinner.sabergo.Data;

/**
 * Created by Michael Sinner on 18/4/2017.
 */

public class Question
{
    private long questionID;
    private String answer;
    private String questionURL;
    private String questionArea;
    private String questionCompetencia;
    private String questionToken;

    public Question(long questionID, String answer, String questionURL, String questionArea, String questionCompetencia) {
        this.questionID = questionID;
        this.answer = answer;
        this.questionURL = questionURL;
        this.questionArea = questionArea;
        this.questionCompetencia = questionCompetencia;
    }

    public Question(long questionID, String answer,  String questionURL){
        this.questionID = questionID;
        this.answer = answer;
        this.questionURL = questionURL;
    }

    public  Question(){

    }



    public long getQuestionID() {
        return questionID;
    }

    public void setQuestionID(long questionID) {
        this.questionID = questionID;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestionURL() {
        return questionURL;
    }

    public void setQuestionURL(String questionURL) {
        this.questionURL = questionURL;
    }

    public String getQuestionArea() {
        return questionArea;
    }

    public void setQuestionArea(String questionArea) {
        this.questionArea = questionArea;
    }

    public String getQuestionCompetencia() {
        return questionCompetencia;
    }

    public void setQuestionCompetencia(String questionCompetencia) {
        this.questionCompetencia = questionCompetencia;
    }

    public String getQuestionToken() {
        return questionToken;
    }

    public void setQuestionToken(String questionToken) {
        this.questionToken = questionToken;
    }
}
