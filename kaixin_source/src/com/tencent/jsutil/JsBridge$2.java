package com.tencent.jsutil;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

class JsBridge$2
  implements Runnable
{
  public void run()
  {
    while (JsBridge.access$100(this.this$0) < 100);
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("javascript:");
    localStringBuffer.append(this.val$method);
    localStringBuffer.append("(");
    if (this.val$params != null)
    {
      int i = 0;
      while (true)
        if (i < this.val$params.length)
          try
          {
            Integer.valueOf(this.val$params[i]);
            localStringBuffer.append(this.val$params[i]);
            if (i != -1 + this.val$params.length)
              localStringBuffer.append(",");
            i++;
          }
          catch (Exception localException)
          {
            while (true)
              localStringBuffer.append("'" + JsBridge.access$200(this.this$0, this.val$params[i]) + "'");
          }
    }
    localStringBuffer.append(");");
    Log.i("JsBridge", "excuteMethod:" + localStringBuffer.toString());
    Message localMessage = new Message();
    localMessage.what = 0;
    localMessage.obj = localStringBuffer.toString();
    this.this$0.handler.sendMessage(localMessage);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.jsutil.JsBridge.2
 * JD-Core Version:    0.6.0
 */