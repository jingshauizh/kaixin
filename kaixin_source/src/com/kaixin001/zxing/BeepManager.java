package com.kaixin001.zxing;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.preference.PreferenceManager;
import com.kaixin001.util.KXLog;
import java.io.IOException;

public final class BeepManager
{
  private static final float BEEP_VOLUME = 0.1F;
  private static final String TAG = BeepManager.class.getSimpleName();
  private static final long VIBRATE_DURATION = 200L;
  private static final boolean vibrate;
  private final Activity activity;
  private MediaPlayer mediaPlayer;
  private boolean playBeep;

  public BeepManager(Activity paramActivity)
  {
    this.activity = paramActivity;
    this.mediaPlayer = null;
    updatePrefs();
  }

  private static MediaPlayer buildMediaPlayer(Context paramContext)
  {
    MediaPlayer localMediaPlayer = new MediaPlayer();
    localMediaPlayer.setAudioStreamType(3);
    localMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
    {
      public void onCompletion(MediaPlayer paramMediaPlayer)
      {
        paramMediaPlayer.seekTo(0);
      }
    });
    AssetFileDescriptor localAssetFileDescriptor = paramContext.getResources().openRawResourceFd(2131099648);
    try
    {
      localMediaPlayer.setDataSource(localAssetFileDescriptor.getFileDescriptor(), localAssetFileDescriptor.getStartOffset(), localAssetFileDescriptor.getLength());
      localAssetFileDescriptor.close();
      localMediaPlayer.setVolume(0.1F, 0.1F);
      localMediaPlayer.prepare();
      return localMediaPlayer;
    }
    catch (IOException localIOException)
    {
      KXLog.e(TAG, "buildMediaPlayer", localIOException);
    }
    return null;
  }

  private static boolean shouldBeep(SharedPreferences paramSharedPreferences, Context paramContext)
  {
    int i = 1;
    if ((i != 0) && (((AudioManager)paramContext.getSystemService("audio")).getRingerMode() != 2))
      i = 0;
    return i;
  }

  public void playBeepSoundAndVibrate()
  {
    if ((this.playBeep) && (this.mediaPlayer != null))
      this.mediaPlayer.start();
  }

  public void updatePrefs()
  {
    this.playBeep = shouldBeep(PreferenceManager.getDefaultSharedPreferences(this.activity), this.activity);
    if ((this.playBeep) && (this.mediaPlayer == null))
    {
      this.activity.setVolumeControlStream(3);
      this.mediaPlayer = buildMediaPlayer(this.activity);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.zxing.BeepManager
 * JD-Core Version:    0.6.0
 */