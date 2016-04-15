package com.example.myapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.mine.Constant;
import com.example.myapplication.mine.UserConstant;
import com.example.myapplication.utils.StreamUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class MineActivity extends BaseActivity implements View.OnClickListener {
    private final String TAG = MineActivity.class.getSimpleName();

    //头像处理
    public static final String TEMP_PHOTO_FILE_NAME = "temp_avatar_photo.jpg";
    public static final int REQUEST_CODE_GALLERY = 0x1;
    public static final int REQUEST_CODE_TAKE_PICTURE = 0x2;
    public static final int REQUEST_CODE_CROP_IMAGE = 0x3;
    private File mFileTemp;

    private ImageView mine_back;
    private ImageView mine_save;
    private ImageView mine_avatar;
    private TextView mine_nickname;
    private TextView mine_birthday;
    private TextView mine_gender;
    private TextView mine_height;
    private TextView mine_weight;
    private TextView mine_phone;
    private TextView mine_email;

    private LinearLayout mine_nickname_llt;
    private LinearLayout mine_birthday_llt;
    private LinearLayout mine_gender_llt;
    private LinearLayout mine_height_llt;
    private LinearLayout mine_weight_llt;
    private LinearLayout mine_phone_llt;
    private LinearLayout mine_email_llt;
    private LinearLayout mine_reset_pwd_llt;

    private String mAvatarUrl;
    private String mNickName;
    private String mBirthday;
    private String mGender;
    private String mHeight;
    private String mWeight;
    private String mPhone;
    private String mEmail;

    private String[] mHeightData;
    private String[] mWeightData;
    private String[] mGenderData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
        init();
        initDatas();
        initViews();
    }

    private void init() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            mFileTemp = new File(Environment.getExternalStorageDirectory(), TEMP_PHOTO_FILE_NAME);
        } else {
            mFileTemp = new File(mContext.getFilesDir(), TEMP_PHOTO_FILE_NAME);
        }
    }

    private void initViews() {
        mine_nickname_llt = (LinearLayout) this.findViewById(R.id.mine_nickname_llt);
        mine_nickname_llt.setOnClickListener(this);
        mine_birthday_llt = (LinearLayout) this.findViewById(R.id.mine_birthday_llt);
        mine_birthday_llt.setOnClickListener(this);
        mine_gender_llt = (LinearLayout) this.findViewById(R.id.mine_gender_llt);
        mine_gender_llt.setOnClickListener(this);
        mine_height_llt = (LinearLayout) this.findViewById(R.id.mine_height_llt);
        mine_height_llt.setOnClickListener(this);
        mine_weight_llt = (LinearLayout) this.findViewById(R.id.mine_weight_llt);
        mine_weight_llt.setOnClickListener(this);
        mine_phone_llt = (LinearLayout) this.findViewById(R.id.mine_phone_llt);
        mine_phone_llt.setOnClickListener(this);
        mine_email_llt = (LinearLayout) this.findViewById(R.id.mine_email_llt);
        mine_email_llt.setOnClickListener(this);
        mine_reset_pwd_llt = (LinearLayout) this.findViewById(R.id.mine_reset_pwd_llt);
        mine_reset_pwd_llt.setOnClickListener(this);

        mine_back = (ImageView) this.findViewById(R.id.mine_back);
        mine_back.setOnClickListener(this);
        mine_save = (ImageView) this.findViewById(R.id.mine_save);
        mine_save.setOnClickListener(this);
        mine_avatar = (ImageView) this.findViewById(R.id.mine_avatar);
        mine_avatar.setOnClickListener(this);
        mine_nickname = (TextView) this.findViewById(R.id.mine_nickname);
        mine_birthday = (TextView) this.findViewById(R.id.mine_birthday);
        mine_gender = (TextView) this.findViewById(R.id.mine_gender);
        mine_height = (TextView) this.findViewById(R.id.mine_height);
        mine_weight = (TextView) this.findViewById(R.id.mine_weight);
        mine_phone = (TextView) this.findViewById(R.id.mine_phone);
        mine_email = (TextView) this.findViewById(R.id.mine_email);

        if (!TextUtils.isEmpty(mAvatarUrl)) {
            //TODO
        }
        if (!TextUtils.isEmpty(mNickName)) {
            mine_nickname.setText(mNickName);
        }
        if (!TextUtils.isEmpty(mBirthday)) {
            mine_birthday.setText(mBirthday);
        }
        if (!TextUtils.isEmpty(mGender)) {
            mine_gender.setText(mGender);
        }
        if (!TextUtils.isEmpty(mHeight)) {
            mine_height.setText(mHeight);
        }
        if (!TextUtils.isEmpty(mWeight)) {
            mine_weight.setText(mWeight);
        }
        if (!TextUtils.isEmpty(mPhone)) {
            mine_phone.setText(mPhone);
        }
        if (!TextUtils.isEmpty(mEmail)) {
            mine_email.setText(mEmail);
        }
    }

    private void initDatas() {
        mAvatarUrl = Constant.REQUEST_URL + UserConstant.getUserHeadUrl(mContext);
        mNickName = UserConstant.getUserName(mContext);
        mBirthday = UserConstant.getUserBirthday(mContext);
        mGender = UserConstant.getUserSex(mContext);
        mHeight = UserConstant.getUserHeight(mContext);
        mWeight = UserConstant.getUserWeight(mContext);
        mPhone = UserConstant.getUserPhone(mContext);
        mEmail = UserConstant.getUserEmail(mContext);
        Log.d(TAG, "mNickName:" + mNickName + ",mBirthday:" + mBirthday + ",mGender:" + mGender
                + ",mHeight:" + mHeight + ",mWeight:" + mWeight + ",mPhone:" + mPhone + ",mEmail:" + mEmail);
        mHeightData = mRes.getStringArray(R.array.height);
        mWeightData = mRes.getStringArray(R.array.weight);
        mGenderData = mRes.getStringArray(R.array.gender);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_back:
                finish();
                break;
            case R.id.mine_save:

                break;
            case R.id.mine_avatar:
                showTakePhoto();
                break;
            case R.id.mine_nickname_llt:
                break;
            case R.id.mine_birthday_llt:
                break;
            case R.id.mine_gender_llt:
                break;
            case R.id.mine_height_llt:
                break;
            case R.id.mine_weight_llt:
                break;
            case R.id.mine_phone_llt:
                break;
            case R.id.mine_email_llt:
                break;
            case R.id.mine_reset_pwd_llt:
                break;
            default:
                break;
        }
    }

    private void showTakePhoto() {
        new AlertDialog.Builder(mContext).setTitle("选择方式").setSingleChoiceItems(new String[]{"相机拍摄", "相册选取"}, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (which == 0) {
                    takePicture();
                } else {
                    pickPicture();
                }
            }
        }).show();
    }

    private void pickPicture() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, mRes.getString(R.string.gallery_select_pic)), REQUEST_CODE_GALLERY);
    }

    private void takePicture() {
        if (mFileTemp.exists()) {
            mFileTemp.delete();
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mFileTemp));
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == Activity.RESULT_OK) {
            Log.d(TAG, "requestCode:" + requestCode);
            if (REQUEST_CODE_TAKE_PICTURE == requestCode) {
                startCropImage();
            } else if (REQUEST_CODE_GALLERY == requestCode) {
                try {
                    InputStream inputStream = mContext.getContentResolver().openInputStream(intent.getData());
                    FileOutputStream fileOutputStream = new FileOutputStream(mFileTemp);
                    StreamUtils.copyStream(inputStream, fileOutputStream);
                    fileOutputStream.close();
                    inputStream.close();
                    startCropImage();
                } catch (Exception e) {
                    Log.e(TAG, "Error while creating temp file" + e.toString());
                }
            } else if (REQUEST_CODE_CROP_IMAGE == requestCode) {
                String photoUrl = intent.getStringExtra(Constant.EXTRA_PHOTO_URL);
                Log.d(TAG, "photoUrl:" + photoUrl);
                    //TODO
            }
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }

    private void startCropImage() {
        String path = mFileTemp.getPath();
        if (TextUtils.isEmpty(path)) {
            return;
        }
        Intent intent = new Intent(mContext, AvatarUploadActivity.class);
        intent.putExtra(Constant.EXTRA_PHOTO_URL, path);
        startActivityForResult(intent, REQUEST_CODE_CROP_IMAGE);
    }

}
