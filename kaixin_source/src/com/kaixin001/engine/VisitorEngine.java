package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.item.VisitorItem;
import com.kaixin001.model.VisitorModel;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class VisitorEngine extends KXEngine
{
  private static final String LOGTAG = "VisitorEngine";
  public static final int NUM = 10;
  public static final int RESULT_FAILED = 0;
  public static final int RESULT_FAILED_NETWORK = -2;
  public static final int RESULT_FAILED_PARSE = -1;
  public static final int RESULT_OK = 1;
  private static VisitorEngine instance = null;
  private String mLastError = "";
  VisitorModel visitorModel = VisitorModel.getInstance();

  private ArrayList<VisitorItem> getFaceList(JSONArray paramJSONArray)
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
      label59: VisitorItem localVisitorItem = new VisitorItem();
      localVisitorItem.fuid = localJSONObject.optString("uid");
      localVisitorItem.fname = localJSONObject.optString("name");
      localVisitorItem.flogo = localJSONObject.optString("icon50");
      localVisitorItem.city = localJSONObject.optString("city");
      localVisitorItem.school = localJSONObject.optString("education");
      localVisitorItem.company = localJSONObject.optString("company");
      localVisitorItem.isMyFriend = localJSONObject.optInt("isfriend", 0);
      localArrayList.add(localVisitorItem);
    }
  }

  public static VisitorEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new VisitorEngine();
      VisitorEngine localVisitorEngine = instance;
      return localVisitorEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public int doGetVisitorList(Context paramContext, String paramString1, int paramInt, String paramString2, String paramString3)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeGetPhotoVisitorRequest(paramString1, paramInt, paramString2, paramString3);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (paramString1.equals("0"))
        this.visitorModel.clearVisitors();
      if (TextUtils.isEmpty(str2))
        return 0;
    }
    catch (Exception localException)
    {
      String str2;
      while (true)
      {
        KXLog.e("VisitorEngine", "doGetVisitorList error", localException);
        str2 = null;
      }
      JSONObject localJSONObject = super.parseJSON(paramContext, str2);
      if (localJSONObject == null)
        return -1;
      if (this.ret == 1)
      {
        int i = localJSONObject.optInt("vcount", 0);
        int j = localJSONObject.optInt("total", 0);
        JSONArray localJSONArray = localJSONObject.optJSONArray("data");
        this.visitorModel.setVisitors(i, j, getFaceList(localJSONArray), paramString1);
        return 1;
      }
    }
    return 0;
  }

  public String getLastError()
  {
    return this.mLastError;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.VisitorEngine
 * JD-Core Version:    0.6.0
 */