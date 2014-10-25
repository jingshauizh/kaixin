package com.kaixin001.activity.wxapi;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Toast;


public class WXManager
{
  private static final String TAG = "WX";
  public static final String WEIXIN_APP_ID = "wx9dacf17c1550c735";
  public static final String WEIXIN_APP_KEY = "c1a1c99fa33fc51b59f246976880acaa";
  private static WXManager instance = new WXManager();
  

  public static WXManager getInstance()
  {
    return instance;
  }


  public void sendMsgToFriendCircle(Context paramContext, String paramString1, String paramString2, Bitmap paramBitmap, String paramString3)
  {
   
      return;
   
  }

  public void sendMsgToWxFriend(Context paramContext, String paramString1, String paramString2, Bitmap paramBitmap, String paramString3)
  {
	  return;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.activity.wxapi.WXManager
 * JD-Core Version:    0.6.0
 */