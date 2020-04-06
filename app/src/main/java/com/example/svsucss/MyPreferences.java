package com.example.svsucss;

import android.content.Context;
import android.content.SharedPreferences;

public class MyPreferences {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;


    private static final String PREF_NAME = "svsucss";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String IS_NOT_LOGIN = "IsNotLogin";
    private static final String TYPE_OF_USER = "TypeOfUser";
    private static final String USER_ID = "UserId";

    private static final String USER_NAME = "UserName";





    public MyPreferences(Context context) {
        if(context!=null) {
            this._context = context;
            pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
            editor = pref.edit();
        }
    }

    public void setUserId(String userId)
    {
        editor.putString(USER_ID,userId);
        editor.commit();
    }

    public String getUserId()
    {
        return pref.getString(USER_ID,"default");
    }

    public void setUserName(String userName)
    {
        editor.putString(USER_NAME,userName);
        editor.commit();
    }

    public String getUserName()
    {
        return pref.getString(USER_NAME,"default");
    }

    public void saveString(String key, String value){
        editor.putString(key, value);
        editor.commit();
    }

    public String getString(String key){
        return pref.getString(key, null);
    }

    public  boolean isNotLogin() {
        return pref.getBoolean(IS_NOT_LOGIN, true);
    }

    public void setIsNotLogin(boolean isNotLogin) {
        editor.putBoolean(IS_NOT_LOGIN, isNotLogin);
        editor.commit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setTypeOfUser(String user)
    {
        editor.putString(TYPE_OF_USER, user);
        editor.commit();
    }

    public String getTypeOfUser()
    {
        return pref.getString(TYPE_OF_USER,"student");
    }

}
