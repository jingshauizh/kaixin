package com.kaixin001.activity;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.text.TextUtils;
import android.widget.Toast;
import com.kaixin001.db.UserContentObserver;
import com.kaixin001.engine.UpgradeEngine;
import com.kaixin001.model.KaixinConst;
import com.kaixin001.model.Setting;
import com.kaixin001.model.UpgradeModel;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpConnection;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.service.RefreshNewMessageService;
import com.kaixin001.service.UpdateContactsService;
import com.kaixin001.util.CloudAlbumManager;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.UpgradeDownloadFile;

public class SettingActivity extends PreferenceActivity
{
  private static int NUM_CLICK = 0;
  private Handler handler = new Handler()
  {
    public void handleMessage(Message paramMessage)
    {
      switch (paramMessage.what)
      {
      default:
        return;
      case 2:
        SettingActivity.this.finish();
        return;
      case 10000:
      }
      SettingActivity.this.finish();
    }
  };
  private CheckNewUpdatesTask mCheckNewUpdatesTask = null;
  private UserContentObserver mContentObserver = null;
  private ProgressDialog mProgressDialog = null;

  private void cancelTask()
  {
    if ((this.mCheckNewUpdatesTask != null) && (!this.mCheckNewUpdatesTask.isCancelled()) && (this.mCheckNewUpdatesTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.mCheckNewUpdatesTask.cancel(true);
      this.mCheckNewUpdatesTask = null;
    }
  }

  private void checkNewUpdates()
  {
    if (!HttpConnection.checkNetworkAndHint(true, this))
      return;
    cancelTask();
    this.mCheckNewUpdatesTask = new CheckNewUpdatesTask();
    this.mCheckNewUpdatesTask.execute(new Void[] { null });
  }

  private void dismissDialog()
  {
    if ((this.mProgressDialog != null) && (this.mProgressDialog.isShowing()))
    {
      this.mProgressDialog.dismiss();
      this.mProgressDialog = null;
    }
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    addPreferencesFromResource(2131034115);
    Uri localUri = Uri.parse("content://com.kaixin001.provider/user");
    this.mContentObserver = new UserContentObserver(this.handler);
    getContentResolver().registerContentObserver(localUri, false, this.mContentObserver);
    ListPreference localListPreference1 = (ListPreference)findPreference("notification_refresh_interval");
    localListPreference1.setSummary(localListPreference1.getEntry());
    2 local2 = new Preference.OnPreferenceChangeListener()
    {
      public boolean onPreferenceChange(Preference paramPreference, Object paramObject)
      {
        ListPreference localListPreference = (ListPreference)SettingActivity.this.findPreference("notification_refresh_interval");
        CharSequence[] arrayOfCharSequence1 = localListPreference.getEntryValues();
        CharSequence[] arrayOfCharSequence2 = localListPreference.getEntries();
        for (int i = 0; ; i++)
        {
          if (i >= arrayOfCharSequence1.length);
          while (true)
          {
            return true;
            if (!arrayOfCharSequence1[i].equals(paramObject))
              break;
            localListPreference.setSummary(arrayOfCharSequence2[i]);
            KXLog.d("preferenceChangeListener", "begin to set mInterval=" + arrayOfCharSequence2[i]);
          }
        }
      }
    };
    localListPreference1.setOnPreferenceChangeListener(local2);
    CheckBoxPreference localCheckBoxPreference1 = (CheckBoxPreference)findPreference("push_repost_preference");
    3 local3 = new Preference.OnPreferenceChangeListener()
    {
      public boolean onPreferenceChange(Preference paramPreference, Object paramObject)
      {
        new AsyncTask((Boolean)paramObject)
        {
          protected Integer doInBackground(Void[] paramArrayOfVoid)
          {
            String str1;
            if (this.val$value.booleanValue())
              str1 = "0";
            while (true)
            {
              String str2 = Protocol.getInstance().makeGetRepostPush(User.getInstance().getUID(), str1);
              HttpProxy localHttpProxy = new HttpProxy(SettingActivity.this);
              try
              {
                localHttpProxy.httpGet(str2, null, null);
                return null;
                str1 = "1";
              }
              catch (Exception localException)
              {
              }
            }
            return null;
          }
        }
        .execute(new Void[0]);
        return true;
      }
    };
    localCheckBoxPreference1.setOnPreferenceChangeListener(local3);
    CheckBoxPreference localCheckBoxPreference2 = (CheckBoxPreference)findPreference("push_horoscope_preference");
    4 local4 = new Preference.OnPreferenceChangeListener()
    {
      public boolean onPreferenceChange(Preference paramPreference, Object paramObject)
      {
        new AsyncTask((Boolean)paramObject)
        {
          protected Integer doInBackground(Void[] paramArrayOfVoid)
          {
            String str1;
            if (this.val$value.booleanValue())
              str1 = "1";
            while (true)
            {
              String str2 = Protocol.getInstance().setStarsPush(str1);
              HttpProxy localHttpProxy = new HttpProxy(SettingActivity.this);
              try
              {
                localHttpProxy.httpGet(str2, null, null);
                return null;
                str1 = "0";
              }
              catch (Exception localException)
              {
              }
            }
            return null;
          }
        }
        .execute(new Void[0]);
        return true;
      }
    };
    localCheckBoxPreference2.setOnPreferenceChangeListener(local4);
    ListPreference localListPreference2 = (ListPreference)findPreference("horoscope_setting_choose_list");
    localListPreference2.setSummary(KXEnvironment.loadStrParams(getBaseContext(), "star.name", false, (String)localListPreference2.getEntry()));
    CharSequence[] arrayOfCharSequence = localListPreference2.getEntries();
    5 local5 = new Preference.OnPreferenceChangeListener(arrayOfCharSequence, localListPreference2)
    {
      public boolean onPreferenceChange(Preference paramPreference, Object paramObject)
      {
        int i = Integer.parseInt(paramObject.toString());
        String str = (String)this.val$entries[(i - 1)];
        KXEnvironment.saveStrParams(SettingActivity.this.getBaseContext(), "star.name", false, str);
        this.val$listPreference.setSummary(str);
        new AsyncTask(str)
        {
          protected Void doInBackground(Void[] paramArrayOfVoid)
          {
            String str = Protocol.getInstance().getSetStarUrl(this.val$star);
            HttpProxy localHttpProxy = new HttpProxy(SettingActivity.this);
            try
            {
              localHttpProxy.httpGet(str, null, null);
              return null;
            }
            catch (Exception localException)
            {
              localException.printStackTrace();
            }
            return null;
          }
        }
        .execute(new Void[0]);
        return false;
      }
    };
    localListPreference2.setOnPreferenceChangeListener(local5);
    ListPreference localListPreference3 = (ListPreference)findPreference("widget_refresh_interval");
    localListPreference3.setSummary(localListPreference3.getEntry());
    6 local6 = new Preference.OnPreferenceChangeListener()
    {
      public boolean onPreferenceChange(Preference paramPreference, Object paramObject)
      {
        ListPreference localListPreference = (ListPreference)SettingActivity.this.findPreference("widget_refresh_interval");
        CharSequence[] arrayOfCharSequence1 = localListPreference.getEntryValues();
        CharSequence[] arrayOfCharSequence2 = localListPreference.getEntries();
        for (int i = 0; ; i++)
        {
          if (i >= arrayOfCharSequence1.length);
          while (true)
          {
            return true;
            if (!arrayOfCharSequence1[i].equals(paramObject))
              break;
            localListPreference.setSummary(arrayOfCharSequence2[i]);
          }
        }
      }
    };
    localListPreference3.setOnPreferenceChangeListener(local6);
    CheckBoxPreference localCheckBoxPreference3 = (CheckBoxPreference)findPreference("setting_saveflow_pref");
    7 local7 = new Preference.OnPreferenceChangeListener()
    {
      public boolean onPreferenceChange(Preference paramPreference, Object paramObject)
      {
        Boolean localBoolean = (Boolean)paramObject;
        KXEnvironment.saveFlowChanged(SettingActivity.this.getApplicationContext(), localBoolean.booleanValue());
        return true;
      }
    };
    localCheckBoxPreference3.setOnPreferenceChangeListener(local7);
    CheckBoxPreference localCheckBoxPreference4 = (CheckBoxPreference)findPreference("cloud_album_setting_network_pref");
    8 local8 = new Preference.OnPreferenceChangeListener(localCheckBoxPreference4)
    {
      public boolean onPreferenceChange(Preference paramPreference, Object paramObject)
      {
        if (!((Boolean)paramObject).booleanValue())
        {
          DialogUtil.showMsgDlg(SettingActivity.this, 2131428515, 2131428516, 2131427381, 2131427587, new DialogInterface.OnClickListener()
          {
            public void onClick(DialogInterface paramDialogInterface, int paramInt)
            {
              CloudAlbumManager.getInstance().setSyncState(SettingActivity.this, false);
            }
          }
          , new DialogInterface.OnClickListener(this.val$syncOpen)
          {
            public void onClick(DialogInterface paramDialogInterface, int paramInt)
            {
              this.val$syncOpen.setChecked(true);
            }
          });
          return true;
        }
        CloudAlbumManager.getInstance().setSyncState(SettingActivity.this, true);
        return true;
      }
    };
    localCheckBoxPreference4.setOnPreferenceChangeListener(local8);
    ListPreference localListPreference4 = (ListPreference)findPreference("cloud_album_setting_content_pref");
    localListPreference4.setSummary(localListPreference4.getEntry());
    9 local9 = new Preference.OnPreferenceChangeListener()
    {
      public boolean onPreferenceChange(Preference paramPreference, Object paramObject)
      {
        ListPreference localListPreference = (ListPreference)SettingActivity.this.findPreference("cloud_album_setting_content_pref");
        CharSequence[] arrayOfCharSequence1 = localListPreference.getEntryValues();
        CharSequence[] arrayOfCharSequence2 = localListPreference.getEntries();
        for (int i = 0; ; i++)
        {
          if (i >= arrayOfCharSequence1.length);
          while (true)
          {
            return true;
            if (!arrayOfCharSequence1[i].equals(paramObject))
              break;
            localListPreference.setSummary(arrayOfCharSequence2[i]);
            try
            {
              CloudAlbumManager.getInstance().setSyncContentType(SettingActivity.this, Integer.parseInt((String)arrayOfCharSequence1[i]));
            }
            catch (Exception localException)
            {
              localException.printStackTrace();
            }
          }
        }
      }
    };
    localListPreference4.setOnPreferenceChangeListener(local9);
    Preference localPreference1 = findPreference("software_check_new_updates");
    Preference localPreference2;
    if ((TextUtils.isEmpty(User.getInstance().getUID())) || (TextUtils.isEmpty(User.getInstance().getOauthToken())))
    {
      ((PreferenceCategory)findPreference("software_version")).removePreference(localPreference1);
      if (!Setting.getInstance().widgetEnabled())
        getPreferenceScreen().removePreference(localListPreference3);
      localPreference2 = findPreference("software_version_preference");
      if (!Setting.getInstance().isDebug())
        break label462;
      localPreference2.setSummary(localPreference2.getSummary().toString());
    }
    while (true)
    {
      MessageHandlerHolder.getInstance().addHandler(this.handler);
      return;
      10 local10 = new Preference.OnPreferenceClickListener()
      {
        public boolean onPreferenceClick(Preference paramPreference)
        {
          SettingActivity.this.checkNewUpdates();
          return true;
        }
      };
      localPreference1.setOnPreferenceClickListener(local10);
      break;
      label462: if ((Setting.getInstance().showVersion()) || (localPreference2 == null))
        continue;
      getPreferenceScreen().removePreference(localPreference2);
    }
  }

  protected void onDestroy()
  {
    MessageHandlerHolder.getInstance().removeHandler(this.handler);
    startService(new Intent(this, RefreshNewMessageService.class));
    Intent localIntent = new Intent(this, UpdateContactsService.class);
    stopService(localIntent);
    startService(localIntent);
    sendBroadcast(new Intent("com.kaixin001.WIDGET_UPDATE_INTERVAL"));
    if (this.mContentObserver != null)
    {
      getContentResolver().unregisterContentObserver(this.mContentObserver);
      this.mContentObserver = null;
    }
    cancelTask();
    dismissDialog();
    super.onDestroy();
  }

  class CheckNewUpdatesTask extends AsyncTask<Void, Void, Boolean>
  {
    CheckNewUpdatesTask()
    {
    }

    protected Boolean doInBackground(Void[] paramArrayOfVoid)
    {
      return Boolean.valueOf(UpgradeEngine.getInstance().checkUpgradeInformation(SettingActivity.this.getApplicationContext()));
    }

    protected void onCancelled()
    {
      super.onCancelled();
      SettingActivity.this.dismissDialog();
    }

    protected void onPostExecute(Boolean paramBoolean)
    {
      super.onPostExecute(paramBoolean);
      SettingActivity.this.dismissDialog();
      if (!paramBoolean.booleanValue())
      {
        Toast.makeText(SettingActivity.this, 2131427547, 0).show();
        return;
      }
      if (UpgradeModel.getInstance().getDownloadFile())
      {
        ((NotificationManager)SettingActivity.this.getSystemService("notification")).cancel(KaixinConst.ID_CAN_UPGRADE_NOTIIFICATION);
        Intent localIntent = new Intent(SettingActivity.this, UpgradeDialogActivity.class);
        Bundle localBundle = new Bundle();
        localBundle.putString("stringMessage", UpgradeDownloadFile.getInstance().mDescription);
        localBundle.putInt("dialogtype", 1);
        localIntent.putExtras(localBundle);
        localIntent.addFlags(536870912);
        SettingActivity.this.startActivity(localIntent);
        UpgradeModel.enableUpgradeService(false);
        return;
      }
      SettingActivity.NUM_CLICK = 1 + SettingActivity.NUM_CLICK;
      if (SettingActivity.NUM_CLICK == 1)
      {
        Toast.makeText(SettingActivity.this, 2131427786, 0).show();
        return;
      }
      Toast.makeText(SettingActivity.this, 2131427787, 0).show();
    }

    protected void onPreExecute()
    {
      SettingActivity.this.dismissDialog();
      SettingActivity.this.mProgressDialog = ProgressDialog.show(SettingActivity.this, SettingActivity.this.getResources().getString(2131427328), SettingActivity.this.getResources().getString(2131427785), true, true, new DialogInterface.OnCancelListener()
      {
        public void onCancel(DialogInterface paramDialogInterface)
        {
          SettingActivity.this.mCheckNewUpdatesTask.cancel(true);
          SettingActivity.this.mCheckNewUpdatesTask = null;
        }
      });
      super.onPreExecute();
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.activity.SettingActivity
 * JD-Core Version:    0.6.0
 */