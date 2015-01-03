package com.tencent.open;

import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Interpolator;

class TaskGuide$CollapseExpandRunnable
  implements Runnable
{
  boolean expand = false;
  float index = 0.0F;

  public TaskGuide$CollapseExpandRunnable(TaskGuide paramTaskGuide, boolean paramBoolean)
  {
    this.expand = paramBoolean;
  }

  public void run()
  {
    int i = 1;
    SystemClock.currentThreadTimeMillis();
    this.index = (float)(0.1D + this.index);
    float f = this.index;
    if (f > 1.0F)
      f = 1.0F;
    int j;
    int k;
    if (f >= 1.0F)
    {
      j = i;
      k = (int)(TaskGuide.access$2600(this.this$0).getInterpolation(f) * TaskGuide.access$2700(this.this$0));
      if (!this.expand)
        break label171;
      TaskGuide.access$1000(this.this$0).y = (k + TaskGuide.access$2800(this.this$0));
      label99: Log.d("TAG", "mWinParams.y = " + TaskGuide.access$1000(this.this$0).y + "deltaDistence = " + k);
      if (TaskGuide.access$000(this.this$0))
        break label194;
    }
    while (true)
    {
      if (i == 0)
        break label226;
      TaskGuide.access$3000(this.this$0);
      return;
      j = 0;
      break;
      label171: TaskGuide.access$1000(this.this$0).y = (TaskGuide.access$2800(this.this$0) - k);
      break label99;
      label194: TaskGuide.access$2900(this.this$0).updateViewLayout(TaskGuide.access$100(this.this$0), TaskGuide.access$1000(this.this$0));
      i = j;
    }
    label226: TaskGuide.access$3200(this.this$0).postDelayed(TaskGuide.access$3100(this.this$0), 5L);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.open.TaskGuide.CollapseExpandRunnable
 * JD-Core Version:    0.6.0
 */