package com.kaixin001.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.engine.MessageEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.item.MessageItem;
import com.kaixin001.model.FaceModel;
import com.kaixin001.model.MessageCenterModel;
import com.kaixin001.model.Setting;
import com.kaixin001.model.User;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.CrashRecoverUtil;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.StringUtil;
import com.kaixin001.view.KXTopTab;
import com.kaixin001.view.KXTopTabHost;
import com.kaixin001.view.KXTopTabHost.OnTabChangeListener;
import com.kaixin001.view.PullToRefreshView;
import com.kaixin001.view.PullToRefreshView.PullToRefreshListener;
import java.util.ArrayList;

public class MessageListFragment extends BaseFragment
  implements KXTopTabHost.OnTabChangeListener, AdapterView.OnItemClickListener, PullToRefreshView.PullToRefreshListener
{
  protected static final int MENU_HOME_ID = 412;
  protected static final int MENU_HOME_ME_ID = 413;
  protected static final int MENU_REFRESH_ID = 410;
  protected static final int MENU_TO_TOP_ID = 411;
  private static final String TAG = "MessageListActivity";
  private MessageAdapter mAdapter = new MessageAdapter();
  private int mClickPosition = -1;
  private TextView mClickTotalMsgNum;
  protected PullToRefreshView mDownView;
  private ProgressBar mFooterProBar;
  private TextView mFooterTV;
  private View mFooterView;
  private GetMessageTask mGetMessageTask = null;
  private int mInboxListSelectionPosition = 0;
  ListView mListView;
  MessageCenterModel mMessageCenterModel = MessageCenterModel.getInstance();
  private final ArrayList<MessageItem> mMessageList = new ArrayList();
  private int mMessageType = 101;
  private ProgressBar mRightTopProBar;
  private ImageView mRightTopbtnPro;
  private int mSentboxListSelectionPosition = 0;
  private PopupWindow mSettingOptionPopupWindow;
  private KXTopTabHost mTabHost;
  private ImageView mTitleRightButton;

  private void cancelTask()
  {
    if ((this.mGetMessageTask != null) && (this.mGetMessageTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.mGetMessageTask.cancel(true);
      this.mGetMessageTask = null;
    }
  }

  private void doGetListData()
  {
    showEmptyNotice(false);
    this.mMessageType = this.mMessageCenterModel.getActiveMessageType();
    ArrayList localArrayList1;
    int i;
    boolean bool;
    if (this.mMessageType == 101)
    {
      localArrayList1 = this.mMessageCenterModel.getMessageInboxList();
      this.mMessageCenterModel.getMsgInboxTotal();
      i = this.mMessageCenterModel.getNoticeCnt(1);
      if (this.mMessageType != 101)
        break label161;
      bool = Setting.getInstance().isNeedRefresh(0);
      Setting.getInstance().setNeedRefresh(0, false);
    }
    while (true)
    {
      if ((localArrayList1 == null) || (localArrayList1.size() == 0) || (bool))
        MessageEngine.getInstance().loadCachedMsgList(getActivity(), User.getInstance().getUID(), this.mMessageType);
      updateListData();
      if ((this.mMessageList.size() != 0) && (i <= 0))
        break label181;
      refreshData();
      showListLoadingProgressBar(true, 0);
      return;
      localArrayList1 = this.mMessageCenterModel.getMessageSentList();
      this.mMessageCenterModel.getMsgOutboxTotal();
      break;
      label161: bool = Setting.getInstance().isNeedRefresh(1);
      Setting.getInstance().setNeedRefresh(1, false);
    }
    label181: showList(true);
    ArrayList localArrayList2;
    if (this.mMessageType == 101)
      localArrayList2 = this.mMessageCenterModel.getMessageInboxList();
    for (int j = this.mMessageCenterModel.getMsgInboxTotal(); ; j = this.mMessageCenterModel.getMsgOutboxTotal())
    {
      MessageItem localMessageItem = (MessageItem)this.mMessageList.get(-1 + this.mMessageList.size());
      if ((localArrayList2 != null) && (localArrayList2.size() < j) && (localMessageItem.getCtime().longValue() != -1L))
        getMoreMessage(false);
      if (!bool)
        break;
      refreshData();
      return;
      localArrayList2 = this.mMessageCenterModel.getMessageSentList();
    }
  }

  private void getMoreMessage(boolean paramBoolean)
  {
    if ((this.mGetMessageTask != null) && (this.mGetMessageTask.getStatus() != AsyncTask.Status.FINISHED) && (!this.mGetMessageTask.isCancelled()))
      return;
    if (!super.checkNetworkAndHint(paramBoolean))
    {
      showLoadingFooter(false);
      return;
    }
    showListLoadingProgressBar(true, 1);
    String[] arrayOfString = new String[2];
    ArrayList localArrayList;
    if (this.mMessageType == 101)
    {
      arrayOfString[0] = "true";
      localArrayList = this.mMessageCenterModel.getMessageInboxList();
    }
    while (true)
    {
      arrayOfString[1] = "";
      if ((localArrayList != null) && (localArrayList.size() > 0));
      try
      {
        arrayOfString[1] = ((MessageItem)localArrayList.get(-1 + localArrayList.size())).getCtime().toString();
        if ((this.mMessageList.size() > 0) && (((MessageItem)this.mMessageList.get(-1 + this.mMessageList.size())).getCtime().longValue() == -1L))
          setDownloadingProgressBar(true);
        this.mGetMessageTask = new GetMessageTask(null);
        this.mGetMessageTask.execute(arrayOfString);
        return;
        arrayOfString[0] = "false";
        localArrayList = this.mMessageCenterModel.getMessageSentList();
      }
      catch (Exception localException)
      {
        while (true)
          localException.printStackTrace();
      }
    }
  }

  private void refreshData()
  {
    if (!super.checkNetworkAndHint(true))
      return;
    cancelTask();
    showEmptyNotice(false);
    showListLoadingProgressBar(true, 1);
    String[] arrayOfString = new String[2];
    if (this.mMessageType == 101);
    for (boolean bool = true; ; bool = false)
    {
      arrayOfString[0] = String.valueOf(bool);
      arrayOfString[1] = "";
      this.mGetMessageTask = new GetMessageTask(null);
      this.mGetMessageTask.execute(arrayOfString);
      return;
    }
  }

  private void setDownloadingProgressBar(boolean paramBoolean)
  {
    if (!paramBoolean)
    {
      this.mRightTopProBar.setVisibility(8);
      this.mRightTopbtnPro.setImageResource(2130838834);
      this.mRightTopbtnPro.setVisibility(0);
      return;
    }
    this.mRightTopProBar.setVisibility(0);
    this.mRightTopbtnPro.setImageResource(0);
  }

  private void showEmptyNotice(boolean paramBoolean)
  {
    TextView localTextView = (TextView)getView().findViewById(2131363036);
    if (this.mMessageType == 101)
      localTextView.setText(2131427578);
    while (paramBoolean)
    {
      localTextView.setVisibility(0);
      return;
      localTextView.setText(2131427579);
    }
    localTextView.setVisibility(8);
  }

  private void showList(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.mDownView.setVisibility(0);
      return;
    }
    this.mDownView.setVisibility(8);
  }

  private void showListLoadingProgressBar(boolean paramBoolean, int paramInt)
  {
    View localView = getView().findViewById(2131362490);
    if (paramBoolean)
    {
      if (paramInt == 0)
      {
        localView.setVisibility(0);
        return;
      }
      localView.setVisibility(8);
      this.mRightTopProBar.setVisibility(0);
      this.mRightTopbtnPro.setImageResource(0);
      return;
    }
    if (paramInt == 0)
    {
      localView.setVisibility(8);
      return;
    }
    this.mRightTopProBar.setVisibility(8);
    this.mRightTopbtnPro.setImageResource(2130838834);
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

  public void beforeTabChanged(int paramInt)
  {
    if (paramInt == 0)
    {
      this.mInboxListSelectionPosition = this.mListView.getFirstVisiblePosition();
      return;
    }
    this.mSentboxListSelectionPosition = this.mListView.getFirstVisiblePosition();
  }

  public void finish()
  {
    super.finish();
    AnimationUtil.finishActivity(getActivity(), 2);
  }

  protected void getAndGotoDetail(String paramString1, int paramInt1, int paramInt2, int paramInt3, String paramString2)
  {
    Intent localIntent = new Intent(getActivity(), MessageDetailFragment.class);
    localIntent.putExtra("id", paramString1);
    localIntent.putExtra("mtype", paramInt1);
    localIntent.putExtra("count", paramInt2);
    localIntent.putExtra("newcount", paramInt3);
    localIntent.putExtra("fuid", paramString2);
    if (this.mMessageType == 101)
      localIntent.putExtra("type", 201);
    while (true)
    {
      localIntent.putExtra("mode", 2);
      startActivityForResult(localIntent, 501);
      return;
      localIntent.putExtra("type", 202);
    }
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if ((this.mClickPosition >= 0) && (this.mMessageCenterModel.getMessageInboxList() != null) && (this.mMessageCenterModel.getMessageInboxList().size() > this.mClickPosition))
      ((MessageItem)this.mMessageCenterModel.getMessageInboxList().get(this.mClickPosition)).setUnread(0);
    if ((paramInt2 == -1) && (paramInt1 == 501))
      try
      {
        int i = Integer.parseInt(paramIntent.getStringExtra("comment_count"));
        MessageItem localMessageItem = (MessageItem)this.mMessageCenterModel.getMessageInboxList().get(this.mClickPosition);
        int j;
        if ((i != 0) && (this.mClickPosition >= 0))
        {
          if (this.mMessageType != 101)
            break label166;
          j = i + localMessageItem.getMsgnum();
          localMessageItem.setMsgnum(j);
        }
        while (true)
        {
          this.mAdapter.updateHolderView(j);
          localMessageItem.setUnread(0);
          this.mAdapter.notifyDataSetChanged();
          return;
          label166: j = i + localMessageItem.getMsgnum();
          localMessageItem.setMsgnum(j);
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
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

  public void onCreateOptionsMenu(Menu paramMenu, MenuInflater paramMenuInflater)
  {
    super.onCreateOptionsMenu(paramMenu, paramMenuInflater);
    paramMenu.add(0, 410, 0, 2131427690).setIcon(2130838607);
    paramMenu.add(0, 411, 0, 2131427698).setIcon(2130838609);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903224, paramViewGroup, false);
  }

  public void onDestroy()
  {
    cancelTask();
    MessageEngine.getInstance().cancel();
    this.mMessageList.clear();
    this.mMessageCenterModel.setActiveMessageType(101);
    this.mMessageCenterModel.setMessageInboxList(null);
    this.mMessageCenterModel.setMessageOutboxList(null);
    super.onDestroy();
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    if (paramInt != this.mMessageList.size());
    try
    {
      this.mClickPosition = paramInt;
      this.mClickTotalMsgNum = ((TextView)paramView.findViewById(2131363073));
      MessageItem localMessageItem = (MessageItem)this.mMessageList.get(paramInt);
      getAndGotoDetail(localMessageItem.getMid(), localMessageItem.getMtype(), localMessageItem.getMsgnum(), localMessageItem.getUnread(), localMessageItem.getFuid());
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default:
      return super.onOptionsItemSelected(paramMenuItem);
    case 410:
      showEmptyNotice(false);
      showListLoadingProgressBar(true, 1);
      refreshData();
      return true;
    case 411:
      if (this.mListView.getVisibility() == 0)
        this.mListView.setSelection(0);
      return true;
    case 412:
      IntentUtil.returnToDesktop(getActivity());
      return true;
    case 413:
    }
    IntentUtil.showMyHomeFragment(this);
    return true;
  }

  public void onStart()
  {
    super.onStart();
    this.mAdapter.notifyDataSetChanged();
  }

  public void onTabChanged(int paramInt)
  {
    cancelTask();
    TextView localTextView;
    if (paramInt == 0)
    {
      this.mMessageCenterModel.setActiveMessageType(101);
      this.mMessageType = this.mMessageCenterModel.getActiveMessageType();
      showListLoadingProgressBar(false, 0);
      setDownloadingProgressBar(false);
      localTextView = (TextView)getView().findViewById(2131362920);
      if (this.mMessageType != 101)
        break label145;
      localTextView.setText(getResources().getText(2131427551));
    }
    while (true)
    {
      doGetListData();
      if (this.mMessageType != 101)
        break label162;
      if ((this.mMessageCenterModel.getMessageInboxList() != null) && (this.mMessageCenterModel.getMessageInboxList().size() <= this.mInboxListSelectionPosition))
        this.mInboxListSelectionPosition = 0;
      this.mListView.setSelection(this.mInboxListSelectionPosition);
      return;
      this.mMessageCenterModel.setActiveMessageType(102);
      break;
      label145: localTextView.setText(getResources().getText(2131427552));
    }
    label162: if ((this.mMessageCenterModel.getMessageSentList() != null) && (this.mMessageCenterModel.getMessageSentList().size() <= this.mSentboxListSelectionPosition))
      this.mSentboxListSelectionPosition = 0;
    this.mListView.setSelection(this.mSentboxListSelectionPosition);
  }

  public void onUpdate()
  {
    refreshData();
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    this.mMessageType = this.mMessageCenterModel.getActiveMessageType();
    setTitleBar(paramView);
    setTab(paramView);
    this.mFooterView = getActivity().getLayoutInflater().inflate(2130903259, null);
    this.mFooterView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        ArrayList localArrayList;
        int i;
        if (MessageListFragment.this.mMessageType == 101)
        {
          localArrayList = MessageListFragment.this.mMessageCenterModel.getMessageInboxList();
          i = MessageListFragment.this.mMessageCenterModel.getMsgInboxTotal();
          if ((localArrayList != null) && (localArrayList.size() >= MessageListFragment.this.mMessageList.size()))
            break label113;
          MessageListFragment.this.showLoadingFooter(true);
        }
        for (boolean bool = true; ; bool = false)
        {
          if ((localArrayList == null) || (localArrayList.size() >= i))
            break label126;
          MessageListFragment.this.getMoreMessage(bool);
          return;
          localArrayList = MessageListFragment.this.mMessageCenterModel.getMessageSentList();
          i = MessageListFragment.this.mMessageCenterModel.getMsgOutboxTotal();
          break;
          label113: MessageListFragment.this.updateListData();
        }
        label126: MessageListFragment.this.updateListData();
      }
    });
    this.mFooterTV = ((TextView)this.mFooterView.findViewById(2131362073));
    this.mFooterTV.setText(2131427748);
    this.mFooterProBar = ((ProgressBar)this.mFooterView.findViewById(2131362072));
    this.mFooterProBar.setVisibility(4);
    this.mListView = ((ListView)paramView.findViewById(2131363062));
    this.mListView.setAdapter(this.mAdapter);
    this.mListView.setOnItemClickListener(this);
    this.mDownView = ((PullToRefreshView)paramView.findViewById(2131362488));
    this.mDownView.setPullToRefreshListener(this);
    doGetListData();
    this.mAdapter.notifyDataSetChanged();
    this.mListView.requestFocus();
  }

  protected void setTab(View paramView)
  {
    this.mTabHost = ((KXTopTabHost)paramView.findViewById(2131363061));
    KXTopTab localKXTopTab1 = new KXTopTab(getActivity());
    localKXTopTab1.setText(2131427551);
    this.mTabHost.addTab(localKXTopTab1);
    KXTopTab localKXTopTab2 = new KXTopTab(getActivity());
    localKXTopTab2.setText(2131427552);
    this.mTabHost.addTab(localKXTopTab2);
    this.mMessageType = this.mMessageCenterModel.getActiveMessageType();
    if (this.mMessageType == 101)
      this.mTabHost.setCurrentTab(0);
    while (true)
    {
      this.mTabHost.setOnTabChangeListener(this);
      return;
      this.mTabHost.setCurrentTab(1);
    }
  }

  protected void setTitleBar(View paramView)
  {
    ImageView localImageView = (ImageView)paramView.findViewById(2131362914);
    localImageView.setVisibility(0);
    localImageView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        MessageListFragment.this.finish();
      }
    });
    this.mTitleRightButton = ((ImageView)paramView.findViewById(2131362924));
    this.mTitleRightButton.setVisibility(0);
    this.mTitleRightButton.setImageResource(2130839066);
    this.mTitleRightButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        Intent localIntent = new Intent(MessageListFragment.this.getActivity(), PostMessageFragment.class);
        localIntent.putExtra("PreviousActivityName", "MessageListActivity");
        MessageListFragment.this.startFragment(localIntent, true, 3);
      }
    });
    this.mRightTopbtnPro = ((ImageView)paramView.findViewById(2131362928));
    this.mRightTopbtnPro.setImageResource(2130838834);
    this.mRightTopbtnPro.setVisibility(0);
    this.mRightTopbtnPro.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        MessageListFragment.this.refreshData();
      }
    });
    this.mRightTopProBar = ((ProgressBar)paramView.findViewById(2131362929));
    ((ImageView)paramView.findViewById(2131362919)).setVisibility(8);
    TextView localTextView = (TextView)paramView.findViewById(2131362920);
    localTextView.setVisibility(0);
    if (this.mMessageType == 101)
    {
      localTextView.setText(getResources().getText(2131427551));
      return;
    }
    localTextView.setText(getResources().getText(2131427552));
  }

  protected void updateListData()
  {
    this.mMessageType = this.mMessageCenterModel.getActiveMessageType();
    ArrayList localArrayList;
    if (this.mMessageType == 101)
      localArrayList = this.mMessageCenterModel.getMessageInboxList();
    for (int i = this.mMessageCenterModel.getMsgInboxTotal(); ; i = this.mMessageCenterModel.getMsgOutboxTotal())
    {
      this.mMessageList.clear();
      if (localArrayList != null)
        this.mMessageList.addAll(localArrayList);
      if ((localArrayList != null) && (localArrayList.size() < i))
      {
        MessageItem localMessageItem = new MessageItem();
        localMessageItem.setCtime(Long.valueOf(-1L));
        this.mMessageList.add(localMessageItem);
        if (this.mAdapter != null)
          this.mAdapter.notifyDataSetChanged();
      }
      if (this.mAdapter != null)
        this.mAdapter.notifyDataSetChanged();
      return;
      localArrayList = this.mMessageCenterModel.getMessageSentList();
    }
  }

  private class FNameOnClickListener
    implements View.OnClickListener
  {
    private String fname;
    private String fuid;

    public FNameOnClickListener(String paramString1, String arg3)
    {
      this.fuid = paramString1;
      Object localObject;
      this.fname = localObject;
    }

    public void onClick(View paramView)
    {
      if ((this.fuid != null) && (this.fname != null))
        IntentUtil.showHomeFragment(MessageListFragment.this, this.fuid, this.fname);
    }
  }

  private class GetMessageTask extends KXFragment.KXAsyncTask<String, Void, Integer>
  {
    private String mBefore = null;
    private Boolean mInbox;

    private GetMessageTask()
    {
      super();
    }

    protected Integer doInBackgroundA(String[] paramArrayOfString)
    {
      if (paramArrayOfString.length < 2)
        return Integer.valueOf(0);
      this.mInbox = Boolean.valueOf(Boolean.parseBoolean(paramArrayOfString[0]));
      this.mBefore = paramArrayOfString[1];
      try
      {
        Integer localInteger = Integer.valueOf(MessageEngine.getInstance().doGetMessage(MessageListFragment.this.getActivity().getApplicationContext(), this.mInbox.booleanValue(), false, paramArrayOfString[1]));
        return localInteger;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
      }
      return null;
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      if ((MessageListFragment.this.getActivity() == null) || (MessageListFragment.this.getView() == null))
        return;
      if (paramInteger == null)
      {
        MessageListFragment.this.finish();
        return;
      }
      while (true)
      {
        try
        {
          MessageListFragment.this.showListLoadingProgressBar(false, 0);
          MessageListFragment.this.showListLoadingProgressBar(false, 1);
          if (paramInteger.intValue() != 1)
            break label342;
          if (MessageListFragment.this.mMessageType == 101)
          {
            localArrayList = MessageListFragment.this.mMessageCenterModel.getMessageInboxList();
            i = MessageListFragment.this.mMessageCenterModel.getMsgInboxTotal();
            if ((!TextUtils.isEmpty(this.mBefore)) && (MessageListFragment.this.mMessageList.size() != 0) && (MessageListFragment.this.mFooterProBar.getVisibility() != 0))
              continue;
            MessageListFragment.this.updateListData();
            if (!TextUtils.isEmpty(this.mBefore))
              continue;
            MessageListFragment.this.mListView.setSelection(0);
            if ((localArrayList == null) || (localArrayList.size() >= i))
              continue;
            MessageListFragment.this.mGetMessageTask = null;
            MessageListFragment.this.getMoreMessage(false);
            MessageListFragment.this.showLoadingFooter(false);
            if (MessageListFragment.this.mMessageList != null)
              break label291;
            MessageListFragment.this.showList(false);
            MessageListFragment.this.showEmptyNotice(false);
            if ((MessageListFragment.this.mDownView == null) || (!MessageListFragment.this.mDownView.isFrefrshing()))
              break;
            MessageListFragment.this.mDownView.onRefreshComplete();
            return;
          }
        }
        catch (Exception localException)
        {
          KXLog.e("MessageListActivity", "onPostExecute", localException);
          return;
        }
        ArrayList localArrayList = MessageListFragment.this.mMessageCenterModel.getMessageSentList();
        int i = MessageListFragment.this.mMessageCenterModel.getMsgOutboxTotal();
        continue;
        label291: if (MessageListFragment.this.mMessageList.size() == 0)
        {
          MessageListFragment.this.showEmptyNotice(true);
          MessageListFragment.this.showList(false);
          continue;
        }
        MessageListFragment.this.showEmptyNotice(false);
        MessageListFragment.this.showList(true);
        continue;
        label342: if (!TextUtils.isEmpty(this.mBefore))
          continue;
        Toast.makeText(MessageListFragment.this.getActivity().getApplicationContext(), 2131427374, 0).show();
      }
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private class MessageAdapter extends BaseAdapter
  {
    public MessageAdapter()
    {
    }

    public int getCount()
    {
      if (MessageListFragment.this.mMessageList != null)
        return MessageListFragment.this.mMessageList.size();
      return 0;
    }

    public Object getItem(int paramInt)
    {
      return Integer.valueOf(paramInt);
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      if (MessageListFragment.this.mMessageList != null)
      {
        MessageListFragment.MessageListViewHolder localMessageListViewHolder;
        while (true)
        {
          try
          {
            if (paramInt >= MessageListFragment.this.mMessageList.size())
              return paramView;
            MessageItem localMessageItem = (MessageItem)MessageListFragment.this.mMessageList.get(paramInt);
            long l = localMessageItem.getCtime().longValue();
            if (l != -1L)
              continue;
            MessageListFragment.this.mFooterTV.setVisibility(0);
            MessageListFragment.this.mFooterView.setVisibility(0);
            return MessageListFragment.this.mFooterView;
            if (paramView == null)
              continue;
            View localView = MessageListFragment.this.mFooterView;
            if (paramView != localView)
              continue;
            paramView = MessageListFragment.this.getActivity().getLayoutInflater().inflate(2130903225, paramViewGroup, false);
            localMessageListViewHolder = new MessageListFragment.MessageListViewHolder();
            localMessageListViewHolder.avatarView = ((ImageView)paramView.findViewById(2131363065));
            localMessageListViewHolder.outboxImage = ((ImageView)paramView.findViewById(2131363066));
            localMessageListViewHolder.fnameView = ((TextView)paramView.findViewById(2131363069));
            localMessageListViewHolder.mtypeView = ((TextView)paramView.findViewById(2131363070));
            localMessageListViewHolder.timeView = ((TextView)paramView.findViewById(2131363068));
            localMessageListViewHolder.contView = ((TextView)paramView.findViewById(2131363071));
            localMessageListViewHolder.msgnumDesView = ((TextView)paramView.findViewById(2131363073));
            localMessageListViewHolder.unreadDesView = ((TextView)paramView.findViewById(2131363074));
            paramView.setTag(localMessageListViewHolder);
            String str1 = localMessageItem.getFlogo();
            String str2 = localMessageItem.getFname();
            String str3 = localMessageItem.getFuid();
            int i = localMessageItem.getMtype();
            String str4 = MessageCenterModel.formatTimestamp(1000L * l);
            String str5 = localMessageItem.getAbscont();
            int j = localMessageItem.getMsgnum();
            int k = localMessageItem.getUnread();
            MessageListFragment.this.displayPicture(localMessageListViewHolder.avatarView, str1, 2130838676);
            ImageView localImageView = localMessageListViewHolder.avatarView;
            MessageListFragment.FNameOnClickListener localFNameOnClickListener1 = new MessageListFragment.FNameOnClickListener(MessageListFragment.this, str3, str2);
            localImageView.setOnClickListener(localFNameOnClickListener1);
            if (MessageListFragment.this.mMessageType != 101)
              continue;
            if (localMessageListViewHolder.outboxImage == null)
              continue;
            localMessageListViewHolder.outboxImage.setVisibility(8);
            localMessageListViewHolder.fnameView.setText(str2);
            TextView localTextView = localMessageListViewHolder.fnameView;
            MessageListFragment.FNameOnClickListener localFNameOnClickListener2 = new MessageListFragment.FNameOnClickListener(MessageListFragment.this, str3, str2);
            localTextView.setOnClickListener(localFNameOnClickListener2);
            if (i == 2)
            {
              localMessageListViewHolder.mtypeView.setVisibility(0);
              localMessageListViewHolder.timeView.setText(str4);
              SpannableString localSpannableString = FaceModel.getInstance().processTextForFace(str5);
              localMessageListViewHolder.contView.setText(localSpannableString);
              if (j <= 1)
                break;
              String str6 = StringUtil.replaceTokenWith(MessageListFragment.this.getResources().getString(2131427548), "*", String.valueOf(j));
              String str7 = StringUtil.replaceTokenWith(MessageListFragment.this.getResources().getString(2131427549), "*", String.valueOf(k));
              if (k <= 0)
                break label675;
              str6 = str6 + ",";
              localMessageListViewHolder.unreadDesView.setTextColor(MessageListFragment.this.getResources().getColor(2130839389));
              localMessageListViewHolder.unreadDesView.setVisibility(0);
              localMessageListViewHolder.msgnumDesView.setText(str6);
              localMessageListViewHolder.unreadDesView.setText(str7);
              localMessageListViewHolder.msgnumDesView.setVisibility(0);
              break label711;
              localMessageListViewHolder = (MessageListFragment.MessageListViewHolder)paramView.getTag();
              continue;
              if (localMessageListViewHolder.outboxImage == null)
                continue;
              localMessageListViewHolder.outboxImage.setVisibility(0);
              continue;
            }
          }
          catch (Exception localException)
          {
            localException.printStackTrace();
          }
          localMessageListViewHolder.mtypeView.setVisibility(8);
          continue;
          label675: localMessageListViewHolder.unreadDesView.setVisibility(8);
        }
        localMessageListViewHolder.msgnumDesView.setVisibility(8);
        localMessageListViewHolder.unreadDesView.setVisibility(8);
      }
      label711: return paramView;
    }

    public void updateHolderView(int paramInt)
    {
      if (paramInt != -1)
      {
        String str = StringUtil.replaceTokenWith(MessageListFragment.this.getResources().getString(2131427548), "*", paramInt);
        MessageListFragment.this.mClickTotalMsgNum.setText(str);
      }
    }
  }

  static class MessageListViewHolder
  {
    ImageView avatarView;
    TextView contView;
    TextView fnameView;
    TextView msgnumDesView;
    TextView mtypeView;
    ImageView outboxImage;
    TextView timeView;
    TextView unreadDesView;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.MessageListFragment
 * JD-Core Version:    0.6.0
 */