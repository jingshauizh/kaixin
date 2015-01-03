package com.kaixin001.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextUtils;
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
import com.kaixin001.activity.KXEnvironment;
import com.kaixin001.engine.CloudAlbumEngine;
import com.kaixin001.engine.LoginEngine;
import com.kaixin001.engine.PhoneRegisterEngine;
import com.kaixin001.model.PhoneRegisterModel;
import com.kaixin001.model.User;
import com.kaixin001.util.CloudAlbumManager;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.UserHabitUtils;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneRegisterThirdFragment extends Fragment
  implements View.OnClickListener
{
  private EditText etPassword;
  private ImageView leftBtn;
  private String mCode;
  private String mPassword;
  private String mPhoneNumber;
  private Button mRegistedBtn;
  private ImageView pwdDelete;
  private ImageView repwdDelete;
  private EditText retPassword;

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
    this.pwdDelete = ((ImageView)findViewById(2131363354));
    this.pwdDelete.setOnClickListener(this);
    this.repwdDelete = ((ImageView)findViewById(2131363357));
    this.repwdDelete.setOnClickListener(this);
    this.etPassword = ((EditText)findViewById(2131363353));
    this.etPassword.addTextChangedListener(new TextWatcher()
    {
      public void afterTextChanged(Editable paramEditable)
      {
      }

      public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
      {
      }

      public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
      {
        if (PhoneRegisterThirdFragment.this.etPassword.getText().length() > 0)
        {
          PhoneRegisterThirdFragment.this.pwdDelete.setVisibility(0);
          return;
        }
        PhoneRegisterThirdFragment.this.pwdDelete.setVisibility(8);
      }
    });
    this.etPassword.setFocusableInTouchMode(true);
    this.etPassword.setFocusable(true);
    this.etPassword.requestFocus();
    new Timer().schedule(new TimerTask()
    {
      public void run()
      {
        ((InputMethodManager)PhoneRegisterThirdFragment.this.getActivity().getSystemService("input_method")).showSoftInput(PhoneRegisterThirdFragment.this.etPassword, 0);
      }
    }
    , 200L);
    this.retPassword = ((EditText)findViewById(2131363356));
    this.retPassword.addTextChangedListener(new TextWatcher()
    {
      public void afterTextChanged(Editable paramEditable)
      {
      }

      public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
      {
      }

      public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
      {
        if (PhoneRegisterThirdFragment.this.retPassword.getText().toString().length() > 0)
        {
          PhoneRegisterThirdFragment.this.mRegistedBtn.setEnabled(true);
          PhoneRegisterThirdFragment.this.repwdDelete.setVisibility(0);
          return;
        }
        PhoneRegisterThirdFragment.this.mRegistedBtn.setEnabled(false);
        PhoneRegisterThirdFragment.this.repwdDelete.setVisibility(8);
      }
    });
    this.mRegistedBtn = ((Button)findViewById(2131363358));
    this.mRegistedBtn.setEnabled(false);
    this.mRegistedBtn.setOnClickListener(this);
  }

  private boolean isValidPassword(String paramString)
  {
    return Pattern.compile("^[a-z0-9_-]{6,20}$").matcher(paramString).find();
  }

  private void setTitleBar()
  {
    findViewById(2131362928).setVisibility(8);
    this.leftBtn = ((ImageView)findViewById(2131362914));
    this.leftBtn.setOnClickListener(this);
    TextView localTextView = (TextView)findViewById(2131362920);
    localTextView.setVisibility(0);
    localTextView.setText(2131428595);
  }

  public void onClick(View paramView)
  {
    if ((getActivity() == null) || (getView() == null));
    do
    {
      return;
      if (paramView == this.leftBtn)
      {
        finish();
        return;
      }
      if (paramView == this.pwdDelete)
      {
        this.etPassword.setText("");
        return;
      }
      if (paramView != this.repwdDelete)
        continue;
      this.retPassword.setText("");
      return;
    }
    while (paramView != this.mRegistedBtn);
    String str1 = this.etPassword.getText().toString();
    String str2 = this.retPassword.getText().toString();
    if (str1.length() < 6)
    {
      Toast.makeText(getActivity(), "密码不足6位", 0).show();
      return;
    }
    if (!isValidPassword(str1))
    {
      Toast.makeText(getActivity(), "密码只能为6-20位字母或数字", 0).show();
      return;
    }
    if (!str1.equals(str2))
    {
      Toast.makeText(getActivity(), "两次输入的密码不一致", 0).show();
      return;
    }
    this.mPassword = this.etPassword.getText().toString();
    String[] arrayOfString = new String[3];
    arrayOfString[0] = this.mPhoneNumber;
    arrayOfString[1] = this.mPassword;
    arrayOfString[2] = this.mCode;
    new RegisterTask(null).execute(arrayOfString);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Bundle localBundle = getArguments();
    if ((localBundle != null) && (localBundle.containsKey("phone_number")))
    {
      this.mPhoneNumber = localBundle.getString("phone_number");
      this.mCode = localBundle.getString("verify_code");
    }
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903274, paramViewGroup, false);
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    setTitleBar();
    initMainView();
  }

  private class LoginTask extends AsyncTask<String, Void, Integer>
  {
    private LoginTask()
    {
    }

    protected Integer doInBackground(String[] paramArrayOfString)
    {
      if ((PhoneRegisterThirdFragment.this.getActivity() == null) || (PhoneRegisterThirdFragment.this.getView() == null) || (paramArrayOfString == null))
        return null;
      while (true)
      {
        try
        {
          int i = LoginEngine.getInstance().doLogin(PhoneRegisterThirdFragment.this.getActivity(), PhoneRegisterThirdFragment.this.getActivity().getApplicationContext(), paramArrayOfString[0], paramArrayOfString[1], paramArrayOfString[2], paramArrayOfString[3], paramArrayOfString[4], paramArrayOfString[5], paramArrayOfString[6]);
          if (TextUtils.isEmpty(User.getInstance().getUID()))
            continue;
          j = CloudAlbumEngine.getInstance().checkWhiteUser(PhoneRegisterThirdFragment.this.getActivity().getApplicationContext());
          if ((!"99688312".equals(User.getInstance().getUID())) && (!"101940966".equals(User.getInstance().getUID())) && (!"150784446".equals(User.getInstance().getUID())) && (!"150784460".equals(User.getInstance().getUID())))
          {
            CloudAlbumManager.getInstance().setWhiteUser(PhoneRegisterThirdFragment.this.getActivity().getApplicationContext(), User.getInstance().getUID(), j);
            Integer localInteger = Integer.valueOf(i);
            return localInteger;
          }
        }
        catch (Exception localException)
        {
          return null;
        }
        int j = 1;
      }
    }

    protected void onPostExecute(Integer paramInteger)
    {
      if ((PhoneRegisterThirdFragment.this.getActivity() == null) || (PhoneRegisterThirdFragment.this.getView() == null));
      do
      {
        return;
        if (paramInteger != null)
          continue;
        PhoneRegisterThirdFragment.this.finish();
        return;
      }
      while (paramInteger.intValue() != 0);
      try
      {
        UserHabitUtils.getInstance(PhoneRegisterThirdFragment.this.getActivity()).addUserHabit("Photo_Register_Complete");
        KXEnvironment.saveBooleanParams(PhoneRegisterThirdFragment.this.getActivity(), "needCompleteInfo", true, true);
        IntentUtil.returnToDesktop(PhoneRegisterThirdFragment.this.getActivity());
        return;
      }
      catch (Exception localException)
      {
        KXLog.e("PhoneRegisterLogin", "onPostExecute", localException);
      }
    }
  }

  private class RegisterTask extends AsyncTask<String, Void, Integer>
  {
    private RegisterTask()
    {
    }

    protected Integer doInBackground(String[] paramArrayOfString)
    {
      if ((PhoneRegisterThirdFragment.this.getActivity() == null) || (PhoneRegisterThirdFragment.this.getView() == null))
        return null;
      try
      {
        PhoneRegisterEngine.getInstance().commitRegister(PhoneRegisterThirdFragment.this.getActivity(), paramArrayOfString[0], paramArrayOfString[1], paramArrayOfString[2]);
        Integer localInteger = Integer.valueOf(1);
        return localInteger;
      }
      catch (Exception localException)
      {
        Log.e("PhoneRegister", "phone register error");
      }
      return null;
    }

    protected void onPostExecute(Integer paramInteger)
    {
      if ((PhoneRegisterThirdFragment.this.getActivity() == null) || (PhoneRegisterThirdFragment.this.getView() == null) || (paramInteger == null));
      while (true)
      {
        return;
        try
        {
          PhoneRegisterModel localPhoneRegisterModel = PhoneRegisterModel.getInstance();
          if (localPhoneRegisterModel.getRet().equals("1"))
          {
            PhoneRegisterThirdFragment.LoginTask localLoginTask = new PhoneRegisterThirdFragment.LoginTask(PhoneRegisterThirdFragment.this, null);
            String[] arrayOfString = new String[7];
            arrayOfString[0] = PhoneRegisterThirdFragment.access$0(PhoneRegisterThirdFragment.this);
            arrayOfString[1] = PhoneRegisterThirdFragment.access$1(PhoneRegisterThirdFragment.this);
            arrayOfString[2] = null;
            arrayOfString[3] = null;
            arrayOfString[4] = null;
            arrayOfString[5] = null;
            arrayOfString[6] = "login";
            localLoginTask.execute(arrayOfString);
            return;
          }
          if (!localPhoneRegisterModel.getCode().equals("4"))
            continue;
          Toast.makeText(PhoneRegisterThirdFragment.this.getActivity(), "手机注册失败", 1).show();
          return;
        }
        catch (Exception localException)
        {
        }
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.PhoneRegisterThirdFragment
 * JD-Core Version:    0.6.0
 */