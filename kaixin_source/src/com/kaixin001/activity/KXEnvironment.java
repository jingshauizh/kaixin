package com.kaixin001.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import com.kaixin001.model.User;
import com.kaixin001.util.ConnectivityUtil;

public class KXEnvironment
{
  public static float DENSITY = 0.0F;
  public static final String GUIDE_ADD_FRIEND = "guide_add_friend";
  public static final String GUIDE_CHANGE_LOGO = "guide_change_logo";
  public static final String GUIDE_PLAZA = "guide_plaza";
  public static final String GUIDE_RECORD = "guide_record";
  public static final String GUIDE_UGC = "guide_ugc";
  public static final String TAG_NEED_COMPLETE_INFO = "needCompleteInfo";
  public static boolean mGuideAddFriend;
  public static boolean mGuideChangeLogo;
  public static boolean mGuidePlaza;
  public static boolean mGuideRecord;
  public static boolean mGuideUgc;
  private static boolean mMobileEnable;
  public static boolean mNeedCompleteInfo;
  private static boolean mSaveFlowOpen;
  private static boolean mWifiEnable = false;

  static
  {
    mMobileEnable = false;
    mSaveFlowOpen = false;
    DENSITY = 1.0F;
    mNeedCompleteInfo = false;
    mGuideUgc = true;
    mGuideRecord = true;
    mGuidePlaza = true;
    mGuideChangeLogo = true;
    mGuideAddFriend = true;
  }

  public static void init(Context paramContext)
  {
    wifiStateChanged(paramContext);
    mSaveFlowOpen = loadBooleanParams(paramContext, "setting_saveflow_pref", false, true);
    DENSITY = paramContext.getResources().getDisplayMetrics().density;
  }

  public static boolean loadBooleanParams(Context paramContext, String paramString, boolean paramBoolean1, boolean paramBoolean2)
  {
    SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(paramContext);
    if (paramBoolean1)
      paramString = paramString + User.getInstance().getUID();
    return localSharedPreferences.getBoolean(paramString, paramBoolean2);
  }

  public static String loadStrParams(Context paramContext, String paramString1, boolean paramBoolean, String paramString2)
  {
    SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(paramContext);
    if (paramBoolean)
      paramString1 = paramString1 + User.getInstance().getUID();
    return localSharedPreferences.getString(paramString1, paramString2);
  }

  public static void saveBooleanParams(Context paramContext, String paramString, boolean paramBoolean1, boolean paramBoolean2)
  {
    SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(paramContext);
    if (paramBoolean1)
      paramString = paramString + User.getInstance().getUID();
    SharedPreferences.Editor localEditor = localSharedPreferences.edit();
    localEditor.putBoolean(paramString, paramBoolean2);
    localEditor.commit();
  }

  public static void saveFlowChanged(Context paramContext, boolean paramBoolean)
  {
    mSaveFlowOpen = paramBoolean;
  }

  public static boolean saveFlowOpen()
  {
    if (!mMobileEnable)
      return false;
    return mSaveFlowOpen;
  }

  public static void saveStrParams(Context paramContext, String paramString1, boolean paramBoolean, String paramString2)
  {
    SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(paramContext);
    if (paramBoolean)
      paramString1 = paramString1 + User.getInstance().getUID();
    SharedPreferences.Editor localEditor = localSharedPreferences.edit();
    localEditor.putString(paramString1, paramString2);
    localEditor.commit();
  }

  public static boolean wifiEnabled()
  {
    return mWifiEnable;
  }

  public static void wifiStateChanged(Context paramContext)
  {
    mWifiEnable = ConnectivityUtil.isWifiConnected(paramContext);
    mMobileEnable = ConnectivityUtil.isMobileConnected(paramContext);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.activity.KXEnvironment
 * JD-Core Version:    0.6.0
 */