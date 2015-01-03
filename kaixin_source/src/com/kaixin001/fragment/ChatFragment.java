package com.kaixin001.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.activity.MessageHandlerHolder;
import com.kaixin001.adapter.CirclesAdapter;
import com.kaixin001.engine.CirclesEngine;
import com.kaixin001.engine.FriendsEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.item.ChatInfoItem.CircleMemberChanged;
import com.kaixin001.model.ChatModel;
import com.kaixin001.model.ChatModel.Conversition;
import com.kaixin001.model.ChatModel.ConversitionList;
import com.kaixin001.model.CircleModel;
import com.kaixin001.model.CircleModel.CircleItem;
import com.kaixin001.model.FriendsModel;
import com.kaixin001.model.FriendsModel.Friend;
import com.kaixin001.model.User;
import com.kaixin001.service.RefreshNewMessageService;
import com.kaixin001.util.ActivityUtil;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.ImageDownloader.RoundCornerType;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.KXUBLog;
import com.kaixin001.view.KXTopTab;
import com.kaixin001.view.KXTopTabHost;
import com.kaixin001.view.KXTopTabHost.OnTabChangeListener;
import java.util.ArrayList;
import java.util.Iterator;

public class ChatFragment extends BaseFragment
  implements View.OnClickListener, KXTopTabHost.OnTabChangeListener, AdapterView.OnItemClickListener
{
  private final int MAX_CONVERSIION_COUNT = 50;
  private final int UNREAD_CHANGED_ISCLEAR = 1;
  private final int UNREAD_CHANGED_ISGROUP = 1;
  private final ChatAdapter mAdapter = new ChatAdapter(null);
  private final ArrayList<CircleModel.CircleItem> mCircleList = new ArrayList();
  private final ChatModel.ConversitionList mConversitionList = new ChatModel.ConversitionList();
  private int mConversitionUnread = 0;
  private int mCurTabIndex = -1;
  private final ArrayList<FriendsModel.Friend> mFriendList = new ArrayList();
  private int mFriendsUnread = 0;
  private GetCirclesTask mGetCirclesTask;
  private GetFriendsDataTask mGetFriendsTask;
  private View mHeaderView;
  private ListView mListView = null;
  private int mPositionClicked = -1;
  private ProgressBar mProgressBarWait = null;
  private ProgressDialog mProgressDlg = null;
  private int mTabIndex = 0;
  private TextView mTxtFriendsNum = null;
  private TextView mTxtTips = null;
  private View mWaitLayout = null;
  private boolean needGetCircleList = true;
  private boolean needGetFriendsList = true;
  private String searchCode = null;
  private KXTopTabHost tabHost = null;

  private void clearData(int paramInt)
  {
    if ((paramInt != 0) && (paramInt == 1))
      this.mListView.removeHeaderView(this.mHeaderView);
  }

  private void handleCircleMemberChangeMessage(ArrayList<ChatInfoItem.CircleMemberChanged> paramArrayList)
  {
    Iterator localIterator;
    if ((paramArrayList != null) && (paramArrayList.size() > 0))
      localIterator = paramArrayList.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
      {
        if ((this.mTabIndex != 1) && (this.mAdapter != null))
          this.mAdapter.notifyDataSetChanged();
        return;
      }
      ChatInfoItem.CircleMemberChanged localCircleMemberChanged = (ChatInfoItem.CircleMemberChanged)localIterator.next();
      if (localCircleMemberChanged.isSelfKicked())
      {
        Message localMessage = Message.obtain();
        localMessage.what = 90013;
        localMessage.obj = localCircleMemberChanged.mGID;
        MessageHandlerHolder.getInstance().fireMessage(localMessage);
        continue;
      }
      if (!localCircleMemberChanged.isSelfJoinin())
        continue;
      CircleModel.getInstance().addCircleItem(localCircleMemberChanged.mGID, localCircleMemberChanged.mCircleName, localCircleMemberChanged.mCircleType);
    }
  }

  private void hideInputMethod()
  {
  }

  private void initHeaderView()
  {
    this.mHeaderView = ((LayoutInflater)getActivity().getSystemService("layout_inflater")).inflate(2130903066, null);
    this.mTxtFriendsNum = ((TextView)this.mHeaderView.findViewById(2131361996));
  }

  private void initTabHost(View paramView)
  {
    this.tabHost = ((KXTopTabHost)paramView.findViewById(2131361964));
    this.tabHost.clean();
    this.tabHost.setOnTabChangeListener(this);
    KXTopTab localKXTopTab1 = new KXTopTab(getActivity());
    localKXTopTab1.setText(2131428055);
    if ((this.mConversitionUnread > 0) && (0 == 0))
      BitmapFactory.decodeResource(getResources(), 2130839000);
    this.tabHost.addTab(localKXTopTab1);
    KXTopTab localKXTopTab2 = new KXTopTab(getActivity());
    localKXTopTab2.setText(2131428056);
    this.tabHost.addTab(localKXTopTab2);
    KXTopTab localKXTopTab3 = new KXTopTab(getActivity());
    localKXTopTab3.setText(2131428057);
    this.tabHost.addTab(localKXTopTab3);
    if ((this.mCurTabIndex < 0) || (this.mCurTabIndex > 2))
      this.mCurTabIndex = 1;
    this.tabHost.setCurrentTab(this.mCurTabIndex);
  }

  private void initTitleBar(View paramView)
  {
    ImageView localImageView = (ImageView)paramView.findViewById(2131362914);
    localImageView.setVisibility(0);
    localImageView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        ChatFragment.this.finish();
      }
    });
    ((ImageView)paramView.findViewById(2131362928)).setVisibility(8);
    ((ImageView)paramView.findViewById(2131362919)).setVisibility(8);
    TextView localTextView = (TextView)paramView.findViewById(2131362920);
    localTextView.setText(2131428050);
    localTextView.setVisibility(0);
  }

  private void startGetCirclesTask()
  {
    if ((!this.needGetCircleList) || ((this.mGetCirclesTask != null) && (!this.mGetCirclesTask.isCancelled()) && (this.mGetCirclesTask.getStatus() != AsyncTask.Status.FINISHED)))
      return;
    this.mGetCirclesTask = new GetCirclesTask(null);
    GetCirclesTask localGetCirclesTask = this.mGetCirclesTask;
    Integer[] arrayOfInteger = new Integer[2];
    arrayOfInteger[0] = Integer.valueOf(0);
    arrayOfInteger[1] = Integer.valueOf(1000);
    localGetCirclesTask.execute(arrayOfInteger);
  }

  private void startGetFriendsTask()
  {
    if ((!this.needGetFriendsList) || ((this.mGetFriendsTask != null) && (!this.mGetFriendsTask.isCancelled()) && (this.mGetFriendsTask.getStatus() != AsyncTask.Status.FINISHED)))
      return;
    this.mGetFriendsTask = new GetFriendsDataTask(null);
    GetFriendsDataTask localGetFriendsDataTask = this.mGetFriendsTask;
    Integer[] arrayOfInteger = new Integer[1];
    arrayOfInteger[0] = Integer.valueOf(4);
    localGetFriendsDataTask.execute(arrayOfInteger);
  }

  private void switchServiceMode(boolean paramBoolean)
  {
    RefreshNewMessageService localRefreshNewMessageService = RefreshNewMessageService.getInstance();
    if (localRefreshNewMessageService != null)
      localRefreshNewMessageService.switchMode(paramBoolean);
  }

  private void updateCirclesData(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.mCircleList.clear();
      if (this.needGetCircleList)
        startGetCirclesTask();
      ArrayList localArrayList = CircleModel.getInstance().getCircles();
      if ((localArrayList != null) && (localArrayList.size() > 0))
        this.mCircleList.addAll(localArrayList);
      KXLog.d("TEST", "chatCircle:" + this.mCircleList.size());
    }
    if (this.mAdapter != null)
      this.mAdapter.notifyDataSetChanged();
    if (this.mCircleList.size() == 0)
    {
      this.mListView.setVisibility(8);
      this.mWaitLayout.setVisibility(0);
      this.mProgressBarWait.setVisibility(8);
      this.mTxtTips.setVisibility(0);
      this.mTxtTips.setText(2131428074);
      return;
    }
    this.mListView.setVisibility(0);
    this.mWaitLayout.setVisibility(8);
  }

  private boolean updateConversitionUnread()
  {
    int i = ChatModel.getInstance().getConversitionUnreadNum();
    int j = this.mConversitionUnread;
    int k = 0;
    if (j != i)
    {
      this.mConversitionUnread = i;
      k = 1;
    }
    return k;
  }

  private void updateConversitonData(boolean paramBoolean)
  {
    ChatModel.ConversitionList localConversitionList;
    int j;
    if (paramBoolean)
    {
      this.mConversitionList.clear();
      localConversitionList = ChatModel.getInstance().getSortedConversitions();
      if (localConversitionList != null)
      {
        int i = localConversitionList.size();
        if (i > 50)
        {
          j = i - 1;
          if (j >= 50)
            break label175;
        }
        if (i > 0)
          this.mConversitionList.addAll(localConversitionList);
      }
    }
    if (this.mAdapter != null)
      this.mAdapter.notifyDataSetChanged();
    this.mWaitLayout.setVisibility(8);
    this.mProgressBarWait.setVisibility(8);
    if ((FriendsModel.getInstance().getFriends() != null) && (FriendsModel.getInstance().getFriends().size() != 0))
    {
      this.mWaitLayout.setVisibility(8);
      this.mProgressBarWait.setVisibility(8);
    }
    while (true)
    {
      if (this.mConversitionList.size() != 0)
        break label216;
      this.mListView.setVisibility(8);
      this.mWaitLayout.setVisibility(0);
      this.mTxtTips.setVisibility(0);
      this.mTxtTips.setText(2131428061);
      return;
      label175: localConversitionList.remove(j);
      j--;
      break;
      if (ChatModel.getInstance().getMapSize() == 0)
        continue;
      this.mWaitLayout.setVisibility(0);
      this.mProgressBarWait.setVisibility(0);
    }
    label216: this.mListView.setVisibility(0);
    this.mWaitLayout.setVisibility(8);
    this.mProgressBarWait.setVisibility(8);
  }

  private void updateData(int paramInt, boolean paramBoolean)
  {
    if (paramInt == 0)
      updateConversitonData(paramBoolean);
    do
    {
      return;
      if (paramInt != 1)
        continue;
      updateFriendsData(paramBoolean);
      return;
    }
    while (paramInt != 2);
    updateCirclesData(paramBoolean);
  }

  private void updateFriendsData(boolean paramBoolean)
  {
    int i;
    ArrayList localArrayList;
    if (TextUtils.isEmpty(this.searchCode))
    {
      i = 0;
      if (paramBoolean)
      {
        this.mFriendList.clear();
        if (this.needGetFriendsList)
          startGetFriendsTask();
        if (i == 0)
          break label180;
        localArrayList = FriendsModel.getInstance().searchAllFriendsByPinyin(this.searchCode);
        label50: if ((localArrayList != null) && (localArrayList.size() > 0))
          this.mFriendList.addAll(localArrayList);
      }
      if ((i != 0) || (this.mFriendList.size() != 0))
        break label249;
      this.mFriendList.add(null);
      this.mListView.setVisibility(8);
      this.mWaitLayout.setVisibility(0);
      this.mProgressBarWait.setVisibility(8);
      this.mTxtTips.setVisibility(0);
      if ((this.mGetFriendsTask == null) || (this.mGetCirclesTask.getStatus() != AsyncTask.Status.RUNNING))
        break label236;
      this.mTxtTips.setText(2131427599);
    }
    while (true)
    {
      if (this.mAdapter != null)
        this.mAdapter.notifyDataSetChanged();
      return;
      i = 1;
      break;
      label180: localArrayList = ChatModel.getInstance().getOnlineFriends();
      TextView localTextView = this.mTxtFriendsNum;
      Resources localResources = getResources();
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(localArrayList.size());
      localTextView.setText(localResources.getString(2131428459, arrayOfObject));
      break label50;
      label236: this.mTxtTips.setText(2131428075);
      continue;
      label249: this.mWaitLayout.setVisibility(8);
      this.mListView.setVisibility(0);
    }
  }

  private boolean updateFriendsUnread()
  {
    KXLog.d("TEST", this.mFriendsUnread + "(updateFriendsUnread)");
    int i = 0;
    Iterator localIterator = FriendsModel.getInstance().getOnlines().iterator();
    while (true)
    {
      if (!localIterator.hasNext())
      {
        int j = this.mFriendsUnread;
        int k = 0;
        if (j != i)
        {
          this.mFriendsUnread = i;
          k = 1;
        }
        KXLog.d("TEST", i + "(updateFriendsUnread)");
        return k;
      }
      FriendsModel.Friend localFriend = (FriendsModel.Friend)localIterator.next();
      i += ChatModel.getInstance().getUnreadNum(localFriend.getFuid(), false);
    }
  }

  public void beforeTabChanged(int paramInt)
  {
    clearData(paramInt);
  }

  protected boolean handleMessage(Message paramMessage)
  {
    int i = paramMessage.what;
    int j = 0;
    switch (i)
    {
    case 90002:
    case 90003:
    case 90008:
    case 90009:
    case 90010:
    default:
    case 90012:
    case 90011:
    case 90007:
    case 90006:
    case 90005:
    case 90001:
    case 90004:
    }
    while ((j == 0) && (!super.handleMessage(paramMessage)))
    {
      return false;
      handleCircleMemberChangeMessage((ArrayList)paramMessage.obj);
      j = 1;
      continue;
      handleCircleMemberChangeMessage((ArrayList)paramMessage.obj);
      j = 1;
      continue;
      KXLog.d("TEST", "FRIENDS_LIST_UPDATED");
      ChatModel.getInstance().ensureFriendsConversiton();
      if (updateConversitionUnread())
      {
        updateData(this.mTabIndex, true);
        if (this.mConversitionUnread > 0)
          this.mTabIndex = 0;
        initTabHost(getView());
      }
      j = 1;
      continue;
      KXLog.d("TEST", "CIRCLES_UPDATED");
      ChatModel.getInstance().ensureCirclesConversiton();
      if (updateConversitionUnread())
      {
        updateData(this.mTabIndex, true);
        if (this.mConversitionUnread > 0)
          this.mTabIndex = 0;
      }
      j = 1;
      continue;
      int k = this.mTabIndex;
      if (paramMessage.arg2 != 1);
      for (boolean bool = true; ; bool = false)
      {
        updateData(k, bool);
        updateConversitionUnread();
        initTabHost(getView());
        j = 1;
        break;
      }
      ArrayList localArrayList = (ArrayList)paramMessage.obj;
      ChatModel.getInstance().refreshOnlines(localArrayList);
      if (this.mTabIndex == 1)
        updateData(this.mTabIndex, true);
      j = 1;
      continue;
      KXLog.d("TEST", "CHAT_USER_TYPING_MASSAGE");
      j = 0;
    }
    return true;
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if (paramInt2 == -1)
      if (this.mPositionClicked >= 0)
        switch (this.mTabIndex)
        {
        default:
        case 0:
        case 1:
        case 2:
        }
    while (true)
    {
      if (this.mAdapter != null)
        this.mAdapter.notifyDataSetChanged();
      return;
      ((ChatModel.Conversition)this.mConversitionList.get(this.mPositionClicked)).mActiveTime = System.currentTimeMillis();
      continue;
      FriendsModel.Friend localFriend = (FriendsModel.Friend)this.mFriendList.get(this.mPositionClicked);
      ChatModel.getInstance().addConversition(localFriend);
      continue;
      CircleModel.CircleItem localCircleItem = (CircleModel.CircleItem)this.mCircleList.get(this.mPositionClicked);
      ChatModel.getInstance().addConversition(localCircleItem);
    }
  }

  public void onClick(View paramView)
  {
    if (paramView.getId() == 2131362914)
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
    switchServiceMode(true);
    KXUBLog.log("homepage_chat");
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903062, paramViewGroup, false);
  }

  public void onDestroy()
  {
    cancelTask(this.mGetCirclesTask);
    cancelTask(this.mGetFriendsTask);
    DialogUtil.dismissDialog(this.mProgressDlg);
    this.mFriendList.clear();
    switchServiceMode(false);
    super.onDestroy();
  }

  public void onDestroyView()
  {
    this.mCurTabIndex = this.tabHost.getCurrentTab();
    super.onDestroyView();
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    int i = -1;
    this.mPositionClicked = (int)paramLong;
    String str1;
    String str2;
    boolean bool;
    if (this.mTabIndex == 0)
    {
      ChatModel.Conversition localConversition = (ChatModel.Conversition)this.mConversitionList.get(this.mPositionClicked);
      str1 = localConversition.mID;
      str2 = localConversition.mName;
      bool = localConversition.isCircle;
    }
    while (true)
    {
      if (!TextUtils.isEmpty(str1))
      {
        Intent localIntent = new Intent(getActivity(), ChatDetailFragment.class);
        localIntent.putExtra("fuid", str1);
        localIntent.putExtra("fname", str2);
        localIntent.putExtra("isGroup", bool);
        if (i > 0)
          localIntent.putExtra("type", i);
        startFragmentForResultNew(localIntent, 0, 1, true);
      }
      while (true)
      {
        return;
        if (this.mTabIndex == 1)
        {
          FriendsModel.Friend localFriend = (FriendsModel.Friend)this.mFriendList.get(this.mPositionClicked);
          if (localFriend == null)
            continue;
          str1 = localFriend.getFuid();
          str2 = localFriend.getFname();
          bool = false;
          break;
        }
      }
      int j = this.mTabIndex;
      str1 = null;
      bool = false;
      str2 = null;
      if (j != 2)
        continue;
      CircleModel.CircleItem localCircleItem = (CircleModel.CircleItem)this.mCircleList.get(this.mPositionClicked);
      str1 = localCircleItem.gid;
      str2 = localCircleItem.gname;
      bool = true;
      i = localCircleItem.type;
    }
  }

  public void onResume()
  {
    super.onResume();
    if (this.mTabIndex == 0)
    {
      this.mConversitionList.sort();
      if (this.mAdapter != null)
        this.mAdapter.notifyDataSetChanged();
    }
  }

  public void onStart()
  {
    super.onStart();
  }

  public void onTabChanged(int paramInt)
  {
    this.mTabIndex = paramInt;
    updateData(paramInt, true);
    if (paramInt == 1)
    {
      this.mListView.setAdapter(null);
      this.mListView.addHeaderView(this.mHeaderView);
      this.mListView.setAdapter(this.mAdapter);
    }
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    initTitleBar(paramView);
    this.mListView = ((ListView)paramView.findViewById(2131361968));
    this.mListView.setOnItemClickListener(this);
    this.mWaitLayout = paramView.findViewById(2131361965);
    this.mTxtTips = ((TextView)paramView.findViewById(2131361967));
    this.mProgressBarWait = ((ProgressBar)paramView.findViewById(2131361966));
    initHeaderView();
    startGetFriendsTask();
    startGetCirclesTask();
    int i = ChatModel.getInstance().getUnreadNum("CHAT_MSG_TOTAL", false);
    if (!dataInited())
      if (i != 0)
        break label194;
    label194: for (this.mTabIndex = 1; ; this.mTabIndex = 0)
    {
      updateData(this.mTabIndex, true);
      updateConversitionUnread();
      if (this.mConversitionUnread > 0)
        onTabChanged(0);
      initTabHost(paramView);
      if (1 == this.mTabIndex)
        onTabChanged(this.mTabIndex);
      this.mListView.setAdapter(this.mAdapter);
      if (!ActivityUtil.needNotification(getActivity()))
        Toast.makeText(getActivity(), 2131428312, 1).show();
      return;
    }
  }

  private class ChatAdapter extends BaseAdapter
  {
    View blankView = null;

    private ChatAdapter()
    {
    }

    private View initBlankView()
    {
      return ChatFragment.this.getActivity().getLayoutInflater().inflate(2130903065, null);
    }

    public int getCount()
    {
      if (ChatFragment.this.mTabIndex == 0)
        return ChatFragment.this.mConversitionList.size();
      if (ChatFragment.this.mTabIndex == 1)
        return ChatFragment.this.mFriendList.size();
      if (ChatFragment.this.mTabIndex == 2)
        return ChatFragment.this.mCircleList.size();
      return 0;
    }

    public Object getItem(int paramInt)
    {
      if (ChatFragment.this.mTabIndex == 0)
        if ((paramInt >= 0) && (paramInt < ChatFragment.this.mConversitionList.size()));
      do
        while (true)
        {
          return null;
          return ChatFragment.this.mConversitionList.get(paramInt);
          if (ChatFragment.this.mTabIndex != 1)
            break;
          if ((paramInt >= 0) && (paramInt < ChatFragment.this.mFriendList.size()))
            return ChatFragment.this.mFriendList.get(paramInt);
        }
      while ((ChatFragment.this.mTabIndex != 2) || (paramInt < 0) || (paramInt >= ChatFragment.this.mCircleList.size()));
      return ChatFragment.this.mCircleList.get(paramInt);
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      Object localObject = getItem(paramInt);
      if (localObject == null)
      {
        if (this.blankView == null)
          this.blankView = initBlankView();
        int i = ChatFragment.this.mListView.getHeight();
        int j = ChatFragment.this.mHeaderView.getHeight();
        this.blankView.setMinimumHeight(-2 + (i - j));
        return this.blankView;
      }
      ChatFragment.ChatViewItemCache localChatViewItemCache;
      if ((paramView == null) || (paramView == this.blankView))
      {
        paramView = ChatFragment.this.getActivity().getLayoutInflater().inflate(2130903067, null);
        localChatViewItemCache = new ChatFragment.ChatViewItemCache(ChatFragment.this, paramView);
        paramView.setTag(localChatViewItemCache);
      }
      while (localChatViewItemCache == null)
      {
        return paramView;
        localChatViewItemCache = (ChatFragment.ChatViewItemCache)paramView.getTag();
      }
      if (localObject != null)
      {
        if (ChatFragment.this.mTabIndex != 0)
          break label166;
        localChatViewItemCache.showData((ChatModel.Conversition)localObject);
      }
      while (true)
      {
        return paramView;
        label166: if (ChatFragment.this.mTabIndex == 1)
        {
          localChatViewItemCache.showData((FriendsModel.Friend)localObject);
          continue;
        }
        if (ChatFragment.this.mTabIndex != 2)
          continue;
        localChatViewItemCache.showData((CircleModel.CircleItem)localObject);
      }
    }
  }

  private class ChatViewItemCache
  {
    public ImageView mImgAvatar;
    public ImageView mImgState;
    public TextView mTxtName;
    public TextView mTxtNewMsg;
    public TextView mTxtSection;

    public ChatViewItemCache(View arg2)
    {
      Object localObject;
      this.mTxtSection = ((TextView)localObject.findViewById(2131361997));
      this.mImgAvatar = ((ImageView)localObject.findViewById(2131362000));
      this.mImgState = ((ImageView)localObject.findViewById(2131362002));
      this.mTxtName = ((TextView)localObject.findViewById(2131362001));
      this.mTxtNewMsg = ((TextView)localObject.findViewById(2131362003));
    }

    private void setUnreadShow(int paramInt)
    {
      if (this.mTxtNewMsg != null)
      {
        if (paramInt > 0)
        {
          this.mTxtNewMsg.setVisibility(0);
          this.mTxtNewMsg.setText(paramInt);
        }
      }
      else
        return;
      this.mTxtNewMsg.setText("0");
      this.mTxtNewMsg.setVisibility(8);
    }

    public void showData(ChatModel.Conversition paramConversition)
    {
      if (paramConversition == null)
        return;
      if (this.mTxtSection != null)
        this.mTxtSection.setVisibility(8);
      if (this.mImgAvatar != null)
      {
        if (paramConversition.isCircle)
        {
          if ((paramConversition.mCircleType < 0) || (paramConversition.mCircleType >= CirclesAdapter.CIRCLE_ICONS.length))
            paramConversition.mCircleType = 0;
          this.mImgAvatar.setImageDrawable(null);
          this.mImgAvatar.setImageResource(CirclesAdapter.CIRCLE_ICONS[paramConversition.mCircleType]);
          this.mImgAvatar.setBackgroundDrawable(null);
          this.mImgAvatar.setScaleType(ImageView.ScaleType.CENTER);
        }
      }
      else
      {
        if (this.mTxtName != null)
          this.mTxtName.setText(paramConversition.mName);
        if (this.mImgState != null)
        {
          if (!paramConversition.isCircle)
            break label175;
          this.mImgState.setVisibility(8);
        }
      }
      while (true)
      {
        setUnreadShow(paramConversition.unreadNum().intValue());
        return;
        ChatFragment.this.displayRoundPicture(this.mImgAvatar, paramConversition.mLogo, ImageDownloader.RoundCornerType.hdpi_small, 2130838334);
        break;
        label175: String str = paramConversition.isOnline;
        if ("1".equals(str))
        {
          this.mImgState.setImageResource(2130838704);
          this.mImgState.setVisibility(0);
          continue;
        }
        if ("2".equals(str))
        {
          this.mImgState.setImageResource(2130838621);
          this.mImgState.setVisibility(0);
          continue;
        }
        this.mImgState.setVisibility(8);
      }
    }

    public void showData(CircleModel.CircleItem paramCircleItem)
    {
      if (paramCircleItem == null)
        return;
      if (this.mTxtSection != null)
        this.mTxtSection.setVisibility(8);
      if ((paramCircleItem.type < 0) || (paramCircleItem.type >= CirclesAdapter.CIRCLE_ICONS.length))
        paramCircleItem.type = 0;
      this.mImgAvatar.setImageDrawable(null);
      this.mImgAvatar.setImageResource(CirclesAdapter.CIRCLE_ICONS[paramCircleItem.type]);
      this.mImgAvatar.setBackgroundDrawable(null);
      this.mImgAvatar.setScaleType(ImageView.ScaleType.CENTER);
      if (this.mTxtName != null)
        this.mTxtName.setText(paramCircleItem.gname);
      if (this.mImgState != null)
        this.mImgState.setVisibility(8);
      setUnreadShow(0);
    }

    public void showData(FriendsModel.Friend paramFriend)
    {
      if (paramFriend == null)
        return;
      if (this.mTxtSection != null)
        this.mTxtSection.setVisibility(8);
      if (this.mImgAvatar != null)
        ChatFragment.this.displayRoundPicture(this.mImgAvatar, paramFriend.getFlogo(), ImageDownloader.RoundCornerType.hdpi_small, 2130838334);
      if (this.mTxtName != null)
        this.mTxtName.setText(paramFriend.getFname());
      String str;
      if (this.mImgState != null)
      {
        str = paramFriend.getOnline();
        if (!"1".equals(str))
          break label110;
        this.mImgState.setImageResource(2130838704);
        this.mImgState.setVisibility(0);
      }
      while (true)
      {
        setUnreadShow(0);
        return;
        label110: if ("2".equals(str))
        {
          this.mImgState.setImageResource(2130838621);
          this.mImgState.setVisibility(0);
          continue;
        }
        this.mImgState.setVisibility(8);
      }
    }
  }

  private class GetCirclesTask extends KXFragment.KXAsyncTask<Integer, Void, Integer>
  {
    private int number;
    private int start;

    private GetCirclesTask()
    {
      super();
    }

    protected Integer doInBackgroundA(Integer[] paramArrayOfInteger)
    {
      if ((paramArrayOfInteger == null) || (paramArrayOfInteger.length == 0) || (paramArrayOfInteger.length != 2))
        return null;
      this.start = paramArrayOfInteger[0].intValue();
      this.number = paramArrayOfInteger[1].intValue();
      try
      {
        Integer localInteger = Integer.valueOf(CirclesEngine.getInstance().doGetCircleList(ChatFragment.this.getActivity().getApplicationContext(), this.start, this.number));
        return localInteger;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
      }
      return null;
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      if ((paramInteger == null) || (paramInteger.intValue() != 1))
        Toast.makeText(ChatFragment.this.getActivity().getApplicationContext(), 2131427374, 0).show();
      do
      {
        return;
        ChatFragment.this.needGetCircleList = false;
      }
      while (ChatFragment.this.mTabIndex != 2);
      ChatFragment.this.updateCirclesData(true);
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private class GetFriendsDataTask extends KXFragment.KXAsyncTask<Integer, Void, Integer>
  {
    private boolean bManualRefreshView = false;

    private GetFriendsDataTask()
    {
      super();
    }

    protected Integer doInBackgroundA(Integer[] paramArrayOfInteger)
    {
      if ((paramArrayOfInteger == null) || (paramArrayOfInteger.length == 0))
        return Integer.valueOf(0);
      try
      {
        if (FriendsModel.getInstance().getFriends().size() == 0)
          if (FriendsEngine.getInstance().loadFriendsCache(ChatFragment.this.getActivity(), User.getInstance().getUID()))
          {
            if (!FriendsEngine.getInstance().getFriendsList(ChatFragment.this.getActivity().getApplicationContext(), paramArrayOfInteger[0].intValue()))
              return Integer.valueOf(0);
            this.bManualRefreshView = true;
          }
        while (true)
        {
          return Integer.valueOf(1);
          if (!FriendsEngine.getInstance().getFriendsList(ChatFragment.this.getActivity().getApplicationContext(), 1))
            return Integer.valueOf(0);
          this.bManualRefreshView = true;
          continue;
          if (!FriendsEngine.getInstance().getFriendsList(ChatFragment.this.getActivity().getApplicationContext(), paramArrayOfInteger[0].intValue()))
            return Integer.valueOf(0);
          this.bManualRefreshView = true;
        }
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
      }
      return null;
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      if (paramInteger == null);
      while (true)
      {
        return;
        try
        {
          if (paramInteger.intValue() != 1)
            continue;
          ChatFragment.this.needGetFriendsList = false;
          if (this.bManualRefreshView)
          {
            ChatModel.getInstance().ensureFriendsConversiton();
            if (ChatFragment.this.updateConversitionUnread())
            {
              ChatFragment.this.updateData(ChatFragment.this.mTabIndex, true);
              if (ChatFragment.this.mConversitionUnread > 0)
                ChatFragment.this.mTabIndex = 0;
              ChatFragment.this.initTabHost(ChatFragment.this.getView());
            }
          }
          if (ChatFragment.this.mTabIndex != 1)
            continue;
          ChatFragment.this.updateFriendsData(true);
          return;
        }
        catch (Exception localException)
        {
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
 * Qualified Name:     com.kaixin001.fragment.ChatFragment
 * JD-Core Version:    0.6.0
 */