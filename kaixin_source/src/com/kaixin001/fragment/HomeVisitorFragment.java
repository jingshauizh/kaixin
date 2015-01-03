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
import com.kaixin001.engine.HomeVisitorEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.model.HomeVisitorModel;
import com.kaixin001.util.KXLog;
import com.kaixin001.view.KXListView;
import java.util.ArrayList;

public class HomeVisitorFragment extends BaseFragment
{
  private static final String TAG = "HomeVisitorActivity";
  private String fuid = "";
  private GetHomeVisitorTask getHomeVisitorTask = null;
  private KXListView list;
  private LinearLayout loadingLayout;
  private FriendsVisitorListAdapter mAdapter;
  private ProgressBar mFooterProBar;
  private TextView mFooterTV;
  private View mFooterView;
  private ProgressBar mProgressBarWait = null;
  private TextView mTxtTips = null;
  private HomeVisitorModel model = null;
  HomeVisitorTaskStruct struct = new HomeVisitorTaskStruct(null);

  private void cancelTask()
  {
    if ((this.getHomeVisitorTask != null) && (!this.getHomeVisitorTask.isCancelled()) && (this.getHomeVisitorTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.getHomeVisitorTask.cancel(true);
      HomeVisitorEngine.getInstance().cancel();
      this.getHomeVisitorTask = null;
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
    if ((this.getHomeVisitorTask != null) && (!this.getHomeVisitorTask.isCancelled()) && (this.getHomeVisitorTask.getStatus() != AsyncTask.Status.FINISHED))
      return;
    this.getHomeVisitorTask = new GetHomeVisitorTask(null);
    this.struct.fuid = this.fuid;
    this.struct.start = paramString;
    this.struct.n = "40";
    this.struct.model = this.model;
    HomeVisitorTaskStruct[] arrayOfHomeVisitorTaskStruct = new HomeVisitorTaskStruct[1];
    arrayOfHomeVisitorTaskStruct[0] = this.struct;
    this.getHomeVisitorTask.execute(arrayOfHomeVisitorTaskStruct);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903156, paramViewGroup, false);
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
      String str = localBundle.getString("fuid");
      if (!TextUtils.isEmpty(str))
        this.fuid = str;
    }
    this.list = ((KXListView)paramView.findViewById(2131362682));
    this.loadingLayout = ((LinearLayout)paramView.findViewById(2131362683));
    this.mTxtTips = ((TextView)paramView.findViewById(2131362685));
    this.mProgressBarWait = ((ProgressBar)paramView.findViewById(2131362684));
    setTitleBar(paramView);
    if (this.model == null)
    {
      this.list.setVisibility(8);
      this.loadingLayout.setVisibility(0);
      this.mProgressBarWait.setVisibility(0);
      this.mTxtTips.setText(2131427380);
      this.model = new HomeVisitorModel();
    }
    while (true)
    {
      this.mAdapter = new FriendsVisitorListAdapter(this, this.model);
      this.mFooterView = getActivity().getLayoutInflater().inflate(2130903259, null);
      this.mFooterTV = ((TextView)this.mFooterView.findViewById(2131362073));
      this.mFooterTV.setText(2131427748);
      this.mFooterProBar = ((ProgressBar)this.mFooterView.findViewById(2131362072));
      this.mFooterView.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          HomeVisitorFragment.this.showLoadingFooter(true);
          HomeVisitorFragment.this.updateVisitor(String.valueOf(HomeVisitorFragment.this.model.getVisitorList().size()));
        }
      });
      this.list.addFooterView(this.mFooterView, null, false);
      this.list.setAdapter(this.mAdapter);
      updateVisitor("0");
      return;
      if (this.model.getVisitorList().size() == 0)
      {
        this.list.setVisibility(8);
        this.loadingLayout.setVisibility(0);
        this.mProgressBarWait.setVisibility(0);
        this.mTxtTips.setText(2131428464);
        continue;
      }
      this.list.setVisibility(0);
      this.loadingLayout.setVisibility(8);
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
        HomeVisitorFragment.this.finish();
      }
    });
    ((ImageView)paramView.findViewById(2131362928)).setVisibility(8);
    ((ImageView)paramView.findViewById(2131362919)).setVisibility(8);
    TextView localTextView = (TextView)paramView.findViewById(2131362920);
    localTextView.setText(2131428463);
    localTextView.setVisibility(0);
  }

  private class GetHomeVisitorTask extends KXFragment.KXAsyncTask<HomeVisitorFragment.HomeVisitorTaskStruct, Void, Boolean>
  {
    HomeVisitorFragment.HomeVisitorTaskStruct struct;

    private GetHomeVisitorTask()
    {
      super();
    }

    protected Boolean doInBackgroundA(HomeVisitorFragment.HomeVisitorTaskStruct[] paramArrayOfHomeVisitorTaskStruct)
    {
      Boolean localBoolean1;
      try
      {
        localBoolean1 = Boolean.valueOf(false);
        if (paramArrayOfHomeVisitorTaskStruct != null)
        {
          if (paramArrayOfHomeVisitorTaskStruct.length != 1)
            return localBoolean1;
          this.struct = paramArrayOfHomeVisitorTaskStruct[0];
          Boolean localBoolean2 = Boolean.valueOf(HomeVisitorEngine.getInstance().doGetHomeVisitorsRequest(HomeVisitorFragment.this.getActivity().getApplicationContext(), this.struct.fuid, this.struct.start, this.struct.n, this.struct.model));
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
          HomeVisitorFragment.this.list.setVisibility(8);
          HomeVisitorFragment.this.loadingLayout.setVisibility(0);
          HomeVisitorFragment.this.mProgressBarWait.setVisibility(0);
          HomeVisitorFragment.this.mTxtTips.setText(2131428464);
          HomeVisitorFragment.this.getHomeVisitorTask = null;
          return;
          if (this.struct.model.getVisitorList().size() == this.struct.model.getTotal())
          {
            HomeVisitorFragment.this.mFooterView.setVisibility(8);
            HomeVisitorFragment.this.list.setVisibility(0);
            HomeVisitorFragment.this.loadingLayout.setVisibility(8);
            HomeVisitorFragment.this.mAdapter.notifyDataSetChanged();
            continue;
          }
        }
        catch (Exception localException)
        {
          KXLog.e("HomeVisitorActivity", "onPostExecute", localException);
          return;
        }
        HomeVisitorFragment.this.showLoadingFooter(false);
      }
      if (this.struct.model.getVisitorList().size() != 0)
      {
        HomeVisitorFragment.this.showLoadingFooter(false);
        Toast.makeText(HomeVisitorFragment.this.getActivity(), 2131427547, 0).show();
        return;
      }
      HomeVisitorFragment.this.loadingLayout.setVisibility(8);
      Toast.makeText(HomeVisitorFragment.this.getActivity(), 2131427547, 0).show();
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private class HomeVisitorTaskStruct
  {
    String fuid;
    HomeVisitorModel model;
    String n;
    String start;

    private HomeVisitorTaskStruct()
    {
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.HomeVisitorFragment
 * JD-Core Version:    0.6.0
 */