package com.tencent.jsutil;

import android.os.Handler;
import android.os.Message;

class JsBridge$3
  implements Runnable
{
  public void run()
  {
    while (JsBridge.access$100(this.this$0) < 100);
    String str = "javascript:" + this.val$method + ";";
    Message localMessage = new Message();
    localMessage.what = 0;
    localMessage.obj = str;
    this.this$0.handler.sendMessage(localMessage);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.jsutil.JsBridge.3
 * JD-Core Version:    0.6.0
 */