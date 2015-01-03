package com.tencent.sdkutil;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import java.lang.ref.WeakReference;

class WebViewDialog$THandler extends Handler
{
  private WebViewDialog.OnTimeListener mL;

  public WebViewDialog$THandler(WebViewDialog.OnTimeListener paramOnTimeListener, Looper paramLooper)
  {
    super(paramLooper);
    this.mL = paramOnTimeListener;
  }

  public void handleMessage(Message paramMessage)
  {
    switch (paramMessage.what)
    {
    default:
    case 1:
    case 2:
    case 3:
    case 4:
    case 5:
    }
    do
    {
      do
      {
        do
        {
          return;
          WebViewDialog.OnTimeListener.access$000(this.mL, (String)paramMessage.obj);
          return;
          this.mL.onCancel();
          return;
        }
        while ((WebViewDialog.access$100() == null) || (WebViewDialog.access$100().get() == null));
        WebViewDialog.access$200((Context)WebViewDialog.access$100().get(), (String)paramMessage.obj);
        return;
      }
      while ((WebViewDialog.access$300() == null) || (WebViewDialog.access$300().get() == null));
      ((View)WebViewDialog.access$300().get()).setVisibility(8);
      return;
    }
    while ((WebViewDialog.access$100() == null) || (WebViewDialog.access$100().get() == null));
    WebViewDialog.access$400((Context)WebViewDialog.access$100().get(), (String)paramMessage.obj);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.sdkutil.WebViewDialog.THandler
 * JD-Core Version:    0.6.0
 */