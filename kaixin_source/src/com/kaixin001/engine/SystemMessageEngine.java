package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.FileUtil;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

public class SystemMessageEngine extends KXEngine
{
  private static final String FILE_NAME_SYSTEM_MENTIONME_CACHE = "sys_mentionme.kx";
  private static final String FILE_NAME_SYSTEM_NOTICE_CACHE = "sys_notice.kx";
  private static final String FILE_NAME_SYSTEM_REPOST_CACHE = "sys_repost.kx";
  private static final String LOGTAG = "SystemMessageEngine";
  public static final int NUM = 10;
  public static final String TYPE_MENTIONME_RECORD = "2";
  public static final String TYPE_REPOST_RECORD = "3";
  public static final String TYPE_SYSTEM_NOTICE = "1";
  private static SystemMessageEngine instance = null;

  public static SystemMessageEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new SystemMessageEngine();
      SystemMessageEngine localSystemMessageEngine = instance;
      return localSystemMessageEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  // ERROR //
  private boolean parseSystemMessageByTypeJSON(Context paramContext, String paramString1, String paramString2, String paramString3, int paramInt, boolean paramBoolean)
    throws SecurityErrorException
  {
    // Byte code:
    //   0: aload_2
    //   1: invokestatic 55	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   4: ifne +10 -> 14
    //   7: aload_3
    //   8: invokestatic 55	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   11: ifeq +5 -> 16
    //   14: iconst_0
    //   15: ireturn
    //   16: iload 6
    //   18: ifeq +137 -> 155
    //   21: new 57	org/json/JSONObject
    //   24: dup
    //   25: aload_2
    //   26: invokespecial 60	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   29: astore 15
    //   31: aload 15
    //   33: astore 7
    //   35: iconst_0
    //   36: istore 8
    //   38: aload 7
    //   40: ifnull +99 -> 139
    //   43: ldc 62
    //   45: new 64	java/lang/StringBuilder
    //   48: dup
    //   49: ldc 66
    //   51: invokespecial 67	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   54: aload 7
    //   56: invokevirtual 71	org/json/JSONObject:toString	()Ljava/lang/String;
    //   59: invokevirtual 75	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   62: invokevirtual 76	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   65: invokestatic 82	com/kaixin001/util/KXLog:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   68: invokestatic 87	com/kaixin001/model/MessageCenterModel:getInstance	()Lcom/kaixin001/model/MessageCenterModel;
    //   71: astore 10
    //   73: aload_3
    //   74: invokestatic 93	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   77: istore 11
    //   79: aload 7
    //   81: ldc 95
    //   83: invokevirtual 98	org/json/JSONObject:optInt	(Ljava/lang/String;)I
    //   86: istore 12
    //   88: aload 7
    //   90: ldc 100
    //   92: invokevirtual 98	org/json/JSONObject:optInt	(Ljava/lang/String;)I
    //   95: istore 13
    //   97: aload 7
    //   99: ldc 102
    //   101: invokevirtual 106	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   104: astore 14
    //   106: iload 11
    //   108: tableswitch	default:+28 -> 136, 1:+58->166, 2:+117->225, 3:+163->271
    //   137: istore 8
    //   139: iload 8
    //   141: ireturn
    //   142: astore 16
    //   144: aload 16
    //   146: invokevirtual 109	java/lang/Exception:printStackTrace	()V
    //   149: aconst_null
    //   150: astore 7
    //   152: goto -117 -> 35
    //   155: aload_0
    //   156: aload_1
    //   157: aload_2
    //   158: invokespecial 113	com/kaixin001/engine/KXEngine:parseJSON	(Landroid/content/Context;Ljava/lang/String;)Lorg/json/JSONObject;
    //   161: astore 7
    //   163: goto -128 -> 35
    //   166: aload 10
    //   168: iload 12
    //   170: invokevirtual 117	com/kaixin001/model/MessageCenterModel:setReturnNum	(I)V
    //   173: aload 10
    //   175: iload 13
    //   177: invokevirtual 120	com/kaixin001/model/MessageCenterModel:setSystemMessageTotal	(I)V
    //   180: aload 4
    //   182: invokestatic 55	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   185: ifeq +28 -> 213
    //   188: aload 10
    //   190: aload 14
    //   192: iload 5
    //   194: invokevirtual 124	com/kaixin001/model/MessageCenterModel:setSystemMessageList	(Lorg/json/JSONArray;I)V
    //   197: goto -61 -> 136
    //   200: astore 9
    //   202: aload 9
    //   204: invokevirtual 125	org/json/JSONException:printStackTrace	()V
    //   207: iconst_0
    //   208: istore 8
    //   210: goto -71 -> 139
    //   213: aload 10
    //   215: aload 14
    //   217: iload 5
    //   219: invokevirtual 128	com/kaixin001/model/MessageCenterModel:appendSystemMessageList	(Lorg/json/JSONArray;I)V
    //   222: goto -86 -> 136
    //   225: aload 10
    //   227: iload 12
    //   229: invokevirtual 131	com/kaixin001/model/MessageCenterModel:setMentionMeReturnNum	(I)V
    //   232: aload 10
    //   234: iload 13
    //   236: invokevirtual 134	com/kaixin001/model/MessageCenterModel:setMentionMeTotal	(I)V
    //   239: aload 4
    //   241: invokestatic 55	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   244: ifeq +15 -> 259
    //   247: aload 10
    //   249: aload 14
    //   251: iload 5
    //   253: invokevirtual 137	com/kaixin001/model/MessageCenterModel:setSystemMentionMeList	(Lorg/json/JSONArray;I)V
    //   256: goto -120 -> 136
    //   259: aload 10
    //   261: aload 14
    //   263: iload 5
    //   265: invokevirtual 140	com/kaixin001/model/MessageCenterModel:appendMentionMeMessageList	(Lorg/json/JSONArray;I)V
    //   268: goto -132 -> 136
    //   271: aload 10
    //   273: iload 12
    //   275: invokevirtual 143	com/kaixin001/model/MessageCenterModel:setRepostReturnNum	(I)V
    //   278: aload 10
    //   280: iload 13
    //   282: invokevirtual 146	com/kaixin001/model/MessageCenterModel:setRepostTotal	(I)V
    //   285: aload 4
    //   287: invokestatic 55	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   290: ifeq +15 -> 305
    //   293: aload 10
    //   295: aload 14
    //   297: iload 5
    //   299: invokevirtual 149	com/kaixin001/model/MessageCenterModel:setSystemRepostList	(Lorg/json/JSONArray;I)V
    //   302: goto -166 -> 136
    //   305: aload 10
    //   307: aload 14
    //   309: iload 5
    //   311: invokevirtual 152	com/kaixin001/model/MessageCenterModel:appendRepostMessageList	(Lorg/json/JSONArray;I)V
    //   314: goto -178 -> 136
    //
    // Exception table:
    //   from	to	target	type
    //   21	31	142	java/lang/Exception
    //   68	106	200	org/json/JSONException
    //   166	197	200	org/json/JSONException
    //   213	222	200	org/json/JSONException
    //   225	256	200	org/json/JSONException
    //   259	268	200	org/json/JSONException
    //   271	302	200	org/json/JSONException
    //   305	314	200	org/json/JSONException
  }

  public boolean doAcceptFriendRequest(Context paramContext, String paramString1, String paramString2)
  {
    String str1 = Protocol.getInstance().makeAcceptFriendRequest(paramString1, paramString2, User.getInstance().getUID());
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (str2 == null)
        return false;
    }
    catch (Exception localException)
    {
      String str2;
      while (true)
      {
        KXLog.e("SystemMessageEngine", "doAcceptFriendRequest error", localException);
        str2 = null;
      }
      try
      {
        int i = new JSONObject(str2).getInt("ret");
        return i == 1;
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
      }
    }
    return false;
  }

  public boolean doGetSystemMessageByType(Context paramContext, String paramString1, String paramString2)
    throws SecurityErrorException
  {
    if (paramString1 == null)
      paramString1 = "";
    String str1 = User.getInstance().getUID();
    String str2 = Protocol.getInstance().makeGetSystemtMessageByTypeRequest(paramString1, 10, User.getInstance().getUID(), paramString2);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    String str3;
    try
    {
      String str4 = localHttpProxy.httpGet(str2, null, null);
      str3 = str4;
      if (str3 == null)
        return false;
    }
    catch (Exception localException)
    {
      while (true)
      {
        KXLog.e("SystemMessageEngine", "doGetSystemMessageByType error", localException);
        str3 = null;
      }
      if (TextUtils.isEmpty(paramString1))
        setSystemMessageCache(paramContext, str1, paramString2, str3);
    }
    return parseSystemMessageByTypeJSON(paramContext, str3, paramString2, paramString1, 10, false);
  }

  public boolean doGetSystemMessageForCertainNumber(Context paramContext, int paramInt)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeGetSystemtMessageRequest("", paramInt, User.getInstance().getUID());
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    ArrayList localArrayList;
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (str2 == null)
        return false;
    }
    catch (Exception localException)
    {
      String str2;
      while (true)
      {
        KXLog.e("SystemMessageEngine", "doGetSystemMessageForCertainNumber error", localException);
        str2 = null;
      }
      localArrayList = Protocol.getInstance().putSystemMessageData(str2);
    }
    return parseSystemMessageJSON(paramContext, (String)localArrayList.get(0), (String)localArrayList.get(1), (String)localArrayList.get(2));
  }

  public boolean doRefuseFriendRequest(Context paramContext, String paramString1, String paramString2)
  {
    String str1 = Protocol.getInstance().makeRefuseFriendRequest(paramString1, paramString2, User.getInstance().getUID());
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (str2 == null)
        return false;
    }
    catch (Exception localException)
    {
      String str2;
      while (true)
      {
        KXLog.e("SystemMessageEngine", "doRefuseFriendRequest error", localException);
        str2 = null;
      }
      try
      {
        int i = new JSONObject(str2).getInt("ret");
        return i == 1;
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
      }
    }
    return false;
  }

  public boolean loadSystemMessageCache(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    if ((paramContext == null) || (TextUtils.isEmpty(paramString)))
      return false;
    String str1 = FileUtil.getKXCacheDir(paramContext);
    String str2 = FileUtil.getCacheData(str1, paramString, "sys_notice.kx");
    String str3 = FileUtil.getCacheData(str1, paramString, "sys_mentionme.kx");
    String str4 = FileUtil.getCacheData(str1, paramString, "sys_repost.kx");
    if (!TextUtils.isEmpty(str2))
      parseSystemMessageByTypeJSON(paramContext, str2, "1", "", 10, true);
    if (!TextUtils.isEmpty(str2))
      parseSystemMessageByTypeJSON(paramContext, str3, "2", "", 10, true);
    if (!TextUtils.isEmpty(str2))
      parseSystemMessageByTypeJSON(paramContext, str4, "3", "", 10, true);
    return true;
  }

  public boolean parseSystemMessageJSON(Context paramContext, String paramString1, String paramString2, String paramString3)
    throws SecurityErrorException
  {
    int i;
    if ((1 != 0) && (parseSystemMessageByTypeJSON(paramContext, paramString1, "1", "", 10, false)))
    {
      i = 1;
      if ((i == 0) || (!parseSystemMessageByTypeJSON(paramContext, paramString2, "2", "", 10, false)))
        break label77;
    }
    label77: for (int j = 1; ; j = 0)
    {
      if ((j == 0) || (!parseSystemMessageByTypeJSON(paramContext, paramString3, "3", "", 10, false)))
        break label83;
      return true;
      i = 0;
      break;
    }
    label83: return false;
  }

  public boolean setSystemMessageCache(Context paramContext, String paramString1, String paramString2, String paramString3)
  {
    try
    {
      String str1 = FileUtil.getKXCacheDir(paramContext);
      String str2;
      if ("1".equals(paramString2))
        str2 = "sys_notice.kx";
      while (true)
      {
        FileUtil.setCacheData(str1, paramString1, str2, paramString3);
        return true;
        if ("2".equals(paramString2))
        {
          str2 = "sys_mentionme.kx";
          continue;
        }
        if (!"3".equals(paramString2))
          break;
        str2 = "sys_repost.kx";
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.SystemMessageEngine
 * JD-Core Version:    0.6.0
 */