package com.kaixin001.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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
import com.kaixin001.engine.CircleMsgEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.item.CircleMsgItem;
import com.kaixin001.item.CircleMsgItem.CircleReplyContent;
import com.kaixin001.item.KXLinkInfo;
import com.kaixin001.model.CircleMessageModel;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class CircleMessageFragment extends BaseFragment
  implements AdapterView.OnItemClickListener, KXIntroView.OnClickLinkListener, KXTopTabHost.OnTabChangeListener, PullToRefreshView.PullToRefreshListener
{
  protected static final int MENU_HOME_ID = 412;
  protected static final int MENU_HOME_ME_ID = 413;
  protected static final int MENU_REFRESH_ID = 410;
  protected static final int MENU_TO_TOP_ID = 411;
  private static final int TAB_NOTICE_MAX_MESSAGE_COUNT = 99;
  private String AT = "[|s|]@[|m|]10066329[|m|]-101[|e|]";
  CircleMessageModel circleMessageModel = null;
  private int[] focusedItemPositions = null;
  private boolean fromDetail = false;
  private final CircleMsgListAdapter mAdapter = new CircleMsgListAdapter();
  private int[] mBeroreTabChangedListCount = null;
  private final ArrayList<CircleMsgItem> mCircleMsgList = new ArrayList();
  private KXTopTabHost mCircleTabHost;
  private int mClickListPosition = -1;
  private TextView mEmptyMessageView;
  private ProgressBar mFooterProBar;
  private TextView mFooterTV;
  private View mFooterView;
  private int[] mNewCircleMsgCount = null;
  private PullToRefreshView mPullView = null;
  ListView mReplyList;
  private int mReplyType;
  private int mTabIndex;
  TextView mTitleText = null;
  private GetCircleReplyTask replyTask = null;
  private ImageView rightBtn;
  private ProgressBar rightProBar;

  private void cancelTask()
  {
    if ((this.replyTask != null) && (!this.replyTask.isCancelled()) && (this.replyTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.replyTask.cancel(true);
      this.replyTask = null;
      if (CircleMsgEngine.getInstance() != null)
        CircleMsgEngine.getInstance().cancel();
    }
  }

  private void getMoreReply(boolean paramBoolean)
  {
    if ((this.replyTask != null) && (!this.replyTask.isCancelled()) && (this.replyTask.getStatus() != AsyncTask.Status.FINISHED))
      return;
    if (!super.checkNetworkAndHint(paramBoolean))
    {
      showLoadingFooter(false);
      return;
    }
    Integer[] arrayOfInteger = new Integer[2];
    arrayOfInteger[1] = Integer.valueOf(0);
    ArrayList localArrayList;
    if (this.mReplyType == 10)
    {
      localArrayList = this.circleMessageModel.getReplyMeList();
      arrayOfInteger[0] = Integer.valueOf(10);
    }
    while (true)
    {
      if ((localArrayList != null) && (localArrayList.size() > 0))
        arrayOfInteger[1] = Integer.valueOf(localArrayList.size());
      showListLoadingProgressBar(true, 1);
      this.rightProBar.setVisibility(0);
      this.rightBtn.setVisibility(8);
      this.replyTask = new GetCircleReplyTask(null);
      this.replyTask.execute(arrayOfInteger);
      return;
      localArrayList = this.circleMessageModel.getMeReplyList();
      arrayOfInteger[0] = Integer.valueOf(11);
    }
  }

  private void getNewCircleMsgNums()
  {
    if (this.mNewCircleMsgCount == null)
      this.mNewCircleMsgCount = new int[2];
    this.mNewCircleMsgCount[0] = this.circleMessageModel.getReplyMeMsgCnt();
    this.mNewCircleMsgCount[1] = this.circleMessageModel.getMeReplyMsgCnt();
  }

  private void initReplyList()
  {
    this.mReplyType = this.circleMessageModel.getActiveCircleReplyType();
    ArrayList localArrayList1;
    int i;
    if (this.mReplyType == 10)
    {
      this.mTabIndex = 0;
      localArrayList1 = this.circleMessageModel.getReplyMeList();
      i = this.circleMessageModel.getReplyMeMsgCnt();
      if ((localArrayList1 != null) && ((localArrayList1 == null) || (localArrayList1.size() != 0)))
        break label130;
      CircleMsgEngine.getInstance().loadCachedMsgList(getActivity(), User.getInstance().getUID(), this.mReplyType);
      label77: updateList();
      if ((this.mCircleMsgList.size() != 0) && (i <= 0))
        break label172;
      showListLoadingProgressBar(true, 0);
      refreshDataList();
    }
    while (true)
    {
      return;
      this.mTabIndex = 1;
      localArrayList1 = this.circleMessageModel.getMeReplyList();
      i = this.circleMessageModel.getMeReplyMsgCnt();
      break;
      label130: if ((this.mBeroreTabChangedListCount[this.mTabIndex] <= 0) || (this.mBeroreTabChangedListCount[this.mTabIndex] >= localArrayList1.size()))
        break label77;
      this.circleMessageModel.resetReplyList(this.mReplyType);
      break label77;
      label172: showEmptyNotice(false);
      showListLoadingProgressBar(false, 1);
      showList(true);
      ArrayList localArrayList2;
      if (this.mReplyType == 10)
        localArrayList2 = this.circleMessageModel.getReplyMeList();
      for (int j = this.circleMessageModel.getReplyMeListTotal(); (localArrayList2 != null) && (localArrayList2.size() < j); j = this.circleMessageModel.getMeReplyListTotal())
      {
        getMoreReply(false);
        return;
        localArrayList2 = this.circleMessageModel.getMeReplyList();
      }
    }
  }

  private void refreshDataList()
  {
    if (!super.checkNetworkAndHint(true))
      return;
    cancelTask();
    showEmptyNotice(false);
    showListLoadingProgressBar(true, 1);
    this.replyTask = new GetCircleReplyTask(null);
    Integer[] arrayOfInteger = new Integer[2];
    if (this.mReplyType == 10)
      arrayOfInteger[0] = Integer.valueOf(10);
    while (true)
    {
      arrayOfInteger[1] = Integer.valueOf(0);
      this.replyTask.execute(arrayOfInteger);
      return;
      arrayOfInteger[0] = Integer.valueOf(11);
    }
  }

  private void selectTabButton()
  {
    if ((this.mNewCircleMsgCount[0] <= 0) && (this.mNewCircleMsgCount[1] > 0))
    {
      this.mReplyType = 11;
      this.mTabIndex = 1;
      this.mCircleTabHost.setCurrentTab(1);
    }
    while (true)
    {
      this.circleMessageModel.setActiveCircleReplyType(this.mReplyType);
      return;
      this.mTabIndex = 0;
      this.mReplyType = 10;
      this.mCircleTabHost.setCurrentTab(0);
    }
  }

  private void setFooter()
  {
    this.mFooterView = getActivity().getLayoutInflater().inflate(2130903259, null);
    this.mFooterView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        ArrayList localArrayList;
        int i;
        if (CircleMessageFragment.this.mReplyType == 10)
        {
          localArrayList = CircleMessageFragment.this.circleMessageModel.getReplyMeList();
          i = CircleMessageFragment.this.circleMessageModel.getReplyMeListTotal();
          if ((localArrayList != null) && (localArrayList.size() >= CircleMessageFragment.this.mCircleMsgList.size()))
            break label113;
          CircleMessageFragment.this.showLoadingFooter(true);
        }
        for (boolean bool = true; ; bool = false)
        {
          if ((localArrayList == null) || (localArrayList.size() >= i))
            break label126;
          CircleMessageFragment.this.getMoreReply(bool);
          return;
          localArrayList = CircleMessageFragment.this.circleMessageModel.getMeReplyList();
          i = CircleMessageFragment.this.circleMessageModel.getMeReplyListTotal();
          break;
          label113: CircleMessageFragment.this.updateList();
        }
        label126: CircleMessageFragment.this.updateList();
      }
    });
    this.mFooterTV = ((TextView)this.mFooterView.findViewById(2131362073));
    this.mFooterTV.setText(2131427748);
    this.mFooterProBar = ((ProgressBar)this.mFooterView.findViewById(2131362072));
    this.mFooterProBar.setVisibility(4);
  }

  private void setTabHost(View paramView)
  {
    this.mCircleTabHost = ((KXTopTabHost)paramView.findViewById(2131363061));
    KXTopTab localKXTopTab1 = new KXTopTab(getActivity());
    localKXTopTab1.setText(2131428063);
    this.mCircleTabHost.addTab(localKXTopTab1);
    KXTopTab localKXTopTab2 = new KXTopTab(getActivity());
    localKXTopTab2.setText(2131428064);
    this.mCircleTabHost.addTab(localKXTopTab2);
    selectTabButton();
    this.mCircleTabHost.setOnTabChangeListener(this);
  }

  private void setTitleBar(View paramView)
  {
    ImageView localImageView = (ImageView)paramView.findViewById(2131362914);
    localImageView.setVisibility(0);
    localImageView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        CircleMessageFragment.this.finish();
      }
    });
    this.rightBtn = ((ImageView)paramView.findViewById(2131362928));
    this.rightBtn.setImageResource(2130838834);
    this.rightBtn.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        CircleMessageFragment.this.refreshDataList();
      }
    });
    this.rightProBar = ((ProgressBar)paramView.findViewById(2131362929));
    ((ImageView)paramView.findViewById(2131362919)).setVisibility(8);
    if (this.mTitleText == null)
      this.mTitleText = ((TextView)paramView.findViewById(2131362920));
    this.mTitleText.setVisibility(0);
    this.mTitleText.setText(2131428062);
  }

  private void showEmptyNotice(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      if (this.mReplyType == 10)
        this.mEmptyMessageView.setText(2131428065);
      while (true)
      {
        this.mEmptyMessageView.setVisibility(0);
        return;
        this.mEmptyMessageView.setText(2131428066);
      }
    }
    this.mEmptyMessageView.setVisibility(8);
  }

  private void showList(boolean paramBoolean)
  {
    if (this.mReplyList == null)
      this.mReplyList = ((ListView)getView().findViewById(2131363062));
    if (paramBoolean)
    {
      this.mReplyList.setVisibility(0);
      return;
    }
    this.mReplyList.setVisibility(8);
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
      this.rightBtn.setVisibility(8);
      return;
    }
    if (paramInt == 0)
    {
      localView.setVisibility(8);
      return;
    }
    this.rightProBar.setVisibility(8);
    this.rightBtn.setVisibility(0);
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
    cancelTask();
    this.focusedItemPositions[paramInt] = this.mReplyList.getFirstVisiblePosition();
    if ((this.mCircleMsgList.size() > 0) && (((CircleMsgItem)this.mCircleMsgList.get(-1 + this.mCircleMsgList.size())).ctime.longValue() == -1L))
    {
      this.mBeroreTabChangedListCount[paramInt] = (-1 + this.mCircleMsgList.size());
      if (this.mNewCircleMsgCount[paramInt] > 0)
      {
        this.mNewCircleMsgCount[paramInt] = 0;
        this.mCircleTabHost.clean();
        if (this.mReplyType != 10)
          break label139;
        this.circleMessageModel.resetReplyMeMsgCnt();
      }
    }
    while (true)
    {
      setTabHost(getView());
      return;
      this.mBeroreTabChangedListCount[paramInt] = this.mCircleMsgList.size();
      break;
      label139: this.circleMessageModel.resetMeReplyMsgCnt();
    }
  }

  public void finish()
  {
    super.finish();
    AnimationUtil.finishActivity(getActivity(), 2);
  }

  public String formatTimestamp(long paramLong)
  {
    Calendar localCalendar1 = Calendar.getInstance();
    int i = localCalendar1.get(1);
    int j = localCalendar1.get(6);
    Calendar localCalendar2 = Calendar.getInstance();
    localCalendar2.setTimeInMillis(paramLong);
    int k = localCalendar2.get(1);
    int m = localCalendar2.get(6);
    String str;
    if (k != i)
      str = "yyyy年MM月dd日 HH:mm";
    while (true)
    {
      Date localDate = new Date(paramLong);
      return new SimpleDateFormat(str).format(localDate);
      if (m != j)
      {
        str = "MM月dd日 HH:mm";
        continue;
      }
      str = "HH:mm";
    }
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if (paramInt2 == -1)
    {
      Bundle localBundle = paramIntent.getExtras();
      if (localBundle != null)
      {
        this.fromDetail = true;
        int i = localBundle.getInt("position");
        CircleMsgItem localCircleMsgItem = (CircleMsgItem)this.mCircleMsgList.get(i);
        localCircleMsgItem.fname_last = localBundle.getString("name");
        localCircleMsgItem.logo120_last = localBundle.getString("logo");
        localCircleMsgItem.ctime = Long.valueOf(localBundle.getLong("ctime"));
        localCircleMsgItem.reserved = localBundle.getString("subject");
        localCircleMsgItem.totalCount = localBundle.getInt("totalcount");
        localCircleMsgItem.quitSession = localBundle.getInt("quitsession");
        if (this.mAdapter != null)
          this.mAdapter.notifyDataSetChanged();
        this.mReplyList.setSelection(i);
      }
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
    this.circleMessageModel = CircleMessageModel.getInstance();
    super.onCreate(paramBundle);
    if (CrashRecoverUtil.isCrashBefore())
    {
      CrashRecoverUtil.crashRecover(getActivity().getApplicationContext());
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
    this.mCircleMsgList.clear();
    int i = this.circleMessageModel.getReplyMeListTotal();
    ArrayList localArrayList = this.circleMessageModel.getReplyMeList();
    if ((localArrayList != null) && (localArrayList.size() > 0) && (localArrayList.size() < i))
      this.circleMessageModel.resetReplyList(10);
    super.onDestroy();
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    if (this.mCircleMsgList == null);
    CircleMsgItem localCircleMsgItem;
    do
    {
      do
        return;
      while (paramInt == this.mCircleMsgList.size());
      this.mClickListPosition = paramInt;
      localCircleMsgItem = (CircleMsgItem)this.mCircleMsgList.get(this.mClickListPosition);
    }
    while ((localCircleMsgItem.gid == null) || (localCircleMsgItem.stid == null));
    Intent localIntent = new Intent(getActivity(), CircleMessageDetailFragment.class);
    localIntent.putExtra("tid", localCircleMsgItem.stid);
    localIntent.putExtra("gid", localCircleMsgItem.gid);
    localIntent.putExtra("gname", localCircleMsgItem.circleName);
    localIntent.putExtra("count", localCircleMsgItem.totalCount);
    localIntent.putExtra("mode", 2);
    localIntent.putExtra("quitsession", localCircleMsgItem.quitSession);
    if (this.mReplyType == 10)
    {
      localIntent.putExtra("type", 207);
      localIntent.putExtra("appid", "回复我");
    }
    while (true)
    {
      localIntent.putExtra("listtitle", localCircleMsgItem.detailInfo);
      int i = 1;
      if ((localCircleMsgItem.talk_type == 1) || (localCircleMsgItem.talk_type == 2))
        i = 0;
      localIntent.putExtra("canjump", i);
      localIntent.putExtra("userrole", localCircleMsgItem.circleUserRole);
      localIntent.putExtra("position", this.mClickListPosition);
      startActivityForResult(localIntent, 501);
      return;
      localIntent.putExtra("type", 208);
      localIntent.putExtra("appid", "我回复");
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
      refreshDataList();
      return true;
    case 411:
      if (this.mReplyList == null)
        this.mReplyList = ((ListView)getView().findViewById(2131363062));
      if (this.mReplyList.getVisibility() == 0)
        this.mReplyList.setSelection(0);
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
    if (paramInt == 0)
      this.mReplyType = 10;
    for (this.mTabIndex = 0; ; this.mTabIndex = 1)
    {
      this.circleMessageModel.setActiveCircleReplyType(this.mReplyType);
      initReplyList();
      if (this.focusedItemPositions != null)
        this.mReplyList.setSelection(this.focusedItemPositions[paramInt]);
      return;
      this.mReplyType = 11;
    }
  }

  public void onUpdate()
  {
    refreshDataList();
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    if (this.focusedItemPositions == null)
    {
      this.focusedItemPositions = new int[2];
      this.focusedItemPositions[0] = 0;
      this.focusedItemPositions[1] = 0;
    }
    if (this.mBeroreTabChangedListCount == null)
    {
      this.mBeroreTabChangedListCount = new int[2];
      this.mBeroreTabChangedListCount[0] = 0;
      this.mBeroreTabChangedListCount[1] = 0;
    }
    this.circleMessageModel.setNewCicleMsgNums();
    getNewCircleMsgNums();
    setTitleBar(paramView);
    setTabHost(paramView);
    setFooter();
    this.mReplyList = ((ListView)paramView.findViewById(2131363062));
    this.mReplyList.setAdapter(this.mAdapter);
    this.mReplyList.setOnItemClickListener(this);
    this.mEmptyMessageView = ((TextView)paramView.findViewById(2131363036));
    initReplyList();
    this.mPullView = ((PullToRefreshView)paramView.findViewById(2131362488));
    this.mPullView.setPullToRefreshListener(this);
  }

  public String processTextForAt(String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    HashMap localHashMap = new HashMap();
    if (paramString.length() == 0)
      return null;
    int i = 0;
    int j = paramString.indexOf("@", i);
    label44: Iterator localIterator;
    if (j < 0)
      localIterator = localArrayList.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
      {
        return paramString;
        int k = paramString.indexOf("#)", i);
        if ((k < 0) || (j >= k))
          break label44;
        String str1 = paramString.substring(j, k + 2);
        String str2 = str1.substring(1 + str1.indexOf("@"), str1.indexOf("(#")).trim();
        String str3 = str1.substring(1 + str1.indexOf("#"), str1.lastIndexOf("#")).trim();
        StringBuffer localStringBuffer = new StringBuffer();
        localStringBuffer.append(this.AT).append("[|s|]").append(str3).append("[|m|]").append(str2).append("[|m|]").append("0").append("[|e|]");
        localArrayList.add(str1);
        localHashMap.put(str1, localStringBuffer.toString());
        i = k + 1;
        break;
      }
      String str4 = (String)localIterator.next();
      paramString = paramString.replace(str4, (String)localHashMap.get(str4));
    }
  }

  protected void updateList()
  {
    this.mCircleMsgList.clear();
    this.mReplyType = this.circleMessageModel.getActiveCircleReplyType();
    ArrayList localArrayList;
    if (this.mReplyType == 10)
      localArrayList = this.circleMessageModel.getReplyMeList();
    for (int i = this.circleMessageModel.getReplyMeListTotal(); ; i = this.circleMessageModel.getMeReplyListTotal())
    {
      if ((localArrayList != null) && (localArrayList.size() > 0))
        this.mCircleMsgList.addAll(localArrayList);
      if ((localArrayList != null) && (localArrayList.size() > 0) && (localArrayList.size() < i))
      {
        CircleMsgItem localCircleMsgItem = new CircleMsgItem();
        localCircleMsgItem.ctime = Long.valueOf(-1L);
        this.mCircleMsgList.add(localCircleMsgItem);
      }
      if (this.mAdapter != null)
        this.mAdapter.notifyDataSetChanged();
      return;
      localArrayList = this.circleMessageModel.getMeReplyList();
    }
  }

  private class CircleMsgListAdapter extends BaseAdapter
  {
    public CircleMsgListAdapter()
    {
    }

    public int getCount()
    {
      if (CircleMessageFragment.this.mCircleMsgList != null)
        return CircleMessageFragment.this.mCircleMsgList.size();
      return 0;
    }

    public Object getItem(int paramInt)
    {
      if ((paramInt < 0) || (paramInt >= CircleMessageFragment.this.mCircleMsgList.size()))
        return null;
      return CircleMessageFragment.this.mCircleMsgList.get(paramInt);
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      CircleMsgItem localCircleMsgItem = (CircleMsgItem)getItem(paramInt);
      if (localCircleMsgItem == null)
        return paramView;
      if (localCircleMsgItem.ctime.longValue() == -1L)
      {
        CircleMessageFragment.this.mFooterTV.setVisibility(0);
        CircleMessageFragment.this.mFooterView.setVisibility(0);
        return CircleMessageFragment.this.mFooterView;
      }
      if ((paramView == null) || (CircleMessageFragment.this.mFooterView == paramView))
        paramView = CircleMessageFragment.this.getActivity().getLayoutInflater().inflate(2130903078, null);
      while (true)
      {
        String str4;
        StringBuilder localStringBuilder1;
        StringBuilder localStringBuilder2;
        int m;
        int n;
        TextView localTextView;
        int k;
        Object localObject;
        CircleMsgItem.CircleReplyContent localCircleReplyContent2;
        try
        {
          long l = localCircleMsgItem.ctime.longValue();
          String str1 = localCircleMsgItem.circleName;
          String str2 = localCircleMsgItem.logo120_last;
          String str3 = localCircleMsgItem.fname_last;
          str4 = localCircleMsgItem.sname;
          localStringBuilder1 = new StringBuilder();
          localStringBuilder2 = new StringBuilder();
          int i = localCircleMsgItem.totalCount;
          String str5 = localCircleMsgItem.fuid_last;
          String str6 = CircleMessageFragment.this.formatTimestamp(1000L * l);
          ImageView localImageView = (ImageView)paramView.findViewById(2131362061);
          CircleMessageFragment.this.displayPicture(localImageView, str2, 2130838676);
          CircleMessageFragment.NameOrLogoOnClickListener localNameOrLogoOnClickListener = new CircleMessageFragment.NameOrLogoOnClickListener(CircleMessageFragment.this, str5, str3);
          localImageView.setOnClickListener(localNameOrLogoOnClickListener);
          KXIntroView localKXIntroView1 = (KXIntroView)paramView.findViewById(2131362063);
          localKXIntroView1.setTitleList(ParseNewsInfoUtil.parseNewsLinkString(KXTextUtil.getUserText(str3, str5, false)));
          1 local1 = new KXIntroView.OnClickLinkListener()
          {
            public void onClick(KXLinkInfo paramKXLinkInfo)
            {
              String str1 = paramKXLinkInfo.getId();
              String str2 = paramKXLinkInfo.getContent();
              IntentUtil.showHomeFragment(CircleMessageFragment.this, str1, str2);
            }
          };
          localKXIntroView1.setOnClickLinkListener(local1);
          ((TextView)paramView.findViewById(2131362064)).setText(str6);
          KXIntroView localKXIntroView2 = (KXIntroView)paramView.findViewById(2131362065);
          if (CircleMessageFragment.this.fromDetail)
            break label696;
          m = localCircleMsgItem.content_last.size();
          n = 0;
          break label923;
          localKXIntroView2.setTitleList(localCircleMsgItem.makeTitleList(localStringBuilder2.toString()));
          localKXIntroView2.setOnClickLinkListener(CircleMessageFragment.this);
          localTextView = (TextView)paramView.findViewById(2131362066);
          if (i <= 1)
            break label718;
          localTextView.setText(StringUtil.replaceTokenWith(CircleMessageFragment.this.getResources().getString(2131427548), "*", String.valueOf(i)));
          localTextView.setVisibility(0);
          KXIntroView localKXIntroView3 = (KXIntroView)paramView.findViewById(2131362068);
          int j = localCircleMsgItem.sContent.size();
          k = 0;
          if (k < j)
            break label728;
          if (CircleMessageFragment.this.mReplyType != 10)
            break label858;
          localObject = CircleMessageFragment.this.getResources().getString(2131427554) + "圈子记录" + '"' + localStringBuilder1.toString() + '"';
          localCircleMsgItem.detailInfo = ((String)localObject);
          localKXIntroView3.setTitleList(ParseNewsInfoUtil.parseNewsLinkString((String)localObject));
          ((TextView)paramView.findViewById(2131362069)).setText(CircleMessageFragment.this.getResources().getString(2131428067) + " " + str1);
          break label933;
          localCircleReplyContent2 = (CircleMsgItem.CircleReplyContent)localCircleMsgItem.content_last.get(n);
          if (localCircleReplyContent2.type != 0)
            continue;
          localStringBuilder2.append(localCircleReplyContent2.txt);
          break label935;
          if (localCircleReplyContent2.type == 2)
            localStringBuilder2.append(localCircleReplyContent2.txt);
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
          break label933;
        }
        if (localCircleReplyContent2.type == 1)
        {
          String str9 = "@" + localCircleReplyContent2.atUid + "(#" + localCircleReplyContent2.atFname + "#)";
          localStringBuilder2.append(CircleMessageFragment.this.processTextForAt(str9));
          break label935;
          label696: CircleMessageFragment.this.fromDetail = false;
          localStringBuilder2.append(localCircleMsgItem.reserved);
          continue;
          label718: localTextView.setVisibility(8);
          continue;
          label728: CircleMsgItem.CircleReplyContent localCircleReplyContent1 = (CircleMsgItem.CircleReplyContent)localCircleMsgItem.sContent.get(k);
          if (localCircleReplyContent1.type == 0)
          {
            localStringBuilder1.append(localCircleReplyContent1.txt);
            break label941;
          }
          if (localCircleReplyContent1.type == 2)
          {
            localStringBuilder1.append(localCircleReplyContent1.txt);
            break label941;
          }
          if (localCircleReplyContent1.type != 1)
            break label941;
          String str8 = "@" + localCircleReplyContent1.atUid + "(#" + localCircleReplyContent1.atFname + "#)";
          localStringBuilder1.append(CircleMessageFragment.this.processTextForAt(str8));
          break label941;
          label858: String str7 = StringUtil.replaceTokenWith(CircleMessageFragment.this.getResources().getString(2131427555), "*", str4) + "圈子记录" + '"' + localStringBuilder1.toString() + '"';
          localObject = str7;
          continue;
        }
        while (true)
        {
          label923: if (n < m)
            break label939;
          break;
          label933: return paramView;
          label935: n++;
        }
        label939: continue;
        label941: k++;
      }
    }
  }

  private class GetCircleReplyTask extends AsyncTask<Integer, Void, Boolean>
  {
    private int mStart;

    private GetCircleReplyTask()
    {
    }

    protected Boolean doInBackground(Integer[] paramArrayOfInteger)
    {
      if (paramArrayOfInteger.length < 2)
        return Boolean.valueOf(false);
      this.mStart = paramArrayOfInteger[1].intValue();
      try
      {
        Boolean localBoolean = Boolean.valueOf(CircleMsgEngine.getInstance().doGetReplyList(CircleMessageFragment.this.getActivity().getApplicationContext(), paramArrayOfInteger[0].intValue(), paramArrayOfInteger[1].intValue()));
        return localBoolean;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
      }
      return null;
    }

    protected void onPostExecute(Boolean paramBoolean)
    {
      if (paramBoolean == null)
      {
        CircleMessageFragment.this.finish();
        return;
      }
      while (true)
      {
        try
        {
          CircleMessageFragment.this.showListLoadingProgressBar(false, 0);
          CircleMessageFragment.this.showListLoadingProgressBar(false, 1);
          if (!paramBoolean.booleanValue())
            break label330;
          if (CircleMessageFragment.this.mReplyType == 10)
          {
            localArrayList = CircleMessageFragment.this.circleMessageModel.getReplyMeList();
            i = CircleMessageFragment.this.circleMessageModel.getReplyMeListTotal();
            if ((this.mStart != 0) && (CircleMessageFragment.this.mCircleMsgList.size() != 0) && (CircleMessageFragment.this.mFooterProBar.getVisibility() != 0))
              continue;
            CircleMessageFragment.this.updateList();
            if (CircleMessageFragment.this.mReplyList != null)
              continue;
            CircleMessageFragment.this.mReplyList = ((ListView)CircleMessageFragment.this.getView().findViewById(2131363062));
            if (CircleMessageFragment.this.mReplyList == null)
              continue;
            CircleMessageFragment.this.mReplyList.setSelection(0);
            if ((localArrayList == null) || (localArrayList.size() >= i))
              continue;
            CircleMessageFragment.this.replyTask = null;
            if ((this.mStart != 0) || (CircleMessageFragment.this.mCircleMsgList.size() <= 0))
              continue;
            CircleMessageFragment.this.getMoreReply(false);
            CircleMessageFragment.this.showLoadingFooter(false);
            if (CircleMessageFragment.this.mCircleMsgList.size() != 0)
              break label311;
            CircleMessageFragment.this.showEmptyNotice(true);
            CircleMessageFragment.this.showList(false);
            if (!CircleMessageFragment.this.mPullView.isFrefrshing())
              break;
            CircleMessageFragment.this.mPullView.onRefreshComplete();
            return;
          }
        }
        catch (Exception localException)
        {
          KXLog.e("CircleMessageActivity", "onPostExecute", localException);
          return;
        }
        ArrayList localArrayList = CircleMessageFragment.this.circleMessageModel.getMeReplyList();
        int i = CircleMessageFragment.this.circleMessageModel.getMeReplyListTotal();
        continue;
        label311: CircleMessageFragment.this.showEmptyNotice(false);
        CircleMessageFragment.this.showList(true);
        continue;
        label330: if ((0 == 0) && (CircleMessageFragment.this.mCircleMsgList.size() == 0))
        {
          CircleMessageFragment.this.showEmptyNotice(true);
          CircleMessageFragment.this.showList(false);
        }
        if (this.mStart != 0)
          continue;
        Toast.makeText(CircleMessageFragment.this.getActivity().getApplicationContext(), 2131427374, 0).show();
      }
    }
  }

  private class NameOrLogoOnClickListener
    implements View.OnClickListener
  {
    private String fname;
    private String fuid;

    public NameOrLogoOnClickListener(String paramString1, String arg3)
    {
      this.fuid = paramString1;
      Object localObject;
      this.fname = localObject;
    }

    public void onClick(View paramView)
    {
      if ((this.fuid != null) && (this.fname != null))
        IntentUtil.showHomeFragment(CircleMessageFragment.this, this.fuid, this.fname);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.CircleMessageFragment
 * JD-Core Version:    0.6.0
 */