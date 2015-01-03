package com.kaixin001.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.activity.PhoneRegisterActivity;
import com.kaixin001.engine.PhoneRegisterEngine;
import com.kaixin001.model.PhoneRegisterModel;
import com.kaixin001.model.Setting;
import com.kaixin001.util.UserHabitUtils;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneRegisterFirstFragment extends Fragment
  implements View.OnClickListener, TextWatcher
{
  private static final String TAG = "PhoneRegisterFirstFragment";
  private EditText etNumber;
  private ImageView leftBtn;
  private ImageView mDeleteBtn;
  private String mPhoneNumber;
  private Button nextStepBtn;
  private Button tvRightText;

  private static int dipToPx(Context paramContext, float paramFloat)
  {
    return (int)(0.5F + paramFloat * paramContext.getResources().getDisplayMetrics().density);
  }

  private View findViewById(int paramInt)
  {
    return getView().findViewById(paramInt);
  }

  private void initMainView()
  {
    this.etNumber = ((EditText)findViewById(2131363337));
    this.etNumber.addTextChangedListener(this);
    this.etNumber.setFocusableInTouchMode(true);
    this.etNumber.setFocusable(true);
    this.etNumber.requestFocus();
    new Timer().schedule(new TimerTask()
    {
      public void run()
      {
        if ((PhoneRegisterFirstFragment.this.getActivity() == null) || (PhoneRegisterFirstFragment.this.getView() == null))
          return;
        ((InputMethodManager)PhoneRegisterFirstFragment.this.getActivity().getSystemService("input_method")).showSoftInput(PhoneRegisterFirstFragment.this.etNumber, 0);
      }
    }
    , 200L);
    this.mDeleteBtn = ((ImageView)findViewById(2131363338));
    this.mDeleteBtn.setVisibility(8);
    this.mDeleteBtn.setOnClickListener(this);
    this.nextStepBtn = ((Button)findViewById(2131363339));
    this.nextStepBtn.setOnClickListener(this);
  }

  private boolean isPhoneNumber(String paramString)
  {
    return Pattern.compile("^1[3|4|5|8][0-9]\\d{8}$").matcher(paramString).find();
  }

  private void registerFromWeb()
  {
    String str = Setting.getInstance().getCType();
    if (str != null)
      str.substring(0, 5);
    while (true)
    {
      SimpleWebPageFragment localSimpleWebPageFragment = new SimpleWebPageFragment();
      Bundle localBundle = new Bundle();
      localBundle.putString("url", "http://3g.kaixin001.com/reg/reg_client.php?id=kx-%@");
      localBundle.putString("title", "账号注册");
      localSimpleWebPageFragment.setArguments(localBundle);
      ((PhoneRegisterActivity)getActivity()).startNextFragment(localSimpleWebPageFragment);
      return;
    }
  }

  private void setTitleBar()
  {
    ImageView localImageView = (ImageView)findViewById(2131362926);
    localImageView.setVisibility(0);
    localImageView.setImageResource(2130838295);
    findViewById(2131362928).setVisibility(8);
    this.leftBtn = ((ImageView)findViewById(2131362914));
    this.leftBtn.setOnClickListener(this);
    TextView localTextView = (TextView)findViewById(2131362920);
    localTextView.setVisibility(0);
    localTextView.setText(2131428592);
    this.tvRightText = ((Button)findViewById(2131362931));
    this.tvRightText.setVisibility(0);
    this.tvRightText.setPadding(dipToPx(getActivity(), 7.0F), 0, dipToPx(getActivity(), 12.0F), 0);
    this.tvRightText.setText(2131428593);
    Drawable localDrawable = getResources().getDrawable(2130838557);
    this.tvRightText.setCompoundDrawablesWithIntrinsicBounds(localDrawable, null, null, null);
    this.tvRightText.setOnClickListener(this);
  }

  private void startSecondFragment()
  {
    PhoneRegisterSecondFragment localPhoneRegisterSecondFragment = new PhoneRegisterSecondFragment();
    Bundle localBundle = new Bundle();
    localBundle.putString("phone_number", ((EditText)findViewById(2131363337)).getText().toString());
    localPhoneRegisterSecondFragment.setArguments(localBundle);
    ((PhoneRegisterActivity)getActivity()).startNextFragment(localPhoneRegisterSecondFragment);
  }

  public void afterTextChanged(Editable paramEditable)
  {
  }

  public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
  {
  }

  public void onClick(View paramView)
  {
    if (paramView == this.leftBtn)
      getActivity().finish();
    do
    {
      return;
      if (paramView == this.mDeleteBtn)
      {
        this.etNumber.setText("");
        return;
      }
      if (paramView != this.tvRightText)
        continue;
      registerFromWeb();
      UserHabitUtils.getInstance(getActivity()).addUserHabit("Wap_Register");
      UserHabitUtils.getInstance(getActivity()).addUserHabit("<JustLook_RegisterEmail>");
      return;
    }
    while (paramView != this.nextStepBtn);
    this.mPhoneNumber = this.etNumber.getText().toString();
    if (isPhoneNumber(this.mPhoneNumber))
    {
      if ((((PhoneRegisterActivity)getActivity()).isRecodeSendTime(this.mPhoneNumber)) && (!((PhoneRegisterActivity)getActivity()).after10Mins(this.mPhoneNumber)))
      {
        startSecondFragment();
        return;
      }
      PhoneNumberTask localPhoneNumberTask = new PhoneNumberTask(null);
      String[] arrayOfString = new String[1];
      arrayOfString[0] = this.mPhoneNumber;
      localPhoneNumberTask.execute(arrayOfString);
      return;
    }
    Toast.makeText(getActivity(), "请输入正确的手机号码", 0).show();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903272, paramViewGroup, false);
  }

  public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
  {
    if ((getActivity() == null) || (getView() == null))
      return;
    if (this.etNumber.getText().toString().length() > 0)
    {
      this.mDeleteBtn.setVisibility(0);
      this.nextStepBtn.setTextColor(-1);
      this.nextStepBtn.setEnabled(true);
      return;
    }
    this.mDeleteBtn.setVisibility(8);
    this.nextStepBtn.setTextColor(2147483647);
    this.nextStepBtn.setEnabled(false);
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    setTitleBar();
    initMainView();
  }

  private class PhoneNumberTask extends AsyncTask<String, Void, Integer>
  {
    private PhoneNumberTask()
    {
    }

    protected Integer doInBackground(String[] paramArrayOfString)
    {
      if ((PhoneRegisterFirstFragment.this.getActivity() == null) || (PhoneRegisterFirstFragment.this.getView() == null))
        return null;
      try
      {
        PhoneRegisterEngine.getInstance().getRegisterCodeResult(PhoneRegisterFirstFragment.this.getActivity(), PhoneRegisterFirstFragment.this.mPhoneNumber);
        Integer localInteger = Integer.valueOf(1);
        return localInteger;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
      return null;
    }

    protected void onPostExecute(Integer paramInteger)
    {
      if ((PhoneRegisterFirstFragment.this.getActivity() == null) || (PhoneRegisterFirstFragment.this.getView() == null) || (paramInteger == null))
        return;
      PhoneRegisterModel localPhoneRegisterModel = PhoneRegisterModel.getInstance();
      if ((localPhoneRegisterModel.getRet() != null) && (localPhoneRegisterModel.getRet().equals("1")))
      {
        ((PhoneRegisterActivity)PhoneRegisterFirstFragment.this.getActivity()).addSendTime(PhoneRegisterFirstFragment.this.mPhoneNumber);
        PhoneRegisterFirstFragment.this.startSecondFragment();
      }
      while (true)
      {
        localPhoneRegisterModel.clear();
        return;
        if ((localPhoneRegisterModel.getRet() == null) || (!localPhoneRegisterModel.getRet().equals("0")) || (localPhoneRegisterModel.getCode() == null) || (!localPhoneRegisterModel.getCode().equals("1")))
          continue;
        Toast.makeText(PhoneRegisterFirstFragment.this.getActivity(), "该号码已被注册或绑定，请更换手机号或采用账号注册", 1).show();
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.PhoneRegisterFirstFragment
 * JD-Core Version:    0.6.0
 */