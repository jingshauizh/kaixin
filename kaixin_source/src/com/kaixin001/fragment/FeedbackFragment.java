package com.kaixin001.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.AsyncTask.Status;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.engine.FeedbackEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.util.CrashRecoverUtil;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;

public class FeedbackFragment extends BaseFragment
  implements View.OnClickListener
{
  public static final int REQUEST_CODE_FEEDBACK = 10001;
  public static final int RESULE_CODE_FEEDBACK = 10002;
  private static final int SUBMIT_SUC = 1;
  private ImageView btnLeft;
  private ImageView btnRight;
  private RelativeLayout btnVoice;
  private EditText evContent;
  private String mLabel = "";
  private ProgressDialog mProgressDialog = null;
  private SubmitFeedbackTask mSubmitFeedbackTask;
  private String postScript = "";

  private String getPostscript()
  {
    PackageManager localPackageManager = getActivity().getPackageManager();
    String str1 = "";
    try
    {
      str1 = localPackageManager.getPackageInfo(getActivity().getPackageName(), 0).versionName;
      String str2 = Build.VERSION.RELEASE;
      String str3 = Build.MODEL;
      ConnectivityManager localConnectivityManager = (ConnectivityManager)getActivity().getSystemService("connectivity");
      NetworkInfo.State localState1 = localConnectivityManager.getNetworkInfo(1).getState();
      localState2 = localConnectivityManager.getNetworkInfo(0).getState();
      str4 = "";
      if (localState1 == NetworkInfo.State.CONNECTED)
      {
        str4 = "wifi";
        return "[" + str1 + "_" + str2 + "_" + str3 + "_" + str4 + "]";
      }
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      while (true)
      {
        NetworkInfo.State localState2;
        localNameNotFoundException.printStackTrace();
        continue;
        if (localState2 != NetworkInfo.State.CONNECTED)
          continue;
        String str4 = "MONET";
      }
    }
  }

  private void submitFeedback(String paramString)
  {
    if (!super.checkNetworkAndHint(true))
      return;
    this.mSubmitFeedbackTask = new SubmitFeedbackTask(null);
    String[] arrayOfString = { paramString };
    this.mSubmitFeedbackTask.execute(arrayOfString);
    this.mProgressDialog = ProgressDialog.show(getActivity(), "", getResources().getString(2131427434), true, true, new DialogInterface.OnCancelListener()
    {
      public void onCancel(DialogInterface paramDialogInterface)
      {
        FeedbackFragment.this.mProgressDialog = null;
        FeedbackFragment.this.mSubmitFeedbackTask.cancel(true);
      }
    });
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if ((paramInt1 == 10001) && (paramInt2 == 10002))
      finish();
  }

  public void onClick(View paramView)
  {
    if (paramView.equals(this.btnLeft))
    {
      hideKeyboard();
      finish();
    }
    do
    {
      return;
      if (!paramView.equals(this.btnRight))
        continue;
      hideKeyboard();
      String str = this.postScript + String.valueOf(this.evContent.getText()).trim();
      if (TextUtils.isEmpty(str))
      {
        Toast.makeText(getActivity(), 2131427458, 0).show();
        return;
      }
      submitFeedback(str);
      return;
    }
    while (!paramView.equals(this.btnVoice));
    hideKeyboard();
    Bundle localBundle = new Bundle();
    localBundle.putString("postScript", this.postScript);
    localBundle.putString("content", getResources().getString(2131428526) + "\n" + this.postScript);
    IntentUtil.showVoiceRecordActivityForResult(getActivity(), this, localBundle, 10001);
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
    return paramLayoutInflater.inflate(2130903104, paramViewGroup, false);
  }

  public void onDestroy()
  {
    super.onDestroy();
    if ((this.mSubmitFeedbackTask != null) && (this.mSubmitFeedbackTask.getStatus() != AsyncTask.Status.FINISHED))
      this.mSubmitFeedbackTask.cancel(true);
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    try
    {
      Bundle localBundle = getArguments();
      if (localBundle != null)
      {
        String str = localBundle.getString("label");
        if (!TextUtils.isEmpty(str))
          this.mLabel = str;
      }
      if (TextUtils.isEmpty(this.mLabel))
        this.mLabel = getString(2131427781);
      setTitleBar(paramView);
      this.evContent = ((EditText)paramView.findViewById(2131362225));
      this.btnVoice = ((RelativeLayout)paramView.findViewById(2131362226));
      this.btnVoice.setOnClickListener(this);
      this.postScript = getPostscript();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  protected void setTitleBar(View paramView)
  {
    this.btnLeft = ((ImageView)paramView.findViewById(2131362914));
    this.btnLeft.setOnClickListener(this);
    ((ImageView)paramView.findViewById(2131362919)).setVisibility(8);
    TextView localTextView = (TextView)paramView.findViewById(2131362920);
    localTextView.setText(this.mLabel);
    localTextView.setVisibility(0);
    ((ImageView)paramView.findViewById(2131362919)).setVisibility(8);
    this.btnRight = ((ImageView)paramView.findViewById(2131362928));
    this.btnRight.setOnClickListener(this);
    this.btnRight.setImageResource(2130839272);
  }

  private class SubmitFeedbackTask extends KXFragment.KXAsyncTask<String, Void, Integer>
  {
    private SubmitFeedbackTask()
    {
      super();
    }

    protected Integer doInBackgroundA(String[] paramArrayOfString)
    {
      if (paramArrayOfString.length != 1)
        return Integer.valueOf(-1);
      try
      {
        if (FeedbackEngine.getInstance().postFeedbackRequest(FeedbackFragment.this.getActivity().getApplicationContext(), paramArrayOfString[0]).booleanValue())
          return Integer.valueOf(1);
        Integer localInteger = Integer.valueOf(0);
        return localInteger;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
      }
      return null;
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      if (paramInteger == null)
        return;
      KXLog.d("FeedbackActivity", "SubmitFeedbackTask");
      FeedbackFragment.this.mProgressDialog.dismiss();
      if (paramInteger.intValue() == 1)
      {
        Toast.makeText(FeedbackFragment.this.getActivity(), 2131427447, 0).show();
        FeedbackFragment.this.finish();
        return;
      }
      Toast.makeText(FeedbackFragment.this.getActivity(), 2131427440, 0).show();
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
 * Qualified Name:     com.kaixin001.fragment.FeedbackFragment
 * JD-Core Version:    0.6.0
 */