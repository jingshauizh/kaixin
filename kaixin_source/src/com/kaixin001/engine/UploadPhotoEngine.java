package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProgressListener;
import org.json.JSONObject;

public class UploadPhotoEngine extends KXEngine
{
  public static final String DEFAULTID = "0";
  private static final String FROM_WEBPAGE = "from_webpage";
  private static final String LOGTAG = "UploadPhotoEngine";
  public static final int POST_PROGRESS_NOTIFY = 101;
  private static UploadPhotoEngine instance = null;
  private int mLastErrorCode = -1;
  private String msLargeUri = "";
  private String msThumbnailUri = "";

  public static UploadPhotoEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new UploadPhotoEngine();
      UploadPhotoEngine localUploadPhotoEngine = instance;
      return localUploadPhotoEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private void parseHomePhotoJson(Context paramContext, String paramString)
  {
    if (TextUtils.isEmpty(paramString));
    while (true)
    {
      return;
      try
      {
        JSONObject localJSONObject1 = super.parseJSON(paramContext, paramString);
        if (localJSONObject1 == null)
          continue;
        JSONObject localJSONObject2 = localJSONObject1.getJSONObject("pics");
        if (localJSONObject2 != null)
        {
          this.msThumbnailUri = localJSONObject2.getString("small");
          this.msLargeUri = localJSONObject2.getString("large");
        }
        JSONObject localJSONObject3 = localJSONObject1.optJSONObject("cover");
        if (localJSONObject3 == null)
          continue;
        String str1 = localJSONObject3.optString("id");
        String str2 = localJSONObject3.optString("url");
        if (!TextUtils.isEmpty(str1))
          User.getInstance().setCoverId(str1);
        if (TextUtils.isEmpty(str2))
          continue;
        User.getInstance().setCoverUrl(str2);
        return;
      }
      catch (Exception localException)
      {
      }
    }
  }

  // ERROR //
  public boolean doUploadPhoto(Context paramContext, String paramString1, String paramString2, android.os.Handler paramHandler, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, HttpProgressListener paramHttpProgressListener, boolean paramBoolean)
  {
    // Byte code:
    //   0: invokestatic 105	com/kaixin001/network/Protocol:getInstance	()Lcom/kaixin001/network/Protocol;
    //   3: aload_2
    //   4: ldc 107
    //   6: invokestatic 87	com/kaixin001/model/User:getInstance	()Lcom/kaixin001/model/User;
    //   9: invokevirtual 111	com/kaixin001/model/User:getUID	()Ljava/lang/String;
    //   12: aload 5
    //   14: aload 6
    //   16: aload 7
    //   18: aload 8
    //   20: aload 9
    //   22: invokevirtual 115	com/kaixin001/network/Protocol:makeUploadPhotoRequest	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   25: astore 12
    //   27: aload_1
    //   28: invokevirtual 121	android/content/Context:getApplicationContext	()Landroid/content/Context;
    //   31: ldc 11
    //   33: iconst_0
    //   34: invokevirtual 125	android/content/Context:getSharedPreferences	(Ljava/lang/String;I)Landroid/content/SharedPreferences;
    //   37: ldc 127
    //   39: ldc 8
    //   41: invokeinterface 132 3 0
    //   46: astore 13
    //   48: aload_1
    //   49: invokestatic 137	com/kaixin001/util/UserHabitUtils:getInstance	(Landroid/content/Context;)Lcom/kaixin001/util/UserHabitUtils;
    //   52: new 139	java/lang/StringBuilder
    //   55: dup
    //   56: ldc 141
    //   58: invokespecial 143	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   61: aload 13
    //   63: invokevirtual 147	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   66: invokevirtual 150	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   69: invokevirtual 153	com/kaixin001/util/UserHabitUtils:addUserHabit	(Ljava/lang/String;)V
    //   72: aconst_null
    //   73: astore 14
    //   75: new 155	java/io/File
    //   78: dup
    //   79: aload_3
    //   80: invokespecial 156	java/io/File:<init>	(Ljava/lang/String;)V
    //   83: astore 15
    //   85: aload 15
    //   87: invokevirtual 160	java/io/File:exists	()Z
    //   90: istore 18
    //   92: aconst_null
    //   93: astore 14
    //   95: iload 18
    //   97: ifne +10 -> 107
    //   100: aload 14
    //   102: invokestatic 166	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   105: iconst_0
    //   106: ireturn
    //   107: new 168	java/io/FileInputStream
    //   110: dup
    //   111: aload 15
    //   113: invokespecial 171	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   116: astore 19
    //   118: aload 15
    //   120: ifnull +492 -> 612
    //   123: aload 19
    //   125: ifnonnull +10 -> 135
    //   128: aload 19
    //   130: astore 14
    //   132: goto -32 -> 100
    //   135: new 173	java/util/HashMap
    //   138: dup
    //   139: invokespecial 174	java/util/HashMap:<init>	()V
    //   142: astore 20
    //   144: aload 20
    //   146: ldc 176
    //   148: invokestatic 87	com/kaixin001/model/User:getInstance	()Lcom/kaixin001/model/User;
    //   151: invokevirtual 111	com/kaixin001/model/User:getUID	()Ljava/lang/String;
    //   154: invokeinterface 182 3 0
    //   159: pop
    //   160: aload 20
    //   162: ldc 184
    //   164: aload_2
    //   165: invokeinterface 182 3 0
    //   170: pop
    //   171: aload 5
    //   173: invokestatic 50	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   176: ifne +15 -> 191
    //   179: aload 20
    //   181: ldc 186
    //   183: aload 5
    //   185: invokeinterface 182 3 0
    //   190: pop
    //   191: aload 9
    //   193: invokestatic 50	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   196: ifne +15 -> 211
    //   199: aload 20
    //   201: ldc 188
    //   203: aload 9
    //   205: invokeinterface 182 3 0
    //   210: pop
    //   211: aload 6
    //   213: invokestatic 50	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   216: ifne +39 -> 255
    //   219: aload 20
    //   221: ldc 190
    //   223: aload 6
    //   225: invokeinterface 182 3 0
    //   230: pop
    //   231: aload 20
    //   233: ldc 192
    //   235: aload 7
    //   237: invokeinterface 182 3 0
    //   242: pop
    //   243: aload 20
    //   245: ldc 194
    //   247: aload 8
    //   249: invokeinterface 182 3 0
    //   254: pop
    //   255: aload 20
    //   257: ldc 196
    //   259: ldc 198
    //   261: invokeinterface 182 3 0
    //   266: pop
    //   267: aload 20
    //   269: ldc 200
    //   271: ldc 202
    //   273: invokeinterface 182 3 0
    //   278: pop
    //   279: aload 15
    //   281: ifnull +15 -> 296
    //   284: aload 20
    //   286: ldc 204
    //   288: aload 15
    //   290: invokeinterface 182 3 0
    //   295: pop
    //   296: iload 11
    //   298: ifeq +57 -> 355
    //   301: aload_1
    //   302: invokevirtual 121	android/content/Context:getApplicationContext	()Landroid/content/Context;
    //   305: ldc 11
    //   307: iconst_0
    //   308: invokevirtual 125	android/content/Context:getSharedPreferences	(Ljava/lang/String;I)Landroid/content/SharedPreferences;
    //   311: astore 26
    //   313: aload 20
    //   315: ldc 206
    //   317: aload 26
    //   319: ldc 77
    //   321: ldc 8
    //   323: invokeinterface 132 3 0
    //   328: invokeinterface 182 3 0
    //   333: pop
    //   334: aload 26
    //   336: invokeinterface 210 1 0
    //   341: ldc 212
    //   343: iconst_0
    //   344: invokeinterface 218 3 0
    //   349: invokeinterface 221 1 0
    //   354: pop
    //   355: new 223	com/kaixin001/network/HttpProxy
    //   358: dup
    //   359: aload_1
    //   360: invokespecial 226	com/kaixin001/network/HttpProxy:<init>	(Landroid/content/Context;)V
    //   363: astore 29
    //   365: aload 29
    //   367: aload 12
    //   369: aload 20
    //   371: aconst_null
    //   372: aload 10
    //   374: invokevirtual 230	com/kaixin001/network/HttpProxy:httpPost	(Ljava/lang/String;Ljava/util/Map;Lcom/kaixin001/network/HttpRequestState;Lcom/kaixin001/network/HttpProgressListener;)Ljava/lang/String;
    //   377: astore 41
    //   379: aload 41
    //   381: astore 31
    //   383: aload 19
    //   385: invokestatic 166	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   388: aload 31
    //   390: invokestatic 50	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   393: ifeq +50 -> 443
    //   396: iconst_0
    //   397: ireturn
    //   398: astore 30
    //   400: ldc 14
    //   402: ldc 232
    //   404: aload 30
    //   406: invokestatic 238	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   409: aconst_null
    //   410: astore 31
    //   412: goto -29 -> 383
    //   415: astore 16
    //   417: aload 19
    //   419: astore 14
    //   421: aload 16
    //   423: invokevirtual 241	java/io/FileNotFoundException:printStackTrace	()V
    //   426: aload 14
    //   428: invokestatic 166	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   431: iconst_0
    //   432: ireturn
    //   433: astore 17
    //   435: aload 14
    //   437: invokestatic 166	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   440: aload 17
    //   442: athrow
    //   443: aconst_null
    //   444: astore 32
    //   446: aload_0
    //   447: aload_1
    //   448: aload 31
    //   450: invokespecial 54	com/kaixin001/engine/KXEngine:parseJSON	(Landroid/content/Context;Ljava/lang/String;)Lorg/json/JSONObject;
    //   453: astore 32
    //   455: aload 32
    //   457: ldc 56
    //   459: invokevirtual 62	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
    //   462: astore 40
    //   464: aload 40
    //   466: astore 34
    //   468: aload 32
    //   470: ifnonnull +60 -> 530
    //   473: iconst_0
    //   474: istore 36
    //   476: aload 4
    //   478: ifnull +30 -> 508
    //   481: invokestatic 247	android/os/Message:obtain	()Landroid/os/Message;
    //   484: astore 37
    //   486: aload 37
    //   488: bipush 101
    //   490: putfield 250	android/os/Message:what	I
    //   493: aload 37
    //   495: bipush 100
    //   497: putfield 253	android/os/Message:arg1	I
    //   500: aload 4
    //   502: aload 37
    //   504: invokevirtual 259	android/os/Handler:sendMessage	(Landroid/os/Message;)Z
    //   507: pop
    //   508: iload 36
    //   510: ireturn
    //   511: astore 33
    //   513: ldc_w 261
    //   516: aload 33
    //   518: invokevirtual 262	java/lang/Exception:toString	()Ljava/lang/String;
    //   521: invokestatic 265	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   524: aconst_null
    //   525: astore 34
    //   527: goto -59 -> 468
    //   530: aload_0
    //   531: getfield 268	com/kaixin001/engine/KXEngine:ret	I
    //   534: istore 39
    //   536: iload 39
    //   538: iconst_1
    //   539: if_icmpne +31 -> 570
    //   542: aload_0
    //   543: aload 34
    //   545: ldc 64
    //   547: invokevirtual 68	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   550: putfield 33	com/kaixin001/engine/UploadPhotoEngine:msThumbnailUri	Ljava/lang/String;
    //   553: aload_0
    //   554: aload 34
    //   556: ldc 70
    //   558: invokevirtual 68	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   561: putfield 35	com/kaixin001/engine/UploadPhotoEngine:msLargeUri	Ljava/lang/String;
    //   564: iconst_1
    //   565: istore 36
    //   567: goto -91 -> 476
    //   570: aload_0
    //   571: iload 39
    //   573: putfield 37	com/kaixin001/engine/UploadPhotoEngine:mLastErrorCode	I
    //   576: iconst_0
    //   577: istore 36
    //   579: goto -103 -> 476
    //   582: astore 35
    //   584: aload 35
    //   586: invokevirtual 269	org/json/JSONException:printStackTrace	()V
    //   589: iconst_0
    //   590: istore 36
    //   592: goto -116 -> 476
    //   595: astore 17
    //   597: aload 19
    //   599: astore 14
    //   601: goto -166 -> 435
    //   604: astore 16
    //   606: aconst_null
    //   607: astore 14
    //   609: goto -188 -> 421
    //   612: aload 19
    //   614: astore 14
    //   616: goto -516 -> 100
    //
    // Exception table:
    //   from	to	target	type
    //   365	379	398	java/lang/Exception
    //   135	191	415	java/io/FileNotFoundException
    //   191	211	415	java/io/FileNotFoundException
    //   211	255	415	java/io/FileNotFoundException
    //   255	279	415	java/io/FileNotFoundException
    //   284	296	415	java/io/FileNotFoundException
    //   301	355	415	java/io/FileNotFoundException
    //   355	365	415	java/io/FileNotFoundException
    //   365	379	415	java/io/FileNotFoundException
    //   400	409	415	java/io/FileNotFoundException
    //   75	92	433	finally
    //   107	118	433	finally
    //   421	426	433	finally
    //   446	464	511	java/lang/Exception
    //   530	536	582	org/json/JSONException
    //   542	564	582	org/json/JSONException
    //   570	576	582	org/json/JSONException
    //   135	191	595	finally
    //   191	211	595	finally
    //   211	255	595	finally
    //   255	279	595	finally
    //   284	296	595	finally
    //   301	355	595	finally
    //   355	365	595	finally
    //   365	379	595	finally
    //   400	409	595	finally
    //   75	92	604	java/io/FileNotFoundException
    //   107	118	604	java/io/FileNotFoundException
  }

  public String getLargeUri()
  {
    return this.msLargeUri;
  }

  public int getLastErrorCode()
  {
    return this.mLastErrorCode;
  }

  public String getThumbnailUri()
  {
    return this.msThumbnailUri;
  }

  // ERROR //
  public void uploadHomePhoto(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9)
  {
    // Byte code:
    //   0: invokestatic 105	com/kaixin001/network/Protocol:getInstance	()Lcom/kaixin001/network/Protocol;
    //   3: invokestatic 87	com/kaixin001/model/User:getInstance	()Lcom/kaixin001/model/User;
    //   6: invokevirtual 111	com/kaixin001/model/User:getUID	()Ljava/lang/String;
    //   9: aload 4
    //   11: aload 7
    //   13: aload_2
    //   14: aload 6
    //   16: invokevirtual 279	com/kaixin001/network/Protocol:getUploadHomePhotoUrl	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   19: astore 11
    //   21: aconst_null
    //   22: astore 12
    //   24: new 155	java/io/File
    //   27: dup
    //   28: aload_3
    //   29: invokespecial 156	java/io/File:<init>	(Ljava/lang/String;)V
    //   32: astore 13
    //   34: aload 13
    //   36: invokevirtual 160	java/io/File:exists	()Z
    //   39: istore 16
    //   41: aconst_null
    //   42: astore 12
    //   44: iload 16
    //   46: ifne +9 -> 55
    //   49: aload 12
    //   51: invokestatic 166	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   54: return
    //   55: new 168	java/io/FileInputStream
    //   58: dup
    //   59: aload 13
    //   61: invokespecial 171	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   64: astore 17
    //   66: aload 13
    //   68: ifnull +311 -> 379
    //   71: aload 17
    //   73: ifnonnull +10 -> 83
    //   76: aload 17
    //   78: astore 12
    //   80: goto -31 -> 49
    //   83: new 173	java/util/HashMap
    //   86: dup
    //   87: invokespecial 174	java/util/HashMap:<init>	()V
    //   90: astore 18
    //   92: aload 18
    //   94: ldc 176
    //   96: invokestatic 87	com/kaixin001/model/User:getInstance	()Lcom/kaixin001/model/User;
    //   99: invokevirtual 111	com/kaixin001/model/User:getUID	()Ljava/lang/String;
    //   102: invokeinterface 182 3 0
    //   107: pop
    //   108: aload 18
    //   110: ldc 184
    //   112: aload_2
    //   113: invokeinterface 182 3 0
    //   118: pop
    //   119: aload 4
    //   121: invokestatic 50	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   124: ifne +15 -> 139
    //   127: aload 18
    //   129: ldc 188
    //   131: aload 4
    //   133: invokeinterface 182 3 0
    //   138: pop
    //   139: aload 5
    //   141: invokestatic 50	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   144: ifne +15 -> 159
    //   147: aload 18
    //   149: ldc 186
    //   151: aload 5
    //   153: invokeinterface 182 3 0
    //   158: pop
    //   159: aload 6
    //   161: invokestatic 50	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   164: ifne +16 -> 180
    //   167: aload 18
    //   169: ldc_w 281
    //   172: aload 6
    //   174: invokeinterface 182 3 0
    //   179: pop
    //   180: aload 7
    //   182: invokestatic 50	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   185: ifne +15 -> 200
    //   188: aload 18
    //   190: ldc 72
    //   192: aload 7
    //   194: invokeinterface 182 3 0
    //   199: pop
    //   200: aload 8
    //   202: invokestatic 50	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   205: ifne +39 -> 244
    //   208: aload 18
    //   210: ldc 190
    //   212: aload 8
    //   214: invokeinterface 182 3 0
    //   219: pop
    //   220: aload 18
    //   222: ldc 192
    //   224: aload 9
    //   226: invokeinterface 182 3 0
    //   231: pop
    //   232: aload 18
    //   234: ldc 194
    //   236: aload 10
    //   238: invokeinterface 182 3 0
    //   243: pop
    //   244: aload 18
    //   246: ldc_w 283
    //   249: ldc_w 285
    //   252: invokeinterface 182 3 0
    //   257: pop
    //   258: aload 18
    //   260: ldc 196
    //   262: ldc 198
    //   264: invokeinterface 182 3 0
    //   269: pop
    //   270: aload 18
    //   272: ldc 200
    //   274: ldc 202
    //   276: invokeinterface 182 3 0
    //   281: pop
    //   282: aload 13
    //   284: ifnull +15 -> 299
    //   287: aload 18
    //   289: ldc 204
    //   291: aload 13
    //   293: invokeinterface 182 3 0
    //   298: pop
    //   299: new 223	com/kaixin001/network/HttpProxy
    //   302: dup
    //   303: aload_1
    //   304: invokespecial 226	com/kaixin001/network/HttpProxy:<init>	(Landroid/content/Context;)V
    //   307: astore 26
    //   309: aload 26
    //   311: aload 11
    //   313: aload 18
    //   315: aconst_null
    //   316: aconst_null
    //   317: invokevirtual 230	com/kaixin001/network/HttpProxy:httpPost	(Ljava/lang/String;Ljava/util/Map;Lcom/kaixin001/network/HttpRequestState;Lcom/kaixin001/network/HttpProgressListener;)Ljava/lang/String;
    //   320: astore 28
    //   322: aload 17
    //   324: invokestatic 166	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   327: aload_0
    //   328: aload_1
    //   329: aload 28
    //   331: invokespecial 287	com/kaixin001/engine/UploadPhotoEngine:parseHomePhotoJson	(Landroid/content/Context;Ljava/lang/String;)V
    //   334: return
    //   335: astore 27
    //   337: aload 17
    //   339: invokestatic 166	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   342: return
    //   343: astore 15
    //   345: aload 12
    //   347: invokestatic 166	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   350: return
    //   351: astore 14
    //   353: aload 12
    //   355: invokestatic 166	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   358: aload 14
    //   360: athrow
    //   361: astore 14
    //   363: aload 17
    //   365: astore 12
    //   367: goto -14 -> 353
    //   370: astore 19
    //   372: aload 17
    //   374: astore 12
    //   376: goto -31 -> 345
    //   379: aload 17
    //   381: astore 12
    //   383: goto -334 -> 49
    //
    // Exception table:
    //   from	to	target	type
    //   309	322	335	java/lang/Exception
    //   24	41	343	java/lang/Exception
    //   55	66	343	java/lang/Exception
    //   24	41	351	finally
    //   55	66	351	finally
    //   83	139	361	finally
    //   139	159	361	finally
    //   159	180	361	finally
    //   180	200	361	finally
    //   200	244	361	finally
    //   244	282	361	finally
    //   287	299	361	finally
    //   299	309	361	finally
    //   309	322	361	finally
    //   83	139	370	java/lang/Exception
    //   139	159	370	java/lang/Exception
    //   159	180	370	java/lang/Exception
    //   180	200	370	java/lang/Exception
    //   200	244	370	java/lang/Exception
    //   244	282	370	java/lang/Exception
    //   287	299	370	java/lang/Exception
    //   299	309	370	java/lang/Exception
  }

  public class UploadPhotoHttpProgressListener
    implements HttpProgressListener
  {
    public UploadPhotoHttpProgressListener()
    {
    }

    public void transferred(long paramLong1, long paramLong2)
    {
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.UploadPhotoEngine
 * JD-Core Version:    0.6.0
 */