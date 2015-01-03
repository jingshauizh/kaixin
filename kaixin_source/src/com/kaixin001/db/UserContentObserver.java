package com.kaixin001.db;

import android.database.ContentObserver;
import android.os.Handler;
import android.os.Message;

public class UserContentObserver extends ContentObserver
{
  public static final int USER_CHANGED = 2;
  Handler mHandler;

  public UserContentObserver(Handler paramHandler)
  {
    super(paramHandler);
    this.mHandler = paramHandler;
  }

  public void onChange(boolean paramBoolean)
  {
    super.onChange(paramBoolean);
    if (this.mHandler != null)
    {
      Message localMessage = Message.obtain();
      localMessage.what = 2;
      this.mHandler.sendMessage(localMessage);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.db.UserContentObserver
 * JD-Core Version:    0.6.0
 */