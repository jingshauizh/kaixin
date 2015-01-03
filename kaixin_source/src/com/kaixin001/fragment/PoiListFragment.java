package com.kaixin001.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.location.Location;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.activity.CaptureActivity;
import com.kaixin001.engine.LbsEngine;
import com.kaixin001.item.PoiItem;
import com.kaixin001.lbs.LocationService;
import com.kaixin001.lbs.RefreshLocationProxy;
import com.kaixin001.lbs.RefreshLocationProxy.IRefreshLocationCallback;
import com.kaixin001.model.LbsModel;
import com.kaixin001.model.Setting;
import com.kaixin001.util.ActivityUtil;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.FriendsUtil;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXUBLog;
import com.kaixin001.util.UserHabitUtils;
import com.kaixin001.view.PullToRefreshView;
import com.kaixin001.view.PullToRefreshView.PullToRefreshListener;
import java.util.ArrayList;
import java.util.Iterator;

public class PoiListFragment extends BaseFragment
  implements AdapterView.OnItemClickListener, PullToRefreshView.PullToRefreshListener
{
  public static final String IS_SHOW_POI_DETAIL = "is_show_poi_detail";
  public static final String LATITUDE = "lat";
  public static final String LONGITUDE = "lon";
  public static final String POI_NAME = "poi_name";
  private static final String TAG_VIEW_MORE = "view_more";
  Location currentLocation;
  protected String lat;
  protected String lon;
  final PoiListAdapter mAdapter = new PoiListAdapter(null);
  private ImageView mBtnScan;
  private Button mBtnSearch = null;
  protected PullToRefreshView mDownView;
  private EditText mEditSearch = null;
  private ProgressBar mEmptyProBar = null;
  private View mFooterView = null;
  private ArrayList<SystemMessageListFragment.NameIdItem> mFriendsList;
  private GetPoiListTask mGetPoiListTask = null;
  private View mHeaderView = null;
  private boolean mIsShowPoiDetail = true;
  private View mLayoutEmpty = null;
  private View mLbsFirstView = null;
  private ListView mListView = null;
  final PoiDetailListener mPoiDetailListener = new PoiDetailListener(null);
  final ArrayList<PoiItem> mPoiList = new ArrayList();
  String mSearchKey = null;
  private TextView mTxtEmpty = null;
  private boolean mUserRefresh = false;
  private View mViewMorePoi = null;
  private RefreshLocationProxy refreshLocationProxy;
  private TextView txtAward;

  private void cancelTask()
  {
    if ((this.mGetPoiListTask != null) && (!this.mGetPoiListTask.isCancelled()) && (this.mGetPoiListTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.mGetPoiListTask.cancel(true);
      LbsEngine.getInstance().cancel();
      this.mGetPoiListTask = null;
    }
  }

  private String convertUserInfoList2String(ArrayList<SystemMessageListFragment.NameIdItem> paramArrayList)
  {
    if (paramArrayList == null)
      return null;
    StringBuilder localStringBuilder = new StringBuilder();
    Iterator localIterator = paramArrayList.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return localStringBuilder.toString();
      SystemMessageListFragment.NameIdItem localNameIdItem = (SystemMessageListFragment.NameIdItem)localIterator.next();
      localStringBuilder.append(FriendsUtil.getNameBmpFormat(localNameIdItem.mId, localNameIdItem.mName));
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
          boolean bool = true;
          PoiListFragment.this.showLocating();
          PoiListFragment.this.cancelTask();
          PoiListFragment.this.mUserRefresh = bool;
          PoiListFragment.this.mEditSearch.setText("");
          PoiListFragment localPoiListFragment = PoiListFragment.this;
          if (PoiListFragment.this.mPoiList.size() > 0);
          while (true)
          {
            localPoiListFragment.showLoading(bool);
            PoiListFragment.this.mUserRefresh = false;
            return;
            bool = false;
          }
        }

        public void onCancelRefreshLocation()
        {
          PoiListFragment.this.stopPullToRefresh();
          PoiListFragment.this.showError();
          if (PoiListFragment.this.mPoiList.size() > 0)
            PoiListFragment.this.showPoiList();
        }

        public void onLocationAvailable(Location paramLocation)
        {
          PoiListFragment.this.currentLocation = paramLocation;
          PoiListFragment.this.lat = PoiListFragment.this.getLatitude();
          PoiListFragment.this.lon = PoiListFragment.this.getLongitude();
          PoiListFragment.this.refreshData(0, true);
        }

        public void onLocationFailed()
        {
          PoiListFragment.this.stopPullToRefresh();
          Toast.makeText(PoiListFragment.this.getActivity(), 2131427722, 0).show();
          PoiListFragment.this.showPoiList();
        }
      });
    return this.refreshLocationProxy;
  }

  private void hideInputMethod()
  {
    View localView = getActivity().getCurrentFocus();
    if (localView != null)
      ((InputMethodManager)getActivity().getSystemService("input_method")).hideSoftInputFromWindow(localView.getWindowToken(), 2);
  }

  private void initFooterView()
  {
    if (this.mFooterView != null)
      return;
    this.mFooterView = getActivity().getLayoutInflater().inflate(2130903293, null);
    PoiItemCache localPoiItemCache = new PoiItemCache(this.mFooterView);
    this.mFooterView.setTag(localPoiItemCache);
    localPoiItemCache.showAddNewPoi();
  }

  private void initHeaderView()
  {
    if (this.mHeaderView != null)
      return;
    this.mHeaderView = getActivity().getLayoutInflater().inflate(2130903328, null);
    this.mEditSearch = ((EditText)this.mHeaderView.findViewById(2131363601));
    this.mEditSearch.setHint(2131428177);
    this.mBtnSearch = ((Button)this.mHeaderView.findViewById(2131362164));
    Button localButton = (Button)this.mHeaderView.findViewById(2131363602);
    localButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        PoiListFragment.this.mEditSearch.setText("");
      }
    });
    this.mEditSearch.addTextChangedListener(new TextWatcher(localButton)
    {
      public void afterTextChanged(Editable paramEditable)
      {
      }

      public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
      {
      }

      public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
      {
        if (PoiListFragment.this.isNeedReturn());
        while (true)
        {
          return;
          if (!TextUtils.isEmpty(PoiListFragment.this.mEditSearch.getText().toString().trim().toLowerCase()))
            break;
          this.val$cancelButton.setVisibility(8);
          PoiListFragment.this.mBtnSearch.setVisibility(8);
          PoiListFragment.this.mListView.requestFocus();
          PoiListFragment.this.hideInputMethod();
          PoiListFragment.this.mSearchKey = null;
          if (PoiListFragment.this.mUserRefresh)
            continue;
          PoiListFragment.this.showLoading(true);
          PoiListFragment.this.refreshData(0, true);
          return;
        }
        this.val$cancelButton.setVisibility(0);
        PoiListFragment.this.mBtnSearch.setVisibility(0);
      }
    });
    this.mBtnSearch.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        String str = PoiListFragment.this.mEditSearch.getText().toString().trim().toLowerCase();
        if (TextUtils.isEmpty(str))
        {
          PoiListFragment.this.mSearchKey = null;
          Toast.makeText(PoiListFragment.this.getActivity(), 2131428179, 0).show();
          return;
        }
        PoiListFragment.this.mSearchKey = str;
        PoiListFragment.this.showLoading(true);
        PoiListFragment.this.refreshData(0, true);
      }
    });
    if (this.mLbsFirstView == null)
      this.mLbsFirstView = getActivity().getLayoutInflater().inflate(2130903196, null);
    this.txtAward = ((TextView)this.mLbsFirstView.findViewById(2131362953));
    TextView localTextView = this.txtAward;
    Resources localResources = getResources();
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.valueOf(LbsModel.getInstance().getActivityTotal());
    localTextView.setText(localResources.getString(2131428455, arrayOfObject));
    this.mLbsFirstView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        if (PoiListFragment.this.currentLocation == null)
        {
          IntentUtil.showLbsActivityListFragment(PoiListFragment.this, null, null);
          return;
        }
        String str1 = String.valueOf(PoiListFragment.this.currentLocation.getLatitude());
        String str2 = String.valueOf(PoiListFragment.this.currentLocation.getLongitude());
        IntentUtil.showLbsActivityListFragment(PoiListFragment.this, str2, str1);
      }
    });
  }

  private void onViewMoreClicked(View paramView)
  {
    this.mViewMorePoi = paramView;
    LbsModel localLbsModel = LbsModel.getInstance();
    ArrayList localArrayList = localLbsModel.getPoiList();
    PoiItemCache localPoiItemCache = (PoiItemCache)paramView.getTag();
    boolean bool = false;
    int i = this.mPoiList.size();
    if (i > 0)
    {
      PoiItem localPoiItem = (PoiItem)this.mPoiList.get(i - 1);
      if ((localPoiItem != null) && ("view_more".equals(localPoiItem.poiId)))
        i--;
    }
    if ((localArrayList != null) && (i < localArrayList.size()))
    {
      updateList();
      showPoiList();
    }
    while (true)
    {
      if ((localArrayList != null) && (localArrayList.size() < localLbsModel.getTotal()))
      {
        localPoiItemCache.showViewMore(bool);
        refreshData(localArrayList.size(), bool);
      }
      return;
      bool = true;
    }
  }

  private void refreshData(int paramInt, boolean paramBoolean)
  {
    if (!super.checkNetworkAndHint(paramBoolean))
    {
      stopPullToRefresh();
      return;
    }
    if ((this.mGetPoiListTask != null) && (!this.mGetPoiListTask.isCancelled()) && (this.mGetPoiListTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      stopPullToRefresh();
      return;
    }
    String str = getCellIdInfo();
    if ((this.currentLocation == null) && (TextUtils.isEmpty(str)))
    {
      showError();
      stopPullToRefresh();
      return;
    }
    String[] arrayOfString = new String[3];
    arrayOfString[0] = str;
    arrayOfString[1] = String.valueOf(paramInt);
    arrayOfString[2] = this.mSearchKey;
    this.mGetPoiListTask = new GetPoiListTask(null);
    this.mGetPoiListTask.showErrorToast(paramBoolean);
    this.mGetPoiListTask.execute(arrayOfString);
  }

  private void refreshLocation(boolean paramBoolean)
  {
    this.currentLocation = null;
    getRefreshLocationProxy().refreshLocation(paramBoolean);
  }

  private void setListView()
  {
    initHeaderView();
    initFooterView();
    this.mListView = ((ListView)findViewById(2131363445));
    this.mListView.addHeaderView(this.mHeaderView);
    this.mListView.addFooterView(this.mFooterView);
    this.mListView.setAdapter(this.mAdapter);
    this.mListView.setOnItemClickListener(this);
    this.mListView.setOnScrollListener(new AbsListView.OnScrollListener()
    {
      public void onScroll(AbsListView paramAbsListView, int paramInt1, int paramInt2, int paramInt3)
      {
      }

      public void onScrollStateChanged(AbsListView paramAbsListView, int paramInt)
      {
        if (paramInt == 1)
          ActivityUtil.hideInputMethod(PoiListFragment.this.getActivity());
      }
    });
  }

  private void setOtherViews()
  {
    findViewById(2131363444).setVisibility(8);
    this.mLayoutEmpty = findViewById(2131363102);
    this.mEmptyProBar = ((ProgressBar)findViewById(2131363446));
    this.mTxtEmpty = ((TextView)findViewById(2131361972));
  }

  private void showError()
  {
    this.mListView.setVisibility(8);
    this.mLayoutEmpty.setVisibility(0);
    this.mEmptyProBar.setVisibility(8);
    this.mTxtEmpty.setText(2131428172);
    this.mTxtEmpty.setVisibility(0);
  }

  private void showLoading(boolean paramBoolean)
  {
    if ((this.mPoiList.size() == 0) && (!paramBoolean))
    {
      this.mListView.setVisibility(8);
      this.mLayoutEmpty.setVisibility(0);
      this.mEmptyProBar.setVisibility(0);
      this.mTxtEmpty.setVisibility(0);
      this.mTxtEmpty.setText(2131428171);
      return;
    }
    this.mLayoutEmpty.setVisibility(8);
    this.mListView.setVisibility(0);
  }

  private void showLocating()
  {
    if (this.mPoiList.isEmpty())
    {
      this.mListView.setVisibility(8);
      this.mLayoutEmpty.setVisibility(0);
      this.mEmptyProBar.setVisibility(0);
      this.mTxtEmpty.setText(2131428174);
      this.mTxtEmpty.setVisibility(0);
    }
  }

  private void showPoiList()
  {
    this.mLayoutEmpty.setVisibility(8);
    this.mListView.setVisibility(0);
  }

  private void stopPullToRefresh()
  {
    if ((this.mDownView != null) && (this.mDownView.isFrefrshing()))
      this.mDownView.onRefreshComplete();
  }

  public void finish()
  {
    super.finish();
    AnimationUtil.finishActivity(getActivity(), 2);
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if (paramInt1 == 12)
      if (paramInt2 == -1)
      {
        String[] arrayOfString = paramIntent.getStringArrayExtra("params");
        if ((arrayOfString != null) && (arrayOfString.length >= 4))
          IntentUtil.showLbsPositionDetailFragment(this, arrayOfString[0], arrayOfString[1], arrayOfString[2], arrayOfString[3]);
      }
    do
    {
      return;
      finish();
      return;
    }
    while ((paramInt2 != -1) || (paramInt1 != 11) || (paramIntent == null));
    Bundle localBundle = paramIntent.getExtras();
    IntentUtil.showLbsPositionDetailFragment(this, localBundle.getString("poiid"), localBundle.getString("poiname"), "", localBundle.getString("location"));
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
    if (this.mEditSearch.getText().length() > 0)
      LbsModel.getInstance().clear();
    this.mListView.setAdapter(null);
    this.mPoiList.clear();
    super.onDestroy();
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    if (paramView != this.mHeaderView)
    {
      if (paramView != this.mFooterView)
        break label101;
      Intent localIntent = new Intent(getActivity(), LbsAddLocationFragment.class);
      localIntent.putExtra("poi_name", this.mEditSearch.getText().toString());
      localIntent.putExtra("lon", this.lon);
      localIntent.putExtra("lat", this.lat);
      localIntent.putExtra("at_firends", convertUserInfoList2String(this.mFriendsList));
      AnimationUtil.startFragment(this, localIntent, 1);
    }
    label101: PoiItem localPoiItem;
    while (true)
    {
      return;
      try
      {
        localPoiItem = (PoiItem)this.mPoiList.get((int)paramLong);
        if (localPoiItem == null)
          continue;
        if (!"view_more".equals(localPoiItem.poiId))
          break;
        onViewMoreClicked(paramView);
        return;
      }
      catch (Exception localException1)
      {
        localException1.printStackTrace();
        return;
      }
    }
    while (true)
    {
      try
      {
        double d = Double.parseDouble(localPoiItem.distance);
        if (d < 2000.0D)
        {
          i = 1;
          if (i == 0)
            continue;
          String str = convertUserInfoList2String(this.mFriendsList);
          IntentUtil.showCheckInFragment(getActivity(), this, localPoiItem.poiId, localPoiItem.name, localPoiItem.location, localPoiItem.x, localPoiItem.y, null, true, str);
          return;
        }
      }
      catch (Exception localException2)
      {
        localException2.printStackTrace();
        i = 0;
        continue;
        Toast.makeText(getActivity(), 2131428170, 0).show();
        return;
      }
      int i = 0;
    }
  }

  public void onResume()
  {
    getRefreshLocationProxy().reworkOnResume();
    super.onResume();
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
      this.mFriendsList = ((ArrayList)localBundle.getSerializable("at_firends"));
      this.mIsShowPoiDetail = localBundle.getBoolean("is_show_poi_detail", true);
    }
    this.mDownView = ((PullToRefreshView)findViewById(2131361814));
    this.mDownView.setPullToRefreshListener(this);
    setOtherViews();
    setTitleBar();
    setListView();
    updateList();
    getRefreshLocationProxy().refreshLocation(false);
    KXUBLog.log("homepage_sign");
  }

  public void requestFinish()
  {
  }

  protected void setTitleBar()
  {
    ImageView localImageView = (ImageView)findViewById(2131362914);
    localImageView.setVisibility(0);
    localImageView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        PoiListFragment.this.finish();
      }
    });
    ((ImageView)findViewById(2131362919)).setVisibility(8);
    TextView localTextView = (TextView)findViewById(2131362920);
    localTextView.setText(2131428364);
    localTextView.setVisibility(0);
    this.mBtnScan = ((ImageView)findViewById(2131362928));
    this.mBtnScan.setImageResource(2130838484);
    this.mBtnScan.setVisibility(0);
    if (Setting.getInstance().getCType().startsWith("062"))
      this.mBtnScan.setVisibility(8);
    this.mBtnScan.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        UserHabitUtils.getInstance(PoiListFragment.this.getActivity()).addUserHabit("click_checkin_erweima");
        Intent localIntent = new Intent();
        localIntent.setClass(PoiListFragment.this.getActivity(), CaptureActivity.class);
        PoiListFragment.this.getActivity().startActivityForResult(localIntent, 11);
      }
    });
  }

  void updateList()
  {
    this.mPoiList.clear();
    this.mPoiList.add(null);
    LbsModel localLbsModel = LbsModel.getInstance();
    ArrayList localArrayList = localLbsModel.getPoiList();
    if ((localArrayList != null) && (localArrayList.size() > 0))
      this.mPoiList.addAll(localArrayList);
    if (this.mPoiList.size() < localLbsModel.getTotal())
    {
      PoiItem localPoiItem = new PoiItem();
      localPoiItem.poiId = "view_more";
      this.mPoiList.add(localPoiItem);
    }
    this.mAdapter.notifyDataSetChanged();
  }

  private class GetPoiListTask extends KXFragment.KXAsyncTask<String, Void, Boolean>
  {
    private int mStart = 0;
    private boolean mTips = true;

    private GetPoiListTask()
    {
      super();
    }

    protected Boolean doInBackgroundA(String[] paramArrayOfString)
    {
      if ((paramArrayOfString == null) || (paramArrayOfString.length < 3))
        return Boolean.valueOf(false);
      Location localLocation;
      try
      {
        this.mStart = Integer.parseInt(paramArrayOfString[1]);
        localLocation = LocationService.getLocationService().lastBestMapABCLocation;
        if (LocationService.isMapABCLocation(PoiListFragment.this.currentLocation))
        {
          str1 = "0";
          str2 = "0";
          str3 = String.valueOf(PoiListFragment.this.currentLocation.getLongitude());
          str4 = String.valueOf(PoiListFragment.this.currentLocation.getLatitude());
          return Boolean.valueOf(LbsEngine.getInstance().getPois(PoiListFragment.this.getActivity().getApplicationContext(), str1, str2, str3, str4, paramArrayOfString[0], this.mStart, 50, paramArrayOfString[2]));
        }
      }
      catch (Exception localException)
      {
        while (true)
          localException.printStackTrace();
        if (PoiListFragment.this.currentLocation == null)
          return Boolean.valueOf(false);
        String str1 = String.valueOf(PoiListFragment.this.currentLocation.getLongitude());
        String str2 = String.valueOf(PoiListFragment.this.currentLocation.getLatitude());
        if (localLocation != null)
          break label193;
      }
      String str3 = null;
      label183: String str4 = null;
      if (localLocation == null);
      while (true)
      {
        break;
        label193: str3 = String.valueOf(localLocation.getLongitude());
        break label183;
        str4 = String.valueOf(localLocation.getLatitude());
      }
    }

    protected void onPostExecuteA(Boolean paramBoolean)
    {
      LbsModel localLbsModel = LbsModel.getInstance();
      ArrayList localArrayList = localLbsModel.getPoiList();
      if (paramBoolean.booleanValue())
        if ((localArrayList == null) || (localArrayList.size() <= 0));
      do
        while (true)
        {
          try
          {
            if (PoiListFragment.this.mViewMorePoi == null)
              continue;
            PoiListFragment.PoiItemCache localPoiItemCache = (PoiListFragment.PoiItemCache)PoiListFragment.this.mViewMorePoi.getTag();
            int i = 0;
            if (localPoiItemCache == null)
              continue;
            if (localPoiItemCache.mProgressBar.getVisibility() != 0)
              continue;
            i = 1;
            if ((this.mStart != 0) && (i == 0))
              continue;
            PoiListFragment.this.updateList();
            if ((localArrayList == null) || (localArrayList.size() >= localLbsModel.getTotal()))
              continue;
            PoiListFragment.this.mGetPoiListTask = null;
            PoiListFragment.this.refreshData(localArrayList.size(), false);
            if ((localPoiItemCache == null) || (i == 0))
              continue;
            localPoiItemCache.showViewMore(false);
            PoiListFragment.this.showPoiList();
            PoiListFragment.this.stopPullToRefresh();
            return;
            i = 0;
            continue;
            PoiListFragment.this.mViewMorePoi = null;
            i = 0;
            localPoiItemCache = null;
            continue;
          }
          catch (Exception localException)
          {
            localException.printStackTrace();
            continue;
          }
          PoiListFragment.this.updateList();
          PoiListFragment.this.showPoiList();
          Toast.makeText(PoiListFragment.this.getActivity(), 2131428173, 0).show();
        }
      while (!this.mTips);
      if ((this.mStart > 0) && (PoiListFragment.this.mPoiList.size() > 0))
        PoiListFragment.this.showPoiList();
      while (true)
      {
        Toast.makeText(PoiListFragment.this.getActivity(), 2131427547, 0).show();
        break;
        PoiListFragment.this.showError();
      }
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }

    public void showErrorToast(boolean paramBoolean)
    {
      this.mTips = paramBoolean;
    }
  }

  private class PoiDetailListener
    implements View.OnClickListener
  {
    private PoiDetailListener()
    {
    }

    public void onClick(View paramView)
    {
      try
      {
        PoiItem localPoiItem = (PoiItem)paramView.getTag();
        if (localPoiItem == null)
          return;
        if (!"view_more".equals(localPoiItem.poiId))
        {
          Intent localIntent = new Intent(PoiListFragment.this.getActivity(), PoiFragment.class);
          localIntent.putExtra("poi_id", localPoiItem.poiId);
          localIntent.putExtra("poi_name", localPoiItem.name);
          localIntent.putExtra("poi_location", localPoiItem.location);
          AnimationUtil.startFragment(PoiListFragment.this, localIntent, 1);
          return;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }

  private class PoiItemCache
  {
    public ImageView mImgIcon;
    public ImageView mIsMyCheckImgIcon;
    public ProgressBar mProgressBar;
    public TextView mTxtLocation;
    public TextView mTxtName;
    public TextView mTxtRight;

    public PoiItemCache(View arg2)
    {
      Object localObject;
      if (localObject == null)
        return;
      this.mImgIcon = ((ImageView)localObject.findViewById(2131362957));
      this.mTxtRight = ((TextView)localObject.findViewById(2131363452));
      this.mTxtName = ((TextView)localObject.findViewById(2131362008));
      this.mTxtLocation = ((TextView)localObject.findViewById(2131362009));
      this.mProgressBar = ((ProgressBar)localObject.findViewById(2131363451));
      this.mIsMyCheckImgIcon = ((ImageView)localObject.findViewById(2131363453));
    }

    private void showAddNewPoi()
    {
      this.mImgIcon.setImageResource(2130838471);
      this.mImgIcon.setVisibility(0);
      this.mTxtRight.setVisibility(8);
      this.mTxtLocation.setVisibility(8);
      this.mProgressBar.setVisibility(8);
      this.mTxtName.setTextColor(PoiListFragment.this.getResources().getColor(2130839419));
      this.mTxtName.setText(2131428180);
      this.mTxtRight.setOnClickListener(null);
    }

    public void showPoiItem(PoiItem paramPoiItem)
    {
      this.mProgressBar.setVisibility(8);
      if (paramPoiItem == null)
      {
        this.mImgIcon.setVisibility(4);
        this.mTxtName.setText(null);
        this.mTxtLocation.setText(null);
        this.mTxtRight.setVisibility(4);
        return;
      }
      this.mImgIcon.setImageResource(2130838516);
      this.mImgIcon.setVisibility(0);
      Resources localResources = PoiListFragment.this.getResources();
      this.mTxtName.setTextColor(localResources.getColor(2130839393));
      this.mTxtName.setText(paramPoiItem.name);
      if (!TextUtils.isEmpty(paramPoiItem.location))
      {
        this.mTxtLocation.setTextColor(localResources.getColor(2130839395));
        this.mTxtLocation.setText(paramPoiItem.location);
        this.mTxtLocation.setVisibility(0);
        if (!paramPoiItem.hasActivity)
          break label230;
        this.mTxtRight.setBackgroundResource(2130838470);
        this.mTxtRight.setText(2131428191);
        label162: if (!PoiListFragment.this.mIsShowPoiDetail)
          break label250;
        this.mTxtRight.setVisibility(0);
        this.mTxtRight.setTag(paramPoiItem);
        this.mTxtRight.setOnClickListener(PoiListFragment.this.mPoiDetailListener);
      }
      while (true)
      {
        if (!paramPoiItem.isMyChecked)
          break label270;
        this.mIsMyCheckImgIcon.setVisibility(0);
        return;
        this.mTxtLocation.setVisibility(8);
        break;
        label230: this.mTxtRight.setBackgroundResource(2130838501);
        this.mTxtRight.setText(null);
        break label162;
        label250: this.mTxtRight.setVisibility(8);
        this.mTxtRight.setOnClickListener(null);
      }
      label270: this.mIsMyCheckImgIcon.setVisibility(8);
    }

    public void showViewMore(boolean paramBoolean)
    {
      this.mImgIcon.setVisibility(8);
      this.mTxtRight.setVisibility(8);
      this.mTxtLocation.setVisibility(8);
      ProgressBar localProgressBar = this.mProgressBar;
      int i;
      TextView localTextView;
      Resources localResources;
      if (paramBoolean)
      {
        i = 0;
        localProgressBar.setVisibility(i);
        localTextView = this.mTxtName;
        localResources = PoiListFragment.this.getResources();
        if (!paramBoolean)
          break label101;
      }
      label101: for (int j = 2130839395; ; j = 2130839419)
      {
        localTextView.setTextColor(localResources.getColor(j));
        this.mTxtName.setText(2131428176);
        this.mTxtRight.setOnClickListener(null);
        return;
        i = 4;
        break;
      }
    }
  }

  private class PoiListAdapter extends BaseAdapter
  {
    private PoiListAdapter()
    {
    }

    public int getCount()
    {
      return PoiListFragment.this.mPoiList.size();
    }

    public Object getItem(int paramInt)
    {
      if ((paramInt < 0) || (paramInt >= PoiListFragment.this.mPoiList.size()))
        return null;
      return PoiListFragment.this.mPoiList.get(paramInt);
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      PoiItem localPoiItem = (PoiItem)getItem(paramInt);
      if (localPoiItem == null)
        return PoiListFragment.this.mLbsFirstView;
      PoiListFragment.PoiItemCache localPoiItemCache;
      if ((paramView == null) || (PoiListFragment.this.mLbsFirstView == paramView))
      {
        paramView = PoiListFragment.this.getActivity().getLayoutInflater().inflate(2130903293, null);
        localPoiItemCache = new PoiListFragment.PoiItemCache(PoiListFragment.this, paramView);
        paramView.setTag(localPoiItemCache);
      }
      while (localPoiItemCache == null)
      {
        return paramView;
        localPoiItemCache = (PoiListFragment.PoiItemCache)paramView.getTag();
      }
      if (localPoiItem != null)
      {
        if (!"view_more".equals(localPoiItem.poiId))
          break label120;
        localPoiItemCache.showViewMore(false);
      }
      while (true)
      {
        return paramView;
        label120: localPoiItemCache.showPoiItem(localPoiItem);
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.PoiListFragment
 * JD-Core Version:    0.6.0
 */