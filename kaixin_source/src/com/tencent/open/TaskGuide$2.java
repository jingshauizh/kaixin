package com.tencent.open;

import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;

class TaskGuide$2
  implements Runnable
{
  public void run()
  {
    TaskGuide.access$102(this.this$0, TaskGuide.access$900(this.this$0, TaskGuide.access$800(this.this$0)));
    TaskGuide.access$1002(this.this$0, TaskGuide.access$1200(this.this$0, TaskGuide.access$1100(this.this$0)));
    TaskGuide.access$1300(this.this$0);
    WindowManager localWindowManager = (WindowManager)TaskGuide.access$1400(this.this$0).getSystemService("window");
    if (((Activity)TaskGuide.access$1500(this.this$0)).isFinishing())
      return;
    if (!TaskGuide.access$000(this.this$0))
      localWindowManager.addView(TaskGuide.access$100(this.this$0), TaskGuide.access$1000(this.this$0));
    TaskGuide.access$002(this.this$0, true);
    TaskGuide.access$600(this.this$0, 2);
    TaskGuide.access$1600(this.this$0);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.open.TaskGuide.2
 * JD-Core Version:    0.6.0
 */