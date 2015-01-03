package com.kaixin001.lbs;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.amap.mapapi.location.LocationManagerProxy;
import com.kaixin001.lbs.cell.GeoLocationManager;
import com.kaixin001.model.Setting;
import com.kaixin001.util.KXLog;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.ReentrantLock;

public class LocationService
{
  public static final int GETLOCATION_FAILED = 0;
  private static final long LOCATION_INVALID_TIME = 600000L;
  public static final int LOCATION_RECEIVED = 15;
  public static final int MAX_LOCATION_QUEUE = 5;
  private static final int RECENT_TRIP_TIME = 30000;
  public static final int REFRESHGPS_NOPROVIDER = 8;
  public static final int SERVICE_DISABLE = 10;
  private static final String TAG = "LocationService";
  public static final int THRESHOLD_GPS = 30;
  public static final int THRESHOLD_NETWORK = 80;
  public static final int THRESHOLD_WIFI = 155;
  private static final long TIME_OF_MIDDLE_TRIP_TIMEOUT = 20000L;
  private static final long TIME_OF_SHORT_TRIP_TIMEOUT = 10000L;
  private static final long TIME_OF_TRIP_TIMEOUT = 30000L;
  public static final String WIFI_PROVIDER = "wifi";
  private static volatile LocationService locationUtil = null;
  private Context context = null;
  ArrayList<Location> gpsLocationQueue = new ArrayList(5);
  ReentrantLock gpsLock = new ReentrantLock();
  volatile boolean isRefreshing = false;
  public Location lastBestMapABCLocation;
  public Location lastNoticedLocation;
  private final KXLocationListener locationListener = new KXLocationListener(null);
  private LocationManager locationManager = null;
  private ArrayList<WeakReference<LocationRequester>> locationRequesters;
  private GeoLocationManager mGeoLocationManager;
  ArrayList<Location> mapABCLocationQueue = new ArrayList(5);
  ReentrantLock mapABCLock = new ReentrantLock();
  private LocationManagerProxy mapAbcLocationManager = null;
  ArrayList<Location> networkLocationQueue = new ArrayList(5);
  ReentrantLock networkLock = new ReentrantLock();
  private ReentrantLock requesterListLock = new ReentrantLock();
  long shortTripTimeout = 10000L;
  private Timer shortTripTimer;
  private Handler timerHandler = new Handler()
  {
    public void handleMessage(Message paramMessage)
    {
      if ((LocationService.this.locationManager == null) || (paramMessage == null))
        return;
      LocationService.this.cancleWholeTripTimer();
      LocationService.this.cancleShortTripTimer();
      Location localLocation = LocationService.this.getLocationByMinTime(600000L);
      if (localLocation != null)
      {
        LocationService.this.notifyRequesters(15, localLocation);
        return;
      }
      KXLog.d("LocationService", "Time Out: No Data");
      LocationService.this.notifyRequesters(0, null);
    }
  };
  private Timer wholeTripTimer;
  ArrayList<Location> wifiLocationQueue = new ArrayList(5);
  ReentrantLock wifiLock = new ReentrantLock();

  private void addLocation(ArrayList<Location> paramArrayList, Location paramLocation, ReentrantLock paramReentrantLock)
  {
    while (true)
    {
      int i;
      try
      {
        paramReentrantLock.lock();
        if (paramArrayList.size() < 0)
          continue;
        i = -1 + paramArrayList.size();
        if (i < 0)
        {
          int j = paramArrayList.size();
          if (j < 5)
            continue;
          paramArrayList.remove(j - 1);
          paramArrayList.add(paramLocation);
          return;
          if (isLocationValid((Location)paramArrayList.get(0)))
            continue;
          paramArrayList.remove(0);
          continue;
        }
      }
      finally
      {
        paramReentrantLock.unlock();
      }
      if (((Location)paramArrayList.get(i)).getAccuracy() >= paramLocation.getAccuracy())
        paramArrayList.remove(i);
      i--;
    }
  }

  private void addLocationFromOtherApps(long paramLong)
  {
    Location localLocation = getLastBestLocation(1000, paramLong);
    if ((localLocation != null) && (localLocation.getProvider() != null))
    {
      if (!localLocation.getProvider().equals("gps"))
        break label46;
      addLocation(this.gpsLocationQueue, localLocation, this.gpsLock);
    }
    label46: 
    do
      return;
    while (!localLocation.getProvider().equals("network"));
    addLocation(this.networkLocationQueue, localLocation, this.networkLock);
  }

  private void addLocationRequester(LocationRequester paramLocationRequester)
  {
    try
    {
      this.requesterListLock.lock();
      if (this.locationRequesters == null)
        this.locationRequesters = new ArrayList();
      int i = 0;
      for (int j = -1 + this.locationRequesters.size(); ; j--)
      {
        if (j < 0)
        {
          if (i == 0)
            this.locationRequesters.add(new WeakReference(paramLocationRequester));
          return;
        }
        LocationRequester localLocationRequester = (LocationRequester)((WeakReference)this.locationRequesters.get(j)).get();
        if (localLocationRequester != paramLocationRequester)
          continue;
        i = 1;
      }
    }
    finally
    {
      this.requesterListLock.unlock();
    }
    throw localObject;
  }

  private void cancleShortTripTimer()
  {
    if (this.shortTripTimer != null)
    {
      this.shortTripTimer.cancel();
      this.shortTripTimer = null;
    }
  }

  private void cancleWholeTripTimer()
  {
    if (this.wholeTripTimer != null)
    {
      this.wholeTripTimer.cancel();
      this.wholeTripTimer = null;
    }
  }

  private void cleanLocation(ArrayList<Location> paramArrayList, ReentrantLock paramReentrantLock, long paramLong)
  {
    try
    {
      paramReentrantLock.lock();
      while (true)
      {
        int i = paramArrayList.size();
        if (i >= 0);
        do
          return;
        while (isLocationValid((Location)paramArrayList.get(0), paramLong));
        paramArrayList.remove(0);
      }
    }
    finally
    {
      paramReentrantLock.unlock();
    }
    throw localObject;
  }

  private LocationManagerProxy createMapAbcLocationManager()
  {
    return LocationManagerProxy.getInstance(this.context);
  }

  private Location getBestLocation(ArrayList<Location> paramArrayList, ReentrantLock paramReentrantLock, long paramLong)
  {
    try
    {
      paramReentrantLock.lock();
      int i = 0;
      while (true)
      {
        int j = paramArrayList.size();
        if (i >= j)
          return null;
        Location localLocation = (Location)paramArrayList.get(i);
        if (isLocationValid(localLocation))
        {
          long l1 = System.currentTimeMillis();
          long l2 = localLocation.getTime();
          long l3 = l1 - l2;
          if ((0L < l3) && (l3 < paramLong))
            return localLocation;
          i++;
          continue;
        }
        paramArrayList.remove(i);
      }
    }
    finally
    {
      paramReentrantLock.unlock();
    }
    throw localObject;
  }

  private Location getLastBestLocation(int paramInt, long paramLong)
  {
    Object localObject = null;
    float f1 = 3.4028235E+38F;
    long l1 = 9223372036854775807L;
    Iterator localIterator = this.locationManager.getAllProviders().iterator();
    while (true)
    {
      if (!localIterator.hasNext())
      {
        if ((l1 > paramLong) || (f1 > paramInt))
          localObject = null;
        return localObject;
      }
      String str = (String)localIterator.next();
      Location localLocation = this.locationManager.getLastKnownLocation(str);
      if (localLocation == null)
        continue;
      float f2 = localLocation.getAccuracy();
      long l2 = System.currentTimeMillis() - localLocation.getTime();
      if ((l2 < paramLong) && (f2 < f1))
      {
        localObject = localLocation;
        f1 = f2;
        l1 = l2;
        continue;
      }
      if ((l2 <= paramLong) || (f1 != 3.4028235E+38F) || (l2 >= l1))
        continue;
      localObject = localLocation;
      l1 = l2;
    }
  }

  public static LocationService getLocationService()
  {
    if (locationUtil == null)
      monitorenter;
    try
    {
      if (locationUtil == null)
        locationUtil = new LocationService();
      return locationUtil;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public static boolean isMapABCLocation(Location paramLocation)
  {
    if (paramLocation == null);
    String str;
    do
    {
      return false;
      str = paramLocation.getProvider();
    }
    while ((!"lbs".equals(str)) && (!TextUtils.isEmpty(str)));
    return true;
  }

  private static boolean isValidCoordinate(String paramString)
  {
    if (TextUtils.isEmpty(paramString));
    do
      return false;
    while (paramString.equals("0.0"));
    return true;
  }

  private boolean isWifiEnabled(Context paramContext)
  {
    return true;
  }

  public static boolean locationUpdated(Location paramLocation1, Location paramLocation2)
  {
    return (paramLocation1.getLatitude() != paramLocation2.getLatitude()) || (paramLocation1.getLongitude() != paramLocation2.getLongitude()) || (paramLocation1.getAccuracy() != paramLocation2.getAccuracy()) || (StrictMath.abs(paramLocation1.getTime() - paramLocation2.getTime()) > 60000L);
  }

  private void notifyRequesters(int paramInt, Location paramLocation)
  {
    try
    {
      this.requesterListLock.lock();
      this.lastNoticedLocation = paramLocation;
      if (this.locationRequesters != null)
      {
        boolean bool = this.locationRequesters.isEmpty();
        if (!bool);
      }
      else
      {
        return;
      }
      int i = -1 + this.locationRequesters.size();
      while (true)
      {
        if (i < 0)
        {
          stopRefreshLocation(false);
          return;
        }
        LocationRequester localLocationRequester = (LocationRequester)((WeakReference)this.locationRequesters.get(i)).get();
        if (localLocationRequester != null);
        try
        {
          localLocationRequester.notifyChange(paramInt, paramLocation);
          this.locationRequesters.remove(i);
          i--;
        }
        catch (Exception localException)
        {
          while (true)
            localException.printStackTrace();
        }
      }
    }
    finally
    {
      this.requesterListLock.unlock();
    }
    throw localObject;
  }

  private void quickReturnLocation(Location paramLocation1, Location paramLocation2)
  {
    cancleWholeTripTimer();
    cancleShortTripTimer();
    this.lastBestMapABCLocation = paramLocation2;
    this.shortTripTimeout = 10000L;
    notifyRequesters(15, paramLocation1);
  }

  private void refreshLocation()
  {
    KXLog.d("LocationService", "begin refresh location ");
    removeLocationListener();
    cleanLocation(this.gpsLocationQueue, this.gpsLock, 600000L);
    cleanLocation(this.wifiLocationQueue, this.wifiLock, 600000L);
    cleanLocation(this.networkLocationQueue, this.networkLock, 0L);
    cleanLocation(this.mapABCLocationQueue, this.mapABCLock, 600000L);
    this.isRefreshing = true;
    this.mapAbcLocationManager = createMapAbcLocationManager();
    try
    {
      Iterator localIterator = this.mapAbcLocationManager.getProviders(true).iterator();
      while (true)
      {
        boolean bool = localIterator.hasNext();
        if (!bool)
        {
          this.wholeTripTimer = new Timer();
          TimeOutTask localTimeOutTask = new TimeOutTask(null);
          this.wholeTripTimer.schedule(localTimeOutTask, 30000L);
          return;
        }
        str = (String)localIterator.next();
        if (!"gps".equals(str))
          break;
        KXLog.d("LocationService", "requestLocationUpdates by NETWORK_PROVIDER");
        this.locationManager.requestLocationUpdates("gps", 0L, 0.0F, this.locationListener);
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        String str;
        localException.printStackTrace();
        continue;
        if (!"lbs".equals(str))
          continue;
        KXLog.d("LocationService", "requestLocationUpdates by NETWORK_PROVIDER");
        this.mapAbcLocationManager.requestLocationUpdates("lbs", 0L, 0.0F, this.locationListener);
      }
    }
  }

  private void removeLocationListener()
  {
    if (this.locationManager != null)
      this.locationManager.removeUpdates(this.locationListener);
    if (this.mapAbcLocationManager != null)
    {
      this.mapAbcLocationManager.removeUpdates(this.locationListener);
      this.mapAbcLocationManager = null;
    }
    if (this.mGeoLocationManager != null)
    {
      this.mGeoLocationManager.removeUpdates();
      this.mGeoLocationManager = null;
    }
    this.isRefreshing = false;
  }

  private boolean startShortTripTimer()
  {
    if (this.shortTripTimer == null)
    {
      this.shortTripTimer = new Timer();
      TimeOutTask localTimeOutTask = new TimeOutTask(null);
      this.shortTripTimer.schedule(localTimeOutTask, this.shortTripTimeout);
      return true;
    }
    return false;
  }

  public void addTestLocation(Location paramLocation)
  {
    if (Setting.getInstance().isDebug())
      addLocation(this.gpsLocationQueue, paramLocation, this.gpsLock);
  }

  public void destroy()
  {
    stopRefreshLocation(true);
    this.context = null;
    try
    {
      this.requesterListLock.lock();
      this.locationRequesters.clear();
      return;
    }
    finally
    {
      this.requesterListLock.unlock();
    }
    throw localObject;
  }

  public void forceRefreshLocation(LocationRequester paramLocationRequester)
  {
    if (!isLocationProviderEnabled())
      paramLocationRequester.notifyChange(10, null);
    do
    {
      return;
      addLocationRequester(paramLocationRequester);
    }
    while (this.isRefreshing);
    refreshLocation();
  }

  public Location getLocationByMinTime(long paramLong)
  {
    addLocationFromOtherApps(paramLong);
    Location localLocation1 = getBestLocation(this.gpsLocationQueue, this.gpsLock, paramLong);
    Location localLocation2 = getBestLocation(this.wifiLocationQueue, this.wifiLock, paramLong);
    Location localLocation3 = getBestLocation(this.networkLocationQueue, this.networkLock, paramLong);
    Location localLocation4 = getBestLocation(this.mapABCLocationQueue, this.mapABCLock, paramLong);
    this.lastBestMapABCLocation = null;
    this.shortTripTimeout = 10000L;
    if (isLocationValid(localLocation1))
      localLocation3 = localLocation1;
    while (true)
    {
      return localLocation3;
      if ((isLocationValid(localLocation2)) && (localLocation2.getAccuracy() < 200.0F))
        return localLocation2;
      Location localLocation5;
      if ((isLocationValid(localLocation3)) && (localLocation3.getAccuracy() < 200.0F))
      {
        if (!isLocationValid(localLocation2))
          break label272;
        localLocation5 = localLocation2;
      }
      while (true)
      {
        if (localLocation5 != null)
        {
          float[] arrayOfFloat = new float[3];
          Location.distanceBetween(localLocation5.getLatitude(), localLocation5.getLongitude(), localLocation3.getLatitude(), localLocation3.getLongitude(), arrayOfFloat);
          if (arrayOfFloat[0] < localLocation3.getAccuracy() + localLocation5.getAccuracy())
            break;
          KXLog.d("LocationService", "network location is invalid, below is the data:");
          KXLog.d("LocationService", localLocation3.toString());
          KXLog.d("LocationService", localLocation5.toString());
        }
        this.shortTripTimeout = 20000L;
        if (!isLocationValid(localLocation2))
          break label315;
        if (!isLocationValid(localLocation4))
          break label312;
        if (localLocation2.getAccuracy() >= localLocation4.getAccuracy())
          break label294;
        return localLocation2;
        label272: if (isLocationValid(localLocation4))
        {
          localLocation5 = localLocation4;
          continue;
        }
        localLocation5 = null;
      }
      label294: this.lastBestMapABCLocation = localLocation4;
      if (!isLocationValid(localLocation3))
      {
        return localLocation4;
        label312: return localLocation2;
        label315: if (!isLocationValid(localLocation3))
          break;
        if (!isLocationValid(localLocation4))
          continue;
        this.lastBestMapABCLocation = localLocation4;
        return localLocation3;
      }
    }
    if (isLocationValid(localLocation4))
    {
      this.lastBestMapABCLocation = localLocation4;
      return localLocation4;
    }
    return null;
  }

  public boolean isLocationProviderEnabled()
  {
    boolean bool1;
    try
    {
      boolean bool3 = this.locationManager.isProviderEnabled("gps");
      bool1 = bool3;
      if (bool1);
    }
    catch (Exception localException1)
    {
      try
      {
        boolean bool2 = this.locationManager.isProviderEnabled("network");
        bool1 = bool2;
        return bool1;
        localException1 = localException1;
        localException1.printStackTrace();
        bool1 = false;
      }
      catch (Exception localException2)
      {
        localException2.printStackTrace();
      }
    }
    return bool1;
  }

  public boolean isLocationValid(Location paramLocation)
  {
    return isLocationValid(paramLocation, 600000L);
  }

  public boolean isLocationValid(Location paramLocation, long paramLong)
  {
    if (paramLocation == null);
    long l;
    do
    {
      return false;
      l = System.currentTimeMillis() - paramLocation.getTime();
    }
    while ((0L >= l) || (l >= paramLong));
    return true;
  }

  public void removeLocationRequester(LocationRequester paramLocationRequester)
  {
    try
    {
      this.requesterListLock.lock();
      if (this.locationRequesters != null)
      {
        boolean bool = this.locationRequesters.isEmpty();
        if (!bool);
      }
      else
      {
        return;
      }
      int i = this.locationRequesters.size();
      for (int j = i - 1; ; j--)
      {
        if (j < 0)
          return;
        if ((LocationRequester)((WeakReference)this.locationRequesters.get(j)).get() != paramLocationRequester)
          continue;
        this.locationRequesters.remove(j);
      }
    }
    finally
    {
      this.requesterListLock.unlock();
    }
    throw localObject;
  }

  public void requestRefreshLocation(LocationRequester paramLocationRequester)
  {
    try
    {
      addLocationFromOtherApps(600000L);
      Location localLocation = getLocationByMinTime(600000L);
      if (localLocation != null)
      {
        paramLocationRequester.notifyChange(15, localLocation);
        return;
      }
      forceRefreshLocation(paramLocationRequester);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void setContext(Context paramContext)
  {
    this.context = paramContext;
    if (paramContext == null)
      return;
    this.locationManager = ((LocationManager)paramContext.getSystemService("location"));
  }

  public void stopRefreshLocation(boolean paramBoolean)
  {
    if (this.locationManager != null)
      this.locationManager.removeUpdates(this.locationListener);
    if (this.mapAbcLocationManager != null)
    {
      this.mapAbcLocationManager.removeUpdates(this.locationListener);
      if (paramBoolean)
        this.mapAbcLocationManager.destory();
      this.mapAbcLocationManager = null;
    }
    if (this.mGeoLocationManager != null)
    {
      this.mGeoLocationManager.removeUpdates();
      this.mGeoLocationManager = null;
    }
    this.isRefreshing = false;
    cancleWholeTripTimer();
    cancleShortTripTimer();
  }

  private class KXLocationListener
    implements LocationListener
  {
    private KXLocationListener()
    {
    }

    private void onGotGPSLocation(Location paramLocation)
    {
      LocationService.this.addLocation(LocationService.this.gpsLocationQueue, paramLocation, LocationService.this.gpsLock);
      LocationService.this.quickReturnLocation(paramLocation, null);
    }

    private void onGotMapABCLocation(Location paramLocation)
    {
      paramLocation.setTime(System.currentTimeMillis());
      LocationService.this.addLocation(LocationService.this.mapABCLocationQueue, paramLocation, LocationService.this.mapABCLock);
      LocationService.this.quickReturnLocation(paramLocation, null);
    }

    private void onGotNetworkLocation(Location paramLocation)
    {
      LocationService.this.addLocation(LocationService.this.networkLocationQueue, paramLocation, LocationService.this.networkLock);
      LocationService.this.startShortTripTimer();
    }

    private void onGotWifiLocation(Location paramLocation)
    {
      LocationService.this.addLocation(LocationService.this.wifiLocationQueue, paramLocation, LocationService.this.wifiLock);
      if (paramLocation.getAccuracy() < 155.0F)
      {
        LocationService.this.quickReturnLocation(paramLocation, null);
        return;
      }
      LocationService.this.startShortTripTimer();
    }

    public void onLocationChanged(Location paramLocation)
    {
      KXLog.d("LocationService", "onLocationChanged");
      if (paramLocation == null)
        KXLog.d("LocationService", "location=null is returned");
      do
      {
        do
          return;
        while ((LocationService.isMapABCLocation(paramLocation)) && (!LocationService.access$5(String.valueOf(paramLocation.getLongitude()))) && (!LocationService.access$5(String.valueOf(paramLocation.getLatitude()))));
        KXLog.d("LocationService", paramLocation.toString());
        Bundle localBundle = paramLocation.getExtras();
        if (localBundle != null)
        {
          KXLog.d("LocationService", localBundle.getString("networkLocationSource"));
          KXLog.d("LocationService", localBundle.getString("networkLocationType"));
        }
        if (paramLocation.getProvider().equals("gps"))
        {
          onGotGPSLocation(paramLocation);
          return;
        }
        if (paramLocation.getProvider().equals("wifi"))
        {
          onGotWifiLocation(paramLocation);
          return;
        }
        if (!paramLocation.getProvider().equals("network"))
          continue;
        onGotNetworkLocation(paramLocation);
        return;
      }
      while (!LocationService.isMapABCLocation(paramLocation));
      onGotMapABCLocation(paramLocation);
    }

    public void onProviderDisabled(String paramString)
    {
    }

    public void onProviderEnabled(String paramString)
    {
    }

    public void onStatusChanged(String paramString, int paramInt, Bundle paramBundle)
    {
    }
  }

  private class TimeOutTask extends TimerTask
  {
    private TimeOutTask()
    {
    }

    public void run()
    {
      Message localMessage = Message.obtain();
      LocationService.this.timerHandler.sendMessage(localMessage);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.lbs.LocationService
 * JD-Core Version:    0.6.0
 */