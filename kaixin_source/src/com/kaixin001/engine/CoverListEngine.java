package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.item.CoverItem;
import com.kaixin001.model.CoverListModel;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CoverListEngine extends KXEngine
{
  private static final String LOGTAG = "CoverListEngine";
  private static CoverListEngine instance;

  public static CoverListEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new CoverListEngine();
      CoverListEngine localCoverListEngine = instance;
      return localCoverListEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private boolean parseCoverListJSON(Context paramContext, String paramString)
    throws JSONException
  {
    int i;
    int j;
    do
    {
      try
      {
        JSONObject localJSONObject1 = super.parseJSON(paramContext, paramString);
        if (localJSONObject1 == null)
          return false;
        if (this.ret == 1)
        {
          CoverListModel localCoverListModel = CoverListModel.getInstance();
          localCoverListModel.clear();
          localCoverListModel.setCtime(localJSONObject1.getString("ctime"));
          JSONArray localJSONArray = localJSONObject1.getJSONArray("list");
          i = localJSONArray.length();
          if (i <= 0)
            break;
          localCoverListModel.addCover(new CoverItem("99999999", "ITEM_ADD", ""));
          j = 0;
          continue;
          JSONObject localJSONObject2 = localJSONArray.getJSONObject(j);
          localCoverListModel.addCover(new CoverItem(localJSONObject2.optString("id"), localJSONObject2.optString("thumb"), localJSONObject2.optString("url")));
          j++;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
      return false;
    }
    while (j < i);
    return true;
  }

  private boolean parseSetCoverJSON(Context paramContext, String paramString)
    throws JSONException
  {
    try
    {
      if (super.parseJSON(paramContext, paramString) != null)
      {
        int i = this.ret;
        if (i == 1)
          return true;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }

  public boolean doGetCoverListRequest(Context paramContext)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeGetCoverList();
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      boolean bool1 = TextUtils.isEmpty(str2);
      i = 0;
      if (bool1);
    }
    catch (Exception localException)
    {
      try
      {
        boolean bool2 = parseCoverListJSON(paramContext, str2);
        int i = bool2;
        return i;
        localException = localException;
        KXLog.e("CoverListEngine", "doGetCoverListRequest error", localException);
        String str2 = null;
      }
      catch (JSONException localJSONException)
      {
        KXLog.e("CoverListEngine", localJSONException.getMessage());
      }
    }
    return false;
  }

  public boolean doSetCoverListRequest(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeSetCoverList(paramString);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      boolean bool1 = TextUtils.isEmpty(str2);
      i = 0;
      if (bool1);
    }
    catch (Exception localException)
    {
      try
      {
        boolean bool2 = parseSetCoverJSON(paramContext, str2);
        int i = bool2;
        return i;
        localException = localException;
        KXLog.e("CoverListEngine", "doGetCoverListRequest error", localException);
        String str2 = null;
      }
      catch (JSONException localJSONException)
      {
        KXLog.e("CoverListEngine", localJSONException.getMessage());
      }
    }
    return false;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.CoverListEngine
 * JD-Core Version:    0.6.0
 */