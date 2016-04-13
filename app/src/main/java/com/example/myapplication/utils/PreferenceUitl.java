package com.example.myapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @ClassName: PreferenceUitl
 * @Description: PreferenceUitl:作为SharedPreferences的存储管理
 * @date 2014年1月11日 下午2:25:51
 */
public class PreferenceUitl {
    private static PreferenceUitl preferenceUitl;
    private SharedPreferences.Editor ed;
    private SharedPreferences sp;

    private PreferenceUitl(Context paramContext) {
        init(paramContext);
    }

    public static PreferenceUitl getInstance(Context paramContext) {
        if (preferenceUitl == null) {
            preferenceUitl = new PreferenceUitl(paramContext);
        }
        return preferenceUitl;
    }

    public void destroy() {
        this.sp = null;
        this.ed = null;
        preferenceUitl = null;
    }

    public Float getFloat(String paramString, Float paramFloat) {
        return this.sp.getFloat(paramString, paramFloat);
    }

    public boolean getBoolean(String paramString, boolean paramBoolean) {
        return this.sp.getBoolean(paramString, paramBoolean);
    }

    public int getInt(String paramString, int paramInt) {
        return this.sp.getInt(paramString, paramInt);
    }

    public long getLong(String paramString, long paramLong) {
        return this.sp.getLong(paramString, paramLong);
    }

    public String getString(String paramString1, String paramString2) {
        return this.sp.getString(paramString1, paramString2);
    }

    public void init(Context paramContext) {
        if ((this.sp != null) && (this.ed != null)) {
            return;
        }
        try {
            this.sp = paramContext.getSharedPreferences("preference_apkol",
                    paramContext.MODE_PRIVATE);
            this.ed = this.sp.edit();
            return;
        } catch (Exception localException) {
        }
    }

    public boolean remove(String paramString) {
        this.ed.remove(paramString);
        return this.ed.commit();
    }

    public boolean saveBoolean(String paramString, boolean paramBoolean) {
        this.ed.putBoolean(paramString, paramBoolean);
        return this.ed.commit();
    }

    public boolean saveInt(String paramString, int paramInt) {
        if (this.ed == null) {
            return false;
        }
        ed.putInt(paramString, paramInt);
        return this.ed.commit();
    }

    public boolean saveLong(String paramString, long paramLong) {
        this.ed.putLong(paramString, paramLong);
        return this.ed.commit();
    }

    public boolean saveString(String paramString1, String paramString2) {
        this.ed.putString(paramString1, paramString2);
        return this.ed.commit();
    }

    public boolean saveFloat(String paramString1, Float paramString2) {
        this.ed.putFloat(paramString1, paramString2);
        return this.ed.commit();
    }
}