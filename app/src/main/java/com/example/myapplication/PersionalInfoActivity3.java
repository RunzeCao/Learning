package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.mine.Constant;
import com.example.myapplication.mine.UserConstant;
import com.example.myapplication.utils.PreferenceUitl;
import com.example.myapplication.utils.ToastUtils;
import com.example.myapplication.volley.ApiParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class PersionalInfoActivity3 extends BaseActivity implements View.OnClickListener {
    private static final String TAG = PersionalInfoActivity3.class.getSimpleName();
    private Spinner info3_spf;
    private Button info3_continue;
    private Button info3_have_account;

    private String[] mSPF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persional_info3);
        initDatas();
        initViews();
    }

    private void initViews() {
        info3_spf = (Spinner) this.findViewById(R.id.info3_spf);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mSPF);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        info3_spf.setAdapter(adapter);
        info3_continue = (Button) this.findViewById(R.id.info3_continue);
        info3_continue.setOnClickListener(this);
        info3_have_account = (Button) this.findViewById(R.id.info3_have_account);
        info3_have_account.setOnClickListener(this);
    }

    private void initDatas() {
        mSPF = mRes.getStringArray(R.array.spf);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.info3_continue:
                continueHandle();
                break;
            case R.id.info3_have_account:
                break;
        }
    }

    private void continueHandle() {
        executeRequest(new StringRequest(Request.Method.POST, Constant.REQUEST_URL + Constant.UPDATE_INFO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG,response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if ("ok".equalsIgnoreCase(jsonObject.optString("result"))) {
                        goAction(MainActivity.class, true);
                        PreferenceUitl.getInstance(mContext).saveBoolean(Constant.PRF_COMPELED_USERINFO, true);
                        ToastUtils.makeText(mContext, mRes.getString(R.string.upload_userinfo_ok));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtils.makeText(mContext, mRes.getString(R.string.upload_userinfo_fail));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastUtils.makeText(mContext, mRes.getString(R.string.upload_userinfo_fail));
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return new ApiParams().with("UserID", UserConstant.getUserID(mContext))
                        .with("UserName", UserConstant.getUserName(mContext))
                        .with("NickName", UserConstant.getUserName(mContext))
                        .with("Birthday", UserConstant.getUserBirthday(mContext))
                        .with("Sex", UserConstant.getUserSex(mContext))
                        .with("Height", UserConstant.getUserHeight(mContext))
                        .with("Weight", UserConstant.getUserWeight(mContext));
            }
        });
    }

}
