package com.kaixin001.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TableLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.activity.BumpFriendsActivity;
import com.kaixin001.engine.FriendsEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.item.FriendInfo;
import com.kaixin001.model.FriendsModel;
import com.kaixin001.model.FriendsModel.Friend;
import com.kaixin001.model.User;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.CrashRecoverUtil;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.ImageDownloader.RoundCornerType;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.KXUBLog;
import com.kaixin001.util.UserHabitUtils;
import com.kaixin001.view.AlphabetIndexerBar;
import com.kaixin001.view.AlphabetIndexerBar.OnIndexerChangedListener;
import com.kaixin001.view.KXTopTab;
import com.kaixin001.view.KXTopTabHost;
import com.kaixin001.view.KXTopTabHost.OnTabChangeListener;
import com.kaixin001.view.PullToRefreshView;
import com.kaixin001.view.PullToRefreshView.PullToRefreshListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FriendsFragment extends BaseFragment
  implements KXTopTabHost.OnTabChangeListener, View.OnClickListener, AdapterView.OnItemClickListener, TextWatcher, PullToRefreshView.PullToRefreshListener
{
  public static final int AT_MODE = 2;
  protected static final int COMMON_MODE = 0;
  public static final int FRIEND_SELECTED = 4;
  private static final int FRIEND_TAB = 0;
  public static final String MAX_NUM = "maxnum";
  protected static final int MENU_DESKTOP_ID = 403;
  protected static final int MENU_HOME_ME_ID = 404;
  protected static final int MENU_REFRESH_ID = 401;
  protected static final int MENU_TOP_ID = 402;
  public static final String MODE_KEY = "MODE";
  private static final String PIN_YIN = "abcdefghijklmnopqrstuvwxyz";
  private static final String PREFERENCE_KEY_BUMP_GUIDE_HAS_VIEWED = "bump_guid_has_viewed";
  public static final int SELECT_MODE = 1;
  private static final int STAR_TAB = 1;
  public static final String TAG = "FriendsActivity";
  private ImageView btnLeft;
  private ImageView btnRight;
  private TextView btnRightText;
  private Button cancelButton;
  private TextView centerText;
  private ArrayList<FriendInfo> checkedFriendsList = new ArrayList();
  private ListView friendListView;
  private FriendsAdapter friendsAdapter;
  private ArrayList<FriendsModel.Friend> friendslist = new ArrayList();
  private GetDataTask getDataTask;
  private LinearLayout headerProBarParent;
  private HorizontalScrollView hsView;
  private boolean mBirthListIsExpland = false;
  private LinearLayout mBrithFriendsList;
  private FriendsModel.Friend mCurrentItem = null;
  private PullToRefreshView mDownView;
  private Button mFindFriendBtn;
  public ArrayList<FriendsModel.Friend> mFriendBirthList = new ArrayList();
  private PopupWindow mSettingOptionPopupWindow;
  private AlphabetIndexerBar mViewAlphabetIndexer = null;
  private int maxNum = -1;
  private EditText metSearchHintTxt;
  private int mode = 0;
  private RelativeLayout searchLayout;
  private FriendsModel.Friend searchedItem = null;
  private KXTopTabHost tabHost;
  private int tabIndex = 0;
  private int[] tabIndexArray = new int[4];
  private RelativeLayout tabLayout;
  private TextView tvOnlineNum;

  private void cancelTask()
  {
    if ((this.getDataTask != null) && (this.getDataTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.getDataTask.cancel(true);
      if (FriendsEngine.getInstance() != null)
        FriendsEngine.getInstance().cancel();
    }
  }

  private void changeTab(FriendsModel.Friend paramFriend)
  {
    this.searchedItem = null;
    boolean bool = isStar(paramFriend);
    if ((this.tabHost.getCurrentTab() == 0) && (bool))
    {
      this.searchedItem = paramFriend;
      this.tabHost.setCurrentTab(1);
    }
    do
      return;
    while ((this.tabHost.getCurrentTab() != 1) || (bool));
    this.searchedItem = paramFriend;
    this.tabHost.setCurrentTab(0);
  }

  private void constructBirthFriendsViews()
  {
    if (this.mBrithFriendsList == null);
    do
    {
      return;
      this.mBrithFriendsList.removeAllViews();
    }
    while (this.mFriendBirthList.size() == 0);
    if (this.mFriendBirthList.size() < 12);
    int j;
    for (int i = 1 + this.mFriendBirthList.size(); ; i = 2 + this.mFriendBirthList.size())
    {
      j = 0;
      if (j < i)
        break;
      this.mBrithFriendsList.setVisibility(0);
      return;
    }
    LinearLayout localLinearLayout = new LinearLayout(getActivity());
    TableLayout.LayoutParams localLayoutParams = new TableLayout.LayoutParams();
    localLayoutParams.width = -1;
    localLayoutParams.height = -2;
    localLinearLayout.setLayoutParams(localLayoutParams);
    int m;
    ImageView localImageView1;
    TextView localTextView;
    for (int k = 0; ; k++)
    {
      if (k >= 6);
      do
      {
        this.mBrithFriendsList.addView(localLinearLayout);
        j += 6;
        break;
        m = j + k;
      }
      while ((j > 11) && (k > 4));
      if (m >= i)
        continue;
      View localView1 = getActivity().getLayoutInflater().inflate(2130903127, null, false);
      localImageView1 = (ImageView)localView1.findViewById(2131362361);
      ImageView localImageView2 = (ImageView)localView1.findViewById(2131362362);
      localTextView = (TextView)localView1.findViewById(2131362363);
      if ((m != 5) && ((m != i - 1) || (i > 6)))
        break label300;
      localImageView1.setVisibility(8);
      localImageView2.setBackgroundResource(2130837997);
      6 local6 = new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          ArrayList localArrayList = new ArrayList();
          Iterator localIterator = FriendsFragment.this.mFriendBirthList.iterator();
          while (true)
          {
            if (!localIterator.hasNext())
            {
              Intent localIntent = new Intent(FriendsFragment.this.getActivity(), SendGiftFragment.class);
              Bundle localBundle = new Bundle();
              localBundle.putString("giftId", "2");
              localBundle.putString("defaultps", "祝你生日快乐！(#生日蛋糕)");
              localBundle.putSerializable("checkedFriendsList", localArrayList);
              localIntent.putExtras(localBundle);
              AnimationUtil.startFragment(FriendsFragment.this, localIntent, 1);
              return;
            }
            FriendsModel.Friend localFriend = (FriendsModel.Friend)localIterator.next();
            if (localArrayList.size() >= 10)
              continue;
            localArrayList.add(new FriendInfo(localFriend.getFname(), localFriend.getFuid(), localFriend.getFlogo()));
          }
        }
      };
      localImageView2.setOnClickListener(local6);
      localTextView.setText(2131428442);
      localTextView.setTextColor(-4934476);
      localLinearLayout.addView(localView1);
    }
    label300: if ((m == 11) && (i > 12))
    {
      View localView2 = getActivity().getLayoutInflater().inflate(2130903128, null, false);
      ImageView localImageView3 = (ImageView)localView2.findViewById(2131362365);
      RelativeLayout localRelativeLayout = (RelativeLayout)localView2.findViewById(2131362364);
      if (this.mBirthListIsExpland)
        localImageView3.setBackgroundResource(2130838139);
      while (true)
      {
        localRelativeLayout.setTag(localImageView3);
        7 local7 = new View.OnClickListener()
        {
          public void onClick(View paramView)
          {
            ImageView localImageView = (ImageView)paramView.getTag();
            FriendsFragment localFriendsFragment;
            if (FriendsFragment.this.mBirthListIsExpland)
            {
              localImageView.setBackgroundResource(2130838125);
              localFriendsFragment = FriendsFragment.this;
              if (!FriendsFragment.this.mBirthListIsExpland)
                break label65;
            }
            label65: for (boolean bool = false; ; bool = true)
            {
              localFriendsFragment.mBirthListIsExpland = bool;
              FriendsFragment.this.constructBirthFriendsViews();
              return;
              localImageView.setBackgroundResource(2130838139);
              break;
            }
          }
        };
        localRelativeLayout.setOnClickListener(local7);
        localLinearLayout.addView(localView2);
        break;
        localImageView3.setBackgroundResource(2130838125);
        j = i;
      }
    }
    int n;
    if (m < 5)
      n = m;
    while (true)
    {
      displayRoundPicture(localImageView1, ((FriendsModel.Friend)this.mFriendBirthList.get(n)).getFlogo(), ImageDownloader.RoundCornerType.hdpi_small, 2130838334);
      localImageView1.setVisibility(0);
      localImageView1.setTag(this.mFriendBirthList.get(n));
      8 local8 = new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          ArrayList localArrayList = new ArrayList();
          FriendsModel.Friend localFriend = (FriendsModel.Friend)paramView.getTag();
          localArrayList.add(new FriendInfo(localFriend.getFname(), localFriend.getFuid(), localFriend.getFlogo()));
          Intent localIntent = new Intent(FriendsFragment.this.getActivity(), SendGiftFragment.class);
          Bundle localBundle = new Bundle();
          localBundle.putString("giftId", "2");
          localBundle.putString("defaultps", "祝你生日快乐！(#生日蛋糕)");
          localBundle.putSerializable("checkedFriendsList", localArrayList);
          localIntent.putExtras(localBundle);
          AnimationUtil.startFragment(FriendsFragment.this, localIntent, 1);
        }
      };
      localImageView1.setOnClickListener(local8);
      localTextView.setText(((FriendsModel.Friend)this.mFriendBirthList.get(n)).getFname());
      break;
      if (m < 12)
      {
        n = m - 1;
        continue;
      }
      n = m - 2;
    }
  }

  private void constructViews(View paramView)
  {
    LinearLayout localLinearLayout = (LinearLayout)paramView.findViewById(2131362375);
    localLinearLayout.removeAllViews();
    int i = 0;
    int j = this.checkedFriendsList.size();
    for (int k = 0; ; k++)
    {
      if (k >= j)
      {
        View localView2 = getActivity().getLayoutInflater().inflate(2130903129, null, false);
        ImageView localImageView1 = (ImageView)localView2.findViewById(2131362367);
        ImageView localImageView2 = (ImageView)localView2.findViewById(2131362368);
        localImageView1.setImageResource(2130838929);
        localImageView2.setVisibility(4);
        localImageView1.setBackgroundDrawable(null);
        localLinearLayout.addView(localView2);
        this.hsView.setVisibility(0);
        return;
      }
      View localView1 = getActivity().getLayoutInflater().inflate(2130903129, null, false);
      displayRoundPicture((ImageView)localView1.findViewById(2131362367), ((FriendInfo)this.checkedFriendsList.get(k)).getFlogo(), ImageDownloader.RoundCornerType.hdpi_small, 2130838334);
      localView1.setTag(Integer.valueOf(k));
      localView1.setClickable(true);
      localView1.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          int i = ((Integer)paramView.getTag()).intValue();
          FriendsFragment.this.checkedFriendsList.remove(i);
          FriendsFragment.this.friendsAdapter.notifyDataSetChanged();
        }
      });
      localLinearLayout.addView(localView1);
      i++;
    }
  }

  private void dealOnlineNum()
  {
    int i = this.tabHost.getCurrentTab();
    this.headerProBarParent.setVisibility(0);
    if ((i == 2) || ((i == 1) && (this.mode == 1)));
    for (String str1 = FriendsModel.getInstance().getStotal(); TextUtils.isEmpty(str1); str1 = FriendsModel.getInstance().getOnlinetotal())
      return;
    if ((i == 2) || ((i == 1) && (this.mode == 1)))
    {
      String str2 = getResources().getString(2131427466).replace("*", str1);
      this.tvOnlineNum.setText(str2);
      return;
    }
    TextView localTextView = this.tvOnlineNum;
    Resources localResources = getResources();
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.valueOf(this.mFriendBirthList.size());
    localTextView.setText(localResources.getString(2131428460, arrayOfObject));
  }

  private void getFriendsList(ArrayList<FriendsModel.Friend> paramArrayList)
  {
    this.friendslist.clear();
    if (paramArrayList != null)
      this.friendslist.addAll(paramArrayList);
    this.friendsAdapter.notifyDataSetChanged();
  }

  private int getSelectedIndex()
  {
    if (this.searchedItem == null)
    {
      i = -1;
      return i;
    }
    for (int i = 0; ; i++)
    {
      if (i >= this.friendslist.size())
        return -1;
      FriendsModel.Friend localFriend = (FriendsModel.Friend)this.friendslist.get(i);
      if (this.searchedItem.getFuid().equals(localFriend.getFuid()))
        break;
    }
  }

  private void hideInputMethod()
  {
    View localView = getActivity().getCurrentFocus();
    if (localView != null)
      ((InputMethodManager)getActivity().getSystemService("input_method")).hideSoftInputFromWindow(localView.getWindowToken(), 2);
  }

  private void initTabHost(View paramView)
  {
    this.tabHost = ((KXTopTabHost)paramView.findViewById(2131362370));
    this.tabHost.setOnTabChangeListener(this);
    this.tabLayout = ((RelativeLayout)paramView.findViewById(2131362373));
    this.hsView = ((HorizontalScrollView)paramView.findViewById(2131362374));
    KXTopTab localKXTopTab1 = new KXTopTab(getActivity());
    localKXTopTab1.setText(2131427461);
    this.tabHost.addTab(localKXTopTab1);
    if (this.mode == 0)
    {
      this.hsView.setVisibility(8);
      KXTopTab localKXTopTab2 = new KXTopTab(getActivity());
      localKXTopTab2.setText(2131427464);
      this.tabHost.addTab(localKXTopTab2);
      this.btnRight.setVisibility(8);
      this.btnRightText.setText(2131428086);
      this.btnLeft.setOnClickListener(this);
    }
    do
      while (true)
      {
        this.tabHost.setCurrentTab(0);
        return;
        if (this.mode != 1)
          break;
        this.btnLeft.setOnClickListener(this);
        this.btnRight.setImageResource(2130839022);
        this.btnRightText.setVisibility(8);
        ((ImageView)paramView.findViewById(2131362919)).setVisibility(8);
        ((TextView)paramView.findViewById(2131362920)).setText(2131427468);
        this.tabHost.setVisibility(8);
        this.hsView.setVisibility(0);
        constructViews(paramView);
      }
    while (this.mode != 2);
    this.hsView.setVisibility(8);
    this.btnLeft.setVisibility(8);
    this.btnRight.setImageResource(2130839016);
    this.btnRightText.setVisibility(8);
    ((ImageView)paramView.findViewById(2131362919)).setVisibility(8);
    TextView localTextView = (TextView)paramView.findViewById(2131362920);
    if (this.mode == 1)
      localTextView.setText(2131427468);
    while (true)
    {
      localTextView.setVisibility(0);
      this.tabLayout.setVisibility(8);
      break;
      localTextView.setText(2131427470);
    }
  }

  private void initTitle()
  {
    this.btnLeft = ((ImageView)findViewById(2131362914));
    this.btnRight = ((ImageView)findViewById(2131362928));
    this.btnRightText = ((TextView)findViewById(2131362930));
    this.btnLeft.setOnClickListener(this);
    this.btnRight.setOnClickListener(this);
    this.btnRightText.setOnClickListener(this);
    findViewById(2131362929).setVisibility(8);
    findViewById(2131362919).setVisibility(8);
    this.centerText = ((TextView)findViewById(2131362920));
    this.centerText.setVisibility(0);
    if (this.mode == 1)
    {
      this.centerText.setText(2131427590);
      this.btnRightText.setVisibility(8);
      this.btnRight.setVisibility(0);
    }
    View localView;
    while (true)
    {
      localView = findViewById(2131362371);
      this.mFindFriendBtn = ((Button)findViewById(2131362372));
      this.mFindFriendBtn.setOnClickListener(this);
      if (this.mode != 0)
        break;
      localView.setVisibility(0);
      return;
      if (this.mode == 2)
      {
        this.centerText.setText(2131427394);
        this.btnRightText.setVisibility(8);
        this.btnRight.setVisibility(0);
        continue;
      }
      this.centerText.setText(2131427394);
      this.btnRightText.setVisibility(0);
      this.btnRight.setVisibility(8);
      this.btnRightText.setWidth(dipToPx(74.0F));
      this.btnRightText.setTextSize(16.0F);
      this.btnRightText.setText(2131428086);
      this.btnRightText.setGravity(17);
      this.btnRightText.setVisibility(0);
    }
    localView.setVisibility(8);
  }

  private void initialData()
  {
    ArrayList localArrayList1 = FriendsModel.getInstance().getFriends();
    if ((localArrayList1 == null) || (localArrayList1.size() == 0));
    try
    {
      FriendsEngine.getInstance().loadFriendsCache(getActivity(), User.getInstance().getUID());
      ArrayList localArrayList2 = FriendsModel.getInstance().getFriends();
      if ((localArrayList2 != null) && (localArrayList2.size() > 0))
      {
        KXLog.d("friends", "not null");
        getFriendsList(FriendsModel.getInstance().getFriends());
      }
      if (!super.checkNetworkAndHint(true));
      return;
    }
    catch (SecurityErrorException localSecurityErrorException)
    {
      while (true)
        localSecurityErrorException.printStackTrace();
    }
  }

  private boolean isStar(FriendsModel.Friend paramFriend)
  {
    ArrayList localArrayList = FriendsModel.getInstance().getStarFriends();
    Iterator localIterator;
    if ((localArrayList != null) && (localArrayList.size() > 0))
      localIterator = localArrayList.iterator();
    FriendsModel.Friend localFriend;
    do
    {
      if (!localIterator.hasNext())
        return false;
      localFriend = (FriendsModel.Friend)localIterator.next();
    }
    while (!paramFriend.getFuid().equals(localFriend.getFuid()));
    return true;
  }

  private void refresh()
  {
    if (!super.checkNetworkAndHint(true))
    {
      stopPullToRefresh();
      return;
    }
    if (this.mode != 1);
    cancelTask();
    int i = 1;
    int j = this.tabHost.getCurrentTab();
    if (this.mode == 1)
      switch (j)
      {
      default:
      case 0:
      case 1:
      }
    while (true)
    {
      this.getDataTask = new GetDataTask(null);
      GetDataTask localGetDataTask = this.getDataTask;
      Integer[] arrayOfInteger = new Integer[1];
      arrayOfInteger[0] = Integer.valueOf(i);
      localGetDataTask.execute(arrayOfInteger);
      return;
      i = 1;
      continue;
      i = 3;
      continue;
      switch (j)
      {
      default:
        break;
      case 0:
        i = 0;
        break;
      case 1:
        i = 2;
        break;
      case 2:
        i = 3;
      }
    }
  }

  private void refreshData()
  {
    int i = this.tabHost.getCurrentTab();
    this.headerProBarParent.setVisibility(8);
    this.mBrithFriendsList.setVisibility(8);
    if (this.mode == 1)
    {
      switch (i)
      {
      default:
        return;
      case 0:
        this.searchLayout.setVisibility(0);
        getFriendsList(FriendsModel.getInstance().getFriends());
        return;
      case 1:
      }
      this.searchLayout.setVisibility(0);
      getFriendsList(FriendsModel.getInstance().getStarFriends());
      return;
    }
    switch (i)
    {
    default:
      return;
    case 0:
      this.searchLayout.setVisibility(0);
      if (this.mode == 0)
      {
        this.mBrithFriendsList.setVisibility(0);
        constructBirthFriendsViews();
        dealOnlineNum();
      }
      getFriendsList(FriendsModel.getInstance().getFriends());
      return;
    case 1:
    }
    this.searchLayout.setVisibility(0);
    getFriendsList(FriendsModel.getInstance().getStarFriends());
  }

  private void searchListData(ArrayList<FriendsModel.Friend> paramArrayList, String paramString)
  {
    Iterator localIterator1 = paramArrayList.iterator();
    label66: label213: 
    while (true)
    {
      if (!localIterator1.hasNext())
        return;
      FriendsModel.Friend localFriend = (FriendsModel.Friend)localIterator1.next();
      String[] arrayOfString1 = localFriend.getPy();
      ArrayList localArrayList = new ArrayList();
      int i = arrayOfString1.length;
      int j = 0;
      Iterator localIterator2;
      int n;
      if (j >= i)
      {
        localIterator2 = localArrayList.iterator();
        boolean bool = localIterator2.hasNext();
        n = 0;
        if (bool)
          break label166;
      }
      while (true)
      {
        if ((n != 0) || (!localFriend.getFname().startsWith(paramString)))
          break label213;
        this.friendslist.add(localFriend);
        break;
        String[] arrayOfString2 = arrayOfString1[j].split(",");
        int k = arrayOfString2.length;
        for (int m = 0; ; m++)
        {
          if (m >= k)
          {
            j++;
            break;
          }
          localArrayList.add(arrayOfString2[m]);
        }
        String str = (String)localIterator2.next();
        if ((!str.startsWith(paramString)) || (str.length() < paramString.length()))
          break label66;
        this.friendslist.add(localFriend);
        n = 1;
      }
    }
  }

  private void sendMsg(String paramString1, String paramString2, String paramString3)
  {
    Intent localIntent = new Intent(getActivity(), PostMessageFragment.class);
    Bundle localBundle = new Bundle();
    localBundle.putString("fuid", paramString1);
    localBundle.putString("fname", paramString2);
    localBundle.putString("flogo", paramString3);
    localBundle.putString("PreviousActivityName", "FriendsActivity");
    localIntent.putExtras(localBundle);
    AnimationUtil.startFragment(this, localIntent, 1);
  }

  private void stopPullToRefresh()
  {
    if ((this.mDownView != null) && (this.mDownView.isFrefrshing()))
      this.mDownView.onRefreshComplete();
  }

  public void afterTextChanged(Editable paramEditable)
  {
  }

  public void beforeTabChanged(int paramInt)
  {
    this.tabIndexArray[paramInt] = this.friendListView.getFirstVisiblePosition();
  }

  public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
  {
    this.friendslist.clear();
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if ((paramInt2 == -1) && (paramInt1 == 4) && (this.mode != 0))
    {
      String str1 = paramIntent.getStringExtra("fuid");
      String str2 = paramIntent.getStringExtra("fname");
      Intent localIntent = new Intent();
      localIntent.putExtra("fuid", str1);
      localIntent.putExtra("fname", str2);
      setResult(-1, localIntent);
      finishFragment(4);
    }
  }

  public void onClick(View paramView)
  {
    if (paramView.equals(this.btnLeft))
    {
      hideInputMethod();
      Bundle localBundle = getArguments();
      if ((localBundle != null) && (localBundle.getString("from") != null) && (localBundle.getString("from").equals("KaixinMenuListFragment")))
        if (super.isMenuListVisibleInMain())
          super.showSubActivityInMain();
    }
    do
    {
      do
        while (true)
        {
          return;
          super.showMenuListInMain();
          return;
          finish();
          return;
          if (!paramView.equals(this.btnRight))
            break;
          hideInputMethod();
          if (this.mode == 0)
            continue;
          if (this.mode == 1)
          {
            sendMessageWithList();
            return;
          }
          if (this.mode != 2)
            continue;
          finish();
          return;
        }
      while (paramView.equals(this.metSearchHintTxt));
      if (paramView.equals(this.cancelButton))
      {
        this.metSearchHintTxt.setText("");
        return;
      }
      if (!paramView.equals(this.btnRightText))
        continue;
      hideInputMethod();
      SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
      localEditor.putBoolean("bump_guid_has_viewed", true);
      localEditor.commit();
      Intent localIntent = new Intent(getActivity(), BumpFriendsActivity.class);
      AnimationUtil.startActivity(getActivity(), localIntent, 1);
      UserHabitUtils.getInstance(getActivity()).addUserHabit("friends_fragment_exchange_cards");
      return;
    }
    while (!paramView.equals(this.mFindFriendBtn));
    hideInputMethod();
    startFragment(new Intent(getActivity(), FindFriendFragment.class), true, 1);
    UserHabitUtils.getInstance(getActivity()).addUserHabit("friends_fragment_add_friend");
  }

  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (CrashRecoverUtil.isCrashBefore())
    {
      CrashRecoverUtil.crashRecover(getActivity().getApplicationContext());
      IntentUtil.returnToDesktop(getActivity());
      return;
    }
    Bundle localBundle = getArguments();
    if (localBundle != null)
    {
      this.mode = localBundle.getInt("MODE", 0);
      this.maxNum = localBundle.getInt("maxnum", -1);
      if (localBundle.getSerializable("checkedFriendsList") != null)
        this.checkedFriendsList = ((ArrayList)localBundle.getSerializable("checkedFriendsList"));
    }
    while (true)
    {
      UserHabitUtils.getInstance(getActivity()).addUserHabit("visit_friend_all");
      KXUBLog.log("homepage_friend");
      return;
      this.maxNum = -1;
    }
  }

  public void onCreateOptionsMenu(Menu paramMenu, MenuInflater paramMenuInflater)
  {
    super.onCreateOptionsMenu(paramMenu, paramMenuInflater);
    if ((this.mode == 1) || (this.mode == 2))
      return;
    paramMenu.add(0, 401, 0, 2131427690).setIcon(2130838607);
    paramMenu.add(0, 402, 0, 2131427698).setIcon(2130838609);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903130, paramViewGroup, false);
  }

  public void onDestroy()
  {
    cancelTask();
    if (this.friendslist != null)
      this.friendslist.clear();
    super.onDestroy();
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    FriendsModel.Friend localFriend;
    String str1;
    String str2;
    try
    {
      if (this.friendslist == null)
        return;
      if ((paramLong < 0L) || (paramLong >= this.friendslist.size()))
        return;
      localFriend = (FriendsModel.Friend)this.friendslist.get((int)paramLong);
      str1 = localFriend.getFuid();
      if (TextUtils.isEmpty(str1))
        return;
      str2 = localFriend.getFname();
      String str3 = localFriend.getFlogo();
      if (this.mode == 0)
      {
        IntentUtil.showHomeFragment(this, str1, str2, str3);
        return;
      }
    }
    catch (Exception localException)
    {
      KXLog.e("FriendsActivity", "onItemClick", localException);
      return;
    }
    if (this.mode == 1)
    {
      ((CheckBox)paramView.findViewById(2131362381)).performClick();
      changeTab(localFriend);
      return;
    }
    if (this.tabHost.getCurrentTab() == 0)
      FriendsModel.getInstance().setSelectedIndex(paramInt);
    Intent localIntent = new Intent();
    localIntent.putExtra("fuid", str1);
    localIntent.putExtra("fname", str2);
    setResult(-1, localIntent);
    finishFragment(4);
    finish();
  }

  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default:
      return super.onOptionsItemSelected(paramMenuItem);
    case 401:
      refresh();
      return true;
    case 402:
      this.friendListView.setSelection(0);
      return true;
    case 403:
      IntentUtil.returnToDesktop(getActivity());
      return true;
    case 404:
    }
    IntentUtil.showMyHomeFragment(this);
    return true;
  }

  public void onTabChanged(int paramInt)
  {
    AlphabetIndexerBar localAlphabetIndexerBar;
    if (paramInt == 0)
    {
      UserHabitUtils.getInstance(getActivity()).addUserHabit("visit_friend_all");
      if ((FriendsModel.getInstance().getVisitors().size() == 0) || (FriendsModel.getInstance().getStarFriends().size() == 0))
        refresh();
      if (this.mViewAlphabetIndexer != null)
      {
        localAlphabetIndexerBar = this.mViewAlphabetIndexer;
        if (paramInt != 0)
          break label147;
      }
    }
    label147: for (int k = 0; ; k = 4)
    {
      localAlphabetIndexerBar.setVisibility(k);
      this.metSearchHintTxt.setText("");
      refreshData();
      int i = this.tabIndexArray[paramInt];
      if ((this.mode == 1) && (this.searchedItem != null))
      {
        int j = getSelectedIndex();
        if (j != -1)
          i = j + 1;
      }
      this.friendListView.setSelection(i);
      return;
      UserHabitUtils.getInstance(getActivity()).addUserHabit("visit_friend_star");
      break;
    }
  }

  public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
  {
    if (isNeedReturn())
      return;
    String str = this.metSearchHintTxt.getText().toString().trim().toLowerCase();
    if (TextUtils.isEmpty(str))
    {
      refreshData();
      this.friendsAdapter.notifyDataSetChanged();
      this.cancelButton.setVisibility(8);
      this.friendListView.requestFocus();
      hideInputMethod();
      return;
    }
    this.friendslist.clear();
    this.cancelButton.setVisibility(0);
    if (this.tabHost.getCurrentTab() == 0)
      searchListData(FriendsModel.getInstance().getFriends(), str);
    while (true)
    {
      if (this.friendslist.size() == 0)
      {
        FriendsModel localFriendsModel = FriendsModel.getInstance();
        localFriendsModel.getClass();
        FriendsModel.Friend localFriend = new FriendsModel.Friend(localFriendsModel);
        localFriend.setSearchTxt(str);
        this.friendslist.add(localFriend);
      }
      this.friendsAdapter.notifyDataSetChanged();
      return;
      if (this.tabHost.getCurrentTab() != 1)
        continue;
      searchListData(FriendsModel.getInstance().getStarFriends(), str);
    }
  }

  public void onUpdate()
  {
    refresh();
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    this.mDownView = ((PullToRefreshView)paramView.findViewById(2131361814));
    this.mDownView.setPullToRefreshListener(this);
    this.mViewAlphabetIndexer = ((AlphabetIndexerBar)paramView.findViewById(2131362143));
    this.mViewAlphabetIndexer.setOnIndexerChangedListener(new AlphabetIndexerBar.OnIndexerChangedListener()
    {
      public void onIndexerChanged(String paramString)
      {
        if (paramString == null);
        Object[] arrayOfObject;
        do
        {
          return;
          if (paramString.equals(" "))
          {
            FriendsFragment.this.friendListView.setSelection(0);
            return;
          }
          arrayOfObject = FriendsFragment.this.friendsAdapter.getSections();
        }
        while ((arrayOfObject == null) || (arrayOfObject.length == 0));
        for (int i = -1 + arrayOfObject.length; ; i--)
        {
          int j = 0;
          if (i < 0);
          while (true)
          {
            int k = j + FriendsFragment.this.friendListView.getHeaderViewsCount();
            if (k < 0)
              k = 0;
            FriendsFragment.this.friendListView.setSelection(k);
            return;
            if (paramString.compareTo(arrayOfObject[i].toString()) < 0)
              break;
            j = FriendsFragment.this.friendsAdapter.getPositionForSection(i);
          }
        }
      }
    });
    this.friendListView = ((ListView)paramView.findViewById(2131362376));
    View localView = ((LayoutInflater)getActivity().getSystemService("layout_inflater")).inflate(2130903126, null);
    this.searchLayout = ((RelativeLayout)localView.findViewById(2131362160));
    this.metSearchHintTxt = ((EditText)localView.findViewById(2131363601));
    this.cancelButton = ((Button)localView.findViewById(2131363602));
    this.metSearchHintTxt.setOnClickListener(this);
    this.metSearchHintTxt.addTextChangedListener(this);
    this.metSearchHintTxt.setOnFocusChangeListener(new View.OnFocusChangeListener()
    {
      public void onFocusChange(View paramView, boolean paramBoolean)
      {
        if ((!paramBoolean) && (FriendsFragment.this.mode != 0));
      }
    });
    this.metSearchHintTxt.setOnLongClickListener(new View.OnLongClickListener()
    {
      public boolean onLongClick(View paramView)
      {
        return true;
      }
    });
    this.cancelButton.setOnClickListener(this);
    this.headerProBarParent = ((LinearLayout)localView.findViewById(2131362357));
    this.tvOnlineNum = ((TextView)localView.findViewById(2131362358));
    this.mBrithFriendsList = ((LinearLayout)localView.findViewById(2131362359));
    this.friendListView.addHeaderView(localView);
    this.friendsAdapter = new FriendsAdapter(getActivity(), 2130903131, this.friendslist);
    this.friendListView.setAdapter(this.friendsAdapter);
    this.friendListView.setOnItemClickListener(this);
    this.friendListView.setOnScrollListener(new AbsListView.OnScrollListener()
    {
      public void onScroll(AbsListView paramAbsListView, int paramInt1, int paramInt2, int paramInt3)
      {
      }

      public void onScrollStateChanged(AbsListView paramAbsListView, int paramInt)
      {
        FriendsFragment.this.friendListView.requestFocus();
        FriendsFragment.this.hideInputMethod();
      }
    });
    this.friendListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
    {
      public boolean onItemLongClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
      {
        if (FriendsFragment.this.friendslist == null)
          return false;
        if ((paramLong < 0L) || (paramLong >= FriendsFragment.this.friendslist.size()))
          return false;
        String[] arrayOfString = new String[1];
        arrayOfString[0] = FriendsFragment.this.getString(2131427699);
        FriendsFragment.this.mCurrentItem = ((FriendsModel.Friend)FriendsFragment.this.friendslist.get((int)paramLong));
        if (FriendsFragment.this.mCurrentItem == null)
          return false;
        DialogUtil.showSelectListDlg(FriendsFragment.this.getActivity(), 2131427509, arrayOfString, new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramDialogInterface, int paramInt)
          {
            if (paramInt == 0)
              FriendsFragment.this.sendMsg(FriendsFragment.this.mCurrentItem.getFuid(), FriendsFragment.this.mCurrentItem.getFname(), FriendsFragment.this.mCurrentItem.getFlogo());
          }
        }
        , 1);
        return true;
      }
    });
    initTitle();
    initTabHost(paramView);
    initialData();
    this.friendListView.requestFocus();
    refresh();
  }

  void sendMessageWithList()
  {
    Intent localIntent = new Intent();
    Bundle localBundle = new Bundle();
    localBundle.putSerializable("checkedFriendsList", this.checkedFriendsList);
    localIntent.putExtra("bundle", localBundle);
    setResult(-1, localIntent);
    finishFragment(4);
    finish();
  }

  private class FriendItemViewTag
  {
    private View findInKaixinLayout = null;
    private View friendItemLayout = null;
    private CheckBox ivBox = null;
    private ImageView ivEnd = null;
    private ImageView ivLogo = null;
    private ImageView ivOnline = null;
    private View noSearchLayout = null;
    private TextView tvFindInKaixin = null;
    private TextView tvName = null;
    private TextView tvSection = null;
    private ImageView tvSectionDivid = null;
    private TextView tvTime = null;

    private FriendItemViewTag(View arg2)
    {
      Object localObject;
      this.tvName = ((TextView)localObject.findViewById(2131362380));
      this.tvTime = ((TextView)localObject.findViewById(2131362382));
      this.tvSection = ((TextView)localObject.findViewById(2131362144));
      this.tvSectionDivid = ((ImageView)localObject.findViewById(2131362377));
      this.ivLogo = ((ImageView)localObject.findViewById(2131362000));
      this.ivOnline = ((ImageView)localObject.findViewById(2131362002));
      this.ivEnd = ((ImageView)localObject.findViewById(2131362383));
      this.ivBox = ((CheckBox)localObject.findViewById(2131362381));
      this.friendItemLayout = localObject.findViewById(2131362378);
      this.noSearchLayout = localObject.findViewById(2131362384);
      this.findInKaixinLayout = localObject.findViewById(2131362387);
      this.tvFindInKaixin = ((TextView)localObject.findViewById(2131362389));
    }

    private int getSearchPosition(String paramString)
    {
      ArrayList localArrayList = FriendsFragment.this.friendslist;
      int i = 0;
      if (localArrayList != null)
      {
        int j = FriendsFragment.this.friendslist.size();
        i = 0;
        if (j <= 0);
      }
      for (int k = 0; ; k++)
      {
        if (k >= FriendsFragment.this.friendslist.size());
        while (true)
        {
          return i;
          i++;
          if (!((FriendsModel.Friend)FriendsFragment.this.friendslist.get(k)).getFuid().equals(paramString))
            break;
          String[] arrayOfString = ((FriendsModel.Friend)FriendsFragment.this.friendslist.get(k)).getPy();
          if ((arrayOfString == null) || (arrayOfString.length <= 0))
            continue;
          int m = arrayOfString[0].toLowerCase().indexOf("abcdefghijklmnopqrstuvwxyz");
          if (m >= 0)
            return i + (m + 1);
        }
      }
    }

    private void updateFriend(FriendsModel.Friend paramFriend, boolean paramBoolean1, boolean paramBoolean2)
    {
      if (paramFriend.getSearchTxt() != null)
      {
        String str5 = paramFriend.getSearchTxt();
        this.friendItemLayout.setVisibility(8);
        this.noSearchLayout.setVisibility(0);
        this.tvFindInKaixin.setText("在开心网搜索：" + str5);
        this.findInKaixinLayout.setOnClickListener(new View.OnClickListener(str5)
        {
          public void onClick(View paramView)
          {
            Intent localIntent = new Intent(FriendsFragment.this.getActivity(), FindFriendListFragment.class);
            Bundle localBundle = new Bundle();
            localBundle.putInt("mode", FindFriendListFragment.MODE_SEARCH_NAME);
            localBundle.putString("person_name", this.val$name);
            localIntent.putExtras(localBundle);
            FriendsFragment.this.startFragment(localIntent, true, 1);
          }
        });
        return;
      }
      this.friendItemLayout.setVisibility(0);
      this.noSearchLayout.setVisibility(8);
      if (FriendsFragment.this.mode != 1)
        this.ivBox.setVisibility(8);
      String str1 = paramFriend.getFlogo();
      String str2 = paramFriend.getFuid();
      FriendsFragment.this.displayRoundPicture(this.ivLogo, str1, ImageDownloader.RoundCornerType.hdpi_small, 2130838334);
      String str3 = paramFriend.getFname();
      this.tvName.setText(str3);
      this.tvName.setPadding(0, 2, 0, 2);
      String str4 = paramFriend.getOnline();
      label205: int i;
      label218: Iterator localIterator;
      label255: int j;
      if ("1".equals(str4))
      {
        this.ivOnline.setImageResource(2130838704);
        if (!paramBoolean1)
          break label349;
        this.tvTime.setText(paramFriend.getStrvtime());
        ImageView localImageView = this.ivEnd;
        if (!paramBoolean2)
          break label360;
        i = 0;
        localImageView.setVisibility(i);
        this.ivBox.setTag(paramFriend);
        if (FriendsFragment.this.checkedFriendsList == null)
          break label402;
        localIterator = FriendsFragment.this.checkedFriendsList.iterator();
        boolean bool = localIterator.hasNext();
        j = 0;
        if (bool)
          break label367;
        label272: if (j == 0)
          this.ivBox.setChecked(false);
      }
      while (true)
      {
        this.ivBox.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramView)
          {
            FriendsFragment.this.constructViews(FriendsFragment.this.getView());
          }
        });
        this.ivBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
          public void onCheckedChanged(CompoundButton paramCompoundButton, boolean paramBoolean)
          {
            FriendsModel.Friend localFriend = (FriendsModel.Friend)paramCompoundButton.getTag();
            int i = FriendsFragment.this.checkedFriendsList.size();
            int j = -1;
            String str = localFriend.getFuid();
            int k = 0;
            int m = 0;
            if (k >= i)
            {
              label45: if ((FriendsFragment.this.checkedFriendsList.size() <= 29) || (!paramBoolean) || (m != 0))
                break label146;
              DialogUtil.showKXAlertDialog(FriendsFragment.this.getActivity(), 2131427469, null);
              FriendsFragment.FriendItemViewTag.this.ivBox.setChecked(false);
            }
            label146: label360: 
            do
            {
              FriendInfo localFriendInfo;
              do
              {
                return;
                if (((FriendInfo)FriendsFragment.this.checkedFriendsList.get(k)).getFuid().equals(str))
                {
                  m = 1;
                  j = k;
                  break label45;
                }
                k++;
                break;
                if ((FriendsFragment.this.maxNum > 0) && (FriendsFragment.this.checkedFriendsList.size() > FriendsFragment.this.maxNum) && (paramBoolean) && (m == 0))
                {
                  DialogUtil.showKXAlertDialog(FriendsFragment.this.getActivity(), FriendsFragment.this.getResources().getString(2131428453).toString(), null);
                  FriendsFragment.FriendItemViewTag.this.ivBox.setChecked(false);
                  return;
                }
                if (!paramBoolean)
                  break label360;
                if ((m == 0) && (!TextUtils.isEmpty(FriendsFragment.this.metSearchHintTxt.getText())))
                {
                  FriendsFragment.this.metSearchHintTxt.setText("");
                  FriendsFragment.this.friendListView.setSelection(FriendsFragment.FriendItemViewTag.this.getSearchPosition(localFriend.getFuid()));
                }
                FriendsFragment.FriendItemViewTag.this.ivBox.setChecked(true);
                localFriendInfo = new FriendInfo(localFriend.getFname(), localFriend.getFuid(), localFriend.getFlogo());
              }
              while (m != 0);
              FriendsFragment.this.checkedFriendsList.add(localFriendInfo);
              return;
              if ((m != 0) && (!TextUtils.isEmpty(FriendsFragment.this.metSearchHintTxt.getText())))
                FriendsFragment.this.metSearchHintTxt.setText("");
              FriendsFragment.FriendItemViewTag.this.ivBox.setChecked(false);
            }
            while (j == -1);
            FriendsFragment.this.checkedFriendsList.remove(j);
          }
        });
        return;
        if ("2".equals(str4))
        {
          this.ivOnline.setImageResource(2130838621);
          break;
        }
        this.ivOnline.setImageBitmap(null);
        break;
        label349: this.tvTime.setText(null);
        break label205;
        label360: i = 8;
        break label218;
        label367: if (!((FriendInfo)localIterator.next()).getFuid().equals(str2))
          break label255;
        this.ivBox.setChecked(true);
        j = 1;
        break label272;
        label402: this.ivBox.setChecked(false);
      }
    }

    public void setSection(String paramString)
    {
      if (TextUtils.isEmpty(paramString))
      {
        this.tvSection.setVisibility(8);
        this.tvSectionDivid.setVisibility(8);
        return;
      }
      this.tvSection.setVisibility(0);
      this.tvSectionDivid.setVisibility(0);
      this.tvSection.setText(paramString);
    }
  }

  private class FriendsAdapter extends ArrayAdapter<FriendsModel.Friend>
    implements SectionIndexer
  {
    private Context mContext;
    private ArrayList<Integer> mListSecPos = new ArrayList();
    private ArrayList<String> mListSections = new ArrayList();

    public FriendsAdapter(int paramArrayList, ArrayList<FriendsModel.Friend> arg3)
    {
      super(i, localList);
      this.mContext = paramArrayList;
    }

    public int getPositionForSection(int paramInt)
    {
      if ((paramInt < 0) || (paramInt >= this.mListSecPos.size()))
        return 0;
      return ((Integer)this.mListSecPos.get(paramInt)).intValue();
    }

    public int getSectionForPosition(int paramInt)
    {
      int i = 0;
      if (paramInt < 0)
        i = 0;
      while (true)
      {
        return i;
        if (paramInt >= FriendsFragment.this.friendslist.size())
          return -1 + this.mListSecPos.size();
        for (int j = 0; (j < this.mListSecPos.size()) && (paramInt >= ((Integer)this.mListSecPos.get(j)).intValue()); j++)
          i = j;
      }
    }

    public Object[] getSections()
    {
      return this.mListSections.toArray();
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      int i = 1;
      int k = FriendsFragment.this.tabHost.getCurrentTab();
      FriendsModel.Friend localFriend = (FriendsModel.Friend)FriendsFragment.this.friendslist.get(paramInt);
      FriendsFragment.FriendItemViewTag localFriendItemViewTag;
      int n;
      label146: label168: int i1;
      if (paramView == null)
      {
        paramView = ((LayoutInflater)this.mContext.getSystemService("layout_inflater")).inflate(2130903131, null);
        localFriendItemViewTag = new FriendsFragment.FriendItemViewTag(FriendsFragment.this, paramView, null);
        paramView.setTag(localFriendItemViewTag);
        int m = -1;
        n = 0;
        if (k == 0)
        {
          m = getSectionForPosition(paramInt);
          int i3 = this.mListSections.size();
          n = 0;
          if (m < i3)
          {
            if ((paramInt != getPositionForSection(m)) || (((String)this.mListSections.get(m)).equals(" ")))
              break label232;
            n = i;
          }
        }
        if (n == 0)
          break label238;
        localFriendItemViewTag.setSection((String)this.mListSections.get(m));
        if (k != i)
          break label247;
        i1 = i;
        label179: if (FriendsFragment.this.mode == i)
        {
          if (k != i)
            break label253;
          i1 = i;
        }
        label202: if (k == 0)
          break label259;
      }
      while (true)
      {
        localFriendItemViewTag.updateFriend(localFriend, i1, i);
        return paramView;
        localFriendItemViewTag = (FriendsFragment.FriendItemViewTag)paramView.getTag();
        break;
        label232: n = 0;
        break label146;
        label238: localFriendItemViewTag.setSection(null);
        break label168;
        label247: int i2 = 0;
        break label179;
        label253: i2 = 0;
        break label202;
        label259: int j = 0;
      }
    }

    public void notifyDataSetChanged()
    {
      this.mListSections.clear();
      this.mListSecPos.clear();
      Object localObject1 = "-1";
      int i = 0;
      if (i >= FriendsFragment.this.friendslist.size())
      {
        if (FriendsFragment.this.mode == 1)
          FriendsFragment.this.constructViews(FriendsFragment.this.getView());
        super.notifyDataSetChanged();
        return;
      }
      String[] arrayOfString = ((FriendsModel.Friend)FriendsFragment.this.friendslist.get(i)).getPy();
      String str;
      if ((arrayOfString == null) || (arrayOfString.length == 0))
      {
        str = "";
        label94: if (!TextUtils.isEmpty(str))
          break label152;
      }
      label152: for (Object localObject2 = localObject1; ; localObject2 = str.substring(0, 1).toUpperCase())
      {
        if (!((String)localObject2).equals(localObject1))
        {
          localObject1 = localObject2;
          this.mListSections.add(localObject1);
          this.mListSecPos.add(Integer.valueOf(i));
        }
        i++;
        break;
        str = arrayOfString[0];
        break label94;
      }
    }
  }

  private class GetDataTask extends KXFragment.KXAsyncTask<Integer, Void, Integer>
  {
    private GetDataTask()
    {
      super();
    }

    protected Integer doInBackgroundA(Integer[] paramArrayOfInteger)
    {
      if ((paramArrayOfInteger == null) || (paramArrayOfInteger.length == 0))
        return Integer.valueOf(0);
      try
      {
        if (((paramArrayOfInteger[0].intValue() == 1) || (paramArrayOfInteger[0].intValue() == 0)) && (!FriendsEngine.getInstance().getFriendsList(FriendsFragment.this.getActivity().getApplicationContext(), 6)))
          return Integer.valueOf(0);
        if (FriendsEngine.getInstance().getFriendsList(FriendsFragment.this.getActivity().getApplicationContext(), paramArrayOfInteger[0].intValue()))
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
      if ((FriendsFragment.this.getActivity() == null) || (FriendsFragment.this.getView() == null) || (paramInteger == null))
        return;
      while (true)
      {
        try
        {
          if (paramInteger.intValue() != 1)
            continue;
          FriendsFragment.this.mFriendBirthList.clear();
          FriendsFragment.this.mFriendBirthList.addAll(FriendsModel.getInstance().getBirthFriends());
          FriendsFragment.this.refreshData();
          if (FriendsFragment.this.mode != 2)
            continue;
          FriendsFragment.this.btnRight.setImageResource(2130839016);
          FriendsFragment.this.btnRightText.setVisibility(8);
          FriendsFragment.this.getDataTask = null;
          return;
          Toast.makeText(FriendsFragment.this.getActivity(), 2131427547, 0).show();
          continue;
        }
        catch (Exception localException)
        {
          KXLog.e("FriendsActivity", "onPostExecute", localException);
          return;
          if (FriendsFragment.this.mode == 1)
          {
            FriendsFragment.this.btnRight.setImageResource(2130839022);
            FriendsFragment.this.btnRightText.setVisibility(8);
            continue;
          }
        }
        finally
        {
          FriendsFragment.this.stopPullToRefresh();
        }
        FriendsFragment.this.btnRight.setVisibility(8);
        FriendsFragment.this.btnRightText.setText(2131428086);
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
 * Qualified Name:     com.kaixin001.fragment.FriendsFragment
 * JD-Core Version:    0.6.0
 */