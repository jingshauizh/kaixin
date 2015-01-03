package com.kaixin001.engine;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.kaixin001.model.NewsModel;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.KXUBLog;
import org.json.JSONObject;

public class UpdateEngine extends KXEngine
  implements Runnable
{
  public static final int CHECK_HAS_NEW_NEWS_DONE = 8002;
  public static final int EVENT_REFRESH_DONE = 8007;
  public static final int LOAD_DONE = 8006;
  public static final int LOAD_FRIEND_DONE = 8004;
  public static final int LOAD_HOME_DONE = 8003;
  public static final int LOAD_NEWS_DONE = 8005;
  private static final String LOGTAG = "UpdateEngine";
  public static final String NEED_UPDATE = "update";
  private static UpdateEngine instance;
  private boolean bFriendLoaded = false;
  private boolean bHomeLoaded = false;
  private boolean bUpdated = false;
  private boolean bUpdating = false;
  private Context ctx = null;
  private Handler mKXDeskTopHandler;
  private Thread mThread;

  public static UpdateEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new UpdateEngine();
      UpdateEngine localUpdateEngine = instance;
      return localUpdateEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private void sendEventRefreshDone()
  {
    this.bUpdating = false;
    if (this.mKXDeskTopHandler == null)
      return;
    Message localMessage = Message.obtain();
    localMessage.what = 8007;
    this.mKXDeskTopHandler.sendMessage(localMessage);
  }

  private void sendLoadDone()
  {
    if (this.mKXDeskTopHandler == null)
      return;
    Message localMessage = Message.obtain();
    localMessage.what = 8006;
    this.mKXDeskTopHandler.sendMessage(localMessage);
  }

  private void sendLoadFriendDone()
  {
    KXLog.d("LoadFriendCache", "sendLoadFriendDone");
    this.bFriendLoaded = true;
    if (this.mKXDeskTopHandler == null)
      return;
    Message localMessage = Message.obtain();
    localMessage.what = 8004;
    this.mKXDeskTopHandler.sendMessage(localMessage);
  }

  private void sendLoadHomeDone()
  {
    this.bHomeLoaded = true;
    if (this.mKXDeskTopHandler == null)
      return;
    Message localMessage = Message.obtain();
    localMessage.what = 8003;
    this.mKXDeskTopHandler.sendMessage(localMessage);
  }

  private void sendUpdateDone()
  {
    this.bUpdating = false;
    if (this.mKXDeskTopHandler == null)
      return;
    Message localMessage = Message.obtain();
    localMessage.what = 8002;
    this.mKXDeskTopHandler.sendMessage(localMessage);
  }

  // ERROR //
  private void updateEvent(Context paramContext)
  {
    // Byte code:
    //   0: invokestatic 98	com/kaixin001/model/EventModel:getInstance	()Lcom/kaixin001/model/EventModel;
    //   3: astore_2
    //   4: aload_1
    //   5: ifnonnull +4 -> 9
    //   8: return
    //   9: invokestatic 103	com/kaixin001/model/User:getInstance	()Lcom/kaixin001/model/User;
    //   12: astore_3
    //   13: invokestatic 108	com/kaixin001/network/Protocol:getInstance	()Lcom/kaixin001/network/Protocol;
    //   16: aload_3
    //   17: invokevirtual 112	com/kaixin001/model/User:getUID	()Ljava/lang/String;
    //   20: invokevirtual 116	com/kaixin001/network/Protocol:getAdvertEventUrl	(Ljava/lang/String;)Ljava/lang/String;
    //   23: astore 4
    //   25: new 118	com/kaixin001/network/HttpProxy
    //   28: dup
    //   29: aload_1
    //   30: invokespecial 120	com/kaixin001/network/HttpProxy:<init>	(Landroid/content/Context;)V
    //   33: astore 5
    //   35: aload 5
    //   37: aload 4
    //   39: aconst_null
    //   40: aconst_null
    //   41: invokevirtual 124	com/kaixin001/network/HttpProxy:httpGet	(Ljava/lang/String;Lcom/kaixin001/network/HttpRequestState;Lcom/kaixin001/network/HttpProgressListener;)Ljava/lang/String;
    //   44: astore 22
    //   46: aload 22
    //   48: astore 7
    //   50: aload 7
    //   52: invokestatic 130	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   55: ifne -47 -> 8
    //   58: aload_2
    //   59: invokevirtual 133	com/kaixin001/model/EventModel:clear	()V
    //   62: aload_0
    //   63: aload_1
    //   64: aload 7
    //   66: invokespecial 137	com/kaixin001/engine/KXEngine:parseJSON	(Landroid/content/Context;Ljava/lang/String;)Lorg/json/JSONObject;
    //   69: astore 9
    //   71: aload 9
    //   73: ifnull +82 -> 155
    //   76: new 139	java/util/ArrayList
    //   79: dup
    //   80: invokespecial 140	java/util/ArrayList:<init>	()V
    //   83: astore 10
    //   85: aload 9
    //   87: ldc 142
    //   89: invokevirtual 148	org/json/JSONObject:optJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
    //   92: astore 11
    //   94: aload 11
    //   96: ifnull +11 -> 107
    //   99: aload 10
    //   101: aload 11
    //   103: invokevirtual 152	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   106: pop
    //   107: aload 9
    //   109: ldc 154
    //   111: invokevirtual 158	org/json/JSONObject:optJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   114: astore 13
    //   116: aload 13
    //   118: ifnull +16 -> 134
    //   121: iconst_0
    //   122: istore 20
    //   124: iload 20
    //   126: aload 13
    //   128: invokevirtual 164	org/json/JSONArray:length	()I
    //   131: if_icmplt +46 -> 177
    //   134: aload 10
    //   136: invokevirtual 168	java/util/ArrayList:iterator	()Ljava/util/Iterator;
    //   139: astore 14
    //   141: aload 14
    //   143: invokeinterface 174 1 0
    //   148: istore 15
    //   150: iload 15
    //   152: ifne +47 -> 199
    //   155: aload_0
    //   156: invokespecial 176	com/kaixin001/engine/UpdateEngine:sendEventRefreshDone	()V
    //   159: return
    //   160: astore 6
    //   162: ldc 23
    //   164: ldc 178
    //   166: aload 6
    //   168: invokestatic 182	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   171: aconst_null
    //   172: astore 7
    //   174: goto -124 -> 50
    //   177: aload 10
    //   179: aload 13
    //   181: iload 20
    //   183: invokevirtual 186	org/json/JSONArray:get	(I)Ljava/lang/Object;
    //   186: checkcast 144	org/json/JSONObject
    //   189: invokevirtual 152	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   192: pop
    //   193: iinc 20 1
    //   196: goto -72 -> 124
    //   199: aload 14
    //   201: invokeinterface 190 1 0
    //   206: checkcast 144	org/json/JSONObject
    //   209: astore 16
    //   211: aload 16
    //   213: ifnull -72 -> 141
    //   216: new 192	com/kaixin001/model/EventModel$EventData
    //   219: dup
    //   220: invokespecial 193	com/kaixin001/model/EventModel$EventData:<init>	()V
    //   223: astore 17
    //   225: aload 17
    //   227: aload 16
    //   229: ldc 195
    //   231: invokevirtual 198	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   234: invokevirtual 202	com/kaixin001/model/EventModel$EventData:setId	(Ljava/lang/String;)V
    //   237: aload 17
    //   239: aload 16
    //   241: ldc 204
    //   243: invokevirtual 198	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   246: invokevirtual 207	com/kaixin001/model/EventModel$EventData:setTitle	(Ljava/lang/String;)V
    //   249: aload 17
    //   251: aload 16
    //   253: ldc 209
    //   255: invokevirtual 198	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   258: invokevirtual 212	com/kaixin001/model/EventModel$EventData:setUrl	(Ljava/lang/String;)V
    //   261: aload 17
    //   263: aload 16
    //   265: ldc 214
    //   267: invokevirtual 198	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   270: invokevirtual 217	com/kaixin001/model/EventModel$EventData:setCTime	(Ljava/lang/String;)V
    //   273: aload 17
    //   275: aload 16
    //   277: ldc 219
    //   279: iconst_0
    //   280: invokevirtual 223	org/json/JSONObject:optInt	(Ljava/lang/String;I)I
    //   283: invokevirtual 227	com/kaixin001/model/EventModel$EventData:setEventType	(I)V
    //   286: aload 17
    //   288: aload 16
    //   290: ldc 229
    //   292: invokevirtual 198	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   295: invokevirtual 232	com/kaixin001/model/EventModel$EventData:setActionTitle	(Ljava/lang/String;)V
    //   298: aload 17
    //   300: aload 16
    //   302: ldc 234
    //   304: invokevirtual 198	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   307: invokevirtual 237	com/kaixin001/model/EventModel$EventData:setOptionTitle	(Ljava/lang/String;)V
    //   310: aload 16
    //   312: ldc 239
    //   314: iconst_0
    //   315: invokevirtual 223	org/json/JSONObject:optInt	(Ljava/lang/String;I)I
    //   318: iconst_1
    //   319: if_icmpne +48 -> 367
    //   322: iconst_1
    //   323: istore 18
    //   325: aload 17
    //   327: iload 18
    //   329: invokevirtual 243	com/kaixin001/model/EventModel$EventData:setAlwaysShow	(Z)V
    //   332: aload 17
    //   334: aload 16
    //   336: ldc 245
    //   338: invokevirtual 198	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   341: invokevirtual 248	com/kaixin001/model/EventModel$EventData:setKeyWord	(Ljava/lang/String;)V
    //   344: aload_2
    //   345: getfield 252	com/kaixin001/model/EventModel:mList	Ljava/util/ArrayList;
    //   348: aload 17
    //   350: invokevirtual 152	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   353: pop
    //   354: goto -213 -> 141
    //   357: astore 8
    //   359: aload 8
    //   361: invokevirtual 255	java/lang/Exception:printStackTrace	()V
    //   364: goto -209 -> 155
    //   367: iconst_0
    //   368: istore 18
    //   370: goto -45 -> 325
    //
    // Exception table:
    //   from	to	target	type
    //   35	46	160	java/lang/Exception
    //   62	71	357	java/lang/Exception
    //   76	94	357	java/lang/Exception
    //   99	107	357	java/lang/Exception
    //   107	116	357	java/lang/Exception
    //   124	134	357	java/lang/Exception
    //   134	141	357	java/lang/Exception
    //   141	150	357	java/lang/Exception
    //   177	193	357	java/lang/Exception
    //   199	211	357	java/lang/Exception
    //   216	322	357	java/lang/Exception
    //   325	354	357	java/lang/Exception
  }

  private boolean updateNews(Context paramContext)
    throws SecurityErrorException
  {
    String str1 = User.getInstance().getUID();
    if (!this.bUpdating);
    JSONObject localJSONObject;
    while (true)
    {
      return false;
      KXLog.d("LoadFriendCache", "bFriendLoaded=" + this.bFriendLoaded);
      if (!this.bFriendLoaded)
      {
        FriendsEngine.getInstance().loadFriendsCache(paramContext, str1);
        sendLoadFriendDone();
      }
      if (!this.bUpdating)
        continue;
      if (!this.bHomeLoaded)
      {
        NewsEngine.getInstance().loadHomeDataCache(paramContext, str1);
        sendLoadHomeDone();
      }
      if (!this.bUpdating)
        continue;
      String str2 = Protocol.getInstance().getNewsCountUrl(str1, NewsModel.getInstance().getctime());
      HttpProxy localHttpProxy = new HttpProxy(paramContext);
      try
      {
        String str4 = localHttpProxy.httpGet(str2, null, null);
        str3 = str4;
        if (TextUtils.isEmpty(str3))
          continue;
        localJSONObject = super.parseJSON(paramContext, str3);
        if (localJSONObject == null)
        {
          sendLoadDone();
          return false;
        }
      }
      catch (Exception localException)
      {
        while (true)
        {
          KXLog.e("UpdateEngine", "updateNews error", localException);
          String str3 = null;
        }
      }
    }
    boolean bool;
    if (1 == localJSONObject.optInt("hasnew", 0))
    {
      bool = true;
      NewsModel.setHasNew(bool);
      if (localJSONObject.optInt("hasnew", 0) == 0)
        break label280;
      NewsModel.getInstance().setNewsCount(localJSONObject.optInt("newscount", 0));
    }
    while (true)
    {
      NewsModel.getInstance().setPublicMore(localJSONObject.optInt("publicMore", 0));
      sendUpdateDone();
      updateEvent(paramContext);
      this.bUpdated = true;
      if (KXUBEngine.getInstance().uploadUBLog(paramContext, KXUBLog.getAllLog()))
        KXUBLog.clearLog();
      return true;
      bool = false;
      break;
      label280: NewsModel.getInstance().setNewsCount(0);
    }
  }

  public void cancel()
  {
    super.cancel();
    this.bUpdating = false;
    try
    {
      if ((this.mThread != null) && (this.mThread.isAlive()))
        this.mThread.interrupt();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void clear()
  {
    this.bUpdated = false;
  }

  public void clearContext()
  {
    this.ctx = null;
  }

  public boolean getFaceList(Context paramContext)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeFaceRequest(User.getInstance().getUID());
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    String str2;
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (TextUtils.isEmpty(str2))
        return false;
    }
    catch (Exception localException)
    {
      while (true)
      {
        KXLog.e("UpdateEngine", "getFaceList error", localException);
        str2 = null;
      }
    }
    return parseFaceListJSON(paramContext, str2);
  }

  public boolean isFriendLoaded()
  {
    return this.bFriendLoaded;
  }

  public boolean isHomeLoaded()
  {
    return this.bHomeLoaded;
  }

  public boolean isUpdated()
  {
    return this.bUpdated;
  }

  public boolean isUpdating()
  {
    return this.bUpdating;
  }

  public boolean parseFaceListJSON(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    if (super.parseJSON(paramContext, paramString) == null)
      return false;
    if (this.ret == 1)
      return true;
    KXLog.e("UpdateFaceEngine failed", paramString);
    return false;
  }

  public void run()
  {
    try
    {
      this.bUpdating = true;
      updateNews(this.ctx);
      return;
    }
    catch (Exception localException)
    {
      KXLog.e("UpdateEngine", "run", localException);
      return;
    }
    finally
    {
      sendLoadDone();
    }
    throw localObject;
  }

  public void setContext(Context paramContext)
  {
    this.ctx = paramContext;
  }

  public void setFriendLoaded(boolean paramBoolean)
  {
    this.bFriendLoaded = paramBoolean;
  }

  public void setKXDesktopHandler(Handler paramHandler)
  {
    this.mKXDeskTopHandler = paramHandler;
  }

  public void updateAsync()
  {
    if ((!this.bUpdated) && ((this.mThread == null) || (!this.mThread.isAlive())))
    {
      KXLog.d("LoadFriendCache", "start thread");
      this.mThread = new Thread(this);
      this.mThread.start();
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.UpdateEngine
 * JD-Core Version:    0.6.0
 */