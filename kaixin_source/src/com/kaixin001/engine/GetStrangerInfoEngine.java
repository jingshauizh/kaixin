package com.kaixin001.engine;

import android.content.Context;
import com.kaixin001.item.KaixinCircleMember;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class GetStrangerInfoEngine extends KXEngine
{
  public static final String FIELDS = "uid,career,city,education";
  private static final String LOGTAG = "GetStrangerInfoEngine";
  public static final int NUM = 10;
  public static final int RESULT_FAILED = 0;
  public static final int RESULT_FAILED_NETWORK = -2;
  public static final int RESULT_FAILED_NO_PERMISSION = -3002;
  public static final int RESULT_FAILED_PARSE = -1;
  public static final int RESULT_OK = 1;
  private static GetStrangerInfoEngine instance = null;
  private String mLastError = "";

  private void appendAdditionInfotoList(JSONArray paramJSONArray, ArrayList<KaixinCircleMember> paramArrayList)
  {
    if (paramJSONArray == null);
    for (int i = 0; i == 0; i = paramJSONArray.length())
      return;
    int j = 0;
    label22: JSONObject localJSONObject;
    if (j < i)
    {
      localJSONObject = paramJSONArray.optJSONObject(j);
      if (localJSONObject != null)
        break label47;
    }
    while (true)
    {
      j++;
      break label22;
      break;
      label47: KaixinCircleMember localKaixinCircleMember = getMemberByUid(localJSONObject.optString("uid"), paramArrayList);
      if (localKaixinCircleMember == null)
        continue;
      localKaixinCircleMember.city = localJSONObject.optString("city");
      localKaixinCircleMember.school = schoolValueOf(localJSONObject.optJSONArray("education"));
      localKaixinCircleMember.company = companyValueOf(localJSONObject.optJSONArray("career"));
    }
  }

  public static GetStrangerInfoEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new GetStrangerInfoEngine();
      GetStrangerInfoEngine localGetStrangerInfoEngine = instance;
      return localGetStrangerInfoEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private KaixinCircleMember getMemberByUid(String paramString, ArrayList<KaixinCircleMember> paramArrayList)
  {
    if (paramArrayList == null);
    KaixinCircleMember localKaixinCircleMember;
    for (int i = 0; i == 0; i = paramArrayList.size())
    {
      localKaixinCircleMember = null;
      return localKaixinCircleMember;
    }
    for (int j = 0; ; j++)
    {
      if (j >= i)
        return null;
      localKaixinCircleMember = (KaixinCircleMember)paramArrayList.get(j);
      if (paramString.equals(localKaixinCircleMember.uid))
        break;
    }
  }

  public String companyValueOf(JSONArray paramJSONArray)
  {
    if (paramJSONArray == null);
    for (int i = 0; i == 0; i = paramJSONArray.length())
    {
      localObject1 = null;
      return localObject1;
    }
    Object localObject1 = null;
    Object localObject2 = null;
    Object localObject3 = null;
    int j = 0;
    label33: JSONObject localJSONObject;
    if (j < i)
    {
      localJSONObject = paramJSONArray.optJSONObject(j);
      if (localJSONObject != null)
        break label58;
    }
    label166: 
    while (true)
    {
      j++;
      break label33;
      break;
      label58: String str1 = localJSONObject.optString("beginyear");
      String str2 = localJSONObject.optString("beginmonth");
      String str3 = localJSONObject.optString("company");
      if (localObject2 == null)
      {
        localObject1 = str3;
        localObject2 = str1;
        localObject3 = str2;
        continue;
      }
      int k;
      if (str1.compareTo(localObject2) > 0)
        k = 1;
      while (true)
      {
        if (k == 0)
          break label166;
        localObject1 = str3;
        localObject2 = str1;
        localObject3 = str2;
        break;
        if ((str1.compareTo(localObject2) == 0) && (str2.compareTo(localObject3) > 0))
        {
          k = 1;
          continue;
        }
        k = 0;
      }
    }
  }

  public int doGetCircleMemberAdditionInfo(Context paramContext, ArrayList<KaixinCircleMember> paramArrayList)
    throws SecurityErrorException
  {
    if (paramArrayList == null);
    for (int i = 0; i == 0; i = paramArrayList.size())
      return 1;
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(((KaixinCircleMember)paramArrayList.get(0)).uid);
    int j = 1;
    while (true)
    {
      String str1;
      HttpProxy localHttpProxy;
      if (j >= i)
      {
        str1 = Protocol.getInstance().getStrangerInfo(User.getInstance().getAccount(), localStringBuffer.toString(), "uid,career,city,education");
        localHttpProxy = new HttpProxy(paramContext);
      }
      try
      {
        String str3 = localHttpProxy.httpGet(str1, null, null);
        str2 = str3;
        if (str2 == null)
        {
          return 0;
          KaixinCircleMember localKaixinCircleMember = (KaixinCircleMember)paramArrayList.get(j);
          localStringBuffer.append(",").append(localKaixinCircleMember.uid);
          j++;
        }
      }
      catch (Exception localException)
      {
        String str2;
        while (true)
        {
          KXLog.e("GetStrangerInfoEngine", "doGetCircleMemberAdditionInfo error", localException);
          str2 = null;
        }
        JSONObject localJSONObject = super.parseJSON(paramContext, str2);
        if (localJSONObject == null)
          return -1;
        if (this.ret != 1)
          break;
        appendAdditionInfotoList(localJSONObject.optJSONObject("result").optJSONArray("users"), paramArrayList);
        return 1;
      }
    }
    return 0;
  }

  public String getLastError()
  {
    return this.mLastError;
  }

  public String schoolValueOf(JSONArray paramJSONArray)
  {
    if (paramJSONArray == null);
    for (int i = 0; i == 0; i = paramJSONArray.length())
      return null;
    int j = 0;
    label22: JSONObject localJSONObject;
    if (j < i)
    {
      localJSONObject = paramJSONArray.optJSONObject(j);
      if (localJSONObject != null)
        break label45;
    }
    label45: 
    do
    {
      j++;
      break label22;
      break;
    }
    while (localJSONObject.optInt("schooltype") != 0);
    return localJSONObject.optString("school");
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.GetStrangerInfoEngine
 * JD-Core Version:    0.6.0
 */