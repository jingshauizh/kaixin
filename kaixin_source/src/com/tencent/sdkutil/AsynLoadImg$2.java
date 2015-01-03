package com.tencent.sdkutil;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import java.io.File;

class AsynLoadImg$2
  implements Runnable
{
  public void run()
  {
    Log.v("AsynLoadImg", "saveFileRunnable:");
    String str1 = Util.encrypt(AsynLoadImg.access$100(this.this$0));
    String str2 = "share_qq_" + str1 + ".jpg";
    String str3 = AsynLoadImg.access$200() + str2;
    File localFile = new File(str3);
    Message localMessage = AsynLoadImg.access$300(this.this$0).obtainMessage();
    if (localFile.exists())
    {
      localMessage.arg1 = 0;
      localMessage.obj = str3;
      Log.v("AsynLoadImg", "file exists: time:" + (System.currentTimeMillis() - AsynLoadImg.access$400(this.this$0)));
      AsynLoadImg.access$300(this.this$0).sendMessage(localMessage);
      return;
    }
    Bitmap localBitmap = AsynLoadImg.getbitmap(AsynLoadImg.access$100(this.this$0));
    boolean bool;
    if (localBitmap != null)
    {
      bool = this.this$0.saveFile(localBitmap, str2);
      label188: if (!bool)
        break label258;
      localMessage.arg1 = 0;
      localMessage.obj = str3;
    }
    while (true)
    {
      Log.v("AsynLoadImg", "file not exists: download time:" + (System.currentTimeMillis() - AsynLoadImg.access$400(this.this$0)));
      break;
      Log.v("AsynLoadImg", "saveFileRunnable:get bmp fail---");
      bool = false;
      break label188;
      label258: localMessage.arg1 = 1;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.sdkutil.AsynLoadImg.2
 * JD-Core Version:    0.6.0
 */