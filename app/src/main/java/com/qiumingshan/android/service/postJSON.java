package com.qiumingshan.android.service;

import com.qiumingshan.android.db.Question;
import com.qiumingshan.android.db.UserInfo;

import org.json.JSONArray;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.util.List;

public class postJSON {
    private String postString;

    private String getPostQuestionString() {

        JSONArray array = new JSONArray();
        List<Question> allQuestions = LitePal.findAll(Question.class);
        JSONObject[] jsonObjects = new JSONObject[allQuestions.size()];

        try {
            for (int i = 0; i < jsonObjects.length; i++) {
                jsonObjects[i].put("questionid", allQuestions.get(i).getQuestionid());
                jsonObjects[i].put("useranswer", allQuestions.get(i).getUser_answer());
                jsonObjects[i].put("answertimes", allQuestions.get(i).getAnswertimes());
                jsonObjects[i].put("passtimes", allQuestions.get(i).getPasstimes());
                array.put(jsonObjects[i]);
            }
            postString = array.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return postString;
    }

    /**
     * type = 0登录
     * type = 1注册
     * @param type
     * @return
     */
    private String getPostUserString(int type) {

        JSONArray array = new JSONArray();
        List<UserInfo> allUserInfos = LitePal.findAll(UserInfo.class);
        JSONObject[] jsonObjects = new JSONObject[allUserInfos.size()];

        try {
            for (int i = 0; i < jsonObjects.length; i++) {
                jsonObjects[i].put("username", allUserInfos.get(i).getUserName());
                jsonObjects[i].put("password", allUserInfos.get(i).getPassword());
                jsonObjects[i].put("type", type);
                array.put(jsonObjects[i]);
            }
            postString = array.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return postString;
    }
}
