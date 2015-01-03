package com.kaixin001.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.engine.FriendsEngine;
import com.kaixin001.engine.GetGiftListEngine;
import com.kaixin001.engine.GiftNewsEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.item.FriendInfo;
import com.kaixin001.item.GiftItem;
import com.kaixin001.item.GiftNewsItem;
import com.kaixin001.item.KXLinkInfo;
import com.kaixin001.item.NewsInfo;
import com.kaixin001.model.FriendsModel;
import com.kaixin001.model.FriendsModel.Friend;
import com.kaixin001.model.GiftListModel;
import com.kaixin001.model.GiftNewsModel;
import com.kaixin001.model.User;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.CrashRecoverUtil;
import com.kaixin001.util.ImageDownloader.RoundCornerType;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.UserHabitUtils;
import com.kaixin001.view.KXIntroView;
import com.kaixin001.view.KXIntroView.OnClickLinkListener;
import com.kaixin001.view.PullToRefreshView;
import com.kaixin001.view.PullToRefreshView.PullToRefreshListener;
import java.io.Serializable;
import java.util.ArrayList;

public class GiftNewsFragment extends BaseFragment
  implements KXIntroView.OnClickLinkListener, View.OnClickListener, PullToRefreshView.PullToRefreshListener
{
  private static final int DIALOG_ID_GET_GIFTS_AND_FRIENDS = 1;
  private static final int DIALOG_ID_GET_GIFTS_NEWS = 2;
  protected static final int MENU_HOME_ID = 10003;
  protected static final int MENU_HOME_ME_ID = 10004;
  protected static final int MENU_REFRESH_ID = 10001;
  protected static final int MENU_TO_TOP_ID = 10002;
  public static final int PAGE_GIFT_NUM = 8;
  public static final String SYSTEM_MESSAGE_LIST_POSITON = "position";
  private static final String TAG = "GiftNewsActivity";
  static View headerView;
  private static View mFooterView;
  private GetFriendAndHotGiftDataTask getFriendAndHotGiftDataTask;
  private GiftFriendsAdapter giftAndFriendList;
  private ImageView giftListImage;
  ArrayList<GiftNewsItem> giftNewsList = new ArrayList();
  private ImageView[] headImage = new ImageView[8];
  private LinearLayout[] headLinearLayout = new LinearLayout[8];
  private TextView[] headText = new TextView[8];
  private boolean isInitGiftAndFriendsList = false;
  private boolean isInitGiftNewsList = false;
  private GiftNewsAdapter listAdapter;
  public ArrayList<FriendsModel.Friend> mBirthFriendList = null;
  private ImageView mBtnRight;
  protected PullToRefreshView mDownView;
  public ArrayList<FriendsModel.Friend> mFocusFriendList = null;
  private ProgressBar mFooterProBar;
  private TextView mFooterTV;
  FriendsModel mFriendsModel = FriendsModel.getInstance();
  private GetGiftNewsTask mGetGiftNewsTask = null;
  GiftNewsModel mGiftNewsModel = GiftNewsModel.getInstance();
  private ListView mListView = null;
  private int mTabIndex = 0;
  private int[] mTabIndexArray = new int[2];
  private int showingDialogId = 0;

  private void getMoreGiftNews(boolean paramBoolean)
  {
    if (!super.checkNetworkAndHint(paramBoolean))
      showLoadingFooter(false);
    int i;
    int j;
    do
    {
      return;
      showLoadingFooter(true);
      i = GiftNewsModel.getInstance().total;
      ArrayList localArrayList = GiftNewsModel.getInstance().getGiftNewsList();
      j = 0;
      if (localArrayList == null)
        continue;
      j = localArrayList.size();
    }
    while ((i <= 0) || (j >= i));
    String[] arrayOfString = new String[1];
    arrayOfString[0] = Integer.toString(j);
    this.mGetGiftNewsTask = new GetGiftNewsTask(null);
    this.mGetGiftNewsTask.execute(arrayOfString);
  }

  private void intHeadView()
  {
    headerView = getActivity().getLayoutInflater().inflate(2130903139, null);
    this.giftListImage = ((ImageView)headerView.findViewById(2131362458));
    this.giftListImage.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        Intent localIntent = new Intent(GiftNewsFragment.this.getActivity(), SelectGiftFragment.class);
        AnimationUtil.startFragment(GiftNewsFragment.this, localIntent, 1);
      }
    });
    this.headLinearLayout[0] = ((LinearLayout)headerView.findViewById(2131362460));
    this.headLinearLayout[1] = ((LinearLayout)headerView.findViewById(2131362463));
    this.headLinearLayout[2] = ((LinearLayout)headerView.findViewById(2131362466));
    this.headLinearLayout[3] = ((LinearLayout)headerView.findViewById(2131362469));
    this.headLinearLayout[4] = ((LinearLayout)headerView.findViewById(2131362472));
    this.headLinearLayout[5] = ((LinearLayout)headerView.findViewById(2131362475));
    this.headLinearLayout[6] = ((LinearLayout)headerView.findViewById(2131362478));
    this.headLinearLayout[7] = ((LinearLayout)headerView.findViewById(2131362481));
    this.headImage[0] = ((ImageView)headerView.findViewById(2131362461));
    this.headImage[1] = ((ImageView)headerView.findViewById(2131362464));
    this.headImage[2] = ((ImageView)headerView.findViewById(2131362467));
    this.headImage[3] = ((ImageView)headerView.findViewById(2131362470));
    this.headImage[4] = ((ImageView)headerView.findViewById(2131362473));
    this.headImage[5] = ((ImageView)headerView.findViewById(2131362476));
    this.headImage[6] = ((ImageView)headerView.findViewById(2131362479));
    this.headImage[7] = ((ImageView)headerView.findViewById(2131362482));
    this.headText[0] = ((TextView)headerView.findViewById(2131362462));
    this.headText[1] = ((TextView)headerView.findViewById(2131362465));
    this.headText[2] = ((TextView)headerView.findViewById(2131362468));
    this.headText[3] = ((TextView)headerView.findViewById(2131362471));
    this.headText[4] = ((TextView)headerView.findViewById(2131362474));
    this.headText[5] = ((TextView)headerView.findViewById(2131362477));
    this.headText[6] = ((TextView)headerView.findViewById(2131362480));
    this.headText[7] = ((TextView)headerView.findViewById(2131362483));
  }

  private void intViewPager()
  {
    GiftItem[] arrayOfGiftItem = GiftListModel.getInstance().gifts;
    int i = 0;
    if (arrayOfGiftItem != null)
      if ((i < 8) && (i < arrayOfGiftItem.length))
        break label34;
    for (int j = i; ; j++)
    {
      if (j >= 8)
      {
        return;
        label34: this.headText[i].setText(arrayOfGiftItem[i].gname);
        displayPicture(this.headImage[i], arrayOfGiftItem[i].pic, 2130838085);
        this.headLinearLayout[i].setTag(arrayOfGiftItem[i]);
        this.headLinearLayout[i].setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramView)
          {
            GiftItem localGiftItem = (GiftItem)paramView.getTag();
            Intent localIntent = new Intent();
            localIntent.putExtra("giftId", localGiftItem.gid);
            localIntent.putExtra("defaultps", localGiftItem.defaultPs);
            localIntent.setClass(GiftNewsFragment.this.getActivity(), SendGiftFragment.class);
            AnimationUtil.startFragment(GiftNewsFragment.this, localIntent, 1);
          }
        });
        this.headLinearLayout[i].setVisibility(0);
        i++;
        break;
      }
      this.headLinearLayout[j].setVisibility(8);
    }
  }

  private void refreshGiftAndFriendsList()
  {
    this.giftAndFriendList.notifyDataSetChanged();
    intViewPager();
  }

  private void refreshGiftAndFrineds()
  {
    if (!super.checkNetworkAndHint(true))
      return;
    this.getFriendAndHotGiftDataTask = new GetFriendAndHotGiftDataTask(null);
    this.getFriendAndHotGiftDataTask.execute(new Integer[0]);
    this.mListView.setSelection(0);
  }

  private void refreshGiftNews()
  {
    if (!super.checkNetworkAndHint(true))
      return;
    String[] arrayOfString = new String[1];
    arrayOfString[0] = Integer.toString(0);
    this.mGetGiftNewsTask = new GetGiftNewsTask(null);
    this.mGetGiftNewsTask.execute(arrayOfString);
    this.mListView.setSelection(0);
  }

  private void setListAdapter()
  {
    switch (this.mTabIndex)
    {
    default:
    case 0:
    case 1:
    }
    while (true)
    {
      return;
      this.mTabIndex = 0;
      this.mListView.setAdapter(this.giftAndFriendList);
      if ((this.mBirthFriendList.size() != 0) || (this.mFocusFriendList.size() != 0) || (GiftListModel.getInstance().gifts != null))
      {
        this.giftAndFriendList.notifyDataSetChanged();
        intViewPager();
      }
      if (this.isInitGiftAndFriendsList)
        continue;
      this.getFriendAndHotGiftDataTask = new GetFriendAndHotGiftDataTask(null);
      this.getFriendAndHotGiftDataTask.execute(new Integer[0]);
      if ((this.mBirthFriendList.size() == 0) || (this.mFocusFriendList.size() == 0) || (GiftListModel.getInstance().gifts == null))
        continue;
      showDialog(1);
      return;
      this.mTabIndex = 1;
      this.mListView.setAdapter(this.listAdapter);
      try
      {
        boolean bool2 = GiftNewsEngine.getInstance().loadGiftNewsCache(getActivity(), User.getInstance().getUID());
        bool1 = bool2;
        if (this.mGiftNewsModel.getGiftNewsList().size() != 0)
          this.listAdapter.notifyDataSetChanged();
        if ((this.isInitGiftNewsList) || (bool1))
          continue;
        String[] arrayOfString = new String[1];
        arrayOfString[0] = Integer.toString(0);
        this.mGetGiftNewsTask = new GetGiftNewsTask(null);
        this.mGetGiftNewsTask.execute(arrayOfString);
        if (this.mGiftNewsModel.getGiftNewsList().size() != 0)
          continue;
        showDialog(2);
        return;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
        while (true)
        {
          localSecurityErrorException.printStackTrace();
          boolean bool1 = false;
        }
      }
    }
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

  public void onClick(View paramView)
  {
    if (paramView.getId() == 2131362914)
    {
      Bundle localBundle = getArguments();
      if ((localBundle == null) || (localBundle.getString("from") == null) || (!localBundle.getString("from").equals("KaixinMenuListFragment")))
        break label62;
      if (super.isMenuListVisibleInMain())
        super.showSubActivityInMain();
    }
    else
    {
      return;
    }
    super.showMenuListInMain();
    return;
    label62: finish();
  }

  public void onClick(KXLinkInfo paramKXLinkInfo)
  {
    if ((paramKXLinkInfo.isStar()) || (paramKXLinkInfo.isUserName()))
      IntentUtil.showHomeFragment(this, paramKXLinkInfo.getId(), paramKXLinkInfo.getContent());
    do
    {
      return;
      if (!paramKXLinkInfo.isUrlLink())
        continue;
      IntentUtil.showWebPage(getActivity(), this, paramKXLinkInfo.getId(), null);
      return;
    }
    while (!paramKXLinkInfo.isTopic());
    IntentUtil.showTopicGroupActivity(this, paramKXLinkInfo.getId());
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

  protected Dialog onCreateDialog(int paramInt)
  {
    if (paramInt == 1)
    {
      this.showingDialogId = 1;
      return ProgressDialog.show(getActivity(), "", getResources().getText(2131427546), true, true, new DialogInterface.OnCancelListener()
      {
        public void onCancel(DialogInterface paramDialogInterface)
        {
          GiftNewsFragment.this.dismissDialog(1);
          GiftNewsFragment.this.getFriendAndHotGiftDataTask.cancel(true);
          GiftNewsFragment.this.showingDialogId = 0;
        }
      });
    }
    if (paramInt == 2)
    {
      this.showingDialogId = 2;
      return ProgressDialog.show(getActivity(), "", getResources().getText(2131427546), true, true, new DialogInterface.OnCancelListener()
      {
        public void onCancel(DialogInterface paramDialogInterface)
        {
          GiftNewsFragment.this.dismissDialog(2);
          GiftNewsFragment.this.mGetGiftNewsTask.cancel(true);
          GiftNewsFragment.this.showingDialogId = 0;
        }
      });
    }
    return super.onCreateDialog(paramInt);
  }

  public void onCreateOptionsMenu(Menu paramMenu, MenuInflater paramMenuInflater)
  {
    paramMenu.add(0, 10001, 0, 2131427690).setIcon(2130838607);
    paramMenu.add(0, 10002, 0, 2131427698).setIcon(2130838609);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903142, paramViewGroup, false);
  }

  public void onDestroy()
  {
    cancelTask(this.mGetGiftNewsTask);
    cancelTask(this.getFriendAndHotGiftDataTask);
    super.onDestroy();
  }

  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default:
      return super.onOptionsItemSelected(paramMenuItem);
    case 10001:
      if ((this.mListView != null) && (this.mListView.getVisibility() == 0))
        onUpdate();
      return true;
    case 10002:
      ListView localListView = (ListView)getView().findViewById(2131362489);
      if (localListView.getVisibility() == 0)
        localListView.setSelection(0);
      return true;
    case 10003:
      IntentUtil.returnToDesktop(getActivity());
      return true;
    case 10004:
    }
    IntentUtil.showMyHomeFragment(this);
    return true;
  }

  public void onResume()
  {
    super.onResume();
  }

  public void onUpdate()
  {
    if (this.mTabIndex == 1)
    {
      refreshGiftNews();
      return;
    }
    refreshGiftAndFrineds();
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    setTitleBar(paramView);
    this.mTabIndexArray = new int[2];
    mFooterView = getActivity().getLayoutInflater().inflate(2130903259, null);
    this.mFooterTV = ((TextView)mFooterView.findViewById(2131362073));
    this.mFooterProBar = ((ProgressBar)mFooterView.findViewById(2131362072));
    mFooterView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        ArrayList localArrayList = GiftNewsFragment.this.mGiftNewsModel.getGiftNewsList();
        int i = GiftNewsFragment.this.mGiftNewsModel.total;
        if ((localArrayList == null) || (localArrayList.size() < GiftNewsFragment.this.giftNewsList.size()))
          GiftNewsFragment.this.showLoadingFooter(true);
        for (boolean bool = true; (localArrayList != null) && (localArrayList.size() < i); bool = false)
        {
          GiftNewsFragment.this.getMoreGiftNews(bool);
          return;
          GiftNewsFragment.this.updateMessageList();
        }
        GiftNewsFragment.this.updateMessageList();
      }
    });
    ((TextView)mFooterView.findViewById(2131362073)).setText(2131427748);
    ((ProgressBar)mFooterView.findViewById(2131362072)).setVisibility(4);
    intHeadView();
    this.mDownView = ((PullToRefreshView)paramView.findViewById(2131362488));
    this.mDownView.setPullToRefreshListener(this);
    this.mBirthFriendList = FriendsModel.getInstance().getBirthFriends();
    this.mFocusFriendList = FriendsModel.getInstance().getFocusFriends();
    this.giftAndFriendList = new GiftFriendsAdapter(this);
    this.mListView = ((ListView)paramView.findViewById(2131362489));
    this.listAdapter = new GiftNewsAdapter(this, this.giftNewsList);
    setListAdapter();
  }

  public String searchGender(ArrayList<FriendsModel.Friend> paramArrayList, String paramString)
  {
    for (int i = 0; ; i++)
    {
      if (i >= paramArrayList.size())
      {
        Intent localIntent = new Intent("com.kaixin001.UPDATE_FRIENDS");
        getActivity().sendBroadcast(localIntent);
        return "0";
      }
      if (((FriendsModel.Friend)paramArrayList.get(i)).getFuid().equals(paramString))
        return ((FriendsModel.Friend)paramArrayList.get(i)).getGender();
    }
  }

  protected void setTitleBar(View paramView)
  {
    ImageView localImageView = (ImageView)paramView.findViewById(2131362914);
    localImageView.setVisibility(0);
    localImageView.setOnClickListener(this);
    this.mBtnRight = ((ImageView)paramView.findViewById(2131362928));
    this.mBtnRight.setImageResource(2130839020);
    this.mBtnRight.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        Intent localIntent = new Intent();
        localIntent.setClass(GiftNewsFragment.this.getActivity(), SendGiftFragment.class);
        AnimationUtil.startFragment(GiftNewsFragment.this, localIntent, 1);
        UserHabitUtils.getInstance(GiftNewsFragment.this.getActivity()).addUserHabit("Gift_Send");
      }
    });
    ((ImageView)paramView.findViewById(2131362919)).setVisibility(8);
    TextView localTextView = (TextView)paramView.findViewById(2131362920);
    localTextView.setVisibility(0);
    localTextView.setText(getResources().getText(2131428436));
  }

  protected void updateMessageList()
  {
    this.giftNewsList.clear();
    ArrayList localArrayList = this.mGiftNewsModel.getGiftNewsList();
    int i = this.mGiftNewsModel.total;
    if (localArrayList != null)
      this.giftNewsList.addAll(localArrayList);
    if ((localArrayList != null) && (localArrayList.size() < i) && (this.mGiftNewsModel.isHasMore))
    {
      GiftNewsItem localGiftNewsItem = new GiftNewsItem();
      this.giftNewsList.add(localGiftNewsItem);
    }
    if (this.listAdapter != null)
      this.listAdapter.notifyDataSetChanged();
  }

  static class FriendsViewTag
  {
    TextView Rbirth;
    RelativeLayout childItem;
    LinearLayout groupItem;
    ImageView rLogo;
    TextView rName;
    ImageView sendGift;
    TextView tvText;

    public FriendsViewTag(View paramView)
    {
      this.groupItem = ((LinearLayout)paramView.findViewById(2131362448));
      this.tvText = ((TextView)paramView.findViewById(2131362449));
      this.childItem = ((RelativeLayout)paramView.findViewById(2131362450));
      this.rLogo = ((ImageView)paramView.findViewById(2131362452));
      this.rName = ((TextView)paramView.findViewById(2131362454));
      this.Rbirth = ((TextView)paramView.findViewById(2131362455));
      this.sendGift = ((ImageView)paramView.findViewById(2131362456));
    }

    public void showFrinedItem(GiftNewsFragment paramGiftNewsFragment, FriendsModel.Friend paramFriend)
    {
      if (paramFriend == null)
      {
        this.groupItem.setVisibility(0);
        this.childItem.setVisibility(8);
        return;
      }
      this.groupItem.setVisibility(8);
      this.childItem.setVisibility(0);
      paramGiftNewsFragment.displayRoundPicture(this.rLogo, paramFriend.getFlogo(), ImageDownloader.RoundCornerType.hdpi_big, 2130838334);
      this.rName.setText(paramFriend.getFname());
      if (paramFriend.getBirthDisplay() != null)
      {
        this.Rbirth.setText(paramFriend.getBirthDisplay());
        this.Rbirth.setVisibility(0);
        return;
      }
      this.Rbirth.setVisibility(8);
    }
  }

  private class GetFriendAndHotGiftDataTask extends KXFragment.KXAsyncTask<Integer, Void, Integer>
  {
    private GetFriendAndHotGiftDataTask()
    {
      super();
    }

    protected Integer doInBackgroundA(Integer[] paramArrayOfInteger)
    {
      try
      {
        if ((FriendsEngine.getInstance().getFriendsList(GiftNewsFragment.this.getActivity().getApplicationContext(), 6)) && (FriendsEngine.getInstance().getFriendsList(GiftNewsFragment.this.getActivity().getApplicationContext(), 7)) && (GetGiftListEngine.getInstance().getGiftList(GiftNewsFragment.this.getActivity().getApplicationContext(), String.valueOf(80)) == 1))
          return Integer.valueOf(1);
        Integer localInteger = Integer.valueOf(0);
        return localInteger;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
      }
      return null;
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      if ((GiftNewsFragment.this.getActivity() == null) || (GiftNewsFragment.this.getView() == null));
      do
        return;
      while (paramInteger == null);
      GiftNewsFragment.this.mBirthFriendList = FriendsModel.getInstance().getBirthFriends();
      GiftNewsFragment.this.mFocusFriendList = FriendsModel.getInstance().getFocusFriends();
      while (true)
      {
        try
        {
          if (GiftNewsFragment.this.showingDialogId != 1)
            continue;
          GiftNewsFragment.this.dismissDialog(1);
          GiftNewsFragment.this.showingDialogId = 0;
          if (paramInteger.intValue() == 1)
          {
            GiftNewsFragment.this.refreshGiftAndFriendsList();
            GiftNewsFragment.this.isInitGiftAndFriendsList = true;
            GiftNewsFragment.this.getFriendAndHotGiftDataTask = null;
            if ((GiftNewsFragment.this.mDownView == null) || (!GiftNewsFragment.this.mDownView.isFrefrshing()))
              break;
            GiftNewsFragment.this.mDownView.onRefreshComplete();
            return;
          }
        }
        catch (Exception localException)
        {
          KXLog.e("GiftNewsActivity", "onPostExecute", localException);
          return;
        }
        Toast.makeText(GiftNewsFragment.this.getActivity(), 2131427547, 0).show();
      }
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private class GetGiftNewsTask extends KXFragment.KXAsyncTask<String, Void, Boolean>
  {
    private GetGiftNewsTask()
    {
      super();
    }

    protected Boolean doInBackgroundA(String[] paramArrayOfString)
    {
      if (paramArrayOfString != null);
      try
      {
        if (paramArrayOfString.length != 1)
        {
          if (1 == 0)
            return Boolean.valueOf(true);
          return Boolean.valueOf(false);
        }
        if (GiftNewsEngine.getInstance().doGetGiftNews(GiftNewsFragment.this.getActivity().getApplicationContext(), paramArrayOfString[0]) == 1)
          return Boolean.valueOf(true);
        Boolean localBoolean = Boolean.valueOf(false);
        return localBoolean;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
      }
      return Boolean.valueOf(false);
    }

    protected void onPostExecuteA(Boolean paramBoolean)
    {
      if ((GiftNewsFragment.this.getActivity() == null) || (GiftNewsFragment.this.getView() == null));
      do
        return;
      while (paramBoolean == null);
      while (true)
      {
        try
        {
          if (GiftNewsFragment.this.showingDialogId != 2)
            continue;
          GiftNewsFragment.this.dismissDialog(2);
          GiftNewsFragment.this.showingDialogId = 0;
          if (paramBoolean.booleanValue())
          {
            GiftNewsFragment.this.updateMessageList();
            GiftNewsFragment.this.showLoadingFooter(false);
            GiftNewsFragment.this.isInitGiftNewsList = true;
            if ((GiftNewsFragment.this.mDownView == null) || (!GiftNewsFragment.this.mDownView.isFrefrshing()))
              continue;
            GiftNewsFragment.this.mDownView.onRefreshComplete();
            GiftNewsFragment.this.mGetGiftNewsTask = null;
            return;
          }
        }
        catch (Exception localException)
        {
          KXLog.e("GiftNewsActivity", "onPostExecute", localException);
          return;
        }
        Toast.makeText(GiftNewsFragment.this.getActivity(), 2131427547, 0).show();
      }
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  public static class GiftFriendsAdapter extends BaseAdapter
  {
    GiftNewsFragment giftNewsFragment;
    ArrayList<FriendsModel.Friend> mAllList = new ArrayList();
    LayoutInflater vi;

    public GiftFriendsAdapter(GiftNewsFragment paramGiftNewsFragment)
    {
      this.giftNewsFragment = paramGiftNewsFragment;
      this.mAllList.clear();
      this.mAllList.add(null);
      if (paramGiftNewsFragment.mBirthFriendList != null)
        this.mAllList.addAll(paramGiftNewsFragment.mBirthFriendList);
      this.mAllList.add(null);
      if (paramGiftNewsFragment.mFocusFriendList != null)
        this.mAllList.addAll(paramGiftNewsFragment.mFocusFriendList);
      this.vi = ((LayoutInflater)paramGiftNewsFragment.getActivity().getSystemService("layout_inflater"));
    }

    public int getCount()
    {
      return 1 + this.mAllList.size();
    }

    public Object getItem(int paramInt)
    {
      return this.mAllList.get(paramInt);
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      if (paramInt == 0)
        return GiftNewsFragment.headerView;
      FriendsModel.Friend localFriend = (FriendsModel.Friend)this.mAllList.get(paramInt - 1);
      GiftNewsFragment.FriendsViewTag localFriendsViewTag;
      if ((paramView == null) || (paramView.getTag() == null) || (paramView == GiftNewsFragment.headerView))
      {
        paramView = this.vi.inflate(2130903138, null);
        localFriendsViewTag = new GiftNewsFragment.FriendsViewTag(paramView);
        paramView.setTag(localFriendsViewTag);
        if (localFriend != null)
          break label150;
        if (paramInt != 1)
          break label137;
        if (this.mAllList.get(paramInt) != null)
          break label124;
        localFriendsViewTag.tvText.setText(2131428451);
        label99: localFriendsViewTag.showFrinedItem(this.giftNewsFragment, localFriend);
      }
      while (true)
      {
        return paramView;
        localFriendsViewTag = (GiftNewsFragment.FriendsViewTag)paramView.getTag();
        break;
        label124: localFriendsViewTag.tvText.setText(2131428445);
        break label99;
        label137: localFriendsViewTag.tvText.setText(2131428446);
        break label99;
        label150: localFriendsViewTag.showFrinedItem(this.giftNewsFragment, localFriend);
        localFriendsViewTag.sendGift.setOnClickListener(new View.OnClickListener(localFriend)
        {
          public void onClick(View paramView)
          {
            Intent localIntent = new Intent(GiftNewsFragment.GiftFriendsAdapter.this.giftNewsFragment.getActivity(), SendGiftFragment.class);
            Bundle localBundle = new Bundle();
            ArrayList localArrayList = new ArrayList();
            localArrayList.add(new FriendInfo(this.val$item.getFname(), this.val$item.getFuid(), this.val$item.getFlogo()));
            if (this.val$item.getBirthDisplay() != null)
            {
              localBundle.putString("giftId", "2");
              localBundle.putString("defaultps", "祝你生日快乐！(#生日蛋糕)");
            }
            localBundle.putSerializable("checkedFriendsList", localArrayList);
            localIntent.putExtras(localBundle);
            AnimationUtil.startFragment(GiftNewsFragment.GiftFriendsAdapter.this.giftNewsFragment, localIntent, 1);
            if (this.val$item.getBirthDisplay() != null)
            {
              UserHabitUtils.getInstance(GiftNewsFragment.GiftFriendsAdapter.this.giftNewsFragment.getActivity()).addUserHabit("Gift_Birthday_Send");
              return;
            }
            UserHabitUtils.getInstance(GiftNewsFragment.GiftFriendsAdapter.this.giftNewsFragment.getActivity()).addUserHabit("Gift_Attention_Send");
          }
        });
        localFriendsViewTag.rLogo.setOnClickListener(new View.OnClickListener(localFriend)
        {
          public void onClick(View paramView)
          {
            Intent localIntent = new Intent(GiftNewsFragment.GiftFriendsAdapter.this.giftNewsFragment.getActivity(), HomeFragment.class);
            Bundle localBundle = new Bundle();
            localBundle.putString("fuid", this.val$item.getFuid());
            localBundle.putString("fname", this.val$item.getFname());
            localBundle.putString("flogo", this.val$item.getFlogo());
            localIntent.putExtras(localBundle);
            GiftNewsFragment.GiftFriendsAdapter.this.giftNewsFragment.startFragment(localIntent, true, 1);
          }
        });
      }
    }

    public boolean isEnabled(int paramInt)
    {
      return false;
    }

    public void notifyDataSetChanged()
    {
      this.mAllList.clear();
      this.mAllList.add(null);
      if (this.giftNewsFragment.mBirthFriendList != null)
        this.mAllList.addAll(this.giftNewsFragment.mBirthFriendList);
      this.mAllList.add(null);
      if (this.giftNewsFragment.mFocusFriendList != null)
        this.mAllList.addAll(this.giftNewsFragment.mFocusFriendList);
      super.notifyDataSetChanged();
    }
  }

  public static class GiftNewsAdapter extends BaseAdapter
  {
    GiftNewsFragment giftNewsFragment;
    ArrayList<GiftNewsItem> list;
    LayoutInflater vi;

    public GiftNewsAdapter(GiftNewsFragment paramGiftNewsFragment, ArrayList<GiftNewsItem> paramArrayList)
    {
      this.giftNewsFragment = paramGiftNewsFragment;
      this.vi = ((LayoutInflater)paramGiftNewsFragment.getActivity().getSystemService("layout_inflater"));
      this.list = paramArrayList;
    }

    public int getCount()
    {
      ArrayList localArrayList = this.list;
      int i = 0;
      if (localArrayList != null)
        i = this.list.size();
      return i;
    }

    public Object getItem(int paramInt)
    {
      return this.list.get(paramInt);
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      GiftNewsItem localGiftNewsItem = (GiftNewsItem)getItem(paramInt);
      if (localGiftNewsItem.mSgiftLogoList.size() == 0)
        return GiftNewsFragment.mFooterView;
      GiftNewsFragment.GiftNewsItemViewTag localGiftNewsItemViewTag;
      if ((paramView == null) || (paramView == GiftNewsFragment.mFooterView))
      {
        paramView = this.vi.inflate(2130903143, null);
        localGiftNewsItemViewTag = new GiftNewsFragment.GiftNewsItemViewTag(paramView);
        paramView.setTag(localGiftNewsItemViewTag);
      }
      while (true)
      {
        localGiftNewsItemViewTag.showGiftNewsItem(this.giftNewsFragment, localGiftNewsItem);
        localGiftNewsItemViewTag.mUserLogo.setOnClickListener(new View.OnClickListener(localGiftNewsItem)
        {
          public void onClick(View paramView)
          {
            Intent localIntent = new Intent(GiftNewsFragment.GiftNewsAdapter.this.giftNewsFragment.getActivity(), HomeFragment.class);
            Bundle localBundle = new Bundle();
            localBundle.putString("fuid", this.val$item.mRuid);
            localBundle.putString("fname", this.val$item.mRname);
            localBundle.putString("flogo", this.val$item.mRlogo);
            localIntent.putExtras(localBundle);
            GiftNewsFragment.GiftNewsAdapter.this.giftNewsFragment.startFragment(localIntent, true, 1);
          }
        });
        localGiftNewsItemViewTag.sendGift.setOnClickListener(new View.OnClickListener(localGiftNewsItem)
        {
          public void onClick(View paramView)
          {
            Intent localIntent = new Intent(GiftNewsFragment.GiftNewsAdapter.this.giftNewsFragment.getActivity(), SendGiftFragment.class);
            Bundle localBundle = new Bundle();
            ArrayList localArrayList = new ArrayList();
            localArrayList.add(new FriendInfo(this.val$item.mRname, this.val$item.mRuid, this.val$item.mRlogo));
            localBundle.putSerializable("checkedFriendsList", localArrayList);
            localIntent.putExtras(localBundle);
            AnimationUtil.startFragment(GiftNewsFragment.GiftNewsAdapter.this.giftNewsFragment, localIntent, 1);
          }
        });
        return paramView;
        localGiftNewsItemViewTag = (GiftNewsFragment.GiftNewsItemViewTag)paramView.getTag();
      }
    }

    public boolean isEnabled(int paramInt)
    {
      return false;
    }
  }

  static class GiftNewsItemViewTag
  {
    ImageView mGiftLogo1;
    ImageView mGiftLogo2;
    KXIntroView mIntro;
    TextView mTime;
    ImageView mUserLogo;
    ImageView sendGift;

    public GiftNewsItemViewTag(View paramView)
    {
      this.mUserLogo = ((ImageView)paramView.findViewById(2131362491));
      this.mIntro = ((KXIntroView)paramView.findViewById(2131362492));
      this.mGiftLogo1 = ((ImageView)paramView.findViewById(2131362494));
      this.mGiftLogo2 = ((ImageView)paramView.findViewById(2131362495));
      this.mTime = ((TextView)paramView.findViewById(2131362496));
      this.sendGift = ((ImageView)paramView.findViewById(2131362456));
    }

    public void showGiftNewsItem(GiftNewsFragment paramGiftNewsFragment, GiftNewsItem paramGiftNewsItem)
    {
      paramGiftNewsFragment.displayRoundPicture(this.mUserLogo, paramGiftNewsItem.mRlogo, ImageDownloader.RoundCornerType.hdpi_big, 2130838334);
      ArrayList localArrayList = NewsInfo.makeIntroList(paramGiftNewsItem.mIntro);
      this.mIntro.setTitleList(localArrayList);
      this.mIntro.setOnClickLinkListener(new KXIntroView.OnClickLinkListener(paramGiftNewsFragment)
      {
        public void onClick(KXLinkInfo paramKXLinkInfo)
        {
          String str1 = paramKXLinkInfo.getId();
          String str2 = paramKXLinkInfo.getContent();
          IntentUtil.showHomeFragment(this.val$fragment, str1, str2);
        }
      });
      paramGiftNewsFragment.displayPicture(this.mGiftLogo1, (String)paramGiftNewsItem.mSgiftLogoList.get(0), 0);
      if (paramGiftNewsItem.mSgiftLogoList.size() > 1)
      {
        paramGiftNewsFragment.displayPicture(this.mGiftLogo2, (String)paramGiftNewsItem.mSgiftLogoList.get(1), 0);
        this.mGiftLogo2.setVisibility(0);
      }
      while (true)
      {
        this.mTime.setText(paramGiftNewsItem.mStime);
        return;
        this.mGiftLogo2.setVisibility(8);
      }
    }
  }

  static class NameIdItem
    implements Serializable
  {
    public final String mId;
    public final String mName;

    NameIdItem(String paramString1, String paramString2)
    {
      this.mName = paramString1;
      this.mId = paramString2;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.GiftNewsFragment
 * JD-Core Version:    0.6.0
 */