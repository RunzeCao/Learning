package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.mine.Constant;
import com.example.myapplication.mine.UserBean;
import com.example.myapplication.mine.UserConstant;
import com.example.myapplication.utils.LogUtils;
import com.example.myapplication.utils.NetUtils;
import com.example.myapplication.utils.PreferenceUitl;
import com.example.myapplication.utils.StringUtils;
import com.example.myapplication.utils.ToastUtils;
import com.example.myapplication.volley.ApiParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.Map;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = LoginActivity.class.getSimpleName();
    private EditText login_user;
    private EditText login_pwd;
    private TextView login_ok;
    private TextView login_forget_pwd;
    private TextView login_register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        login_user = (EditText) this.findViewById(R.id.login_user);
        login_pwd = (EditText) this.findViewById(R.id.login_pwd);
        login_ok = (TextView) this.findViewById(R.id.login_ok);
        login_ok.setOnClickListener(this);
        login_forget_pwd = (TextView) this.findViewById(R.id.login_forget_pwd);
        login_forget_pwd.setOnClickListener(this);
        login_register = (TextView) this.findViewById(R.id.login_register);
        login_register.setOnClickListener(this);
        login_user.setText("15116404484");
        login_pwd.setText("123456");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_ok:
                attemptLogin();
                break;
            case R.id.login_forget_pwd:
                goAction(ResetPwdActivity.class, false);
                break;
            case R.id.login_register:
                goAction(RegisterActivity.class, true);
                break;
            default:
                break;
        }
    }

    private void attemptLogin() {
        boolean networkAvailable = NetUtils.isNetworkAvailable(this);
        if (!networkAvailable) {
            ToastUtils.makeText(this, this.getString(R.string.net_unavailable_tips));
            return;
        }
        //用户名
        String input_login_account = login_user.getText().toString().trim();
        if (TextUtils.isEmpty(input_login_account)) {
            ToastUtils.makeText(this, this.getString(R.string.input_register_user_empty));
            return;
        }

        boolean isMobileNo = StringUtils.isMobileNO(input_login_account);
        boolean isEmail = StringUtils.isEmail(input_login_account);
        if (!isMobileNo && !isEmail) {
            ToastUtils.makeText(this, this.getString(R.string.input_register_user_error));
        }

        if (isMobileNo) {
            UserConstant.saveUserPhone(this, input_login_account);
        }
        if (isEmail) {
            UserConstant.saveUserEmail(this, input_login_account);
        }
        //密码
        String input_login_pwd = login_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(input_login_pwd)) {
            ToastUtils.makeText(this, this.getString(R.string.input_register_pwd_empty));
            return;
        }
        if (!StringUtils.isMatchPassword(input_login_pwd)) {
            ToastUtils.makeText(this, this.getString(R.string.input_register_pwd_match));
            return;
        }

        //密码长度
        if (input_login_pwd.length() < 6 || input_login_pwd.length() > 16) {
            ToastUtils.makeText(this, this.getString(R.string.input_register_pwd_length));
            return;
        }

        requestLogin(input_login_account, input_login_pwd);
    }

    private void requestLogin(final String input_login_account, final String input_login_pwd) {
        executeRequest(new StringRequest(Request.Method.POST, Constant.REQUEST_URL + Constant.LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    LogUtils.d(response);
                    if ("ok".equalsIgnoreCase(jsonObject.optString("result"))) {
                        String message = jsonObject.optString("message");
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        JSONObject object = jsonArray.getJSONObject(0);
                        UserBean userBean = new UserBean(object);
                        UserConstant.savaUserInfo(mContext, userBean);
                        ToastUtils.makeText(mContext, message);
                        boolean hasInfo = PreferenceUitl.getInstance(mContext).getBoolean(Constant.PRF_COMPELED_USERINFO, false);
                        if (!hasInfo) {
                            goAction(PersionalInfoActivity1.class, true);
                        } else {
                            goAction(MainActivity.class, true);
                        }
                    } else {
                        ToastUtils.makeText(mContext, mRes.getString(R.string.login_fail));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtils.makeText(mContext, mRes.getString(R.string.login_fail));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG,error.toString());
                Toast.makeText(mActivity, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            public Map<String, String> getParams() {
                return new ApiParams().with("Account", input_login_account).with("Password", input_login_pwd);
            }
        });
    }

}
