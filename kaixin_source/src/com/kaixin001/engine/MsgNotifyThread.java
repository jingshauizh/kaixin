package com.kaixin001.engine;

import android.os.Handler;
import android.os.Message;
import com.kaixin001.model.MessageCenterModel;
import com.kaixin001.util.KXLog;

public class MsgNotifyThread extends Thread
{
  public static final int NEW_MESSAGE_NOTIFY = 5999;
  private boolean mStopFlag = false;
  private Handler mhandler;
  private int msgNum = 0;

  public MsgNotifyThread(Handler paramHandler)
  {
    this.mhandler = paramHandler;
    setPriority(1);
  }

  public void cancel()
  {
    this.mStopFlag = true;
    this.mhandler = null;
  }

  public void run()
  {
    while (true)
    {
      if (this.mStopFlag)
        return;
      try
      {
        int i = MessageCenterModel.getInstance().getTotalNoticeCnt();
        if (this.msgNum != i)
        {
          this.msgNum = i;
          Message localMessage = Message.obtain();
          localMessage.what = 5999;
          this.mhandler.sendMessage(localMessage);
        }
        sleep(15000L);
      }
      catch (Exception localException)
      {
        KXLog.e("msgNotifyThread", "run", localException);
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.MsgNotifyThread
 * JD-Core Version:    0.6.0
 */