package com.kaixin001.model;

import android.text.TextUtils;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class UserInfoModel extends KXModel
{
  private static UserInfoModel instance;
  private String mBirthday;
  private String mCity;
  private String mGender;
  private String mHometown;
  private String mPhoneNum;
  private String mState;
  private UserLevel mUserLevel;
  private JSONArray m_friendList;
  private String online;
  private String stateTime;
  private ArrayList<JSONObject> userInfoList = new ArrayList();

  public static String getCompany(JSONObject paramJSONObject)
  {
    if (paramJSONObject == null)
      return null;
    while (true)
    {
      JSONArray localJSONArray;
      int i;
      StringBuffer localStringBuffer;
      int j;
      try
      {
        localJSONArray = paramJSONObject.getJSONArray("career");
        if (localJSONArray == null)
          break;
        i = localJSONArray.length();
        if (i == 0)
          break;
        localStringBuffer = new StringBuffer();
        j = 0;
        if (j >= i)
        {
          localStringBuffer.toString();
          return null;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("UserInfoModel", "getCompany", localException);
        return null;
      }
      String str = localJSONArray.getJSONObject(j).getString("company");
      if (!TextUtils.isEmpty(str))
      {
        localStringBuffer.append(str);
        if (j < i - 1)
          localStringBuffer.append("\r\n");
      }
      j++;
    }
  }

  public static String getEducation(JSONObject paramJSONObject)
  {
    if (paramJSONObject == null)
      return null;
    while (true)
    {
      JSONArray localJSONArray;
      int i;
      StringBuffer localStringBuffer;
      int j;
      try
      {
        localJSONArray = paramJSONObject.getJSONArray("education");
        if (localJSONArray == null)
          break;
        i = localJSONArray.length();
        if (i == 0)
          break;
        localStringBuffer = new StringBuffer();
        j = 0;
        if (j >= i)
        {
          localStringBuffer.toString();
          return null;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("UserInfoModel", "getEducation", localException);
        return null;
      }
      String str = localJSONArray.getJSONObject(j).getString("school");
      if (!TextUtils.isEmpty(str))
      {
        localStringBuffer.append(str);
        if (j < i - 1)
          localStringBuffer.append("\r\n");
      }
      j++;
    }
  }

  public static UserInfoModel getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new UserInfoModel();
      UserInfoModel localUserInfoModel = instance;
      return localUserInfoModel;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public void clear()
  {
    this.m_friendList = null;
    this.userInfoList = null;
  }

  public int count()
  {
    if (this.m_friendList == null)
      return 0;
    return this.m_friendList.length();
  }

  public String getBirthday()
  {
    return this.mBirthday;
  }

  public String getCity()
  {
    return this.mCity;
  }

  public JSONObject getFirstItem()
  {
    JSONArray localJSONArray = this.m_friendList;
    Object localObject = null;
    if (localJSONArray != null)
    {
      int i = this.m_friendList.length();
      localObject = null;
      if (i <= 0);
    }
    try
    {
      JSONObject localJSONObject = this.m_friendList.getJSONObject(0);
      localObject = localJSONObject;
      return localObject;
    }
    catch (Exception localException)
    {
      KXLog.e("UserInfoModel", "getFirstItem", localException);
    }
    return null;
  }

  public JSONArray getFriendList()
  {
    return this.m_friendList;
  }

  public String getGender()
  {
    return this.mGender;
  }

  public String getHometown()
  {
    return this.mHometown;
  }

  public String getOnline()
  {
    return this.online;
  }

  public String getPhoneNum()
  {
    return this.mPhoneNum;
  }

  public String getState()
  {
    return this.mState;
  }

  public String getStateTime()
  {
    return this.stateTime;
  }

  public ArrayList<JSONObject> getUserInfoList()
  {
    return this.userInfoList;
  }

  public UserLevel getmUserLevel()
  {
    return this.mUserLevel;
  }

  public void setBirthday(String paramString)
  {
    this.mBirthday = paramString;
  }

  public void setCity(String paramString)
  {
    this.mCity = paramString;
  }

  public void setFriendList(JSONArray paramJSONArray)
  {
    this.m_friendList = paramJSONArray;
  }

  public void setGender(String paramString)
  {
    this.mGender = paramString;
  }

  public void setHometown(String paramString)
  {
    this.mHometown = paramString;
  }

  public void setOnline(String paramString)
  {
    this.online = paramString;
  }

  public void setPhoneNum(String paramString)
  {
    this.mPhoneNum = paramString;
  }

  public void setState(String paramString)
  {
    this.mState = paramString;
  }

  public void setStateTime(String paramString)
  {
    this.stateTime = paramString;
  }

  public void setUserInfoList(ArrayList<JSONObject> paramArrayList)
  {
    this.userInfoList = paramArrayList;
  }

  public void setmUserLevel(UserLevel paramUserLevel)
  {
    this.mUserLevel = paramUserLevel;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.UserInfoModel
 * JD-Core Version:    0.6.0
 */