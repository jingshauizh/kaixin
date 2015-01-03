package com.kaixin001.fragment;

import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.location.Location;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.os.Message;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.activity.KXEnvironment;
import com.kaixin001.activity.MainActivity;
import com.kaixin001.adapter.MenuAdapter.MenuListAdapter;
import com.kaixin001.businesslogic.LogoutAndExitProxy;
import com.kaixin001.engine.NavigatorApplistEngine;
import com.kaixin001.engine.RecommendAppNotifyEngine;
import com.kaixin001.engine.UpdateEngine;
import com.kaixin001.engine.UploadTaskListEngine;
import com.kaixin001.lbs.LocationRequester;
import com.kaixin001.lbs.LocationService;
import com.kaixin001.menu.KaixinNavigator;
import com.kaixin001.menu.MenuCategory;
import com.kaixin001.menu.MenuCategoryId;
import com.kaixin001.menu.MenuItem;
import com.kaixin001.menu.MenuItem.OnClickListener;
import com.kaixin001.menu.MenuItemId;
import com.kaixin001.menu.TippedMenuItem;
import com.kaixin001.model.ChatModel;
import com.kaixin001.model.EventModel;
import com.kaixin001.model.FriendsModel;
import com.kaixin001.model.KaixinConst;
import com.kaixin001.model.KaixinModelTemplate;
import com.kaixin001.model.LbsModel;
import com.kaixin001.model.MessageCenterModel;
import com.kaixin001.model.NewsModel;
import com.kaixin001.model.PhotographModel;
import com.kaixin001.model.Setting;
import com.kaixin001.model.User;
import com.kaixin001.model.UserLevel;
import com.kaixin001.task.GetFriendsListTask;
import com.kaixin001.task.GetLbsActivityTask;
import com.kaixin001.task.GetLoginUserRealNameTask;
import com.kaixin001.task.GetPictureActionTask;
import com.kaixin001.task.GetRecommendAppNotifyTask;
import com.kaixin001.ugc.UGCMenuLayout;
import com.kaixin001.ugc.UGCMenuLayout.MenuClickListener;
import com.kaixin001.ugc.UGCMenuLayout.MenuItem;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.ImageDownloader.RoundCornerType;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.TimeUtil;
import com.kaixin001.util.UploadNotificationHandler;
import com.kaixin001.util.UserHabitUtils;
import com.kaixin001.view.KXSliderLayout2.OnSlideListener;
import java.io.File;
import java.util.ArrayList;

public class KaixinMenuListFragment extends BaseFragment
  implements UGCMenuLayout.MenuClickListener, KXSliderLayout2.OnSlideListener
{
  private static final int MAX_TIPS_COUNT = 99;
  private static final long MIN_TIMESPAN_CHECK_HAS_NEW = 60000L;
  private static final String TAG = "KaixinMenuListFragment";
  public static boolean isRefreshBackground = false;
  public static boolean mShowPictureAction = false;
  MenuCategory category;
  private GetFriendsListTask getFriendsListTask = null;
  private GetLoginUserRealNameTask getLoginUserRealNameTask = null;
  private boolean hasNew;
  private long lastCheckTime = 0L;
  private Button levelBtn;
  private View levelLayout;
  private View levelTaskFlag;
  private MenuAdapter.MenuListAdapter listAdapter;
  ArrayList<MenuCategory> localMenu;
  private LocationRequester locationRequester;
  private LogoutAndExitProxy logoutAndExit;
  private ListView lvNavigator;
  private GetLbsActivityTask mGetActivityNumTask = null;
  private GetPictureActionTask mGetPictureActionTask = null;
  private GetRecommendAppNotifyTask mGetRecommendAppNotifyTask = null;
  private ImageView mLayoutLeftBackground;
  private TextView mLevelId;
  private ImageView mLevelImage;
  private ImageView mSelectImageView;
  private TextView mSelectTextView;
  private RelativeLayout mSetting;
  private LinearLayout mUserGradeLayout;
  private RelativeLayout mUserGroup;
  private ImageView mUserLogo;
  private TextView mUserName;
  public MenuItem.OnClickListener onClickLogout = new MenuItem.OnClickListener()
  {
    public void onClick(BaseFragment paramBaseFragment)
    {
      KaixinMenuListFragment.this.dismissDialog();
      KaixinMenuListFragment.this.dialog = DialogUtil.showMsgDlgStd(paramBaseFragment.getActivity(), 2131427697, 2131427369, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          if (!Setting.getInstance().isTestVersion())
            KaixinMenuListFragment.this.logoutAndExit.logout(1, null);
        }
      });
    }
  };
  public MenuItem.OnClickListener onClickMoreSuggestApp = new MenuItem.OnClickListener()
  {
    public void onClick(BaseFragment paramBaseFragment)
    {
      KaixinMenuListFragment.this.showMoreSuggestApplication();
    }
  };
  public View.OnClickListener onClickUploadPhoto = new View.OnClickListener()
  {
    public void onClick(View paramView)
    {
      KaixinMenuListFragment.this.showUploadPhotoDialog(KaixinMenuListFragment.this.getString(2131427636));
    }
  };

  private void checkHasNewTaskInfo()
  {
    new NewTaskInfoTask().execute(new Void[0]);
  }

  private void getPictureAction()
  {
    if ((this.mGetPictureActionTask != null) && (this.mGetPictureActionTask.getStatus() != AsyncTask.Status.FINISHED))
      this.mGetPictureActionTask.cancel(true);
    this.mGetPictureActionTask = new GetPictureActionTask()
    {
      protected void onPostExecute(Integer paramInteger)
      {
        if (KaixinMenuListFragment.this.isNeedReturn());
        do
          return;
        while (paramInteger.intValue() != 1);
        if ((PhotographModel.getInstance().item != null) && (PhotographModel.getInstance().item.length > 0))
          KaixinMenuListFragment.mShowPictureAction = true;
        while (true)
        {
          KaixinMenuListFragment.this.updateRecommendAppNotifyIcon();
          return;
          KaixinMenuListFragment.mShowPictureAction = false;
        }
      }
    };
    this.mGetPictureActionTask.execute(new Void[0]);
  }

  private void getRecommendAppNotify()
  {
    if ((this.mGetRecommendAppNotifyTask != null) && (this.mGetRecommendAppNotifyTask.getStatus() != AsyncTask.Status.FINISHED))
      this.mGetRecommendAppNotifyTask.cancel(true);
    this.mGetRecommendAppNotifyTask = new GetRecommendAppNotifyTask()
    {
      protected void onPostExecute(Integer paramInteger)
      {
        if (KaixinMenuListFragment.this.isNeedReturn());
        do
          return;
        while (paramInteger.intValue() != 1);
        KaixinMenuListFragment.this.updateRecommendAppNotifyIcon();
      }
    };
    this.mGetRecommendAppNotifyTask.execute(new Void[0]);
  }

  private boolean isLoadCache(int paramInt1, int paramInt2)
  {
    String str = "loadThirdAppFinishedTime" + paramInt1 + "_" + paramInt2;
    long l1 = PreferenceManager.getDefaultSharedPreferences(getActivity()).getLong(str, -1L);
    long l2 = System.currentTimeMillis() / 86400000L;
    long l3 = l2 + 1L;
    boolean bool1 = l1 < -1L;
    int i = 0;
    if (bool1)
    {
      boolean bool2 = 24L * (60L * (60L * (1000L * l2))) < l1;
      i = 0;
      if (bool2)
      {
        boolean bool3 = l1 < 24L * (60L * (60L * (1000L * l3)));
        i = 0;
        if (bool3)
          i = 1;
      }
    }
    return i;
  }

  private void onSettingClicked()
  {
    this.mSelectImageView.setImageResource(2130838973);
    this.mSelectTextView.setTextColor(-1);
    this.mSetting.setBackgroundResource(2130838978);
    ((MainActivity)getActivity()).showSettingMenu();
    UserHabitUtils.getInstance(getActivity()).addUserHabit("click_list_setting");
  }

  private void updateFriendIcon()
  {
    if (User.getInstance().GetLookAround());
    TippedMenuItem localTippedMenuItem;
    do
    {
      MenuItem localMenuItem;
      do
      {
        return;
        localMenuItem = KaixinNavigator.instance.getMenuItemById(MenuItemId.ID_FRIENDS);
      }
      while (localMenuItem == null);
      localTippedMenuItem = (TippedMenuItem)localMenuItem;
    }
    while ((this.getFriendsListTask != null) && (this.getFriendsListTask.getStatus() != AsyncTask.Status.FINISHED) && (!this.getFriendsListTask.isCancelled()));
    if (!FriendsModel.getInstance().getStotal().equals("0"))
    {
      localTippedMenuItem.textTip = (FriendsModel.getInstance().getOnlinetotal() + getResources().getString(2131428468));
      this.listAdapter.notifyDataSetChanged();
      return;
    }
    this.getFriendsListTask = new GetFriendsListTask(getActivity(), localTippedMenuItem)
    {
      protected void onPostExecute(Integer paramInteger)
      {
        if ((paramInteger == null) || (paramInteger.intValue() == 0) || (KaixinMenuListFragment.this.isNeedReturn()))
          return;
        this.val$tipItem.textTip = (FriendsModel.getInstance().getOnlinetotal() + KaixinMenuListFragment.this.getResources().getString(2131428468));
        KaixinMenuListFragment.this.listAdapter.notifyDataSetChanged();
      }
    };
    GetFriendsListTask localGetFriendsListTask = this.getFriendsListTask;
    Integer[] arrayOfInteger = new Integer[1];
    arrayOfInteger[0] = Integer.valueOf(4);
    localGetFriendsListTask.execute(arrayOfInteger);
  }

  private void updateMessageIcon()
  {
    MenuItem localMenuItem = KaixinNavigator.instance.getMenuItemById(MenuItemId.ID_MSG_CENTER);
    if (localMenuItem == null)
      return;
    TippedMenuItem localTippedMenuItem = (TippedMenuItem)localMenuItem;
    int i = MessageCenterModel.getInstance().getTotalNoticeCnt() + ChatModel.getInstance().getUnreadNum("CHAT_MSG_TOTAL", false);
    if ((i == 0) && (!User.getInstance().GetLookAround()))
      ((NotificationManager)getActivity().getSystemService("notification")).cancel(KaixinConst.ID_NEW_MESSAGE_NOTIFICATION);
    for (localTippedMenuItem.textTip = null; ; localTippedMenuItem.textTip = makeNoticeText(i))
    {
      this.listAdapter.notifyDataSetChanged();
      return;
    }
  }

  private void updateNewsIcon()
  {
    MenuItem localMenuItem = KaixinNavigator.instance.getMenuItemById(MenuItemId.ID_NEWS);
    if (localMenuItem == null)
      return;
    TippedMenuItem localTippedMenuItem = (TippedMenuItem)localMenuItem;
    if ((NewsModel.getHasNew()) && (!User.getInstance().GetLookAround()));
    for (localTippedMenuItem.textTip = getString(2131427406); ; localTippedMenuItem.textTip = null)
    {
      this.listAdapter.notifyDataSetChanged();
      return;
    }
  }

  private void updatePositionIcon()
  {
    MenuItem localMenuItem = KaixinNavigator.instance.getMenuItemById(MenuItemId.ID_POSITION);
    if (localMenuItem == null)
      return;
    TippedMenuItem localTippedMenuItem = (TippedMenuItem)localMenuItem;
    int i = LbsModel.getInstance().getActivityTotal();
    if ((i == 0) || (User.getInstance().GetLookAround()));
    for (localTippedMenuItem.textTip = null; ; localTippedMenuItem.textTip = makeNoticeText(i))
    {
      this.listAdapter.notifyDataSetChanged();
      return;
    }
  }

  private void updateRecommendAppNotifyIcon()
  {
    MenuItem localMenuItem = KaixinNavigator.instance.getMenuItemById(MenuItemId.ID_GAME);
    if (localMenuItem == null)
      return;
    TippedMenuItem localTippedMenuItem = (TippedMenuItem)localMenuItem;
    if ((RecommendAppNotifyEngine.getInstance().getNewGameCount() == 0) && (!User.getInstance().GetLookAround()));
    for (localTippedMenuItem.textTip = null; ; localTippedMenuItem.textTip = "新")
    {
      this.listAdapter.notifyDataSetChanged();
      return;
    }
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
      KXLog.e("KaixinMenuListFragment", "onActivityResult", localException);
    }
  }

  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent)
  {
    FragmentActivity localFragmentActivity = getActivity();
    if ((localFragmentActivity != null) && ((localFragmentActivity instanceof MainActivity)) && (!((MainActivity)localFragmentActivity).isMenuListVisible()))
      return false;
    return super.dispatchTouchEvent(paramMotionEvent);
  }

  public void doResume()
  {
    if (MainActivity.isRefresh)
      loadMySelfInfo();
    if (isRefreshBackground)
    {
      showBackground();
      isRefreshBackground = false;
    }
    UGCMenuLayout.resetAnimationState();
    checkHasNewTaskInfo();
    updateMessageIcon();
    updateNewsIcon();
    updateFriendIcon();
    updateRecommendAppNotifyIcon();
    setTaskBtnVisible();
    if ((Math.abs(SystemClock.elapsedRealtime() - this.lastCheckTime) > 60000L) && (!NewsModel.getHasNew()))
    {
      UpdateEngine.getInstance().clear();
      UpdateEngine.getInstance().updateAsync();
    }
    this.locationRequester = new LocationRequester()
    {
      public void notifyChange(int paramInt, Location paramLocation)
      {
        switch (paramInt)
        {
        case 0:
        default:
          return;
        case 15:
        }
        KaixinMenuListFragment.this.refreshActivityNum(paramLocation);
      }
    };
    LocationService.getLocationService().requestRefreshLocation(this.locationRequester);
    boolean bool = KXEnvironment.loadBooleanParams(getActivity().getApplicationContext(), "shakeBtn_" + TimeUtil.getStringDateShort(), true, true);
    if (bool)
    {
      Animation localAnimation = AnimationUtils.loadAnimation(getActivity(), 2130968596);
      this.levelLayout.setAnimation(localAnimation);
      localAnimation.start();
    }
    if ((this.hasNew) && (bool))
    {
      this.levelBtn.setBackgroundResource(2130838584);
      return;
    }
    this.levelBtn.setBackgroundResource(2130838583);
  }

  protected boolean handleMessage(Message paramMessage)
  {
    if (paramMessage == null)
      return false;
    int i = 1;
    switch (paramMessage.what)
    {
    default:
      i = 0;
    case 8007:
    case 5999:
    case 8006:
    case 8002:
    case 2:
    case 10000:
    case 8100:
    case 40101:
    case 40051:
    }
    while (i == 0)
    {
      return super.handleMessage(paramMessage);
      EventModel.setLoadDone(true);
      continue;
      updateMessageIcon();
      continue;
      updateNewsIcon();
      this.lastCheckTime = SystemClock.elapsedRealtime();
      continue;
      this.logoutAndExit.exit(true);
      continue;
      if (TextUtils.isEmpty(User.getInstance().getOauthToken()))
        continue;
      this.logoutAndExit.logout(2, null);
      continue;
      String str3 = (String)paramMessage.obj;
      Toast.makeText(getActivity(), str3, 0).show();
      continue;
      if (TextUtils.isEmpty(User.getInstance().getOauthToken()))
        continue;
      String str2 = (String)paramMessage.obj;
      Toast.makeText(getActivity(), str2, 0).show();
      this.logoutAndExit.logout(2, null);
      continue;
      if (TextUtils.isEmpty(User.getInstance().getOauthToken()))
        continue;
      String str1 = (String)paramMessage.obj;
      Toast.makeText(getActivity(), str1, 0).show();
      this.logoutAndExit.logout(2, null);
    }
    return true;
  }

  public void loadMySelfInfo()
  {
    String str1 = User.getInstance().getRealName();
    String str2 = User.getInstance().getLogo();
    if ((TextUtils.isEmpty(str1)) || (TextUtils.isEmpty(str2)) || (User.getInstance().isNeedRefresh()) || (MainActivity.isRefresh))
    {
      MainActivity.isRefresh = false;
      User.getInstance().setNeedRefresh(false);
      if ((this.getLoginUserRealNameTask != null) && (this.getLoginUserRealNameTask.getStatus() != AsyncTask.Status.FINISHED))
        this.getLoginUserRealNameTask.cancel(true);
      updateSelfView();
      this.getLoginUserRealNameTask = new GetLoginUserRealNameTask()
      {
        protected void onPostExecute(Integer paramInteger)
        {
          if ((paramInteger == null) || (KaixinMenuListFragment.this.isNeedReturn()));
          do
            return;
          while (paramInteger.intValue() != 1);
          KaixinMenuListFragment.this.updateSelfView();
        }
      };
      this.getLoginUserRealNameTask.execute(new Void[] { null });
      return;
    }
    updateSelfView();
  }

  protected String makeNoticeText(int paramInt)
  {
    if (paramInt <= 0)
      return null;
    if (paramInt > 99)
      paramInt = 99;
    return String.valueOf(paramInt);
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    mShowPictureAction = false;
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903216, paramViewGroup, false);
  }

  public void onDestroy()
  {
    UploadTaskListEngine.getInstance().unRegister(this.mHandler);
    NavigatorApplistEngine.getInstance().result3dAppList.clearItemList();
    cancelTask(this.getFriendsListTask);
    cancelTask(this.getLoginUserRealNameTask);
    cancelTask(this.mGetActivityNumTask);
    cancelTask(this.mGetPictureActionTask);
    cancelTask(this.mGetRecommendAppNotifyTask);
    super.onDestroy();
  }

  public void onMenuItemClicked(UGCMenuLayout.MenuItem paramMenuItem)
  {
    switch ($SWITCH_TABLE$com$kaixin001$ugc$UGCMenuLayout$MenuItem()[paramMenuItem.ordinal()])
    {
    default:
      return;
    case 1:
      showUploadPhotoDialog(getString(2131427636));
      return;
    case 2:
      IntentUtil.showPoiListFragment(this, null);
      return;
    case 4:
      writeNewRecord(null);
      return;
    case 3:
      IntentUtil.showVoiceRecordFragment(this, null);
      return;
    case 5:
    }
    writeNewDiary();
  }

  public void onResume()
  {
    doResume();
    super.onResume();
  }

  public void onSlided()
  {
    updateMenuListVisiable(true);
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    KaixinNavigator.instance.init(getActivity());
    this.levelLayout = paramView.findViewById(2131363020);
    this.levelTaskFlag = paramView.findViewById(2131363022);
    this.levelBtn = ((Button)paramView.findViewById(2131363021));
    this.levelBtn.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        KXEnvironment.saveBooleanParams(KaixinMenuListFragment.this.getActivity().getApplicationContext(), "shakeBtn_" + TimeUtil.getStringDateShort(), true, false);
        ((MainActivity)KaixinMenuListFragment.this.getActivity()).clearBackStack();
        Intent localIntent = new Intent(KaixinMenuListFragment.this.getActivity(), UserAboutFragment.class);
        localIntent.putExtra("from", "KaixinMenuListFragment");
        localIntent.putExtra("fuid", User.getInstance().getUID());
        localIntent.putExtra("fname", User.getInstance().getRealName());
        localIntent.putExtra("flogo", User.getInstance().getLogo());
        localIntent.putExtra("target_tab", 0);
        ((MainActivity)KaixinMenuListFragment.this.getActivity()).startSubFragment(localIntent);
        ((MainActivity)KaixinMenuListFragment.this.getActivity()).showSubFragment();
        UserHabitUtils.getInstance(KaixinMenuListFragment.this.getActivity()).addUserHabit("user_mission_btn_click");
      }
    });
    if (paramBundle != null)
      paramBundle.getInt("isRefreshApp", 0);
    this.localMenu = KaixinNavigator.instance.menuCategoryList;
    this.mLayoutLeftBackground = ((ImageView)paramView.findViewById(2131363013));
    this.lvNavigator = ((ListView)paramView.findViewById(2131363027));
    this.mUserGroup = ((RelativeLayout)paramView.findViewById(2131363014));
    this.mUserLogo = ((ImageView)paramView.findViewById(2131363016));
    this.mUserName = ((TextView)paramView.findViewById(2131363017));
    this.mLevelImage = ((ImageView)paramView.findViewById(2131362533));
    this.mLevelId = ((TextView)paramView.findViewById(2131362536));
    this.mUserGradeLayout = ((LinearLayout)paramView.findViewById(2131363019));
    this.mSetting = ((RelativeLayout)paramView.findViewById(2131363024));
    this.mSetting.setClickable(true);
    this.mSetting.setVisibility(0);
    this.mSelectImageView = ((ImageView)paramView.findViewById(2131363025));
    this.mSelectTextView = ((TextView)paramView.findViewById(2131362454));
    this.mSetting.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        KaixinMenuListFragment.this.listAdapter.setAllItemUnSelected(KaixinMenuListFragment.this.lvNavigator);
        if (!User.getInstance().GetLookAround())
        {
          KaixinMenuListFragment.this.onSettingClicked();
          return;
        }
        KaixinMenuListFragment.this.showLoginPage(0);
      }
    });
    refreshMenuItems();
    UploadTaskListEngine.getInstance().register(this.mHandler);
    UpdateEngine.getInstance().setContext(getActivity().getApplicationContext());
    UpdateEngine.getInstance().setKXDesktopHandler(this.mHandler);
    UpdateEngine.getInstance().updateAsync();
    this.logoutAndExit = new LogoutAndExitProxy(getActivity());
    if ((getArguments() != null) && (!TextUtils.isEmpty(getArguments().getString("logout"))))
      this.logoutAndExit.logout(1, null);
    refreshData();
    UploadTaskListEngine.getInstance().register(UploadNotificationHandler.getInstance(getActivity().getApplicationContext()));
    UserHabitUtils.getInstance(getActivity().getApplicationContext()).addUserHabit("visit_list");
  }

  public void refreshActivityNum(Location paramLocation)
  {
    if (paramLocation == null);
    do
      return;
    while ((this.mGetActivityNumTask != null) && (this.mGetActivityNumTask.getStatus() != AsyncTask.Status.FINISHED) && (!this.mGetActivityNumTask.isCancelled()));
    Location localLocation = LocationService.getLocationService().lastBestMapABCLocation;
    this.mGetActivityNumTask = new GetLbsActivityTask(getActivity().getApplicationContext(), paramLocation, localLocation)
    {
      protected void onPostExecuteA(Integer paramInteger)
      {
        if ((paramInteger == null) || (paramInteger.intValue() == 0) || (KaixinMenuListFragment.this.isNeedReturn()))
          return;
        KaixinMenuListFragment.this.updatePositionIcon();
      }
    };
    GetLbsActivityTask localGetLbsActivityTask = this.mGetActivityNumTask;
    Integer[] arrayOfInteger = new Integer[2];
    arrayOfInteger[0] = Integer.valueOf(0);
    arrayOfInteger[1] = Integer.valueOf(0);
    localGetLbsActivityTask.execute(arrayOfInteger);
  }

  public void refreshData()
  {
    getRecommendAppNotify();
    getPictureAction();
    loadMySelfInfo();
  }

  public void refreshMenuItems()
  {
    KaixinNavigator.instance.init(getActivity());
    this.category = KaixinNavigator.instance.getMenuCategory(MenuCategoryId.ID_APPLICATION);
    this.listAdapter = new MenuAdapter.MenuListAdapter(this, this.category, this.mSelectImageView, this.mSelectTextView, this.mSetting);
    this.lvNavigator.setAdapter(this.listAdapter);
    this.lvNavigator.setOnItemClickListener(this.listAdapter);
  }

  public void setNewTaskInfo()
  {
    if (this.hasNew)
    {
      this.levelBtn.setBackgroundResource(2130838584);
      return;
    }
    this.levelBtn.setBackgroundResource(2130838583);
  }

  public void setSelectedMenuItem(Class<?> paramClass)
  {
    MenuItem localMenuItem = KaixinNavigator.instance.getMenuItemByTargetActivity(paramClass);
    this.listAdapter.selectedItem = localMenuItem;
  }

  public void setTaskBtnVisible()
  {
    if (TextUtils.isEmpty(User.getInstance().getUID()))
    {
      this.levelLayout.setVisibility(8);
      this.mLevelId.setVisibility(8);
      this.mLevelImage.setVisibility(8);
      return;
    }
    this.levelLayout.setVisibility(0);
    this.mLevelId.setVisibility(0);
    this.mLevelImage.setVisibility(0);
  }

  public void showBackground()
  {
    if (TextUtils.isEmpty(User.getInstance().getCoverUrl()))
    {
      this.mLayoutLeftBackground.setImageResource(2130837741);
      return;
    }
    displayPictureExt(this.mLayoutLeftBackground, User.getInstance().getCoverUrl(), 2130837741);
  }

  protected void showMoreSuggestApplication()
  {
    Intent localIntent = new Intent(getActivity(), GamesFragment.class);
    localIntent.putExtra("from", "KaixinMenuListFragment");
    MenuItem.startRightFragment(this, localIntent);
  }

  public void updateMenuListVisiable(boolean paramBoolean)
  {
    View localView = getView();
    if (localView != null)
      if (!paramBoolean)
        break label21;
    label21: for (int i = 0; ; i = 8)
    {
      localView.setVisibility(i);
      return;
    }
  }

  public void updateSelfView()
  {
    User localUser = User.getInstance();
    String str = localUser.getRealName();
    if (TextUtils.isEmpty(str))
      str = getString(2131427565);
    this.mUserName.setText(str);
    if (User.getInstance().GetLookAround())
    {
      this.mUserGradeLayout.setVisibility(8);
      showBackground();
      displayRoundPicture(this.mUserLogo, localUser.getLogo(), ImageDownloader.RoundCornerType.hdpi_big, 2130838334);
      UserLevel localUserLevel = localUser.getmUserLevel();
      showLevelToast();
      if (localUserLevel == null)
        break label168;
      this.mUserGradeLayout.setVisibility(0);
      displayPicture(this.mLevelImage, localUserLevel.getImage(), 0);
      this.mLevelId.setText("LV." + localUserLevel.getLevel());
    }
    while (true)
    {
      this.mUserGroup.setClickable(true);
      this.mUserGroup.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          if (User.getInstance().GetLookAround())
          {
            KaixinMenuListFragment.this.showLoginPage(0);
            return;
          }
          KaixinMenuListFragment.this.listAdapter.selectedItem = null;
          Intent localIntent = new Intent(KaixinMenuListFragment.this.getActivity(), HomeFragment.class);
          localIntent.putExtras(IntentUtil.getHomeActivityIntent(User.getInstance().getUID(), User.getInstance().getRealName(), User.getInstance().getLogo(), "KaixinMenuListFragment"));
          MenuItem.startRightFragment(KaixinMenuListFragment.this, localIntent);
        }
      });
      return;
      this.mUserGradeLayout.setVisibility(0);
      break;
      label168: this.mUserGradeLayout.setVisibility(8);
    }
  }

  public void writeNewDiary()
  {
    if (UploadTaskListEngine.getInstance().getWaitingTaskCount() < 20)
    {
      startFragment(new Intent(getActivity(), WriteDiaryFragment.class), true, 3);
      return;
    }
    showCantUploadOptions();
  }

  public class NewTaskInfoTask extends KXFragment.KXAsyncTask<Void, Void, Integer>
  {
    public NewTaskInfoTask()
    {
      super();
    }

    // ERROR //
    protected Integer doInBackgroundA(Void[] paramArrayOfVoid)
    {
      // Byte code:
      //   0: iconst_1
      //   1: istore_2
      //   2: invokestatic 24	com/kaixin001/network/Protocol:getInstance	()Lcom/kaixin001/network/Protocol;
      //   5: invokevirtual 28	com/kaixin001/network/Protocol:makeHasNewTaskUrl	()Ljava/lang/String;
      //   8: astore_3
      //   9: new 30	com/kaixin001/network/HttpProxy
      //   12: dup
      //   13: aload_0
      //   14: getfield 11	com/kaixin001/fragment/KaixinMenuListFragment$NewTaskInfoTask:this$0	Lcom/kaixin001/fragment/KaixinMenuListFragment;
      //   17: invokevirtual 36	com/kaixin001/fragment/KaixinMenuListFragment:getActivity	()Landroid/support/v4/app/FragmentActivity;
      //   20: invokespecial 39	com/kaixin001/network/HttpProxy:<init>	(Landroid/content/Context;)V
      //   23: astore 4
      //   25: new 41	org/json/JSONObject
      //   28: dup
      //   29: aload 4
      //   31: aload_3
      //   32: aconst_null
      //   33: aconst_null
      //   34: invokevirtual 45	com/kaixin001/network/HttpProxy:httpGet	(Ljava/lang/String;Lcom/kaixin001/network/HttpRequestState;Lcom/kaixin001/network/HttpProgressListener;)Ljava/lang/String;
      //   37: invokespecial 48	org/json/JSONObject:<init>	(Ljava/lang/String;)V
      //   40: astore 5
      //   42: aload 5
      //   44: ldc 50
      //   46: invokevirtual 54	org/json/JSONObject:optJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
      //   49: astore 7
      //   51: aload 7
      //   53: ifnull +26 -> 79
      //   56: aload_0
      //   57: getfield 11	com/kaixin001/fragment/KaixinMenuListFragment$NewTaskInfoTask:this$0	Lcom/kaixin001/fragment/KaixinMenuListFragment;
      //   60: astore 8
      //   62: aload 7
      //   64: ldc 56
      //   66: invokevirtual 60	org/json/JSONObject:getInt	(Ljava/lang/String;)I
      //   69: iload_2
      //   70: if_icmpne +18 -> 88
      //   73: aload 8
      //   75: iload_2
      //   76: invokestatic 64	com/kaixin001/fragment/KaixinMenuListFragment:access$1	(Lcom/kaixin001/fragment/KaixinMenuListFragment;Z)V
      //   79: iconst_1
      //   80: invokestatic 70	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   83: astore 9
      //   85: aload 9
      //   87: areturn
      //   88: iconst_0
      //   89: istore_2
      //   90: goto -17 -> 73
      //   93: astore 6
      //   95: aload 6
      //   97: invokevirtual 74	java/lang/Exception:printStackTrace	()V
      //   100: aconst_null
      //   101: areturn
      //   102: astore 6
      //   104: goto -9 -> 95
      //
      // Exception table:
      //   from	to	target	type
      //   25	42	93	java/lang/Exception
      //   42	51	102	java/lang/Exception
      //   56	73	102	java/lang/Exception
      //   73	79	102	java/lang/Exception
      //   79	85	102	java/lang/Exception
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      KaixinMenuListFragment.this.setNewTaskInfo();
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
 * Qualified Name:     com.kaixin001.fragment.KaixinMenuListFragment
 * JD-Core Version:    0.6.0
 */