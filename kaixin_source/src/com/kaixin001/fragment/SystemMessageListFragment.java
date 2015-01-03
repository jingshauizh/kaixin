package com.kaixin001.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.NinePatch;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.businesslogic.ShowPhoto;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.engine.SystemMessageEngine;
import com.kaixin001.item.DetailItem;
import com.kaixin001.item.FriendInfo;
import com.kaixin001.item.KXLinkInfo;
import com.kaixin001.item.NewsInfo;
import com.kaixin001.item.RecordInfo;
import com.kaixin001.item.RepItem;
import com.kaixin001.item.RepostAlbumItem;
import com.kaixin001.item.SystemMessageItem;
import com.kaixin001.item.SystemMessageItem.SystemMsgExtendItem;
import com.kaixin001.item.SystemMessageSource;
import com.kaixin001.model.FriendsModel;
import com.kaixin001.model.FriendsModel.Friend;
import com.kaixin001.model.MessageCenterModel;
import com.kaixin001.model.NewsModel;
import com.kaixin001.model.User;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.CrashRecoverUtil;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.ParseNewsInfoUtil;
import com.kaixin001.util.UserHabitUtils;
import com.kaixin001.view.KXIntroView;
import com.kaixin001.view.KXIntroView.OnClickLinkListener;
import com.kaixin001.view.KXTopTab;
import com.kaixin001.view.KXTopTabHost;
import com.kaixin001.view.KXTopTabHost.OnTabChangeListener;
import com.kaixin001.view.PullToRefreshView;
import com.kaixin001.view.PullToRefreshView.PullToRefreshListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SystemMessageListFragment extends BaseFragment
  implements KXIntroView.OnClickLinkListener, AdapterView.OnItemClickListener, KXTopTabHost.OnTabChangeListener, PullToRefreshView.PullToRefreshListener
{
  private static final int ITEM_STATE_ACCEPTING_REQUEST = 1002;
  private static final int ITEM_STATE_IGNORING_REQUEST = 1003;
  private static final int ITEM_STATE_NORMAL = 1001;
  private static final int ITEM_STATE_PROCESS_FAIL = 1004;
  protected static final int MENU_HOME_ID = 412;
  protected static final int MENU_HOME_ME_ID = 413;
  protected static final int MENU_REFRESH_ID = 410;
  protected static final int MENU_TO_TOP_ID = 411;
  private static final String MSG_COMPARE_Praise = "评比: ";
  private static final String MSG_PHOTO_DIARY = "照片日记";
  private static final String MSG_REGULAR_END = "[|e|]";
  private static final String MSG_REGULAR_MIDDLE = "[|m|]";
  private static final String MSG_REGULAR_START = "[|s|]";
  private static final String MSG_REPOST_TYPE_ALBUM = "专辑";
  private static final String MSG_REPOST_TYPE_ALBUM_Praise = "专辑: ";
  private static final String MSG_REPOST_TYPE_COMPARE = "评比";
  private static final String MSG_REPOST_TYPE_DIARY = "日记";
  private static final String MSG_REPOST_TYPE_LOCATION = "位置";
  private static final String MSG_REPOST_TYPE_PICTURE = "照片";
  private static final String MSG_REPOST_TYPE_VOTE = "投票";
  private static final String MSG_TYPE_BIRTHDAY = "生日提醒";
  private static final String MSG_TYPE_DIARY = "日记: ";
  private static final String MSG_TYPE_FEEL_YOUR = "觉得你的";
  private static final String MSG_TYPE_FORWARD = "转发:";
  private static final String MSG_TYPE_FRIEND_REQUEST = "好友请求";
  private static final String MSG_TYPE_GIFT = "礼物";
  private static final String MSG_TYPE_GIFT_ANONYMITY = "匿名";
  private static final String MSG_TYPE_GIFT_SOMEBODY_ANONYMITY = "某人匿名";
  private static final String MSG_TYPE_LOCATION_SERVICE = "位置服务";
  private static final String MSG_TYPE_POST = "转贴: ";
  private static final String MSG_TYPE_PRAISE = "赞";
  private static final String MSG_TYPE_RECORD = "记录: ";
  private static final String MSG_TYPE_REPOST = "转帖";
  private static final String MSG_TYPE_STYLE_BOX = "九宫格日记: ";
  private static final String MSG_TYPE_STYLE_BOX_ALIAS = "九宫格";
  private static final String MSG_TYPE_TOUCH_THEM = "动他一下";
  private static final String MSG_TYPE_TRUTH = "真心话";
  private static final String MSG_TYPE_VOTE = "投票: ";
  public static final String SYSTEM_MESSAGE_LIST_POSITON = "position";
  public static final int SYSTEM_MESSAGE_MODE = 501;
  private static final String TAG = "SystemMessageListActivity";
  private static final String TYPE_MENTION_ME = "2";
  private static final String TYPE_NOTICE = "1";
  private static final String TYPE_REPOST = "3";
  private static String mGiftType = "0";
  ShowPhoto albumShowUtil;
  private SystemRecordMessageAdapter mAdapterMentionMe = new SystemRecordMessageAdapter();
  private SystemNoticeMessageAdapter mAdapterNotice = new SystemNoticeMessageAdapter();
  private SystemRecordMessageAdapter mAdapterRepost = new SystemRecordMessageAdapter();
  private ImageView mBtnRight;
  private BaseAdapter mCurAdapter;
  protected PullToRefreshView mDownView;
  private ProgressBar mFooterProBar;
  private TextView mFooterTV;
  private View mFooterView;
  private ArrayList<FriendsModel.Friend> mFriendList = null;
  FriendsModel mFriendsModel = FriendsModel.getInstance();
  private GetSystemMessageTask mGetSystemMessageTask = null;
  private ImageViewOnClickListener mImgOnClickListener = new ImageViewOnClickListener(null);
  private ArrayList<Integer> mItemStates = new ArrayList();
  private final ArrayList<SystemMessageItem> mListMessages = new ArrayList();
  private ListView mListView = null;
  MessageCenterModel mMessageCenterModel = MessageCenterModel.getInstance();
  private int[] mNewMsgCount = new int[3];
  private boolean[] mPageLoaded = new boolean[3];
  private ProgressBar mRightProBar;
  private KXTopTabHost mTabHost;
  private int mTabIndex = 0;
  private int[] mTabIndexArray = new int[3];
  private ArrayList<Integer> mUnreadItems = new ArrayList();
  private boolean mVideoPressed = false;

  private KXLinkInfo AbstractPoiFromKXLinkInfo(ArrayList<KXLinkInfo> paramArrayList)
  {
    Iterator localIterator = paramArrayList.iterator();
    KXLinkInfo localKXLinkInfo;
    do
    {
      if (!localIterator.hasNext())
        return null;
      localKXLinkInfo = (KXLinkInfo)localIterator.next();
    }
    while (!localKXLinkInfo.isLbsPoi());
    return localKXLinkInfo;
  }

  private ArrayList<NameIdItem> AbstractUserInfoFromKXLinkInfo(ArrayList<KXLinkInfo> paramArrayList, SystemMessageItem paramSystemMessageItem)
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(new NameIdItem(paramSystemMessageItem.getRealName(), paramSystemMessageItem.getFuid()));
    int i = 0;
    Iterator localIterator = paramArrayList.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return localArrayList;
      KXLinkInfo localKXLinkInfo = (KXLinkInfo)localIterator.next();
      if ((localKXLinkInfo.getContent().equals("@")) && (localKXLinkInfo.getType().equals("-101")))
      {
        i = 1;
        continue;
      }
      if ((i == 1) && (localKXLinkInfo.getType().equals("0")) && (!localKXLinkInfo.getId().equals(User.getInstance().getUID())))
        localArrayList.add(new NameIdItem(localKXLinkInfo.getContent(), localKXLinkInfo.getId()));
      i = 0;
    }
  }

  private void cancelDownloadTask()
  {
    if ((this.mGetSystemMessageTask != null) && (!this.mGetSystemMessageTask.isCancelled()) && (this.mGetSystemMessageTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.mGetSystemMessageTask.cancel(true);
      this.mGetSystemMessageTask = null;
      SystemMessageEngine.getInstance().cancel();
    }
  }

  private void doGetFriendListdata()
  {
    this.mFriendList = this.mFriendsModel.getFriends();
  }

  private void doGetListData()
  {
    updateMessageList();
    if ((this.mListMessages.size() == 0) || (this.mPageLoaded[this.mTabIndex] == 0))
    {
      showList(false);
      showEmptyNotice(false);
      showListLoadingProgressBar(true, 2);
      refreshSystemMessage();
      return;
    }
    this.mListView.setSelection(this.mTabIndexArray[this.mTabIndex]);
    judgeList(this.mListMessages);
  }

  private String getEmptyTextView(String paramString1, String paramString2)
  {
    if (paramString1.compareTo("0") == 0);
    for (String str = getResources().getString(2131427384); ; str = getResources().getString(2131427385))
      return paramString2.replace("*", str);
  }

  private void getMoreMessage(String paramString, boolean paramBoolean)
  {
    if ((paramString == null) || (this.mMessageCenterModel == null))
      break label11;
    label11: 
    do
      return;
    while ((this.mGetSystemMessageTask != null) && (!this.mGetSystemMessageTask.isCancelled()) && (this.mGetSystemMessageTask.getStatus() != AsyncTask.Status.FINISHED));
    if (!super.checkNetworkAndHint(paramBoolean))
    {
      showLoadingFooter(false);
      return;
    }
    showLoadingFooter(true);
    String[] arrayOfString = { "", paramString };
    ArrayList localArrayList;
    if ("1".equals(paramString))
      localArrayList = this.mMessageCenterModel.getSystemMessageList();
    while (true)
    {
      if ((localArrayList != null) && (localArrayList.size() > 0))
        arrayOfString[0] = String.valueOf(((SystemMessageItem)localArrayList.get(-1 + localArrayList.size())).getCtime());
      showItemProgress();
      this.mGetSystemMessageTask = new GetSystemMessageTask(null);
      this.mGetSystemMessageTask.execute(arrayOfString);
      return;
      if ("2".equals(paramString))
      {
        localArrayList = this.mMessageCenterModel.getSystemMentionMeList();
        continue;
      }
      if (!"3".equals(paramString))
        break;
      localArrayList = this.mMessageCenterModel.getSystemRepostList();
    }
  }

  private void getMoreMessage(boolean paramBoolean)
  {
    String str;
    switch (this.mTabIndex)
    {
    default:
      str = "1";
    case 0:
    case 1:
    case 2:
    }
    while (true)
    {
      getMoreMessage(str, paramBoolean);
      return;
      str = "1";
      continue;
      str = "2";
      continue;
      str = "3";
    }
  }

  private ShowPhoto getPhotoShowUtil()
  {
    if (this.albumShowUtil == null)
      this.albumShowUtil = new ShowPhoto(getActivity(), this, false);
    return this.albumShowUtil;
  }

  private void getSystemMessageNum()
  {
    this.mNewMsgCount[0] = this.mMessageCenterModel.getNoticeCnt(7);
    this.mNewMsgCount[1] = this.mMessageCenterModel.getNoticeCnt(8);
    this.mNewMsgCount[2] = this.mMessageCenterModel.getNoticeCnt(9);
  }

  private void initTabHost(View paramView, boolean paramBoolean)
  {
    if (paramView == null)
      return;
    if (paramBoolean)
      showWhichTabIndex();
    Bitmap localBitmap = BitmapFactory.decodeResource(getResources(), 2130838237);
    NinePatch localNinePatch = new NinePatch(localBitmap, localBitmap.getNinePatchChunk(), null);
    this.mTabHost = ((KXTopTabHost)paramView.findViewById(2131363061));
    KXTopTab localKXTopTab1 = new KXTopTab(getActivity());
    localKXTopTab1.setText(2131427889);
    if (this.mNewMsgCount[0] > 0)
    {
      localKXTopTab1.setRightText(String.valueOf(this.mNewMsgCount[0]));
      localKXTopTab1.setRightIcon(localNinePatch);
    }
    this.mTabHost.addTab(localKXTopTab1);
    KXTopTab localKXTopTab2 = new KXTopTab(getActivity());
    localKXTopTab2.setText(2131427890);
    if (this.mNewMsgCount[1] > 0)
    {
      localKXTopTab2.setRightText(String.valueOf(this.mNewMsgCount[1]));
      localKXTopTab2.setRightIcon(localNinePatch);
    }
    this.mTabHost.addTab(localKXTopTab2);
    KXTopTab localKXTopTab3 = new KXTopTab(getActivity());
    localKXTopTab3.setText(2131427891);
    if (this.mNewMsgCount[2] > 0)
    {
      localKXTopTab3.setRightText(String.valueOf(this.mNewMsgCount[2]));
      localKXTopTab3.setRightIcon(localNinePatch);
    }
    this.mTabHost.addTab(localKXTopTab3);
    this.mTabHost.setCurrentTab(this.mTabIndex);
    this.mTabHost.setOnTabChangeListener(this);
    this.mTabHost.setVisibility(0);
  }

  private void judgeList(ArrayList<SystemMessageItem> paramArrayList)
  {
    if ((paramArrayList == null) || (paramArrayList.size() == 0))
    {
      showEmptyNotice(true);
      showList(false);
      return;
    }
    showEmptyNotice(false);
    showList(true);
  }

  private void loadSystemMessageCache()
  {
    String str = User.getInstance().getUID();
    if (TextUtils.isEmpty(str));
    ArrayList localArrayList1;
    ArrayList localArrayList2;
    ArrayList localArrayList3;
    do
    {
      return;
      if (this.mMessageCenterModel == null)
        this.mMessageCenterModel = MessageCenterModel.getInstance();
      localArrayList1 = this.mMessageCenterModel.getSystemMessageList();
      localArrayList2 = this.mMessageCenterModel.getSystemMentionMeList();
      localArrayList3 = this.mMessageCenterModel.getSystemRepostList();
    }
    while ((localArrayList1 != null) && (localArrayList1.size() != 0) && (localArrayList2 != null) && (localArrayList2.size() != 0) && (localArrayList3 != null) && (localArrayList3.size() != 0));
    try
    {
      SystemMessageEngine.getInstance().loadSystemMessageCache(getActivity(), str);
      return;
    }
    catch (SecurityErrorException localSecurityErrorException)
    {
      localSecurityErrorException.printStackTrace();
    }
  }

  private void modifyUnreadNum(int paramInt)
  {
    Integer localInteger = new Integer(paramInt);
    if ((this.mCurAdapter != null) && (this.mUnreadItems.contains(localInteger)))
      this.mHandler.postDelayed(new Runnable(localInteger)
      {
        public void run()
        {
          if (SystemMessageListFragment.this.isNeedReturn())
            return;
          SystemMessageListFragment.this.mUnreadItems.remove(this.val$id);
          if (SystemMessageListFragment.this.mNewMsgCount[SystemMessageListFragment.this.mTabIndex] > 0)
          {
            int[] arrayOfInt = SystemMessageListFragment.this.mNewMsgCount;
            int i = SystemMessageListFragment.this.mTabIndex;
            arrayOfInt[i] = (-1 + arrayOfInt[i]);
            SystemMessageListFragment.this.mTabHost.clean();
            SystemMessageListFragment.this.initTabHost(SystemMessageListFragment.this.getView(), false);
          }
          SystemMessageListFragment.this.mCurAdapter.notifyDataSetChanged();
        }
      }
      , 200L);
  }

  private void recordUnreadItems(int paramInt)
  {
    KXLog.d("TESTAPP", "recordUnreadItems index:" + paramInt + ", num:" + this.mNewMsgCount[paramInt]);
    this.mUnreadItems.clear();
    if (this.mNewMsgCount[paramInt] > 0);
    for (int i = 0; ; i++)
    {
      if (i >= this.mNewMsgCount[paramInt])
        return;
      this.mUnreadItems.add(Integer.valueOf(i));
    }
  }

  private void refreshSystemMessage()
  {
    if (!super.checkNetworkAndHint(true))
      return;
    cancelDownloadTask();
    String str;
    switch (this.mTabIndex)
    {
    default:
      str = "1";
    case 0:
    case 1:
    case 2:
    }
    while (true)
    {
      String[] arrayOfString = { "", str };
      this.mGetSystemMessageTask = new GetSystemMessageTask(null);
      this.mGetSystemMessageTask.execute(arrayOfString);
      return;
      str = "1";
      continue;
      str = "2";
      continue;
      str = "3";
    }
  }

  private void setListAdapter()
  {
    switch (this.mTabIndex)
    {
    default:
      this.mListView.setAdapter(this.mAdapterNotice);
      this.mCurAdapter = this.mAdapterNotice;
      return;
    case 0:
      this.mListView.setAdapter(this.mAdapterNotice);
      this.mCurAdapter = this.mAdapterNotice;
      return;
    case 1:
      this.mListView.setAdapter(this.mAdapterMentionMe);
      this.mCurAdapter = this.mAdapterMentionMe;
      return;
    case 2:
    }
    this.mListView.setAdapter(this.mAdapterRepost);
    this.mCurAdapter = this.mAdapterRepost;
  }

  private void showAlbum(SystemMessageItem paramSystemMessageItem)
  {
    SystemMessageSource localSystemMessageSource = paramSystemMessageItem.getSourceItem();
    if ("4".equals(localSystemMessageSource.privacy))
    {
      Toast.makeText(getActivity(), 2131427925, 0).show();
      return;
    }
    if ((localSystemMessageSource.albumid == null) || (localSystemMessageSource.pics == null))
    {
      Toast.makeText(getActivity(), 2131427902, 0).show();
      return;
    }
    if ((!User.getInstance().getUID().equals(paramSystemMessageItem.getOUID())) && ("1".equals(localSystemMessageSource.privacy)) && ("0".equals(((RepostAlbumItem)localSystemMessageSource.pics.get(0)).visible)))
    {
      Toast.makeText(getActivity(), 2131427903, 0).show();
      return;
    }
    getPhotoShowUtil().showAlbum(localSystemMessageSource.albumid, localSystemMessageSource.albumtitle, localSystemMessageSource.ouid, paramSystemMessageItem.getOName(), localSystemMessageSource.picnum, localSystemMessageSource.privacy);
  }

  private void showDiaryDetailForPraise(SystemMessageItem paramSystemMessageItem)
  {
    Intent localIntent = new Intent(getActivity(), DiaryDetailFragment.class);
    Bundle localBundle = new Bundle();
    localBundle.putString("fuid", User.getInstance().getUID());
    localBundle.putString("fname", getResources().getString(2131427565));
    localBundle.putString("did", paramSystemMessageItem.getSourceItem().rid);
    localBundle.putString("title", "[|s|]" + paramSystemMessageItem.getOName() + "[|m|]" + User.getInstance().getUID() + "[|m|]" + paramSystemMessageItem.getFStar() + "[|e|]" + " 发表了一篇日记");
    localBundle.putString("intro", "[|s|]" + paramSystemMessageItem.getSourceItem().title + "[|m|]" + paramSystemMessageItem.getSourceItem().rid + "[|m|]" + paramSystemMessageItem.getAppId() + "[|e|]" + "\n" + paramSystemMessageItem.getSourceItem().intro);
    localBundle.putString("cnum", paramSystemMessageItem.getCnum());
    localIntent.putExtras(localBundle);
    startFragment(localIntent);
  }

  private void showEmptyNotice(boolean paramBoolean)
  {
    TextView localTextView = (TextView)getView().findViewById(2131363036);
    if (paramBoolean)
    {
      switch (this.mTabIndex)
      {
      default:
      case 0:
      case 1:
      case 2:
      }
      while (true)
      {
        localTextView.setVisibility(0);
        return;
        localTextView.setText(2131427580);
        continue;
        localTextView.setText(2131427908);
        continue;
        localTextView.setText(2131427909);
      }
    }
    localTextView.setVisibility(8);
  }

  private void showItemProgress()
  {
    if (this.mRightProBar.getVisibility() == 0)
      return;
    showListLoadingProgressBar(true, 1);
    this.mRightProBar.setVisibility(0);
    this.mBtnRight.setImageResource(0);
  }

  private void showList(boolean paramBoolean)
  {
    View localView = getView().findViewById(2131363062);
    if (paramBoolean)
    {
      localView.setVisibility(0);
      return;
    }
    localView.setVisibility(8);
  }

  private void showListLoadingProgressBar(boolean paramBoolean, int paramInt)
  {
    View localView = getView().findViewById(2131362490);
    if (paramBoolean)
      if (paramInt == 0)
      {
        showEmptyNotice(false);
        localView.setVisibility(0);
      }
    do
    {
      do
      {
        return;
        this.mRightProBar.setVisibility(0);
        this.mBtnRight.setImageResource(0);
      }
      while (paramInt != 2);
      showEmptyNotice(false);
      localView.setVisibility(0);
      return;
      if (paramInt == 0)
      {
        localView.setVisibility(8);
        return;
      }
      this.mRightProBar.setVisibility(8);
      this.mBtnRight.setImageResource(2130838834);
    }
    while (paramInt != 2);
    localView.setVisibility(8);
  }

  private void showLoadingFooter(boolean paramBoolean)
  {
    ProgressBar localProgressBar;
    if (this.mFooterProBar != null)
    {
      localProgressBar = this.mFooterProBar;
      if (!paramBoolean)
        break label67;
    }
    label67: for (int j = 0; ; j = 4)
    {
      localProgressBar.setVisibility(j);
      if (this.mFooterTV != null)
      {
        int i = getResources().getColor(2130839419);
        if (paramBoolean)
          i = getResources().getColor(2130839395);
        this.mFooterTV.setTextColor(i);
      }
      return;
    }
  }

  private void showPhoto(SystemMessageItem paramSystemMessageItem)
  {
    SystemMessageSource localSystemMessageSource = paramSystemMessageItem.getSourceItem();
    if ("4".equals(localSystemMessageSource.privacy))
    {
      Toast.makeText(getActivity(), 2131427924, 0).show();
      return;
    }
    if ((!User.getInstance().getUID().equals(paramSystemMessageItem.getOUID())) && ("1".equals(localSystemMessageSource.privacy)) && ("0".equals(localSystemMessageSource.visible)))
    {
      Toast.makeText(getActivity(), 2131427903, 0).show();
      return;
    }
    getPhotoShowUtil().showPhoto(localSystemMessageSource.albumid, 2, Integer.parseInt(localSystemMessageSource.pos), paramSystemMessageItem.getOUID(), localSystemMessageSource.albumtitle, localSystemMessageSource.privacy);
  }

  private boolean showWhichTabIndex()
  {
    int i = 0;
    this.mTabIndex = 0;
    for (int j = 0; ; j++)
    {
      if (j >= this.mNewMsgCount.length)
        return false;
      if (this.mNewMsgCount[j] <= 0)
        continue;
      if (i != 0)
        return true;
      this.mTabIndex = j;
      i = 1;
    }
  }

  public void beforeTabChanged(int paramInt)
  {
    KXLog.d("TESTAPP", "beforeTabChanged index:" + paramInt);
    if (this.mListView != null)
      this.mTabIndexArray[paramInt] = this.mListView.getFirstVisiblePosition();
    if (this.mNewMsgCount[paramInt] > 0)
    {
      this.mNewMsgCount[paramInt] = 0;
      this.mTabHost.clean();
      initTabHost(getView(), false);
    }
    KXLog.d("TESTAPP", "beforeTabChanged end! index:" + paramInt + ", mTabIndex = " + this.mTabIndex);
  }

  public void finish()
  {
    super.finish();
    AnimationUtil.finishActivity(getActivity(), 2);
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    int j;
    if (paramInt1 == 1)
      if (paramInt2 == -1)
      {
        j = Integer.parseInt(paramIntent.getExtras().getString("position"));
        mGiftType = paramIntent.getExtras().getString("giftType");
      }
    Bundle localBundle;
    do
    {
      do
        while (true)
        {
          try
          {
            SystemMessageItem localSystemMessageItem2 = (SystemMessageItem)this.mListMessages.get(j);
            localSystemMessageItem2.setAction(0);
            localSystemMessageItem2.setResult(1);
            this.mItemStates.set(j, new Integer(1001));
            this.mAdapterNotice.notifyDataSetChanged();
            return;
          }
          catch (Exception localException2)
          {
            localException2.printStackTrace();
            continue;
          }
          this.mItemStates.set(0, new Integer(1004));
        }
      while ((paramInt1 != 501) || (paramInt2 != -1));
      localBundle = paramIntent.getExtras();
    }
    while (localBundle == null);
    String str1 = localBundle.getString("cnum");
    String str2 = localBundle.getString("tnum");
    int i = localBundle.getInt("position");
    switch (this.mTabIndex)
    {
    default:
      return;
    case 1:
    case 2:
    }
    try
    {
      SystemMessageItem localSystemMessageItem1 = (SystemMessageItem)this.mListMessages.get(i);
      localSystemMessageItem1.setCnum(str1);
      localSystemMessageItem1.setTnum(str2);
      return;
    }
    catch (Exception localException1)
    {
      KXLog.e("SystemMessageListActivity", "onActivityResult", localException1);
    }
  }

  public void onClick(KXLinkInfo paramKXLinkInfo)
  {
    if (this.mVideoPressed);
    while (true)
    {
      return;
      if ((paramKXLinkInfo.isStar()) || (paramKXLinkInfo.isUserName()))
      {
        IntentUtil.showHomeFragment(this, paramKXLinkInfo.getId(), paramKXLinkInfo.getContent());
        return;
      }
      if (paramKXLinkInfo.isUrlLink())
      {
        IntentUtil.showWebPage(getActivity(), this, paramKXLinkInfo.getId(), null);
        return;
      }
      if ("1210".equals(paramKXLinkInfo.getType()))
        continue;
      if (paramKXLinkInfo.isVideo())
      {
        this.mVideoPressed = true;
        IntentUtil.showVideoPage(getActivity().getApplicationContext(), paramKXLinkInfo.getId(), paramKXLinkInfo.getType(), paramKXLinkInfo.getContent());
        return;
      }
      try
      {
        if ((this.mListMessages == null) || (this.mListMessages.size() <= paramKXLinkInfo.getPosition()) || (paramKXLinkInfo.getPosition() < 0))
          continue;
        SystemMessageItem localSystemMessageItem = (SystemMessageItem)this.mListMessages.get(paramKXLinkInfo.getPosition());
        if (!"1088".equals(paramKXLinkInfo.getType()))
          continue;
        String str1 = localSystemMessageItem.getId();
        String str2 = localSystemMessageItem.getRealName();
        String str3 = localSystemMessageItem.getFuid();
        if ((TextUtils.isEmpty(str1)) || (TextUtils.isEmpty(str3)) || (TextUtils.isEmpty(str2)))
          continue;
        Intent localIntent = new Intent(getActivity(), RepostDetailFragment.class);
        ArrayList localArrayList = new ArrayList();
        RepItem localRepItem = new RepItem();
        localRepItem.fname = str2;
        localRepItem.fuid = str3;
        localRepItem.id = str1;
        localArrayList.add(localRepItem);
        Bundle localBundle = new Bundle();
        localBundle.putSerializable("repostList", (Serializable)localArrayList);
        localBundle.putInt("position", 0);
        localIntent.putExtras(localBundle);
        AnimationUtil.startFragment(this, localIntent, 1);
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (CrashRecoverUtil.isCrashBefore())
    {
      CrashRecoverUtil.crashRecover(getActivity());
      IntentUtil.returnToDesktop(getActivity());
    }
  }

  public void onCreateOptionsMenu(Menu paramMenu, MenuInflater paramMenuInflater)
  {
    super.onCreateOptionsMenu(paramMenu, paramMenuInflater);
    paramMenu.add(0, 410, 0, 2131427690).setIcon(2130838607);
    paramMenu.add(0, 411, 0, 2131427698).setIcon(2130838609);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903224, paramViewGroup, false);
  }

  public void onDestroy()
  {
    cancelDownloadTask();
    super.onDestroy();
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    if (this.mListMessages == null);
    String str3;
    String str4;
    String str5;
    do
    {
      SystemMessageItem localSystemMessageItem;
      String str2;
      do
      {
        String str1;
        while (true)
        {
          return;
          try
          {
            localSystemMessageItem = (SystemMessageItem)this.mListMessages.get(paramInt);
            if (localSystemMessageItem == null)
              continue;
            modifyUnreadNum(paramInt);
            str1 = localSystemMessageItem.getMsgType();
            str2 = localSystemMessageItem.getAppId();
            if ((this.mTabIndex != 1) && (this.mTabIndex != 2))
              break;
            RecordInfo localRecordInfo = RecordInfo.valueOf(localSystemMessageItem);
            if ((localRecordInfo == null) || ("6".equals(str2)) || ("1".equals(str2)) || ("1016".equals(str2)) || ("2".equals(str2)) || ("1194".equals(str2)) || ("4".equals(str2)) || ("1210".equals(str2)) || ("1210".equals(str1)) || ((("1192".equals(str2)) || ("1008".equals(str2))) && (("1242".equals(str1)) || ("1192".equals(str1)) || ("1008".equals(str1)) || ("1".equals(str1)) || ("1263".equals(str1)) || ("1263".equals(str2)) || ("1058".equals(str1)) || ("1058".equals(str2)))))
              continue;
            Intent localIntent1 = new Intent(getActivity(), NewsDetailFragment.class);
            DetailItem localDetailItem1 = DetailItem.makeWeiboDetailItem("1018", localRecordInfo.getRecordFeedFuid(), localRecordInfo.getRecordFeedRid());
            localDetailItem1.setTagRecordInfo(localRecordInfo);
            Bundle localBundle1 = new Bundle();
            localBundle1.putSerializable("data", localDetailItem1);
            localIntent1.putExtras(localBundle1);
            startActivityForResult(localIntent1, 1300);
            return;
          }
          catch (Exception localException)
          {
            KXLog.e("SystemMessageListActivity", "onPostExecute", localException);
            return;
          }
        }
        if (!"赞".equals(str1))
          continue;
        if ("2".equals(str2))
        {
          showDiaryDetailForPraise(localSystemMessageItem);
          return;
        }
        if (localSystemMessageItem.getAppId().equals("1018"))
        {
          Intent localIntent3 = new Intent(getActivity(), NewsDetailFragment.class);
          DetailItem localDetailItem2 = DetailItem.makeWeiboDetailItem("1018", User.getInstance().getUID(), localSystemMessageItem.getSourceItem().rid);
          Bundle localBundle2 = new Bundle();
          localBundle2.putSerializable("data", localDetailItem2);
          localIntent3.putExtras(localBundle2);
          startActivityForResult(localIntent3, 1300);
          return;
        }
        if ("1088".equals(str2))
        {
          Intent localIntent4 = new Intent(getActivity(), RepostDetailFragment.class);
          Bundle localBundle3 = new Bundle();
          ArrayList localArrayList = new ArrayList();
          RepItem localRepItem = new RepItem();
          localRepItem.fname = User.getInstance().getUID();
          localRepItem.id = localSystemMessageItem.getSourceItem().rid;
          localArrayList.add(localRepItem);
          localBundle3.putSerializable("repostList", (Serializable)localArrayList);
          localBundle3.putInt("position", 0);
          localIntent4.putExtras(localBundle3);
          AnimationUtil.startFragment(this, localIntent4, 1);
          return;
        }
        if ("1016".equals(str2))
        {
          Intent localIntent5 = new Intent(getActivity(), VoteDetailFragment.class);
          Bundle localBundle4 = new Bundle();
          localBundle4.putString("vid", localSystemMessageItem.getSourceItem().rid);
          localIntent5.putExtras(localBundle4);
          startFragment(localIntent5, true, 1);
          return;
        }
        if ("1210".equals(str2))
        {
          Intent localIntent6 = new Intent(getActivity(), StyleBoxDiaryDetailFragment.class);
          Bundle localBundle5 = new Bundle();
          localBundle5.putString("fuid", User.getInstance().getUID());
          localBundle5.putString("id", localSystemMessageItem.getSourceItem().rid);
          localBundle5.putString("title", localSystemMessageItem.getSourceItem().title);
          localBundle5.putString("cnum", localSystemMessageItem.getCnum());
          localBundle5.putString("visible", localSystemMessageItem.getSourceItem().visible);
          localBundle5.putString("delete", localSystemMessageItem.getSourceItem().delete);
          localIntent6.putExtras(localBundle5);
          startFragment(localIntent6, true, 1);
          return;
        }
        if ("1".equals(str2))
        {
          SystemMessageSource localSystemMessageSource = localSystemMessageItem.getSourceItem();
          if ((localSystemMessageItem == null) || (TextUtils.isEmpty(localSystemMessageSource.rid)) || ("0".equals(localSystemMessageSource.rid)))
          {
            Toast.makeText(getActivity(), 2131427924, 0).show();
            return;
          }
          IntentUtil.showViewPhotoActivityNotFromAlbum(getActivity(), this, Integer.parseInt(localSystemMessageItem.getSourceItem().pos), User.getInstance().getUID(), localSystemMessageItem.getSourceItem().title, localSystemMessageItem.getSourceItem().albumid, 2, null);
          return;
        }
        "1242".equals(str2);
        return;
      }
      while (!"1088".equals(str2));
      str3 = localSystemMessageItem.getId();
      str4 = localSystemMessageItem.getRealName();
      str5 = localSystemMessageItem.getFuid();
    }
    while ((TextUtils.isEmpty(str3)) || (TextUtils.isEmpty(str5)) || (TextUtils.isEmpty(str4)));
    Intent localIntent2 = new Intent(getActivity(), RepostDetailFragment.class);
    localIntent2.putExtra("fname", str4);
    localIntent2.putExtra("fuid", str5);
    localIntent2.putExtra("rpid", str3);
    AnimationUtil.startFragment(this, localIntent2, 1);
  }

  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default:
      return super.onOptionsItemSelected(paramMenuItem);
    case 410:
      showEmptyNotice(false);
      showListLoadingProgressBar(true, 1);
      refreshSystemMessage();
      return true;
    case 411:
      ListView localListView = (ListView)getView().findViewById(2131363062);
      if (localListView.getVisibility() == 0)
        localListView.setSelection(0);
      return true;
    case 412:
      IntentUtil.returnToDesktop(getActivity());
      return true;
    case 413:
    }
    IntentUtil.showMyHomeFragment(this);
    return true;
  }

  public void onResume()
  {
    super.onResume();
    this.mVideoPressed = false;
  }

  public void onTabChanged(int paramInt)
  {
    KXLog.d("TESTAPP", "onTabChanged index:" + paramInt);
    cancelDownloadTask();
    switch (this.mTabHost.getCurrentTab())
    {
    default:
      this.mTabIndex = 0;
      recordUnreadItems(this.mTabIndex);
      doGetListData();
      this.mListView.setAdapter(this.mAdapterNotice);
      this.mCurAdapter = this.mAdapterNotice;
    case 1:
    case 2:
    }
    while (true)
    {
      KXLog.d("TESTAPP", "onTabChanged end! index:" + paramInt + ", mTabIndex = " + this.mTabIndex);
      this.mListView.setSelection(this.mTabIndexArray[this.mTabIndex]);
      return;
      this.mTabIndex = 1;
      recordUnreadItems(this.mTabIndex);
      doGetListData();
      this.mListView.setAdapter(this.mAdapterMentionMe);
      this.mCurAdapter = this.mAdapterMentionMe;
      continue;
      this.mTabIndex = 2;
      recordUnreadItems(this.mTabIndex);
      doGetListData();
      this.mListView.setAdapter(this.mAdapterRepost);
      this.mCurAdapter = this.mAdapterRepost;
    }
  }

  public void onUpdate()
  {
    refreshSystemMessage();
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    setTitleBar(paramView);
    getSystemMessageNum();
    initTabHost(paramView, true);
    recordUnreadItems(this.mTabIndex);
    this.mTabIndexArray = new int[3];
    this.mFooterView = getActivity().getLayoutInflater().inflate(2130903259, null);
    this.mFooterTV = ((TextView)this.mFooterView.findViewById(2131362073));
    this.mFooterProBar = ((ProgressBar)this.mFooterView.findViewById(2131362072));
    this.mFooterView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        ArrayList localArrayList;
        int i;
        if (1 == SystemMessageListFragment.this.mTabIndex)
        {
          localArrayList = SystemMessageListFragment.this.mMessageCenterModel.getSystemMentionMeList();
          i = SystemMessageListFragment.this.mMessageCenterModel.getMentionMeTotal();
          if ((localArrayList != null) && (localArrayList.size() >= SystemMessageListFragment.this.mListMessages.size()))
            break label148;
          SystemMessageListFragment.this.showLoadingFooter(true);
        }
        for (boolean bool = true; ; bool = false)
        {
          if ((localArrayList == null) || (localArrayList.size() >= i))
            break label161;
          SystemMessageListFragment.this.getMoreMessage(bool);
          return;
          if (2 == SystemMessageListFragment.this.mTabIndex)
          {
            localArrayList = SystemMessageListFragment.this.mMessageCenterModel.getSystemRepostList();
            i = SystemMessageListFragment.this.mMessageCenterModel.getRepostTotal();
            break;
          }
          localArrayList = SystemMessageListFragment.this.mMessageCenterModel.getSystemMessageList();
          i = SystemMessageListFragment.this.mMessageCenterModel.getSystemMessageTotal();
          break;
          label148: SystemMessageListFragment.this.updateMessageList();
        }
        label161: SystemMessageListFragment.this.updateMessageList();
      }
    });
    ((TextView)this.mFooterView.findViewById(2131362073)).setText(2131427748);
    ((ProgressBar)this.mFooterView.findViewById(2131362072)).setVisibility(4);
    this.mDownView = ((PullToRefreshView)paramView.findViewById(2131362488));
    this.mDownView.setPullToRefreshListener(this);
    this.mListView = ((ListView)paramView.findViewById(2131363062));
    setListAdapter();
    this.mListView.setOnItemClickListener(this);
    loadSystemMessageCache();
    doGetListData();
    doGetFriendListdata();
  }

  public String searchGender(ArrayList<FriendsModel.Friend> paramArrayList, String paramString)
  {
    for (int i = 0; ; i++)
    {
      if (i >= paramArrayList.size())
      {
        Intent localIntent = new Intent("com.kaixin001.UPDATE_FRIENDS");
        getActivity().sendBroadcast(localIntent);
        return "0";
      }
      if (((FriendsModel.Friend)paramArrayList.get(i)).getFuid().equals(paramString))
        return ((FriendsModel.Friend)paramArrayList.get(i)).getGender();
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
        SystemMessageListFragment.this.finish();
      }
    });
    this.mBtnRight = ((ImageView)paramView.findViewById(2131362928));
    this.mBtnRight.setImageResource(2130838834);
    this.mBtnRight.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        SystemMessageListFragment.this.showEmptyNotice(false);
        SystemMessageListFragment.this.showListLoadingProgressBar(true, 1);
        SystemMessageListFragment.this.mTabIndexArray[SystemMessageListFragment.this.mTabIndex] = 0;
        SystemMessageListFragment.this.mTabHost.setCurrentTab(SystemMessageListFragment.this.mTabIndex);
        SystemMessageListFragment.this.refreshSystemMessage();
      }
    });
    this.mRightProBar = ((ProgressBar)paramView.findViewById(2131362929));
    ((ImageView)paramView.findViewById(2131362919)).setVisibility(8);
    TextView localTextView = (TextView)paramView.findViewById(2131362920);
    localTextView.setVisibility(0);
    localTextView.setText(getResources().getText(2131427538));
  }

  protected void updateMessageList()
  {
    this.mListMessages.clear();
    ArrayList localArrayList;
    int i;
    int k;
    switch (this.mTabIndex)
    {
    default:
      localArrayList = this.mMessageCenterModel.getSystemMessageList();
      i = this.mMessageCenterModel.getSystemMessageTotal();
      this.mTabIndex = 0;
      if (localArrayList != null)
        this.mListMessages.addAll(localArrayList);
      if ((localArrayList != null) && (localArrayList.size() < i))
      {
        SystemMessageItem localSystemMessageItem = new SystemMessageItem();
        localSystemMessageItem.setCtime(Long.valueOf(-1L));
        this.mListMessages.add(localSystemMessageItem);
      }
      if (this.mTabIndex == 0)
      {
        this.mItemStates.clear();
        int j = this.mListMessages.size();
        k = 0;
        label133: if (k < j);
      }
      else
      {
        switch (this.mTabIndex)
        {
        default:
          if (this.mAdapterNotice != null)
            this.mAdapterNotice.notifyDataSetChanged();
        case 1:
        case 2:
        }
      }
    case 1:
    case 2:
    }
    do
    {
      do
      {
        return;
        localArrayList = this.mMessageCenterModel.getSystemMentionMeList();
        i = this.mMessageCenterModel.getMentionMeTotal();
        break;
        localArrayList = this.mMessageCenterModel.getSystemRepostList();
        i = this.mMessageCenterModel.getRepostTotal();
        break;
        this.mItemStates.add(new Integer(1001));
        k++;
        break label133;
      }
      while (this.mAdapterMentionMe == null);
      this.mAdapterMentionMe.notifyDataSetChanged();
      return;
    }
    while (this.mAdapterRepost == null);
    this.mAdapterRepost.notifyDataSetChanged();
  }

  private class ButtonOnClickListener
    implements View.OnClickListener
  {
    public static final int ACCEPT_BUTTON_TYPE = 1;
    public static final int IGNORE_BUTTON_TYPE = 2;
    private int mItemPosition;
    private int mType;

    public ButtonOnClickListener(int paramInt1, int arg3)
    {
      this.mType = paramInt1;
      int i;
      this.mItemPosition = i;
    }

    public void onClick(View paramView)
    {
      SystemMessageListFragment.NoticeMessageItemCache localNoticeMessageItemCache = (SystemMessageListFragment.NoticeMessageItemCache)paramView.getTag();
      if (localNoticeMessageItemCache != null)
      {
        if (localNoticeMessageItemCache.mLayoutButtons != null)
          localNoticeMessageItemCache.mLayoutButtons.setVisibility(8);
        if (localNoticeMessageItemCache.mLayoutViewMore != null)
          localNoticeMessageItemCache.mLayoutViewMore.setVisibility(0);
      }
      String str1 = "";
      Object localObject = "";
      try
      {
        if (SystemMessageListFragment.this.mListMessages != null)
        {
          SystemMessageItem localSystemMessageItem = (SystemMessageItem)SystemMessageListFragment.this.mListMessages.get(this.mItemPosition);
          str1 = localSystemMessageItem.getFuid();
          String str2 = localSystemMessageItem.getSmid();
          localObject = str2;
        }
        SystemMessageListFragment.this.modifyUnreadNum(this.mItemPosition);
        arrayOfString = new String[4];
        arrayOfString[1] = String.valueOf(this.mItemPosition);
        arrayOfString[2] = str1;
        arrayOfString[3] = localObject;
        if (this.mType == 1)
        {
          SystemMessageListFragment.this.mItemStates.set(this.mItemPosition, new Integer(1002));
          arrayOfString[0] = String.valueOf(1);
          new SystemMessageListFragment.ProcessFriendRequestTask(SystemMessageListFragment.this, null).execute(arrayOfString);
          return;
        }
      }
      catch (Exception localException)
      {
        while (true)
        {
          String[] arrayOfString;
          localException.printStackTrace();
          continue;
          SystemMessageListFragment.this.mItemStates.set(this.mItemPosition, new Integer(1003));
          arrayOfString[0] = String.valueOf(2);
        }
      }
    }
  }

  private class GetSystemMessageTask extends KXFragment.KXAsyncTask<String, Void, Boolean>
  {
    private String mBefore = null;

    private GetSystemMessageTask()
    {
      super();
    }

    protected Boolean doInBackgroundA(String[] paramArrayOfString)
    {
      if (paramArrayOfString != null);
      try
      {
        if (paramArrayOfString.length != 2)
          return Boolean.valueOf(false);
        this.mBefore = paramArrayOfString[0];
        Boolean localBoolean = Boolean.valueOf(SystemMessageEngine.getInstance().doGetSystemMessageByType(SystemMessageListFragment.this.getActivity(), this.mBefore, paramArrayOfString[1]));
        return localBoolean;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
      }
      return null;
    }

    protected void onPostExecuteA(Boolean paramBoolean)
    {
      if (paramBoolean == null)
      {
        SystemMessageListFragment.this.finish();
        return;
      }
      if (paramBoolean.booleanValue())
        SystemMessageListFragment.this.mPageLoaded[SystemMessageListFragment.this.mTabIndex] = 1;
      while (true)
      {
        try
        {
          SystemMessageListFragment.this.showListLoadingProgressBar(false, 0);
          SystemMessageListFragment.this.showListLoadingProgressBar(false, 1);
          if (!paramBoolean.booleanValue())
            break label316;
          if (1 == SystemMessageListFragment.this.mTabIndex)
          {
            localArrayList = SystemMessageListFragment.this.mMessageCenterModel.getSystemMentionMeList();
            i = SystemMessageListFragment.this.mMessageCenterModel.getMentionMeTotal();
            if ((!TextUtils.isEmpty(this.mBefore)) && (SystemMessageListFragment.this.mListMessages.size() != 0) && (SystemMessageListFragment.this.mFooterProBar.getVisibility() != 0))
              continue;
            SystemMessageListFragment.this.updateMessageList();
            if (!TextUtils.isEmpty(this.mBefore))
              continue;
            SystemMessageListFragment.this.mListView.setSelection(0);
            if ((localArrayList == null) || (localArrayList.size() >= i))
              continue;
            SystemMessageListFragment.this.mGetSystemMessageTask = null;
            SystemMessageListFragment.this.getMoreMessage(false);
            SystemMessageListFragment.this.showLoadingFooter(false);
            SystemMessageListFragment.this.judgeList(SystemMessageListFragment.this.mListMessages);
            if ((SystemMessageListFragment.this.mDownView == null) || (!SystemMessageListFragment.this.mDownView.isFrefrshing()))
              break;
            SystemMessageListFragment.this.mDownView.onRefreshComplete();
            return;
          }
        }
        catch (Exception localException)
        {
          KXLog.e("SystemMessageListActivity", "onPostExecute", localException);
          return;
        }
        if (2 == SystemMessageListFragment.this.mTabIndex)
        {
          localArrayList = SystemMessageListFragment.this.mMessageCenterModel.getSystemRepostList();
          i = SystemMessageListFragment.this.mMessageCenterModel.getRepostTotal();
          continue;
        }
        ArrayList localArrayList = SystemMessageListFragment.this.mMessageCenterModel.getSystemMessageList();
        int i = SystemMessageListFragment.this.mMessageCenterModel.getSystemMessageTotal();
        continue;
        label316: if (!TextUtils.isEmpty(this.mBefore))
          continue;
        Toast.makeText(SystemMessageListFragment.this.getActivity(), 2131427547, 0).show();
      }
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private class ImageViewOnClickListener
    implements View.OnClickListener
  {
    private ImageViewOnClickListener()
    {
    }

    public void onClick(View paramView)
    {
      String[] arrayOfString = (String[])paramView.getTag();
      if ((arrayOfString == null) || (arrayOfString.length != 2))
        return;
      Intent localIntent = new Intent(SystemMessageListFragment.this.getActivity(), PreviewUploadPhotoFragment.class);
      localIntent.putExtra("small", arrayOfString[0]);
      localIntent.putExtra("large", arrayOfString[1]);
      localIntent.putExtra("action", "com.kaixin001.VIEW_SINGLE_PHOTO");
      SystemMessageListFragment.this.startFragment(localIntent, true, 1);
    }
  }

  public class MentionMeAndRepostMessageItemView
  {
    ImageView mImg1 = null;
    ImageView mImg2 = null;
    ImageView mImg3 = null;
    ImageView mImgArrow = null;
    ImageView mImgAvatar = null;
    ImageView mImgTypeIcon = null;
    ImageView mIvRecordPhoto = null;
    LinearLayout mLayoutDetail = null;
    LinearLayout mLayoutImages = null;
    TextView mTxtTime = null;
    TextView mTxtType = null;
    KXIntroView mViewContent = null;
    KXIntroView mViewNote = null;
    KXIntroView mViewOriginalContent = null;
    Button signBtn = null;
    View singLayout = null;
    Button viewBtn = null;

    public MentionMeAndRepostMessageItemView()
    {
    }

    public MentionMeAndRepostMessageItemView(View arg2)
    {
      Object localObject;
      this.mImgTypeIcon = ((ImageView)localObject.findViewById(2131363656));
      this.mTxtType = ((TextView)localObject.findViewById(2131363657));
      this.mTxtTime = ((TextView)localObject.findViewById(2131363658));
      this.mImgAvatar = ((ImageView)localObject.findViewById(2131363660));
      this.mViewContent = ((KXIntroView)localObject.findViewById(2131363661));
      this.mViewNote = ((KXIntroView)localObject.findViewById(2131363684));
      this.mLayoutDetail = ((LinearLayout)localObject.findViewById(2131363682));
      this.mViewOriginalContent = ((KXIntroView)localObject.findViewById(2131362909));
      this.mLayoutImages = ((LinearLayout)localObject.findViewById(2131362910));
      this.mImg1 = ((ImageView)localObject.findViewById(2131362911));
      this.mImg2 = ((ImageView)localObject.findViewById(2131362912));
      this.mImg3 = ((ImageView)localObject.findViewById(2131362913));
      this.mIvRecordPhoto = ((ImageView)localObject.findViewById(2131363681));
      this.mImgArrow = ((ImageView)localObject.findViewById(2131363654));
      this.singLayout = localObject.findViewById(2131363685);
      this.viewBtn = ((Button)localObject.findViewById(2131363686));
      this.signBtn = ((Button)localObject.findViewById(2131363687));
    }

    private void setIconAndTypeText(String paramString)
    {
      int i = 2130838695;
      String str = SystemMessageListFragment.this.getResources().getString(2131427892);
      if (!TextUtils.isEmpty(paramString))
      {
        if (!"1".equals(paramString))
          break label69;
        i = 2130838693;
        str = "照片";
      }
      while (true)
      {
        if (this.mImgTypeIcon != null)
          this.mImgTypeIcon.setBackgroundResource(i);
        if (this.mTxtType != null)
          this.mTxtType.setText(str);
        return;
        label69: if ("6".equals(paramString))
        {
          i = 2130838693;
          str = "专辑";
          continue;
        }
        if ("1016".equals(paramString))
        {
          i = 2130838702;
          str = "投票";
          continue;
        }
        if ("1210".equals(paramString))
        {
          i = 2130837759;
          str = "九宫格";
          continue;
        }
        if ("2".equals(paramString))
        {
          i = 2130838674;
          str = "日记";
          continue;
        }
        if ("1194".equals(paramString))
        {
          i = 2130838695;
          str = "评比";
          continue;
        }
        if ("1192".equals(paramString))
        {
          i = 2130837743;
          str = "位置";
          continue;
        }
        if (!"1263".equals(paramString))
          continue;
        i = 2130838693;
        str = "照片日记";
      }
    }

    private void showDiary(SystemMessageItem paramSystemMessageItem, KXIntroView.OnClickLinkListener paramOnClickLinkListener)
    {
      this.mLayoutDetail.setVisibility(0);
      this.mLayoutImages.setVisibility(8);
      if (this.mImgArrow != null)
        this.mImgArrow.setVisibility(8);
      try
      {
        ArrayList localArrayList = new ArrayList();
        KXLinkInfo localKXLinkInfo1 = new KXLinkInfo();
        localKXLinkInfo1.setContent(paramSystemMessageItem.getRealName());
        localKXLinkInfo1.setType("0");
        localKXLinkInfo1.setId(paramSystemMessageItem.getFuid());
        localArrayList.add(localKXLinkInfo1);
        KXLinkInfo localKXLinkInfo2 = new KXLinkInfo();
        localKXLinkInfo2.setContent(paramSystemMessageItem.getTitle());
        localArrayList.add(localKXLinkInfo2);
        this.mViewContent.setTitleList(localArrayList);
        this.mViewContent.setOnClickLinkListener(SystemMessageListFragment.this);
        String str1 = paramSystemMessageItem.getSubTitle();
        String str2 = paramSystemMessageItem.getSourceItem().fragment;
        String str3 = str1 + "\n" + str2;
        if (!TextUtils.isEmpty(str1))
        {
          this.mViewOriginalContent.setTitleList(paramSystemMessageItem.makeTitleList(str3));
          this.mViewOriginalContent.setOnClickLinkListener(paramOnClickLinkListener);
          this.mViewOriginalContent.setVisibility(0);
        }
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }

    private boolean showPicAlbumCompareAndVote(SystemMessageItem paramSystemMessageItem, KXIntroView.OnClickLinkListener paramOnClickLinkListener, View.OnClickListener paramOnClickListener)
    {
      this.mLayoutDetail.setVisibility(0);
      if (this.mImgArrow != null)
        this.mImgArrow.setVisibility(8);
      SystemMessageSource localSystemMessageSource = paramSystemMessageItem.getSourceItem();
      if (localSystemMessageSource != null)
      {
        ArrayList localArrayList1 = new ArrayList();
        KXLinkInfo localKXLinkInfo1 = new KXLinkInfo();
        localKXLinkInfo1.setContent(paramSystemMessageItem.getRealName());
        localKXLinkInfo1.setType("0");
        localKXLinkInfo1.setId(paramSystemMessageItem.getFuid());
        localArrayList1.add(localKXLinkInfo1);
        KXLinkInfo localKXLinkInfo2 = new KXLinkInfo();
        localKXLinkInfo2.setContent(paramSystemMessageItem.getTitle());
        localArrayList1.add(localKXLinkInfo2);
        this.mViewContent.setTitleList(localArrayList1);
        this.mViewContent.setOnClickLinkListener(SystemMessageListFragment.this);
        this.mViewOriginalContent.setVisibility(0);
        this.mViewOriginalContent.setTitleList(paramSystemMessageItem.makeTitleList(paramSystemMessageItem.getSubTitle()));
        this.mViewOriginalContent.setOnClickLinkListener(paramOnClickLinkListener);
        ArrayList localArrayList2;
        ImageView[] arrayOfImageView;
        int i;
        if (("6".equals(paramSystemMessageItem.getAppId())) || ("1194".equals(paramSystemMessageItem.getAppId())))
          if (!"4".equals(localSystemMessageSource.privacy))
          {
            localArrayList2 = localSystemMessageSource.pics;
            if ((localArrayList2 == null) || (localArrayList2.size() <= 0))
              break label390;
            this.mLayoutImages.setVisibility(0);
            arrayOfImageView = new ImageView[3];
            arrayOfImageView[0] = this.mImg1;
            arrayOfImageView[1] = this.mImg2;
            arrayOfImageView[2] = this.mImg3;
            i = 0;
            if (i < arrayOfImageView.length)
              break label272;
          }
        while (true)
        {
          return true;
          label272: if (i < localArrayList2.size())
          {
            String str1 = ((RepostAlbumItem)localArrayList2.get(i)).thumbnail;
            arrayOfImageView[i].setVisibility(0);
            if ("6".equals(paramSystemMessageItem.getAppId()))
            {
              arrayOfImageView[i].setTag(localArrayList2.get(i));
              arrayOfImageView[i].setOnClickListener(paramOnClickListener);
              label342: SystemMessageListFragment.this.displayPicture(arrayOfImageView[i], str1, 2130838780);
            }
          }
          while (true)
          {
            i++;
            break;
            arrayOfImageView[i].setOnClickListener(null);
            break label342;
            arrayOfImageView[i].setVisibility(8);
          }
          label390: this.mLayoutImages.setVisibility(8);
          continue;
          if ("1".equals(paramSystemMessageItem.getAppId()))
          {
            this.mLayoutImages.setVisibility(0);
            this.mImg1.setVisibility(0);
            this.mImg2.setVisibility(8);
            this.mImg3.setVisibility(8);
            String str2 = localSystemMessageSource.thumbnail;
            if (!TextUtils.isEmpty(str2))
            {
              this.mImg1.setTag(localSystemMessageSource);
              this.mImg1.setOnClickListener(paramOnClickListener);
              SystemMessageListFragment.this.displayPicture(this.mImg1, str2, 2130838780);
              continue;
            }
            this.mLayoutImages.setVisibility(8);
            continue;
          }
          if (!"1016".equals(paramSystemMessageItem.getAppId()))
            continue;
          this.mLayoutImages.setVisibility(8);
        }
      }
      this.mLayoutDetail.setVisibility(8);
      return !"1".equals(paramSystemMessageItem.getMsgType());
    }

    private void showStyleBox(SystemMessageItem paramSystemMessageItem)
    {
      this.mImgAvatar.setVisibility(0);
      SystemMessageListFragment.this.displayPicture(this.mImgAvatar, paramSystemMessageItem.getFlogo(), 2130838676);
      if (TextUtils.isEmpty(paramSystemMessageItem.getSubTitle()))
      {
        this.mViewContent.setVisibility(0);
        this.mLayoutDetail.setVisibility(8);
        String str3 = paramSystemMessageItem.getTitle();
        if (!TextUtils.isEmpty(str3))
        {
          ArrayList localArrayList3 = paramSystemMessageItem.makeTitleList(str3);
          this.mViewContent.setTitleList(localArrayList3);
          this.mViewContent.setOnClickLinkListener(SystemMessageListFragment.this);
          return;
        }
        this.mViewContent.setVisibility(8);
        return;
      }
      this.mViewContent.setVisibility(0);
      this.mLayoutDetail.setVisibility(0);
      String str1 = paramSystemMessageItem.getContent();
      ArrayList localArrayList2;
      if (!TextUtils.isEmpty(str1))
        if (SystemMessageListFragment.this.mTabIndex == 1)
        {
          localArrayList2 = paramSystemMessageItem.makeTitleList(": " + str1);
          KXLinkInfo localKXLinkInfo = new KXLinkInfo();
          localKXLinkInfo.setType("0");
          localKXLinkInfo.setId(paramSystemMessageItem.getFuid());
          localKXLinkInfo.setContent(paramSystemMessageItem.getRealName());
          localArrayList2.add(0, localKXLinkInfo);
          this.mViewContent.setTitleList(localArrayList2);
          this.mViewContent.setOnClickLinkListener(SystemMessageListFragment.this);
        }
      while (true)
      {
        String str2 = paramSystemMessageItem.getSubTitle();
        if (TextUtils.isEmpty(str2))
          break label308;
        ArrayList localArrayList1 = paramSystemMessageItem.makeTitleList(str2);
        this.mViewOriginalContent.setOnClickLinkListener(null);
        this.mViewOriginalContent.setTitleList(localArrayList1);
        this.mViewOriginalContent.setVisibility(0);
        this.mLayoutDetail.setVisibility(0);
        return;
        localArrayList2 = paramSystemMessageItem.makeTitleList(paramSystemMessageItem.getTitle());
        break;
        this.mViewContent.setVisibility(8);
      }
      label308: this.mViewOriginalContent.setVisibility(8);
      this.mLayoutDetail.setVisibility(8);
    }

    public boolean handleKnownType(SystemMessageItem paramSystemMessageItem, int paramInt)
    {
      if (paramSystemMessageItem == null);
      String str;
      label32: label243: label255: 
      do
      {
        return false;
        1 local1 = new KXIntroView.OnClickLinkListener(paramSystemMessageItem)
        {
          public void onClick(KXLinkInfo paramKXLinkInfo)
          {
            if ("6".equals(paramKXLinkInfo.getType()))
            {
              SystemMessageListFragment.this.showAlbum(this.val$item);
              return;
            }
            if ("1".equals(paramKXLinkInfo.getType()))
            {
              SystemMessageListFragment.this.showPhoto(this.val$item);
              return;
            }
            if ("1016".equals(paramKXLinkInfo.getType()))
            {
              String str2 = paramKXLinkInfo.getId();
              IntentUtil.showVoteDetailActivity(SystemMessageListFragment.this.getActivity(), str2);
              return;
            }
            if ("2".equals(paramKXLinkInfo.getType()))
            {
              Intent localIntent1 = new Intent(SystemMessageListFragment.this.getActivity(), DiaryDetailFragment.class);
              Bundle localBundle1 = new Bundle();
              localBundle1.putString("fuid", this.val$item.getOUID());
              if (User.getInstance().getUID().equals(this.val$item.getOUID()));
              for (String str1 = SystemMessageListFragment.this.getResources().getString(2131427565); ; str1 = this.val$item.getOName())
              {
                localBundle1.putString("fname", str1);
                localBundle1.putString("did", this.val$item.getObjID());
                localBundle1.putString("title", "[|s|]" + this.val$item.getOName() + "[|m|]" + this.val$item.getOUID() + "[|m|]" + this.val$item.getFStar() + "[|e|]" + " 发表了一篇日记");
                localBundle1.putString("intro", "[|s|]" + paramKXLinkInfo.getContent() + "[|m|]" + this.val$item.getObjID() + "[|m|]" + this.val$item.getAppId() + "[|e|]" + "\n" + this.val$item.getSourceItem().fragment);
                localIntent1.putExtras(localBundle1);
                SystemMessageListFragment.this.startFragment(localIntent1, true, 1);
                return;
              }
            }
            if (paramKXLinkInfo.getType().equals("1210"))
            {
              Intent localIntent2 = new Intent(SystemMessageListFragment.this.getActivity(), StyleBoxDiaryDetailFragment.class);
              Bundle localBundle2 = new Bundle();
              localBundle2.putString("fuid", User.getInstance().getUID());
              localBundle2.putString("id", paramKXLinkInfo.getId());
              localBundle2.putString("title", this.val$item.getSourceItem().title);
              localBundle2.putString("cnum", this.val$item.getCnum());
              localBundle2.putString("visible", this.val$item.getSourceItem().visible);
              localBundle2.putString("delete", this.val$item.getSourceItem().delete);
              localIntent2.putExtras(localBundle2);
              SystemMessageListFragment.this.startFragment(localIntent2, true, 1);
              return;
            }
            SystemMessageListFragment.this.onClick(paramKXLinkInfo);
          }
        };
        if (TextUtils.isEmpty(paramSystemMessageItem.getAppId()))
        {
          str = paramSystemMessageItem.getMsgType();
          this.mViewNote.setVisibility(8);
          setIconAndTypeText(str);
          if ((!"1192".equals(str)) || (paramInt != 1) || ((!TextUtils.isEmpty(paramSystemMessageItem.getMsgType())) && (paramSystemMessageItem.getMsgType().equals("1242"))))
            break label243;
          this.singLayout.setVisibility(0);
          this.signBtn.setOnClickListener(new View.OnClickListener(paramSystemMessageItem)
          {
            public void onClick(View paramView)
            {
              if (!TextUtils.isEmpty(this.val$item.getContent()))
              {
                SystemMessageListFragment.this.modifyUnreadNum(SystemMessageListFragment.this.mListMessages.indexOf(this.val$item));
                new ArrayList();
                ArrayList localArrayList1 = this.val$item.makeTitleList(this.val$item.getContent());
                ArrayList localArrayList2 = SystemMessageListFragment.this.AbstractUserInfoFromKXLinkInfo(localArrayList1, this.val$item);
                Bundle localBundle = new Bundle();
                localBundle.putSerializable("at_firends", localArrayList2);
                localBundle.putBoolean("is_show_poi_detail", false);
                IntentUtil.showPoiListFragment(SystemMessageListFragment.this, localBundle);
              }
            }
          });
          this.viewBtn.setOnClickListener(new View.OnClickListener(paramSystemMessageItem)
          {
            public void onClick(View paramView)
            {
              new ArrayList();
              ArrayList localArrayList = this.val$item.makeTitleList(this.val$item.getContent());
              if ((SystemMessageListFragment.this.AbstractPoiFromKXLinkInfo(localArrayList) != null) && (this.val$item.mExtendItem != null))
                IntentUtil.showLbsCheckInCommentFragment(SystemMessageListFragment.this, this.val$item.mExtendItem.mWid);
            }
          });
        }
        while (true)
        {
          if (this.mTxtTime != null)
            this.mTxtTime.setText(paramSystemMessageItem.getStrCtime());
          if (this.mImgArrow != null)
            this.mImgArrow.setVisibility(0);
          this.mLayoutDetail.setVisibility(0);
          this.mImgAvatar.setVisibility(0);
          if ((!"1".equals(str)) && (!"6".equals(str)) && (!"1016".equals(str)) && (!"1194".equals(str)))
            break label255;
          if (!showPicAlbumCompareAndVote(paramSystemMessageItem, local1, new View.OnClickListener(paramSystemMessageItem)
          {
            public void onClick(View paramView)
            {
              if ((paramView.getTag() instanceof RepostAlbumItem))
              {
                RepostAlbumItem localRepostAlbumItem = (RepostAlbumItem)paramView.getTag();
                if ("4".equals(localRepostAlbumItem.privacy))
                {
                  Toast.makeText(SystemMessageListFragment.this.getActivity(), 2131427924, 0).show();
                  return;
                }
                if ((!User.getInstance().getUID().equals(this.val$item.getOUID())) && ("1".equals(localRepostAlbumItem.privacy)) && ("0".equals(localRepostAlbumItem.visible)))
                {
                  Toast.makeText(SystemMessageListFragment.this.getActivity(), 2131427903, 0).show();
                  return;
                }
                SystemMessageListFragment.this.getPhotoShowUtil().showPhoto(localRepostAlbumItem.albumid, 2, Integer.parseInt(localRepostAlbumItem.pos), this.val$item.getOUID(), localRepostAlbumItem.albumtitle, localRepostAlbumItem.privacy);
                return;
              }
              SystemMessageListFragment.this.showPhoto(this.val$item);
            }
          }))
            break;
          return true;
          str = paramSystemMessageItem.getAppId();
          break label32;
          this.singLayout.setVisibility(8);
        }
        if ("1210".equals(str))
        {
          showStyleBox(paramSystemMessageItem);
          this.mImgArrow.setVisibility(8);
          return true;
        }
        if ("2".equals(str))
        {
          showDiary(paramSystemMessageItem, local1);
          return true;
        }
        if ("1192".equals(str))
        {
          this.mImgArrow.setVisibility(8);
          return false;
        }
        if ("1008".equals(str))
        {
          this.mImgArrow.setVisibility(8);
          return false;
        }
        if ("4".equals(str))
        {
          this.mImgArrow.setVisibility(8);
          return false;
        }
        if (!"1263".equals(str))
          continue;
        setPhotoDiary(paramSystemMessageItem, paramInt);
        return true;
      }
      while (!"1058".equals(str));
      this.mImgArrow.setVisibility(8);
      return false;
    }

    void setPhotoDiary(SystemMessageItem paramSystemMessageItem, int paramInt)
    {
      this.mImgArrow.setVisibility(8);
      String str1 = paramSystemMessageItem.getRealName();
      String str2 = paramSystemMessageItem.getFuid();
      ArrayList localArrayList = new ArrayList();
      KXLinkInfo localKXLinkInfo1 = new KXLinkInfo();
      localKXLinkInfo1.setContent(str1);
      localKXLinkInfo1.setType("0");
      localKXLinkInfo1.setId(str2);
      localArrayList.add(localKXLinkInfo1);
      KXLinkInfo localKXLinkInfo2 = new KXLinkInfo();
      if (paramInt == 2)
        if (!TextUtils.isEmpty(paramSystemMessageItem.getTitle()))
        {
          localKXLinkInfo2.setContent(paramSystemMessageItem.getTitle());
          localArrayList.add(localKXLinkInfo2);
          this.mViewContent.setTitleList(localArrayList);
        }
      while (true)
      {
        this.mViewContent.setOnClickLinkListener(SystemMessageListFragment.this);
        if (TextUtils.isEmpty(paramSystemMessageItem.getSubTitle()))
          break;
        this.mViewOriginalContent.setVisibility(0);
        this.mViewOriginalContent.setTitleList(paramSystemMessageItem.makeTitleList(paramSystemMessageItem.getSubTitle()));
        this.mViewOriginalContent.setOnClickLinkListener(SystemMessageListFragment.this);
        return;
        if ((paramInt != 1) || (TextUtils.isEmpty(paramSystemMessageItem.getContent())))
          continue;
        localKXLinkInfo2.setContent(": ");
        localArrayList.add(localKXLinkInfo2);
        localArrayList.addAll(paramSystemMessageItem.makeTitleList(paramSystemMessageItem.getContent()));
        this.mViewContent.setTitleList(localArrayList);
      }
      this.mViewOriginalContent.setVisibility(8);
      this.mViewOriginalContent.setOnClickLinkListener(null);
    }

    public void setSystemItem(SystemMessageItem paramSystemMessageItem)
    {
      String str1 = paramSystemMessageItem.getFlogo();
      SystemMessageListFragment.this.displayPicture(this.mImgAvatar, str1, 2130838676);
      this.mImg1.setVisibility(8);
      this.mImg2.setVisibility(8);
      this.mImg2.setBackgroundResource(2130839426);
      this.mImg3.setVisibility(8);
      this.mIvRecordPhoto.setVisibility(8);
      JSONObject localJSONObject3;
      if (SystemMessageListFragment.this.mTabIndex == 1)
        if (!handleKnownType(paramSystemMessageItem, SystemMessageListFragment.this.mTabIndex))
        {
          String str9 = paramSystemMessageItem.getRealName();
          String str10 = paramSystemMessageItem.getFuid();
          String str11 = paramSystemMessageItem.getContent();
          String str12 = paramSystemMessageItem.getLocation();
          if (!TextUtils.isEmpty(str12))
            str11 = str11 + str12;
          new ArrayList();
          ArrayList localArrayList7 = paramSystemMessageItem.makeTitleList(": " + str11);
          KXLinkInfo localKXLinkInfo7 = new KXLinkInfo();
          localKXLinkInfo7.setContent(str9);
          localKXLinkInfo7.setId(str10);
          localKXLinkInfo7.setType("0");
          localArrayList7.add(0, localKXLinkInfo7);
          this.mViewContent.setTitleList(localArrayList7);
          this.mViewContent.setOnClickLinkListener(SystemMessageListFragment.this);
          localJSONObject3 = paramSystemMessageItem.getJSONSubInfo();
          if (localJSONObject3 == null)
            break label899;
        }
      label899: label1105: label1107: label1117: 
      do
        while (true)
        {
          ArrayList localArrayList5;
          ArrayList localArrayList6;
          int m;
          try
          {
            String str15 = localJSONObject3.optString("intro");
            String str16 = localJSONObject3.optString("location");
            if (TextUtils.isEmpty(str16))
              continue;
            str15 = str15 + str16;
            ArrayList localArrayList8 = paramSystemMessageItem.makeTitleList(str15);
            this.mViewOriginalContent.setTitleList(localArrayList8);
            this.mViewOriginalContent.setOnClickLinkListener(SystemMessageListFragment.this);
            this.mViewOriginalContent.setVisibility(0);
            this.mLayoutDetail.setVisibility(0);
            JSONArray localJSONArray2 = localJSONObject3.optJSONArray("images");
            if ((localJSONArray2 == null) || (localJSONArray2.length() <= 0))
              continue;
            JSONObject localJSONObject4 = (JSONObject)localJSONArray2.get(0);
            String str17 = localJSONObject4.optString("thumbnail");
            String str18 = localJSONObject4.optString("large");
            if (TextUtils.isEmpty(str17))
              continue;
            this.mLayoutImages.setVisibility(0);
            this.mImg1.setTag(new String[] { str17, str18 });
            this.mImg1.setOnClickListener(SystemMessageListFragment.this.mImgOnClickListener);
            SystemMessageListFragment.this.displayPicture(this.mImg1, str17, 2130838780);
            this.mImg1.setVisibility(0);
            if (("1018".equals(paramSystemMessageItem.getAppId())) || ("1018".equals(paramSystemMessageItem.getMsgType())) || ("1210".equals(paramSystemMessageItem.getAppId())) || ("1210".equals(paramSystemMessageItem.getMsgType())) || ("1192".equals(paramSystemMessageItem.getAppId())) || ("1192".equals(paramSystemMessageItem.getMsgType())) || ("1008".equals(paramSystemMessageItem.getAppId())) || ("1".equals(paramSystemMessageItem.getMsgType())) || ("1263".equals(paramSystemMessageItem.getMsgType())) || ("1263".equals(paramSystemMessageItem.getAppId())) || ("1058".equals(paramSystemMessageItem.getMsgType())) || ("1058".equals(paramSystemMessageItem.getAppId())))
              continue;
            localArrayList5 = new ArrayList();
            KXLinkInfo localKXLinkInfo5 = new KXLinkInfo();
            localKXLinkInfo5.setContent("附言  \"");
            localKXLinkInfo5.setId("10066329");
            localKXLinkInfo5.setType("-101");
            localArrayList5.add(localKXLinkInfo5);
            String str8 = paramSystemMessageItem.getContent();
            if (TextUtils.isEmpty(str8))
              continue;
            localArrayList6 = paramSystemMessageItem.makeTitleList(str8);
            if (localArrayList6 != null)
              break label1107;
            k = 0;
            m = 0;
            if (m < k)
              break label1117;
            KXLinkInfo localKXLinkInfo6 = new KXLinkInfo();
            localKXLinkInfo6.setContent("\"");
            localKXLinkInfo6.setId("10066329");
            localKXLinkInfo6.setType("-101");
            localArrayList5.add(localKXLinkInfo6);
            this.mViewNote.setTitleList(localArrayList5);
            this.mViewNote.setVisibility(0);
            this.mViewNote.setOnClickLinkListener(SystemMessageListFragment.this);
            if (paramSystemMessageItem.getVImgSnapShot(0) == null)
              continue;
            SystemMessageListFragment.this.showVideoThumbnail(this.mImg2, paramSystemMessageItem.getVImgSnapShot(0));
            ImageView localImageView1 = this.mImg2;
            6 local6 = new View.OnClickListener(paramSystemMessageItem)
            {
              public void onClick(View paramView)
              {
                if (SystemMessageListFragment.this.mVideoPressed);
                while (true)
                {
                  return;
                  JSONObject localJSONObject = this.val$item.getJSONSubInfo();
                  Object localObject = null;
                  if (localJSONObject != null);
                  try
                  {
                    String str = localJSONObject.optString("intro");
                    ArrayList localArrayList = this.val$item.makeTitleList(str);
                    localObject = localArrayList;
                    if (localObject == null)
                      continue;
                    i = 0;
                    if (i >= localObject.size())
                    {
                      if (i >= localObject.size())
                        continue;
                      SystemMessageListFragment.this.mVideoPressed = true;
                      IntentUtil.showVideoPage(SystemMessageListFragment.this.getActivity().getApplicationContext(), ((KXLinkInfo)localObject.get(i)).getId(), ((KXLinkInfo)localObject.get(i)).getType(), ((KXLinkInfo)localObject.get(i)).getContent());
                      return;
                    }
                  }
                  catch (Exception localException)
                  {
                    while (true)
                    {
                      int i;
                      localException.printStackTrace();
                      localObject = null;
                      continue;
                      if (((KXLinkInfo)localObject.get(i)).isVideo())
                        continue;
                      i++;
                    }
                  }
                }
              }
            };
            localImageView1.setOnClickListener(local6);
            this.mLayoutImages.setVisibility(0);
            return;
            this.mLayoutImages.setVisibility(8);
            this.mImg1.setVisibility(8);
            this.mImg2.setVisibility(8);
            this.mImg3.setVisibility(8);
            this.mIvRecordPhoto.setVisibility(8);
            continue;
          }
          catch (JSONException localJSONException2)
          {
            KXLog.e("SystemMessageListActivity", "onPostExecute", localJSONException2);
            continue;
          }
          this.mViewOriginalContent.setVisibility(8);
          this.mLayoutDetail.setVisibility(8);
          this.mLayoutImages.setVisibility(8);
          String str13 = paramSystemMessageItem.getRecordThumbnail();
          String str14 = paramSystemMessageItem.getRecordLarge();
          if (!TextUtils.isEmpty(str13))
          {
            this.mIvRecordPhoto.setTag(new String[] { str13, str14 });
            this.mIvRecordPhoto.setOnClickListener(SystemMessageListFragment.this.mImgOnClickListener);
            SystemMessageListFragment.this.displayPicture(this.mIvRecordPhoto, str13, 2130838780);
            this.mIvRecordPhoto.setVisibility(0);
            this.mImg2.setVisibility(8);
            this.mImg3.setVisibility(8);
          }
          while (true)
          {
            if (TextUtils.isEmpty(paramSystemMessageItem.getVImgSnapShot(0)))
              break label1105;
            SystemMessageListFragment.this.showVideoThumbnail(this.mIvRecordPhoto, paramSystemMessageItem.getVImgSnapShot(0));
            ImageView localImageView2 = this.mIvRecordPhoto;
            5 local5 = new View.OnClickListener(paramSystemMessageItem)
            {
              public void onClick(View paramView)
              {
                SystemMessageListFragment.this.modifyUnreadNum(SystemMessageListFragment.this.mListMessages.indexOf(this.val$item));
                if (SystemMessageListFragment.this.mVideoPressed);
                JSONObject localJSONObject;
                do
                {
                  JSONArray localJSONArray;
                  do
                  {
                    return;
                    SystemMessageListFragment.this.mVideoPressed = true;
                    localJSONArray = this.val$item.getJSONVideoInfo();
                  }
                  while (localJSONArray == null);
                  localJSONObject = localJSONArray.optJSONObject(0);
                }
                while (localJSONObject == null);
                String str1 = localJSONObject.optString("url");
                String str2 = localJSONObject.optString("type");
                String str3 = localJSONObject.optString("title");
                IntentUtil.showVideoPage(SystemMessageListFragment.this.getActivity().getApplicationContext(), str1, str2, str3);
              }
            };
            localImageView2.setOnClickListener(local5);
            this.mImg2.setVisibility(8);
            this.mImg3.setVisibility(8);
            break;
            this.mIvRecordPhoto.setVisibility(8);
          }
          continue;
          int k = localArrayList6.size();
          continue;
          localArrayList5.add((KXLinkInfo)localArrayList6.get(m));
          m++;
        }
      while (SystemMessageListFragment.this.mTabIndex != 2);
      KXLinkInfo localKXLinkInfo4;
      label1251: JSONObject localJSONObject1;
      if (!handleKnownType(paramSystemMessageItem, SystemMessageListFragment.this.mTabIndex))
      {
        String str2 = paramSystemMessageItem.getRealName();
        String str3 = paramSystemMessageItem.getFuid();
        ArrayList localArrayList3 = new ArrayList();
        KXLinkInfo localKXLinkInfo3 = new KXLinkInfo();
        localKXLinkInfo3.setContent(str2);
        localKXLinkInfo3.setType("0");
        localKXLinkInfo3.setId(str3);
        localArrayList3.add(localKXLinkInfo3);
        localKXLinkInfo4 = new KXLinkInfo();
        if (!TextUtils.isEmpty(paramSystemMessageItem.getTitle()))
          break label1659;
        localKXLinkInfo4.setContent("转发了你的记录");
        localArrayList3.add(localKXLinkInfo4);
        this.mViewContent.setTitleList(localArrayList3);
        this.mViewContent.setOnClickLinkListener(SystemMessageListFragment.this);
        localJSONObject1 = paramSystemMessageItem.getJSONSubInfo();
        if (localJSONObject1 == null)
          break label1699;
      }
      while (true)
      {
        ArrayList localArrayList1;
        ArrayList localArrayList2;
        int j;
        try
        {
          String str4 = localJSONObject1.optString("intro");
          String str5 = localJSONObject1.optString("location");
          if (TextUtils.isEmpty(str5))
            continue;
          str4 = str4 + str5;
          ArrayList localArrayList4 = paramSystemMessageItem.makeTitleList(str4);
          this.mViewOriginalContent.setVisibility(0);
          this.mViewOriginalContent.setTitleList(localArrayList4);
          this.mViewOriginalContent.setOnClickLinkListener(SystemMessageListFragment.this);
          this.mLayoutDetail.setVisibility(0);
          JSONArray localJSONArray1 = localJSONObject1.optJSONArray("images");
          if ((localJSONArray1 == null) || (localJSONArray1.length() <= 0))
            continue;
          JSONObject localJSONObject2 = (JSONObject)localJSONArray1.get(0);
          String str6 = localJSONObject2.optString("thumbnail");
          String str7 = localJSONObject2.optString("large");
          if (TextUtils.isEmpty(str6))
            continue;
          this.mLayoutImages.setVisibility(0);
          this.mImg1.setTag(new String[] { str6, str7 });
          this.mImg1.setOnClickListener(SystemMessageListFragment.this.mImgOnClickListener);
          SystemMessageListFragment.this.displayPicture(this.mImg1, str6, 2130838780);
          this.mImg1.setVisibility(0);
          localArrayList1 = new ArrayList();
          KXLinkInfo localKXLinkInfo1 = new KXLinkInfo();
          localKXLinkInfo1.setContent("附言  \"");
          localKXLinkInfo1.setId("10066329");
          localKXLinkInfo1.setType("-101");
          localArrayList1.add(localKXLinkInfo1);
          localArrayList2 = paramSystemMessageItem.makeTitleList(paramSystemMessageItem.getContent());
          if (localArrayList2 != null)
            break label1711;
          i = 0;
          j = 0;
          if (j < i)
            break label1721;
          KXLinkInfo localKXLinkInfo2 = new KXLinkInfo();
          localKXLinkInfo2.setContent("\"");
          localKXLinkInfo2.setId("10066329");
          localKXLinkInfo2.setType("-101");
          localArrayList1.add(localKXLinkInfo2);
          this.mViewNote.setTitleList(localArrayList1);
          this.mViewNote.setVisibility(0);
          this.mViewNote.setOnClickLinkListener(SystemMessageListFragment.this);
          break;
          label1659: localKXLinkInfo4.setContent(paramSystemMessageItem.getTitle());
          break label1251;
          this.mLayoutImages.setVisibility(8);
          continue;
        }
        catch (JSONException localJSONException1)
        {
          KXLog.e("SystemMessageListActivity", "onPostExecute", localJSONException1);
          continue;
        }
        label1699: this.mLayoutDetail.setVisibility(8);
        continue;
        label1711: int i = localArrayList2.size();
        continue;
        label1721: localArrayList1.add((KXLinkInfo)localArrayList2.get(j));
        j++;
      }
    }
  }

  static class NameIdItem
    implements Serializable
  {
    public final String mId;
    public final String mName;

    NameIdItem(String paramString1, String paramString2)
    {
      this.mName = paramString1;
      this.mId = paramString2;
    }
  }

  private class NoticeMessageItemCache
  {
    public Button mBtnAccept = null;
    public Button mBtnGift = null;
    public Button mBtnIgnore = null;
    public Button mBtnReplyMessage = null;
    public ImageView mImgArrow = null;
    public ImageView mImgAvatar = null;
    public ImageView mImgTypeIcon = null;
    public SystemMessageItem mItem = null;
    public LinearLayout mLayoutButtons = null;
    public LinearLayout mLayoutContent = null;
    public LinearLayout mLayoutGift = null;
    public RelativeLayout mLayoutHeader = null;
    public View mLayoutRoot = null;
    public RelativeLayout mLayoutSameFriends = null;
    public View mLayoutViewMore = null;
    public int mPosition = -1;
    public ProgressBar mProgressBar = null;
    public TextView mTxtFriendsCount = null;
    public TextView mTxtPostScript = null;
    public TextView mTxtRequester = null;
    public TextView mTxtResult = null;
    public TextView mTxtTime = null;
    public TextView mTxtType = null;
    public TextView mTxtViewMore = null;
    public KXIntroView mViewContent = null;
    public KXIntroView mViewPraiseOriginal = null;
    public KXIntroView mViewRepostPraiseBubblePart = null;
    public ImageView mivPicPraise = null;
    public ImageView mivSameFriendArraw = null;
    public TextView mtvFriendsList = null;

    public NoticeMessageItemCache(View arg2)
    {
      Object localObject;
      if (localObject == null)
        return;
      this.mLayoutRoot = localObject;
      this.mLayoutHeader = ((RelativeLayout)localObject.findViewById(2131363655));
      this.mImgTypeIcon = ((ImageView)localObject.findViewById(2131363656));
      this.mTxtType = ((TextView)localObject.findViewById(2131363657));
      this.mTxtTime = ((TextView)localObject.findViewById(2131363658));
      this.mLayoutContent = ((LinearLayout)localObject.findViewById(2131363659));
      this.mImgAvatar = ((ImageView)localObject.findViewById(2131363660));
      this.mViewPraiseOriginal = ((KXIntroView)localObject.findViewById(2131363662));
      this.mViewRepostPraiseBubblePart = ((KXIntroView)localObject.findViewById(2131363663));
      this.mivPicPraise = ((ImageView)localObject.findViewById(2131363664));
      this.mViewContent = ((KXIntroView)localObject.findViewById(2131363661));
      this.mTxtPostScript = ((TextView)localObject.findViewById(2131363665));
      this.mivSameFriendArraw = ((ImageView)localObject.findViewById(2131363668));
      this.mLayoutSameFriends = ((RelativeLayout)localObject.findViewById(2131363666));
      if (this.mLayoutSameFriends != null)
        this.mLayoutSameFriends.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramView)
          {
            if (SystemMessageListFragment.NoticeMessageItemCache.this.mtvFriendsList == null)
              return;
            if (SystemMessageListFragment.NoticeMessageItemCache.this.mtvFriendsList.isShown())
            {
              SystemMessageListFragment.NoticeMessageItemCache.this.mtvFriendsList.setVisibility(8);
              SystemMessageListFragment.NoticeMessageItemCache.this.mivSameFriendArraw.setImageResource(2130838917);
              return;
            }
            SystemMessageListFragment.NoticeMessageItemCache.this.mtvFriendsList.setVisibility(0);
            SystemMessageListFragment.NoticeMessageItemCache.this.mivSameFriendArraw.setImageResource(2130838918);
          }
        });
      this.mtvFriendsList = ((TextView)localObject.findViewById(2131363669));
      this.mTxtRequester = ((TextView)localObject.findViewById(2131363670));
      this.mTxtFriendsCount = ((TextView)localObject.findViewById(2131363667));
      this.mImgArrow = ((ImageView)localObject.findViewById(2131363654));
      this.mLayoutButtons = ((LinearLayout)localObject.findViewById(2131363671));
      this.mBtnAccept = ((Button)localObject.findViewById(2131363672));
      this.mBtnIgnore = ((Button)localObject.findViewById(2131363673));
      this.mLayoutViewMore = localObject.findViewById(2131363674);
      if (this.mLayoutViewMore != null)
      {
        this.mProgressBar = ((ProgressBar)this.mLayoutViewMore.findViewById(2131362185));
        this.mTxtViewMore = ((TextView)this.mLayoutViewMore.findViewById(2131362186));
      }
      this.mTxtResult = ((TextView)localObject.findViewById(2131363675));
      this.mLayoutGift = ((LinearLayout)localObject.findViewById(2131363676));
      this.mBtnGift = ((Button)localObject.findViewById(2131363677));
      this.mBtnReplyMessage = ((Button)localObject.findViewById(2131363680));
    }

    private String getPraiseContentType(String paramString)
    {
      String str = "";
      if (paramString.equals("2"))
        str = "日记: ";
      if (paramString.equals("1018"))
        str = "记录: ";
      if (paramString.equals("1210"))
        str = "九宫格日记: ";
      if (paramString.equals("1016"))
        str = "投票: ";
      if (paramString.equals("1088"))
        str = "转贴: ";
      if (paramString.equals("1242"))
        str = "转发:";
      if (paramString.equals("6"))
        str = "专辑: ";
      if (paramString.equals("1194"))
        str = "评比: ";
      if (paramString.equals("1"))
        str = "照片";
      return str;
    }

    private void setAvatar(SystemMessageItem paramSystemMessageItem)
    {
      if ((paramSystemMessageItem == null) || (this.mImgAvatar == null))
        return;
      String str1 = paramSystemMessageItem.getMsgType();
      boolean bool = "礼物".equals(str1);
      if ((!"好友请求".equals(str1)) && (!bool) && (!"真心话".equals(str1)))
      {
        this.mImgAvatar.setVisibility(8);
        return;
      }
      String str2 = paramSystemMessageItem.getFlogo();
      if (paramSystemMessageItem.getContent().indexOf("匿名") != -1)
        str2 = "";
      SystemMessageListFragment.this.displayPicture(this.mImgAvatar, str2, 2130838676);
      this.mImgAvatar.setVisibility(0);
    }

    private void setBirthday(SystemMessageItem paramSystemMessageItem, int paramInt)
    {
      if ((paramSystemMessageItem == null) || (this.mLayoutGift == null));
      int i;
      String str6;
      do
        while (true)
        {
          return;
          String str1 = paramSystemMessageItem.getFlogo();
          String str2 = paramSystemMessageItem.getFuid();
          String str3 = paramSystemMessageItem.getRealName();
          String str4 = paramSystemMessageItem.getSmid();
          String str5 = String.valueOf(paramInt);
          i = paramSystemMessageItem.getResult();
          str6 = SystemMessageListFragment.this.searchGender(SystemMessageListFragment.this.mFriendList, str2);
          if (i != 0)
            break;
          SystemMessageListFragment.mGiftType = "1";
          this.mLayoutGift.setVisibility(0);
          if (this.mBtnGift != null)
          {
            this.mBtnGift.setText(SystemMessageListFragment.this.getEmptyTextView(str6, SystemMessageListFragment.this.getResources().getString(2131427861)));
            this.mBtnGift.setVisibility(0);
            this.mBtnGift.setOnClickListener(new View.OnClickListener(paramInt, str3, str2, str4, str5, str1)
            {
              public void onClick(View paramView)
              {
                SystemMessageListFragment.this.modifyUnreadNum(this.val$position);
                Intent localIntent = new Intent(SystemMessageListFragment.this.getActivity(), SendGiftFragment.class);
                localIntent.putExtra("fname", this.val$name);
                localIntent.putExtra("fuid", this.val$fuid);
                localIntent.putExtra("giftType", SystemMessageListFragment.mGiftType);
                localIntent.putExtra("smid", this.val$smid);
                localIntent.putExtra("position", this.val$pos);
                Bundle localBundle = new Bundle();
                ArrayList localArrayList = new ArrayList();
                localArrayList.add(new FriendInfo(this.val$name, this.val$fuid, this.val$logo));
                localBundle.putSerializable("checkedFriendsList", localArrayList);
                localIntent.putExtras(localBundle);
                AnimationUtil.startFragmentForResult(SystemMessageListFragment.this, localIntent, 1, 1);
              }
            });
          }
          if (this.mBtnReplyMessage == null)
            continue;
          this.mBtnReplyMessage.setVisibility(0);
          this.mBtnReplyMessage.setOnClickListener(new View.OnClickListener(paramInt, str2)
          {
            public void onClick(View paramView)
            {
              SystemMessageListFragment.this.modifyUnreadNum(this.val$position);
              Intent localIntent = new Intent(SystemMessageListFragment.this.getActivity(), InputFragment.class);
              Bundle localBundle = new Bundle();
              localBundle.putInt("mode", 7);
              localBundle.putString("fuid", this.val$fuid);
              localIntent.putExtras(localBundle);
              SystemMessageListFragment.this.startFragment(localIntent, true, 1);
            }
          });
          return;
        }
      while ((i != 1) || (this.mTxtResult == null));
      this.mTxtResult.setVisibility(0);
      this.mTxtResult.setText(SystemMessageListFragment.this.getEmptyTextView(str6, SystemMessageListFragment.this.getResources().getString(2131427876)));
    }

    private void setContentView(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, SystemMessageItem paramSystemMessageItem)
    {
      if ((this.mViewContent == null) || (paramString3 == null) || (paramString4 == null))
        return;
      ArrayList localArrayList1 = new ArrayList();
      KXLinkInfo localKXLinkInfo = new KXLinkInfo();
      int m;
      if ((!TextUtils.isEmpty(paramString1)) && (!"赞".equals(paramString4)))
      {
        if (paramString3.indexOf(paramString1) == 0)
          paramString3 = paramString3.substring(paramString1.length());
        if (("好友请求".equals(paramString4)) && (!TextUtils.isEmpty(paramString5)))
        {
          m = -1 + paramString3.indexOf(paramString5);
          int n = 3 + (m + paramString5.length());
          if (paramString3.length() <= n)
            break label360;
          paramString3 = paramString3.substring(0, m) + paramString3.substring(n);
        }
        label149: if ((!"礼物".equals(paramString4)) || (paramString3.indexOf("匿名") == -1))
        {
          localKXLinkInfo.setContent(paramString1);
          localKXLinkInfo.setType("0");
          localKXLinkInfo.setId(paramString2);
          localArrayList1.add(localKXLinkInfo);
        }
      }
      if ("赞".equals(paramString4))
      {
        int i = paramString3.indexOf("\"");
        int j = paramString3.lastIndexOf("\"");
        int k = paramString3.indexOf("觉得你的");
        if ((i != -1) && (j != -1) && (k != -1))
          paramString3 = paramString3.substring(0, k) + " " + paramString3.substring(k, i - 1) + paramString3.substring(j + 2, -1 + paramString3.length());
        localArrayList1 = NewsInfo.makeIntroList(paramString3);
      }
      while (true)
      {
        if (this.mViewContent != null)
        {
          this.mViewContent.setTitleList(localArrayList1);
          this.mViewContent.setOnClickLinkListener(SystemMessageListFragment.this);
        }
        if (this.mivPicPraise == null)
          break;
        this.mivPicPraise.setVisibility(8);
        return;
        label360: paramString3 = paramString3.substring(0, m);
        break label149;
        ArrayList localArrayList2 = NewsInfo.makeIntroList(paramString3);
        if (localArrayList2 == null)
          continue;
        localArrayList1.addAll(localArrayList2);
      }
    }

    private void setFriendRequest(SystemMessageItem paramSystemMessageItem, int paramInt)
    {
      if (paramSystemMessageItem == null);
      label85: int i;
      label368: 
      do
      {
        do
          while (true)
          {
            return;
            if (this.mTxtPostScript != null)
            {
              String str3 = paramSystemMessageItem.getPostScript();
              if (!TextUtils.isEmpty(str3))
              {
                String str4 = SystemMessageListFragment.this.getString(2131427865) + "\"" + str3 + "\"";
                this.mTxtPostScript.setText(str4);
                this.mTxtPostScript.setVisibility(0);
              }
            }
            else
            {
              if ((this.mTxtFriendsCount == null) || (this.mtvFriendsList == null) || (this.mivSameFriendArraw == null) || (paramSystemMessageItem.getFriendCount() == null) || (paramSystemMessageItem.getFriendCount().length() <= 0) || (paramSystemMessageItem.getFriendCount().equals("0")))
                break label368;
              String str2 = SystemMessageListFragment.this.getString(2131428013).replace("*", paramSystemMessageItem.getFriendCount());
              this.mTxtFriendsCount.setText(str2);
              this.mLayoutSameFriends.setVisibility(0);
              this.mTxtFriendsCount.setVisibility(0);
              this.mtvFriendsList.setText(paramSystemMessageItem.getFriends());
              this.mivSameFriendArraw.setVisibility(0);
            }
            int j;
            int k;
            while (true)
            {
              if (this.mTxtRequester != null)
              {
                String str1 = paramSystemMessageItem.getAddFriendByWho();
                if (!TextUtils.isEmpty(str1))
                {
                  this.mTxtRequester.setVisibility(0);
                  this.mTxtRequester.setText("(" + str1 + ")");
                }
              }
              i = paramSystemMessageItem.getResult();
              j = paramSystemMessageItem.getAction();
              k = ((Integer)SystemMessageListFragment.this.mItemStates.get(paramInt)).intValue();
              if ((k != 1002) && (k != 1003))
                break label435;
              if (this.mLayoutViewMore != null)
                this.mLayoutViewMore.setVisibility(0);
              if (this.mLayoutButtons != null)
                this.mLayoutButtons.setVisibility(8);
              if (this.mTxtResult == null)
                break;
              this.mTxtResult.setVisibility(8);
              return;
              this.mTxtPostScript.setVisibility(8);
              break label85;
              if ((this.mTxtFriendsCount == null) || (this.mtvFriendsList == null) || (this.mivSameFriendArraw == null) || (this.mLayoutSameFriends == null))
                continue;
              this.mTxtFriendsCount.setVisibility(8);
              this.mtvFriendsList.setVisibility(8);
              this.mivSameFriendArraw.setVisibility(8);
              this.mLayoutSameFriends.setVisibility(8);
            }
            if (k == 1004)
            {
              if (this.mTxtResult == null)
                continue;
              this.mTxtResult.setText(SystemMessageListFragment.this.getResources().getString(2131427573));
              return;
            }
            if (j != 1)
              break;
            if (this.mLayoutButtons == null)
              continue;
            this.mLayoutButtons.setVisibility(0);
            this.mBtnAccept.setTag(this);
            this.mBtnAccept.setOnClickListener(new SystemMessageListFragment.ButtonOnClickListener(SystemMessageListFragment.this, 1, paramInt));
            this.mBtnIgnore.setTag(this);
            this.mBtnIgnore.setOnClickListener(new SystemMessageListFragment.ButtonOnClickListener(SystemMessageListFragment.this, 2, paramInt));
            if (this.mLayoutGift == null)
              continue;
            this.mLayoutGift.setVisibility(8);
            return;
          }
        while (this.mTxtResult == null);
        if (i != 1)
          continue;
        this.mTxtResult.setVisibility(0);
        this.mTxtResult.setText(SystemMessageListFragment.this.getString(2131427560));
        return;
      }
      while (i != 3);
      label435: this.mTxtResult.setVisibility(0);
      this.mTxtResult.setText(SystemMessageListFragment.this.getString(2131427561));
    }

    private void setGift(SystemMessageItem paramSystemMessageItem, int paramInt, String paramString)
    {
      if ((paramSystemMessageItem == null) || (this.mLayoutGift == null));
      String str2;
      int i;
      label189: 
      do
      {
        return;
        String str1 = paramSystemMessageItem.getFuid();
        str2 = SystemMessageListFragment.this.searchGender(SystemMessageListFragment.this.mFriendList, str1);
        i = paramSystemMessageItem.getResult();
        if (i != 0)
          continue;
        if (paramSystemMessageItem.getContent().indexOf("匿名") == -1)
        {
          SystemMessageListFragment.mGiftType = "0";
          this.mLayoutGift.setVisibility(0);
          if (this.mBtnGift != null)
          {
            this.mBtnGift.setTag(this);
            if ("礼物".equals(paramString))
              break label189;
          }
          for (int j = 2131428428; ; j = 2131427858)
          {
            this.mBtnGift.setText(SystemMessageListFragment.this.getEmptyTextView(str2, SystemMessageListFragment.this.getResources().getString(j)));
            this.mBtnGift.setVisibility(0);
            this.mBtnGift.setOnClickListener(new View.OnClickListener(paramSystemMessageItem)
            {
              public void onClick(View paramView)
              {
                SystemMessageListFragment.NoticeMessageItemCache localNoticeMessageItemCache = (SystemMessageListFragment.NoticeMessageItemCache)paramView.getTag();
                SystemMessageListFragment.this.modifyUnreadNum(SystemMessageListFragment.this.mListMessages.indexOf(this.val$item));
                if ((localNoticeMessageItemCache == null) || (localNoticeMessageItemCache.mItem == null))
                  return;
                String str1 = localNoticeMessageItemCache.mItem.getRealName();
                String str2 = localNoticeMessageItemCache.mItem.getFuid();
                String str3 = localNoticeMessageItemCache.mItem.getSmid();
                String str4 = localNoticeMessageItemCache.mItem.getFlogo();
                String str5 = String.valueOf(localNoticeMessageItemCache.mPosition);
                Intent localIntent = new Intent(SystemMessageListFragment.this.getActivity(), SendGiftFragment.class);
                localIntent.putExtra("fname", str1);
                localIntent.putExtra("fuid", str2);
                localIntent.putExtra("giftType", SystemMessageListFragment.mGiftType);
                localIntent.putExtra("smid", str3);
                localIntent.putExtra("position", str5);
                Bundle localBundle = new Bundle();
                ArrayList localArrayList = new ArrayList();
                localArrayList.add(new FriendInfo(str1, str2, str4));
                localBundle.putSerializable("checkedFriendsList", localArrayList);
                localIntent.putExtras(localBundle);
                AnimationUtil.startFragmentForResult(SystemMessageListFragment.this, localIntent, 1, 1);
                UserHabitUtils.getInstance(SystemMessageListFragment.this.getActivity()).addUserHabit("Gift_Resend");
              }
            });
            if (this.mBtnReplyMessage == null)
              break;
            this.mBtnReplyMessage.setVisibility(0);
            this.mBtnReplyMessage.setOnClickListener(new View.OnClickListener(paramSystemMessageItem, str1)
            {
              public void onClick(View paramView)
              {
                SystemMessageListFragment.this.modifyUnreadNum(SystemMessageListFragment.this.mListMessages.indexOf(this.val$item));
                Intent localIntent = new Intent(SystemMessageListFragment.this.getActivity(), InputFragment.class);
                Bundle localBundle = new Bundle();
                localBundle.putInt("mode", 7);
                localBundle.putString("fuid", this.val$fuid);
                localIntent.putExtras(localBundle);
                SystemMessageListFragment.this.startFragment(localIntent, true, 3);
                UserHabitUtils.getInstance(SystemMessageListFragment.this.getActivity()).addUserHabit("Gift_Message");
              }
            });
            return;
          }
        }
        this.mBtnReplyMessage.setVisibility(8);
        this.mBtnGift.setVisibility(8);
        return;
      }
      while ((!paramString.equals("礼物")) || (i != 1) || (this.mTxtResult == null));
      this.mTxtResult.setVisibility(0);
      this.mTxtResult.setText(SystemMessageListFragment.this.getEmptyTextView(str2, SystemMessageListFragment.this.getResources().getString(2131427875)));
    }

    private void setHeadLayout(String paramString1, String paramString2)
    {
      if (this.mTxtType != null)
        this.mTxtType.setText(paramString1);
      if (this.mTxtTime != null)
        this.mTxtTime.setText(paramString2);
    }

    private void setPicTypePraiseImg(SystemMessageItem paramSystemMessageItem)
    {
      if ((paramSystemMessageItem == null) || (this.mivPicPraise == null));
      do
        return;
      while ((TextUtils.isEmpty(paramSystemMessageItem.getSourceItem().rid)) || ("0".equals(paramSystemMessageItem.getSourceItem().rid)));
      SystemMessageListFragment.this.displayPicture(this.mivPicPraise, paramSystemMessageItem.getSourceItem().thumbnail, 2130838779);
      this.mivPicPraise.setVisibility(0);
      SystemMessageListFragment.PicTag localPicTag = new SystemMessageListFragment.PicTag();
      localPicTag.albumId = paramSystemMessageItem.getSourceItem().albumid;
      localPicTag.fuid = User.getInstance().getUID();
      localPicTag.picIndex = paramSystemMessageItem.getSourceItem().pos;
      localPicTag.picTitle = paramSystemMessageItem.getSourceItem().title;
      localPicTag.rid = paramSystemMessageItem.getSourceItem().rid;
      this.mivPicPraise.setTag(localPicTag);
      this.mivPicPraise.setOnClickListener(new View.OnClickListener(paramSystemMessageItem)
      {
        public void onClick(View paramView)
        {
          SystemMessageListFragment.this.modifyUnreadNum(SystemMessageListFragment.this.mListMessages.indexOf(this.val$item));
          SystemMessageListFragment.PicTag localPicTag = (SystemMessageListFragment.PicTag)paramView.getTag();
          if ((localPicTag == null) || (TextUtils.isEmpty(localPicTag.rid)) || ("0".equals(localPicTag.rid)))
          {
            Toast.makeText(SystemMessageListFragment.this.getActivity(), 2131427924, 0).show();
            return;
          }
          IntentUtil.showViewPhotoActivityNotFromAlbum(SystemMessageListFragment.this.getActivity(), SystemMessageListFragment.this, Integer.parseInt(localPicTag.picIndex), localPicTag.fuid, localPicTag.picTitle, localPicTag.albumId, 2, null);
        }
      });
    }

    private void setPraiseView(String paramString1, String paramString2, String paramString3, SystemMessageItem paramSystemMessageItem)
    {
      if ((this.mViewContent == null) || (paramString2 == null) || (paramString3 == null))
      {
        this.mViewPraiseOriginal.setTitleList(null);
        return;
      }
      new ArrayList();
      KXLinkInfo localKXLinkInfo = new KXLinkInfo();
      localKXLinkInfo.setContent(getPraiseContentType(paramString3));
      ArrayList localArrayList1;
      if (paramString3.equals("1242"))
      {
        ArrayList localArrayList2 = NewsInfo.makeIntroList(paramSystemMessageItem.getSourceItem().title);
        this.mViewRepostPraiseBubblePart.setTitleList(localArrayList2);
        this.mViewRepostPraiseBubblePart.setVisibility(0);
        if (NewsModel.getInstance() != null)
        {
          localArrayList1 = NewsInfo.makeIntroList(User.getInstance().getRealName());
          label107: localArrayList1.add(localKXLinkInfo);
        }
      }
      while (true)
      {
        2 local2 = new KXIntroView.OnClickLinkListener(paramSystemMessageItem)
        {
          public void onClick(KXLinkInfo paramKXLinkInfo)
          {
            if (paramKXLinkInfo.getType().equals("2"))
              SystemMessageListFragment.this.showDiaryDetailForPraise(this.val$item);
            do
            {
              return;
              if (paramKXLinkInfo.getType().equals("1018"))
              {
                Intent localIntent1 = new Intent(SystemMessageListFragment.this.getActivity(), NewsDetailFragment.class);
                DetailItem localDetailItem = DetailItem.makeWeiboDetailItem("1018", User.getInstance().getUID(), paramKXLinkInfo.getId());
                Bundle localBundle1 = new Bundle();
                localBundle1.putSerializable("data", localDetailItem);
                localIntent1.putExtras(localBundle1);
                SystemMessageListFragment.this.startActivityForResult(localIntent1, 1300);
                return;
              }
              if (paramKXLinkInfo.getType().equals("1088"))
              {
                Intent localIntent2 = new Intent(SystemMessageListFragment.this.getActivity(), RepostDetailFragment.class);
                ArrayList localArrayList = new ArrayList();
                RepItem localRepItem = new RepItem();
                localRepItem.fuid = User.getInstance().getUID();
                localRepItem.id = paramKXLinkInfo.getId();
                localArrayList.add(localRepItem);
                Bundle localBundle2 = new Bundle();
                localBundle2.putSerializable("repostList", (Serializable)localArrayList);
                localBundle2.putInt("position", 0);
                localIntent2.putExtras(localBundle2);
                AnimationUtil.startFragment(SystemMessageListFragment.this, localIntent2, 1);
                return;
              }
              if (paramKXLinkInfo.getType().equals("1016"))
              {
                Intent localIntent3 = new Intent(SystemMessageListFragment.this.getActivity(), VoteDetailFragment.class);
                Bundle localBundle3 = new Bundle();
                localBundle3.putString("vid", paramKXLinkInfo.getId());
                localIntent3.putExtras(localBundle3);
                AnimationUtil.startFragment(SystemMessageListFragment.this, localIntent3, 1);
                return;
              }
              if (!paramKXLinkInfo.getType().equals("1210"))
                continue;
              Intent localIntent4 = new Intent(SystemMessageListFragment.this.getActivity(), StyleBoxDiaryDetailFragment.class);
              Bundle localBundle4 = new Bundle();
              localBundle4.putString("fuid", User.getInstance().getUID());
              localBundle4.putString("id", paramKXLinkInfo.getId());
              localBundle4.putString("title", this.val$item.getSourceItem().title);
              localBundle4.putString("cnum", this.val$item.getCnum());
              localBundle4.putString("visible", this.val$item.getSourceItem().visible);
              localBundle4.putString("delete", this.val$item.getSourceItem().delete);
              localIntent4.putExtras(localBundle4);
              AnimationUtil.startFragment(SystemMessageListFragment.this, localIntent4, 1);
              return;
            }
            while (paramKXLinkInfo.getType().equals("1242"));
            SystemMessageListFragment.this.onClick(paramKXLinkInfo);
          }
        };
        if ((this.mViewPraiseOriginal != null) && (!paramString3.equals("1")))
        {
          this.mViewPraiseOriginal.setTitleList(localArrayList1);
          this.mViewPraiseOriginal.setOnClickLinkListener(local2);
        }
        if (!paramString3.equals("1"))
          break;
        this.mViewPraiseOriginal.setTitleList(null);
        setPicTypePraiseImg(paramSystemMessageItem);
        return;
        localArrayList1 = NewsInfo.makeIntroList("");
        break label107;
        if ((paramString1 != null) && (!paramSystemMessageItem.getAppId().equals("6")) && (!paramSystemMessageItem.getAppId().equals("1")))
        {
          localArrayList1 = NewsInfo.makeIntroList("[|s|]" + paramString2 + "[|m|]" + paramString1 + "[|m|]" + paramString3 + "[|e|]");
          localArrayList1.add(0, localKXLinkInfo);
          continue;
        }
        localArrayList1 = NewsInfo.makeIntroList(paramString2);
        if (localArrayList1 == null)
          continue;
        localArrayList1.add(0, localKXLinkInfo);
      }
    }

    private void setRepost(String paramString, int paramInt)
    {
      ArrayList localArrayList;
      if (this.mViewContent != null)
        localArrayList = ParseNewsInfoUtil.parseNewsLinkString(paramString);
      for (int i = 0; ; i++)
      {
        if (i >= localArrayList.size())
        {
          this.mViewContent.setTitleList(localArrayList);
          if (this.mImgArrow != null)
            this.mImgArrow.setVisibility(0);
          return;
        }
        ((KXLinkInfo)localArrayList.get(i)).setPosition(paramInt);
      }
    }

    private void setTouchThem(SystemMessageItem paramSystemMessageItem, int paramInt)
    {
      if ((paramSystemMessageItem == null) || (this.mLayoutGift == null))
        return;
      String str1 = paramSystemMessageItem.getFuid();
      String str2 = paramSystemMessageItem.getRealName();
      String str3 = paramSystemMessageItem.getSmid();
      String str4 = String.valueOf(paramInt);
      int i = paramSystemMessageItem.getResult();
      String str5 = SystemMessageListFragment.this.searchGender(SystemMessageListFragment.this.mFriendList, str1);
      if ((i == 1) && (this.mTxtResult != null))
      {
        this.mTxtResult.setVisibility(0);
        this.mTxtResult.setText(SystemMessageListFragment.this.getEmptyTextView(str5, SystemMessageListFragment.this.getResources().getString(2131428395)));
      }
      this.mLayoutGift.setVisibility(0);
      if (this.mBtnGift != null)
      {
        if (i != 1)
          break label223;
        this.mBtnGift.setText(SystemMessageListFragment.this.getEmptyTextView(str5, SystemMessageListFragment.this.getResources().getString(2131428394)));
      }
      while (true)
      {
        this.mBtnGift.setVisibility(0);
        this.mBtnGift.setOnClickListener(new View.OnClickListener(paramSystemMessageItem, str2, str1, str3, str4, i)
        {
          public void onClick(View paramView)
          {
            SystemMessageListFragment.this.modifyUnreadNum(SystemMessageListFragment.this.mListMessages.indexOf(this.val$item));
            Intent localIntent = new Intent(SystemMessageListFragment.this.getActivity(), SendTouchThemFragment.class);
            localIntent.putExtra("fname", this.val$name);
            localIntent.putExtra("fuid", this.val$fuid);
            localIntent.putExtra("giftType", SystemMessageListFragment.mGiftType);
            localIntent.putExtra("smid", this.val$smid);
            localIntent.putExtra("position", this.val$pos);
            SystemMessageListFragment.this.startActivityForResult(localIntent, 1);
            ((TextView)paramView).getText().toString();
            if (this.val$result == 1)
            {
              UserHabitUtils.getInstance(SystemMessageListFragment.this.getActivity()).addUserHabit("HitHim_Again");
              return;
            }
            UserHabitUtils.getInstance(SystemMessageListFragment.this.getActivity()).addUserHabit("HitHim_Once");
          }
        });
        if (this.mBtnReplyMessage == null)
          break;
        this.mBtnReplyMessage.setVisibility(0);
        this.mBtnReplyMessage.setOnClickListener(new View.OnClickListener(str1)
        {
          public void onClick(View paramView)
          {
            Intent localIntent = new Intent(SystemMessageListFragment.this.getActivity(), InputFragment.class);
            Bundle localBundle = new Bundle();
            localBundle.putInt("mode", 7);
            localBundle.putString("fuid", this.val$fuid);
            localIntent.putExtras(localBundle);
            SystemMessageListFragment.this.startFragment(localIntent, true, 1);
            UserHabitUtils.getInstance(SystemMessageListFragment.this.getActivity()).addUserHabit("HitHim_Message");
          }
        });
        return;
        label223: this.mBtnGift.setText(SystemMessageListFragment.this.getEmptyTextView(str5, SystemMessageListFragment.this.getResources().getString(2131428393)));
      }
    }

    public boolean showNoticeMessage(SystemMessageItem paramSystemMessageItem, int paramInt)
    {
      if ((paramSystemMessageItem == null) || (this.mLayoutRoot == null))
        return false;
      this.mItem = paramSystemMessageItem;
      this.mPosition = paramInt;
      if (this.mBtnReplyMessage != null)
        this.mBtnReplyMessage.setVisibility(8);
      if (this.mLayoutButtons != null)
        this.mLayoutButtons.setVisibility(8);
      if (this.mLayoutViewMore != null)
        this.mLayoutViewMore.setVisibility(8);
      if (this.mImgArrow != null)
        this.mImgArrow.setVisibility(8);
      if (this.mTxtResult != null)
        this.mTxtResult.setVisibility(8);
      if (this.mLayoutGift != null)
        this.mLayoutGift.setVisibility(8);
      if (this.mImgAvatar != null)
        this.mImgAvatar.setVisibility(8);
      if (this.mTxtRequester != null)
        this.mTxtRequester.setVisibility(8);
      String str1 = paramSystemMessageItem.getContent();
      if ((TextUtils.isEmpty(str1)) && (-1L == paramSystemMessageItem.getCtime().longValue()))
      {
        if (this.mLayoutHeader != null)
          this.mLayoutHeader.setVisibility(8);
        if (this.mLayoutContent != null)
          this.mLayoutContent.setVisibility(8);
        if (this.mLayoutViewMore != null)
          this.mLayoutViewMore.setVisibility(0);
        if (this.mProgressBar != null)
          this.mProgressBar.setVisibility(8);
        if (this.mTxtViewMore != null)
          this.mTxtViewMore.setText(2131427557);
        return true;
      }
      if (this.mLayoutHeader != null)
        this.mLayoutHeader.setVisibility(0);
      if (this.mLayoutContent != null)
        this.mLayoutContent.setVisibility(0);
      if (this.mProgressBar != null)
        this.mProgressBar.setVisibility(0);
      if (this.mTxtViewMore != null)
        this.mTxtViewMore.setText(2131427572);
      String str2 = paramSystemMessageItem.getMsgType();
      String str3 = paramSystemMessageItem.getRealName();
      String str4 = paramSystemMessageItem.getFuid();
      String str5;
      String str6;
      if (str1 == null)
      {
        str5 = "";
        str6 = str5.replaceAll("\t", "").replaceAll("    ", "");
      }
      while (true)
      {
        try
        {
          setHeadLayout(str2, MessageCenterModel.formatTimestamp(1000L * paramSystemMessageItem.getCtime().longValue()));
          setContentView(str3, str4, str6, str2, paramSystemMessageItem.getPostScript(), paramSystemMessageItem);
          if ((this.mTxtFriendsCount == null) || (this.mtvFriendsList == null) || (this.mivSameFriendArraw == null) || (this.mViewRepostPraiseBubblePart == null))
            continue;
          this.mTxtFriendsCount.setVisibility(8);
          this.mtvFriendsList.setVisibility(8);
          this.mivSameFriendArraw.setVisibility(8);
          this.mViewRepostPraiseBubblePart.setVisibility(8);
          if (this.mImgTypeIcon == null)
            continue;
          this.mImgTypeIcon.setImageResource(2130837743);
          if (str2.equals("赞"))
          {
            SystemMessageSource localSystemMessageSource = paramSystemMessageItem.getSourceItem();
            String str7 = null;
            String str8 = null;
            if (localSystemMessageSource == null)
              continue;
            str8 = paramSystemMessageItem.getSourceItem().title;
            str7 = paramSystemMessageItem.getSourceItem().rid;
            if (str8 != null)
              continue;
            str8 = str6;
            if (!str8.contains("\""))
              continue;
            int i = str8.indexOf("\"");
            int j = str8.indexOf("\"", i + 1);
            if ((i == -1) || (j == -1))
              continue;
            str8 = str8.substring(i + 1, j);
            setPraiseView(str7, str8, paramSystemMessageItem.getAppId(), paramSystemMessageItem);
            if (this.mViewPraiseOriginal.getTitleList() == null)
              continue;
            this.mViewPraiseOriginal.setVisibility(0);
            if (this.mImgTypeIcon == null)
              continue;
            this.mImgTypeIcon.setImageResource(2130838506);
            if ((!paramSystemMessageItem.getAppId().equals("2")) && (!paramSystemMessageItem.getAppId().equals("1")) && (!paramSystemMessageItem.getAppId().equals("1018")) && (!paramSystemMessageItem.getAppId().equals("1088")) && (!paramSystemMessageItem.getAppId().equals("1016")) && (!paramSystemMessageItem.getAppId().equals("1210")))
              continue;
            this.mImgArrow.setVisibility(0);
            setAvatar(paramSystemMessageItem);
            if (!"好友请求".equals(str2))
              break label799;
            setFriendRequest(paramSystemMessageItem, paramInt);
            return true;
            str5 = str1.trim();
            break;
            str8 = null;
            continue;
            this.mViewPraiseOriginal.setVisibility(8);
            continue;
          }
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
          return false;
        }
        this.mViewPraiseOriginal.setVisibility(8);
        continue;
        label799: if ("礼物".equals(str2))
        {
          setGift(paramSystemMessageItem, paramInt, str2);
          continue;
        }
        if ("转帖".equals(str2))
        {
          setRepost(str6, paramInt);
          continue;
        }
        if ("生日提醒".equals(str2))
        {
          setBirthday(paramSystemMessageItem, paramInt);
          continue;
        }
        if (!"动他一下".equals(str2))
          continue;
        setTouchThem(paramSystemMessageItem, paramInt);
      }
    }
  }

  public static class PicTag
  {
    String albumId;
    String albumTitle;
    String fuid;
    String picIndex;
    String picTitle;
    String pwd;
    String rid;
  }

  private class ProcessFriendRequestTask extends KXFragment.KXAsyncTask<String, Void, Boolean>
  {
    public static final int ACCEPT_REQUEST = 1;
    public static final int REFUSE_REQUEST = 2;
    private int mItemPosition;
    private int mType;

    private ProcessFriendRequestTask()
    {
      super();
    }

    protected Boolean doInBackgroundA(String[] paramArrayOfString)
    {
      if (paramArrayOfString.length < 4)
        return Boolean.valueOf(false);
      this.mType = Integer.parseInt(paramArrayOfString[0]);
      this.mItemPosition = Integer.parseInt(paramArrayOfString[1]);
      String str1 = paramArrayOfString[2];
      String str2 = paramArrayOfString[3];
      if (this.mType == 1)
        return Boolean.valueOf(SystemMessageEngine.getInstance().doAcceptFriendRequest(SystemMessageListFragment.this.getActivity(), str1, str2));
      return Boolean.valueOf(SystemMessageEngine.getInstance().doRefuseFriendRequest(SystemMessageListFragment.this.getActivity(), str1, str2));
    }

    protected void onPostExecuteA(Boolean paramBoolean)
    {
      while (true)
      {
        try
        {
          boolean bool = paramBoolean.booleanValue();
          if (bool)
            try
            {
              SystemMessageItem localSystemMessageItem = (SystemMessageItem)SystemMessageListFragment.this.mListMessages.get(this.mItemPosition);
              localSystemMessageItem.setAction(0);
              if (this.mType != 1)
                continue;
              Intent localIntent = new Intent("com.kaixin001.UPDATE_FRIENDS");
              SystemMessageListFragment.this.getActivity().sendBroadcast(localIntent);
              localSystemMessageItem.setResult(1);
              SystemMessageListFragment.this.mItemStates.set(this.mItemPosition, new Integer(1001));
              SystemMessageListFragment.this.mAdapterNotice.notifyDataSetChanged();
              return;
              localSystemMessageItem.setResult(3);
              continue;
            }
            catch (Exception localException2)
            {
              localException2.printStackTrace();
              continue;
            }
        }
        catch (Exception localException1)
        {
          KXLog.e("SystemMessageListActivity", "onPostExecute", localException1);
          return;
        }
        SystemMessageListFragment.this.mItemStates.set(this.mItemPosition, new Integer(1004));
      }
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private class SystemNoticeMessageAdapter extends BaseAdapter
  {
    public SystemNoticeMessageAdapter()
    {
    }

    public int getCount()
    {
      ArrayList localArrayList = SystemMessageListFragment.this.mListMessages;
      int i = 0;
      if (localArrayList != null)
        i = SystemMessageListFragment.this.mListMessages.size();
      return i;
    }

    public Object getItem(int paramInt)
    {
      if (SystemMessageListFragment.this.mListMessages == null);
      do
        return null;
      while ((paramInt < 0) || (paramInt >= SystemMessageListFragment.this.mListMessages.size()));
      return SystemMessageListFragment.this.mListMessages.get(paramInt);
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      SystemMessageItem localSystemMessageItem = (SystemMessageItem)getItem(paramInt);
      if ((localSystemMessageItem != null) && (localSystemMessageItem.getCtime().longValue() == -1L))
        return SystemMessageListFragment.this.mFooterView;
      SystemMessageListFragment.NoticeMessageItemCache localNoticeMessageItemCache;
      if ((paramView == null) || (paramView == SystemMessageListFragment.this.mFooterView))
      {
        paramView = SystemMessageListFragment.this.getActivity().getLayoutInflater().inflate(2130903340, null);
        localNoticeMessageItemCache = new SystemMessageListFragment.NoticeMessageItemCache(SystemMessageListFragment.this, paramView);
        paramView.setTag(localNoticeMessageItemCache);
      }
      while (localNoticeMessageItemCache == null)
      {
        return paramView;
        localNoticeMessageItemCache = (SystemMessageListFragment.NoticeMessageItemCache)paramView.getTag();
      }
      localNoticeMessageItemCache.showNoticeMessage(localSystemMessageItem, paramInt);
      if (SystemMessageListFragment.this.mUnreadItems.contains(new Integer(paramInt)))
        paramView.findViewById(2131363652).setVisibility(0);
      while (true)
      {
        return paramView;
        paramView.findViewById(2131363652).setVisibility(8);
      }
    }
  }

  private class SystemRecordMessageAdapter extends BaseAdapter
  {
    public SystemRecordMessageAdapter()
    {
    }

    public int getCount()
    {
      return SystemMessageListFragment.this.mListMessages.size();
    }

    public Object getItem(int paramInt)
    {
      if (SystemMessageListFragment.this.mListMessages == null);
      do
        return null;
      while ((paramInt < 0) || (paramInt >= SystemMessageListFragment.this.mListMessages.size()));
      return SystemMessageListFragment.this.mListMessages.get(paramInt);
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      SystemMessageItem localSystemMessageItem = (SystemMessageItem)getItem(paramInt);
      if (localSystemMessageItem == null)
        return paramView;
      if (localSystemMessageItem.getCtime().longValue() == -1L)
        return SystemMessageListFragment.this.mFooterView;
      SystemMessageListFragment.MentionMeAndRepostMessageItemView localMentionMeAndRepostMessageItemView;
      if ((paramView != null) && (paramView != SystemMessageListFragment.this.mFooterView))
      {
        localMentionMeAndRepostMessageItemView = (SystemMessageListFragment.MentionMeAndRepostMessageItemView)paramView.getTag();
        localMentionMeAndRepostMessageItemView.setSystemItem(localSystemMessageItem);
        if (!SystemMessageListFragment.this.mUnreadItems.contains(new Integer(paramInt)))
          break label144;
        paramView.findViewById(2131363652).setVisibility(0);
      }
      while (true)
      {
        return paramView;
        paramView = SystemMessageListFragment.this.getActivity().getLayoutInflater().inflate(2130903341, null);
        localMentionMeAndRepostMessageItemView = new SystemMessageListFragment.MentionMeAndRepostMessageItemView(SystemMessageListFragment.this, paramView);
        paramView.setTag(localMentionMeAndRepostMessageItemView);
        break;
        label144: paramView.findViewById(2131363652).setVisibility(8);
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.SystemMessageListFragment
 * JD-Core Version:    0.6.0
 */