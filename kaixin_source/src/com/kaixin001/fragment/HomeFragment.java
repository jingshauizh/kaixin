package com.kaixin001.fragment;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.Uri;
import android.os.AsyncTask.Status;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TableLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.activity.KXApplication;
import com.kaixin001.activity.KXEnvironment;
import com.kaixin001.activity.LoginActivity;
import com.kaixin001.activity.MainActivity;
import com.kaixin001.adapter.NewsItemAdapter;
import com.kaixin001.adapter.NewsItemAdapter.NewsItemUI;
import com.kaixin001.adapter.NewsItemAdapter.OnViewMoreClickListener;
import com.kaixin001.engine.AddVerifyEngine;
import com.kaixin001.engine.AlbumListEngine;
import com.kaixin001.engine.DelStarEngine;
import com.kaixin001.engine.HomeVisitorEngine;
import com.kaixin001.engine.NewFriendEngine;
import com.kaixin001.engine.NewsEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.engine.UpdateEngine;
import com.kaixin001.engine.UploadPhotoEngine;
import com.kaixin001.engine.UploadTaskListEngine;
import com.kaixin001.engine.UserInfoEngine;
import com.kaixin001.item.FriendInfo;
import com.kaixin001.item.HomeVisitorItem;
import com.kaixin001.item.NewsInfo;
import com.kaixin001.model.AlbumListModel;
import com.kaixin001.model.HomeVisitorModel;
import com.kaixin001.model.NewsModel;
import com.kaixin001.model.User;
import com.kaixin001.model.UserInfoModel;
import com.kaixin001.network.HttpConnection;
import com.kaixin001.ugc.UGCMenuLayout.MenuClickListener;
import com.kaixin001.ugc.UGCMenuLayout.MenuItem;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.CrashRecoverUtil;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.ImageCache;
import com.kaixin001.util.ImageDownloader.RoundCornerType;
import com.kaixin001.util.ImageUtil;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.KXUBLog;
import com.kaixin001.view.KXInputView.KXInputListener;
import com.kaixin001.view.KXIntroView;
import com.kaixin001.view.KXUGCView;
import com.kaixin001.view.KXUGCView.OnUGCItemClickListener;
import com.kaixin001.view.PullToRefreshView.PullToRefreshListener;
import com.kaixin001.view.PullToRefreshView2;
import com.kaixin001.view.media.KXMediaInfo;
import com.kaixin001.view.media.KXMediaManager;
import java.io.File;
import java.util.ArrayList;
import java.util.Timer;

public class HomeFragment extends BaseFragment
  implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, PullToRefreshView.PullToRefreshListener, UGCMenuLayout.MenuClickListener, AbsListView.OnScrollListener, View.OnTouchListener, KXInputView.KXInputListener
{
  public static final String FLAG_EMPTY = "-1";
  private static final int FRIEND_HEAD_PICTURE_NOT_EXISTS_DIALOG = 501;
  private static final String FROM_WEBPAGE = "from_webpage";
  private static final int HOME_HIDE_GUIDE = 503;
  protected static final int MENU_ADDFANS_ID = 406;
  protected static final int MENU_ADDFRIEND_ID = 403;
  protected static final int MENU_DELFANS_ID = 407;
  protected static final int MENU_DESKTOP_ID = 404;
  protected static final int MENU_HOME_ME_ID = 405;
  protected static final int MENU_MSG_ID = 402;
  protected static final int MENU_REFRESH_ID = 401;
  public static final int REQUEST_CROP_IMAGE = 112;
  public static final int REQUEST_UPDATE_COVER = 114;
  public static final int REQUEST_UPDATE_INFO = 113;
  private static final int SDK_VERSION_NOT_SUPPORT = 502;
  private AddVerifyTask addVerifyTask;
  boolean bMenuNeedChange = false;
  private boolean bStop = false;
  private Bitmap bgBmp;
  private View btnLayout;
  private ImageView btnLeft;
  private ImageView btnRight;
  private ImageView btnRightBar;
  private GetDataTask dataTask;
  private DelStarTask delStarTask;
  private EditText evVerify;
  private String gender = "0";
  private String indexprivacy = "0";
  boolean isSelf = false;
  private boolean isUploadLogo = false;
  private String ismyfriend = "0";
  private String istar = "0";
  private ImageView ivCover;
  private ImageView ivLogo;
  private ImageView ivStar;
  private TextView layoutPrivacy;
  private ListView lvHomeNews;
  private NewsItemAdapter mAdapter;
  private View mBtnAboutLayout;
  private ImageView mBtnAddFriend;
  private ImageView mBtnChat;
  private View mBtnDiaryLayout;
  private View mBtnFriendLayout;
  private ImageView mBtnGifts;
  private View mBtnLocationLayout;
  private View mBtnPhotoLayout;
  private View mBtnRecordLayout;
  private TextView mCity;
  private TextView mConstellation;
  private TextView mDiaryNumText;
  protected PullToRefreshView2 mDownView;
  protected PullToRefreshView2 mDownView2;
  private float mDownY = -1.0F;
  private ImageView mEditStatusButton;
  private View mFooterView;
  private TextView mFriendNumText;
  private ImageView mGenderIcon;
  private GetVisitorsTask mGetVisitsTask;
  Timer mGuideTimer;
  private View mHeaderView;
  private TranslateAnimation mHideAnimation;
  private final ArrayList<NewsInfo> mHomeNewsData = new ArrayList();
  private TextView mLevelExp;
  private TextView mLevelId;
  private ImageView mLevelImage;
  private TextView mLevelTitle;
  private ListView mLvPeopleInfo;
  private View mLytStauts;
  private boolean mNeedAutoLoading = true;
  NewsItemAdapter.OnViewMoreClickListener mOnViewMoreClickListener = new NewsItemAdapter.OnViewMoreClickListener()
  {
    public void onViewMoreClick()
    {
      ArrayList localArrayList = HomeFragment.this.newsModel.getNewsList();
      int i = HomeFragment.this.newsModel.getTotalNum(null);
      if ((localArrayList != null) && (localArrayList.size() >= HomeFragment.this.mHomeNewsData.size()))
        HomeFragment.this.refreshDone(1);
      while (true)
      {
        if ((localArrayList != null) && (localArrayList.size() < i))
          HomeFragment.this.refreshMore(true);
        return;
        HomeFragment.this.mAdapter.showLoadingFooter(true);
      }
    }
  };
  private HomePeopleInfoAdapter mPeopleInfoAdapter;
  private final ArrayList<HomePeopleInfo> mPeopleInfoData = new ArrayList();
  private TextView mPhotoNumText;
  private ProgressDialog mProgressDialog;
  private TranslateAnimation mShowAnimation;
  private TextView mTvGuildView = null;
  private KXUGCView mUgcView;
  private Button mVisitorNum;
  private View mVisitorsLayout;
  private HomeVisitorModel mVisitorsModel = new HomeVisitorModel();
  private String m_flogo = null;
  private String m_fname = null;
  private String m_fuid = null;
  private NewFriendTask newFriendTask;
  private NewsModel newsModel;
  private ProgressBar rightProBar;
  private TextView tvName;
  private KXIntroView tvState;
  private TextView tvTitle;
  private UpdateUserLogoTask updateUserLogTask;
  private UploadHomeBgTask uploadBgTask;
  private LinearLayout waitLayout;

  static
  {
    if (!HomeFragment.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }

  private void actionForward(NewsInfo paramNewsInfo)
  {
    if ((paramNewsInfo != null) && (!TextUtils.isEmpty(paramNewsInfo.mFpri)) && (paramNewsInfo.mFpri.equals("0")))
    {
      DialogUtil.showMsgDlgStdConfirm(getActivity(), 2131427329, 2131427744, null);
      return;
    }
    Intent localIntent = new Intent(getActivity(), ForwardWeiboFragment.class);
    Bundle localBundle = new Bundle();
    localBundle.putSerializable("recordInfo", NewsItemAdapter.getRecordInfoFromNewsInfo(paramNewsInfo));
    localIntent.putExtras(localBundle);
    AnimationUtil.startFragmentForResult(this, localIntent, 1201, 3);
  }

  private void addNewFriend(String paramString)
  {
    this.bStop = false;
    boolean bool = TextUtils.isEmpty(paramString);
    String[] arrayOfString1 = null;
    if (!bool)
    {
      arrayOfString1 = new String[1];
      arrayOfString1[0] = paramString;
    }
    if (User.getInstance().GetLookAround())
    {
      FragmentActivity localFragmentActivity = getActivity();
      String[] arrayOfString2 = new String[2];
      arrayOfString2[0] = getString(2131427338);
      arrayOfString2[1] = getString(2131427339);
      DialogUtil.showSelectListDlg(localFragmentActivity, 2131427729, arrayOfString2, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          switch (paramInt)
          {
          default:
            return;
          case 0:
            Intent localIntent2 = new Intent(HomeFragment.this.getActivity(), LoginActivity.class);
            AnimationUtil.startFragment(HomeFragment.this, localIntent2, 1);
            HomeFragment.this.getActivity().finish();
            return;
          case 1:
          }
          Intent localIntent1 = new Intent(HomeFragment.this.getActivity(), LoginActivity.class);
          Bundle localBundle = new Bundle();
          localBundle.putInt("regist", 1);
          localIntent1.putExtras(localBundle);
          AnimationUtil.startFragment(HomeFragment.this, localIntent1, 1);
          HomeFragment.this.getActivity().finish();
        }
      }
      , 1);
      return;
    }
    this.newFriendTask = new NewFriendTask(null);
    this.newFriendTask.execute(arrayOfString1);
    this.mProgressDialog = ProgressDialog.show(getActivity(), "", getResources().getText(2131427512), true, true, new DialogInterface.OnCancelListener()
    {
      public void onCancel(DialogInterface paramDialogInterface)
      {
        HomeFragment.this.bStop = true;
        if (NewFriendEngine.getInstance() != null)
          NewFriendEngine.getInstance().cancel();
        HomeFragment.this.newFriendTask.cancel(true);
      }
    });
  }

  private void addNewFriendResult()
  {
    switch (NewFriendEngine.getInstance().getRet())
    {
    default:
      return;
    case 2:
      Toast.makeText(getActivity(), NewFriendEngine.getInstance().getTipMsg(), 0).show();
      return;
    case 3:
      showInputVerify();
      return;
    case 4:
    case 5:
      Toast.makeText(getActivity(), NewFriendEngine.getInstance().getTipMsg(), 0).show();
      return;
    case 6:
      DialogUtil.showKXAlertDialog(getActivity(), NewFriendEngine.getInstance().getTipMsg(), 2131427517, 2131427587, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          HomeFragment.this.addNewFriend("3");
        }
      }
      , null);
      return;
    case 7:
    case 8:
      Toast.makeText(getActivity(), NewFriendEngine.getInstance().getTipMsg(), 0).show();
      return;
    case 9:
    case 10:
      DialogUtil.showKXAlertDialog(getActivity(), NewFriendEngine.getInstance().getTipMsg(), 2131427517, 2131427587, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          HomeFragment.this.addVerify(false);
        }
      }
      , null);
      return;
    case 11:
    case 12:
    }
    Toast.makeText(getActivity(), NewFriendEngine.getInstance().getTipMsg(), 0).show();
  }

  private void addVerify(boolean paramBoolean)
  {
    this.bStop = false;
    this.addVerifyTask = new AddVerifyTask(null);
    if (paramBoolean)
    {
      if (this.evVerify == null)
        return;
      String str = String.valueOf(this.evVerify.getText()).trim();
      if (TextUtils.isEmpty(str))
      {
        DialogUtil.showKXAlertDialog(getActivity(), 2131427514, null);
        return;
      }
      this.addVerifyTask.execute(new String[] { str });
    }
    while (true)
    {
      this.mProgressDialog = ProgressDialog.show(getActivity(), "", getResources().getText(2131427512), true, true, new DialogInterface.OnCancelListener()
      {
        public void onCancel(DialogInterface paramDialogInterface)
        {
          HomeFragment.this.bStop = true;
          if (AddVerifyEngine.getInstance() != null)
            AddVerifyEngine.getInstance().cancel();
          HomeFragment.this.addVerifyTask.cancel(true);
        }
      });
      return;
      this.addVerifyTask.execute(null);
    }
  }

  private void addVerifyResult()
  {
    Toast.makeText(getActivity(), AddVerifyEngine.getInstance().getTipMsg(), 0).show();
    initTitleButton();
  }

  private void clearAndRefresh()
  {
    getServerData();
  }

  private void clearHomeData()
  {
    getActivity().removeDialog(501);
    getActivity().removeDialog(502);
    if ((this.dataTask != null) && (this.dataTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.dataTask.cancel(true);
      NewsEngine.getInstance().cancel();
      AlbumListEngine.getInstance().cancel();
      UserInfoEngine.getInstance().cancel();
    }
    if ((this.m_fuid != null) && (User.getInstance().getUID().compareTo(this.m_fuid) != 0) && (this.newsModel != null))
      this.newsModel.clear();
    AlbumListModel.getInstance().clear();
    UserInfoModel.getInstance().clear();
    if (this.lvHomeNews != null);
    try
    {
      this.lvHomeNews.removeHeaderView(this.mHeaderView);
      this.lvHomeNews.setAdapter(null);
      this.lvHomeNews = null;
      if (this.mLvPeopleInfo == null);
    }
    catch (Exception localException2)
    {
      try
      {
        this.mLvPeopleInfo.removeHeaderView(this.mHeaderView);
        this.mLvPeopleInfo.removeFooterView(this.mFooterView);
        this.mLvPeopleInfo.setAdapter(null);
        this.mLvPeopleInfo = null;
        if (this.mAdapter != null)
        {
          this.mAdapter.clear();
          this.mAdapter = null;
        }
        return;
        localException2 = localException2;
        localException2.printStackTrace();
      }
      catch (Exception localException1)
      {
        while (true)
          localException1.printStackTrace();
      }
    }
  }

  private void constructViews()
  {
    ArrayList localArrayList = new ArrayList();
    if (this.mVisitorsModel.getVisitorList() != null)
      localArrayList.addAll(this.mVisitorsModel.getVisitorList());
    String str = getResources().getString(2131428669).replaceAll("0", this.mVisitorsModel.getTotal());
    this.mVisitorNum.setText(str);
    LinearLayout localLinearLayout1 = (LinearLayout)this.mHeaderView.findViewById(2131362577);
    localLinearLayout1.removeAllViews();
    LinearLayout localLinearLayout2 = new LinearLayout(getActivity());
    TableLayout.LayoutParams localLayoutParams = new TableLayout.LayoutParams();
    localLayoutParams.width = -1;
    localLayoutParams.height = -2;
    localLinearLayout2.setLayoutParams(localLayoutParams);
    localLinearLayout2.setGravity(16);
    int i = localArrayList.size();
    if (i > 10)
      i = 10;
    if (i > 0);
    for (int j = i - 1; ; j--)
    {
      if (j < 0)
        return;
      View localView = getActivity().getLayoutInflater().inflate(2130903159, null, false);
      ImageView localImageView = (ImageView)localView.findViewById(2131362697);
      HomeVisitorItem localHomeVisitorItem = (HomeVisitorItem)localArrayList.get(j);
      displayRoundPicture(localImageView, localHomeVisitorItem.icon, ImageDownloader.RoundCornerType.hdpi_small, 2130838334);
      localLinearLayout1.addView(localView, 0);
      localView.setOnClickListener(new View.OnClickListener(localHomeVisitorItem)
      {
        public void onClick(View paramView)
        {
          IntentUtil.showHomeFragment(HomeFragment.this, this.val$friend.uid, this.val$friend.name, this.val$friend.icon);
        }
      });
    }
  }

  private void delStar()
  {
    this.bStop = false;
    this.delStarTask = new DelStarTask(null);
    this.delStarTask.execute(new Void[0]);
    this.mProgressDialog = ProgressDialog.show(getActivity(), "", getResources().getText(2131427512), true, true, new DialogInterface.OnCancelListener()
    {
      public void onCancel(DialogInterface paramDialogInterface)
      {
        HomeFragment.this.bStop = true;
        if (DelStarEngine.getInstance() != null)
          DelStarEngine.getInstance().cancel();
        HomeFragment.this.delStarTask.cancel(true);
      }
    });
  }

  private void doAction()
  {
    if (((this.istar.compareTo("0") == 0) || (this.istar.compareTo("1") == 0)) && (this.ismyfriend.compareTo("0") != 0))
      showChatDetailPage();
    do
    {
      return;
      if (((this.istar.compareTo("0") == 0) || (this.istar.compareTo("1") == 0)) && (this.ismyfriend.compareTo("0") == 0))
      {
        addNewFriend(null);
        return;
      }
      if ((this.istar.compareTo("2") != 0) || (this.ismyfriend.compareTo("0") == 0))
        continue;
      showChatDetailPage();
      return;
    }
    while ((this.istar.compareTo("2") != 0) || (this.ismyfriend.compareTo("0") != 0));
    addNewFriend(null);
  }

  private void doPhoneContactCheck()
  {
    Object localObject = getResources().getStringArray(2131492873);
    int i;
    if ((this.m_fuid != null) && (this.m_fuid.equals(User.getInstance().getUID())))
      i = 1;
    while (i == 0)
    {
      if (TextUtils.isEmpty(this.m_flogo))
      {
        return;
        i = 0;
        continue;
      }
      String[] arrayOfString2 = new String[1];
      arrayOfString2[0] = localObject[(-1 + localObject.length)];
      localObject = arrayOfString2;
    }
    do
    {
      DialogUtil.showSelectListDlg(getActivity(), 2131427509, localObject, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          switch (paramInt)
          {
          default:
            return;
          case 0:
            if ((HomeFragment.this.m_fuid != null) && (HomeFragment.this.m_fuid.equals(User.getInstance().getUID())))
            {
              HomeFragment.this.setMyLogo();
              return;
            }
            HomeFragment.this.setContactHead();
            return;
          case 1:
            HomeFragment.this.showUserinfoPage();
            return;
          case 2:
          }
          HomeFragment.this.setContactHead();
        }
      }
      , 1);
      return;
    }
    while (!TextUtils.isEmpty(this.m_flogo));
    String[] arrayOfString1 = new String[-1 + localObject.length];
    for (int j = 0; ; j++)
    {
      if (j >= arrayOfString1.length)
      {
        localObject = arrayOfString1;
        break;
      }
      arrayOfString1[j] = localObject[j];
    }
  }

  private void getServerData()
  {
    if (User.getInstance().getUID().compareTo(this.m_fuid) == 0)
    {
      this.newsModel = NewsModel.getMyHomeModel();
      if (this.tvState.getTitleList() == null)
        this.tvState.setVisibility(8);
      setHomeTitle();
      this.tvName.setText(this.m_fname);
      showInfoCardButton(0);
      this.layoutPrivacy.setVisibility(8);
      if (this.m_flogo != null)
        showLogo(this.m_flogo);
      String str = getArguments().getString("action");
      if ((UpdateEngine.getInstance().isHomeLoaded()) || (str != null))
        break label122;
    }
    label122: 
    do
    {
      return;
      this.newsModel = NewsModel.getHomeModel(this);
      break;
      User.getInstance().getUID().compareTo(this.m_fuid);
      this.btnRight.setEnabled(false);
      if (super.checkNetworkAndHint(true))
        break label205;
      this.waitLayout.setVisibility(8);
      this.btnRight.setEnabled(true);
    }
    while (User.getInstance().getUID().compareTo(this.m_fuid) != 0);
    this.rightProBar.setVisibility(8);
    this.btnRight.setImageResource(2130838834);
    return;
    label205: refreshData(0, 20);
    showLoading(true, -1);
  }

  private void hideGuide()
  {
    if (this.mTvGuildView != null)
      this.mTvGuildView.setVisibility(8);
  }

  private void initAnimation()
  {
    this.mShowAnimation = new TranslateAnimation(0.0F, 0.0F, 200.0F, 0.0F);
    this.mHideAnimation = new TranslateAnimation(0.0F, 0.0F, 0.0F, 200.0F);
    this.mShowAnimation.setDuration(500L);
    this.mHideAnimation.setDuration(500L);
  }

  private void initHeader()
  {
    if (User.getInstance().getUID().compareTo(this.m_fuid) == 0);
    for (this.newsModel = NewsModel.getMyHomeModel(); ; this.newsModel = NewsModel.getHomeModel(this))
    {
      this.mHeaderView = getActivity().getLayoutInflater().inflate(2130903150, null);
      this.mLevelImage = ((ImageView)this.mHeaderView.findViewById(2131362533));
      this.mLevelId = ((TextView)this.mHeaderView.findViewById(2131362536));
      this.mLevelTitle = ((TextView)this.mHeaderView.findViewById(2131362534));
      this.mLevelExp = ((TextView)this.mHeaderView.findViewById(2131362537));
      this.ivCover = ((ImageView)this.mHeaderView.findViewById(2131362542));
      this.ivCover.setOnClickListener(this);
      Bundle localBundle = getArguments();
      if ((localBundle != null) && (localBundle.getString("from") != null) && (localBundle.getString("from").equals("KXNewsBarView")))
        displayPicture(this.ivCover, User.getInstance().getCoverUrl(), 0);
      this.ivStar = ((ImageView)this.mHeaderView.findViewById(2131362547));
      this.ivStar.setVisibility(8);
      this.ivLogo = ((ImageView)this.mHeaderView.findViewById(2131362521));
      this.ivLogo.setOnClickListener(this);
      if (this.m_flogo != null)
        showLogo(this.m_flogo);
      this.tvName = ((TextView)this.mHeaderView.findViewById(2131362532));
      this.tvName.setText(this.m_fname);
      this.mGenderIcon = ((ImageView)this.mHeaderView.findViewById(2131362549));
      this.mGenderIcon.setVisibility(8);
      this.mCity = ((TextView)this.mHeaderView.findViewById(2131362550));
      this.mCity.setVisibility(8);
      this.mConstellation = ((TextView)this.mHeaderView.findViewById(2131362551));
      this.mConstellation.setVisibility(8);
      this.tvState = ((KXIntroView)this.mHeaderView.findViewById(2131362529));
      DisplayMetrics localDisplayMetrics = getResources().getDisplayMetrics();
      float f = localDisplayMetrics.density;
      int i = (int)(localDisplayMetrics.widthPixels * (205.0F * f) / (320.0F * f));
      this.tvState.setMaxWidth(i);
      this.mLytStauts = this.mHeaderView.findViewById(2131362552);
      this.mLytStauts.setOnClickListener(this);
      if (User.getInstance().getUID().compareTo(this.m_fuid) != 0)
        this.mLytStauts.setEnabled(false);
      this.mEditStatusButton = ((ImageView)this.mHeaderView.findViewById(2131362553));
      this.mEditStatusButton.setVisibility(8);
      showInfoCardButton(0);
      this.btnLayout = this.mHeaderView.findViewById(2131362555);
      this.btnLayout.setVisibility(8);
      this.mBtnAboutLayout = ((LinearLayout)this.mHeaderView.findViewById(2131362556));
      this.mBtnPhotoLayout = ((LinearLayout)this.mHeaderView.findViewById(2131362558));
      this.mBtnDiaryLayout = ((LinearLayout)this.mHeaderView.findViewById(2131362566));
      this.mBtnFriendLayout = ((LinearLayout)this.mHeaderView.findViewById(2131362563));
      this.mBtnRecordLayout = ((LinearLayout)this.mHeaderView.findViewById(2131362569));
      this.mBtnLocationLayout = ((LinearLayout)this.mHeaderView.findViewById(2131362572));
      this.mPhotoNumText = ((TextView)this.mHeaderView.findViewById(2131362562));
      this.mDiaryNumText = ((TextView)this.mHeaderView.findViewById(2131362568));
      this.mFriendNumText = ((TextView)this.mHeaderView.findViewById(2131362565));
      this.mBtnAboutLayout.setEnabled(false);
      this.mBtnPhotoLayout.setEnabled(false);
      this.mBtnDiaryLayout.setEnabled(false);
      this.mBtnFriendLayout.setEnabled(false);
      this.mBtnRecordLayout.setEnabled(false);
      this.mBtnLocationLayout.setEnabled(false);
      this.mBtnAboutLayout.setOnClickListener(this);
      this.mBtnPhotoLayout.setOnClickListener(this);
      this.mBtnDiaryLayout.setOnClickListener(this);
      this.mBtnFriendLayout.setOnClickListener(this);
      this.mBtnRecordLayout.setOnClickListener(this);
      this.mBtnLocationLayout.setOnClickListener(this);
      this.mBtnAddFriend = ((ImageView)this.mHeaderView.findViewById(2131362543));
      this.mBtnAddFriend.setVisibility(8);
      this.mBtnChat = ((ImageView)this.mHeaderView.findViewById(2131362544));
      this.mBtnChat.setVisibility(8);
      this.mBtnGifts = ((ImageView)this.mHeaderView.findViewById(2131362545));
      this.mBtnGifts.setVisibility(8);
      this.mBtnAddFriend.setOnClickListener(this);
      this.mBtnChat.setOnClickListener(this);
      this.mBtnGifts.setOnClickListener(this);
      this.mVisitorsLayout = this.mHeaderView.findViewById(2131362575);
      this.mVisitorsLayout.setVisibility(8);
      this.mVisitorNum = ((Button)this.mHeaderView.findViewById(2131362578));
      this.mVisitorNum.setOnClickListener(this);
      constructViews();
      return;
    }
  }

  private void initMainViews(View paramView)
  {
    this.waitLayout = ((LinearLayout)paramView.findViewById(2131362509));
    this.lvHomeNews = ((ListView)paramView.findViewById(2131362506));
    this.mHeaderView.setFocusable(true);
    this.lvHomeNews.addHeaderView(this.mHeaderView, this.mHeaderView, true);
    this.mTvGuildView = ((TextView)paramView.findViewById(2131362510));
    this.mTvGuildView.setVisibility(8);
    this.mTvGuildView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        HomeFragment.this.hideGuide();
      }
    });
    this.mDownView = ((PullToRefreshView2)paramView.findViewById(2131361814));
    this.mDownView.setPullToRefreshListener(this);
    this.mDownView.setActiveView(this.mHeaderView.findViewById(2131362542), 100);
    this.mDownView2 = ((PullToRefreshView2)paramView.findViewById(2131362507));
    this.mDownView2.setPullToRefreshListener(this);
    this.mDownView2.setActiveView(this.mHeaderView.findViewById(2131362542), 100);
    this.mDownView2.setVisibility(8);
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(Integer.valueOf(2130903153));
    localArrayList.add(Integer.valueOf(2131362073));
    localArrayList.add(Integer.valueOf(2131362072));
    localArrayList.add(Integer.valueOf(2130837699));
    this.mAdapter = new NewsItemAdapter(this, this.mHomeNewsData, 0, null);
    this.mAdapter.setInputListener(this);
    this.mAdapter.setOnViewMoreClickListener(this.mOnViewMoreClickListener);
    this.mAdapter.setCurrentUid(this.m_fuid);
    this.mLvPeopleInfo = ((ListView)paramView.findViewById(2131362508));
    this.mPeopleInfoAdapter = new HomePeopleInfoAdapter(getActivity(), 2130903154, this.mPeopleInfoData);
    this.mFooterView = getActivity().getLayoutInflater().inflate(2130903155, null);
    this.layoutPrivacy = ((TextView)this.mFooterView.findViewById(2131362681));
    this.mLvPeopleInfo.addHeaderView(this.mHeaderView, this.mHeaderView, true);
    this.mLvPeopleInfo.addFooterView(this.mFooterView);
    this.mLvPeopleInfo.setAdapter(this.mPeopleInfoAdapter);
    this.mLvPeopleInfo.setOnItemClickListener(this);
    this.mLvPeopleInfo.setVisibility(8);
    this.mDownView.setVisibility(0);
    if (User.getInstance().getUID().compareTo(this.m_fuid) != 0)
    {
      NewsItemAdapter.NewsItemUI localNewsItemUI1 = new NewsItemAdapter.NewsItemUI();
      localNewsItemUI1.mFirstBackGround = 2130838338;
      localNewsItemUI1.mItemBackGround = 2130837700;
      this.mAdapter.setItemStyle(localNewsItemUI1);
    }
    while (true)
    {
      this.lvHomeNews.setAdapter(this.mAdapter);
      this.lvHomeNews.setOnScrollListener(this);
      this.lvHomeNews.setOnItemClickListener(this);
      this.lvHomeNews.setOnItemLongClickListener(this);
      this.lvHomeNews.setOnTouchListener(this);
      return;
      NewsItemAdapter.NewsItemUI localNewsItemUI2 = new NewsItemAdapter.NewsItemUI();
      localNewsItemUI2.mItemBackGround = 2130837700;
      this.mAdapter.setItemStyle(localNewsItemUI2);
    }
  }

  private void initTitle(View paramView)
  {
    ((ImageView)paramView.findViewById(2131362919)).setVisibility(8);
    this.tvTitle = ((TextView)paramView.findViewById(2131362920));
    setHomeTitle();
    this.btnRight = ((ImageView)paramView.findViewById(2131362928));
    this.btnRightBar = ((ImageView)paramView.findViewById(2131362924));
    this.btnRight.setVisibility(8);
    this.btnRightBar.setVisibility(8);
    this.btnRight.setOnClickListener(this);
    this.btnRightBar.setOnClickListener(this);
    this.rightProBar = ((ProgressBar)paramView.findViewById(2131362929));
    this.btnLeft = ((ImageView)paramView.findViewById(2131362914));
    this.btnLeft.setOnClickListener(this);
    initTitleButton();
  }

  private void initTitleButton()
  {
    if (User.getInstance().getUID().compareTo(this.m_fuid) == 0)
    {
      this.btnRight.setImageResource(2130838834);
      return;
    }
    this.btnRight.setVisibility(8);
  }

  private boolean isHasNet()
  {
    ConnectivityManager localConnectivityManager = (ConnectivityManager)getActivity().getSystemService("connectivity");
    NetworkInfo.State localState1 = localConnectivityManager.getNetworkInfo(0).getState();
    NetworkInfo.State localState2 = localConnectivityManager.getNetworkInfo(1).getState();
    int i;
    if ((localState2 != NetworkInfo.State.CONNECTING) && (localState2 != NetworkInfo.State.CONNECTED) && (localState1 != NetworkInfo.State.CONNECTING))
    {
      NetworkInfo.State localState3 = NetworkInfo.State.CONNECTED;
      i = 0;
      if (localState1 != localState3);
    }
    else
    {
      i = 1;
    }
    return i;
  }

  private void refreshData(int paramInt1, int paramInt2)
  {
    if ((this.dataTask != null) && (this.dataTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.rightProBar.setVisibility(8);
      this.btnRight.setImageResource(2130838834);
      this.btnRight.setEnabled(true);
      return;
    }
    String[] arrayOfString = new String[6];
    arrayOfString[0] = Integer.toString(paramInt1);
    arrayOfString[1] = Integer.toString(paramInt2);
    arrayOfString[2] = "all";
    arrayOfString[5] = this.m_fuid;
    String str;
    NewsInfo localNewsInfo;
    int j;
    if (paramInt1 > 0)
    {
      str = null;
      try
      {
        int i = -1 + this.mHomeNewsData.size();
        localNewsInfo = (NewsInfo)this.mHomeNewsData.get(i);
        if ((TextUtils.isEmpty(localNewsInfo.mFuid)) || ("-1".equals(localNewsInfo.mFuid)))
        {
          j = i - 1;
          if (j < 0)
          {
            this.rightProBar.setVisibility(8);
            this.btnRight.setImageResource(2130838834);
            this.btnRight.setEnabled(true);
            return;
          }
        }
      }
      catch (Exception localException)
      {
        KXLog.e("HomeActivity", "refreshData", localException);
      }
    }
    while (true)
    {
      arrayOfString[3] = str;
      this.dataTask = new GetDataTask(null);
      this.dataTask.execute(arrayOfString);
      return;
      localNewsInfo = (NewsInfo)this.mHomeNewsData.get(j);
      str = localNewsInfo.mCtime;
    }
  }

  private void refreshDone(int paramInt)
  {
    int i;
    if ((paramInt == 0) && (User.getInstance().getUID().compareTo(this.m_fuid) != 0))
    {
      this.btnRight.setVisibility(8);
      this.ismyfriend = this.newsModel.getIsmyfriend();
      this.indexprivacy = this.newsModel.getPrivacy();
      this.istar = this.newsModel.getIstar();
      this.gender = this.newsModel.getGender();
      if (!this.newsModel.getRealname().equals(""))
        this.m_fname = this.newsModel.getRealname();
      setHomeTitle();
      this.tvName.setText(this.m_fname);
      this.newsModel.getOnline();
      if ((this.istar.trim().compareTo("1") == 0) || (this.istar.trim().compareTo("2") == 0))
      {
        this.ivStar.setVisibility(0);
        this.ivStar.setImageResource(2130838822);
        TextUtils.isEmpty(this.newsModel.getStarintro());
      }
      String str1 = this.newsModel.getCoverUrl();
      if (!TextUtils.isEmpty(str1))
        displayPicture(this.ivCover, str1, 0);
      this.mGenderIcon.setVisibility(0);
      ImageView localImageView1 = this.mGenderIcon;
      if ((this.newsModel.getGender() == null) || (!this.newsModel.getGender().equals("0")))
        break label747;
      i = 2130838820;
      label256: localImageView1.setImageResource(i);
      this.mCity.setVisibility(0);
      this.mCity.setText(this.newsModel.getHomeCity());
      this.mConstellation.setVisibility(0);
      this.mConstellation.setText(this.newsModel.getConstellation());
      ArrayList localArrayList = new ArrayList();
      if (this.newsModel.getStateList() != null)
        localArrayList.addAll(this.newsModel.getStateList());
      if (!TextUtils.isEmpty(this.newsModel.getStatus()))
      {
        this.tvState.setVisibility(0);
        this.tvState.setTitleList(localArrayList);
      }
      if (TextUtils.isEmpty(this.m_flogo))
        this.m_flogo = this.newsModel.getLogo();
      String str2 = this.newsModel.getLogo120();
      if (str2 != null)
        this.m_flogo = str2;
      displayRoundPicture(this.ivLogo, this.m_flogo, ImageDownloader.RoundCornerType.hdpi_big, 2130838334);
      this.btnLayout.setVisibility(0);
      this.mPhotoNumText.setText(String.valueOf("照片 " + this.newsModel.getAllPhotoNum()));
      this.mDiaryNumText.setText(String.valueOf("日记 " + this.newsModel.getDiaryNum()));
      this.mFriendNumText.setText(String.valueOf("好友 " + this.newsModel.getFriendNum()));
      if (!TextUtils.isEmpty(this.newsModel.getNewPhotoUrl()))
      {
        this.mHeaderView.findViewById(2131362559).setVisibility(8);
        ImageView localImageView2 = (ImageView)this.mHeaderView.findViewById(2131362560);
        localImageView2.setVisibility(0);
        this.mHeaderView.findViewById(2131362561).setVisibility(0);
        displayPicture(localImageView2, this.newsModel.getNewPhotoUrl(), 0);
      }
      this.mBtnPhotoLayout.setEnabled(true);
      this.mBtnDiaryLayout.setEnabled(true);
      this.mBtnFriendLayout.setEnabled(true);
      this.mBtnRecordLayout.setEnabled(true);
      this.mBtnLocationLayout.setEnabled(true);
      initTitleButton();
      if (this.indexprivacy.compareTo("1") != 0)
        break label770;
      this.layoutPrivacy.setVisibility(0);
      this.btnLayout.setVisibility(8);
      this.mDownView.setVisibility(8);
      this.mDownView2.setVisibility(0);
      if (this.ismyfriend.compareTo("0") == 0)
        break label755;
      this.mLvPeopleInfo.setVisibility(0);
    }
    while (true)
    {
      this.mVisitorsLayout.setVisibility(0);
      refreshVisitorsData();
      return;
      this.btnRight.setVisibility(0);
      break;
      label747: i = 2130838821;
      break label256;
      label755: this.mLvPeopleInfo.setVisibility(0);
      setPeopleInfoListView();
      continue;
      label770: if (this.indexprivacy.compareTo("2") == 0)
      {
        this.layoutPrivacy.setVisibility(0);
        this.btnLayout.setVisibility(8);
        this.mDownView.setVisibility(8);
        this.mDownView2.setVisibility(0);
        if (this.ismyfriend.compareTo("0") != 0)
        {
          this.mLvPeopleInfo.setVisibility(0);
          continue;
        }
        this.mLvPeopleInfo.setVisibility(0);
        setPeopleInfoListView();
        continue;
      }
      this.btnLayout.setVisibility(0);
      showInfoCardButton(1);
      this.layoutPrivacy.setVisibility(8);
      setNewsContent();
      this.mLvPeopleInfo.setVisibility(8);
      this.mDownView.setVisibility(0);
      this.mDownView2.setVisibility(8);
      if (User.getInstance().getUID().compareTo(this.m_fuid) != 0)
      {
        if (this.ismyfriend.compareTo("0") == 0)
          this.btnRight.setImageResource(2130837657);
        while (true)
        {
          this.btnRightBar.setVisibility(0);
          this.btnRight.setVisibility(0);
          this.btnRightBar.setImageResource(2130839020);
          break;
          this.btnRight.setImageResource(2130838818);
        }
      }
      this.mEditStatusButton.setVisibility(0);
    }
  }

  private void refreshMore(boolean paramBoolean)
  {
    if ((this.dataTask != null) && (!this.dataTask.isCancelled()) && (this.dataTask.getStatus() != AsyncTask.Status.FINISHED))
      return;
    if (!super.checkNetworkAndHint(paramBoolean))
    {
      this.mAdapter.showLoadingFooter(false);
      return;
    }
    int i = this.mHomeNewsData.size();
    int j = 0;
    if (i > 0)
    {
      NewsInfo localNewsInfo = (NewsInfo)this.mHomeNewsData.get(i - 1);
      if ((!TextUtils.isEmpty(localNewsInfo.mFuid)) && (!"-1".equals(localNewsInfo.mFuid)))
        break label113;
    }
    label113: for (j = i - 1; ; j = i)
    {
      refreshData(j, 20);
      return;
    }
  }

  private void refreshVisitorsData()
  {
    if (HttpConnection.checkNetworkAvailable(getActivity()) < 0)
      return;
    assert (this.m_fuid != null);
    this.mGetVisitsTask = new GetVisitorsTask(null);
    String[] arrayOfString = { this.m_fuid, "home", this.m_fuid };
    this.mGetVisitsTask.execute(arrayOfString);
  }

  private void sendGift()
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(new FriendInfo(this.m_fname, this.m_fuid, this.m_flogo));
    Intent localIntent = new Intent(getActivity(), SendGiftFragment.class);
    Bundle localBundle = new Bundle();
    localBundle.putSerializable("checkedFriendsList", localArrayList);
    localIntent.putExtras(localBundle);
    AnimationUtil.startFragment(this, localIntent, 1);
  }

  private void setContactHead()
  {
    if (!ImageCache.getInstance().isCacheFileExists(this.m_flogo))
    {
      Toast.makeText(getActivity(), 2131427511, 0);
      return;
    }
    if (Integer.parseInt(Build.VERSION.SDK) < 7)
    {
      showDialog(502);
      return;
    }
    if (!TextUtils.isEmpty(this.m_flogo))
    {
      Intent localIntent = new Intent(getActivity(), PhoneContactsListFragment.class);
      Bundle localBundle = new Bundle();
      localBundle.putString("fuid", this.m_fuid);
      localBundle.putString("fname", this.m_fname);
      localBundle.putString("flogo", this.m_flogo);
      localIntent.putExtras(localBundle);
      AnimationUtil.startFragment(this, localIntent, 1);
      return;
    }
    showDialog(501);
  }

  private void setHomeTitle()
  {
    if (User.getInstance().getUID().compareTo(this.m_fuid) == 0)
    {
      setTitle(getString(2131427695));
      return;
    }
    setTitle(this.m_fname);
  }

  private void setMyLogo()
  {
    this.isUploadLogo = true;
    selectPictureFromGallery();
  }

  private void setNewsContent()
  {
    this.mHomeNewsData.clear();
    ArrayList localArrayList = this.newsModel.getNewsList();
    int i = this.newsModel.getTotalNum(null);
    if ((localArrayList == null) || (localArrayList.size() == 0));
    while (true)
    {
      try
      {
        NewsInfo localNewsInfo1 = new NewsInfo();
        localNewsInfo1.mFuid = "-1";
        this.mHomeNewsData.add(localNewsInfo1);
        if (this.mAdapter == null)
          continue;
        this.mAdapter.notifyDataSetChanged();
        if ((!this.newsModel.isFirstRefresh()) || (User.getInstance().getUID().compareTo(this.m_fuid) != 0))
          continue;
        if (HttpConnection.checkNetworkAvailable(getActivity().getApplicationContext()) >= 0)
          break;
        this.newsModel.setFirstRefresh(false);
        return;
      }
      catch (Exception localException)
      {
        KXLog.e("HomeActivity", "setNewsContent", localException);
        continue;
      }
      this.mHomeNewsData.addAll(localArrayList);
      if ((localArrayList != null) && (localArrayList.size() < i) && (this.newsModel.getRefreshNum() == 20))
      {
        NewsInfo localNewsInfo2 = new NewsInfo();
        localNewsInfo2.mFuid = "";
        this.mHomeNewsData.add(localNewsInfo2);
        this.mAdapter.setHideFooter(0);
        continue;
      }
      this.mAdapter.setHideFooter(1);
    }
    this.btnRight.setEnabled(false);
    this.btnRight.setImageResource(0);
    this.rightProBar.setVisibility(0);
    this.newsModel.setFirstRefresh(false);
    refreshData(0, 20);
  }

  private void setPeopleInfoListView()
  {
    this.mPeopleInfoData.clear();
    if (!this.newsModel.getSameFriends().equals(""))
    {
      HomePeopleInfo localHomePeopleInfo1 = new HomePeopleInfo();
      localHomePeopleInfo1.mCaption = getResources().getString(2131428077);
      localHomePeopleInfo1.mShowArrow = 1;
      localHomePeopleInfo1.mContent = this.newsModel.getSameFriends();
      this.mPeopleInfoData.add(localHomePeopleInfo1);
    }
    HomePeopleInfo localHomePeopleInfo2;
    if (!this.newsModel.getGender().equals(""))
    {
      localHomePeopleInfo2 = new HomePeopleInfo();
      localHomePeopleInfo2.mCaption = getResources().getString(2131427518);
      localHomePeopleInfo2.mShowArrow = 0;
      if (!this.newsModel.getGender().equals("0"))
        break label302;
    }
    label302: for (localHomePeopleInfo2.mContent = getResources().getString(2131427532); ; localHomePeopleInfo2.mContent = getResources().getString(2131427533))
    {
      this.mPeopleInfoData.add(localHomePeopleInfo2);
      if (!this.newsModel.getConstellation().equals(""))
      {
        HomePeopleInfo localHomePeopleInfo3 = new HomePeopleInfo();
        localHomePeopleInfo3.mCaption = getResources().getString(2131427520);
        localHomePeopleInfo3.mShowArrow = 0;
        localHomePeopleInfo3.mContent = this.newsModel.getConstellation();
        this.mPeopleInfoData.add(localHomePeopleInfo3);
      }
      if (!this.newsModel.getHomeCity().equals(""))
      {
        HomePeopleInfo localHomePeopleInfo4 = new HomePeopleInfo();
        localHomePeopleInfo4.mCaption = getResources().getString(2131427522);
        localHomePeopleInfo4.mShowArrow = 0;
        localHomePeopleInfo4.mContent = this.newsModel.getHomeCity();
        this.mPeopleInfoData.add(localHomePeopleInfo4);
      }
      if (this.mPeopleInfoAdapter != null)
        this.mPeopleInfoAdapter.notifyDataSetChanged();
      return;
    }
  }

  private void setTitle(String paramString)
  {
    if (this.tvTitle == null)
      this.tvTitle = ((TextView)getView().findViewById(2131362920));
    if (this.tvTitle == null)
      return;
    this.tvTitle.setVisibility(0);
    this.tvTitle.setText(paramString);
    this.tvTitle.setMaxWidth(dipToPx(150.0F));
  }

  private void showChatDetailPage()
  {
    Intent localIntent = new Intent(getActivity(), ChatDetailFragment.class);
    localIntent.putExtra("fuid", this.m_fuid);
    localIntent.putExtra("fname", this.m_fname);
    localIntent.putExtra("isGroup", false);
    AnimationUtil.startFragmentForResult(this, localIntent, 0, 3);
  }

  private void showCoverListPage()
  {
    Intent localIntent = new Intent(getActivity(), CoverListFragment.class);
    Bundle localBundle = new Bundle();
    localBundle.putString("fuid", this.m_fuid);
    localBundle.putString("id", this.newsModel.getCoverId());
    localIntent.putExtras(localBundle);
    AnimationUtil.startFragmentForResult(this, localIntent, 114, 3);
  }

  private void showDiaryListPage()
  {
    Intent localIntent = new Intent(getActivity(), DiaryListFragment.class);
    Bundle localBundle = new Bundle();
    localBundle.putString("fuid", this.m_fuid);
    localBundle.putString("fname", this.m_fname);
    localBundle.putString("ismyfriend", this.ismyfriend);
    localBundle.putString("gender", this.gender);
    localIntent.putExtras(localBundle);
    AnimationUtil.startFragment(this, localIntent, 1);
  }

  private void showEditState()
  {
    Intent localIntent = new Intent(getActivity(), WriteWeiboFragment.class);
    Bundle localBundle = new Bundle();
    localBundle.putInt("mode", 16);
    localIntent.putExtras(localBundle);
    AnimationUtil.startFragmentForResult(this, localIntent, 16, 3);
  }

  private void showFriendListPage()
  {
    Intent localIntent = new Intent(getActivity(), FriendsOfSomeoneFragment.class);
    Bundle localBundle = new Bundle();
    localBundle.putString("fuid", this.m_fuid);
    localBundle.putString("fname", this.m_fname);
    localBundle.putString("flogo", this.m_flogo);
    localBundle.putString("ismyfriend", this.ismyfriend);
    localIntent.putExtras(localBundle);
    AnimationUtil.startFragment(this, localIntent, 1);
  }

  private void showInfoCardButton(int paramInt)
  {
    View localView;
    if (this.mBtnAboutLayout != null)
    {
      localView = this.mBtnAboutLayout;
      if (paramInt <= 0)
        break label24;
    }
    label24: for (boolean bool = true; ; bool = false)
    {
      localView.setEnabled(bool);
      return;
    }
  }

  private void showInputVerify()
  {
    String str = NewFriendEngine.getInstance().getTipMsg();
    this.evVerify = new EditText(getActivity());
    EditText localEditText = this.evVerify;
    InputFilter[] arrayOfInputFilter = new InputFilter[1];
    arrayOfInputFilter[0] = new InputFilter.LengthFilter(50);
    localEditText.setFilters(arrayOfInputFilter);
    this.evVerify.setLayoutParams(new TableLayout.LayoutParams(-2, -2));
    new AlertDialog.Builder(getActivity()).setMessage(str).setTitle(2131427329).setView(this.evVerify).setPositiveButton(2131427381, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        HomeFragment.this.addVerify(true);
      }
    }).setNegativeButton(2131427587, null).show();
  }

  private void showLoactionListPage()
  {
    Intent localIntent = new Intent(getActivity(), LocationListFragment.class);
    Bundle localBundle = new Bundle();
    localBundle.putString("fuid", this.m_fuid);
    localBundle.putString("fname", this.m_fname);
    localBundle.putString("gender", this.gender);
    localBundle.putString("istar", this.newsModel.getIstar());
    localIntent.putExtras(localBundle);
    startFragment(localIntent, true, 1);
  }

  private void showLoading(boolean paramBoolean, int paramInt)
  {
    if (paramBoolean)
    {
      if (this.mHomeNewsData.size() == 0)
      {
        this.waitLayout.setVisibility(0);
        return;
      }
      this.waitLayout.setVisibility(8);
      return;
    }
    this.waitLayout.setVisibility(8);
  }

  private void showLogo(String paramString)
  {
    displayRoundPicture(this.ivLogo, paramString, ImageDownloader.RoundCornerType.hdpi_big, 2130838334);
  }

  private void showPhotoListPage()
  {
    Intent localIntent = new Intent(getActivity(), AlbumListFragment.class);
    Bundle localBundle = new Bundle();
    localBundle.putString("fuid", this.m_fuid);
    localBundle.putString("fname", this.m_fname);
    localBundle.putString("ismyfriend", this.ismyfriend);
    localBundle.putString("gender", this.gender);
    localIntent.putExtras(localBundle);
    AnimationUtil.startFragment(this, localIntent, 1);
  }

  private void showRecordListPage()
  {
    Intent localIntent = new Intent(getActivity(), RecordListFragment.class);
    Bundle localBundle = new Bundle();
    localBundle.putString("fuid", this.m_fuid);
    localBundle.putString("fname", this.m_fname);
    localBundle.putString("gender", this.gender);
    localBundle.putString("istar", this.newsModel.getIstar());
    localIntent.putExtras(localBundle);
    startFragment(localIntent, true, 1);
  }

  private void showUserinfoPage()
  {
    Intent localIntent = new Intent(getActivity(), UserAboutFragment.class);
    String str = this.newsModel.getLogo120();
    if (TextUtils.isEmpty(str))
      str = this.m_flogo;
    Bundle localBundle = new Bundle();
    localBundle.putString("fuid", this.m_fuid);
    localBundle.putString("fname", this.m_fname);
    localBundle.putString("flogo", str);
    localBundle.putString("state", this.newsModel.getStatus());
    localBundle.putString("online", this.newsModel.getOnline());
    localBundle.putString("stateTime", this.newsModel.getStatustime());
    localBundle.putString("star", this.newsModel.getIstar());
    localIntent.putExtras(localBundle);
    startFragmentForResultNew(localIntent, 113, 1, true);
  }

  private void showVisitorListPage()
  {
    Intent localIntent = new Intent(getActivity(), HomeVisitorFragment.class);
    Bundle localBundle = new Bundle();
    localBundle.putString("fuid", this.m_fuid);
    localBundle.putString("ismyfriend", this.ismyfriend);
    localIntent.putExtras(localBundle);
    AnimationUtil.startFragment(this, localIntent, 1);
  }

  private void startUploadHomeBgTask(String paramString)
  {
    String[] arrayOfString = { paramString };
    this.uploadBgTask = new UploadHomeBgTask();
    this.uploadBgTask.execute(arrayOfString);
  }

  public void curUserChanged()
  {
    clearHomeData();
    initTitleButton();
    initHeader();
    initMainViews(getView());
    initTitle(getView());
    if ((User.getInstance().getUID().equals(this.m_fuid)) && (UpdateEngine.getInstance().isHomeLoaded()) && (this.newsModel.newsListAll.size() > 0))
    {
      refreshDone(1);
      showLoading(false, -1);
      refreshMore(false);
    }
    while (true)
    {
      this.bMenuNeedChange = true;
      return;
      clearAndRefresh();
    }
  }

  protected void dealPhotoResult(String paramString1, String paramString2)
  {
    if (getActivity() == null);
    do
      return;
    while (TextUtils.isEmpty(paramString1));
    File localFile = new File(paramString1);
    if (!localFile.exists())
    {
      Toast.makeText(getActivity(), 2131427841, 0).show();
      return;
    }
    if (this.isUploadLogo)
    {
      Intent localIntent = new Intent("com.android.camera.action.CROP");
      localIntent.setDataAndType(Uri.fromFile(localFile), "image/*");
      localIntent.putExtra("crop", "true");
      localIntent.putExtra("aspectX", 1);
      localIntent.putExtra("aspectY", 1);
      localIntent.putExtra("outputX", 120);
      localIntent.putExtra("outputY", 120);
      localIntent.putExtra("noFaceDetection", true);
      localIntent.putExtra("return-data", true);
      startActivityForResult(localIntent, 112);
      return;
    }
    try
    {
      Bundle localBundle = new Bundle();
      localBundle.putString("filePathName", paramString1);
      localBundle.putString("fileFrom", paramString2);
      IntentUtil.launchEditPhotoForResult(getActivity(), this, 104, localBundle);
      return;
    }
    catch (Exception localException)
    {
      KXLog.e("KaixinDesktop", "onActivityResult", localException);
    }
  }

  protected boolean handleMessage(Message paramMessage)
  {
    switch (paramMessage.what)
    {
    default:
      return super.handleMessage(paramMessage);
    case 503:
    }
    hideGuide();
    return true;
  }

  public void initMenu(View paramView)
  {
    this.mUgcView = ((KXUGCView)paramView.findViewById(2131362511));
    this.mUgcView.setHander(this.mHandler);
    this.mUgcView.setOnUGCItemClickListener(new KXUGCView.OnUGCItemClickListener()
    {
      public void onUGCItemClick(int paramInt)
      {
        switch (paramInt)
        {
        default:
          return;
        case 0:
          IntentUtil.showPoiListFragment(HomeFragment.this, null);
          return;
        case 1:
          HomeFragment.this.writeNewRecord(null);
          return;
        case 2:
          HomeFragment.this.showUploadPhotoDialog(HomeFragment.this.getString(2131427636));
          return;
        case 3:
        }
        IntentUtil.showVoiceRecordFragment(HomeFragment.this, null);
      }

      public void onUGCItemShow()
      {
        View localView = HomeFragment.this.findViewById(2131362512);
        localView.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramView)
          {
            paramView.setVisibility(8);
            KXEnvironment.mGuideRecord = false;
            KXEnvironment.saveBooleanParams(HomeFragment.this.getActivity(), "guide_record", true, false);
          }
        });
        if (KXEnvironment.mGuideRecord);
        for (int i = 0; ; i = 8)
        {
          localView.setVisibility(i);
          return;
        }
      }
    });
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if (paramInt2 == -1)
    {
      NewsInfo localNewsInfo3;
      if (paramInt1 == 10)
      {
        localNewsInfo3 = this.mAdapter.getCurrentNewsInfo();
        if (localNewsInfo3 != null);
      }
      while (true)
      {
        return;
        String str6 = paramIntent.getStringExtra("cnum");
        String str7 = paramIntent.getStringExtra("upnum");
        if (str6 != null)
          localNewsInfo3.mCnum = str6;
        if (str7 != null)
          localNewsInfo3.mUpnum = str7;
        this.mAdapter.notifyDataSetChanged();
        return;
        if (paramInt1 == 2)
        {
          NewsInfo localNewsInfo2 = this.mAdapter.getCurrentNewsInfo();
          if (localNewsInfo2 == null)
            continue;
          localNewsInfo2.mCnum = String.valueOf(1 + Integer.parseInt(localNewsInfo2.mCnum));
          this.mAdapter.notifyDataSetChanged();
          Toast.makeText(getActivity(), 2131427450, 0).show();
          return;
        }
        if (paramInt1 == 0)
        {
          if (paramIntent != null)
          {
            String str5 = paramIntent.getStringExtra("content");
            this.newsModel.setStatus(str5);
            this.tvState.setTitleList(this.newsModel.getStateList());
            User.getInstance().setState(str5);
          }
          ((MainActivity)getActivity()).refreshLeftMenuInfo();
          return;
        }
        if (paramInt1 == 16)
        {
          String str4 = paramIntent.getStringExtra("content");
          this.newsModel.setStatus(str4);
          this.tvState.setTitleList(this.newsModel.getStateList());
          return;
        }
        if (paramInt1 == 112)
        {
          if (paramIntent == null)
            continue;
          Bundle localBundle2 = paramIntent.getExtras();
          if (localBundle2 == null)
            continue;
          this.mProgressDialog = ProgressDialog.show(getActivity(), "", getResources().getText(2131428301), true, true, null);
          String str3 = Environment.getExternalStorageDirectory() + "/kaixin001" + "/" + "kx_header_tmp.jpg";
          try
          {
            Bitmap localBitmap2 = (Bitmap)localBundle2.getParcelable("data");
            ImageCache.saveBitmapToFileWithAbsolutePath(getActivity(), localBitmap2, null, str3);
            Bitmap localBitmap3 = ImageUtil.getRoundedCornerBitmap(localBitmap2, ImageDownloader.RoundCornerType.hdpi_big);
            ImageCache.getInstance().addBitmapToHardCache(this.m_flogo, localBitmap3);
            this.updateUserLogTask = new UpdateUserLogoTask(null);
            String[] arrayOfString = new String[2];
            arrayOfString[0] = str3;
            arrayOfString[1] = this.m_fuid;
            this.updateUserLogTask.execute(arrayOfString);
            return;
          }
          catch (Exception localException)
          {
            while (true)
              localException.printStackTrace();
          }
        }
        if (paramInt1 == 113)
        {
          if (paramIntent.getBooleanExtra("changelogo", false))
          {
            String str2 = Environment.getExternalStorageDirectory() + "/kaixin001" + "/" + "kx_header_tmp.jpg";
            Bitmap localBitmap1 = ImageUtil.getRoundedCornerBitmap(BitmapFactory.decodeFile(str2), ImageDownloader.RoundCornerType.hdpi_big);
            ImageCache.saveBitmapToFileWithAbsolutePath(getActivity(), localBitmap1, null, str2);
            ImageCache.getInstance().addBitmapToHardCache(this.m_flogo, localBitmap1);
            this.ivLogo.setImageBitmap(localBitmap1);
          }
          String str1 = paramIntent.getStringExtra("city");
          this.newsModel.setHomeCity(str1);
          this.mCity.setText(str1);
          return;
        }
        if (paramInt1 == 114)
        {
          Bundle localBundle1 = paramIntent.getExtras();
          if ((localBundle1 == null) || (!localBundle1.containsKey("from")))
            continue;
          switch (localBundle1.getInt("from"))
          {
          default:
            return;
          case 22586:
            if (TextUtils.isEmpty(paramIntent.getExtras().getString("filepath")))
              continue;
            startUploadHomeBgTask(User.getInstance().getCoverPath());
            return;
          case 25521:
            if ((this.ivCover == null) || (TextUtils.isEmpty(User.getInstance().getCoverUrl())))
              continue;
            this.newsModel.setCoverId(User.getInstance().getCoverId());
            this.newsModel.setCoverUrl(User.getInstance().getCoverUrl());
            displayPicture(this.ivCover, this.newsModel.getCoverUrl(), 0);
            return;
          }
        }
        if (paramInt1 != 1300)
          break;
        int i = paramIntent.getIntExtra("comment", 0);
        int j = paramIntent.getIntExtra("forward", 0);
        int k = paramIntent.getIntExtra("zan", 0);
        NewsInfo localNewsInfo1 = this.mAdapter.getCurrentNewsInfo();
        if (localNewsInfo1 == null)
          continue;
        if (!TextUtils.isEmpty(localNewsInfo1.mCnum))
          localNewsInfo1.mCnum = String.valueOf(i + Integer.parseInt(localNewsInfo1.mCnum));
        if (!TextUtils.isEmpty(localNewsInfo1.mTnum))
          localNewsInfo1.mTnum = String.valueOf(j + Integer.parseInt(localNewsInfo1.mTnum));
        if (!TextUtils.isEmpty(localNewsInfo1.mUpnum))
          localNewsInfo1.mUpnum = String.valueOf(k + Integer.parseInt(localNewsInfo1.mUpnum));
        this.mAdapter.notifyDataSetChanged();
        return;
      }
      super.onActivityResult(paramInt1, paramInt2, paramIntent);
      return;
    }
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
  }

  public void onClick(View paramView)
  {
    if (paramView.equals(this.btnLeft))
    {
      Bundle localBundle = getArguments();
      if (((localBundle != null) && (localBundle.getString("from") != null) && (localBundle.getString("from").equals("KaixinMenuListFragment"))) || ((localBundle != null) && (localBundle.getString("action") != null) && (localBundle.getString("action").equals("com.kaixin001.VIEW_HOME_DETAIL"))))
        if (super.isMenuListVisibleInMain())
          super.showSubActivityInMain();
    }
    do
    {
      while (true)
      {
        return;
        super.showMenuListInMain();
        return;
        finish();
        return;
        if (paramView.equals(this.btnRight))
        {
          if (User.getInstance().getUID().compareTo(this.m_fuid) != 0)
          {
            if (this.ismyfriend.compareTo("0") == 0)
            {
              addNewFriend(null);
              return;
            }
            showChatDetailPage();
            return;
          }
          if (User.getInstance().getUID().compareTo(this.m_fuid) == 0)
          {
            if (!super.checkNetworkAndHint(true))
              continue;
            this.rightProBar.setVisibility(0);
            this.btnRight.setImageResource(0);
            clearAndRefresh();
            return;
          }
          doAction();
          return;
        }
        if (paramView == this.ivCover)
        {
          if (!this.m_fuid.equals(User.getInstance().getUID()))
            continue;
          showCoverListPage();
          return;
        }
        if (paramView != this.ivLogo)
          break;
        getActivity().getApplicationContext().getSharedPreferences("from_webpage", 0).edit().putBoolean("fromwebpage", false).commit();
        if (Build.VERSION.SDK_INT <= 5)
          continue;
        doPhoneContactCheck();
        return;
      }
      if (paramView == this.mLytStauts)
      {
        showEditState();
        return;
      }
      if (paramView == this.mBtnAboutLayout)
      {
        showUserinfoPage();
        return;
      }
      if (paramView == this.mBtnPhotoLayout)
      {
        showPhotoListPage();
        return;
      }
      if (paramView == this.mBtnDiaryLayout)
      {
        showDiaryListPage();
        return;
      }
      if (paramView == this.mBtnFriendLayout)
      {
        showFriendListPage();
        return;
      }
      if (paramView == this.mBtnRecordLayout)
      {
        showRecordListPage();
        return;
      }
      if (paramView == this.mBtnLocationLayout)
      {
        showLoactionListPage();
        return;
      }
      if (paramView == this.mBtnAddFriend)
      {
        addNewFriend(null);
        return;
      }
      if (paramView == this.mBtnChat)
      {
        showChatDetailPage();
        return;
      }
      if (paramView == this.mBtnGifts)
      {
        sendGift();
        return;
      }
      if (paramView != this.mVisitorNum)
        continue;
      showVisitorListPage();
      return;
    }
    while (paramView != this.btnRightBar);
    sendGift();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Bundle localBundle = getArguments();
    String str = null;
    if (localBundle != null)
      str = localBundle.getString("action");
    if ((str != null) && (!str.equals("com.kaixin001.VIEW_HOME_DETAIL")) && (CrashRecoverUtil.isCrashBefore()))
    {
      CrashRecoverUtil.crashRecover(getActivity().getApplicationContext());
      IntentUtil.returnToDesktop(getActivity());
      return;
    }
    User.getInstance().loadDataIfEmpty(getActivity().getApplicationContext());
    if (localBundle != null)
    {
      this.m_fuid = localBundle.getString("fuid");
      this.m_fname = localBundle.getString("fname");
      if ((this.m_fuid == null) || (this.m_fname == null))
        Toast.makeText(getActivity().getApplicationContext(), 2131427800, 0).show();
      if (User.getInstance().getUID().compareTo(this.m_fuid) == 0)
        this.isSelf = true;
      this.m_flogo = localBundle.getString("flogo");
      return;
    }
    Toast.makeText(getActivity().getApplicationContext(), 2131427800, 0).show();
  }

  protected Dialog onCreateDialog(int paramInt)
  {
    switch (paramInt)
    {
    default:
      return null;
    case 501:
      return DialogUtil.showMsgDlgLiteConfirm(getActivity(), 2131427882, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          paramDialogInterface.dismiss();
        }
      });
    case 502:
    }
    return DialogUtil.showMsgDlgLiteConfirm(getActivity(), 2131427884, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        paramDialogInterface.dismiss();
      }
    });
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903147, paramViewGroup, false);
  }

  public void onDestroy()
  {
    clearHomeData();
    NewsModel.clearHomeModel(this);
    if (this.mGuideTimer != null)
      this.mGuideTimer.cancel();
    if ((this.newFriendTask != null) && (!this.newFriendTask.isCancelled()) && (this.newFriendTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.newFriendTask.cancel(true);
      NewFriendEngine.getInstance().cancel();
      this.newFriendTask = null;
    }
    if ((this.addVerifyTask != null) && (!this.addVerifyTask.isCancelled()) && (this.addVerifyTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.addVerifyTask.cancel(true);
      AddVerifyEngine.getInstance().cancel();
      this.addVerifyTask = null;
    }
    if ((this.delStarTask != null) && (!this.delStarTask.isCancelled()) && (this.delStarTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.delStarTask.cancel(true);
      DelStarEngine.getInstance().cancel();
      this.delStarTask = null;
    }
    if ((this.dataTask != null) && (!this.dataTask.isCancelled()) && (this.dataTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.dataTask.cancel(true);
      NewsEngine.getInstance().cancel();
      this.dataTask = null;
    }
    cancelTask(this.mGetVisitsTask);
    if (this.bgBmp != null)
    {
      this.bgBmp.recycle();
      System.gc();
    }
    super.onDestroy();
  }

  public void onInputViewCancel(String paramString)
  {
  }

  public boolean onInputViewComplete(String paramString)
  {
    return false;
  }

  public boolean onInputViewShow(int paramInt, Object paramObject)
  {
    if ((paramObject != null) && ((paramObject instanceof NewsInfo)))
    {
      NewsInfo localNewsInfo = (NewsInfo)paramObject;
      if ("1018".equals(localNewsInfo.mNtype))
      {
        if (paramInt == 0)
        {
          if (localNewsInfo != null)
          {
            if (localNewsInfo.mMediaInfo != null)
              localNewsInfo.mMediaInfo.setState(1);
            if (localNewsInfo.mSubMediaInfo != null)
              localNewsInfo.mSubMediaInfo.setState(1);
          }
          KXMediaManager.getInstance().requestStopCurrentMedia();
          this.mAdapter.showWeiboDetail(localNewsInfo, 1);
          return true;
        }
        actionForward(localNewsInfo);
        return true;
      }
      if (paramInt == 0)
        return false;
      actionForward(localNewsInfo);
      return true;
    }
    return false;
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    if (paramAdapterView.equals(this.lvHomeNews))
    {
      try
      {
        if (this.mHomeNewsData == null)
          return;
        if (User.getInstance().GetLookAround())
        {
          FragmentActivity localFragmentActivity = getActivity();
          String[] arrayOfString = new String[2];
          arrayOfString[0] = getString(2131427338);
          arrayOfString[1] = getString(2131427339);
          DialogUtil.showSelectListDlg(localFragmentActivity, 2131427729, arrayOfString, new DialogInterface.OnClickListener()
          {
            public void onClick(DialogInterface paramDialogInterface, int paramInt)
            {
              switch (paramInt)
              {
              default:
                return;
              case 0:
                Intent localIntent2 = new Intent(HomeFragment.this.getActivity(), LoginActivity.class);
                AnimationUtil.startFragment(HomeFragment.this, localIntent2, 1);
                HomeFragment.this.getActivity().finish();
                return;
              case 1:
              }
              Intent localIntent1 = new Intent(HomeFragment.this.getActivity(), LoginActivity.class);
              Bundle localBundle = new Bundle();
              localBundle.putInt("regist", 1);
              localIntent1.putExtras(localBundle);
              AnimationUtil.startFragment(HomeFragment.this, localIntent1, 1);
              HomeFragment.this.getActivity().finish();
            }
          }
          , 1);
          return;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("HomeActivity", "onItemClick", localException);
        return;
      }
      NewsInfo localNewsInfo = (NewsInfo)this.mHomeNewsData.get((int)paramLong);
      if (!TextUtils.isEmpty(localNewsInfo.mFuid))
      {
        this.mAdapter.setCurrentNewsInfo(localNewsInfo);
        String str = localNewsInfo.mNtype;
        if (!"1088".equals(str))
        {
          if ("1072".equals(str))
          {
            this.mAdapter.showTruth(localNewsInfo);
            return;
          }
          if ("2".equals(str))
          {
            this.mAdapter.showDiaryDetail(localNewsInfo);
            return;
          }
          if ("1016".equals(str))
          {
            this.mAdapter.showVoteList(localNewsInfo);
            return;
          }
          if ("1018".equals(str))
          {
            this.mAdapter.showWeiboDetail(localNewsInfo);
            return;
          }
          if ("1210".equals(str))
          {
            this.mAdapter.showStyleBoxDiaryDetail(localNewsInfo);
            return;
          }
          if ("1242".equals(str))
          {
            this.mAdapter.showRepost3Item(localNewsInfo);
            return;
          }
          if ("1192".equals(str))
          {
            this.mAdapter.showCommentDetail(localNewsInfo);
            return;
          }
        }
      }
    }
    else if ((paramAdapterView.equals(this.mLvPeopleInfo)) && (!paramView.equals(this.mFooterView)) && (((HomePeopleInfo)this.mPeopleInfoData.get(paramInt - 1)).mCaption.equals(getString(2131428077))))
    {
      Intent localIntent = new Intent(getActivity(), MutualFriendsFragment.class);
      Bundle localBundle = new Bundle();
      localBundle.putString("UID", this.m_fuid);
      localIntent.putExtras(localBundle);
      AnimationUtil.startFragment(this, localIntent, 1);
    }
  }

  public boolean onItemLongClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    NewsInfo localNewsInfo;
    try
    {
      if (this.mHomeNewsData == null)
        return false;
      if ((paramLong < 0L) || (paramLong >= this.mHomeNewsData.size()))
        break label344;
      localNewsInfo = (NewsInfo)this.mHomeNewsData.get((int)paramLong);
      if (TextUtils.isEmpty(localNewsInfo.mFuid))
        break label344;
      if ("1088".equals(localNewsInfo.mNtype))
      {
        this.mAdapter.onRepostLongClicked(localNewsInfo);
        break label346;
      }
      if ("3".equals(localNewsInfo.mNtype))
        this.mAdapter.onStateLongClicked(localNewsInfo);
    }
    catch (Exception localException)
    {
      KXLog.e("HomeActivity", "onListItemClick", localException);
    }
    if ("1018".equals(localNewsInfo.mNtype))
    {
      this.mAdapter.onRecordLongClicked(localNewsInfo);
    }
    else if ("2".equals(localNewsInfo.mNtype))
    {
      this.mAdapter.onDiaryLongClicked(localNewsInfo);
    }
    else if ("1016".equals(localNewsInfo.mNtype))
    {
      this.mAdapter.onVoteLongClicked(localNewsInfo);
    }
    else if ("1".equals(localNewsInfo.mNtype))
    {
      this.mAdapter.onImageLongClicked(localNewsInfo);
    }
    else if ("1242".equals(localNewsInfo.mNtype))
    {
      if ("转发照片专辑".equals(localNewsInfo.mNtypename))
        this.mAdapter.onRepostAlbumLongClicked(localNewsInfo);
      else if ("转发照片".equals(localNewsInfo.mNtypename))
        this.mAdapter.onRepostPhotoLongClicked(localNewsInfo);
      else if ("转发投票".equals(localNewsInfo.mNtypename))
        this.mAdapter.onVoteLongClicked(localNewsInfo);
      else
        this.mAdapter.onOtherLongClicked(localNewsInfo);
    }
    else
    {
      this.mAdapter.onOtherLongClicked(localNewsInfo);
      break label346;
      label344: return false;
    }
    label346: return true;
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    return super.onKeyDown(paramInt, paramKeyEvent);
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

  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default:
      return super.onOptionsItemSelected(paramMenuItem);
    case 401:
      if (User.getInstance().getUID().compareTo(this.m_fuid) == 0)
      {
        this.rightProBar.setVisibility(0);
        this.btnRight.setImageResource(0);
      }
      clearAndRefresh();
      return true;
    case 402:
      showChatDetailPage();
      return true;
    case 403:
      addNewFriend(null);
      return true;
    case 407:
      delStar();
      return true;
    case 406:
      addNewFriend(null);
      return true;
    case 404:
      IntentUtil.returnToDesktop(getActivity());
      return true;
    case 405:
    }
    this.m_fuid = User.getInstance().getUID();
    this.m_fname = User.getInstance().getRealName();
    this.m_flogo = User.getInstance().getLogo();
    Bitmap localBitmap = ImageCache.getInstance().createSafeImage(this.m_flogo);
    if (localBitmap != null)
      this.ivLogo.setImageBitmap(localBitmap);
    while (true)
    {
      curUserChanged();
      return true;
      this.ivLogo.setImageResource(2130837561);
    }
  }

  public void onResume()
  {
    super.onResume();
    this.mAdapter.setVideoPressed(false);
    if (this.mUgcView != null)
      this.mUgcView.showOpenBtn();
  }

  public void onScroll(AbsListView paramAbsListView, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = -1 + (paramInt1 + paramInt2);
    int j = -1 + this.mAdapter.getCount();
    if ((i >= j - 2) && (j > 0) && (!this.mNeedAutoLoading))
    {
      ArrayList localArrayList = this.mHomeNewsData;
      boolean bool1 = false;
      if (localArrayList != null)
      {
        int k = this.mHomeNewsData.size();
        bool1 = false;
        if (k > 0)
        {
          NewsInfo localNewsInfo = (NewsInfo)this.mHomeNewsData.get(-1 + this.mHomeNewsData.size());
          if (!TextUtils.isEmpty(localNewsInfo.mFuid))
          {
            boolean bool2 = localNewsInfo.mFuid.equals("-1");
            bool1 = false;
            if (!bool2);
          }
          else
          {
            bool1 = true;
          }
        }
      }
      this.mNeedAutoLoading = bool1;
    }
  }

  public void onScrollStateChanged(AbsListView paramAbsListView, int paramInt)
  {
    if (paramInt == 0)
    {
      setCanLoad();
      if ((this.mNeedAutoLoading) && (this.mOnViewMoreClickListener != null))
      {
        this.mOnViewMoreClickListener.onViewMoreClick();
        this.mNeedAutoLoading = false;
      }
      return;
    }
    setNotCanLoad();
  }

  public boolean onTouch(View paramView, MotionEvent paramMotionEvent)
  {
    if (!User.getInstance().GetLookAround())
    {
      if (paramMotionEvent.getAction() != 0)
        break label26;
      this.mDownY = paramMotionEvent.getY();
    }
    while (true)
    {
      return false;
      label26: if ((paramMotionEvent.getAction() != 2) && (paramMotionEvent.getAction() != 1))
        continue;
      if (paramMotionEvent.getAction() == 2)
      {
        this.mDownY = paramMotionEvent.getY();
        continue;
      }
      this.mDownY = -1.0F;
    }
  }

  public void onUpdate()
  {
    if (User.getInstance().getUID().compareTo(this.m_fuid) == 0)
    {
      this.btnRight.setEnabled(false);
      this.btnRight.setImageResource(0);
      this.rightProBar.setVisibility(0);
    }
    refreshData(0, 20);
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    initHeader();
    initMainViews(paramView);
    initTitle(paramView);
    initAnimation();
    initMenu(paramView);
    if ((User.getInstance().getUID().equals(this.m_fuid)) && (UpdateEngine.getInstance().isHomeLoaded()) && (this.newsModel.newsListAll.size() > 0))
    {
      refreshDone(1);
      showLoading(false, -1);
      refreshMore(false);
      this.lvHomeNews.requestFocus();
      if ((User.getInstance().GetLookAround()) || (!User.getInstance().getUID().equals(this.m_fuid)))
        break label255;
      this.mUgcView.startAnimation(this.mShowAnimation);
      this.mUgcView.setVisibility(0);
    }
    while (true)
    {
      if (User.getInstance().getUID().equals(this.m_fuid))
        KXUBLog.log("homepage_myhome");
      KXEnvironment.mGuideAddFriend = KXEnvironment.loadBooleanParams(getActivity(), "guide_add_friend", true, true);
      KXEnvironment.mGuideRecord = KXEnvironment.loadBooleanParams(getActivity(), "guide_record", true, true);
      KXEnvironment.mGuideChangeLogo = KXEnvironment.loadBooleanParams(getActivity(), "guide_change_logo", true, true);
      this.mHandler.postDelayed(new Runnable()
      {
        public void run()
        {
          if (HomeFragment.this.isNeedReturn());
          View localView1;
          do
          {
            do
              return;
            while ((HomeFragment.this.getActivity() == null) || (HomeFragment.this.getView() == null));
            localView1 = HomeFragment.this.findViewById(2131362513);
            View localView2 = HomeFragment.this.findViewById(2131362514);
            ViewGroup.LayoutParams localLayoutParams = localView2.getLayoutParams();
            int[] arrayOfInt = new int[2];
            HomeFragment.this.ivCover.getLocationOnScreen(arrayOfInt);
            int i = HomeFragment.this.ivCover.getHeight() + arrayOfInt[1];
            if (localLayoutParams != null)
            {
              int k = i - HomeFragment.this.dipToPx(170.7F);
              if (k > 0)
              {
                localLayoutParams.height = k;
                localView2.setLayoutParams(localLayoutParams);
              }
            }
            localView1.setOnClickListener(new View.OnClickListener()
            {
              public void onClick(View paramView)
              {
                paramView.setVisibility(8);
                KXEnvironment.mGuideChangeLogo = false;
                KXEnvironment.saveBooleanParams(HomeFragment.this.getActivity(), "guide_change_logo", true, false);
              }
            });
          }
          while ((User.getInstance().GetLookAround()) || (!User.getInstance().getUID().equals(HomeFragment.this.m_fuid)));
          if (KXEnvironment.mGuideChangeLogo);
          for (int j = 0; ; j = 8)
          {
            localView1.setVisibility(j);
            return;
          }
        }
      }
      , 100L);
      return;
      if (dataInited())
      {
        refreshDone(1);
        showLoading(false, -1);
        refreshMore(false);
        break;
      }
      getServerData();
      break;
      label255: this.mUgcView.setVisibility(8);
    }
  }

  public void updateUserUpgradeView()
  {
    displayPicture(this.mLevelImage, this.newsModel.getLarge(), 0);
    this.mLevelId.setText("LV." + this.newsModel.getLevel());
    this.mLevelTitle.setText(this.newsModel.getTitle());
    this.mLevelExp.setText("经验值:" + this.newsModel.getExp());
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

  private class AddVerifyTask extends KXFragment.KXAsyncTask<String, Void, Integer>
  {
    private AddVerifyTask()
    {
      super();
    }

    protected Integer doInBackgroundA(String[] paramArrayOfString)
    {
      String str = null;
      if (paramArrayOfString != null)
      {
        int i = paramArrayOfString.length;
        str = null;
        if (i > 0)
          str = paramArrayOfString[0];
      }
      try
      {
        if (AddVerifyEngine.getInstance().addVerify(HomeFragment.this.getActivity().getApplicationContext(), HomeFragment.this.m_fuid, str))
        {
          Integer localInteger = Integer.valueOf(1);
          return localInteger;
        }
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
        return null;
      }
      return Integer.valueOf(0);
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      if (paramInteger == null);
      do
        return;
      while ((HomeFragment.this.getView() == null) || (HomeFragment.this.getActivity() == null));
      while (true)
      {
        try
        {
          if (HomeFragment.this.bStop)
            break;
          if (HomeFragment.this.mProgressDialog == null)
            continue;
          HomeFragment.this.mProgressDialog.dismiss();
          if (paramInteger.intValue() == 1)
          {
            HomeFragment.this.addVerifyResult();
            HomeFragment.this.addVerifyTask = null;
            return;
          }
        }
        catch (Exception localException)
        {
          KXLog.e("HomeActivity", "onPostExecute", localException);
          return;
        }
        Toast.makeText(HomeFragment.this.getActivity(), 2131427513, 0).show();
      }
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private class DelStarTask extends KXFragment.KXAsyncTask<Void, Void, Integer>
  {
    private DelStarTask()
    {
      super();
    }

    protected Integer doInBackgroundA(Void[] paramArrayOfVoid)
    {
      try
      {
        if (DelStarEngine.getInstance().delStar(HomeFragment.this.getActivity().getApplicationContext(), HomeFragment.this.m_fuid))
        {
          Integer localInteger = Integer.valueOf(1);
          return localInteger;
        }
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
        return null;
      }
      return Integer.valueOf(0);
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      if ((paramInteger == null) || (HomeFragment.this.getView() == null) || (HomeFragment.this.getActivity() == null))
        return;
      while (true)
      {
        try
        {
          if (HomeFragment.this.bStop)
            break;
          if (HomeFragment.this.mProgressDialog == null)
            continue;
          HomeFragment.this.mProgressDialog.dismiss();
          if (paramInteger.intValue() == 1)
          {
            Toast.makeText(HomeFragment.this.getActivity(), 2131427515, 0).show();
            HomeFragment.this.ismyfriend = "0";
            HomeFragment.this.initTitleButton();
            HomeFragment.this.delStarTask = null;
            return;
          }
        }
        catch (Exception localException)
        {
          KXLog.e("HomeActivity", "onPostExecute", localException);
          return;
        }
        Toast.makeText(HomeFragment.this.getActivity(), 2131427516, 0).show();
      }
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
    private int mStart = -1;

    private GetDataTask()
    {
      super();
    }

    protected Integer doInBackgroundA(String[] paramArrayOfString)
    {
      try
      {
        if (paramArrayOfString.length < 6)
          return Integer.valueOf(-1);
        this.mStart = Integer.parseInt(paramArrayOfString[0]);
        if (NewsEngine.getInstance().getHomeForData(HomeFragment.this.getActivity().getApplicationContext(), HomeFragment.this.newsModel, paramArrayOfString[0], paramArrayOfString[1], paramArrayOfString[2], paramArrayOfString[3], paramArrayOfString[4], paramArrayOfString[5]))
        {
          HomeFragment.this.newsModel.setFirstRefresh(false);
          return Integer.valueOf(this.mStart);
        }
        Integer localInteger = Integer.valueOf(-1);
        return localInteger;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
        return null;
      }
      catch (Exception localException)
      {
        KXLog.e("HomeActivity", "GetDataTask", localException);
      }
      return Integer.valueOf(-1);
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      if ((paramInteger == null) || (HomeFragment.this.getView() == null) || (HomeFragment.this.getActivity() == null));
      while (true)
      {
        return;
        if (paramInteger != null);
        try
        {
          if (HomeFragment.this.waitLayout != null)
            HomeFragment.this.waitLayout.setVisibility(8);
          HomeFragment.this.btnRight.setEnabled(true);
          if (User.getInstance().getUID().compareTo(HomeFragment.this.m_fuid) == 0)
          {
            HomeFragment.this.rightProBar.setVisibility(8);
            HomeFragment.this.btnRight.setImageResource(2130838834);
          }
          HomeFragment.this.updateUserUpgradeView();
          ArrayList localArrayList = HomeFragment.this.newsModel.getNewsList();
          int i = HomeFragment.this.newsModel.getTotalNum(null);
          if ((paramInteger.intValue() == 0) || (HomeFragment.this.mHomeNewsData.size() == 0) || (HomeFragment.this.mAdapter.isFooterShowLoading()))
          {
            if (paramInteger.intValue() >= 0)
              break label443;
            HomeFragment.this.refreshDone(0);
          }
          while (true)
          {
            if ((HomeFragment.this.btnRight.getVisibility() == 0) && (User.getInstance().getUID().compareTo(HomeFragment.this.m_fuid) != 0) && (HomeFragment.this.ismyfriend.compareTo("0") == 0))
            {
              View localView = HomeFragment.this.findViewById(2131362515);
              localView.setOnClickListener(new View.OnClickListener()
              {
                public void onClick(View paramView)
                {
                  paramView.setVisibility(8);
                  KXEnvironment.mGuideAddFriend = false;
                  KXEnvironment.saveBooleanParams(HomeFragment.this.getActivity(), "guide_add_friend", true, false);
                }
              });
              if ((!User.getInstance().GetLookAround()) && (!User.getInstance().getUID().equals(HomeFragment.this.m_fuid)))
              {
                boolean bool = KXEnvironment.mGuideAddFriend;
                j = 0;
                if (!bool)
                  break label467;
                localView.setVisibility(j);
              }
            }
            if ((paramInteger.intValue() == 0) && (HomeFragment.this.lvHomeNews != null))
              HomeFragment.this.lvHomeNews.setSelection(0);
            if ((localArrayList != null) && (localArrayList.size() < i))
              HomeFragment.this.dataTask = null;
            HomeFragment.this.mAdapter.showLoadingFooter(false);
            if ((this.mStart == 0) && (paramInteger.intValue() < 0))
            {
              Toast.makeText(HomeFragment.this.getActivity(), 2131427374, 0).show();
              HomeFragment.this.showInfoCardButton(0);
            }
            HomeFragment.this.dataTask = null;
            if ((HomeFragment.this.mDownView == null) || (!HomeFragment.this.mDownView.isFrefrshing()))
              break;
            HomeFragment.this.mDownView.onRefreshComplete();
            return;
            label443: HomeFragment.this.refreshDone(1);
          }
        }
        catch (Exception localException)
        {
          while (true)
          {
            KXLog.e("HomeActivity", "onPostExecute", localException);
            continue;
            label467: int j = 8;
          }
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

  private class GetVisitorsTask extends KXFragment.KXAsyncTask<String, Void, Boolean>
  {
    private GetVisitorsTask()
    {
      super();
    }

    protected Boolean doInBackgroundA(String[] paramArrayOfString)
    {
      String str = paramArrayOfString[2];
      try
      {
        boolean bool2 = HomeVisitorEngine.getInstance().doGetHomeVisitorsRequest(HomeFragment.this.getActivity(), str, "0", "20", HomeFragment.this.mVisitorsModel);
        bool1 = bool2;
        return Boolean.valueOf(bool1);
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
        while (true)
        {
          localSecurityErrorException.printStackTrace();
          boolean bool1 = false;
        }
      }
    }

    protected void onPostExecuteA(Boolean paramBoolean)
    {
      if ((paramBoolean == null) || (HomeFragment.this.getView() == null) || (HomeFragment.this.getActivity() == null));
      do
        return;
      while (!paramBoolean.booleanValue());
      HomeFragment.this.constructViews();
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private class NewFriendTask extends KXFragment.KXAsyncTask<String, Void, Integer>
  {
    private NewFriendTask()
    {
      super();
    }

    protected Integer doInBackgroundA(String[] paramArrayOfString)
    {
      String str = null;
      if (paramArrayOfString != null)
        str = paramArrayOfString[0];
      try
      {
        if (NewFriendEngine.getInstance().addNewFriend(HomeFragment.this.getActivity().getApplicationContext(), HomeFragment.this.m_fuid, str))
        {
          Integer localInteger = Integer.valueOf(1);
          return localInteger;
        }
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
        return null;
      }
      return Integer.valueOf(0);
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      if ((paramInteger == null) || (HomeFragment.this.getView() == null) || (HomeFragment.this.getActivity() == null))
        return;
      while (true)
      {
        try
        {
          if (HomeFragment.this.bStop)
            break;
          if (HomeFragment.this.mProgressDialog == null)
            continue;
          HomeFragment.this.mProgressDialog.dismiss();
          if (paramInteger.intValue() == 1)
          {
            HomeFragment.this.addNewFriendResult();
            HomeFragment.this.newFriendTask = null;
            return;
          }
        }
        catch (Exception localException)
        {
          KXLog.e("HomeActivity", "onPostExecute", localException);
          return;
        }
        Toast.makeText(HomeFragment.this.getActivity(), 2131427513, 0).show();
      }
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private class UpdateUserLogoTask extends KXFragment.KXAsyncTask<String, Void, Integer>
  {
    String filePathName;

    private UpdateUserLogoTask()
    {
      super();
    }

    protected Integer doInBackgroundA(String[] paramArrayOfString)
    {
      if ((paramArrayOfString == null) || (paramArrayOfString.length == 0));
      do
      {
        return null;
        this.filePathName = paramArrayOfString[0];
      }
      while (!new File(this.filePathName).exists());
      try
      {
        Integer localInteger = Integer.valueOf(NewsEngine.getInstance().postUserLogoRequest(HomeFragment.this.getActivity().getApplicationContext(), this.filePathName, paramArrayOfString[1]));
        return localInteger;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
      }
      return null;
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      if (HomeFragment.this.mProgressDialog != null)
      {
        HomeFragment.this.mProgressDialog.dismiss();
        HomeFragment.this.mProgressDialog = null;
      }
      if ((paramInteger == null) || (HomeFragment.this.getView() == null) || (HomeFragment.this.getActivity() == null));
      do
      {
        return;
        if (paramInteger.intValue() != 1)
        {
          Toast.makeText(HomeFragment.this.getActivity(), 2131428303, 0).show();
          return;
        }
        Toast.makeText(HomeFragment.this.getActivity().getApplicationContext(), 2131428302, 1).show();
        HomeFragment.this.setResult(-1);
      }
      while (TextUtils.isEmpty(this.filePathName));
      Bitmap localBitmap1 = BitmapFactory.decodeFile(this.filePathName);
      String str1 = HomeFragment.this.newsModel.getLogo120();
      String str2 = HomeFragment.this.m_flogo;
      int i = 0;
      if (str2 != null)
      {
        i = 0;
        if (str1 != null)
        {
          boolean bool = str1.equals(HomeFragment.this.m_flogo);
          i = 0;
          if (!bool)
          {
            HomeFragment.this.m_flogo = str1;
            i = 1;
          }
        }
      }
      Bitmap localBitmap2 = ImageUtil.getRoundedCornerBitmap(localBitmap1, ImageDownloader.RoundCornerType.hdpi_big);
      ImageCache.getInstance().addBitmapToHardCache(HomeFragment.this.m_flogo, localBitmap2);
      if (localBitmap2 != null)
        new File(this.filePathName).renameTo(new File(ImageCache.getInstance().getFileByUrl(HomeFragment.this.m_flogo)));
      if (i != 0)
        HomeFragment.this.displayRoundPicture(HomeFragment.this.ivLogo, HomeFragment.this.m_flogo, ImageDownloader.RoundCornerType.hdpi_big, 2130838334);
      User.getInstance().setNeedRefresh(true);
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  class UploadHomeBgTask extends KXFragment.KXAsyncTask<String, Void, Integer>
  {
    UploadHomeBgTask()
    {
      super();
    }

    protected Integer doInBackgroundA(String[] paramArrayOfString)
    {
      String str = paramArrayOfString[0];
      if (!HomeFragment.this.isHasNet())
        return Integer.valueOf(0);
      UploadPhotoEngine.getInstance().uploadHomePhoto(HomeFragment.this.getActivity(), "background", str, "手机背景图", "", "homebg", "1", "", "", "");
      return Integer.valueOf(1);
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      switch (paramInteger.intValue())
      {
      default:
        return;
      case 0:
        Toast.makeText(HomeFragment.this.getActivity(), "没有网络，上传失败", 0).show();
        return;
      case 1:
      }
      KaixinMenuListFragment.isRefreshBackground = true;
      User.getInstance().saveUserInfo(KXApplication.getInstance());
      HomeFragment.this.newsModel.setCoverId(User.getInstance().getCoverId());
      HomeFragment.this.newsModel.setCoverUrl(User.getInstance().getCoverUrl());
      HomeFragment.this.displayPicture(HomeFragment.this.ivCover, HomeFragment.this.newsModel.getCoverUrl(), 0);
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
 * Qualified Name:     com.kaixin001.fragment.HomeFragment
 * JD-Core Version:    0.6.0
 */