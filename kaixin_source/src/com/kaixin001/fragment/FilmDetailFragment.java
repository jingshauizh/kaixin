package com.kaixin001.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.engine.FilmEngine;
import com.kaixin001.item.FilmCommentItem;
import com.kaixin001.item.FilmInfo;
import com.kaixin001.item.KXLinkInfo;
import com.kaixin001.item.NewsInfo;
import com.kaixin001.model.FilmDetailModel;
import com.kaixin001.model.User;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.ImageCache;
import com.kaixin001.util.ImageDownloader.RoundCornerType;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.KXTextUtil;
import com.kaixin001.util.UserHabitUtils;
import com.kaixin001.view.KXFrameImageView;
import com.kaixin001.view.KXIntroView;
import com.kaixin001.view.KXIntroView.OnClickLinkListener;
import com.kaixin001.view.KXTopTab;
import com.kaixin001.view.KXTopTabHost2;
import com.kaixin001.view.KXTopTabHost2.OnTabChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class FilmDetailFragment extends BaseFragment
  implements View.OnClickListener, AbsListView.OnScrollListener
{
  private static final int BOTTOM_VIEW_NUM = 1;
  public static final int FILM_WRITE_COMMNET = 2013;
  private static final int HEAD_VIEW_NUM = 2;
  private static final String TAG = "FilmDetailActivity";
  private static final int TYPE_ALL = 1;
  private static final int TYPE_FRIEND = 2;
  private FilmDetailAdapter mAdapter;
  private Button mBottomBtn;
  private View mBottomBtnLayout;
  private View mBottomSpace;
  private boolean mBottomSpaceInit = false;
  protected ImageView mBtnLeft;
  protected ImageView mBtnRight;
  private int mCurTabIndex = 0;
  private int mCurType = 1;
  private String mFid = "";
  private ArrayList<FilmCommentItem> mFilmCommentList = new ArrayList();
  KXIntroView mFilmIntro;
  private int mFirstVisibleItem = 0;
  private ProgressBar mFooterProBar;
  private TextView mFooterTV;
  protected View mFooterView;
  private GeDataTask mGetDataTask;
  private View mHeadTab;
  private View mHeadView;
  private HashMap<Integer, Integer> mListItemHeiMap = new HashMap();
  private ListView mListView;
  protected View mListViewNodataLayout;
  private ProgressBar mListViewNodataProBar;
  private TextView mListViewNodataTV = null;
  private FilmDetailModel mModel = new FilmDetailModel();
  private ArrayList<FilmCommentItem> mMyComment = new ArrayList();
  private String mName = "";
  private boolean mNeedAutoLoading = false;
  private ProgressDialog mProgressDialog;
  protected ProgressBar mRightProBar;
  private boolean mShowDetail = false;
  private KXTopTabHost2 mTabHost;
  protected TextView mTextRight;
  private View mTitleBar;
  protected TextView mTopTitle;
  private TextView mTvEmpty = null;
  protected View mWaitLayout;
  private ProgressBar mWaitingProBar;
  private WanttoTask mWanttoTask;

  private void actionWantto(boolean paramBoolean)
  {
    cancelTask(this.mWanttoTask);
    String str = getResources().getString(2131428506);
    this.mProgressDialog = ProgressDialog.show(getActivity(), null, str);
    this.mWanttoTask = new WanttoTask();
    WanttoTask localWanttoTask = this.mWanttoTask;
    Boolean[] arrayOfBoolean = new Boolean[1];
    arrayOfBoolean[0] = Boolean.valueOf(paramBoolean);
    localWanttoTask.execute(arrayOfBoolean);
  }

  private void addBottomView()
  {
    FilmCommentItem localFilmCommentItem = new FilmCommentItem();
    localFilmCommentItem.mFuid = "bottomspace";
    this.mFilmCommentList.add(localFilmCommentItem);
  }

  private void addHeadView()
  {
    FilmCommentItem localFilmCommentItem1 = new FilmCommentItem();
    localFilmCommentItem1.mFuid = "headtab";
    this.mFilmCommentList.add(0, localFilmCommentItem1);
    FilmCommentItem localFilmCommentItem2 = new FilmCommentItem();
    localFilmCommentItem2.mFuid = "headview";
    this.mFilmCommentList.add(0, localFilmCommentItem2);
  }

  private void addListToData(ArrayList<FilmCommentItem> paramArrayList)
  {
    if (paramArrayList == null);
    while (true)
    {
      return;
      Iterator localIterator = paramArrayList.iterator();
      while (localIterator.hasNext())
      {
        FilmCommentItem localFilmCommentItem = (FilmCommentItem)localIterator.next();
        if ((localFilmCommentItem == null) || (TextUtils.isEmpty(localFilmCommentItem.mComment)))
          continue;
        this.mFilmCommentList.add(localFilmCommentItem);
      }
    }
  }

  private void displayStar(View paramView, int paramInt1, String paramString, int paramInt2)
  {
    try
    {
      float f2 = Float.parseFloat(paramString);
      f1 = f2;
      ImageView localImageView = (ImageView)paramView.findViewById(paramInt1);
      int i = dipToPx(16.700001F);
      int j = dipToPx(16.700001F);
      localImageView.setImageBitmap(getStarBitmap(f1 - paramInt2 * 2, 2, i, j));
      return;
    }
    catch (Exception localException)
    {
      while (true)
      {
        localException.printStackTrace();
        float f1 = 0.0F;
      }
    }
  }

  private ArrayList<FilmCommentItem> getCommentList(int paramInt)
  {
    if (paramInt == 1)
      return this.mModel.getAllComments();
    return this.mModel.getFriendComments();
  }

  private void getDetail()
  {
    cancelTask(this.mGetDataTask);
    showWait(true);
    this.mGetDataTask = new GeDataTask();
    GeDataTask localGeDataTask = this.mGetDataTask;
    Integer[] arrayOfInteger = new Integer[2];
    arrayOfInteger[0] = Integer.valueOf(-1);
    arrayOfInteger[1] = Integer.valueOf(this.mCurType);
    localGeDataTask.execute(arrayOfInteger);
  }

  private void getDetailFinished(boolean paramBoolean)
  {
    TextView localTextView = this.mTextRight;
    if (paramBoolean);
    for (int i = 0; ; i = 8)
    {
      localTextView.setVisibility(i);
      if ((!paramBoolean) || (this.mModel.getInfo() == null) || (!this.mModel.getInfo().mWanted))
        break;
      this.mBottomBtnLayout.setVisibility(8);
      return;
    }
    View localView = this.mBottomBtnLayout;
    int j = 0;
    if (paramBoolean);
    while (true)
    {
      localView.setVisibility(j);
      return;
      j = 8;
    }
  }

  private void getMoreData()
  {
    cancelTask(this.mGetDataTask);
    showLoadingFooter(true);
    this.mGetDataTask = new GeDataTask();
    GeDataTask localGeDataTask = this.mGetDataTask;
    Integer[] arrayOfInteger = new Integer[2];
    arrayOfInteger[0] = Integer.valueOf(0);
    arrayOfInteger[1] = Integer.valueOf(this.mCurType);
    localGeDataTask.execute(arrayOfInteger);
  }

  private Bitmap getStarBitmap(float paramFloat, int paramInt1, int paramInt2, int paramInt3)
  {
    Bitmap localBitmap1 = ImageCache.getInstance().getLoadingBitmap(2130838632, 0, 0);
    Bitmap localBitmap2 = ImageCache.getInstance().getLoadingBitmap(2130838630, 0, 0);
    if (paramFloat <= 0.0F)
      return localBitmap2;
    if (paramFloat >= paramInt1)
      return localBitmap1;
    Paint localPaint = new Paint();
    Bitmap localBitmap3 = Bitmap.createBitmap(paramInt2, paramInt3, Bitmap.Config.ARGB_8888);
    Canvas localCanvas = new Canvas(localBitmap3);
    int i = (int)(paramFloat * localBitmap1.getWidth() / paramInt1);
    int j = (int)(paramFloat * paramInt2 / paramInt1);
    Rect localRect1 = new Rect(0, 0, i, localBitmap1.getHeight());
    Rect localRect2 = new Rect(i, 0, localBitmap1.getWidth(), localBitmap1.getHeight());
    Rect localRect3 = new Rect(0, 0, j, paramInt3);
    Rect localRect4 = new Rect(j, 0, paramInt2, paramInt3);
    localCanvas.drawBitmap(localBitmap1, localRect1, localRect3, localPaint);
    localCanvas.drawBitmap(localBitmap2, localRect2, localRect4, localPaint);
    return localBitmap3;
  }

  private boolean hasMore(int paramInt)
  {
    if (paramInt == 1)
      return this.mModel.allHasMore();
    return this.mModel.friendHasMore();
  }

  private void initListViewSelectIndex()
  {
    if (this.mFirstVisibleItem >= 1)
      this.mListView.setSelection(1);
    this.mBottomSpaceInit = false;
  }

  private void initView()
  {
    this.mListViewNodataLayout = getActivity().getLayoutInflater().inflate(2130903203, null);
    this.mListViewNodataTV = ((TextView)this.mListViewNodataLayout.findViewById(2131362970));
    this.mListViewNodataProBar = ((ProgressBar)this.mListViewNodataLayout.findViewById(2131362969));
    this.mHeadView = getActivity().getLayoutInflater().inflate(2130903109, null);
    this.mHeadTab = getActivity().getLayoutInflater().inflate(2130903108, null);
    this.mBottomSpace = getActivity().getLayoutInflater().inflate(2130903106, null);
    this.mListView = ((ListView)findViewById(2131362233));
    this.mAdapter = new FilmDetailAdapter();
    this.mListView.setAdapter(this.mAdapter);
    this.mListView.setOnScrollListener(this);
    this.mFooterView = getActivity().getLayoutInflater().inflate(2130903259, null);
    this.mFooterView.setBackgroundResource(2130839413);
    this.mFooterView.setOnClickListener(this);
    this.mFooterTV = ((TextView)this.mFooterView.findViewById(2131362073));
    this.mFooterTV.setText(2131427748);
    this.mFooterProBar = ((ProgressBar)this.mFooterView.findViewById(2131362072));
    this.mFooterProBar.setVisibility(4);
    this.mWaitLayout = findViewById(2131362236);
    this.mTvEmpty = ((TextView)findViewById(2131362238));
    this.mWaitingProBar = ((ProgressBar)findViewById(2131362237));
    this.mBottomBtn = ((Button)findViewById(2131362240));
    this.mBottomBtnLayout = findViewById(2131362239);
    this.mBottomBtn.setOnClickListener(this);
    getDetailFinished(false);
  }

  private void onTypeChanged(int paramInt)
  {
    if (paramInt == this.mCurType);
    do
    {
      return;
      this.mBottomSpaceInit = false;
      this.mListItemHeiMap.clear();
      this.mCurType = paramInt;
      updateListData(paramInt);
      initListViewSelectIndex();
    }
    while (getCommentList(paramInt).size() != 0);
    showListLoading();
    getMoreData();
  }

  private void recordListItemHei()
  {
    int i = this.mListView.getFirstVisiblePosition();
    int j = 0;
    if (j >= this.mListView.getChildCount())
      return;
    if (i + j < 1);
    while (true)
    {
      j++;
      break;
      View localView = this.mListView.getChildAt(j);
      this.mListItemHeiMap.put(Integer.valueOf(i + j), Integer.valueOf(localView.getHeight()));
    }
  }

  private void showHome(String paramString1, String paramString2, String paramString3)
  {
    Intent localIntent = new Intent(getActivity(), HomeFragment.class);
    Bundle localBundle = new Bundle();
    localBundle.putString("fuid", paramString1);
    localBundle.putString("fname", paramString2);
    localIntent.putExtras(localBundle);
    AnimationUtil.startFragment(this, localIntent, 1);
  }

  private void showListEmpty(String paramString)
  {
    this.mFilmCommentList.clear();
    FilmCommentItem localFilmCommentItem = new FilmCommentItem();
    localFilmCommentItem.mFuid = "empty";
    localFilmCommentItem.mComment = paramString;
    this.mFilmCommentList.add(localFilmCommentItem);
    addHeadView();
    addBottomView();
    this.mAdapter.notifyDataSetChanged();
    initListViewSelectIndex();
  }

  private void showListLoading()
  {
    this.mFilmCommentList.clear();
    FilmCommentItem localFilmCommentItem = new FilmCommentItem();
    localFilmCommentItem.mFuid = "loading";
    localFilmCommentItem.mComment = getResources().getString(2131427380);
    this.mFilmCommentList.add(localFilmCommentItem);
    addHeadView();
    addBottomView();
    this.mAdapter.notifyDataSetChanged();
    initListViewSelectIndex();
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

  public void finish()
  {
    super.finish();
    AnimationUtil.finishActivity(getActivity(), 2);
  }

  protected void initTitle()
  {
    this.mTitleBar = findViewById(2131362229);
    ((ImageView)findViewById(2131362919)).setVisibility(8);
    this.mBtnRight = ((ImageView)findViewById(2131362928));
    this.mBtnRight.setVisibility(8);
    this.mTextRight = ((TextView)findViewById(2131362930));
    this.mTextRight.setVisibility(0);
    this.mTextRight.setText(2131428538);
    this.mTextRight.setOnClickListener(this);
    this.mRightProBar = ((ProgressBar)findViewById(2131362929));
    this.mBtnLeft = ((ImageView)findViewById(2131362914));
    this.mBtnLeft.setOnClickListener(this);
    TextView localTextView = (TextView)findViewById(2131362920);
    localTextView.setVisibility(0);
    localTextView.setText(this.mName);
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if ((paramInt2 == -1) && (paramInt1 == 2013))
    {
      FilmCommentItem localFilmCommentItem = new FilmCommentItem();
      User localUser = User.getInstance();
      localFilmCommentItem.mFuid = localUser.getUID();
      localFilmCommentItem.mFname = localUser.getRealName();
      localFilmCommentItem.mIcon50 = localUser.getLogo();
      localFilmCommentItem.mScore = Integer.parseInt(paramIntent.getStringExtra("score"));
      localFilmCommentItem.mComment = paramIntent.getStringExtra("content");
      localFilmCommentItem.mTime = KXTextUtil.formatTimestamp(System.currentTimeMillis());
      this.mMyComment.add(localFilmCommentItem);
      updateListData(this.mCurType);
      return;
    }
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
  }

  public void onClick(View paramView)
  {
    if (paramView == this.mBtnLeft)
      finish();
    do
    {
      return;
      if (paramView == this.mTextRight)
      {
        Intent localIntent = new Intent();
        localIntent.setClass(getActivity(), FilmWriteCommentFragment.class);
        localIntent.putExtra("fid", this.mFid);
        localIntent.putExtra("name", this.mName);
        AnimationUtil.startFragmentForResult(this, localIntent, 2013, 3);
        return;
      }
      if (paramView != this.mFooterView)
        continue;
      getMoreData();
      return;
    }
    while (paramView != this.mBottomBtn);
    actionWantto(true);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903105, paramViewGroup, false);
  }

  public void onDestroy()
  {
    cancelTask(this.mGetDataTask);
    cancelTask(this.mWanttoTask);
    super.onDestroy();
  }

  public void onScroll(AbsListView paramAbsListView, int paramInt1, int paramInt2, int paramInt3)
  {
    this.mFirstVisibleItem = paramInt1;
    int i = -1 + (paramInt1 + paramInt2);
    int j = -1 + (-1 + this.mAdapter.getCount());
    KXLog.d("FilmDetailActivity", "onScroll lastVisibleIndex:" + i + ", " + this.mNeedAutoLoading);
    if ((i >= j) && (j > 0) && (!this.mNeedAutoLoading))
    {
      ArrayList localArrayList = this.mFilmCommentList;
      boolean bool1 = false;
      if (localArrayList != null)
      {
        int i6 = this.mFilmCommentList.size();
        bool1 = false;
        if (i6 > 0)
        {
          boolean bool2 = TextUtils.isEmpty(((FilmCommentItem)this.mFilmCommentList.get(-2 + this.mFilmCommentList.size())).mFuid);
          bool1 = false;
          if (bool2)
            bool1 = true;
        }
      }
      this.mNeedAutoLoading = bool1;
    }
    KXLog.d("FilmDetailActivity", "-------------------------------------------------------------------------------");
    int k;
    int m;
    label186: int i1;
    if ((this.mTabHost != null) && (paramInt1 > 0))
    {
      k = 1;
      if (k == 0)
        break label442;
      m = 0;
      if ((this.mTabHost != null) && (this.mTabHost.getVisibility() != m))
        this.mTabHost.setVisibility(m);
      int n = -1 + this.mAdapter.getCount();
      if ((n >= 2) && (paramInt1 + paramInt2 >= n) && (!this.mBottomSpaceInit))
      {
        recordListItemHei();
        this.mBottomSpaceInit = true;
        i1 = 0;
      }
    }
    for (int i2 = 1; ; i2++)
    {
      if (i2 >= -1 + this.mFilmCommentList.size())
      {
        Rect localRect = new Rect();
        getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        int i3 = localRect.top;
        int i4 = getResources().getDisplayMetrics().heightPixels - i3 - this.mTitleBar.getHeight();
        if ((this.mModel.getInfo() != null) && (!this.mModel.getInfo().mWanted))
          i4 -= this.mBottomBtnLayout.getHeight();
        int i5 = 1;
        if ((this.mFilmCommentList.size() < 11) && (i1 < i4))
          i5 = i4 - i1;
        View localView = this.mBottomSpace.findViewById(2131362241);
        localView.setLayoutParams(new RelativeLayout.LayoutParams(1, i5));
        localView.setBackgroundColor(-65536);
        this.mAdapter.notifyDataSetChanged();
        return;
        k = 0;
        break;
        label442: m = 4;
        break label186;
      }
      Integer localInteger = (Integer)this.mListItemHeiMap.get(Integer.valueOf(i2));
      if (localInteger == null)
        continue;
      i1 += localInteger.intValue();
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
    Bundle localBundle = getArguments();
    if (localBundle != null)
    {
      this.mFid = localBundle.getString("fid");
      this.mName = localBundle.getString("name");
    }
    initTitle();
    initView();
    getDetail();
    UserHabitUtils.getInstance(getActivity()).addUserHabit("visit_film_detail");
  }

  protected void showWait(boolean paramBoolean)
  {
    ListView localListView = this.mListView;
    int i;
    View localView;
    int j;
    if (paramBoolean)
    {
      i = 8;
      localListView.setVisibility(i);
      localView = this.mWaitLayout;
      j = 0;
      if (!paramBoolean)
        break label43;
    }
    while (true)
    {
      localView.setVisibility(j);
      return;
      i = 0;
      break;
      label43: j = 8;
    }
  }

  public void updateHeadView()
  {
    FilmInfo localFilmInfo = this.mModel.getInfo();
    TextView localTextView;
    if (localFilmInfo != null)
    {
      displayPicture((ImageView)this.mHeadView.findViewById(2131362253), localFilmInfo.mCover, 2130838784);
      ((TextView)this.mHeadView.findViewById(2131362261)).setText(localFilmInfo.mScore);
      displayStar(this.mHeadView, 2131362256, localFilmInfo.mScore, 0);
      displayStar(this.mHeadView, 2131362257, localFilmInfo.mScore, 1);
      displayStar(this.mHeadView, 2131362258, localFilmInfo.mScore, 2);
      displayStar(this.mHeadView, 2131362259, localFilmInfo.mScore, 3);
      displayStar(this.mHeadView, 2131362260, localFilmInfo.mScore, 4);
      ((TextView)this.mHeadView.findViewById(2131362264)).setText(localFilmInfo.mType);
      ((TextView)this.mHeadView.findViewById(2131362266)).setText(localFilmInfo.mZone);
      ((TextView)this.mHeadView.findViewById(2131362268)).setText(localFilmInfo.mDirector);
      ((TextView)this.mHeadView.findViewById(2131362270)).setText(localFilmInfo.mActors);
      localTextView = (TextView)this.mHeadView.findViewById(2131362272);
      if (!TextUtils.isEmpty(localFilmInfo.mCostTime))
        break label728;
      this.mHeadView.findViewById(2131362271).setVisibility(8);
      localTextView.setVisibility(8);
    }
    while (true)
    {
      KXLinkInfo localKXLinkInfo = new KXLinkInfo();
      localKXLinkInfo.setContent("简介：");
      localKXLinkInfo.setType("-101");
      localKXLinkInfo.setColor(-1);
      this.mFilmIntro = ((KXIntroView)this.mHeadView.findViewById(2131362274));
      this.mFilmIntro.setShowShadow(false);
      ArrayList localArrayList = NewsInfo.makeIntroList(localFilmInfo.mIntro);
      localArrayList.add(0, localKXLinkInfo);
      this.mFilmIntro.setTitleList(localArrayList);
      this.mFilmIntro.setVisibility(4);
      this.mFilmIntro.setOnClickLinkListener(new KXIntroView.OnClickLinkListener(localKXLinkInfo)
      {
        public void onClick(KXLinkInfo paramKXLinkInfo)
        {
          if (FilmDetailFragment.this.mShowDetail)
          {
            FilmDetailFragment.this.mShowDetail = false;
            FilmDetailFragment.this.mFilmIntro.setIntroViewMaxLines(4);
            ArrayList localArrayList2 = NewsInfo.makeIntroList(FilmDetailFragment.this.mModel.getInfo().mIntro + "[|s|]查看全部[|m|]0[|m|]-102[|e|]");
            localArrayList2.add(0, this.val$link);
            Iterator localIterator2 = localArrayList2.iterator();
            while (true)
            {
              if (!localIterator2.hasNext())
              {
                ((KXLinkInfo)localArrayList2.get(-1 + localArrayList2.size())).setColor(-13061425);
                FilmDetailFragment.this.mFilmIntro.setTitleList(localArrayList2);
                FilmDetailFragment.this.mFilmIntro.setLbsEllipse(true);
                FilmDetailFragment.this.mFilmIntro.setIsLbs(true);
                FilmDetailFragment.this.mFilmIntro.setViewMore(true);
                FilmDetailFragment.this.mAdapter.notifyDataSetChanged();
                FilmDetailFragment.this.mAdapter.notifyDataSetInvalidated();
                return;
              }
              ((KXLinkInfo)localIterator2.next()).setClickEnable(true);
            }
          }
          FilmDetailFragment.this.mShowDetail = true;
          FilmDetailFragment.this.mFilmIntro.setIntroViewMaxLines(2147483647);
          ArrayList localArrayList1 = NewsInfo.makeIntroList(FilmDetailFragment.this.mModel.getInfo().mIntro);
          localArrayList1.add(0, this.val$link);
          Iterator localIterator1 = localArrayList1.iterator();
          while (true)
          {
            if (!localIterator1.hasNext())
            {
              FilmDetailFragment.this.mFilmIntro.setTitleList(localArrayList1);
              FilmDetailFragment.this.mFilmIntro.setLbsEllipse(false);
              FilmDetailFragment.this.mFilmIntro.setIsLbs(false);
              FilmDetailFragment.this.mFilmIntro.setViewMore(false);
              FilmDetailFragment.this.mAdapter.notifyDataSetChanged();
              FilmDetailFragment.this.mAdapter.notifyDataSetInvalidated();
              return;
            }
            ((KXLinkInfo)localIterator1.next()).setClickEnable(true);
          }
        }
      });
      this.mHandler.postDelayed(new Runnable(localKXLinkInfo)
      {
        public void run()
        {
          if (FilmDetailFragment.this.isNeedReturn())
            return;
          ArrayList localArrayList;
          Iterator localIterator;
          if (FilmDetailFragment.this.mFilmIntro.isEllipsed())
          {
            localArrayList = NewsInfo.makeIntroList(FilmDetailFragment.this.mModel.getInfo().mIntro + "[|s|]查看全部[|m|]0[|m|]-102[|e|]");
            localArrayList.add(0, this.val$link);
            localIterator = localArrayList.iterator();
          }
          while (true)
          {
            if (!localIterator.hasNext())
            {
              ((KXLinkInfo)localArrayList.get(-1 + localArrayList.size())).setColor(-13061425);
              FilmDetailFragment.this.mFilmIntro.setTitleList(localArrayList);
              FilmDetailFragment.this.mFilmIntro.setLbsEllipse(true);
              FilmDetailFragment.this.mFilmIntro.setIsLbs(true);
              FilmDetailFragment.this.mFilmIntro.setViewMore(true);
              FilmDetailFragment.this.mAdapter.notifyDataSetChanged();
              FilmDetailFragment.this.mAdapter.notifyDataSetInvalidated();
              FilmDetailFragment.this.mFilmIntro.setVisibility(0);
              return;
            }
            ((KXLinkInfo)localIterator.next()).setClickEnable(true);
          }
        }
      }
      , 0L);
      KXTopTabHost2 localKXTopTabHost2 = (KXTopTabHost2)this.mHeadTab.findViewById(2131362249);
      this.mTabHost = ((KXTopTabHost2)getView().findViewById(2131362235));
      this.mTabHost.setBackImg(2130838623);
      this.mTabHost.setOnTabChangeListener(new KXTopTabHost2.OnTabChangeListener(localKXTopTabHost2)
      {
        public void beforeTabChanged(int paramInt)
        {
        }

        public void onTabChanged(int paramInt)
        {
          if (paramInt != FilmDetailFragment.this.mCurTabIndex)
          {
            FilmDetailFragment.this.mCurTabIndex = paramInt;
            if (paramInt != 0)
              break label41;
            FilmDetailFragment.this.onTypeChanged(1);
          }
          while (true)
          {
            this.val$tabHost.setCurrentTab(paramInt, false);
            return;
            label41: FilmDetailFragment.this.onTypeChanged(2);
          }
        }
      });
      KXTopTab localKXTopTab1 = new KXTopTab(getActivity());
      localKXTopTab1.setText(getResources().getString(2131428536).replace("*", this.mModel.getAllCommentNum() + this.mMyComment.size()));
      this.mTabHost.addTab(localKXTopTab1);
      KXTopTab localKXTopTab2 = new KXTopTab(getActivity());
      localKXTopTab2.setText(getResources().getString(2131428537).replace("*", this.mModel.getFriendCommentNum()));
      this.mTabHost.addTab(localKXTopTab2);
      this.mTabHost.setCurrentTab(0);
      localKXTopTabHost2.setOnTabChangeListener(new KXTopTabHost2.OnTabChangeListener()
      {
        public void beforeTabChanged(int paramInt)
        {
        }

        public void onTabChanged(int paramInt)
        {
          if (paramInt != FilmDetailFragment.this.mCurTabIndex)
          {
            FilmDetailFragment.this.mCurTabIndex = paramInt;
            if (paramInt != 0)
              break label44;
            FilmDetailFragment.this.onTypeChanged(1);
          }
          while (true)
          {
            FilmDetailFragment.this.mTabHost.setCurrentTab(paramInt, false);
            return;
            label44: FilmDetailFragment.this.onTypeChanged(2);
          }
        }
      });
      KXTopTab localKXTopTab3 = new KXTopTab(getActivity());
      localKXTopTab3.setText(getResources().getString(2131428536).replace("*", this.mModel.getAllCommentNum()));
      localKXTopTabHost2.addTab(localKXTopTab3);
      KXTopTab localKXTopTab4 = new KXTopTab(getActivity());
      localKXTopTab4.setText(getResources().getString(2131428537).replace("*", this.mModel.getFriendCommentNum()));
      localKXTopTabHost2.addTab(localKXTopTab4);
      localKXTopTabHost2.setCurrentTab(0);
      onTypeChanged(1);
      return;
      label728: this.mHeadView.findViewById(2131362271).setVisibility(0);
      localTextView.setVisibility(0);
      localTextView.setText(localFilmInfo.mCostTime);
    }
  }

  public void updateListData(int paramInt)
  {
    this.mFilmCommentList.clear();
    if ((this.mCurType == 1) && (this.mMyComment.size() > 0))
      addListToData(this.mMyComment);
    ArrayList localArrayList = getCommentList(this.mCurType);
    if (localArrayList != null)
      addListToData(localArrayList);
    if ((localArrayList != null) && (localArrayList.size() > 0) && (hasMore(this.mCurType)))
    {
      FilmCommentItem localFilmCommentItem = new FilmCommentItem();
      localFilmCommentItem.mFuid = "";
      this.mFilmCommentList.add(localFilmCommentItem);
    }
    addHeadView();
    addBottomView();
    if (this.mAdapter != null)
      this.mAdapter.notifyDataSetChanged();
  }

  class FilmDetailAdapter extends BaseAdapter
  {
    FilmDetailAdapter()
    {
    }

    public int getCount()
    {
      return FilmDetailFragment.this.mFilmCommentList.size();
    }

    public Object getItem(int paramInt)
    {
      return FilmDetailFragment.this.mFilmCommentList.get(paramInt);
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      FilmCommentItem localFilmCommentItem = (FilmCommentItem)FilmDetailFragment.this.mFilmCommentList.get(paramInt);
      if (TextUtils.isEmpty(localFilmCommentItem.mFuid))
        return FilmDetailFragment.this.mFooterView;
      if ("loading".equals(localFilmCommentItem.mFuid))
      {
        FilmDetailFragment.this.mListViewNodataProBar.setVisibility(0);
        FilmDetailFragment.this.mListViewNodataTV.setText(localFilmCommentItem.mComment);
        return FilmDetailFragment.this.mListViewNodataLayout;
      }
      if ("empty".equals(localFilmCommentItem.mFuid))
      {
        FilmDetailFragment.this.mListViewNodataProBar.setVisibility(8);
        FilmDetailFragment.this.mListViewNodataTV.setText(localFilmCommentItem.mComment);
        return FilmDetailFragment.this.mListViewNodataLayout;
      }
      if ("headview".equals(localFilmCommentItem.mFuid))
        return FilmDetailFragment.this.mHeadView;
      if ("headtab".equals(localFilmCommentItem.mFuid))
        return FilmDetailFragment.this.mHeadTab;
      if ("bottomspace".equals(localFilmCommentItem.mFuid))
      {
        int i = 1;
        if (FilmDetailFragment.this.mFilmCommentList.size() < 11)
          i = FilmDetailFragment.this.getResources().getDisplayMetrics().heightPixels;
        if (!FilmDetailFragment.this.mBottomSpaceInit)
          FilmDetailFragment.this.mBottomSpace.findViewById(2131362241).setLayoutParams(new RelativeLayout.LayoutParams(1, i));
        return FilmDetailFragment.this.mBottomSpace;
      }
      FilmDetailFragment.ViewHolder localViewHolder;
      if ((paramView == null) || (paramView == FilmDetailFragment.this.mFooterView) || (paramView == FilmDetailFragment.this.mListViewNodataLayout) || (paramView == FilmDetailFragment.this.mHeadView) || (paramView == FilmDetailFragment.this.mHeadTab) || (paramView == FilmDetailFragment.this.mBottomSpace))
      {
        paramView = FilmDetailFragment.this.getActivity().getLayoutInflater().inflate(2130903107, null);
        localViewHolder = new FilmDetailFragment.ViewHolder(FilmDetailFragment.this);
        localViewHolder.initView(paramView);
        paramView.setTag(localViewHolder);
      }
      while (true)
      {
        localViewHolder.updateView(localFilmCommentItem);
        FilmDetailFragment.this.recordListItemHei();
        return paramView;
        localViewHolder = (FilmDetailFragment.ViewHolder)paramView.getTag();
      }
    }
  }

  class GeDataTask extends KXFragment.KXAsyncTask<Integer, Void, Integer>
  {
    private boolean mGetDetail;
    private int mType;
    int num = 10;
    int start = 0;

    GeDataTask()
    {
      super();
    }

    protected Integer doInBackgroundA(Integer[] paramArrayOfInteger)
    {
      int i = paramArrayOfInteger[0].intValue();
      boolean bool = false;
      if (i == -1)
        bool = true;
      this.mGetDetail = bool;
      this.mType = paramArrayOfInteger[1].intValue();
      if (this.mGetDetail)
        return Integer.valueOf(FilmEngine.getInstance().getFilmDetail(FilmDetailFragment.this.getActivity().getApplicationContext(), FilmDetailFragment.this.mModel, FilmDetailFragment.this.mFid, this.start, this.num, this.mType));
      this.start = FilmDetailFragment.this.getCommentList(this.mType).size();
      return Integer.valueOf(FilmEngine.getInstance().getFilmCommentList(FilmDetailFragment.this.getActivity().getApplicationContext(), FilmDetailFragment.this.mModel, FilmDetailFragment.this.mFid, this.start, this.num, this.mType));
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      FilmDetailFragment.this.showWait(false);
      FilmDetailFragment.this.showLoadingFooter(false);
      if (paramInteger.intValue() == 1)
      {
        if (this.mGetDetail)
        {
          FilmDetailFragment.this.getDetailFinished(true);
          if (FilmDetailFragment.this.getView() != null)
            FilmDetailFragment.this.updateHeadView();
        }
        if ((this.mType == 1) && (this.start == 0) && (!this.mGetDetail))
          FilmDetailFragment.this.mMyComment.clear();
        FilmDetailFragment.this.updateListData(this.mType);
        if (FilmDetailFragment.this.mFilmCommentList.size() == 3)
          FilmDetailFragment.this.showListEmpty("还没有人发表影评");
        if ((!this.mGetDetail) && (this.start == 0))
          FilmDetailFragment.this.initListViewSelectIndex();
        return;
      }
      if (this.mGetDetail)
      {
        FilmDetailFragment.this.mWaitLayout.setVisibility(0);
        FilmDetailFragment.this.mWaitingProBar.setVisibility(8);
        FilmDetailFragment.this.mTvEmpty.setText("获取数据失败！");
        return;
      }
      String str = FilmDetailFragment.this.getResources().getString(2131427374);
      Toast.makeText(FilmDetailFragment.this.getActivity(), str, 1).show();
      FilmDetailFragment.this.showListEmpty("获取影评失败");
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
    TextView mContent;
    ImageView mGender;
    KXFrameImageView mLogo;
    TextView mName;
    TextView mTime;

    ViewHolder()
    {
    }

    public void initView(View paramView)
    {
      this.mLogo = ((KXFrameImageView)paramView.findViewById(2131362242));
      this.mGender = ((ImageView)paramView.findViewById(2131362244));
      this.mName = ((TextView)paramView.findViewById(2131362243));
      this.mTime = ((TextView)paramView.findViewById(2131362245));
      this.mContent = ((TextView)paramView.findViewById(2131362246));
    }

    public void updateView(FilmCommentItem paramFilmCommentItem)
    {
      this.mLogo.setFrameNinePatchResId(2130838099);
      this.mLogo.setOnClickListener(new View.OnClickListener(paramFilmCommentItem)
      {
        public void onClick(View paramView)
        {
          FilmDetailFragment.this.showHome(this.val$item.mFuid, this.val$item.mFname, this.val$item.mIcon90);
        }
      });
      FilmDetailFragment.this.displayRoundPicture(this.mLogo, paramFilmCommentItem.mIcon50, ImageDownloader.RoundCornerType.hdpi_big, 2130838334);
      String str = paramFilmCommentItem.mFname;
      if (str.length() > 6)
        str = str.substring(0, 6) + "...";
      this.mName.setText(str);
      this.mName.setOnClickListener(new View.OnClickListener(paramFilmCommentItem)
      {
        public void onClick(View paramView)
        {
          FilmDetailFragment.this.showHome(this.val$item.mFuid, this.val$item.mFname, this.val$item.mIcon90);
        }
      });
      this.mTime.setText(paramFilmCommentItem.mTime);
      this.mContent.setText(paramFilmCommentItem.mComment);
      if ((FilmDetailFragment.this.mCurType == 2) || (User.getInstance().getUID().equals(paramFilmCommentItem.mFuid)))
      {
        this.mGender.setVisibility(8);
        return;
      }
      this.mGender.setVisibility(0);
      if (paramFilmCommentItem.mGender != 1)
      {
        this.mGender.setImageResource(2130838186);
        return;
      }
      this.mGender.setImageResource(2130838200);
    }
  }

  class WanttoTask extends KXFragment.KXAsyncTask<Boolean, Void, Integer>
  {
    private boolean isWantto;

    WanttoTask()
    {
      super();
    }

    protected Integer doInBackgroundA(Boolean[] paramArrayOfBoolean)
    {
      this.isWantto = paramArrayOfBoolean[0].booleanValue();
      return Integer.valueOf(FilmEngine.getInstance().wantTo(FilmDetailFragment.this.getActivity().getApplicationContext(), FilmDetailFragment.this.mFid));
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      if (FilmDetailFragment.this.mProgressDialog != null)
      {
        FilmDetailFragment.this.mProgressDialog.dismiss();
        FilmDetailFragment.this.mProgressDialog = null;
      }
      if (paramInteger.intValue() == 1)
      {
        Toast.makeText(FilmDetailFragment.this.getActivity(), "发送成功", 1).show();
        FilmDetailFragment.this.mBottomBtnLayout.setVisibility(8);
        return;
      }
      Toast.makeText(FilmDetailFragment.this.getActivity(), "发送失败", 1).show();
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
 * Qualified Name:     com.kaixin001.fragment.FilmDetailFragment
 * JD-Core Version:    0.6.0
 */