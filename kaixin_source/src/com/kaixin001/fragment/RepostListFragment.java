package com.kaixin001.fragment;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.kaixin001.adapter.RepostListAdapter;
import com.kaixin001.engine.RepostEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.item.NewsInfo;
import com.kaixin001.item.RepItem;
import com.kaixin001.model.NewsModel;
import com.kaixin001.model.RepostModel;
import com.kaixin001.model.SharedPostModel;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpConnection;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.CrashRecoverUtil;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.IntentUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class RepostListFragment extends BaseFragment
  implements AbsListView.OnScrollListener
{
  private static final String TAG = "RepostListFragment";
  private boolean hasMore = true;
  private int lastVisibleIndex = 0;
  private RepostListAdapter mAdapter;
  private String mCommentFlag = "";
  private ProgressBar mFooterProBar;
  private TextView mFooterTV;
  private View mFooterView;
  private String mFriendName = "";
  private String mFuid = "";
  private GetMoreRepostTask mGetMoreRepostTask = null;
  private boolean mIsHot = false;
  private boolean mNeedAutoLoading = false;
  private ArrayList<RepItem> mRepostArray1 = new ArrayList();
  private final ArrayList<RepItem> mRepostArray2 = new ArrayList();

  private void getMoreRepost(boolean paramBoolean)
  {
    SharedPostModel.clear("hot");
    this.mGetMoreRepostTask = new GetMoreRepostTask(null);
    this.mGetMoreRepostTask.execute(new String[] { "" });
    showProgressBar(true);
  }

  private ArrayList<RepItem> getRepList()
  {
    if (this.mIsHot)
      return SharedPostModel.getInstance("hot").getRepostList();
    return RepostModel.getInstance().getMoreRepostList();
  }

  private int getRepListSize()
  {
    if (this.mIsHot)
      return SharedPostModel.getInstance("hot").getTotal();
    return RepostModel.getInstance().getAllTotal();
  }

  private void initViews()
  {
    this.mFooterTV = ((TextView)this.mFooterView.findViewById(2131362073));
    this.mFooterTV.setText(2131427748);
    this.mFooterProBar = ((ProgressBar)this.mFooterView.findViewById(2131362072));
  }

  private void loadMoreData()
  {
    if ((this.hasMore) && (!TextUtils.isEmpty(((RepItem)this.mRepostArray2.get(-1 + this.mRepostArray2.size())).id)))
    {
      this.mRepostArray2.add(new RepItem());
      this.mAdapter.notifyDataSetChanged();
      new loadMoreDataTask().execute(new Void[0]);
    }
  }

  private void onMoreItemClicked()
  {
    ArrayList localArrayList = getRepList();
    int i = getRepListSize();
    if ((localArrayList == null) || (localArrayList.size() < this.mRepostArray1.size()))
      showLoadingFooter(true);
    for (boolean bool = true; ; bool = false)
    {
      if ((localArrayList != null) && (localArrayList.size() < i))
        refreshMore(bool);
      return;
      showLoadingFooter(false);
      updateRepostList();
    }
  }

  private void refreshMore(boolean paramBoolean)
  {
    if (!super.checkNetworkAndHint(paramBoolean))
      showLoadingFooter(false);
    String str;
    do
    {
      int i;
      do
      {
        return;
        ArrayList localArrayList1 = getRepList();
        i = -1;
        if (localArrayList1 == null)
          continue;
        i = -1 + localArrayList1.size();
      }
      while (i <= 0);
      ArrayList localArrayList2 = this.mRepostArray2;
      str = null;
      if (localArrayList2 == null)
        continue;
      int j = this.mRepostArray2.size();
      str = null;
      if (j <= i)
        continue;
      str = ((RepItem)this.mRepostArray2.get(i)).id;
    }
    while (str == null);
    refreshMoreRepostListData(str);
  }

  private void refreshMoreRepostListData(String paramString)
  {
    if (paramString == null);
    do
      return;
    while (((this.mGetMoreRepostTask != null) && (!this.mGetMoreRepostTask.isCancelled()) && (this.mGetMoreRepostTask.getStatus() != AsyncTask.Status.FINISHED)) || (HttpConnection.checkNetworkAvailable(getActivity().getApplicationContext()) < 0));
    this.mFooterTV.setTextColor(getResources().getColor(2130839395));
    this.mFooterProBar.setVisibility(0);
    this.mGetMoreRepostTask = new GetMoreRepostTask(null);
    this.mGetMoreRepostTask.execute(new String[] { paramString });
  }

  private void showLoadingFooter(boolean paramBoolean)
  {
    ProgressBar localProgressBar;
    if (this.mFooterProBar != null)
    {
      localProgressBar = this.mFooterProBar;
      if (!paramBoolean)
        break label66;
    }
    label66: for (int j = 0; ; j = 4)
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

  private void showProgressBar(boolean paramBoolean)
  {
    View localView = findViewById(2131362183);
    if (paramBoolean)
    {
      localView.setVisibility(0);
      return;
    }
    localView.setVisibility(8);
  }

  private void updateData()
  {
    NewsInfo localNewsInfo = NewsModel.getInstance().getActiveItem();
    if (localNewsInfo == null);
    try
    {
      if (NewsModel.getInstance().getNewsList().size() > 0)
        localNewsInfo = (NewsInfo)NewsModel.getInstance().getNewsList().get(0);
      if (localNewsInfo != null)
      {
        this.mFriendName = localNewsInfo.mFname;
        ArrayList localArrayList = localNewsInfo.mRepostList;
        if (localArrayList != null)
          this.mRepostArray1.addAll(localArrayList);
        this.mFuid = localNewsInfo.mFuid;
      }
      getMoreRepost(true);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private void updateRepostList()
  {
    this.mRepostArray2.clear();
    ArrayList localArrayList = getRepList();
    int i = getRepListSize();
    if (localArrayList != null)
    {
      this.mRepostArray2.addAll(localArrayList);
      if (i - localArrayList.size() - this.mRepostArray1.size() > 0)
      {
        RepItem localRepItem = new RepItem();
        localRepItem.id = "";
        this.mRepostArray2.add(localRepItem);
      }
    }
    if (this.mAdapter != null)
      this.mAdapter.notifyDataSetChanged();
    if (this.mRepostArray2.size() < 10)
      this.hasMore = false;
  }

  public void finish()
  {
    super.finish();
    AnimationUtil.finishActivity(getActivity(), 2);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (CrashRecoverUtil.isCrashBefore())
    {
      CrashRecoverUtil.crashRecover(getActivity().getApplicationContext());
      IntentUtil.returnToDesktop(getActivity());
    }
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903321, paramViewGroup, false);
  }

  public void onDestroy()
  {
    if ((this.mGetMoreRepostTask != null) && (this.mGetMoreRepostTask.getStatus() != AsyncTask.Status.FINISHED))
      this.mGetMoreRepostTask.cancel(true);
    RepostModel.getInstance().clear();
    SharedPostModel.clear("hot");
    super.onDestroy();
  }

  public void onScroll(AbsListView paramAbsListView, int paramInt1, int paramInt2, int paramInt3)
  {
    this.lastVisibleIndex = (-1 + (paramInt1 + paramInt2));
    int i = -1 + this.mAdapter.getCount();
    if ((this.lastVisibleIndex >= i) && (i > 0) && (!this.mNeedAutoLoading))
    {
      ArrayList localArrayList = this.mRepostArray2;
      boolean bool1 = false;
      if (localArrayList != null)
      {
        int j = this.mRepostArray2.size();
        bool1 = false;
        if (j > 0)
        {
          boolean bool2 = TextUtils.isEmpty(((RepItem)this.mRepostArray2.get(-1 + this.mRepostArray2.size())).id);
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
      if ((!this.mIsHot) && (this.lastVisibleIndex == -1 + this.mRepostArray2.size()) && (this.hasMore))
        loadMoreData();
      return;
    }
    setNotCanLoad();
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    Bundle localBundle = getArguments();
    if (localBundle != null)
    {
      String str = localBundle.getString("commentflag");
      if (str != null)
        this.mCommentFlag = str;
      this.mIsHot = localBundle.getBoolean("hot");
    }
    this.mFooterView = getActivity().getLayoutInflater().inflate(2130903259, null);
    this.mAdapter = new RepostListAdapter(this, this.mFooterView, this.mRepostArray2);
    ListView localListView = (ListView)findViewById(2131363582);
    localListView.setAdapter(this.mAdapter);
    localListView.setOnItemClickListener(new ListOnItemClickListener(null));
    localListView.setOnScrollListener(this);
    updateData();
    View localView = findViewById(2131363581);
    if (localView != null)
      localView.setVisibility(8);
    Button localButton = (Button)findViewById(2131363583);
    if (localButton != null)
    {
      localButton.setText(this.mFriendName + getString(2131427602));
      localButton.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          Intent localIntent = new Intent(RepostListFragment.this.getActivity(), SharedPostFragment.class);
          localIntent.putExtra("fuid", RepostListFragment.this.mFuid);
          localIntent.putExtra("fname", RepostListFragment.this.mFriendName);
          AnimationUtil.startFragment(RepostListFragment.this, localIntent, 1);
        }
      });
    }
    initViews();
    setTitleBar();
    if (TextUtils.isEmpty(User.getInstance().getUID()))
      DialogUtil.showKXAlertDialog(getActivity(), 2131427386, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          IntentUtil.returnToDesktop(RepostListFragment.this.getActivity());
        }
      });
  }

  public void setRepItemList()
  {
    Iterator localIterator = this.mRepostArray2.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return;
      RepItem localRepItem = (RepItem)localIterator.next();
      localRepItem.fname = this.mFriendName;
      localRepItem.fuid = this.mFuid;
    }
  }

  protected void setTitleBar()
  {
    ImageView localImageView = (ImageView)findViewById(2131362914);
    localImageView.setVisibility(0);
    localImageView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        RepostListFragment.this.finish();
      }
    });
    ((ImageView)findViewById(2131362928)).setVisibility(8);
    ((ImageView)findViewById(2131362919)).setVisibility(8);
    TextView localTextView = (TextView)findViewById(2131362920);
    if (this.mFriendName.length() > 3)
      this.mFriendName = (this.mFriendName.substring(0, 3) + "...");
    if (this.mIsHot);
    for (String str = getResources().getString(2131427844); ; str = this.mFriendName + getResources().getString(2131427600))
    {
      localTextView.setText(str);
      localTextView.setVisibility(0);
      return;
    }
  }

  private class GetMoreRepostTask extends KXFragment.KXAsyncTask<String, Void, Boolean>
  {
    private String mLasturpid = "";

    private GetMoreRepostTask()
    {
      super();
    }

    protected Boolean doInBackgroundA(String[] paramArrayOfString)
    {
      try
      {
        this.mLasturpid = paramArrayOfString[0];
        if (RepostListFragment.this.mIsHot)
          return RepostEngine.getInstance().doGetRepostList(RepostListFragment.this.getActivity().getApplicationContext(), this.mLasturpid, "hot", Integer.valueOf(10), User.getInstance().getUID());
        Boolean localBoolean = Boolean.valueOf(RepostEngine.getInstance().doGetRepostList(RepostListFragment.this.getActivity().getApplicationContext(), RepostListFragment.this.mFuid, this.mLasturpid));
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
        RepostListFragment.this.finish();
        return;
      }
      try
      {
        ArrayList localArrayList = RepostListFragment.this.getRepList();
        int i = RepostListFragment.this.getRepListSize();
        if (RepostListFragment.this.mRepostArray2.size() == 0)
        {
          RepostListFragment.this.updateRepostList();
          if (RepostListFragment.this.mRepostArray2.size() != 0)
          {
            RepostListFragment.this.showProgressBar(false);
            if ((localArrayList != null) && (localArrayList.size() < i))
            {
              RepostListFragment.this.mGetMoreRepostTask = null;
              RepostListFragment.this.refreshMore(false);
            }
          }
        }
        RepostListFragment.this.showLoadingFooter(false);
        return;
      }
      catch (Exception localException)
      {
      }
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private class ListOnItemClickListener
    implements AdapterView.OnItemClickListener
  {
    private ListOnItemClickListener()
    {
    }

    public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
    {
      Intent localIntent;
      Bundle localBundle;
      if (!TextUtils.isEmpty(((RepItem)RepostListFragment.this.mRepostArray2.get(paramInt)).id))
      {
        localIntent = new Intent(RepostListFragment.this.getActivity(), RepostDetailFragment.class);
        localBundle = new Bundle();
        localBundle.putSerializable("repostList", RepostListFragment.this.mRepostArray2);
        localBundle.putInt("position", paramInt);
        localBundle.putBoolean("isShowGuide", true);
        if (!RepostListFragment.this.mIsHot)
          break label118;
        localBundle.putString("commentflag", "1");
      }
      while (true)
      {
        localIntent.putExtras(localBundle);
        AnimationUtil.startFragment(RepostListFragment.this, localIntent, 1);
        return;
        label118: RepostListFragment.this.setRepItemList();
        localBundle.putString("commentflag", RepostListFragment.this.mCommentFlag);
      }
    }
  }

  class loadMoreDataTask extends KXFragment.KXAsyncTask<Void, Void, Boolean>
  {
    loadMoreDataTask()
    {
      super();
    }

    protected Boolean doInBackgroundA(Void[] paramArrayOfVoid)
    {
      int i = -1 + RepostListFragment.this.mRepostArray2.size();
      if (RepostListFragment.this.mIsHot)
        return Boolean.valueOf(RepostEngine.getInstance().doGetMoreDataRepostList(RepostListFragment.this.getActivity().getApplicationContext(), "hot", Integer.valueOf(10), User.getInstance().getUID(), i));
      return Boolean.valueOf(RepostEngine.getInstance().doGetMoreDataRepostList(RepostListFragment.this.getActivity().getApplicationContext(), RepostListFragment.this.mFuid, i));
    }

    protected void onPostExecuteA(Boolean paramBoolean)
    {
      if (paramBoolean == null)
        return;
      if (!paramBoolean.booleanValue())
      {
        RepostListFragment.this.hasMore = false;
        return;
      }
      Object localObject;
      if (RepostListFragment.this.mIsHot)
      {
        localObject = SharedPostModel.getInstance("hot").getMoreItems();
        if (((List)localObject).size() >= 10)
          break label115;
      }
      label115: for (RepostListFragment.this.hasMore = false; ; RepostListFragment.this.hasMore = true)
      {
        RepostListFragment.this.mRepostArray2.remove(-1 + RepostListFragment.this.mRepostArray2.size());
        RepostListFragment.this.mRepostArray2.addAll((Collection)localObject);
        RepostListFragment.this.mAdapter.notifyDataSetChanged();
        return;
        localObject = RepostModel.getInstance().getMoreRepItemsData();
        break;
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
 * Qualified Name:     com.kaixin001.fragment.RepostListFragment
 * JD-Core Version:    0.6.0
 */