package com.kaixin001.task;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.engine.UpEngine;
import com.kaixin001.util.KXLog;

public class PostUpGeneralTask extends AsyncTask<Object, Void, Integer>
{
  private static final String TAG = "PostUpGeneralTask";
  private Context mContext;
  private OnPostUpListener mListener;

  public PostUpGeneralTask(Context paramContext)
  {
    this.mContext = paramContext;
  }

  protected Integer doInBackground(Object[] paramArrayOfObject)
  {
    String str1 = (String)paramArrayOfObject[0];
    String str2 = (String)paramArrayOfObject[1];
    String str3 = (String)paramArrayOfObject[2];
    try
    {
      Integer localInteger = Integer.valueOf(UpEngine.getInstance().postUp(this.mContext, str1, str2, str3));
      return localInteger;
    }
    catch (SecurityErrorException localSecurityErrorException)
    {
    }
    return null;
  }

  protected void onPostExecute(Integer paramInteger)
  {
    if (this.mListener == null)
    {
      if (paramInteger == null)
      {
        Toast.makeText(this.mContext, 2131427387, 0).show();
        return;
      }
      try
      {
        if (paramInteger.intValue() == 1)
        {
          Toast.makeText(this.mContext, 2131427846, 0).show();
          return;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("PostUpGeneralTask", "onPostExecute", localException);
        return;
      }
      Toast.makeText(this.mContext, 2131427377, 0).show();
      return;
    }
    this.mListener.onPostUp(paramInteger);
  }

  public void setOnPostUpListener(OnPostUpListener paramOnPostUpListener)
  {
    this.mListener = paramOnPostUpListener;
  }

  public static abstract interface OnPostUpListener
  {
    public abstract void onPostUp(Integer paramInteger);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.task.PostUpGeneralTask
 * JD-Core Version:    0.6.0
 */