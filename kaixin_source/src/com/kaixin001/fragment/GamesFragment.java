package com.kaixin001.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.activity.UpgradeDialogActivity;
import com.kaixin001.businesslogic.ThirdPartyAppUtil;
import com.kaixin001.engine.KXGamesEngine;
import com.kaixin001.engine.RecommendAppNotifyEngine;
import com.kaixin001.item.AdvGameItem;
import com.kaixin001.model.AdvGameModel;
import com.kaixin001.model.KXGamesInfo;
import com.kaixin001.model.KXGamesInfo.KXGameModel;
import com.kaixin001.model.ThirdGameInfo;
import com.kaixin001.model.ThirdGameInfo.ThirdGameModel;
import com.kaixin001.model.User;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.ImageDownloader.RoundCornerType;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.UserHabitUtils;
import com.kaixin001.view.KXFrameImageView;
import com.kaixin001.view.KXHorizontalListView;
import com.kaixin001.view.KXNewsBarForGamesView;
import com.kaixin001.view.PullToRefreshView;
import com.kaixin001.view.PullToRefreshView.PullToRefreshListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GamesFragment extends BaseFragment
  implements View.OnClickListener, PullToRefreshView.PullToRefreshListener, AdapterView.OnItemClickListener
{
  private static final String TAG = "GamesFragment";
  private KX360Adapter adapter;
  private ArrayList<KXGamesInfo.KXGameModel> gameList = new ArrayList();
  private GetGameDataTask gamesTask;
  private View headerView;
  private HorizAdapter horizAdapter;
  private LayoutInflater inflater;
  private ArrayList<AdvGameItem> items;
  private KXHorizontalListView kxHorizListView;
  private ImageView leftButton;
  private PullToRefreshView mDownView;
  private ListView mListView;
  private RelativeLayout mTopBarLayout;
  private ArrayList<ThirdGameInfo.ThirdGameModel> thirdList = new ArrayList();

  private void downloadApk(String paramString)
  {
    if (Patterns.WEB_URL.matcher(paramString).matches())
    {
      Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse(paramString));
      if (ThirdPartyAppUtil.isApkExist(getActivity(), "com.android.browser"))
        localIntent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
      startActivity(localIntent);
      return;
    }
    Toast.makeText(getActivity(), "不是有效的地址", 0).show();
  }

  private void freshData()
  {
    if ((this.gamesTask != null) && (!this.gamesTask.isCancelled()) && (this.gamesTask.getStatus() != AsyncTask.Status.FINISHED))
      return;
    this.gamesTask = new GetGameDataTask(null);
    this.gamesTask.execute(new String[0]);
  }

  private void initData()
  {
    if ((ThirdGameInfo.getInstance().getThirdGameList().size() > 0) && (KXGamesInfo.getInstance().getClickInfo().size() + KXGamesInfo.getInstance().getKXGameList().size() > 0))
    {
      findViewById(2131362435).setVisibility(8);
      ArrayList localArrayList1 = ThirdGameInfo.getInstance().getThirdGameList();
      ArrayList localArrayList2 = KXGamesInfo.getInstance().getKXGameList();
      ArrayList localArrayList3 = KXGamesInfo.getInstance().getClickInfo();
      this.thirdList.clear();
      this.gameList.clear();
      if ((localArrayList1 != null) && (localArrayList2 != null))
      {
        this.thirdList.addAll(localArrayList1);
        this.gameList.addAll(localArrayList3);
        this.gameList.addAll(localArrayList2);
      }
      this.adapter.notifyDataSetChanged();
      this.horizAdapter.notifyDataSetChanged();
      return;
    }
    this.mListView.setVisibility(8);
    freshData();
  }

  private void initGridView()
  {
    this.mDownView = ((PullToRefreshView)findViewById(2131362433));
    this.mDownView.setPullToRefreshListener(this);
    this.mListView = ((ListView)findViewById(2131362434));
    this.mListView.addHeaderView(this.headerView);
    this.adapter = new KX360Adapter(null);
    this.mListView.setAdapter(this.adapter);
  }

  private void initHeader()
  {
    this.headerView = this.inflater.inflate(2130903136, null);
    this.mTopBarLayout = ((RelativeLayout)this.headerView.findViewById(2131362436));
    this.kxHorizListView = ((KXHorizontalListView)this.headerView.findViewById(2131362438));
    this.horizAdapter = new HorizAdapter();
    this.kxHorizListView.setAdapter(this.horizAdapter);
    this.kxHorizListView.setOnItemClickListener(this);
  }

  private void seeDetail(int paramInt, ThirdGameInfo.ThirdGameModel paramThirdGameModel)
  {
    switch ($SWITCH_TABLE$com$kaixin001$model$ThirdGameInfo$ThirdGameType()[paramThirdGameModel.type.ordinal()])
    {
    default:
      return;
    case 1:
      Intent localIntent2 = new Intent(getActivity(), GameDetailFragment.class);
      localIntent2.putExtra("index", paramInt);
      startFragment(localIntent2, true, 1);
      return;
    case 2:
    }
    Intent localIntent1 = new Intent(getActivity(), UpgradeDialogActivity.class);
    Bundle localBundle = new Bundle();
    localBundle.putInt("dialogtype", 5);
    localBundle.putString("appname", paramThirdGameModel.name);
    localBundle.putString("appauthor", paramThirdGameModel.developer);
    localBundle.putString("appdetail", paramThirdGameModel.description);
    localBundle.putString("applogourl", paramThirdGameModel.iconUrl);
    localBundle.putString("downloadurl", paramThirdGameModel.downloadUrl);
    localIntent1.putExtras(localBundle);
    startActivity(localIntent1);
  }

  private void setGamesBanner()
  {
    this.mTopBarLayout.removeAllViews();
    this.items = AdvGameModel.getInstance().getGameBannerItems();
    if ((this.items != null) && (this.items.size() != 0))
    {
      this.mTopBarLayout.setVisibility(0);
      KXNewsBarForGamesView localKXNewsBarForGamesView = new KXNewsBarForGamesView(this);
      localKXNewsBarForGamesView.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
      this.mTopBarLayout.addView(localKXNewsBarForGamesView);
      return;
    }
    this.mTopBarLayout.setVisibility(8);
  }

  private void updateGridView()
  {
    ArrayList localArrayList = ThirdGameInfo.getInstance().getThirdGameList();
    if (localArrayList.size() > 0)
    {
      this.thirdList.clear();
      this.thirdList.addAll(localArrayList);
    }
    this.adapter.notifyDataSetChanged();
  }

  protected void initTitleBar()
  {
    this.leftButton = ((ImageView)findViewById(2131362914));
    this.leftButton.setVisibility(0);
    this.leftButton.setOnClickListener(this);
    TextView localTextView = (TextView)findViewById(2131362920);
    localTextView.setVisibility(0);
    localTextView.setText(getResources().getText(2131428688));
    findViewById(2131362928).setVisibility(8);
  }

  public void onClick(View paramView)
  {
    if (paramView == this.leftButton)
    {
      if (super.isMenuListVisibleInMain())
        super.showSubActivityInMain();
    }
    else
      return;
    super.showMenuListInMain();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.inflater = getActivity().getLayoutInflater();
    UserHabitUtils.getInstance(getActivity()).addUserHabit("visit_games");
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903135, paramViewGroup, false);
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    KXGamesInfo.KXGameModel localKXGameModel = (KXGamesInfo.KXGameModel)this.gameList.get(paramInt);
    localKXGameModel.isnew = Integer.toString(0);
    KXGamesInfo.getInstance().addClickItem(localKXGameModel);
    KXGamesInfo.getInstance().removeKxGameItem(localKXGameModel);
    String str1 = User.getInstance().getUID();
    String str2 = localKXGameModel.name;
    StringBuffer localStringBuffer = new StringBuffer(localKXGameModel.wapurl);
    localStringBuffer.append("?uid=").append(str1);
    Intent localIntent = new Intent(getActivity(), MoreItemDetailFragment.class);
    localIntent.putExtra("label", str2);
    localIntent.putExtra("link", localStringBuffer.toString());
    localIntent.putExtra("appId", localKXGameModel.appid);
    localIntent.putExtra("share", localKXGameModel.weixin);
    ArrayList localArrayList = new ArrayList();
    if (!TextUtils.isEmpty(localKXGameModel.weixin))
      localArrayList.add(Integer.valueOf(Integer.parseInt(localKXGameModel.weixin)));
    while (true)
    {
      localIntent.putIntegerArrayListExtra("share", localArrayList);
      AnimationUtil.startFragment(this, localIntent, 1);
      return;
      localArrayList.add(Integer.valueOf(0));
    }
  }

  public void onUpdate()
  {
    freshData();
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    initTitleBar();
    initHeader();
    initGridView();
    setGamesBanner();
    initData();
    RecommendAppNotifyEngine.getInstance().clearNewCount(getActivity());
  }

  public void requestFinish()
  {
  }

  public void setOfficialGame()
  {
    ArrayList localArrayList1 = KXGamesInfo.getInstance().getClickInfo();
    Iterator localIterator = localArrayList1.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
      {
        ArrayList localArrayList2 = KXGamesInfo.getInstance().getKXGameList();
        this.gameList.clear();
        this.gameList.addAll(localArrayList1);
        this.gameList.addAll(localArrayList2);
        this.horizAdapter.notifyDataSetChanged();
        return;
      }
      KXGamesInfo.KXGameModel localKXGameModel = (KXGamesInfo.KXGameModel)localIterator.next();
      KXGamesInfo.getInstance().removeKxGameItem(localKXGameModel);
    }
  }

  class ContentView
  {
    KXFrameImageView imgView;
    ImageView newImageView;
    TextView textView;

    ContentView()
    {
    }
  }

  private class GetGameDataTask extends KXFragment.KXAsyncTask<String, Void, Integer>
  {
    private GetGameDataTask()
    {
      super();
    }

    protected Integer doInBackgroundA(String[] paramArrayOfString)
    {
      try
      {
        KXGamesEngine.getInstance().getBannerData(GamesFragment.this.getActivity(), "2");
        KXGamesEngine.getInstance().getKXGameslistData(GamesFragment.this.getActivity());
        KXGamesEngine.getInstance().getKXAppslistData(GamesFragment.this.getActivity());
        KXGamesEngine.getInstance().getRecommendGamesList(GamesFragment.this.getActivity());
        Integer localInteger = Integer.valueOf(1);
        return localInteger;
      }
      catch (Exception localException)
      {
        KXLog.e("GamesFragment", "get game data error");
      }
      return null;
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      if ((GamesFragment.this.mDownView != null) && (GamesFragment.this.mDownView.isFrefrshing()))
        GamesFragment.this.mDownView.onRefreshComplete();
      GamesFragment.this.mListView.setVisibility(0);
      GamesFragment.this.findViewById(2131362435).setVisibility(8);
      GamesFragment.this.setGamesBanner();
      GamesFragment.this.setOfficialGame();
      GamesFragment.this.updateGridView();
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  class HorizAdapter extends BaseAdapter
  {
    HorizAdapter()
    {
    }

    public int getCount()
    {
      return GamesFragment.this.gameList.size();
    }

    public Object getItem(int paramInt)
    {
      return GamesFragment.this.gameList.get(paramInt);
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      KXGamesInfo.KXGameModel localKXGameModel = (KXGamesInfo.KXGameModel)GamesFragment.this.gameList.get(paramInt);
      GamesFragment.ContentView localContentView;
      if (paramView == null)
      {
        localContentView = new GamesFragment.ContentView(GamesFragment.this);
        paramView = LayoutInflater.from(GamesFragment.this.getActivity()).inflate(2130903194, null);
        localContentView.imgView = ((KXFrameImageView)paramView.findViewById(2131362949));
        localContentView.textView = ((TextView)paramView.findViewById(2131362950));
        localContentView.newImageView = ((ImageView)paramView.findViewById(2131362951));
        paramView.setTag(localContentView);
      }
      while (true)
      {
        localContentView.imgView.setFrameNinePatchResId(2130838099);
        GamesFragment.this.displayRoundPicture(localContentView.imgView, localKXGameModel.logo, ImageDownloader.RoundCornerType.hdpi_big, 2130838334);
        localContentView.textView.setText(localKXGameModel.name);
        if ((TextUtils.isEmpty(localKXGameModel.isnew)) || (Integer.parseInt(localKXGameModel.isnew) != 1))
          break;
        localContentView.newImageView.setVisibility(0);
        return paramView;
        localContentView = (GamesFragment.ContentView)paramView.getTag();
      }
      localContentView.newImageView.setVisibility(8);
      return paramView;
    }
  }

  private class KX360Adapter extends BaseAdapter
  {
    private KX360Adapter()
    {
    }

    public int getCount()
    {
      return (1 + GamesFragment.this.thirdList.size()) / 2;
    }

    public Object getItem(int paramInt)
    {
      return GamesFragment.this.thirdList.get(paramInt);
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      GamesFragment.ThirdGameHolder localThirdGameHolder;
      if (paramView == null)
      {
        paramView = GamesFragment.this.getActivity().getLayoutInflater().inflate(2130903343, null);
        localThirdGameHolder = new GamesFragment.ThirdGameHolder(GamesFragment.this, null);
        localThirdGameHolder.initView(paramView);
        paramView.setTag(localThirdGameHolder);
      }
      while (true)
      {
        localThirdGameHolder.updateView(paramInt * 2);
        return paramView;
        localThirdGameHolder = (GamesFragment.ThirdGameHolder)paramView.getTag();
      }
    }
  }

  private class ThirdGameHolder
  {
    private View leftDownIcon;
    private View leftLayout;
    private KXFrameImageView leftLogo;
    private TextView leftName;
    private TextView leftSize;
    private TextView leftType;
    private View rightDownIcon;
    private View rightLayout;
    private KXFrameImageView rightLogo;
    private TextView rightName;
    private TextView rightSize;
    private TextView rightType;

    private ThirdGameHolder()
    {
    }

    private void updateLeftView(int paramInt, ThirdGameInfo.ThirdGameModel paramThirdGameModel)
    {
      this.leftLogo.setFrameNinePatchResId(2130838099);
      GamesFragment.this.displayRoundPicture(this.leftLogo, paramThirdGameModel.iconUrl, ImageDownloader.RoundCornerType.hdpi_big, 2130838334);
      this.leftName.setText(paramThirdGameModel.name);
      this.leftSize.setText(paramThirdGameModel.apkSize);
      this.leftType.setText(paramThirdGameModel.tag);
      this.leftDownIcon.setOnClickListener(new View.OnClickListener(paramThirdGameModel)
      {
        public void onClick(View paramView)
        {
          UserHabitUtils.getInstance(GamesFragment.this.getActivity()).addUserHabit("recommendGame_download_" + this.val$model.name);
          String str = this.val$model.downloadUrl;
          GamesFragment.this.downloadApk(str);
        }
      });
      this.leftLayout.setOnClickListener(new View.OnClickListener(paramInt, paramThirdGameModel)
      {
        public void onClick(View paramView)
        {
          GamesFragment.this.seeDetail(this.val$index, this.val$model);
        }
      });
    }

    private void updateRightView(int paramInt, ThirdGameInfo.ThirdGameModel paramThirdGameModel)
    {
      this.rightLogo.setFrameNinePatchResId(2130838099);
      GamesFragment.this.displayRoundPicture(this.rightLogo, paramThirdGameModel.iconUrl, ImageDownloader.RoundCornerType.hdpi_big, 2130838334);
      this.rightName.setText(paramThirdGameModel.name);
      this.rightSize.setText(paramThirdGameModel.apkSize);
      this.rightType.setText(paramThirdGameModel.tag);
      this.rightDownIcon.setOnClickListener(new View.OnClickListener(paramThirdGameModel)
      {
        public void onClick(View paramView)
        {
          UserHabitUtils.getInstance(GamesFragment.this.getActivity()).addUserHabit("recommendGame_download_" + this.val$model.name);
          String str = this.val$model.downloadUrl;
          GamesFragment.this.downloadApk(str);
        }
      });
      this.rightLayout.setOnClickListener(new View.OnClickListener(paramInt, paramThirdGameModel)
      {
        public void onClick(View paramView)
        {
          GamesFragment.this.seeDetail(this.val$index, this.val$model);
        }
      });
    }

    public void initView(View paramView)
    {
      this.leftLayout = paramView.findViewById(2131363699);
      this.leftLogo = ((KXFrameImageView)paramView.findViewById(2131363701));
      this.leftName = ((TextView)paramView.findViewById(2131363702));
      this.leftSize = ((TextView)paramView.findViewById(2131363703));
      this.leftType = ((TextView)paramView.findViewById(2131363704));
      this.leftDownIcon = paramView.findViewById(2131363700);
      this.rightLayout = paramView.findViewById(2131363706);
      this.rightLogo = ((KXFrameImageView)paramView.findViewById(2131363708));
      this.rightName = ((TextView)paramView.findViewById(2131363709));
      this.rightSize = ((TextView)paramView.findViewById(2131363710));
      this.rightType = ((TextView)paramView.findViewById(2131363711));
      this.rightDownIcon = paramView.findViewById(2131363707);
    }

    public void updateView(int paramInt)
    {
      try
      {
        if (paramInt < -1 + GamesFragment.this.thirdList.size())
        {
          int i = paramInt + 1;
          ThirdGameInfo.ThirdGameModel localThirdGameModel1 = (ThirdGameInfo.ThirdGameModel)GamesFragment.this.thirdList.get(paramInt);
          ThirdGameInfo.ThirdGameModel localThirdGameModel2 = (ThirdGameInfo.ThirdGameModel)GamesFragment.this.thirdList.get(i);
          updateLeftView(paramInt, localThirdGameModel1);
          updateRightView(i, localThirdGameModel2);
          this.rightLayout.setVisibility(0);
          this.rightLogo.setVisibility(0);
          this.rightName.setVisibility(0);
          this.rightSize.setVisibility(0);
          this.rightType.setVisibility(0);
          this.rightDownIcon.setVisibility(0);
          return;
        }
        updateLeftView(paramInt, (ThirdGameInfo.ThirdGameModel)GamesFragment.this.thirdList.get(paramInt));
        this.rightLayout.setVisibility(4);
        this.rightLogo.setVisibility(4);
        this.rightName.setVisibility(4);
        this.rightSize.setVisibility(4);
        this.rightType.setVisibility(4);
        this.rightDownIcon.setVisibility(4);
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.GamesFragment
 * JD-Core Version:    0.6.0
 */