package com.kaixin001.engine;

import android.content.Context;
import com.kaixin001.model.AddFriendModel;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import org.json.JSONException;
import org.json.JSONObject;

public class AddFriendEngine extends KXEngine
{
  private static final String LOGTAG = "AddFriendEngine";
  private static AddFriendEngine instance = null;

  public static AddFriendEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new AddFriendEngine();
      AddFriendEngine localAddFriendEngine = instance;
      return localAddFriendEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public boolean doSearchFriend(Context paramContext, String paramString1, String paramString2)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeSearchFriendRequest(paramString2);
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
        KXLog.e("AddFriendEngine", "doSearchFriend error", localException);
        str2 = null;
      }
      KXLog.d("content", str2);
      JSONObject localJSONObject1 = super.parseJSON(paramContext, str2);
      if (localJSONObject1 == null)
        return false;
      try
      {
        if (this.ret == 1)
        {
          JSONObject localJSONObject2 = localJSONObject1.getJSONObject("result");
          if (localJSONObject2 == null)
            break label228;
          int i = localJSONObject2.getInt("result_code");
          AddFriendModel.getInstance().mResult_code = i;
          if (i != 3000)
            break label228;
          AddFriendModel.getInstance().mUid = localJSONObject2.getString("uid");
          AddFriendModel.getInstance().mRealName = localJSONObject2.getString("real_name");
          AddFriendModel.getInstance().mGender = localJSONObject2.getInt("gender");
          AddFriendModel.getInstance().mHasLogo = localJSONObject2.getInt("logo");
          if (AddFriendModel.getInstance().mHasLogo == 1)
          {
            AddFriendModel.getInstance().mLogo = localJSONObject2.getString("icon120");
            break label228;
          }
          AddFriendModel.getInstance().mLogo = "";
        }
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
        return false;
      }
      return false;
    }
    label228: return true;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.AddFriendEngine
 * JD-Core Version:    0.6.0
 */