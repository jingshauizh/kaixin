package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.model.CircleAlbumModel;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import org.json.JSONObject;

public class CircleAlbumEngine extends KXEngine
{
  private static final String LOGTAG = "CircleAlbumEngine";
  public static final int NUM = 20;
  private static CircleAlbumEngine instance = null;

  public static CircleAlbumEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new CircleAlbumEngine();
      CircleAlbumEngine localCircleAlbumEngine = instance;
      return localCircleAlbumEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public int getCirclePhotoList(Context paramContext, int paramInt, String paramString)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeCircleAlbumList(paramString, paramInt, 20);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    JSONObject localJSONObject;
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (TextUtils.isEmpty(str2))
        return 0;
    }
    catch (Exception localException)
    {
      String str2;
      while (true)
      {
        KXLog.e("CircleAlbumEngine", "getCirclePhotoList error", localException);
        str2 = null;
      }
      localJSONObject = super.parseJSON(paramContext, str2);
    }
    return CircleAlbumModel.getInstance().parseCircleAlbumJSON(localJSONObject, paramInt, paramString);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.CircleAlbumEngine
 * JD-Core Version:    0.6.0
 */