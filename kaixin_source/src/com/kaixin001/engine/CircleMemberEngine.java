package com.kaixin001.engine;

import android.content.Context;
import com.kaixin001.item.KaixinCircleMember;
import com.kaixin001.model.CircleMemberModel;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class CircleMemberEngine extends KXEngine
{
  private static final String LOGTAG = "CircleMemberEngine";
  public static final int NUM = 10;
  public static final int RESULT_FAILED = 0;
  public static final int RESULT_FAILED_NETWORK = -2;
  public static final int RESULT_FAILED_NO_PERMISSION = -3002;
  public static final int RESULT_FAILED_PARSE = -1;
  public static final int RESULT_OK = 1;
  private static CircleMemberEngine instance = null;
  private String mLastError = "";

  private ArrayList<KaixinCircleMember> getCircleMemberList(JSONArray paramJSONArray)
  {
    if (paramJSONArray == null);
    for (int i = 0; i == 0; i = paramJSONArray.length())
    {
      localArrayList = null;
      return localArrayList;
    }
    ArrayList localArrayList = new ArrayList(i);
    int j = 0;
    label34: JSONObject localJSONObject;
    if (j < i)
    {
      localJSONObject = paramJSONArray.optJSONObject(j);
      if (localJSONObject != null)
        break label59;
    }
    while (true)
    {
      j++;
      break label34;
      break;
      label59: localArrayList.add(valueOf(localJSONObject));
    }
  }

  public static CircleMemberEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new CircleMemberEngine();
      CircleMemberEngine localCircleMemberEngine = instance;
      return localCircleMemberEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public int doGetCircleMemberList(Context paramContext, String paramString, int paramInt1, int paramInt2)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeCircleMemberList(paramString, paramInt1, paramInt2);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (paramInt1 == 0)
        CircleMemberModel.getInstance().clearFriends();
      if (str2 == null)
        return 0;
    }
    catch (Exception localException)
    {
      String str2;
      while (true)
      {
        KXLog.e("CircleMemberEngine", "doGetCircleMemberList error", localException);
        str2 = null;
      }
      JSONObject localJSONObject1 = super.parseJSON(paramContext, str2);
      if (localJSONObject1 == null)
        return -1;
      if (this.ret == 1)
      {
        JSONObject localJSONObject2 = localJSONObject1.optJSONObject("result");
        int i = localJSONObject2.optInt("total", 0);
        JSONArray localJSONArray = localJSONObject2.optJSONArray("list");
        CircleMemberModel.getInstance().setCircleMemberList(i, getCircleMemberList(localJSONArray), paramInt1);
        return 1;
      }
      if (localJSONObject1.optInt("code", 0) == 3002)
        return -3002;
    }
    return 0;
  }

  public String getLastError()
  {
    return this.mLastError;
  }

  public KaixinCircleMember valueOf(JSONObject paramJSONObject)
  {
    if (paramJSONObject == null);
    String str;
    do
    {
      return null;
      str = paramJSONObject.optString("uid");
    }
    while (str == null);
    KaixinCircleMember localKaixinCircleMember = new KaixinCircleMember();
    localKaixinCircleMember.uid = str;
    localKaixinCircleMember.realname = paramJSONObject.optString("name");
    localKaixinCircleMember.logo = paramJSONObject.optString("icon50");
    localKaixinCircleMember.relation = paramJSONObject.optInt("relation", -1);
    localKaixinCircleMember.gender = paramJSONObject.optInt("gender", -1);
    localKaixinCircleMember.online = paramJSONObject.optInt("online", -1);
    return localKaixinCircleMember;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.CircleMemberEngine
 * JD-Core Version:    0.6.0
 */