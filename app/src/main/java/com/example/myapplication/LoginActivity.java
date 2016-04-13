package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.mine.UserConstant;
import com.example.myapplication.utils.NetUtils;
import com.example.myapplication.utils.StringUtils;
import com.example.myapplication.utils.ToastUtils;

public class LoginActivity extends Activity implements View.OnClickListener {
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_ok:
                attemptLogin();
                break;
            case R.id.login_forget_pwd:
                break;
            case R.id.login_register:
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
        requestLogin(input_login_account,input_login_pwd);
    }

    private void requestLogin(String input_login_account, String input_login_pwd) {

    }


}
