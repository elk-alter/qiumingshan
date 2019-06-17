package com.qiumingshan.android.db;

import org.litepal.crud.LitePalSupport;

import java.sql.Blob;

public class Question extends LitePalSupport {

    private int questionid;

    private int q_type;

    private String title;//问题

    private String optionA;

    private String optionB;

    private String optionC;

    private String optionD;

    private String tips;

    private String answer;

    private String explain;

    private String user_answer;

    private int answertimes;

    private int passtimes;

    private String image;

    public Question() {

    }
    public Question(String title, String optionA, String optionB, String optionC, String optionD) {
        this.title = title;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
    }

    public int getQ_type() {
        return q_type;
    }

    public void setQ_type(int q_type) {
        this.q_type = q_type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getUser_answer() {
        return user_answer;
    }

    public void setUser_answer(String user_answer) {
        this.user_answer = user_answer;
    }

    public int getAnswertimes() {
        return answertimes;
    }

    public void setAnswertimes(int answertimes) {
        this.answertimes = answertimes;
    }

    public int getPasstimes() {
        return passtimes;
    }

    public void setPasstimes(int passtimes) {
        this.passtimes = passtimes;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getQuestionid() {
        return questionid;
    }

    public void setQuestionid(int questionid) {
        this.questionid = questionid;
    }
}
