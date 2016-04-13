package com.example.myapplication.mine;

/**
 * Created by 123 on 2016/4/12.
 */
public class Constant {

    //加密密钥
    public static final String DES_KEY = "com.qijiu";
    //验证码有效时间为60s
    public static final int VERIFICATION_EFFECTIVE_TIME = 60 * 1000;
    //验证码定时器计时时间间隔为1s
    public static final int VERIFICATION_TIMER_INTERVAL = 1000;

    //内网地址
//    public static final String REQUEST_URL = "http://192.168.1.221:1212/";
    //外网地址
    public static final String REQUEST_URL = "http://120.24.65.212:1910/";
    //手机号码注册
    public static final String REGISTRY_PHONE = "Passport/register";
    //邮箱注册
    public static final String REGISTRY_EMAIL = "Passport/emailregister";
    //验证手机号
    public static final String GET_VERIFICATION_CODE = "Passport/messagecode";
    //登录
    public static final String LOGIN = "Passport/login";
    //重置密码
    public static final String RESET_PWD = "Passport/resetpassword";
    //修改用户信息
    public static final String UPDATE_INFO = "My/reviseuserinfo";
    //头像上传
    public static final String UPLOAD_PHOTO = "My/uploadphoto";
    //紫外线数据请求
    public static final String REQUEST_UV = "Home/uvdatacount";

    //第一次登陆
    public static final String PRF_FIRST_LOGIN = "prf.first.login";
    //用户信息填写
    public static final String PRF_COMPELED_USERINFO = "prf.compeled.userinfo";
    //剪切图URL
    public static final String EXTRA_PHOTO_URL = "photo_url";
}
