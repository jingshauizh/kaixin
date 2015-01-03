package com.kaixin001.task;

import android.content.Context;
import android.os.AsyncTask;
import com.kaixin001.engine.NavigatorApplistEngine;
import com.kaixin001.engine.SecurityErrorException;

public class MenuTask
{
  public static class GetCommonAppDataTask extends AsyncTask<Void, Void, Integer>
  {
    Context context;

    public GetCommonAppDataTask(Context paramContext)
    {
      this.context = paramContext;
    }

    protected Integer doInBackground(Void[] paramArrayOfVoid)
    {
      try
      {
        Integer localInteger = NavigatorApplistEngine.getInstance().getKXAppslistData(this.context);
        return localInteger;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
      }
      return null;
    }
  }

  public static class GetKXAppDataTask extends AsyncTask<Void, Void, Integer>
  {
    Context context;

    public GetKXAppDataTask(Context paramContext)
    {
      this.context = paramContext;
    }

    protected Integer doInBackground(Void[] paramArrayOfVoid)
    {
      try
      {
        Integer localInteger = NavigatorApplistEngine.getInstance().getKXGameslistData(this.context);
        return localInteger;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
      }
      return null;
    }
  }

  public static class GetThirdAppDataTask extends AsyncTask<Object, Void, Integer>
  {
    Context context;

    public GetThirdAppDataTask(Context paramContext)
    {
      this.context = paramContext;
    }

    protected Integer doInBackground(Object[] paramArrayOfObject)
    {
      try
      {
        Integer localInteger1 = (Integer)paramArrayOfObject[0];
        Integer localInteger2 = (Integer)paramArrayOfObject[1];
        Boolean localBoolean = (Boolean)paramArrayOfObject[2];
        Integer localInteger3 = NavigatorApplistEngine.getInstance().getThirdApplistData(this.context, localInteger1.intValue(), localInteger2.intValue(), localBoolean.booleanValue());
        return localInteger3;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
      }
      return null;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.task.MenuTask
 * JD-Core Version:    0.6.0
 */