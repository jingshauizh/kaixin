package com.kaixin001.engine;

import android.content.Context;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import org.json.JSONObject;

public class LbsAddLocationEngine extends KXEngine
{
  public static final int ERROR_ADD_LCATION_FREQUENCY = 103;
  public static final int ERROR_ADD_LOCATION_DIRTY_WORD = 101;
  public static final int ERROR_ADD_LOCATION_HAS_EXISTS = 102;
  public static final int ERROR_ADD_LOCATION_INTERUPTED_BY_USER = -2;
  public static final int ERROR_ADD_LOCATION_NO_AVAILABLE_CONTENT = -1;
  public static final int ERROR_ADD_LOCATION_NO_AVAILABLE_LON_LAT = 104;
  public static final int ERROR_ADD_LOCATION_UNKNOWN_ERROR = 199;
  private static final String LOGTAG = "LbsAddLocationEngine";
  private static LbsAddLocationEngine instance = null;
  private HttpProxy httpProxy;

  public static LbsAddLocationEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new LbsAddLocationEngine();
      LbsAddLocationEngine localLbsAddLocationEngine = instance;
      return localLbsAddLocationEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public void cancel()
  {
    this.httpProxy.cancel();
  }

  public int doUploadLbsLocation(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, StringBuilder paramStringBuilder)
    throws SecurityErrorException, InterruptedException
  {
    String str1 = Protocol.getInstance().makeLbsAddLocationRequest(paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, User.getInstance().getUID());
    this.httpProxy = new HttpProxy(paramContext);
    try
    {
      String str3 = this.httpProxy.httpGet(str1, null, null);
      str2 = str3;
      Thread.sleep(0L);
      if (str2 == null)
        return 199;
    }
    catch (Exception localException)
    {
      String str2;
      while (true)
      {
        KXLog.e("LbsAddLocationEngine", "doUploadLbsLocation error", localException);
        str2 = null;
      }
      JSONObject localJSONObject = super.parseJSON(paramContext, str2);
      if (localJSONObject == null)
        return 199;
      int i = this.ret;
      if (i == 1)
      {
        paramStringBuilder.append(localJSONObject.optString("poiid", ""));
        return 1;
      }
      int j = localJSONObject.optInt("code", 199);
      if (j == 101)
        return 101;
      if (j == 102)
        return 102;
      if (j == 103)
        return 103;
      if (i == 104)
        return 104;
      if (j == 199)
        return 199;
    }
    return 199;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.LbsAddLocationEngine
 * JD-Core Version:    0.6.0
 */