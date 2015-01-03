package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.model.KXAppShareModel;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import org.json.JSONObject;

public class KXAppShareEngine extends KXEngine
{
  private int parseShareInfo(Context paramContext, KXAppShareModel paramKXAppShareModel, String paramString)
  {
    try
    {
      if (!TextUtils.isEmpty(paramString))
      {
        JSONObject localJSONObject1 = super.parseJSON(paramContext, paramString);
        if (localJSONObject1 == null)
          return -1;
        if (this.ret == 1)
        {
          JSONObject localJSONObject2 = localJSONObject1.optJSONObject("data");
          if (localJSONObject2 != null)
          {
            paramKXAppShareModel.mTitle = localJSONObject2.optString("sharingtitle");
            paramKXAppShareModel.mImgUrl = localJSONObject2.optString("sharingimgurl");
            paramKXAppShareModel.mDes = localJSONObject2.optString("sharingdesc");
            paramKXAppShareModel.mGiftInfo = localJSONObject2.optString("giftsinfo");
            paramKXAppShareModel.mLinkUrl = localJSONObject2.optString("sharinglinkurl");
          }
          return 1;
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return -1;
  }

  private int parseShareScore(Context paramContext, KXAppShareModel paramKXAppShareModel, String paramString)
  {
    try
    {
      if (!TextUtils.isEmpty(paramString))
      {
        JSONObject localJSONObject = super.parseJSON(paramContext, paramString);
        if (localJSONObject == null)
          return -1;
        if (this.ret == 1)
        {
          paramKXAppShareModel.mResultInfo = localJSONObject.optString("res");
          return 1;
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return -1;
  }

  public int getShareWxAddScore(Context paramContext, KXAppShareModel paramKXAppShareModel, String paramString1, String paramString2, String paramString3)
  {
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      int i = parseShareScore(paramContext, paramKXAppShareModel, localHttpProxy.httpGet(Protocol.getInstance().makeShareWXAddScore(paramString1, paramString2, paramString3), null, null));
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return -1;
  }

  public int getShareWxGetInfo(Context paramContext, KXAppShareModel paramKXAppShareModel, String paramString1, String paramString2)
  {
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      int i = parseShareInfo(paramContext, paramKXAppShareModel, localHttpProxy.httpGet(Protocol.getInstance().makeShareWXGetInfo(paramString1, paramString2), null, null));
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return -1;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.KXAppShareEngine
 * JD-Core Version:    0.6.0
 */