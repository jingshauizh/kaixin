package com.tencent.jsutil;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.tencent.sdkutil.PKDialog;
import com.tencent.sdkutil.TDialog;
import com.tencent.sdkutil.TemporaryStorage;
import com.tencent.sdkutil.WebViewDialog;
import com.tencent.tauth.IUiListener;

class JsDialogListener$1 extends Handler
{
  public void handleMessage(Message paramMessage)
  {
    Bundle localBundle = paramMessage.getData();
    String str1 = localBundle.getString("action");
    String str2 = localBundle.getString("actionUrl");
    IUiListener localIUiListener = TemporaryStorage.getListener(localBundle.getInt("listenerKey"));
    if (paramMessage.what == 1)
      new PKDialog(JsDialogListener.access$000(this.this$0), str1, str2, localIUiListener, JsDialogListener.access$100(this.this$0)).show();
    do
    {
      return;
      if (paramMessage.what != 2)
        continue;
      Log.d("JsDialogListener", "creatDialog");
      if (JsDialogListener.access$000(this.this$0) == null)
        Log.e("mActivity", "null");
      if (localIUiListener == null)
        Log.e("listener", "null");
      if (localIUiListener == null)
        Log.e("mQQToken", "null");
      Log.e("TDialog activity", JsDialogListener.access$000(this.this$0).toString());
      new TDialog(JsDialogListener.access$000(this.this$0), str1, str2, localIUiListener, JsDialogListener.access$100(this.this$0)).show();
      return;
    }
    while (paramMessage.what != 3);
    new WebViewDialog(JsDialogListener.access$000(this.this$0), str1, str2, localIUiListener, JsDialogListener.access$100(this.this$0)).show();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.jsutil.JsDialogListener.1
 * JD-Core Version:    0.6.0
 */