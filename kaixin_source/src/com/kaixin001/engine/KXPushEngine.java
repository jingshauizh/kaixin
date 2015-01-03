package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.model.KXCityModel;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import org.json.JSONObject;

public class KXPushEngine extends KXEngine
{
  private static final String TAG = "KXPushEngine";
  private static KXPushEngine instance = null;

  public static KXPushEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new KXPushEngine();
      KXPushEngine localKXPushEngine = instance;
      return localKXPushEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  // ERROR //
  private KXCityModel parseJSON(JSONObject paramJSONObject)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: aload_1
    //   3: ifnull +174 -> 177
    //   6: aload_1
    //   7: ldc 26
    //   9: ldc 28
    //   11: invokevirtual 34	org/json/JSONObject:optString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   14: astore 5
    //   16: aload 5
    //   18: invokestatic 40	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   21: istore 6
    //   23: aconst_null
    //   24: astore_2
    //   25: iload 6
    //   27: ifne +150 -> 177
    //   30: new 42	com/kaixin001/model/KXCityModel
    //   33: dup
    //   34: invokespecial 43	com/kaixin001/model/KXCityModel:<init>	()V
    //   37: astore 7
    //   39: aload 7
    //   41: aload 5
    //   43: invokevirtual 47	com/kaixin001/model/KXCityModel:setTitle	(Ljava/lang/String;)V
    //   46: aload 7
    //   48: aload_1
    //   49: ldc 49
    //   51: ldc 28
    //   53: invokevirtual 34	org/json/JSONObject:optString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   56: invokevirtual 52	com/kaixin001/model/KXCityModel:setLabel	(Ljava/lang/String;)V
    //   59: aload 7
    //   61: aload_1
    //   62: ldc 54
    //   64: ldc 28
    //   66: invokevirtual 34	org/json/JSONObject:optString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   69: invokevirtual 57	com/kaixin001/model/KXCityModel:setUrl	(Ljava/lang/String;)V
    //   72: aload 7
    //   74: aload_1
    //   75: ldc 59
    //   77: ldc 28
    //   79: invokevirtual 34	org/json/JSONObject:optString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   82: invokevirtual 62	com/kaixin001/model/KXCityModel:setType	(Ljava/lang/String;)V
    //   85: aload 7
    //   87: aload_1
    //   88: ldc 64
    //   90: ldc 28
    //   92: invokevirtual 34	org/json/JSONObject:optString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   95: invokevirtual 67	com/kaixin001/model/KXCityModel:setUserType	(Ljava/lang/String;)V
    //   98: aload 7
    //   100: aload_1
    //   101: ldc 69
    //   103: ldc 28
    //   105: invokevirtual 34	org/json/JSONObject:optString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   108: invokevirtual 72	com/kaixin001/model/KXCityModel:setFname	(Ljava/lang/String;)V
    //   111: aload 7
    //   113: aload_1
    //   114: ldc 74
    //   116: ldc 28
    //   118: invokevirtual 34	org/json/JSONObject:optString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   121: invokevirtual 77	com/kaixin001/model/KXCityModel:setFuid	(Ljava/lang/String;)V
    //   124: aload 7
    //   126: aload_1
    //   127: ldc 79
    //   129: ldc 28
    //   131: invokevirtual 34	org/json/JSONObject:optString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   134: invokevirtual 82	com/kaixin001/model/KXCityModel:setRid	(Ljava/lang/String;)V
    //   137: aload 7
    //   139: aload_1
    //   140: ldc 84
    //   142: ldc 86
    //   144: invokevirtual 34	org/json/JSONObject:optString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   147: invokevirtual 89	com/kaixin001/model/KXCityModel:setCommentFlag	(Ljava/lang/String;)V
    //   150: aload 7
    //   152: aload_1
    //   153: ldc 91
    //   155: ldc 28
    //   157: invokevirtual 34	org/json/JSONObject:optString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   160: invokevirtual 94	com/kaixin001/model/KXCityModel:setStatKey	(Ljava/lang/String;)V
    //   163: aload_1
    //   164: ldc 96
    //   166: ldc 28
    //   168: invokevirtual 34	org/json/JSONObject:optString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   171: putstatic 99	com/kaixin001/model/KXCityModel:mTopic	Ljava/lang/String;
    //   174: aload 7
    //   176: astore_2
    //   177: aload_2
    //   178: areturn
    //   179: astore_3
    //   180: aload_3
    //   181: invokevirtual 102	java/lang/Exception:printStackTrace	()V
    //   184: iconst_1
    //   185: anewarray 104	java/lang/Object
    //   188: astore 4
    //   190: aload 4
    //   192: iconst_0
    //   193: aload_3
    //   194: invokevirtual 108	java/lang/Exception:toString	()Ljava/lang/String;
    //   197: aastore
    //   198: ldc 8
    //   200: ldc 110
    //   202: aload 4
    //   204: invokestatic 116	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   207: aload_2
    //   208: areturn
    //   209: astore_3
    //   210: aload 7
    //   212: astore_2
    //   213: goto -33 -> 180
    //
    // Exception table:
    //   from	to	target	type
    //   6	23	179	java/lang/Exception
    //   30	39	179	java/lang/Exception
    //   39	174	209	java/lang/Exception
  }

  public boolean getHoroscopePush(Context paramContext)
  {
    String str1 = Protocol.getInstance().getHoroscopePush();
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    String str2;
    try
    {
      str2 = localHttpProxy.httpGet(str1, null, null);
      if (TextUtils.isEmpty(str2))
        return false;
    }
    catch (Exception localException1)
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = localException1.toString();
      KXLog.e("KXPushEngine", "getHoroscopePush error", arrayOfObject);
      return false;
    }
    try
    {
      JSONObject localJSONObject = super.parseJSON(paramContext, false, str2);
      KXCityModel localKXCityModel = parseJSON(localJSONObject);
      if (localKXCityModel != null)
        sendKXCityNotification(paramContext, localKXCityModel);
      return true;
    }
    catch (Exception localException2)
    {
      localException2.printStackTrace();
    }
    return false;
  }

  public boolean getPushMessage(Context paramContext)
  {
    String str1 = Protocol.getInstance().makeGetKXCityPush();
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      boolean bool = TextUtils.isEmpty(str2);
      localObject = null;
      i = 0;
      if (bool);
    }
    catch (Exception localException1)
    {
      try
      {
        JSONObject localJSONObject = super.parseJSON(paramContext, false, str2);
        localObject = localJSONObject;
        i = 1;
        KXCityModel localKXCityModel = parseJSON(localObject);
        if (localKXCityModel != null)
          sendKXCityNotification(paramContext, localKXCityModel);
        return i;
        localException1 = localException1;
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = localException1.toString();
        KXLog.e("KXPushEngine", "getPushMessage error", arrayOfObject);
        String str2 = null;
      }
      catch (Exception localException2)
      {
        while (true)
        {
          localException2.printStackTrace();
          Object localObject = null;
          int i = 0;
        }
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.KXPushEngine
 * JD-Core Version:    0.6.0
 */