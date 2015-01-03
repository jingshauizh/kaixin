package com.kaixin001.model;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import com.kaixin001.activity.KXApplication;
import com.kaixin001.util.KXLog;

public class User extends KXModel
{
  private static final String TAG = "User";
  private static volatile User instance;
  private static final long serialVersionUID = 1L;
  public int fnum = -1;
  public int imcomplete = -1;
  private int isLookAround = -1;
  private String mCoverId = "";
  private String mCoverPath = "";
  private String mCoverUrl = "";
  private long mExpiresIn = -1L;
  private boolean mNeedRefresh = false;
  private String mOnline = "0";
  private String mState = "";
  private UserLevel mUserLevel;
  private String strAccessToken = "";
  private String strAccount = "";
  private String strLogo = "";
  private String strLogo120 = "";
  private String strName = "";
  private String strOAuthTokenSecret = "";
  private String strPassword = "";
  private String strRefreshToken = "";
  private String strReserved = "";
  private String strUID = "";
  private String strWapVerify = "";

  private static void clearInstance()
  {
    instance = null;
  }

  public static User getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new User();
      User localUser = instance;
      return localUser;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public boolean GetLookAround()
  {
    return this.isLookAround == 1;
  }

  public void clear()
  {
    clearInstance();
  }

  public void clearAccout()
  {
    this.strAccessToken = "";
    logout();
  }

  public void clearLookAroundFlag()
  {
    this.isLookAround = 0;
  }

  public String getAccount()
  {
    return this.strAccount;
  }

  public String getCoverId()
  {
    return this.mCoverId;
  }

  public String getCoverPath()
  {
    return Environment.getExternalStorageDirectory() + "/kaixin001" + "/" + "kx_home_bg_temp.jpg";
  }

  public String getCoverUrl()
  {
    return this.mCoverUrl;
  }

  public long getExpiresIn()
  {
    return this.mExpiresIn;
  }

  public String getLogo()
  {
    if (GetLookAround())
      return null;
    return this.strLogo;
  }

  public String getLogo120()
  {
    if (GetLookAround())
      return null;
    return this.strLogo120;
  }

  public String getOauthToken()
  {
    return this.strAccessToken;
  }

  public String getOauthTokenSecret()
  {
    return this.strOAuthTokenSecret;
  }

  public String getOnline()
  {
    return this.mOnline;
  }

  public String getRealName()
  {
    if (GetLookAround())
      return "未登录";
    return this.strName;
  }

  public String getRefreshToken()
  {
    return this.strRefreshToken;
  }

  public String getReserved()
  {
    return this.strReserved;
  }

  public String getState()
  {
    return this.mState;
  }

  public String getUID()
  {
    return this.strUID;
  }

  public String getWapVerify()
  {
    return this.strWapVerify;
  }

  public UserLevel getmUserLevel()
  {
    return this.mUserLevel;
  }

  public boolean isNeedRefresh()
  {
    return this.mNeedRefresh;
  }

  public void loadDataIfEmpty(Context paramContext)
  {
    if (TextUtils.isEmpty(this.strAccessToken))
      loadUserData(paramContext);
  }

  // ERROR //
  public boolean loadUserData(Context paramContext)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: aconst_null
    //   3: astore_3
    //   4: new 175	com/kaixin001/db/UserDBAdapter
    //   7: dup
    //   8: aload_1
    //   9: invokespecial 177	com/kaixin001/db/UserDBAdapter:<init>	(Landroid/content/Context;)V
    //   12: astore 4
    //   14: aload 4
    //   16: invokevirtual 181	com/kaixin001/db/UserDBAdapter:getLoginedUser	()Landroid/database/Cursor;
    //   19: astore_3
    //   20: aload_3
    //   21: ifnull +229 -> 250
    //   24: aload_3
    //   25: invokeinterface 187 1 0
    //   30: ifle +220 -> 250
    //   33: aload_0
    //   34: aload_3
    //   35: aload_3
    //   36: ldc 189
    //   38: invokeinterface 193 2 0
    //   43: invokeinterface 197 2 0
    //   48: putfield 49	com/kaixin001/model/User:strPassword	Ljava/lang/String;
    //   51: aload_0
    //   52: aload_3
    //   53: aload_3
    //   54: ldc 199
    //   56: invokeinterface 193 2 0
    //   61: invokeinterface 197 2 0
    //   66: putfield 47	com/kaixin001/model/User:strAccount	Ljava/lang/String;
    //   69: aload_0
    //   70: aload_3
    //   71: aload_3
    //   72: ldc 201
    //   74: invokeinterface 193 2 0
    //   79: invokeinterface 197 2 0
    //   84: putfield 51	com/kaixin001/model/User:strUID	Ljava/lang/String;
    //   87: aload_0
    //   88: aload_3
    //   89: aload_3
    //   90: ldc 203
    //   92: invokeinterface 193 2 0
    //   97: invokeinterface 197 2 0
    //   102: putfield 53	com/kaixin001/model/User:strName	Ljava/lang/String;
    //   105: aload_0
    //   106: aload_3
    //   107: aload_3
    //   108: ldc 205
    //   110: invokeinterface 193 2 0
    //   115: invokeinterface 197 2 0
    //   120: putfield 85	com/kaixin001/model/User:mState	Ljava/lang/String;
    //   123: aload_0
    //   124: aload_3
    //   125: aload_3
    //   126: ldc 207
    //   128: invokeinterface 193 2 0
    //   133: invokeinterface 197 2 0
    //   138: putfield 81	com/kaixin001/model/User:mCoverUrl	Ljava/lang/String;
    //   141: aload_0
    //   142: aload_3
    //   143: aload_3
    //   144: ldc 209
    //   146: invokeinterface 193 2 0
    //   151: invokeinterface 197 2 0
    //   156: putfield 55	com/kaixin001/model/User:strLogo	Ljava/lang/String;
    //   159: aload_0
    //   160: aload_3
    //   161: aload_3
    //   162: ldc 211
    //   164: invokeinterface 193 2 0
    //   169: invokeinterface 197 2 0
    //   174: putfield 59	com/kaixin001/model/User:strAccessToken	Ljava/lang/String;
    //   177: aload_0
    //   178: aload_3
    //   179: aload_3
    //   180: ldc 213
    //   182: invokeinterface 193 2 0
    //   187: invokeinterface 197 2 0
    //   192: putfield 61	com/kaixin001/model/User:strRefreshToken	Ljava/lang/String;
    //   195: aload_0
    //   196: aload_3
    //   197: aload_3
    //   198: ldc 215
    //   200: invokeinterface 193 2 0
    //   205: invokeinterface 197 2 0
    //   210: putfield 63	com/kaixin001/model/User:strOAuthTokenSecret	Ljava/lang/String;
    //   213: aload_0
    //   214: aload_3
    //   215: aload_3
    //   216: ldc 217
    //   218: invokeinterface 193 2 0
    //   223: invokeinterface 221 2 0
    //   228: i2l
    //   229: putfield 69	com/kaixin001/model/User:mExpiresIn	J
    //   232: aload_0
    //   233: aload_3
    //   234: aload_3
    //   235: ldc 223
    //   237: invokeinterface 193 2 0
    //   242: invokeinterface 197 2 0
    //   247: putfield 71	com/kaixin001/model/User:strReserved	Ljava/lang/String;
    //   250: aload_0
    //   251: getfield 49	com/kaixin001/model/User:strPassword	Ljava/lang/String;
    //   254: invokestatic 167	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   257: ifne +15 -> 272
    //   260: aload_0
    //   261: ldc 45
    //   263: putfield 49	com/kaixin001/model/User:strPassword	Ljava/lang/String;
    //   266: aload_0
    //   267: aload_1
    //   268: invokevirtual 227	com/kaixin001/model/User:saveUserLoginInfo	(Landroid/content/Context;)J
    //   271: pop2
    //   272: aload_3
    //   273: ifnull +9 -> 282
    //   276: aload_3
    //   277: invokeinterface 230 1 0
    //   282: aload 4
    //   284: ifnull +8 -> 292
    //   287: aload 4
    //   289: invokevirtual 231	com/kaixin001/db/UserDBAdapter:close	()V
    //   292: iconst_1
    //   293: ireturn
    //   294: astore 5
    //   296: ldc 8
    //   298: ldc 233
    //   300: aload 5
    //   302: invokestatic 239	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   305: aload_3
    //   306: ifnull +9 -> 315
    //   309: aload_3
    //   310: invokeinterface 230 1 0
    //   315: aload_2
    //   316: ifnull +7 -> 323
    //   319: aload_2
    //   320: invokevirtual 231	com/kaixin001/db/UserDBAdapter:close	()V
    //   323: iconst_0
    //   324: ireturn
    //   325: astore 6
    //   327: aload_3
    //   328: ifnull +9 -> 337
    //   331: aload_3
    //   332: invokeinterface 230 1 0
    //   337: aload_2
    //   338: ifnull +7 -> 345
    //   341: aload_2
    //   342: invokevirtual 231	com/kaixin001/db/UserDBAdapter:close	()V
    //   345: aload 6
    //   347: athrow
    //   348: astore 6
    //   350: aload 4
    //   352: astore_2
    //   353: goto -26 -> 327
    //   356: astore 5
    //   358: aload 4
    //   360: astore_2
    //   361: goto -65 -> 296
    //
    // Exception table:
    //   from	to	target	type
    //   4	14	294	java/lang/Exception
    //   4	14	325	finally
    //   296	305	325	finally
    //   14	20	348	finally
    //   24	250	348	finally
    //   250	272	348	finally
    //   14	20	356	java/lang/Exception
    //   24	250	356	java/lang/Exception
    //   250	272	356	java/lang/Exception
  }

  public void logout()
  {
    this.strWapVerify = "";
    if (this.isLookAround != 1)
      saveUserLoginInfo(KXApplication.getInstance());
    logoutClear();
    com.kaixin001.fragment.NewsFragment.mRefresh = true;
    com.kaixin001.activity.MainActivity.isRefresh = true;
  }

  public void logoutClear()
  {
    this.strUID = "";
    this.strName = "";
    this.mCoverUrl = "";
    this.mCoverId = "";
    this.mState = "";
    this.strLogo = "";
    this.strAccessToken = "";
    this.strRefreshToken = "";
    this.strOAuthTokenSecret = "";
    this.mExpiresIn = -1L;
    this.strReserved = "";
  }

  // ERROR //
  public long saveConfigData(Context paramContext, String paramString1, String paramString2)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 4
    //   3: new 261	com/kaixin001/db/ConfigDBAdapter
    //   6: dup
    //   7: aload_1
    //   8: invokespecial 262	com/kaixin001/db/ConfigDBAdapter:<init>	(Landroid/content/Context;)V
    //   11: astore 5
    //   13: aload 5
    //   15: aload_0
    //   16: getfield 51	com/kaixin001/model/User:strUID	Ljava/lang/String;
    //   19: aload_3
    //   20: aload_2
    //   21: ldc 45
    //   23: invokevirtual 266	com/kaixin001/db/ConfigDBAdapter:addConfig	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)J
    //   26: lstore 8
    //   28: aload 5
    //   30: ifnull +8 -> 38
    //   33: aload 5
    //   35: invokevirtual 267	com/kaixin001/db/ConfigDBAdapter:close	()V
    //   38: lload 8
    //   40: lreturn
    //   41: astore 6
    //   43: ldc 8
    //   45: ldc_w 268
    //   48: aload 6
    //   50: invokestatic 239	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   53: aload 4
    //   55: ifnull +8 -> 63
    //   58: aload 4
    //   60: invokevirtual 267	com/kaixin001/db/ConfigDBAdapter:close	()V
    //   63: ldc2_w 66
    //   66: lreturn
    //   67: astore 7
    //   69: aload 4
    //   71: ifnull +8 -> 79
    //   74: aload 4
    //   76: invokevirtual 267	com/kaixin001/db/ConfigDBAdapter:close	()V
    //   79: aload 7
    //   81: athrow
    //   82: astore 7
    //   84: aload 5
    //   86: astore 4
    //   88: goto -19 -> 69
    //   91: astore 6
    //   93: aload 5
    //   95: astore 4
    //   97: goto -54 -> 43
    //
    // Exception table:
    //   from	to	target	type
    //   3	13	41	java/lang/Exception
    //   3	13	67	finally
    //   43	53	67	finally
    //   13	28	82	finally
    //   13	28	91	java/lang/Exception
  }

  // ERROR //
  public long saveUserInfo(Context paramContext)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 77	com/kaixin001/model/User:isLookAround	I
    //   4: iconst_1
    //   5: if_icmpne +11 -> 16
    //   8: ldc2_w 66
    //   11: lstore 7
    //   13: lload 7
    //   15: lreturn
    //   16: new 175	com/kaixin001/db/UserDBAdapter
    //   19: dup
    //   20: aload_1
    //   21: invokespecial 177	com/kaixin001/db/UserDBAdapter:<init>	(Landroid/content/Context;)V
    //   24: astore_2
    //   25: aload_2
    //   26: aload_0
    //   27: getfield 51	com/kaixin001/model/User:strUID	Ljava/lang/String;
    //   30: aload_0
    //   31: getfield 53	com/kaixin001/model/User:strName	Ljava/lang/String;
    //   34: aload_0
    //   35: getfield 85	com/kaixin001/model/User:mState	Ljava/lang/String;
    //   38: aload_0
    //   39: getfield 55	com/kaixin001/model/User:strLogo	Ljava/lang/String;
    //   42: aload_0
    //   43: getfield 57	com/kaixin001/model/User:strLogo120	Ljava/lang/String;
    //   46: aload_0
    //   47: getfield 81	com/kaixin001/model/User:mCoverUrl	Ljava/lang/String;
    //   50: invokevirtual 273	com/kaixin001/db/UserDBAdapter:updateUserInfo	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)J
    //   53: lstore 5
    //   55: lload 5
    //   57: lstore 7
    //   59: aload_2
    //   60: ifnull -47 -> 13
    //   63: aload_2
    //   64: invokevirtual 231	com/kaixin001/db/UserDBAdapter:close	()V
    //   67: lload 7
    //   69: lreturn
    //   70: astore_3
    //   71: aconst_null
    //   72: astore_2
    //   73: ldc 8
    //   75: ldc_w 275
    //   78: aload_3
    //   79: invokestatic 239	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   82: aload_2
    //   83: ifnull +7 -> 90
    //   86: aload_2
    //   87: invokevirtual 231	com/kaixin001/db/UserDBAdapter:close	()V
    //   90: ldc2_w 66
    //   93: lreturn
    //   94: astore 4
    //   96: aconst_null
    //   97: astore_2
    //   98: aload_2
    //   99: ifnull +7 -> 106
    //   102: aload_2
    //   103: invokevirtual 231	com/kaixin001/db/UserDBAdapter:close	()V
    //   106: aload 4
    //   108: athrow
    //   109: astore 4
    //   111: goto -13 -> 98
    //   114: astore_3
    //   115: goto -42 -> 73
    //
    // Exception table:
    //   from	to	target	type
    //   16	25	70	java/lang/Exception
    //   16	25	94	finally
    //   25	55	109	finally
    //   73	82	109	finally
    //   25	55	114	java/lang/Exception
  }

  // ERROR //
  public long saveUserLoginInfo(Context paramContext)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 77	com/kaixin001/model/User:isLookAround	I
    //   4: iconst_1
    //   5: if_icmpne +11 -> 16
    //   8: ldc2_w 66
    //   11: lstore 7
    //   13: lload 7
    //   15: lreturn
    //   16: new 175	com/kaixin001/db/UserDBAdapter
    //   19: dup
    //   20: aload_1
    //   21: invokespecial 177	com/kaixin001/db/UserDBAdapter:<init>	(Landroid/content/Context;)V
    //   24: astore_2
    //   25: aload_2
    //   26: aload_0
    //   27: getfield 47	com/kaixin001/model/User:strAccount	Ljava/lang/String;
    //   30: ldc 45
    //   32: aload_0
    //   33: getfield 51	com/kaixin001/model/User:strUID	Ljava/lang/String;
    //   36: aload_0
    //   37: getfield 59	com/kaixin001/model/User:strAccessToken	Ljava/lang/String;
    //   40: aload_0
    //   41: getfield 61	com/kaixin001/model/User:strRefreshToken	Ljava/lang/String;
    //   44: aload_0
    //   45: getfield 63	com/kaixin001/model/User:strOAuthTokenSecret	Ljava/lang/String;
    //   48: aload_0
    //   49: getfield 69	com/kaixin001/model/User:mExpiresIn	J
    //   52: aload_0
    //   53: getfield 71	com/kaixin001/model/User:strReserved	Ljava/lang/String;
    //   56: invokevirtual 278	com/kaixin001/db/UserDBAdapter:saveUserLoginInfo	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;JLjava/lang/String;)J
    //   59: lstore 5
    //   61: lload 5
    //   63: lstore 7
    //   65: aload_2
    //   66: ifnull -53 -> 13
    //   69: aload_2
    //   70: invokevirtual 231	com/kaixin001/db/UserDBAdapter:close	()V
    //   73: lload 7
    //   75: lreturn
    //   76: astore_3
    //   77: aconst_null
    //   78: astore_2
    //   79: ldc 8
    //   81: ldc_w 275
    //   84: aload_3
    //   85: invokestatic 239	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   88: aload_2
    //   89: ifnull +7 -> 96
    //   92: aload_2
    //   93: invokevirtual 231	com/kaixin001/db/UserDBAdapter:close	()V
    //   96: ldc2_w 66
    //   99: lreturn
    //   100: astore 4
    //   102: aconst_null
    //   103: astore_2
    //   104: aload_2
    //   105: ifnull +7 -> 112
    //   108: aload_2
    //   109: invokevirtual 231	com/kaixin001/db/UserDBAdapter:close	()V
    //   112: aload 4
    //   114: athrow
    //   115: astore 4
    //   117: goto -13 -> 104
    //   120: astore_3
    //   121: goto -42 -> 79
    //
    // Exception table:
    //   from	to	target	type
    //   16	25	76	java/lang/Exception
    //   16	25	100	finally
    //   25	61	115	finally
    //   79	88	115	finally
    //   25	61	120	java/lang/Exception
  }

  public void setAccount(String paramString)
  {
    this.strAccount = paramString;
  }

  public void setCoverId(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      KXLog.d("UserName", "set login url:" + paramString);
      this.mCoverId = paramString;
    }
  }

  public void setCoverUrl(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      KXLog.d("UserName", "set login url:" + paramString);
      this.mCoverUrl = paramString;
    }
  }

  public void setExpiresIn(long paramLong)
  {
    this.mExpiresIn = paramLong;
  }

  public void setImcomplete(int paramInt)
  {
    this.imcomplete = paramInt;
  }

  public void setLogo(String paramString)
  {
    this.strLogo = paramString;
  }

  public void setLogo120(String paramString)
  {
    this.strLogo120 = paramString;
  }

  public void setLookAround(Boolean paramBoolean)
  {
    if (paramBoolean.booleanValue())
    {
      this.isLookAround = 1;
      return;
    }
    this.isLookAround = 0;
  }

  public void setName(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      KXLog.d("UserName", "set login user name:" + paramString);
      this.strName = paramString;
    }
  }

  public void setNeedRefresh(boolean paramBoolean)
  {
    this.mNeedRefresh = paramBoolean;
  }

  public void setOauthToken(String paramString)
  {
    this.strAccessToken = paramString;
  }

  public void setOauthTokenSecret(String paramString)
  {
    this.strOAuthTokenSecret = paramString;
  }

  public void setOnline(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      KXLog.d("UserName", "set mOnline:" + paramString);
      this.mOnline = paramString;
    }
  }

  public void setPassword(String paramString)
  {
    this.strPassword = paramString;
  }

  public void setRefreshToken(String paramString)
  {
    this.strRefreshToken = paramString;
  }

  public void setReserved(String paramString)
  {
    this.strReserved = paramString;
  }

  public void setState(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      KXLog.d("UserName", "set state:" + paramString);
      this.mState = paramString;
    }
  }

  public void setUID(String paramString)
  {
    this.strUID = paramString;
  }

  public void setWapVerify(String paramString)
  {
    this.strWapVerify = paramString;
  }

  public void setmUserLevel(UserLevel paramUserLevel)
  {
    this.mUserLevel = paramUserLevel;
  }

  public boolean syncPhotoUnderWifi()
  {
    return true;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.User
 * JD-Core Version:    0.6.0
 */