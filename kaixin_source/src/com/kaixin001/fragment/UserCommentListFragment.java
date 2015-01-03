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
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.engine.UserCommentEngine;
import com.kaixin001.item.KXLinkInfo;
import com.kaixin001.item.UserCommentItem;
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

public class UserCommentListFragment extends BaseFragment
  implements AdapterView.OnItemClickListener, KXIntroView.OnClickLinkListener, KXTopTabHost.OnTabChangeListener, PullToRefreshView.PullToRefreshListener
{
  protected static final int MENU_HOME_ID = 412;
  protected static final int MENU_HOME_ME_ID = 413;
  protected static final int MENU_REFRESH_ID = 410;
  protected static final int MENU_TO_TOP_ID = 411;
  private static final String TAG = "UserCommentListActivity";
  private ImageView btnRight;
  private UserCommentAdapter mAdapter = new UserCommentAdapter();
  TextView mCenterText;
  private int mClickPosition = -1;
  private TextView mClickTotalMsgNum;
  private ProgressBar mFooterProBar;
  private TextView mFooterTV;
  private View mFooterView;
  private GetUserCommentTask mGetUserCommentTask = null;
  ListView mListView;
  MessageCenterModel mMessageCenterModel = MessageCenterModel.getInstance();
  private int[] mNewMsgCount;
  private PullToRefreshView mPullView = null;
  private KXTopTabHost mTabHost;
  private ArrayList<UserCommentItem> mUserCommentList = null;
  private int mUserCommentType;
  private ProgressBar rightProBar;
  private int[] scrollbarPositions = null;

  private void cancelTasks()
  {
    if ((this.mGetUserCommentTask != null) && (!this.mGetUserCommentTask.isCancelled()) && (this.mGetUserCommentTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.mGetUserCommentTask.cancel(true);
      this.mGetUserCommentTask = null;
      UserCommentEngine.getInstance().cancel();
    }
  }

  private void doGetListData()
  {
    this.mUserCommentType = this.mMessageCenterModel.getActiveUserCommentType();
    int i = this.mMessageCenterModel.getNoticeCnt(this.mUserCommentType);
    ArrayList localArrayList1;
    boolean bool;
    if (this.mUserCommentType == 2)
    {
      localArrayList1 = this.mMessageCenterModel.getUserCommentList();
      if (this.mUserCommentType != 2)
        break label148;
      bool = Setting.getInstance().isNeedRefresh(4);
      Setting.getInstance().setNeedRefresh(4, false);
    }
    while (true)
    {
      if ((localArrayList1 == null) || (localArrayList1.size() == 0) || (bool))
        UserCommentEngine.getInstance().loadCacheFile(getActivity().getApplicationContext(), User.getInstance().getUID(), this.mUserCommentType);
      updateListData();
      if ((this.mUserCommentList != null) && (this.mUserCommentList.size() != 0) && (i <= 0))
        break label167;
      showListLoadingProgressBar(true, 0);
      refreshData();
      return;
      localArrayList1 = this.mMessageCenterModel.getSentUserCommentList();
      break;
      label148: bool = Setting.getInstance().isNeedRefresh(5);
      Setting.getInstance().setNeedRefresh(5, false);
    }
    label167: showEmptyNotice(false);
    showList(true);
    ArrayList localArrayList2;
    if (this.mUserCommentType == 2)
      localArrayList2 = this.mMessageCenterModel.getUserCommentList();
    for (int j = this.mMessageCenterModel.getUserCommentTotal(); ; j = this.mMessageCenterModel.getSentUserCommentTotal())
    {
      if ((localArrayList2 != null) && (localArrayList2.size() < j))
        getMoreUserComment(false);
      if (!bool)
        break;
      refreshData();
      return;
      localArrayList2 = this.mMessageCenterModel.getSentUserCommentList();
    }
  }

  private void getMoreUserComment(boolean paramBoolean)
  {
    ArrayList localArrayList;
    int i;
    if (this.mUserCommentType == 2)
    {
      localArrayList = this.mMessageCenterModel.getUserCommentList();
      i = this.mMessageCenterModel.getUserCommentTotal();
      if ((localArrayList != null) && (localArrayList.size() != 0))
        break label55;
    }
    label55: 
    do
    {
      return;
      localArrayList = this.mMessageCenterModel.getSentUserCommentList();
      i = this.mMessageCenterModel.getSentUserCommentTotal();
      break;
    }
    while ((i == localArrayList.size()) || ((this.mGetUserCommentTask != null) && (!this.mGetUserCommentTask.isCancelled()) && (this.mGetUserCommentTask.getStatus() != AsyncTask.Status.FINISHED)));
    if (!super.checkNetworkAndHint(paramBoolean))
    {
      showLoadingFooter(false);
      return;
    }
    showListLoadingProgressBar(true, 1);
    this.rightProBar.setVisibility(0);
    this.btnRight.setImageResource(0);
    this.mFooterTV.setTextColor(getResources().getColor(2130839395));
    String[] arrayOfString = new String[2];
    arrayOfString[0] = String.valueOf(this.mUserCommentType);
    if (this.mUserCommentType == 2)
      arrayOfString[1] = ((UserCommentItem)localArrayList.get(-1 + localArrayList.size())).getPosTime();
    while (true)
    {
      this.mGetUserCommentTask = new GetUserCommentTask(null);
      this.mGetUserCommentTask.execute(arrayOfString);
      return;
      arrayOfString[1] = ((UserCommentItem)localArrayList.get(-1 + localArrayList.size())).getCtime_last();
    }
  }

  private void getUnreadNums()
  {
    if (this.mNewMsgCount == null)
      this.mNewMsgCount = new int[2];
    this.mNewMsgCount[0] = this.mMessageCenterModel.getNoticeCnt(2);
    this.mNewMsgCount[1] = this.mMessageCenterModel.getNoticeCnt(5);
  }

  private void initUserCommentType()
  {
    int i = 2;
    if ((this.mNewMsgCount != null) && (this.mNewMsgCount.length > 1) && (this.mNewMsgCount[0] <= 0))
      if (this.mNewMsgCount[1] > 0)
        i = 5;
    for (this.mUserCommentType = i; ; this.mUserCommentType = i)
    {
      this.mMessageCenterModel.setActiveUserCommentType(this.mUserCommentType);
      return;
    }
  }

  private void refreshData()
  {
    if (!super.checkNetworkAndHint(true))
      return;
    showEmptyNotice(false);
    if (this.mUserCommentList == null)
    {
      showList(false);
      showListLoadingProgressBar(true, 0);
      if (this.mUserCommentType != 2)
        break label101;
    }
    label101: for (int i = 2; ; i = 5)
    {
      cancelTasks();
      this.mGetUserCommentTask = new GetUserCommentTask(null);
      String[] arrayOfString = new String[2];
      arrayOfString[0] = String.valueOf(i);
      arrayOfString[1] = "";
      this.mGetUserCommentTask.execute(arrayOfString);
      return;
      showList(true);
      showListLoadingProgressBar(true, 1);
      break;
    }
  }

  private void setTitleText()
  {
    this.mCenterText = ((TextView)getView().findViewById(2131362920));
    this.mCenterText.setVisibility(0);
    if (this.mUserCommentType == 2)
    {
      this.mCenterText.setText(getResources().getText(2131427576));
      return;
    }
    this.mCenterText.setText(getResources().getText(2131427577));
  }

  private void showEmptyNotice(boolean paramBoolean)
  {
    TextView localTextView = (TextView)getView().findViewById(2131363036);
    if (paramBoolean)
    {
      localTextView.setVisibility(0);
      return;
    }
    localTextView.setVisibility(8);
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
      this.btnRight.setImageResource(0);
      return;
    }
    if (paramInt == 0)
    {
      localView.setVisibility(8);
      return;
    }
    this.rightProBar.setVisibility(8);
    this.btnRight.setImageResource(2130838834);
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

  protected void getAndGotoDetail(String paramString1, String paramString2, int paramInt1, int paramInt2)
  {
    Intent localIntent = new Intent(getActivity(), MessageDetailFragment.class);
    localIntent.putExtra("id", paramString1);
    localIntent.putExtra("mtype", paramString2);
    localIntent.putExtra("count", paramInt1);
    localIntent.putExtra("newcount", paramInt2);
    if (this.mUserCommentType == 2)
      localIntent.putExtra("type", 205);
    while (true)
    {
      startActivityForResult(localIntent, 501);
      return;
      localIntent.putExtra("type", 206);
    }
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    int i;
    int j;
    if (this.mClickPosition >= 0)
    {
      if (this.mUserCommentType == 2)
        ((UserCommentItem)this.mMessageCenterModel.getUserCommentList().get(this.mClickPosition)).setUnread(0);
    }
    else if ((paramInt2 == -1) && (paramInt1 == 501))
    {
      i = Integer.parseInt(paramIntent.getStringExtra("comment_count"));
      if (i != 0)
      {
        if (this.mUserCommentType != 2)
          break label150;
        UserCommentItem localUserCommentItem2 = (UserCommentItem)this.mMessageCenterModel.getUserCommentList().get(this.mClickPosition);
        j = i + localUserCommentItem2.getCnum();
        localUserCommentItem2.setCnum(j);
      }
    }
    while (true)
    {
      this.mAdapter.updateHolderView(j);
      return;
      ((UserCommentItem)this.mMessageCenterModel.getSentUserCommentList().get(this.mClickPosition)).setUnread(0);
      break;
      label150: UserCommentItem localUserCommentItem1 = (UserCommentItem)this.mMessageCenterModel.getSentUserCommentList().get(this.mClickPosition);
      j = i + localUserCommentItem1.getCnum();
      localUserCommentItem1.setCnum(j);
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
    cancelTasks();
    super.onDestroy();
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    if (this.mUserCommentList == null);
    do
      return;
    while (paramInt == this.mUserCommentList.size());
    try
    {
      this.mClickPosition = paramInt;
      this.mClickTotalMsgNum = ((TextView)paramView.findViewById(2131363875));
      UserCommentItem localUserCommentItem = (UserCommentItem)this.mUserCommentList.get(paramInt);
      getAndGotoDetail(localUserCommentItem.getThread_cid(), String.valueOf(localUserCommentItem.getMtype()), localUserCommentItem.getCnum(), localUserCommentItem.getUnread());
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
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
      refreshData();
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

  public void onStart()
  {
    super.onStart();
    this.mAdapter.notifyDataSetChanged();
  }

  public void onTabChanged(int paramInt)
  {
    cancelTasks();
    int i;
    TextView localTextView;
    if (paramInt == 0)
    {
      i = 2;
      this.mUserCommentType = i;
      this.mMessageCenterModel.setActiveUserCommentType(this.mUserCommentType);
      localTextView = (TextView)getView().findViewById(2131363036);
      if (this.mUserCommentType != 2)
        break label89;
      localTextView.setText(2131427583);
    }
    while (true)
    {
      setTitleText();
      doGetListData();
      if (this.scrollbarPositions != null)
        this.mListView.setSelection(this.scrollbarPositions[paramInt]);
      return;
      i = 5;
      break;
      label89: localTextView.setText(2131427584);
    }
  }

  public void onUpdate()
  {
    refreshData();
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
    getUnreadNums();
    initUserCommentType();
    Bundle localBundle = getArguments();
    TextView localTextView;
    if (localBundle != null)
    {
      if (localBundle.getBoolean("receivecomment"))
      {
        this.mUserCommentType = 2;
        this.mMessageCenterModel.setActiveUserCommentType(this.mUserCommentType);
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
          boolean bool;
          if (UserCommentListFragment.this.mUserCommentType == 2)
          {
            localArrayList = UserCommentListFragment.this.mMessageCenterModel.getUserCommentList();
            i = UserCommentListFragment.this.mMessageCenterModel.getUserCommentTotal();
            if ((localArrayList != null) && (localArrayList.size() >= UserCommentListFragment.this.mUserCommentList.size()))
              break label112;
            bool = true;
            UserCommentListFragment.this.showLoadingFooter(true);
          }
          while (true)
          {
            if ((localArrayList == null) || (localArrayList.size() >= i))
              break label125;
            UserCommentListFragment.this.getMoreUserComment(bool);
            return;
            localArrayList = UserCommentListFragment.this.mMessageCenterModel.getSentUserCommentList();
            i = UserCommentListFragment.this.mMessageCenterModel.getSentUserCommentTotal();
            break;
            label112: UserCommentListFragment.this.updateListData();
            bool = false;
          }
          label125: UserCommentListFragment.this.updateListData();
        }
      });
      this.mFooterTV = ((TextView)this.mFooterView.findViewById(2131362073));
      this.mFooterTV.setText(2131427748);
      this.mFooterProBar = ((ProgressBar)this.mFooterView.findViewById(2131362072));
      this.mFooterProBar.setVisibility(4);
      this.mListView = ((ListView)paramView.findViewById(2131363062));
      this.mListView.setAdapter(this.mAdapter);
      this.mListView.setOnItemClickListener(this);
      localTextView = (TextView)paramView.findViewById(2131363036);
      if (this.mUserCommentType != 2)
        break label268;
      localTextView.setText(2131427583);
    }
    while (true)
    {
      doGetListData();
      this.mPullView = ((PullToRefreshView)paramView.findViewById(2131362488));
      this.mPullView.setPullToRefreshListener(this);
      return;
      this.mUserCommentType = 5;
      break;
      label268: localTextView.setText(2131427584);
    }
  }

  protected void setTab(View paramView)
  {
    this.mTabHost = ((KXTopTabHost)paramView.findViewById(2131363061));
    KXTopTab localKXTopTab1 = new KXTopTab(getActivity());
    localKXTopTab1.setText(2131427541);
    this.mTabHost.addTab(localKXTopTab1);
    KXTopTab localKXTopTab2 = new KXTopTab(getActivity());
    localKXTopTab2.setText(2131427542);
    this.mTabHost.addTab(localKXTopTab2);
    if (this.mUserCommentType == 2)
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
        UserCommentListFragment.this.finish();
      }
    });
    this.btnRight = ((ImageView)paramView.findViewById(2131362928));
    this.btnRight.setImageResource(2130838834);
    this.btnRight.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        UserCommentListFragment.this.refreshData();
      }
    });
    this.rightProBar = ((ProgressBar)paramView.findViewById(2131362929));
    ((ImageView)paramView.findViewById(2131362919)).setVisibility(8);
    setTitleText();
  }

  protected void updateListData()
  {
    ArrayList localArrayList;
    if (this.mUserCommentList == null)
    {
      this.mUserCommentList = new ArrayList();
      this.mUserCommentType = this.mMessageCenterModel.getActiveUserCommentType();
      if (this.mUserCommentType != 2)
        break label129;
      localArrayList = this.mMessageCenterModel.getUserCommentList();
    }
    for (int i = this.mMessageCenterModel.getUserCommentTotal(); ; i = this.mMessageCenterModel.getSentUserCommentTotal())
    {
      if (localArrayList != null)
      {
        this.mUserCommentList.addAll(localArrayList);
        if (localArrayList.size() < i)
        {
          UserCommentItem localUserCommentItem = new UserCommentItem();
          localUserCommentItem.setCtime(Long.valueOf(-1L));
          this.mUserCommentList.add(localUserCommentItem);
        }
      }
      if (this.mAdapter != null)
        this.mAdapter.notifyDataSetChanged();
      return;
      this.mUserCommentList.clear();
      break;
      label129: localArrayList = this.mMessageCenterModel.getSentUserCommentList();
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
        IntentUtil.showHomeFragment(UserCommentListFragment.this, this.fuid, this.fname);
    }
  }

  private class GetUserCommentTask extends KXFragment.KXAsyncTask<String, Void, Boolean>
  {
    private String mBefore = null;
    private int mUserCommentType;

    private GetUserCommentTask()
    {
      super();
    }

    protected Boolean doInBackgroundA(String[] paramArrayOfString)
    {
      Object localObject;
      if (paramArrayOfString.length < 2)
      {
        localObject = Boolean.valueOf(false);
        return localObject;
      }
      Boolean.valueOf(false);
      this.mUserCommentType = Integer.parseInt(paramArrayOfString[0]);
      this.mBefore = paramArrayOfString[1];
      while (true)
      {
        try
        {
          if (this.mUserCommentType == 2)
          {
            localObject = Boolean.valueOf(UserCommentEngine.getInstance().doGetUserComment(UserCommentListFragment.this.getActivity().getApplicationContext(), this.mBefore));
            if (!((Boolean)localObject).booleanValue())
              break;
            MessageCenterModel.getInstance().getReturnNum();
            return localObject;
          }
        }
        catch (SecurityErrorException localSecurityErrorException)
        {
          return Boolean.valueOf(false);
        }
        Boolean localBoolean = Boolean.valueOf(UserCommentEngine.getInstance().doGetSentUserComment(UserCommentListFragment.this.getActivity().getApplicationContext(), this.mBefore));
        localObject = localBoolean;
      }
    }

    protected void onPostExecuteA(Boolean paramBoolean)
    {
      while (true)
      {
        try
        {
          UserCommentListFragment.this.showListLoadingProgressBar(false, 0);
          UserCommentListFragment.this.showListLoadingProgressBar(false, 1);
          if (paramBoolean.booleanValue())
          {
            if (this.mUserCommentType != 2)
              continue;
            ArrayList localArrayList = UserCommentListFragment.this.mMessageCenterModel.getUserCommentList();
            int i = UserCommentListFragment.this.mMessageCenterModel.getUserCommentTotal();
            if ((!TextUtils.isEmpty(this.mBefore)) && (UserCommentListFragment.this.mUserCommentList.size() != 0) && (UserCommentListFragment.this.mFooterProBar.getVisibility() != 0))
              continue;
            UserCommentListFragment.this.updateListData();
            if (!TextUtils.isEmpty(this.mBefore))
              continue;
            if (UserCommentListFragment.this.mListView != null)
              continue;
            UserCommentListFragment.this.mListView = ((ListView)UserCommentListFragment.this.getView().findViewById(2131363062));
            if (UserCommentListFragment.this.mListView == null)
              continue;
            UserCommentListFragment.this.mListView.setSelection(0);
            if ((localArrayList == null) || (localArrayList.size() >= i))
              continue;
            UserCommentListFragment.this.mGetUserCommentTask = null;
            UserCommentListFragment.this.getMoreUserComment(false);
            UserCommentListFragment.this.showLoadingFooter(false);
            if (UserCommentListFragment.this.mUserCommentList.size() != 0)
              continue;
            UserCommentListFragment.this.showEmptyNotice(true);
            UserCommentListFragment.this.showList(false);
            if (!UserCommentListFragment.this.mPullView.isFrefrshing())
              break;
            UserCommentListFragment.this.mPullView.onRefreshComplete();
            return;
            localArrayList = UserCommentListFragment.this.mMessageCenterModel.getSentUserCommentList();
            i = UserCommentListFragment.this.mMessageCenterModel.getSentUserCommentTotal();
            continue;
            UserCommentListFragment.this.showEmptyNotice(false);
            UserCommentListFragment.this.showList(true);
            continue;
          }
        }
        catch (Exception localException)
        {
          KXLog.e("UserCommentListActivity", "onPostExecute", localException);
          return;
        }
        if (!TextUtils.isEmpty(this.mBefore))
          continue;
        Toast.makeText(UserCommentListFragment.this.getActivity(), 2131427547, 0).show();
      }
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private class UserCommentAdapter extends BaseAdapter
  {
    public UserCommentAdapter()
    {
    }

    public int getCount()
    {
      if (UserCommentListFragment.this.mUserCommentList != null)
        return UserCommentListFragment.this.mUserCommentList.size();
      return 0;
    }

    public Object getItem(int paramInt)
    {
      if (UserCommentListFragment.this.mUserCommentList == null);
      do
        return null;
      while ((paramInt < 0) || (paramInt >= UserCommentListFragment.this.mUserCommentList.size()));
      return UserCommentListFragment.this.mUserCommentList.get(paramInt);
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      UserCommentItem localUserCommentItem = (UserCommentItem)getItem(paramInt);
      if (localUserCommentItem == null)
        return paramView;
      if (localUserCommentItem.getCtime().longValue() == -1L)
      {
        UserCommentListFragment.this.mFooterTV.setTextColor(UserCommentListFragment.this.getResources().getColor(2130839419));
        return UserCommentListFragment.this.mFooterView;
      }
      if (paramView != null)
      {
        View localView = UserCommentListFragment.this.mFooterView;
        if (paramView != localView);
      }
      else
      {
        paramView = UserCommentListFragment.this.getActivity().getLayoutInflater().inflate(2130903392, paramViewGroup, false);
        ViewHolder localViewHolder1 = new ViewHolder(null);
        localViewHolder1.avatarView = ((ImageView)paramView.findViewById(2131363868));
        localViewHolder1.mtypeView = ((TextView)paramView.findViewById(2131363869));
        localViewHolder1.fnameView = ((KXIntroView)paramView.findViewById(2131363871));
        localViewHolder1.timeView = ((TextView)paramView.findViewById(2131363872));
        localViewHolder1.timeView2 = ((TextView)paramView.findViewById(2131363870));
        localViewHolder1.contView = ((KXIntroView)paramView.findViewById(2131363873));
        localViewHolder1.msgnumDesView = ((TextView)paramView.findViewById(2131363875));
        localViewHolder1.unreadDesView = ((TextView)paramView.findViewById(2131363876));
        localViewHolder1.whereView = ((TextView)paramView.findViewById(2131363877));
        paramView.setTag(localViewHolder1);
      }
      try
      {
        ViewHolder localViewHolder2 = (ViewHolder)paramView.getTag();
        long l = localUserCommentItem.getCtime().longValue();
        String str1 = localUserCommentItem.getFlogo();
        String str2 = localUserCommentItem.getFname();
        str3 = str2;
        Object localObject = localUserCommentItem.getFuid();
        int i = localUserCommentItem.getMtype();
        String str4 = localUserCommentItem.getAbscont();
        int j = localUserCommentItem.getCnum();
        int k = localUserCommentItem.getUnread();
        String str5 = localUserCommentItem.getFuid_last();
        if ((str5 != null) && (str5.length() > 0))
        {
          localObject = str5;
          str2 = localUserCommentItem.getFname_last();
          l = Long.parseLong(localUserCommentItem.getCtime_last());
          str4 = localUserCommentItem.getAbscont_last();
        }
        String str6 = MessageCenterModel.formatTimestamp(1000L * l);
        ImageView localImageView = localViewHolder2.avatarView;
        UserCommentListFragment.this.displayPicture(localImageView, str1, 2130838676);
        UserCommentListFragment.FNameOnClickListener localFNameOnClickListener = new UserCommentListFragment.FNameOnClickListener(UserCommentListFragment.this, (String)localObject, str2);
        localImageView.setOnClickListener(localFNameOnClickListener);
        if (i == 1)
        {
          localViewHolder2.mtypeView.setVisibility(0);
          localViewHolder2.timeView.setVisibility(8);
          localViewHolder2.timeView2.setVisibility(0);
          localViewHolder2.timeView2.setText(str6);
        }
        while (true)
        {
          KXIntroView localKXIntroView1 = localViewHolder2.fnameView;
          localKXIntroView1.setText(str2);
          localKXIntroView1.setTitleList(ParseNewsInfoUtil.parseNewsLinkString(KXTextUtil.getUserText(str2, (String)localObject, false)));
          1 local1 = new KXIntroView.OnClickLinkListener()
          {
            public void onClick(KXLinkInfo paramKXLinkInfo)
            {
              String str1 = paramKXLinkInfo.getId();
              String str2 = paramKXLinkInfo.getContent();
              IntentUtil.showHomeFragment(UserCommentListFragment.this, str1, str2);
            }
          };
          localKXIntroView1.setOnClickLinkListener(local1);
          KXIntroView localKXIntroView2 = localViewHolder2.contView;
          localKXIntroView2.setTitleList(localUserCommentItem.makeTitleList(str4));
          localKXIntroView2.setOnClickLinkListener(UserCommentListFragment.this);
          localTextView1 = localViewHolder2.msgnumDesView;
          localTextView2 = localViewHolder2.unreadDesView;
          if (j <= 1)
            break label770;
          String str7 = StringUtil.replaceTokenWith(UserCommentListFragment.this.getResources().getString(2131427548), "*", String.valueOf(j));
          String str8 = StringUtil.replaceTokenWith(UserCommentListFragment.this.getResources().getString(2131427549), "*", String.valueOf(k));
          if (k <= 0)
            break;
          str7 = str7 + ",";
          localTextView2.setTextColor(UserCommentListFragment.this.getResources().getColor(2130839389));
          localTextView2.setVisibility(0);
          localTextView1.setText(str7);
          localTextView2.setText(str8);
          localTextView1.setVisibility(0);
          localTextView3 = localViewHolder2.whereView;
          if (UserCommentListFragment.this.mUserCommentType != 2)
            break label787;
          localTextView3.setVisibility(8);
          break label819;
          localViewHolder2.mtypeView.setVisibility(8);
          localViewHolder2.timeView2.setVisibility(8);
          localViewHolder2.timeView.setVisibility(0);
          localViewHolder2.timeView.setText(str6);
        }
      }
      catch (Exception localException)
      {
        String str3;
        TextView localTextView3;
        while (true)
        {
          TextView localTextView1;
          TextView localTextView2;
          localException.printStackTrace();
          break;
          localTextView2.setVisibility(8);
          continue;
          label770: localTextView1.setVisibility(8);
          localTextView2.setVisibility(8);
        }
        label787: localTextView3.setText(StringUtil.replaceTokenWith(UserCommentListFragment.this.getResources().getString(2131427556), "*", str3));
        localTextView3.setVisibility(0);
      }
      label819: return (View)paramView;
    }

    public void updateHolderView(int paramInt)
    {
      String str = StringUtil.replaceTokenWith(UserCommentListFragment.this.getResources().getString(2131427548), "*", paramInt);
      UserCommentListFragment.this.mClickTotalMsgNum.setText(str);
    }

    private class ViewHolder
    {
      public ImageView avatarView;
      public KXIntroView contView;
      public KXIntroView fnameView;
      public TextView msgnumDesView;
      public TextView mtypeView;
      public TextView timeView;
      public TextView timeView2;
      public TextView unreadDesView;
      public TextView whereView;

      private ViewHolder()
      {
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.UserCommentListFragment
 * JD-Core Version:    0.6.0
 */