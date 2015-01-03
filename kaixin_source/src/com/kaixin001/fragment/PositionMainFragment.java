package com.kaixin001.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Location;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.os.Message;
import android.os.Vibrator;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.activity.OnViewMoreClickListener;
import com.kaixin001.adapter.CheckInAdapter;
import com.kaixin001.adapter.FriendsCheckInAdapter;
import com.kaixin001.adapter.PoiPhotoesItemAdapter;
import com.kaixin001.businesslogic.AddFriend;
import com.kaixin001.businesslogic.AddFriendResult;
import com.kaixin001.businesslogic.ShowMenuDialog;
import com.kaixin001.engine.CheckinListEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.item.CheckInItem;
import com.kaixin001.item.CheckInUser;
import com.kaixin001.item.PoiItem;
import com.kaixin001.item.PoiPhotoesItem;
import com.kaixin001.lbs.LocationService;
import com.kaixin001.lbs.RefreshLocationProxy;
import com.kaixin001.lbs.RefreshLocationProxy.IRefreshLocationCallback;
import com.kaixin001.model.CheckInInfoModel;
import com.kaixin001.model.KaixinModelTemplate;
import com.kaixin001.model.KaixinUser;
import com.kaixin001.model.LbsModel;
import com.kaixin001.model.User;
import com.kaixin001.task.GetLbsActivityTask;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.KXUBLog;
import com.kaixin001.util.UserHabitUtils;
import com.kaixin001.view.KXListView;
import com.kaixin001.view.KXTopTab;
import com.kaixin001.view.KXTopTabHost;
import com.kaixin001.view.KXTopTabHost.OnTabChangeListener;
import com.kaixin001.view.PullToRefreshView;
import com.kaixin001.view.PullToRefreshView.PullToRefreshListener;
import java.util.ArrayList;
import java.util.HashMap;

public class PositionMainFragment extends BaseFragment
  implements KXTopTabHost.OnTabChangeListener, View.OnClickListener, OnViewMoreClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, PullToRefreshView.PullToRefreshListener
{
  private static final int FETCH_NUM = 20;
  public static final int PHOTO_NUM = 20;
  private static final int STATE_DOWNLOADING = 1;
  private static final int STATE_FINSHED = 2;
  private static final int STATE_INITIAL = 0;
  private static final int TAB_FRIEND = 0;
  private static final int TAB_NUM = 3;
  private static final int TAB_PHOTO = 2;
  private static final int TAB_STRANGER = 1;
  private static final String TAG = "PositionMainFragment";
  private static final int TASK_FOR_LOAD = 1;
  private static final int TASK_FOR_PRELOAD;
  private int activityTab = 0;
  private FriendsCheckInAdapter adapterFriendsCheckin;
  private PoiPhotoesItemAdapter adapterPoiPhotoesItemAdapter;
  private CheckInAdapter adapterStrangerCheckin;
  HashMap<String, AddFriendResult> addFriendApplyChanges = new HashMap();
  private AddFriend addFriendUtil;
  private Button btnActivity;
  private ImageView btnLeft;
  private ImageView btnRight;
  private TextView centerText;
  Location currentLocation;
  private GetLbsActivityTask getActivityNumTask;
  private GetDataTask getDataTask;
  int[] idArrayNoData = { 2131428144, 2131428145, 2131428146 };
  private boolean isRefreshLocation;
  String lat;
  private LinearLayout llytWait;
  String lon;
  private KXListView lstvPosition;
  private BaseAdapter mCurAdapter;
  private View mHeaderView = null;
  private PullToRefreshView mPullView = null;
  private ShowMenuDialog menuUtil;
  private final ArrayList<CheckInItem> nearbyFriendPoiList = new ArrayList();
  private final ArrayList<CheckInItem> otherFriendPoiList = new ArrayList();
  private final ArrayList<PoiPhotoesItem> poiPhotoesItemList = new ArrayList();
  private RefreshLocationProxy refreshLocationProxy;
  private final ArrayList<CheckInItem> strangerPoiList = new ArrayList();
  private KXTopTabHost tabHost;
  private int[] tabIndexArray = new int[3];
  private int[] tabPrivStateArray = new int[3];
  private int[] tabStateArray = new int[3];
  private TextView tvNodata;
  private TextView tvWaiting;
  private TextView txtAward;

  private void forceRefresh()
  {
    onDownloading(true);
    refreshLocation(false);
  }

  private int getActivityState()
  {
    return this.tabStateArray[this.activityTab];
  }

  private void getFirstPageData(int paramInt)
  {
    onDownloading(true);
    this.getDataTask = new GetDataTask(null);
    GetDataTask localGetDataTask = this.getDataTask;
    Integer[] arrayOfInteger1 = new Integer[4];
    arrayOfInteger1[0] = Integer.valueOf(paramInt);
    arrayOfInteger1[1] = Integer.valueOf(1);
    arrayOfInteger1[2] = Integer.valueOf(0);
    arrayOfInteger1[3] = Integer.valueOf(20);
    localGetDataTask.execute(arrayOfInteger1);
    Location localLocation = LocationService.getLocationService().lastBestMapABCLocation;
    this.getActivityNumTask = new GetLbsActivityTask(getActivity(), this.currentLocation, localLocation)
    {
      protected void onPostExecuteA(Integer paramInteger)
      {
        if (PositionMainFragment.this.isNeedReturn())
          return;
        int i = LbsModel.getInstance().getActivityTotal();
        if ((paramInteger.intValue() > 0) && (i > 0))
        {
          new StringBuilder(String.valueOf(PositionMainFragment.this.getString(2131428251))).append("(").append(String.valueOf(i)).append(")").toString();
          TextView localTextView2 = PositionMainFragment.this.txtAward;
          Resources localResources2 = PositionMainFragment.this.getResources();
          Object[] arrayOfObject2 = new Object[1];
          arrayOfObject2[0] = Integer.valueOf(i);
          localTextView2.setText(localResources2.getString(2131428455, arrayOfObject2));
        }
        while (true)
        {
          PositionMainFragment.this.txtAward.setEnabled(true);
          return;
          TextView localTextView1 = PositionMainFragment.this.txtAward;
          Resources localResources1 = PositionMainFragment.this.getResources();
          Object[] arrayOfObject1 = new Object[1];
          arrayOfObject1[0] = Integer.valueOf(0);
          localTextView1.setText(localResources1.getString(2131428455, arrayOfObject1));
        }
      }
    };
    GetLbsActivityTask localGetLbsActivityTask = this.getActivityNumTask;
    Integer[] arrayOfInteger2 = new Integer[2];
    arrayOfInteger2[0] = Integer.valueOf(0);
    arrayOfInteger2[1] = Integer.valueOf(0);
    localGetLbsActivityTask.execute(arrayOfInteger2);
  }

  private void getNextPageData(int paramInt1, int paramInt2)
  {
    int i;
    int j;
    if (paramInt1 == 0)
    {
      i = -1 + this.otherFriendPoiList.size();
      j = 20;
      if ((this.getDataTask == null) || (this.getDataTask.getStatus() == AsyncTask.Status.FINISHED) || (this.getDataTask.start != i) || (this.getDataTask.taskPurpose != 0))
        break label123;
      this.getDataTask.taskPurpose = 1;
    }
    while (true)
    {
      if (this.getDataTask.taskPurpose == 1)
        onDownloading(true);
      return;
      if (paramInt1 == 1)
      {
        i = -1 + this.strangerPoiList.size();
        j = 20;
        break;
      }
      i = -1 + this.poiPhotoesItemList.size();
      j = 20;
      break;
      label123: this.getDataTask = new GetDataTask(null);
      GetDataTask localGetDataTask = this.getDataTask;
      Integer[] arrayOfInteger = new Integer[4];
      arrayOfInteger[0] = Integer.valueOf(paramInt1);
      arrayOfInteger[1] = Integer.valueOf(paramInt2);
      arrayOfInteger[2] = Integer.valueOf(i);
      arrayOfInteger[3] = Integer.valueOf(j);
      localGetDataTask.execute(arrayOfInteger);
    }
  }

  private RefreshLocationProxy getRefreshLocationProxy()
  {
    if (this.refreshLocationProxy == null)
      this.refreshLocationProxy = new RefreshLocationProxy(getActivity(), new RefreshLocationProxy.IRefreshLocationCallback()
      {
        public void onBeginRefreshLocation()
        {
          if (!PositionMainFragment.this.hasDataInList())
          {
            PositionMainFragment.this.tvWaiting.setText(2131428147);
            PositionMainFragment.this.llytWait.setVisibility(0);
            PositionMainFragment.this.tvNodata.setVisibility(8);
            PositionMainFragment.this.lstvPosition.setVisibility(8);
          }
          PositionMainFragment.this.isRefreshLocation = true;
        }

        public void onCancelRefreshLocation()
        {
          PositionMainFragment.this.isRefreshLocation = false;
          PositionMainFragment.this.onDownloading(false);
        }

        public void onLocationAvailable(Location paramLocation)
        {
          PositionMainFragment.this.isRefreshLocation = false;
          PositionMainFragment.this.llytWait.setVisibility(8);
          PositionMainFragment.this.currentLocation = paramLocation;
          for (int i = 0; ; i++)
          {
            if (i >= 3)
            {
              PositionMainFragment.this.resumeActivity();
              return;
            }
            PositionMainFragment.this.tabStateArray[i] = 0;
            PositionMainFragment.this.tabPrivStateArray[i] = 0;
          }
        }

        public void onLocationFailed()
        {
          PositionMainFragment.this.llytWait.setVisibility(8);
          PositionMainFragment.this.isRefreshLocation = false;
          PositionMainFragment.this.currentLocation = null;
          PositionMainFragment.this.resumeActivity();
          Toast.makeText(PositionMainFragment.this.getActivity(), 2131427722, 0).show();
        }
      });
    return this.refreshLocationProxy;
  }

  private boolean hasDataInList()
  {
    if (this.activityTab == 0)
      return this.nearbyFriendPoiList.size() + this.otherFriendPoiList.size() > 0;
    if (this.activityTab == 1)
      return this.strangerPoiList.size() > 0;
    return this.poiPhotoesItemList.size() > 0;
  }

  private void initTabHost(View paramView)
  {
    this.tabHost = ((KXTopTabHost)paramView.findViewById(2131363469));
    this.tabHost.setVisibility(0);
    this.tabHost.setOnTabChangeListener(this);
    KXTopTab localKXTopTab1 = new KXTopTab(getActivity());
    localKXTopTab1.setText(2131428138);
    this.tabHost.addTab(localKXTopTab1);
    KXTopTab localKXTopTab2 = new KXTopTab(getActivity());
    localKXTopTab2.setText(2131428139);
    this.tabHost.addTab(localKXTopTab2);
    KXTopTab localKXTopTab3 = new KXTopTab(getActivity());
    localKXTopTab3.setText(2131428140);
    this.tabHost.addTab(localKXTopTab3);
    Bundle localBundle = getArguments();
    if (localBundle != null)
    {
      int i = localBundle.getInt("CurrentTab");
      if (i == 1)
      {
        this.tabHost.setCurrentTab(1);
        return;
      }
      if (i == 2)
      {
        this.tabHost.setCurrentTab(2);
        return;
      }
      this.tabHost.setCurrentTab(this.activityTab);
      return;
    }
    this.tabHost.setCurrentTab(this.activityTab);
  }

  private void initTitle(View paramView)
  {
    this.btnLeft = ((ImageView)paramView.findViewById(2131362914));
    this.btnLeft.setOnClickListener(this);
    ImageView localImageView = (ImageView)paramView.findViewById(2131362919);
    this.centerText = ((TextView)paramView.findViewById(2131362920));
    this.centerText.setVisibility(0);
    this.centerText.setText(2131428136);
    localImageView.setVisibility(8);
    this.btnRight = ((ImageView)paramView.findViewById(2131362928));
    this.btnRight.setImageResource(2130839021);
    this.btnRight.setOnClickListener(this);
  }

  private void loadCache()
  {
    if (this.activityTab == 0)
    {
      ArrayList localArrayList3 = CheckinListEngine.getInstance().loadCheckInItemCache(getActivity(), User.getInstance().getUID(), 0);
      if (localArrayList3 != null)
        this.nearbyFriendPoiList.addAll(localArrayList3);
      ArrayList localArrayList4 = CheckinListEngine.getInstance().loadCheckInItemCache(getActivity(), User.getInstance().getUID(), 1);
      if (localArrayList4 != null)
        this.otherFriendPoiList.addAll(localArrayList4);
    }
    while (true)
    {
      return;
      if (this.activityTab == 1)
      {
        ArrayList localArrayList2 = CheckinListEngine.getInstance().loadCheckInItemCache(getActivity(), User.getInstance().getUID(), 2);
        if (localArrayList2 == null)
          continue;
        this.strangerPoiList.addAll(localArrayList2);
        return;
      }
      try
      {
        ArrayList localArrayList1 = CheckinListEngine.getInstance().loadCheckInPhotoCache(getActivity(), User.getInstance().getUID());
        if (localArrayList1 == null)
          continue;
        this.poiPhotoesItemList.addAll(localArrayList1);
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }

  private void loadCacheIfNeeded()
  {
    if ((getActivityState() == 0) && (!hasDataInList()))
      new KXFragment.KXAsyncTask(this)
      {
        protected Void doInBackgroundA(Void[] paramArrayOfVoid)
        {
          if (PositionMainFragment.this.isNeedReturn())
            return null;
          PositionMainFragment.this.loadCache();
          return null;
        }

        protected void onPostExecuteA(Void paramVoid)
        {
          if (PositionMainFragment.this.isNeedReturn())
            return;
          PositionMainFragment.this.stateInitOnCreate();
        }

        protected void onPreExecuteA()
        {
        }

        protected void onProgressUpdateA(Void[] paramArrayOfVoid)
        {
        }
      }
      .execute(new Void[0]);
  }

  private void onDownloading(boolean paramBoolean)
  {
  }

  private void refreshLocation(boolean paramBoolean)
  {
    this.currentLocation = null;
    getRefreshLocationProxy().refreshLocation(paramBoolean);
  }

  private void resumeActivity()
  {
    loadCacheIfNeeded();
    if (this.currentLocation == null)
      if (this.isRefreshLocation)
        if (!hasDataInList())
        {
          this.llytWait.setVisibility(0);
          this.tvWaiting.setText(2131428147);
          this.tvNodata.setVisibility(8);
          this.lstvPosition.setVisibility(8);
        }
    do
    {
      do
      {
        return;
        onDownloading(false);
      }
      while (hasDataInList());
      this.llytWait.setVisibility(8);
      this.tvNodata.setText(2131428148);
      this.tvNodata.setVisibility(0);
      this.lstvPosition.setVisibility(8);
      return;
      int i = this.tabIndexArray[this.activityTab];
      this.lstvPosition.setSelection(i);
      if (getActivityState() != 0)
        continue;
      setActivityState(1);
      stateInitOnCreate();
      getFirstPageData(this.activityTab);
      return;
    }
    while (getActivityState() != 2);
    setStateOnFetchFinished(true);
  }

  private void setActivityState(int paramInt)
  {
    this.tabPrivStateArray[this.activityTab] = this.tabStateArray[this.activityTab];
    this.tabStateArray[this.activityTab] = paramInt;
  }

  private void setStateOnFetchFinished(boolean paramBoolean)
  {
    showLoadingFooter(false);
    onDownloading(false);
    this.llytWait.setVisibility(8);
    int i;
    if (this.activityTab == 0)
      i = this.nearbyFriendPoiList.size() + this.otherFriendPoiList.size();
    while (paramBoolean)
    {
      setActivityState(2);
      this.tabStateArray[this.activityTab] = 2;
      if (i == 0)
      {
        this.tvNodata.setVisibility(0);
        this.tvNodata.setText(getString(this.idArrayNoData[this.activityTab]));
        this.lstvPosition.setVisibility(8);
        return;
        if (this.activityTab == 1)
        {
          i = this.strangerPoiList.size();
          continue;
        }
        i = this.poiPhotoesItemList.size();
        continue;
      }
      BaseAdapter[] arrayOfBaseAdapter = new BaseAdapter[3];
      arrayOfBaseAdapter[0] = this.adapterFriendsCheckin;
      arrayOfBaseAdapter[1] = this.adapterStrangerCheckin;
      arrayOfBaseAdapter[2] = this.adapterPoiPhotoesItemAdapter;
      this.tvNodata.setVisibility(8);
      arrayOfBaseAdapter[this.activityTab].notifyDataSetChanged();
      this.lstvPosition.setVisibility(0);
      return;
    }
    this.tabStateArray[this.activityTab] = this.tabPrivStateArray[this.activityTab];
    if (i == 0)
    {
      this.tvNodata.setVisibility(8);
      this.lstvPosition.setVisibility(8);
      return;
    }
    this.tvNodata.setVisibility(8);
    this.lstvPosition.setVisibility(0);
  }

  private void stateInitOnCreate()
  {
    if (hasDataInList())
    {
      this.tvNodata.setVisibility(8);
      this.lstvPosition.setVisibility(0);
      this.llytWait.setVisibility(8);
      return;
    }
    this.tvNodata.setVisibility(8);
    this.lstvPosition.setVisibility(8);
    this.tvWaiting.setText(2131427599);
    this.llytWait.setVisibility(0);
  }

  private void stopActivity()
  {
    this.tabIndexArray[this.activityTab] = this.lstvPosition.getFirstVisiblePosition();
    if (getActivityState() == 1)
    {
      setActivityState(this.tabPrivStateArray[this.activityTab]);
      this.getDataTask.cancel(true);
      return;
    }
    getActivityState();
  }

  private void updateDataList(int paramInt)
  {
    int i = 1;
    int j = this.lstvPosition.getFirstVisiblePosition();
    if (paramInt == 0)
    {
      ArrayList localArrayList3 = CheckInInfoModel.getInstance().nearbyFriendCheckin.getItemList();
      this.nearbyFriendPoiList.clear();
      int i1;
      ArrayList localArrayList4;
      int i3;
      if (localArrayList3 == null)
      {
        i1 = 0;
        if (i1 > 0)
        {
          this.nearbyFriendPoiList.addAll(localArrayList3);
          this.lstvPosition.setVisibility(0);
        }
        localArrayList4 = CheckInInfoModel.getInstance().otherFriendCheckin.getItemList();
        int i2 = CheckInInfoModel.getInstance().otherFriendCheckin.total;
        this.otherFriendPoiList.clear();
        if (localArrayList4 != null)
          break label180;
        i3 = 0;
        label100: if (i3 > 0)
          this.otherFriendPoiList.addAll(localArrayList4);
        if (this.otherFriendPoiList.size() >= i2)
          break label190;
      }
      while (true)
      {
        if (i != 0)
        {
          CheckInItem localCheckInItem2 = new CheckInItem();
          localCheckInItem2.checkInUser = null;
          this.otherFriendPoiList.add(localCheckInItem2);
          getNextPageData(paramInt, 0);
        }
        this.adapterFriendsCheckin.notifyDataSetChanged();
        return;
        i1 = localArrayList3.size();
        break;
        label180: i3 = localArrayList4.size();
        break label100;
        label190: i = 0;
      }
    }
    if (paramInt == i)
    {
      ArrayList localArrayList2 = CheckInInfoModel.getInstance().strangerCheckin.getItemList();
      int n = CheckInInfoModel.getInstance().strangerCheckin.total;
      this.strangerPoiList.clear();
      if (localArrayList2 != null)
        this.strangerPoiList.addAll(localArrayList2);
      if (this.strangerPoiList.size() < n)
      {
        if (i != 0)
        {
          CheckInItem localCheckInItem1 = new CheckInItem();
          localCheckInItem1.checkInUser = null;
          this.strangerPoiList.add(localCheckInItem1);
          getNextPageData(paramInt, 0);
        }
        this.adapterStrangerCheckin.notifyDataSetChanged();
        if (j >= this.strangerPoiList.size())
          break label323;
      }
      while (true)
      {
        this.lstvPosition.setSelection(j);
        return;
        i = 0;
        break;
        label323: j = 0;
      }
    }
    ArrayList localArrayList1 = CheckInInfoModel.getInstance().poiPhotoesList.getItemList();
    int k = CheckInInfoModel.getInstance().poiPhotoesList.total;
    this.poiPhotoesItemList.clear();
    int m;
    if (localArrayList1 == null)
    {
      m = 0;
      if (m > 0)
        this.poiPhotoesItemList.addAll(localArrayList1);
      if (this.poiPhotoesItemList.size() >= k)
        break label464;
      label392: if (i != 0)
      {
        PoiPhotoesItem localPoiPhotoesItem = new PoiPhotoesItem();
        localPoiPhotoesItem.poi = null;
        this.poiPhotoesItemList.add(localPoiPhotoesItem);
        getNextPageData(paramInt, 0);
      }
      this.adapterPoiPhotoesItemAdapter.notifyDataSetChanged();
      if (j >= this.poiPhotoesItemList.size())
        break label469;
    }
    while (true)
    {
      this.lstvPosition.setSelection(j);
      return;
      m = localArrayList1.size();
      break;
      label464: i = 0;
      break label392;
      label469: j = 0;
    }
  }

  public void beforeTabChanged(int paramInt)
  {
    if (paramInt == -1)
      return;
    this.tabIndexArray[paramInt] = this.lstvPosition.getFirstVisiblePosition();
    stopActivity();
    showLoadingFooter(false);
  }

  protected void dealPhotoResult(String paramString1, String paramString2)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("filePathName", paramString1);
    IntentUtil.launchEditPhotoForResult(getActivity(), this, 105, localBundle);
  }

  public AddFriend getAddFriendUtil()
  {
    if (this.addFriendUtil == null)
    {
      this.addFriendUtil = new AddFriend(this, this.mHandler);
      this.addFriendUtil.setAddFriendApplyChanges(this.addFriendApplyChanges);
    }
    return this.addFriendUtil;
  }

  public ShowMenuDialog getShowMenuDialog()
  {
    if (this.menuUtil == null)
      this.menuUtil = new ShowMenuDialog(this);
    return this.menuUtil;
  }

  protected boolean handleMessage(Message paramMessage)
  {
    if (paramMessage == null)
      return true;
    if (paramMessage.what == 113)
    {
      String str = (String)paramMessage.obj;
      getAddFriendUtil().addNewFriendResult(str);
      return true;
    }
    return super.handleMessage(paramMessage);
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if (paramInt2 == -1)
    {
      if (paramInt1 != 11)
        break label67;
      if (paramIntent != null)
      {
        Bundle localBundle2 = paramIntent.getExtras();
        IntentUtil.showLbsPositionDetailFragment(this, localBundle2.getString("poiid"), localBundle2.getString("poiname"), "", localBundle2.getString("location"));
      }
    }
    while (true)
    {
      this.adapterStrangerCheckin.notifyDataSetChanged();
      return;
      label67: if (paramInt1 == 12)
      {
        this.adapterPoiPhotoesItemAdapter.updatePoiPhotoes(this.adapterPoiPhotoesItemAdapter.currentCheckInPoiItem);
        continue;
      }
      if (paramInt1 == 99)
      {
        Bundle localBundle1 = paramIntent.getExtras();
        getAddFriendUtil().addNewFriendResult(localBundle1.getString("fuid"));
        continue;
      }
      if (paramInt1 != 105)
        continue;
      try
      {
        PoiPhotoesItem localPoiPhotoesItem = this.adapterPoiPhotoesItemAdapter.currentCheckInPoiItem;
        IntentUtil.showCheckInFragment(getActivity(), this, localPoiPhotoesItem.poi.poiId, localPoiPhotoesItem.poi.name, localPoiPhotoesItem.poi.location, this.lon, this.lat, "hasdata", true);
      }
      catch (Exception localException)
      {
        KXLog.e("PositionMainActivity", "onActivityResult", localException);
      }
    }
  }

  public void onClick(View paramView)
  {
    if (paramView.getId() == 2131362914)
    {
      Bundle localBundle = getArguments();
      if ((localBundle != null) && (localBundle.getString("from") != null) && (localBundle.getString("from").equals("KaixinMenuListFragment")))
        if (super.isMenuListVisibleInMain())
          super.showSubActivityInMain();
    }
    do
    {
      return;
      super.showMenuListInMain();
      return;
      finish();
      return;
      if (paramView != this.btnRight)
        continue;
      if (this.currentLocation == null)
        IntentUtil.showPoiListFragment(this, null, null);
      while (true)
      {
        UserHabitUtils.getInstance(getActivity()).addUserHabit("Location_WantCheckIn");
        return;
        String str2 = String.valueOf(this.currentLocation.getLatitude());
        IntentUtil.showPoiListFragment(this, String.valueOf(this.currentLocation.getLongitude()), str2);
      }
    }
    while (paramView != this.btnActivity);
    if (this.currentLocation == null)
    {
      IntentUtil.showLbsActivityListFragment(this, null, null);
      return;
    }
    String str1 = String.valueOf(this.currentLocation.getLatitude());
    IntentUtil.showLbsActivityListFragment(this, String.valueOf(this.currentLocation.getLongitude()), str1);
  }

  public void onCreate(Bundle paramBundle)
  {
    this.activityTab = 0;
    for (int i = 0; ; i++)
    {
      if (i >= 3)
      {
        super.onCreate(paramBundle);
        getActivity().getWindow().setFlags(3, 3);
        UserHabitUtils.getInstance(getActivity()).addUserHabit("visit_location_friend");
        return;
      }
      this.tabStateArray[i] = 0;
      this.tabPrivStateArray[i] = 0;
    }
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903299, paramViewGroup, false);
  }

  public void onDestroy()
  {
    if ((this.getDataTask != null) && (this.getDataTask.getStatus() != AsyncTask.Status.FINISHED))
      this.getDataTask.cancel(true);
    if ((this.getActivityNumTask != null) && (this.getActivityNumTask.getStatus() != AsyncTask.Status.FINISHED))
      this.getActivityNumTask.cancel(true);
    CheckInInfoModel.getInstance().clear();
    super.onDestroy();
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    if (paramLong < 0L);
    while (true)
    {
      return;
      if (this.activityTab != 2)
        break;
      if (((PoiPhotoesItem)this.lstvPosition.getItemAtPosition(paramInt)).poi != null)
        continue;
      onViewMoreClick();
      return;
    }
    CheckInItem localCheckInItem = (CheckInItem)this.lstvPosition.getItemAtPosition(paramInt);
    if (localCheckInItem.checkInUser == null)
    {
      onViewMoreClick();
      return;
    }
    IntentUtil.showLbsCheckInCommentFragment(this, localCheckInItem);
  }

  public boolean onItemLongClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    if (this.activityTab == 2);
    CheckInItem localCheckInItem;
    do
    {
      return true;
      try
      {
        Vibrator localVibrator = (Vibrator)getActivity().getSystemService("vibrator");
        localVibrator.vibrate(1000L);
        localVibrator.vibrate(new long[] { 100L, 300L, 150L, 400L }, -1);
        localCheckInItem = (CheckInItem)this.lstvPosition.getItemAtPosition(paramInt);
        if (this.activityTab != 0)
          continue;
        getShowMenuDialog().showMenuOfFriendCheckInItem(localCheckInItem);
        return true;
      }
      catch (Exception localException)
      {
        KXLog.e("PositionMainFragment", "onListItemClick", localException);
        return true;
      }
    }
    while (this.activityTab != 1);
    String str = localCheckInItem.checkInUser.user.uid;
    AddFriendResult localAddFriendResult = (AddFriendResult)this.addFriendApplyChanges.get(str);
    if ((localAddFriendResult == null) || (localAddFriendResult.code == 0))
    {
      getShowMenuDialog().showMenuOfStrangerCheckInItem(localCheckInItem, getAddFriendUtil());
      return true;
    }
    getShowMenuDialog().showMenuOfFriendCheckInItem(localCheckInItem);
    return true;
  }

  public void onResume()
  {
    if (!getRefreshLocationProxy().reworkOnResume())
      resumeActivity();
    super.onResume();
  }

  public void onStop()
  {
    super.onStop();
    stopActivity();
  }

  public void onTabChanged(int paramInt)
  {
    if (paramInt == 0)
    {
      this.activityTab = 0;
      this.lstvPosition.setAdapter(this.adapterFriendsCheckin);
      this.adapterFriendsCheckin.notifyDataSetChanged();
      this.mCurAdapter = this.adapterFriendsCheckin;
      UserHabitUtils.getInstance(getActivity()).addUserHabit("visit_location_friend");
      UserHabitUtils.getInstance(getActivity()).addUserHabit("Location_FridCheckIn");
    }
    while (true)
    {
      resumeActivity();
      return;
      if (paramInt == 1)
      {
        this.activityTab = 1;
        this.lstvPosition.setAdapter(this.adapterStrangerCheckin);
        this.adapterStrangerCheckin.notifyDataSetChanged();
        this.mCurAdapter = this.adapterStrangerCheckin;
        UserHabitUtils.getInstance(getActivity()).addUserHabit("visit_location_nearpeople");
        UserHabitUtils.getInstance(getActivity()).addUserHabit("Location_PeopleAround");
        continue;
      }
      this.activityTab = 2;
      this.lstvPosition.setAdapter(this.adapterPoiPhotoesItemAdapter);
      this.adapterPoiPhotoesItemAdapter.notifyDataSetChanged();
      this.mCurAdapter = this.adapterPoiPhotoesItemAdapter;
      UserHabitUtils.getInstance(getActivity()).addUserHabit("visit_location_nearpic");
      UserHabitUtils.getInstance(getActivity()).addUserHabit("Location_PictureAround");
    }
  }

  public void onUpdate()
  {
    forceRefresh();
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    if (this.mHeaderView == null)
      this.mHeaderView = getActivity().getLayoutInflater().inflate(2130903198, null);
    this.txtAward = ((TextView)this.mHeaderView.findViewById(2131362953));
    TextView localTextView = this.txtAward;
    Resources localResources = getResources();
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.valueOf(LbsModel.getInstance().getActivityTotal());
    localTextView.setText(localResources.getString(2131428455, arrayOfObject));
    this.mHeaderView.findViewById(2131362952).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        if (PositionMainFragment.this.currentLocation == null)
          IntentUtil.showLbsActivityListFragment(PositionMainFragment.this, null, null);
        while (true)
        {
          UserHabitUtils.getInstance(PositionMainFragment.this.getActivity()).addUserHabit("Location_ShopCheckIn");
          return;
          String str1 = String.valueOf(PositionMainFragment.this.currentLocation.getLatitude());
          String str2 = String.valueOf(PositionMainFragment.this.currentLocation.getLongitude());
          IntentUtil.showLbsActivityListFragment(PositionMainFragment.this, str2, str1);
        }
      }
    });
    if (this.adapterFriendsCheckin == null)
    {
      this.adapterFriendsCheckin = new FriendsCheckInAdapter(this, this.nearbyFriendPoiList, this.otherFriendPoiList);
      this.adapterStrangerCheckin = new CheckInAdapter(this, 2130903298, this.strangerPoiList);
      this.adapterPoiPhotoesItemAdapter = new PoiPhotoesItemAdapter(this, 2130903294, this.poiPhotoesItemList);
      this.mCurAdapter = this.adapterFriendsCheckin;
    }
    this.lstvPosition = ((KXListView)paramView.findViewById(2131363470));
    this.lstvPosition.addHeaderView(this.mHeaderView);
    this.lstvPosition.setAdapter(this.mCurAdapter);
    this.lstvPosition.setOnItemClickListener(this);
    this.lstvPosition.setOnItemLongClickListener(this);
    this.tvNodata = ((TextView)paramView.findViewById(2131361816));
    this.llytWait = ((LinearLayout)paramView.findViewById(2131361817));
    this.tvWaiting = ((TextView)paramView.findViewById(2131361819));
    initTitle(paramView);
    initTabHost(paramView);
    this.mPullView = ((PullToRefreshView)paramView.findViewById(2131361814));
    this.mPullView.setPullToRefreshListener(this);
    if (!dataInited())
      getRefreshLocationProxy().refreshLocation(false);
    KXUBLog.log("homepage_local");
  }

  public void onViewMoreClick()
  {
    if (this.currentLocation == null)
    {
      refreshLocation(false);
      return;
    }
    int k;
    if (this.activityTab == 0)
    {
      int i1 = CheckInInfoModel.getInstance().otherFriendCheckin.getItemList().size();
      int i2 = -1 + this.otherFriendPoiList.size();
      k = 0;
      if (i1 > i2)
        k = 1;
    }
    while (k != 0)
    {
      updateDataList(this.activityTab);
      return;
      if (this.activityTab == 1)
      {
        int m = CheckInInfoModel.getInstance().strangerCheckin.getItemList().size();
        int n = -1 + this.strangerPoiList.size();
        k = 0;
        if (m > n)
          k = 1;
        continue;
      }
      int i = CheckInInfoModel.getInstance().poiPhotoesList.getItemList().size();
      int j = -1 + this.poiPhotoesItemList.size();
      k = 0;
      if (i > j)
        k = 1;
    }
    showLoadingFooter(true);
    onDownloading(true);
    getNextPageData(this.activityTab, 1);
  }

  public void showLoadingFooter(boolean paramBoolean)
  {
    if (this.activityTab == 0)
    {
      this.adapterFriendsCheckin.showLoadingFooter(paramBoolean);
      return;
    }
    if (this.activityTab == 1)
    {
      this.adapterStrangerCheckin.showLoadingFooter(paramBoolean);
      return;
    }
    this.adapterPoiPhotoesItemAdapter.showLoadingFooter(paramBoolean);
  }

  private class GetDataTask extends KXFragment.KXAsyncTask<Integer, Void, Integer>
  {
    private int number;
    private int start;
    public int taskPurpose;
    private int type;

    private GetDataTask()
    {
      super();
    }

    protected Integer doInBackgroundA(Integer[] paramArrayOfInteger)
    {
      Integer localInteger1;
      if ((paramArrayOfInteger == null) || (paramArrayOfInteger.length == 0) || (paramArrayOfInteger.length != 4))
        localInteger1 = null;
      while (true)
      {
        return localInteger1;
        if (PositionMainFragment.this.currentLocation == null)
          return null;
        this.type = paramArrayOfInteger[0].intValue();
        this.taskPurpose = paramArrayOfInteger[1].intValue();
        this.start = paramArrayOfInteger[2].intValue();
        this.number = paramArrayOfInteger[3].intValue();
        Location localLocation = LocationService.getLocationService().lastBestMapABCLocation;
        String str1;
        String str2;
        String str3;
        String str4;
        if (LocationService.isMapABCLocation(PositionMainFragment.this.currentLocation))
        {
          str1 = "0";
          str2 = "0";
          str3 = String.valueOf(PositionMainFragment.this.currentLocation.getLongitude());
          str4 = String.valueOf(PositionMainFragment.this.currentLocation.getLatitude());
        }
        try
        {
          if (this.type == 0)
          {
            if (this.start == 0)
            {
              int i = CheckinListEngine.getInstance().doGetNearbyFriendCheckInList(PositionMainFragment.this.getActivity().getApplicationContext(), str2, str1, str3, str4, this.start, this.number);
              if (i != 1)
              {
                Integer localInteger3 = Integer.valueOf(i);
                return localInteger3;
                str1 = String.valueOf(PositionMainFragment.this.currentLocation.getLongitude());
                str2 = String.valueOf(PositionMainFragment.this.currentLocation.getLatitude());
                if (localLocation == null)
                {
                  str3 = null;
                  label230: if (localLocation != null)
                    break label252;
                }
                label252: for (str4 = null; ; str4 = String.valueOf(localLocation.getLatitude()))
                {
                  break;
                  str3 = String.valueOf(localLocation.getLongitude());
                  break label230;
                }
              }
            }
            return Integer.valueOf(CheckinListEngine.getInstance().doGetOtherFriendCheckInList(PositionMainFragment.this.getActivity().getApplicationContext(), str2, str1, str3, str4, this.start, this.number));
          }
          if (this.type == 1)
            return Integer.valueOf(CheckinListEngine.getInstance().doGetStrangeCheckInList(PositionMainFragment.this.getActivity().getApplicationContext(), str2, str1, str3, str4, this.start, this.number));
          KaixinModelTemplate localKaixinModelTemplate = new KaixinModelTemplate();
          localInteger1 = CheckinListEngine.getInstance().getPois(PositionMainFragment.this.getActivity().getApplicationContext(), str2, str1, str3, str4, null, "101", this.start, this.number, localKaixinModelTemplate);
          if (localInteger1.intValue() != 1)
            continue;
          if (localKaixinModelTemplate.getItemList().isEmpty())
            return Integer.valueOf(1);
          Integer localInteger2 = CheckinListEngine.getInstance().getPoisPhotoes(PositionMainFragment.this.getActivity().getApplicationContext(), localKaixinModelTemplate, this.start, 20);
          return localInteger2;
        }
        catch (SecurityErrorException localSecurityErrorException)
        {
        }
      }
      return null;
    }

    protected void onCancelledA()
    {
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      if ((paramInteger == null) || (PositionMainFragment.this.getView() == null) || (PositionMainFragment.this.getActivity() == null));
      label117: 
      do
      {
        while (true)
        {
          return;
          if (PositionMainFragment.this.mPullView.isFrefrshing())
            PositionMainFragment.this.mPullView.onRefreshComplete();
          if (PositionMainFragment.this.activityTab == this.type);
          for (int i = 1; (this.taskPurpose != 0) && (i != 0); i = 0)
          {
            if ((paramInteger != null) && (paramInteger.intValue() == 1))
              break label117;
            Toast.makeText(PositionMainFragment.this.getActivity(), 2131427374, 0).show();
            PositionMainFragment.this.setStateOnFetchFinished(false);
            return;
          }
        }
        PositionMainFragment.this.updateDataList(PositionMainFragment.this.activityTab);
        PositionMainFragment.this.setStateOnFetchFinished(true);
      }
      while (this.start != 0);
      PositionMainFragment.this.lstvPosition.setSelection(0);
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
 * Qualified Name:     com.kaixin001.fragment.PositionMainFragment
 * JD-Core Version:    0.6.0
 */