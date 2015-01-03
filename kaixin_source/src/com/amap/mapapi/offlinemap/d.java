package com.amap.mapapi.offlinemap;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

class d extends Handler
{
  d(OfflineMapManager paramOfflineMapManager)
  {
  }

  public void handleMessage(Message paramMessage)
  {
    this.a.b.onDownload(paramMessage.getData().getInt("status"), paramMessage.getData().getInt("completepercent"));
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.offlinemap.d
 * JD-Core Version:    0.6.0
 */