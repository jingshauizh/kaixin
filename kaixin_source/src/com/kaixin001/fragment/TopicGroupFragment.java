package com.kaixin001.fragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.adapter.NewsItemAdapter;
import com.kaixin001.adapter.NewsItemAdapter.OnViewMoreClickListener;
import com.kaixin001.engine.TopicGroupEngine;
import com.kaixin001.item.NewsInfo;
import com.kaixin001.model.NewsModel;
import com.kaixin001.model.Setting;
import com.kaixin001.model.TopicModel;
import com.kaixin001.model.User;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.KXUBLog;
import com.kaixin001.view.ContentListWindow;
import com.kaixin001.view.ContentListWindow.DoSelect;
import com.kaixin001.view.PullToRefreshView;
import com.kaixin001.view.PullToRefreshView.PullToRefreshListener;
import com.kaixin001.view.media.KXMediaInfo;
import com.kaixin001.view.media.KXMediaManager;
import java.util.ArrayList;

public class TopicGroupFragment extends BaseFragment
  implements View.OnClickListener, AdapterView.OnItemLongClickListener, PullToRefreshView.PullToRefreshListener, AbsListView.OnScrollListener, ContentListWindow.DoSelect, PopupWindow.OnDismissListener
{
  public static int CHANGE_IS_MOR = 0;
  public static final String SEARCH = "search";
  private static final String TAG = "TopicGroupActivity";
  public static int TITLECOUNT = 7;
  private ImageView btnLeft;
  private ImageView btnRight;
  private GetTopicGroupTask getTopicGroupTask;
  private boolean isRefresh = false;
  private ListView lvHome;
  private NewsItemAdapter mAdapter;
  private int mCurrentSelectType = 301;
  protected PullToRefreshView mDownView;
  private boolean mNeedAutoLoading = false;
  private NewsItemAdapter.OnViewMoreClickListener mOnViewMoreClickListener = new NewsItemAdapter.OnViewMoreClickListener()
  {
    public void onViewMoreClick()
    {
      try
      {
        ArrayList localArrayList = TopicGroupFragment.this.topicModel.getTopicList();
        boolean bool;
        if (localArrayList != null)
        {
          int i = localArrayList.size();
          int j = TopicGroupFragment.this.newslist.size();
          bool = false;
          if (i >= j);
        }
        else
        {
          TopicGroupFragment.this.mAdapter.showLoadingFooter(true);
          bool = true;
        }
        TopicGroupFragment.this.getNewsData(bool, true);
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  };
  private ContentListWindow mPopupWindow;
  private String mSearch = null;
  private final ArrayList<NewsInfo> newslist = new ArrayList();
  private ProgressBar proBar;
  private ImageView rocordButton;
  private TopicModel topicModel = TopicModel.getInstance();
  private TextView tvEmpty;
  private LinearLayout waitLayout;

  private void getNewsData(boolean paramBoolean1, boolean paramBoolean2)
  {
    this.newslist.clear();
    int i = this.topicModel.total;
    ArrayList localArrayList = this.topicModel.getTopicList();
    if ((localArrayList == null) || (localArrayList.size() == 0))
      showLoading(false, 2131427412);
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
        if (j < i)
        {
          NewsInfo localNewsInfo1 = new NewsInfo();
          localNewsInfo1.mFuid = "";
          this.newslist.add(localNewsInfo1);
          if (paramBoolean2)
            refreshMore(paramBoolean1);
        }
        if (TextUtils.isEmpty(this.topicModel.detail))
          continue;
        NewsInfo localNewsInfo2 = new NewsInfo();
        localNewsInfo2.mFuid = "-3";
        this.newslist.add(0, localNewsInfo2);
      }
      catch (Exception localException)
      {
        KXLog.e("TopicGroupActivity", "getNewsData", localException);
      }
    }
  }

  private void initListView()
  {
    this.lvHome = ((ListView)findViewById(2131363722));
    this.lvHome.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
      {
        TopicGroupFragment.this.onListItemClick(paramView, paramInt, paramLong);
      }
    });
    this.lvHome.setOnItemLongClickListener(this);
    this.mAdapter = new NewsItemAdapter(this, this.newslist);
    this.mAdapter.setOnViewMoreClickListener(this.mOnViewMoreClickListener);
    this.lvHome.setAdapter(this.mAdapter);
    this.lvHome.setOnScrollListener(this);
  }

  private void initTitle()
  {
    findViewById(2131363716).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        TopicGroupFragment.this.lvHome.setSelection(0);
      }
    });
    this.btnRight = ((ImageView)findViewById(2131362928));
    this.btnRight.setImageResource(2130838134);
    this.btnRight.setOnClickListener(this);
    this.btnLeft = ((ImageView)findViewById(2131362914));
    this.btnLeft.setOnClickListener(this);
    ImageView localImageView = (ImageView)findViewById(2131362919);
    localImageView.setImageResource(2130839036);
    ((ImageView)findViewById(2131362919)).setVisibility(8);
    TextView localTextView = (TextView)findViewById(2131362920);
    localTextView.setText("#" + this.mSearch + "#");
    localTextView.setEllipsize(TextUtils.TruncateAt.MIDDLE);
    localTextView.setMaxWidth(dipToPx(220.0F));
    localTextView.setVisibility(0);
    localImageView.setVisibility(8);
  }

  private void onListItemClick(View paramView, int paramInt, long paramLong)
  {
    NewsInfo localNewsInfo;
    String str;
    try
    {
      if (this.newslist == null)
        return;
      localNewsInfo = (NewsInfo)this.newslist.get((int)paramLong);
      if (TextUtils.isEmpty(localNewsInfo.mFuid))
        return;
      if (User.getInstance().GetLookAround())
        this.mAdapter.showLoginDialog();
      this.mAdapter.setCurrentNewsInfo(localNewsInfo);
      str = localNewsInfo.mNtype;
      if ("1088".equals(str))
        return;
      if ("1072".equals(str))
      {
        this.mAdapter.showTruth(localNewsInfo);
        return;
      }
    }
    catch (Exception localException)
    {
      KXLog.e("TopicGroupActivity", "onListItemClick", localException);
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
    if ("2".equals(str))
    {
      this.mAdapter.showCommentDetail(localNewsInfo);
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
      this.mAdapter.showCommentDetail(localNewsInfo);
  }

  private void refresh()
  {
    if (!super.checkNetworkAndHint(true))
    {
      if ((this.mDownView != null) && (this.mDownView.isFrefrshing()))
        this.mDownView.onRefreshComplete();
      showLoading(false, -1);
      return;
    }
    this.isRefresh = true;
    showLoading(true, -1);
    refreshData(this.mSearch, 0, 20);
  }

  private void refreshData(String paramString, int paramInt1, int paramInt2)
  {
    if ((this.getTopicGroupTask != null) && (!this.getTopicGroupTask.isCancelled()) && (this.getTopicGroupTask.getStatus() != AsyncTask.Status.FINISHED))
      return;
    String[] arrayOfString = new String[4];
    arrayOfString[0] = paramString;
    arrayOfString[1] = Integer.toString(paramInt1);
    arrayOfString[2] = Integer.toString(paramInt2);
    arrayOfString[3] = User.getInstance().getUID();
    this.getTopicGroupTask = new GetTopicGroupTask(null);
    this.getTopicGroupTask.execute(arrayOfString);
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
      while ((this.getTopicGroupTask != null) && (!this.getTopicGroupTask.isCancelled()) && (this.getTopicGroupTask.getStatus() != AsyncTask.Status.FINISHED));
      i = this.topicModel.total;
      ArrayList localArrayList = this.topicModel.getTopicList();
      j = 0;
      if (localArrayList == null)
        continue;
      j = localArrayList.size();
    }
    while ((i <= 0) || (j >= i));
    refreshData(this.mSearch, j, 20);
  }

  private void showLoading(boolean paramBoolean, int paramInt)
  {
    if (paramBoolean)
      if (this.newslist.size() == 0)
      {
        this.lvHome.setVisibility(8);
        this.waitLayout.setVisibility(0);
        this.proBar.setVisibility(0);
        this.tvEmpty.setText(2131427599);
        this.tvEmpty.setVisibility(0);
      }
    do
    {
      return;
      this.waitLayout.setVisibility(8);
      this.lvHome.setVisibility(0);
      return;
    }
    while (paramBoolean);
    if (this.newslist.size() == 0)
    {
      this.lvHome.setVisibility(8);
      if (paramInt >= 0)
      {
        this.waitLayout.setVisibility(0);
        this.proBar.setVisibility(8);
        this.tvEmpty.setText(paramInt);
        this.tvEmpty.setVisibility(0);
        return;
      }
      this.waitLayout.setVisibility(8);
      return;
    }
    this.waitLayout.setVisibility(8);
    this.lvHome.setVisibility(0);
  }

  private void showPopUpWindow(View paramView)
  {
    View localView = ((LayoutInflater)getActivity().getSystemService("layout_inflater")).inflate(2130903239, null, false);
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
    float f = getResources().getDisplayMetrics().density;
    int i = getResources().getConfiguration().orientation;
    Resources localResources = getResources();
    int[] arrayOfInt1 = { 2130839076, 2130839077 };
    ContentListWindow.mEditContent = new String[2];
    ContentListWindow.mEditContent[0] = localResources.getString(2131428533);
    ContentListWindow.mEditContent[1] = localResources.getString(2131428534);
    if (i == 1);
    for (this.mPopupWindow = new ContentListWindow(localView, (int)(180.0F * f), -2, true, getActivity(), this.mCurrentSelectType, arrayOfInt1); ; this.mPopupWindow = new ContentListWindow(localView, (int)(180.0F * f), -2, true, getActivity(), this.mCurrentSelectType, arrayOfInt1))
    {
      this.mPopupWindow.setDoSelectListener(this);
      this.mPopupWindow.setOnDismissListener(this);
      this.mPopupWindow.setOutsideTouchable(true);
      this.mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
      this.mPopupWindow.setClippingEnabled(false);
      int[] arrayOfInt2 = new int[2];
      paramView.getLocationInWindow(arrayOfInt2);
      int j = 6 * (int)f;
      this.mPopupWindow.showAtLocation(paramView, 53, -4, 6 + (arrayOfInt2[1] + paramView.getHeight() - j));
      return;
    }
  }

  private void showUpdateTime()
  {
    String str1 = NewsModel.getInstance().getUpdateTime();
    if ((!TextUtils.isEmpty(str1)) && (getActivity() != null))
    {
      String str2 = getResources().getString(2131427404);
      this.mDownView.setUpdateTime(str2 + " " + str1);
    }
  }

  public void doSelect(int paramInt)
  {
    switch (paramInt)
    {
    default:
      return;
    case 0:
      Intent localIntent = new Intent(getActivity(), WriteWeiboFragment.class);
      Bundle localBundle2 = new Bundle();
      localBundle2.putString("content", "#" + this.mSearch + "#");
      localIntent.putExtras(localBundle2);
      AnimationUtil.startFragment(this, localIntent, 1);
      return;
    case 1:
    }
    showPopUpWindow(this.btnRight);
    Bundle localBundle1 = new Bundle();
    localBundle1.putString("content", "#" + this.mSearch + "#");
    IntentUtil.showVoiceRecordFragment(this, localBundle1);
  }

  public void finish()
  {
    super.finish();
    AnimationUtil.finishActivity(getActivity(), 2);
  }

  public void onClick(View paramView)
  {
    if (paramView.equals(this.rocordButton))
    {
      Intent localIntent = new Intent(getActivity(), WriteWeiboFragment.class);
      Bundle localBundle = new Bundle();
      localBundle.putString("content", "#" + this.mSearch + "#");
      localIntent.putExtras(localBundle);
      AnimationUtil.startFragment(this, localIntent, 1);
    }
    do
    {
      return;
      if (!paramView.equals(this.btnRight))
        continue;
      showPopUpWindow(this.btnRight);
      return;
    }
    while (!paramView.equals(this.btnLeft));
    finish();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903345, paramViewGroup, false);
  }

  public void onDestroy()
  {
    cancelTask(this.getTopicGroupTask);
    super.onDestroy();
  }

  public void onDismiss()
  {
  }

  public boolean onItemLongClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    while (true)
    {
      try
      {
        if (!Setting.getInstance().getCType().startsWith("06203"))
          continue;
        if (this.newslist == null)
        {
          return false;
          Vibrator localVibrator = (Vibrator)getActivity().getSystemService("vibrator");
          localVibrator.vibrate(1000L);
          localVibrator.vibrate(new long[] { 100L, 300L, 150L, 400L }, -1);
          continue;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("TopicGroupActivity", "onListItemClick", localException);
        return true;
      }
      if ((paramLong < 0L) || (paramLong >= this.newslist.size()))
        break;
      NewsInfo localNewsInfo = (NewsInfo)this.newslist.get((int)paramLong);
      if (TextUtils.isEmpty(localNewsInfo.mFuid))
        break;
      if ("1088".equals(localNewsInfo.mNtype))
      {
        this.mAdapter.onRepostLongClicked(localNewsInfo);
        continue;
      }
      if ("3".equals(localNewsInfo.mNtype))
      {
        this.mAdapter.onStateLongClicked(localNewsInfo);
        continue;
      }
      if ("1018".equals(localNewsInfo.mNtype))
      {
        this.mAdapter.onRecordLongClicked(localNewsInfo);
        continue;
      }
      if ("2".equals(localNewsInfo.mNtype))
      {
        this.mAdapter.onDiaryLongClicked(localNewsInfo);
        continue;
      }
      if ("1016".equals(localNewsInfo.mNtype))
      {
        this.mAdapter.onVoteLongClicked(localNewsInfo);
        continue;
      }
      if ("1".equals(localNewsInfo.mNtype))
      {
        this.mAdapter.onImageLongClicked(localNewsInfo);
        continue;
      }
      if ("1242".equals(localNewsInfo.mNtype))
      {
        if ("转发照片专辑".equals(localNewsInfo.mNtypename))
        {
          this.mAdapter.onRepostAlbumLongClicked(localNewsInfo);
          continue;
        }
        if ("转发照片".equals(localNewsInfo.mNtypename))
        {
          this.mAdapter.onRepostPhotoLongClicked(localNewsInfo);
          continue;
        }
        if ("转发投票".equals(localNewsInfo.mNtypename))
        {
          this.mAdapter.onVoteLongClicked(localNewsInfo);
          continue;
        }
        this.mAdapter.onOtherLongClicked(localNewsInfo);
        continue;
      }
      this.mAdapter.onOtherLongClicked(localNewsInfo);
    }
    return false;
  }

  public void onScroll(AbsListView paramAbsListView, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = -1 + (paramInt1 + paramInt2);
    int j = -1 + this.mAdapter.getCount();
    Log.i("TopicGroupActivity", paramInt1);
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

  public void onUpdate()
  {
    refresh();
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    User.getInstance().loadDataIfEmpty(getActivity().getApplicationContext());
    Bundle localBundle = getArguments();
    if (localBundle != null);
    for (this.mSearch = localBundle.getString("search"); ; this.mSearch = "")
    {
      initTitle();
      this.tvEmpty = ((TextView)findViewById(2131363719));
      this.proBar = ((ProgressBar)findViewById(2131363718));
      this.waitLayout = ((LinearLayout)findViewById(2131363717));
      this.mDownView = ((PullToRefreshView)findViewById(2131363721));
      this.mDownView.setPullToRefreshListener(this);
      initListView();
      if (!dataInited())
      {
        showLoading(true, -1);
        refresh();
      }
      if (this.lvHome != null)
        this.lvHome.requestFocus();
      KXUBLog.log("homepage_dynamic");
      return;
    }
  }

  private class GetTopicGroupTask extends KXFragment.KXAsyncTask<String, Void, Integer>
  {
    private int mStart = -1;

    private GetTopicGroupTask()
    {
      super();
    }

    protected Integer doInBackgroundA(String[] paramArrayOfString)
    {
      if (paramArrayOfString.length < 4)
        return Integer.valueOf(-1);
      try
      {
        this.mStart = Integer.parseInt(paramArrayOfString[1]);
      }
      catch (Exception localException1)
      {
        try
        {
          while (TopicGroupEngine.getInstance().getTopicGroupData(TopicGroupFragment.this.getActivity().getApplicationContext(), TopicModel.getInstance(), paramArrayOfString[0], paramArrayOfString[1], paramArrayOfString[2], paramArrayOfString[3]))
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
          KXLog.e("TopicGroupActivity", "GetTopicGroupTask", localException2);
        }
      }
      return Integer.valueOf(-1);
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      try
      {
        TopicGroupFragment.this.proBar.setVisibility(8);
        if ((paramInteger.intValue() == -1) && (this.mStart == 0))
        {
          Toast.makeText(TopicGroupFragment.this.getActivity(), 2131427374, 0).show();
          TopicGroupFragment.this.tvEmpty.setVisibility(8);
        }
        while (TopicGroupFragment.this.mAdapter == null)
        {
          KXLog.e("TopicGroupActivity", "Adapter in finally == null");
          if ((TopicGroupFragment.this.mDownView != null) && (TopicGroupFragment.this.mDownView.isFrefrshing()))
            TopicGroupFragment.this.mDownView.onRefreshComplete();
          TopicGroupFragment.this.showUpdateTime();
          return;
          ArrayList localArrayList = TopicGroupFragment.this.topicModel.getTopicList();
          if ((paramInteger.intValue() < 0) || (localArrayList == null))
            continue;
          if (TopicGroupFragment.this.mAdapter == null)
            KXLog.e("TopicGroupActivity", "Adapter == null");
          if ((this.mStart != 0) && (TopicGroupFragment.this.newslist.size() != 0) && ((TopicGroupFragment.this.mAdapter == null) || (!TopicGroupFragment.this.mAdapter.isFooterShowLoading())))
            continue;
          TopicGroupFragment.this.getTopicGroupTask = null;
          TopicGroupFragment.this.getNewsData(false, false);
        }
      }
      catch (Exception localException)
      {
        KXLog.e("TopicGroupActivity", "onPostExecute", localException);
        if (TopicGroupFragment.this.mAdapter == null)
          KXLog.e("TopicGroupActivity", "Adapter in finally == null");
        while (true)
        {
          if ((TopicGroupFragment.this.mDownView != null) && (TopicGroupFragment.this.mDownView.isFrefrshing()))
            TopicGroupFragment.this.mDownView.onRefreshComplete();
          TopicGroupFragment.this.showUpdateTime();
          return;
          TopicGroupFragment.this.mAdapter.showLoadingFooter(false);
        }
      }
      finally
      {
        while (true)
        {
          if (TopicGroupFragment.this.mAdapter == null)
            KXLog.e("TopicGroupActivity", "Adapter in finally == null");
          while (true)
          {
            if ((TopicGroupFragment.this.mDownView != null) && (TopicGroupFragment.this.mDownView.isFrefrshing()))
              TopicGroupFragment.this.mDownView.onRefreshComplete();
            TopicGroupFragment.this.showUpdateTime();
            throw localObject;
            TopicGroupFragment.this.mAdapter.showLoadingFooter(false);
          }
          TopicGroupFragment.this.mAdapter.showLoadingFooter(false);
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
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.TopicGroupFragment
 * JD-Core Version:    0.6.0
 */