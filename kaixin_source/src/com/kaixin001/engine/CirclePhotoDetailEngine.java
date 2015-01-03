package com.kaixin001.engine;

import android.content.Context;
import com.kaixin001.item.PhotoItem;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;
import org.json.JSONObject;

public class CirclePhotoDetailEngine extends KXEngine
{
  private static final String LOGTAG = "CirclePhotoDetailEngine";
  public static final int NUM = 10;
  public static final int RESULT_FAILED = 0;
  public static final int RESULT_FAILED_NETWORK = -2;
  public static final int RESULT_FAILED_PARSE = -1;
  public static final int RESULT_OK = 1;
  private static CirclePhotoDetailEngine instance = null;
  private String mLastError = "";

  public static CirclePhotoDetailEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new CirclePhotoDetailEngine();
      CirclePhotoDetailEngine localCirclePhotoDetailEngine = instance;
      return localCirclePhotoDetailEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public int doGetCirclePhotoDetail(Context paramContext, String paramString1, String paramString2, String paramString3, ArrayList<PhotoItem> paramArrayList)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeGetCirclePhotoDetail(paramString1, paramString2, paramString3);
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
        KXLog.e("CirclePhotoDetailEngine", "doGetCirclePhotoDetail error", localException);
        str2 = null;
      }
      JSONObject localJSONObject1 = super.parseJSON(paramContext, str2);
      if (localJSONObject1 == null)
        return -1;
      if (this.ret == 1)
      {
        JSONObject localJSONObject2 = localJSONObject1.optJSONObject("result");
        PhotoItem localPhotoItem = new PhotoItem();
        localPhotoItem.pid = localJSONObject2.optString("pid", null);
        localPhotoItem.albumId = localJSONObject2.optString("albumid", null);
        localPhotoItem.albumType = 3;
        localPhotoItem.albumTitle = localJSONObject2.optString("albumtitle", null);
        localPhotoItem.title = localJSONObject2.optString("title", null);
        localPhotoItem.thumbnail = localJSONObject2.optString("thumbnail", null);
        localPhotoItem.large = localJSONObject2.optString("large", null);
        localPhotoItem.picnum = localJSONObject2.optString("picnum", null);
        localPhotoItem.index = localJSONObject2.optString("pos", null);
        localPhotoItem.ctime = localJSONObject2.optString("ctime", null);
        paramArrayList.add(localPhotoItem);
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
 * Qualified Name:     com.kaixin001.engine.CirclePhotoDetailEngine
 * JD-Core Version:    0.6.0
 */