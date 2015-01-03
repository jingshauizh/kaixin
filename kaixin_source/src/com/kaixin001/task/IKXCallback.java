package com.kaixin001.task;

import android.app.Activity;
import java.lang.ref.WeakReference;

public abstract interface IKXCallback
{
  public static abstract class ICallback
  {
    public WeakReference<Activity> wrActivity;

    public ICallback(WeakReference<Activity> paramWeakReference)
    {
      this.wrActivity = paramWeakReference;
    }

    public abstract void callActivityBussinessLogic();
  }

  public static abstract interface ICallbackForActivity
  {
    public abstract void callActivityBussinessLogic(Integer paramInteger);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.task.IKXCallback
 * JD-Core Version:    0.6.0
 */