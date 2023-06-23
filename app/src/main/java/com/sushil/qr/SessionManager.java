package com.sushil.qr;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.sushil.qr.models.SessionModel;

public class SessionManager {

    public static final String KEY_USER_DETAILS = "user_details";
    private static final String PREF_NAME = "userData";
    private static final String IS_LOGIN = "isLogin";
    private static final String KEY_FIRST_TIME = "first_time";
    private static  final String KEY_AUTH_DETAILS= "auth_details";

    public static final String AUTH_KEY = "CHC1YIaTBiLMzqYb4SPGbgZyjIMtc92jO8La3PKVFIo=";
    private static SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;


    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

 /*  public void setUserDetails(ProfileModel profileModel){
       editor.putString(KEY_USER_DETAILS, new Gson().toJson(profileModel));
       editor.commit();
   }*/


    public void createLoginSession(SessionModel sessionModel) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putBoolean(KEY_FIRST_TIME, true);
        editor.putString(KEY_AUTH_DETAILS, new Gson().toJson(sessionModel));
        editor.commit();
    }
    public SessionModel getLoginSession() {
        return new Gson().fromJson(pref.getString(KEY_AUTH_DETAILS, ""), SessionModel.class);
    }


    public void clearSession() {
        editor.clear();
        editor.commit();

    }
    public void logoutUser() {
        clearSession();
        Intent i = new Intent(_context, LogininActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(i);

    }

   /* public ProfileModel getUser() {
        return new Gson().fromJson(pref.getString(KEY_USER_DETAILS, null), ProfileModel.class);
    }
*/
    public boolean isLoggedIn() {

        return pref.getBoolean(IS_LOGIN, false);
    }
    public Boolean getFirstTime() {
        return pref.getBoolean(KEY_FIRST_TIME, false);
    }

    public void setFirstTime(Boolean firstTime) {
        editor.putBoolean(KEY_FIRST_TIME, firstTime);
        editor.commit();
    }

    public boolean getFirstTimeLaunch() {
        return pref.getBoolean(KEY_FIRST_TIME, true);
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(KEY_FIRST_TIME, isFirstTime);
        editor.commit();
    }

}









