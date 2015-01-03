package com.tencent.sdkutil;

import android.os.Handler;
import android.os.Message;

class WebViewDialog$JsListener
{
  private WebViewDialog$JsListener(WebViewDialog paramWebViewDialog)
  {
  }

  public void onAddShare(String paramString)
  {
    onComplete(paramString);
  }

  public void onCancel(String paramString)
  {
    WebViewDialog.access$700(this.this$0).obtainMessage(2, paramString).sendToTarget();
    this.this$0.dismiss();
  }

  public void onCancelAddShare(int paramInt)
  {
    onCancel(null);
  }

  public void onCancelInvite()
  {
    onCancel(null);
  }

  public void onCancelLogin()
  {
    onCancel(null);
  }

  public void onComplete(String paramString)
  {
    WebViewDialog.access$700(this.this$0).obtainMessage(1, paramString).sendToTarget();
    this.this$0.dismiss();
  }

  public void onInvite(String paramString)
  {
    onComplete(paramString);
  }

  public void onLoad(String paramString)
  {
    WebViewDialog.access$700(this.this$0).obtainMessage(4, paramString).sendToTarget();
  }

  public void showMsg(String paramString)
  {
    WebViewDialog.access$700(this.this$0).obtainMessage(3, paramString).sendToTarget();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.sdkutil.WebViewDialog.JsListener
 * JD-Core Version:    0.6.0
 */