package com.kaixin001.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.activity.CloudAlbumSettingActivity;
import com.kaixin001.adapter.AlbumlistAdapter;
import com.kaixin001.businesslogic.ShowPhoto;
import com.kaixin001.engine.AlbumListEngine;
import com.kaixin001.engine.FriendPhotoEngine;
import com.kaixin001.engine.PhotographEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.engine.UploadTaskListEngine;
import com.kaixin001.item.AlbumInfo;
import com.kaixin001.item.FriendPhotoInfo;
import com.kaixin001.model.AlbumListModel;
import com.kaixin001.model.FriendPhotoModel;
import com.kaixin001.model.PhotographModel;
import com.kaixin001.model.User;
import com.kaixin001.util.CrashRecoverUtil;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.KXUBLog;
import com.kaixin001.util.ScreenUtil;
import com.kaixin001.util.UserHabitUtils;
import com.kaixin001.view.KXNewsBarForPictureView;
import com.kaixin001.view.KXTopTab;
import com.kaixin001.view.KXTopTabHost;
import com.kaixin001.view.KXTopTabHost.OnTabChangeListener;
import java.io.File;
import java.util.ArrayList;

public class PicturesFragment extends BaseFragment
  implements KXTopTabHost.OnTabChangeListener, View.OnClickListener, AdapterView.OnItemClickListener, CloudAlbumFragment.OnRefreshCloudListener
{
  private static final String FROM_WEBPAGE = "from_webpage";
  private static final int MENU_DESKTOP_ID = 403;
  private static final int MENU_HOME_ME_ID = 404;
  private static final int MENU_REFRESH_ID = 401;
  private static final int MENU_TOP_ID = 402;
  private static final int REFRESH_FRIEND_PHOTO_LIST = 300;
  private static final int STATE_DOWNLOADING = 1;
  private static final int STATE_FINSHED = 2;
  private static final int STATE_INITIAL = 0;
  public static final int TAB_CLOUD_ALBUM = 2;
  public static final int TAB_FRIEND_PHOTO = 0;
  public static final int TAB_MY_ALBUMLIST = 1;
  private static final int TAB_NUM = 3;
  public static final String TAG = "PicturesActivity";
  private boolean bHaveMore = false;
  private ImageView btnLeft;
  private CloudAlbumFragment cloudFragment;
  private FragmentManager fm;
  private boolean haveFooterView = false;
  private PhotoListAdapter mAdapter;
  private AlbumlistAdapter mAlbumListAdapter;
  private ArrayList<AlbumInfo> mAlbumListData = new ArrayList();
  private ImageView mBtnTitleRefresh;
  private ImageView mBtnTitleUploadOpt;
  private int mCurTabIndex = -1;
  private View mFooterProBar;
  private View mFooterView;
  private GetAlbumlistTask mGetAlbumlistTask;
  private GetDataTask mGetDataTask;
  private GetDataTask mGetMoreDataTask;
  private ArrayList<FriendPhotoInfo> mListData = new ArrayList();
  private ListView mListView;
  private GridView mMyPhotoListView;
  protected PhotoClickListener mPhotoClickListener = new PhotoClickListener(null);
  private ShowPhoto mPhotoUtil;
  private int mScreenWidth;
  private int mTabIndex = 0;
  private View mTitleRefresProgressBar;
  private RelativeLayout mTopBarLayout;
  private TextView mTvViewMore;
  private TextView mTvWaitInfo;
  private int mType = 0;
  private LinearLayout mWaitLayout;
  private KXTopTabHost tabHost;
  private int[] tabIndexArray = new int[3];
  private int[] tabPrivStateArray = new int[3];
  private int[] tabStateArray = new int[3];

  private void OnFetchFinishedUpdateView()
  {
    if (getListDataSize() > 0)
    {
      if (this.mTabIndex == 1)
        addFooterView(false);
      if (this.mTabIndex == 0)
      {
        this.mListView.setVisibility(0);
        this.mMyPhotoListView.setVisibility(8);
      }
      while (true)
      {
        this.mWaitLayout.setVisibility(8);
        return;
        if (this.mTabIndex != 1)
          continue;
        this.mListView.setVisibility(8);
        this.mMyPhotoListView.setVisibility(0);
      }
    }
    if (this.mTabIndex == 0);
    for (String str = getResources().getString(2131428012); ; str = getResources().getString(2131427501).replace("*", "你"))
    {
      this.mListView.setVisibility(8);
      this.mMyPhotoListView.setVisibility(8);
      this.mWaitLayout.setVisibility(0);
      this.mWaitLayout.findViewById(2131363415).setVisibility(8);
      this.mTvWaitInfo.setText(str);
      return;
    }
  }

  private void addFooterView(boolean paramBoolean)
  {
    monitorenter;
    if (paramBoolean);
    try
    {
      int i;
      if (!this.haveFooterView)
      {
        this.mFooterView.setVisibility(0);
        this.mListView.addFooterView(this.mFooterView);
        this.haveFooterView = true;
        i = 1;
        if (this.mTabIndex == 0)
          this.mListView.setAdapter(this.mAdapter);
        if (i != 0)
        {
          if (this.mTabIndex != 0)
            break label128;
          this.mAdapter.notifyDataSetChanged();
        }
      }
      while (true)
      {
        return;
        i = 0;
        if (paramBoolean)
          break;
        boolean bool = this.haveFooterView;
        i = 0;
        if (!bool)
          break;
        this.mFooterView.setVisibility(8);
        this.mListView.removeFooterView(this.mFooterView);
        this.haveFooterView = false;
        i = 1;
        break;
        label128: this.mListView.setAdapter(this.mAlbumListAdapter);
        this.mAlbumListAdapter.notifyDataSetChanged();
      }
    }
    catch (Exception localException)
    {
      while (true)
        KXLog.e("TEST", "addFooterView(" + paramBoolean + ") error:" + localException.toString());
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private void cancelCurrentTask()
  {
    if ((this.mGetDataTask != null) && (this.mGetDataTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.mGetDataTask.cancel(true);
      this.mGetDataTask = null;
    }
    if ((this.mGetMoreDataTask != null) && (this.mGetMoreDataTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.mGetMoreDataTask.cancel(true);
      this.mGetMoreDataTask = null;
    }
    if ((this.mGetAlbumlistTask != null) && (this.mGetAlbumlistTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.mGetAlbumlistTask.cancel(true);
      this.mGetAlbumlistTask = null;
    }
  }

  private void dealLoadingMoreDateResult(int paramInt)
  {
    if (isShowLoadMoreResult())
    {
      if (paramInt == 1)
        updatePhotoListView();
    }
    else
      return;
    Toast.makeText(getActivity(), 2131427485, 0).show();
    this.mFooterProBar.setVisibility(8);
    this.mTvViewMore.setTextColor(getResources().getColor(2130839419));
  }

  private void dealViewMoreClick()
  {
    int i = FriendPhotoEngine.getInstance().getmLoadMoreDataState();
    if (i == 0)
      updatePhotoListView();
    do
    {
      return;
      if (i != 2)
        continue;
      showLoadingMoreProgressBar();
      return;
    }
    while (!super.checkNetworkAndHint(true));
    showLoadingMoreProgressBar();
    getMoreDataInBackground();
  }

  private void forceRefreshList(boolean paramBoolean)
  {
    if ((paramBoolean) && (!super.checkNetworkAndHint(true)))
    {
      setStateOnFetchFinished(true);
      return;
    }
    showLoading(true);
    setActivityState(1);
    refreshData(this.mType, 0, 24, -1L);
  }

  private int getActivityState()
  {
    return this.tabStateArray[this.mTabIndex];
  }

  private int getListDataSize()
  {
    if (this.mTabIndex == 0)
      return this.mListData.size();
    return this.mAlbumListData.size();
  }

  private void getMoreDataInBackground()
  {
    long l;
    if (this.mType == 0)
    {
      FriendPhotoModel.getInstance().getmFriendPhotoTotal();
      l = FriendPhotoModel.getInstance().getmTimeStampFirend();
    }
    while (true)
    {
      int i = this.mAdapter.getAlbumCount();
      getMoreDate(this.mType, i, 24, l);
      return;
      FriendPhotoModel.getInstance().getmPlazaPhotoTotal();
      l = FriendPhotoModel.getInstance().getmTimeStampPlaza();
    }
  }

  private void getMoreDate(int paramInt1, int paramInt2, int paramInt3, long paramLong)
  {
    String[] arrayOfString = new String[4];
    arrayOfString[0] = String.valueOf(paramInt1);
    arrayOfString[1] = String.valueOf(paramInt2);
    arrayOfString[2] = String.valueOf(paramInt3);
    arrayOfString[3] = String.valueOf(paramLong);
    if ((this.mGetMoreDataTask != null) && (this.mGetMoreDataTask.getStatus() != AsyncTask.Status.FINISHED))
      return;
    this.mGetMoreDataTask = new GetDataTask(null);
    this.mGetMoreDataTask.execute(arrayOfString);
  }

  private void initListView()
  {
    this.mListView = ((ListView)findViewById(2131363417));
    this.mFooterView = LayoutInflater.from(getActivity()).inflate(2130903286, null);
    this.mFooterProBar = this.mFooterView.findViewById(2131362200);
    this.mTvViewMore = ((TextView)this.mFooterView.findViewById(2131362201));
    this.mFooterView.setOnClickListener(this);
    FragmentActivity localFragmentActivity;
    if (this.mAdapter == null)
    {
      localFragmentActivity = getActivity();
      if (ScreenUtil.getOrientation(getActivity()) != 2)
        break label210;
    }
    label210: for (boolean bool = true; ; bool = false)
    {
      this.mAdapter = new PhotoListAdapter(localFragmentActivity, 0, bool);
      this.mAdapter.setType(0);
      this.mListView.setAdapter(this.mAdapter);
      this.mListView.setOnScrollListener(new PhotoScrollListener(null));
      this.mListView.setOnItemClickListener(this);
      this.mMyPhotoListView = ((GridView)findViewById(2131363418));
      this.mMyPhotoListView.setOnItemClickListener(this);
      this.mAlbumListAdapter = new AlbumlistAdapter(this, 2130903148, this.mAlbumListData, "1");
      this.mMyPhotoListView.setAdapter(this.mAlbumListAdapter);
      return;
    }
  }

  private void initTabHost()
  {
    this.tabHost = ((KXTopTabHost)findViewById(2131363412));
    this.tabHost.setOnTabChangeListener(this);
    KXTopTab localKXTopTab1 = new KXTopTab(getActivity());
    localKXTopTab1.setText(2131428009);
    this.tabHost.addTab(localKXTopTab1);
    KXTopTab localKXTopTab2 = new KXTopTab(getActivity());
    localKXTopTab2.setText(2131428010);
    this.tabHost.addTab(localKXTopTab2);
    KXTopTab localKXTopTab3 = new KXTopTab(getActivity());
    localKXTopTab3.setText("云存储");
    this.tabHost.addTab(localKXTopTab3);
    setListAndTab();
  }

  private void initTitle()
  {
    this.mBtnTitleUploadOpt = ((ImageView)findViewById(2131362924));
    this.mBtnTitleUploadOpt.setVisibility(0);
    ImageView localImageView = this.mBtnTitleUploadOpt;
    if (this.mCurTabIndex == 2);
    for (int i = 2130838152; ; i = 2130839048)
    {
      localImageView.setImageResource(i);
      this.mBtnTitleUploadOpt.setOnClickListener(this);
      this.mBtnTitleRefresh = ((ImageView)findViewById(2131362928));
      this.mBtnTitleRefresh.setImageResource(2130839050);
      this.mBtnTitleRefresh.setVisibility(0);
      this.mBtnTitleRefresh.setOnClickListener(this);
      this.mTitleRefresProgressBar = findViewById(2131362929);
      TextView localTextView = (TextView)findViewById(2131362920);
      localTextView.setText(2131427395);
      localTextView.setVisibility(0);
      findViewById(2131362919).setVisibility(8);
      this.btnLeft = ((ImageView)findViewById(2131362914));
      this.btnLeft.setOnClickListener(this);
      return;
    }
  }

  private void initTopBar()
  {
    this.mTopBarLayout = ((RelativeLayout)findViewById(2131363413));
    this.mTopBarLayout.removeAllViews();
    if (PhotographModel.getInstance().item != null)
    {
      KXNewsBarForPictureView localKXNewsBarForPictureView = new KXNewsBarForPictureView(this);
      localKXNewsBarForPictureView.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
      this.mTopBarLayout.addView(localKXNewsBarForPictureView);
    }
  }

  private boolean isShowLoadMoreResult()
  {
    return this.mFooterProBar.getVisibility() == 0;
  }

  private boolean loadFriendPhotosCacheData()
  {
    int i = this.mListData.size();
    boolean bool = false;
    if (i == 0)
    {
      bool = FriendPhotoEngine.getInstance().loadDataFromCache(getActivity(), this.mType);
      if (bool)
      {
        ArrayList localArrayList = FriendPhotoModel.getInstance().getFriendPhotoList();
        if ((localArrayList != null) && (localArrayList.size() > 0))
        {
          this.mListData.clear();
          this.mListData.addAll(localArrayList);
        }
      }
    }
    return bool;
  }

  private boolean loadMyAlbumCacheData()
  {
    int i = this.mAlbumListData.size();
    boolean bool = false;
    if (i == 0)
    {
      bool = AlbumListEngine.getInstance().loadDataFromCache(getActivity());
      if (bool)
      {
        ArrayList localArrayList = AlbumListModel.getMyAlbumList().getAlbumslist();
        if ((localArrayList != null) && (localArrayList.size() > 0))
        {
          this.mAlbumListData.clear();
          this.mAlbumListData.addAll(localArrayList);
        }
      }
    }
    return bool;
  }

  private void refreshData(int paramInt1, int paramInt2, int paramInt3, long paramLong)
  {
    String[] arrayOfString = new String[4];
    arrayOfString[0] = String.valueOf(paramInt1);
    arrayOfString[1] = String.valueOf(paramInt2);
    arrayOfString[2] = String.valueOf(paramInt3);
    arrayOfString[3] = String.valueOf(paramLong);
    if (this.mTabIndex == 0)
      if ((this.mGetDataTask == null) || (this.mGetDataTask.getStatus() == AsyncTask.Status.FINISHED));
    do
    {
      return;
      this.mGetDataTask = new GetDataTask(null);
      this.mGetDataTask.execute(arrayOfString);
      return;
    }
    while ((this.mGetAlbumlistTask != null) && (this.mGetAlbumlistTask.getStatus() != AsyncTask.Status.FINISHED));
    this.mGetAlbumlistTask = new GetAlbumlistTask(null);
    this.mGetAlbumlistTask.execute(new Void[0]);
  }

  private void resetColumnWidth()
  {
    this.mScreenWidth = ScreenUtil.getScreenWidth(getActivity());
    int i;
    PhotoListAdapter localPhotoListAdapter;
    if (this.mMyPhotoListView != null)
    {
      if (ScreenUtil.getOrientation(getActivity()) == 2)
      {
        i = 4;
        int j = getResources().getDisplayMetrics().widthPixels / i - (int)(15.0F * getResources().getDisplayMetrics().density);
        this.mMyPhotoListView.setColumnWidth(j);
      }
    }
    else if (this.mAdapter != null)
    {
      localPhotoListAdapter = this.mAdapter;
      if (ScreenUtil.getOrientation(getActivity()) != 2)
        break label106;
    }
    label106: for (boolean bool = true; ; bool = false)
    {
      localPhotoListAdapter.setOrientationChanged(bool);
      return;
      i = 3;
      break;
    }
  }

  private void resumeActivity()
  {
    int i = this.tabIndexArray[this.mTabIndex];
    this.mListView.setSelection(i);
    if (getActivityState() == 0)
      forceRefreshList(true);
    do
    {
      do
        return;
      while (getActivityState() != 2);
      setStateOnFetchFinished(true);
    }
    while (getListDataSize() != 0);
    forceRefreshList(true);
  }

  private void setActivityState(int paramInt)
  {
    this.tabPrivStateArray[this.mTabIndex] = this.tabStateArray[this.mTabIndex];
    this.tabStateArray[this.mTabIndex] = paramInt;
  }

  private void setListAndTab()
  {
    if (this.mCurTabIndex < 0)
      this.mCurTabIndex = 0;
    if (this.mCurTabIndex == 0)
    {
      this.mListView.setVisibility(0);
      this.mMyPhotoListView.setVisibility(8);
    }
    while (true)
    {
      this.tabHost.setCurrentTab(this.mCurTabIndex);
      return;
      if (this.mCurTabIndex == 1)
      {
        this.mListView.setVisibility(8);
        this.mMyPhotoListView.setVisibility(0);
        continue;
      }
      if (this.mCurTabIndex != 2)
        continue;
      this.mListView.setVisibility(8);
      this.mMyPhotoListView.setVisibility(8);
      showCloudFragment();
    }
  }

  private void setStateOnFetchFinished(boolean paramBoolean)
  {
    OnFetchFinishedUpdateView();
    if (paramBoolean)
    {
      setActivityState(2);
      if (this.mTabIndex == 0)
        this.mAdapter.notifyDataSetChanged();
      while (true)
      {
        if (this.mTabIndex == 0)
        {
          this.bHaveMore = updateFooterView();
          if (this.bHaveMore)
            getMoreDataInBackground();
        }
        return;
        this.mAlbumListAdapter.notifyDataSetChanged();
        this.mListView.setSelection(0);
      }
    }
    this.tabStateArray[this.mTabIndex] = this.tabPrivStateArray[this.mTabIndex];
  }

  private void showAlbumView(AlbumInfo paramAlbumInfo, String paramString)
  {
    if (paramAlbumInfo == null)
      return;
    User localUser = User.getInstance();
    IntentUtil.showViewAlbumNotFromViewPhoto(getActivity(), this, paramAlbumInfo.albumsFeedAlbumid, paramAlbumInfo.albumsFeedAlbumtitle, localUser.getUID(), localUser.getRealName(), paramAlbumInfo.albumsFeedPicnum, paramString);
  }

  private void showLoading(boolean paramBoolean)
  {
    int i = getListDataSize();
    if (paramBoolean)
    {
      if (i > 0)
      {
        this.mListView.setVisibility(0);
        this.mWaitLayout.setVisibility(8);
      }
      while (true)
      {
        showTitleRefreshBar(true);
        return;
        this.mWaitLayout.setVisibility(0);
        this.mListView.setVisibility(8);
        this.mMyPhotoListView.setVisibility(8);
        this.mWaitLayout.findViewById(2131363415).setVisibility(0);
        this.mTvWaitInfo.setText(2131427599);
      }
    }
    showTitleRefreshBar(false);
  }

  private void showLoadingMoreProgressBar()
  {
    this.mFooterProBar.setVisibility(0);
    this.mTvViewMore.setTextColor(getResources().getColor(2130839395));
  }

  private void showTitleRefreshBar(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.mTitleRefresProgressBar.setVisibility(0);
      this.mBtnTitleRefresh.setImageResource(0);
      return;
    }
    this.mTitleRefresProgressBar.setVisibility(4);
    this.mBtnTitleRefresh.setImageResource(2130839050);
  }

  private void stopActivity()
  {
    this.tabIndexArray[this.mTabIndex] = this.mListView.getFirstVisiblePosition();
    if (getActivityState() == 1)
    {
      setActivityState(this.tabPrivStateArray[this.mTabIndex]);
      showLoading(false);
      cancelCurrentTask();
      return;
    }
    getActivityState();
  }

  private boolean updateFooterView()
  {
    this.mFooterProBar.setVisibility(8);
    this.mTvViewMore.setTextColor(getResources().getColor(2130839419));
    if (this.mAdapter.getAlbumCount() < FriendPhotoModel.getInstance().getmFriendPhotoTotal())
    {
      addFooterView(true);
      return true;
    }
    addFooterView(false);
    return false;
  }

  private void updatePhotoListView()
  {
    ArrayList localArrayList = FriendPhotoModel.getInstance().getFriendPhotoList();
    if ((localArrayList != null) && (localArrayList.size() > 0))
    {
      this.mWaitLayout.setVisibility(8);
      this.mListView.setVisibility(0);
      this.mListData = localArrayList;
      this.mAdapter.setData(localArrayList);
    }
    while (true)
    {
      this.bHaveMore = updateFooterView();
      if (this.bHaveMore)
        getMoreDataInBackground();
      return;
      this.mWaitLayout.setVisibility(0);
      this.mWaitLayout.findViewById(2131363415).setVisibility(8);
      this.mTvWaitInfo.setText(2131428012);
    }
  }

  public void beforeTabChanged(int paramInt)
  {
    this.tabIndexArray[paramInt] = this.mListView.getFirstVisiblePosition();
    stopActivity();
  }

  public void cloudWithNoPhotos()
  {
    this.mBtnTitleRefresh.setVisibility(8);
    this.mBtnTitleUploadOpt.setVisibility(8);
  }

  public void cloudWithPhotos()
  {
    this.mBtnTitleRefresh.setVisibility(0);
    this.mBtnTitleUploadOpt.setVisibility(0);
  }

  protected void dealPhotoResult(String paramString1, String paramString2)
  {
    try
    {
      if (!new File(paramString1).exists())
      {
        Toast.makeText(getActivity(), 2131427841, 0).show();
        return;
      }
      Bundle localBundle = new Bundle();
      localBundle.putString("filePathName", paramString1);
      localBundle.putString("fileFrom", paramString2);
      IntentUtil.launchEditPhotoForResult(getActivity(), this, 104, localBundle);
      return;
    }
    catch (Exception localException)
    {
      KXLog.e("PicturesActivity", "dealPhotoResult", localException);
    }
  }

  void gotoCloudSetting()
  {
    startActivity(new Intent(getActivity(), CloudAlbumSettingActivity.class));
  }

  protected boolean handleMessage(Message paramMessage)
  {
    if (super.handleMessage(paramMessage))
      return true;
    if (paramMessage.what == 300)
    {
      updatePhotoListView();
      this.mListView.setSelection(0);
      return true;
    }
    return false;
  }

  void hideCloudFragment()
  {
    if (this.cloudFragment != null)
      this.fm.beginTransaction().hide(this.cloudFragment).commitAllowingStateLoss();
    this.mBtnTitleUploadOpt.setImageResource(2130839048);
  }

  void initCloudFragment()
  {
    if (this.cloudFragment != null)
    {
      if (this.mCurTabIndex == 2)
        this.fm.beginTransaction().attach(this.cloudFragment).show(this.cloudFragment).commitAllowingStateLoss();
    }
    else
      return;
    this.fm.beginTransaction().attach(this.cloudFragment).hide(this.cloudFragment).commitAllowingStateLoss();
  }

  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    default:
      return;
    case 2131362914:
      if (super.isMenuListVisibleInMain())
      {
        super.showSubActivityInMain();
        return;
      }
      super.showMenuListInMain();
      return;
    case 2131362928:
      if (this.mCurTabIndex != 2)
      {
        cancelCurrentTask();
        forceRefreshList(true);
        return;
      }
      showLoading(true);
      this.cloudFragment.refresh();
      return;
    case 2131362924:
      if (this.mCurTabIndex != 2)
      {
        FragmentActivity localFragmentActivity = getActivity();
        getActivity();
        localFragmentActivity.getSharedPreferences("from_webpage", 0).edit().putBoolean("fromwebpage", false).commit();
        if (UploadTaskListEngine.getInstance().getWaitingTaskCount() < 20)
          selectPictureFromBulk();
        while (true)
        {
          UserHabitUtils.getInstance(getActivity()).addUserHabit("Homelist_Photo");
          return;
          showCantUploadOptions();
        }
      }
      gotoCloudSetting();
      return;
    case 2131363420:
    }
    dealViewMoreClick();
  }

  public void onCloudRefreshed()
  {
    showLoading(false);
  }

  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
    resetColumnWidth();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Bundle localBundle = getArguments();
    String str = null;
    if (localBundle != null)
    {
      str = localBundle.getString("action");
      if (localBundle.containsKey("index"))
        this.mCurTabIndex = localBundle.getInt("index");
    }
    if ((str != null) && (!str.equals("com.kaixin001.VIEW_NEWS")) && (CrashRecoverUtil.isCrashBefore()))
    {
      CrashRecoverUtil.crashRecover(getActivity());
      IntentUtil.returnToDesktop(getActivity());
      return;
    }
    this.fm = getChildFragmentManager();
    resetColumnWidth();
    UserHabitUtils.getInstance(getActivity()).addUserHabit("visit_photo_friend");
    KXUBLog.log("homepage_viewpic");
  }

  public void onCreateOptionsMenu(Menu paramMenu, MenuInflater paramMenuInflater)
  {
    super.onCreateOptionsMenu(paramMenu, paramMenuInflater);
    paramMenu.add(0, 401, 0, 2131427690).setIcon(2130838607);
    paramMenu.add(0, 402, 0, 2131427698).setIcon(2130838609);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903285, paramViewGroup, false);
  }

  public void onDestroy()
  {
    super.onDestroy();
    FriendPhotoModel.getInstance().clear(true);
    if (this.mGetDataTask != null)
      this.mGetDataTask.cancel(true);
    if (this.mGetMoreDataTask != null)
      this.mGetMoreDataTask.cancel(true);
  }

  public void onDestroyView()
  {
    if (this.cloudFragment != null)
      this.fm.beginTransaction().detach(this.cloudFragment).commitAllowingStateLoss();
    super.onDestroyView();
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    if ((this.mTabIndex != 1) || (this.mAlbumListData == null));
    AlbumInfo localAlbumInfo;
    do
    {
      return;
      localAlbumInfo = (AlbumInfo)this.mAlbumListData.get(paramInt);
    }
    while (localAlbumInfo == null);
    try
    {
      showAlbumView(localAlbumInfo, "");
      return;
    }
    catch (Exception localException)
    {
      KXLog.e("AlbumListActivity", "onItemClick", localException);
    }
  }

  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default:
      return super.onOptionsItemSelected(paramMenuItem);
    case 401:
      forceRefreshList(true);
      return true;
    case 402:
      this.mListView.setSelection(0);
      return true;
    case 403:
      IntentUtil.returnToDesktop(getActivity());
      return true;
    case 404:
    }
    IntentUtil.showMyHomeFragment(this);
    return true;
  }

  public void onResume()
  {
    super.onResume();
    resetColumnWidth();
  }

  public void onStop()
  {
    super.onStop();
    stopActivity();
  }

  public void onTabChanged(int paramInt)
  {
    this.mCurTabIndex = paramInt;
    if (paramInt == 0)
    {
      this.mMyPhotoListView.setVisibility(8);
      this.mListView.setVisibility(0);
      hideCloudFragment();
      this.mTabIndex = 0;
      addFooterView(this.bHaveMore);
      this.mListView.setAdapter(this.mAdapter);
      this.mListView.setBackgroundColor(getResources().getColor(2130839400));
      this.mListView.setDividerHeight(0);
      this.mAdapter.notifyDataSetChanged();
      UserHabitUtils.getInstance(getActivity()).addUserHabit("Picture_Friend");
      UserHabitUtils.getInstance(getActivity()).addUserHabit("visit_photo_friend");
    }
    while (true)
    {
      resumeActivity();
      return;
      if (paramInt == 1)
      {
        this.mListView.setVisibility(8);
        this.mMyPhotoListView.setVisibility(0);
        hideCloudFragment();
        this.mTabIndex = 1;
        addFooterView(false);
        this.mMyPhotoListView.setAdapter(this.mAlbumListAdapter);
        this.mMyPhotoListView.setBackgroundColor(getResources().getColor(2130839415));
        this.mAlbumListAdapter.notifyDataSetChanged();
        this.mTabIndex = 1;
        addFooterView(false);
        this.mListView.setAdapter(this.mAlbumListAdapter);
        this.mListView.setBackgroundColor(getResources().getColor(2130839385));
        this.mListView.setDividerHeight(1);
        this.mAlbumListAdapter.notifyDataSetChanged();
        UserHabitUtils.getInstance(getActivity()).addUserHabit("Picture_Self");
        UserHabitUtils.getInstance(getActivity()).addUserHabit("visit_photo_mine");
        continue;
      }
      if (paramInt != 2)
        continue;
      this.mListView.setVisibility(8);
      this.mMyPhotoListView.setVisibility(8);
      showCloudFragment();
    }
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    this.mWaitLayout = ((LinearLayout)paramView.findViewById(2131363414));
    this.mTvWaitInfo = ((TextView)paramView.findViewById(2131363416));
    this.mPhotoUtil = new ShowPhoto(getActivity(), this, false);
    initTitle();
    initListView();
    initTabHost();
    initCloudFragment();
    initTopBar();
    FriendPhotoModel.getInstance().setHasShutDown(false);
    if (!dataInited())
    {
      forceRefreshList(false);
      loadMyAlbumCacheData();
    }
  }

  void showCloudFragment()
  {
    if (this.cloudFragment == null)
    {
      this.cloudFragment = new CloudAlbumFragment();
      this.cloudFragment.setPicFragment(this);
      this.fm.beginTransaction().add(2131363419, this.cloudFragment).commitAllowingStateLoss();
      this.cloudFragment.setOnRefreshCloudListener(this);
    }
    while (true)
    {
      this.mBtnTitleUploadOpt.setImageResource(2130838152);
      return;
      this.fm.beginTransaction().show(this.cloudFragment).commitAllowingStateLoss();
    }
  }

  private class GetAlbumlistTask extends KXFragment.KXAsyncTask<Void, Void, Boolean>
  {
    private GetAlbumlistTask()
    {
      super();
    }

    protected Boolean doInBackgroundA(Void[] paramArrayOfVoid)
    {
      try
      {
        Boolean localBoolean = Boolean.valueOf(AlbumListEngine.getInstance().getAlbumPhotoList(PicturesFragment.this.getActivity(), User.getInstance().getUID()));
        return localBoolean;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
      }
      return null;
    }

    protected void onPostExecuteA(Boolean paramBoolean)
    {
      if ((paramBoolean == null) || (PicturesFragment.this.getView() == null) || (PicturesFragment.this.getActivity() == null))
        return;
      PicturesFragment.this.mWaitLayout.setVisibility(4);
      PicturesFragment.this.showLoading(false);
      if (paramBoolean == null)
      {
        PicturesFragment.this.getFragmentManager().beginTransaction().remove(PicturesFragment.this).commitAllowingStateLoss();
        return;
      }
      try
      {
        if (paramBoolean.booleanValue())
        {
          ArrayList localArrayList = AlbumListModel.getMyAlbumList().getAlbumslist();
          if (localArrayList != null)
          {
            PicturesFragment.this.mAlbumListData.clear();
            PicturesFragment.this.mAlbumListData.addAll(localArrayList);
          }
          PicturesFragment.this.setStateOnFetchFinished(true);
          return;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("GetAlbumlistTask", "onPostExecute", localException);
        return;
      }
      Toast.makeText(PicturesFragment.this.getActivity(), 2131427741, 0).show();
      PicturesFragment.this.setStateOnFetchFinished(false);
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private class GetDataTask extends KXFragment.KXAsyncTask<String, Void, Integer>
  {
    private int mStart = 0;
    private int mType = 0;

    private GetDataTask()
    {
      super();
    }

    protected Integer doInBackgroundA(String[] paramArrayOfString)
    {
      if (paramArrayOfString != null)
      {
        if (paramArrayOfString != null);
        try
        {
          if (paramArrayOfString.length < 4)
            return null;
          this.mType = Integer.valueOf(paramArrayOfString[0]).intValue();
          this.mStart = Integer.valueOf(paramArrayOfString[1]).intValue();
          long l = Long.valueOf(paramArrayOfString[3]).longValue();
          if (PicturesFragment.this.loadFriendPhotosCacheData())
            publishProgress(new Void[] { null });
          PhotographEngine.getInstance().getPhoto(PicturesFragment.this.getActivity());
          boolean bool = FriendPhotoEngine.getInstance().getPhotoList(PicturesFragment.this.getActivity(), this.mType, Integer.valueOf(paramArrayOfString[1]).intValue(), Integer.valueOf(paramArrayOfString[2]).intValue(), l);
          i = 0;
          if (bool)
            i = 1;
          return Integer.valueOf(i);
        }
        catch (Exception localException)
        {
          while (true)
          {
            KXLog.e("PicturesActivity", "GetData.Task.doInBackground...", localException);
            int i = 0;
          }
        }
      }
      return null;
    }

    protected void onCancelledA()
    {
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      if ((paramInteger == null) || (PicturesFragment.this.getView() == null) || (PicturesFragment.this.getActivity() == null));
      do
      {
        while (true)
        {
          return;
          PicturesFragment.this.mWaitLayout.setVisibility(4);
          PicturesFragment.this.showLoading(false);
          if ((this.mStart != 0) || ((paramInteger != null) && (paramInteger.intValue() == 1)))
            break;
          if (PicturesFragment.this.checkNetworkAndHint(true))
          {
            Toast.makeText(PicturesFragment.this.getActivity(), 2131427485, 0).show();
            PicturesFragment.this.setStateOnFetchFinished(false);
          }
          ArrayList localArrayList = FriendPhotoModel.getInstance().getFriendPhotoList();
          if ((localArrayList != null) && (localArrayList.size() > 0))
            continue;
          PicturesFragment.this.mWaitLayout.setVisibility(0);
          PicturesFragment.this.mWaitLayout.findViewById(2131363415).setVisibility(8);
          PicturesFragment.this.mTvWaitInfo.setText(2131428012);
          PicturesFragment.this.setStateOnFetchFinished(true);
          return;
        }
        if ((PicturesFragment.this.mAdapter == null) || (this.mStart != 0))
          continue;
        PicturesFragment.this.setActivityState(2);
        PicturesFragment.this.mHandler.sendEmptyMessageDelayed(300, 100L);
        PicturesFragment.this.initTopBar();
        return;
      }
      while ((PicturesFragment.this.mAdapter == null) || (this.mStart <= 0));
      PicturesFragment.this.dealLoadingMoreDateResult(paramInteger.intValue());
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
      ArrayList localArrayList = FriendPhotoModel.getInstance().getFriendPhotoList();
      if ((localArrayList != null) && (localArrayList.size() > 0))
      {
        PicturesFragment.this.mWaitLayout.setVisibility(8);
        PicturesFragment.this.mListView.setVisibility(0);
        PicturesFragment.this.mListData = localArrayList;
        PicturesFragment.this.mAdapter.setData(localArrayList);
      }
      PicturesFragment.this.addFooterView(false);
    }
  }

  private class PhotoClickListener
    implements View.OnClickListener
  {
    private PhotoClickListener()
    {
    }

    public void onClick(View paramView)
    {
      Object localObject = paramView.getTag();
      if ((localObject == null) || (!(localObject instanceof FriendPhotoInfo)));
      FriendPhotoInfo localFriendPhotoInfo;
      do
      {
        return;
        localFriendPhotoInfo = (FriendPhotoInfo)localObject;
      }
      while (PicturesFragment.this.mPhotoUtil == null);
      String str1 = localFriendPhotoInfo.albumId;
      String str2 = localFriendPhotoInfo.ownerId;
      String str3 = "1";
      if (localFriendPhotoInfo.bNeedPwd)
        str3 = "2";
      PicturesFragment.this.mPhotoUtil.showPhoto(str1, 2, 1, str2, "", str3);
    }
  }

  private class PhotoListAdapter extends BaseAdapter
  {
    public static final int LIST_ITEM_COUNT_3 = 3;
    public static final int LIST_ITEM_COUNT_4 = 4;
    private boolean isLandOrientation;
    private LayoutInflater mInflater;
    private int mItemCount = 3;
    private ArrayList<FriendPhotoInfo> mPhotoList;
    private int mType = 0;

    public PhotoListAdapter(Context paramInt, int paramBoolean, boolean arg4)
    {
      this.mInflater = ((LayoutInflater)paramInt.getSystemService("layout_inflater"));
      setType(paramBoolean);
      boolean bool;
      this.isLandOrientation = bool;
      if (this.isLandOrientation)
      {
        this.mItemCount = 4;
        return;
      }
      this.mItemCount = 3;
    }

    public int getAlbumCount()
    {
      if (this.mPhotoList == null)
        return 0;
      return this.mPhotoList.size();
    }

    public int getCount()
    {
      if (this.mPhotoList == null)
        return 0;
      return (int)Math.ceil(1.0D * this.mPhotoList.size() / this.mItemCount);
    }

    public FriendPhotoInfo getItem(int paramInt)
    {
      if (this.mPhotoList == null);
      do
        return null;
      while ((paramInt < 0) || (paramInt >= this.mPhotoList.size()));
      return (FriendPhotoInfo)this.mPhotoList.get(paramInt);
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      View localView1;
      int i;
      ViewHolder localViewHolder;
      if (paramView == null)
      {
        localView1 = this.mInflater.inflate(2130903283, null, false);
        localView1.setTag(new ViewHolder(localView1));
        i = paramInt * this.mItemCount;
        localViewHolder = (ViewHolder)localView1.getTag();
        localViewHolder.initWithOrientation(this.isLandOrientation, PicturesFragment.this.mScreenWidth);
      }
      FriendPhotoInfo localFriendPhotoInfo;
      View localView2;
      ImageView localImageView;
      TextView localTextView1;
      TextView localTextView2;
      for (int j = 0; ; j++)
      {
        if (j >= this.mItemCount)
        {
          return localView1;
          localView1 = paramView;
          break;
        }
        localFriendPhotoInfo = getItem(i + j);
        localView2 = localViewHolder.item_layouts[j];
        localImageView = localViewHolder.albumCovers[j];
        localTextView1 = localViewHolder.friendsNames[j];
        localTextView2 = localViewHolder.albumNames[j];
        if (localFriendPhotoInfo != null)
          break label155;
        localView2.setVisibility(4);
      }
      label155: localView2.setVisibility(0);
      localView2.setTag(localFriendPhotoInfo);
      if (localFriendPhotoInfo.bNeedPwd)
        localImageView.setImageResource(2130837545);
      while (true)
      {
        localTextView2.setText(localFriendPhotoInfo.imageName);
        localTextView1.setMaxWidth(localImageView.getMeasuredWidth());
        localTextView1.setText(localFriendPhotoInfo.ownerName);
        localTextView1.setVisibility(0);
        break;
        PicturesFragment.this.displayPicture(localImageView, localFriendPhotoInfo.img, 2130838784);
      }
    }

    public void setData(ArrayList<FriendPhotoInfo> paramArrayList)
    {
      if (paramArrayList == null)
        return;
      ArrayList localArrayList = new ArrayList();
      localArrayList.addAll(paramArrayList);
      this.mPhotoList.clear();
      this.mPhotoList.addAll(localArrayList);
      notifyDataSetChanged();
    }

    public void setOrientationChanged(boolean paramBoolean)
    {
      this.isLandOrientation = paramBoolean;
      if (this.isLandOrientation);
      for (this.mItemCount = 4; ; this.mItemCount = 3)
      {
        notifyDataSetChanged();
        return;
      }
    }

    public void setType(int paramInt)
    {
      this.mType = paramInt;
      if (this.mType == 0)
      {
        this.mPhotoList = PicturesFragment.this.mListData;
        return;
      }
      this.mPhotoList = FriendPhotoModel.getInstance().getPlazaPhotoList();
    }

    private class ViewHolder
    {
      public ImageView[] albumCovers = new ImageView[4];
      public TextView[] albumNames = new TextView[4];
      public TextView[] friendsNames = new TextView[4];
      public View[] imageLayouts = new View[4];
      public View[] item_layouts = new View[4];

      public ViewHolder(View arg2)
      {
        Object localObject;
        this.item_layouts[0] = localObject.findViewById(2131363407);
        this.item_layouts[1] = localObject.findViewById(2131363408);
        this.item_layouts[2] = localObject.findViewById(2131363409);
        this.item_layouts[3] = localObject.findViewById(2131363410);
        for (int i = 0; ; i++)
        {
          if (i >= 4)
            return;
          View localView = this.item_layouts[i];
          localView.setOnClickListener(PicturesFragment.this.mPhotoClickListener);
          this.imageLayouts[i] = localView.findViewById(2131362516);
          this.albumCovers[i] = ((ImageView)localView.findViewById(2131362730));
          this.albumNames[i] = ((TextView)localView.findViewById(2131363441));
          this.friendsNames[i] = ((TextView)localView.findViewById(2131363442));
        }
      }

      public void initWithOrientation(boolean paramBoolean, int paramInt)
      {
        setItemWidth(mesureItemWidth(paramInt, paramBoolean));
        if (paramBoolean)
        {
          this.item_layouts[3].setVisibility(0);
          return;
        }
        this.item_layouts[3].setVisibility(8);
      }

      protected int mesureItemWidth(int paramInt, boolean paramBoolean)
      {
        if (paramBoolean);
        for (int i = 4; ; i = 3)
          return -10 + (paramInt - 35) / i;
      }

      protected void setItemWidth(int paramInt)
      {
        ViewGroup.LayoutParams localLayoutParams = this.imageLayouts[0].getLayoutParams();
        localLayoutParams.width = paramInt;
        localLayoutParams.height = paramInt;
        this.imageLayouts[0].setLayoutParams(localLayoutParams);
        this.imageLayouts[1].setLayoutParams(localLayoutParams);
        this.imageLayouts[2].setLayoutParams(localLayoutParams);
        this.imageLayouts[3].setLayoutParams(localLayoutParams);
      }
    }
  }

  private class PhotoScrollListener
    implements AbsListView.OnScrollListener
  {
    private boolean mNeedAutoLoading = false;

    private PhotoScrollListener()
    {
    }

    public void onScroll(AbsListView paramAbsListView, int paramInt1, int paramInt2, int paramInt3)
    {
      int i = -1 + (paramInt1 + paramInt2);
      int j = paramInt3 - 1;
      if ((i >= j) && (j > 0) && (!this.mNeedAutoLoading))
      {
        View localView = PicturesFragment.this.mFooterView;
        boolean bool = false;
        if (localView != null)
        {
          int k = PicturesFragment.this.mFooterView.getVisibility();
          bool = false;
          if (k == 0)
            bool = true;
        }
        this.mNeedAutoLoading = bool;
      }
    }

    public void onScrollStateChanged(AbsListView paramAbsListView, int paramInt)
    {
      if (this.mNeedAutoLoading)
      {
        PicturesFragment.this.dealViewMoreClick();
        this.mNeedAutoLoading = false;
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.PicturesFragment
 * JD-Core Version:    0.6.0
 */