package com.kaixin001.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import com.kaixin001.engine.ContactsEngine;
import com.kaixin001.engine.ContactsRelateEngine;
import com.kaixin001.engine.FriendsInfoEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.model.ContactsRelatedModel;
import com.kaixin001.model.FriendStatus;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class UpdateContactsService extends Service
{
  private static long mContactsUpdateInterval = 1800000L;
  private TimerTask mRefreshTask = null;
  private Timer mRefreshTimer = null;

  private void setUpdateIntervalTimer()
  {
    if (this.mRefreshTimer != null)
      this.mRefreshTimer.cancel();
    if (this.mRefreshTask != null)
      this.mRefreshTask.cancel();
    this.mRefreshTimer = new Timer();
    this.mRefreshTask = new TimerTask()
    {
      public void run()
      {
        ArrayList localArrayList1 = ContactsRelateEngine.getInstance().loadContactsRelatedObjs(UpdateContactsService.this.getApplicationContext());
        if (localArrayList1 != null);
        ArrayList localArrayList2;
        int j;
        try
        {
          localArrayList2 = FriendsInfoEngine.getInstance().getContactsStatus(UpdateContactsService.this.getApplicationContext(), localArrayList1);
          if (localArrayList2 != null)
          {
            int i = localArrayList2.size();
            j = 0;
            if (j < i);
          }
          else
          {
            return;
          }
        }
        catch (SecurityErrorException localSecurityErrorException)
        {
          return;
        }
        FriendStatus localFriendStatus = (FriendStatus)localArrayList2.get(j);
        String str = localFriendStatus.getFuid();
        int k = localArrayList1.size();
        label155: for (int m = 0; ; m++)
        {
          if (m >= k);
          while (true)
          {
            j++;
            break;
            ContactsRelatedModel localContactsRelatedModel = (ContactsRelatedModel)localArrayList1.get(m);
            if (!localContactsRelatedModel.getFuid().equals(str))
              break label155;
            ContactsEngine.getInstance().statusUpdates(UpdateContactsService.this.getContentResolver(), localContactsRelatedModel.getCid().longValue(), localFriendStatus.getStatus(), localFriendStatus.getTimeStamp());
          }
        }
      }
    };
    this.mRefreshTimer.schedule(this.mRefreshTask, 0L, mContactsUpdateInterval);
  }

  public IBinder onBind(Intent paramIntent)
  {
    return null;
  }

  public void onCreate()
  {
    super.onCreate();
  }

  public void onDestroy()
  {
    if (this.mRefreshTimer != null)
    {
      this.mRefreshTimer.cancel();
      this.mRefreshTimer = null;
    }
    if (this.mRefreshTask != null)
    {
      this.mRefreshTask.cancel();
      this.mRefreshTask = null;
    }
    super.onDestroy();
  }

  public void onStart(Intent paramIntent, int paramInt)
  {
    super.onStart(paramIntent, paramInt);
    SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    if (localSharedPreferences.getBoolean("check_widget_refresh_interval", true))
    {
      long l = Long.parseLong(localSharedPreferences.getString("widget_refresh_interval", "1800000"));
      if ((l != mContactsUpdateInterval) || (this.mRefreshTimer == null))
      {
        mContactsUpdateInterval = l;
        setUpdateIntervalTimer();
      }
    }
    do
    {
      return;
      if (this.mRefreshTimer == null)
        continue;
      this.mRefreshTimer.cancel();
      this.mRefreshTimer = null;
    }
    while (this.mRefreshTask == null);
    this.mRefreshTask.cancel();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.service.UpdateContactsService
 * JD-Core Version:    0.6.0
 */