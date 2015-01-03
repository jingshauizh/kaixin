package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.network.HttpProgressListener;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.UploadFile;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class CircleRecordEngine extends KXEngine
{
  public static final int CODE_ERROR_DUPLICATED = 216;
  public static final int CODE_ERROR_OTHER = 0;
  public static final int CODE_ERROR_TOO_FREQUENT = 218;
  public static final int CODE_OK = 1;
  private static final String LOGTAG = "CircleRecordEngine";
  public static final int RESULT_FAILED_NO_PERMISSION = -3002;
  private static CircleRecordEngine instance;
  private String msRetRecordId = null;

  public static CircleRecordEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new CircleRecordEngine();
      CircleRecordEngine localCircleRecordEngine = instance;
      return localCircleRecordEngine;
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
    throws SecurityErrorException
  {
    JSONObject localJSONObject1 = super.parseJSON(paramContext, paramString);
    if (localJSONObject1 == null);
    int i;
    do
    {
      return 0;
      if (this.ret == 1)
      {
        JSONObject localJSONObject2 = localJSONObject1.optJSONObject("result");
        if (localJSONObject2 != null)
          this.msRetRecordId = localJSONObject2.optString("tid", "");
        return 1;
      }
      i = localJSONObject1.optInt("code", -1);
      if (i == 218)
        return 218;
      if (i == 216)
        return 216;
    }
    while (i != 3002);
    return -3002;
  }

  public int postCircleRecordRequest(Context paramContext, String paramString1, String paramString2, String paramString3, HttpProgressListener paramHttpProgressListener)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makePostCircleRecordRequest();
    HashMap localHashMap = new HashMap();
    localHashMap.put("gid", paramString1);
    localHashMap.put("message", paramString2);
    boolean bool = TextUtils.isEmpty(paramString3);
    UploadFile[] arrayOfUploadFile = null;
    if (!bool)
    {
      arrayOfUploadFile = new UploadFile[1];
      arrayOfUploadFile[0] = new UploadFile(paramString3, "pic", "image/jpeg");
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
        String str3 = localHttpProxy.httpPost(str1, localHashMap, null, paramHttpProgressListener);
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
          KXLog.e("CircleRecordEngine", "postCheckInRequest error", localException);
          str2 = null;
        }
      }
    }
    return parseRecordJSON(paramContext, str2);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.CircleRecordEngine
 * JD-Core Version:    0.6.0
 */