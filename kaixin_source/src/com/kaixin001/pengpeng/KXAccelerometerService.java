package com.kaixin001.pengpeng;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import com.kaixin001.pengpeng.data.KXDataManager;
import java.util.Timer;
import java.util.TimerTask;

public class KXAccelerometerService
{
  protected static float ACC_SCALE = 0.0F;
  protected static int LOOKBACK = 0;
  protected static double PRE_THRESH = 0.0D;
  private static final String TAG = "KXAccelerometerService";
  protected static double TRIGER1;
  protected static double TRIGER2;
  protected static double[] acc1;
  protected static double[] acc2;
  protected static int bumpcycle;
  protected static int cycle = 0;
  protected static int suppresshowlongago;
  protected static double[] ttt;
  protected static double[] xx;
  protected static double[] yy;
  protected static double[] zz;
  private MediaPlayer bumpSound;
  private KXDataManager dataManagerInstance = KXDataManager.getInstance();
  protected float lastX;
  protected float lastY;
  protected float lastZ;
  private final Sensor mAccelerometer;
  private final KXAcceloEventListener mAcceloEventListener = new KXAcceloEventListener(null);
  private boolean mBListening = false;
  private TimerTask mBumpTask;
  private Timer mBumpTimer;
  private final SensorManager mSensorManager;
  private OnShakeListener mShakeListener;
  protected int tryTime = 0;

  static
  {
    ACC_SCALE = 1.0F;
    LOOKBACK = 19;
    PRE_THRESH = 0.07000000000000001D;
    TRIGER1 = 1.0D;
    TRIGER2 = 1.6D;
  }

  public KXAccelerometerService(Context paramContext, float paramFloat)
  {
    this.mSensorManager = ((SensorManager)paramContext.getSystemService("sensor"));
    this.mAccelerometer = this.mSensorManager.getDefaultSensor(1);
    this.bumpSound = MediaPlayer.create(paramContext, 2131099649);
  }

  public void resetSensor()
  {
    this.lastX = 0.0F;
    this.lastY = 0.0F;
    this.lastZ = -1.0F;
    bumpcycle = 100;
    suppresshowlongago = 0;
    cycle = 0;
    this.tryTime = 0;
    xx = new double[100];
    yy = new double[100];
    zz = new double[100];
    acc1 = new double[100];
    acc2 = new double[100];
    ttt = new double[100];
  }

  public void setOnShakeListener(OnShakeListener paramOnShakeListener)
  {
    this.mShakeListener = paramOnShakeListener;
  }

  protected void shakeOccurred()
  {
    monitorenter;
    try
    {
      stopListenSensor();
      if (this.mShakeListener != null)
        this.mShakeListener.onShakeOccurred();
      if (this.mBumpTimer == null)
        this.mBumpTimer = new Timer();
      if (this.mBumpTask != null)
        this.mBumpTask.cancel();
      this.mBumpTask = new TimerTask()
      {
        public void run()
        {
          if (KXAccelerometerService.this.mShakeListener != null)
            KXAccelerometerService.this.mShakeListener.onShakeOccurred();
          if ((KXAccelerometerService.this.bumpSound != null) && (!KXAccelerometerService.this.bumpSound.isPlaying()))
            KXAccelerometerService.this.bumpSound.start();
        }
      };
      this.mBumpTimer.schedule(this.mBumpTask, 50L);
      return;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public void startLisntenSensor()
  {
    if (this.mBListening)
      return;
    resetSensor();
    this.mBListening = this.mSensorManager.registerListener(this.mAcceloEventListener, this.mAccelerometer, 1);
  }

  public void stopListenSensor()
  {
    if (!this.mBListening)
      return;
    this.mBListening = false;
    this.mSensorManager.unregisterListener(this.mAcceloEventListener, this.mAccelerometer);
  }

  private class KXAcceloEventListener
    implements SensorEventListener
  {
    private KXAcceloEventListener()
    {
    }

    public void onAccuracyChanged(Sensor paramSensor, int paramInt)
    {
    }

    public void onSensorChanged(SensorEvent paramSensorEvent)
    {
      float[] arrayOfFloat = paramSensorEvent.values;
      int i = KXAccelerometerService.cycle % 100;
      KXAccelerometerService localKXAccelerometerService = KXAccelerometerService.this;
      int j = localKXAccelerometerService.tryTime;
      localKXAccelerometerService.tryTime = (j + 1);
      if (j < 3);
      do
        return;
      while ((Math.abs(arrayOfFloat[0]) == 0.0F) && (Math.abs(arrayOfFloat[1]) == 0.0F) && (Math.abs(arrayOfFloat[2]) == 0.0F));
      float f1 = arrayOfFloat[0] / 9.80665F;
      float f2 = arrayOfFloat[1] / 9.80665F;
      float f3 = arrayOfFloat[2] / 9.80665F;
      double d1 = KXAccelerometerService.ACC_SCALE * (Math.abs(f1 - KXAccelerometerService.this.lastX) + Math.abs(f2 - KXAccelerometerService.this.lastY) + Math.abs(f3 - KXAccelerometerService.this.lastZ));
      KXAccelerometerService.xx[i] = f1;
      KXAccelerometerService.yy[i] = f2;
      KXAccelerometerService.zz[i] = f3;
      KXAccelerometerService.acc1[i] = d1;
      KXAccelerometerService.ttt[i] = (System.currentTimeMillis() / 1000.0D);
      KXAccelerometerService.this.lastX = f1;
      KXAccelerometerService.this.lastY = f2;
      KXAccelerometerService.this.lastZ = f3;
      double d2 = 0.0D;
      double d3 = 0.0D;
      for (int k = -(2 + KXAccelerometerService.LOOKBACK); ; k++)
      {
        if (k >= -2)
        {
          double d4 = d2 / d3;
          KXAccelerometerService.acc2[i] = d4;
          if (((d1 > KXAccelerometerService.TRIGER1) && (d4 < KXAccelerometerService.PRE_THRESH) && (KXAccelerometerService.cycle > 0)) || ((d1 > KXAccelerometerService.TRIGER2) && (KXAccelerometerService.cycle > 4 + KXAccelerometerService.LOOKBACK) && (KXAccelerometerService.suppresshowlongago > 4 + KXAccelerometerService.LOOKBACK) && (KXAccelerometerService.bumpcycle > 12)))
          {
            double d5 = KXAccelerometerService.this.dataManagerInstance.getDoubleRecord("upoffset");
            double d6 = KXAccelerometerService.this.dataManagerInstance.getDoubleRecord("latency");
            double d7 = d5 + KXAccelerometerService.ttt[i] - d6;
            KXAccelerometerService.this.dataManagerInstance.setDoubleRecord("abstime", d7);
            KXAccelerometerService.this.shakeOccurred();
            KXAccelerometerService.bumpcycle = 0;
          }
          KXAccelerometerService.cycle = 1 + KXAccelerometerService.cycle;
          KXAccelerometerService.bumpcycle = 1 + KXAccelerometerService.bumpcycle;
          KXAccelerometerService.suppresshowlongago = 1 + KXAccelerometerService.suppresshowlongago;
          return;
        }
        int m = (k + KXAccelerometerService.cycle) % 100;
        if (m < 0)
          m += 100;
        d2 += KXAccelerometerService.acc1[m];
        d3 += 1.0D;
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.pengpeng.KXAccelerometerService
 * JD-Core Version:    0.6.0
 */