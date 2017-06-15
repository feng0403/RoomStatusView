package com.feng.roomstatusview.util;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.ArrayRes;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.RawRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;

import com.feng.roomstatusview.MyApplication;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * 读取res目录下的内容的工具类
 */
public class ResourceUtils {

    public static final String LAYTOUT = "layout";
    public static final String DRAWABLE = "drawable";
    public static final String MIPMAP = "mipmap";
    public static final String MENU = "menu";
    public static final String RAW = "raw";
    public static final String ANIM = "anim";
    public static final String STRING = "string";
    public static final String STYLE = "style";
    public static final String STYLEABLE = "styleable";
    public static final String INTEGER = "integer";
    public static final String ID = "id";
    public static final String DIMEN = "dimen";
    public static final String COLOR = "color";
    public static final String BOOL = "bool";
    public static final String ATTR = "attr";

    /**
     * 根据资源名获得资源id
     *
     * @param name 资源名
     * @param type 资源类型
     * @return 资源id，找不到返回0
     */
    public static int getResourceId(String name, String type) {
        Resources resources;
        try {
            resources = MyApplication.getInstance().getResources();
            return resources.getIdentifier(name, type, MyApplication.getInstance().getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 从assets目录下读取文件内容
     *
     * @param fileName 文件名
     * @return 文件字节流
     */
    public static byte[] readBytesFromAssets(String fileName) {
        InputStream is = null;
        byte[] buffer = null;
        try {
            is = MyApplication.getInstance().getAssets().open(fileName);
            int size = is.available();
            buffer = new byte[size];
            is.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return buffer;
    }

    /**
     * 从res/raw目录下读取文件内容
     *
     * @param rawId rawId
     * @return 文件字节流
     */
    public static byte[] readBytesFromRaw(@RawRes int rawId) {
        InputStream is = null;
        byte[] buffer = null;
        try {
            is = MyApplication.getInstance().getResources().openRawResource(rawId);
            int size = is.available();
            buffer = new byte[size];
            is.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return buffer;
    }

    /**
     * 从assets目录读取文本
     *
     * @param fileName 文件名
     * @return 文本内容
     */
    public static String readStringFromAssets(String fileName) {
        String result = null;
        byte[] buffer = ResourceUtils.readBytesFromAssets(fileName);
        try {
            result = new String(buffer, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 从raw目录读取文本
     *
     * @param rawId id值
     * @return 文本内容
     */
    public static String readStringFromRaw(@RawRes int rawId) {
        String result = null;
        byte[] buffer = ResourceUtils.readBytesFromRaw(rawId);
        try {
            result = new String(buffer, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获得字符串
     *
     * @param strId 字符串id
     * @return 字符串
     */
    public static String getString(@StringRes int strId) {
        return MyApplication.getInstance().getResources().getString(strId);
    }

    /**
     * 获得格式化字符串
     *
     * @param strId 字符串id
     * @param args  格式化参数
     * @return 字符串
     */
    public static String getString(@StringRes int strId, Object... args) {
        return MyApplication.getInstance().getResources().getString(strId, args);
    }

    /**
     * 获得颜色
     *
     * @param colorId 颜色id
     * @return 颜色
     */
    public static int getColor(@ColorRes int colorId) {
        return ContextCompat.getColor(MyApplication.getInstance(), colorId);
    }

    /**
     * 获得ColorList
     *
     * @param selectorId 颜色id
     * @return 颜色
     */
    public static ColorStateList getColorStateList(@DrawableRes int selectorId) {
        return ContextCompat.getColorStateList(MyApplication.getInstance(), selectorId);
    }

    /**
     * 获得Drawable
     *
     * @param drawableId Drawable的id
     * @return Drawable
     */
    public static Drawable getDrawable(@DrawableRes int drawableId) {
        if (drawableId == 0) {
            return null;
        }
        return ContextCompat.getDrawable(MyApplication.getInstance(), drawableId);
    }

    /**
     * 获得Drawable
     *
     * @param drawableId Drawable的id
     * @return Drawable
     */
    public static Drawable getDrawable(@DrawableRes int drawableId, @ColorRes int colorRes) {
        if (drawableId == 0) {
            return null;
        }
        Drawable drawable = ContextCompat.getDrawable(MyApplication.getInstance(), drawableId);
        drawable.setColorFilter(getColor(colorRes), PorterDuff.Mode.SRC_IN);
        return drawable;
    }

    /**
     * 获取Dimen对应的pixel
     */
    public static int getDimen(@DimenRes int dimenId) {
        return MyApplication.getInstance().getResources().getDimensionPixelSize(dimenId);
    }

    /**
     * 获取String数组
     */
    public static String[] getStringArray(@ArrayRes int arrayId) {
        return MyApplication.getInstance().getResources().getStringArray(arrayId);
    }
}