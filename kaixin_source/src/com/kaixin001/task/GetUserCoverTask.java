package com.kaixin001.task;

import android.os.AsyncTask;
import com.kaixin001.activity.KXApplication;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.util.ImageCache;
import com.kaixin001.util.KXLog;

public class GetUserCoverTask extends AsyncTask<Void, Void, Boolean>
{
  private static final String TAG = "GetUserCoverTask";

  public static Boolean getCover()
  {
    try
    {
      String str = ImageCache.getInstance().getCacheBmpPath(User.getInstance().getUID());
      HttpProxy localHttpProxy = new HttpProxy(KXApplication.getInstance());
      try
      {
        boolean bool2 = localHttpProxy.httpDownload(User.getInstance().getCoverUrl(), str, false, null, null);
        bool1 = bool2;
        return Boolean.valueOf(bool1);
      }
      catch (Exception localException2)
      {
        while (true)
        {
          KXLog.e("GetUserCoverTask", "GetUserCoverTask error", localException2);
          bool1 = false;
        }
      }
    }
    catch (Exception localException1)
    {
      while (true)
      {
        KXLog.e("GetUserCoverTask", "GetUserCoverTask", localException1);
        boolean bool1 = false;
      }
    }
  }

  protected Boolean doInBackground(Void[] paramArrayOfVoid)
  {
    return getCover();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.task.GetUserCoverTask
 * JD-Core Version:    0.6.0
 */