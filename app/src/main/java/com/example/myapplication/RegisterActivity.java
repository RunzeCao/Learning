package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.mine.Constant;
import com.example.myapplication.utils.NetUtils;
import com.example.myapplication.utils.StringUtils;
import com.example.myapplication.utils.ToastUtils;
import com.example.myapplication.volley.ApiParams;

import java.util.Map;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private EditText register_user;
    private EditText register_verification;
    private TextView register_get_verification;
    private EditText register_pwd;
    private TextView register_protocol;
    private TextView register_ok;
    private TextView register_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        initViews();
    }

    private void init() {

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
        executeRequest(new StringRequest(Request.Method.POST, Constant.REQUEST_URL + Constant.GET_VERIFICATION_CODE,requestListener(),errorListener()){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return new ApiParams().with("Phone",input_register_user);
            }
        });

    }

    private Response.Listener<String> requestListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("onResponse",response);
            }
        };
    }

    private void registerCompleteHandle() {
    }


}
