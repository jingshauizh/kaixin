package com.kaixin001.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.adapter.RepostListAdapter;
import com.kaixin001.engine.RepostEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.item.RepItem;
import com.kaixin001.model.SharedPostModel;
import com.kaixin001.model.User;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.KXUBLog;
import com.kaixin001.util.UserHabitUtils;
import com.kaixin001.view.KXTopTab;
import com.kaixin001.view.KXTopTabHost;
import com.kaixin001.view.KXTopTabHost.OnTabChangeListener;
import java.util.ArrayList;
import java.util.List;

public class SharedPostFragment extends BaseFragment
  implements View.OnClickListener, KXTopTabHost.OnTabChangeListener, AbsListView.OnScrollListener
{
  private static final int CACHE_DATA_READY = 501;
  protected static final String FLAG_VIEW_MORE = "-1";
  private static final int GET_MORE = 2;
  private static final int MENU_DESKTOP_ID = 403;
  private static final int MENU_HOME_ME_ID = 404;
  private static final int MENU_REFRESH_ID = 401;
  private static final int MENU_TOP_ID = 402;
  private static final int REFRESH = 0;
  private static final int REFRESH_USE_CACHE = 1;
  private static final int TAB_COUNT = 2;
  private static final String TAG = "SharedPostActivity";
  private static int current_tab = 1;
  boolean hasMore = true;
  int lastVisibleIndex = 0;
  protected RepostListAdapter mAdapter;
  protected ImageView mBtnLeft;
  protected ImageView mBtnRight;
  private boolean mCallByDesktop = true;
  private String mFname = null;
  private ProgressBar mFooterProBar;
  private TextView mFooterTV;
  protected View mFooterView;
  private FriendOnClickListener mFriendOnClickListener = new FriendOnClickListener();
  private String mFuid = null;
  protected LoadDataTask mGetDataTask = null;
  protected final int[] mIndexOfTabs = new int[2];
  protected final ArrayList<RepItem> mListRepost = new ArrayList();
  protected ListView mListView;
  private boolean mNeedAutoLoading = false;
  private ProgressBar proBar;
  protected ProgressBar rightProBar;
  protected KXTopTabHost tabHost;
  private TextView tvEmpty = null;
  protected TextView tvTitle;
  protected LinearLayout waitLayout;

  private void getMoreData()
  {
    ArrayList localArrayList = SharedPostModel.getInstance(getCurViewType()).getRepostList();
    int i = SharedPostModel.getInstance(getCurViewType()).getTotal();
    if ((localArrayList == null) || (localArrayList.size() < this.mListRepost.size()))
      showLoadingFooter(true);
    while ((localArrayList != null) && (localArrayList.size() < i) && (localArrayList.size() > 0))
    {
      refreshData(2, true);
      return;
      updateRepostList();
    }
    updateRepostList();
  }

  private void loadMoreData()
  {
    if ((this.mListRepost == null) || (this.mListRepost.size() == 0));
    do
      return;
    while ((TextUtils.isEmpty(((RepItem)this.mListRepost.get(-1 + this.mListRepost.size())).id)) || (!this.hasMore));
    this.mListRepost.add(new RepItem());
    this.mAdapter.notifyDataSetChanged();
    new LoadingMoreTask().execute(new Void[0]);
  }

  private void noFriendSharedPost()
  {
    this.mBtnRight.setEnabled(true);
    this.waitLayout.setVisibility(0);
    if (this.tabHost.getCurrentTab() == 0)
      this.tvEmpty.setText(2131427413);
    while (true)
    {
      this.mListView.setVisibility(8);
      this.rightProBar.setVisibility(8);
      this.proBar.setVisibility(8);
      this.mBtnRight.setImageResource(2130838834);
      return;
      this.tvEmpty.setText(2131427414);
    }
  }

  private void showLoadingFooter(boolean paramBoolean)
  {
    ProgressBar localProgressBar;
    if (this.mFooterProBar != null)
    {
      localProgressBar = this.mFooterProBar;
      if (!paramBoolean)
        break label65;
    }
    label65: for (int j = 0; ; j = 4)
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

  private void tabchangedStatusBar()
  {
    this.proBar.setVisibility(0);
    this.tvEmpty.setText(2131427599);
    this.rightProBar.setVisibility(8);
    this.mBtnRight.setVisibility(0);
  }

  public void beforeTabChanged(int paramInt)
  {
    this.mIndexOfTabs[paramInt] = this.mListView.getFirstVisiblePosition();
    cancelTask();
    setRefreshStatus(false, false);
  }

  protected boolean cancelTask()
  {
    if ((this.mGetDataTask != null) && (this.mGetDataTask.getStatus() != AsyncTask.Status.FINISHED) && (!this.mGetDataTask.isCancelled()))
    {
      this.mGetDataTask.cancel(true);
      this.mGetDataTask = null;
      return true;
    }
    return false;
  }

  protected String getCurViewType()
  {
    if (current_tab == 0)
      return "normal";
    return "hot";
  }

  protected void goHome()
  {
    IntentUtil.returnToDesktop(getActivity());
  }

  protected boolean handleMessage(Message paramMessage)
  {
    if (paramMessage == null)
      return false;
    if (paramMessage.what == 501)
    {
      updateRepostList();
      showWait(false);
      return true;
    }
    return super.handleMessage(paramMessage);
  }

  protected void initListView(View paramView)
  {
    this.mListView = ((ListView)paramView.findViewById(2131361968));
    this.mListView.setOnItemClickListener(new ViewPostDetial(null));
    this.mAdapter = new RepostListAdapter(this, this.mFooterView, this.mListRepost);
    this.mListView.setAdapter(this.mAdapter);
    this.mListView.setOnScrollListener(this);
  }

  protected void initTabHost(View paramView)
  {
    this.tabHost = ((KXTopTabHost)paramView.findViewById(2131361964));
    this.tabHost.setOnTabChangeListener(this);
    KXTopTab localKXTopTab1 = new KXTopTab(getActivity());
    localKXTopTab1.setText(2131427843);
    this.tabHost.addTab(localKXTopTab1);
    KXTopTab localKXTopTab2 = new KXTopTab(getActivity());
    localKXTopTab2.setText(2131427844);
    this.tabHost.addTab(localKXTopTab2);
    this.tabHost.setCurrentTab(current_tab);
  }

  protected void initTitle(View paramView)
  {
    ((ImageView)paramView.findViewById(2131362919)).setVisibility(8);
    this.rightProBar = ((ProgressBar)paramView.findViewById(2131362929));
    this.mBtnRight = ((ImageView)paramView.findViewById(2131362928));
    this.mBtnRight.setImageResource(2130838834);
    this.mBtnRight.setOnClickListener(this);
    this.mBtnLeft = ((ImageView)paramView.findViewById(2131362914));
    this.mBtnLeft.setOnClickListener(this);
    this.tvTitle = ((TextView)paramView.findViewById(2131362920));
    this.tvTitle.setVisibility(0);
    this.tvTitle.setText(2131428352);
  }

  protected boolean isReloadingData()
  {
    return this.rightProBar.getVisibility() == 0;
  }

  public void onClick(View paramView)
  {
    if (paramView.equals(this.mBtnLeft))
      if (getActivity() == null)
        IntentUtil.returnToDesktop(getActivity());
    do
    {
      return;
      if (super.isMenuListVisibleInMain())
      {
        super.showSubActivityInMain();
        return;
      }
      super.showMenuListInMain();
      return;
      if (!paramView.equals(this.mBtnRight))
        continue;
      try
      {
        this.mIndexOfTabs[this.tabHost.getCurrentTab()] = 0;
        refreshData(0, true);
        return;
      }
      catch (Exception localException)
      {
        KXLog.e("SharedPostActivity", "onClick", localException);
        return;
      }
    }
    while (paramView != this.mFooterView);
    getMoreData();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mFuid = User.getInstance().getUID();
    Bundle localBundle = getArguments();
    if (localBundle != null)
    {
      String str = localBundle.getString("fuid");
      if (!TextUtils.isEmpty(str))
        this.mFuid = str;
      this.mFname = localBundle.getString("fname");
      this.mCallByDesktop = false;
    }
    UserHabitUtils.getInstance(getActivity()).addUserHabit("visit_repaste_hot");
    KXUBLog.log("homepage_reprint");
  }

  public void onCreateOptionsMenu(Menu paramMenu, MenuInflater paramMenuInflater)
  {
    paramMenu.add(0, 401, 0, 2131427690).setIcon(2130838607);
    paramMenu.add(0, 402, 0, 2131427698).setIcon(2130838609);
    getActivity().onCreateOptionsMenu(paramMenu);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903238, paramViewGroup, false);
  }

  public void onDestroy()
  {
    try
    {
      cancelTask();
      SharedPostModel.clear("normal");
      SharedPostModel.clear("hot");
      this.mListView.setAdapter(null);
      this.mListRepost.clear();
      super.onDestroy();
      return;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  public void onDestroyView()
  {
    super.onDestroyView();
    current_tab = this.tabHost.getCurrentTab();
    if (current_tab < 0)
      current_tab = 1;
  }

  public void onDetach()
  {
    super.onDetach();
  }

  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default:
      return super.onOptionsItemSelected(paramMenuItem);
    case 401:
      refreshData(0, true);
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

  public void onScroll(AbsListView paramAbsListView, int paramInt1, int paramInt2, int paramInt3)
  {
    this.lastVisibleIndex = (-1 + (paramInt1 + paramInt2));
    int i = -1 + this.mAdapter.getCount();
    KXLog.d("SharedPostActivity", "onScroll lastVisibleIndex:" + this.lastVisibleIndex + ", " + this.mNeedAutoLoading);
    if ((this.lastVisibleIndex >= i) && (i > 0) && (!this.mNeedAutoLoading))
    {
      ArrayList localArrayList = this.mListRepost;
      boolean bool1 = false;
      if (localArrayList != null)
      {
        int j = this.mListRepost.size();
        bool1 = false;
        if (j > 0)
        {
          RepItem localRepItem = (RepItem)this.mListRepost.get(-1 + this.mListRepost.size());
          if (!"-1".equals(localRepItem.id))
          {
            boolean bool2 = TextUtils.isEmpty(localRepItem.id);
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
      if ((this.lastVisibleIndex == -1 + this.mListRepost.size()) && (current_tab == 0))
        loadMoreData();
      return;
    }
    setNotCanLoad();
  }

  public void onTabChanged(int paramInt)
  {
    current_tab = paramInt;
    this.mNeedAutoLoading = false;
    if (paramInt == 1)
      UserHabitUtils.getInstance(getActivity()).addUserHabit("visit_repaste_hot");
    while (true)
    {
      cancelTask();
      tabchangedStatusBar();
      showLoadingFooter(false);
      updateRepostList();
      int i = this.mListRepost.size();
      boolean bool = false;
      if (i == 0)
        bool = true;
      showWait(bool);
      if (this.mListRepost.size() != 0)
        break;
      refreshData(1, true);
      return;
      UserHabitUtils.getInstance(getActivity()).addUserHabit("visit_repaste_friend");
    }
    this.mListView.setSelection(this.mIndexOfTabs[paramInt]);
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    initTitle(paramView);
    initTabHost(paramView);
    this.mFooterView = getActivity().getLayoutInflater().inflate(2130903259, null);
    this.mFooterTV = ((TextView)this.mFooterView.findViewById(2131362073));
    this.mFooterTV.setText(2131427748);
    this.mFooterProBar = ((ProgressBar)this.mFooterView.findViewById(2131362072));
    this.mFooterProBar.setVisibility(0);
    initListView(paramView);
    this.waitLayout = ((LinearLayout)paramView.findViewById(2131361965));
    this.tvEmpty = ((TextView)paramView.findViewById(2131361967));
    this.proBar = ((ProgressBar)paramView.findViewById(2131361966));
    this.mListView.requestFocus();
    updateRepostList();
    if (this.mListRepost.size() == 0);
    for (boolean bool = true; ; bool = false)
    {
      showWait(bool);
      ArrayList localArrayList = SharedPostModel.getInstance(getCurViewType()).getRepostList();
      if ((localArrayList == null) || (localArrayList.size() == 0))
        refreshData(1, true);
      return;
    }
  }

  protected void refreshData(int paramInt, boolean paramBoolean)
  {
    int i = 1;
    if (isReloadingData())
      return;
    if ((paramInt != 0) && (paramInt != i));
    for (int j = 0; ; j = i)
    {
      if ((this.mGetDataTask != null) && (this.mGetDataTask.getStatus() != AsyncTask.Status.FINISHED) && (!this.mGetDataTask.isCancelled()))
      {
        if (j == 0)
          break;
        cancelTask();
      }
      if (super.checkNetworkAndHint(paramBoolean))
        break label82;
      showLoadingFooter(false);
      return;
    }
    label82: if (j != 0)
      SharedPostModel.getInstance(getCurViewType()).clear();
    setRefreshStatus(j, i);
    this.mGetDataTask = new LoadDataTask();
    LoadDataTask localLoadDataTask = this.mGetDataTask;
    if (paramInt == i);
    while (true)
    {
      localLoadDataTask.isUseCacheData = i;
      this.mGetDataTask.isNetworkError = false;
      this.mGetDataTask.isShowTips = paramBoolean;
      this.mGetDataTask.execute(new Void[0]);
      return;
      i = 0;
    }
  }

  protected void setRefreshStatus(boolean paramBoolean1, boolean paramBoolean2)
  {
    if (paramBoolean1)
    {
      if (paramBoolean2)
      {
        this.mBtnRight.setEnabled(false);
        this.rightProBar.setVisibility(0);
        this.mBtnRight.setImageResource(0);
        return;
      }
      this.mBtnRight.setEnabled(true);
      this.rightProBar.setVisibility(8);
      this.mBtnRight.setImageResource(2130838834);
      return;
    }
    ImageView localImageView = this.mBtnRight;
    boolean bool = false;
    if (paramBoolean2);
    while (true)
    {
      localImageView.setEnabled(bool);
      this.rightProBar.setVisibility(8);
      this.mBtnRight.setImageResource(2130838834);
      return;
      bool = true;
    }
  }

  protected void setRightBarEnableFalse()
  {
    this.mBtnRight.setEnabled(false);
  }

  protected void showWait(boolean paramBoolean)
  {
    ListView localListView = this.mListView;
    int i;
    LinearLayout localLinearLayout;
    int j;
    if (paramBoolean)
    {
      i = 8;
      localListView.setVisibility(i);
      localLinearLayout = this.waitLayout;
      j = 0;
      if (!paramBoolean)
        break label43;
    }
    while (true)
    {
      localLinearLayout.setVisibility(j);
      return;
      i = 0;
      break;
      label43: j = 8;
    }
  }

  public void updateRepostList()
  {
    this.mListRepost.clear();
    SharedPostModel localSharedPostModel = SharedPostModel.getInstance(getCurViewType());
    ArrayList localArrayList = localSharedPostModel.getRepostList();
    int i = localSharedPostModel.getTotal();
    if (localArrayList != null)
      this.mListRepost.addAll(localArrayList);
    if ((localArrayList != null) && (localArrayList.size() < i))
    {
      RepItem localRepItem = new RepItem();
      localRepItem.id = "";
      this.mListRepost.add(localRepItem);
    }
    if (this.mAdapter != null)
      this.mAdapter.notifyDataSetChanged();
  }

  protected class FriendOnClickListener
    implements View.OnClickListener
  {
    protected FriendOnClickListener()
    {
    }

    public void onClick(View paramView)
    {
      RepItem localRepItem = (RepItem)paramView.getTag();
      if (localRepItem != null)
        IntentUtil.showHomeFragment(SharedPostFragment.this, localRepItem.fuid, localRepItem.fname, localRepItem.flogo);
    }
  }

  protected class LoadDataTask extends KXFragment.KXAsyncTask<Void, Void, Boolean>
  {
    protected boolean isNetworkError;
    public boolean isShowTips;
    protected boolean isUseCacheData;
    String postId = null;

    protected LoadDataTask()
    {
      super();
    }

    public void cancel()
    {
      cancel(true);
      RepostEngine.getInstance().cancel();
    }

    protected Boolean doInBackgroundA(Void[] paramArrayOfVoid)
    {
      try
      {
        if ((!this.isUseCacheData) && (SharedPostFragment.this.mListRepost != null) && (SharedPostFragment.this.mListRepost.size() > 0))
        {
          ArrayList localArrayList = SharedPostModel.getInstance(SharedPostFragment.this.getCurViewType()).getRepostList();
          if ((localArrayList != null) && (localArrayList.size() > 0))
            this.postId = ((RepItem)localArrayList.get(-1 + localArrayList.size())).id;
        }
        if ((this.isUseCacheData) && (RepostEngine.getInstance().loadRepostCache(SharedPostFragment.this.getActivity(), this.postId, SharedPostFragment.this.getCurViewType(), SharedPostFragment.this.mFuid)))
          SharedPostFragment.this.mHandler.sendEmptyMessage(501);
        if (this.isNetworkError)
        {
          SharedPostFragment.this.hasMore = false;
          return Boolean.valueOf(false);
        }
        Boolean localBoolean = RepostEngine.getInstance().doGetRepostList(SharedPostFragment.this.getActivity(), this.postId, SharedPostFragment.this.getCurViewType(), Integer.valueOf(10), SharedPostFragment.this.mFuid);
        return localBoolean;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
      }
      return null;
    }

    protected void onPostExecuteA(Boolean paramBoolean)
    {
      if ((SharedPostFragment.this.getView() == null) || (SharedPostFragment.this.getActivity() == null));
      while (true)
      {
        return;
        if (paramBoolean == null)
        {
          SharedPostFragment.this.noFriendSharedPost();
          SharedPostFragment.this.mGetDataTask = null;
          return;
        }
        SharedPostFragment.this.setRefreshStatus(this.isUseCacheData, false);
        if (paramBoolean.booleanValue())
          break;
        if ((this.isShowTips) && (this.isNetworkError));
        for (int j = 1; j == 0; j = 0)
        {
          Toast.makeText(SharedPostFragment.this.getActivity(), 2131427374, 0).show();
          return;
        }
      }
      ArrayList localArrayList = SharedPostModel.getInstance(SharedPostFragment.this.getCurViewType()).getRepostList();
      int i = SharedPostModel.getInstance(SharedPostFragment.this.getCurViewType()).getTotal();
      if ((SharedPostFragment.current_tab == 0) && (i < 10))
        SharedPostFragment.this.hasMore = false;
      if ((TextUtils.isEmpty(this.postId)) || (SharedPostFragment.this.mListRepost.size() == 0) || (SharedPostFragment.this.mFooterProBar.getVisibility() == 0))
      {
        SharedPostFragment.this.updateRepostList();
        if ((SharedPostFragment.this.mListView != null) && (TextUtils.isEmpty(this.postId)))
          SharedPostFragment.this.mListView.setSelection(0);
        if ((localArrayList != null) && (localArrayList.size() < i) && (localArrayList.size() > 0))
        {
          SharedPostFragment.this.mGetDataTask = null;
          SharedPostFragment.this.refreshData(2, false);
        }
      }
      SharedPostFragment.this.showLoadingFooter(false);
      if (SharedPostFragment.this.mListRepost.size() == 0)
      {
        SharedPostFragment.this.noFriendSharedPost();
        return;
      }
      SharedPostFragment.this.showWait(false);
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  class LoadingMoreTask extends KXFragment.KXAsyncTask<Void, Void, Boolean>
  {
    LoadingMoreTask()
    {
      super();
    }

    protected Boolean doInBackgroundA(Void[] paramArrayOfVoid)
    {
      int i = -1 + SharedPostFragment.this.mListRepost.size();
      return Boolean.valueOf(RepostEngine.getInstance().doGetMoreRepost(SharedPostFragment.this.getActivity(), Integer.valueOf(10), SharedPostFragment.this.mFuid, i));
    }

    protected void onPostExecuteA(Boolean paramBoolean)
    {
      if (paramBoolean == null)
        return;
      if (!paramBoolean.booleanValue())
      {
        SharedPostFragment.this.hasMore = false;
        return;
      }
      List localList = SharedPostModel.getInstance("normal").getMoreItems();
      if (localList.size() < 10);
      for (SharedPostFragment.this.hasMore = false; ; SharedPostFragment.this.hasMore = true)
      {
        SharedPostFragment.this.mListRepost.remove(-1 + SharedPostFragment.this.mListRepost.size());
        SharedPostFragment.this.mListRepost.addAll(localList);
        SharedPostModel.getInstance("normal").setPosts(SharedPostFragment.this.mListRepost, SharedPostFragment.this.mListRepost.size());
        SharedPostFragment.this.mAdapter.notifyDataSetChanged();
        return;
      }
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private class ViewPostDetial
    implements AdapterView.OnItemClickListener
  {
    private ViewPostDetial()
    {
    }

    public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
    {
      if ((TextUtils.isEmpty(((RepItem)SharedPostFragment.this.mListRepost.get(paramInt)).id)) || (SharedPostFragment.this.mListRepost == null));
      int i;
      do
      {
        return;
        i = SharedPostFragment.this.mListRepost.size();
      }
      while ((i == 0) || (paramInt == i));
      Intent localIntent = new Intent(SharedPostFragment.this.getActivity(), RepostDetailFragment.class);
      Bundle localBundle = new Bundle();
      localBundle.putSerializable("repostList", SharedPostFragment.this.mListRepost);
      localBundle.putInt("position", paramInt);
      localBundle.putString("commentflag", "1");
      localBundle.putBoolean("isShowGuide", true);
      localIntent.putExtras(localBundle);
      AnimationUtil.startFragment(SharedPostFragment.this, localIntent, 1);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.SharedPostFragment
 * JD-Core Version:    0.6.0
 */