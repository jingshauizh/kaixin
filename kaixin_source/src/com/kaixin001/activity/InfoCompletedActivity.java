package com.kaixin001.activity;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.businesslogic.LogoutAndExitProxy;
import com.kaixin001.businesslogic.LogoutAndExitProxy.IOnLogoutListener;
import com.kaixin001.engine.InfoCompletedEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.engine.UserInfoEngine;
import com.kaixin001.model.CityModel;
import com.kaixin001.model.Setting;
import com.kaixin001.model.User;
import com.kaixin001.model.UserInfoModel;
import com.kaixin001.network.HttpConnection;
import com.kaixin001.util.ActivityUtil;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.ImageCache;
import com.kaixin001.util.InnerPushManager;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.StringUtil;
import com.kaixin001.view.WheelView;
import com.kaixin001.view.WheelView.ArrayWheelAdapter;
import com.kaixin001.view.WheelView.OnWheelChangedListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import org.json.JSONObject;

public class InfoCompletedActivity extends KXDownloadPicActivity
  implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, View.OnFocusChangeListener
{
  private static final String BOARDCAST_TAG = "com.kx.info.logo";
  public static final int COMPLETE_MODE = 1;
  protected static final int DATE_PICKER_DIALOG = 12;
  public static final String MODE_KEY = "mode";
  public static final int MODIFY_MODE = 0;
  protected static final int SUBMIT_PROGRESS_DIALOG = 11;
  private static final String TAG = "InfoCompletedActivity";
  private boolean bBackKeyPressed = false;
  private String birthday = "";
  private ImageView btnLeft;
  private ImageView changeLogoIcon;
  private String city = "";
  private DatePickerDialog datePickerDialog;
  private EditText evName;
  private EditText evPhoneNum;
  private String filePathName;
  private String from;
  private boolean hasEdit = false;
  private String homeTown = "";
  private ImageView ivLogo;
  private LogoutAndExitProxy logoutAndExit;
  private View mBirthdayLayout;
  private TextView mBirthdayText;
  String[] mCities;
  private View mCityLayout;
  private TextView mCityText;
  private GetDataTask mDataTask;
  private int mDay;
  private int mDefaultCityIndex = 0;
  private int mDefaultProvinceIndex = 0;
  private String mFname;
  private String mFuid;
  private String mGender = null;
  private View mGenderLayout;
  private Bitmap mLoadedBitmap = null;
  private int mMonth;
  private View mPhoneLayout;
  private Dialog mPlaceDialog;
  private ProgressDialog mProgressDialog;
  String[] mProvinces;
  private ArrayList<JSONObject> mUserInfo = new ArrayList();
  private int mYear;
  private String m_flogo;
  private int maxNameLength = 6;
  private int maxPhoneLength = 20;
  private int mode = 0;
  private String phoneNumber = "";
  private View radioFemale;
  private ImageView radioFemaleIcon;
  private View radioMale;
  private ImageView radioMaleIcon;
  private LogoReceiver receiver;
  private Button saveBtn;
  private TextView tvUserName;
  private UpdateUserInfoTask updateUserInfoTask;
  private UserInfoModel userInfoModel = UserInfoModel.getInstance();

  private void backOrExit()
  {
    String str1 = this.evName.getText().toString();
    if (this.mode == 0)
    {
      String str2 = this.evPhoneNum.getText().toString();
      String str3 = this.mBirthdayText.getText().toString();
      String str4 = this.mCityText.getText().toString();
      if ((!TextUtils.isEmpty(this.filePathName)) || (!this.phoneNumber.equals(str2)) || (!this.birthday.equals(str3)) || (!this.homeTown.equals("")) || (!this.city.equals(str4)))
        break label183;
    }
    label183: for (this.hasEdit = false; ((this.mode == 1) && (!TextUtils.isEmpty(str1))) || ((this.mode == 0) && (this.hasEdit)); this.hasEdit = true)
    {
      DialogUtil.showKXAlertDialog(this, 2131427451, 2131427382, 2131427383, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          if (InfoCompletedActivity.this.mode == 1)
          {
            InfoCompletedActivity.this.logout();
            return;
          }
          InfoCompletedActivity.this.finish();
        }
      }
      , new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
        }
      });
      return;
    }
    if (this.mode == 1)
    {
      logout();
      return;
    }
    finish();
  }

  private boolean checkNetworkAndHint()
  {
    int i = 1;
    if (HttpConnection.checkNetworkAvailable(getApplicationContext()) == i);
    while (true)
    {
      if (i == 0)
        showToast(2131427387);
      return i;
      i = 0;
    }
  }

  private void doSubmit()
  {
    String str = this.evName.getText().toString();
    if (this.mode == 1)
    {
      if (TextUtils.isEmpty(str.trim()))
        Toast.makeText(this, 2131427963, 0).show();
    }
    else
      return;
    if (!StringUtil.isChinese(str.trim()))
    {
      Toast.makeText(this, 2131427964, 0).show();
      return;
    }
    if (TextUtils.isEmpty(this.mGender))
    {
      Toast.makeText(this, 2131427965, 0).show();
      return;
    }
    if (TextUtils.isEmpty(this.mBirthdayText.getText().toString().trim()))
    {
      Toast.makeText(this, 2131427966, 0).show();
      return;
    }
    VerifyTask localVerifyTask = new VerifyTask();
    String[] arrayOfString = new String[1];
    arrayOfString[0] = str.trim();
    localVerifyTask.execute(arrayOfString);
  }

  private void initData()
  {
    this.mDataTask = new GetDataTask(null);
    this.mDataTask.execute(new Void[0]);
  }

  private void initDefaultIndex()
  {
    String str = this.mCityText.getText().toString();
    this.mDefaultProvinceIndex = 0;
    this.mDefaultCityIndex = 0;
    int i;
    int k;
    label46: String[] arrayOfString2;
    if (!TextUtils.isEmpty(str))
    {
      i = 0;
      int j = this.mProvinces.length;
      k = 0;
      if (i < j)
        break label76;
      if (k == 0)
        break label133;
      arrayOfString2 = CityModel.getInstance().getCities(this, this.mDefaultProvinceIndex);
    }
    for (int i1 = 0; ; i1++)
    {
      if (i1 >= arrayOfString2.length)
      {
        return;
        label76: if (str.indexOf(this.mProvinces[i]) != -1)
        {
          k = 1;
          this.mDefaultProvinceIndex = i;
          break label46;
        }
        i++;
        break;
      }
      if (str.indexOf(arrayOfString2[i1]) == -1)
        continue;
      this.mDefaultCityIndex = i1;
      return;
    }
    label133: int m = 0;
    label136: String[] arrayOfString1;
    if (m < this.mProvinces.length)
      arrayOfString1 = CityModel.getInstance().getCities(this, m);
    for (int n = 0; ; n++)
    {
      if (n >= arrayOfString1.length)
      {
        m++;
        break label136;
        break;
      }
      if (str.indexOf(arrayOfString1[n]) == -1)
        continue;
      this.mDefaultProvinceIndex = m;
      this.mDefaultCityIndex = n;
      return;
    }
  }

  private void initGenderView()
  {
    this.mGenderLayout = findViewById(2131362835);
    this.radioMaleIcon = ((ImageView)findViewById(2131362840));
    this.radioFemaleIcon = ((ImageView)findViewById(2131362843));
    this.radioMale = findViewById(2131362839);
    this.radioFemale = findViewById(2131362842);
    this.radioMale.setOnClickListener(this);
    this.radioFemale.setOnClickListener(this);
    this.mGender = "0";
  }

  private void initView()
  {
    this.ivLogo = ((ImageView)findViewById(2131362828));
    this.changeLogoIcon = ((ImageView)findViewById(2131362830));
    if (!TextUtils.isEmpty(this.m_flogo))
      displayPicture(this.ivLogo, this.m_flogo, 0);
    this.ivLogo.setOnClickListener(this);
    this.changeLogoIcon.setOnClickListener(this);
    this.evName = ((EditText)findViewById(2131362833));
    this.evName.setOnFocusChangeListener(this);
    InputFilter[] arrayOfInputFilter1 = new InputFilter[1];
    arrayOfInputFilter1[0] = new InputFilter.LengthFilter(this.maxNameLength);
    this.evName.setFilters(arrayOfInputFilter1);
    this.tvUserName = ((TextView)findViewById(2131362834));
    if (this.mFname != null)
      this.tvUserName.setText(this.mFname);
    initGenderView();
    this.mBirthdayLayout = findViewById(2131362845);
    this.mBirthdayLayout.setOnClickListener(this);
    this.mBirthdayText = ((TextView)findViewById(2131362846));
    this.mBirthdayText.setOnFocusChangeListener(this);
    this.mCityLayout = findViewById(2131362847);
    this.mCityLayout.setOnClickListener(this);
    this.mCityText = ((TextView)findViewById(2131362848));
    this.mCityText.setOnFocusChangeListener(this);
    this.mPhoneLayout = findViewById(2131362849);
    this.evPhoneNum = ((EditText)findViewById(2131362850));
    InputFilter[] arrayOfInputFilter2 = new InputFilter[1];
    arrayOfInputFilter2[0] = new InputFilter.LengthFilter(this.maxPhoneLength);
    this.evName.setFilters(arrayOfInputFilter2);
    if (this.mode == 0)
    {
      this.mGenderLayout.setVisibility(8);
      this.evName.setVisibility(8);
    }
    while (true)
    {
      this.saveBtn = ((Button)findViewById(2131362851));
      this.saveBtn.setOnClickListener(this);
      return;
      this.mPhoneLayout.setVisibility(8);
      this.tvUserName.setVisibility(8);
    }
  }

  private void logout()
  {
    this.logoutAndExit.logout(1, new LogoutAndExitProxy.IOnLogoutListener()
    {
      public void onLogout()
      {
        InfoCompletedActivity.this.setResult(0);
        InfoCompletedActivity.this.finish();
      }
    });
  }

  private void registerIntentReceivers()
  {
    this.receiver = new LogoReceiver();
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("com.kx.info.logo");
    registerReceiver(this.receiver, localIntentFilter);
  }

  private void saveNativeUserInfo()
  {
    String str = this.evName.getText().toString();
    User.getInstance().setName(str);
    MainActivity.isRefresh = true;
    User.getInstance().saveUserInfo(this);
  }

  private void setFocusBg(int paramInt)
  {
    findViewById(paramInt).setBackgroundResource(2130838413);
  }

  private void setNOfocusBg(int paramInt)
  {
    findViewById(paramInt).setBackgroundResource(2130838411);
  }

  private void showMyDialog(int paramInt)
  {
    dismissDialog();
    switch (paramInt)
    {
    default:
      return;
    case 11:
      this.dialog = ProgressDialog.show(this, "", getResources().getText(2131427434), true, true, new DialogInterface.OnCancelListener()
      {
        public void onCancel(DialogInterface paramDialogInterface)
        {
          InfoCompletedActivity.this.updateUserInfoTask.cancel(true);
        }
      });
      this.dialog.show();
      return;
    case 12:
    }
    if (this.mYear == 0)
    {
      Calendar localCalendar = Calendar.getInstance();
      this.mYear = (-20 + localCalendar.get(1));
      this.mMonth = localCalendar.get(2);
      this.mDay = localCalendar.get(5);
    }
    this.datePickerDialog = new MyDatePickerDialog(this, new DatePickerDialog.OnDateSetListener()
    {
      public void onDateSet(DatePicker paramDatePicker, int paramInt1, int paramInt2, int paramInt3)
      {
        InfoCompletedActivity.this.mYear = paramInt1;
        InfoCompletedActivity.this.mMonth = paramInt2;
        InfoCompletedActivity.this.mDay = paramInt3;
        StringBuffer localStringBuffer = new StringBuffer();
        localStringBuffer.append(InfoCompletedActivity.this.mYear).append(".").append(1 + InfoCompletedActivity.this.mMonth).append(".").append(InfoCompletedActivity.this.mDay);
        InfoCompletedActivity.this.mBirthdayText.setText(localStringBuffer.toString());
        InfoCompletedActivity.this.datePickerDialog.onDateChanged(paramDatePicker, paramInt1, paramInt2, paramInt3);
      }
    }
    , this.mYear, this.mMonth, this.mDay);
    this.dialog = this.datePickerDialog;
    this.dialog.show();
  }

  private void showPlacePicker()
  {
    this.mProvinces = CityModel.getInstance().getProvinces(this);
    initDefaultIndex();
    this.mCities = CityModel.getInstance().getCities(this, this.mDefaultProvinceIndex);
    this.mPlaceDialog = new Dialog(this);
    this.mPlaceDialog.setTitle(getResources().getText(2131427960));
    View localView = ((LayoutInflater)getSystemService("layout_inflater")).inflate(2130903398, null);
    WheelView localWheelView1 = (WheelView)localView.findViewById(2131363921);
    localWheelView1.getClass();
    localWheelView1.setAdapter(new WheelView.ArrayWheelAdapter(localWheelView1, this.mProvinces));
    localWheelView1.setCyclic(true);
    localWheelView1.setLabel("");
    localWheelView1.setCurrentItem(this.mDefaultProvinceIndex);
    WheelView localWheelView2 = (WheelView)localView.findViewById(2131363922);
    localWheelView2.getClass();
    localWheelView2.setAdapter(new WheelView.ArrayWheelAdapter(localWheelView2, this.mCities));
    localWheelView2.setCyclic(true);
    localWheelView2.setLabel("");
    localWheelView2.setCurrentItem(this.mDefaultCityIndex);
    localWheelView1.addChangingListener(new WheelView.OnWheelChangedListener(localWheelView2)
    {
      public void onChanged(WheelView paramWheelView, int paramInt1, int paramInt2)
      {
        InfoCompletedActivity.this.mCities = CityModel.getInstance().getCities(InfoCompletedActivity.this, paramInt2);
        WheelView localWheelView1 = this.val$wv2;
        WheelView localWheelView2 = this.val$wv2;
        localWheelView2.getClass();
        localWheelView1.setAdapter(new WheelView.ArrayWheelAdapter(localWheelView2, InfoCompletedActivity.this.mCities));
        this.val$wv2.setCurrentItem(0);
      }
    });
    int i = (int)(16.0F * getResources().getDisplayMetrics().density);
    localWheelView2.TEXT_SIZE = i;
    localWheelView1.TEXT_SIZE = i;
    Button localButton1 = (Button)localView.findViewById(2131363923);
    Button localButton2 = (Button)localView.findViewById(2131363924);
    localButton1.setOnClickListener(new View.OnClickListener(localWheelView1, localWheelView2)
    {
      public void onClick(View paramView)
      {
        String str = InfoCompletedActivity.this.mProvinces[this.val$wv1.getCurrentItem()] + InfoCompletedActivity.this.mCities[this.val$wv2.getCurrentItem()];
        InfoCompletedActivity.this.mCityText.setText(str);
        InfoCompletedActivity.this.mPlaceDialog.dismiss();
      }
    });
    localButton2.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        InfoCompletedActivity.this.mPlaceDialog.dismiss();
      }
    });
    this.mPlaceDialog.setContentView(localView);
    this.mPlaceDialog.show();
  }

  private void showUploadPhotoOptions()
  {
    Intent localIntent = new Intent(this, MainActivity.class);
    localIntent.putExtra("fragment", "PhotoSelectFragment");
    localIntent.putExtra("single", true);
    localIntent.putExtra("callfrom", "activity");
    localIntent.putExtra("callcode", 103);
    localIntent.putExtra("boardcast", "com.kx.info.logo");
    localIntent.putExtra("from", "InfoCompletedActivity_select_flogo");
    AnimationUtil.startActivityForResult(this, localIntent, 103, 1);
  }

  private void unregisterIntentReceivers()
  {
    if (this.receiver != null)
      unregisterReceiver(this.receiver);
  }

  protected void dealPhotoResult(String paramString1, String paramString2)
  {
    MainActivity.destroySelf();
    if (TextUtils.isEmpty(paramString1));
    File localFile;
    do
    {
      return;
      localFile = new File(paramString1);
    }
    while (!localFile.exists());
    Intent localIntent = new Intent("com.android.camera.action.CROP");
    localIntent.setDataAndType(Uri.fromFile(localFile), "image/*");
    localIntent.putExtra("crop", "true");
    localIntent.putExtra("aspectX", 1);
    localIntent.putExtra("aspectY", 1);
    localIntent.putExtra("outputX", 120);
    localIntent.putExtra("outputY", 120);
    localIntent.putExtra("noFaceDetection", true);
    localIntent.putExtra("return-data", true);
    startActivityForResult(localIntent, 112);
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if (paramInt2 == -1)
    {
      if (paramInt1 == 112)
      {
        if (paramIntent == null);
        Bundle localBundle;
        do
        {
          return;
          localBundle = paramIntent.getExtras();
        }
        while (localBundle == null);
        this.filePathName = (Environment.getExternalStorageDirectory() + "/kaixin001" + "/" + "kx_header_tmp.jpg");
        Bitmap localBitmap = (Bitmap)localBundle.getParcelable("data");
        try
        {
          ImageCache.saveBitmapToFileWithAbsolutePath(this, localBitmap, null, this.filePathName);
          if (localBitmap != null)
          {
            this.ivLogo.setImageBitmap(localBitmap);
            return;
          }
        }
        catch (Exception localException)
        {
          while (true)
            localException.printStackTrace();
          Toast.makeText(this, 2131428303, 0).show();
          return;
        }
      }
      super.onActivityResult(paramInt1, paramInt2, paramIntent);
      return;
    }
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
  }

  public void onCheckedChanged(RadioGroup paramRadioGroup, int paramInt)
  {
    if (paramInt == this.radioMale.getId())
    {
      this.mGender = "0";
      this.ivLogo.setBackgroundResource(2130838881);
      this.radioMaleIcon.setBackgroundResource(2130838705);
      this.radioFemaleIcon.setBackgroundResource(2130838706);
      return;
    }
    this.mGender = "1";
    this.ivLogo.setBackgroundResource(2130838880);
    this.radioMaleIcon.setBackgroundResource(2130838706);
    this.radioFemaleIcon.setBackgroundResource(2130838705);
  }

  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    case 2131362931:
    default:
    case 2131362914:
    case 2131362828:
    case 2131362830:
    case 2131362845:
    case 2131362847:
    case 2131362851:
      do
      {
        return;
        backOrExit();
        return;
        showUploadPhotoOptions();
        return;
        View localView = getCurrentFocus();
        if (localView != null)
          ((InputMethodManager)getSystemService("input_method")).hideSoftInputFromWindow(localView.getWindowToken(), 2);
        showMyDialog(12);
        return;
        showPlacePicker();
        return;
        ActivityUtil.hideInputMethod(this);
      }
      while (!checkNetworkAndHint());
      doSubmit();
      return;
    case 2131362839:
      this.mGender = "0";
      this.ivLogo.setBackgroundResource(2130838881);
      this.radioMaleIcon.setBackgroundResource(2130838705);
      this.radioFemaleIcon.setBackgroundResource(2130838706);
      return;
    case 2131362842:
    }
    this.mGender = "1";
    this.ivLogo.setBackgroundResource(2130838880);
    this.radioMaleIcon.setBackgroundResource(2130838706);
    this.radioFemaleIcon.setBackgroundResource(2130838705);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903175);
    Bundle localBundle = getIntent().getExtras();
    if (localBundle != null)
    {
      this.mode = localBundle.getInt("mode", 0);
      String str1 = localBundle.getString("fuid");
      if (str1 != null)
        this.mFuid = str1;
      String str2 = localBundle.getString("fname");
      if (str2 != null)
        this.mFname = str2;
      String str3 = localBundle.getString("flogo");
      if (str3 != null)
        this.m_flogo = str3;
      String str4 = localBundle.getString("from");
      if (str4 != null)
        this.from = str4;
    }
    setTitleBar();
    initView();
    initData();
    this.logoutAndExit = new LogoutAndExitProxy(this);
    registerIntentReceivers();
    MainActivity.destroySelf();
  }

  protected void onDestroy()
  {
    dismissDialog();
    if (this.mLoadedBitmap != null)
      this.mLoadedBitmap.recycle();
    unregisterIntentReceivers();
    super.onDestroy();
  }

  public void onFocusChange(View paramView, boolean paramBoolean)
  {
    if (paramBoolean)
      if (paramView == this.evName)
        setFocusBg(2131362832);
    do
    {
      do
      {
        return;
        if (paramView != this.mBirthdayText)
          continue;
        setFocusBg(2131362845);
        return;
      }
      while (paramView != this.mCityText);
      setFocusBg(2131362847);
      return;
      if (paramView == this.evName)
      {
        setNOfocusBg(2131362832);
        return;
      }
      if (paramView != this.mBirthdayText)
        continue;
      setNOfocusBg(2131362845);
      return;
    }
    while (paramView != this.mCityText);
    setNOfocusBg(2131362847);
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 4)
    {
      if (!Setting.getInstance().isTestVersion())
      {
        if (this.bBackKeyPressed)
        {
          KXApplication.getInstance().clearCountLogin();
          InnerPushManager.getInstance(this).setLoginTime();
          finish();
          return true;
        }
        Toast.makeText(this, 2131427399, 0).show();
        this.bBackKeyPressed = true;
        return true;
      }
      return false;
    }
    return super.onKeyDown(paramInt, paramKeyEvent);
  }

  public void requestFinish()
  {
    backOrExit();
  }

  protected void setTitleBar()
  {
    this.btnLeft = ((ImageView)findViewById(2131362914));
    this.btnLeft.setOnClickListener(this);
    TextView localTextView1 = (TextView)findViewById(2131362920);
    if (this.mode == 1)
      localTextView1.setText(2131427958);
    while (true)
    {
      localTextView1.setVisibility(0);
      findViewById(2131362919).setVisibility(8);
      findViewById(2131362928).setVisibility(8);
      if ((this.from != null) && ((this.from.equals("LoginActivity")) || (this.from.equals("PhoneRegisterActivity"))))
        this.btnLeft.setVisibility(8);
      TextView localTextView2 = (TextView)findViewById(2131362922);
      View localView1 = findViewById(2131362914);
      View localView2 = findViewById(2131362916);
      View localView3 = findViewById(2131362917);
      localView1.setVisibility(8);
      localView2.setVisibility(8);
      localView3.setVisibility(8);
      localTextView1.setVisibility(8);
      localTextView2.setVisibility(0);
      localTextView2.setText(2131427958);
      return;
      this.mProgressDialog = ProgressDialog.show(this, "", getResources().getText(2131428245), true, true, null);
      localTextView1.setText(2131427956);
    }
  }

  private class GetDataTask extends AsyncTask<Void, Void, Integer>
  {
    private GetDataTask()
    {
    }

    protected Integer doInBackground(Void[] paramArrayOfVoid)
    {
      try
      {
        if (UserInfoEngine.getInstance().getUserInfo(InfoCompletedActivity.this.getApplicationContext(), InfoCompletedActivity.this.mFuid))
        {
          Integer localInteger = Integer.valueOf(0);
          return localInteger;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("HomeActivity", "GetDataTask", localException);
      }
      return Integer.valueOf(-1);
    }

    // ERROR //
    protected void onPostExecute(Integer paramInteger)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 11	com/kaixin001/activity/InfoCompletedActivity$GetDataTask:this$0	Lcom/kaixin001/activity/InfoCompletedActivity;
      //   4: invokestatic 68	com/kaixin001/activity/InfoCompletedActivity:access$1	(Lcom/kaixin001/activity/InfoCompletedActivity;)Landroid/app/ProgressDialog;
      //   7: ifnull +13 -> 20
      //   10: aload_0
      //   11: getfield 11	com/kaixin001/activity/InfoCompletedActivity$GetDataTask:this$0	Lcom/kaixin001/activity/InfoCompletedActivity;
      //   14: invokestatic 68	com/kaixin001/activity/InfoCompletedActivity:access$1	(Lcom/kaixin001/activity/InfoCompletedActivity;)Landroid/app/ProgressDialog;
      //   17: invokevirtual 73	android/app/ProgressDialog:dismiss	()V
      //   20: aload_1
      //   21: invokevirtual 77	java/lang/Integer:intValue	()I
      //   24: ifne +558 -> 582
      //   27: aload_0
      //   28: getfield 11	com/kaixin001/activity/InfoCompletedActivity$GetDataTask:this$0	Lcom/kaixin001/activity/InfoCompletedActivity;
      //   31: invokevirtual 81	com/kaixin001/activity/InfoCompletedActivity:getResources	()Landroid/content/res/Resources;
      //   34: ldc 82
      //   36: invokevirtual 88	android/content/res/Resources:getString	(I)Ljava/lang/String;
      //   39: astore_3
      //   40: aload_0
      //   41: getfield 11	com/kaixin001/activity/InfoCompletedActivity$GetDataTask:this$0	Lcom/kaixin001/activity/InfoCompletedActivity;
      //   44: invokevirtual 81	com/kaixin001/activity/InfoCompletedActivity:getResources	()Landroid/content/res/Resources;
      //   47: ldc 89
      //   49: invokevirtual 88	android/content/res/Resources:getString	(I)Ljava/lang/String;
      //   52: astore 4
      //   54: aload_0
      //   55: getfield 11	com/kaixin001/activity/InfoCompletedActivity$GetDataTask:this$0	Lcom/kaixin001/activity/InfoCompletedActivity;
      //   58: invokevirtual 81	com/kaixin001/activity/InfoCompletedActivity:getResources	()Landroid/content/res/Resources;
      //   61: ldc 90
      //   63: invokevirtual 88	android/content/res/Resources:getString	(I)Ljava/lang/String;
      //   66: astore 5
      //   68: aload_0
      //   69: getfield 11	com/kaixin001/activity/InfoCompletedActivity$GetDataTask:this$0	Lcom/kaixin001/activity/InfoCompletedActivity;
      //   72: invokevirtual 81	com/kaixin001/activity/InfoCompletedActivity:getResources	()Landroid/content/res/Resources;
      //   75: ldc 91
      //   77: invokevirtual 88	android/content/res/Resources:getString	(I)Ljava/lang/String;
      //   80: astore 6
      //   82: aload_0
      //   83: getfield 11	com/kaixin001/activity/InfoCompletedActivity$GetDataTask:this$0	Lcom/kaixin001/activity/InfoCompletedActivity;
      //   86: invokevirtual 81	com/kaixin001/activity/InfoCompletedActivity:getResources	()Landroid/content/res/Resources;
      //   89: ldc 92
      //   91: invokevirtual 88	android/content/res/Resources:getString	(I)Ljava/lang/String;
      //   94: astore 7
      //   96: aload_0
      //   97: getfield 11	com/kaixin001/activity/InfoCompletedActivity$GetDataTask:this$0	Lcom/kaixin001/activity/InfoCompletedActivity;
      //   100: aload_0
      //   101: getfield 11	com/kaixin001/activity/InfoCompletedActivity$GetDataTask:this$0	Lcom/kaixin001/activity/InfoCompletedActivity;
      //   104: invokestatic 96	com/kaixin001/activity/InfoCompletedActivity:access$2	(Lcom/kaixin001/activity/InfoCompletedActivity;)Lcom/kaixin001/model/UserInfoModel;
      //   107: invokevirtual 102	com/kaixin001/model/UserInfoModel:getUserInfoList	()Ljava/util/ArrayList;
      //   110: invokestatic 106	com/kaixin001/activity/InfoCompletedActivity:access$3	(Lcom/kaixin001/activity/InfoCompletedActivity;Ljava/util/ArrayList;)V
      //   113: iconst_0
      //   114: istore 8
      //   116: iload 8
      //   118: aload_0
      //   119: getfield 11	com/kaixin001/activity/InfoCompletedActivity$GetDataTask:this$0	Lcom/kaixin001/activity/InfoCompletedActivity;
      //   122: invokestatic 110	com/kaixin001/activity/InfoCompletedActivity:access$4	(Lcom/kaixin001/activity/InfoCompletedActivity;)Ljava/util/ArrayList;
      //   125: invokevirtual 115	java/util/ArrayList:size	()I
      //   128: if_icmplt +26 -> 154
      //   131: aload_0
      //   132: getfield 11	com/kaixin001/activity/InfoCompletedActivity$GetDataTask:this$0	Lcom/kaixin001/activity/InfoCompletedActivity;
      //   135: invokestatic 119	com/kaixin001/activity/InfoCompletedActivity:access$18	(Lcom/kaixin001/activity/InfoCompletedActivity;)Landroid/widget/ImageView;
      //   138: iconst_1
      //   139: invokevirtual 125	android/widget/ImageView:setEnabled	(Z)V
      //   142: aload_0
      //   143: getfield 11	com/kaixin001/activity/InfoCompletedActivity$GetDataTask:this$0	Lcom/kaixin001/activity/InfoCompletedActivity;
      //   146: invokestatic 128	com/kaixin001/activity/InfoCompletedActivity:access$19	(Lcom/kaixin001/activity/InfoCompletedActivity;)Landroid/widget/ImageView;
      //   149: iconst_1
      //   150: invokevirtual 125	android/widget/ImageView:setEnabled	(Z)V
      //   153: return
      //   154: aload_0
      //   155: getfield 11	com/kaixin001/activity/InfoCompletedActivity$GetDataTask:this$0	Lcom/kaixin001/activity/InfoCompletedActivity;
      //   158: invokestatic 110	com/kaixin001/activity/InfoCompletedActivity:access$4	(Lcom/kaixin001/activity/InfoCompletedActivity;)Ljava/util/ArrayList;
      //   161: iload 8
      //   163: invokevirtual 132	java/util/ArrayList:get	(I)Ljava/lang/Object;
      //   166: checkcast 134	org/json/JSONObject
      //   169: ldc 136
      //   171: invokevirtual 139	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
      //   174: astore 9
      //   176: aload 9
      //   178: ldc 141
      //   180: invokevirtual 147	java/lang/String:equals	(Ljava/lang/Object;)Z
      //   183: ifne +400 -> 583
      //   186: aload 9
      //   188: ldc 149
      //   190: invokevirtual 147	java/lang/String:equals	(Ljava/lang/Object;)Z
      //   193: ifne +390 -> 583
      //   196: aload_0
      //   197: getfield 11	com/kaixin001/activity/InfoCompletedActivity$GetDataTask:this$0	Lcom/kaixin001/activity/InfoCompletedActivity;
      //   200: invokestatic 110	com/kaixin001/activity/InfoCompletedActivity:access$4	(Lcom/kaixin001/activity/InfoCompletedActivity;)Ljava/util/ArrayList;
      //   203: iload 8
      //   205: invokevirtual 132	java/util/ArrayList:get	(I)Ljava/lang/Object;
      //   208: checkcast 134	org/json/JSONObject
      //   211: ldc 151
      //   213: invokevirtual 139	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
      //   216: astore 10
      //   218: ldc 51
      //   220: new 153	java/lang/StringBuilder
      //   223: dup
      //   224: ldc 155
      //   226: invokespecial 158	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   229: aload 9
      //   231: invokevirtual 162	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   234: invokevirtual 166	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   237: invokestatic 170	com/kaixin001/util/KXLog:d	(Ljava/lang/String;Ljava/lang/String;)V
      //   240: ldc 51
      //   242: new 153	java/lang/StringBuilder
      //   245: dup
      //   246: ldc 172
      //   248: invokespecial 158	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   251: aload 10
      //   253: invokevirtual 162	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   256: invokevirtual 166	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   259: invokestatic 170	com/kaixin001/util/KXLog:d	(Ljava/lang/String;Ljava/lang/String;)V
      //   262: aload 9
      //   264: aload_3
      //   265: invokevirtual 147	java/lang/String:equals	(Ljava/lang/Object;)Z
      //   268: ifeq +27 -> 295
      //   271: aload_0
      //   272: getfield 11	com/kaixin001/activity/InfoCompletedActivity$GetDataTask:this$0	Lcom/kaixin001/activity/InfoCompletedActivity;
      //   275: aload 10
      //   277: invokestatic 176	com/kaixin001/activity/InfoCompletedActivity:access$5	(Lcom/kaixin001/activity/InfoCompletedActivity;Ljava/lang/String;)V
      //   280: aload_0
      //   281: getfield 11	com/kaixin001/activity/InfoCompletedActivity$GetDataTask:this$0	Lcom/kaixin001/activity/InfoCompletedActivity;
      //   284: invokestatic 180	com/kaixin001/activity/InfoCompletedActivity:access$6	(Lcom/kaixin001/activity/InfoCompletedActivity;)Landroid/widget/EditText;
      //   287: aload 10
      //   289: invokevirtual 186	android/widget/EditText:setText	(Ljava/lang/CharSequence;)V
      //   292: goto +291 -> 583
      //   295: aload 9
      //   297: aload 4
      //   299: invokevirtual 147	java/lang/String:equals	(Ljava/lang/Object;)Z
      //   302: ifeq +176 -> 478
      //   305: aload_0
      //   306: getfield 11	com/kaixin001/activity/InfoCompletedActivity$GetDataTask:this$0	Lcom/kaixin001/activity/InfoCompletedActivity;
      //   309: aload 10
      //   311: invokestatic 189	com/kaixin001/activity/InfoCompletedActivity:access$7	(Lcom/kaixin001/activity/InfoCompletedActivity;Ljava/lang/String;)V
      //   314: aload_0
      //   315: getfield 11	com/kaixin001/activity/InfoCompletedActivity$GetDataTask:this$0	Lcom/kaixin001/activity/InfoCompletedActivity;
      //   318: invokestatic 192	com/kaixin001/activity/InfoCompletedActivity:access$8	(Lcom/kaixin001/activity/InfoCompletedActivity;)Ljava/lang/String;
      //   321: ldc 194
      //   323: invokevirtual 198	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
      //   326: ifeq +93 -> 419
      //   329: aload_0
      //   330: getfield 11	com/kaixin001/activity/InfoCompletedActivity$GetDataTask:this$0	Lcom/kaixin001/activity/InfoCompletedActivity;
      //   333: aload 10
      //   335: iconst_0
      //   336: iconst_4
      //   337: invokevirtual 202	java/lang/String:substring	(II)Ljava/lang/String;
      //   340: invokestatic 205	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
      //   343: invokevirtual 77	java/lang/Integer:intValue	()I
      //   346: invokestatic 209	com/kaixin001/activity/InfoCompletedActivity:access$9	(Lcom/kaixin001/activity/InfoCompletedActivity;I)V
      //   349: aload_0
      //   350: getfield 11	com/kaixin001/activity/InfoCompletedActivity$GetDataTask:this$0	Lcom/kaixin001/activity/InfoCompletedActivity;
      //   353: iconst_m1
      //   354: aload 10
      //   356: iconst_5
      //   357: bipush 7
      //   359: invokevirtual 202	java/lang/String:substring	(II)Ljava/lang/String;
      //   362: invokestatic 205	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
      //   365: invokevirtual 77	java/lang/Integer:intValue	()I
      //   368: iadd
      //   369: invokestatic 212	com/kaixin001/activity/InfoCompletedActivity:access$10	(Lcom/kaixin001/activity/InfoCompletedActivity;I)V
      //   372: aload_0
      //   373: getfield 11	com/kaixin001/activity/InfoCompletedActivity$GetDataTask:this$0	Lcom/kaixin001/activity/InfoCompletedActivity;
      //   376: aload 10
      //   378: bipush 8
      //   380: bipush 10
      //   382: invokevirtual 202	java/lang/String:substring	(II)Ljava/lang/String;
      //   385: invokestatic 205	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
      //   388: invokevirtual 77	java/lang/Integer:intValue	()I
      //   391: invokestatic 215	com/kaixin001/activity/InfoCompletedActivity:access$11	(Lcom/kaixin001/activity/InfoCompletedActivity;I)V
      //   394: aload_0
      //   395: getfield 11	com/kaixin001/activity/InfoCompletedActivity$GetDataTask:this$0	Lcom/kaixin001/activity/InfoCompletedActivity;
      //   398: invokestatic 219	com/kaixin001/activity/InfoCompletedActivity:access$12	(Lcom/kaixin001/activity/InfoCompletedActivity;)Landroid/widget/TextView;
      //   401: aload 10
      //   403: invokevirtual 222	android/widget/TextView:setText	(Ljava/lang/CharSequence;)V
      //   406: goto +177 -> 583
      //   409: astore_2
      //   410: ldc 224
      //   412: ldc 225
      //   414: aload_2
      //   415: invokestatic 57	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
      //   418: return
      //   419: aload_0
      //   420: getfield 11	com/kaixin001/activity/InfoCompletedActivity$GetDataTask:this$0	Lcom/kaixin001/activity/InfoCompletedActivity;
      //   423: iconst_m1
      //   424: aload 10
      //   426: iconst_0
      //   427: iconst_2
      //   428: invokevirtual 202	java/lang/String:substring	(II)Ljava/lang/String;
      //   431: invokestatic 205	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
      //   434: invokevirtual 77	java/lang/Integer:intValue	()I
      //   437: iadd
      //   438: invokestatic 212	com/kaixin001/activity/InfoCompletedActivity:access$10	(Lcom/kaixin001/activity/InfoCompletedActivity;I)V
      //   441: aload_0
      //   442: getfield 11	com/kaixin001/activity/InfoCompletedActivity$GetDataTask:this$0	Lcom/kaixin001/activity/InfoCompletedActivity;
      //   445: aload 10
      //   447: iconst_3
      //   448: iconst_5
      //   449: invokevirtual 202	java/lang/String:substring	(II)Ljava/lang/String;
      //   452: invokestatic 205	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
      //   455: invokevirtual 77	java/lang/Integer:intValue	()I
      //   458: invokestatic 215	com/kaixin001/activity/InfoCompletedActivity:access$11	(Lcom/kaixin001/activity/InfoCompletedActivity;I)V
      //   461: goto -67 -> 394
      //   464: astore 11
      //   466: ldc 227
      //   468: ldc 229
      //   470: aload 11
      //   472: invokestatic 57	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
      //   475: goto -81 -> 394
      //   478: aload 9
      //   480: aload 5
      //   482: invokevirtual 147	java/lang/String:equals	(Ljava/lang/Object;)Z
      //   485: ifeq +15 -> 500
      //   488: aload_0
      //   489: getfield 11	com/kaixin001/activity/InfoCompletedActivity$GetDataTask:this$0	Lcom/kaixin001/activity/InfoCompletedActivity;
      //   492: aload 10
      //   494: invokestatic 232	com/kaixin001/activity/InfoCompletedActivity:access$13	(Lcom/kaixin001/activity/InfoCompletedActivity;Ljava/lang/String;)V
      //   497: goto +86 -> 583
      //   500: aload 9
      //   502: aload 6
      //   504: invokevirtual 147	java/lang/String:equals	(Ljava/lang/Object;)Z
      //   507: ifeq +27 -> 534
      //   510: aload_0
      //   511: getfield 11	com/kaixin001/activity/InfoCompletedActivity$GetDataTask:this$0	Lcom/kaixin001/activity/InfoCompletedActivity;
      //   514: aload 10
      //   516: invokestatic 235	com/kaixin001/activity/InfoCompletedActivity:access$14	(Lcom/kaixin001/activity/InfoCompletedActivity;Ljava/lang/String;)V
      //   519: aload_0
      //   520: getfield 11	com/kaixin001/activity/InfoCompletedActivity$GetDataTask:this$0	Lcom/kaixin001/activity/InfoCompletedActivity;
      //   523: invokestatic 238	com/kaixin001/activity/InfoCompletedActivity:access$15	(Lcom/kaixin001/activity/InfoCompletedActivity;)Landroid/widget/TextView;
      //   526: aload 10
      //   528: invokevirtual 222	android/widget/TextView:setText	(Ljava/lang/CharSequence;)V
      //   531: goto +52 -> 583
      //   534: aload 9
      //   536: aload 7
      //   538: invokevirtual 147	java/lang/String:equals	(Ljava/lang/Object;)Z
      //   541: ifeq +42 -> 583
      //   544: aload 10
      //   546: ldc 240
      //   548: invokevirtual 147	java/lang/String:equals	(Ljava/lang/Object;)Z
      //   551: ifeq +17 -> 568
      //   554: aload_0
      //   555: getfield 11	com/kaixin001/activity/InfoCompletedActivity$GetDataTask:this$0	Lcom/kaixin001/activity/InfoCompletedActivity;
      //   558: invokestatic 244	com/kaixin001/activity/InfoCompletedActivity:access$16	(Lcom/kaixin001/activity/InfoCompletedActivity;)Landroid/view/View;
      //   561: iconst_1
      //   562: invokevirtual 249	android/view/View:setSelected	(Z)V
      //   565: goto +18 -> 583
      //   568: aload_0
      //   569: getfield 11	com/kaixin001/activity/InfoCompletedActivity$GetDataTask:this$0	Lcom/kaixin001/activity/InfoCompletedActivity;
      //   572: invokestatic 252	com/kaixin001/activity/InfoCompletedActivity:access$17	(Lcom/kaixin001/activity/InfoCompletedActivity;)Landroid/view/View;
      //   575: iconst_1
      //   576: invokevirtual 249	android/view/View:setSelected	(Z)V
      //   579: goto +4 -> 583
      //   582: return
      //   583: iinc 8 1
      //   586: goto -470 -> 116
      //
      // Exception table:
      //   from	to	target	type
      //   27	113	409	java/lang/Exception
      //   116	153	409	java/lang/Exception
      //   154	292	409	java/lang/Exception
      //   295	314	409	java/lang/Exception
      //   394	406	409	java/lang/Exception
      //   466	475	409	java/lang/Exception
      //   478	497	409	java/lang/Exception
      //   500	531	409	java/lang/Exception
      //   534	565	409	java/lang/Exception
      //   568	579	409	java/lang/Exception
      //   314	394	464	java/lang/Exception
      //   419	461	464	java/lang/Exception
    }
  }

  public class LogoReceiver extends BroadcastReceiver
  {
    public LogoReceiver()
    {
    }

    public void onReceive(Context paramContext, Intent paramIntent)
    {
      Bundle localBundle = paramIntent.getExtras();
      if (localBundle != null)
      {
        int i = localBundle.getInt("callcode");
        InfoCompletedActivity.this.onActivityResult(i, -1, paramIntent);
      }
    }
  }

  public static class MyDatePickerDialog extends DatePickerDialog
  {
    SimpleDateFormat mTitleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");

    public MyDatePickerDialog(Context paramContext, DatePickerDialog.OnDateSetListener paramOnDateSetListener, int paramInt1, int paramInt2, int paramInt3)
    {
      super(paramOnDateSetListener, paramInt1, paramInt2, paramInt3);
      resetTitle(paramInt1, paramInt2, paramInt3);
    }

    public void onDateChanged(DatePicker paramDatePicker, int paramInt1, int paramInt2, int paramInt3)
    {
      super.onDateChanged(paramDatePicker, paramInt1, paramInt2, paramInt3);
      resetTitle(paramInt1, paramInt2, paramInt3);
    }

    public void resetTitle(int paramInt1, int paramInt2, int paramInt3)
    {
      Calendar localCalendar = Calendar.getInstance();
      if (localCalendar != null)
      {
        localCalendar.set(1, paramInt1);
        localCalendar.set(2, paramInt2);
        localCalendar.set(5, paramInt3);
        if (this.mTitleDateFormat != null)
          setTitle(this.mTitleDateFormat.format(localCalendar.getTime()));
      }
    }
  }

  private class UpdateUserInfoTask extends AsyncTask<String, Void, Integer>
  {
    private UpdateUserInfoTask()
    {
    }

    protected Integer doInBackground(String[] paramArrayOfString)
    {
      if ((paramArrayOfString == null) || (paramArrayOfString.length == 0) || (paramArrayOfString.length != 8))
        return null;
      String str1 = paramArrayOfString[0];
      String str2 = paramArrayOfString[1];
      String str3 = paramArrayOfString[2];
      String str4 = paramArrayOfString[3];
      String str5 = paramArrayOfString[4];
      String str6 = paramArrayOfString[5];
      String str7 = paramArrayOfString[6];
      String str8 = paramArrayOfString[7];
      try
      {
        if (InfoCompletedActivity.this.mode == 1)
          return Integer.valueOf(InfoCompletedEngine.getInstance().postInfoCompletedRequest(InfoCompletedActivity.this, str1, str2, str3, str4, str5, str8, "", "", InfoCompletedActivity.this.filePathName, InfoCompletedActivity.this.mFuid));
        Integer localInteger = Integer.valueOf(InfoCompletedEngine.getInstance().getInfoUpdatedRequest(InfoCompletedActivity.this, str7, str3, str4, str5, str6, str8, InfoCompletedActivity.this.filePathName, InfoCompletedActivity.this.mFuid, str2));
        return localInteger;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
      }
      return null;
    }

    protected void onPostExecute(Integer paramInteger)
    {
      if (paramInteger == null)
        return;
      InfoCompletedActivity.this.dismissDialog();
      KXEnvironment.saveBooleanParams(InfoCompletedActivity.this.getApplicationContext(), "needCompleteInfo", true, false);
      if (paramInteger.intValue() == 1)
      {
        InfoCompletedActivity.this.saveNativeUserInfo();
        if (!TextUtils.isEmpty(InfoCompletedActivity.this.from))
        {
          if ((InfoCompletedActivity.this.from.equals("MainActivity")) || (InfoCompletedActivity.this.from.equals("PhoneRegisterActivity")))
          {
            User.getInstance().setImcomplete(0);
            Intent localIntent2 = new Intent(InfoCompletedActivity.this, MainActivity.class);
            localIntent2.putExtra("fragment", "NewsFragment");
            InfoCompletedActivity.this.startActivity(localIntent2);
            return;
          }
          Intent localIntent3 = new Intent();
          InfoCompletedActivity.this.setResult(-1, localIntent3);
          InfoCompletedActivity.this.finish();
          return;
        }
        Toast.makeText(InfoCompletedActivity.this.getApplicationContext(), 2131427955, 1).show();
        Intent localIntent1 = new Intent();
        boolean bool1 = TextUtils.isEmpty(InfoCompletedActivity.this.filePathName);
        boolean bool2 = false;
        if (bool1);
        while (true)
        {
          localIntent1.putExtra("changelogo", bool2);
          InfoCompletedActivity.this.setResult(-1, localIntent1);
          InfoCompletedActivity.this.finish();
          return;
          bool2 = true;
        }
      }
      String str = InfoCompletedEngine.getInstance().msg;
      if ((str != null) && (str.equals("生日日期不正确")))
      {
        Toast.makeText(InfoCompletedActivity.this, 2131428305, 0).show();
        return;
      }
      if (!TextUtils.isEmpty(str))
      {
        Toast.makeText(InfoCompletedActivity.this, str, 0).show();
        return;
      }
      Toast.makeText(InfoCompletedActivity.this, 2131428304, 0).show();
    }
  }

  public class VerifyTask extends AsyncTask<String, Void, Object>
  {
    public VerifyTask()
    {
    }

    protected Object doInBackground(String[] paramArrayOfString)
    {
      if (TextUtils.isEmpty(paramArrayOfString[0]))
        return Integer.valueOf(0);
      String str = paramArrayOfString[0];
      return InfoCompletedEngine.getInstance().verifyNameLegal(InfoCompletedActivity.this, str);
    }

    protected void onPostExecute(Object paramObject)
    {
      super.onPostExecute(paramObject);
      if (paramObject == null)
        Toast.makeText(InfoCompletedActivity.this, "此用户名不可用", 0).show();
      int i;
      do
      {
        return;
        Map localMap = (Map)paramObject;
        i = ((Integer)localMap.get("ret")).intValue();
        if (i != 0)
          continue;
        String str4 = (String)localMap.get("reason");
        Toast.makeText(InfoCompletedActivity.this, str4, 0).show();
        return;
      }
      while (i != 1);
      String str1 = InfoCompletedActivity.this.evPhoneNum.getText().toString();
      String str2 = InfoCompletedActivity.this.mCityText.getText().toString();
      String str3 = InfoCompletedActivity.this.evName.getText().toString();
      InfoCompletedActivity.this.updateUserInfoTask = new InfoCompletedActivity.UpdateUserInfoTask(InfoCompletedActivity.this, null);
      InfoCompletedActivity.this.showMyDialog(11);
      InfoCompletedActivity.UpdateUserInfoTask localUpdateUserInfoTask = InfoCompletedActivity.this.updateUserInfoTask;
      String[] arrayOfString = new String[8];
      arrayOfString[0] = str3;
      arrayOfString[1] = InfoCompletedActivity.access$24(InfoCompletedActivity.this);
      arrayOfString[2] = InfoCompletedActivity.access$25(InfoCompletedActivity.this);
      arrayOfString[3] = (1 + InfoCompletedActivity.access$26(InfoCompletedActivity.this));
      arrayOfString[4] = InfoCompletedActivity.access$27(InfoCompletedActivity.this);
      arrayOfString[5] = "";
      arrayOfString[6] = str1;
      arrayOfString[7] = str2;
      localUpdateUserInfoTask.execute(arrayOfString);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.activity.InfoCompletedActivity
 * JD-Core Version:    0.6.0
 */