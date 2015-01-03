package com.kaixin001.pengpeng;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import com.kaixin001.lbs.LocationService;
import com.kaixin001.model.User;
import com.kaixin001.pengpeng.data.BumpFriend;
import com.kaixin001.pengpeng.data.KXDataManager;
import com.kaixin001.pengpeng.data.KXDataUpdateListener;
import com.kaixin001.pengpeng.data.KXRecord;
import com.kaixin001.pengpeng.http.AsyncHttpRequest;
import com.kaixin001.pengpeng.http.HttpException;
import com.kaixin001.pengpeng.http.HttpRequest;
import com.kaixin001.pengpeng.http.HttpRequestCallback;
import com.kaixin001.pengpeng.http.LinkManager;
import com.kaixin001.pengpeng.state.ShakeStateMachine;
import com.kaixin001.util.KXLog;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONException;
import org.json.JSONObject;

public class ShakeApiHelper
  implements KXDataUpdateListener, LocationListener, HttpRequestCallback
{
  private static final String TAG = "ShakeUIHelper";
  private Object httpSequenceLock = new Object();
  private KXAccelerometerService mAcceleService;
  private Context mContext;
  private KXDataManager mDataManager;
  private Location mLocation;
  private Timer mServicePollTimer;
  private ShakeStateMachine mStateMachine;
  private IShakeUIAdapter mUIListener;

  public ShakeApiHelper(Context paramContext, IShakeUIAdapter paramIShakeUIAdapter, Handler paramHandler)
  {
    this.mContext = paramContext;
    this.mUIListener = paramIShakeUIAdapter;
  }

  private void sendDataToServer()
  {
    AsyncHttpRequest localAsyncHttpRequest = new AsyncHttpRequest("", 8000, false, this);
    LinkManager.getInstance(this.mContext).submitRequest(localAsyncHttpRequest);
  }

  private void startPoll()
  {
    KXLog.w("ShakeUIHelper", "--------------------  start poll -----------------");
    if (this.mServicePollTimer != null)
      this.mServicePollTimer.cancel();
    this.mServicePollTimer = new Timer();
    1 local1 = new TimerTask()
    {
      public void run()
      {
        ShakeApiHelper.this.sendDataToServer();
      }
    };
    this.mServicePollTimer.scheduleAtFixedRate(local1, 0L, 500L);
  }

  public void asyncRequestFail(HttpRequest paramHttpRequest, HttpException paramHttpException)
  {
    KXLog.w("ShakeUIHelper", paramHttpException.toString());
    release();
    this.mUIListener.setHttpError();
  }

  public void asyncRequestSuccess(HttpRequest paramHttpRequest, String paramString)
  {
    String str1;
    synchronized (this.httpSequenceLock)
    {
      KXLog.w("ShakeUIHelper", "asyncRequestSuccess witch result>>" + paramString);
      str1 = paramHttpRequest.getRequestTag();
    }
    JSONObject localJSONObject1;
    try
    {
      localJSONObject1 = new JSONObject(paramString);
      int i = localJSONObject1.getInt("ret");
      this.mDataManager.setIntRecord("ret", i);
      if (i != 1)
      {
        if (i == 9001)
          clearRequestQueue();
        monitorexit;
        return;
      }
      if ("bump.getSystime".equals(str1))
      {
        double d1 = System.currentTimeMillis() / 1000.0D;
        double d2 = localJSONObject1.getDouble("systime");
        double d3 = paramHttpRequest.getStartTime() / 1000.0D;
        double d4 = d2 - d3;
        double d5 = 0.5D * (d1 - d3);
        Object[] arrayOfObject7 = new Object[4];
        arrayOfObject7[0] = Double.valueOf(d2);
        arrayOfObject7[1] = Double.valueOf(d3);
        arrayOfObject7[2] = Double.valueOf(d4);
        arrayOfObject7[3] = Double.valueOf(d5);
        KXLog.w("ShakeUIHelper", "[servertime] = %f    [jobtime]=%f   [maxoffset]=%f [latency]=%f", arrayOfObject7);
        this.mDataManager.setDoubleRecord("upoffset", d4);
        this.mDataManager.setDoubleRecord("latency", d5);
        int i5;
        if (this.mStateMachine.getState() == 0)
        {
          i5 = this.mDataManager.getIntRecord("retryidle");
          if (i5 < 5)
            break label260;
          this.mStateMachine.startState(1);
        }
        while (true)
        {
          monitorexit;
          return;
          localObject2 = finally;
          monitorexit;
          throw localObject2;
          label260: int i6 = i5 + 1;
          this.mDataManager.setIntRecord("retryidle", i6);
        }
      }
    }
    catch (JSONException localJSONException)
    {
      while (true)
      {
        localJSONException.printStackTrace();
        continue;
        if (!"bump.match".equals(str1))
          break;
        if (!localJSONObject1.has("randnum"))
          continue;
        long l2 = localJSONObject1.getLong("randnum");
        Object[] arrayOfObject6 = new Object[1];
        arrayOfObject6[0] = Long.valueOf(l2);
        KXLog.w("ShakeUIHelper", "------------------  parsed [randum]=%d  ------", arrayOfObject6);
        this.mDataManager.setLongRecord("randnum", l2);
        this.mDataManager.setRecordSendFlag("randnum", 1);
        this.mDataManager.setRecordSendFlag("abstime", 0);
        this.mDataManager.setRecordSendFlag("lat", 0);
        this.mDataManager.setRecordSendFlag("lon", 0);
        this.mDataManager.setRecordSendFlag("accuracy", 0);
        this.mDataManager.setRecordHasdataFlag("lat", 0);
        this.mDataManager.setRecordHasdataFlag("lon", 0);
        this.mDataManager.setRecordHasdataFlag("accuracy", 0);
        this.mDataManager.setStringRecord("method", "bump.result");
      }
      if (!"bump.result".equals(str1))
        break label662;
    }
    int i4;
    if ((this.mStateMachine.getState() != 0) && (this.mStateMachine.getState() != 1))
    {
      i4 = localJSONObject1.getInt("num");
      if (i4 == 1)
      {
        long l1 = localJSONObject1.getLong("randnumT");
        this.mDataManager.setLongRecord("randnumT", l1);
        if (localJSONObject1.getInt("isfriend") != 1)
          break label1259;
      }
    }
    label662: label1247: label1259: for (boolean bool = true; ; bool = false)
    {
      JSONObject localJSONObject3 = localJSONObject1.getJSONObject("profile");
      String str2 = localJSONObject3.getString("name");
      String str3 = localJSONObject3.optString("uid");
      String str4 = localJSONObject3.optString("logo");
      BumpFriend localBumpFriend2 = new BumpFriend();
      localBumpFriend2.uid = str3;
      localBumpFriend2.name = str2;
      localBumpFriend2.logo = str4;
      localBumpFriend2.isAlreadyFrid = bool;
      this.mDataManager.setObjectRecord("otherinfo", localBumpFriend2);
      while (true)
      {
        this.mDataManager.setIntRecord("matched", i4);
        break;
        this.mDataManager.setStringRecord("again_reason", "not matched");
        this.mDataManager.setRecordSendFlag("randnum", 0);
      }
      if (("bump.affirm".equals(str1)) && (this.mStateMachine.getState() != 0) && (this.mStateMachine.getState() != 1))
      {
        int n = this.mDataManager.getIntRecord("serveraffirm");
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = Integer.valueOf(n);
        KXLog.w("ShakeUIHelper", "bump.affirm result ok and last [serveraffirm]=%d", arrayOfObject2);
        int i1 = n | 0x1;
        Object[] arrayOfObject3 = new Object[1];
        arrayOfObject3[0] = Integer.valueOf(i1);
        KXLog.w("ShakeUIHelper", "bump.affirm after update serveraffirm=%d", arrayOfObject3);
        this.mDataManager.setIntRecord("serveraffirm", i1);
        int i2 = this.mDataManager.getIntRecord("agree");
        int i3 = this.mDataManager.getIntRecord("otheragree");
        Object[] arrayOfObject4 = new Object[2];
        arrayOfObject4[0] = Integer.valueOf(i2);
        arrayOfObject4[1] = Integer.valueOf(i3);
        KXLog.w("ShakeUIHelper", "$$$$$$$$$ after bump.affim, current [iAgree]=%d, [otherAgree]=%d  $$$$$", arrayOfObject4);
        if (i2 == 0)
        {
          this.mDataManager.setRecordSendFlag("agree", 0);
          this.mDataManager.setRecordSendFlag("randnumT", 0);
          this.mDataManager.setStringRecord("method", "bump.getSystime");
          break;
        }
        if (i2 == 1)
        {
          this.mDataManager.setRecordSendFlag("agree", 0);
          if (i3 == 1)
          {
            this.mDataManager.setRecordSendFlag("randnumT", 0);
            this.mDataManager.setStringRecord("method", "bump.getSystime");
            break;
          }
          this.mDataManager.setRecordSendFlag("randnumT", 1);
          this.mDataManager.setStringRecord("method", "bump.getAffirm");
          break;
        }
        Object[] arrayOfObject5 = new Object[1];
        arrayOfObject5[0] = Integer.valueOf(i2);
        KXLog.w("ShakeUIHelper", "------------------  EXCEPTION WHAT HAPPEND with [agree]=%d --------", arrayOfObject5);
        break;
      }
      if ((!"bump.getAffirm".equals(str1)) || (this.mStateMachine.getState() == 0) || (this.mStateMachine.getState() == 1) || (this.mStateMachine.getState() == 2))
        break;
      int j = localJSONObject1.getInt("agree");
      int k = this.mDataManager.getIntRecord("serveraffirm");
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = Integer.valueOf(k);
      KXLog.w("ShakeUIHelper", "bump.getAffirm result ok and last [serveraffirm]=%d", arrayOfObject1);
      int m = k | 0x2;
      this.mDataManager.setIntRecord("serveraffirm", m);
      if ((j != 0) && (j != 1))
        break;
      JSONObject localJSONObject2;
      KXRecord localKXRecord;
      if (localJSONObject1.has("profile"))
      {
        localJSONObject2 = localJSONObject1.getJSONObject("profile");
        localKXRecord = this.mDataManager.getRecordForName("otherinfo");
        if (localKXRecord == null)
          break label1247;
      }
      for (BumpFriend localBumpFriend1 = (BumpFriend)localKXRecord.getObjectValue(); ; localBumpFriend1 = new BumpFriend())
      {
        localBumpFriend1.city = localJSONObject2.optString("city");
        localBumpFriend1.post = localJSONObject2.optString("title");
        localBumpFriend1.company = localJSONObject2.optString("com");
        localBumpFriend1.phone = localJSONObject2.optString("phone");
        localBumpFriend1.mobile = localJSONObject2.optString("mobile");
        localBumpFriend1.email = localJSONObject2.optString("email");
        this.mDataManager.setObjectRecord("otherinfo", localBumpFriend1);
        this.mDataManager.setIntRecord("otheragree", j);
        break;
      }
    }
  }

  public void clearRequestQueue()
  {
    KXLog.w("ShakeUIHelper", "+++++++++++    clear http request queue   +++++++++");
    LinkManager.getInstance(this.mContext).removeAllRequestFromQ();
  }

  public void confirm()
  {
    KXLog.w("ShakeUIHelper", "~~~~~~~~~~~~~~~~~~~ I CONFIRM  ~~~~~  ~~~~~~");
    this.mDataManager.setIntRecord("agree", 1);
    this.mDataManager.setRecordSendFlag("agree", 1);
    this.mDataManager.setStringRecord("method", "bump.affirm");
    this.mStateMachine.startState(4);
  }

  public void init()
  {
    LocationManager localLocationManager = (LocationManager)this.mContext.getSystemService("location");
    try
    {
      localLocationManager.requestLocationUpdates("gps", 250L, 10.0F, this, this.mContext.getMainLooper());
    }
    catch (Exception localException1)
    {
      try
      {
        while (true)
        {
          localLocationManager.requestLocationUpdates("network", 250L, 10.0F, this, this.mContext.getMainLooper());
          this.mDataManager = KXDataManager.getInstance();
          this.mDataManager.initSessionData();
          this.mDataManager.setStringRecord("method", "bump.getSystime");
          this.mDataManager.setRecordSendFlag("method", 1);
          this.mDataManager.setStringRecord("verify", User.getInstance().getOauthToken());
          this.mDataManager.setRecordSendFlag("verify", 1);
          this.mStateMachine = new ShakeStateMachine(this.mUIListener);
          startPoll();
          this.mAcceleService = new KXAccelerometerService(this.mContext, 1.5F);
          this.mAcceleService.setOnShakeListener(this.mStateMachine);
          return;
          localException1 = localException1;
          localException1.printStackTrace();
        }
      }
      catch (Exception localException2)
      {
        while (true)
          localException2.printStackTrace();
      }
    }
  }

  public void onLocationChanged(Location paramLocation)
  {
    if ((paramLocation != null) && ((this.mLocation == null) || (LocationService.locationUpdated(paramLocation, this.mLocation))) && (this.mDataManager != null))
    {
      this.mDataManager.setDoubleRecord("lat", paramLocation.getLatitude());
      this.mDataManager.setDoubleRecord("lon", paramLocation.getLongitude());
      this.mDataManager.setDoubleRecord("accuracy", paramLocation.getAccuracy());
      this.mDataManager.setDoubleRecord("gpstime", paramLocation.getTime() / 1000.0D);
      this.mLocation = paramLocation;
    }
  }

  public void onPause()
  {
    if ((this.mStateMachine != null) && (this.mStateMachine.getState() == 1))
      this.mAcceleService.stopListenSensor();
  }

  public void onProviderDisabled(String paramString)
  {
  }

  public void onProviderEnabled(String paramString)
  {
  }

  public boolean onRecordUpdate(String paramString)
  {
    return false;
  }

  public void onResume()
  {
    if ((this.mStateMachine != null) && (this.mStateMachine.getState() == 1))
    {
      this.mAcceleService.resetSensor();
      this.mAcceleService.startLisntenSensor();
    }
  }

  public void onStatusChanged(String paramString, int paramInt, Bundle paramBundle)
  {
  }

  public void reStartFromIdle()
  {
    if ((this.mStateMachine != null) && (this.mStateMachine.getState() != 1))
      this.mStateMachine.startState(1);
  }

  public void reject()
  {
    this.mDataManager.setIntRecord("agree", 0);
    this.mDataManager.setRecordSendFlag("agree", 1);
    this.mDataManager.setStringRecord("method", "bump.affirm");
    this.mStateMachine.startState(1);
  }

  public void release()
  {
    if (this.mServicePollTimer != null)
      this.mServicePollTimer.cancel();
    if (this.mAcceleService != null)
    {
      this.mAcceleService.stopListenSensor();
      this.mAcceleService.setOnShakeListener(null);
    }
    if (this.mDataManager != null)
      this.mDataManager.clearRecdMonitors();
    if (this.mStateMachine != null)
      this.mStateMachine.stopStateMachine();
    ((LocationManager)this.mContext.getSystemService("location")).removeUpdates(this);
  }

  public void resetIdleForCancel()
  {
    KXLog.w("ShakeUIHelper", "----------------- resetIdleForCancel  -------------");
    this.mStateMachine.startState(1);
  }

  public void restartRequest()
  {
    startPoll();
  }

  public void startBumpDetection()
  {
    if (this.mAcceleService != null)
      this.mAcceleService.startLisntenSensor();
  }

  public void stopBumpDetection()
  {
    if (this.mAcceleService != null)
      this.mAcceleService.stopListenSensor();
  }

  public void stopRequest()
  {
    if (this.mServicePollTimer != null)
      this.mServicePollTimer.cancel();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.pengpeng.ShakeApiHelper
 * JD-Core Version:    0.6.0
 */