package com.tencent.jsutil;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.webkit.JavascriptInterface;
import com.tencent.tauth.QQToken;

public class JsDialogListener
{
  private static final String KEY_ACTION = "action";
  private static final String KEY_LISTENNER = "listenerKey";
  private static final String KEY_URL = "actionUrl";
  private static final int PK_DIALOG = 1;
  private static final int T_DIALOG = 2;
  private static final int WEB_DIALOG = 3;
  private Activity mActivity;
  private Handler mHandler = new JsDialogListener.1(this);
  private QQToken mQQToken;

  public JsDialogListener(QQToken paramQQToken, Activity paramActivity)
  {
    this.mQQToken = paramQQToken;
    this.mActivity = paramActivity;
  }

  public Activity getActivity()
  {
    return this.mActivity;
  }

  @JavascriptInterface
  public void onOpenDialog(String paramString1, String paramString2, int paramInt)
  {
    Log.e("opendialog", this.mActivity.toString());
    Log.d("JsDialogListener", "onOpenDialog action =" + paramString1 + " url=" + paramString2 + "key=" + paramInt);
    Bundle localBundle = new Bundle();
    localBundle.putString("action", paramString1);
    localBundle.putString("actionUrl", paramString2);
    localBundle.putInt("listenerKey", paramInt);
    Message localMessage = new Message();
    localMessage.setData(localBundle);
    if (("action_brag".equals(paramString1)) || ("action_challenge".equals(paramString1)))
    {
      localMessage.what = 1;
      this.mHandler.sendMessage(localMessage);
      return;
    }
    localMessage.what = 2;
    this.mHandler.sendMessage(localMessage);
  }

  @JavascriptInterface
  public void onOpenWebViewDialog(String paramString1, String paramString2, int paramInt)
  {
    Message localMessage = new Message();
    Bundle localBundle = new Bundle();
    localBundle.putString("action", paramString2);
    localBundle.putString("actionUrl", paramString1);
    localBundle.putInt("listenerKey", paramInt);
    localMessage.setData(localBundle);
    localMessage.what = 3;
    this.mHandler.sendMessage(localMessage);
  }

  public void setActivity(Activity paramActivity)
  {
    this.mActivity = paramActivity;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.jsutil.JsDialogListener
 * JD-Core Version:    0.6.0
 */