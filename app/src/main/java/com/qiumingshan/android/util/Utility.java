package com.qiumingshan.android.util;

import android.text.TextUtils;

import com.qiumingshan.android.db.Question;
import com.qiumingshan.android.db.Questionset;
import com.qiumingshan.android.db.Test;
import com.qiumingshan.android.db.UserInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Utility {

    /**
     * 解析和处理服务器返回的用户信息
     * @param response
     * @return
     */
    public static boolean handleUserInfoResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allUserInfos = new JSONArray(response);
                for (int i = 0; i < allUserInfos.length(); i++) {
                    JSONObject userInfoObject = allUserInfos.getJSONObject(i);
                    UserInfo userInfo = new UserInfo();
                    userInfo.setUserName(userInfoObject.getString("username"));
                    userInfo.setPassword(userInfoObject.getString("password"));
                    userInfo.setCredit(userInfoObject.getDouble("credit"));
                    userInfo.setTesttimes(userInfoObject.getInt("testtimes"));
                    userInfo.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 解析和处理服务器返回的考试记录
     * @param response
     * @return
     */
    public static boolean handleTestResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allTests = new JSONArray(response);
                for (int i = 0; i < allTests.length(); i++) {
                    JSONObject testObject = allTests.getJSONObject(i);
                    Test test = new Test();
                    test.setTestId(testObject.getInt("testid"));
                    test.setUserId(testObject.getInt("userid"));
                    test.setProblemsetId(testObject.getInt("problemsetid"));
                    test.setUserAnswer(testObject.getInt("useranswer"));
                    test.setIsPass(testObject.getInt("pass"));
                    test.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 解析和处理服务器返回的题集信息
     * @param response
     * @return
     */
    public static boolean handleQuestionsetResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allQuestionsets = new JSONArray(response);
                for (int i = 0; i < allQuestionsets.length(); i++) {
                    JSONObject questionsetObject = allQuestionsets.getJSONObject(i);
                    Questionset questionset = new Questionset();
                    questionset.setProblemsetId(questionsetObject.getInt("problemsetid"));
                    questionset.setProblemId(questionsetObject.getInt("problemid"));
                    questionset.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    //TODO
    /**
     * 解析和处理服务器返回的题目信息
     * @param response
     * @return
     */
    public static boolean handleQuestionResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allQuestions = new JSONArray(response);
                for (int i = 0; i < allQuestions.length(); i++) {
                    JSONObject questionObject = allQuestions.getJSONObject(i);
                    Question question = new Question();
                    question.setQuestionid(questionObject.getInt(""));
                    question.setQ_type(questionObject.getInt(""));
                    question.setTitle(questionObject.getString(""));
                    question.setOptionA(questionObject.getString(""));
                    question.setOptionB(questionObject.getString(""));
                    question.setOptionC(questionObject.getString(""));
                    question.setOptionD(questionObject.getString(""));
                    question.setTips(questionObject.getString(""));
                    question.setAnswer(questionObject.getString(""));
                    question.setExplain(questionObject.getString(""));
                    question.setUser_answer(questionObject.getString(""));
                    question.setAnswertimes(questionObject.getInt(""));
                    question.setPasstimes(questionObject.getInt(""));
                    question.setImage(questionObject.getString(""));
                    question.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}