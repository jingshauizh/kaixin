package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.model.HoroscopeModel;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import org.json.JSONObject;

public class HoroscopeEngine extends KXEngine
{
  private static final String LOGTAG;
  public static final int NO_TAKE_PUSH = 0;
  public static final int TAKE_PUSH = 1;
  private static HoroscopeEngine instance = null;
  private HoroscopeModel mHoroscopeModel = null;
  String pushTxt;

  static
  {
    LOGTAG = HoroscopeEngine.class.getSimpleName();
  }

  private HoroscopeEngine()
  {
    if (this.mHoroscopeModel == null)
      this.mHoroscopeModel = new HoroscopeModel();
  }

  public static HoroscopeEngine getInstance()
  {
    if (instance == null)
      instance = new HoroscopeEngine();
    return instance;
  }

  // ERROR //
  private Boolean parseHoroscopeJSON(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: iconst_1
    //   3: aload_2
    //   4: invokespecial 49	com/kaixin001/engine/KXEngine:parseJSON	(Landroid/content/Context;ZLjava/lang/String;)Lorg/json/JSONObject;
    //   7: pop
    //   8: aload_0
    //   9: getfield 33	com/kaixin001/engine/HoroscopeEngine:mHoroscopeModel	Lcom/kaixin001/model/HoroscopeModel;
    //   12: invokevirtual 53	com/kaixin001/model/HoroscopeModel:getHoroscopeData	()Lcom/kaixin001/item/HoroscopeData;
    //   15: astore 4
    //   17: aload_0
    //   18: getfield 33	com/kaixin001/engine/HoroscopeEngine:mHoroscopeModel	Lcom/kaixin001/model/HoroscopeModel;
    //   21: invokevirtual 57	com/kaixin001/model/HoroscopeModel:getUserInfoItem	()Lcom/kaixin001/item/UserInfoItem;
    //   24: pop
    //   25: new 59	org/json/JSONObject
    //   28: dup
    //   29: aload_2
    //   30: invokespecial 62	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   33: astore 6
    //   35: aload 6
    //   37: ldc 64
    //   39: invokevirtual 68	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
    //   42: astore 9
    //   44: aload 4
    //   46: aload 9
    //   48: ldc 70
    //   50: invokevirtual 74	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   53: putfield 78	com/kaixin001/item/HoroscopeData:id	Ljava/lang/String;
    //   56: aload 4
    //   58: aload 9
    //   60: ldc 80
    //   62: invokevirtual 74	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   65: putfield 82	com/kaixin001/item/HoroscopeData:type	Ljava/lang/String;
    //   68: aload 4
    //   70: aload 9
    //   72: ldc 84
    //   74: invokevirtual 74	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   77: putfield 86	com/kaixin001/item/HoroscopeData:name	Ljava/lang/String;
    //   80: aload 4
    //   82: aload 9
    //   84: ldc 88
    //   86: invokevirtual 74	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   89: putfield 90	com/kaixin001/item/HoroscopeData:total_idx	Ljava/lang/String;
    //   92: aload 4
    //   94: aload 9
    //   96: ldc 92
    //   98: invokevirtual 74	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   101: putfield 94	com/kaixin001/item/HoroscopeData:love_idx	Ljava/lang/String;
    //   104: aload 4
    //   106: aload 9
    //   108: ldc 96
    //   110: invokevirtual 74	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   113: putfield 98	com/kaixin001/item/HoroscopeData:health_idx	Ljava/lang/String;
    //   116: aload 4
    //   118: aload 9
    //   120: ldc 100
    //   122: invokevirtual 74	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   125: putfield 102	com/kaixin001/item/HoroscopeData:money_idx	Ljava/lang/String;
    //   128: aload 4
    //   130: aload 9
    //   132: ldc 104
    //   134: invokevirtual 74	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   137: putfield 106	com/kaixin001/item/HoroscopeData:work_idx	Ljava/lang/String;
    //   140: aload 4
    //   142: aload 9
    //   144: ldc 108
    //   146: invokevirtual 74	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   149: putfield 110	com/kaixin001/item/HoroscopeData:color	Ljava/lang/String;
    //   152: aload 4
    //   154: aload 9
    //   156: ldc 112
    //   158: invokevirtual 74	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   161: putfield 114	com/kaixin001/item/HoroscopeData:number	Ljava/lang/String;
    //   164: aload 4
    //   166: aload 9
    //   168: ldc 116
    //   170: invokevirtual 74	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   173: putfield 118	com/kaixin001/item/HoroscopeData:goodstar	Ljava/lang/String;
    //   176: aload 4
    //   178: aload 9
    //   180: ldc 120
    //   182: invokevirtual 74	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   185: putfield 122	com/kaixin001/item/HoroscopeData:badstar	Ljava/lang/String;
    //   188: aload 4
    //   190: aload 9
    //   192: ldc 124
    //   194: invokevirtual 74	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   197: putfield 126	com/kaixin001/item/HoroscopeData:matchstar	Ljava/lang/String;
    //   200: aload 4
    //   202: aload 9
    //   204: ldc 128
    //   206: invokevirtual 74	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   209: putfield 130	com/kaixin001/item/HoroscopeData:exresult	Ljava/lang/String;
    //   212: aload 4
    //   214: aload 9
    //   216: ldc 132
    //   218: invokevirtual 74	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   221: putfield 134	com/kaixin001/item/HoroscopeData:atime	Ljava/lang/String;
    //   224: aload 4
    //   226: aload 9
    //   228: ldc 136
    //   230: invokevirtual 74	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   233: putfield 138	com/kaixin001/item/HoroscopeData:ctime	Ljava/lang/String;
    //   236: aload 4
    //   238: new 140	java/util/ArrayList
    //   241: dup
    //   242: invokespecial 141	java/util/ArrayList:<init>	()V
    //   245: putfield 145	com/kaixin001/item/HoroscopeData:result	Ljava/util/ArrayList;
    //   248: aload 9
    //   250: ldc 146
    //   252: invokevirtual 150	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   255: astore 10
    //   257: iconst_0
    //   258: istore 11
    //   260: aload 10
    //   262: invokevirtual 156	org/json/JSONArray:length	()I
    //   265: istore 12
    //   267: iload 11
    //   269: iload 12
    //   271: if_icmplt +12 -> 283
    //   274: iconst_1
    //   275: istore 8
    //   277: iload 8
    //   279: invokestatic 162	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
    //   282: areturn
    //   283: aload 10
    //   285: iload 11
    //   287: invokevirtual 165	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
    //   290: astore 13
    //   292: new 167	org/apache/http/message/BasicNameValuePair
    //   295: dup
    //   296: aload 13
    //   298: ldc 169
    //   300: invokevirtual 74	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   303: aload 13
    //   305: ldc 171
    //   307: invokevirtual 74	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   310: invokespecial 174	org/apache/http/message/BasicNameValuePair:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   313: astore 14
    //   315: aload 4
    //   317: getfield 145	com/kaixin001/item/HoroscopeData:result	Ljava/util/ArrayList;
    //   320: aload 14
    //   322: invokevirtual 178	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   325: pop
    //   326: iinc 11 1
    //   329: goto -69 -> 260
    //   332: astore 7
    //   334: aload 7
    //   336: invokevirtual 181	org/json/JSONException:printStackTrace	()V
    //   339: iconst_0
    //   340: istore 8
    //   342: goto -65 -> 277
    //   345: astore 7
    //   347: goto -13 -> 334
    //
    // Exception table:
    //   from	to	target	type
    //   25	35	332	org/json/JSONException
    //   35	257	345	org/json/JSONException
    //   260	267	345	org/json/JSONException
    //   283	326	345	org/json/JSONException
  }

  public Boolean doGetHoroscopeRequest(Context paramContext, String paramString1, String paramString2)
  {
    Object localObject = Boolean.valueOf(false);
    String str1 = Protocol.getInstance().makeGetHoroscope(paramString1, paramString2);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (TextUtils.isEmpty(str2));
    }
    catch (Exception localException)
    {
      try
      {
        Boolean localBoolean = parseHoroscopeJSON(paramContext, str2);
        localObject = localBoolean;
        return localObject;
        localException = localException;
        KXLog.e(LOGTAG, "doGetHoroscopeRequest", localException);
        String str2 = null;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
        localSecurityErrorException.printStackTrace();
      }
    }
    return (Boolean)localObject;
  }

  public HoroscopeModel getModel()
  {
    return this.mHoroscopeModel;
  }

  public String getPushInfo()
  {
    return this.pushTxt;
  }

  public boolean setStarsPush(Context paramContext, String paramString)
  {
    int i = 1;
    String str1 = Protocol.getInstance().setStarsPush(paramString);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (TextUtils.isEmpty(str2))
        return false;
    }
    catch (Exception localException)
    {
      String str2;
      while (true)
      {
        KXLog.e(LOGTAG, "set star push error", localException);
        str2 = null;
      }
      try
      {
        int j = parseJSON(paramContext, str2).optInt("ret", 0);
        if (j == i);
        while (true)
        {
          return i;
          i = 0;
        }
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
        localSecurityErrorException.printStackTrace();
      }
    }
    return false;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.HoroscopeEngine
 * JD-Core Version:    0.6.0
 */