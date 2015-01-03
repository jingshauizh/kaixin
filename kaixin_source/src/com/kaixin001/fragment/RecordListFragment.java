package com.kaixin001.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.adapter.NewsItemAdapter;
import com.kaixin001.adapter.NewsItemAdapter.OnViewMoreClickListener;
import com.kaixin001.engine.RecordListEngine;
import com.kaixin001.item.NewsInfo;
import com.kaixin001.model.RecordListModel;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpConnection;
import com.kaixin001.util.CrashRecoverUtil;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;

public class RecordListFragment extends BaseFragment
{
  private static final String TAG = "RecordListActivity";
  private ListView lvRecordList;
  private NewsItemAdapter mAdapter;
  private String mFname = "";
  private String mFuid;
  private String mGender;
  private GetRecordListTask mGetRecordListTask;
  private View mProgressBar;
  private ArrayList<NewsInfo> mRecordListData = new ArrayList();
  private String mStar = "0";
  private boolean mVideoPressed = false;
  private String mViewFname;
  NewsInfo selectedRecordInfo;
  private View titleProgressView;
  private TextView tvEmptyList;

  private void fillAttributes()
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
      String str3 = localBundle.getString("gender");
      if (str3 != null)
        this.mGender = str3;
      String str4 = localBundle.getString("istar");
      if (str4 != null)
        this.mStar = str4;
    }
    if (this.mFname.length() > 3)
    {
      this.mViewFname = (this.mFname.substring(0, 3) + "...");
      return;
    }
    this.mViewFname = this.mFname;
  }

  private String getEmptyTextView(String paramString)
  {
    String str;
    if (User.getInstance().getUID().compareTo(this.mFuid) == 0)
      str = getResources().getString(2131427565);
    while (true)
    {
      return paramString.replace("*", str);
      if (this.mGender.compareTo("0") == 0)
      {
        str = getResources().getString(2131427384);
        continue;
      }
      str = getResources().getString(2131427385);
    }
  }

  private void initRecordListData()
  {
    if (!super.checkNetworkAndHint(true))
    {
      this.lvRecordList.setVisibility(8);
      this.tvEmptyList.setVisibility(8);
      this.mProgressBar.setVisibility(8);
      return;
    }
    this.lvRecordList.setVisibility(8);
    this.tvEmptyList.setVisibility(8);
    this.mProgressBar.setVisibility(0);
    refreshRecordListData(0);
  }

  private void initViews()
  {
    this.tvEmptyList = ((TextView)findViewById(2131363533));
    this.lvRecordList = ((ListView)findViewById(2131363534));
    this.mAdapter = new NewsItemAdapter(this, this.mRecordListData);
    this.lvRecordList.setAdapter(this.mAdapter);
    this.lvRecordList.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
      {
        NewsInfo localNewsInfo;
        String str;
        try
        {
          if (RecordListFragment.this.mRecordListData == null)
            return;
          localNewsInfo = (NewsInfo)RecordListFragment.this.mRecordListData.get((int)paramLong);
          if (TextUtils.isEmpty(localNewsInfo.mFuid))
            return;
          RecordListFragment.this.mAdapter.setCurrentNewsInfo(localNewsInfo);
          str = localNewsInfo.mNtype;
          if ("1088".equals(str))
            return;
          if ("1072".equals(str))
          {
            RecordListFragment.this.mAdapter.showTruth(localNewsInfo);
            return;
          }
        }
        catch (Exception localException)
        {
          KXLog.e("RecordListActivity", "onListItemClick", localException);
          return;
        }
        if ("2".equals(str))
        {
          RecordListFragment.this.mAdapter.showDiaryDetail(localNewsInfo);
          return;
        }
        if ("1016".equals(str))
        {
          RecordListFragment.this.mAdapter.showVoteList(localNewsInfo);
          return;
        }
        if ("1018".equals(str))
        {
          RecordListFragment.this.mAdapter.showWeiboDetail(localNewsInfo);
          return;
        }
        if ("2".equals(str))
        {
          RecordListFragment.this.mAdapter.showCommentDetail(localNewsInfo);
          return;
        }
        if ("1210".equals(str))
        {
          RecordListFragment.this.mAdapter.showStyleBoxDiaryDetail(localNewsInfo);
          return;
        }
        if ("1242".equals(str))
        {
          RecordListFragment.this.mAdapter.showRepost3Item(localNewsInfo);
          return;
        }
        if ("1192".equals(str))
          RecordListFragment.this.mAdapter.showCommentDetail(localNewsInfo);
      }
    });
    this.mAdapter.setOnViewMoreClickListener(new NewsItemAdapter.OnViewMoreClickListener()
    {
      public void onViewMoreClick()
      {
        RecordListModel localRecordListModel = RecordListModel.getInstance();
        ArrayList localArrayList = localRecordListModel.getRecordList();
        if ((localArrayList == null) || (localArrayList.size() < RecordListFragment.this.mRecordListData.size()))
          RecordListFragment.this.mAdapter.showLoadingFooter(true);
        for (boolean bool = true; ; bool = false)
        {
          if ((localArrayList != null) && (localArrayList.size() < localRecordListModel.getTotal()))
            RecordListFragment.this.refreshMore(bool);
          return;
          RecordListFragment.this.updateRecordList();
        }
      }
    });
    this.mProgressBar = findViewById(2131363535);
  }

  private void refreshMore(boolean paramBoolean)
  {
    if ((this.mGetRecordListTask != null) && (!this.mGetRecordListTask.isCancelled()) && (this.mGetRecordListTask.getStatus() != AsyncTask.Status.FINISHED));
    while (true)
    {
      return;
      if (!super.checkNetworkAndHint(paramBoolean))
      {
        this.mAdapter.showLoadingFooter(false);
        return;
      }
      if (this.mRecordListData == null)
        continue;
      int i = this.mRecordListData.size();
      int j = 0;
      if (i > 0)
        if (!TextUtils.isEmpty(((NewsInfo)this.mRecordListData.get(i - 1)).mFuid))
          break label111;
      label111: for (j = i - 1; j != RecordListModel.getInstance().getTotal(); j = i)
      {
        refreshRecordListData(j);
        return;
      }
    }
  }

  private void refreshRecordListData(int paramInt)
  {
    if ((this.mGetRecordListTask != null) && (!this.mGetRecordListTask.isCancelled()) && (this.mGetRecordListTask.getStatus() != AsyncTask.Status.FINISHED));
    do
      return;
    while (HttpConnection.checkNetworkAvailable(getActivity().getApplicationContext()) == -1);
    this.mGetRecordListTask = new GetRecordListTask(null);
    GetRecordListTask localGetRecordListTask = this.mGetRecordListTask;
    String[] arrayOfString = new String[1];
    arrayOfString[0] = String.valueOf(paramInt);
    localGetRecordListTask.execute(arrayOfString);
  }

  private void updateRecordList()
  {
    this.mRecordListData.clear();
    RecordListModel localRecordListModel = RecordListModel.getInstance();
    ArrayList localArrayList = localRecordListModel.getRecordList();
    if ((localArrayList != null) && (localArrayList.size() > 0))
    {
      this.mRecordListData.addAll(localArrayList);
      if (localArrayList.size() < localRecordListModel.getTotal())
      {
        NewsInfo localNewsInfo = new NewsInfo();
        localNewsInfo.mFuid = "";
        this.mRecordListData.add(localNewsInfo);
      }
    }
    if (this.mAdapter != null)
      this.mAdapter.notifyDataSetChanged();
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    NewsInfo localNewsInfo2;
    if (paramInt2 == -1)
    {
      if (paramInt1 != 10)
        break label83;
      localNewsInfo2 = this.mAdapter.getCurrentNewsInfo();
      if (localNewsInfo2 != null)
        break label33;
    }
    label33: label83: NewsInfo localNewsInfo1;
    do
    {
      do
      {
        return;
        String str1 = paramIntent.getStringExtra("cnum");
        String str2 = paramIntent.getStringExtra("upnum");
        if (str1 != null)
          localNewsInfo2.mCnum = str1;
        if (str2 != null)
          localNewsInfo2.mUpnum = str2;
        this.mAdapter.notifyDataSetChanged();
        return;
      }
      while (paramInt1 != 2);
      localNewsInfo1 = this.mAdapter.getCurrentNewsInfo();
    }
    while (localNewsInfo1 == null);
    localNewsInfo1.mCnum = String.valueOf(1 + Integer.parseInt(localNewsInfo1.mCnum));
    this.mAdapter.notifyDataSetChanged();
    Toast.makeText(getActivity(), 2131427450, 0).show();
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
    return paramLayoutInflater.inflate(2130903315, paramViewGroup, false);
  }

  public void onDestroy()
  {
    if ((this.mGetRecordListTask != null) && (this.mGetRecordListTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.mGetRecordListTask.cancel(true);
      this.mGetRecordListTask = null;
      RecordListEngine.getInstance().cancel();
    }
    RecordListModel.getInstance().clear();
    super.onDestroy();
  }

  public void onResume()
  {
    super.onResume();
    this.mVideoPressed = false;
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    fillAttributes();
    initViews();
    setTitleBar();
    initRecordListData();
  }

  protected void setTitleBar()
  {
    ImageView localImageView = (ImageView)findViewById(2131362914);
    localImageView.setVisibility(0);
    localImageView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        RecordListFragment.this.finish();
      }
    });
    TextView localTextView;
    String str;
    if (User.getInstance().getUID().compareTo(this.mFuid) == 0)
    {
      getResources().getString(2131427734);
      this.titleProgressView = findViewById(2131362925);
      ((ImageView)findViewById(2131362928)).setVisibility(8);
      ((ImageView)findViewById(2131362919)).setVisibility(8);
      localTextView = (TextView)findViewById(2131362920);
      str = getResources().getString(2131427753);
      if (User.getInstance().getUID().compareTo(this.mFuid) != 0)
        break label163;
      localTextView.setText(getResources().getString(2131427737));
    }
    while (true)
    {
      localTextView.setVisibility(0);
      return;
      break;
      label163: localTextView.setText(this.mViewFname + str);
    }
  }

  private class GetRecordListTask extends KXFragment.KXAsyncTask<String, Void, Boolean>
  {
    private int mStart = 0;

    private GetRecordListTask()
    {
      super();
    }

    protected Boolean doInBackgroundA(String[] paramArrayOfString)
    {
      try
      {
        this.mStart = Integer.parseInt(paramArrayOfString[0]);
        Boolean localBoolean = Boolean.valueOf(RecordListEngine.getInstance().getRecordList(RecordListFragment.this.getActivity().getApplicationContext(), RecordListFragment.this.mFuid, this.mStart));
        return localBoolean;
      }
      catch (Exception localException)
      {
      }
      return Boolean.valueOf(false);
    }

    protected void onPostExecuteA(Boolean paramBoolean)
    {
      RecordListFragment.this.titleProgressView.setVisibility(8);
      RecordListFragment.this.mProgressBar.setVisibility(8);
      if (!paramBoolean.booleanValue())
      {
        if (this.mStart == 0)
          Toast.makeText(RecordListFragment.this.getActivity(), 2131427742, 0).show();
        return;
      }
      try
      {
        RecordListModel localRecordListModel = RecordListModel.getInstance();
        if ((this.mStart == 0) || (RecordListFragment.this.mRecordListData.size() == 0) || (RecordListFragment.this.mAdapter.isFooterShowLoading()))
        {
          RecordListFragment.this.updateRecordList();
          if (localRecordListModel.getTotal() > 0)
          {
            RecordListFragment.this.mGetRecordListTask = null;
            RecordListFragment.this.refreshMore(false);
          }
        }
        RecordListFragment.this.mAdapter.showLoadingFooter(false);
        if (RecordListFragment.this.mRecordListData.size() == 0)
        {
          String str = RecordListFragment.this.getResources().getString(2131427755);
          RecordListFragment.this.tvEmptyList.setText(RecordListFragment.this.getEmptyTextView(str));
          RecordListFragment.this.tvEmptyList.setVisibility(0);
          RecordListFragment.this.lvRecordList.setVisibility(8);
          return;
        }
      }
      catch (Resources.NotFoundException localNotFoundException)
      {
        KXLog.e("RecordListActivity", "onPostExecute", localNotFoundException);
        return;
      }
      RecordListFragment.this.tvEmptyList.setVisibility(8);
      RecordListFragment.this.lvRecordList.setVisibility(0);
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
 * Qualified Name:     com.kaixin001.fragment.RecordListFragment
 * JD-Core Version:    0.6.0
 */