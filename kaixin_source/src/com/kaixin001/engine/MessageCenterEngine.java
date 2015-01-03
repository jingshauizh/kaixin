package com.kaixin001.engine;

import android.content.Context;
import com.kaixin001.model.MessageCenterModel;
import org.json.JSONException;
import org.json.JSONObject;

public class MessageCenterEngine extends KXEngine
{
  private static final String LOGTAG = "MessageCenterEngine";
  private static MessageCenterEngine instance = null;

  public static MessageCenterEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new MessageCenterEngine();
      MessageCenterEngine localMessageCenterEngine = instance;
      return localMessageCenterEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  // ERROR //
  public boolean checkNewMessage(Context paramContext, String paramString)
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnull +10 -> 11
    //   4: aload_2
    //   5: invokestatic 30	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   8: ifeq +5 -> 13
    //   11: iconst_0
    //   12: ireturn
    //   13: invokestatic 35	com/kaixin001/network/Protocol:getInstance	()Lcom/kaixin001/network/Protocol;
    //   16: aload_2
    //   17: invokevirtual 39	com/kaixin001/network/Protocol:makeMessageCenterRequest	(Ljava/lang/String;)Ljava/lang/String;
    //   20: astore_3
    //   21: new 41	com/kaixin001/network/HttpProxy
    //   24: dup
    //   25: aload_1
    //   26: invokespecial 44	com/kaixin001/network/HttpProxy:<init>	(Landroid/content/Context;)V
    //   29: astore 4
    //   31: aload 4
    //   33: aload_3
    //   34: aconst_null
    //   35: aconst_null
    //   36: invokevirtual 48	com/kaixin001/network/HttpProxy:httpGet	(Ljava/lang/String;Lcom/kaixin001/network/HttpRequestState;Lcom/kaixin001/network/HttpProgressListener;)Ljava/lang/String;
    //   39: astore 12
    //   41: aload 12
    //   43: astore 6
    //   45: aload_0
    //   46: aload_1
    //   47: iconst_0
    //   48: aload 6
    //   50: invokespecial 52	com/kaixin001/engine/KXEngine:parseJSON	(Landroid/content/Context;ZLjava/lang/String;)Lorg/json/JSONObject;
    //   53: astore 8
    //   55: aload 8
    //   57: ldc 54
    //   59: invokevirtual 60	org/json/JSONObject:optJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   62: astore 9
    //   64: aload 9
    //   66: ifnull +65 -> 131
    //   69: invokestatic 65	com/kaixin001/model/MessageCenterModel:getInstance	()Lcom/kaixin001/model/MessageCenterModel;
    //   72: aload 9
    //   74: invokevirtual 69	com/kaixin001/model/MessageCenterModel:setNoticeArray	(Lorg/json/JSONArray;)V
    //   77: aload 8
    //   79: ldc 71
    //   81: iconst_0
    //   82: invokevirtual 75	org/json/JSONObject:optInt	(Ljava/lang/String;I)I
    //   85: istore 10
    //   87: invokestatic 65	com/kaixin001/model/MessageCenterModel:getInstance	()Lcom/kaixin001/model/MessageCenterModel;
    //   90: invokevirtual 79	com/kaixin001/model/MessageCenterModel:getTotalNoticeCnt	()I
    //   93: istore 11
    //   95: iload 11
    //   97: ifle +34 -> 131
    //   100: iload 10
    //   102: ifeq +29 -> 131
    //   105: aload_0
    //   106: aload_1
    //   107: iload 11
    //   109: invokevirtual 83	com/kaixin001/engine/MessageCenterEngine:sendNewMsgNotificationBroadcast	(Landroid/content/Context;I)V
    //   112: iconst_1
    //   113: ireturn
    //   114: astore 5
    //   116: ldc 8
    //   118: ldc 85
    //   120: aload 5
    //   122: invokestatic 91	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   125: aconst_null
    //   126: astore 6
    //   128: goto -83 -> 45
    //   131: iconst_0
    //   132: ireturn
    //   133: astore 7
    //   135: aload 7
    //   137: invokevirtual 94	java/lang/Exception:printStackTrace	()V
    //   140: iconst_0
    //   141: ireturn
    //
    // Exception table:
    //   from	to	target	type
    //   31	41	114	java/lang/Exception
    //   45	64	133	java/lang/Exception
    //   69	95	133	java/lang/Exception
    //   105	112	133	java/lang/Exception
  }

  public boolean parseMessageCenterJSON(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    JSONObject localJSONObject = super.parseJSON(paramContext, paramString);
    if (localJSONObject == null)
      return false;
    try
    {
      MessageCenterModel.getInstance().setNoticeArray(localJSONObject.getJSONArray("notices"));
      return true;
    }
    catch (JSONException localJSONException)
    {
      while (true)
        localJSONException.printStackTrace();
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.MessageCenterEngine
 * JD-Core Version:    0.6.0
 */