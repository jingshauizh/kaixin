package com.kaixin001.engine;

import android.content.Context;
import com.kaixin001.model.StrangerModel;
import com.kaixin001.model.StrangerModel.Stranger;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;

public class AddFriendsEngine extends KXEngine
{
  private static final String LOGTAG = "AddFriendEngin";
  public static final int RESULT_FAILED = 0;
  public static final int RESULT_FAILED_NETWORK = -2;
  public static final int RESULT_FAILED_PARSE = -1;
  public static final int RESULT_OK = 1;
  private static AddFriendsEngine instance = null;
  StrangerModel addFriendModel = StrangerModel.getInstance();

  private ArrayList<StrangerModel.Stranger> getFriendList(JSONArray paramJSONArray)
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
      label59: StrangerModel.Stranger localStranger = new StrangerModel.Stranger();
      localStranger.fuid = localJSONObject.optString("uid");
      localStranger.fname = localJSONObject.optString("name");
      localStranger.flogo = localJSONObject.optString("icon");
      localStranger.isStar = localJSONObject.optInt("isstar", 0);
      localStranger.fans = localJSONObject.optString("fansnum");
      localStranger.gender = localJSONObject.optInt("gender", 0);
      localStranger.city = localJSONObject.optString("city");
      localStranger.school = localJSONObject.optString("education");
      localStranger.company = localJSONObject.optString("company");
      localStranger.sameFriends = localJSONObject.optString("samefriends");
      localStranger.constellation = localJSONObject.optString("Constellation");
      localStranger.isMyFriend = localJSONObject.optInt("ismyfriend", 0);
      localStranger.isMobileFriend = localJSONObject.optInt("bycontact", 0);
      localStranger.state = localJSONObject.optString("state");
      localArrayList.add(localStranger);
    }
  }

  public static AddFriendsEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new AddFriendsEngine();
      AddFriendsEngine localAddFriendsEngine = instance;
      return localAddFriendsEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public int doGetFriendList(Context paramContext, int paramInt, String paramString)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeGetFindFriendRequest();
    HashMap localHashMap = new HashMap();
    if (paramString != null)
      localHashMap.put("frienduids", paramString);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str3 = localHttpProxy.httpPost(str1, localHashMap, null, null);
      str2 = str3;
      if (paramInt == 0)
        this.addFriendModel.clearFriends();
      if (str2 == null)
        return 0;
    }
    catch (Exception localException)
    {
      String str2;
      while (true)
      {
        KXLog.e("AddFriendEngin", "doGetRecommandFriendList error", localException);
        str2 = null;
      }
      JSONObject localJSONObject = super.parseJSON(paramContext, str2);
      if (localJSONObject == null)
        return -1;
      if (this.ret == 1)
      {
        int i = localJSONObject.optInt("total", 0);
        JSONArray localJSONArray = localJSONObject.optJSONArray("userinfos");
        this.addFriendModel.setFriends(i, getFriendList(localJSONArray), 2);
        return 1;
      }
    }
    return 0;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.AddFriendsEngine
 * JD-Core Version:    0.6.0
 */