package com.kaixin001.activity.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.kaixin001.util.KXLog;
import com.tencent.mm.sdk.openapi.BaseReq;
import com.tencent.mm.sdk.openapi.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;

public class WXEntryActivity extends Activity
  implements IWXAPIEventHandler
{
  public static final String BROARCAST_WX = "com.kx.share.wx";
  private static final String TAG = "WX";
  private IWXAPI mApi;

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mApi = WXManager.getInstance().getWxApi();
    this.mApi.handleIntent(getIntent(), this);
  }

  protected void onNewIntent(Intent paramIntent)
  {
    super.onNewIntent(paramIntent);
    setIntent(paramIntent);
    this.mApi.handleIntent(paramIntent, this);
  }

  public void onReq(BaseReq paramBaseReq)
  {
    KXLog.d("WX", "onReq()...");
  }

  public void onResp(BaseResp paramBaseResp)
  {
    KXLog.d("WX", "onResp()..." + paramBaseResp.errCode);
    switch (paramBaseResp.errCode)
    {
    case -4:
    case -3:
    case -2:
    case -1:
    default:
    case 0:
    }
    while (true)
    {
      finish();
      return;
      sendBroadcast(new Intent("com.kx.share.wx"));
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.activity.wxapi.WXEntryActivity
 * JD-Core Version:    0.6.0
 */