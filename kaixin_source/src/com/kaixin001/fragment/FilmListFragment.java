package com.kaixin001.fragment;

import android.content.Intent;
import android.content.res.Resources;
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
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.kaixin001.engine.FilmEngine;
import com.kaixin001.item.FilmInfo;
import com.kaixin001.model.FilmListModel;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.UserHabitUtils;
import com.kaixin001.view.KXFrameImageView;
import java.util.ArrayList;

public class FilmListFragment extends BaseFragment
  implements View.OnClickListener, AdapterView.OnItemClickListener, AbsListView.OnScrollListener
{
  private static final String TAG = "FilmListFragment";
  private FilmAdapter mAdapter;
  protected ImageView mBtnLeft;
  protected ImageView mBtnRight;
  private ArrayList<FilmInfo> mFilmList = new ArrayList();
  private ProgressBar mFooterProBar;
  private TextView mFooterTV;
  protected View mFooterView;
  private GetListTask mGetDataTask;
  private ListView mListView;
  private boolean mNeedAutoLoading = false;
  protected ProgressBar mRightProBar;
  protected TextView mTopTitle;
  private TextView mTvEmpty = null;
  protected LinearLayout mWaitLayout;
  private ProgressBar mWaitingProBar;

  private void getMoreData()
  {
    cancelTask(this.mGetDataTask);
    showLoadingFooter(true);
    this.mGetDataTask = new GetListTask();
    this.mGetDataTask.execute(new Void[0]);
  }

  private void initView()
  {
    this.mListView = ((ListView)findViewById(2131362128));
    this.mAdapter = new FilmAdapter();
    this.mListView.setAdapter(this.mAdapter);
    this.mListView.setOnItemClickListener(this);
    this.mListView.setOnScrollListener(this);
    this.mFooterView = getActivity().getLayoutInflater().inflate(2130903259, null);
    this.mFooterView.setOnClickListener(this);
    this.mFooterTV = ((TextView)this.mFooterView.findViewById(2131362073));
    this.mFooterTV.setText(2131427748);
    this.mFooterProBar = ((ProgressBar)this.mFooterView.findViewById(2131362072));
    this.mFooterProBar.setVisibility(4);
    this.mWaitLayout = ((LinearLayout)findViewById(2131362134));
    this.mTvEmpty = ((TextView)findViewById(2131362136));
    this.mWaitingProBar = ((ProgressBar)findViewById(2131362135));
  }

  private void refreshData(boolean paramBoolean)
  {
    cancelTask(this.mGetDataTask);
    showWait(paramBoolean);
    setRefreshStatus(true);
    FilmListModel.getInstance().getFilmList().clear();
    this.mGetDataTask = new GetListTask();
    this.mGetDataTask.execute(new Void[0]);
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

  protected void initTitle()
  {
    ((ImageView)findViewById(2131362919)).setVisibility(8);
    this.mBtnRight = ((ImageView)findViewById(2131362928));
    this.mBtnRight.setImageResource(2130838834);
    this.mBtnRight.setOnClickListener(this);
    this.mRightProBar = ((ProgressBar)findViewById(2131362929));
    this.mBtnLeft = ((ImageView)findViewById(2131362914));
    this.mBtnLeft.setOnClickListener(this);
    TextView localTextView = (TextView)findViewById(2131362920);
    localTextView.setVisibility(0);
    localTextView.setText(2131428535);
  }

  public void onClick(View paramView)
  {
    if (paramView.equals(this.mBtnLeft))
    {
      Bundle localBundle = getArguments();
      if ((localBundle != null) && (localBundle.getString("from") != null) && (localBundle.getString("from").equals("KaixinMenuListFragment")))
        if (super.isMenuListVisibleInMain())
          super.showSubActivityInMain();
    }
    do
    {
      return;
      super.showMenuListInMain();
      return;
      finish();
      return;
      if (!paramView.equals(this.mBtnRight))
        continue;
      refreshData(false);
      return;
    }
    while (paramView != this.mFooterView);
    getMoreData();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    UserHabitUtils.getInstance(getActivity()).addUserHabit("visit_film_list");
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903087, paramViewGroup, false);
  }

  public void onDestroy()
  {
    cancelTask(this.mGetDataTask);
    super.onDestroy();
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    FilmInfo localFilmInfo = (FilmInfo)this.mFilmList.get(paramInt);
    if ((localFilmInfo != null) && (!TextUtils.isEmpty(localFilmInfo.mFid)))
    {
      Intent localIntent = new Intent();
      localIntent.setClass(getActivity(), FilmDetailFragment.class);
      localIntent.putExtra("fid", localFilmInfo.mFid);
      localIntent.putExtra("name", localFilmInfo.mName);
      AnimationUtil.startFragment(this, localIntent, 1);
    }
  }

  public void onScroll(AbsListView paramAbsListView, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = -1 + (paramInt1 + paramInt2);
    int j = -1 + this.mAdapter.getCount();
    KXLog.d("FilmListFragment", "onScroll lastVisibleIndex:" + i + ", " + this.mNeedAutoLoading);
    if ((i >= j) && (j > 0) && (!this.mNeedAutoLoading))
    {
      ArrayList localArrayList = this.mFilmList;
      boolean bool1 = false;
      if (localArrayList != null)
      {
        int k = this.mFilmList.size();
        bool1 = false;
        if (k > 0)
        {
          boolean bool2 = TextUtils.isEmpty(((FilmInfo)this.mFilmList.get(-1 + this.mFilmList.size())).mFid);
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
      if (this.mNeedAutoLoading)
      {
        getMoreData();
        this.mNeedAutoLoading = false;
      }
      return;
    }
    setNotCanLoad();
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    initTitle();
    initView();
    if (FilmListModel.getInstance().getFilmList().size() == 0)
    {
      refreshData(true);
      return;
    }
    updateListData();
  }

  protected void setRefreshStatus(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.mBtnRight.setEnabled(false);
      this.mRightProBar.setVisibility(0);
      this.mBtnRight.setImageResource(0);
      return;
    }
    this.mBtnRight.setEnabled(true);
    this.mRightProBar.setVisibility(8);
    this.mBtnRight.setImageResource(2130838834);
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
      localLinearLayout = this.mWaitLayout;
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

  public void updateListData()
  {
    this.mFilmList.clear();
    ArrayList localArrayList = FilmListModel.getInstance().getFilmList();
    if (localArrayList != null)
      this.mFilmList.addAll(localArrayList);
    if ((localArrayList != null) && (localArrayList.size() > 0) && (FilmListModel.getInstance().hasMore()))
    {
      FilmInfo localFilmInfo = new FilmInfo();
      localFilmInfo.mFid = "";
      this.mFilmList.add(localFilmInfo);
    }
    if (this.mAdapter != null)
      this.mAdapter.notifyDataSetChanged();
  }

  class FilmAdapter extends BaseAdapter
  {
    FilmAdapter()
    {
    }

    public int getCount()
    {
      return FilmListFragment.this.mFilmList.size();
    }

    public Object getItem(int paramInt)
    {
      return FilmListFragment.this.mFilmList.get(paramInt);
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      FilmInfo localFilmInfo = (FilmInfo)FilmListFragment.this.mFilmList.get(paramInt);
      if (localFilmInfo.mFid == "")
        return FilmListFragment.this.mFooterView;
      FilmListFragment.ViewHolder localViewHolder;
      if ((paramView == null) || (paramView == FilmListFragment.this.mFooterView))
      {
        paramView = FilmListFragment.this.getActivity().getLayoutInflater().inflate(2130903110, null);
        localViewHolder = new FilmListFragment.ViewHolder(FilmListFragment.this);
        localViewHolder.initView(paramView);
        paramView.setTag(localViewHolder);
      }
      while (true)
      {
        localViewHolder.updateView(localFilmInfo);
        return paramView;
        localViewHolder = (FilmListFragment.ViewHolder)paramView.getTag();
      }
    }
  }

  class GetListTask extends KXFragment.KXAsyncTask<Void, Void, Integer>
  {
    GetListTask()
    {
      super();
    }

    protected Integer doInBackgroundA(Void[] paramArrayOfVoid)
    {
      return Integer.valueOf(FilmEngine.getInstance().getFilmList(FilmListFragment.this.getActivity().getApplicationContext(), FilmListModel.getInstance().getFilmList().size(), 10));
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      if ((FilmListFragment.this.getActivity() == null) || (FilmListFragment.this.getView() == null))
        return;
      FilmListFragment.this.showWait(false);
      FilmListFragment.this.setRefreshStatus(false);
      FilmListFragment.this.showLoadingFooter(false);
      if (paramInteger.intValue() == 1)
      {
        FilmListFragment.this.updateListData();
        return;
      }
      FilmListFragment.this.showToast(2131427374);
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  class ViewHolder
  {
    TextView mActor;
    KXFrameImageView mLogo;
    TextView mName;
    TextView mScore;

    ViewHolder()
    {
    }

    public void initView(View paramView)
    {
      this.mLogo = ((KXFrameImageView)paramView.findViewById(2131362275));
      this.mName = ((TextView)paramView.findViewById(2131362277));
      this.mScore = ((TextView)paramView.findViewById(2131362279));
      this.mActor = ((TextView)paramView.findViewById(2131362281));
    }

    public void updateView(FilmInfo paramFilmInfo)
    {
      this.mLogo.setFrameNinePatchResId(2130838626);
      FilmListFragment.this.displayPicture(this.mLogo, paramFilmInfo.mCover, 2130838784);
      this.mName.setText(paramFilmInfo.mName);
      this.mScore.setText(paramFilmInfo.mScore);
      this.mActor.setText("主演：" + paramFilmInfo.mActors);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.FilmListFragment
 * JD-Core Version:    0.6.0
 */