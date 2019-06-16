package com.qiumingshan.android.db;

import org.litepal.crud.LitePalSupport;

public class Questionset extends LitePalSupport {

    private int problemsetId;

    private int problemId;

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
}
