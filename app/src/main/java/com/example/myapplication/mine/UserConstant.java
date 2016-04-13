package com.example.myapplication.mine;

import android.content.Context;
import android.text.TextUtils;

import com.example.myapplication.utils.DESUtils;
import com.example.myapplication.utils.LogUtils;
import com.example.myapplication.utils.PreferenceUitl;


/**
 * Created by Administrator on 2016/3/20.
 */
public class UserConstant {
    private static final String TAG = UserConstant.class.getSimpleName();
    //用户ID
    public static final String USER_ID = "prf.user.id";
    //用户头像地址
    public static final String USER_HEADURL = "prf.user.headurl";
    //用户手机号码
    public static final String USER_PHONE = "prf.user.phone";
    //用户名
    public static final String USER_NAME = "prf.user.name";
    //用户性别
    public static final String USER_SEX = "prf.user.sex";
    //用户出生日期
    public static final String USER_BIRTHDAY = "prf.user.birthday";
    //用户身高
    public static final String USER_HEIGHT = "prf.user.height";
    //用户体重
    public static final String USER_WEIGHT = "prf.user.weight";
    //用户邮箱
    public static final String USER_EMAIL = "prf.user.email";

    /**
     * 保存用户信息
     */
    public static void savaUserInfo(Context mContext, UserBean bean) {
        if (bean == null) {
            LogUtils.e(TAG, "bean == null");
            return;
        }
        DESUtils des = new DESUtils(Constant.DES_KEY);
        //USER_ID
        if (!TextUtils.isEmpty(bean.getUserId())) {
            try {
                PreferenceUitl.getInstance(mContext).saveString(USER_ID, des.encrypt(bean.getUserId()));
            } catch (Exception e) {
                PreferenceUitl.getInstance(mContext).saveString(USER_ID, bean.getUserId());
            }
        }
        //USER_HEADURL
        if (!TextUtils.isEmpty(bean.getUserHeadUrl())) {
            try {
                PreferenceUitl.getInstance(mContext).saveString(USER_HEADURL, des.encrypt(bean.getUserHeadUrl()));
            } catch (Exception e) {
                e.printStackTrace();
                PreferenceUitl.getInstance(mContext).saveString(USER_HEADURL, bean.getUserHeadUrl());
            }
        }
        //USER_PHONE
        if (!TextUtils.isEmpty(bean.getUserPhone())) {
            try {
                PreferenceUitl.getInstance(mContext).saveString(USER_PHONE, des.encrypt(bean.getUserPhone()));
            } catch (Exception e) {
                e.printStackTrace();
                PreferenceUitl.getInstance(mContext).saveString(USER_PHONE, bean.getUserPhone());
            }
        }
        //USER_NAME
        if (!TextUtils.isEmpty(bean.getUserName())) {
            try {
                PreferenceUitl.getInstance(mContext).saveString(USER_NAME, des.encrypt(bean.getUserName()));
            } catch (Exception e) {
                e.printStackTrace();
                PreferenceUitl.getInstance(mContext).saveString(USER_NAME, bean.getUserName());
            }
        }
        //USER_SEX
        if (!TextUtils.isEmpty(bean.getUserSex())) {
            try {
                PreferenceUitl.getInstance(mContext).saveString(USER_SEX, des.encrypt(bean.getUserSex()));
            } catch (Exception e) {
                e.printStackTrace();
                PreferenceUitl.getInstance(mContext).saveString(USER_SEX, bean.getUserSex());
            }
        }
        //USER_BIRTHDAY
        if (!TextUtils.isEmpty(bean.getUserBirthday())) {
            try {
                PreferenceUitl.getInstance(mContext).saveString(USER_BIRTHDAY, des.encrypt(bean.getUserBirthday()));
            } catch (Exception e) {
                e.printStackTrace();
                PreferenceUitl.getInstance(mContext).saveString(USER_BIRTHDAY, bean.getUserBirthday());
            }
        }
        //USER_HEIGHT
        if (!TextUtils.isEmpty(bean.getUserHeight())) {
            try {
                PreferenceUitl.getInstance(mContext).saveString(USER_HEIGHT, des.encrypt(bean.getUserHeight()));
            } catch (Exception e) {
                e.printStackTrace();
                PreferenceUitl.getInstance(mContext).saveString(USER_HEIGHT, bean.getUserHeight());
            }
        }
        //USER_WEIGHT
        if (!TextUtils.isEmpty(bean.getUserWeight())) {
            try {
                PreferenceUitl.getInstance(mContext).saveString(USER_WEIGHT, des.encrypt(bean.getUserWeight()));
            } catch (Exception e) {
                e.printStackTrace();
                PreferenceUitl.getInstance(mContext).saveString(USER_WEIGHT, bean.getUserWeight());
            }
        }
    }

    /**
     * 获取用户id
     */
    public static String getUserID(Context context) {
        DESUtils des = new DESUtils(Constant.DES_KEY);
        String userId = PreferenceUitl.getInstance(context).getString(USER_ID, "");
        try {
            userId = des.decrypt(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userId;
    }

    /**
     * 保存用户id
     */
    public static void saveUserID(Context context, String userId) {
        DESUtils des = new DESUtils(Constant.DES_KEY);
        if (!TextUtils.isEmpty(userId)) {
            try {
                PreferenceUitl.getInstance(context).saveString(USER_ID, des.encrypt(userId));
            } catch (Exception e) {
                PreferenceUitl.getInstance(context).saveString(USER_ID, userId);
            }
        }
    }

    /**
     * 获取用户头像地址
     */
    public static String getUserHeadUrl(Context context) {
        DESUtils des = new DESUtils(Constant.DES_KEY);
        String userHeadUrl = PreferenceUitl.getInstance(context).getString(USER_HEADURL, "");
        try {
            userHeadUrl = des.decrypt(userHeadUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userHeadUrl;
    }

    /**
     * 保存用户头像地址
     */
    public static void saveUserHeadUrl(Context context, String userHeadurl) {
        DESUtils des = new DESUtils(Constant.DES_KEY);
        if (!TextUtils.isEmpty(userHeadurl)) {
            try {
                PreferenceUitl.getInstance(context).saveString(USER_HEADURL, des.encrypt(userHeadurl));
            } catch (Exception e) {
                PreferenceUitl.getInstance(context).saveString(USER_HEADURL, userHeadurl);
            }
        }
    }

    /**
     * 获取用户手机号码
     */
    public static String getUserPhone(Context context) {
        DESUtils des = new DESUtils(Constant.DES_KEY);
        String userPhone = PreferenceUitl.getInstance(context).getString(USER_PHONE, "");
        try {
            userPhone = des.decrypt(userPhone);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userPhone;
    }

    /**
     * 保存用户手机号码
     */
    public static void saveUserPhone(Context context, String userPhone) {
        DESUtils des = new DESUtils(Constant.DES_KEY);
        if (!TextUtils.isEmpty(userPhone)) {
            try {
                PreferenceUitl.getInstance(context).saveString(USER_PHONE, des.encrypt(userPhone));
            } catch (Exception e) {
                PreferenceUitl.getInstance(context).saveString(USER_PHONE, userPhone);
            }
        }
    }

    /**
     * 获取用户邮箱
     */
    public static String getUserEmail(Context context) {
        DESUtils des = new DESUtils(Constant.DES_KEY);
        String userEmail = PreferenceUitl.getInstance(context).getString(USER_EMAIL, "");
        try {
            userEmail = des.decrypt(userEmail);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userEmail;
    }

    /**
     * 保存用户邮箱
     */
    public static void saveUserEmail(Context context, String userEmail) {
        DESUtils des = new DESUtils(Constant.DES_KEY);
        if (!TextUtils.isEmpty(userEmail)) {
            try {
                PreferenceUitl.getInstance(context).saveString(USER_EMAIL, des.encrypt(userEmail));
            } catch (Exception e) {
                PreferenceUitl.getInstance(context).saveString(USER_EMAIL, userEmail);
            }
        }
    }

    /**
     * 获取用户名
     */
    public static String getUserName(Context context) {
        DESUtils des = new DESUtils(Constant.DES_KEY);
        String userName = PreferenceUitl.getInstance(context).getString(USER_NAME, "");
        try {
            userName = des.decrypt(userName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userName;
    }

    /**
     * 保存用户名
     */
    public static void saveUserName(Context context, String userName) {
        DESUtils des = new DESUtils(Constant.DES_KEY);
        if (!TextUtils.isEmpty(userName)) {
            try {
                PreferenceUitl.getInstance(context).saveString(USER_NAME, des.encrypt(userName));
            } catch (Exception e) {
                PreferenceUitl.getInstance(context).saveString(USER_NAME, userName);
            }
        }
    }

    /**
     * 获取用户性别
     */
    public static String getUserSex(Context context) {
        DESUtils des = new DESUtils(Constant.DES_KEY);
        String userSex = PreferenceUitl.getInstance(context).getString(USER_SEX, "");
        try {
            userSex = des.decrypt(userSex);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userSex;
    }

    /**
     * 保存用户性别
     */
    public static void saveUserSex(Context context, String userSex) {
        DESUtils des = new DESUtils(Constant.DES_KEY);
        if (!TextUtils.isEmpty(userSex)) {
            try {
                PreferenceUitl.getInstance(context).saveString(USER_SEX, des.encrypt(userSex));
            } catch (Exception e) {
                PreferenceUitl.getInstance(context).saveString(USER_SEX, userSex);
            }
        }
    }

    /**
     * 获取用户出生日期
     */
    public static String getUserBirthday(Context context) {
        DESUtils des = new DESUtils(Constant.DES_KEY);
        String userBirthday = PreferenceUitl.getInstance(context).getString(USER_BIRTHDAY, "");
        try {
            userBirthday = des.decrypt(userBirthday);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userBirthday;
    }

    /**
     * 保存用户出生日期
     */
    public static void saveUserBirthday(Context context, String userBirthday) {
        DESUtils des = new DESUtils(Constant.DES_KEY);
        if (!TextUtils.isEmpty(userBirthday)) {
            try {
                PreferenceUitl.getInstance(context).saveString(USER_BIRTHDAY, des.encrypt(userBirthday));
            } catch (Exception e) {
                PreferenceUitl.getInstance(context).saveString(USER_BIRTHDAY, userBirthday);
            }
        }
    }

    /**
     * 获取用户身高
     */
    public static String getUserHeight(Context context) {
        DESUtils des = new DESUtils(Constant.DES_KEY);
        String userHeight = PreferenceUitl.getInstance(context).getString(USER_HEIGHT, "");
        try {
            userHeight = des.decrypt(userHeight);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userHeight;
    }

    /**
     * 保存用户身高
     */
    public static void saveUserHeight(Context context, String userHeight) {
        DESUtils des = new DESUtils(Constant.DES_KEY);
        if (!TextUtils.isEmpty(userHeight)) {
            try {
                PreferenceUitl.getInstance(context).saveString(USER_HEIGHT, des.encrypt(userHeight));
            } catch (Exception e) {
                PreferenceUitl.getInstance(context).saveString(USER_HEIGHT, userHeight);
            }
        }
    }

    /**
     * 获取用户体重
     */
    public static String getUserWeight(Context context) {
        DESUtils des = new DESUtils(Constant.DES_KEY);
        String userWeight = PreferenceUitl.getInstance(context).getString(USER_WEIGHT, "");
        try {
            userWeight = des.decrypt(userWeight);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userWeight;
    }

    /**
     * 保存用户体重
     */
    public static void saveUserWeight(Context context, String userWeight) {
        DESUtils des = new DESUtils(Constant.DES_KEY);
        if (!TextUtils.isEmpty(userWeight)) {
            try {
                PreferenceUitl.getInstance(context).saveString(USER_WEIGHT, des.encrypt(userWeight));
            } catch (Exception e) {
                PreferenceUitl.getInstance(context).saveString(USER_WEIGHT, userWeight);
            }
        }
    }

    /**
     * 清除用户信息
     */
    public static void clearUserInfo(Context mContext) {
        PreferenceUitl.getInstance(mContext).saveString(USER_ID, "");
        PreferenceUitl.getInstance(mContext).saveString(USER_HEADURL, "");
        PreferenceUitl.getInstance(mContext).saveString(USER_PHONE, "");
        PreferenceUitl.getInstance(mContext).saveString(USER_NAME, "");
        PreferenceUitl.getInstance(mContext).saveString(USER_SEX, "");
        PreferenceUitl.getInstance(mContext).saveString(USER_BIRTHDAY, "");
        PreferenceUitl.getInstance(mContext).saveString(USER_HEIGHT, "");
        PreferenceUitl.getInstance(mContext).saveString(USER_WEIGHT, "");
    }
}
