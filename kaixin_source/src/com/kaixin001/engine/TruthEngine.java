package com.kaixin001.engine;

import android.content.Context;
import com.kaixin001.model.RepostModel;
import com.kaixin001.model.TruthModel;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import org.json.JSONException;
import org.json.JSONObject;

public class TruthEngine extends KXEngine
{
  private static final String LOGTAG = "TruthEngine";
  private static TruthEngine instance = null;

  public static TruthEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new TruthEngine();
      TruthEngine localTruthEngine = instance;
      return localTruthEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public boolean doGetTruthDetail(Context paramContext, String paramString1, String paramString2)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeGetTruthDetailRequest(paramString1, paramString2, User.getInstance().getUID());
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str5 = localHttpProxy.httpGet(str1, null, null);
      str2 = str5;
      if (str2 == null)
      {
        i = 0;
        if (i == 0)
          RepostModel.getInstance().clear();
        return i;
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        int i;
        KXLog.e("TruthEngine", "doGetTruthDetail error", localException);
        String str2 = null;
        continue;
        JSONObject localJSONObject = super.parseJSON(paramContext, str2);
        if (localJSONObject == null)
        {
          i = 0;
          continue;
        }
        try
        {
          if (this.ret == 1)
          {
            TruthModel.getInstance().setFuid(paramString2);
            TruthModel.getInstance().setTid(paramString1);
            String str3 = localJSONObject.getString("title");
            TruthModel.getInstance().setTitle(str3);
            int j = localJSONObject.getInt("truthid");
            TruthModel.getInstance().setTruthid(j);
            String str4 = localJSONObject.getString("truth");
            TruthModel.getInstance().setTruthContent(str4);
            int k = localJSONObject.getInt("status");
            TruthModel.getInstance().setStatus(k);
            int m = localJSONObject.getInt("cnum");
            TruthModel.getInstance().setCNum(m);
            i = 1;
            continue;
          }
          i = 0;
        }
        catch (JSONException localJSONException)
        {
          KXLog.e("TruthEngine", "doGetTruthDetail", localJSONException);
          i = 0;
        }
      }
    }
  }

  public boolean submitExchangeTruth(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4)
    throws SecurityErrorException
  {
    int i = 0;
    String str1 = Protocol.getInstance().makeExchangeTruthRequest(paramString1, paramString2, paramString3, paramString4, User.getInstance().getUID());
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (str2 == null)
        return false;
    }
    catch (Exception localException)
    {
      String str2;
      while (true)
      {
        KXLog.e("TruthEngine", "submitExchangeTruth error", localException);
        str2 = null;
      }
      JSONObject localJSONObject = super.parseJSON(paramContext, str2);
      if (localJSONObject == null)
        return false;
      try
      {
        if (this.ret == 1)
        {
          i = 1;
          TruthModel.getInstance().setMsg(localJSONObject.getString("msg"));
          return i;
        }
      }
      catch (JSONException localJSONException)
      {
        KXLog.e("TruthEngine", "submitExchangeTruth", localJSONException);
        return i;
      }
    }
    return false;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.TruthEngine
 * JD-Core Version:    0.6.0
 */