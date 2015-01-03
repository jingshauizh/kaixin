package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.item.AdvertItem;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;

public class NewsAdvertEngine extends KXEngine
{
  private static final String LOGTAG = "NewsAdvertEngine";
  private static NewsAdvertEngine instance;

  public static NewsAdvertEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new NewsAdvertEngine();
      NewsAdvertEngine localNewsAdvertEngine = instance;
      return localNewsAdvertEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public int getAdvert(Context paramContext)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeGetAdvertRequest();
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    String str2;
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (TextUtils.isEmpty(str2))
        return -1002;
    }
    catch (Exception localException)
    {
      while (true)
      {
        KXLog.e("NewsAdvertEngine", "getGiftList error", localException);
        str2 = null;
      }
    }
    return parseAdvertJSON(paramContext, str2);
  }

  // ERROR //
  public int parseAdvertJSON(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: aload_2
    //   3: invokespecial 65	com/kaixin001/engine/KXEngine:parseJSON	(Landroid/content/Context;Ljava/lang/String;)Lorg/json/JSONObject;
    //   6: astore_3
    //   7: aload_3
    //   8: ifnonnull +7 -> 15
    //   11: sipush -1001
    //   14: ireturn
    //   15: invokestatic 70	com/kaixin001/model/Setting:getInstance	()Lcom/kaixin001/model/Setting;
    //   18: invokevirtual 74	com/kaixin001/model/Setting:isDebug	()Z
    //   21: ifeq +27 -> 48
    //   24: ldc 75
    //   26: new 77	java/lang/StringBuilder
    //   29: dup
    //   30: ldc 79
    //   32: invokespecial 82	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   35: aload_3
    //   36: invokevirtual 87	org/json/JSONObject:toString	()Ljava/lang/String;
    //   39: invokevirtual 91	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   42: invokevirtual 92	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   45: invokestatic 96	com/kaixin001/util/KXLog:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   48: invokestatic 101	com/kaixin001/model/AdvertModel:getInstance	()Lcom/kaixin001/model/AdvertModel;
    //   51: astore 5
    //   53: aload_0
    //   54: aload_3
    //   55: ldc 103
    //   57: iconst_0
    //   58: invokevirtual 107	org/json/JSONObject:optInt	(Ljava/lang/String;I)I
    //   61: putfield 110	com/kaixin001/engine/NewsAdvertEngine:ret	I
    //   64: aload_0
    //   65: getfield 110	com/kaixin001/engine/NewsAdvertEngine:ret	I
    //   68: iconst_1
    //   69: if_icmpne +294 -> 363
    //   72: aload 5
    //   74: aload_3
    //   75: ldc 112
    //   77: iconst_0
    //   78: invokevirtual 107	org/json/JSONObject:optInt	(Ljava/lang/String;I)I
    //   81: putfield 114	com/kaixin001/model/AdvertModel:uid	I
    //   84: aload 5
    //   86: aload_3
    //   87: ldc 116
    //   89: iconst_0
    //   90: invokevirtual 107	org/json/JSONObject:optInt	(Ljava/lang/String;I)I
    //   93: putfield 118	com/kaixin001/model/AdvertModel:notice	I
    //   96: aload 5
    //   98: aload_3
    //   99: ldc 120
    //   101: iconst_0
    //   102: invokevirtual 107	org/json/JSONObject:optInt	(Ljava/lang/String;I)I
    //   105: putfield 122	com/kaixin001/model/AdvertModel:ctime	I
    //   108: aload_3
    //   109: ldc 124
    //   111: invokevirtual 128	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   114: astore 6
    //   116: aload 6
    //   118: ifnonnull +38 -> 156
    //   121: iconst_0
    //   122: istore 7
    //   124: iload 7
    //   126: ifle +214 -> 340
    //   129: iload 7
    //   131: anewarray 130	com/kaixin001/item/AdvertItem
    //   134: astore 8
    //   136: iconst_0
    //   137: istore 9
    //   139: iload 9
    //   141: iload 7
    //   143: if_icmplt +23 -> 166
    //   146: aload 5
    //   148: aload 8
    //   150: putfield 134	com/kaixin001/model/AdvertModel:item	[Lcom/kaixin001/item/AdvertItem;
    //   153: goto +223 -> 376
    //   156: aload 6
    //   158: invokevirtual 140	org/json/JSONArray:length	()I
    //   161: istore 7
    //   163: goto -39 -> 124
    //   166: aload 6
    //   168: iload 9
    //   170: invokevirtual 144	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
    //   173: astore 10
    //   175: aload 10
    //   177: ldc 146
    //   179: iconst_0
    //   180: invokevirtual 107	org/json/JSONObject:optInt	(Ljava/lang/String;I)I
    //   183: istore 11
    //   185: aload 10
    //   187: ldc 148
    //   189: invokevirtual 152	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   192: astore 12
    //   194: aload 10
    //   196: ldc 154
    //   198: invokevirtual 158	org/json/JSONObject:optJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
    //   201: astore 13
    //   203: aconst_null
    //   204: astore 14
    //   206: aconst_null
    //   207: astore 15
    //   209: aconst_null
    //   210: astore 16
    //   212: aconst_null
    //   213: astore 17
    //   215: aload 13
    //   217: ifnull +47 -> 264
    //   220: aload 13
    //   222: ldc 160
    //   224: invokevirtual 152	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   227: astore 14
    //   229: aload 13
    //   231: ldc 162
    //   233: invokevirtual 152	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   236: astore 15
    //   238: aload 13
    //   240: ldc 146
    //   242: ldc 164
    //   244: invokevirtual 167	org/json/JSONObject:optString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   247: astore 16
    //   249: aload 13
    //   251: ldc 169
    //   253: ldc 164
    //   255: invokevirtual 167	org/json/JSONObject:optString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   258: astore 18
    //   260: aload 18
    //   262: astore 17
    //   264: aload 10
    //   266: ldc 171
    //   268: invokevirtual 173	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
    //   271: astore 23
    //   273: aload 23
    //   275: astore 20
    //   277: aconst_null
    //   278: astore 21
    //   280: aconst_null
    //   281: astore 22
    //   283: aload 20
    //   285: ifnull +21 -> 306
    //   288: aload 20
    //   290: ldc 169
    //   292: invokevirtual 152	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   295: astore 21
    //   297: aload 20
    //   299: ldc 175
    //   301: invokevirtual 152	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   304: astore 22
    //   306: aload 8
    //   308: iload 9
    //   310: new 130	com/kaixin001/item/AdvertItem
    //   313: dup
    //   314: iload 11
    //   316: aload 12
    //   318: aload 14
    //   320: aload 15
    //   322: aload 21
    //   324: aload 22
    //   326: aload 16
    //   328: aload 17
    //   330: invokespecial 178	com/kaixin001/item/AdvertItem:<init>	(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   333: aastore
    //   334: iinc 9 1
    //   337: goto -198 -> 139
    //   340: aload 5
    //   342: invokevirtual 181	com/kaixin001/model/AdvertModel:clear	()V
    //   345: goto +31 -> 376
    //   348: astore 4
    //   350: ldc 183
    //   352: ldc 183
    //   354: aload 4
    //   356: invokestatic 57	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   359: sipush -1001
    //   362: ireturn
    //   363: aload_0
    //   364: getfield 110	com/kaixin001/engine/NewsAdvertEngine:ret	I
    //   367: ireturn
    //   368: astore 19
    //   370: aconst_null
    //   371: astore 20
    //   373: goto -96 -> 277
    //   376: iconst_1
    //   377: ireturn
    //
    // Exception table:
    //   from	to	target	type
    //   48	116	348	java/lang/Exception
    //   129	136	348	java/lang/Exception
    //   146	153	348	java/lang/Exception
    //   156	163	348	java/lang/Exception
    //   166	203	348	java/lang/Exception
    //   220	260	348	java/lang/Exception
    //   288	306	348	java/lang/Exception
    //   306	334	348	java/lang/Exception
    //   340	345	348	java/lang/Exception
    //   264	273	368	java/lang/Exception
  }

  public void sendAdvertClose(Context paramContext)
  {
    int i = com.kaixin001.model.AdvertModel.getInstance().item[0].id;
    String str = Protocol.getInstance().makeAdvertCloseRequest(String.valueOf(i));
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      localHttpProxy.httpGet(str, null, null);
      return;
    }
    catch (Exception localException)
    {
      KXLog.e("NewsAdvertEngine", "getGiftList error", localException);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.NewsAdvertEngine
 * JD-Core Version:    0.6.0
 */