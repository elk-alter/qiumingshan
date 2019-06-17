package com.qiumingshan.android.db;

import org.litepal.crud.LitePalSupport;

public class Questionset extends LitePalSupport {

    private int problemsetId;

    private int problemId;

    private String problemsetName;

    private int problemsetImage;

    public Questionset() {
    }
    public Questionset(String problemsetName, int problemsetImage) {
        this.problemsetName = problemsetName;
        this.problemsetImage = problemsetImage;
    }
    public int getProblemsetId() {
        return problemsetId;
    }

    public void setProblemsetId(int problemsetId) {
        this.problemsetId = problemsetId;
    }

    public int getProblemId() {
        return problemId;
    }

    public void setProblemId(int problemId) {
        this.problemId = problemId;
    }

    public String getProblemsetName() {
        return problemsetName;
    }

    public void setProblemsetName(String problemsetName) {
        this.problemsetName = problemsetName;
    }

    public int getProblemsetImage() {
        return problemsetImage;
    }

    public void setProblemsetImage(int problemsetImage) {
        this.problemsetImage = problemsetImage;
    }
}
