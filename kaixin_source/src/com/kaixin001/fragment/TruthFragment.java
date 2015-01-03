package com.kaixin001.fragment;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.engine.TruthEngine;
import com.kaixin001.model.TruthModel;
import com.kaixin001.util.CrashRecoverUtil;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;

public class TruthFragment extends BaseFragment
  implements View.OnClickListener
{
  private Button btnTruth;
  private Button btnTruthButtom;
  private ImageView imAnswer;
  private ImageView imAnswerText;
  private RelativeLayout mButtomLayout;
  private String mCnum = "";
  private String mCommentFlag = "";
  private String mFriendName = "";
  private String mFuid = "";
  private GetTruthDataTask mGetTruthDataTask;
  private LinearLayout mLinearLayout;
  private View mMainLayout;
  private String mTid = "";
  private String mTruTitle = "";
  private LinearLayout mTruthTopic;
  private String mTruthid = "";
  private TextView tvAnswer;
  private TextView tvTruthTopic;

  private void getTruthData()
  {
    if (!super.checkNetworkAndHint(true))
      return;
    this.mMainLayout.setVisibility(8);
    this.mButtomLayout.setVisibility(8);
    this.mLinearLayout.setVisibility(0);
    this.mGetTruthDataTask = new GetTruthDataTask(null);
    this.mGetTruthDataTask.execute(new Void[0]);
  }

  private void handleExchangeTruthOptions(int paramInt)
  {
    if (paramInt == 0);
    for (int i = 0; ; i = 1)
    {
      writeInputTruth(i);
      return;
    }
  }

  private void initView()
  {
    this.mLinearLayout = ((LinearLayout)findViewById(2131363743));
    this.mMainLayout = findViewById(2131363734);
    this.mButtomLayout = ((RelativeLayout)findViewById(2131363746));
    this.imAnswer = ((ImageView)findViewById(2131363739));
    this.imAnswerText = ((ImageView)findViewById(2131363740));
    this.tvAnswer = ((TextView)findViewById(2131363741));
    this.mTruthTopic = ((LinearLayout)findViewById(2131363735));
    this.tvTruthTopic = ((TextView)findViewById(2131363737));
    this.btnTruthButtom = ((Button)findViewById(2131363747));
    this.btnTruthButtom.setText(this.mCnum);
    this.btnTruthButtom.setOnClickListener(this);
    this.btnTruth = ((Button)findViewById(2131363742));
    this.btnTruth.setOnClickListener(this);
  }

  private void setTitleBar()
  {
    ImageView localImageView = (ImageView)findViewById(2131362914);
    localImageView.setVisibility(0);
    localImageView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        TruthFragment.this.finish();
      }
    });
    ((ImageView)findViewById(2131362928)).setVisibility(8);
    ((ImageView)findViewById(2131362919)).setVisibility(8);
    TextView localTextView = (TextView)findViewById(2131362920);
    if ((this.mFriendName != null) && (this.mFriendName.length() > 3))
      this.mFriendName = (this.mFriendName.substring(0, 3) + "...");
    localTextView.setText(this.mFriendName + getResources().getString(2131427728));
    localTextView.setVisibility(0);
  }

  private void showExchangeTruthOptions()
  {
    String[] arrayOfString = getResources().getStringArray(2131492886);
    arrayOfString[1] = arrayOfString[1].replace("*", this.mFriendName);
    DialogUtil.showSelectListDlg(getActivity(), 2131427729, arrayOfString, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        TruthFragment.this.handleExchangeTruthOptions(paramInt);
      }
    }
    , 1);
  }

  private void writeInputTruth(int paramInt)
  {
    Intent localIntent = new Intent(getActivity(), TruthInputFragment.class);
    Bundle localBundle = new Bundle();
    localBundle.putString("fuid", this.mFuid);
    localBundle.putString("tid", this.mTid);
    localBundle.putString("privacy", String.valueOf(paramInt));
    localIntent.putExtras(localBundle);
    startActivityForResult(localIntent, 1);
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if ((paramInt2 == -1) && (paramInt1 == 1) && (paramIntent.getIntExtra("privacy", -1) == 1))
    {
      String str = TruthModel.getInstance().getTruthContent();
      this.tvAnswer.setText(str);
      this.imAnswerText.setVisibility(8);
      this.tvAnswer.setVisibility(0);
      this.btnTruth.setVisibility(8);
    }
  }

  public void onClick(View paramView)
  {
    if (paramView.equals(this.btnTruthButtom))
      if ((this.mFuid.length() > 0) && (this.mTid.length() > 0))
      {
        Intent localIntent = new Intent(getActivity(), ObjCommentFragment.class);
        localIntent.putExtra("fuid", this.mFuid);
        localIntent.putExtra("id", this.mTruthid);
        localIntent.putExtra("type", "1072");
        localIntent.putExtra("commentflag", this.mCommentFlag);
        startFragment(localIntent, true, 1);
      }
    do
      return;
    while (!paramView.equals(this.btnTruth));
    showExchangeTruthOptions();
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
    return paramLayoutInflater.inflate(2130903349, paramViewGroup, false);
  }

  public void onDestroy()
  {
    if ((this.mGetTruthDataTask != null) && (this.mGetTruthDataTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.mGetTruthDataTask.cancel(true);
      this.mGetTruthDataTask = null;
      TruthEngine.getInstance().cancel();
    }
    TruthModel.getInstance().clear();
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
        this.mFuid = str1;
      String str2 = localBundle.getString("tid");
      if (str2 != null)
        this.mTid = str2;
      String str3 = localBundle.getString("commentflag");
      if (str3 != null)
        this.mCommentFlag = str3;
      String str4 = localBundle.getString("fname");
      if (str4 != null)
        this.mFriendName = str4;
      String str5 = localBundle.getString("cnum");
      if (str5 != null)
        this.mCnum = str5;
    }
    setTitleBar();
    initView();
    getTruthData();
  }

  private class GetTruthDataTask extends KXFragment.KXAsyncTask<Void, Void, Boolean>
  {
    private GetTruthDataTask()
    {
      super();
    }

    protected Boolean doInBackgroundA(Void[] paramArrayOfVoid)
    {
      try
      {
        Boolean localBoolean = Boolean.valueOf(TruthEngine.getInstance().doGetTruthDetail(TruthFragment.this.getActivity().getApplicationContext(), TruthFragment.this.mTid, TruthFragment.this.mFuid));
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
      {
        TruthFragment.this.finish();
        return;
      }
      while (true)
      {
        try
        {
          if (!paramBoolean.booleanValue())
            break;
          TruthFragment.this.mLinearLayout.setVisibility(8);
          TruthFragment.this.mButtomLayout.setVisibility(0);
          TruthFragment.this.mMainLayout.setVisibility(0);
          TruthFragment.this.imAnswer.setVisibility(0);
          int i = TruthModel.getInstance().getStatus();
          TruthFragment.this.mTruTitle = TruthModel.getInstance().getTitle();
          TruthFragment.this.tvTruthTopic.setText(TruthFragment.this.mTruTitle);
          TruthFragment.this.mTruthTopic.setVisibility(0);
          if (i == 1)
          {
            String str = TruthModel.getInstance().getTruthContent();
            TruthFragment.this.tvAnswer.setText(str);
            TruthFragment.this.imAnswerText.setVisibility(8);
            TruthFragment.this.tvAnswer.setVisibility(0);
            TruthFragment.this.btnTruth.setVisibility(8);
            int j = TruthModel.getInstance().getTruthid();
            TruthFragment.this.mTruthid = String.valueOf(j);
            int k = TruthModel.getInstance().getCNum();
            TruthFragment.this.btnTruthButtom.setText(String.valueOf(k));
            return;
          }
        }
        catch (Exception localException)
        {
          KXLog.e("TruthActivity", "onPostExecute", localException);
          return;
        }
        TruthFragment.this.imAnswerText.setVisibility(0);
        TruthFragment.this.tvAnswer.setVisibility(8);
        TruthFragment.this.btnTruth.setVisibility(0);
      }
      TruthFragment.this.mLinearLayout.setVisibility(8);
      Toast.makeText(TruthFragment.this.getActivity(), 2131427547, 0).show();
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
 * Qualified Name:     com.kaixin001.fragment.TruthFragment
 * JD-Core Version:    0.6.0
 */