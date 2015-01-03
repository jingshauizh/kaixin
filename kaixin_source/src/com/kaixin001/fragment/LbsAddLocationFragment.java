package com.kaixin001.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Location;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.engine.LbsAddLocationEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.lbs.LocationService;
import com.kaixin001.util.IntentUtil;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LbsAddLocationFragment extends BaseFragment
  implements View.OnClickListener
{
  private static final String ADD_LOCATION_PATTERN = "^[0-9\\s=`~!@#$%\\^&*()\\-+=|{}':;',\\[\\].\\\\<>\"/?~！@#￥%……*（）—【】‘；：”“’。，、？]*$";
  private final int ADDRESS_NAME_ID = 2;
  private final int LOCATION_NAME_ID = 1;
  private ImageView mBtnLeft = null;
  private ImageView mBtnRight = null;
  private EditText mEditTextAddress = null;
  private EditText mEditTextLocationName = null;
  private String mFriends = null;
  private int mIsAddressNameAvaiable = -1;
  private int mIsLocationNameAvaiable = -1;
  private String mLat = null;
  private String mLon = null;
  private StringBuilder mPoiId = new StringBuilder();
  SubmitLocationTask mSubmitLocationTask = null;
  private ProgressDialog m_ProgressDialog = null;

  private void cancelSubmitAddLocation()
  {
    if (this.mSubmitLocationTask != null)
    {
      this.mSubmitLocationTask.cancel(true);
      this.mSubmitLocationTask = null;
      LbsAddLocationEngine.getInstance().cancel();
    }
  }

  public static boolean checkIsAddLocationRule(String paramString)
  {
    if (TextUtils.isEmpty(paramString));
    do
      return false;
    while (Pattern.compile("^[0-9\\s=`~!@#$%\\^&*()\\-+=|{}':;',\\[\\].\\\\<>\"/?~！@#￥%……*（）—【】‘；：”“’。，、？]*$").matcher(paramString).matches());
    return true;
  }

  private void finishThisActivity(int paramInt)
  {
    if (paramInt == 1)
    {
      Intent localIntent = new Intent();
      localIntent.putExtra("poiId", this.mPoiId.toString());
      localIntent.putExtra("locationName", this.mEditTextLocationName.getText().toString().trim());
      localIntent.putExtra("addressName", this.mEditTextAddress.getText().toString().trim());
      setResult(-1, localIntent);
    }
    while (true)
    {
      finish();
      return;
      setResult(0);
    }
  }

  Integer doUploadLocationInformation()
  {
    String str1 = this.mEditTextAddress.getText().toString().trim();
    String str2 = this.mEditTextLocationName.getText().toString().trim();
    this.mPoiId.delete(0, this.mPoiId.length());
    if ((!checkIsAddLocationRule(str1)) || (!checkIsAddLocationRule(str2)))
      return Integer.valueOf(-1);
    if (TextUtils.isEmpty(this.mLon))
      return Integer.valueOf(104);
    if (TextUtils.isEmpty(this.mLat))
      return Integer.valueOf(104);
    while (true)
    {
      Location localLocation1;
      try
      {
        localLocation1 = LocationService.getLocationService().lastBestMapABCLocation;
        Location localLocation2 = LocationService.getLocationService().lastNoticedLocation;
        if (localLocation2 == null)
          break label299;
        if (!LocationService.isMapABCLocation(localLocation2))
          continue;
        str3 = "0";
        str4 = "0";
        str5 = String.valueOf(localLocation2.getLongitude());
        localObject = String.valueOf(localLocation2.getLatitude());
        return Integer.valueOf(LbsAddLocationEngine.getInstance().doUploadLbsLocation(getActivity(), str2, str1, str3, str4, str5, (String)localObject, this.mPoiId));
        str3 = String.valueOf(localLocation2.getLongitude());
        str4 = String.valueOf(localLocation2.getLatitude());
        if (localLocation1 != null)
          continue;
        str5 = null;
        break label288;
        str5 = String.valueOf(localLocation1.getLongitude());
        break label288;
        localObject = String.valueOf(localLocation1.getLatitude());
        break label296;
        str5 = String.valueOf(localLocation1.getLongitude());
        break label315;
        String str6 = String.valueOf(localLocation1.getLatitude());
        localObject = str6;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
        return Integer.valueOf(199);
      }
      catch (InterruptedException localInterruptedException)
      {
        return Integer.valueOf(-2);
      }
      label288: Object localObject = null;
      if (localLocation1 != null)
        continue;
      label296: continue;
      label299: String str3 = "0";
      String str4 = "0";
      if (localLocation1 != null)
        continue;
      String str5 = null;
      label315: localObject = null;
      if (localLocation1 != null)
        continue;
    }
  }

  void initTextEdit()
  {
    this.mEditTextAddress = ((EditText)findViewById(2131362964));
    this.mEditTextLocationName = ((EditText)findViewById(2131362961));
    1LbsAddLocationTextWatcher local1LbsAddLocationTextWatcher1 = new TextWatcher(this.mEditTextLocationName, 1)
    {
      private int mCpId = -1;
      private EditText mEdtitext = null;

      public void afterTextChanged(Editable paramEditable)
      {
      }

      public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
      {
      }

      public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
      {
        String str = this.mEdtitext.getText().toString().trim();
        if ((!TextUtils.isEmpty(str)) && (str.length() >= 2))
          if (this.mCpId == 1)
            LbsAddLocationFragment.this.mIsLocationNameAvaiable = 1;
        while (true)
        {
          LbsAddLocationFragment.this.updateTitle();
          return;
          if (this.mCpId != 2)
            continue;
          LbsAddLocationFragment.this.mIsAddressNameAvaiable = 1;
          continue;
          if (this.mCpId == 1)
          {
            LbsAddLocationFragment.this.mIsLocationNameAvaiable = 0;
            continue;
          }
          if (this.mCpId != 2)
            continue;
          LbsAddLocationFragment.this.mIsAddressNameAvaiable = 0;
        }
      }
    };
    this.mEditTextLocationName.addTextChangedListener(local1LbsAddLocationTextWatcher1);
    1LbsAddLocationTextWatcher local1LbsAddLocationTextWatcher2 = new TextWatcher(this.mEditTextAddress, 2)
    {
      private int mCpId = -1;
      private EditText mEdtitext = null;

      public void afterTextChanged(Editable paramEditable)
      {
      }

      public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
      {
      }

      public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
      {
        String str = this.mEdtitext.getText().toString().trim();
        if ((!TextUtils.isEmpty(str)) && (str.length() >= 2))
          if (this.mCpId == 1)
            LbsAddLocationFragment.this.mIsLocationNameAvaiable = 1;
        while (true)
        {
          LbsAddLocationFragment.this.updateTitle();
          return;
          if (this.mCpId != 2)
            continue;
          LbsAddLocationFragment.this.mIsAddressNameAvaiable = 1;
          continue;
          if (this.mCpId == 1)
          {
            LbsAddLocationFragment.this.mIsLocationNameAvaiable = 0;
            continue;
          }
          if (this.mCpId != 2)
            continue;
          LbsAddLocationFragment.this.mIsAddressNameAvaiable = 0;
        }
      }
    };
    this.mEditTextAddress.addTextChangedListener(local1LbsAddLocationTextWatcher2);
  }

  void initTitle()
  {
    this.mBtnLeft = ((ImageView)findViewById(2131362914));
    this.mBtnLeft.setOnClickListener(this);
    ((ImageView)findViewById(2131362919)).setVisibility(8);
    TextView localTextView = (TextView)findViewById(2131362920);
    localTextView.setText(2131428157);
    localTextView.setVisibility(0);
    this.mBtnRight = ((ImageView)findViewById(2131362928));
    this.mBtnRight.setImageResource(2130838115);
    this.mBtnRight.setOnClickListener(this);
    getActivity().getBaseContext().getResources().getColorStateList(2130839264);
  }

  public void onClick(View paramView)
  {
    if (paramView.equals(this.mBtnLeft))
      finishThisActivity(0);
    do
      return;
    while (!paramView.equals(this.mBtnRight));
    submitAddLocation();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903199, paramViewGroup, false);
  }

  public void onDestroy()
  {
    cancelTask(this.mSubmitLocationTask);
    super.onDestroy();
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    initTitle();
    initTextEdit();
    updateTitle();
    Bundle localBundle = getArguments();
    this.mLon = localBundle.getString("lon");
    this.mLat = localBundle.getString("lat");
    this.mFriends = localBundle.getString("at_firends");
    String str = localBundle.getString("poi_name");
    if ((str != null) && (this.mEditTextLocationName != null))
      this.mEditTextLocationName.setText(str);
  }

  void submitAddLocation()
  {
    if ((this.mSubmitLocationTask != null) && (this.mSubmitLocationTask.getStatus() != AsyncTask.Status.FINISHED))
      return;
    this.mSubmitLocationTask = new SubmitLocationTask(null);
    this.mSubmitLocationTask.execute(new String[] { "" });
    this.m_ProgressDialog = ProgressDialog.show(getActivity(), "", getResources().getText(2131427434), true, true, new DialogInterface.OnCancelListener()
    {
      public void onCancel(DialogInterface paramDialogInterface)
      {
        LbsAddLocationFragment.this.cancelSubmitAddLocation();
      }
    });
  }

  void updateTitle()
  {
    if ((this.mIsLocationNameAvaiable > 0) && (this.mIsAddressNameAvaiable > 0))
    {
      this.mBtnRight.setEnabled(true);
      return;
    }
    this.mBtnRight.setEnabled(false);
  }

  private class SubmitLocationTask extends KXFragment.KXAsyncTask<String, Void, Integer>
  {
    private SubmitLocationTask()
    {
      super();
    }

    protected Integer doInBackgroundA(String[] paramArrayOfString)
    {
      return LbsAddLocationFragment.this.doUploadLocationInformation();
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      if (LbsAddLocationFragment.this.m_ProgressDialog != null)
      {
        LbsAddLocationFragment.this.m_ProgressDialog.dismiss();
        LbsAddLocationFragment.this.m_ProgressDialog = null;
      }
      if (paramInteger == null)
        LbsAddLocationFragment.this.finishThisActivity(0);
      do
      {
        return;
        if (paramInteger.intValue() == 1)
        {
          Toast.makeText(LbsAddLocationFragment.this.getActivity(), 2131428149, 0).show();
          IntentUtil.showCheckInFragment(LbsAddLocationFragment.this.getActivity(), LbsAddLocationFragment.this, LbsAddLocationFragment.this.mPoiId.toString(), LbsAddLocationFragment.this.mEditTextLocationName.getText().toString().trim(), LbsAddLocationFragment.this.mEditTextAddress.getText().toString().trim(), LbsAddLocationFragment.this.mLon, LbsAddLocationFragment.this.mLat, null, true, LbsAddLocationFragment.this.mFriends);
          LbsAddLocationFragment.this.finish();
          return;
        }
        if (paramInteger.intValue() == 104)
        {
          Toast.makeText(LbsAddLocationFragment.this.getActivity(), 2131428166, 0).show();
          return;
        }
        if (paramInteger.intValue() == -1)
        {
          Toast.makeText(LbsAddLocationFragment.this.getActivity(), 2131428162, 0).show();
          return;
        }
        if (paramInteger.intValue() == 102)
        {
          Toast.makeText(LbsAddLocationFragment.this.getActivity(), 2131428163, 0).show();
          return;
        }
        if (paramInteger.intValue() == 103)
        {
          Toast.makeText(LbsAddLocationFragment.this.getActivity(), 2131428165, 0).show();
          return;
        }
        if (paramInteger.intValue() != 101)
          continue;
        Toast.makeText(LbsAddLocationFragment.this.getActivity(), 2131428164, 0).show();
        return;
      }
      while (paramInteger.intValue() != 199);
      Toast.makeText(LbsAddLocationFragment.this.getActivity(), 2131428161, 0).show();
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.LbsAddLocationFragment
 * JD-Core Version:    0.6.0
 */