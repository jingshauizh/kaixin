package com.kaixin001.task;

import android.content.Context;
import android.os.AsyncTask;
import com.kaixin001.engine.FriendsEngine;
import com.kaixin001.engine.SecurityErrorException;

public class GetFriendsListTask extends AsyncTask<Integer, Void, Integer>
{
  private Context mContext;

  public GetFriendsListTask(Context paramContext)
  {
    this.mContext = paramContext;
  }

  protected Integer doInBackground(Integer[] paramArrayOfInteger)
  {
    if ((paramArrayOfInteger == null) || (paramArrayOfInteger.length == 0))
      return Integer.valueOf(0);
    try
    {
      if (FriendsEngine.getInstance().getFriendsList(this.mContext.getApplicationContext(), paramArrayOfInteger[0].intValue()))
        return Integer.valueOf(1);
      Integer localInteger = Integer.valueOf(0);
      return localInteger;
    }
    catch (SecurityErrorException localSecurityErrorException)
    {
    }
    return null;
  }

  protected void onPostExecute(Integer paramInteger)
  {
    if ((paramInteger != null) && (paramInteger.intValue() == 0));
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.task.GetFriendsListTask
 * JD-Core Version:    0.6.0
 */