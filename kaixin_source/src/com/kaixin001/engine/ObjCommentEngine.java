package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.model.ObjCommentModel;
import com.kaixin001.model.ObjCommentModel.LbsCommentTitle;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import org.json.JSONObject;

public class ObjCommentEngine extends KXEngine
{
  private static final String LOGTAG = "ObjCommentEngine";
  private static ObjCommentEngine instance;
  private String mError = null;

  public static ObjCommentEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new ObjCommentEngine();
      ObjCommentEngine localObjCommentEngine = instance;
      return localObjCommentEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public boolean getLBSCommentTitle(Context paramContext, String paramString, ObjCommentModel paramObjCommentModel)
    throws SecurityErrorException
  {
    this.ret = 0;
    if (paramObjCommentModel == null);
    while (true)
    {
      return false;
      paramObjCommentModel.clear();
      String str1 = Protocol.getInstance().makeLBSCommentTitleRequest(paramString);
      HttpProxy localHttpProxy = new HttpProxy(paramContext);
      try
      {
        String str3 = localHttpProxy.httpGet(str1, null, null);
        str2 = str3;
        if (TextUtils.isEmpty(str2))
          continue;
        this.mError = null;
        return parseLBSCommentTitleJSON(paramContext, str2, paramObjCommentModel);
      }
      catch (Exception localException)
      {
        while (true)
        {
          KXLog.e("ObjCommentEngine", "getLBSCommentTitle error", localException);
          String str2 = null;
        }
      }
    }
  }

  public String getLastError()
  {
    return this.mError;
  }

  public boolean getObjCommentList(Context paramContext, String paramString1, String paramString2, String paramString3, ObjCommentModel paramObjCommentModel)
    throws SecurityErrorException
  {
    this.ret = 0;
    if (paramObjCommentModel == null);
    while (true)
    {
      return false;
      paramObjCommentModel.clear();
      String str1 = Protocol.getInstance().makeObjCommentRequest(User.getInstance().getUID(), paramString1, paramString2, paramString3);
      HttpProxy localHttpProxy = new HttpProxy(paramContext);
      try
      {
        String str3 = localHttpProxy.httpGet(str1, null, null);
        str2 = str3;
        if (TextUtils.isEmpty(str2))
          continue;
        this.mError = null;
        return parseObjCommentJSON(paramContext, str2, paramObjCommentModel);
      }
      catch (Exception localException)
      {
        while (true)
        {
          KXLog.e("ObjCommentEngine", "getObjCommentList error", localException);
          String str2 = null;
        }
      }
    }
  }

  public boolean parseLBSCommentTitleJSON(Context paramContext, String paramString, ObjCommentModel paramObjCommentModel)
    throws SecurityErrorException
  {
    int i = 1;
    JSONObject localJSONObject1 = super.parseJSON(paramContext, paramString);
    if ((localJSONObject1 == null) || (paramObjCommentModel == null))
      i = 0;
    while (true)
    {
      return i;
      if (this.ret != i)
        break;
      ObjCommentModel.LbsCommentTitle localLbsCommentTitle = paramObjCommentModel.getmLbsCommentTitle();
      JSONObject localJSONObject2 = localJSONObject1.optJSONObject("checkin");
      if (localJSONObject2 == null)
        continue;
      localLbsCommentTitle.mChid = localJSONObject2.optString("chid", "");
      localLbsCommentTitle.mPoiId = localJSONObject2.optString("poiid", "");
      localLbsCommentTitle.mFuid = localJSONObject2.optString("fuid", "");
      localLbsCommentTitle.mFname = localJSONObject2.optString("fname", "");
      localLbsCommentTitle.mFlogo = localJSONObject2.optString("flogo", "");
      localLbsCommentTitle.mContent = localJSONObject2.optString("content", "");
      localLbsCommentTitle.mThumbnail = localJSONObject2.optString("thumbnail", "");
      localLbsCommentTitle.mLargePhoto = localJSONObject2.optString("large", "");
      localLbsCommentTitle.mSource = localJSONObject2.optString("source", "");
      localLbsCommentTitle.mCtime = localJSONObject2.optString("ctime", "");
      localLbsCommentTitle.mPoiName = localJSONObject2.optString("poiname", "");
      return i;
    }
    return false;
  }

  // ERROR //
  public boolean parseObjCommentJSON(Context paramContext, String paramString, ObjCommentModel paramObjCommentModel)
    throws SecurityErrorException
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: aload_2
    //   3: invokespecial 97	com/kaixin001/engine/KXEngine:parseJSON	(Landroid/content/Context;Ljava/lang/String;)Lorg/json/JSONObject;
    //   6: astore 4
    //   8: aload 4
    //   10: ifnull +7 -> 17
    //   13: aload_3
    //   14: ifnonnull +5 -> 19
    //   17: iconst_0
    //   18: ireturn
    //   19: aload_0
    //   20: getfield 32	com/kaixin001/engine/ObjCommentEngine:ret	I
    //   23: iconst_1
    //   24: if_icmpne +433 -> 457
    //   27: aload 4
    //   29: ldc 176
    //   31: invokevirtual 180	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   34: astore 7
    //   36: aload 7
    //   38: invokevirtual 186	org/json/JSONArray:length	()I
    //   41: istore 8
    //   43: aload_3
    //   44: invokevirtual 190	com/kaixin001/model/ObjCommentModel:getCommentList	()Ljava/util/ArrayList;
    //   47: astore 9
    //   49: iload 8
    //   51: ifle +79 -> 130
    //   54: aload 7
    //   56: iconst_0
    //   57: invokevirtual 194	org/json/JSONArray:get	(I)Ljava/lang/Object;
    //   60: checkcast 105	org/json/JSONObject
    //   63: astore 10
    //   65: iconst_0
    //   66: istore 11
    //   68: goto +430 -> 498
    //   71: new 196	com/kaixin001/item/ObjCommentItem
    //   74: dup
    //   75: invokespecial 197	com/kaixin001/item/ObjCommentItem:<init>	()V
    //   78: astore 12
    //   80: aload 12
    //   82: iconst_m1
    //   83: invokevirtual 201	com/kaixin001/item/ObjCommentItem:setMainThread	(I)V
    //   86: aload 12
    //   88: aload 10
    //   90: ldc 203
    //   92: invokevirtual 206	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   95: invokevirtual 210	com/kaixin001/item/ObjCommentItem:setThread_cid	(Ljava/lang/String;)V
    //   98: aload 12
    //   100: aload 10
    //   102: ldc 129
    //   104: invokevirtual 206	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   107: invokevirtual 213	com/kaixin001/item/ObjCommentItem:setFuid	(Ljava/lang/String;)V
    //   110: aload 12
    //   112: aload 10
    //   114: ldc 215
    //   116: invokevirtual 206	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   119: invokevirtual 218	com/kaixin001/item/ObjCommentItem:setType	(Ljava/lang/String;)V
    //   122: aload 9
    //   124: aload 12
    //   126: invokevirtual 224	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   129: pop
    //   130: aload 4
    //   132: ldc 226
    //   134: invokevirtual 206	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   137: astore 15
    //   139: aload 15
    //   141: invokestatic 61	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   144: ifne +12 -> 156
    //   147: aload_3
    //   148: aload 15
    //   150: invokestatic 232	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   153: invokevirtual 235	com/kaixin001/model/ObjCommentModel:setUpCount	(I)V
    //   156: aload_3
    //   157: aload 4
    //   159: ldc 237
    //   161: invokevirtual 206	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   164: putfield 240	com/kaixin001/model/ObjCommentModel:likeStr	Ljava/lang/String;
    //   167: aload 4
    //   169: ldc 242
    //   171: invokevirtual 206	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   174: astore 16
    //   176: aload 16
    //   178: invokestatic 61	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   181: ifne +18 -> 199
    //   184: aload 16
    //   186: ldc 244
    //   188: invokevirtual 249	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   191: ifeq +289 -> 480
    //   194: aload_3
    //   195: iconst_1
    //   196: invokevirtual 253	com/kaixin001/model/ObjCommentModel:setSelfUp	(Z)V
    //   199: aload_3
    //   200: aload 4
    //   202: ldc 255
    //   204: invokevirtual 180	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   207: invokevirtual 259	com/kaixin001/model/ObjCommentModel:setUpList	(Lorg/json/JSONArray;)V
    //   210: iconst_1
    //   211: ireturn
    //   212: aload 7
    //   214: iload 11
    //   216: invokevirtual 194	org/json/JSONArray:get	(I)Ljava/lang/Object;
    //   219: checkcast 105	org/json/JSONObject
    //   222: astore 17
    //   224: new 196	com/kaixin001/item/ObjCommentItem
    //   227: dup
    //   228: invokespecial 197	com/kaixin001/item/ObjCommentItem:<init>	()V
    //   231: astore 18
    //   233: aload 18
    //   235: aload 17
    //   237: ldc_w 261
    //   240: invokevirtual 206	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   243: invokevirtual 264	com/kaixin001/item/ObjCommentItem:setContent	(Ljava/lang/String;)V
    //   246: aload 18
    //   248: aload 17
    //   250: ldc 203
    //   252: invokevirtual 206	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   255: invokevirtual 210	com/kaixin001/item/ObjCommentItem:setThread_cid	(Ljava/lang/String;)V
    //   258: aload 18
    //   260: aload 17
    //   262: ldc 139
    //   264: invokevirtual 206	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   267: invokevirtual 267	com/kaixin001/item/ObjCommentItem:setFlogo	(Ljava/lang/String;)V
    //   270: aload 18
    //   272: aload 17
    //   274: ldc 134
    //   276: invokevirtual 206	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   279: invokevirtual 270	com/kaixin001/item/ObjCommentItem:setFname	(Ljava/lang/String;)V
    //   282: aload 18
    //   284: aload 17
    //   286: ldc 129
    //   288: invokevirtual 206	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   291: invokevirtual 213	com/kaixin001/item/ObjCommentItem:setFuid	(Ljava/lang/String;)V
    //   294: aload 18
    //   296: aload 17
    //   298: ldc_w 272
    //   301: invokevirtual 275	org/json/JSONObject:getInt	(Ljava/lang/String;)I
    //   304: invokevirtual 201	com/kaixin001/item/ObjCommentItem:setMainThread	(I)V
    //   307: aload 18
    //   309: aload 17
    //   311: ldc_w 277
    //   314: invokevirtual 206	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   317: invokevirtual 280	com/kaixin001/item/ObjCommentItem:setStime	(Ljava/lang/String;)V
    //   320: aload 18
    //   322: aload 17
    //   324: ldc 215
    //   326: invokevirtual 206	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   329: invokevirtual 218	com/kaixin001/item/ObjCommentItem:setType	(Ljava/lang/String;)V
    //   332: aload 17
    //   334: ldc_w 272
    //   337: invokevirtual 275	org/json/JSONObject:getInt	(Ljava/lang/String;)I
    //   340: iconst_1
    //   341: if_icmpne +118 -> 459
    //   344: iload 11
    //   346: ifle +113 -> 459
    //   349: new 196	com/kaixin001/item/ObjCommentItem
    //   352: dup
    //   353: invokespecial 197	com/kaixin001/item/ObjCommentItem:<init>	()V
    //   356: astore 19
    //   358: aload 19
    //   360: iconst_m1
    //   361: invokevirtual 201	com/kaixin001/item/ObjCommentItem:setMainThread	(I)V
    //   364: aload 19
    //   366: aload 10
    //   368: ldc 203
    //   370: invokevirtual 206	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   373: invokevirtual 210	com/kaixin001/item/ObjCommentItem:setThread_cid	(Ljava/lang/String;)V
    //   376: aload 19
    //   378: aload 10
    //   380: ldc 129
    //   382: invokevirtual 206	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   385: invokevirtual 213	com/kaixin001/item/ObjCommentItem:setFuid	(Ljava/lang/String;)V
    //   388: aload 19
    //   390: aload 10
    //   392: ldc 215
    //   394: invokevirtual 206	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   397: invokevirtual 218	com/kaixin001/item/ObjCommentItem:setType	(Ljava/lang/String;)V
    //   400: aload 9
    //   402: aload 19
    //   404: invokevirtual 224	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   407: pop
    //   408: aload 9
    //   410: aload 18
    //   412: invokevirtual 224	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   415: pop
    //   416: aload 17
    //   418: astore 10
    //   420: goto +95 -> 515
    //   423: astore 20
    //   425: aload 20
    //   427: invokevirtual 283	org/json/JSONException:printStackTrace	()V
    //   430: goto -30 -> 400
    //   433: astore 5
    //   435: ldc 8
    //   437: ldc_w 284
    //   440: aload 5
    //   442: invokestatic 72	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   445: aload_0
    //   446: aload 4
    //   448: ldc_w 286
    //   451: invokevirtual 206	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   454: putfield 17	com/kaixin001/engine/ObjCommentEngine:mError	Ljava/lang/String;
    //   457: iconst_0
    //   458: ireturn
    //   459: aload 9
    //   461: aload 18
    //   463: invokevirtual 224	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   466: pop
    //   467: goto +48 -> 515
    //   470: astore 13
    //   472: aload 13
    //   474: invokevirtual 287	java/lang/Exception:printStackTrace	()V
    //   477: goto -355 -> 122
    //   480: aload_3
    //   481: iconst_0
    //   482: invokevirtual 253	com/kaixin001/model/ObjCommentModel:setSelfUp	(Z)V
    //   485: goto -286 -> 199
    //   488: astore 6
    //   490: aload 6
    //   492: invokevirtual 283	org/json/JSONException:printStackTrace	()V
    //   495: goto -38 -> 457
    //   498: iload 11
    //   500: iload 8
    //   502: if_icmpge -431 -> 71
    //   505: iload 11
    //   507: bipush 100
    //   509: if_icmplt -297 -> 212
    //   512: goto -441 -> 71
    //   515: iinc 11 1
    //   518: goto -20 -> 498
    //
    // Exception table:
    //   from	to	target	type
    //   358	400	423	org/json/JSONException
    //   27	49	433	java/lang/Exception
    //   54	65	433	java/lang/Exception
    //   71	80	433	java/lang/Exception
    //   122	130	433	java/lang/Exception
    //   130	156	433	java/lang/Exception
    //   156	199	433	java/lang/Exception
    //   199	210	433	java/lang/Exception
    //   212	344	433	java/lang/Exception
    //   349	358	433	java/lang/Exception
    //   358	400	433	java/lang/Exception
    //   400	416	433	java/lang/Exception
    //   425	430	433	java/lang/Exception
    //   459	467	433	java/lang/Exception
    //   472	477	433	java/lang/Exception
    //   480	485	433	java/lang/Exception
    //   80	122	470	java/lang/Exception
    //   445	457	488	org/json/JSONException
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.ObjCommentEngine
 * JD-Core Version:    0.6.0
 */