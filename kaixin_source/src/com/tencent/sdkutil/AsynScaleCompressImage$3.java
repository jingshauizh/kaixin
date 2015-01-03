package com.tencent.sdkutil;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.util.ArrayList;

final class AsynScaleCompressImage$3 extends Handler
{
  public void handleMessage(Message paramMessage)
  {
    switch (paramMessage.what)
    {
    default:
      super.handleMessage(paramMessage);
      return;
    case 101:
    }
    ArrayList localArrayList = paramMessage.getData().getStringArrayList("images");
    this.val$asynLoadImgBack.batchSaved(0, localArrayList);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.sdkutil.AsynScaleCompressImage.3
 * JD-Core Version:    0.6.0
 */