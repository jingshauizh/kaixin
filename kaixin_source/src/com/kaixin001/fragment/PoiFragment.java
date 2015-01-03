package com.kaixin001.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Location;
import android.net.Uri;
import android.nfc.Tag;
import android.os.AsyncTask.Status;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Message;
import android.os.Vibrator;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.activity.LoginActivity;
import com.kaixin001.activity.OnViewMoreClickListener;
import com.kaixin001.adapter.CheckInAdapter;
import com.kaixin001.businesslogic.AddFriend;
import com.kaixin001.businesslogic.AddFriendResult;
import com.kaixin001.businesslogic.ShowMenuDialog;
import com.kaixin001.engine.FriendsEngine;
import com.kaixin001.engine.LbsEngine;
import com.kaixin001.item.CheckInItem;
import com.kaixin001.item.CheckInUser;
import com.kaixin001.item.PoiItem;
import com.kaixin001.lbs.LocationService;
import com.kaixin001.lbs.RefreshLocationProxy;
import com.kaixin001.lbs.RefreshLocationProxy.IRefreshLocationCallback;
import com.kaixin001.model.FriendsModel;
import com.kaixin001.model.FriendsModel.Friend;
import com.kaixin001.model.KaixinUser;
import com.kaixin001.model.LbsModel.CheckInList;
import com.kaixin001.model.User;
import com.kaixin001.nfc.NfcNdefMessage;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.view.PullToRefreshView;
import com.kaixin001.view.PullToRefreshView.PullToRefreshListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import org.json.JSONObject;

public class PoiFragment extends BaseFragment
  implements AdapterView.OnItemClickListener, OnViewMoreClickListener, AdapterView.OnItemLongClickListener, PullToRefreshView.PullToRefreshListener
{
  private static final String CHECKIN_TYPE_ALL = "0";
  public static final int MAX_NEARBY_DISTANCE = 2000;
  public static final String POI_ID = "poi_id";
  public static final String POI_LOCATION = "poi_location";
  public static final String POI_NAME = "poi_name";
  public static final String POI_NEARBY = "poi_nearby";
  private static final String TAG_VIEW_MORE = "view_more_checkin";
  HashMap<String, AddFriendResult> addFriendApplyChanges = new HashMap();
  private AddFriend addFriendUtil;
  public Location currentLocation;
  private boolean isNoLoginByNfc = false;
  private CheckInAdapter mAdapter;
  private final LbsModel.CheckInList mAdapterList = new LbsModel.CheckInList();
  private Dialog mAlertDialog = null;
  private Button mBtnActivity = null;
  private ImageView mBtnRight = null;
  private final LbsModel.CheckInList mBufferList = new LbsModel.CheckInList();
  private PullToRefreshView mDownView;
  private GetCheckInPeopleTask mGetCheckInTask = null;
  private GetPoiInfoTask mGetPoiInfoTask = null;
  private View mHeaderView = null;
  private ImageView mImgMap = null;
  private PoiItem mItem = null;
  private View mLayoutEmpty = null;
  private ListView mListView = null;
  private String mLocation = "";
  private String mName = "";
  private String mPoiId = "";
  private boolean mPoiNearby = false;
  private ProgressBar mRightProBar = null;
  private TextView mTxtCheckedInNum = null;
  private TextView mTxtLocalCheckIn = null;
  private TextView mTxtLocation = null;
  private TextView mTxtName = null;
  private TextView mTxtTitle = null;
  private ShowMenuDialog menuUtil;
  RefreshLocationProxy refreshLocationProxy = null;

  private void cancelTask()
  {
    if ((this.mGetPoiInfoTask != null) && (!this.mGetPoiInfoTask.isCancelled()) && (this.mGetPoiInfoTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.mGetPoiInfoTask.cancel(true);
      LbsEngine.getInstance().cancel();
      this.mGetPoiInfoTask = null;
    }
    if ((this.mGetCheckInTask != null) && (!this.mGetCheckInTask.isCancelled()) && (this.mGetCheckInTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.mGetCheckInTask.cancel(true);
      LbsEngine.getInstance().cancel();
      this.mGetCheckInTask = null;
    }
  }

  private RefreshLocationProxy getRefreshLocationProxy()
  {
    if (this.refreshLocationProxy == null)
      this.refreshLocationProxy = new RefreshLocationProxy(getActivity(), new RefreshLocationProxy.IRefreshLocationCallback()
      {
        public void onBeginRefreshLocation()
        {
          if (!PoiFragment.this.checkNetworkAndHint(true))
            return;
          PoiFragment.this.showLoading();
        }

        public void onCancelRefreshLocation()
        {
          PoiFragment.this.stopPullToRefresh();
        }

        public void onLocationAvailable(Location paramLocation)
        {
          PoiFragment.this.refresh(paramLocation);
        }

        public void onLocationFailed()
        {
          PoiFragment.this.stopPullToRefresh();
          PoiFragment.this.showCheckedInList();
          Toast.makeText(PoiFragment.this.getActivity(), 2131427722, 0).show();
        }
      });
    return this.refreshLocationProxy;
  }

  private void initHeaderView()
  {
    if (this.mHeaderView != null)
      return;
    this.mHeaderView = getActivity().getLayoutInflater().inflate(2130903292, null);
    this.mImgMap = ((ImageView)this.mHeaderView.findViewById(2131363447));
    this.mTxtName = ((TextView)this.mHeaderView.findViewById(2131362008));
    this.mTxtLocation = ((TextView)this.mHeaderView.findViewById(2131362009));
    this.mTxtCheckedInNum = ((TextView)this.mHeaderView.findViewById(2131363448));
    this.mTxtCheckedInNum.setVisibility(8);
    this.mTxtLocalCheckIn = ((TextView)this.mHeaderView.findViewById(2131363450));
    this.mTxtLocalCheckIn.setVisibility(8);
    this.mBtnActivity = ((Button)this.mHeaderView.findViewById(2131363449));
    this.mBtnActivity.setVisibility(8);
  }

  private void refresh(Location paramLocation)
  {
    cancelTask();
    this.currentLocation = paramLocation;
    if (!super.checkNetworkAndHint(true))
    {
      stopPullToRefresh();
      return;
    }
    showLoading();
    this.mGetPoiInfoTask = new GetPoiInfoTask(null);
    GetPoiInfoTask localGetPoiInfoTask = this.mGetPoiInfoTask;
    String[] arrayOfString = new String[1];
    arrayOfString[0] = this.mPoiId;
    localGetPoiInfoTask.execute(arrayOfString);
  }

  private void refreshCheckInPeople(int paramInt, boolean paramBoolean)
  {
    if (!super.checkNetworkAndHint(paramBoolean))
      stopPullToRefresh();
    do
      return;
    while ((this.mGetCheckInTask != null) && (!this.mGetCheckInTask.isCancelled()) && (this.mGetCheckInTask.getStatus() != AsyncTask.Status.FINISHED));
    String[] arrayOfString = new String[3];
    arrayOfString[0] = this.mPoiId;
    arrayOfString[1] = "0";
    arrayOfString[2] = String.valueOf(paramInt);
    this.mGetCheckInTask = new GetCheckInPeopleTask(null);
    this.mGetCheckInTask.showTips(paramBoolean);
    this.mGetCheckInTask.execute(arrayOfString);
  }

  private void setCheckInLayout()
  {
    View localView = getView().findViewById(2131363444);
    localView.findViewById(2131362020).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        PoiFragment.this.checkIn(PoiFragment.this.getDefaultCheckInWord());
      }
    });
    TextView localTextView = (TextView)localView.findViewById(2131362022);
    if (!this.mPoiNearby)
    {
      localTextView.setTextColor(getResources().getColor(2130839395));
      localTextView.setText(2131428170);
      return;
    }
    localTextView.setTextColor(getResources().getColor(2130839419));
    localTextView.setText(2131428169);
  }

  private void setListView(View paramView)
  {
    initHeaderView();
    this.mAdapter = new CheckInAdapter(this, 2130903298, this.mAdapterList);
    this.mAdapter.setCurrentPoiId(this.mPoiId);
    this.mListView = ((ListView)paramView.findViewById(2131363445));
    this.mListView.addHeaderView(this.mHeaderView);
    this.mListView.setAdapter(this.mAdapter);
    this.mListView.setOnItemClickListener(this);
    this.mListView.setOnItemLongClickListener(this);
    this.mListView.setVisibility(0);
    this.mListView.setBackgroundResource(2130839400);
    this.mListView.setHeaderDividersEnabled(false);
    this.mListView.setDivider(null);
    this.mListView.setDividerHeight(0);
    this.mListView.setOnItemClickListener(this);
    this.mTxtName.setText(this.mName);
    this.mTxtLocation.setText(this.mLocation);
  }

  private void setOtherViews(View paramView)
  {
    setCheckInLayout();
    this.mLayoutEmpty = paramView.findViewById(2131363102);
    this.mLayoutEmpty.setVisibility(8);
    ((TextView)this.mLayoutEmpty.findViewById(2131361972)).setText(2131427599);
  }

  private void showCheckedInList()
  {
    this.mListView.setVisibility(0);
    this.mLayoutEmpty.setVisibility(8);
    this.mRightProBar.setVisibility(8);
    this.mBtnRight.setImageResource(2130838834);
    this.mTxtCheckedInNum.setVisibility(0);
  }

  private void showLoading()
  {
    this.mRightProBar.setVisibility(0);
    this.mBtnRight.setImageResource(0);
    this.mTxtCheckedInNum.setVisibility(8);
    if (this.mItem == null)
    {
      this.mListView.setVisibility(8);
      this.mLayoutEmpty.setVisibility(0);
    }
  }

  private void stopPullToRefresh()
  {
    if ((this.mDownView != null) && (this.mDownView.isFrefrshing()))
      this.mDownView.onRefreshComplete();
  }

  private void updateListView()
  {
    this.mAdapterList.clear();
    Iterator localIterator;
    if (this.mBufferList.size() > 0)
      localIterator = this.mBufferList.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
      {
        if (this.mBufferList.size() < this.mBufferList.getTotal())
        {
          CheckInItem localCheckInItem2 = new CheckInItem();
          localCheckInItem2.wid = "view_more_checkin";
          this.mAdapterList.add(localCheckInItem2);
        }
        this.mAdapter.notifyDataSetChanged();
        return;
      }
      CheckInItem localCheckInItem1 = (CheckInItem)localIterator.next();
      if (TextUtils.isEmpty(localCheckInItem1.poiName))
        localCheckInItem1.poiName = this.mName;
      this.mAdapterList.add(localCheckInItem1);
    }
  }

  void checkIn(String paramString)
  {
    if ((!this.mPoiNearby) || (this.mItem == null))
    {
      Toast.makeText(getActivity(), 2131428170, 0).show();
      return;
    }
    String str1 = this.mItem.x;
    String str2 = this.mItem.y;
    IntentUtil.showCheckInFragment(getActivity(), this, this.mPoiId, this.mName, this.mLocation, str1, str2, null, false, paramString);
  }

  public void finish()
  {
    super.finish();
    AnimationUtil.finishActivity(getActivity(), 2);
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

  String getDefaultCheckInWord()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.mItem == null)
      return null;
    if (!TextUtils.isEmpty(this.mItem.mActivityDefaultWord))
      localStringBuffer.append(this.mItem.mActivityDefaultWord);
    if (this.mItem.mAtNum > 0);
    try
    {
      FriendsModel localFriendsModel = FriendsModel.getInstance();
      ArrayList localArrayList1 = localFriendsModel.getFriends();
      if ((localArrayList1 == null) || (localArrayList1.size() == 0))
        FriendsEngine.getInstance().loadFriendsCache(getActivity(), User.getInstance().getUID());
      localArrayList2 = localFriendsModel.getFriends();
      HashMap localHashMap;
      int i;
      if ((localArrayList2 != null) && (localArrayList2.size() >= this.mItem.mAtNum))
      {
        localHashMap = new HashMap();
        i = localArrayList2.size();
      }
      while (true)
      {
        if (localHashMap.size() >= this.mItem.mAtNum)
        {
          localIterator = localHashMap.entrySet().iterator();
          boolean bool = localIterator.hasNext();
          if (bool)
            break;
          return localStringBuffer.toString();
        }
        int j = (int)(Math.random() * i);
        localHashMap.put(String.valueOf(j), Integer.valueOf(j));
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        ArrayList localArrayList2;
        Iterator localIterator;
        localException.printStackTrace();
        continue;
        FriendsModel.Friend localFriend = (FriendsModel.Friend)localArrayList2.get(((Integer)((Map.Entry)localIterator.next()).getValue()).intValue());
        localStringBuffer.append(" ").append("@").append(localFriend.getFuid()).append("(#").append(localFriend.getFname()).append("#)");
      }
    }
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
    if ((paramInt1 == 12) && (paramInt2 == -1))
    {
      refreshCheckInPeople(0, true);
      return;
    }
    if ((paramInt1 == 99) && (paramInt2 == -1))
    {
      Bundle localBundle = paramIntent.getExtras();
      getAddFriendUtil().addNewFriendResult(localBundle.getString("fuid"));
      return;
    }
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Bundle localBundle = getArguments();
    if (localBundle != null)
    {
      this.mPoiId = localBundle.getString("poi_id");
      this.mName = localBundle.getString("poi_name");
      this.mLocation = localBundle.getString("poi_location");
      this.mPoiNearby = localBundle.getBoolean("poi_nearby", true);
    }
    String str;
    if ((Build.VERSION.SDK_INT >= 10) && (getIntent() != null) && ((Tag)getIntent().getParcelableExtra("android.nfc.extra.TAG") != null))
    {
      str = NfcNdefMessage.printNdefMessage(getIntent());
      if (str == null);
    }
    try
    {
      JSONObject localJSONObject = new JSONObject(str);
      this.mPoiId = localJSONObject.getString("poiid");
      this.mName = localJSONObject.getString("poiname");
      this.mLocation = localJSONObject.getString("location");
      this.mPoiNearby = true;
      this.isNoLoginByNfc = false;
      if (TextUtils.isEmpty(User.getInstance().getOauthToken()))
      {
        this.isNoLoginByNfc = true;
        Intent localIntent = new Intent();
        localIntent.setClass(getActivity(), LoginActivity.class);
        localIntent.putExtra("from_share_text", true);
        startActivity(localIntent);
        getActivity().finish();
      }
      return;
    }
    catch (Exception localException)
    {
      while (true)
        KXLog.e("NFC CHECK IN", "error nfcMessage:" + str);
    }
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903291, paramViewGroup, false);
  }

  public void onDestroy()
  {
    cancelTask();
    this.mListView.setAdapter(null);
    this.mAdapterList.clear();
    this.mBufferList.clear();
    super.onDestroy();
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    if ((paramLong < 0L) || (paramLong >= this.mAdapterList.size()))
      return;
    CheckInItem localCheckInItem = (CheckInItem)this.mAdapterList.get((int)paramLong);
    if ("view_more_checkin".equals(localCheckInItem.wid))
    {
      onViewMoreClick();
      return;
    }
    IntentUtil.showLbsCheckInCommentFragment(this, localCheckInItem);
  }

  public boolean onItemLongClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    if ((paramLong < 0L) || (paramLong >= this.mAdapterList.size()))
      return false;
    CheckInItem localCheckInItem = (CheckInItem)this.mAdapterList.get((int)paramLong);
    if ("view_more_checkin".equals(localCheckInItem.wid))
      return false;
    Vibrator localVibrator = (Vibrator)getActivity().getSystemService("vibrator");
    localVibrator.vibrate(1000L);
    localVibrator.vibrate(new long[] { 100L, 300L, 150L, 400L }, -1);
    KaixinUser localKaixinUser = localCheckInItem.checkInUser.user;
    ArrayList localArrayList1 = new ArrayList();
    if (localKaixinUser.relation > 0)
    {
      ArrayList localArrayList2 = new ArrayList(Arrays.asList(getResources().getStringArray(2131492899)));
      localArrayList1.add(Integer.valueOf(0));
      localArrayList1.add(Integer.valueOf(2));
      localArrayList1.add(Integer.valueOf(3));
      localArrayList1.add(Integer.valueOf(4));
      getShowMenuDialog().showMenuOfItem(localCheckInItem, localArrayList2, localArrayList1, null);
    }
    while (true)
    {
      return true;
      if (localKaixinUser.relation > 0)
        continue;
      String str = localCheckInItem.checkInUser.user.uid;
      AddFriendResult localAddFriendResult = (AddFriendResult)this.addFriendApplyChanges.get(str);
      if ((localAddFriendResult == null) || (localAddFriendResult.code == 0))
      {
        ArrayList localArrayList3 = new ArrayList(Arrays.asList(getResources().getStringArray(2131492900)));
        getShowMenuDialog().showMenuOfItem(localCheckInItem, localArrayList3, null, getAddFriendUtil());
        continue;
      }
      ArrayList localArrayList4 = new ArrayList(Arrays.asList(getResources().getStringArray(2131492899)));
      localArrayList1.add(Integer.valueOf(0));
      localArrayList1.add(Integer.valueOf(2));
      localArrayList1.add(Integer.valueOf(3));
      localArrayList1.add(Integer.valueOf(4));
      getShowMenuDialog().showMenuOfItem(localCheckInItem, localArrayList4, localArrayList1, null);
    }
  }

  public void onResume()
  {
    if ((!getRefreshLocationProxy().reworkOnResume()) && (!TextUtils.isEmpty(User.getInstance().getOauthToken())) && (this.isNoLoginByNfc))
    {
      getRefreshLocationProxy().refreshLocation(false);
      this.isNoLoginByNfc = false;
    }
    super.onResume();
  }

  public void onUpdate()
  {
    if ((this.mRightProBar != null) && (this.mRightProBar.getVisibility() != 0))
      getRefreshLocationProxy().refreshLocation(true);
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    this.mDownView = ((PullToRefreshView)paramView.findViewById(2131361814));
    this.mDownView.setPullToRefreshListener(this);
    setOtherViews(paramView);
    setTitleBar(paramView);
    setListView(paramView);
    if (!TextUtils.isEmpty(User.getInstance().getOauthToken()))
      getRefreshLocationProxy().refreshLocation(false);
  }

  public void onViewMoreClick()
  {
    boolean bool = false;
    int i = this.mAdapterList.size();
    if (i > 0);
    try
    {
      CheckInItem localCheckInItem = (CheckInItem)this.mAdapterList.get(-1 + this.mAdapterList.size());
      if (localCheckInItem != null)
      {
        CheckInUser localCheckInUser = localCheckInItem.checkInUser;
        if (localCheckInUser != null);
      }
      else
      {
        i--;
      }
      if (this.mBufferList.size() > i)
      {
        updateListView();
        showCheckedInList();
        if (this.mBufferList.size() < this.mBufferList.getTotal())
        {
          this.mAdapter.showLoadingFooter(bool);
          refreshCheckInPeople(this.mBufferList.size(), bool);
        }
        return;
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        localException.printStackTrace();
        continue;
        bool = true;
      }
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
        PoiFragment.this.finish();
      }
    });
    this.mBtnRight = ((ImageView)paramView.findViewById(2131362928));
    this.mBtnRight.setImageResource(2130838834);
    this.mBtnRight.setVisibility(0);
    this.mBtnRight.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        if ((PoiFragment.this.mRightProBar != null) && (PoiFragment.this.mRightProBar.getVisibility() != 0))
          PoiFragment.this.getRefreshLocationProxy().refreshLocation(true);
      }
    });
    ((ImageView)paramView.findViewById(2131362919)).setVisibility(8);
    this.mTxtTitle = ((TextView)paramView.findViewById(2131362920));
    this.mTxtTitle.setText(this.mName);
    this.mTxtTitle.setVisibility(0);
    this.mRightProBar = ((ProgressBar)paramView.findViewById(2131362929));
  }

  private class GetCheckInPeopleTask extends KXFragment.KXAsyncTask<String, Void, Boolean>
  {
    private int mStart = 0;
    private boolean mTips = true;

    private GetCheckInPeopleTask()
    {
      super();
    }

    protected Boolean doInBackgroundA(String[] paramArrayOfString)
    {
      if ((paramArrayOfString == null) || (paramArrayOfString.length != 3))
        return Boolean.valueOf(false);
      try
      {
        this.mStart = Integer.parseInt(paramArrayOfString[2]);
        return Boolean.valueOf(LbsEngine.getInstance().getPoiCheckIn(PoiFragment.this, paramArrayOfString[0], paramArrayOfString[1], this.mStart, 20, PoiFragment.this.mBufferList));
      }
      catch (Exception localException)
      {
        while (true)
          localException.printStackTrace();
      }
    }

    protected void onPostExecuteA(Boolean paramBoolean)
    {
      if (paramBoolean.booleanValue())
      {
        if ((this.mStart == 0) || (PoiFragment.this.mAdapter.isFooterShowLoading()))
        {
          PoiFragment.this.updateListView();
          PoiFragment.this.showCheckedInList();
          if (PoiFragment.this.mBufferList.size() < PoiFragment.this.mBufferList.getTotal())
          {
            PoiFragment.this.mGetCheckInTask = null;
            PoiFragment.this.refreshCheckInPeople(PoiFragment.this.mBufferList.size(), false);
          }
          PoiFragment.this.mAdapter.showLoadingFooter(false);
        }
        if (PoiFragment.this.mBufferList.getTotal() == 0)
        {
          PoiFragment.this.mTxtCheckedInNum.setText(2131428183);
          if ((PoiFragment.this.mBufferList.isLocalCheckin()) || (PoiFragment.this.mBufferList.size() <= 0))
            break label365;
          PoiFragment.this.mTxtLocalCheckIn.setVisibility(0);
          label163: if ((PoiFragment.this.mItem == null) || (TextUtils.isEmpty(PoiFragment.this.mItem.mActivityId)))
            break label425;
          PoiFragment.this.mBtnActivity.setText(PoiFragment.this.mItem.mActivityName);
          PoiFragment.this.mBtnActivity.setVisibility(0);
          PoiFragment.this.mBtnActivity.setOnClickListener(new View.OnClickListener()
          {
            public void onClick(View paramView)
            {
              String[] arrayOfString;
              if (PoiFragment.this.mItem != null)
              {
                DialogUtil.dismissDialog(PoiFragment.this.mAlertDialog);
                arrayOfString = new String[2];
                if (PoiFragment.this.mItem.mJoin)
                  break label128;
                arrayOfString[0] = PoiFragment.this.getString(2131428188);
              }
              label128: Resources localResources;
              Object[] arrayOfObject;
              for (String str = PoiFragment.this.mItem.mActivityName; ; str = localResources.getString(2131428187, arrayOfObject))
              {
                arrayOfString[1] = PoiFragment.this.getString(2131428189);
                PoiFragment.this.mAlertDialog = DialogUtil.showSelectListDlg(PoiFragment.this.getActivity(), str, arrayOfString, new DialogInterface.OnClickListener()
                {
                  public void onClick(DialogInterface paramDialogInterface, int paramInt)
                  {
                    if (paramInt == 0)
                      if (!PoiFragment.this.mItem.mJoin)
                        PoiFragment.this.checkIn(PoiFragment.this.getDefaultCheckInWord());
                    do
                    {
                      return;
                      Intent localIntent = new Intent(PoiFragment.this.getActivity(), ActivityAwardsFragment.class);
                      PoiFragment.this.startFragment(localIntent, true, 1);
                      return;
                    }
                    while (1 != paramInt);
                    IntentUtil.showWebPage(PoiFragment.this.getActivity(), PoiFragment.this, PoiFragment.this.mItem.mWapUrl, null);
                  }
                }
                , 1);
                return;
                arrayOfString[0] = PoiFragment.this.getString(2131428190);
                localResources = PoiFragment.this.getResources();
                arrayOfObject = new Object[1];
                arrayOfObject[0] = PoiFragment.access$1(PoiFragment.this).mActivityName;
              }
            }
          });
          if (this.mStart == 0)
          {
            Toast localToast = Toast.makeText(PoiFragment.this.getActivity(), 2131428259, 1);
            localToast.setGravity(17, 0, 0);
            localToast.show();
          }
        }
      }
      while (true)
      {
        PoiFragment.this.showCheckedInList();
        PoiFragment.this.stopPullToRefresh();
        return;
        if (PoiFragment.this.mBufferList.isLocalCheckin());
        for (int i = 2131428181; ; i = 2131428182)
        {
          TextView localTextView = PoiFragment.this.mTxtCheckedInNum;
          String str = PoiFragment.this.getString(i);
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = Integer.valueOf(PoiFragment.access$13(PoiFragment.this).getTotal());
          localTextView.setText(String.format(str, arrayOfObject));
          break;
        }
        label365: PoiFragment.this.mTxtLocalCheckIn.setVisibility(8);
        break label163;
        if (!this.mTips)
          break label163;
        if (this.mStart == 0)
          PoiFragment.this.mTxtCheckedInNum.setVisibility(8);
        Toast.makeText(PoiFragment.this.getActivity(), 2131427547, 0).show();
        break label163;
        label425: PoiFragment.this.mBtnActivity.setVisibility(8);
      }
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }

    public void showTips(boolean paramBoolean)
    {
      this.mTips = paramBoolean;
    }
  }

  private class GetPoiInfoTask extends KXFragment.KXAsyncTask<String, Void, Boolean>
  {
    private GetPoiInfoTask()
    {
      super();
    }

    public String addVerify4Url(String paramString)
    {
      if (TextUtils.isEmpty(paramString))
        paramString = null;
      String str;
      do
      {
        return paramString;
        str = User.getInstance().getWapVerify();
      }
      while ((str != null) && (paramString.contains(str)));
      StringBuilder localStringBuilder = new StringBuilder(paramString);
      if (paramString.contains("?"))
        localStringBuilder.append("&verify=").append(str);
      while (true)
      {
        return localStringBuilder.toString();
        localStringBuilder.append("?verify=").append(str);
      }
    }

    protected Boolean doInBackgroundA(String[] paramArrayOfString)
    {
      if ((paramArrayOfString == null) || (paramArrayOfString.length != 1))
        return Boolean.valueOf(false);
      String str1;
      String str2;
      String str3;
      String str4;
      if (LocationService.isMapABCLocation(PoiFragment.this.currentLocation))
      {
        str1 = "0";
        str2 = "0";
        str3 = String.valueOf(PoiFragment.this.currentLocation.getLongitude());
        str4 = String.valueOf(PoiFragment.this.currentLocation.getLatitude());
        PoiFragment.this.mItem = LbsEngine.getInstance().getPoiInfo(PoiFragment.this.getActivity().getApplicationContext(), paramArrayOfString[0], str1, str2, str3, str4);
        if (PoiFragment.this.mItem == null)
          return Boolean.valueOf(true);
      }
      else
      {
        Location localLocation = LocationService.getLocationService().lastBestMapABCLocation;
        str1 = String.valueOf(PoiFragment.this.currentLocation.getLongitude());
        str2 = String.valueOf(PoiFragment.this.currentLocation.getLatitude());
        if (localLocation == null)
        {
          str3 = null;
          label156: str4 = null;
          if (localLocation != null)
            break label178;
        }
        while (true)
        {
          break;
          str3 = String.valueOf(localLocation.getLongitude());
          break label156;
          label178: str4 = String.valueOf(localLocation.getLatitude());
        }
      }
      return Boolean.valueOf(false);
    }

    protected void onPostExecuteA(Boolean paramBoolean)
    {
      if (PoiFragment.this.mItem != null)
      {
        if (!TextUtils.isEmpty(PoiFragment.this.mItem.name))
        {
          PoiFragment.this.mName = PoiFragment.this.mItem.name;
          PoiFragment.this.mTxtName.setText(PoiFragment.this.mItem.name);
          PoiFragment.this.mTxtTitle.setText(PoiFragment.this.mName);
        }
        if (!TextUtils.isEmpty(PoiFragment.this.mItem.location))
        {
          PoiFragment.this.mLocation = PoiFragment.this.mItem.location;
          PoiFragment.this.mTxtLocation.setText(PoiFragment.this.mItem.location);
          PoiFragment.this.mTxtLocation.setVisibility(0);
        }
        PoiFragment.this.displayPicture(PoiFragment.this.mImgMap, PoiFragment.this.mItem.mapUrl, 0);
        if (PoiFragment.this.mItem.mapUrl != null)
        {
          String str = "geo:" + PoiFragment.this.mItem.y + "," + PoiFragment.this.mItem.x;
          PoiFragment.this.mImgMap.setTag(str);
          PoiFragment.this.mImgMap.setOnClickListener(new View.OnClickListener()
          {
            public void onClick(View paramView)
            {
              try
              {
                Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse((String)paramView.getTag()));
                PoiFragment.this.startActivity(localIntent);
                return;
              }
              catch (Exception localException)
              {
                localException.printStackTrace();
              }
            }
          });
        }
        PoiFragment.this.mItem.mWapUrl = addVerify4Url(PoiFragment.this.mItem.mWapUrl);
      }
      try
      {
        double d = Double.parseDouble(PoiFragment.this.mItem.distance);
        if (d < 2000.0D);
        for (bool = true; ; bool = false)
        {
          if (bool != PoiFragment.this.mPoiNearby)
          {
            PoiFragment.this.mPoiNearby = bool;
            PoiFragment.this.setCheckInLayout();
          }
          PoiFragment.this.refreshCheckInPeople(0, true);
          return;
        }
      }
      catch (Exception localException)
      {
        while (true)
        {
          localException.printStackTrace();
          boolean bool = false;
        }
      }
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
 * Qualified Name:     com.kaixin001.fragment.PoiFragment
 * JD-Core Version:    0.6.0
 */