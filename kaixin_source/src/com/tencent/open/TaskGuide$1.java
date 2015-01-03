package com.tencent.open;

import android.view.ViewGroup;

class TaskGuide$1
  implements Runnable
{
  public void run()
  {
    if (TaskGuide.access$000(this.this$0))
    {
      if (this.val$index != 0)
        break label42;
      ((TaskGuide.TaskLinearLayout)TaskGuide.access$100(this.this$0).findViewById(1)).updateView(TaskGuide.access$200(this.this$0));
    }
    label42: 
    do
    {
      do
      {
        return;
        if (this.val$index != 1)
          continue;
        ((TaskGuide.TaskLinearLayout)TaskGuide.access$100(this.this$0).findViewById(2)).updateView(TaskGuide.access$300(this.this$0));
        return;
      }
      while (this.val$index != 2);
      ((TaskGuide.TaskLinearLayout)TaskGuide.access$100(this.this$0).findViewById(1)).updateView(TaskGuide.access$200(this.this$0));
    }
    while (TaskGuide.access$100(this.this$0).getChildCount() <= 1);
    ((TaskGuide.TaskLinearLayout)TaskGuide.access$100(this.this$0).findViewById(2)).updateView(TaskGuide.access$300(this.this$0));
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.open.TaskGuide.1
 * JD-Core Version:    0.6.0
 */