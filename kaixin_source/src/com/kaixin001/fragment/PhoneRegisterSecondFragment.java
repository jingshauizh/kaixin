package com.kaixin001.fragment;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import java.util.Timer;
import java.util.TimerTask;

public class PhoneRegisterSecondFragment extends Fragment
  implements View.OnClickListener, TextWatcher
{
  private Button btnRegain;
  private ImageView deleteBtn;
  private EditText etVerifyCode;
  private ImageView leftBtn;
  private String mPhoneNumber;
  private Button nextStep;

  private View findViewById(int paramInt)
  {
    return getView().findViewById(paramInt);
  }

  private void finish()
  {
    if (getActivity().getSupportFragmentManager().getBackStackEntryCount() > 0)
      getActivity().getSupportFragmentManager().popBackStack();
  }

  private void initMainView()
  {
    Bundle localBundle = getArguments();
    if ((localBundle != null) && (localBundle.containsKey("phone_number")))
      this.mPhoneNumber = localBundle.getString("phone_number");
    ((TextView)findViewById(2131363344)).setText(getResources().getString(2131428597) + this.mPhoneNumber);
    this.deleteBtn = ((ImageView)findViewById(2131363338));
    this.deleteBtn.setOnClickListener(this);
    this.etVerifyCode = ((EditText)findViewById(2131363347));
    this.etVerifyCode.addTextChangedListener(this);
    this.etVerifyCode.setFocusableInTouchMode(true);
    this.etVerifyCode.setFocusable(true);
    this.etVerifyCode.requestFocus();
    new Timer().schedule(new TimerTask()
    {
      public void run()
      {
        if ((PhoneRegisterSecondFragment.this.getActivity() == null) || (PhoneRegisterSecondFragment.this.getView() == null))
          return;
        ((InputMethodManager)PhoneRegisterSecondFragment.this.getActivity().getSystemService("input_method")).showSoftInput(PhoneRegisterSecondFragment.this.etVerifyCode, 0);
      }
    }
    , 200L);
    this.btnRegain = ((Button)findViewById(2131363348));
    this.btnRegain.setText(getResources().getString(2131428591) + "(60)");
    this.btnRegain.setOnClickListener(this);
    this.nextStep = ((Button)findViewById(2131363349));
    this.nextStep.setOnClickListener(this);
  }

  private void setTitleBar()
  {
    findViewById(2131362928).setVisibility(8);
    this.leftBtn = ((ImageView)findViewById(2131362914));
    this.leftBtn.setOnClickListener(this);
    TextView localTextView = (TextView)findViewById(2131362920);
    localTextView.setVisibility(0);
    localTextView.setText(2131428594);
  }

  private void startThirdFragment()
  {
    PhoneRegisterThirdFragment localPhoneRegisterThirdFragment = new PhoneRegisterThirdFragment();
    Bundle localBundle = new Bundle();
    localBundle.putString("phone_number", this.mPhoneNumber);
    localBundle.putString("verify_code", this.etVerifyCode.getText().toString());
    localPhoneRegisterThirdFragment.setArguments(localBundle);
    ((PhoneRegisterActivity)getActivity()).startNextFragment(localPhoneRegisterThirdFragment);
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
      finish();
    do
    {
      return;
      if (paramView == this.deleteBtn)
      {
        this.etVerifyCode.setText("");
        return;
      }
      if (paramView != this.btnRegain)
        continue;
      this.btnRegain.setEnabled(false);
      this.btnRegain.setTextColor(2130839397);
      this.btnRegain.setText(getResources().getString(2131428591) + "(60)");
      new DownTimer(60000L, 1000L).start();
      PhoneNumberTask localPhoneNumberTask = new PhoneNumberTask(null);
      String[] arrayOfString = new String[1];
      arrayOfString[0] = this.mPhoneNumber;
      localPhoneNumberTask.execute(arrayOfString);
      return;
    }
    while (paramView != this.nextStep);
    String str = this.etVerifyCode.getText().toString();
    new CheckUpTask(null).execute(new String[] { str });
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903273, paramViewGroup, false);
  }

  public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
  {
    if (this.etVerifyCode.getText().toString().length() > 0)
    {
      this.deleteBtn.setVisibility(0);
      this.nextStep.setEnabled(true);
      return;
    }
    this.deleteBtn.setVisibility(8);
    this.nextStep.setEnabled(false);
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    setTitleBar();
    initMainView();
    new DownTimer(60000L, 1000L).start();
  }

  private class CheckUpTask extends AsyncTask<String, Void, Integer>
  {
    private CheckUpTask()
    {
    }

    protected Integer doInBackground(String[] paramArrayOfString)
    {
      if ((PhoneRegisterSecondFragment.this.getActivity() == null) || (PhoneRegisterSecondFragment.this.getView() == null))
        return null;
      ((PhoneRegisterActivity)PhoneRegisterSecondFragment.this.getActivity()).addSendTime(PhoneRegisterSecondFragment.this.mPhoneNumber);
      String str = PhoneRegisterSecondFragment.this.etVerifyCode.getText().toString();
      try
      {
        PhoneRegisterEngine.getInstance().getCodeVerifyResult(PhoneRegisterSecondFragment.this.getActivity(), PhoneRegisterSecondFragment.this.mPhoneNumber, str);
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
      if ((PhoneRegisterSecondFragment.this.getActivity() == null) || (PhoneRegisterSecondFragment.this.getView() == null) || (paramInteger == null));
      do
      {
        return;
        try
        {
          if (PhoneRegisterModel.getInstance().getRet().equals("1"))
          {
            PhoneRegisterSecondFragment.this.startThirdFragment();
            return;
          }
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
          Toast.makeText(PhoneRegisterSecondFragment.this.getActivity(), "验证失败，请重新输入验证码", 0).show();
          return;
        }
        if (!((PhoneRegisterActivity)PhoneRegisterSecondFragment.this.getActivity()).after10Mins(PhoneRegisterSecondFragment.this.mPhoneNumber))
          continue;
        Toast.makeText(PhoneRegisterSecondFragment.this.getActivity(), "验证码已经过期，请重新获取验证码", 0).show();
        return;
      }
      while (!PhoneRegisterModel.getInstance().getCode().equals("3"));
      Toast.makeText(PhoneRegisterSecondFragment.this.getActivity(), "验证码错误", 0).show();
    }
  }

  private class DownTimer extends CountDownTimer
  {
    private int restMin = 60;

    public DownTimer(long arg2, long arg4)
    {
      super(localObject);
    }

    public void onFinish()
    {
      if ((PhoneRegisterSecondFragment.this.getActivity() == null) || (PhoneRegisterSecondFragment.this.getView() == null))
        return;
      PhoneRegisterSecondFragment.this.btnRegain.setText(2131428591);
      PhoneRegisterSecondFragment.this.btnRegain.setEnabled(true);
    }

    public void onTick(long paramLong)
    {
      this.restMin = (-1 + this.restMin);
      if ((PhoneRegisterSecondFragment.this.getActivity() == null) || (PhoneRegisterSecondFragment.this.getView() == null))
        return;
      String str = PhoneRegisterSecondFragment.this.getResources().getString(2131428591) + "(" + this.restMin + ")";
      PhoneRegisterSecondFragment.this.btnRegain.setText(str);
    }
  }

  private class PhoneNumberTask extends AsyncTask<String, Void, Integer>
  {
    private PhoneNumberTask()
    {
    }

    protected Integer doInBackground(String[] paramArrayOfString)
    {
      if ((PhoneRegisterSecondFragment.this.getActivity() == null) || (PhoneRegisterSecondFragment.this.getView() == null))
        return null;
      try
      {
        PhoneRegisterEngine.getInstance().getRegisterCodeResult(PhoneRegisterSecondFragment.this.getActivity(), PhoneRegisterSecondFragment.this.mPhoneNumber);
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
      if ((PhoneRegisterSecondFragment.this.getActivity() == null) || (PhoneRegisterSecondFragment.this.getView() == null) || (paramInteger == null))
        return;
      while (true)
      {
        PhoneRegisterModel localPhoneRegisterModel;
        try
        {
          localPhoneRegisterModel = PhoneRegisterModel.getInstance();
          if (localPhoneRegisterModel.getRet().equals("1"))
          {
            Log.i("jiangfeng", " 发送验证码  ");
            localPhoneRegisterModel.clear();
            return;
          }
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
          Toast.makeText(PhoneRegisterSecondFragment.this.getActivity(), "重新发送失败", 0).show();
          return;
        }
        if ((!localPhoneRegisterModel.getRet().equals("0")) || (!localPhoneRegisterModel.getCode().equals("1")))
          continue;
        Toast.makeText(PhoneRegisterSecondFragment.this.getActivity(), "该号码已被注册或绑定，请更换手机号或采用账号注册", 0).show();
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.PhoneRegisterSecondFragment
 * JD-Core Version:    0.6.0
 */