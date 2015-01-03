package com.kaixin001.task;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import com.kaixin001.engine.LbsEngine;
import com.kaixin001.lbs.LocationService;

public abstract class GetLbsActivityTask extends AsyncTask<Integer, Void, Integer>
{
  Context ctx;
  Location location;
  Location mapABCLocation;

  public GetLbsActivityTask(Context paramContext, Location paramLocation1, Location paramLocation2)
  {
    this.ctx = paramContext;
    this.location = paramLocation1;
    this.mapABCLocation = paramLocation2;
  }

  protected Integer doInBackground(Integer[] paramArrayOfInteger)
  {
    if (this.ctx == null)
      return null;
    if (paramArrayOfInteger != null);
    label187: 
    while (true)
      try
      {
        if (paramArrayOfInteger.length < 2)
          return Integer.valueOf(0);
        if (!LocationService.isMapABCLocation(this.location))
          continue;
        String str1 = "0";
        String str2 = "0";
        String str3 = String.valueOf(this.location.getLongitude());
        Object localObject = String.valueOf(this.location.getLatitude());
        return Integer.valueOf(LbsEngine.getInstance().getPoisActivityList(this.ctx, str1, str2, str3, (String)localObject, "", paramArrayOfInteger[0].intValue(), paramArrayOfInteger[1].intValue()));
        str1 = String.valueOf(this.location.getLongitude());
        str2 = String.valueOf(this.location.getLatitude());
        if (this.mapABCLocation != null)
          continue;
        str3 = null;
        if (this.mapABCLocation != null)
          continue;
        localObject = null;
        break label187;
        str3 = String.valueOf(this.mapABCLocation.getLongitude());
        continue;
        String str4 = String.valueOf(this.mapABCLocation.getLatitude());
        localObject = str4;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return null;
      }
  }

  protected void onPostExecute(Integer paramInteger)
  {
    if ((this.ctx == null) || (paramInteger == null))
      return;
    try
    {
      onPostExecuteA(paramInteger);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  protected abstract void onPostExecuteA(Integer paramInteger);
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.task.GetLbsActivityTask
 * JD-Core Version:    0.6.0
 */