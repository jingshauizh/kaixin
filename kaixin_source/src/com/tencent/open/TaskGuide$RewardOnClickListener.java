package com.tencent.open;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

class TaskGuide$RewardOnClickListener
  implements View.OnClickListener
{
  int index;

  public TaskGuide$RewardOnClickListener(TaskGuide paramTaskGuide, int paramInt)
  {
    this.index = paramInt;
  }

  public void onClick(View paramView)
  {
    ((Button)paramView);
    if (TaskGuide.access$400(this.this$0, this.index) == TaskGuide.TaskState.NORAML)
    {
      TaskGuide.access$500(this.this$0, this.index);
      TaskGuide.access$600(this.this$0, this.index);
    }
    TaskGuide.access$700(this.this$0);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.open.TaskGuide.RewardOnClickListener
 * JD-Core Version:    0.6.0
 */