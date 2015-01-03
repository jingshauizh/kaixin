package com.kaixin001.activity;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.kaixin001.lbs.LocationService;
import com.kaixin001.model.Setting;
import com.kaixin001.network.Protocol;
import java.util.Arrays;
import java.util.List;

public class KXTestSettingActivity extends Activity
{
  int currentSelectionHost;
  private EditText evLat;
  private EditText evLon;
  String[] items = { "http://api.kaixin001.com", "http://api.kaixin002.com" };
  private Spinner spHost;

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903342);
    this.spHost = ((Spinner)findViewById(2131363698));
    this.spHost.setPrompt("请选择服务端地址");
    ArrayAdapter localArrayAdapter = new ArrayAdapter(this, 17367048, this.items);
    localArrayAdapter.setDropDownViewResource(17367049);
    this.spHost.setAdapter(localArrayAdapter);
    this.spHost.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
    {
      public void onItemSelected(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
      {
        KXTestSettingActivity.this.currentSelectionHost = paramInt;
      }

      public void onNothingSelected(AdapterView<?> paramAdapterView)
      {
      }
    });
    this.evLat = ((EditText)findViewById(2131363695));
    this.evLon = ((EditText)findViewById(2131363692));
  }

  protected void onResume()
  {
    super.onResume();
    String str = Setting.getInstance().getHost();
    int i = Arrays.asList(this.items).indexOf(str);
    this.spHost.setSelection(i);
    Location localLocation = LocationService.getLocationService().getLocationByMinTime(600000L);
    if (localLocation == null)
    {
      this.evLat.setText("39.98153187");
      this.evLon.setText("116.30296797");
      return;
    }
    this.evLat.setText(String.valueOf(localLocation.getLatitude()));
    this.evLon.setText(String.valueOf(localLocation.getLongitude()));
  }

  protected void onStop()
  {
    super.onStop();
    Setting.getInstance().setHost(this.items[this.currentSelectionHost]);
    Protocol.getInstance().mHostName = this.items[this.currentSelectionHost];
    try
    {
      String str1 = this.evLat.getEditableText().toString();
      String str2 = this.evLon.getEditableText().toString();
      if ((TextUtils.isEmpty(str1)) || (TextUtils.isEmpty(str2)))
      {
        Toast.makeText(this, "failed to set lat, lon", 0).show();
        return;
      }
      Location localLocation = new Location("gps");
      localLocation.setLatitude(Double.valueOf(str1).doubleValue());
      localLocation.setLongitude(Double.valueOf(str2).doubleValue());
      localLocation.setAccuracy(5.0F);
      localLocation.setTime(System.currentTimeMillis());
      LocationService.getLocationService().addTestLocation(localLocation);
      return;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      Toast.makeText(this, "failed to set lat, lon", 0).show();
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.activity.KXTestSettingActivity
 * JD-Core Version:    0.6.0
 */