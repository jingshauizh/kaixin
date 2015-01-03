package com.kaixin001.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Location;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.engine.LbsEngine;
import com.kaixin001.item.PoiActivityItem;
import com.kaixin001.lbs.LocationService;
import com.kaixin001.lbs.RefreshLocationProxy;
import com.kaixin001.lbs.RefreshLocationProxy.IRefreshLocationCallback;
import com.kaixin001.model.LbsModel;
import com.kaixin001.task.GetLbsActivityTask;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.view.PullToRefreshView;
import com.kaixin001.view.PullToRefreshView.PullToRefreshListener;
import java.util.ArrayList;

public class LbsActivityListFragment extends BaseFragment
  implements AdapterView.OnItemClickListener, PullToRefreshView.PullToRefreshListener
{
  public static final String CELL_ID = "cell_id";
  public static final String LATITUDE = "lat";
  public static final String LONGITUDE = "lon";
  private static final String TAG_VIEW_MORE = "view_more";
  Location currentLocation;
  final PoiListAdapter mAdapter = new PoiListAdapter(null);
  private ImageView mBtnRight = null;
  private String mCellId;
  private PullToRefreshView mDownView;
  private ProgressBar mEmptyProBar = null;
  private View mFooter;
  private ProgressBar mFooterProBar;
  private TextView mFooterTV;
  private GetLbsActivityTask mGetActivityListTask = null;
  private View mHeaderView = null;
  private String mLatitude;
  private View mLayoutEmpty = null;
  private ListView mListView = null;
  private String mLongitude;
  final ArrayList<PoiActivityItem> mPoiList = new ArrayList();
  private ProgressBar mRightProBar = null;
  private TextView mTxtEmpty = null;
  RefreshLocationProxy refreshLocationProxy = null;

  private void cancelTask()
  {
    if ((this.mGetActivityListTask != null) && (!this.mGetActivityListTask.isCancelled()) && (this.mGetActivityListTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.mGetActivityListTask.cancel(true);
      LbsEngine.getInstance().cancel();
      this.mGetActivityListTask = null;
    }
  }

  private String getCellIdInfo()
  {
    try
    {
      TelephonyManager localTelephonyManager = (TelephonyManager)getActivity().getSystemService("phone");
      String str1 = localTelephonyManager.getNetworkOperator();
      String str2 = str1.substring(-1 + str1.length());
      GsmCellLocation localGsmCellLocation = (GsmCellLocation)localTelephonyManager.getCellLocation();
      if (localGsmCellLocation != null)
      {
        StringBuffer localStringBuffer = new StringBuffer();
        localStringBuffer.append(localGsmCellLocation.getCid()).append(":").append(localGsmCellLocation.getLac()).append(":").append(str2);
        String str3 = localStringBuffer.toString();
        return str3;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  private String getLatitude()
  {
    if (this.currentLocation == null)
      return "";
    return String.valueOf(this.currentLocation.getLatitude());
  }

  private String getLongitude()
  {
    if (this.currentLocation == null)
      return "";
    return String.valueOf(this.currentLocation.getLongitude());
  }

  private RefreshLocationProxy getRefreshLocationProxy()
  {
    if (this.refreshLocationProxy == null)
      this.refreshLocationProxy = new RefreshLocationProxy(getActivity(), new RefreshLocationProxy.IRefreshLocationCallback()
      {
        public void onBeginRefreshLocation()
        {
          LbsActivityListFragment.this.showLocating();
          LbsActivityListFragment.this.cancelTask();
          LbsActivityListFragment localLbsActivityListFragment = LbsActivityListFragment.this;
          if (LbsActivityListFragment.this.mPoiList.size() > 0);
          for (boolean bool = true; ; bool = false)
          {
            localLbsActivityListFragment.showLoading(bool);
            return;
          }
        }

        public void onCancelRefreshLocation()
        {
          LbsActivityListFragment.this.stopPullToRefresh();
          if (LbsActivityListFragment.this.mPoiList.size() == 0)
          {
            LbsActivityListFragment.this.showError(2131428172);
            return;
          }
          LbsActivityListFragment.this.showPoiList();
        }

        public void onLocationAvailable(Location paramLocation)
        {
          LbsActivityListFragment.this.currentLocation = paramLocation;
          LbsActivityListFragment.this.mLatitude = LbsActivityListFragment.this.getLatitude();
          LbsActivityListFragment.this.mLongitude = LbsActivityListFragment.this.getLongitude();
          LbsActivityListFragment.this.refreshData(0, true);
        }

        public void onLocationFailed()
        {
          Toast.makeText(LbsActivityListFragment.this.getActivity(), 2131427722, 0).show();
          LbsActivityListFragment.this.stopPullToRefresh();
          LbsActivityListFragment.this.showPoiList();
        }
      });
    return this.refreshLocationProxy;
  }

  private void onViewMoreClicked(View paramView)
  {
    if (paramView != this.mFooter)
      KXLog.d("Error!!!!", "mFooter");
    while (true)
    {
      return;
      LbsModel localLbsModel = LbsModel.getInstance();
      ArrayList localArrayList = localLbsModel.getActivityList();
      boolean bool = false;
      int i = this.mPoiList.size();
      if (i > 0)
      {
        PoiActivityItem localPoiActivityItem = (PoiActivityItem)this.mPoiList.get(i - 1);
        if ((localPoiActivityItem != null) && ("view_more".equals(localPoiActivityItem.mActivityId)))
          i--;
      }
      if ((localArrayList != null) && (i < localArrayList.size()))
      {
        updateList();
        showPoiList();
      }
      while ((localArrayList != null) && (localArrayList.size() < localLbsModel.getActivityTotal()))
      {
        showViewMore(bool);
        refreshData(localArrayList.size(), bool);
        return;
        bool = true;
      }
    }
  }

  private void refreshData(int paramInt, boolean paramBoolean)
  {
    if (!super.checkNetworkAndHint(paramBoolean))
    {
      stopPullToRefresh();
      showError(-1);
      showViewMore(false);
    }
    do
      return;
    while ((this.mGetActivityListTask != null) && (!this.mGetActivityListTask.isCancelled()) && (this.mGetActivityListTask.getStatus() != AsyncTask.Status.FINISHED));
    if ((TextUtils.isEmpty(this.mLongitude)) && (TextUtils.isEmpty(this.mLatitude)) && (TextUtils.isEmpty(this.mCellId)))
    {
      showError(2131428172);
      return;
    }
    if ((TextUtils.isEmpty(this.mLongitude)) || (TextUtils.isEmpty(this.mLatitude)))
      this.mCellId = getCellIdInfo();
    Location localLocation = LocationService.getLocationService().lastBestMapABCLocation;
    this.mGetActivityListTask = new GetLbsActivityTask(getActivity(), this.currentLocation, localLocation, paramInt, paramBoolean)
    {
      protected void onPostExecuteA(Integer paramInteger)
      {
        if (LbsActivityListFragment.this.isNeedReturn())
          return;
        LbsModel localLbsModel = LbsModel.getInstance();
        ArrayList localArrayList = localLbsModel.getActivityList();
        if (paramInteger.intValue() > 0);
        label149: 
        do
          try
          {
            View localView = LbsActivityListFragment.this.mFooter;
            int i = 0;
            if (localView != null)
              if (LbsActivityListFragment.this.mFooterProBar.getVisibility() != 0)
                break label149;
            for (i = 1; ; i = 0)
            {
              if ((this.val$start == 0) || (i != 0))
              {
                LbsActivityListFragment.this.updateList();
                if ((localArrayList != null) && (localArrayList.size() > 0) && (localArrayList.size() < localLbsModel.getActivityTotal()))
                {
                  LbsActivityListFragment.this.mGetActivityListTask = null;
                  LbsActivityListFragment.this.refreshData(localArrayList.size(), false);
                }
              }
              if (i != 0)
                LbsActivityListFragment.this.showViewMore(false);
              LbsActivityListFragment.this.showPoiList();
              LbsActivityListFragment.this.stopPullToRefresh();
              return;
            }
          }
          catch (Exception localException)
          {
            while (true)
              localException.printStackTrace();
          }
        while (!this.val$bTips);
        if (LbsActivityListFragment.this.mPoiList.size() > 0)
          LbsActivityListFragment.this.showPoiList();
        while (true)
        {
          Toast localToast = Toast.makeText(LbsActivityListFragment.this.getActivity(), 2131428256, 0);
          localToast.setGravity(16, 0, 0);
          localToast.show();
          break;
          LbsActivityListFragment.this.showError(-1);
        }
      }
    };
    GetLbsActivityTask localGetLbsActivityTask = this.mGetActivityListTask;
    Integer[] arrayOfInteger = new Integer[2];
    arrayOfInteger[0] = Integer.valueOf(paramInt);
    arrayOfInteger[1] = Integer.valueOf(50);
    localGetLbsActivityTask.execute(arrayOfInteger);
  }

  private void refreshLocation(boolean paramBoolean)
  {
    this.currentLocation = null;
    getRefreshLocationProxy().refreshLocation(paramBoolean);
  }

  private void setListView()
  {
    if (this.mHeaderView == null)
      this.mHeaderView = getActivity().getLayoutInflater().inflate(2130903195, null);
    TextView localTextView = (TextView)this.mHeaderView.findViewById(2131362953);
    Resources localResources = getResources();
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.valueOf(LbsModel.getInstance().getRewardTotal());
    localTextView.setText(localResources.getString(2131428253, arrayOfObject));
    this.mHeaderView.findViewById(2131362952).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        Intent localIntent = new Intent(LbsActivityListFragment.this.getActivity(), ActivityAwardsFragment.class);
        AnimationUtil.startFragment(LbsActivityListFragment.this, localIntent, 1);
      }
    });
    this.mListView = ((ListView)findViewById(2131363445));
    this.mListView.removeHeaderView(this.mHeaderView);
    this.mListView.addHeaderView(this.mHeaderView);
    this.mListView.setDivider(null);
    this.mListView.setDividerHeight(0);
    this.mListView.setAdapter(this.mAdapter);
    this.mListView.setOnItemClickListener(this);
  }

  private void setOtherViews()
  {
    findViewById(2131363444).setVisibility(8);
    this.mLayoutEmpty = findViewById(2131363102);
    this.mEmptyProBar = ((ProgressBar)findViewById(2131363446));
    this.mTxtEmpty = ((TextView)findViewById(2131361972));
    this.mFooter = getActivity().getLayoutInflater().inflate(2130903153, null);
    this.mFooterTV = ((TextView)this.mFooter.findViewById(2131362073));
    this.mFooterProBar = ((ProgressBar)this.mFooter.findViewById(2131362072));
    this.mFooterProBar.setVisibility(4);
    this.mFooterTV.setText(2131428258);
  }

  private void showError(int paramInt)
  {
    this.mListView.setVisibility(8);
    this.mRightProBar.setVisibility(8);
    this.mBtnRight.setImageResource(2130838834);
    if (paramInt < 0)
    {
      this.mLayoutEmpty.setVisibility(8);
      return;
    }
    this.mLayoutEmpty.setVisibility(0);
    this.mEmptyProBar.setVisibility(8);
    this.mTxtEmpty.setText(paramInt);
    this.mTxtEmpty.setVisibility(0);
  }

  private void showLoading(boolean paramBoolean)
  {
    if ((this.mPoiList.size() == 0) && (paramBoolean))
    {
      this.mListView.setVisibility(8);
      this.mLayoutEmpty.setVisibility(0);
      this.mEmptyProBar.setVisibility(0);
      this.mTxtEmpty.setVisibility(0);
      this.mTxtEmpty.setText(2131428254);
    }
    while (true)
    {
      this.mRightProBar.setVisibility(0);
      this.mBtnRight.setImageResource(0);
      return;
      this.mLayoutEmpty.setVisibility(8);
      this.mListView.setVisibility(0);
    }
  }

  private void showLocating()
  {
    this.mListView.setVisibility(8);
    this.mRightProBar.setVisibility(0);
    this.mBtnRight.setImageResource(0);
    this.mLayoutEmpty.setVisibility(0);
    this.mEmptyProBar.setVisibility(0);
    this.mTxtEmpty.setText(2131428174);
    this.mTxtEmpty.setVisibility(0);
  }

  private void showPoiList()
  {
    this.mLayoutEmpty.setVisibility(8);
    this.mListView.setVisibility(0);
    this.mRightProBar.setVisibility(8);
    this.mBtnRight.setImageResource(2130838834);
  }

  private void showViewMore(boolean paramBoolean)
  {
    ProgressBar localProgressBar = this.mFooterProBar;
    int i;
    int j;
    label31: boolean bool;
    label55: TextView localTextView3;
    Resources localResources;
    if (paramBoolean)
    {
      i = 0;
      localProgressBar.setVisibility(i);
      TextView localTextView1 = this.mFooterTV;
      if (!paramBoolean)
        break label101;
      j = 2131428257;
      localTextView1.setText(getString(j));
      TextView localTextView2 = this.mFooterTV;
      bool = false;
      if (!paramBoolean)
        break label109;
      localTextView2.setEnabled(bool);
      localTextView3 = this.mFooterTV;
      localResources = getResources();
      if (!paramBoolean)
        break label115;
    }
    label101: label109: label115: for (int k = 2130839395; ; k = 2130839419)
    {
      localTextView3.setTextColor(localResources.getColor(k));
      return;
      i = 4;
      break;
      j = 2131428258;
      break label31;
      bool = true;
      break label55;
    }
  }

  private void stopPullToRefresh()
  {
    if ((this.mDownView != null) && (this.mDownView.isFrefrshing()))
      this.mDownView.onRefreshComplete();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903291, paramViewGroup, false);
  }

  public void onDestroy()
  {
    cancelTask();
    this.mListView.setAdapter(null);
    this.mPoiList.clear();
    super.onDestroy();
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    try
    {
      PoiActivityItem localPoiActivityItem = (PoiActivityItem)this.mPoiList.get((int)paramLong);
      if (localPoiActivityItem != null)
      {
        if ("view_more".equals(localPoiActivityItem.mActivityId))
        {
          onViewMoreClicked(paramView);
          return;
        }
        IntentUtil.showLbsPositionDetailFragment(this, localPoiActivityItem.mPoiId, localPoiActivityItem.mPoiName, "", "");
        return;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void onResume()
  {
    super.onResume();
    getRefreshLocationProxy().reworkOnResume();
  }

  public void onUpdate()
  {
    refreshLocation(true);
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    Bundle localBundle = getArguments();
    if (localBundle != null)
    {
      this.mLongitude = localBundle.getString("lon");
      this.mLatitude = localBundle.getString("lat");
      this.mCellId = localBundle.getString("cell_id");
    }
    this.mDownView = ((PullToRefreshView)findViewById(2131361814));
    this.mDownView.setPullToRefreshListener(this);
    setOtherViews();
    setTitleBar();
    setListView();
    getRefreshLocationProxy().refreshLocation(false);
  }

  protected void setTitleBar()
  {
    ImageView localImageView = (ImageView)findViewById(2131362914);
    localImageView.setVisibility(0);
    localImageView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        LbsActivityListFragment.this.finish();
      }
    });
    this.mBtnRight = ((ImageView)findViewById(2131362928));
    this.mBtnRight.setImageResource(2130838834);
    this.mBtnRight.setVisibility(0);
    this.mBtnRight.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        if ((LbsActivityListFragment.this.mRightProBar != null) && (LbsActivityListFragment.this.mRightProBar.getVisibility() != 0))
        {
          LbsActivityListFragment.this.cancelTask();
          LbsActivityListFragment.this.refreshLocation(true);
        }
      }
    });
    ((ImageView)findViewById(2131362919)).setVisibility(8);
    TextView localTextView = (TextView)findViewById(2131362920);
    localTextView.setText(2131428251);
    localTextView.setVisibility(0);
    this.mRightProBar = ((ProgressBar)findViewById(2131362929));
  }

  void updateList()
  {
    this.mPoiList.clear();
    LbsModel localLbsModel = LbsModel.getInstance();
    TextView localTextView = (TextView)this.mHeaderView.findViewById(2131362953);
    int i = localLbsModel.getRewardTotal();
    if (i < 0)
      i = 0;
    Resources localResources = getResources();
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.valueOf(i);
    localTextView.setText(localResources.getString(2131428253, arrayOfObject));
    ArrayList localArrayList = localLbsModel.getActivityList();
    if ((localArrayList != null) && (localArrayList.size() > 0))
      this.mPoiList.addAll(localArrayList);
    if (this.mPoiList.size() < localLbsModel.getActivityTotal())
    {
      PoiActivityItem localPoiActivityItem = new PoiActivityItem();
      localPoiActivityItem.mActivityId = "view_more";
      this.mPoiList.add(localPoiActivityItem);
    }
    View localView = this.mHeaderView.findViewById(2131362955);
    if (localView != null)
    {
      boolean bool = this.mPoiList.isEmpty();
      int j = 0;
      if (bool)
        j = 8;
      localView.setVisibility(j);
    }
    this.mAdapter.notifyDataSetChanged();
  }

  private class PoiActivityItemCache
  {
    public ImageView mImgIcon;
    public View mLayout;
    public View mLayoutActivity;
    public TextView mTxtLocation;
    public TextView mTxtName;
    public TextView mTxtType;

    public PoiActivityItemCache(View arg2)
    {
      Object localObject;
      if (localObject == null)
        return;
      this.mLayout = localObject;
      this.mImgIcon = ((ImageView)localObject.findViewById(2131362957));
      this.mTxtName = ((TextView)localObject.findViewById(2131362008));
      this.mTxtLocation = ((TextView)localObject.findViewById(2131362009));
      this.mTxtType = ((TextView)localObject.findViewById(2131362958));
      this.mLayoutActivity = localObject.findViewById(2131362956);
    }

    public void showLbsActivityItem(PoiActivityItem paramPoiActivityItem, int paramInt)
    {
      int i = (int)(10.0F * LbsActivityListFragment.this.getResources().getDisplayMetrics().density);
      if (LbsActivityListFragment.this.mPoiList.size() == 1)
      {
        this.mLayoutActivity.setBackgroundResource(2130837701);
        this.mLayout.setPadding(i, i, i, i);
      }
      while (paramPoiActivityItem == null)
      {
        this.mImgIcon.setVisibility(4);
        this.mTxtName.setText(null);
        this.mTxtLocation.setText(null);
        return;
        if (paramInt == 0)
        {
          this.mLayoutActivity.setBackgroundResource(2130837702);
          this.mLayout.setPadding(i, i, i, 0);
          continue;
        }
        if (paramInt == -1 + LbsActivityListFragment.this.mPoiList.size())
        {
          this.mLayoutActivity.setBackgroundResource(2130837699);
          this.mLayout.setPadding(i, 0, i, i);
          continue;
        }
        this.mLayoutActivity.setBackgroundResource(2130837700);
        this.mLayout.setPadding(i, 0, i, 0);
      }
      this.mTxtName.setText(paramPoiActivityItem.mPoiName);
      this.mTxtLocation.setText(paramPoiActivityItem.mActivityName);
      this.mTxtType.setText(paramPoiActivityItem.mType);
    }
  }

  private class PoiListAdapter extends BaseAdapter
  {
    private PoiListAdapter()
    {
    }

    public int getCount()
    {
      return LbsActivityListFragment.this.mPoiList.size();
    }

    public Object getItem(int paramInt)
    {
      if ((paramInt < 0) || (paramInt >= LbsActivityListFragment.this.mPoiList.size()))
        return null;
      return LbsActivityListFragment.this.mPoiList.get(paramInt);
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      PoiActivityItem localPoiActivityItem = (PoiActivityItem)getItem(paramInt);
      if ("view_more".equals(localPoiActivityItem.mActivityId))
        return LbsActivityListFragment.this.mFooter;
      LbsActivityListFragment.PoiActivityItemCache localPoiActivityItemCache;
      if ((paramView == null) || (LbsActivityListFragment.this.mFooter == paramView))
      {
        paramView = LbsActivityListFragment.this.getActivity().getLayoutInflater().inflate(2130903197, null);
        localPoiActivityItemCache = new LbsActivityListFragment.PoiActivityItemCache(LbsActivityListFragment.this, paramView);
        paramView.setTag(localPoiActivityItemCache);
      }
      while (true)
      {
        if (localPoiActivityItem != null)
          localPoiActivityItemCache.showLbsActivityItem(localPoiActivityItem, paramInt);
        return paramView;
        localPoiActivityItemCache = (LbsActivityListFragment.PoiActivityItemCache)paramView.getTag();
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.LbsActivityListFragment
 * JD-Core Version:    0.6.0
 */