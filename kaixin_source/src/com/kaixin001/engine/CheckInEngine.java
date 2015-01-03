package com.kaixin001.engine;

import android.content.Context;
import android.location.Location;
import android.text.TextUtils;
import com.kaixin001.lbs.LocationService;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.UploadFile;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class CheckInEngine extends KXEngine
{
  public static final int CODE_ERROR_FORBIDDEN_WORD = 15;
  public static final int CODE_ERROR_OTHER = 0;
  public static final int CODE_ERROR_TOO_FREQUENT = 14;
  public static final int CODE_OK = 1;
  private static final String TAG = "CheckInEngine";
  private static CheckInEngine instance;
  private String msRetRecordId = null;

  public static CheckInEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new CheckInEngine();
      CheckInEngine localCheckInEngine = instance;
      return localCheckInEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public String getRetRecordId()
  {
    return this.msRetRecordId;
  }

  public int parseRecordJSON(Context paramContext, String paramString)
  {
    try
    {
      JSONObject localJSONObject = super.parseJSON(paramContext, paramString);
      if (localJSONObject == null)
        return 0;
      if (this.ret == 1)
      {
        this.msRetRecordId = localJSONObject.optString("rid");
        return 1;
      }
      int i = localJSONObject.optInt("code", -1);
      if (i == 14)
        return 14;
      if (i == 15)
        return 15;
    }
    catch (Exception localException)
    {
    }
    return 0;
  }

  public int postCheckInRequest(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6)
  {
    String str1 = Protocol.getInstance().makeLBSCheckInRequest(User.getInstance().getUID());
    if (paramString3 == null)
      paramString3 = "";
    HashMap localHashMap = new HashMap();
    localHashMap.put("poiid", paramString3);
    if (!TextUtils.isEmpty(paramString5))
      localHashMap.put("lon", paramString5);
    if (!TextUtils.isEmpty(paramString4))
      localHashMap.put("lat", paramString4);
    LocationService localLocationService = LocationService.getLocationService();
    Location localLocation = localLocationService.lastBestMapABCLocation;
    if (localLocationService.isLocationValid(localLocation))
    {
      localHashMap.put("x", String.valueOf(localLocation.getLongitude()));
      localHashMap.put("y", String.valueOf(localLocation.getLatitude()));
    }
    localHashMap.put("privacy", paramString1);
    if (!TextUtils.isEmpty(paramString2))
      localHashMap.put("title", paramString2);
    boolean bool = TextUtils.isEmpty(paramString6);
    UploadFile[] arrayOfUploadFile = null;
    if (!bool)
    {
      arrayOfUploadFile = new UploadFile[1];
      arrayOfUploadFile[0] = new UploadFile(paramString6, "photo", "image/jpeg");
      KXLog.d("CheckInEngine", "photo=" + paramString6);
    }
    int i;
    int j;
    if (arrayOfUploadFile != null)
    {
      i = arrayOfUploadFile.length;
      j = 0;
    }
    String str2;
    while (true)
    {
      HttpProxy localHttpProxy;
      if (j >= i)
        localHttpProxy = new HttpProxy(paramContext);
      try
      {
        String str3 = localHttpProxy.httpPost(str1, localHashMap, null, null);
        str2 = str3;
        if (TextUtils.isEmpty(str2))
        {
          return 0;
          UploadFile localUploadFile = arrayOfUploadFile[j];
          localHashMap.put(localUploadFile.getFormname(), new File(localUploadFile.getFilname()));
          j++;
        }
      }
      catch (Exception localException)
      {
        while (true)
        {
          KXLog.e("CheckInEngine", "postCheckInRequest error", localException);
          str2 = null;
        }
      }
    }
    return parseRecordJSON(paramContext, str2);
  }

  public void setRetRecordId(String paramString)
  {
    this.msRetRecordId = paramString;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.CheckInEngine
 * JD-Core Version:    0.6.0
 */