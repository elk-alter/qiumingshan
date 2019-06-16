package com.qiumingshan.android.db;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

public class Test extends LitePalSupport {

    private int testId;

    private int userId;

    private int problemsetId;

    private int userAnswer;

    private int isPass;

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProblemsetId() {
        return problemsetId;
    }

    public void setProblemsetId(int problemsetId) {
        this.problemsetId = problemsetId;
    }

    public int getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(int userAnswer) {
        this.userAnswer = userAnswer;
    }

    public int getIsPass() {
        return isPass;
    }

    public void setIsPass(int isPass) {
        this.isPass = isPass;
    }
}
