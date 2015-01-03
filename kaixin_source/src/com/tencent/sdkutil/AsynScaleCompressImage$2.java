package com.tencent.sdkutil;

import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

final class AsynScaleCompressImage$2
  implements Runnable
{
  public void run()
  {
    Bitmap localBitmap = AsynScaleCompressImage.scaleBitmap(this.val$imgPath, 140);
    if (localBitmap != null)
    {
      String str1 = Environment.getExternalStorageDirectory() + "/tmp/";
      String str2 = Util.encrypt(this.val$imgPath);
      String str3 = "share2qq_temp" + str2 + ".jpg";
      if (!AsynScaleCompressImage.access$000(this.val$imgPath, 140, 140))
        Log.d("AsynScaleCompressImage", "not out of bound,not compress!");
      for (String str4 = this.val$imgPath; str4 != null; str4 = AsynScaleCompressImage.compressBitmap(localBitmap, str1, str3))
      {
        Message localMessage2 = this.val$handler.obtainMessage(101);
        localMessage2.obj = str4;
        this.val$handler.sendMessage(localMessage2);
        return;
        Log.d("AsynScaleCompressImage", "out of bound,compress!");
      }
    }
    Message localMessage1 = this.val$handler.obtainMessage(102);
    localMessage1.arg1 = 3;
    this.val$handler.sendMessage(localMessage1);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.sdkutil.AsynScaleCompressImage.2
 * JD-Core Version:    0.6.0
 */