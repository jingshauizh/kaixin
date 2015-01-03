package com.kaixin001.fragment;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.engine.TruthEngine;
import com.kaixin001.model.TruthModel;
import com.kaixin001.util.CrashRecoverUtil;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;

public class TruthInputFragment extends BaseFragment
  implements View.OnClickListener, DialogInterface.OnClickListener
{
  public static final int INPUT_TRUTH = 1;
  private boolean bStop;
  private ImageView btnLeft;
  private ImageView btnRight;
  private EditText evContent;
  private String fuid = "";
  private View mEditView;
  private SubmitTruthTask mSubmitTruthTask;
  private String privacy = "";
  private String tid = "";
  private TextView tvTitle;
  private LinearLayout waitLayout;

  private void submitTruth()
  {
    String str = String.valueOf(this.evContent.getText()).trim();
    if (str.length() < 2)
      DialogUtil.showKXAlertDialog(getActivity(), 2131427731, null);
    do
      return;
    while (!super.checkNetworkAndHint(true));
    this.btnRight.setEnabled(false);
    this.mEditView.setVisibility(8);
    this.waitLayout.setVisibility(0);
    this.bStop = false;
    this.mSubmitTruthTask = new SubmitTruthTask(null);
    String[] arrayOfString = { str };
    this.mSubmitTruthTask.execute(arrayOfString);
  }

  public void onClick(DialogInterface paramDialogInterface, int paramInt)
  {
    if (paramInt == -1)
      finish();
  }

  public void onClick(View paramView)
  {
    if (paramView.equals(this.btnRight))
      submitTruth();
    do
      return;
    while (!paramView.equals(this.btnLeft));
    if (TextUtils.isEmpty(String.valueOf(this.evContent.getText()).trim()))
    {
      finish();
      return;
    }
    DialogUtil.showKXAlertDialog(getActivity(), 2131427451, 2131427382, 2131427383, this, this);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (CrashRecoverUtil.isCrashBefore())
    {
      CrashRecoverUtil.crashRecover(getActivity().getApplicationContext());
      IntentUtil.returnToDesktop(getActivity());
    }
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903178, paramViewGroup, false);
  }

  public void onDestroy()
  {
    if ((this.mSubmitTruthTask != null) && (this.mSubmitTruthTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.mSubmitTruthTask.cancel(true);
      this.mSubmitTruthTask = null;
      TruthEngine.getInstance().cancel();
    }
    super.onDestroy();
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    Bundle localBundle = getArguments();
    if (localBundle != null)
    {
      String str1 = localBundle.getString("fuid");
      if (str1 != null)
        this.fuid = str1;
      String str2 = localBundle.getString("tid");
      if (str2 != null)
        this.tid = str2;
      String str3 = localBundle.getString("privacy");
      if (str3 != null)
        this.privacy = str3;
    }
    this.waitLayout = ((LinearLayout)findViewById(2131362867));
    this.evContent = ((EditText)findViewById(2131362866));
    InputFilter[] arrayOfInputFilter = new InputFilter[1];
    arrayOfInputFilter[0] = new InputFilter.LengthFilter(500);
    this.evContent.setFilters(arrayOfInputFilter);
    this.mEditView = findViewById(2131362865);
    setTitleBar();
  }

  public void requestFinish()
  {
    if (TextUtils.isEmpty(String.valueOf(this.evContent.getText()).trim()))
    {
      finish();
      return;
    }
    DialogUtil.showKXAlertDialog(getActivity(), 2131427451, 2131427382, 2131427383, this, this);
  }

  protected void setTitleBar()
  {
    this.btnLeft = ((ImageView)findViewById(2131362914));
    this.btnLeft.setOnClickListener(this);
    this.tvTitle = ((TextView)findViewById(2131362920));
    this.tvTitle.setText(2131427730);
    this.tvTitle.setVisibility(0);
    ((ImageView)findViewById(2131362919)).setVisibility(8);
    this.btnRight = ((ImageView)findViewById(2131362928));
    this.btnRight.setOnClickListener(this);
    this.btnRight.setImageResource(2130839272);
  }

  private class SubmitTruthTask extends KXFragment.KXAsyncTask<String, Void, Boolean>
  {
    private SubmitTruthTask()
    {
      super();
    }

    protected Boolean doInBackgroundA(String[] paramArrayOfString)
    {
      if (paramArrayOfString.length < 1)
        return Boolean.valueOf(false);
      String str = paramArrayOfString[0];
      try
      {
        Boolean localBoolean = Boolean.valueOf(TruthEngine.getInstance().submitExchangeTruth(TruthInputFragment.this.getActivity().getApplicationContext(), TruthInputFragment.this.tid, TruthInputFragment.this.fuid, TruthInputFragment.this.privacy, str));
        return localBoolean;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
      }
      return null;
    }

    protected void onPostExecuteA(Boolean paramBoolean)
    {
      if (paramBoolean == null)
        TruthInputFragment.this.finish();
      do
      {
        return;
        try
        {
          if (paramBoolean.booleanValue())
          {
            TruthInputFragment.this.waitLayout.setVisibility(8);
            Intent localIntent = new Intent();
            localIntent.putExtra("privacy", 1);
            TruthInputFragment.this.setResult(-1, localIntent);
            TruthInputFragment.this.finishFragment(1);
            TruthInputFragment.this.finish();
            Toast.makeText(TruthInputFragment.this.getActivity(), TruthModel.getInstance().getMsg(), 0).show();
            return;
          }
        }
        catch (Exception localException)
        {
          KXLog.e("TruthInputActivity", "onPostExecute", localException);
          return;
        }
        TruthInputFragment.this.btnRight.setEnabled(true);
        TruthInputFragment.this.waitLayout.setVisibility(8);
        TruthInputFragment.this.mEditView.setVisibility(0);
      }
      while (TruthInputFragment.this.bStop);
      Toast.makeText(TruthInputFragment.this.getActivity(), 2131427732, 0).show();
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
 * Qualified Name:     com.kaixin001.fragment.TruthInputFragment
 * JD-Core Version:    0.6.0
 */