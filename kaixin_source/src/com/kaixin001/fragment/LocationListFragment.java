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
import com.kaixin001.engine.LocationListEngine;
import com.kaixin001.item.NewsInfo;
import com.kaixin001.model.LocationListModel;
import com.kaixin001.model.NewsModel;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpConnection;
import com.kaixin001.util.CrashRecoverUtil;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;

public class LocationListFragment extends BaseFragment
{
  private static final String TAG = "LocationListFragment";
  private ListView lvLocationList;
  private NewsItemAdapter mAdapter;
  private String mFname = "";
  private String mFuid;
  private String mGender;
  private GetLocationListTask mGetLocationListTask;
  private ArrayList<NewsInfo> mLocationListData = new ArrayList();
  private View mProgressBar;
  private String mStar = "0";
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

  private void initLocationListData()
  {
    if (!super.checkNetworkAndHint(true))
    {
      this.lvLocationList.setVisibility(8);
      this.tvEmptyList.setVisibility(8);
      this.mProgressBar.setVisibility(8);
      return;
    }
    this.lvLocationList.setVisibility(8);
    this.tvEmptyList.setVisibility(8);
    this.mProgressBar.setVisibility(0);
    refreshLocationListData(0);
  }

  private void initViews()
  {
    this.tvEmptyList = ((TextView)findViewById(2131362975));
    this.lvLocationList = ((ListView)findViewById(2131362976));
    this.mAdapter = new NewsItemAdapter(this, this.mLocationListData);
    this.mAdapter.notifyDataSetChanged();
    this.lvLocationList.setAdapter(this.mAdapter);
    this.lvLocationList.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
      {
        NewsInfo localNewsInfo;
        String str;
        try
        {
          if (LocationListFragment.this.mLocationListData == null)
            return;
          localNewsInfo = (NewsInfo)LocationListFragment.this.mLocationListData.get((int)paramLong);
          if (TextUtils.isEmpty(localNewsInfo.mFuid))
            return;
          LocationListFragment.this.mAdapter.setCurrentNewsInfo(localNewsInfo);
          str = localNewsInfo.mNtype;
          if ("1088".equals(str))
            return;
          if ("1072".equals(str))
          {
            LocationListFragment.this.mAdapter.showTruth(localNewsInfo);
            return;
          }
        }
        catch (Exception localException)
        {
          KXLog.e("LocationListFragment", "onListItemClick", localException);
          return;
        }
        if ("2".equals(str))
        {
          LocationListFragment.this.mAdapter.showDiaryDetail(localNewsInfo);
          return;
        }
        if ("1016".equals(str))
        {
          LocationListFragment.this.mAdapter.showVoteList(localNewsInfo);
          return;
        }
        if ("1018".equals(str))
        {
          LocationListFragment.this.mAdapter.showWeiboDetail(localNewsInfo);
          return;
        }
        if ("2".equals(str))
        {
          LocationListFragment.this.mAdapter.showCommentDetail(localNewsInfo);
          return;
        }
        if ("1210".equals(str))
        {
          LocationListFragment.this.mAdapter.showStyleBoxDiaryDetail(localNewsInfo);
          return;
        }
        if ("1242".equals(str))
        {
          LocationListFragment.this.mAdapter.showRepost3Item(localNewsInfo);
          return;
        }
        if ("1192".equals(str))
          LocationListFragment.this.mAdapter.showCommentDetail(localNewsInfo);
      }
    });
    this.mAdapter.setOnViewMoreClickListener(new NewsItemAdapter.OnViewMoreClickListener()
    {
      public void onViewMoreClick()
      {
        LocationListModel localLocationListModel = LocationListModel.getInstance();
        ArrayList localArrayList = localLocationListModel.getLocationList();
        if ((localArrayList == null) || (localArrayList.size() < LocationListFragment.this.mLocationListData.size()))
          LocationListFragment.this.mAdapter.showLoadingFooter(true);
        for (boolean bool = true; ; bool = false)
        {
          if ((localArrayList != null) && (localArrayList.size() < localLocationListModel.getTotal()))
            LocationListFragment.this.refreshMore(bool);
          return;
          LocationListFragment.this.updateLocationList();
        }
      }
    });
    this.mProgressBar = findViewById(2131362977);
  }

  private void refreshLocationListData(int paramInt)
  {
    if ((this.mGetLocationListTask != null) && (!this.mGetLocationListTask.isCancelled()) && (this.mGetLocationListTask.getStatus() != AsyncTask.Status.FINISHED));
    do
      return;
    while (HttpConnection.checkNetworkAvailable(getActivity().getApplicationContext()) == -1);
    this.mGetLocationListTask = new GetLocationListTask(null);
    GetLocationListTask localGetLocationListTask = this.mGetLocationListTask;
    String[] arrayOfString = new String[1];
    arrayOfString[0] = String.valueOf(paramInt);
    localGetLocationListTask.execute(arrayOfString);
  }

  private void refreshMore(boolean paramBoolean)
  {
    if ((this.mGetLocationListTask != null) && (!this.mGetLocationListTask.isCancelled()) && (this.mGetLocationListTask.getStatus() != AsyncTask.Status.FINISHED));
    while (true)
    {
      return;
      if (!super.checkNetworkAndHint(paramBoolean))
      {
        this.mAdapter.showLoadingFooter(false);
        return;
      }
      if (this.mLocationListData == null)
        continue;
      int i = this.mLocationListData.size();
      int j = 0;
      if (i > 0)
        if (!TextUtils.isEmpty(((NewsInfo)this.mLocationListData.get(i - 1)).mFuid))
          break label111;
      label111: for (j = i - 1; j != LocationListModel.getInstance().getTotal(); j = i)
      {
        refreshLocationListData(j);
        return;
      }
    }
  }

  private void setData()
  {
    ArrayList localArrayList = NewsModel.getInstance().getNewsList();
    int i = localArrayList.size();
    for (int j = 0; ; j++)
    {
      if (j >= i)
        return;
      if (!((NewsInfo)localArrayList.get(j)).mNtype.equals("1192"))
        continue;
      this.mLocationListData.add((NewsInfo)localArrayList.get(j));
    }
  }

  private void updateLocationList()
  {
    this.mLocationListData.clear();
    LocationListModel localLocationListModel = LocationListModel.getInstance();
    ArrayList localArrayList = localLocationListModel.getLocationList();
    if ((localArrayList != null) && (localArrayList.size() > 0))
    {
      this.mLocationListData.addAll(localArrayList);
      if (localArrayList.size() < localLocationListModel.getTotal())
      {
        NewsInfo localNewsInfo = new NewsInfo();
        localNewsInfo.mFuid = "";
        this.mLocationListData.add(localNewsInfo);
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
    return paramLayoutInflater.inflate(2130903205, paramViewGroup, false);
  }

  public void onDestroy()
  {
    if ((this.mGetLocationListTask != null) && (this.mGetLocationListTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.mGetLocationListTask.cancel(true);
      this.mGetLocationListTask = null;
      LocationListEngine.getInstance().cancel();
    }
    LocationListModel.getInstance().clear();
    super.onDestroy();
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    fillAttributes();
    setTitleBar();
    initViews();
    initLocationListData();
  }

  protected void setTitleBar()
  {
    ImageView localImageView = (ImageView)findViewById(2131362914);
    localImageView.setVisibility(0);
    localImageView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        LocationListFragment.this.finish();
      }
    });
    findViewById(2131362928).setVisibility(8);
    findViewById(2131362919).setVisibility(8);
    this.titleProgressView = findViewById(2131362925);
    TextView localTextView = (TextView)findViewById(2131362920);
    if (User.getInstance().getUID().compareTo(this.mFuid) == 0)
      localTextView.setText(getResources().getString(2131427739));
    while (true)
    {
      localTextView.setVisibility(0);
      return;
      localTextView.setText(this.mViewFname + getResources().getString(2131427754));
    }
  }

  private class GetLocationListTask extends KXFragment.KXAsyncTask<String, Void, Boolean>
  {
    private int mStart = 0;

    private GetLocationListTask()
    {
      super();
    }

    protected Boolean doInBackgroundA(String[] paramArrayOfString)
    {
      try
      {
        this.mStart = Integer.parseInt(paramArrayOfString[0]);
        Boolean localBoolean = Boolean.valueOf(LocationListEngine.getInstance().getLocationList(LocationListFragment.this.getActivity().getApplicationContext(), LocationListFragment.this.mFuid, this.mStart));
        return localBoolean;
      }
      catch (Exception localException)
      {
      }
      return Boolean.valueOf(false);
    }

    protected void onPostExecuteA(Boolean paramBoolean)
    {
      LocationListFragment.this.titleProgressView.setVisibility(8);
      LocationListFragment.this.mProgressBar.setVisibility(8);
      if (!paramBoolean.booleanValue())
      {
        if (this.mStart == 0)
          Toast.makeText(LocationListFragment.this.getActivity(), 2131427743, 0).show();
        return;
      }
      try
      {
        LocationListModel localLocationListModel = LocationListModel.getInstance();
        if ((this.mStart == 0) || (LocationListFragment.this.mLocationListData.size() == 0) || (LocationListFragment.this.mAdapter.isFooterShowLoading()))
        {
          LocationListFragment.this.updateLocationList();
          if (localLocationListModel.getTotal() > 0)
          {
            LocationListFragment.this.mGetLocationListTask = null;
            LocationListFragment.this.refreshMore(false);
          }
        }
        LocationListFragment.this.mAdapter.showLoadingFooter(false);
        if (LocationListFragment.this.mLocationListData.size() == 0)
        {
          String str = LocationListFragment.this.getResources().getString(2131427755);
          LocationListFragment.this.tvEmptyList.setText(LocationListFragment.this.getEmptyTextView(str));
          LocationListFragment.this.tvEmptyList.setVisibility(0);
          LocationListFragment.this.lvLocationList.setVisibility(8);
          return;
        }
      }
      catch (Resources.NotFoundException localNotFoundException)
      {
        KXLog.e("LocationListFragment", "onPostExecute", localNotFoundException);
        return;
      }
      LocationListFragment.this.tvEmptyList.setVisibility(8);
      LocationListFragment.this.lvLocationList.setVisibility(0);
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
 * Qualified Name:     com.kaixin001.fragment.LocationListFragment
 * JD-Core Version:    0.6.0
 */