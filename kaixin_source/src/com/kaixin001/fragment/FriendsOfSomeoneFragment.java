package com.kaixin001.fragment;

import android.content.res.Resources;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.adapter.FriendsVisitorListAdapter;
import com.kaixin001.engine.FriendsOfSomeOneEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.model.HomeVisitorModel;
import com.kaixin001.util.KXLog;
import com.kaixin001.view.KXListView;
import java.util.ArrayList;

public class FriendsOfSomeoneFragment extends BaseFragment
{
  private static final String TAG = "FriendsOfSomeoneActivity";
  private String fname = "";
  private String fuid = "";
  private GetFriendsOfSomeoneTask getFriendsOfSomeoneTask;
  private String ismyfriend = "0";
  private KXListView list;
  private LinearLayout loadingLayout;
  private FriendsVisitorListAdapter mAdapter;
  private ProgressBar mFooterProBar;
  private TextView mFooterTV;
  private View mFooterView;
  private ProgressBar mProgressBarWait = null;
  private TextView mTxtTips = null;
  private HomeVisitorModel model = null;
  private LinearLayout privateLayout;
  private FriendsOfSomeoneTaskStruct struct = new FriendsOfSomeoneTaskStruct(null);

  private void cancelTask()
  {
    if ((this.getFriendsOfSomeoneTask != null) && (!this.getFriendsOfSomeoneTask.isCancelled()) && (this.getFriendsOfSomeoneTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.getFriendsOfSomeoneTask.cancel(true);
      FriendsOfSomeOneEngine.getInstance().cancel();
      this.getFriendsOfSomeoneTask = null;
    }
  }

  private void showLoadingFooter(boolean paramBoolean)
  {
    ProgressBar localProgressBar;
    if (this.mFooterProBar != null)
    {
      localProgressBar = this.mFooterProBar;
      if (!paramBoolean)
        break label65;
    }
    label65: for (int j = 0; ; j = 4)
    {
      localProgressBar.setVisibility(j);
      if (this.mFooterTV != null)
      {
        int i = getResources().getColor(2130839419);
        if (paramBoolean)
          i = getResources().getColor(2130839395);
        this.mFooterTV.setTextColor(i);
      }
      return;
    }
  }

  private void updateVisitor(String paramString)
  {
    if ((this.getFriendsOfSomeoneTask != null) && (!this.getFriendsOfSomeoneTask.isCancelled()) && (this.getFriendsOfSomeoneTask.getStatus() != AsyncTask.Status.FINISHED))
      return;
    this.getFriendsOfSomeoneTask = new GetFriendsOfSomeoneTask(null);
    this.struct.fuid = this.fuid;
    this.struct.start = paramString;
    this.struct.n = "40";
    this.struct.model = this.model;
    FriendsOfSomeoneTaskStruct[] arrayOfFriendsOfSomeoneTaskStruct = new FriendsOfSomeoneTaskStruct[1];
    arrayOfFriendsOfSomeoneTaskStruct[0] = this.struct;
    this.getFriendsOfSomeoneTask.execute(arrayOfFriendsOfSomeoneTaskStruct);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903132, paramViewGroup, false);
  }

  public void onDestroy()
  {
    cancelTask();
    super.onDestroy();
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    Bundle localBundle = getArguments();
    if (localBundle != null)
    {
      String str1 = localBundle.getString("fuid");
      if (!TextUtils.isEmpty(str1))
        this.fuid = str1;
      String str2 = localBundle.getString("fname");
      if (!TextUtils.isEmpty(str2))
        this.fname = str2;
      String str3 = localBundle.getString("ismyfriend");
      if (!TextUtils.isEmpty(str3))
        this.ismyfriend = str3;
    }
    this.list = ((KXListView)paramView.findViewById(2131362391));
    this.loadingLayout = ((LinearLayout)paramView.findViewById(2131362392));
    this.privateLayout = ((LinearLayout)paramView.findViewById(2131362395));
    this.mTxtTips = ((TextView)paramView.findViewById(2131362394));
    this.mProgressBarWait = ((ProgressBar)paramView.findViewById(2131362393));
    setTitleBar(paramView);
    this.loadingLayout.setOnClickListener(null);
    if (this.model == null)
    {
      this.list.setVisibility(8);
      this.loadingLayout.setVisibility(0);
      this.mProgressBarWait.setVisibility(0);
      this.mTxtTips.setText(2131427380);
      this.model = new HomeVisitorModel();
      this.mFooterView = getActivity().getLayoutInflater().inflate(2130903259, null);
      this.mFooterTV = ((TextView)this.mFooterView.findViewById(2131362073));
      this.mFooterTV.setText(2131427748);
      this.mFooterProBar = ((ProgressBar)this.mFooterView.findViewById(2131362072));
      this.mFooterView.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          FriendsOfSomeoneFragment.this.showLoadingFooter(true);
          FriendsOfSomeoneFragment.this.updateVisitor(String.valueOf(FriendsOfSomeoneFragment.this.model.getVisitorList().size()));
        }
      });
      if (!this.ismyfriend.equals("0"))
        break label413;
      this.mAdapter = new FriendsVisitorListAdapter(this, this.model)
      {
        public int getCount()
        {
          return 1;
        }
      };
      this.privateLayout.setVisibility(0);
    }
    while (true)
    {
      this.list.setAdapter(this.mAdapter);
      updateVisitor("0");
      return;
      if (this.model.getVisitorList().size() == 0)
      {
        this.list.setVisibility(8);
        this.loadingLayout.setVisibility(0);
        this.mProgressBarWait.setVisibility(0);
        this.mTxtTips.setText(2131428464);
        break;
      }
      this.list.setVisibility(0);
      this.loadingLayout.setVisibility(8);
      break;
      label413: this.mAdapter = new FriendsVisitorListAdapter(this, this.model);
      this.privateLayout.setVisibility(8);
      this.list.addFooterView(this.mFooterView, null, false);
    }
  }

  protected void setTitleBar(View paramView)
  {
    ImageView localImageView = (ImageView)paramView.findViewById(2131362914);
    localImageView.setVisibility(0);
    localImageView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        FriendsOfSomeoneFragment.this.finish();
      }
    });
    ((ImageView)paramView.findViewById(2131362928)).setVisibility(8);
    ((ImageView)paramView.findViewById(2131362919)).setVisibility(8);
    TextView localTextView = (TextView)paramView.findViewById(2131362920);
    localTextView.setText(this.fname + getResources().getString(2131428465));
    localTextView.setVisibility(0);
  }

  private class FriendsOfSomeoneTaskStruct
  {
    String fuid;
    HomeVisitorModel model;
    String n;
    String start;

    private FriendsOfSomeoneTaskStruct()
    {
    }
  }

  private class GetFriendsOfSomeoneTask extends KXFragment.KXAsyncTask<FriendsOfSomeoneFragment.FriendsOfSomeoneTaskStruct, Void, Boolean>
  {
    FriendsOfSomeoneFragment.FriendsOfSomeoneTaskStruct struct;

    private GetFriendsOfSomeoneTask()
    {
      super();
    }

    protected Boolean doInBackgroundA(FriendsOfSomeoneFragment.FriendsOfSomeoneTaskStruct[] paramArrayOfFriendsOfSomeoneTaskStruct)
    {
      Boolean localBoolean1;
      try
      {
        localBoolean1 = Boolean.valueOf(false);
        if (paramArrayOfFriendsOfSomeoneTaskStruct != null)
        {
          if (paramArrayOfFriendsOfSomeoneTaskStruct.length != 1)
            return localBoolean1;
          this.struct = paramArrayOfFriendsOfSomeoneTaskStruct[0];
          Boolean localBoolean2 = Boolean.valueOf(FriendsOfSomeOneEngine.getInstance().doGetFriendsOfSomeoneRequest(FriendsOfSomeoneFragment.this.getActivity().getApplicationContext(), this.struct.fuid, this.struct.start, this.struct.n, this.struct.model));
          return localBoolean2;
        }
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
        localBoolean1 = Boolean.valueOf(true);
      }
      return localBoolean1;
    }

    protected void onPostExecuteA(Boolean paramBoolean)
    {
      while (true)
      {
        try
        {
          if (!paramBoolean.booleanValue())
            break;
          if (this.struct.model.getVisitorList().size() != 0)
            continue;
          FriendsOfSomeoneFragment.this.list.setVisibility(8);
          FriendsOfSomeoneFragment.this.loadingLayout.setVisibility(0);
          FriendsOfSomeoneFragment.this.mProgressBarWait.setVisibility(0);
          FriendsOfSomeoneFragment.this.mTxtTips.setText(2131428464);
          FriendsOfSomeoneFragment.this.getFriendsOfSomeoneTask = null;
          return;
          if (this.struct.model.getVisitorList().size() == this.struct.model.getTotal())
          {
            FriendsOfSomeoneFragment.this.mFooterView.setVisibility(8);
            FriendsOfSomeoneFragment.this.list.setVisibility(0);
            FriendsOfSomeoneFragment.this.loadingLayout.setVisibility(8);
            FriendsOfSomeoneFragment.this.mAdapter.notifyDataSetChanged();
            continue;
          }
        }
        catch (Exception localException)
        {
          KXLog.e("FriendsOfSomeoneActivity", "onPostExecute", localException);
          return;
        }
        FriendsOfSomeoneFragment.this.showLoadingFooter(false);
      }
      if (this.struct.model.getVisitorList().size() != 0)
      {
        Toast.makeText(FriendsOfSomeoneFragment.this.getActivity(), 2131427547, 0).show();
        return;
      }
      FriendsOfSomeoneFragment.this.loadingLayout.setVisibility(8);
      Toast.makeText(FriendsOfSomeoneFragment.this.getActivity(), 2131427547, 0).show();
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
 * Qualified Name:     com.kaixin001.fragment.FriendsOfSomeoneFragment
 * JD-Core Version:    0.6.0
 */