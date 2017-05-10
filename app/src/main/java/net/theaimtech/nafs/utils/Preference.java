package net.theaimtech.nafs.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Base64InputStream;
import android.util.Base64OutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Aimanzaki Kagzi on 12/11/2015.
 */
public class Preference {

    private static final String FILE_NAME = "localdata";

    private static Preference mInstance = null;

    public static Preference getInstance() {
        if (null == mInstance) {
            mInstance = new Preference();
        }
        return mInstance;
    }
    public static String objectToString(Serializable obj) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(
                    new Base64OutputStream(baos, Base64.NO_PADDING
                            | Base64.NO_WRAP));
            oos.writeObject(obj);
            oos.close();
            return baos.toString("UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Object stringToObject(String str) {
        try {
            return new ObjectInputStream(new Base64InputStream(
                    new ByteArrayInputStream(str.getBytes()), Base64.NO_PADDING
                    | Base64.NO_WRAP)).readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void put(Context context, String key, String value) {


        SharedPreferences pref = context.getSharedPreferences(FILE_NAME, MODE_PRIVATE);

        SharedPreferences.Editor editor = pref.edit();

        editor.putString(key, value);
        editor.commit();
    }

    public void put(Context context, String key, boolean value) {

        SharedPreferences pref = context.getSharedPreferences(FILE_NAME, MODE_PRIVATE);

        SharedPreferences.Editor editor = pref.edit();

        editor.putBoolean(key, value);
        editor.commit();
    }

    public void clear(Context context) {
        SharedPreferences pref = context.getSharedPreferences(FILE_NAME, MODE_PRIVATE);

        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }

    public void put(Context context, String key, int value) {

        SharedPreferences pref = context.getSharedPreferences(FILE_NAME, MODE_PRIVATE);

        SharedPreferences.Editor editor = pref.edit();

        editor.putInt(key, value);
        editor.commit();
    }

    public void remove(Context context, String key) {
        SharedPreferences pref = context.getSharedPreferences(FILE_NAME, MODE_PRIVATE);

        SharedPreferences.Editor editor = pref.edit();
        editor.remove(key);
        editor.commit();

    }

    public void put(Context context, String key, long value) {

        SharedPreferences pref = context.getSharedPreferences(FILE_NAME, MODE_PRIVATE);

        SharedPreferences.Editor editor = pref.edit();

        editor.putLong(key, value);
        editor.commit();
    }

    public String getValue(Context context, String key, String defaultValue) {

        SharedPreferences pref = context.getSharedPreferences(FILE_NAME, MODE_PRIVATE);

        try {
            return pref.getString(key, defaultValue);
        } catch (Exception e) {

            return defaultValue;
        }
    }

    public int getValue(Context context, String key, int defaultValue) {

        SharedPreferences pref = context.getSharedPreferences(FILE_NAME, MODE_PRIVATE);

        try {
            return pref.getInt(key, defaultValue);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public boolean getValue(Context context, String key, boolean defaultValue) {

        SharedPreferences pref = context.getSharedPreferences(FILE_NAME, MODE_PRIVATE);

        try {
            return pref.getBoolean(key, defaultValue);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public long getValue(Context context, String key, long defaultValue) {

        SharedPreferences pref = context.getSharedPreferences(FILE_NAME, MODE_PRIVATE);

        try {
            return pref.getLong(key, defaultValue);
        } catch (Exception e) {
            return defaultValue;
        }
    }
}
