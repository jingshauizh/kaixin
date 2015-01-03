package com.kaixin001.task;

import android.content.Context;
import android.os.AsyncTask;
import com.kaixin001.engine.ChatEngine;
import com.kaixin001.model.User;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;
import java.util.Iterator;

public class GetChatContextTask extends AsyncTask<ArrayList<String>, Void, Integer>
{
  private Context mContext;

  public GetChatContextTask(Context paramContext)
  {
    this.mContext = paramContext;
  }

  protected Integer doInBackground(ArrayList<String>[] paramArrayOfArrayList)
  {
    if ((paramArrayOfArrayList == null) || (paramArrayOfArrayList.length > 1))
      return null;
    Iterator localIterator = paramArrayOfArrayList[0].iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return Integer.valueOf(0);
      String str = (String)localIterator.next();
      if (1 == ChatEngine.getInstance().getCircleContext(this.mContext, User.getInstance().getUID(), str))
      {
        KXLog.d("TEST", "GetChatContextTask success:" + str);
        continue;
      }
      KXLog.d("TEST", "GetChatContextTask failed:" + str);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.task.GetChatContextTask
 * JD-Core Version:    0.6.0
 */