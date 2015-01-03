package com.tencent.sdkutil;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import java.util.ArrayList;

final class AsynScaleCompressImage$4
  implements Runnable
{
  public void run()
  {
    int i = 0;
    if (i < this.val$imgPaths.size())
    {
      String str1 = (String)this.val$imgPaths.get(i);
      Bitmap localBitmap;
      String str2;
      String str4;
      if ((!Util.isValidUrl(str1)) && (Util.fileExists(str1)))
      {
        localBitmap = AsynScaleCompressImage.scaleBitmap(str1, 10000);
        if (localBitmap != null)
        {
          str2 = Environment.getExternalStorageDirectory() + "/tmp/";
          String str3 = Util.encrypt(str1);
          str4 = "share2qzone_temp" + str3 + ".jpg";
          if (AsynScaleCompressImage.access$000(str1, 640, 10000))
            break label158;
          Log.d("AsynScaleCompressImage", "not out of bound,not compress!");
        }
      }
      while (true)
      {
        if (str1 != null)
          this.val$imgPaths.set(i, str1);
        i++;
        break;
        label158: Log.d("AsynScaleCompressImage", "out of bound, compress!");
        str1 = AsynScaleCompressImage.compressBitmap(localBitmap, str2, str4);
      }
    }
    Message localMessage = this.val$handler.obtainMessage(101);
    Bundle localBundle = new Bundle();
    localBundle.putStringArrayList("images", this.val$imgPaths);
    localMessage.setData(localBundle);
    this.val$handler.sendMessage(localMessage);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.sdkutil.AsynScaleCompressImage.4
 * JD-Core Version:    0.6.0
 */