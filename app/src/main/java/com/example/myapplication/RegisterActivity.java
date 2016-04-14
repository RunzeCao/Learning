package com.example.myapplication;

import android.os.CountDownTimer;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.mine.Constant;
import com.example.myapplication.utils.LogUtils;
import com.example.myapplication.utils.NetUtils;
import com.example.myapplication.utils.StringUtils;
import com.example.myapplication.utils.ToastUtils;
import com.example.myapplication.volley.ApiParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private EditText register_user;
    private EditText register_verification;
    private TextView register_get_verification;
    private EditText register_pwd;
    private TextView register_protocol;
    private TextView register_ok;
    private TextView register_login;

    //验证码是否发送成功
    private boolean isVerificationSendSuc = false;
    //验证码是否过期
    private String VerificationCode = null;
    private boolean isVerificationCodeHasTimeout = false;
    private TimeCount mTimeCount;
    //是否同意隐私条款
    private boolean hasReadRegisteredProtocol = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        initViews();
    }

    private void init() {
        mTimeCount = new TimeCount(Constant.VERIFICATION_EFFECTIVE_TIME, Constant.VERIFICATION_TIMER_INTERVAL);
    }

    private void initViews() {
        register_user = (EditText) this.findViewById(R.id.register_user);
        register_verification = (EditText) this.findViewById(R.id.register_verification);
        register_get_verification = (TextView) this.findViewById(R.id.register_get_verification);
        register_get_verification.setOnClickListener(this);
        register_pwd = (EditText) this.findViewById(R.id.register_pwd);
        register_protocol = (TextView) this.findViewById(R.id.register_protocol);
        register_protocol.setOnClickListener(this);
        register_ok = (TextView) this.findViewById(R.id.register_ok);
        register_ok.setOnClickListener(this);
        register_login = (TextView) this.findViewById(R.id.register_login);
        register_login.setOnClickListener(this);
        register_user.setText("15116404484");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_get_verification:
                getVerificationCode();
                break;
            case R.id.register_login:
                goAction(LoginActivity.class, true);
                break;
            case R.id.register_ok:
                registerCompleteHandle();
                break;
            case R.id.register_protocol:
                break;
            default:
                break;
        }
    }

    private void getVerificationCode() {
        boolean networkAvailable = NetUtils.isNetworkAvailable(mContext);
        if (!networkAvailable) {
            ToastUtils.makeText(mContext, mRes.getString(R.string.net_unavailable_tips));
            return;
        }
        //手机号码不能为空
        String input_register_user = register_user.getText().toString().trim();
        if (TextUtils.isEmpty(input_register_user)) {
            ToastUtils.makeText(mContext, mRes.getString(R.string.input_register_user_empty));
            return;
        }
        //帐号格式是否是手机
        boolean isMobileNO = StringUtils.isMobileNO(input_register_user);
        boolean isEmail = StringUtils.isEmail(input_register_user);
        if (!isMobileNO && !isEmail) {
            ToastUtils.makeText(mContext, mRes.getString(R.string.phone_number_format_error));
            return;
        }
        verificationPhoneIsRegistery(input_register_user);
    }

    private void verificationPhoneIsRegistery(final String input_register_user) {
        executeRequest(new StringRequest(Request.Method.POST, Constant.REQUEST_URL + Constant.GET_VERIFICATION_CODE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("onResponse", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if ("ok".equalsIgnoreCase(jsonObject.optString("result"))) {
                        String data = jsonObject.optString("data");
                        String message = jsonObject.optString("message");
                        JSONObject object = new JSONObject(data);
                        VerificationCode = object.optString("code");
                        ToastUtils.makeText(mContext, message);
                        mTimeCount.start();
                        isVerificationSendSuc = true;
                    } else {
                        ToastUtils.makeText(mContext, mRes.getString(R.string.verification_send_fail));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastUtils.makeText(mContext, error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return new ApiParams().with("Phone", input_register_user);
            }
        });

    }


    private void registerCompleteHandle() {
        boolean networkAvailable = NetUtils.isNetworkAvailable(mContext);
        if (!networkAvailable) {
            ToastUtils.makeText(mContext, mRes.getString(R.string.net_unavailable_tips));
            return;
        }
        //账号不能为空
        String input_register_user = register_user.getText().toString().trim();
        if (TextUtils.isEmpty(input_register_user)) {
            ToastUtils.makeText(mContext, mRes.getString(R.string.input_register_user_empty));
            return;
        }
        //帐号格式验证
        boolean isMobileNO = StringUtils.isMobileNO(input_register_user);
        boolean isEmail = StringUtils.isEmail(input_register_user);
        if (!isMobileNO && !isEmail) {
            ToastUtils.makeText(mContext, mRes.getString(R.string.input_register_user_error));
            return;
        }
        //是否成功获取了验证码
        if (!isVerificationSendSuc) {
            ToastUtils.makeText(mContext, mRes.getString(R.string.pelease_get_verification_first));
            return;
        }
        //验证码不能为空
        String input_verification = register_verification.getText().toString().trim();
        if (TextUtils.isEmpty(input_verification)) {
            ToastUtils.makeText(mContext, mRes.getString(R.string.verification_empty));
            return;
        }
        //验证码是否过期
        if (isVerificationCodeHasTimeout) {
            ToastUtils.makeText(mContext, mRes.getString(R.string.verification_has_timeout));
            return;
        }
        //验证码不正确
        if (!input_verification.equalsIgnoreCase(VerificationCode)) {
            ToastUtils.makeText(mContext, mRes.getString(R.string.verification_error));
            return;
        }
        //密码不能为空
        String input_register_pwd = register_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(input_register_pwd)) {
            ToastUtils.makeText(mContext, mRes.getString(R.string.input_register_pwd_empty));
            return;
        }
        //密码只能是英文或数字
        if (!StringUtils.isMatchPassword(input_register_pwd)) {
            ToastUtils.makeText(mContext, mRes.getString(R.string.input_register_pwd_match));
            return;
        }
        //密码长度
        if (input_register_pwd.length() < 6 || input_register_pwd.length() > 16) {
            ToastUtils.makeText(mContext, mRes.getString(R.string.input_register_pwd_length));
            return;
        }
        requestRegistery(input_register_user, input_register_pwd);
    }

    private void requestRegistery(final String input_register_user, final String input_register_pwd) {
        executeRequest(new StringRequest(Request.Method.POST, Constant.REQUEST_URL + Constant.REGISTRY_PHONE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                LogUtils.d(response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if ("ok".equalsIgnoreCase(jsonObject.optString("result"))) {
                        ToastUtils.makeText(mContext, mRes.getString(R.string.registered_ok));
                        goAction(LoginActivity.class, true);
                    } else {
                        String errorstr = jsonObject.optString("message");
                        ToastUtils.makeText(mContext, errorstr);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastUtils.makeText(mContext, error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return new ApiParams().with("phone",input_register_user).with("password",input_register_pwd);
            }
        });
    }


    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数一次为总时长，和计时间隔。
            isVerificationCodeHasTimeout = false;
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程中显示

            register_get_verification.setText(String.format(mRes.getString(R.string.verification_timer_count), millisUntilFinished / 1000));
            register_get_verification.setEnabled(false);
            register_get_verification.setBackgroundResource(R.color.get_verification_btn_p_color);
            register_get_verification.setTextColor(mRes.getColor(R.color.colorWhite));
        }

        @Override
        public void onFinish() {//即使完毕后触发
            isVerificationSendSuc = false;
            VerificationCode = null;
            register_get_verification.setEnabled(true);
            register_get_verification.setText(getString(R.string.reget_verification));
            isVerificationCodeHasTimeout = true;
            register_get_verification.setClickable(true);
            register_get_verification.setBackgroundResource(R.drawable.get_verification_btn_bg_selector);
            register_get_verification.setTextColor(mRes.getColor(R.color.colorWhite));
        }
    }
}
