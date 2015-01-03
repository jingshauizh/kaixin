package com.kaixin001.fragment;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.Uri;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.activity.CaptureActivity;
import com.kaixin001.activity.KXEnvironment;
import com.kaixin001.activity.LoginActivity;
import com.kaixin001.activity.UpgradeDialogActivity;
import com.kaixin001.adapter.NewsItemAdapter;
import com.kaixin001.adapter.NewsItemAdapter.OnViewMoreClickListener;
import com.kaixin001.engine.NewsAdvertEngine;
import com.kaixin001.engine.NewsEngine;
import com.kaixin001.engine.PostCommentEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.engine.UpgradeEngine;
import com.kaixin001.engine.UploadTaskListEngine;
import com.kaixin001.item.NewsInfo;
import com.kaixin001.item.RecordInfo;
import com.kaixin001.item.RecordUploadTask;
import com.kaixin001.menu.EventMenuItem;
import com.kaixin001.menu.MenuItem.OnClickListener;
import com.kaixin001.model.AdvertModel;
import com.kaixin001.model.EventModel;
import com.kaixin001.model.EventModel.EventData;
import com.kaixin001.model.KaixinConst;
import com.kaixin001.model.NewsModel;
import com.kaixin001.model.RequestVo;
import com.kaixin001.model.Setting;
import com.kaixin001.model.UpgradeModel;
import com.kaixin001.model.User;
import com.kaixin001.parser.GDTDataParser;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.CrashRecoverUtil;
import com.kaixin001.util.DeviceUtil;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.KXUBLog;
import com.kaixin001.util.UpgradeDownloadFile;
import com.kaixin001.util.UserHabitUtils;
import com.kaixin001.view.ContentListWindow;
import com.kaixin001.view.ContentListWindow.DoSelect;
import com.kaixin001.view.KXInputView;
import com.kaixin001.view.KXInputView.KXInputListener;
import com.kaixin001.view.KXNewsBarView;
import com.kaixin001.view.KXUGCView;
import com.kaixin001.view.KXUGCView.OnUGCItemClickListener;
import com.kaixin001.view.PullToRefreshForNewsYearView;
import com.kaixin001.view.PullToRefreshForNewsYearView.PullToRefreshListener;
import com.kaixin001.view.media.KXMediaInfo;
import com.kaixin001.view.media.KXMediaManager;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;

public class NewsFragment extends BaseFragment
  implements View.OnClickListener, AdapterView.OnItemLongClickListener, ContentListWindow.DoSelect, PopupWindow.OnDismissListener, PullToRefreshForNewsYearView.PullToRefreshListener, KXInputView.KXInputListener, AbsListView.OnScrollListener, View.OnTouchListener
{
  private static final String COUNT_LOGIN_AT_WIFI = "loginNumAtWifi";
  private static final String COUNT_LOGIN_HAS_NEWVERSION = "loginNumAfterNewVersion";
  public static final String GDT_URL = "http://mi.gdt.qq.com/gdt_mview.fcg";
  private static final int MENU_DESKTOP_ID = 403;
  private static final int MENU_HOME_ME_ID = 404;
  private static final int MENU_REFRESH_ID = 401;
  private static final int MENU_TOP_ID = 402;
  private static final String PREFERENCE_LOGIN_COUNT = "loginNum";
  public static final String SCALE = "scale";
  private static final String TAG = "NewsFragment";
  public static final String appid = "1101491835";
  private static int countNoFriendNews = 0;
  private static int countNoPlazaNews = 0;
  private static boolean isShowTipBar = false;
  public static boolean mRefresh = false;
  public static final String posid = "9079537217180471308";
  private ImageView btnLeft;
  private ImageView btnRight;
  public int down_x;
  public int down_y;
  private NewsInfo gdtInfo;
  private GetDataTask getDataTask;
  private boolean hasClicked;
  private boolean isRefresh = false;
  private ImageView ivState;
  private FrameLayout logoLayout;
  private ListView lvHome;
  private NewsItemAdapter mAdapter;
  private AlertDialog mAlertDialog = null;
  private int mCurrentSelectType = 100;
  protected PullToRefreshForNewsYearView mDownView;
  private boolean mHasNew = false;
  private TranslateAnimation mHideAnimation;
  private NewsInfo mInputForData;
  private InputTask mInputTask;
  private int mInputType;
  private KXInputView mInputView;
  private ImageView mIvCenterArrow;
  private View mLoginLayout;
  private RelativeLayout mLytCenter;
  private boolean mNeedAutoLoading = false;
  private NewsItemAdapter.OnViewMoreClickListener mOnViewMoreClickListener = new NewsItemAdapter.OnViewMoreClickListener()
  {
    public void onViewMoreClick()
    {
      try
      {
        ArrayList localArrayList = NewsFragment.this.newsModel.getNewsList();
        boolean bool;
        if (localArrayList != null)
        {
          int i = localArrayList.size();
          int j = NewsFragment.this.newslist.size();
          bool = false;
          if (i >= j);
        }
        else
        {
          NewsFragment.this.mAdapter.showLoadingFooter(true);
          bool = true;
        }
        NewsFragment.this.getNewsData(bool, true);
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  };
  private ContentListWindow mPopupWindow;
  private ProgressDialog mProgressDialog;
  private PopupWindow mSettingOptionPopupWindow;
  private boolean mShowAddFriend = false;
  private TranslateAnimation mShowAnimation;
  private TextView mTopIntro1;
  private TextView mTopIntro2;
  private TextView mTopIntro3;
  private TextView mTvCenterContent;
  private KXUGCView mUgcView;
  private NotificationManager manager;
  private Handler mhandler = new Handler();
  private NewsModel newsModel = NewsModel.getInstance();
  private final ArrayList<NewsInfo> newslist = new ArrayList();
  private Notification notification;
  private ProgressBar proBar;
  private ArrayList<String> removelist = new ArrayList();
  private ProgressBar rightProBar;
  private LinearLayout topTipBar;
  public int up_x;
  public int up_y;
  private LinearLayout waitLayout;

  static
  {
    countNoFriendNews = 0;
    countNoPlazaNews = 0;
    mRefresh = true;
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
    startFragmentForResult(localIntent, 1201, 3, true);
  }

  private void cancelTask()
  {
    if ((this.getDataTask != null) && (!this.getDataTask.isCancelled()) && (this.getDataTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.getDataTask.cancel(true);
      NewsEngine.getInstance().cancel();
      this.getDataTask = null;
    }
  }

  private void clearNewsCount()
  {
    switch (this.mCurrentSelectType)
    {
    default:
      return;
    case 100:
      NewsModel.setHasNew(false);
      this.newsModel.setNewsCount(0);
      return;
    case 11:
    }
    this.newsModel.setPublicMore(0);
  }

  private boolean ensureInputHideMenu()
  {
    int i = this.mInputView.getVisibility();
    int j = 0;
    if (i == 0)
    {
      showInputView(false);
      j = 1;
    }
    return j;
  }

  private void getNewsData(boolean paramBoolean1, boolean paramBoolean2)
  {
    this.newslist.clear();
    int i = this.newsModel.getTotalNum(null);
    ArrayList localArrayList = this.newsModel.getNewsList();
    if ((localArrayList == null) || (localArrayList.size() == 0))
      if ((!User.getInstance().GetLookAround()) && (this.mCurrentSelectType == 100))
      {
        NewsInfo localNewsInfo1 = new NewsInfo();
        localNewsInfo1.mFuid = "10080";
        localNewsInfo1.mNtype = "5001";
        this.newslist.add(0, localNewsInfo1);
      }
    while (true)
    {
      if (this.mAdapter != null)
        this.mAdapter.notifyDataSetChanged();
      if (this.isRefresh)
      {
        this.lvHome.setSelection(0);
        this.isRefresh = false;
      }
      return;
      int j = localArrayList.size();
      try
      {
        this.newslist.addAll(localArrayList);
        showLoading(false, -1);
        if ((this.newsModel.getFriendNum() < 5) && (this.mCurrentSelectType == 100) && (!User.getInstance().GetLookAround()))
        {
          NewsInfo localNewsInfo4 = new NewsInfo();
          localNewsInfo4.mFuid = "10080";
          localNewsInfo4.mNtype = "5001";
          this.newslist.add(0, localNewsInfo4);
        }
        if (j < i)
        {
          NewsInfo localNewsInfo2 = new NewsInfo();
          localNewsInfo2.mFuid = "";
          this.newslist.add(localNewsInfo2);
          if (paramBoolean2)
            refreshMore(paramBoolean1);
        }
        if ((j >= 7) && (this.gdtInfo != null) && (!this.newslist.contains(this.gdtInfo)))
          this.newslist.add(6, this.gdtInfo);
        if (AdvertModel.getInstance().item == null)
          continue;
        NewsInfo localNewsInfo3 = new NewsInfo();
        localNewsInfo3.mFuid = "-2";
        this.newslist.add(0, localNewsInfo3);
      }
      catch (Exception localException)
      {
        KXLog.e("NewsFragment", "getNewsData", localException);
      }
    }
  }

  private void gotoHotRepost()
  {
    this.mTopIntro1.setText(getResources().getString(2131428559));
    this.mTopIntro2.setText(getResources().getString(2131428560));
    this.mTopIntro2.setTextColor(Color.parseColor("#38B2CF"));
    this.mTopIntro3.setVisibility(8);
    this.topTipBar.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        Intent localIntent = new Intent(NewsFragment.this.getActivity(), RepostListFragment.class);
        localIntent.putExtra("hot", true);
        AnimationUtil.startFragment(NewsFragment.this, localIntent, 1);
      }
    });
  }

  private void hideInputMethod()
  {
    View localView = getActivity().getCurrentFocus();
    if (localView != null)
      ((InputMethodManager)getActivity().getSystemService("input_method")).hideSoftInputFromWindow(localView.getWindowToken(), 2);
  }

  private void hideProgressDialog()
  {
    if (this.mProgressDialog != null)
    {
      this.mProgressDialog.dismiss();
      this.mProgressDialog = null;
    }
  }

  private void initAnimation()
  {
    this.mShowAnimation = new TranslateAnimation(0.0F, 0.0F, 200.0F, 0.0F);
    this.mHideAnimation = new TranslateAnimation(0.0F, 0.0F, 0.0F, 200.0F);
    this.mShowAnimation.setDuration(500L);
    this.mHideAnimation.setDuration(500L);
  }

  private void initBottomBar()
  {
    ArrayList localArrayList = EventModel.getInstance().getList();
    if ((localArrayList == null) || (localArrayList.size() == 0))
    {
      Event localEvent = new Event(null);
      EventModel.getInstance().setEvent(localEvent);
      return;
    }
    new Event(null).initEvent();
  }

  private void initGDT()
  {
    if (!User.getInstance().GetLookAround())
      setGDTAd();
  }

  private void initListView(View paramView)
  {
    Log.d("NewsFragment", "listView create");
    this.lvHome = ((ListView)paramView.findViewById(2131361968));
    this.lvHome.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
      {
        NewsFragment.this.onListItemClick(paramView, paramInt, paramLong);
        KXLog.d("FRAGMENT", NewsFragment.this.getClass().getName() + "(" + NewsFragment.this.toString() + ")" + " onItemClick()");
      }
    });
    this.lvHome.setOnItemLongClickListener(this);
    if (this.mAdapter == null)
      this.mAdapter = new NewsItemAdapter(this, this.newslist);
    this.mAdapter.setOnViewMoreClickListener(this.mOnViewMoreClickListener);
    this.mAdapter.setInputListener(this);
    this.lvHome.setAdapter(this.mAdapter);
    this.lvHome.setOnScrollListener(this);
    this.lvHome.setOnTouchListener(this);
  }

  private void initTipBar(View paramView)
  {
    this.topTipBar = ((LinearLayout)paramView.findViewById(2131362896));
    this.mTopIntro1 = ((TextView)paramView.findViewById(2131362897));
    this.mTopIntro2 = ((TextView)paramView.findViewById(2131362898));
    this.mTopIntro3 = ((TextView)paramView.findViewById(2131362899));
  }

  private void initTitle()
  {
    findViewById(2131361963).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        NewsFragment.this.lvHome.setSelection(0);
      }
    });
    this.btnRight = ((ImageView)findViewById(2131362928));
    this.btnRight.setImageResource(2130838484);
    this.btnRight.setVisibility(0);
    if (Setting.getInstance().getCType().startsWith("062"))
    {
      this.btnRight = ((ImageView)findViewById(2131362928));
      this.btnRight.setImageResource(2130839050);
      this.btnRight.setOnClickListener(this);
    }
    this.btnRight.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        if (NewsFragment.this.getActivity() != null)
        {
          UserHabitUtils.getInstance(NewsFragment.this.getActivity()).addUserHabit("click_news_erweima");
          Intent localIntent = new Intent();
          localIntent.setClass(NewsFragment.this.getActivity(), CaptureActivity.class);
          NewsFragment.this.getActivity().startActivityForResult(localIntent, 11);
        }
      }
    });
    this.rightProBar = ((ProgressBar)findViewById(2131362929));
    this.btnLeft = ((ImageView)findViewById(2131362914));
    this.btnLeft.setOnClickListener(this);
    ImageView localImageView = (ImageView)findViewById(2131362919);
    localImageView.setImageResource(2130839036);
    if (User.getInstance().GetLookAround())
      localImageView.setVisibility(0);
    while (!User.getInstance().GetLookAround())
    {
      this.mLytCenter = ((RelativeLayout)findViewById(2131362918));
      this.mLytCenter.setBackgroundResource(2130839038);
      this.mLytCenter.setOnClickListener(this);
      ViewGroup.LayoutParams localLayoutParams1 = this.mLytCenter.getLayoutParams();
      localLayoutParams1.height = -1;
      this.mLytCenter.setLayoutParams(localLayoutParams1);
      this.mTvCenterContent = ((TextView)findViewById(2131362920));
      this.mTvCenterContent.setVisibility(0);
      this.mTvCenterContent.setDuplicateParentStateEnabled(true);
      ViewGroup.LayoutParams localLayoutParams2 = this.mTvCenterContent.getLayoutParams();
      localLayoutParams2.height = -1;
      this.mTvCenterContent.setLayoutParams(localLayoutParams2);
      this.mIvCenterArrow = ((ImageView)findViewById(2131362921));
      this.mIvCenterArrow.setVisibility(0);
      updateTitleCenterContent(this.mCurrentSelectType);
      return;
      localImageView.setVisibility(8);
    }
    TextView localTextView = (TextView)findViewById(2131362920);
    localTextView.setText(getResources().getString(2131428419));
    localTextView.setVisibility(0);
    localImageView.setVisibility(8);
  }

  private void onInputComment(NewsInfo paramNewsInfo, String paramString)
  {
    this.mInputTask = new InputTask(null);
    String str = (String)this.mAdapter.getCommendId(paramNewsInfo).get(1);
    String[] arrayOfString = new String[5];
    arrayOfString[0] = paramNewsInfo.mNtype;
    arrayOfString[1] = str;
    arrayOfString[2] = paramNewsInfo.mFuid;
    arrayOfString[3] = "0";
    arrayOfString[4] = paramString;
    this.mInputTask.execute(arrayOfString);
    showProgressDialog(2131427436);
  }

  private void onInputForward(NewsInfo paramNewsInfo, String paramString)
  {
    this.mInputTask = new InputTask(null);
    String[] arrayOfString = { paramString };
    this.mInputTask.execute(arrayOfString);
    this.mInputForData.mTnum = (1 + Integer.parseInt(this.mInputForData.mTnum));
    if (this.mAdapter != null)
      this.mAdapter.notifyDataSetChanged();
    this.mInputView.setDefautText("");
    hideInputMethod();
    showInputView(false);
  }

  private void onListItemClick(View paramView, int paramInt, long paramLong)
  {
    NewsInfo localNewsInfo;
    String str2;
    try
    {
      if (this.newslist == null)
        return;
      localNewsInfo = (NewsInfo)this.newslist.get((int)paramLong);
      String str1 = localNewsInfo.mFuid;
      if (TextUtils.isEmpty(str1))
        return;
      if (str1.equals("-100"))
        downloadGDT(localNewsInfo);
      if (User.getInstance().GetLookAround())
        this.mAdapter.showLoginDialog();
      this.mAdapter.setCurrentNewsInfo(localNewsInfo);
      str2 = localNewsInfo.mNtype;
      if ("1088".equals(str2))
        return;
      if ("1072".equals(str2))
      {
        this.mAdapter.showTruth(localNewsInfo);
        return;
      }
    }
    catch (Exception localException)
    {
      KXLog.e("NewsFragment", "onListItemClick", localException);
      return;
    }
    if ("2".equals(str2))
    {
      this.mAdapter.showDiaryDetail(localNewsInfo);
      return;
    }
    if ("1016".equals(str2))
    {
      this.mAdapter.showVoteList(localNewsInfo);
      return;
    }
    if ("1018".equals(str2))
    {
      if (localNewsInfo != null)
      {
        if (localNewsInfo.mMediaInfo != null)
          localNewsInfo.mMediaInfo.setState(1);
        if (localNewsInfo.mSubMediaInfo != null)
          localNewsInfo.mSubMediaInfo.setState(1);
      }
      KXMediaManager.getInstance().requestStopCurrentMedia();
      this.mAdapter.showWeiboDetail(localNewsInfo);
      return;
    }
    if ("2".equals(str2))
    {
      this.mAdapter.showCommentDetail(localNewsInfo);
      return;
    }
    if ("1210".equals(str2))
    {
      this.mAdapter.showStyleBoxDiaryDetail(localNewsInfo);
      return;
    }
    if ("1242".equals(str2))
    {
      this.mAdapter.showRepost3Item(localNewsInfo);
      return;
    }
    if ("1192".equals(str2))
      this.mAdapter.showCommentDetail(localNewsInfo);
  }

  private void preSetTipBar()
  {
    this.topTipBar.setOnClickListener(null);
    this.mTopIntro1.setVisibility(0);
    this.mTopIntro2.setVisibility(0);
    this.mTopIntro3.setVisibility(0);
  }

  // ERROR //
  private void processApk(NewsInfo paramNewsInfo)
  {
    // Byte code:
    //   0: new 893	com/kaixin001/model/RequestVo
    //   3: dup
    //   4: invokespecial 894	com/kaixin001/model/RequestVo:<init>	()V
    //   7: astore_2
    //   8: aload_1
    //   9: getfield 897	com/kaixin001/item/NewsInfo:rl	Ljava/lang/String;
    //   12: astore_3
    //   13: aload_1
    //   14: getfield 900	com/kaixin001/item/NewsInfo:acttype	Ljava/lang/String;
    //   17: astore 4
    //   19: new 902	org/json/JSONObject
    //   22: dup
    //   23: invokespecial 903	org/json/JSONObject:<init>	()V
    //   26: astore 5
    //   28: aload 5
    //   30: ldc_w 904
    //   33: aload_0
    //   34: getfield 906	com/kaixin001/fragment/NewsFragment:down_x	I
    //   37: invokevirtual 910	org/json/JSONObject:put	(Ljava/lang/String;I)Lorg/json/JSONObject;
    //   40: pop
    //   41: aload 5
    //   43: ldc_w 911
    //   46: aload_0
    //   47: getfield 913	com/kaixin001/fragment/NewsFragment:down_y	I
    //   50: invokevirtual 910	org/json/JSONObject:put	(Ljava/lang/String;I)Lorg/json/JSONObject;
    //   53: pop
    //   54: aload 5
    //   56: ldc_w 914
    //   59: aload_0
    //   60: getfield 916	com/kaixin001/fragment/NewsFragment:up_x	I
    //   63: invokevirtual 910	org/json/JSONObject:put	(Ljava/lang/String;I)Lorg/json/JSONObject;
    //   66: pop
    //   67: aload 5
    //   69: ldc_w 917
    //   72: aload_0
    //   73: getfield 919	com/kaixin001/fragment/NewsFragment:up_y	I
    //   76: invokevirtual 910	org/json/JSONObject:put	(Ljava/lang/String;I)Lorg/json/JSONObject;
    //   79: pop
    //   80: new 799	java/lang/StringBuilder
    //   83: dup
    //   84: aload_3
    //   85: invokestatic 923	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   88: invokespecial 925	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   91: ldc_w 927
    //   94: invokevirtual 930	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   97: aload 4
    //   99: invokevirtual 930	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   102: ldc_w 932
    //   105: invokevirtual 930	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   108: aload 5
    //   110: invokevirtual 933	org/json/JSONObject:toString	()Ljava/lang/String;
    //   113: invokevirtual 930	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   116: invokevirtual 815	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   119: astore 11
    //   121: aload_2
    //   122: sipush 200
    //   125: invokevirtual 936	com/kaixin001/model/RequestVo:setSuccessCode	(I)V
    //   128: aload_2
    //   129: aload 11
    //   131: invokevirtual 939	com/kaixin001/model/RequestVo:setUrl	(Ljava/lang/String;)V
    //   134: aload_2
    //   135: new 941	com/kaixin001/parser/GDTLinkParser
    //   138: dup
    //   139: invokespecial 942	com/kaixin001/parser/GDTLinkParser:<init>	()V
    //   142: invokevirtual 946	com/kaixin001/model/RequestVo:setParser	(Lcom/kaixin001/parser/BaseParser;)V
    //   145: aload_0
    //   146: aload_2
    //   147: new 948	com/kaixin001/fragment/NewsFragment$20
    //   150: dup
    //   151: aload_0
    //   152: aload_1
    //   153: invokespecial 950	com/kaixin001/fragment/NewsFragment$20:<init>	(Lcom/kaixin001/fragment/NewsFragment;Lcom/kaixin001/item/NewsInfo;)V
    //   156: invokevirtual 954	com/kaixin001/fragment/NewsFragment:getDataFromeServer	(Lcom/kaixin001/model/RequestVo;Lcom/kaixin001/fragment/BaseFragment$DataCallback;)V
    //   159: return
    //   160: astore 6
    //   162: aload 6
    //   164: invokevirtual 957	org/json/JSONException:printStackTrace	()V
    //   167: return
    //   168: astore 6
    //   170: goto -8 -> 162
    //
    // Exception table:
    //   from	to	target	type
    //   19	28	160	org/json/JSONException
    //   28	159	168	org/json/JSONException
  }

  private void refresh(int paramInt)
  {
    if (isNeedReturn())
      return;
    if (!super.checkNetworkAndHint(true))
    {
      if ((this.mDownView != null) && (this.mDownView.isFrefrshing()))
        this.mDownView.onRefreshComplete();
      showLoading(false, -1);
      return;
    }
    this.isRefresh = true;
    showLoading(true, -1);
    refreshData(0, 20, paramInt);
  }

  private void refreshData(int paramInt1, int paramInt2, int paramInt3)
  {
    if ((this.getDataTask != null) && (!this.getDataTask.isCancelled()) && (this.getDataTask.getStatus() != AsyncTask.Status.FINISHED))
      return;
    String[] arrayOfString = new String[6];
    arrayOfString[0] = Integer.toString(paramInt1);
    arrayOfString[1] = Integer.toString(paramInt2);
    ArrayList localArrayList = this.newsModel.getNewsList();
    if (paramInt1 > 0)
    {
      String str = null;
      if (localArrayList != null)
      {
        int i = localArrayList.size();
        str = null;
        if (i > 0)
        {
          NewsInfo localNewsInfo = (NewsInfo)localArrayList.get(-1 + localArrayList.size());
          str = null;
          if (localNewsInfo != null)
            str = localNewsInfo.mCtime;
        }
      }
      arrayOfString[3] = str;
    }
    if ((paramInt3 == 100) || (paramInt3 < 0))
    {
      arrayOfString[2] = "all";
      if (User.getInstance().GetLookAround())
        arrayOfString[4] = "0";
    }
    while (true)
    {
      this.getDataTask = new GetDataTask(null);
      this.getDataTask.execute(arrayOfString);
      return;
      arrayOfString[4] = Integer.toString(100);
      continue;
      if (paramInt3 == 101)
      {
        arrayOfString[2] = "star";
        arrayOfString[4] = "";
        continue;
      }
      arrayOfString[2] = "all";
      arrayOfString[4] = Integer.toString(paramInt3);
    }
  }

  private void refreshDoneHeader()
  {
    this.rightProBar.setVisibility(8);
    this.btnRight.setVisibility(0);
  }

  private void refreshHeaderView()
  {
    this.rightProBar.setVisibility(0);
    this.btnRight.setVisibility(8);
  }

  private void refreshMore(boolean paramBoolean)
  {
    if (!super.checkNetworkAndHint(paramBoolean))
      this.mAdapter.showLoadingFooter(false);
    int i;
    int j;
    do
    {
      do
        return;
      while ((this.getDataTask != null) && (!this.getDataTask.isCancelled()) && (this.getDataTask.getStatus() != AsyncTask.Status.FINISHED));
      i = this.newsModel.getTotalNum(null);
      ArrayList localArrayList = this.newsModel.getNewsList();
      j = 0;
      if (localArrayList == null)
        continue;
      j = localArrayList.size();
    }
    while ((i <= 0) || (j >= i));
    refreshData(j, 20, this.mCurrentSelectType);
  }

  private void sendNewsUpdateDoneBroadcast()
  {
    Intent localIntent = new Intent("com.kaixin001.UPDATE_NEWS_DONE_BROADCAST");
    getActivity().sendBroadcast(localIntent);
  }

  private void setGDTAd()
  {
    RequestVo localRequestVo = new RequestVo();
    JSONObject localJSONObject1 = new JSONObject();
    try
    {
      JSONObject localJSONObject2 = new JSONObject();
      localJSONObject2.put("c_device", DeviceUtil.getBrandAndType());
      localJSONObject2.put("c_w", getScreenWidth());
      localJSONObject2.put("c_h", getScreenHeight());
      localJSONObject2.put("c_pkgname", DeviceUtil.getPackageName(getActivity()));
      localJSONObject2.put("muidtype", 1);
      localJSONObject2.put("muid", DeviceUtil.getImei(getActivity()));
      localJSONObject2.put("conn", DeviceUtil.getConnType(getActivity()));
      localJSONObject2.put("carrier", DeviceUtil.getCarrier(getActivity()));
      localJSONObject2.put("c_os", "android");
      localJSONObject2.put("apiver", "1.1");
      localJSONObject2.put("postype", 5);
      localJSONObject2.put("appid", "1101491835");
      localJSONObject2.put("useragent", new WebView(getActivity()).getSettings().getUserAgentString());
      localJSONObject2.put("remoteip", DeviceUtil.getLocalIP(getActivity()));
      localJSONObject1.put("req", localJSONObject2);
      localRequestVo.setUrl("http://mi.gdt.qq.com/gdt_mview.fcg?adposcount=1&count=1&posid=9079537217180471308&posw=1000&posh=560&charset=utf8&datafmt=json" + "&ext=" + localJSONObject1.toString());
      localRequestVo.setParser(new GDTDataParser());
      localRequestVo.setSuccessCode(200);
      getDataFromeServer(localRequestVo, new BaseFragment.DataCallback()
      {
        public void requestFalse()
        {
        }

        public void requestSuccess(Object paramObject)
        {
          if ((paramObject != null) && ((paramObject instanceof NewsInfo)))
          {
            NewsFragment.this.gdtInfo = ((NewsInfo)paramObject);
            NewsFragment.this.setGDTStas(NewsFragment.this.gdtInfo);
          }
        }
      });
      return;
    }
    catch (JSONException localJSONException)
    {
      while (true)
        localJSONException.printStackTrace();
    }
  }

  public static void setShowCloudAlbumItemNew(Context paramContext, boolean paramBoolean)
  {
    SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(paramContext).edit();
    localEditor.putBoolean("kxplaza_new_" + User.getInstance().getUID(), paramBoolean);
    localEditor.commit();
  }

  private void setTipBar(int paramInt1, int paramInt2)
  {
    preSetTipBar();
    switch (this.mCurrentSelectType)
    {
    default:
      return;
    case 100:
      if (paramInt1 > 0)
      {
        this.mTopIntro1.setText(getResources().getString(2131428554));
        this.mTopIntro2.setText(String.valueOf(paramInt1));
        this.mTopIntro2.setTextColor(Color.parseColor("#38B2CF"));
        this.mTopIntro3.setText(getResources().getString(2131428555));
        countNoFriendNews = 0;
        return;
      }
      countNoFriendNews = 1 + countNoFriendNews;
      if (countNoFriendNews % 3 == 1)
      {
        this.mTopIntro1.setText(getResources().getString(2131428557));
        this.mTopIntro2.setText(getResources().getString(2131428558));
        this.mTopIntro2.setTextColor(Color.parseColor("#38B2CF"));
        this.mTopIntro3.setVisibility(8);
        this.topTipBar.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramView)
          {
            NewsFragment.this.doSelect(11);
            ((TextView)NewsFragment.this.getView().findViewById(2131362920)).setText(NewsFragment.this.getResources().getString(2131428419));
          }
        });
        return;
      }
      if (countNoFriendNews % 3 == 2)
      {
        gotoHotRepost();
        return;
      }
      showOneTip(getResources().getString(2131428561));
      return;
    case 11:
    }
    if (paramInt2 != 0)
    {
      showOneTip(getResources().getString(2131428556));
      countNoPlazaNews = 0;
      return;
    }
    countNoPlazaNews = 1 + countNoPlazaNews;
    if (countNoPlazaNews % 2 == 1)
    {
      gotoHotRepost();
      return;
    }
    showOneTip(getResources().getString(2131428561));
  }

  public static boolean showCloudAlbumItemNew(Context paramContext)
  {
    return PreferenceManager.getDefaultSharedPreferences(paramContext).getBoolean("kxplaza_new_" + User.getInstance().getUID(), true);
  }

  private void showInputView(boolean paramBoolean)
  {
    int i = 0;
    if (paramBoolean)
      i = (int)(48.0F * getResources().getDisplayMetrics().density);
    KXInputView localKXInputView = this.mInputView;
    if (paramBoolean);
    for (int j = 0; ; j = 8)
    {
      localKXInputView.setVisibility(j);
      getView().findViewById(2131362895).setPadding(0, 0, 0, i);
      return;
    }
  }

  private void showLoading(boolean paramBoolean, int paramInt)
  {
    if (paramBoolean)
    {
      refreshHeaderView();
      if (this.newslist.size() == 0)
      {
        this.waitLayout.setVisibility(0);
        this.proBar.setVisibility(0);
      }
    }
    do
    {
      return;
      this.waitLayout.setVisibility(8);
      this.lvHome.setVisibility(0);
      this.proBar.setVisibility(8);
      return;
    }
    while (paramBoolean);
    refreshDoneHeader();
    if (this.newslist.size() == 0)
    {
      this.lvHome.setVisibility(8);
      if (paramInt >= 0)
      {
        this.waitLayout.setVisibility(0);
        this.proBar.setVisibility(8);
        return;
      }
      this.waitLayout.setVisibility(8);
      return;
    }
    this.waitLayout.setVisibility(8);
    this.lvHome.setVisibility(0);
  }

  private void showOneTip(String paramString)
  {
    this.mTopIntro1.setText(paramString);
    this.mTopIntro2.setVisibility(8);
    this.mTopIntro3.setVisibility(8);
  }

  private void showPopUpWindow(View paramView)
  {
    View localView = getActivity().getLayoutInflater().inflate(2130903239, null, false);
    if ((this.mPopupWindow != null) && (this.mPopupWindow.isShowing()))
      try
      {
        this.mPopupWindow.dismiss();
        this.mPopupWindow = null;
        return;
      }
      catch (Exception localException)
      {
        while (true)
          localException.printStackTrace();
      }
    int i = getResources().getConfiguration().orientation;
    int[] arrayOfInt = new int[2];
    ImageView localImageView = (ImageView)getView().findViewById(2131362916);
    localImageView.getLocationOnScreen(arrayOfInt);
    int j = arrayOfInt[1] + localImageView.getHeight();
    int k = arrayOfInt[0] - 5 * localImageView.getWidth();
    int m = getResources().getDisplayMetrics().widthPixels - k * 2;
    if (i == 1);
    for (this.mPopupWindow = new ContentListWindow(localView, m, -2, true, getActivity(), this.mCurrentSelectType); ; this.mPopupWindow = new ContentListWindow(localView, m, -2, true, getActivity(), this.mCurrentSelectType))
    {
      this.mPopupWindow.setDoSelectListener(this);
      this.mPopupWindow.setOnDismissListener(this);
      this.mPopupWindow.setOutsideTouchable(true);
      this.mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
      this.mPopupWindow.setClippingEnabled(false);
      this.mPopupWindow.showAtLocation(paramView, 51, k, j);
      return;
    }
  }

  private void showProgressDialog(int paramInt)
  {
    String str = getResources().getString(paramInt);
    hideProgressDialog();
    this.mProgressDialog = ProgressDialog.show(getActivity(), null, str);
  }

  private void showTipBar()
  {
    this.topTipBar.setVisibility(0);
    this.topTipBar.bringToFront();
    this.mhandler.postDelayed(new Runnable()
    {
      public void run()
      {
        if (NewsFragment.this.isNeedReturn())
          return;
        NewsFragment.this.topTipBar.setVisibility(8);
      }
    }
    , 1500L);
  }

  private void showUpdateDialog()
  {
    SharedPreferences localSharedPreferences = getActivity().getSharedPreferences("loginNum", 0);
    int i = 1 + localSharedPreferences.getInt("loginCount", 0);
    SharedPreferences.Editor localEditor = localSharedPreferences.edit();
    localEditor.putInt("loginCount", i);
    localEditor.commit();
    boolean bool1;
    Boolean localBoolean;
    if (i == 1)
    {
      bool1 = true;
      localBoolean = Boolean.valueOf(bool1);
      if (User.getInstance().GetLookAround())
        if ((i == 1) || (i == 2))
          break label128;
    }
    label128: for (boolean bool2 = false; ; bool2 = true)
    {
      localBoolean = Boolean.valueOf(bool2);
      if (localBoolean.booleanValue())
        new CheckNewUpdatesTask().execute(new Void[] { null });
      return;
      bool1 = false;
      break;
    }
  }

  private void showUpdateTime()
  {
    if (isNeedReturn());
    String str1;
    do
    {
      return;
      str1 = NewsModel.getInstance().getUpdateTime();
    }
    while (TextUtils.isEmpty(str1));
    String str2 = getResources().getString(2131427404);
    this.mDownView.setUpdateTime(str2 + " " + str1);
  }

  private void updateState()
  {
    Intent localIntent = new Intent(getActivity(), InputFragment.class);
    Bundle localBundle = new Bundle();
    localBundle.putInt("mode", 0);
    localIntent.putExtras(localBundle);
    startActivityForResult(localIntent, 0);
  }

  private void updateTitleCenterContent(int paramInt)
  {
    if (paramInt > 0)
    {
      String str = ContentListWindow.getStringByType(getActivity(), paramInt);
      this.mTvCenterContent.setText(str);
    }
    this.mIvCenterArrow.setImageResource(2130839007);
  }

  public void addComment(NewsInfo paramNewsInfo)
  {
    try
    {
      Intent localIntent = new Intent(getActivity(), InputFragment.class);
      Bundle localBundle = new Bundle();
      localBundle.putString("id", paramNewsInfo.mNewsId);
      localBundle.putString("type", paramNewsInfo.mNtype);
      localBundle.putString("ntypename", paramNewsInfo.mNtypename);
      localBundle.putString("ouid", paramNewsInfo.mFuid);
      localBundle.putInt("mode", 2);
      localBundle.putString("fname", paramNewsInfo.mFname);
      localBundle.putString("title", paramNewsInfo.mTitle);
      localBundle.putString("intro", paramNewsInfo.mIntro);
      localBundle.putString("stime", paramNewsInfo.mStime);
      localBundle.putString("commentflag", paramNewsInfo.mCommentFlag);
      localIntent.putExtras(localBundle);
      startActivityForResult(localIntent, 2);
      return;
    }
    catch (Exception localException)
    {
      KXLog.e("NewsFragment", "CommentOnClickListener", localException);
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
      KXLog.e("KaixinDesktop", "onActivityResult", localException);
    }
  }

  public int dipToPx(float paramFloat)
  {
    return (int)(0.5F + paramFloat * getResources().getDisplayMetrics().density);
  }

  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent)
  {
    if ((this.mInputView != null) && (this.mInputView.getVisibility() == 0) && (paramMotionEvent.getY() < this.mInputView.getTop()))
    {
      showInputView(false);
      return true;
    }
    return super.dispatchTouchEvent(paramMotionEvent);
  }

  protected void displayNotification(String paramString1, String paramString2, int paramInt)
  {
    Intent localIntent = new Intent(getActivity(), getClass());
    localIntent.addFlags(536870912);
    PendingIntent localPendingIntent = PendingIntent.getActivity(getActivity(), 0, localIntent, 0);
    this.notification = new Notification(2130838348, paramString1, System.currentTimeMillis());
    RemoteViews localRemoteViews = new RemoteViews(getActivity().getPackageName(), 2130903264);
    localRemoteViews.setImageViewBitmap(2131363286, BitmapFactory.decodeResource(getResources(), 2130838348));
    localRemoteViews.setTextViewText(2131363287, paramString2);
    localRemoteViews.setProgressBar(2131363288, 100, paramInt, false);
    this.notification.contentView = localRemoteViews;
    this.notification.contentIntent = localPendingIntent;
    Notification localNotification2;
    if (paramInt == 100)
      localNotification2 = this.notification;
    Notification localNotification1;
    for (localNotification2.flags = (0x10 | localNotification2.flags); ; localNotification1.flags = (0x2 | localNotification1.flags))
    {
      ((NotificationManager)getActivity().getSystemService("notification")).notify(0, this.notification);
      return;
      localNotification1 = this.notification;
    }
  }

  public void doSelect(int paramInt)
  {
    if (paramInt == -1)
      return;
    if (paramInt == 11)
      UserHabitUtils.getInstance(getActivity()).addUserHabit("click_index_hotfeed");
    this.mCurrentSelectType = paramInt;
    this.newslist.clear();
    if (this.mAdapter != null)
      this.mAdapter.notifyDataSetChanged();
    refresh(paramInt);
  }

  protected void downloadApk(Object paramObject, NewsInfo paramNewsInfo)
  {
    if (this.hasClicked)
      Toast.makeText(getActivity(), "应用已经下载", 0).show();
    do
      return;
    while (!(paramObject instanceof JSONObject));
    this.hasClicked = true;
    JSONObject localJSONObject = (JSONObject)paramObject;
    downloadFile(localJSONObject.optString("dstlink"), new BaseFragment.ProgressCallBack(paramNewsInfo, localJSONObject.optString("clickid"))
    {
      public void downloadEnd(File paramFile)
      {
        NewsFragment.this.displayNotification("下载完成", "下载成功", 100);
        RequestVo localRequestVo = new RequestVo();
        localRequestVo.setUrl("http://c.gdt.qq.com/gdt_trace_a.fcg" + "?actionid=7&targettype=6&tagetid=" + this.val$info.targetid + "&clickid=" + this.val$clickid);
        localRequestVo.setSuccessCode(200);
        NewsFragment.this.getDataFromeServer(localRequestVo, new BaseFragment.DataCallback()
        {
          public void requestFalse()
          {
          }

          public void requestSuccess(Object paramObject)
          {
          }
        });
        Intent localIntent = new Intent("android.intent.action.VIEW");
        localIntent.setDataAndType(Uri.fromFile(paramFile), "application/vnd.android.package-archive");
        NewsFragment.this.startActivity(localIntent);
        NewsFragment.this.manager = ((NotificationManager)NewsFragment.this.getActivity().getSystemService("notification"));
        NewsFragment.this.manager.cancelAll();
      }

      public void downloadStart()
      {
        NewsFragment.this.displayNotification("开始下载", "开始下载", 0);
        RequestVo localRequestVo = new RequestVo();
        localRequestVo.setUrl("http://c.gdt.qq.com/gdt_trace_a.fcg" + "?actionid=5&targettype=6&tagetid=" + this.val$info.targetid + "&clickid=" + this.val$clickid);
        localRequestVo.setSuccessCode(200);
        NewsFragment.this.getDataFromeServer(localRequestVo, new BaseFragment.DataCallback()
        {
          public void requestFalse()
          {
          }

          public void requestSuccess(Object paramObject)
          {
          }
        });
      }

      public void downloading(int paramInt)
      {
        if (NewsFragment.this.notification != null)
        {
          RemoteViews localRemoteViews = NewsFragment.this.notification.contentView;
          localRemoteViews.setTextViewText(2131363287, "下载中");
          localRemoteViews.setProgressBar(2131363288, 100, paramInt, false);
          NewsFragment.this.manager = ((NotificationManager)NewsFragment.this.getActivity().getSystemService("notification"));
          NewsFragment.this.manager.notify(0, NewsFragment.this.notification);
        }
      }
    });
  }

  public void downloadGDT(NewsInfo paramNewsInfo)
  {
    if (paramNewsInfo.btn_render == 0)
      return;
    if (paramNewsInfo.btn_render == 1)
    {
      Intent localIntent = new Intent("android.intent.action.VIEW");
      localIntent.setData(Uri.parse(paramNewsInfo.rl));
      startActivity(localIntent);
      return;
    }
    if (DeviceUtil.getConnType(getActivity()) != 1)
    {
      DialogUtil.showMsgDlg(getActivity(), "下载文件", "当前网络下载会消耗您的流量，是否继续下载", "取消", "确定", new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          paramDialogInterface.dismiss();
        }
      }
      , new DialogInterface.OnClickListener(paramNewsInfo)
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          NewsFragment.this.processApk(this.val$info);
          paramDialogInterface.dismiss();
        }
      });
      return;
    }
    processApk(paramNewsInfo);
  }

  public void initMenu(View paramView)
  {
    initAnimation();
    this.mUgcView = ((KXUGCView)paramView.findViewById(2131362904));
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
          IntentUtil.showPoiListFragment(NewsFragment.this, null);
          return;
        case 1:
          NewsFragment.this.writeNewRecord(null);
          return;
        case 2:
          NewsFragment.this.showUploadPhotoDialog(NewsFragment.this.getString(2131427636));
          return;
        case 3:
        }
        IntentUtil.showVoiceRecordFragment(NewsFragment.this, null);
      }

      public void onUGCItemShow()
      {
        if (NewsFragment.this.isNeedReturn())
          return;
        View localView = NewsFragment.this.findViewById(2131362512);
        localView.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramView)
          {
            paramView.setVisibility(8);
            KXEnvironment.mGuideRecord = false;
            KXEnvironment.saveBooleanParams(NewsFragment.this.getActivity(), "guide_record", true, false);
            NewsFragment.this.writeNewRecord(null);
            NewsFragment.this.mHandler.postDelayed(new Runnable()
            {
              public void run()
              {
                if (NewsFragment.this.isNeedReturn())
                  return;
                NewsFragment.this.mUgcView.showOpenBtn();
              }
            }
            , 500L);
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

  public void initRemoveListData(RecordInfo paramRecordInfo)
  {
    String str;
    String[] arrayOfString;
    if ((paramRecordInfo != null) && (paramRecordInfo.isRepost()))
    {
      str = paramRecordInfo.getRecordFeedTitle();
      if (str.indexOf("[|s|]‖[|m|]10066329[|m|]-101[|e|]") == -1)
        break label133;
      arrayOfString = str.split("\\[\\|s\\|\\]‖\\[\\|m\\|\\]10066329\\[\\|m\\|\\]-101\\[\\|e\\|\\]|‖");
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("[|s|]").append(paramRecordInfo.getRecordFeedFname()).append("[|m|]").append(paramRecordInfo.getRecordFeedFuid()).append("[|m|]");
      if ((arrayOfString[0] != null) && (!arrayOfString[0].startsWith(localStringBuffer.toString())))
      {
        localStringBuffer.append("0[|e|]：").append(arrayOfString[0]);
        arrayOfString[0] = localStringBuffer.toString();
      }
    }
    for (int i = 0; ; i++)
    {
      if (i >= arrayOfString.length)
      {
        return;
        label133: arrayOfString = str.split("‖");
        break;
      }
      this.removelist.add(arrayOfString[i]);
    }
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if (paramInt2 == -1)
    {
      if (paramInt1 != 0)
        break label52;
      String str3 = paramIntent.getStringExtra("content");
      this.newsModel.setStatus(str3);
    }
    while (true)
    {
      if (this.mInputView != null)
        this.mInputView.onActivityResultOK(paramInt1, paramInt2, paramIntent);
      label52: label185: label336: int i;
      NewsInfo localNewsInfo1;
      do
      {
        int j;
        int k;
        int m;
        NewsInfo localNewsInfo2;
        do
        {
          NewsInfo localNewsInfo3;
          do
          {
            while (true)
            {
              return;
              if (paramInt1 == 10)
              {
                NewsInfo localNewsInfo4 = this.mAdapter.getCurrentNewsInfo();
                if (localNewsInfo4 == null)
                  continue;
                String str1 = paramIntent.getStringExtra("cnum");
                String str2 = paramIntent.getStringExtra("upnum");
                if (str1 != null)
                  localNewsInfo4.mCnum = str1;
                if (str2 != null)
                  localNewsInfo4.mUpnum = str2;
                this.mAdapter.notifyDataSetChanged();
                break;
              }
            }
            if (paramInt1 != 2)
              break label185;
            localNewsInfo3 = this.mAdapter.getCurrentNewsInfo();
          }
          while (localNewsInfo3 == null);
          localNewsInfo3.mCnum = String.valueOf(1 + Integer.parseInt(localNewsInfo3.mCnum));
          this.mAdapter.notifyDataSetChanged();
          Toast.makeText(getActivity(), 2131427450, 0).show();
          break;
          if (paramInt1 != 1300)
            break label336;
          j = paramIntent.getIntExtra("comment", 0);
          k = paramIntent.getIntExtra("forward", 0);
          m = paramIntent.getIntExtra("zan", 0);
          localNewsInfo2 = this.mAdapter.getCurrentNewsInfo();
        }
        while (localNewsInfo2 == null);
        if (!TextUtils.isEmpty(localNewsInfo2.mCnum))
          localNewsInfo2.mCnum = String.valueOf(j + Integer.parseInt(localNewsInfo2.mCnum));
        if (!TextUtils.isEmpty(localNewsInfo2.mTnum))
          localNewsInfo2.mTnum = String.valueOf(k + Integer.parseInt(localNewsInfo2.mTnum));
        if (!TextUtils.isEmpty(localNewsInfo2.mUpnum))
          localNewsInfo2.mUpnum = String.valueOf(m + Integer.parseInt(localNewsInfo2.mUpnum));
        this.mAdapter.notifyDataSetChanged();
        break;
        if (paramInt1 != 1230)
          break;
        i = paramIntent.getIntExtra("comment", 0);
        localNewsInfo1 = this.mAdapter.getCurrentNewsInfo();
      }
      while (localNewsInfo1 == null);
      if (!TextUtils.isEmpty(localNewsInfo1.mCnum))
        localNewsInfo1.mCnum = String.valueOf(i + Integer.parseInt(localNewsInfo1.mCnum));
      this.mAdapter.notifyDataSetChanged();
    }
  }

  public void onClick(View paramView)
  {
    if (paramView.equals(this.ivState))
      updateState();
    do
    {
      return;
      if (paramView.equals(this.btnLeft))
      {
        if (super.isMenuListVisibleInMain())
        {
          super.showSubActivityInMain();
          return;
        }
        super.showMenuListInMain();
        return;
      }
      if (paramView.equals(this.btnRight))
      {
        isShowTipBar = true;
        refresh(this.mCurrentSelectType);
        return;
      }
      if (!paramView.equals(this.logoLayout))
        continue;
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
              Intent localIntent2 = new Intent(NewsFragment.this.getActivity(), LoginActivity.class);
              NewsFragment.this.startActivity(localIntent2);
              NewsFragment.this.getActivity().finish();
              return;
            case 1:
            }
            Intent localIntent1 = new Intent(NewsFragment.this.getActivity(), LoginActivity.class);
            Bundle localBundle = new Bundle();
            localBundle.putInt("regist", 1);
            localIntent1.putExtras(localBundle);
            NewsFragment.this.startActivity(localIntent1);
            NewsFragment.this.getActivity().finish();
          }
        }
        , 1);
        return;
      }
      IntentUtil.showMyHomeFragment(this);
      return;
    }
    while (!paramView.equals(this.mLytCenter));
    showPopUpWindow(paramView);
  }

  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
    if ((paramConfiguration.orientation != 2) && (paramConfiguration.orientation != 1));
    do
      return;
    while ((this.mPopupWindow == null) || (!this.mPopupWindow.isShowing()));
    this.mPopupWindow.dismiss();
    this.mPopupWindow = null;
    this.mLytCenter.performClick();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Bundle localBundle = getArguments();
    String str = null;
    if (localBundle != null)
      str = getArguments().getString("action");
    if ((str != null) && (!str.equals("com.kaixin001.VIEW_NEWS")) && (CrashRecoverUtil.isCrashBefore()))
    {
      CrashRecoverUtil.crashRecover(getActivity().getApplicationContext());
      IntentUtil.returnToDesktop(getActivity());
    }
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903184, paramViewGroup, false);
  }

  public void onDestroy()
  {
    if (this.mAlertDialog != null)
    {
      if (this.mAlertDialog.isShowing())
        this.mAlertDialog.dismiss();
      this.mAlertDialog = null;
    }
    cancelTask();
    this.newslist.clear();
    if (this.lvHome != null)
    {
      this.lvHome.setAdapter(null);
      this.lvHome = null;
    }
    if (this.mAdapter != null)
    {
      this.mAdapter.clear();
      this.mAdapter = null;
    }
    cancelTask(this.mInputTask);
    super.onDestroy();
  }

  public void onDismiss()
  {
    updateTitleCenterContent(this.mCurrentSelectType);
    View localView = findViewById(2131362907);
    if (KXEnvironment.mGuideUgc);
    for (int i = 0; ; i = 8)
    {
      localView.setVisibility(i);
      return;
    }
  }

  public void onInputViewCancel(String paramString)
  {
    showInputView(false);
  }

  public boolean onInputViewComplete(String paramString)
  {
    int i = 1;
    if ((TextUtils.isEmpty(paramString)) && (this.mInputType == 0))
    {
      DialogUtil.showKXAlertDialog(getActivity(), 2131427454, null);
      i = 0;
    }
    do
    {
      return i;
      if (this.mInputType != 0)
        continue;
      onInputComment(this.mInputForData, paramString);
      return i;
    }
    while (this.mInputType != i);
    onInputForward(this.mInputForData, paramString);
    return i;
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
      if ((this.mInputForData == null) || (this.mInputForData != paramObject) || (this.mInputType != paramInt))
        this.mInputView.setDefautText("");
      if (paramInt == 0)
        return false;
      actionForward(localNewsInfo);
      return true;
    }
    return false;
  }

  public boolean onItemLongClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    NewsInfo localNewsInfo;
    try
    {
      if (this.newslist == null)
        return false;
      if ((paramLong < 0L) || (paramLong >= this.newslist.size()))
        break label343;
      localNewsInfo = (NewsInfo)this.newslist.get((int)paramLong);
      if (TextUtils.isEmpty(localNewsInfo.mFuid))
        break label343;
      if ("1088".equals(localNewsInfo.mNtype))
      {
        this.mAdapter.onRepostLongClicked(localNewsInfo);
        break label345;
      }
      if ("3".equals(localNewsInfo.mNtype))
        this.mAdapter.onStateLongClicked(localNewsInfo);
    }
    catch (Exception localException)
    {
      KXLog.e("NewsFragment", "onListItemClick", localException);
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
      break label345;
      label343: return false;
    }
    label345: return true;
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 4)
      if ((this.mSettingOptionPopupWindow != null) && (this.mSettingOptionPopupWindow.isShowing()))
        this.mSettingOptionPopupWindow.dismiss();
    do
      do
        return false;
      while (!User.getInstance().GetLookAround());
    while (((paramInt != 4) || (User.getInstance().GetLookAround())) && ((paramInt != 4) || (!ensureInputHideMenu())) && (!super.onKeyDown(paramInt, paramKeyEvent)));
    return true;
  }

  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default:
      return super.onOptionsItemSelected(paramMenuItem);
    case 401:
      refresh(this.mCurrentSelectType);
      return true;
    case 402:
      this.lvHome.setSelection(0);
      return true;
    case 403:
      IntentUtil.returnToDesktop(getActivity());
      return true;
    case 404:
    }
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
            Intent localIntent2 = new Intent(NewsFragment.this.getActivity(), LoginActivity.class);
            NewsFragment.this.startActivity(localIntent2);
            NewsFragment.this.getActivity().finish();
            return;
          case 1:
          }
          Intent localIntent1 = new Intent(NewsFragment.this.getActivity(), LoginActivity.class);
          Bundle localBundle = new Bundle();
          localBundle.putInt("regist", 1);
          localIntent1.putExtras(localBundle);
          NewsFragment.this.startActivity(localIntent1);
          NewsFragment.this.getActivity().finish();
        }
      }
      , 1);
    }
    while (true)
    {
      return true;
      IntentUtil.showMyHomeFragment(this);
    }
  }

  public void onPause()
  {
    super.onPause();
    KXMediaManager.getInstance().requestStopCurrentMedia();
  }

  public void onResume()
  {
    super.onResume();
    this.mAdapter.setVideoPressed(false);
    View localView;
    int i;
    if (this.mLoginLayout != null)
    {
      localView = this.mLoginLayout;
      boolean bool = User.getInstance().GetLookAround();
      i = 0;
      if (!bool)
        break label80;
    }
    while (true)
    {
      localView.setVisibility(i);
      if ((this.mCurrentSelectType == 11) && (this.mAdapter != null))
        this.mAdapter.notifyDataSetChanged();
      if (this.mUgcView != null)
        this.mUgcView.showOpenBtn();
      return;
      label80: i = 8;
    }
  }

  public void onScroll(AbsListView paramAbsListView, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = -1 + (paramInt1 + paramInt2);
    int j = -1 + this.mAdapter.getCount();
    if ((i >= j - 4) && (j > 4) && (!this.mNeedAutoLoading))
    {
      ArrayList localArrayList = this.newslist;
      boolean bool1 = false;
      if (localArrayList != null)
      {
        int k = this.newslist.size();
        bool1 = false;
        if (k > 0)
        {
          boolean bool2 = TextUtils.isEmpty(((NewsInfo)this.newslist.get(-1 + this.newslist.size())).mFuid);
          bool1 = false;
          if (bool2)
            bool1 = true;
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
    if ((!User.getInstance().GetLookAround()) && (paramMotionEvent.getPointerCount() == 1))
    {
      if (paramMotionEvent.getAction() != 0)
        break label31;
      paramMotionEvent.getY();
    }
    while (true)
    {
      return false;
      label31: if (((paramMotionEvent.getAction() != 2) && (paramMotionEvent.getAction() != 1)) || (paramMotionEvent.getAction() != 2))
        continue;
      paramMotionEvent.getY();
    }
  }

  public void onUpdate()
  {
    showLoading(true, -1);
    isShowTipBar = true;
    refresh(this.mCurrentSelectType);
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    User.getInstance().loadDataIfEmpty(getActivity().getApplicationContext());
    initTitle();
    initMenu(paramView);
    this.proBar = ((ProgressBar)paramView.findViewById(2131361966));
    this.waitLayout = ((LinearLayout)paramView.findViewById(2131361965));
    this.mDownView = ((PullToRefreshForNewsYearView)paramView.findViewById(2131361814));
    this.mDownView.setPullToRefreshListener(this);
    this.mInputView = ((KXInputView)paramView.findViewById(2131362905));
    this.mInputView.setInputListener(this);
    showInputView(false);
    initListView(paramView);
    initTipBar(paramView);
    initBottomBar();
    countNoFriendNews = 0;
    countNoPlazaNews = 0;
    boolean bool = false;
    if (!dataInited());
    try
    {
      bool = NewsEngine.getInstance().loadNewsCache(getActivity(), User.getInstance().getUID());
      if (bool)
      {
        getNewsData(false, false);
        showUpdateTime();
        this.proBar.setVisibility(8);
      }
      this.mHasNew = NewsModel.getHasNew();
      if ((this.mHasNew) || (!bool) || (mRefresh))
      {
        mRefresh = false;
        showLoading(true, -1);
        refresh(this.mCurrentSelectType);
        if (this.lvHome != null)
          this.lvHome.requestFocus();
        this.mLoginLayout = paramView.findViewById(2131362129);
        View localView1 = this.mLoginLayout;
        if (!User.getInstance().GetLookAround())
          break label539;
        i = 0;
        localView1.setVisibility(i);
        ((Button)paramView.findViewById(2131362130)).setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramView)
          {
            NewsFragment.this.showHideLoginLayoutAnimation(0);
          }
        });
        ((Button)paramView.findViewById(2131362131)).setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramView)
          {
            NewsFragment.this.showHideLoginLayoutAnimation(1);
          }
        });
        if (User.getInstance().GetLookAround())
          break label546;
        this.mUgcView.setVisibility(0);
        KXUBLog.log("homepage_dynamic");
        showUpdateDialog();
        KXEnvironment.mGuideUgc = KXEnvironment.loadBooleanParams(getActivity(), "guide_ugc", true, true);
        KXEnvironment.mGuideRecord = KXEnvironment.loadBooleanParams(getActivity(), "guide_record", true, true);
        KXEnvironment.mGuidePlaza = KXEnvironment.loadBooleanParams(getActivity(), "guide_plaza", true, true);
        KXEnvironment.mGuidePlaza = false;
        if (User.getInstance().GetLookAround())
        {
          KXEnvironment.mGuideUgc = false;
          KXEnvironment.mGuideRecord = false;
          KXEnvironment.mGuidePlaza = false;
        }
        View localView2 = findViewById(2131362906);
        localView2.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramView)
          {
            paramView.setVisibility(8);
            KXEnvironment.mGuidePlaza = false;
            KXEnvironment.saveBooleanParams(NewsFragment.this.getActivity(), "guide_plaza", true, false);
            NewsFragment.this.showPopUpWindow(NewsFragment.this.mLytCenter);
          }
        });
        if (!KXEnvironment.mGuidePlaza)
          break label558;
        j = 0;
        localView2.setVisibility(j);
        View localView3 = findViewById(2131362907);
        localView3.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramView)
          {
            paramView.setVisibility(8);
            KXEnvironment.mGuideUgc = false;
            KXEnvironment.saveBooleanParams(NewsFragment.this.getActivity(), "guide_ugc", true, false);
            if (NewsFragment.this.mUgcView != null)
              NewsFragment.this.mUgcView.showUgc();
          }
        });
        if (!KXEnvironment.mGuidePlaza)
        {
          if (!KXEnvironment.mGuideUgc)
            break label565;
          k = 0;
          localView3.setVisibility(k);
        }
        if (getArguments() != null)
          break label572;
        return;
      }
    }
    catch (SecurityErrorException localSecurityErrorException)
    {
      label539: label546: label558: label565: label572: String str;
      do
      {
        while (true)
        {
          localSecurityErrorException.printStackTrace();
          continue;
          if ((!bool) || (this.newslist.size() != 0))
            continue;
          showLoading(true, -1);
          refresh(this.mCurrentSelectType);
          continue;
          int i = 8;
          continue;
          this.mUgcView.setVisibility(8);
          continue;
          int j = 8;
          continue;
          int k = 8;
        }
        str = getArguments().getString("actionlink");
      }
      while (TextUtils.isEmpty(str));
      getArguments().putString("actionlink", null);
      Intent localIntent = new Intent(getActivity(), WebPageFragment.class);
      localIntent.putExtra("url", str);
      startFragment(localIntent, true);
    }
  }

  protected void setGDTStas(NewsInfo paramNewsInfo)
  {
    String str = "http://v.gdt.qq.com/gdt_stats.fcg" + "?count=1&viewid0=" + paramNewsInfo.viewid;
    RequestVo localRequestVo = new RequestVo();
    localRequestVo.setUrl(str);
    localRequestVo.setSuccessCode(204);
    getDataFromeServer(localRequestVo, new BaseFragment.DataCallback()
    {
      public void requestFalse()
      {
      }

      public void requestSuccess(Object paramObject)
      {
      }
    });
  }

  public void showHideLoginLayoutAnimation(int paramInt)
  {
    TranslateAnimation localTranslateAnimation = new TranslateAnimation(0.0F, 0.0F, 0.0F, this.mLoginLayout.getHeight());
    localTranslateAnimation.setDuration(200L);
    localTranslateAnimation.setAnimationListener(new Animation.AnimationListener(paramInt)
    {
      public void onAnimationEnd(Animation paramAnimation)
      {
        NewsFragment.this.mLoginLayout.setVisibility(4);
        NewsFragment.this.showLoginPage(this.val$type);
      }

      public void onAnimationRepeat(Animation paramAnimation)
      {
      }

      public void onAnimationStart(Animation paramAnimation)
      {
      }
    });
    this.mLoginLayout.startAnimation(localTranslateAnimation);
  }

  class CheckNewUpdatesTask extends KXFragment.KXAsyncTask<Void, Void, Boolean>
  {
    CheckNewUpdatesTask()
    {
      super();
    }

    private void startDialogActivity()
    {
      ((NotificationManager)NewsFragment.this.getActivity().getSystemService("notification")).cancel(KaixinConst.ID_CAN_UPGRADE_NOTIIFICATION);
      Intent localIntent = new Intent(NewsFragment.this.getActivity(), UpgradeDialogActivity.class);
      Bundle localBundle = new Bundle();
      localBundle.putString("stringMessage", UpgradeDownloadFile.getInstance().mDescription);
      localBundle.putInt("dialogtype", 1);
      localIntent.putExtras(localBundle);
      localIntent.addFlags(536870912);
      NewsFragment.this.startActivity(localIntent);
    }

    protected Boolean doInBackgroundA(Void[] paramArrayOfVoid)
    {
      if ((NewsFragment.this.getActivity() == null) || (NewsFragment.this.getView() == null))
        return Boolean.valueOf(false);
      try
      {
        Boolean localBoolean = Boolean.valueOf(UpgradeEngine.getInstance().checkUpgradeInformation(NewsFragment.this.getActivity().getApplicationContext()));
        return localBoolean;
      }
      catch (Exception localException)
      {
      }
      return Boolean.valueOf(false);
    }

    protected void onCancelledA()
    {
    }

    protected void onPostExecuteA(Boolean paramBoolean)
    {
      if ((NewsFragment.this.getView() == null) || (NewsFragment.this.getActivity() == null));
      do
        return;
      while (!UpgradeModel.getInstance().getDownloadFile());
      String str = UpgradeDownloadFile.getInstance().getVersion();
      SharedPreferences localSharedPreferences;
      NetworkInfo.State localState;
      if ((Pattern.compile("^\\d\\.\\d$").matcher(str).find()) || (UpgradeDownloadFile.getInstance().isForcedShow()))
      {
        localSharedPreferences = NewsFragment.this.getActivity().getSharedPreferences("loginNum", 0);
        int i = 1 + localSharedPreferences.getInt("loginNumAfterNewVersion" + str, 0);
        localSharedPreferences.edit().putInt("loginNumAfterNewVersion" + str, i).commit();
        localState = ((ConnectivityManager)NewsFragment.this.getActivity().getSystemService("connectivity")).getNetworkInfo(1).getState();
        if (i != 1)
          break label176;
        startDialogActivity();
      }
      while (true)
      {
        UpgradeModel.enableUpgradeService(false);
        return;
        label176: if ((localState != NetworkInfo.State.CONNECTED) && (localState != NetworkInfo.State.CONNECTING))
          continue;
        int j = 1 + localSharedPreferences.getInt("loginNumAtWifi" + str, 0);
        localSharedPreferences.edit().putInt("loginNumAtWifi" + str, j).commit();
        if (j != 1)
          continue;
        startDialogActivity();
      }
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private class Event
    implements NewsFragment.IEvent
  {
    private Event()
    {
    }

    public void initEvent()
    {
      if (NewsFragment.this.getView() == null);
      RelativeLayout localRelativeLayout;
      do
      {
        return;
        localRelativeLayout = (RelativeLayout)NewsFragment.this.findViewById(2131362901);
      }
      while (localRelativeLayout == null);
      ArrayList localArrayList = EventModel.getInstance().getList();
      if ((localArrayList != null) && (localArrayList.size() > 0))
      {
        localRelativeLayout.setVisibility(0);
        EventModel.EventData localEventData = (EventModel.EventData)localArrayList.get(0);
        ((TextView)NewsFragment.this.getView().findViewById(2131362902)).setText(localEventData.getTitle());
        ((ImageView)NewsFragment.this.getView().findViewById(2131362903)).setOnClickListener(new View.OnClickListener(localRelativeLayout)
        {
          public void onClick(View paramView)
          {
            this.val$mLayoutEvent.setVisibility(8);
          }
        });
        localRelativeLayout.setOnClickListener(new View.OnClickListener(localEventData)
        {
          public void onClick(View paramView)
          {
            if (User.getInstance().GetLookAround())
            {
              FragmentActivity localFragmentActivity = NewsFragment.this.getActivity();
              String[] arrayOfString = new String[2];
              arrayOfString[0] = NewsFragment.this.getString(2131427338);
              arrayOfString[1] = NewsFragment.this.getString(2131427339);
              DialogUtil.showSelectListDlg(localFragmentActivity, 2131427729, arrayOfString, new DialogInterface.OnClickListener()
              {
                public void onClick(DialogInterface paramDialogInterface, int paramInt)
                {
                  switch (paramInt)
                  {
                  default:
                    return;
                  case 0:
                    Intent localIntent2 = new Intent(NewsFragment.this.getActivity(), LoginActivity.class);
                    NewsFragment.this.startActivity(localIntent2);
                    NewsFragment.this.getActivity().finish();
                    return;
                  case 1:
                  }
                  Intent localIntent1 = new Intent(NewsFragment.this.getActivity(), LoginActivity.class);
                  Bundle localBundle = new Bundle();
                  localBundle.putInt("regist", 1);
                  localIntent1.putExtras(localBundle);
                  NewsFragment.this.startActivity(localIntent1);
                  NewsFragment.this.getActivity().finish();
                }
              }
              , 1);
              return;
            }
            new EventMenuItem(1, this.val$data).onMenuItemClickListener.onClick(NewsFragment.this);
          }
        });
        return;
      }
      localRelativeLayout.setVisibility(8);
    }
  }

  private class GetDataTask extends KXFragment.KXAsyncTask<String, Void, Integer>
  {
    private int mStart = -1;
    private int newsCount = 0;

    private GetDataTask()
    {
      super();
    }

    protected Integer doInBackgroundA(String[] paramArrayOfString)
    {
      if (paramArrayOfString.length < 6)
        return Integer.valueOf(-1);
      try
      {
        this.mStart = Integer.parseInt(paramArrayOfString[0]);
        NewsEngine.getInstance().setNewsCount(NewsFragment.this.getActivity());
      }
      catch (Exception localException1)
      {
        try
        {
          if (this.mStart == 0)
            NewsAdvertEngine.getInstance().getAdvert(NewsFragment.this.getActivity().getApplicationContext());
          if (NewsEngine.getInstance().getNewsData(NewsFragment.this.getActivity().getApplicationContext(), NewsModel.getInstance(), paramArrayOfString[0], paramArrayOfString[1], paramArrayOfString[2], paramArrayOfString[3], paramArrayOfString[4], paramArrayOfString[5]))
          {
            Integer localInteger = Integer.valueOf(this.mStart);
            return localInteger;
            localException1 = localException1;
            localException1.printStackTrace();
            this.mStart = 0;
          }
        }
        catch (Exception localException2)
        {
          KXLog.e("NewsFragment", "GetDataTask", localException2);
        }
      }
      return Integer.valueOf(-1);
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      try
      {
        if (NewsFragment.this.getView() != null)
        {
          FragmentActivity localFragmentActivity = NewsFragment.this.getActivity();
          if (localFragmentActivity != null);
        }
        else
        {
          if (NewsFragment.this.mAdapter == null)
            KXLog.e("NewsFragment", "Adapter in finally == null");
          while (true)
          {
            if ((NewsFragment.this.mDownView != null) && (NewsFragment.this.mDownView.isFrefrshing()))
              NewsFragment.this.mDownView.onRefreshComplete();
            NewsFragment.this.showUpdateTime();
            return;
            NewsFragment.this.mAdapter.showLoadingFooter(false);
          }
        }
        NewsFragment.this.proBar.setVisibility(8);
        NewsFragment.this.refreshDoneHeader();
        if (NewsFragment.this.mCurrentSelectType != 11)
          NewsFragment.this.mShowAddFriend = false;
        if ((paramInteger.intValue() == -1) && (this.mStart == 0))
          Toast.makeText(NewsFragment.this.getActivity(), 2131427374, 0).show();
        while (NewsFragment.this.mAdapter == null)
        {
          KXLog.e("NewsFragment", "Adapter in finally == null");
          if ((NewsFragment.this.mDownView != null) && (NewsFragment.this.mDownView.isFrefrshing()))
            NewsFragment.this.mDownView.onRefreshComplete();
          NewsFragment.this.showUpdateTime();
          return;
          if ((User.getInstance().getUID() != null) && (!"".equals(User.getInstance().getUID())))
          {
            this.newsCount += NewsModel.getInstance().getNewsCount();
            if ((this.mStart == 0) && (NewsFragment.isShowTipBar))
            {
              int i = NewsFragment.this.newsModel.getPublicMore();
              NewsFragment.this.setTipBar(this.newsCount, i);
              NewsFragment.this.showTipBar();
              NewsFragment.isShowTipBar = false;
            }
            NewsFragment.this.clearNewsCount();
          }
          if ((this.mStart == 0) && (NewsFragment.this.mAdapter.mAdvView != null))
            NewsFragment.this.mAdapter.mAdvView.notifyDataSetChanged();
          NewsFragment.this.sendNewsUpdateDoneBroadcast();
          ArrayList localArrayList = NewsFragment.this.newsModel.getNewsList();
          if ((paramInteger.intValue() < 0) || (localArrayList == null))
            continue;
          if (NewsFragment.this.mAdapter == null)
            KXLog.e("NewsFragment", "Adapter == null");
          if ((this.mStart == 0) || (NewsFragment.this.newslist.size() == 0) || ((NewsFragment.this.mAdapter != null) && (NewsFragment.this.mAdapter.isFooterShowLoading())))
          {
            NewsFragment.this.getDataTask = null;
            NewsFragment.this.getNewsData(false, false);
          }
          if ((this.mStart != 0) || (NewsFragment.this.newslist.size() != 0) || (NewsFragment.this.mCurrentSelectType == 11))
            continue;
          NewsFragment.this.mShowAddFriend = true;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("NewsFragment", "onPostExecute", localException);
        if (NewsFragment.this.mAdapter == null)
          KXLog.e("NewsFragment", "Adapter in finally == null");
        while (true)
        {
          if ((NewsFragment.this.mDownView != null) && (NewsFragment.this.mDownView.isFrefrshing()))
            NewsFragment.this.mDownView.onRefreshComplete();
          NewsFragment.this.showUpdateTime();
          return;
          NewsFragment.this.mAdapter.showLoadingFooter(false);
        }
      }
      finally
      {
        while (true)
        {
          if (NewsFragment.this.mAdapter == null)
            KXLog.e("NewsFragment", "Adapter in finally == null");
          while (true)
          {
            if ((NewsFragment.this.mDownView != null) && (NewsFragment.this.mDownView.isFrefrshing()))
              NewsFragment.this.mDownView.onRefreshComplete();
            NewsFragment.this.showUpdateTime();
            throw localObject;
            NewsFragment.this.mAdapter.showLoadingFooter(false);
          }
          NewsFragment.this.mAdapter.showLoadingFooter(false);
        }
      }
    }

    protected void onPreExecuteA()
    {
      this.newsCount = NewsModel.getInstance().getNewsCount();
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  public static abstract interface IEvent
  {
    public abstract void initEvent();
  }

  private class InputTask extends KXFragment.KXAsyncTask<String, Void, Integer>
  {
    private InputTask()
    {
      super();
    }

    protected Integer doInBackgroundA(String[] paramArrayOfString)
    {
      Integer localInteger;
      try
      {
        if (NewsFragment.this.mInputType == 0)
          return Integer.valueOf(PostCommentEngine.getInstance().postComment(NewsFragment.this.getActivity().getApplicationContext(), paramArrayOfString[0], paramArrayOfString[1], paramArrayOfString[2], paramArrayOfString[3], paramArrayOfString[4]));
        if (NewsFragment.this.mInputType == 1)
        {
          RecordInfo localRecordInfo = NewsItemAdapter.getRecordInfoFromNewsInfo(NewsFragment.this.mInputForData);
          StringBuffer localStringBuffer = new StringBuffer("");
          Iterator localIterator = NewsFragment.this.removelist.iterator();
          while (true)
          {
            if (!localIterator.hasNext())
            {
              RecordUploadTask localRecordUploadTask = new RecordUploadTask(NewsFragment.this.getActivity());
              localRecordUploadTask.initRecordUploadTask(null, paramArrayOfString[0], null, null, null, null, localStringBuffer.toString(), localRecordInfo.getRecordFeedRid(), 0);
              UploadTaskListEngine.getInstance().addUploadTask(localRecordUploadTask);
              return Integer.valueOf(0);
            }
            String str = ForwardWeiboFragment.converToServerType((String)localIterator.next());
            localStringBuffer.append("‖");
            localStringBuffer.append(str);
          }
        }
      }
      catch (Exception localException)
      {
        while (true)
          KXLog.e("InputActivity", "doInBackground", localException);
        localInteger = Integer.valueOf(-100);
      }
      return localInteger;
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      try
      {
        if (NewsFragment.this.getView() == null)
          return;
        if (NewsFragment.this.getActivity() == null)
          return;
        if (NewsFragment.this.mInputType == 1)
        {
          Toast.makeText(NewsFragment.this.getActivity(), 2131428310, 0).show();
          return;
        }
      }
      catch (Resources.NotFoundException localNotFoundException)
      {
        KXLog.e("InputActivity", "onPostExecute", localNotFoundException);
        return;
      }
      int i = paramInteger.intValue();
      int j = 2131427441;
      if (NewsFragment.this.mInputType == 0)
      {
        NewsFragment.this.hideProgressDialog();
        j = 2131427471;
      }
      if (i == 1)
      {
        if (NewsFragment.this.mInputType == 0)
        {
          NewsFragment.this.mInputForData.mCnum = (1 + Integer.parseInt(NewsFragment.this.mInputForData.mCnum));
          if (NewsFragment.this.mAdapter != null)
            NewsFragment.this.mAdapter.notifyDataSetChanged();
          NewsFragment.this.mInputView.setDefautText("");
          NewsFragment.this.hideInputMethod();
          NewsFragment.this.showInputView(false);
        }
        Toast.makeText(NewsFragment.this.getActivity(), 2131427448, 0).show();
        return;
      }
      Toast.makeText(NewsFragment.this.getActivity(), j, 0).show();
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
 * Qualified Name:     com.kaixin001.fragment.NewsFragment
 * JD-Core Version:    0.6.0
 */