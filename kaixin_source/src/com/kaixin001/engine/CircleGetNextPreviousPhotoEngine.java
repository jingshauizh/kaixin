package com.kaixin001.engine;

import android.content.Context;
import com.kaixin001.model.CirclePhotoModel.CirclePhoto;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;
import org.json.JSONObject;

public class CircleGetNextPreviousPhotoEngine extends KXEngine
{
  private static final String LOGTAG = "CircleGetNextPreviousPhotoEngine";
  public static final int NUM = 10;
  public static final int RESULT_FAILED = 0;
  public static final int RESULT_FAILED_NETWORK = -2;
  public static final int RESULT_FAILED_PARSE = -1;
  public static final int RESULT_OK = 1;
  private static CircleGetNextPreviousPhotoEngine instance = null;
  private String mLastError = "";

  public static CircleGetNextPreviousPhotoEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new CircleGetNextPreviousPhotoEngine();
      CircleGetNextPreviousPhotoEngine localCircleGetNextPreviousPhotoEngine = instance;
      return localCircleGetNextPreviousPhotoEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public int doGetCircleNextPreviousPhoto(Context paramContext, String paramString1, String paramString2, String paramString3, ArrayList<CirclePhotoModel.CirclePhoto> paramArrayList)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeCircleGetNextPreviousPhoto(paramString2, paramString1, paramString3, null);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (str2 == null)
        return 0;
    }
    catch (Exception localException)
    {
      String str2;
      while (true)
      {
        KXLog.e("CircleGetNextPreviousPhotoEngine", "doGetCircleNextPreviousPhoto error", localException);
        str2 = null;
      }
      JSONObject localJSONObject1 = super.parseJSON(paramContext, str2);
      if (localJSONObject1 == null)
        return -1;
      if (this.ret == 1)
      {
        JSONObject localJSONObject2 = localJSONObject1.optJSONObject("result");
        CirclePhotoModel.CirclePhoto localCirclePhoto = new CirclePhotoModel.CirclePhoto();
        localCirclePhoto.gid = localJSONObject2.optString("gid", null);
        localCirclePhoto.pid = localJSONObject2.optString("pid", null);
        localCirclePhoto.tid = localJSONObject2.optString("tid", null);
        localCirclePhoto.aid = localJSONObject2.optString("aid", null);
        localCirclePhoto.uid = localJSONObject2.optString("uid", null);
        localCirclePhoto.username = localJSONObject2.optString("username", null);
        localCirclePhoto.ctime = localJSONObject2.optString("ctime", null);
        localCirclePhoto.smallPhoto = localJSONObject2.optString("simgurl", null);
        localCirclePhoto.largePhoto = localJSONObject2.optString("fimgurl", null);
        localCirclePhoto.title = localJSONObject2.optString("title", null);
        paramArrayList.add(localCirclePhoto);
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
 * Qualified Name:     com.kaixin001.engine.CircleGetNextPreviousPhotoEngine
 * JD-Core Version:    0.6.0
 */