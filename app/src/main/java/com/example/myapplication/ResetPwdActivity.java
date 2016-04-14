package com.example.myapplication;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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

public class ResetPwdActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = ResetPwdActivity.class.getSimpleName();

    private ImageView reset_pwd_back;
    private EditText reset_pwd_user;

    private EditText reset_pwd_new;
    private TextView reset_pwd_ok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pwd);
        initViews();
    }


    private void initViews() {
        reset_pwd_back = (ImageView) this.findViewById(R.id.reset_pwd_back);
        reset_pwd_back.setOnClickListener(this);
        reset_pwd_user = (EditText) this.findViewById(R.id.reset_pwd_user);
        reset_pwd_new = (EditText) this.findViewById(R.id.reset_pwd_new);
        reset_pwd_ok = (TextView) this.findViewById(R.id.reset_pwd_ok);
        reset_pwd_ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reset_pwd_back:
                finish();
                break;
            case R.id.reset_pwd_ok:
                resetPwdCompleteHandle();
                break;
        }
    }

    private void resetPwdCompleteHandle() {
        boolean networkAvailable = NetUtils.isNetworkAvailable(mContext);
        if (!networkAvailable) {
            ToastUtils.makeText(mContext, mRes.getString(R.string.net_unavailable_tips));
            return;
        }
        //账号不能为空
        String input_reset_pwd_user = reset_pwd_user.getText().toString().trim();
        if (TextUtils.isEmpty(input_reset_pwd_user)) {
            ToastUtils.makeText(mContext, mRes.getString(R.string.input_register_user_empty));
            return;
        }
        //帐号格式验证
        boolean isMobileNO = StringUtils.isMobileNO(input_reset_pwd_user);
        boolean isEmail = StringUtils.isEmail(input_reset_pwd_user);
        if (!isMobileNO && !isEmail) {
            ToastUtils.makeText(mContext, mRes.getString(R.string.input_register_user_error));
            return;
        }

        //密码不能为空
        String input_reset_pwd_new = reset_pwd_new.getText().toString().trim();
        if (TextUtils.isEmpty(input_reset_pwd_new)) {
            ToastUtils.makeText(mContext, mRes.getString(R.string.reset_pwd_new_empty));
            return;
        }
        //密码只能是英文或数字
        if (!StringUtils.isMatchPassword(input_reset_pwd_new)) {
            ToastUtils.makeText(mContext, mRes.getString(R.string.input_register_pwd_match));
            return;
        }
        //密码长度
        if (input_reset_pwd_new.length() < 6 || input_reset_pwd_new.length() > 16) {
            ToastUtils.makeText(mContext, mRes.getString(R.string.input_register_pwd_length));
            return;
        }
        requestResetPwd(input_reset_pwd_user, input_reset_pwd_new);
    }

    private void requestResetPwd(final String input_reset_pwd_user, final String input_reset_pwd_new) {
        executeRequest(new StringRequest(Request.Method.POST, Constant.REQUEST_URL + Constant.RESET_PWD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                LogUtils.d(response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if ("ok".equalsIgnoreCase(jsonObject.optString("result"))){
                        String message = jsonObject.optString("message");
                        ToastUtils.makeText(mContext, message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtils.makeText(mContext, mRes.getString(R.string.reset_pwd_fail));
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
                return new ApiParams().with("phone",input_reset_pwd_user).with("NewPassword",input_reset_pwd_new);
            }
        });
    }

}
