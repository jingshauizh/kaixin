package com.tencent.jsutil;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.webkit.WebView;

class JsBridge$1 extends Handler
{
  public void handleMessage(Message paramMessage)
  {
    Log.i("JsBridge", "handleMessage");
    if (paramMessage.what == 0)
    {
      String str = (String)paramMessage.obj;
      Log.i("JsBridge", "url =" + str);
      JsBridge.access$000(this.this$0).loadUrl(str);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.jsutil.JsBridge.1
 * JD-Core Version:    0.6.0
 */