package com.kaixin001.fragment;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.activity.InfoCompletedActivity.MyDatePickerDialog;
import com.kaixin001.engine.InfoCompletedEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.engine.UserInfoEngine;
import com.kaixin001.model.CityModel;
import com.kaixin001.model.User;
import com.kaixin001.model.UserInfoModel;
import com.kaixin001.model.UserLevel;
import com.kaixin001.util.ImageCache;
import com.kaixin001.util.ImageDownloader.RoundCornerType;
import com.kaixin001.util.ImageUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.ParseNewsInfoUtil;
import com.kaixin001.view.KXIntroView;
import com.kaixin001.view.WheelView;
import com.kaixin001.view.WheelView.ArrayWheelAdapter;
import com.kaixin001.view.WheelView.OnWheelChangedListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import org.json.JSONObject;

public class UserInfoFragment extends BaseFragment
{
  private TextView bottomLine;
  private DatePickerDialog datePickerDialog;
  private String filePathName;
  private ImageView imInfo;
  private ImageView ivLogo;
  private ImageView ivLogoEdit;
  private ImageView ivOnline;
  private ImageView ivStar;
  private TextView levelExp;
  private TextView levelId;
  private ImageView levelImage;
  private TextView levelTitle;
  String[] mCities;
  private GetDataTask mDataTask;
  private int mDay;
  private int mDefaultCityIndex = 0;
  private int mDefaultProvinceIndex = 0;
  private BaseFragment mFatherFragment;
  private String mFname = "";
  private String mFuid = "";
  private View mHeaderView;
  private LinearLayout mInfoLayout;
  private int mMonth;
  private Dialog mPlaceDialog;
  private ProgressDialog mProgressDialog;
  String[] mProvinces;
  private UpdateUserInfoTask mUpdateUserInfoTask;
  private String mViewFname = "";
  private int mYear;
  private String m_flogo = "";
  private String m_online = "";
  private String m_star = "";
  private String m_state = "";
  private String m_stateTime = "";
  private TextView tvDivider;
  private TextView tvName;
  private KXIntroView tvState;
  private TextView tvTime;
  private View waitLayout;

  private void doSubmit()
  {
    cancelTask(this.mUpdateUserInfoTask);
    this.mUpdateUserInfoTask = new UpdateUserInfoTask(null);
    String str1 = UserInfoModel.getInstance().getGender();
    String str2 = UserInfoModel.getInstance().getHometown();
    String str3 = UserInfoModel.getInstance().getPhoneNum();
    String str4 = UserInfoModel.getInstance().getCity();
    showProgressDialog(true);
    UpdateUserInfoTask localUpdateUserInfoTask = this.mUpdateUserInfoTask;
    String[] arrayOfString = new String[7];
    arrayOfString[0] = this.mYear;
    arrayOfString[1] = (1 + this.mMonth);
    arrayOfString[2] = this.mDay;
    arrayOfString[3] = str2;
    arrayOfString[4] = str3;
    arrayOfString[5] = str4;
    arrayOfString[6] = str1;
    localUpdateUserInfoTask.execute(arrayOfString);
  }

  private void initData()
  {
    this.mDataTask = new GetDataTask(null);
    this.mDataTask.execute(new Void[0]);
  }

  private void initDefaultIndex(TextView paramTextView)
  {
    String str = paramTextView.getText().toString();
    this.mDefaultProvinceIndex = 0;
    this.mDefaultCityIndex = 0;
    int i;
    int k;
    label45: String[] arrayOfString2;
    if (!TextUtils.isEmpty(str))
    {
      i = 0;
      int j = this.mProvinces.length;
      k = 0;
      if (i < j)
        break label78;
      if (k == 0)
        break label135;
      arrayOfString2 = CityModel.getInstance().getCities(getActivity(), this.mDefaultProvinceIndex);
    }
    for (int i1 = 0; ; i1++)
    {
      if (i1 >= arrayOfString2.length)
      {
        return;
        label78: if (str.indexOf(this.mProvinces[i]) != -1)
        {
          k = 1;
          this.mDefaultProvinceIndex = i;
          break label45;
        }
        i++;
        break;
      }
      if (str.indexOf(arrayOfString2[i1]) == -1)
        continue;
      this.mDefaultCityIndex = i1;
      return;
    }
    label135: int m = 0;
    label138: String[] arrayOfString1;
    if (m < this.mProvinces.length)
      arrayOfString1 = CityModel.getInstance().getCities(getActivity(), m);
    for (int n = 0; ; n++)
    {
      if (n >= arrayOfString1.length)
      {
        m++;
        break label138;
        break;
      }
      if (str.indexOf(arrayOfString1[n]) == -1)
        continue;
      this.mDefaultProvinceIndex = m;
      this.mDefaultCityIndex = n;
      return;
    }
  }

  private void initHeader()
  {
    this.mHeaderView = getActivity().getLayoutInflater().inflate(2130903149, null);
    this.mHeaderView.findViewById(2131362531).setVisibility(8);
    this.mHeaderView.findViewById(2131362535).setVisibility(8);
    this.levelId = ((TextView)this.mHeaderView.findViewById(2131362536));
    this.levelTitle = ((TextView)this.mHeaderView.findViewById(2131362534));
    this.levelExp = ((TextView)this.mHeaderView.findViewById(2131362537));
    this.levelImage = ((ImageView)this.mHeaderView.findViewById(2131362533));
    this.ivStar = ((ImageView)this.mHeaderView.findViewById(2131362523));
    if ((!TextUtils.isEmpty(this.m_star)) && ((this.m_star.trim().compareTo("1") == 0) || (this.m_star.trim().compareTo("2") == 0)))
    {
      this.ivStar.setVisibility(0);
      this.ivStar.setImageResource(2130838822);
    }
    this.ivLogo = ((ImageView)this.mHeaderView.findViewById(2131362521));
    this.ivLogoEdit = ((ImageView)this.mHeaderView.findViewById(2131362522));
    if (User.getInstance().getUID().compareTo(this.mFuid) == 0)
    {
      this.ivLogoEdit.setVisibility(0);
      this.tvName = ((TextView)this.mHeaderView.findViewById(2131362532));
      this.ivOnline = ((ImageView)this.mHeaderView.findViewById(2131362524));
      this.tvState = ((KXIntroView)this.mHeaderView.findViewById(2131362529));
      this.tvTime = ((TextView)this.mHeaderView.findViewById(2131362530));
      this.tvDivider = ((TextView)this.mHeaderView.findViewById(2131362538));
      this.imInfo = ((ImageView)this.mHeaderView.findViewById(2131362526));
      this.imInfo.setVisibility(8);
      this.ivLogo.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          UserInfoFragment.this.showUploadPhotoOptions();
        }
      });
      this.ivLogoEdit.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          UserInfoFragment.this.showUploadPhotoOptions();
        }
      });
      this.tvName.setText(this.mFname);
      displayRoundPicture(this.ivLogo, this.m_flogo, ImageDownloader.RoundCornerType.hdpi_big, 2130837561);
      if (!TextUtils.isEmpty(this.m_state))
      {
        ArrayList localArrayList = ParseNewsInfoUtil.parseNewsLinkString(this.m_state);
        this.tvState.setTitleList(localArrayList);
      }
      if (this.m_online.compareTo("1") != 0)
        break label520;
      this.ivOnline.setImageResource(2130838704);
    }
    while (true)
    {
      this.ivOnline.setVisibility(8);
      this.tvTime.setText(this.m_stateTime);
      this.tvTime.setVisibility(8);
      return;
      this.ivLogoEdit.setVisibility(8);
      this.ivLogo.setEnabled(false);
      break;
      label520: if (this.m_online.compareTo("2") == 0)
      {
        this.ivOnline.setImageResource(2130838621);
        continue;
      }
      this.ivOnline.setImageBitmap(null);
    }
  }

  private void initViews()
  {
    this.waitLayout = findViewById(2131363881);
    this.waitLayout.setVisibility(0);
    this.bottomLine = ((TextView)findViewById(2131363880));
    this.bottomLine.setVisibility(8);
    this.mInfoLayout = ((LinearLayout)findViewById(2131363879));
    this.mInfoLayout.addView(this.mHeaderView);
  }

  private void showMyDialog(int paramInt, TextView paramTextView)
  {
    dismissDialog();
    if (paramInt == 0)
    {
      this.dialog = ProgressDialog.show(getActivity(), "", getResources().getText(2131427434), true, true, new DialogInterface.OnCancelListener()
      {
        public void onCancel(DialogInterface paramDialogInterface)
        {
          if (UserInfoFragment.this.mUpdateUserInfoTask != null)
            UserInfoFragment.this.mUpdateUserInfoTask.cancel(true);
        }
      });
      this.dialog.show();
      return;
    }
    if (this.mYear == 0)
    {
      Calendar localCalendar = Calendar.getInstance();
      this.mYear = (-20 + localCalendar.get(1));
      this.mMonth = localCalendar.get(2);
      this.mDay = localCalendar.get(5);
    }
    this.datePickerDialog = new InfoCompletedActivity.MyDatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener(paramTextView)
    {
      public void onDateSet(DatePicker paramDatePicker, int paramInt1, int paramInt2, int paramInt3)
      {
        UserInfoFragment.this.mYear = paramInt1;
        UserInfoFragment.this.mMonth = paramInt2;
        UserInfoFragment.this.mDay = paramInt3;
        StringBuffer localStringBuffer = new StringBuffer();
        localStringBuffer.append(UserInfoFragment.this.mYear).append("年").append(1 + UserInfoFragment.this.mMonth).append("月").append(UserInfoFragment.this.mDay).append("日");
        this.val$textView.setText(localStringBuffer.toString());
        UserInfoModel.getInstance().setBirthday(localStringBuffer.toString());
        UserInfoFragment.this.datePickerDialog.onDateChanged(paramDatePicker, paramInt1, paramInt2, paramInt3);
      }
    }
    , this.mYear, this.mMonth, this.mDay);
    this.datePickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener()
    {
      public void onDismiss(DialogInterface paramDialogInterface)
      {
        UserInfoFragment.this.doSubmit();
      }
    });
    this.dialog = this.datePickerDialog;
    this.dialog.show();
  }

  private void showPlacePicker(TextView paramTextView)
  {
    this.mProvinces = CityModel.getInstance().getProvinces(getActivity());
    initDefaultIndex(paramTextView);
    this.mCities = CityModel.getInstance().getCities(getActivity(), this.mDefaultProvinceIndex);
    this.mPlaceDialog = new Dialog(getActivity());
    this.mPlaceDialog.setTitle(getResources().getText(2131427960));
    View localView = ((LayoutInflater)getActivity().getSystemService("layout_inflater")).inflate(2130903398, null);
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
        UserInfoFragment.this.mCities = CityModel.getInstance().getCities(UserInfoFragment.this.getActivity(), paramInt2);
        WheelView localWheelView1 = this.val$wv2;
        WheelView localWheelView2 = this.val$wv2;
        localWheelView2.getClass();
        localWheelView1.setAdapter(new WheelView.ArrayWheelAdapter(localWheelView2, UserInfoFragment.this.mCities));
        this.val$wv2.setCurrentItem(0);
      }
    });
    int i = (int)(16.0F * getResources().getDisplayMetrics().density);
    localWheelView2.TEXT_SIZE = i;
    localWheelView1.TEXT_SIZE = i;
    Button localButton1 = (Button)localView.findViewById(2131363923);
    Button localButton2 = (Button)localView.findViewById(2131363924);
    localButton1.setOnClickListener(new View.OnClickListener(localWheelView1, localWheelView2, paramTextView)
    {
      public void onClick(View paramView)
      {
        String str = UserInfoFragment.this.mProvinces[this.val$wv1.getCurrentItem()] + UserInfoFragment.this.mCities[this.val$wv2.getCurrentItem()];
        this.val$textView.setText(str);
        UserInfoModel.getInstance().setCity(str);
        UserInfoFragment.this.mPlaceDialog.dismiss();
        UserInfoFragment.this.doSubmit();
      }
    });
    localButton2.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        UserInfoFragment.this.mPlaceDialog.dismiss();
      }
    });
    this.mPlaceDialog.setContentView(localView);
    this.mPlaceDialog.show();
  }

  private void showProgressDialog(boolean paramBoolean)
  {
    if (getActivity() != null)
    {
      if (this.mProgressDialog != null)
      {
        this.mProgressDialog.dismiss();
        this.mProgressDialog = null;
      }
      if (paramBoolean)
        this.mProgressDialog = ProgressDialog.show(getActivity(), null, getString(2131428506));
    }
  }

  private void showUploadPhotoOptions()
  {
    selectPictureFromGallery();
  }

  private void updateInfoLayout(ArrayList<JSONObject> paramArrayList)
  {
    if (paramArrayList == null)
      return;
    this.mInfoLayout.removeAllViews();
    this.mInfoLayout.addView(this.mHeaderView);
    this.mHeaderView.findViewById(2131362531).setVisibility(0);
    this.mHeaderView.findViewById(2131362535).setVisibility(0);
    UserLevel localUserLevel = UserInfoModel.getInstance().getmUserLevel();
    if (localUserLevel != null)
    {
      this.levelExp.setText("经验值：" + localUserLevel.getExp());
      this.levelId.setText("LV." + localUserLevel.getLevel());
      this.levelTitle.setText(localUserLevel.getTitle());
      displayPicture(this.levelImage, localUserLevel.getImage(), 0);
    }
    int i = 0;
    label142: int j;
    int k;
    View localView1;
    TextView localTextView3;
    View localView2;
    View localView3;
    label371: View localView4;
    if (i < paramArrayList.size())
    {
      JSONObject localJSONObject = (JSONObject)paramArrayList.get(i);
      String str1 = localJSONObject.optString("tag", "");
      j = localJSONObject.optInt("pos", 2);
      k = localJSONObject.optInt("edit", 0);
      String str2 = localJSONObject.optString("value", "");
      if ((!TextUtils.isEmpty(str1)) && (str1.equals("性别")))
        k = 4;
      localView1 = ((LayoutInflater)getActivity().getSystemService("layout_inflater")).inflate(2130903151, null);
      TextView localTextView1 = (TextView)localView1.findViewById(2131362580);
      TextView localTextView2 = (TextView)localView1.findViewById(2131362581);
      localTextView3 = (TextView)localView1.findViewById(2131362582);
      EditText localEditText = (EditText)localView1.findViewById(2131362583);
      localTextView1.setVisibility(8);
      localEditText.setVisibility(8);
      localTextView2.setText(str1);
      localTextView3.setText(str2);
      localView2 = localView1.findViewById(2131362585);
      localView3 = localView1.findViewById(2131362579);
      if (j != 1)
        break label473;
      localView3.setBackgroundResource(2130838717);
      localView2.setVisibility(8);
      localView4 = localView1.findViewById(2131362584);
      localView4.setVisibility(8);
      String str3 = User.getInstance().getUID();
      if ((!TextUtils.isEmpty(str3)) && (str3.equals(this.mFuid)))
      {
        if (k != 1)
          break label514;
        localView4.setVisibility(0);
        localTextView3.setText(UserInfoModel.getInstance().getBirthday());
        3 local3 = new View.OnClickListener(localTextView3)
        {
          public void onClick(View paramView)
          {
            UserInfoFragment.this.showMyDialog(1, this.val$tv);
          }
        };
        localView1.setOnClickListener(local3);
      }
    }
    while (true)
    {
      this.mInfoLayout.addView(localView1);
      i++;
      break label142;
      break;
      label473: if (j == 2)
      {
        localView3.setBackgroundResource(2130838716);
        localView2.setVisibility(8);
        break label371;
      }
      localView3.setBackgroundResource(2130838715);
      localView2.setVisibility(0);
      break label371;
      label514: if (k == 2)
      {
        localView4.setVisibility(0);
        localTextView3.setText(UserInfoModel.getInstance().getCity());
        4 local4 = new View.OnClickListener(localTextView3)
        {
          public void onClick(View paramView)
          {
            UserInfoFragment.this.showPlacePicker(this.val$tv);
          }
        };
        localView1.setOnClickListener(local4);
        continue;
      }
      if (k == 3)
      {
        localView4.setVisibility(0);
        localTextView3.setText(UserInfoModel.getInstance().getPhoneNum());
        5 local5 = new View.OnClickListener(localTextView3)
        {
          public void onClick(View paramView)
          {
            EditText localEditText = new EditText(UserInfoFragment.this.getActivity());
            localEditText.setWidth(UserInfoFragment.this.dipToPx(200.0F));
            localEditText.setHeight(UserInfoFragment.this.dipToPx(50.0F));
            new AlertDialog.Builder(UserInfoFragment.this.getActivity()).setTitle(2131428668).setView(localEditText).setPositiveButton(2131427381, new DialogInterface.OnClickListener(localEditText, this.val$tvValue)
            {
              public void onClick(DialogInterface paramDialogInterface, int paramInt)
              {
                String str = this.val$edPhone.getText().toString().trim();
                UserInfoModel.getInstance().setPhoneNum(str);
                this.val$tvValue.setText(str);
                UserInfoFragment.this.doSubmit();
              }
            }).setNegativeButton(2131427587, new DialogInterface.OnClickListener()
            {
              public void onClick(DialogInterface paramDialogInterface, int paramInt)
              {
              }
            }).create().show();
          }
        };
        localView1.setOnClickListener(local5);
        continue;
      }
      if (k != 4)
        continue;
      localView4.setVisibility(0);
      6 local6 = new View.OnClickListener(localTextView3)
      {
        public void onClick(View paramView)
        {
          new AlertDialog.Builder(UserInfoFragment.this.getActivity()).setTitle("请选择性别").setSingleChoiceItems(new String[] { "男", "女" }, -1, new DialogInterface.OnClickListener(this.val$tvValue)
          {
            public void onClick(DialogInterface paramDialogInterface, int paramInt)
            {
              switch (paramInt)
              {
              default:
                return;
              case 0:
                UserInfoModel.getInstance().setGender("0");
                this.val$tvValue.setText("男");
                return;
              case 1:
              }
              UserInfoModel.getInstance().setGender("1");
              this.val$tvValue.setText("女");
            }
          }).setPositiveButton(2131427381, new DialogInterface.OnClickListener()
          {
            public void onClick(DialogInterface paramDialogInterface, int paramInt)
            {
              UserInfoFragment.this.doSubmit();
            }
          }).setNegativeButton(2131427587, new DialogInterface.OnClickListener()
          {
            public void onClick(DialogInterface paramDialogInterface, int paramInt)
            {
            }
          }).create().show();
        }
      };
      localView1.setOnClickListener(local6);
    }
  }

  protected void dealPhotoResult(String paramString1, String paramString2)
  {
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
        if (paramIntent != null);
      while (true)
      {
        return;
        Bundle localBundle2 = paramIntent.getExtras();
        if (localBundle2 == null)
          continue;
        this.filePathName = (Environment.getExternalStorageDirectory() + "/kaixin001" + "/" + "kx_header_tmp.jpg");
        Bitmap localBitmap2 = (Bitmap)localBundle2.getParcelable("data");
        try
        {
          ImageCache.saveBitmapToFileWithAbsolutePath(getActivity(), localBitmap2, null, this.filePathName);
          if (localBitmap2 != null)
          {
            this.ivLogo.setImageBitmap(localBitmap2);
            doSubmit();
            return;
          }
        }
        catch (Exception localException2)
        {
          while (true)
            localException2.printStackTrace();
          Toast.makeText(getActivity(), 2131428303, 0).show();
          return;
        }
        if (paramInt1 != 112)
          break;
        if (paramIntent == null)
          continue;
        Bundle localBundle1 = paramIntent.getExtras();
        if (localBundle1 == null)
          continue;
        this.filePathName = (Environment.getExternalStorageDirectory() + "/kaixin001" + "/" + "kx_header_tmp.jpg");
        Bitmap localBitmap1 = (Bitmap)localBundle1.getParcelable("data");
        try
        {
          localBitmap1 = ImageUtil.getRoundedCornerBitmap(localBitmap1, ImageDownloader.RoundCornerType.hdpi_big);
          ImageCache.saveBitmapToFileWithAbsolutePath(getActivity(), localBitmap1, null, this.filePathName);
          ImageCache.getInstance().addBitmapToHardCache(this.m_flogo, localBitmap1);
          if (localBitmap1 != null)
          {
            this.ivLogo.setImageBitmap(localBitmap1);
            return;
          }
        }
        catch (Exception localException1)
        {
          while (true)
            localException1.printStackTrace();
          Toast.makeText(getActivity(), 2131428303, 0).show();
          return;
        }
      }
      super.onActivityResult(paramInt1, paramInt2, paramIntent);
      return;
    }
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903393, paramViewGroup, false);
  }

  public void onDestroy()
  {
    if ((this.mDataTask != null) && (this.mDataTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.mDataTask.cancel(true);
      this.mDataTask = null;
      UserInfoEngine.getInstance().cancel();
    }
    cancelTask(this.mUpdateUserInfoTask);
    UserInfoModel.getInstance().clear();
    super.onDestroy();
  }

  public void onPause()
  {
    super.onPause();
  }

  public void onResume()
  {
    super.onResume();
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    try
    {
      Bundle localBundle = getArguments();
      if (localBundle != null)
      {
        String str1 = localBundle.getString("fuid");
        if (str1 != null)
          this.mFuid = str1;
        String str2 = localBundle.getString("fname");
        if (str2 != null)
          this.mFname = str2;
        String str3 = localBundle.getString("flogo");
        if (str3 != null)
          this.m_flogo = str3;
        String str4 = localBundle.getString("state");
        if (str4 != null)
          this.m_state = str4;
        String str5 = localBundle.getString("stateTime");
        if (str5 != null)
          this.m_stateTime = str5;
        String str6 = localBundle.getString("online");
        if (str6 != null)
          this.m_online = str6;
        String str7 = localBundle.getString("star");
        if (str7 != null)
          this.m_star = str7;
      }
      if ((this.mFname != null) && (this.mFname.length() > 3));
      for (this.mViewFname = (this.mFname.substring(0, 3) + "..."); ; this.mViewFname = this.mFname)
      {
        initHeader();
        initViews();
        initData();
        return;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void setFatherFragment(BaseFragment paramBaseFragment)
  {
    this.mFatherFragment = paramBaseFragment;
  }

  private class GetDataTask extends KXFragment.KXAsyncTask<Void, Void, Integer>
  {
    private GetDataTask()
    {
      super();
    }

    protected Integer doInBackgroundA(Void[] paramArrayOfVoid)
    {
      try
      {
        if (UserInfoEngine.getInstance().getUserInfo(UserInfoFragment.this.getActivity().getApplicationContext(), UserInfoFragment.this.mFuid))
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

    protected void onPostExecuteA(Integer paramInteger)
    {
      if (paramInteger.intValue() == 0)
        try
        {
          UserInfoFragment.this.waitLayout.setVisibility(8);
          UserInfoFragment.this.tvDivider.setVisibility(8);
          UserInfoFragment.this.updateInfoLayout(UserInfoModel.getInstance().getUserInfoList());
          String str = UserInfoModel.getInstance().getBirthday();
          try
          {
            if (str.contains("年"))
            {
              UserInfoFragment.this.mYear = Integer.valueOf(str.substring(0, 4)).intValue();
              UserInfoFragment.this.mMonth = (-1 + Integer.valueOf(str.substring(5, 7)).intValue());
              UserInfoFragment.this.mDay = Integer.valueOf(str.substring(8, 10)).intValue();
              return;
            }
            UserInfoFragment.this.mMonth = (-1 + Integer.valueOf(str.substring(0, 2)).intValue());
            UserInfoFragment.this.mDay = Integer.valueOf(str.substring(3, 5)).intValue();
            return;
          }
          catch (Exception localException2)
          {
            KXLog.e("InfoCompletedActivity", "initial time failed", localException2);
            return;
          }
        }
        catch (Exception localException1)
        {
          KXLog.e("UserInfoActivity", "onPostExecute", localException1);
          return;
        }
      Toast.makeText(UserInfoFragment.this.getActivity(), 2131427771, 0).show();
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private class UpdateUserInfoTask extends KXFragment.KXAsyncTask<String, Void, Integer>
  {
    private UpdateUserInfoTask()
    {
      super();
    }

    protected Integer doInBackgroundA(String[] paramArrayOfString)
    {
      if ((paramArrayOfString == null) || (paramArrayOfString.length == 0) || (paramArrayOfString.length != 7))
        return null;
      String str1 = paramArrayOfString[0];
      String str2 = paramArrayOfString[1];
      String str3 = paramArrayOfString[2];
      String str4 = paramArrayOfString[3];
      String str5 = paramArrayOfString[4];
      String str6 = paramArrayOfString[5];
      String str7 = paramArrayOfString[6];
      try
      {
        Integer localInteger = Integer.valueOf(InfoCompletedEngine.getInstance().getInfoUpdatedRequest(UserInfoFragment.this.getActivity(), str5, str1, str2, str3, str4, str6, UserInfoFragment.this.filePathName, UserInfoFragment.this.mFuid, str7));
        return localInteger;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
      }
      return Integer.valueOf(-1);
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      UserInfoFragment.this.showProgressDialog(false);
      UserInfoFragment.this.dismissDialog();
      if ((paramInteger == null) || (paramInteger.intValue() == -1))
      {
        Toast.makeText(UserInfoFragment.this.getActivity(), 2131428304, 0).show();
        return;
      }
      if (paramInteger.intValue() == 1)
      {
        Toast.makeText(UserInfoFragment.this.getActivity().getApplicationContext(), 2131427955, 1).show();
        Intent localIntent = new Intent();
        boolean bool1 = TextUtils.isEmpty(UserInfoFragment.this.filePathName);
        boolean bool2 = false;
        if (bool1);
        while (true)
        {
          localIntent.putExtra("changelogo", bool2);
          localIntent.putExtra("city", UserInfoModel.getInstance().getCity());
          if (UserInfoFragment.this.mFatherFragment == null)
            break;
          UserInfoFragment.this.mFatherFragment.setResult(-1, localIntent);
          return;
          bool2 = true;
        }
      }
      String str = InfoCompletedEngine.getInstance().msg;
      if ((str != null) && (str.equals("生日日期不正确")))
      {
        Toast.makeText(UserInfoFragment.this.getActivity(), 2131428305, 0).show();
        return;
      }
      Toast.makeText(UserInfoFragment.this.getActivity(), 2131428304, 0).show();
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
 * Qualified Name:     com.kaixin001.fragment.UserInfoFragment
 * JD-Core Version:    0.6.0
 */