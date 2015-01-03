package com.kaixin001.activity.wxapi;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Toast;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX.Req;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXWebpageObject;

public class WXManager
{
  private static final String TAG = "WX";
  public static final String WEIXIN_APP_ID = "wx9dacf17c1550c735";
  public static final String WEIXIN_APP_KEY = "c1a1c99fa33fc51b59f246976880acaa";
  private static WXManager instance = new WXManager();
  private IWXAPI api;

  public static WXManager getInstance()
  {
    return instance;
  }

  public IWXAPI getWxApi()
  {
    return this.api;
  }

  public void regToWx(Context paramContext)
  {
    this.api = WXAPIFactory.createWXAPI(paramContext, "wx9dacf17c1550c735", true);
    this.api.registerApp("wx9dacf17c1550c735");
  }

  public void sendMsgToFriendCircle(Context paramContext, String paramString1, String paramString2, Bitmap paramBitmap, String paramString3)
  {
    if (this.api.isWXAppInstalled())
    {
      SendMessageToWX.Req localReq = new SendMessageToWX.Req();
      localReq.transaction = String.valueOf(System.currentTimeMillis());
      localReq.scene = 1;
      WXMediaMessage localWXMediaMessage = new WXMediaMessage();
      localWXMediaMessage.title = paramString1;
      localWXMediaMessage.description = paramString2;
      if (paramBitmap != null)
        localWXMediaMessage.setThumbImage(paramBitmap);
      WXWebpageObject localWXWebpageObject = new WXWebpageObject();
      localWXWebpageObject.webpageUrl = paramString3;
      localWXMediaMessage.mediaObject = localWXWebpageObject;
      localReq.message = localWXMediaMessage;
      this.api.sendReq(localReq);
      return;
    }
    Toast.makeText(paramContext, "微信未安装", 1).show();
  }

  public void sendMsgToWxFriend(Context paramContext, String paramString1, String paramString2, Bitmap paramBitmap, String paramString3)
  {
    if (this.api.isWXAppInstalled())
    {
      SendMessageToWX.Req localReq = new SendMessageToWX.Req();
      localReq.transaction = String.valueOf(System.currentTimeMillis());
      localReq.scene = 0;
      WXMediaMessage localWXMediaMessage = new WXMediaMessage();
      localWXMediaMessage.title = paramString1;
      localWXMediaMessage.description = paramString2;
      localWXMediaMessage.setThumbImage(paramBitmap);
      WXWebpageObject localWXWebpageObject = new WXWebpageObject();
      localWXWebpageObject.webpageUrl = paramString3;
      localWXMediaMessage.mediaObject = localWXWebpageObject;
      localReq.message = localWXMediaMessage;
      this.api.sendReq(localReq);
      return;
    }
    Toast.makeText(paramContext, "微信未安装", 1).show();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.activity.wxapi.WXManager
 * JD-Core Version:    0.6.0
 */