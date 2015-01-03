package com.kaixin001.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask.Status;
import android.os.Bundle;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.engine.CommentEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.item.CommentItem;
import com.kaixin001.item.KXLinkInfo;
import com.kaixin001.model.MessageCenterModel;
import com.kaixin001.model.Setting;
import com.kaixin001.model.User;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.CrashRecoverUtil;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.KXTextUtil;
import com.kaixin001.util.ParseNewsInfoUtil;
import com.kaixin001.util.StringUtil;
import com.kaixin001.view.KXIntroView;
import com.kaixin001.view.KXIntroView.OnClickLinkListener;
import com.kaixin001.view.KXTopTab;
import com.kaixin001.view.KXTopTabHost;
import com.kaixin001.view.KXTopTabHost.OnTabChangeListener;
import com.kaixin001.view.PullToRefreshView;
import com.kaixin001.view.PullToRefreshView.PullToRefreshListener;
import java.util.ArrayList;

public class CommentListFragment extends BaseFragment
  implements AdapterView.OnItemClickListener, KXIntroView.OnClickLinkListener, KXTopTabHost.OnTabChangeListener, PullToRefreshView.PullToRefreshListener
{
  protected static final int MENU_HOME_ID = 412;
  protected static final int MENU_HOME_ME_ID = 413;
  protected static final int MENU_REFRESH_ID = 410;
  protected static final int MENU_TO_TOP_ID = 411;
  private ImageView btnRight;
  private final CommentAdapter mAdapter = new CommentAdapter();
  TextView mCenterText;
  private TextView mClickNewMsgNum;
  private int mClickPosition = -1;
  private TextView mClickTotalMsgNum;
  private final ArrayList<CommentItem> mCommentList = new ArrayList();
  private int mCommentType;
  private TextView mEmptyNoticeView;
  private ProgressBar mFooterProBar;
  private TextView mFooterTV;
  private View mFooterView;
  private GetCommentTask mGetCommentTask = null;
  ListView mListView;
  MessageCenterModel mMessageCenterModel = MessageCenterModel.getInstance();
  private int[] mNewMsgCount;
  private PullToRefreshView mPullView = null;
  private int[] mReturnNum = new int[2];
  private KXTopTabHost mTabHost;
  private ProgressBar rightProBar;
  private int[] scrollbarPositions = null;

  static
  {
    if (!CommentListFragment.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }

  private void cancelTask()
  {
    if ((this.mGetCommentTask != null) && (!this.mGetCommentTask.isCancelled()) && (this.mGetCommentTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.mGetCommentTask.cancel(true);
      this.mGetCommentTask = null;
      if (CommentEngine.getInstance() != null)
        CommentEngine.getInstance().cancel();
    }
  }

  private void doGetListData()
  {
    this.mCommentType = this.mMessageCenterModel.getActiveCommentType();
    ArrayList localArrayList1;
    int i;
    boolean bool;
    if (this.mCommentType == 3)
    {
      localArrayList1 = this.mMessageCenterModel.getCommentList();
      i = this.mMessageCenterModel.getNoticeCnt(3);
      if (this.mCommentType != 3)
        break label168;
      bool = Setting.getInstance().isNeedRefresh(2);
      Setting.getInstance().setNeedRefresh(2, false);
    }
    while (true)
    {
      if ((localArrayList1 == null) || (bool))
      {
        CommentEngine.getInstance().loadCachedMsgList(getActivity(), User.getInstance().getUID(), this.mCommentType);
        this.mReturnNum[getIndexByType(this.mCommentType)] = MessageCenterModel.getInstance().getReturnNum();
      }
      updateListData();
      if ((this.mCommentList.size() != 0) && (i <= 0))
        break label187;
      if (this.mCommentList.size() == 0)
        showListLoadingProgressBar(true, 0);
      refreshCommentList();
      return;
      localArrayList1 = this.mMessageCenterModel.getReplyCommentList();
      i = this.mMessageCenterModel.getNoticeCnt(6);
      break;
      label168: bool = Setting.getInstance().isNeedRefresh(3);
      Setting.getInstance().setNeedRefresh(3, false);
    }
    label187: showEmptyNotice(false);
    showList(true);
    ArrayList localArrayList2;
    if (this.mCommentType == 3)
      localArrayList2 = this.mMessageCenterModel.getCommentList();
    for (int j = this.mMessageCenterModel.getCommentListTotal(); ; j = this.mMessageCenterModel.getReplyCommentListTotal())
    {
      if ((localArrayList2 != null) && (localArrayList2.size() < j))
        getMoreComment(false);
      if (!bool)
        break;
      refreshCommentList();
      return;
      localArrayList2 = this.mMessageCenterModel.getReplyCommentList();
    }
  }

  private int getIndexByType(int paramInt)
  {
    if (paramInt == 3)
      return 0;
    return 1;
  }

  private void getMoreComment(boolean paramBoolean)
  {
    if ((this.mGetCommentTask != null) && (!this.mGetCommentTask.isCancelled()) && (this.mGetCommentTask.getStatus() != AsyncTask.Status.FINISHED))
      return;
    if (!super.checkNetworkAndHint(paramBoolean))
    {
      showLoadingFooter(false);
      return;
    }
    ArrayList localArrayList;
    if (this.mCommentType == 3)
      localArrayList = this.mMessageCenterModel.getCommentList();
    while (true)
    {
      String[] arrayOfString = new String[2];
      arrayOfString[0] = String.valueOf(this.mCommentType);
      arrayOfString[1] = "";
      if ((localArrayList != null) && (localArrayList.size() > 0));
      try
      {
        CommentItem localCommentItem = (CommentItem)localArrayList.get(-1 + localArrayList.size());
        if (this.mCommentType == 3)
          arrayOfString[1] = localCommentItem.getPosTime();
        while (true)
        {
          showListLoadingProgressBar(true, 1);
          this.rightProBar.setVisibility(0);
          this.btnRight.setVisibility(8);
          this.mGetCommentTask = new GetCommentTask(null);
          this.mGetCommentTask.execute(arrayOfString);
          return;
          localArrayList = this.mMessageCenterModel.getReplyCommentList();
          break;
          arrayOfString[1] = localCommentItem.getCtime().toString();
        }
      }
      catch (Exception localException)
      {
        while (true)
          localException.printStackTrace();
      }
    }
  }

  private void getUnreadNums()
  {
    if (this.mNewMsgCount == null)
      this.mNewMsgCount = new int[2];
    this.mNewMsgCount[0] = this.mMessageCenterModel.getNoticeCnt(3);
    this.mNewMsgCount[1] = this.mMessageCenterModel.getNoticeCnt(6);
  }

  private void initCommentType()
  {
    int i = 3;
    if ((this.mNewMsgCount != null) && (this.mNewMsgCount.length > 1) && (this.mNewMsgCount[0] <= 0))
      if (this.mNewMsgCount[1] > 0)
        i = 6;
    for (this.mCommentType = i; ; this.mCommentType = i)
    {
      this.mMessageCenterModel.setActiveCommentType(this.mCommentType);
      return;
    }
  }

  private void refreshCommentList()
  {
    if (!super.checkNetworkAndHint(true))
      return;
    cancelTask();
    showEmptyNotice(false);
    showListLoadingProgressBar(true, 1);
    if (this.mCommentType == 3);
    for (int i = 3; ; i = 6)
    {
      this.mGetCommentTask = new GetCommentTask(null);
      String[] arrayOfString = new String[2];
      arrayOfString[0] = String.valueOf(i);
      arrayOfString[1] = "";
      this.mGetCommentTask.execute(arrayOfString);
      return;
    }
  }

  private void setTitleText()
  {
    if (this.mCenterText == null)
      this.mCenterText = ((TextView)getView().findViewById(2131362920));
    this.mCenterText.setVisibility(0);
    if (this.mCommentType == 3)
    {
      this.mCenterText.setText(getResources().getText(2131427574));
      return;
    }
    this.mCenterText.setText(getResources().getText(2131427575));
  }

  private void showEmptyNotice(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.mEmptyNoticeView.setVisibility(0);
      return;
    }
    this.mEmptyNoticeView.setVisibility(8);
  }

  private void showList(boolean paramBoolean)
  {
    if (this.mListView == null)
      this.mListView = ((ListView)getView().findViewById(2131363062));
    if (paramBoolean)
    {
      this.mListView.setVisibility(0);
      return;
    }
    this.mListView.setVisibility(8);
  }

  private void showListLoadingProgressBar(boolean paramBoolean, int paramInt)
  {
    View localView = getView().findViewById(2131362490);
    if (paramBoolean)
    {
      if (paramInt == 0)
      {
        localView.setVisibility(0);
        return;
      }
      this.rightProBar.setVisibility(0);
      this.btnRight.setVisibility(8);
      return;
    }
    if (paramInt == 0)
    {
      localView.setVisibility(8);
      return;
    }
    this.rightProBar.setVisibility(8);
    this.btnRight.setVisibility(0);
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

  public void beforeTabChanged(int paramInt)
  {
    this.scrollbarPositions[paramInt] = this.mListView.getFirstVisiblePosition();
    if (this.mNewMsgCount[paramInt] > 0)
    {
      this.mNewMsgCount[paramInt] = 0;
      this.mTabHost.clean();
      setTab(getView());
    }
  }

  public void finish()
  {
    super.finish();
    AnimationUtil.finishActivity(getActivity(), 2);
  }

  protected void getAndGotoDetail(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt1, int paramInt2, String paramString5, String paramString6, String paramString7)
  {
    assert ((paramString7 != null) && (paramString5 != null) && (paramString6 != null));
    Intent localIntent = new Intent(getActivity(), MessageDetailFragment.class);
    localIntent.putExtra("id", paramString1);
    localIntent.putExtra("fuid", paramString2);
    localIntent.putExtra("fname", paramString3);
    localIntent.putExtra("mtype", paramString4);
    localIntent.putExtra("count", paramInt1);
    localIntent.putExtra("mode", 2);
    localIntent.putExtra("newcount", paramInt2);
    localIntent.putExtra("apphtml", paramString5);
    localIntent.putExtra("appname", paramString6);
    localIntent.putExtra("appid", paramString7);
    if (this.mCommentType == 3)
      localIntent.putExtra("type", 203);
    while (true)
    {
      startActivityForResult(localIntent, 501);
      return;
      localIntent.putExtra("type", 204);
    }
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    int i;
    int j;
    if ((paramInt2 == -1) && (paramInt1 == 501))
    {
      i = Integer.parseInt(paramIntent.getStringExtra("count"));
      j = Integer.parseInt(paramIntent.getStringExtra("newcount"));
      if ((i != 0) && (this.mClickPosition > -1))
      {
        if (this.mCommentType != 3)
          break label133;
        if ((this.mMessageCenterModel.getCommentList().size() > this.mClickPosition) && (this.mClickPosition >= 0))
        {
          CommentItem localCommentItem2 = (CommentItem)this.mMessageCenterModel.getCommentList().get(this.mClickPosition);
          localCommentItem2.setCnum(i);
          localCommentItem2.setUnread(j);
        }
      }
    }
    while (true)
    {
      this.mAdapter.updateHolderView(i, j);
      return;
      label133: if ((this.mMessageCenterModel.getReplyCommentList().size() <= this.mClickPosition) || (this.mClickPosition < 0))
        continue;
      CommentItem localCommentItem1 = (CommentItem)this.mMessageCenterModel.getReplyCommentList().get(this.mClickPosition);
      localCommentItem1.setCnum(i);
      localCommentItem1.setUnread(j);
    }
  }

  public void onClick(KXLinkInfo paramKXLinkInfo)
  {
    if ((paramKXLinkInfo.isStar()) || (paramKXLinkInfo.isUserName()))
      IntentUtil.showHomeFragment(this, paramKXLinkInfo.getId(), paramKXLinkInfo.getContent());
    do
      return;
    while (!paramKXLinkInfo.isUrlLink());
    IntentUtil.showWebPage(getActivity(), this, paramKXLinkInfo.getId(), null);
  }

  public void onCreate(Bundle paramBundle)
  {
    this.mMessageCenterModel = MessageCenterModel.getInstance();
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
    cancelTask();
    this.mCommentList.clear();
    super.onDestroy();
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    if (this.mCommentList == null);
    do
      return;
    while (paramInt == this.mCommentList.size());
    while (true)
    {
      CommentItem localCommentItem;
      try
      {
        this.mClickPosition = paramInt;
        this.mClickTotalMsgNum = ((TextView)paramView.findViewById(2131362116));
        this.mClickNewMsgNum = ((TextView)paramView.findViewById(2131362117));
        localCommentItem = (CommentItem)this.mCommentList.get(paramInt);
        String str1 = localCommentItem.getThread_cid();
        if (this.mCommentType == 3)
        {
          str2 = User.getInstance().getUID();
          if (this.mCommentType != 3)
            break label179;
          localObject = getString(2131427565);
          String str4 = String.valueOf(localCommentItem.getMtype());
          String str5 = localCommentItem.getApphtml();
          String str6 = localCommentItem.getAppname();
          getAndGotoDetail(str1, str2, (String)localObject, str4, localCommentItem.getCnum(), localCommentItem.getUnread(), str5, str6, localCommentItem.getAppId());
          return;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return;
      }
      String str2 = localCommentItem.getFuid();
      continue;
      label179: String str3 = localCommentItem.getFname();
      Object localObject = str3;
    }
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
      refreshCommentList();
      return true;
    case 411:
      if (this.mListView == null)
        this.mListView = ((ListView)getView().findViewById(2131363062));
      if (this.mListView.getVisibility() == 0)
        this.mListView.setSelection(0);
      return true;
    case 412:
      IntentUtil.returnToDesktop(getActivity());
      return true;
    case 413:
    }
    IntentUtil.showMyHomeFragment(this);
    return true;
  }

  public void onTabChanged(int paramInt)
  {
    cancelTask();
    int i;
    if (paramInt == 0)
    {
      i = 3;
      this.mCommentType = i;
      this.mMessageCenterModel.setActiveCommentType(this.mCommentType);
      if (this.mCommentType != 3)
        break label93;
      this.mEmptyNoticeView.setText(2131427581);
      label44: setTitleText();
      if (this.mMessageCenterModel.getNoticeCnt(this.mCommentType) <= 0)
        break label106;
      doGetListData();
    }
    while (true)
    {
      if (this.scrollbarPositions != null)
        this.mListView.setSelection(this.scrollbarPositions[paramInt]);
      return;
      i = 6;
      break;
      label93: this.mEmptyNoticeView.setText(2131427582);
      break label44;
      label106: updateListData();
      if (this.mCommentList.size() != 0)
        continue;
      doGetListData();
    }
  }

  public void onUpdate()
  {
    refreshCommentList();
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    if (this.scrollbarPositions == null)
    {
      this.scrollbarPositions = new int[2];
      this.scrollbarPositions[0] = 0;
      this.scrollbarPositions[1] = 0;
    }
    this.mReturnNum[0] = -1;
    this.mReturnNum[1] = -1;
    getUnreadNums();
    initCommentType();
    Bundle localBundle = getArguments();
    if (localBundle != null)
    {
      if (localBundle.getBoolean("comment"))
      {
        this.mCommentType = 3;
        this.mMessageCenterModel.setActiveCommentType(this.mCommentType);
      }
    }
    else
    {
      setTitleBar(paramView);
      setTab(paramView);
      this.mFooterView = getActivity().getLayoutInflater().inflate(2130903259, null);
      this.mFooterView.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          ArrayList localArrayList;
          int i;
          if (CommentListFragment.this.mCommentType == 3)
          {
            localArrayList = CommentListFragment.this.mMessageCenterModel.getCommentList();
            i = CommentListFragment.this.mMessageCenterModel.getCommentListTotal();
            if ((localArrayList != null) && (localArrayList.size() >= CommentListFragment.this.mCommentList.size()))
              break label112;
            CommentListFragment.this.showLoadingFooter(true);
          }
          for (boolean bool = true; ; bool = false)
          {
            if ((localArrayList == null) || (localArrayList.size() >= i))
              break label125;
            CommentListFragment.this.getMoreComment(bool);
            return;
            localArrayList = CommentListFragment.this.mMessageCenterModel.getReplyCommentList();
            i = CommentListFragment.this.mMessageCenterModel.getReplyCommentListTotal();
            break;
            label112: CommentListFragment.this.updateListData();
          }
          label125: CommentListFragment.this.updateListData();
        }
      });
      this.mFooterTV = ((TextView)this.mFooterView.findViewById(2131362073));
      this.mFooterTV.setText(2131427748);
      this.mFooterProBar = ((ProgressBar)this.mFooterView.findViewById(2131362072));
      this.mFooterProBar.setVisibility(4);
      this.mListView = ((ListView)paramView.findViewById(2131363062));
      this.mListView.setAdapter(this.mAdapter);
      this.mListView.setOnItemClickListener(this);
      this.mEmptyNoticeView = ((TextView)paramView.findViewById(2131363036));
      if (this.mCommentType != 3)
        break label287;
      this.mEmptyNoticeView.setText(2131427581);
    }
    while (true)
    {
      doGetListData();
      this.mPullView = ((PullToRefreshView)paramView.findViewById(2131362488));
      this.mPullView.setPullToRefreshListener(this);
      return;
      this.mCommentType = 6;
      break;
      label287: this.mEmptyNoticeView.setText(2131427582);
    }
  }

  protected void setTab(View paramView)
  {
    this.mTabHost = ((KXTopTabHost)paramView.findViewById(2131363061));
    KXTopTab localKXTopTab1 = new KXTopTab(getActivity());
    localKXTopTab1.setText(2131427539);
    this.mTabHost.addTab(localKXTopTab1);
    KXTopTab localKXTopTab2 = new KXTopTab(getActivity());
    localKXTopTab2.setText(2131427540);
    this.mTabHost.addTab(localKXTopTab2);
    if (this.mCommentType == 3)
      this.mTabHost.setCurrentTab(0);
    while (true)
    {
      this.mTabHost.setOnTabChangeListener(this);
      return;
      this.mTabHost.setCurrentTab(1);
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
        CommentListFragment.this.finish();
      }
    });
    this.btnRight = ((ImageView)paramView.findViewById(2131362928));
    this.btnRight.setImageResource(2130838834);
    this.btnRight.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        CommentListFragment.this.refreshCommentList();
      }
    });
    this.rightProBar = ((ProgressBar)paramView.findViewById(2131362929));
    ((ImageView)paramView.findViewById(2131362919)).setVisibility(8);
    setTitleText();
  }

  protected void updateListData()
  {
    this.mCommentList.clear();
    this.mCommentType = this.mMessageCenterModel.getActiveCommentType();
    ArrayList localArrayList;
    if (this.mCommentType == 3)
      localArrayList = this.mMessageCenterModel.getCommentList();
    for (int i = this.mMessageCenterModel.getCommentListTotal(); ; i = this.mMessageCenterModel.getReplyCommentListTotal())
    {
      if ((localArrayList != null) && (localArrayList.size() > 0))
      {
        this.mCommentList.addAll(localArrayList);
        showList(true);
        showEmptyNotice(false);
      }
      if ((localArrayList != null) && (localArrayList.size() < i) && (this.mReturnNum[getIndexByType(this.mCommentType)] != 0))
      {
        CommentItem localCommentItem = new CommentItem();
        localCommentItem.setCtime(Long.valueOf(-1L));
        this.mCommentList.add(localCommentItem);
      }
      if (this.mAdapter != null)
        this.mAdapter.notifyDataSetChanged();
      return;
      localArrayList = this.mMessageCenterModel.getReplyCommentList();
    }
  }

  private class CommentAdapter extends BaseAdapter
  {
    public CommentAdapter()
    {
    }

    public int getCount()
    {
      if (CommentListFragment.this.mCommentList != null)
        return CommentListFragment.this.mCommentList.size();
      return 0;
    }

    public Object getItem(int paramInt)
    {
      if ((paramInt < 0) || (paramInt >= CommentListFragment.this.mCommentList.size()))
        return null;
      return CommentListFragment.this.mCommentList.get(paramInt);
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      CommentItem localCommentItem = (CommentItem)getItem(paramInt);
      if (localCommentItem == null)
        return paramView;
      if (localCommentItem.getCtime().longValue() == -1L)
      {
        CommentListFragment.this.mFooterTV.setVisibility(0);
        CommentListFragment.this.mFooterView.setVisibility(0);
        return CommentListFragment.this.mFooterView;
      }
      if ((paramView == null) || (CommentListFragment.this.mFooterView == paramView))
        paramView = CommentListFragment.this.getActivity().getLayoutInflater().inflate(2130903086, null);
      try
      {
        long l1 = localCommentItem.getCtime().longValue();
        String str1 = localCommentItem.getFlogo();
        str2 = localCommentItem.getFname();
        str3 = localCommentItem.getFuid();
        int i = localCommentItem.getMtype();
        String str4 = localCommentItem.getAbscont();
        int j = localCommentItem.getCnum();
        int k = localCommentItem.getUnread();
        str5 = localCommentItem.getAppname();
        str6 = localCommentItem.getApphtml();
        str7 = localCommentItem.getFuid_last();
        str8 = localCommentItem.getFname_last();
        boolean bool = TextUtils.isEmpty(str7);
        if (!bool)
          try
          {
            long l2 = Long.parseLong(localCommentItem.getCtime_last());
            l1 = l2;
            str4 = localCommentItem.getAbscont_last();
            str9 = MessageCenterModel.formatTimestamp(1000L * l1);
            ImageView localImageView = (ImageView)paramView.findViewById(2131362109);
            CommentListFragment.this.displayPicture(localImageView, str1, 2130838676);
            CommentListFragment.FNameOnClickListener localFNameOnClickListener = new CommentListFragment.FNameOnClickListener(CommentListFragment.this, str7, str8);
            localImageView.setOnClickListener(localFNameOnClickListener);
            localTextView1 = (TextView)paramView.findViewById(2131362110);
            localTextView2 = (TextView)paramView.findViewById(2131362113);
            localTextView3 = (TextView)paramView.findViewById(2131362111);
            if (i == 1)
            {
              localTextView1.setVisibility(0);
              localTextView2.setVisibility(8);
              localTextView3.setVisibility(0);
              localTextView3.setText(str9);
              KXIntroView localKXIntroView1 = (KXIntroView)paramView.findViewById(2131362112);
              localKXIntroView1.setTitleList(ParseNewsInfoUtil.parseNewsLinkString(KXTextUtil.getUserText(str8, str7, false)));
              1 local1 = new KXIntroView.OnClickLinkListener()
              {
                public void onClick(KXLinkInfo paramKXLinkInfo)
                {
                  String str1 = paramKXLinkInfo.getId();
                  String str2 = paramKXLinkInfo.getContent();
                  IntentUtil.showHomeFragment(CommentListFragment.this, str1, str2);
                }
              };
              localKXIntroView1.setOnClickLinkListener(local1);
              KXIntroView localKXIntroView2 = (KXIntroView)paramView.findViewById(2131362114);
              localKXIntroView2.setTitleList(localCommentItem.makeTitleList(str4));
              localKXIntroView2.setOnClickLinkListener(CommentListFragment.this);
              localTextView4 = (TextView)paramView.findViewById(2131362116);
              localTextView5 = (TextView)paramView.findViewById(2131362117);
              if (j <= 1)
                break label729;
              String str10 = StringUtil.replaceTokenWith(CommentListFragment.this.getResources().getString(2131427548), "*", String.valueOf(j));
              String str11 = StringUtil.replaceTokenWith(CommentListFragment.this.getResources().getString(2131427549), "*", String.valueOf(k));
              if (k <= 0)
                break label719;
              str10 = str10 + ",";
              localTextView5.setTextColor(CommentListFragment.this.getResources().getColor(2130839389));
              localTextView5.setVisibility(0);
              localTextView4.setText(str10);
              localTextView5.setText(str11);
              localTextView4.setVisibility(0);
              TextView localTextView6 = (TextView)paramView.findViewById(2131362118);
              if ((CommentListFragment.this.mCommentType != 3) && (!User.getInstance().getUID().equals(str3)))
                break label746;
              localObject = CommentListFragment.this.getResources().getString(2131427554) + str5 + '"' + str6.trim() + '"';
              localTextView6.setText((CharSequence)localObject);
            }
          }
          catch (Exception localException2)
          {
            while (true)
              localException2.printStackTrace();
          }
      }
      catch (Exception localException1)
      {
        while (true)
        {
          String str2;
          String str3;
          String str5;
          String str6;
          String str9;
          TextView localTextView1;
          TextView localTextView2;
          TextView localTextView3;
          TextView localTextView4;
          TextView localTextView5;
          localException1.printStackTrace();
          break;
          String str7 = str3;
          String str8 = str2;
          continue;
          localTextView1.setVisibility(8);
          localTextView3.setVisibility(8);
          localTextView2.setVisibility(0);
          localTextView2.setText(str9);
          continue;
          label719: localTextView5.setVisibility(8);
          continue;
          label729: localTextView4.setVisibility(8);
          localTextView5.setVisibility(8);
          continue;
          label746: String str12 = StringUtil.replaceTokenWith(CommentListFragment.this.getResources().getString(2131427555), "*", str2) + str5 + '"' + str6.trim() + '"';
          Object localObject = str12;
        }
      }
      return (View)paramView;
    }

    public void updateHolderView(int paramInt1, int paramInt2)
    {
      String str1 = StringUtil.replaceTokenWith(CommentListFragment.this.getResources().getString(2131427548), "*", paramInt1);
      CommentListFragment.this.mClickTotalMsgNum.setText(str1);
      String str2 = StringUtil.replaceTokenWith(CommentListFragment.this.getResources().getString(2131427549), "*", paramInt2);
      if (paramInt2 == 0)
      {
        CommentListFragment.this.mClickNewMsgNum.setVisibility(8);
        return;
      }
      CommentListFragment.this.mClickNewMsgNum.setText(str2);
    }
  }

  private class FNameOnClickListener
    implements View.OnClickListener
  {
    private String fname;
    private String fuid;

    public FNameOnClickListener(String paramString1, String arg3)
    {
      this.fuid = paramString1;
      Object localObject;
      this.fname = localObject;
    }

    public void onClick(View paramView)
    {
      if ((this.fuid != null) && (this.fname != null))
        IntentUtil.showHomeFragment(CommentListFragment.this, this.fuid, this.fname);
    }
  }

  private class GetCommentTask extends KXFragment.KXAsyncTask<String, Void, Boolean>
  {
    private String mBefore = null;
    private int mCommentType;

    private GetCommentTask()
    {
      super();
    }

    protected Boolean doInBackgroundA(String[] paramArrayOfString)
    {
      if (paramArrayOfString.length < 2)
        return Boolean.valueOf(false);
      Boolean.valueOf(false);
      this.mCommentType = Integer.parseInt(paramArrayOfString[0]);
      this.mBefore = paramArrayOfString[1];
      try
      {
        if (this.mCommentType == 3)
          return Boolean.valueOf(CommentEngine.getInstance().doGetComment(CommentListFragment.this.getActivity().getApplicationContext(), paramArrayOfString[1]));
        Boolean localBoolean = Boolean.valueOf(CommentEngine.getInstance().doGetReplyComment(CommentListFragment.this.getActivity().getApplicationContext(), paramArrayOfString[1]));
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
        CommentListFragment.this.finish();
        return;
      }
      while (true)
      {
        try
        {
          CommentListFragment.this.showListLoadingProgressBar(false, 0);
          CommentListFragment.this.showListLoadingProgressBar(false, 1);
          if (!paramBoolean.booleanValue())
            break label370;
          if (this.mCommentType == 3)
          {
            localArrayList = CommentListFragment.this.mMessageCenterModel.getCommentList();
            i = CommentListFragment.this.mMessageCenterModel.getCommentListTotal();
            CommentListFragment.this.mReturnNum[CommentListFragment.this.getIndexByType(this.mCommentType)] = CommentListFragment.this.mMessageCenterModel.getReturnNum();
            if ((!TextUtils.isEmpty(this.mBefore)) && (CommentListFragment.this.mCommentList.size() != 0) && (CommentListFragment.this.mFooterProBar.getVisibility() != 0))
              continue;
            CommentListFragment.this.updateListData();
            if (!TextUtils.isEmpty(this.mBefore))
              continue;
            if (CommentListFragment.this.mListView != null)
              continue;
            CommentListFragment.this.mListView = ((ListView)CommentListFragment.this.getView().findViewById(2131363062));
            if (CommentListFragment.this.mListView == null)
              continue;
            CommentListFragment.this.mListView.setSelection(0);
            if ((localArrayList == null) || (localArrayList.size() >= i) || (CommentListFragment.this.mReturnNum[CommentListFragment.this.getIndexByType(this.mCommentType)] == 0))
              continue;
            CommentListFragment.this.mGetCommentTask = null;
            CommentListFragment.this.getMoreComment(false);
            CommentListFragment.this.showLoadingFooter(false);
            if (CommentListFragment.this.mCommentList.size() != 0)
              break label351;
            CommentListFragment.this.showEmptyNotice(true);
            CommentListFragment.this.showList(false);
            if (!CommentListFragment.this.mPullView.isFrefrshing())
              break;
            CommentListFragment.this.mPullView.onRefreshComplete();
            return;
          }
        }
        catch (Exception localException)
        {
          KXLog.e("CommentListActivity", "onPostExecute", localException);
          return;
        }
        ArrayList localArrayList = CommentListFragment.this.mMessageCenterModel.getReplyCommentList();
        int i = CommentListFragment.this.mMessageCenterModel.getReplyCommentListTotal();
        continue;
        label351: CommentListFragment.this.showEmptyNotice(false);
        CommentListFragment.this.showList(true);
        continue;
        label370: if (!TextUtils.isEmpty(this.mBefore))
          continue;
        Toast.makeText(CommentListFragment.this.getActivity().getApplicationContext(), 2131427374, 0).show();
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
 * Qualified Name:     com.kaixin001.fragment.CommentListFragment
 * JD-Core Version:    0.6.0
 */