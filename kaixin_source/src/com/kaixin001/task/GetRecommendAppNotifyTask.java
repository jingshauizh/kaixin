package com.kaixin001.task;

import android.os.AsyncTask;
import com.kaixin001.activity.KXApplication;
import com.kaixin001.engine.RecommendAppNotifyEngine;

public class GetRecommendAppNotifyTask extends AsyncTask<Void, Void, Integer>
{
  protected Integer doInBackground(Void[] paramArrayOfVoid)
  {
    return Integer.valueOf(RecommendAppNotifyEngine.getInstance().getRecommendAppNotify(KXApplication.getInstance()));
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.task.GetRecommendAppNotifyTask
 * JD-Core Version:    0.6.0
 */