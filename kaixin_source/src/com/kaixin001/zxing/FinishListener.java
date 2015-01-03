package com.kaixin001.zxing;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;

public final class FinishListener
  implements DialogInterface.OnClickListener, DialogInterface.OnCancelListener, Runnable
{
  private final Activity activityToFinish;

  public FinishListener(Activity paramActivity)
  {
    this.activityToFinish = paramActivity;
  }

  public void onCancel(DialogInterface paramDialogInterface)
  {
    run();
  }

  public void onClick(DialogInterface paramDialogInterface, int paramInt)
  {
    run();
  }

  public void run()
  {
    this.activityToFinish.finish();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.zxing.FinishListener
 * JD-Core Version:    0.6.0
 */