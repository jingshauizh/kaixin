package com.kaixin001.model;

import android.text.TextUtils;
import com.kaixin001.util.GetSystemPropertyUtil;

public class Setting extends KXModel
{
  public static final String APP_ADVERT_ID = "1120";
  public static final String APP_ALBUM = "6";
  public static final String APP_CIRCLE_ME_REPLY = "我回复";
  public static final String APP_CIRCLE_REPLY_ME = "回复我";
  public static final String APP_COMPARE = "1194";
  public static final String APP_DIARY_ID = "2";
  public static final String APP_FILM = "1020";
  public static final String APP_FRIEND_ID = "0";
  public static final String APP_FRIEND_NEWS_ID = "10";
  public static final String APP_HOROSCOPE_ID = "1090";
  public static final String APP_HOT_NEWS_ID = "11";
  public static final String APP_LOCATION_ID = "1192";
  public static final String APP_LOGO_ID = "4";
  public static final String APP_NETWORKDISK_ID = "1008";
  public static final String APP_PHOTO_DIARY = "1263";
  public static final String APP_PHOTO_ID = "1";
  public static final String APP_PUSH_CHECK_IN = "100008";
  public static final String APP_PUSH_FRIEND_PIC = "100002";
  public static final String APP_PUSH_GAMES = "100006";
  public static final String APP_PUSH_GIFT = "100007";
  public static final String APP_PUSH_HOME = "100005";
  public static final String APP_PUSH_HOT_REPOST = "100001";
  public static final String APP_PUSH_NEWS = "100004";
  public static final String APP_PUSH_TOPIC = "100009";
  public static final String APP_PUSH_WRITE_WEIBO = "100003";
  public static final String APP_READBOOK_ID = "1058";
  public static final String APP_RECORD = "1017";
  public static final String APP_RECORD_AUDIO = "audio";
  public static final String APP_RECORD_ID = "1018";
  public static final String APP_REPASTE_ID = "1088";
  public static final String APP_REPOST_3ITEMS = "1242";
  public static final String APP_REPOST_ALBUM = "转发照片专辑";
  public static final String APP_REPOST_PHOTO = "转发照片";
  public static final String APP_REPOST_VOTE = "转发投票";
  public static final String APP_SEARCH_FRIEND = "5001";
  public static final String APP_SEND_GIFT = "1002";
  public static final String APP_STATE_ID = "3";
  public static final String APP_STYLE_BOX_DIARY_ID = "1210";
  public static final String APP_TOUCH_THEM = "1000";
  public static final String APP_TRUTH_ID = "1072";
  public static final String APP_VOTE_ID = "1016";
  public static final String AppID = "100015822";
  public static final int FLAG_COMMENT_INDEX = 2;
  public static final int FLAG_RECEIVED_USER_COMMENT = 4;
  public static final int FLAG_REPLY_COMMENT_INDEX = 3;
  public static final int FLAG_SENT_USER_COMMENT = 5;
  public static final int FLAG_SMS_INBOX_INDEX = 0;
  public static final int FLAG_SMS_OUTBOX_INDEX = 1;
  public static final String PIC_PRIVACY_ANY = "0";
  public static final String PIC_PRIVACY_DELETE = "4";
  public static final String PIC_PRIVACY_FRIEND = "1";
  public static final String PIC_PRIVACY_PWD = "2";
  private static final String SYSTEM_PROPERTIES = "android.os.SystemProperties";
  private static volatile Setting instance;
  public static final boolean isForLenovo;
  public static final boolean isForUc;
  public static final boolean isForYyh;
  public boolean calledByUnicom;
  private boolean mDebug = false;
  private String mDeviceName = "";
  private boolean mGpsEnabled;
  private String mManufacturerName = "";
  private boolean[] mMessageRefreshFlag = new boolean[6];
  private boolean mMonkeyTestVersion = false;
  private boolean mNeedActive;
  private boolean mNeedDisclaimer;
  private String mPhotoAlbumName = null;
  private boolean mShowFaceIconInLandScape;
  private boolean mShowLoginNotification;
  private boolean mShowVersion;
  private String mStrCType;
  private String mStrFrom;
  private String mStrHostUrl;
  String mStrNewHostUrl;
  private boolean mWidgetEnabled;
  private int mnSupportFlash = -1;

  private Setting()
  {
    loadSetting();
  }

  private static void clearInstance()
  {
    instance = null;
  }

  public static Setting getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new Setting();
      Setting localSetting = instance;
      return localSetting;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private int loadSetting()
  {
    this.mStrCType = "03403AndroidClient";
    this.mStrFrom = "kx";
    this.mDebug = true;
    this.mStrHostUrl = "http://api.kaixin001.com";
    this.mStrNewHostUrl = "http://ksa2.kaixin001.com";
    try
    {
      Class localClass2 = GetSystemPropertyUtil.creatClassObject("android.os.SystemProperties");
      localClass1 = localClass2;
      if (localClass1 != null)
      {
        this.mDeviceName = GetSystemPropertyUtil.getSystemProperties(localClass1, "ro.product.model");
        this.mManufacturerName = GetSystemPropertyUtil.getSystemProperties(localClass1, "ro.product.manufacturer");
      }
      this.mShowVersion = false;
      this.mMonkeyTestVersion = false;
      this.mWidgetEnabled = true;
      this.mNeedActive = true;
      this.mGpsEnabled = true;
      this.mNeedDisclaimer = false;
      this.mShowLoginNotification = false;
      this.mShowFaceIconInLandScape = false;
      return 0;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      while (true)
      {
        localClassNotFoundException.printStackTrace();
        Class localClass1 = null;
      }
    }
  }

  public void clear()
  {
    clearInstance();
  }

  public int getAppIcon(String paramString1, String paramString2)
  {
    if (TextUtils.isEmpty(paramString1));
    do
    {
      while (true)
      {
        return 2130838667;
        if ("1002".equals(paramString1))
          return 2130839101;
        if ("2".equals(paramString1))
          return 2130839099;
        if ("3".equals(paramString1))
          return 2130839103;
        if ("1".equals(paramString1))
          return 2130839104;
        if ("1018".equals(paramString1))
          return 2130839105;
        if ("1088".equals(paramString1))
          return 2130839106;
        if ("1016".equals(paramString1))
          return 2130838702;
        if ("1072".equals(paramString1))
          return 2130838694;
        if ("1210".equals(paramString1))
          return 2130839098;
        if (!"1242".equals(paramString1))
          break;
        if ("转发照片专辑".equals(paramString2))
          return 2130839104;
        if ("转发照片".equals(paramString2))
          return 2130839104;
        if ("转发投票".equals(paramString2))
          return 2130838702;
      }
      if ("1192".equals(paramString1))
        return 2130839102;
    }
    while (!"0".equals(paramString1));
    return 2130839100;
  }

  public String getCType()
  {
    return this.mStrCType;
  }

  public String getDeviceName()
  {
    return this.mDeviceName;
  }

  public String getFrom()
  {
    return this.mStrFrom;
  }

  public String getHost()
  {
    return this.mStrHostUrl;
  }

  public String getManufacturerName()
  {
    return this.mManufacturerName;
  }

  public String getNewHost()
  {
    return this.mStrNewHostUrl;
  }

  public String getPhotoAlbumName()
  {
    return this.mPhotoAlbumName;
  }

  public boolean gpsEnabled()
  {
    return this.mGpsEnabled;
  }

  public boolean isDebug()
  {
    return this.mDebug;
  }

  public boolean isNeedRefresh(int paramInt)
  {
    return this.mMessageRefreshFlag[paramInt];
  }

  public boolean isShowFaceIconInLandScape()
  {
    return this.mShowFaceIconInLandScape;
  }

  public boolean isTestVersion()
  {
    return this.mMonkeyTestVersion;
  }

  public boolean needActive()
  {
    return this.mNeedActive;
  }

  public boolean needDisclaimer()
  {
    return this.mNeedDisclaimer;
  }

  public void setHost(String paramString)
  {
    this.mStrHostUrl = paramString;
  }

  public void setNeedRefresh(int paramInt, boolean paramBoolean)
  {
    this.mMessageRefreshFlag[paramInt] = paramBoolean;
  }

  public void setNeedRefreshFlag(boolean paramBoolean)
  {
    for (int i = 0; ; i++)
    {
      if (i >= this.mMessageRefreshFlag.length)
        return;
      this.mMessageRefreshFlag[i] = paramBoolean;
    }
  }

  public void setPhotoAlbumName(String paramString)
  {
    this.mPhotoAlbumName = paramString;
  }

  public boolean showLoginNotification()
  {
    return this.mShowLoginNotification;
  }

  public boolean showVersion()
  {
    return this.mShowVersion;
  }

  public boolean widgetEnabled()
  {
    return this.mWidgetEnabled;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.Setting
 * JD-Core Version:    0.6.0
 */