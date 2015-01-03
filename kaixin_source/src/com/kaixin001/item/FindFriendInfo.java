package com.kaixin001.item;

import org.json.JSONObject;

public class FindFriendInfo
{
  public String city;
  public String education;
  public String gender;
  public String icon;
  public String isMyfriend;
  public String name;
  public String uid;

  public static FindFriendInfo valueOf(JSONObject paramJSONObject)
  {
    if (paramJSONObject != null)
    {
      FindFriendInfo localFindFriendInfo = new FindFriendInfo();
      localFindFriendInfo.uid = paramJSONObject.optString("uid");
      localFindFriendInfo.name = paramJSONObject.optString("name");
      localFindFriendInfo.icon = paramJSONObject.optString("icon");
      localFindFriendInfo.gender = paramJSONObject.optString("gender");
      localFindFriendInfo.city = paramJSONObject.optString("city");
      localFindFriendInfo.education = paramJSONObject.optString("education");
      localFindFriendInfo.isMyfriend = paramJSONObject.optString("ismyfriend");
      return localFindFriendInfo;
    }
    return null;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.item.FindFriendInfo
 * JD-Core Version:    0.6.0
 */