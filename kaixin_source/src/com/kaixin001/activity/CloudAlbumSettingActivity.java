package com.kaixin001.activity;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import com.kaixin001.util.CloudAlbumManager;
import com.kaixin001.util.DialogUtil;

public class CloudAlbumSettingActivity extends PreferenceActivity
{
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    addPreferencesFromResource(2131034112);
    CheckBoxPreference localCheckBoxPreference = (CheckBoxPreference)findPreference("cloud_album_setting_network_pref");
    localCheckBoxPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener(localCheckBoxPreference)
    {
      public boolean onPreferenceChange(Preference paramPreference, Object paramObject)
      {
        if (!((Boolean)paramObject).booleanValue())
        {
          DialogUtil.showKXAlertDialog(CloudAlbumSettingActivity.this, 2131428515, 2131428516, 2131427381, 2131427587, new DialogInterface.OnClickListener()
          {
            public void onClick(DialogInterface paramDialogInterface, int paramInt)
            {
              CloudAlbumManager.getInstance().setSyncState(CloudAlbumSettingActivity.this, false);
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
        CloudAlbumManager.getInstance().setSyncState(CloudAlbumSettingActivity.this, true);
        return true;
      }
    });
    ListPreference localListPreference = (ListPreference)findPreference("cloud_album_setting_content_pref");
    localListPreference.setSummary(localListPreference.getEntry());
    localListPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener()
    {
      public boolean onPreferenceChange(Preference paramPreference, Object paramObject)
      {
        ListPreference localListPreference = (ListPreference)CloudAlbumSettingActivity.this.findPreference("cloud_album_setting_content_pref");
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
              CloudAlbumManager.getInstance().setSyncContentType(CloudAlbumSettingActivity.this, Integer.parseInt((String)arrayOfCharSequence1[i]));
            }
            catch (Exception localException)
            {
              localException.printStackTrace();
            }
          }
        }
      }
    });
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.activity.CloudAlbumSettingActivity
 * JD-Core Version:    0.6.0
 */