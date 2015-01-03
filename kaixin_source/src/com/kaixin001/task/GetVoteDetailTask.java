package com.kaixin001.task;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.engine.VoteEngine;

public class GetVoteDetailTask extends AsyncTask<String, Void, Integer>
{
  Context context;
  Handler handler;

  public GetVoteDetailTask(Context paramContext, Handler paramHandler)
  {
    this.context = paramContext;
    this.handler = paramHandler;
  }

  protected Integer doInBackground(String[] paramArrayOfString)
  {
    String str = paramArrayOfString[0];
    try
    {
      Integer localInteger = Integer.valueOf(VoteEngine.getInstance().doGetVoteDetail(this.context, str));
      return localInteger;
    }
    catch (SecurityErrorException localSecurityErrorException)
    {
    }
    return null;
  }

  protected void onPostExecute(Integer paramInteger)
  {
    if (paramInteger == null)
    {
      Toast.makeText(this.context, 2131427547, 0).show();
      return;
    }
    this.handler.sendEmptyMessage(paramInteger.intValue());
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.task.GetVoteDetailTask
 * JD-Core Version:    0.6.0
 */