package com.tencent.open;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.RelativeLayout;

class TaskGuide$QQRelativeLayout extends RelativeLayout
{
  int startY = 0;

  public TaskGuide$QQRelativeLayout(TaskGuide paramTaskGuide, Context paramContext)
  {
    super(paramContext);
  }

  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    int i = (int)paramMotionEvent.getY();
    Log.d("XXXX", "onInterceptTouchEvent-- action = " + paramMotionEvent.getAction() + "currentY = " + i);
    TaskGuide.access$2400(this.this$0, 3000);
    switch (paramMotionEvent.getAction())
    {
    default:
    case 0:
    case 1:
    }
    do
    {
      return super.onInterceptTouchEvent(paramMotionEvent);
      this.startY = i;
      return false;
    }
    while (this.startY - i <= 2 * ViewConfiguration.getTouchSlop());
    TaskGuide.access$2500(this.this$0);
    return true;
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    super.onTouchEvent(paramMotionEvent);
    int i = (int)paramMotionEvent.getY();
    Log.d("XXXX", " onTouchEvent-----startY = " + this.startY + "currentY = " + i);
    switch (paramMotionEvent.getAction())
    {
    case 2:
    default:
    case 0:
    case 1:
    }
    while (true)
    {
      return false;
      this.startY = i;
      continue;
      if (this.startY - i <= 2 * ViewConfiguration.getTouchSlop())
        continue;
      TaskGuide.access$2500(this.this$0);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.open.TaskGuide.QQRelativeLayout
 * JD-Core Version:    0.6.0
 */