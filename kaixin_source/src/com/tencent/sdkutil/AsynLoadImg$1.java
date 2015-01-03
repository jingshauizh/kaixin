package com.tencent.sdkutil;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

class AsynLoadImg$1 extends Handler
{
  public void handleMessage(Message paramMessage)
  {
    Log.v("AsynLoadImg", "handleMessage:" + paramMessage.arg1);
    if (paramMessage.arg1 == 0)
    {
      String str = (String)paramMessage.obj;
      this.this$0.setLocalImageLocalPath(str);
      AsynLoadImg.access$000(this.this$0).saved(paramMessage.arg1, str);
      return;
    }
    AsynLoadImg.access$000(this.this$0).saved(paramMessage.arg1, null);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.sdkutil.AsynLoadImg.1
 * JD-Core Version:    0.6.0
 */