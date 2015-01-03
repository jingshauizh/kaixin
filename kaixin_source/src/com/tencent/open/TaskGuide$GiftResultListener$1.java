package com.tencent.open;

class TaskGuide$GiftResultListener$1
  implements Runnable
{
  public void run()
  {
    if (this.this$1.index == 0);
    for (TaskGuide.TaskState localTaskState = TaskGuide.access$200(this.this$1.this$0); ; localTaskState = TaskGuide.access$300(this.this$1.this$0))
    {
      if (localTaskState == TaskGuide.TaskState.WAITTING_BACK_REWARD)
      {
        TaskGuide.access$3600(this.this$1.this$0, this.this$1.index, TaskGuide.TaskState.NORAML);
        TaskGuide.access$3800(this.this$1.this$0, "领取失败 :" + this.val$e.getClass().getName());
      }
      TaskGuide.access$600(this.this$1.this$0, this.this$1.index);
      TaskGuide.access$2400(this.this$1.this$0, 2000);
      return;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.open.TaskGuide.GiftResultListener.1
 * JD-Core Version:    0.6.0
 */