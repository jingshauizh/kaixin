package com.kaixin001.fragment;

import android.app.NotificationManager;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
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
import com.kaixin001.engine.MessageCenterEngine;
import com.kaixin001.model.ChatModel;
import com.kaixin001.model.CircleMessageModel;
import com.kaixin001.model.KaixinConst;
import com.kaixin001.model.MessageCenterModel;
import com.kaixin001.model.User;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.CrashRecoverUtil;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXUBLog;
import com.kaixin001.util.UserHabitUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class MessageCenterFragment extends BaseFragment
  implements AdapterView.OnItemClickListener
{
  protected static final int CHAT_MESSAGE_LIST_CMD_ID = 8;
  protected static final int CIRCLE_MESSAGE_LIST_CMD_ID = 7;
  protected static final int COMMENT_LIST_CMD_ID = 3;
  protected static final int MENU_HOME_ID = 501;
  protected static final int MENU_HOME_ME_ID = 502;
  protected static final int MENU_WRITE_MESSAGE_ID = 503;
  protected static final int RECEIVED_USER_COMMNET_LIST_CMD_ID = 5;
  protected static final int REPLY_COMMENT_LIST_CMD_ID = 4;
  protected static final int SENT_USER_COMMNET_LIST_CMD_ID = 6;
  protected static final int SHORT_MESSAGE_LIST_CMD_ID = 1;
  protected static final int SYSTEM_MESSAGE_LIST_CMD_ID = 2;
  GetNoticeCountTask getDataTask = null;
  private ListView listView;
  MessageCenterAdapter mAdapter = null;
  protected final ArrayList<HashMap<String, Object>> mLstItems = new ArrayList();
  private PopupWindow mSettingOptionPopupWindow;
  int mTotalNotice = 0;
  ImageView middleButton = null;
  ProgressBar middleProBar = null;

  private void cancelTask()
  {
    if ((this.getDataTask != null) && (!this.getDataTask.isCancelled()) && (this.getDataTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.getDataTask.cancel(true);
      this.getDataTask = null;
    }
  }

  private void refresh()
  {
    if (!super.checkNetworkAndHint(true))
      return;
    cancelTask();
    startTask();
  }

  private void refreshDoneHeaderView()
  {
    this.middleProBar.setVisibility(8);
    this.middleButton.setImageResource(2130839050);
  }

  private void refreshHeaderView()
  {
    this.middleProBar.setVisibility(0);
    this.middleButton.setImageResource(0);
  }

  private void startTask()
  {
    if ((this.getDataTask != null) && (!this.getDataTask.isCancelled()) && (this.getDataTask.getStatus() != AsyncTask.Status.FINISHED))
      return;
    this.getDataTask = new GetNoticeCountTask(null);
    this.getDataTask.execute(new String[0]);
  }

  protected boolean handleMessage(Message paramMessage)
  {
    if (paramMessage == null)
      return true;
    if (paramMessage.what == 5999)
    {
      updateListItems();
      return true;
    }
    return super.handleMessage(paramMessage);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Bundle localBundle = getArguments();
    String str = null;
    if (localBundle != null)
      str = localBundle.getString("action");
    if ((str != null) && (!str.equals("com.kaixin001.VIEW_MESSAGECENTER_DETAIL")) && (CrashRecoverUtil.isCrashBefore()))
    {
      CrashRecoverUtil.crashRecover(getActivity());
      IntentUtil.returnToDesktop(getActivity());
      return;
    }
    MessageCenterEngine.getInstance().enableNewMessageNotification(false);
    KXUBLog.log("homepage_message");
  }

  public void onCreateOptionsMenu(Menu paramMenu, MenuInflater paramMenuInflater)
  {
    super.onCreateOptionsMenu(paramMenu, paramMenuInflater);
    paramMenu.add(0, 503, 0, 2131427691).setIcon(2130838610);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903218, paramViewGroup, false);
  }

  public void onDestroy()
  {
    Iterator localIterator = this.mLstItems.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
      {
        cancelTask(this.getDataTask);
        this.mLstItems.clear();
        MessageCenterEngine.getInstance().enableNewMessageNotification(true);
        super.onDestroy();
        return;
      }
      ((HashMap)localIterator.next()).clear();
    }
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    switch (((Integer)((HashMap)paramAdapterView.getItemAtPosition(paramInt)).get("CommandId")).intValue())
    {
    default:
      return;
    case 1:
      AnimationUtil.startFragment(this, new Intent(getActivity(), MessageListFragment.class), 1);
      UserHabitUtils.getInstance(getActivity().getApplicationContext()).addUserHabit("Message_Short");
      return;
    case 2:
      AnimationUtil.startFragment(this, new Intent(getActivity(), SystemMessageListFragment.class), 1);
      UserHabitUtils.getInstance(getActivity().getApplicationContext()).addUserHabit("Message_System");
      return;
    case 3:
      MessageCenterModel.getInstance().setActiveCommentType(3);
      AnimationUtil.startFragment(this, new Intent(getActivity(), CommentListFragment.class), 1);
      UserHabitUtils.getInstance(getActivity().getApplicationContext()).addUserHabit("Message_Comment");
      return;
    case 4:
      MessageCenterModel.getInstance().setActiveCommentType(6);
      AnimationUtil.startFragment(this, new Intent(getActivity(), CommentListFragment.class), 1);
      return;
    case 5:
      MessageCenterModel.getInstance().setActiveUserCommentType(2);
      AnimationUtil.startFragment(this, new Intent(getActivity(), UserCommentListFragment.class), 1);
      UserHabitUtils.getInstance(getActivity().getApplicationContext()).addUserHabit("Message_Note");
      return;
    case 6:
      MessageCenterModel.getInstance().setActiveUserCommentType(5);
      AnimationUtil.startFragment(this, new Intent(getActivity(), UserCommentListFragment.class), 1);
      return;
    case 7:
      CircleMessageModel.getInstance().setActiveCircleReplyType(10);
      AnimationUtil.startFragment(this, new Intent(getActivity(), CircleMessageFragment.class), 1);
      UserHabitUtils.getInstance(getActivity().getApplicationContext()).addUserHabit("Message_Circle");
      return;
    case 8:
    }
    AnimationUtil.startFragment(this, new Intent(getActivity(), ChatFragment.class), 1);
    UserHabitUtils.getInstance(getActivity().getApplicationContext()).addUserHabit("Message_Chat");
  }

  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default:
      return super.onOptionsItemSelected(paramMenuItem);
    case 501:
      IntentUtil.returnToDesktop(getActivity());
      return true;
    case 502:
      IntentUtil.showMyHomeFragment(this);
      return true;
    case 503:
    }
    Intent localIntent = new Intent(getActivity(), PostMessageFragment.class);
    localIntent.putExtra("PreviousActivityName", "MessageCenter");
    AnimationUtil.startFragment(this, localIntent, 3);
    return true;
  }

  public void onResume()
  {
    super.onResume();
    ((NotificationManager)getActivity().getSystemService("notification")).cancel(KaixinConst.ID_NEW_MESSAGE_NOTIFICATION);
    if (MessageCenterModel.getInstance().getTotalNoticeCnt() != this.mTotalNotice)
      updateListItems();
  }

  public void onStart()
  {
    super.onStart();
    ((NotificationManager)getActivity().getSystemService("notification")).cancel(KaixinConst.ID_NEW_MESSAGE_NOTIFICATION);
    updateListItems();
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    User.getInstance().loadDataIfEmpty(getActivity().getApplicationContext());
    ImageView localImageView1 = (ImageView)paramView.findViewById(2131362914);
    localImageView1.setVisibility(0);
    localImageView1.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        if (MessageCenterFragment.this.isMenuListVisibleInMain())
        {
          MessageCenterFragment.this.showSubActivityInMain();
          return;
        }
        MessageCenterFragment.this.showMenuListInMain();
      }
    });
    this.middleProBar = ((ProgressBar)paramView.findViewById(2131362929));
    this.middleButton = ((ImageView)paramView.findViewById(2131362928));
    this.middleButton.setImageResource(2130839050);
    this.middleButton.setVisibility(0);
    this.middleButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        MessageCenterFragment.this.refresh();
      }
    });
    ImageView localImageView2 = (ImageView)paramView.findViewById(2131362924);
    localImageView2.setVisibility(0);
    localImageView2.setImageResource(2130839061);
    localImageView2.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        Intent localIntent = new Intent(MessageCenterFragment.this.getActivity(), PostMessageFragment.class);
        localIntent.putExtra("PreviousActivityName", "MessageCenter");
        AnimationUtil.startFragment(MessageCenterFragment.this, localIntent, 3);
      }
    });
    ((ImageView)paramView.findViewById(2131362919)).setVisibility(8);
    TextView localTextView = (TextView)paramView.findViewById(2131362920);
    localTextView.setVisibility(0);
    localTextView.setText(getResources().getText(2131427544));
    this.listView = ((ListView)paramView.findViewById(2131363030));
    this.mAdapter = new MessageCenterAdapter(getActivity().getLayoutInflater());
    this.listView.setAdapter(this.mAdapter);
    this.listView.setOnItemClickListener(this);
    this.listView.requestFocus();
    if (!dataInited())
      refresh();
  }

  protected void updateListItems()
  {
    ArrayList localArrayList = new ArrayList();
    MessageCenterModel localMessageCenterModel = MessageCenterModel.getInstance();
    String str1 = getResources().getString(2131427537);
    String str2 = getResources().getString(2131427543);
    int i = localMessageCenterModel.getNoticeCnt(1);
    String str3 = String.valueOf(i) + str2;
    HashMap localHashMap1 = new HashMap();
    localHashMap1.put("MsgCnt", Integer.valueOf(i));
    localHashMap1.put("ItemLabel", str1);
    localHashMap1.put("ItemContent", str3);
    localHashMap1.put("CommandId", Integer.valueOf(1));
    localArrayList.add(localHashMap1);
    String str4 = getResources().getString(2131427538);
    int j = localMessageCenterModel.getNoticeCnt(4);
    String str5 = String.valueOf(j) + str2;
    HashMap localHashMap2 = new HashMap();
    localHashMap2.put("MsgCnt", Integer.valueOf(j));
    localHashMap2.put("ItemLabel", str4);
    localHashMap2.put("ItemContent", str5);
    localHashMap2.put("CommandId", Integer.valueOf(2));
    localArrayList.add(localHashMap2);
    String str6 = getResources().getString(2131427539);
    int k = localMessageCenterModel.getNoticeCnt(3) + localMessageCenterModel.getNoticeCnt(6);
    String str7 = String.valueOf(k) + str2;
    HashMap localHashMap3 = new HashMap();
    localHashMap3.put("MsgCnt", Integer.valueOf(k));
    localHashMap3.put("ItemLabel", str6);
    localHashMap3.put("ItemContent", str7);
    localHashMap3.put("CommandId", Integer.valueOf(3));
    localArrayList.add(localHashMap3);
    String str8 = getResources().getString(2131427541);
    int m = localMessageCenterModel.getNoticeCnt(2) + localMessageCenterModel.getNoticeCnt(5);
    String str9 = String.valueOf(m) + str2;
    HashMap localHashMap4 = new HashMap();
    localHashMap4.put("MsgCnt", Integer.valueOf(m));
    localHashMap4.put("ItemLabel", str8);
    localHashMap4.put("ItemContent", str9);
    localHashMap4.put("CommandId", Integer.valueOf(5));
    localArrayList.add(localHashMap4);
    String str10 = getResources().getString(2131428062);
    int n = localMessageCenterModel.getNoticeCnt(12);
    String str11 = String.valueOf(n) + str2;
    HashMap localHashMap5 = new HashMap();
    localHashMap5.put("MsgCnt", Integer.valueOf(n));
    localHashMap5.put("ItemLabel", str10);
    localHashMap5.put("ItemContent", str11);
    localHashMap5.put("CommandId", Integer.valueOf(7));
    localArrayList.add(localHashMap5);
    String str12 = getResources().getString(2131428050);
    int i1 = ChatModel.getInstance().getUnreadNum("CHAT_MSG_TOTAL", false);
    String str13 = String.valueOf(i1) + str2;
    HashMap localHashMap6 = new HashMap();
    localHashMap6.put("MsgCnt", Integer.valueOf(i1));
    localHashMap6.put("ItemLabel", str12);
    localHashMap6.put("ItemContent", str13);
    localHashMap6.put("CommandId", Integer.valueOf(8));
    localArrayList.add(localHashMap6);
    this.mLstItems.clear();
    this.mLstItems.addAll(localArrayList);
    if (this.mAdapter != null)
      this.mAdapter.notifyDataSetChanged();
    this.mTotalNotice = MessageCenterModel.getInstance().getTotalNoticeCnt();
  }

  private class GetNoticeCountTask extends KXFragment.KXAsyncTask<String, Void, Integer>
  {
    private GetNoticeCountTask()
    {
      super();
    }

    protected Integer doInBackgroundA(String[] paramArrayOfString)
    {
      MessageCenterEngine.getInstance().checkNewMessage(MessageCenterFragment.this.getActivity(), User.getInstance().getUID());
      return Integer.valueOf(1);
    }

    protected void onCancelledA()
    {
      super.onCancelledA();
      MessageCenterFragment.this.refreshDoneHeaderView();
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      if ((MessageCenterFragment.this.getActivity() == null) || (MessageCenterFragment.this.getView() == null));
      do
      {
        return;
        MessageCenterFragment.this.refreshDoneHeaderView();
      }
      while (MessageCenterModel.getInstance().getTotalNoticeCnt() == MessageCenterFragment.this.mTotalNotice);
      MessageCenterFragment.this.updateListItems();
    }

    protected void onPreExecuteA()
    {
      MessageCenterFragment.this.refreshHeaderView();
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  protected class MessageCenterAdapter extends BaseAdapter
  {
    private LayoutInflater mInflater;

    public MessageCenterAdapter(LayoutInflater arg2)
    {
      Object localObject;
      this.mInflater = localObject;
    }

    public int getCount()
    {
      if (MessageCenterFragment.this.mLstItems != null)
        return MessageCenterFragment.this.mLstItems.size();
      return 0;
    }

    public Object getItem(int paramInt)
    {
      if (MessageCenterFragment.this.mLstItems != null)
        return MessageCenterFragment.this.mLstItems.get(paramInt);
      return null;
    }

    public long getItemId(int paramInt)
    {
      if (MessageCenterFragment.this.mLstItems != null)
        return paramInt;
      return 0L;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      View localView = paramView;
      if (localView == null)
      {
        localView = this.mInflater.inflate(2130903219, paramViewGroup, false);
        ViewHolder localViewHolder2 = new ViewHolder(null);
        localViewHolder2.labelView = ((TextView)localView.findViewById(2131363032));
        localViewHolder2.contentView = ((TextView)localView.findViewById(2131363034));
        localView.setTag(localViewHolder2);
      }
      ViewHolder localViewHolder1 = (ViewHolder)localView.getTag();
      HashMap localHashMap = (HashMap)getItem(paramInt);
      TextView localTextView;
      if (localHashMap != null)
      {
        int i = ((Integer)localHashMap.get("MsgCnt")).intValue();
        String str1 = (String)localHashMap.get("ItemLabel");
        String str2 = (String)localHashMap.get("ItemContent");
        localViewHolder1.labelView.setText(str1);
        localTextView = localViewHolder1.contentView;
        localTextView.setText(str2);
        if (i > 0)
          localTextView.setTextColor(MessageCenterFragment.this.getResources().getColor(2130839389));
      }
      else
      {
        return localView;
      }
      localTextView.setTextColor(MessageCenterFragment.this.getResources().getColor(2130839394));
      return localView;
    }

    private class ViewHolder
    {
      public TextView contentView;
      public TextView labelView;

      private ViewHolder()
      {
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.MessageCenterFragment
 * JD-Core Version:    0.6.0
 */