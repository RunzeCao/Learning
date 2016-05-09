package com.example.myapplication.displayBitmap;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.FileDescriptor;
import java.lang.ref.WeakReference;

/**
 * Created by 123 on 2016/5/5.
 * 图片缩放
 */
public class ImageResizer extends ImageWorker {

    private static final String TAG = "ImageResizer";
    protected int mImageWidth;
    protected int mImageHeight;

    public ImageResizer(Context context, int imageWidth, int imageHeight) {
        setImageSize(imageWidth, imageHeight);
    }

    private void setImageSize(int imageWidth, int imageHeight) {
        mImageWidth = imageWidth;
        mImageHeight = imageHeight;
    }

    /**
     * 确定缩放的比例
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int width = options.outWidth;
        final int height = options.outHeight;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfWidth = width / 2;
            final int halfHeight = height / 2;
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
            //长宽差距比较大。
            long totalPixels = width * height / 2;
            long totalRequestPixels = reqWidth * reqHeight * 2;
            while (totalPixels > totalRequestPixels) {
                inSampleSize *= 2;
                totalPixels /= 2;
            }
        }
        return inSampleSize;
    }

    //从源文件解析
    public static Bitmap decodeSampledBitmapFromResource(Resources resources, int resId, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        //解码的时候避免内存的分配，它会返回一个null的Bitmap，但是可以获取到 outWidth, outHeight 与 outMimeType,防止图片过大导致内存溢出
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, resId, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(resources, resId, options);

    }

    //从文件解析
    public static Bitmap decodeSampledBitmapFromFile(String filename, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        //解码的时候避免内存的分配，它会返回一个null的Bitmap，但是可以获取到 outWidth, outHeight 与 outMimeType,防止图片过大导致内存溢出
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filename, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filename, options);

    }

    public static Bitmap decodeSampledBitmapFromDescriptor(FileDescriptor descriptor, int reqWitch, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(descriptor, null, options);

        options.inSampleSize = calculateInSampleSize(options, reqWitch, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFileDescriptor(descriptor, null, options);

    }

    //非ui线程处理Bitmap
    class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {
        private final WeakReference imageViewRefeeence;
        private int data = 0;

        public BitmapWorkerTask(ImageView imageView) {
            //ImageView使用WeakReference确保了AsyncTask所引用的资源可以被垃圾回收器回收。
            imageViewRefeeence = new WeakReference(imageView);
        }

        @Override
        protected Bitmap doInBackground(Integer... params) {
            data = params[0];
            return decodeSampledBitmapFromResource(Resources.getSystem(), data, 0, 0);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
        }
    }

    public void loadBitmap(int resId, ImageView imageView) {
        BitmapWorkerTask bitmapWorkerTask = new BitmapWorkerTask(imageView);
        bitmapWorkerTask.execute(resId);
    }

}
