package com.uan.michaelsinner.sabergo.Data;

/**
 * Created by Michael Sinner on 18/4/2017.
 */

public class Question {
    private int questionID;
    private String answer;
    private String questionURL;
    private String area;
    private String questionCompetencia;
    private String questionKey;

    public Question(int questionID, String answer, String questionArea) {
        this.questionID = questionID;
        this.answer = answer;
        this.area = questionArea;
    }

    public Question(int questionID, String answer, String questionURL, String questionArea, String questionKey) {
        this.questionID = questionID;
        this.answer = answer;
        this.questionURL = questionURL;
        this.area = questionArea;

    }

    /*
        public Question(long questionID, String answer,  String questionURL){
            this.questionID = questionID;
            this.answer = answer;
            this.questionURL = questionURL;
        }
    */
    public Question() {

    }

    public Question(int questionID, String questionArea) {
        this.questionID = questionID;
        this.area = questionArea;
    }

    public long getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
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

    public String getArea() {
        return area;
    }

    public void setArea(String questionArea) {
        this.area = questionArea;
    }

    public String getQuestionCompetencia() {
        return questionCompetencia;
    }

    public void setQuestionCompetencia(String questionCompetencia) {
        this.questionCompetencia = questionCompetencia;
    }

    public String getQuestionKey() {
        return questionKey;
    }

    public void setQuestionKey(String questionToken) {
        this.questionKey = questionToken;
    }

    @Override
    public String toString() {
        return "Quest :" + getQuestionKey() + " ID : " + getQuestionID() + " Answ : " + getAnswer() + " are : " + getArea();
    }
}
