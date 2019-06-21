package com.qiumingshan.android.util;

import android.text.TextUtils;
import android.util.Log;

import com.qiumingshan.android.db.Question;
import com.qiumingshan.android.db.Questionset;
import com.qiumingshan.android.db.Test;
import com.qiumingshan.android.db.UserInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.util.List;

public class Utility {

    private static final String TAG = "Utility";

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
                    List<UserInfo> userInfos = LitePal.findAll(UserInfo.class);
                    for (int j = 0; j < userInfos.size(); j++) {
                        if (userInfos.get(j).getUserName() ==  userInfoObject.getString("username")) {
                            userInfos.get(j).delete();
                        }
                    }
                    userInfo.setUserName(userInfoObject.getString("username"));
                    userInfo.setPassword(userInfoObject.getString("password"));
                 //   userInfo.setCredit(userInfoObject.getDouble("credit"));
                 //   userInfo.setTesttimes(userInfoObject.getInt("testtimes"));
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
                    List<Questionset> questionsets = LitePal.findAll(Questionset.class);
                    for (int j = 0; j < questionsets.size(); j++) {
                        if (questionsets.get(j).getProblemsetId() ==  questionsetObject.getInt("problemsetid")) {
                            questionsets.get(j).delete();
                        }
                    }
                    questionset.setProblemsetId(questionsetObject.getInt("problemsetid"));
                    questionset.setProblemId(questionsetObject.getString("problemid"));
                    questionset.setProblemsetName(questionsetObject.getString("problemsetname"));
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
                    List<Question> questions = LitePal.findAll(Question.class);
                    for (int j = 0; j < questions.size(); j++) {
                        String a=questionObject.getString("questionid");
                        String b =questions.get(j).getQuestionid();
                        Log.d(TAG, "handleQuestionResponse: "+ questions.get(j).getQuestionid() +"  " +questionObject.getString("questionid")+"\n");
                        if (a.equals(b)) {
                            questions.get(j).delete();
                            Log.d(TAG, "handleQuestionResponse: "+ "成功删除"+"\n");
                        }
                    }
                    question.setQuestionid(questionObject.getString("questionid"));
                    question.setQ_type(questionObject.getInt("q_type"));
                    question.setTitle(questionObject.getString("title"));
                    question.setOptionA(questionObject.getString("optionA"));
                    question.setOptionB(questionObject.getString("optionB"));
                    question.setOptionC(questionObject.getString("optionC"));
                    question.setOptionD(questionObject.getString("optionD"));
                  //  question.setTips(questionObject.getString(""));
                    question.setAnswer(questionObject.getString("answer"));
                    question.setFavorite(questionObject.getString("favorite"));
               //     question.setExplain(questionObject.getString(""));
                    question.setUser_answer(questionObject.getString("useranswer"));
                    question.setAnswertimes(questionObject.getInt("answertimes"));
                    question.setPasstimes(questionObject.getInt("passtimes"));
                //    question.setImage(questionObject.getString(""));
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
