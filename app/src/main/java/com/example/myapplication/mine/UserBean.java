package com.example.myapplication.mine;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONObject;


/**
 * Created by Administrator on 2016/3/20.
 */
public class UserBean {
    private static final String TAG = UserBean.class.getSimpleName();

    //用户id
    private String userId;
    //用户头像地址
    private String userHeadUrl;
    //用户手机号码
    private String userPhone;
    //用户名
    private String userName;
    //用户性别
    private String userSex;
    //用户出生日期
    private String userBirthday;
    //用户身高
    private String userHeight;
    //用户体重
    private String userWeight;

    public UserBean() {

    }

    public UserBean(JSONObject obj) {
        if (obj != null) {
            String userId = obj.optString("UserID");
            String userHeadUrl = obj.optString("HeadImg");
            String userPhone = obj.optString("Phone");
            String userName = obj.optString("UserName");
            String userSex = obj.optString("Sex");
            String userBirthday = obj.optString("Birthday");
            String userHeight = obj.optString("Height");
            String userWeight = obj.optString("Weight");
            if (!TextUtils.isEmpty(userId)) {
                setUserId(userId);
            }
            if (!TextUtils.isEmpty(userHeadUrl)) {
                setUserHeadUrl(userHeadUrl);
            }
            if (!TextUtils.isEmpty(userPhone)) {
                setUserPhone(userPhone);
            }
            if (!TextUtils.isEmpty(userName)) {
                setUserName(userName);
            }
            if (!TextUtils.isEmpty(userSex)) {
                setUserSex(userSex);
            }
            if (!TextUtils.isEmpty(userBirthday)) {
                setUserBirthday(userBirthday);
            }
            if (!TextUtils.isEmpty(userHeight)) {
                setUserHeight(userHeight);
            }
            if (!TextUtils.isEmpty(userWeight)) {
                setUserWeight(userWeight);
            }
        } else {
            Log.e(TAG, "obj == null");

        }
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserHeadUrl() {
        return userHeadUrl;
    }

    public void setUserHeadUrl(String userHeadUrl) {
        this.userHeadUrl = userHeadUrl;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(String userBirthday) {
        this.userBirthday = userBirthday;
    }

    public String getUserHeight() {
        return userHeight;
    }

    public void setUserHeight(String userHeight) {
        this.userHeight = userHeight;
    }

    public String getUserWeight() {
        return userWeight;
    }

    public void setUserWeight(String userWeight) {
        this.userWeight = userWeight;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "userId='" + userId + '\'' +
                ", userHeadUrl='" + userHeadUrl + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", userName='" + userName + '\'' +
                ", userSex='" + userSex + '\'' +
                ", userBirthday='" + userBirthday + '\'' +
                ", userHeight='" + userHeight + '\'' +
                ", userWeight='" + userWeight + '\'' +
                '}';
    }
}
