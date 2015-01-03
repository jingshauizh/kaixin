package com.kaixin001.businesslogic;

import android.content.Context;
import android.os.AsyncTask;
import com.kaixin001.engine.UpEngine;

public class PostUpTask extends AsyncTask<String, Void, Integer>
{
  IPostUpCommand command;
  private Context context;

  public PostUpTask(Context paramContext, IPostUpCommand paramIPostUpCommand)
  {
    this.context = paramContext;
    this.command = paramIPostUpCommand;
  }

  protected Integer doInBackground(String[] paramArrayOfString)
  {
    String str1 = paramArrayOfString[0];
    String str2 = paramArrayOfString[1];
    String str3 = paramArrayOfString[2];
    try
    {
      Integer localInteger = Integer.valueOf(UpEngine.getInstance().postUp(this.context, str1, str2, str3));
      return localInteger;
    }
    catch (Exception localException)
    {
    }
    return Integer.valueOf(0);
  }

  protected void onPostExecute(Integer paramInteger)
  {
    if (paramInteger.intValue() == 1)
    {
      this.command.onResultSuccess();
      return;
    }
    this.command.onResultFailed();
  }

  protected void onPreExecute()
  {
    this.command.onPreExec();
  }

  public void start(String paramString1, String paramString2, String paramString3)
  {
    execute(new String[] { paramString1, paramString2, paramString3 });
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.businesslogic.PostUpTask
 * JD-Core Version:    0.6.0
 */