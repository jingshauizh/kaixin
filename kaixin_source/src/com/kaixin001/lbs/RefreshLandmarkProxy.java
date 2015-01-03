package com.kaixin001.lbs;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.kaixin001.activity.KXApplication;
import com.kaixin001.engine.LocationEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.item.Landmark;
import com.kaixin001.model.LandmarkModel;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;

public class RefreshLandmarkProxy
{
  public static final int GET_LOCATION_OR_BUILDINGLIST_FAILED = 4;
  public static final String NONE_AVAILABLE_LOCATION = "位置加载中";
  public static final int REFRESH_GPS_RESTART = 14;
  public static final int SELECT_LOCATION_COMPLETED = 6;
  IRefreshLandmarkListCallback callback;
  Context context;
  private AsyncTask<Location, Void, Integer> getLandmarksTask = null;
  private LandmarkAdapter landmarkAdapter;
  private Dialog mAlertDialog = null;
  public RefreshLocationProxy refreshLocationProxy;

  public RefreshLandmarkProxy(Activity paramActivity, IRefreshLandmarkListCallback paramIRefreshLandmarkListCallback)
  {
    this.callback = paramIRefreshLandmarkListCallback;
    this.context = paramActivity;
    this.refreshLocationProxy = new RefreshLocationProxy(paramActivity, new RefreshLocationProxy.IRefreshLocationCallback()
    {
      public void onBeginRefreshLocation()
      {
        RefreshLandmarkProxy.this.callback.onRefreshLandmarks();
      }

      public void onCancelRefreshLocation()
      {
        RefreshLandmarkProxy.this.callback.onCancleRefreshLandmarks();
      }

      public void onLocationAvailable(Location paramLocation)
      {
        RefreshLandmarkProxy.this.doGetLandmarks(paramLocation);
      }

      public void onLocationFailed()
      {
        RefreshLandmarkProxy.this.callback.onGetLandmarksFailed();
      }
    });
  }

  private void cancleGetLandmarksTask()
  {
    if ((this.getLandmarksTask != null) && (!this.getLandmarksTask.isCancelled()) && (this.getLandmarksTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.getLandmarksTask.cancel(true);
      this.getLandmarksTask = null;
    }
  }

  private ArrayList<Landmark> getLandmarks()
  {
    return LandmarkModel.getInstance().getLandmarks();
  }

  public void doGetLandmarks(Location paramLocation)
  {
    cancleGetLandmarksTask();
    this.getLandmarksTask = new AsyncTask(paramLocation)
    {
      protected Integer doInBackground(Location[] paramArrayOfLocation)
      {
        try
        {
          Integer localInteger = Integer.valueOf(LocationEngine.getInstance().doGetLandmarks(KXApplication.getInstance(), this.val$location, LocationService.getLocationService().lastBestMapABCLocation));
          return localInteger;
        }
        catch (SecurityErrorException localSecurityErrorException)
        {
        }
        return null;
      }

      protected void onPostExecute(Integer paramInteger)
      {
        if ((paramInteger == null) || (paramInteger.intValue() != 1))
        {
          RefreshLandmarkProxy.this.callback.onGetLandmarksFailed();
          return;
        }
        try
        {
          if (RefreshLandmarkProxy.this.isLandmarkListExist())
          {
            LandmarkModel.getInstance().selectIndex = 0;
            String str = ((Landmark)RefreshLandmarkProxy.this.getLandmarks().get(LandmarkModel.getInstance().selectIndex)).name;
            RefreshLandmarkProxy.this.callback.onSelectLandmark(str);
            return;
          }
        }
        catch (Exception localException)
        {
          KXLog.e("LocaltionUtil", "onPostExecute", localException);
          return;
        }
        RefreshLandmarkProxy.this.callback.onGetLandmarksFailed();
      }
    };
    this.getLandmarksTask.execute(new Location[] { paramLocation });
  }

  public void forceRefreshLandmarks()
  {
    this.refreshLocationProxy.refreshLocation(true);
  }

  public boolean getIncludeLocationPreference()
  {
    if (this.context == null)
      return true;
    return PreferenceManager.getDefaultSharedPreferences(this.context).getBoolean("IncludeLocation", true);
  }

  public Location getLocation()
  {
    return LandmarkModel.getInstance().getLocation();
  }

  public boolean isLandmarkListExist()
  {
    return (getLandmarks() != null) && (getLandmarks().size() > 0);
  }

  public boolean isLocationValid()
  {
    Location localLocation = LandmarkModel.getInstance().getLocation();
    return LocationService.getLocationService().isLocationValid(localLocation);
  }

  public void refreshLandmarks()
  {
    LandmarkModel localLandmarkModel = LandmarkModel.getInstance();
    if (LocationService.getLocationService().isLocationValid(localLandmarkModel.getLocation()))
    {
      ArrayList localArrayList = getLandmarks();
      if ((localArrayList != null) && (localLandmarkModel.selectIndex >= 0) && (localLandmarkModel.selectIndex < localArrayList.size()))
      {
        String str = ((Landmark)localArrayList.get(localLandmarkModel.selectIndex)).name;
        this.callback.onSelectLandmark(str);
      }
      return;
    }
    this.refreshLocationProxy.refreshLocation(false);
  }

  public void saveIncludeLocationPreference(boolean paramBoolean)
  {
    if (this.context == null)
      return;
    SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this.context).edit();
    localEditor.putBoolean("IncludeLocation", paramBoolean);
    localEditor.commit();
  }

  public void showLandmarks(Context paramContext)
  {
    if (isLandmarkListExist())
    {
      if (this.mAlertDialog != null)
      {
        if (this.mAlertDialog.isShowing())
          this.mAlertDialog.dismiss();
        this.mAlertDialog = null;
      }
      AlertDialog.Builder localBuilder = new AlertDialog.Builder(paramContext);
      localBuilder.setTitle(2131427717);
      this.landmarkAdapter = new LandmarkAdapter();
      localBuilder.setAdapter(this.landmarkAdapter, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          paramDialogInterface.dismiss();
          LandmarkModel.getInstance().selectIndex = paramInt;
          String str = ((Landmark)RefreshLandmarkProxy.this.getLandmarks().get(paramInt)).name;
          RefreshLandmarkProxy.this.callback.onSelectLandmark(str);
        }
      });
      localBuilder.setPositiveButton(2131427714, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          paramDialogInterface.dismiss();
          RefreshLandmarkProxy.this.refreshLocationProxy.refreshLocation(true);
        }
      });
      localBuilder.setNegativeButton(2131427587, null);
      this.mAlertDialog = localBuilder.show();
    }
  }

  public static abstract interface IRefreshLandmarkListCallback
  {
    public abstract void onCancleRefreshLandmarks();

    public abstract void onGetLandmarksFailed();

    public abstract void onRefreshLandmarks();

    public abstract void onSelectLandmark(String paramString);
  }

  private class LandmarkAdapter extends BaseAdapter
  {
    LayoutInflater inflater = null;
    ArrayList<Landmark> landmarks = new ArrayList(RefreshLandmarkProxy.this.getLandmarks());

    public LandmarkAdapter()
    {
    }

    public int getCount()
    {
      if (this.landmarks != null)
        return this.landmarks.size();
      return 0;
    }

    public Object getItem(int paramInt)
    {
      return Integer.valueOf(paramInt);
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      if (paramView == null);
      View localView2;
      for (View localView1 = this.inflater.inflate(2130903204, null); ; localView1 = paramView)
      {
        Landmark localLandmark = (Landmark)this.landmarks.get(paramInt);
        ((TextView)localView1.findViewById(2131362972)).setText(localLandmark.name);
        ((TextView)localView1.findViewById(2131362973)).setText(localLandmark.address);
        localView2 = localView1.findViewById(2131362971);
        if (LandmarkModel.getInstance().selectIndex != paramInt)
          break;
        localView2.setVisibility(0);
        return localView1;
      }
      localView2.setVisibility(4);
      return localView1;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.lbs.RefreshLandmarkProxy
 * JD-Core Version:    0.6.0
 */