package com.kaixin001.task;

import android.os.AsyncTask;
import com.kaixin001.activity.KXApplication;
import com.kaixin001.engine.PhotographEngine;

public class GetPictureActionTask extends AsyncTask<Void, Void, Integer>
{
  protected Integer doInBackground(Void[] paramArrayOfVoid)
  {
    try
    {
      Integer localInteger = Integer.valueOf(PhotographEngine.getInstance().getPhoto(KXApplication.getInstance()));
      return localInteger;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return Integer.valueOf(0);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.task.GetPictureActionTask
 * JD-Core Version:    0.6.0
 */