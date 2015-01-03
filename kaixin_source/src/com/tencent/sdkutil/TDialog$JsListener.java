package com.tencent.sdkutil;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.webkit.JavascriptInterface;

class TDialog$JsListener
{
  private TDialog$JsListener(TDialog paramTDialog)
  {
  }

  @JavascriptInterface
  public void onAddShare(String paramString)
  {
    onComplete(paramString);
  }

  @JavascriptInterface
  public void onCancel(String paramString)
  {
    TDialog.access$700(this.this$0).obtainMessage(2, paramString).sendToTarget();
    this.this$0.dismiss();
  }

  @JavascriptInterface
  public void onCancelAddShare(int paramInt)
  {
    onCancel(null);
  }

  @JavascriptInterface
  public void onCancelInvite()
  {
    onCancel(null);
  }

  @JavascriptInterface
  public void onCancelLogin()
  {
    onCancel(null);
  }

  @JavascriptInterface
  public void onComplete(String paramString)
  {
    TDialog.access$700(this.this$0).obtainMessage(1, paramString).sendToTarget();
    Log.e("onComplete", paramString);
    this.this$0.dismiss();
  }

  @JavascriptInterface
  public void onInvite(String paramString)
  {
    onComplete(paramString);
  }

  @JavascriptInterface
  public void onLoad(String paramString)
  {
    TDialog.access$700(this.this$0).obtainMessage(4, paramString).sendToTarget();
  }

  @JavascriptInterface
  public void showMsg(String paramString)
  {
    TDialog.access$700(this.this$0).obtainMessage(3, paramString).sendToTarget();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.sdkutil.TDialog.JsListener
 * JD-Core Version:    0.6.0
 */