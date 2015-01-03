package com.tencent.mm.sdk.platformtools;

import android.os.Handler;
import android.os.Message;

class QueueWorkerThread$1 extends Handler
{
  public void handleMessage(Message paramMessage)
  {
    if ((paramMessage != null) && (paramMessage.obj != null))
      ((QueueWorkerThread.ThreadObject)paramMessage.obj).onPostExecute();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.platformtools.QueueWorkerThread.1
 * JD-Core Version:    0.6.0
 */