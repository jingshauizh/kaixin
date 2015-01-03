package com.kaixin001.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;

public class HoroscopeSettingsActivity extends PreferenceActivity
{
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    addPreferencesFromResource(2131034113);
    ((CheckBoxPreference)findPreference("horoscope_setting_push")).setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener()
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
              HttpProxy localHttpProxy = new HttpProxy(HoroscopeSettingsActivity.this);
              try
              {
                localHttpProxy.httpGet(str2, null, null);
                return null;
                str1 = "0";
              }
              catch (Exception localException)
              {
                localException.printStackTrace();
              }
            }
            return null;
          }
        }
        .execute(new Void[0]);
        return true;
      }
    });
    ListPreference localListPreference = (ListPreference)findPreference("horoscope_setting_choose_list");
    localListPreference.setSummary(KXEnvironment.loadStrParams(getBaseContext(), "star.name", true, (String)localListPreference.getEntry()));
    localListPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener(localListPreference.getEntries(), localListPreference)
    {
      public boolean onPreferenceChange(Preference paramPreference, Object paramObject)
      {
        int i = Integer.parseInt(paramObject.toString());
        String str = (String)this.val$entries[(i - 1)];
        KXEnvironment.saveStrParams(HoroscopeSettingsActivity.this.getBaseContext(), "star.name", true, str);
        this.val$listPreference.setSummary(str);
        new AsyncTask(str)
        {
          protected Void doInBackground(Void[] paramArrayOfVoid)
          {
            String str = Protocol.getInstance().getSetStarUrl(this.val$star);
            HttpProxy localHttpProxy = new HttpProxy(HoroscopeSettingsActivity.this);
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
        return true;
      }
    });
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.activity.HoroscopeSettingsActivity
 * JD-Core Version:    0.6.0
 */