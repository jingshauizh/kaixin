package com.tencent.sdkutil;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import java.lang.ref.WeakReference;

class TDialog$THandler extends Handler
{
  private TDialog.OnTimeListener mL;

  public TDialog$THandler(TDialog.OnTimeListener paramOnTimeListener, Looper paramLooper)
  {
    super(paramLooper);
    this.mL = paramOnTimeListener;
  }

  public void handleMessage(Message paramMessage)
  {
    switch (paramMessage.what)
    {
    default:
    case 1:
    case 2:
    case 3:
    case 4:
    case 5:
    }
    do
    {
      do
      {
        do
        {
          return;
          TDialog.OnTimeListener.access$000(this.mL, (String)paramMessage.obj);
          return;
          this.mL.onCancel();
          return;
        }
        while ((TDialog.access$100() == null) || (TDialog.access$100().get() == null));
        TDialog.access$200((Context)TDialog.access$100().get(), (String)paramMessage.obj);
        return;
      }
      while ((TDialog.access$300() == null) || (TDialog.access$300().get() == null));
      ((View)TDialog.access$300().get()).setVisibility(8);
      return;
    }
    while ((TDialog.access$100() == null) || (TDialog.access$100().get() == null));
    TDialog.access$400((Context)TDialog.access$100().get(), (String)paramMessage.obj);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.sdkutil.TDialog.THandler
 * JD-Core Version:    0.6.0
 */