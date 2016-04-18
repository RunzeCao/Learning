package com.example.myapplication.wight.crop;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.media.ExifInterface;
import android.media.FaceDetector;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.StatFs;
import android.util.Log;


import com.example.myapplication.R;
import com.example.myapplication.utils.ToastUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.CountDownLatch;



@SuppressLint("NewApi")
public class CropImage {

    final int IMAGE_MAX_SIZE = 1024;

    private static final String TAG = "CropImage";
    public static final String IMAGE_PATH = "image-path";
    public static final String SCALE = "scale";
    public static final String ORIENTATION_IN_DEGREES = "orientation_in_degrees";
    public static final String ASPECT_X = "aspectX";
    public static final String ASPECT_Y = "aspectY";
    public static final String OUTPUT_X = "outputX";
    public static final String OUTPUT_Y = "outputY";
    public static final String SCALE_UP_IF_NEEDED = "scaleUpIfNeeded";
    public static final String CIRCLE_CROP = "circleCrop";
    public static final String RETURN_DATA = "return-data";
    public static final String RETURN_DATA_AS_BITMAP = "data";
    public static final String ACTION_INLINE_DATA = "inline-data";

    // These are various options can be specified in the intent.
    private Bitmap.CompressFormat mOutputFormat = Bitmap.CompressFormat.JPEG;
    private Uri mSaveUri = null;
    private boolean mDoFaceDetection = true;
    private boolean mCircleCrop = false;
    private final Handler mHandler = new Handler();

    private int mAspectX = 0;
    private int mAspectY = 0;
    private int mOutputX;
    private int mOutputY;
    private boolean mScale = true;
    private CropImageView mImageView;
    private ContentResolver mContentResolver;
    private Bitmap mBitmap;
    private String mImagePath;

    boolean mWaitingToPick; // Whether we are wait the user to pick a face.
    boolean mSaving = false; // Whether the "save" button is already clicked.
    HighlightView mCrop;

    // These options specifiy the output image size and whether we should
    // scale the output to fit it (or just crop it).
    private boolean mScaleUp = true;
    private Context mContext;

    /**
     * 初始化时，是否旋转到正常角度
     */
    private boolean mRotate;

    public CropImage(Context context, CropImageView cropImageView, String imagePath) throws Exception {
        this.mImageView = cropImageView;
        this.mContext = context;
        this.mImagePath = imagePath;
        this.mImageView.setCropImg(this);
        init();
    }

    public CropImage(Context context, CropImageView cropImageView, String imagePath, boolean rotate) throws Exception {
        this.mImageView = cropImageView;
        this.mContext = context;
        this.mImagePath = imagePath;
        this.mImageView.setCropImg(this);
        this.mRotate = rotate;
        init();
    }

    private void init() throws Exception {
        mContentResolver = mContext.getContentResolver();
        mBitmap = getBitmap(mImagePath);
        if (mBitmap == null) {
            throw new Exception("file not exist");
        }
        mSaveUri = getImageUri(mImagePath);
        if (mRotate) {
            int angle = getDegree(mImagePath);
            mBitmap = rotate(mBitmap, angle);
        }
    }

    public void startCrop() {
        startFaceDetection();
    }

    public void startRotate(float degree) {
        mBitmap = Util.rotateImage(mBitmap, 90);
        RotateBitmap rotateBitmap = new RotateBitmap(mBitmap);
        mImageView.setImageRotateBitmapResetBase(rotateBitmap, true);
        mRunFaceDetection.run();
    }

    private Uri getImageUri(String path) {
        return Uri.fromFile(new File(path));
    }

    private Bitmap getBitmap(String path) {

        Uri uri = getImageUri(path);
        InputStream in = null;
        try {
            in = mContentResolver.openInputStream(uri);
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, o);
            in.close();
            int scale = 1;
            if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
                scale = (int) Math.pow(2,
                        (int) Math.round(Math.log(IMAGE_MAX_SIZE / (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
            }
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            in = mContentResolver.openInputStream(uri);
            Bitmap b = BitmapFactory.decodeStream(in, null, o2);
            in.close();
            return b;
        } catch (FileNotFoundException e) {
            Log.e(TAG, "file " + path + " not found");
        } catch (IOException e) {
            Log.e(TAG, "file " + path + " not found");
        }
        return null;
    }

    private void startFaceDetection() {
        mImageView.setImageBitmapResetBase(mBitmap, true);
        Util.startBackgroundJob(new Runnable() {
            public void run() {
                final CountDownLatch latch = new CountDownLatch(1);
                final Bitmap b = mBitmap;
                mHandler.post(new Runnable() {
                    public void run() {
                        if (b != mBitmap && b != null) {
                            mImageView.setImageBitmapResetBase(b, true);
                            mBitmap.recycle();
                            mBitmap = b;
                        }
                        if (mImageView.getScale() == 1F) {
                            mImageView.center(true, true);
                        }
                        latch.countDown();
                    }
                });
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                mRunFaceDetection.run();
            }
        });
    }

    public String saveToLocal() throws Exception {
        return onSaveClicked();
    }

    private String onSaveClicked() throws Exception {
        if (mSaving)
            return null;
        if (mCrop == null) {
            return null;
        }
        mSaving = true;
        Rect r = mCrop.getCropRect();
        int width = r.width();
        int height = r.height();
        Bitmap croppedImage;
        try {
            croppedImage = Bitmap.createBitmap(width, height, mCircleCrop ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        } catch (Exception e) {
            throw e;
        }
        if (croppedImage == null) {
            return null;
        }
        {
            Canvas canvas = new Canvas(croppedImage);
            Rect dstRect = new Rect(0, 0, width, height);
            canvas.drawBitmap(mBitmap, r, dstRect, null);
        }
        if (mCircleCrop) {
            Canvas c = new Canvas(croppedImage);
            Path p = new Path();
            p.addCircle(width / 2F, height / 2F, width / 2F, Path.Direction.CW);
            c.clipPath(p, Region.Op.DIFFERENCE);
            c.drawColor(0x00000000, PorterDuff.Mode.CLEAR);
        }
        if (mOutputX != 0 && mOutputY != 0) {
            if (mScale) {
                Bitmap old = croppedImage;
                croppedImage = Util.transform(new Matrix(), croppedImage, mOutputX, mOutputY, mScaleUp);
                if (old != croppedImage) {
                    old.recycle();
                }
            } else {
                Bitmap b = Bitmap.createBitmap(mOutputX, mOutputY, Bitmap.Config.RGB_565);
                Canvas canvas = new Canvas(b);
                Rect srcRect = mCrop.getCropRect();
                Rect dstRect = new Rect(0, 0, mOutputX, mOutputY);
                int dx = (srcRect.width() - dstRect.width()) / 2;
                int dy = (srcRect.height() - dstRect.height()) / 2;
                srcRect.inset(Math.max(0, dx), Math.max(0, dy));
                dstRect.inset(Math.max(0, -dx), Math.max(0, -dy));
                canvas.drawBitmap(mBitmap, srcRect, dstRect, null);
                croppedImage.recycle();
                croppedImage = b;
            }
        }
        final Bitmap b = croppedImage;
        return saveOutput(b);
    }

    private String saveOutput(Bitmap croppedImage) {
        if (mSaveUri != null) {
            OutputStream outputStream = null;
            try {
                outputStream = mContentResolver.openOutputStream(mSaveUri);
                if (outputStream != null) {
                    croppedImage.compress(mOutputFormat, 90, outputStream);
                }
            } catch (IOException ex) {
                Log.e(TAG, "Cannot open file: " + mSaveUri, ex);
                return null;
            } finally {
                Util.closeSilently(outputStream);
            }
        } else {
            Log.e(TAG, "not defined image url");
        }
        croppedImage.recycle();
        return mSaveUri.getPath();
    }

    Runnable mRunFaceDetection = new Runnable() {
        @SuppressWarnings("hiding")
        float mScale = 1F;
        Matrix mImageMatrix;
        FaceDetector.Face[] mFaces = new FaceDetector.Face[3];
        int mNumFaces;

        private void handleFace(FaceDetector.Face f) {
            PointF midPoint = new PointF();
            int r = ((int) (f.eyesDistance() * mScale)) * 2;
            f.getMidPoint(midPoint);
            midPoint.x *= mScale;
            midPoint.y *= mScale;
            int midX = (int) midPoint.x;
            int midY = (int) midPoint.y;
            HighlightView hv = new HighlightView(mImageView);
            int width = mBitmap.getWidth();
            int height = mBitmap.getHeight();
            Rect imageRect = new Rect(0, 0, width, height);
            RectF faceRect = new RectF(midX, midY, midX, midY);
            faceRect.inset(-r, -r);
            if (faceRect.left < 0) {
                faceRect.inset(-faceRect.left, -faceRect.left);
            }
            if (faceRect.top < 0) {
                faceRect.inset(-faceRect.top, -faceRect.top);
            }
            if (faceRect.right > imageRect.right) {
                faceRect.inset(faceRect.right - imageRect.right, faceRect.right - imageRect.right);
            }

            if (faceRect.bottom > imageRect.bottom) {
                faceRect.inset(faceRect.bottom - imageRect.bottom, faceRect.bottom - imageRect.bottom);
            }
            hv.setup(mImageMatrix, imageRect, faceRect, mCircleCrop, mAspectX != 0 && mAspectY != 0);
            mImageView.add(hv);
        }

        private void makeDefault() {
            HighlightView hv = new HighlightView(mImageView);
            int width = mBitmap.getWidth();
            int height = mBitmap.getHeight();
            Rect imageRect = new Rect(0, 0, width, height);
            int cropWidth = Math.min(width, height) * 4 / 5;
            int cropHeight = /*cropWidth * 4/5*/cropWidth;
            if (mAspectX != 0 && mAspectY != 0) {
                if (mAspectX > mAspectY) {
                    cropHeight = cropWidth * mAspectY / mAspectX;
                } else {
                    cropWidth = cropHeight * mAspectX / mAspectY;
                }
            }
            int x = (width - cropHeight) / 2;
            int y = (height - cropWidth) / 2;
            RectF cropRect = new RectF(x, y, x + cropHeight, y + cropWidth);
            hv.setup(mImageMatrix, imageRect, cropRect, mCircleCrop,/*mAspectX != 0 && mAspectY != 0*/true);
            mImageView.mHighlightViews.clear(); // Thong added for rotate
            mImageView.add(hv);
        }

        private Bitmap prepareBitmap() {
            if (mBitmap == null) {
                return null;
            }
            // 256 pixels wide is enough.
            if (mBitmap.getWidth() > 256) {
                mScale = 256.0F / mBitmap.getWidth();
            }
            Matrix matrix = new Matrix();
            matrix.setScale(mScale, mScale);
            return Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), matrix, true);
        }

        public void run() {
            mImageMatrix = mImageView.getImageMatrix();
            Bitmap faceBitmap = prepareBitmap();
            mScale = 1.0F / mScale;
            if (faceBitmap != null && mDoFaceDetection) {
                FaceDetector detector = new FaceDetector(faceBitmap.getWidth(), faceBitmap.getHeight(), mFaces.length);
                mNumFaces = detector.findFaces(faceBitmap, mFaces);
            }

            if (faceBitmap != null && faceBitmap != mBitmap) {
                faceBitmap.recycle();
            }

            mHandler.post(new Runnable() {
                public void run() {

                    mWaitingToPick = mNumFaces > 1;
                    if (mNumFaces > 0) {
                        for (int i = 0; i < mNumFaces; i++) {
                            handleFace(mFaces[i]);
                        }
                    } else {
                        makeDefault();
                    }
                    mImageView.invalidate();
                    if (mImageView.mHighlightViews.size() == 1) {
                        mCrop = mImageView.mHighlightViews.get(0);
                        mCrop.setFocus(true);
                    }
                    if (mNumFaces > 1) {
                        ToastUtils.makeText(mContext, "Multi face crop help");
                    }
                }
            });
        }
    };

    public static final int NO_STORAGE_ERROR = -1;
    public static final int CANNOT_STAT_ERROR = -2;

    public static void showStorageToast(Activity activity) {
        showStorageToast(activity, calculatePicturesRemaining(activity));
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

    /**
     * 旋转图片
     */
    public static Bitmap rotate(Bitmap original, final int angle) {
        if ((angle % 360) == 0) {
            return original;
        }
        final boolean dimensionsChanged = angle == 90 || angle == 270;
        final int oldWidth = original.getWidth();
        final int oldHeight = original.getHeight();
        final int newWidth = dimensionsChanged ? oldHeight : oldWidth;
        final int newHeight = dimensionsChanged ? oldWidth : oldHeight;

        Bitmap bitmap = Bitmap.createBitmap(newWidth, newHeight, original.getConfig());
        Canvas canvas = new Canvas(bitmap);

        Matrix matrix = new Matrix();
        matrix.preTranslate((newWidth - oldWidth) / 2f, (newHeight - oldHeight) / 2f);
        matrix.postRotate(angle, bitmap.getWidth() / 2f, bitmap.getHeight() / 2);
        canvas.drawBitmap(original, matrix, null);
        original.recycle();
        return bitmap;
    }

    /**
     * 获取图片角度
     */
    public static int getDegree(String filepath) {
        int degree = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filepath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (exif != null) {
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, -1);
            if (orientation != -1) {
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                }

            }
        }
        return degree;
    }
}
