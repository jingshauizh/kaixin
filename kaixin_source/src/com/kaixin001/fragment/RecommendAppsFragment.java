package com.kaixin001.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.NinePatch;
import android.net.Uri;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.activity.UpgradeDialogActivity;
import com.kaixin001.businesslogic.ThirdPartyAppUtil;
import com.kaixin001.engine.GameBannerEngine;
import com.kaixin001.engine.NavigatorApplistEngine;
import com.kaixin001.engine.NavigatorApplistEngine.AppItem;
import com.kaixin001.engine.NavigatorApplistEngine.KXAppItem;
import com.kaixin001.engine.NavigatorApplistEngine.ThirdAppItem;
import com.kaixin001.engine.RecommendAppNotifyEngine;
import com.kaixin001.item.AdvGameItem;
import com.kaixin001.model.AdvGameModel;
import com.kaixin001.model.KaixinModelTemplate;
import com.kaixin001.model.User;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.ImageDownloader.RoundCornerType;
import com.kaixin001.util.UserHabitUtils;
import com.kaixin001.view.KXFrameImageView;
import com.kaixin001.view.KXNewsBarForGameView;
import com.kaixin001.view.KXTopTab;
import com.kaixin001.view.KXTopTabHost;
import com.kaixin001.view.KXTopTabHost.OnTabChangeListener;
import com.kaixin001.view.PullToRefreshView;
import com.kaixin001.view.PullToRefreshView.PullToRefreshListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RecommendAppsFragment extends BaseFragment
  implements View.OnClickListener, PullToRefreshView.PullToRefreshListener, KXTopTabHost.OnTabChangeListener
{
  private static final int TAB_KAIXIN_APPS = 1;
  private static final int TAB_KAIXIN_GAMES;
  private ArrayList<AdvGameItem> items;
  private RecommendAppsAdapter mAdapter;
  private HashMap<String, Boolean> mAppState = new HashMap();
  private ArrayList<NavigatorApplistEngine.AppItem> mApps = new ArrayList();
  private int mCurTabIndex = -1;
  private PullToRefreshView mDownView;
  private ProgressBar mFooterProBar;
  private TextView mFooterTV;
  private View mFooterView;
  private GetKXAppsDataTask mGetKXAppsTask;
  private GetKXGamesDataTask mGetKXGamesTask;
  private boolean mHasMore;
  private ListView mListView;
  private View mLoadingView;
  private boolean[] mShowMore = new boolean[2];
  private KXTopTabHost mTabHost;
  private RelativeLayout mTopBarLayout;
  private int newAppCount = 0;
  private int newGameCount = 0;

  private void addItemsData(String paramString1, ArrayList<NavigatorApplistEngine.KXAppItem> paramArrayList, String paramString2, ArrayList<NavigatorApplistEngine.ThirdAppItem> paramArrayList1, boolean paramBoolean)
  {
    new NavigatorApplistEngine.AppItem();
    NavigatorApplistEngine.AppItem localAppItem1 = new NavigatorApplistEngine.AppItem();
    localAppItem1.setName(paramString1);
    localAppItem1.setUrl(null);
    localAppItem1.setLogo(null);
    this.mApps.add(localAppItem1);
    if (paramBoolean)
      this.mApps.addAll(paramArrayList);
    while (paramArrayList1.size() == 0)
    {
      return;
      if ((paramArrayList.size() <= 2) || (paramArrayList1.size() == 0))
      {
        this.mApps.addAll(paramArrayList);
        continue;
      }
      if (paramArrayList.size() <= 2)
        continue;
      for (int i = 0; ; i++)
      {
        if (i >= 2)
        {
          NavigatorApplistEngine.AppItem localAppItem3 = new NavigatorApplistEngine.AppItem();
          localAppItem3.setName(getResources().getString(2131428344));
          localAppItem3.setUrl("");
          localAppItem3.setLogo(null);
          this.mApps.add(localAppItem3);
          this.mHasMore = false;
          break;
        }
        this.mApps.add((NavigatorApplistEngine.AppItem)paramArrayList.get(i));
      }
    }
    NavigatorApplistEngine.AppItem localAppItem2 = new NavigatorApplistEngine.AppItem();
    localAppItem2.setName(paramString2);
    localAppItem2.setUrl(null);
    localAppItem2.setLogo(null);
    this.mApps.add(localAppItem2);
    this.mApps.addAll(paramArrayList1);
  }

  private void cancelTask()
  {
    if ((this.mGetKXGamesTask != null) && (!this.mGetKXGamesTask.isCancelled()) && (this.mGetKXGamesTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.mGetKXGamesTask.cancel(true);
      NavigatorApplistEngine.getInstance().cancel();
      this.mGetKXGamesTask = null;
    }
    if ((this.mGetKXAppsTask != null) && (!this.mGetKXAppsTask.isCancelled()) && (this.mGetKXAppsTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.mGetKXAppsTask.cancel(true);
      NavigatorApplistEngine.getInstance().cancel();
      this.mGetKXAppsTask = null;
    }
  }

  private void countRecommendAppGameUserHabit(NavigatorApplistEngine.ThirdAppItem paramThirdAppItem)
  {
    switch (paramThirdAppItem.getType())
    {
    default:
      return;
    case 2:
      UserHabitUtils.getInstance(getActivity().getApplicationContext()).addUserHabit("RecommendApp_download_" + paramThirdAppItem.getName());
      return;
    case 1:
    }
    UserHabitUtils.getInstance(getActivity().getApplicationContext()).addUserHabit("RecommendGame_download_" + paramThirdAppItem.getName());
  }

  private void getMoreApps(boolean paramBoolean)
  {
    if (!super.checkNetworkAndHint(paramBoolean))
      showLoadingFooter(false);
    do
    {
      return;
      if (this.mTabHost.getCurrentTab() != 0)
        continue;
      loadKXGamesData(false);
      return;
    }
    while (this.mTabHost.getCurrentTab() != 1);
    loadKXAppsData(false);
  }

  private void initTabHost(View paramView)
  {
    this.mTabHost = ((KXTopTabHost)paramView.findViewById(2131363491));
    this.mTabHost.setOnTabChangeListener(this);
    KXTopTab localKXTopTab1 = new KXTopTab(getActivity());
    localKXTopTab1.setText(2131428337);
    this.mTabHost.addTab(localKXTopTab1);
    KXTopTab localKXTopTab2 = new KXTopTab(getActivity());
    localKXTopTab2.setText(2131428336);
    this.mTabHost.addTab(localKXTopTab2);
    if ((this.newAppCount > 0) || (this.newGameCount > 0))
    {
      Bitmap localBitmap = BitmapFactory.decodeResource(getResources(), 2130838237);
      NinePatch localNinePatch = new NinePatch(localBitmap, localBitmap.getNinePatchChunk(), null);
      if (this.newAppCount > 0)
      {
        localKXTopTab2.setRightText(String.valueOf(this.newAppCount));
        localKXTopTab2.setRightIcon(localNinePatch);
      }
      if (this.newGameCount > 0)
      {
        localKXTopTab1.setRightText(String.valueOf(this.newGameCount));
        localKXTopTab1.setRightIcon(localNinePatch);
      }
    }
  }

  private void initView(View paramView)
  {
    setTitleBar(paramView);
    intMoreItem();
    this.mDownView = ((PullToRefreshView)paramView.findViewById(2131363493));
    this.mDownView.setPullToRefreshListener(this);
    this.mListView = ((ListView)paramView.findViewById(2131363494));
    if (this.mAdapter == null)
      this.mAdapter = new RecommendAppsAdapter();
    this.mListView.setAdapter(this.mAdapter);
    this.mListView.requestFocus();
    this.mLoadingView = paramView.findViewById(2131363495);
  }

  private void intMoreItem()
  {
    this.mFooterView = getActivity().getLayoutInflater().inflate(2130903259, null);
    this.mFooterTV = ((TextView)this.mFooterView.findViewById(2131362073));
    this.mFooterProBar = ((ProgressBar)this.mFooterView.findViewById(2131362072));
    this.mFooterView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        if (!NavigatorApplistEngine.getInstance().result3dAppList.isGetAllData())
        {
          RecommendAppsFragment.this.showLoadingFooter(true);
          RecommendAppsFragment.this.getMoreApps(true);
          return;
        }
        RecommendAppsFragment.this.updateData(true);
        RecommendAppsFragment.this.mShowMore[RecommendAppsFragment.this.mCurTabIndex] = 1;
        RecommendAppsFragment.this.mAdapter.notifyDataSetChanged();
      }
    });
    ((TextView)this.mFooterView.findViewById(2131362073)).setText(2131427748);
    ((ProgressBar)this.mFooterView.findViewById(2131362072)).setVisibility(4);
  }

  private boolean isAppExist(String paramString)
  {
    boolean bool1 = TextUtils.isEmpty(paramString);
    boolean bool2 = false;
    if (!bool1)
    {
      if (this.mAppState.containsKey(paramString))
        bool2 = ((Boolean)this.mAppState.get(paramString)).booleanValue();
    }
    else
      return bool2;
    boolean bool3 = ThirdPartyAppUtil.isApkExist(getActivity(), paramString);
    this.mAppState.put(paramString, Boolean.valueOf(bool3));
    return bool3;
  }

  private boolean isIn3Days(String paramString)
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date localDate1 = new Date();
    try
    {
      Date localDate2 = localSimpleDateFormat.parse(paramString);
      long l = (localDate1.getTime() - localDate2.getTime()) / 86400000L;
      f = (float)l;
      if (f <= 3.0F)
        return true;
    }
    catch (Exception localException)
    {
      while (true)
      {
        localException.printStackTrace();
        float f = 0.0F;
      }
    }
    return false;
  }

  private void loadKXAppsData(boolean paramBoolean)
  {
    if ((this.mGetKXAppsTask != null) && (!this.mGetKXAppsTask.isCancelled()) && (this.mGetKXAppsTask.getStatus() != AsyncTask.Status.FINISHED))
      return;
    this.mGetKXAppsTask = new GetKXAppsDataTask(getActivity());
    this.mGetKXAppsTask.execute(new Void[0]);
  }

  private void loadKXGamesData(boolean paramBoolean)
  {
    if ((this.mGetKXGamesTask != null) && (!this.mGetKXGamesTask.isCancelled()) && (this.mGetKXGamesTask.getStatus() != AsyncTask.Status.FINISHED))
      return;
    this.mGetKXGamesTask = new GetKXGamesDataTask(getActivity());
    this.mGetKXGamesTask.execute(new Void[0]);
  }

  private void setTopBar(String paramString)
  {
    this.mTopBarLayout.removeAllViews();
    if (paramString.equals("3"))
      this.items = AdvGameModel.getInstance().getAppBannerItems();
    while ((this.items != null) && (this.items.size() != 0))
    {
      this.mTopBarLayout.setVisibility(0);
      KXNewsBarForGameView localKXNewsBarForGameView = new KXNewsBarForGameView(this, paramString);
      localKXNewsBarForGameView.fromPicAction = false;
      localKXNewsBarForGameView.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
      this.mTopBarLayout.addView(localKXNewsBarForGameView);
      return;
      if (!paramString.equals("2"))
        continue;
      this.items = AdvGameModel.getInstance().getGameBannerItems();
    }
    this.mTopBarLayout.setVisibility(8);
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

  private void showLoadingView(boolean paramBoolean)
  {
    View localView;
    if (this.mLoadingView != null)
    {
      localView = this.mLoadingView;
      if (!paramBoolean)
        break label24;
    }
    label24: for (int i = 0; ; i = 8)
    {
      localView.setVisibility(i);
      return;
    }
  }

  public void beforeTabChanged(int paramInt)
  {
    if (paramInt == 0)
      this.newGameCount = 0;
    while (true)
    {
      this.mTabHost.clean();
      initTabHost(getView());
      return;
      this.newAppCount = 0;
    }
  }

  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    default:
      return;
    case 2131362914:
    }
    if (super.isMenuListVisibleInMain())
    {
      super.showSubActivityInMain();
      return;
    }
    super.showMenuListInMain();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    UserHabitUtils.getInstance(getActivity()).addUserHabit("visit_games");
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903307, paramViewGroup, false);
  }

  public void onDestroy()
  {
    cancelTask();
    super.onDestroy();
  }

  public void onDestroyView()
  {
    this.mCurTabIndex = this.mTabHost.getCurrentTab();
    super.onDestroyView();
  }

  public void onPause()
  {
    super.onPause();
  }

  public void onTabChanged(int paramInt)
  {
    this.mCurTabIndex = paramInt;
    updateData(this.mShowMore[this.mCurTabIndex]);
    setTopBar(String.valueOf(2 + this.mCurTabIndex));
    this.mTabHost.getCurrentTab();
  }

  public void onUpdate()
  {
    if (this.mTabHost.getCurrentTab() == 0)
      loadKXGamesData(true);
    do
      return;
    while (this.mTabHost.getCurrentTab() != 1);
    loadKXAppsData(true);
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    this.mTopBarLayout = ((RelativeLayout)findViewById(2131363492));
    initView(paramView);
    this.newAppCount = RecommendAppNotifyEngine.getInstance().getNewAppCount();
    this.newGameCount = RecommendAppNotifyEngine.getInstance().getNewGameCount();
    RecommendAppNotifyEngine.getInstance().clearNewCount(getActivity());
    initTabHost(paramView);
    if ((this.mCurTabIndex < 0) || (this.mCurTabIndex > 1))
      this.mCurTabIndex = 0;
    setTopBar(String.valueOf(2 + this.mCurTabIndex));
    this.mTabHost.setCurrentTab(this.mCurTabIndex);
    if ((getActivity() != null) || (getView() != null))
      updateData(this.mShowMore[this.mCurTabIndex]);
  }

  protected void setTitleBar(View paramView)
  {
    ImageView localImageView = (ImageView)paramView.findViewById(2131362914);
    localImageView.setVisibility(0);
    localImageView.setOnClickListener(this);
    ((ImageView)paramView.findViewById(2131362928)).setVisibility(8);
    ((ImageView)paramView.findViewById(2131362919)).setVisibility(8);
    TextView localTextView = (TextView)paramView.findViewById(2131362920);
    localTextView.setVisibility(0);
    localTextView.setText(getResources().getText(2131428332));
    findViewById(2131362931).setVisibility(8);
  }

  public void updateData(boolean paramBoolean)
  {
    updateView(paramBoolean);
    int i = 0;
    if (!paramBoolean)
      if (AdvGameModel.getInstance().getGameBannerItems() != null)
      {
        int j = AdvGameModel.getInstance().getGameBannerItems().size();
        i = 0;
        if (j != 0);
      }
      else
      {
        i = 1;
      }
    if (this.mTabHost.getCurrentTab() == 0)
    {
      UserHabitUtils.getInstance(getActivity()).addUserHabit("visit_games");
      if ((this.mApps.size() <= 2) || (i != 0))
      {
        showLoadingView(true);
        loadKXGamesData(paramBoolean);
      }
    }
    do
    {
      do
        return;
      while (this.mTabHost.getCurrentTab() != 1);
      UserHabitUtils.getInstance(getActivity()).addUserHabit("visit_apps");
    }
    while ((this.mApps.size() > 0) && (i == 0));
    showLoadingView(true);
    loadKXAppsData(paramBoolean);
  }

  public void updateView(boolean paramBoolean)
  {
    this.mApps.clear();
    if (isNeedReturn())
      return;
    switch (this.mTabHost.getCurrentTab())
    {
    default:
    case 0:
    case 1:
    }
    while (true)
    {
      this.mAdapter.notifyDataSetChanged();
      return;
      if ((NavigatorApplistEngine.getInstance().resultKXGamesList == null) || (NavigatorApplistEngine.getInstance().recommend3dGamesList == null))
        continue;
      String str3 = getResources().getString(2131428340);
      ArrayList localArrayList3 = NavigatorApplistEngine.getInstance().resultKXGamesList;
      String str4 = getResources().getString(2131428341);
      ArrayList localArrayList4 = NavigatorApplistEngine.getInstance().recommend3dGamesList.getItemList();
      this.items = AdvGameModel.getInstance().getGameBannerItems();
      addItemsData(str3, localArrayList3, str4, localArrayList4, paramBoolean);
      continue;
      if ((NavigatorApplistEngine.getInstance().resultKXAppsList == null) || (NavigatorApplistEngine.getInstance().recommend3dAppsList == null))
        continue;
      String str1 = getResources().getString(2131428342);
      ArrayList localArrayList1 = NavigatorApplistEngine.getInstance().resultKXAppsList;
      String str2 = getResources().getString(2131428343);
      ArrayList localArrayList2 = NavigatorApplistEngine.getInstance().recommend3dAppsList.getItemList();
      this.items = AdvGameModel.getInstance().getAppBannerItems();
      addItemsData(str1, localArrayList1, str2, localArrayList2, paramBoolean);
    }
  }

  class GetKXAppsDataTask extends KXFragment.KXAsyncTask<Void, Void, Integer>
  {
    Context context;
    boolean isLoadCache = false;
    int num = 20;
    int start = 0;

    public GetKXAppsDataTask(Context arg2)
    {
      super();
      Object localObject;
      this.context = localObject;
    }

    protected Integer doInBackgroundA(Void[] paramArrayOfVoid)
    {
      try
      {
        GameBannerEngine.getInstance().getBannerData(this.context, "3");
        NavigatorApplistEngine.getInstance().getKXAppslistData(this.context);
        Integer localInteger = NavigatorApplistEngine.getInstance().getThirdApplistData(this.context, this.start, this.num, this.isLoadCache);
        return localInteger;
      }
      catch (Exception localException)
      {
      }
      return null;
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      if ((paramInteger == null) || (RecommendAppsFragment.this.getView() == null) || (RecommendAppsFragment.this.getActivity() == null))
        return;
      if ((RecommendAppsFragment.this.mDownView != null) && (RecommendAppsFragment.this.mDownView.isFrefrshing()))
        RecommendAppsFragment.this.mDownView.onRefreshComplete();
      RecommendAppsFragment.this.showLoadingView(false);
      RecommendAppsFragment.this.showLoadingFooter(false);
      if (paramInteger.intValue() == 1)
      {
        RecommendAppsFragment.this.updateView(false);
        if ((AdvGameModel.getInstance().getAppBannerItems() == null) || (AdvGameModel.getInstance().getAppBannerItems().size() == 0))
          break label223;
        RecommendAppsFragment.this.setTopBar(String.valueOf("3"));
      }
      while (true)
      {
        if (!this.isLoadCache)
          PreferenceManager.getDefaultSharedPreferences(RecommendAppsFragment.this.getActivity()).edit().putLong("loadThirdAppFinishedTime" + this.start + "_" + this.num, System.currentTimeMillis()).commit();
        if (paramInteger.intValue() != 1)
          Toast.makeText(RecommendAppsFragment.this.getActivity(), 2131428361, 0).show();
        RecommendAppsFragment.this.mGetKXAppsTask = null;
        return;
        label223: RecommendAppsFragment.this.mTopBarLayout.setVisibility(8);
      }
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  class GetKXGamesDataTask extends KXFragment.KXAsyncTask<Void, Void, Integer>
  {
    Context context;
    boolean isLoadCache = false;
    int num = 20;
    int start = 0;

    public GetKXGamesDataTask(Context arg2)
    {
      super();
      Object localObject;
      this.context = localObject;
    }

    protected Integer doInBackgroundA(Void[] paramArrayOfVoid)
    {
      try
      {
        GameBannerEngine.getInstance().getBannerData(this.context, "2");
        NavigatorApplistEngine.getInstance().getKXGameslistData(this.context);
        Integer localInteger = NavigatorApplistEngine.getInstance().getThirdApplistData(this.context, this.start, this.num, this.isLoadCache);
        return localInteger;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
      return null;
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      if ((paramInteger == null) || (RecommendAppsFragment.this.getView() == null) || (RecommendAppsFragment.this.getActivity() == null))
        return;
      if ((RecommendAppsFragment.this.mDownView != null) && (RecommendAppsFragment.this.mDownView.isFrefrshing()))
        RecommendAppsFragment.this.mDownView.onRefreshComplete();
      RecommendAppsFragment.this.showLoadingView(false);
      RecommendAppsFragment.this.showLoadingFooter(false);
      if (paramInteger.intValue() == 1)
      {
        RecommendAppsFragment.this.updateView(false);
        if ((AdvGameModel.getInstance().getGameBannerItems() == null) || (AdvGameModel.getInstance().getGameBannerItems().size() == 0))
          break label223;
        RecommendAppsFragment.this.setTopBar(String.valueOf("2"));
      }
      while (true)
      {
        if (!this.isLoadCache)
          PreferenceManager.getDefaultSharedPreferences(RecommendAppsFragment.this.getActivity()).edit().putLong("loadThirdAppFinishedTime" + this.start + "_" + this.num, System.currentTimeMillis()).commit();
        if (paramInteger.intValue() != 1)
          Toast.makeText(RecommendAppsFragment.this.getActivity(), 2131428361, 0).show();
        RecommendAppsFragment.this.mGetKXGamesTask = null;
        return;
        label223: RecommendAppsFragment.this.mTopBarLayout.setVisibility(8);
      }
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  class RecommendAppsAdapter extends BaseAdapter
  {
    RecommendAppsAdapter()
    {
    }

    public int getCount()
    {
      int i = RecommendAppsFragment.this.mApps.size();
      if (RecommendAppsFragment.this.mHasMore)
        i++;
      return i;
    }

    public Object getItem(int paramInt)
    {
      if ((paramInt >= 0) && (paramInt < RecommendAppsFragment.this.mApps.size()))
        return RecommendAppsFragment.this.mApps.get(paramInt);
      return null;
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      NavigatorApplistEngine.AppItem localAppItem = (NavigatorApplistEngine.AppItem)getItem(paramInt);
      if ((paramInt > 0) && (paramInt >= RecommendAppsFragment.this.mApps.size()))
        return RecommendAppsFragment.this.mFooterView;
      RecommendAppsFragment.ViewHolder localViewHolder;
      if ((paramView == null) || (paramView == RecommendAppsFragment.this.mFooterView) || (paramView == RecommendAppsFragment.this.mTopBarLayout))
      {
        paramView = RecommendAppsFragment.this.getActivity().getLayoutInflater().inflate(2130903314, null);
        localViewHolder = new RecommendAppsFragment.ViewHolder(RecommendAppsFragment.this);
        localViewHolder.init(paramView);
        paramView.setTag(localViewHolder);
      }
      while (true)
      {
        localViewHolder.update(localAppItem);
        return paramView;
        localViewHolder = (RecommendAppsFragment.ViewHolder)paramView.getTag();
      }
    }
  }

  class ViewHolder
  {
    public TextView mDescription;
    public ImageView mDownloadOrGetinto;
    public ImageView mIcon;
    public TextView mItemIsNewIcon;
    public RelativeLayout mItemLayout;
    public TextView mName;
    public View mNormalLayout;
    public TextView mTextLookAll;
    public TextView mTypeName;
    public View mTypeNameLayout;

    ViewHolder()
    {
    }

    public void init(View paramView)
    {
      this.mIcon = ((ImageView)paramView.findViewById(2131363500));
      this.mName = ((TextView)paramView.findViewById(2131363502));
      this.mDescription = ((TextView)paramView.findViewById(2131363503));
      this.mTypeName = ((TextView)paramView.findViewById(2131363497));
      this.mTextLookAll = ((TextView)paramView.findViewById(2131363529));
      this.mTypeNameLayout = paramView.findViewById(2131363496);
      this.mNormalLayout = paramView.findViewById(2131363498);
      this.mDownloadOrGetinto = ((ImageView)paramView.findViewById(2131363501));
      this.mItemIsNewIcon = ((TextView)paramView.findViewById(2131363530));
      this.mItemLayout = ((RelativeLayout)paramView.findViewById(2131363528));
    }

    public void update(NavigatorApplistEngine.AppItem paramAppItem)
    {
      if (paramAppItem != null)
      {
        if ((paramAppItem.getUrl() != null) || (paramAppItem.getLogo() != null))
          break label72;
        this.mTypeNameLayout.setVisibility(0);
        this.mNormalLayout.setVisibility(8);
        this.mTextLookAll.setVisibility(8);
        this.mTypeName.setText(paramAppItem.getName());
        this.mItemLayout.setOnClickListener(null);
        this.mDownloadOrGetinto.setOnClickListener(null);
      }
      label72: 
      do
      {
        do
        {
          return;
          if (!paramAppItem.isAdvBanner())
            continue;
          this.mTypeNameLayout.setVisibility(8);
          this.mTextLookAll.setVisibility(8);
          this.mNormalLayout.setVisibility(0);
          return;
        }
        while (paramAppItem.isNativeGame());
        if ((paramAppItem.getUrl().equals("")) && (paramAppItem.getLogo() == null))
        {
          this.mTypeNameLayout.setVisibility(8);
          this.mNormalLayout.setVisibility(8);
          this.mTextLookAll.setVisibility(0);
          this.mTextLookAll.setText(paramAppItem.getName());
          this.mTextLookAll.setOnClickListener(new View.OnClickListener()
          {
            public void onClick(View paramView)
            {
              RecommendAppsFragment.this.updateData(true);
              RecommendAppsFragment.this.mShowMore[RecommendAppsFragment.this.mCurTabIndex] = 1;
            }
          });
          return;
        }
        this.mTypeNameLayout.setVisibility(8);
        this.mTextLookAll.setVisibility(8);
        this.mNormalLayout.setVisibility(0);
        ((KXFrameImageView)this.mIcon).setFrameNinePatchResId(2130838099);
        RecommendAppsFragment.this.displayRoundPicture(this.mIcon, paramAppItem.getLogo(), ImageDownloader.RoundCornerType.hdpi_big, 0);
        this.mName.setText(paramAppItem.getName());
        this.mDescription.setText(paramAppItem.getIntro());
        if ((!TextUtils.isEmpty(paramAppItem.getTime())) && (RecommendAppsFragment.this.isIn3Days(paramAppItem.getTime())) && (paramAppItem.isNew()))
          this.mItemIsNewIcon.setVisibility(0);
        while (true)
        {
          this.mDownloadOrGetinto.setVisibility(0);
          if ((paramAppItem == null) || (!(paramAppItem instanceof NavigatorApplistEngine.ThirdAppItem)))
            break;
          if (RecommendAppsFragment.this.isAppExist(((NavigatorApplistEngine.ThirdAppItem)paramAppItem).getPakageName()))
          {
            this.mDownloadOrGetinto.setImageResource(2130838850);
            this.mDownloadOrGetinto.setOnClickListener(new View.OnClickListener(paramAppItem)
            {
              public void onClick(View paramView)
              {
                String str1 = ((NavigatorApplistEngine.ThirdAppItem)this.val$item).getPakageName();
                ((NavigatorApplistEngine.ThirdAppItem)this.val$item).getLaunchClass();
                try
                {
                  PackageInfo localPackageInfo = RecommendAppsFragment.this.getActivity().getPackageManager().getPackageInfo(str1, 0);
                  Intent localIntent1 = new Intent("android.intent.action.MAIN", null);
                  localIntent1.setPackage(localPackageInfo.packageName);
                  ResolveInfo localResolveInfo = (ResolveInfo)RecommendAppsFragment.this.getActivity().getPackageManager().queryIntentActivities(localIntent1, 0).iterator().next();
                  if (localResolveInfo != null)
                  {
                    String str2 = localResolveInfo.activityInfo.packageName;
                    String str3 = localResolveInfo.activityInfo.name;
                    Intent localIntent2 = new Intent("android.intent.action.MAIN");
                    localIntent2.setComponent(new ComponentName(str2, str3));
                    RecommendAppsFragment.this.startActivity(localIntent2);
                  }
                  return;
                }
                catch (PackageManager.NameNotFoundException localNameNotFoundException)
                {
                  localNameNotFoundException.printStackTrace();
                }
              }
            });
            this.mItemLayout.setOnClickListener(new View.OnClickListener(paramAppItem)
            {
              public void onClick(View paramView)
              {
                String str1 = ((NavigatorApplistEngine.ThirdAppItem)this.val$item).getPakageName();
                ((NavigatorApplistEngine.ThirdAppItem)this.val$item).getLaunchClass();
                try
                {
                  PackageInfo localPackageInfo = RecommendAppsFragment.this.getActivity().getPackageManager().getPackageInfo(str1, 0);
                  Intent localIntent1 = new Intent("android.intent.action.MAIN", null);
                  localIntent1.setPackage(localPackageInfo.packageName);
                  ResolveInfo localResolveInfo = (ResolveInfo)RecommendAppsFragment.this.getActivity().getPackageManager().queryIntentActivities(localIntent1, 0).iterator().next();
                  if (localResolveInfo != null)
                  {
                    String str2 = localResolveInfo.activityInfo.packageName;
                    String str3 = localResolveInfo.activityInfo.name;
                    Intent localIntent2 = new Intent("android.intent.action.MAIN");
                    localIntent2.setComponent(new ComponentName(str2, str3));
                    RecommendAppsFragment.this.startActivity(localIntent2);
                  }
                  return;
                }
                catch (PackageManager.NameNotFoundException localNameNotFoundException)
                {
                  localNameNotFoundException.printStackTrace();
                }
              }
            });
            return;
            this.mItemIsNewIcon.setVisibility(8);
            continue;
          }
          this.mDownloadOrGetinto.setImageResource(2130838849);
          this.mDownloadOrGetinto.setOnClickListener(new View.OnClickListener(paramAppItem)
          {
            public void onClick(View paramView)
            {
              String str = this.val$item.getUrl();
              if (Patterns.WEB_URL.matcher(str).matches())
              {
                Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse(str));
                if (ThirdPartyAppUtil.isApkExist(RecommendAppsFragment.this.getActivity(), "com.android.browser"))
                  localIntent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
                RecommendAppsFragment.this.startActivity(localIntent);
                RecommendAppsFragment.this.countRecommendAppGameUserHabit((NavigatorApplistEngine.ThirdAppItem)this.val$item);
                return;
              }
              Toast.makeText(RecommendAppsFragment.this.getActivity(), "不是有效的地址", 1000).show();
            }
          });
          this.mItemLayout.setOnClickListener(new View.OnClickListener(paramAppItem)
          {
            public void onClick(View paramView)
            {
              Intent localIntent = new Intent(RecommendAppsFragment.this.getActivity(), UpgradeDialogActivity.class);
              Bundle localBundle = new Bundle();
              localBundle.putInt("dialogtype", 5);
              localBundle.putString("appname", this.val$item.getName());
              localBundle.putString("appauthor", ((NavigatorApplistEngine.ThirdAppItem)this.val$item).getDev());
              localBundle.putString("appdetail", this.val$item.getDescription());
              localBundle.putString("applogourl", this.val$item.getLogo());
              localBundle.putString("downloadurl", this.val$item.getUrl());
              localIntent.putExtras(localBundle);
              RecommendAppsFragment.this.startActivity(localIntent);
            }
          });
          return;
        }
      }
      while ((paramAppItem == null) || (!(paramAppItem instanceof NavigatorApplistEngine.AppItem)));
      this.mDownloadOrGetinto.setImageResource(2130838850);
      this.mDownloadOrGetinto.setOnClickListener(new View.OnClickListener(paramAppItem)
      {
        public void onClick(View paramView)
        {
          String str1 = User.getInstance().getUID();
          String str2 = this.val$item.getName();
          StringBuffer localStringBuffer = new StringBuffer(this.val$item.getUrl());
          Intent localIntent = new Intent(RecommendAppsFragment.this.getActivity(), MoreItemDetailFragment.class);
          localStringBuffer.append("?uid=").append(str1);
          localIntent.putExtra("label", str2);
          localIntent.putExtra("link", localStringBuffer.toString());
          if ((this.val$item instanceof NavigatorApplistEngine.KXAppItem))
            localIntent.putExtra("appId", ((NavigatorApplistEngine.KXAppItem)this.val$item).getAppId());
          ArrayList localArrayList = new ArrayList();
          if ((this.val$item instanceof NavigatorApplistEngine.KXAppItem))
            localArrayList.add(Integer.valueOf(((NavigatorApplistEngine.KXAppItem)this.val$item).getShareWX()));
          while (true)
          {
            localIntent.putIntegerArrayListExtra("share", localArrayList);
            AnimationUtil.startFragment(RecommendAppsFragment.this, localIntent, 1);
            return;
            localArrayList.add(Integer.valueOf(0));
          }
        }
      });
      this.mItemLayout.setOnClickListener(new View.OnClickListener(paramAppItem)
      {
        public void onClick(View paramView)
        {
          String str1 = User.getInstance().getUID();
          String str2 = this.val$item.getName();
          StringBuffer localStringBuffer = new StringBuffer(this.val$item.getUrl());
          Intent localIntent = new Intent(RecommendAppsFragment.this.getActivity(), MoreItemDetailFragment.class);
          localStringBuffer.append("?uid=").append(str1);
          localIntent.putExtra("label", str2);
          localIntent.putExtra("link", localStringBuffer.toString());
          if ((this.val$item instanceof NavigatorApplistEngine.KXAppItem))
            localIntent.putExtra("appId", ((NavigatorApplistEngine.KXAppItem)this.val$item).getAppId());
          ArrayList localArrayList = new ArrayList();
          if ((this.val$item instanceof NavigatorApplistEngine.KXAppItem))
            localArrayList.add(Integer.valueOf(((NavigatorApplistEngine.KXAppItem)this.val$item).getShareWX()));
          while (true)
          {
            localIntent.putIntegerArrayListExtra("share", localArrayList);
            AnimationUtil.startFragment(RecommendAppsFragment.this, localIntent, 1);
            return;
            localArrayList.add(Integer.valueOf(0));
          }
        }
      });
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.RecommendAppsFragment
 * JD-Core Version:    0.6.0
 */