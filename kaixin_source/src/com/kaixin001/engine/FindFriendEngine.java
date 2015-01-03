package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.model.FindFriendModel;
import com.kaixin001.model.StrangerModel.Stranger;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class FindFriendEngine extends KXEngine
{
  private static final String LOGTAG = "FindFriendEngine";
  private static final int MAX_REQUEST_NUM = 500;
  public static final int RESULT_FAILED = 0;
  public static final int RESULT_FAILED_NETWORK = -2;
  public static final int RESULT_FAILED_PARSE = -1;
  public static final int RESULT_OK = 1;
  public static final int RET_SUCCESS = 1;
  private boolean bCancel = false;

  public static String getArrayStrs(JSONObject paramJSONObject, String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    try
    {
      JSONArray localJSONArray = paramJSONObject.optJSONArray(paramString);
      int i;
      if (localJSONArray != null)
        i = localJSONArray.length();
      for (int j = i - 1; ; j--)
      {
        if (j < 0)
          return localStringBuffer.toString();
        String str = localJSONArray.getString(j);
        if (j < i - 1)
          localStringBuffer.append(",");
        localStringBuffer.append(str);
      }
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

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
      localArrayList.add(localStranger);
    }
  }

  private boolean parseItems(JSONArray paramJSONArray, ArrayList<StrangerModel.Stranger> paramArrayList, int paramInt)
  {
    int i = 0;
    int k;
    if (paramJSONArray != null)
    {
      int j = paramJSONArray.length();
      i = 0;
      if (j > 0)
        k = paramJSONArray.length();
    }
    for (int m = 0; ; m++)
    {
      if (m >= k)
      {
        i = 0;
        if (k >= paramInt)
          i = 1;
        return i;
      }
      try
      {
        JSONObject localJSONObject = paramJSONArray.getJSONObject(m);
        StrangerModel.Stranger localStranger = new StrangerModel.Stranger();
        localStranger.fname = localJSONObject.optString("name");
        localStranger.fuid = localJSONObject.optString("uid");
        if (localStranger.fuid.equals(User.getInstance().getUID()))
          continue;
        localStranger.flogo = localJSONObject.optString("icon50");
        if (TextUtils.isEmpty(localStranger.flogo))
          localStranger.flogo = localJSONObject.optString("icon");
        localStranger.fans = localJSONObject.optString("fans");
        if (!TextUtils.isEmpty(localStranger.fans))
          localStranger.fans = ("粉丝：" + localStranger.fans);
        localStranger.city = localJSONObject.optString("city");
        localStranger.school = localJSONObject.optString("scholl");
        if (TextUtils.isEmpty(localStranger.school))
          localStranger.school = localJSONObject.optString("education");
        localStranger.company = localJSONObject.optString("company");
        localStranger.distance = localJSONObject.optString("distance", "100米");
        localStranger.location = localJSONObject.optString("location", "第三极");
        localStranger.sameFriends = localJSONObject.optString("samefriends");
        localStranger.sameFriendsNum = localJSONObject.optInt("funms", 0);
        paramArrayList.add(localStranger);
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }

  private boolean parseItemsClassmateList(JSONArray paramJSONArray, ArrayList<StrangerModel.Stranger> paramArrayList, int paramInt)
  {
    int i = 0;
    int k;
    if (paramJSONArray != null)
    {
      int j = paramJSONArray.length();
      i = 0;
      if (j > 0)
        k = paramJSONArray.length();
    }
    for (int m = 0; ; m++)
    {
      if (m >= k)
      {
        i = 0;
        if (k >= paramInt)
          i = 1;
        return i;
      }
      try
      {
        JSONObject localJSONObject = paramJSONArray.getJSONObject(m);
        StrangerModel.Stranger localStranger = new StrangerModel.Stranger();
        localStranger.fname = localJSONObject.optString("name");
        localStranger.fuid = localJSONObject.optString("uid");
        if (localStranger.fuid.equals(User.getInstance().getUID()))
          continue;
        localStranger.flogo = localJSONObject.optString("logo");
        if (TextUtils.isEmpty(localStranger.flogo))
          localStranger.flogo = localJSONObject.optString("icon");
        localStranger.city = localJSONObject.optString("city");
        if (TextUtils.isEmpty(localStranger.city))
          localStranger.city = localJSONObject.optString("hometown");
        localStranger.school = getArrayStrs(localJSONObject, "school");
        if (TextUtils.isEmpty(localStranger.school))
          localStranger.school = localJSONObject.optString("education");
        localStranger.company = getArrayStrs(localJSONObject, "company");
        localStranger.distance = localJSONObject.optString("distance", "100米");
        localStranger.location = localJSONObject.optString("location", "第三极");
        localStranger.sameFriends = localJSONObject.optString("samefriends");
        localStranger.sameFriendsNum = localJSONObject.optInt("funms", 0);
        paramArrayList.add(localStranger);
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }

  private boolean parseNearbyItems(JSONArray paramJSONArray, ArrayList<StrangerModel.Stranger> paramArrayList, int paramInt)
  {
    int i = 0;
    int k;
    int m;
    if (paramJSONArray != null)
    {
      int j = paramJSONArray.length();
      i = 0;
      if (j > 0)
      {
        k = paramJSONArray.length();
        m = 0;
      }
    }
    while (true)
    {
      if (m >= k)
      {
        i = 0;
        if (k >= paramInt)
          i = 1;
        return i;
      }
      try
      {
        JSONObject localJSONObject = paramJSONArray.getJSONObject(m);
        StrangerModel.Stranger localStranger = new StrangerModel.Stranger();
        localStranger.fname = localJSONObject.optString("name");
        localStranger.fuid = localJSONObject.optString("uid");
        localStranger.flogo = localJSONObject.optString("logo");
        localStranger.city = localJSONObject.optString("city");
        localStranger.distance = "";
        String str = localJSONObject.optString("distance");
        if (!TextUtils.isEmpty(str))
        {
          int n = str.indexOf(".");
          if (n >= 0)
            str = str.substring(0, n);
          localStranger.distance = "与你相距*米".replace("*", str);
        }
        localStranger.location = localJSONObject.optString("poiname");
        paramArrayList.add(localStranger);
        m++;
      }
      catch (Exception localException)
      {
        while (true)
          localException.printStackTrace();
      }
    }
  }

  public void cancelRequest()
  {
    this.bCancel = true;
  }

  public int doSearchFriend(Context paramContext, FindFriendModel paramFindFriendModel, String paramString, Findby paramFindby, int paramInt1, int paramInt2)
    throws SecurityErrorException
  {
    if (paramInt2 > 500)
      paramInt2 = 500;
    Protocol localProtocol = Protocol.getInstance();
    String str1 = paramFindby.toString();
    String str2 = paramFindby.paraName;
    String str3 = paramInt1;
    String str4 = localProtocol.makeFindFriendRequest(str1, str2, paramString, paramInt2, null, null, null, null, str3);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str6 = localHttpProxy.httpGet(str4, null, null);
      str5 = str6;
      if (paramInt1 == 0)
      {
        paramFindFriendModel.clearFriends();
        paramFindFriendModel.getSearchList().clear();
      }
      if (str5 == null)
        return 0;
    }
    catch (Exception localException)
    {
      String str5;
      while (true)
      {
        KXLog.e("FindFriendEngine", "getDiaryList error", localException);
        str5 = null;
      }
      KXLog.d("doSearchFriend", "strContent=" + str5);
      JSONObject localJSONObject = super.parseJSON(paramContext, str5);
      if (localJSONObject == null)
        return -1;
      if (this.bCancel)
        return 0;
      if (this.ret == 1)
      {
        int i = localJSONObject.optInt("total", 0);
        ArrayList localArrayList = getFaceList(localJSONObject.optJSONArray("userinfos"));
        paramFindFriendModel.setFriends(localArrayList, paramString);
        int j;
        if (localArrayList == null)
        {
          j = 0;
          if ((paramInt1 == 0) && (j == 0))
            i = 0;
          if (paramFindby != Findby.account)
            break label306;
          paramFindFriendModel.totalSearchFriendByAccount = i;
          label259: if (localArrayList != null)
            paramFindFriendModel.getSearchList().addAll(localArrayList);
          if ((localArrayList == null) || (localArrayList.size() != paramInt2))
            break label315;
        }
        label306: label315: for (paramFindFriendModel.mHasmoreSearch = true; ; paramFindFriendModel.mHasmoreSearch = false)
        {
          return 1;
          j = localArrayList.size();
          break;
          paramFindFriendModel.totalSearchFriendByName = i;
          break label259;
        }
      }
    }
    return 0;
  }

  public int getClassmateColleagueList(boolean paramBoolean, Context paramContext, FindFriendModel paramFindFriendModel, String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2)
    throws SecurityErrorException
  {
    String str1;
    if (paramBoolean)
      str1 = Protocol.getInstance().makeSearchClassmateRequest(paramString1, paramString2, paramString3, paramInt1, paramInt2, User.getInstance().getUID());
    while (true)
    {
      HttpProxy localHttpProxy = new HttpProxy(paramContext);
      try
      {
        String str3 = localHttpProxy.httpGet(str1, null, null);
        str2 = str3;
        if (paramInt1 == 0)
          paramFindFriendModel.getSearchList().clear();
        if (str2 == null)
        {
          return 0;
          str1 = Protocol.getInstance().makeSearchCollegueRequest(paramString1, paramInt1, paramInt2, User.getInstance().getUID());
        }
      }
      catch (Exception localException)
      {
        String str2;
        while (true)
        {
          localException.printStackTrace();
          str2 = null;
        }
        JSONObject localJSONObject = super.parseJSON(paramContext, str2);
        if (localJSONObject == null)
          return -1;
        if (this.bCancel)
          return 0;
        if (this.ret != 1)
          break;
        localJSONObject.optString("total", null);
        paramFindFriendModel.mHasmoreSearch = parseItemsClassmateList(localJSONObject.optJSONArray("info"), paramFindFriendModel.getSearchList(), paramInt2);
        return 1;
      }
    }
    return 0;
  }

  public int getMaybeKnowList(Context paramContext, FindFriendModel paramFindFriendModel, int paramInt1, int paramInt2)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeGetMaybeKnowRequest(paramInt1, paramInt2, User.getInstance().getUID());
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (paramInt1 == 0)
        paramFindFriendModel.getMaybeKnow().clear();
      if (str2 == null)
        return 0;
    }
    catch (Exception localException)
    {
      String str2;
      while (true)
      {
        localException.printStackTrace();
        str2 = null;
      }
      JSONObject localJSONObject = super.parseJSON(paramContext, str2);
      if (localJSONObject == null)
        return -1;
      if (this.bCancel)
        return 0;
      if (this.ret == 1)
      {
        localJSONObject.optString("total", null);
        paramFindFriendModel.mHasmoreMaybeKnow = parseItems(localJSONObject.optJSONArray("userinfos"), paramFindFriendModel.getMaybeKnow(), paramInt2);
        return 1;
      }
    }
    return 0;
  }

  public int getNearbyPersonList(Context paramContext, FindFriendModel paramFindFriendModel, String paramString1, String paramString2, String paramString3, String paramString4, int paramInt1, int paramInt2)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeGetNearbyPersonRequest(paramString1, paramString2, paramString3, paramString4, paramInt1, paramInt2, User.getInstance().getUID());
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (paramInt1 == 0)
        paramFindFriendModel.getNearbyPerson().clear();
      if (str2 == null)
        return 0;
    }
    catch (Exception localException)
    {
      String str2;
      while (true)
      {
        localException.printStackTrace();
        str2 = null;
      }
      JSONObject localJSONObject = super.parseJSON(paramContext, str2);
      if (localJSONObject == null)
        return -1;
      if (this.bCancel)
        return 0;
      if (this.ret == 1)
      {
        localJSONObject.optString("total", null);
        paramFindFriendModel.mHasmoreNearbyPerson = parseNearbyItems(localJSONObject.optJSONArray("checks"), paramFindFriendModel.getNearbyPerson(), paramInt2);
        return 1;
      }
    }
    return 0;
  }

  public int getRecommandStarList(Context paramContext, FindFriendModel paramFindFriendModel, int paramInt1, int paramInt2, String paramString)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeGetRecommentStarsRequest(paramInt1, paramInt2, User.getInstance().getUID(), paramString);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (paramInt1 == 0)
      {
        if (TextUtils.isEmpty(paramString))
          paramFindFriendModel.getRecommendStars().clear();
      }
      else
      {
        if (str2 != null)
          break label93;
        return 0;
      }
    }
    catch (Exception localException)
    {
      String str2;
      while (true)
      {
        localException.printStackTrace();
        str2 = null;
        continue;
        paramFindFriendModel.getRecommendCategoryStars().clear();
      }
      label93: JSONObject localJSONObject = super.parseJSON(paramContext, str2);
      if (localJSONObject == null)
        return -1;
      if (this.bCancel)
        return 0;
      if (this.ret == 1)
      {
        localJSONObject.optString("total", null);
        JSONArray localJSONArray = localJSONObject.optJSONArray("data");
        if (TextUtils.isEmpty(paramString))
          paramFindFriendModel.mHasmoreStar = parseItems(localJSONArray, paramFindFriendModel.getRecommendStars(), paramInt2);
        while (true)
        {
          return 1;
          paramFindFriendModel.mHasmoreCategoryStar = parseItems(localJSONArray, paramFindFriendModel.getRecommendCategoryStars(), paramInt2);
        }
      }
    }
    return 0;
  }

  public static enum Findby
  {
    String paraName;

    static
    {
      Findby[] arrayOfFindby = new Findby[2];
      arrayOfFindby[0] = account;
      arrayOfFindby[1] = name;
      ENUM$VALUES = arrayOfFindby;
    }

    private Findby(String arg3)
    {
      Object localObject;
      this.paraName = localObject;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.FindFriendEngine
 * JD-Core Version:    0.6.0
 */