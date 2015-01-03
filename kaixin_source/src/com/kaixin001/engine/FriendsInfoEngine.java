package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.model.ContactsItemInfo;
import com.kaixin001.model.ContactsRelatedModel;
import com.kaixin001.model.FriendStatus;
import com.kaixin001.model.FriendsModel.Friend;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.FileUtil;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class FriendsInfoEngine extends KXEngine
{
  public static String FRIENDS_INFO_FILE = "friends_info.kx";
  private static final String LOGTAG = "FriendsInfoEngine";
  private static FriendsInfoEngine instance;

  private String getFuids(ArrayList<ContactsItemInfo> paramArrayList)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    int i;
    if (paramArrayList == null)
      i = 0;
    for (int j = 0; ; j++)
    {
      if (j >= i)
      {
        return localStringBuffer.toString();
        i = paramArrayList.size();
        break;
      }
      FriendsModel.Friend localFriend = ((ContactsItemInfo)paramArrayList.get(j)).getFriend();
      if ((localFriend == null) || (localFriend.getFuid() == null))
        continue;
      if (j != 0)
        localStringBuffer.append(",");
      localStringBuffer.append(localFriend.getFuid());
    }
  }

  public static FriendsInfoEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new FriendsInfoEngine();
      FriendsInfoEngine localFriendsInfoEngine = instance;
      return localFriendsInfoEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private String getUids(ArrayList<ContactsRelatedModel> paramArrayList)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    int i;
    if (paramArrayList == null)
      i = 0;
    for (int j = 0; ; j++)
    {
      if (j >= i)
      {
        return localStringBuffer.toString();
        i = paramArrayList.size();
        break;
      }
      ContactsRelatedModel localContactsRelatedModel = (ContactsRelatedModel)paramArrayList.get(j);
      if (j != 0)
        localStringBuffer.append(",");
      localStringBuffer.append(localContactsRelatedModel.getFuid());
    }
  }

  private boolean setFriendsInfoData(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    String str1 = FileUtil.getKXCacheDir(paramContext);
    String str2 = User.getInstance().getUID();
    if (!getInstance().parseFriendsStatusJSON(paramContext, paramString))
      return false;
    FileUtil.setCacheData(str1, str2, FRIENDS_INFO_FILE, paramString);
    return true;
  }

  public ArrayList<FriendStatus> getContactsStatus(Context paramContext, ArrayList<ContactsRelatedModel> paramArrayList)
    throws SecurityErrorException
  {
    if ((paramArrayList == null) || (paramArrayList.size() == 0));
    while (true)
    {
      return null;
      this.ret = 0;
      String str1 = getUids(paramArrayList);
      String str2 = Protocol.getInstance().makeFriendsUserInfoRequest(User.getInstance().getUID(), str1, "1");
      HttpProxy localHttpProxy = new HttpProxy(paramContext);
      try
      {
        String str4 = localHttpProxy.httpGet(str2, null, null);
        str3 = str4;
        if (TextUtils.isEmpty(str3))
          continue;
        return parseFriendsStatusForContactsJSON(paramContext, str3);
      }
      catch (Exception localException)
      {
        while (true)
        {
          KXLog.e("FriendsInfoEngine", "getContactsStatus error", localException);
          String str3 = null;
        }
      }
    }
  }

  public ArrayList<FriendStatus> getFriendsStatus(Context paramContext, ArrayList<ContactsItemInfo> paramArrayList)
    throws SecurityErrorException
  {
    if ((paramArrayList == null) || (paramArrayList.size() == 0));
    while (true)
    {
      return null;
      this.ret = 0;
      String str1 = getFuids(paramArrayList);
      String str2 = Protocol.getInstance().makeFriendsUserInfoRequest(User.getInstance().getUID(), str1, "1");
      HttpProxy localHttpProxy = new HttpProxy(paramContext);
      try
      {
        String str4 = localHttpProxy.httpGet(str2, null, null);
        str3 = str4;
        if (TextUtils.isEmpty(str3))
          continue;
        return parseFriendsStatusForContactsJSON(paramContext, str3);
      }
      catch (Exception localException)
      {
        while (true)
        {
          KXLog.e("FriendsInfoEngine", "getFriendsStatus error", localException);
          String str3 = null;
        }
      }
    }
  }

  public boolean getFriendsStatus(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    if ((TextUtils.isEmpty(paramString)) || (paramContext == null));
    while (true)
    {
      return false;
      this.ret = 0;
      String str1 = Protocol.getInstance().makeFriendsUserInfoRequest(User.getInstance().getUID(), paramString, "1");
      HttpProxy localHttpProxy = new HttpProxy(paramContext);
      try
      {
        String str3 = localHttpProxy.httpGet(str1, null, null);
        str2 = str3;
        if (TextUtils.isEmpty(str2))
          continue;
        return setFriendsInfoData(paramContext, str2);
      }
      catch (Exception localException)
      {
        while (true)
        {
          KXLog.e("FriendsInfoEngine", "getFriendsStatus error", localException);
          String str2 = null;
        }
      }
    }
  }

  public ArrayList<FriendStatus> parseFriendsStatusForContactsJSON(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    JSONObject localJSONObject1 = super.parseJSON(paramContext, true, paramString);
    ArrayList localArrayList;
    if (localJSONObject1 == null)
      localArrayList = null;
    while (true)
    {
      return localArrayList;
      if (this.ret != 1)
        return null;
      try
      {
        JSONArray localJSONArray = localJSONObject1.getJSONArray("friends");
        if (localJSONArray == null)
          return null;
        localArrayList = new ArrayList();
        int i = localJSONArray.length();
        for (int j = 0; j < i; j++)
        {
          JSONObject localJSONObject2 = localJSONArray.getJSONObject(j);
          FriendStatus localFriendStatus = new FriendStatus();
          localFriendStatus.setFuid(localJSONObject2.getString("fuid"));
          localFriendStatus.setName(localJSONObject2.getString("fname"));
          localFriendStatus.setStatus(localJSONObject2.getString("state"));
          localFriendStatus.setTimeStamp(localJSONObject2.getString("statetime"));
          localArrayList.add(localFriendStatus);
        }
      }
      catch (Exception localException)
      {
        KXLog.e("FriendsInfoEngine", "parseFriendsStatusJSON", localException);
      }
    }
    return null;
  }

  public boolean parseFriendsStatusJSON(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    if (super.parseJSON(paramContext, true, paramString) == null);
    do
      return false;
    while (this.ret != 1);
    return true;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.FriendsInfoEngine
 * JD-Core Version:    0.6.0
 */