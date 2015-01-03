package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.model.Setting;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import org.json.JSONObject;

public class NewFriendEngine extends KXEngine
{
  private static final String LOGTAG = "NewFriendEngine";
  private static NewFriendEngine instance;
  private String strTipMsg;

  public static NewFriendEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new NewFriendEngine();
      NewFriendEngine localNewFriendEngine = instance;
      return localNewFriendEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public boolean addNewFriend(Context paramContext, String paramString1, String paramString2)
    throws SecurityErrorException
  {
    this.strTipMsg = null;
    String str1 = Protocol.getInstance().makeNewFriendRequest(User.getInstance().getUID(), paramString1, paramString2);
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
        KXLog.e("NewFriendEngine", "addNewFriend error", localException);
        str2 = null;
      }
    }
    return parseNewFriendJSON(paramContext, str2);
  }

  public String getTipMsg()
  {
    return this.strTipMsg;
  }

  public boolean parseNewFriendJSON(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    JSONObject localJSONObject = super.parseJSON(paramContext, paramString);
    if (localJSONObject == null);
    do
    {
      return false;
      if (!Setting.getInstance().isDebug())
        continue;
      KXLog.d("parseNewFriendJSON", "strContent=" + localJSONObject.toString());
    }
    while (this.ret <= 0);
    try
    {
      this.strTipMsg = localJSONObject.getString("tipmsg");
      return true;
    }
    catch (Exception localException)
    {
      KXLog.e("NewFriendEngine", "parseNewFriendJSON", localException);
    }
    return false;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.NewFriendEngine
 * JD-Core Version:    0.6.0
 */