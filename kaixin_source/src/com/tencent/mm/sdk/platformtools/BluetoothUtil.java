package com.tencent.mm.sdk.platformtools;

import android.media.AudioManager;
import android.os.Build.VERSION;

public class BluetoothUtil
{
  public static boolean startBluetooth(AudioManager paramAudioManager)
  {
    if (Integer.valueOf(Build.VERSION.SDK).intValue() >= 8)
    {
      if ((!paramAudioManager.isBluetoothScoAvailableOffCall()) || (PhoneStatusWatcher.isCalling()))
        return false;
      paramAudioManager.startBluetoothSco();
      paramAudioManager.setBluetoothScoOn(true);
      return true;
    }
    return false;
  }

  public static void stopBluetooth(AudioManager paramAudioManager)
  {
    if ((Integer.valueOf(Build.VERSION.SDK).intValue() >= 8) && (!PhoneStatusWatcher.isCalling()))
      paramAudioManager.stopBluetoothSco();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.platformtools.BluetoothUtil
 * JD-Core Version:    0.6.0
 */