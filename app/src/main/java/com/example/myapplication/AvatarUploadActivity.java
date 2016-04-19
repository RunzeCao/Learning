package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.mine.Constant;
import com.example.myapplication.mine.UserConstant;
import com.example.myapplication.utils.ImageUtils;
import com.example.myapplication.utils.ToastUtils;
import com.example.myapplication.volley.ApiParams;
import com.example.myapplication.wight.crop.CropImage;
import com.example.myapplication.wight.crop.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class AvatarUploadActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = AvatarUploadActivity.class.getSimpleName();

    public static final int NO_STORAGE_ERROR = -1;
    public static final int CANNOT_STAT_ERROR = -2;
    private static final int MSG_REQUEST_SUCCESS = 0x00001;
    private static final int MSG_REQUEST_FAILED = 0x00002;

    private CropImageView mImageView;
    private String mImagePath;
    private String mSavedpath;
    Bitmap bitmap;

    private CropImage mCropImg;
    private ImageView rotateBtn;
    private TextView confirmBtn;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar_upload);
        initView();
        showStorageToast(this);
        Intent intent = getIntent();
        mImagePath = intent.getStringExtra(Constant.EXTRA_PHOTO_URL);

        if (mImagePath == null) {
            this.finish();
            return;
        }
        Log.d(TAG, "mImagePath:" + mImagePath);
        try {
            mCropImg = new CropImage(this, mImageView, mImagePath, true);
        } catch (Exception e1) {
            ToastUtils.makeText(this, mRes.getString(R.string.no_find_picture));
            e1.printStackTrace();
            this.finish();
            return;
        }

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mCropImg.startCrop();
            }
        }, 10);
    }

    private void initView() {
        mImageView = (CropImageView) this.findViewById(R.id.image);
        rotateBtn = (ImageView) this.findViewById(R.id.rotateBtn);
        rotateBtn.setOnClickListener(this);
        confirmBtn = (TextView) this.findViewById(R.id.confirmBtn);
        confirmBtn.setOnClickListener(this);
    }

    public static void showStorageToast(Activity activity) {
        showStorageToast(activity, calculatePicturesRemaining(activity));
    }


    public static int calculatePicturesRemaining(Activity activity) {
        try {
            String storageDirectory = "";
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                storageDirectory = Environment.getExternalStorageDirectory().toString();
            } else {
                storageDirectory = activity.getFilesDir().toString();
            }
            StatFs stat = new StatFs(storageDirectory);
            float remaining = ((float) stat.getAvailableBlocks() * (float) stat.getBlockSize()) / 400000F;
            return (int) remaining;
        } catch (Exception ex) {
            return CANNOT_STAT_ERROR;
        }
    }

    public static void showStorageToast(Activity activity, int remaining) {
        String noStorageText = null;
        if (remaining == NO_STORAGE_ERROR) {
            String state = Environment.getExternalStorageState();
            if (state.equals(Environment.MEDIA_CHECKING)) {
                noStorageText = activity.getString(R.string.preparing_card);
            } else {
                noStorageText = activity.getString(R.string.no_storage_card);
            }
        } else if (remaining < 1) {
            noStorageText = activity.getString(R.string.not_enough_space);
        }
        if (noStorageText != null) {
            ToastUtils.makeText(activity, noStorageText);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirmBtn:
                try {
                    mSavedpath = mCropImg.saveToLocal();
                    uploadPhoto();
                } catch (Exception e) {
                    finish();
                }
                break;
            case R.id.rotateBtn:
                mCropImg.startRotate(90);
                break;
        }
    }

    private void uploadPhoto() {
        if (!TextUtils.isEmpty(mImagePath)) {

            Log.d(TAG,"mImagePath+"+mImagePath);
            bitmap = BitmapFactory.decodeFile(mSavedpath);
            Log.d(TAG,"bitmap"+bitmap);

            executeRequest(new StringRequest(Request.Method.POST, Constant.REQUEST_URL + Constant.UPLOAD_PHOTO, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(TAG, response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if ("ok".equalsIgnoreCase(jsonObject.optString("result"))){
                            ToastUtils.makeText(mContext,"头像上传成功");
                            Intent intent = new Intent();
                            intent.putExtra(Constant.EXTRA_PHOTO_URL, mImagePath);

                            setResult(RESULT_OK, intent);
                            finish();
                        }else{
                            ToastUtils.makeText(mContext,"头像上传失败");
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    ToastUtils.makeText(mContext,"服务器上传头像失败");
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    return new ApiParams().with("UserID", UserConstant.getUserID(mContext)).with("Format", "jpg").with("UserImg", ImageUtils.jpeg2String(bitmap));
                }
            });
        } else {
            this.finish();
        }
    }
}
