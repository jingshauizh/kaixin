package com.kaixin001.lbs;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.location.Location;
import com.kaixin001.util.DialogUtil;

public class RefreshLocationProxy
{
  Context context;
  boolean forceRefreshLocation = true;
  private IRefreshLocationCallback listener;
  LocationRequester locationRequester;
  boolean needCheckLocationSetting = false;

  public RefreshLocationProxy(Context paramContext, IRefreshLocationCallback paramIRefreshLocationCallback)
  {
    this.context = paramContext;
    this.listener = paramIRefreshLocationCallback;
    this.locationRequester = new LocationRequester()
    {
      public void notifyChange(int paramInt, Location paramLocation)
      {
        switch (paramInt)
        {
        default:
          return;
        case 0:
          RefreshLocationProxy.this.listener.onLocationFailed();
          return;
        case 15:
        }
        RefreshLocationProxy.this.listener.onLocationAvailable(paramLocation);
      }
    };
  }

  public void refreshLocation(boolean paramBoolean)
  {
    if (LocationService.getLocationService().isLocationProviderEnabled())
    {
      this.listener.onBeginRefreshLocation();
      if (paramBoolean)
      {
        LocationService.getLocationService().forceRefreshLocation(this.locationRequester);
        return;
      }
      LocationService.getLocationService().requestRefreshLocation(this.locationRequester);
      return;
    }
    this.needCheckLocationSetting = false;
    DialogUtil.showKXAlertDialog(this.context, 2131427669, 2131427382, 2131427383, new DialogInterface.OnClickListener(paramBoolean)
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        RefreshLocationProxy.this.needCheckLocationSetting = true;
        RefreshLocationProxy.this.forceRefreshLocation = this.val$forceRefreshLocation;
        Intent localIntent1 = new Intent("android.settings.LOCATION_SOURCE_SETTINGS");
        try
        {
          RefreshLocationProxy.this.context.startActivity(localIntent1);
          return;
        }
        catch (Exception localException)
        {
          Intent localIntent2 = new Intent("android.settings.SECURITY_SETTINGS");
          RefreshLocationProxy.this.context.startActivity(localIntent2);
        }
      }
    }
    , new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        RefreshLocationProxy.this.listener.onCancelRefreshLocation();
      }
    });
  }

  public boolean reworkOnResume()
  {
    boolean bool = this.needCheckLocationSetting;
    if (this.needCheckLocationSetting)
    {
      this.needCheckLocationSetting = false;
      if (LocationService.getLocationService().isLocationProviderEnabled())
        refreshLocation(this.forceRefreshLocation);
    }
    else
    {
      return bool;
    }
    this.listener.onCancelRefreshLocation();
    return bool;
  }

  public static abstract interface IRefreshLocationCallback
  {
    public abstract void onBeginRefreshLocation();

    public abstract void onCancelRefreshLocation();

    public abstract void onLocationAvailable(Location paramLocation);

    public abstract void onLocationFailed();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.lbs.RefreshLocationProxy
 * JD-Core Version:    0.6.0
 */