package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.model.Setting;
import com.kaixin001.model.StrangerModel;
import com.kaixin001.model.StrangerModel.Stranger;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpConnection;
import com.kaixin001.network.HttpMethod;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;

public class RecommandFriendEngine extends KXEngine
{
  private static final String LOGTAG = "RecommandFriendEngine";
  public static final int NUM = 10;
  public static final int RESULT_FAILED = 0;
  public static final int RESULT_FAILED_NETWORK = -2;
  public static final int RESULT_FAILED_PARSE = -1;
  public static final int RESULT_OK = 1;
  private static RecommandFriendEngine instance = null;
  private String mLastError = "";
  StrangerModel recommandFriendModel = StrangerModel.getInstance();

  private ArrayList<StrangerModel.Stranger> getFaceList(JSONArray paramJSONArray)
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
      localStranger.gender = localJSONObject.optInt("gender", 0);
      localStranger.city = localJSONObject.optString("city");
      localStranger.school = localJSONObject.optString("education");
      localStranger.company = localJSONObject.optString("company");
      localStranger.sameFriends = localJSONObject.optString("samefriends");
      localStranger.constellation = localJSONObject.optString("Constellation");
      localStranger.isMyFriend = localJSONObject.optInt("ismyfriend", 0);
      localStranger.isMobileFriend = localJSONObject.optInt("bycontact", 0);
      localStranger.mobile = localJSONObject.optString("mobile");
      localArrayList.add(localStranger);
    }
  }

  public static RecommandFriendEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new RecommandFriendEngine();
      RecommandFriendEngine localRecommandFriendEngine = instance;
      return localRecommandFriendEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public int doGetRecommandFriendList(Context paramContext, int paramInt1, int paramInt2, String paramString)
    throws SecurityErrorException
  {
    String str1;
    if (User.getInstance().GetLookAround())
      str1 = Setting.getInstance().getNewHost() + Protocol.getInstance().makeLookFindFriendRequest(String.valueOf(paramInt1), String.valueOf(paramInt2));
    while (true)
    {
      HashMap localHashMap = new HashMap();
      if (!TextUtils.isEmpty(paramString))
        localHashMap.put("mobiles", paramString);
      HttpProxy localHttpProxy = new HttpProxy(paramContext);
      try
      {
        String str3;
        if (User.getInstance().GetLookAround())
          str3 = new HttpConnection(paramContext).httpRequest(str1, HttpMethod.POST, localHashMap, null, null, null);
        String str2;
        for (localObject = str3; ; localObject = str2)
        {
          if (paramInt1 == 0)
            this.recommandFriendModel.clearFriends();
          if (localObject != null)
            break label194;
          return 0;
          str1 = Protocol.getInstance().makeGetMayBeFriendsRequest(paramInt1, paramInt2, User.getInstance().getUID());
          break;
          str2 = localHttpProxy.httpPost(str1, localHashMap, null, null);
        }
      }
      catch (Exception localException)
      {
        Object localObject;
        while (true)
        {
          KXLog.e("RecommandFriendEngine", "doGetRecommandFriendList error", localException);
          localObject = null;
        }
        label194: JSONObject localJSONObject = super.parseJSON(paramContext, (String)localObject);
        if (localJSONObject == null)
          return -1;
        if (this.ret != 1)
          break;
        int i = localJSONObject.optInt("total", 0);
        JSONArray localJSONArray = localJSONObject.optJSONArray("userinfos");
        this.recommandFriendModel.setFriends(i, getFaceList(localJSONArray), paramInt1);
        return 1;
      }
    }
    return 0;
  }

  public int doGetRecommandStarList(Context paramContext, int paramInt1, int paramInt2)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeGetStarsRequest(paramInt1, paramInt2, User.getInstance().getUID());
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str4 = localHttpProxy.httpGet(str1, null, null);
      str2 = str4;
      if (paramInt1 == 0)
        this.recommandFriendModel.clearStarFriends();
      if (str2 == null)
        return 0;
    }
    catch (Exception localException)
    {
      JSONObject localJSONObject;
      do
      {
        String str2;
        while (true)
        {
          KXLog.e("RecommandFriendEngine", "doGetRecommandStarList error", localException);
          str2 = null;
        }
        localJSONObject = super.parseJSON(paramContext, str2);
        if (localJSONObject == null)
          return -1;
      }
      while (this.ret != 1);
      String str3 = localJSONObject.optString("total", null);
      JSONArray localJSONArray = localJSONObject.optJSONArray("data");
      this.recommandFriendModel.setStarFriends(str3, getFaceList(localJSONArray), paramInt1);
    }
    return 1;
  }

  public String getLastError()
  {
    return this.mLastError;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.RecommandFriendEngine
 * JD-Core Version:    0.6.0
 */