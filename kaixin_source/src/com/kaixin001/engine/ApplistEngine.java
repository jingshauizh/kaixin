package com.kaixin001.engine;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import com.kaixin001.model.ApplistModel;
import com.kaixin001.model.ApplistModel.AppItem;
import com.kaixin001.model.Setting;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpMethod;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.FileUtil;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public class ApplistEngine extends KXEngine
{
  public static String APPLIST_FILE = "apps_list.kx";
  private static final String LOGTAG = "ApplistEngine";
  public static String THIRD_APPLIST_FILE = "third_apps_list.kx";
  private static ApplistEngine instance = null;

  public static ApplistEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new ApplistEngine();
      ApplistEngine localApplistEngine = instance;
      return localApplistEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public void clearCache(Context paramContext, String paramString)
  {
    if (TextUtils.isEmpty(paramString));
    while (true)
    {
      return;
      try
      {
        if (!Environment.getExternalStorageState().equals("mounted"))
          continue;
        String str = FileUtil.getKXCacheDir(paramContext);
        FileUtil.deleteCacheFile(str, paramString, APPLIST_FILE);
        FileUtil.deleteCacheFile(str, paramString, THIRD_APPLIST_FILE);
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }

  public boolean getApplistCacheData(Context paramContext)
    throws SecurityErrorException
  {
    boolean bool = Environment.getExternalStorageState().equals("mounted");
    String str1 = null;
    String str2 = null;
    if (bool)
    {
      String str3 = FileUtil.getKXCacheDir(paramContext);
      str1 = FileUtil.getCacheData(str3, User.getInstance().getUID(), APPLIST_FILE);
      str2 = FileUtil.getCacheData(str3, User.getInstance().getUID(), THIRD_APPLIST_FILE);
    }
    if ((TextUtils.isEmpty(str1)) && (TextUtils.isEmpty(str2)));
    do
      return false;
    while (!parseApplistJSON(paramContext, str1));
    return parseThirdApplistJSON(paramContext, str2);
  }

  public boolean getApplistData(Context paramContext)
    throws SecurityErrorException
  {
    String str1 = User.getInstance().getUID();
    String str2 = Protocol.getInstance().makeGetApplistRequest(str1);
    HttpProxy localHttpProxy1 = new HttpProxy(paramContext);
    try
    {
      String str16 = localHttpProxy1.httpGet(str2, null, null);
      str3 = str16;
      str4 = Protocol.getInstance().makeGetThirdPartyApplistRequest(str1);
      localHttpProxy2 = new HttpProxy(paramContext);
    }
    catch (Exception localException1)
    {
      try
      {
        String str4;
        HttpProxy localHttpProxy2;
        String str15 = localHttpProxy2.httpGet(str4, null, null);
        str5 = str15;
        if ((str3 == null) || (str5 == null))
        {
          return false;
          localException1 = localException1;
          KXLog.e("ApplistEngine", "getApplist error", localException1);
          str3 = null;
        }
      }
      catch (Exception localException2)
      {
        String str3;
        String str5;
        while (true)
        {
          KXLog.e("ApplistEngine", "getApplist error", localException2);
          str5 = null;
        }
        ApplistModel.getInstance().setGetDataMode(-1);
        boolean bool1 = Environment.getExternalStorageState().equals("mounted");
        String str6 = null;
        String str7 = null;
        if (bool1)
        {
          String str14 = FileUtil.getKXCacheDir(paramContext);
          str6 = FileUtil.getCacheData(str14, User.getInstance().getUID(), APPLIST_FILE);
          str7 = FileUtil.getCacheData(str14, User.getInstance().getUID(), THIRD_APPLIST_FILE);
        }
        if ((TextUtils.isEmpty(str6)) && (TextUtils.isEmpty(str7)))
        {
          if ((parseApplistJSON(paramContext, str3)) && (parseThirdApplistJSON(paramContext, str5)))
          {
            ApplistModel.getInstance().setGetDataMode(0);
            if (Environment.getExternalStorageState().equals("mounted"))
            {
              String str13 = FileUtil.getKXCacheDir(paramContext);
              if (!FileUtil.setCacheData(str13, str1, APPLIST_FILE, str3))
                return false;
              return FileUtil.setCacheData(str13, str1, THIRD_APPLIST_FILE, str5);
            }
            return true;
          }
          return false;
        }
        try
        {
          int i = str3.indexOf("ctime");
          String str8 = str3.replaceAll(str3.substring(i, i + 19), "");
          int j = str6.indexOf("ctime");
          String str9 = str6.replaceAll(str6.substring(j, j + 19), "");
          int k = str5.indexOf("ctime");
          String str10 = str5.replaceAll(str5.substring(k, k + 19), "");
          int m = str7.indexOf("ctime");
          String str11 = str7.replaceAll(str7.substring(m, m + 19), "");
          if ((str8.equals(str9)) && (str10.equals(str11)))
          {
            ApplistModel.getInstance().setGetDataMode(1);
            return true;
          }
          if ((parseApplistJSON(paramContext, str3)) && (parseThirdApplistJSON(paramContext, str5)))
          {
            if (Environment.getExternalStorageState().equals("mounted"))
            {
              String str12 = FileUtil.getKXCacheDir(paramContext);
              if (!FileUtil.setCacheData(str12, str1, APPLIST_FILE, str3))
                return false;
              boolean bool2 = FileUtil.setCacheData(str12, str1, THIRD_APPLIST_FILE, str5);
              return bool2;
            }
            return false;
          }
          return false;
        }
        catch (Exception localException3)
        {
          KXLog.e("ApplistEngine", "getApplistData compare failed", localException3);
        }
      }
    }
    return false;
  }

  public boolean getThirdAppToken(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    HashMap localHashMap = new HashMap();
    localHashMap.put("appids", paramString);
    localHashMap.put("ctype", Setting.getInstance().getCType());
    localHashMap.put("oauth_token", User.getInstance().getOauthToken());
    try
    {
      String str2 = localHttpProxy.httpRequest("http://api.kaixin001.com/mobile/accesstokens.json", HttpMethod.GET, localHashMap, null, null, null);
      str1 = str2;
      return parseAppTokenJSON(paramContext, str1);
    }
    catch (Exception localException)
    {
      while (true)
      {
        KXLog.e("ApplistEngine", "getApp token error", localException);
        String str1 = null;
      }
    }
  }

  public boolean parseAppTokenJSON(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    JSONObject localJSONObject1 = super.parseJSON(paramContext, paramString);
    if (localJSONObject1 == null)
      return false;
    try
    {
      JSONObject localJSONObject2 = (JSONObject)localJSONObject1.getJSONArray("tokens").get(0);
      ApplistModel.getInstance().setOauthToken(localJSONObject2.getString("oauth_token"));
      ApplistModel.getInstance().setOauthTokenSecret(localJSONObject2.getString("oauth_token_secret"));
      ApplistModel.getInstance().setOauthTokenOauth2(localJSONObject2.getString("access_token"));
      ApplistModel.getInstance().setOauthTokenOauth2Expire(localJSONObject2.getString("expires_in"));
      ApplistModel.getInstance().setOauthTokenOauth2Refresh(localJSONObject2.getString("refresh_token"));
      return true;
    }
    catch (Exception localException)
    {
      KXLog.e("ApplistEngine", "parseAppTokenJSON", localException);
    }
    return false;
  }

  public boolean parseApplistJSON(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    JSONObject localJSONObject1 = super.parseJSON(paramContext, paramString);
    if (localJSONObject1 == null);
    do
      return false;
    while (this.ret != 1);
    int i;
    int j;
    do
      try
      {
        JSONArray localJSONArray = localJSONObject1.getJSONObject("applist").getJSONArray("开心组件");
        ArrayList localArrayList = ApplistModel.getInstance().getApplist();
        localArrayList.clear();
        i = localJSONArray.length();
        j = 0;
        continue;
        JSONObject localJSONObject2 = (JSONObject)localJSONArray.get(j);
        ApplistModel.AppItem localAppItem = new ApplistModel.AppItem();
        localAppItem.setName(localJSONObject2.getString("name"));
        localAppItem.setUrl(localJSONObject2.getString("url"));
        localArrayList.add(localAppItem);
        j++;
      }
      catch (Exception localException)
      {
        KXLog.e("ApplistEngine", "parseApplistJSON", localException);
        return false;
      }
    while (j < i);
    return true;
  }

  // ERROR //
  public boolean parseThirdApplistJSON(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: aload_2
    //   3: invokespecial 199	com/kaixin001/engine/KXEngine:parseJSON	(Landroid/content/Context;Ljava/lang/String;)Lorg/json/JSONObject;
    //   6: astore_3
    //   7: aload_3
    //   8: ifnonnull +5 -> 13
    //   11: iconst_0
    //   12: ireturn
    //   13: aload_0
    //   14: getfield 245	com/kaixin001/engine/ApplistEngine:ret	I
    //   17: iconst_1
    //   18: if_icmpne +626 -> 644
    //   21: aload_3
    //   22: ldc_w 285
    //   25: invokevirtual 207	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   28: astore 5
    //   30: aload 5
    //   32: iconst_0
    //   33: invokevirtual 213	org/json/JSONArray:get	(I)Ljava/lang/Object;
    //   36: checkcast 203	org/json/JSONObject
    //   39: ldc_w 287
    //   42: invokevirtual 207	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   45: astore 6
    //   47: aload 6
    //   49: invokevirtual 266	org/json/JSONArray:length	()I
    //   52: istore 7
    //   54: aconst_null
    //   55: astore 8
    //   57: iload 7
    //   59: ifle +74 -> 133
    //   62: invokestatic 125	com/kaixin001/model/ApplistModel:getInstance	()Lcom/kaixin001/model/ApplistModel;
    //   65: invokevirtual 290	com/kaixin001/model/ApplistModel:getSearchlist	()Ljava/util/ArrayList;
    //   68: astore 9
    //   70: aload 9
    //   72: invokevirtual 262	java/util/ArrayList:clear	()V
    //   75: aload 6
    //   77: iconst_0
    //   78: invokevirtual 213	org/json/JSONArray:get	(I)Ljava/lang/Object;
    //   81: checkcast 203	org/json/JSONObject
    //   84: astore 10
    //   86: new 268	com/kaixin001/model/ApplistModel$AppItem
    //   89: dup
    //   90: invokespecial 269	com/kaixin001/model/ApplistModel$AppItem:<init>	()V
    //   93: astore 11
    //   95: aload 11
    //   97: aload 10
    //   99: ldc_w 271
    //   102: invokevirtual 216	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   105: invokevirtual 274	com/kaixin001/model/ApplistModel$AppItem:setName	(Ljava/lang/String;)V
    //   108: aload 11
    //   110: aload 10
    //   112: ldc_w 292
    //   115: invokevirtual 216	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   118: invokevirtual 279	com/kaixin001/model/ApplistModel$AppItem:setUrl	(Ljava/lang/String;)V
    //   121: aload 9
    //   123: aload 11
    //   125: invokevirtual 282	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   128: pop
    //   129: aload 11
    //   131: astore 8
    //   133: aload 5
    //   135: iconst_1
    //   136: invokevirtual 213	org/json/JSONArray:get	(I)Ljava/lang/Object;
    //   139: checkcast 203	org/json/JSONObject
    //   142: ldc_w 287
    //   145: invokevirtual 207	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   148: astore 14
    //   150: invokestatic 125	com/kaixin001/model/ApplistModel:getInstance	()Lcom/kaixin001/model/ApplistModel;
    //   153: invokevirtual 295	com/kaixin001/model/ApplistModel:getPushlist	()Ljava/util/ArrayList;
    //   156: astore 15
    //   158: aload 15
    //   160: invokevirtual 262	java/util/ArrayList:clear	()V
    //   163: iconst_0
    //   164: istore 16
    //   166: aload 8
    //   168: astore 11
    //   170: iload 16
    //   172: aload 14
    //   174: invokevirtual 266	org/json/JSONArray:length	()I
    //   177: if_icmplt +91 -> 268
    //   180: aload 5
    //   182: iconst_2
    //   183: invokevirtual 213	org/json/JSONArray:get	(I)Ljava/lang/Object;
    //   186: checkcast 203	org/json/JSONObject
    //   189: ldc_w 287
    //   192: invokevirtual 207	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   195: astore 20
    //   197: invokestatic 125	com/kaixin001/model/ApplistModel:getInstance	()Lcom/kaixin001/model/ApplistModel;
    //   200: invokevirtual 298	com/kaixin001/model/ApplistModel:getGamelist	()Ljava/util/ArrayList;
    //   203: astore 21
    //   205: aload 21
    //   207: invokevirtual 262	java/util/ArrayList:clear	()V
    //   210: iconst_0
    //   211: istore 22
    //   213: iload 22
    //   215: aload 20
    //   217: invokevirtual 266	org/json/JSONArray:length	()I
    //   220: if_icmplt +126 -> 346
    //   223: aload 5
    //   225: iconst_3
    //   226: invokevirtual 213	org/json/JSONArray:get	(I)Ljava/lang/Object;
    //   229: checkcast 203	org/json/JSONObject
    //   232: ldc_w 287
    //   235: invokevirtual 207	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   238: astore 26
    //   240: invokestatic 125	com/kaixin001/model/ApplistModel:getInstance	()Lcom/kaixin001/model/ApplistModel;
    //   243: invokevirtual 301	com/kaixin001/model/ApplistModel:getThirdApplist	()Ljava/util/ArrayList;
    //   246: astore 27
    //   248: aload 27
    //   250: invokevirtual 262	java/util/ArrayList:clear	()V
    //   253: iconst_0
    //   254: istore 28
    //   256: iload 28
    //   258: aload 26
    //   260: invokevirtual 266	org/json/JSONArray:length	()I
    //   263: if_icmplt +226 -> 489
    //   266: iconst_1
    //   267: ireturn
    //   268: aload 14
    //   270: iload 16
    //   272: invokevirtual 213	org/json/JSONArray:get	(I)Ljava/lang/Object;
    //   275: checkcast 203	org/json/JSONObject
    //   278: astore 17
    //   280: new 268	com/kaixin001/model/ApplistModel$AppItem
    //   283: dup
    //   284: invokespecial 269	com/kaixin001/model/ApplistModel$AppItem:<init>	()V
    //   287: astore 18
    //   289: aload 18
    //   291: aload 17
    //   293: ldc_w 271
    //   296: invokevirtual 216	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   299: invokevirtual 274	com/kaixin001/model/ApplistModel$AppItem:setName	(Ljava/lang/String;)V
    //   302: aload 18
    //   304: aload 17
    //   306: ldc_w 292
    //   309: invokevirtual 216	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   312: invokevirtual 279	com/kaixin001/model/ApplistModel$AppItem:setUrl	(Ljava/lang/String;)V
    //   315: aload 18
    //   317: aload 17
    //   319: ldc_w 303
    //   322: invokevirtual 216	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   325: invokevirtual 306	com/kaixin001/model/ApplistModel$AppItem:setLogo	(Ljava/lang/String;)V
    //   328: aload 15
    //   330: aload 18
    //   332: invokevirtual 282	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   335: pop
    //   336: iinc 16 1
    //   339: aload 18
    //   341: astore 11
    //   343: goto -173 -> 170
    //   346: aload 20
    //   348: iload 22
    //   350: invokevirtual 213	org/json/JSONArray:get	(I)Ljava/lang/Object;
    //   353: checkcast 203	org/json/JSONObject
    //   356: astore 23
    //   358: new 268	com/kaixin001/model/ApplistModel$AppItem
    //   361: dup
    //   362: invokespecial 269	com/kaixin001/model/ApplistModel$AppItem:<init>	()V
    //   365: astore 24
    //   367: aload 24
    //   369: aload 23
    //   371: ldc_w 271
    //   374: invokevirtual 216	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   377: invokevirtual 274	com/kaixin001/model/ApplistModel$AppItem:setName	(Ljava/lang/String;)V
    //   380: aload 24
    //   382: aload 23
    //   384: ldc_w 292
    //   387: invokevirtual 216	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   390: invokevirtual 279	com/kaixin001/model/ApplistModel$AppItem:setUrl	(Ljava/lang/String;)V
    //   393: aload 24
    //   395: aload 23
    //   397: ldc_w 303
    //   400: invokevirtual 216	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   403: invokevirtual 306	com/kaixin001/model/ApplistModel$AppItem:setLogo	(Ljava/lang/String;)V
    //   406: aload 24
    //   408: aload 23
    //   410: ldc_w 308
    //   413: invokevirtual 216	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   416: invokevirtual 311	com/kaixin001/model/ApplistModel$AppItem:setPakageName	(Ljava/lang/String;)V
    //   419: aload 24
    //   421: aload 23
    //   423: ldc_w 313
    //   426: invokevirtual 216	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   429: invokevirtual 316	com/kaixin001/model/ApplistModel$AppItem:setVersion	(Ljava/lang/String;)V
    //   432: aload 24
    //   434: aload 23
    //   436: ldc_w 318
    //   439: invokevirtual 216	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   442: invokevirtual 321	com/kaixin001/model/ApplistModel$AppItem:setDownloadUrl	(Ljava/lang/String;)V
    //   445: aload 24
    //   447: aload 23
    //   449: ldc_w 323
    //   452: invokevirtual 216	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   455: invokevirtual 326	com/kaixin001/model/ApplistModel$AppItem:setAppId	(Ljava/lang/String;)V
    //   458: aload 24
    //   460: aload 23
    //   462: ldc_w 328
    //   465: invokevirtual 216	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   468: invokevirtual 331	com/kaixin001/model/ApplistModel$AppItem:setAppEntry	(Ljava/lang/String;)V
    //   471: aload 21
    //   473: aload 24
    //   475: invokevirtual 282	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   478: pop
    //   479: iinc 22 1
    //   482: aload 24
    //   484: astore 11
    //   486: goto -273 -> 213
    //   489: aload 26
    //   491: iload 28
    //   493: invokevirtual 213	org/json/JSONArray:get	(I)Ljava/lang/Object;
    //   496: checkcast 203	org/json/JSONObject
    //   499: astore 29
    //   501: new 268	com/kaixin001/model/ApplistModel$AppItem
    //   504: dup
    //   505: invokespecial 269	com/kaixin001/model/ApplistModel$AppItem:<init>	()V
    //   508: astore 30
    //   510: aload 30
    //   512: aload 29
    //   514: ldc_w 271
    //   517: invokevirtual 216	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   520: invokevirtual 274	com/kaixin001/model/ApplistModel$AppItem:setName	(Ljava/lang/String;)V
    //   523: aload 30
    //   525: aload 29
    //   527: ldc_w 292
    //   530: invokevirtual 216	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   533: invokevirtual 279	com/kaixin001/model/ApplistModel$AppItem:setUrl	(Ljava/lang/String;)V
    //   536: aload 30
    //   538: aload 29
    //   540: ldc_w 303
    //   543: invokevirtual 216	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   546: invokevirtual 306	com/kaixin001/model/ApplistModel$AppItem:setLogo	(Ljava/lang/String;)V
    //   549: aload 30
    //   551: aload 29
    //   553: ldc_w 308
    //   556: invokevirtual 216	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   559: invokevirtual 311	com/kaixin001/model/ApplistModel$AppItem:setPakageName	(Ljava/lang/String;)V
    //   562: aload 30
    //   564: aload 29
    //   566: ldc_w 313
    //   569: invokevirtual 216	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   572: invokevirtual 316	com/kaixin001/model/ApplistModel$AppItem:setVersion	(Ljava/lang/String;)V
    //   575: aload 30
    //   577: aload 29
    //   579: ldc_w 318
    //   582: invokevirtual 216	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   585: invokevirtual 321	com/kaixin001/model/ApplistModel$AppItem:setDownloadUrl	(Ljava/lang/String;)V
    //   588: aload 30
    //   590: aload 29
    //   592: ldc_w 323
    //   595: invokevirtual 216	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   598: invokevirtual 326	com/kaixin001/model/ApplistModel$AppItem:setAppId	(Ljava/lang/String;)V
    //   601: aload 30
    //   603: aload 29
    //   605: ldc_w 328
    //   608: invokevirtual 216	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   611: invokevirtual 331	com/kaixin001/model/ApplistModel$AppItem:setAppEntry	(Ljava/lang/String;)V
    //   614: aload 27
    //   616: aload 30
    //   618: invokevirtual 282	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   621: pop
    //   622: iinc 28 1
    //   625: aload 30
    //   627: astore 11
    //   629: goto -373 -> 256
    //   632: astore 4
    //   634: ldc 9
    //   636: ldc_w 283
    //   639: aload 4
    //   641: invokestatic 120	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   644: iconst_0
    //   645: ireturn
    //   646: astore 4
    //   648: aload 11
    //   650: pop
    //   651: goto -17 -> 634
    //
    // Exception table:
    //   from	to	target	type
    //   21	54	632	java/lang/Exception
    //   62	95	632	java/lang/Exception
    //   133	163	632	java/lang/Exception
    //   289	336	632	java/lang/Exception
    //   367	479	632	java/lang/Exception
    //   510	622	632	java/lang/Exception
    //   95	129	646	java/lang/Exception
    //   170	210	646	java/lang/Exception
    //   213	253	646	java/lang/Exception
    //   256	266	646	java/lang/Exception
    //   268	289	646	java/lang/Exception
    //   346	367	646	java/lang/Exception
    //   489	510	646	java/lang/Exception
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.ApplistEngine
 * JD-Core Version:    0.6.0
 */