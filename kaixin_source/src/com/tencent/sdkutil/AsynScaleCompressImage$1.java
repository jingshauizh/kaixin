package com.tencent.sdkutil;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

final class AsynScaleCompressImage$1 extends Handler
{
  public void handleMessage(Message paramMessage)
  {
    switch (paramMessage.what)
    {
    default:
      super.handleMessage(paramMessage);
      return;
    case 101:
      String str = (String)paramMessage.obj;
      this.val$asynLoadImgBack.saved(0, str);
      return;
    case 102:
    }
    int i = paramMessage.arg1;
    this.val$asynLoadImgBack.saved(i, null);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.sdkutil.AsynScaleCompressImage.1
 * JD-Core Version:    0.6.0
 */