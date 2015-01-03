package com.kaixin001.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v4.app.FragmentActivity;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.engine.CircleMsgEngine;
import com.kaixin001.engine.UploadTaskListEngine;
import com.kaixin001.item.CircleDetailMessageUploadTask;
import com.kaixin001.item.KXLinkInfo;
import com.kaixin001.item.KXUploadTask;
import com.kaixin001.item.MessageAttachmentItem;
import com.kaixin001.item.MessageDetailItem;
import com.kaixin001.model.CircleMessageModel;
import com.kaixin001.model.MessageCenterModel;
import com.kaixin001.model.User;
import com.kaixin001.util.CrashRecoverUtil;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.ParseNewsInfoUtil;
import com.kaixin001.util.StringUtil;
import com.kaixin001.view.KXIntroView;
import com.kaixin001.view.KXIntroView.OnClickLinkListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

public class CircleMessageDetailFragment extends BaseFragment
  implements AdapterView.OnItemLongClickListener, KXIntroView.OnClickLinkListener
{
  protected static final int DLG_SHOW_COPY = 1002;
  public static final int LIST_CALLBACK = 501;
  protected static final int MENU_FORWARD_ID = 414;
  protected static final int MENU_HOME_ID = 412;
  protected static final int MENU_HOME_ME_ID = 413;
  protected static final int MENU_REFRESH_ID = 410;
  protected static final int MENU_TO_TOP_ID = 411;
  private static final int NO_PERSSION = -3002;
  private static final int SCROLL_TIMER_EVENT = 103;
  private static final int SENDING_STATE_COUNT = 4;
  private static final int SENDING_STATE_INTERVAL = 500;
  private static final String TAG = "CircleMessageDetailActivity";
  private static final String TAG_TASK_FINISHED = "task_finished";
  private static final String TAG_TASK_LAST_REFRESH = "task_last_refresh";
  private int beforePosition = 0;
  private int beforeTotalCount = this.mnMsgCount;
  private final Comparator<MessageDetailItem> cwjComparator = new Comparator()
  {
    public final int compare(MessageDetailItem paramMessageDetailItem1, MessageDetailItem paramMessageDetailItem2)
    {
      int i = paramMessageDetailItem1.getTaskID();
      int j = paramMessageDetailItem2.getTaskID();
      if (i > j)
        return 1;
      if (i <= j)
        return -1;
      return 0;
    }
  };
  private GetCircleMessageDetailTask getDetailTask = null;
  private boolean isReplyItem = false;
  private CircleMessageDetailAdapter mAdapter = new CircleMessageDetailAdapter();
  private RelativeLayout mCheckOriginalLayout = null;
  private TextView mCircleMsgFrom = null;
  private int mDetailType;
  private String mGName = null;
  private String mGid = null;
  private View mHeader;
  private LeaveOrRecoverConversationTask mLeaveOrJoinTask = null;
  private final ArrayList<MessageDetailItem> mListMessages = new ArrayList();
  private String mListTitle = null;
  private ListView mListView = null;
  private Runnable mListViewMoveToLastRowRunable = new Runnable()
  {
    public void run()
    {
      if (CircleMessageDetailFragment.this.isNeedReturn());
      do
        return;
      while (CircleMessageDetailFragment.this.mListView == null);
      CircleMessageDetailFragment.this.mListView.setSelection(-1 + CircleMessageDetailFragment.this.mListMessages.size());
    }
  };
  private ProgressBar mLoadPreviousProgressBar = null;
  private TextView mLoadPreviousText = null;
  private LinearLayout mMessageDetailCheckMore = null;
  private String mMsgContentForCopy = "";
  private View mReplyLine;
  private ImageView mRightButton = null;
  private int mSendingStatusStep = 0;
  private String mTid = null;
  private Runnable mUpdateSendingStatusRunnable = new Runnable()
  {
    public void run()
    {
      if (CircleMessageDetailFragment.this.isNeedReturn());
      do
        return;
      while (!CircleMessageDetailFragment.this.updateSendingState());
      CircleMessageDetailFragment.this.mSendingStatusStep = ((1 + CircleMessageDetailFragment.this.mSendingStatusStep) % 4);
      CircleMessageDetailFragment.this.mHandler.postDelayed(CircleMessageDetailFragment.this.mUpdateSendingStatusRunnable, 500L);
    }
  };
  private int mnListViewTopId = 0;
  private int mnMsgCount = 0;
  private String msCheckOriginalContent = null;
  private KXIntroView mtvCheckOriginal = null;
  private boolean preQuitSession = true;
  private boolean sessionState = true;
  private int userRole = -1;

  static
  {
    if (!CircleMessageDetailFragment.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }

  private void cancelTask()
  {
    if ((this.getDetailTask != null) && (this.getDetailTask.isCancelled()) && (this.getDetailTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.getDetailTask.cancel(true);
      this.getDetailTask = null;
    }
  }

  private boolean checkUserRole()
  {
    switch (this.userRole)
    {
    case 0:
    case 1:
    case 2:
    case 3:
    default:
      return true;
    case 4:
      Toast.makeText(getActivity(), 2131428071, 0).show();
      return false;
    case -1:
    case 5:
    case 6:
    }
    Toast.makeText(getActivity(), 2131428072, 0).show();
    return false;
  }

  private void deleteFinishedDetailInfo()
  {
    UploadTaskListEngine localUploadTaskListEngine = UploadTaskListEngine.getInstance();
    ArrayList localArrayList1 = (ArrayList)localUploadTaskListEngine.getWaitTaskList().clone();
    deleteTasksInQueue(localArrayList1);
    localArrayList1.clear();
    ArrayList localArrayList2 = (ArrayList)localUploadTaskListEngine.getUploadingTaskList().clone();
    deleteTasksInQueue(localArrayList2);
    localArrayList2.clear();
    ArrayList localArrayList3 = (ArrayList)localUploadTaskListEngine.getFinishedTaskList().clone();
    deleteTasksInQueue(localArrayList3);
    localArrayList3.clear();
  }

  private void deleteTasksInQueue(ArrayList<KXUploadTask> paramArrayList)
  {
    if (paramArrayList == null);
    while (true)
    {
      return;
      UploadTaskListEngine localUploadTaskListEngine = UploadTaskListEngine.getInstance();
      for (int i = 0; i < paramArrayList.size(); i++)
      {
        KXUploadTask localKXUploadTask = (KXUploadTask)paramArrayList.get(i);
        if (!(localKXUploadTask instanceof CircleDetailMessageUploadTask))
          continue;
        CircleDetailMessageUploadTask localCircleDetailMessageUploadTask = (CircleDetailMessageUploadTask)localKXUploadTask;
        if ((localCircleDetailMessageUploadTask.getGid() == null) || (!localCircleDetailMessageUploadTask.getGid().equals(this.mGid)) || (localCircleDetailMessageUploadTask.getMode() != 10) || (localCircleDetailMessageUploadTask.getTaskStatus() != 2))
          continue;
        localUploadTaskListEngine.deleteTask(localCircleDetailMessageUploadTask);
      }
    }
  }

  private void doAction(boolean paramBoolean)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put(getString(2131427562), Integer.valueOf(0));
    String str;
    String[] arrayOfString3;
    if (this.msCheckOriginalContent == null)
    {
      str = "Error";
      localHashMap.put(str, Integer.valueOf(1));
      localHashMap.put(getString(2131427905), Integer.valueOf(2));
      localHashMap.put(getString(2131427906), Integer.valueOf(2));
      if (paramBoolean)
        break label196;
      if (this.msCheckOriginalContent == null)
        break label165;
      arrayOfString3 = new String[3];
      arrayOfString3[0] = getString(2131427562);
      arrayOfString3[1] = this.msCheckOriginalContent;
      arrayOfString3[2] = getString(2131427905);
      label126: String[] arrayOfString4 = arrayOfString3;
      DialogUtil.showSelectListDlg(getActivity(), 2131427907, arrayOfString3, new DialogInterface.OnClickListener(localHashMap, arrayOfString4)
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          if (CircleMessageDetailFragment.this.checkUserRole());
          switch (((Integer)this.val$dialogString.get(this.val$itemString[paramInt])).intValue())
          {
          default:
            return;
          case 0:
            IntentUtil.replyCircleDetail(CircleMessageDetailFragment.this.getActivity(), CircleMessageDetailFragment.this, CircleMessageDetailFragment.this.mGid, CircleMessageDetailFragment.this.mTid);
            return;
          case 1:
            CircleMessageDetailFragment.this.turnToOriginalContent();
            return;
          case 2:
          }
          CircleMessageDetailFragment.this.recoverConversation();
        }
      }
      , 1);
    }
    label165: label196: 
    do
    {
      return;
      str = this.msCheckOriginalContent;
      break;
      arrayOfString3 = new String[2];
      arrayOfString3[0] = getString(2131427562);
      arrayOfString3[1] = getString(2131427905);
      break label126;
    }
    while (!paramBoolean);
    String[] arrayOfString1;
    if (this.msCheckOriginalContent != null)
    {
      arrayOfString1 = new String[3];
      arrayOfString1[0] = getString(2131427562);
      arrayOfString1[1] = this.msCheckOriginalContent;
      arrayOfString1[2] = getString(2131427906);
    }
    while (true)
    {
      String[] arrayOfString2 = arrayOfString1;
      DialogUtil.showSelectListDlg(getActivity(), 2131427907, arrayOfString1, new DialogInterface.OnClickListener(localHashMap, arrayOfString2)
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          if (CircleMessageDetailFragment.this.checkUserRole());
          switch (((Integer)this.val$dialogString.get(this.val$itemString[paramInt])).intValue())
          {
          default:
            return;
          case 0:
            IntentUtil.replyCircleDetail(CircleMessageDetailFragment.this.getActivity(), CircleMessageDetailFragment.this, CircleMessageDetailFragment.this.mGid, CircleMessageDetailFragment.this.mTid);
            return;
          case 1:
            CircleMessageDetailFragment.this.turnToOriginalContent();
            return;
          case 2:
          }
          CircleMessageDetailFragment.this.leaveConversation();
        }
      }
      , 1);
      return;
      arrayOfString1 = new String[2];
      arrayOfString1[0] = getString(2131427562);
      arrayOfString1[1] = getString(2131427906);
    }
  }

  private void doGetData(boolean paramBoolean)
  {
    cancelTask();
    if (!paramBoolean)
    {
      setTitleBar();
      this.mRightButton.setVisibility(8);
    }
    while (!super.checkNetworkAndHint(true))
    {
      return;
      hideFooter();
    }
    deleteFinishedDetailInfo();
    if (paramBoolean)
    {
      showLoadingProgressBar(true, 1);
      downloadMessage("0");
      return;
    }
    showLoadingProgressBar(true, 0);
    loadMoreMsg();
  }

  private void downloadMessage(String paramString)
  {
    if ((this.getDetailTask != null) && (this.getDetailTask.isCancelled()) && (this.getDetailTask.getStatus() != AsyncTask.Status.FINISHED))
      return;
    if (TextUtils.isEmpty(paramString))
      paramString = "0";
    this.getDetailTask = new GetCircleMessageDetailTask(null);
    String[] arrayOfString = new String[3];
    arrayOfString[0] = this.mGid;
    arrayOfString[1] = this.mTid;
    arrayOfString[2] = paramString;
    this.getDetailTask.execute(arrayOfString);
  }

  private ArrayList<MessageDetailItem> getDetailListFromUpQueue()
  {
    ArrayList localArrayList = new ArrayList();
    UploadTaskListEngine localUploadTaskListEngine = UploadTaskListEngine.getInstance();
    setMessageDatafromUpQueue(localArrayList, localUploadTaskListEngine.getWaitTaskList());
    setMessageDatafromUpQueue(localArrayList, localUploadTaskListEngine.getUploadingTaskList());
    setMessageDatafromUpQueue(localArrayList, localUploadTaskListEngine.getFinishedTaskList());
    Collections.sort(localArrayList, this.cwjComparator);
    return localArrayList;
  }

  private void leaveConversation()
  {
    String[] arrayOfString = { "0" };
    this.mLeaveOrJoinTask = new LeaveOrRecoverConversationTask(null);
    this.mLeaveOrJoinTask.execute(arrayOfString);
  }

  private void loadMoreMsg()
  {
    if (this.mListMessages != null)
      setMsgDetailAbtainPreviousContent(2);
    if ((this.getDetailTask != null) && (this.getDetailTask.isCancelled()) && (this.getDetailTask.getStatus() != AsyncTask.Status.FINISHED))
      return;
    if ((this.mListMessages != null) && (this.mListMessages.size() > 0));
    for (String str = String.valueOf(this.mListMessages.size()); ; str = "0")
    {
      downloadMessage(str);
      return;
    }
  }

  private ArrayList<KXLinkInfo> makeTitleList(String paramString)
  {
    return ParseNewsInfoUtil.parseCommentAndReplyLinkString(paramString);
  }

  private void recoverConversation()
  {
    String[] arrayOfString = { "1" };
    this.mLeaveOrJoinTask = new LeaveOrRecoverConversationTask(null);
    this.mLeaveOrJoinTask.execute(arrayOfString);
  }

  private void refreshData()
  {
    updateData();
    this.mnMsgCount = (1 + this.mnMsgCount);
    startSendingAnimation();
  }

  private void setMessageDatafromUpQueue(ArrayList<MessageDetailItem> paramArrayList, ArrayList<KXUploadTask> paramArrayList1)
  {
    if ((paramArrayList1 == null) || (paramArrayList == null));
    while (true)
    {
      return;
      for (int i = 0; i < paramArrayList1.size(); i++)
      {
        KXUploadTask localKXUploadTask = (KXUploadTask)paramArrayList1.get(i);
        if ((localKXUploadTask == null) || (!(localKXUploadTask instanceof CircleDetailMessageUploadTask)))
          continue;
        CircleDetailMessageUploadTask localCircleDetailMessageUploadTask = (CircleDetailMessageUploadTask)paramArrayList1.get(i);
        if ((!localCircleDetailMessageUploadTask.getGid().equals(this.mGid)) || (localCircleDetailMessageUploadTask.getMode() != 10))
          continue;
        long l = Calendar.getInstance().getTimeInMillis() / 1000L;
        MessageDetailItem localMessageDetailItem = new MessageDetailItem();
        localMessageDetailItem.setAbscont(localCircleDetailMessageUploadTask.getContent());
        localMessageDetailItem.setCtime(Long.valueOf(l));
        localMessageDetailItem.setFlogo(User.getInstance().getLogo());
        localMessageDetailItem.setFuid(User.getInstance().getUID());
        localMessageDetailItem.setFname(getResources().getString(2131427565));
        localMessageDetailItem.setStatus(localCircleDetailMessageUploadTask.getTaskStatus());
        localMessageDetailItem.setTaskID(localCircleDetailMessageUploadTask.getTaskId());
        paramArrayList.add(localMessageDetailItem);
      }
    }
  }

  private void setMsgDetailAbtainPreviousContent(int paramInt)
  {
    if (paramInt == 0)
    {
      this.mMessageDetailCheckMore.setVisibility(8);
      this.mLoadPreviousProgressBar.setVisibility(8);
      this.mLoadPreviousText.setVisibility(8);
    }
    do
    {
      return;
      if (paramInt == 1)
      {
        this.mMessageDetailCheckMore.setVisibility(0);
        this.mLoadPreviousProgressBar.setVisibility(8);
        this.mLoadPreviousText.setText(2131427943);
        this.mLoadPreviousText.setVisibility(0);
        return;
      }
      if (paramInt != 2)
        continue;
      this.mMessageDetailCheckMore.setVisibility(0);
      this.mLoadPreviousProgressBar.setVisibility(0);
      this.mLoadPreviousText.setText(2131427942);
      this.mLoadPreviousText.setVisibility(0);
      return;
    }
    while ($assertionsDisabled);
    throw new AssertionError();
  }

  private void setReturnResult()
  {
    Intent localIntent;
    if ((this.mListMessages != null) && (this.mListMessages.size() > 0))
    {
      MessageDetailItem localMessageDetailItem = (MessageDetailItem)this.mListMessages.get(-1 + this.mListMessages.size());
      localIntent = new Intent(getActivity(), CircleMessageFragment.class);
      localIntent.putExtra("name", localMessageDetailItem.getFname());
      localIntent.putExtra("ctime", localMessageDetailItem.getCtime());
      localIntent.putExtra("subject", localMessageDetailItem.getAbscont());
      localIntent.putExtra("totalcount", this.mnMsgCount);
      localIntent.putExtra("position", this.beforePosition);
      localIntent.putExtra("logo", localMessageDetailItem.getFlogo());
      if (!this.sessionState)
        break label147;
      localIntent.putExtra("quitsession", 0);
    }
    while (true)
    {
      setResult(-1, localIntent);
      return;
      label147: localIntent.putExtra("quitsession", 1);
    }
  }

  private void showEmptyNotice(boolean paramBoolean)
  {
    TextView localTextView = (TextView)findViewById(2131363036);
    if (paramBoolean)
    {
      localTextView.setVisibility(0);
      return;
    }
    localTextView.setVisibility(8);
  }

  private void showList(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.mListView.setVisibility(0);
      showFooter();
      return;
    }
    this.mListView.setVisibility(8);
  }

  private void showLoadingProgressBar(boolean paramBoolean, int paramInt)
  {
    View localView1 = findViewById(2131363037);
    View localView2 = findViewById(2131362925);
    if (paramBoolean)
    {
      if (paramInt == 0)
      {
        localView1.setVisibility(0);
        return;
      }
      localView2.setVisibility(0);
      return;
    }
    if (paramInt == 0)
    {
      localView1.setVisibility(8);
      return;
    }
    localView2.setVisibility(8);
  }

  private boolean showMessageSendingState(MessageDetailItem paramMessageDetailItem, ImageView paramImageView, TextView paramTextView, View paramView, boolean paramBoolean)
  {
    if ((paramMessageDetailItem == null) || (paramImageView == null) || (paramTextView == null) || (paramView == null))
      return false;
    String str = getString(2131427995);
    KXUploadTask localKXUploadTask;
    int i;
    int k;
    switch (this.mSendingStatusStep)
    {
    default:
      if (paramMessageDetailItem.getStatues() == 2)
      {
        localKXUploadTask = UploadTaskListEngine.getInstance().findTaskById(paramMessageDetailItem.getTaskID());
        i = 0;
        k = 0;
        if (localKXUploadTask != null)
        {
          if (!"task_finished".equals(localKXUploadTask.getTag()))
            break;
          if (!paramBoolean)
            localKXUploadTask.setTag("task_last_refresh");
          k = 1;
          i = 1;
        }
        else
        {
          label122: if (k == 0)
            break label295;
          paramTextView.setText(2131427592);
          paramTextView.setTextColor(getResources().getColor(2130839419));
          paramTextView.setGravity(3);
          paramImageView.setImageResource(2130838884);
          paramImageView.setVisibility(0);
          label165: paramTextView.setVisibility(0);
        }
      }
    case 1:
    case 2:
    case 3:
    }
    while (true)
    {
      return i;
      str = str + ".";
      break;
      str = str + "..";
      break;
      str = str + "...";
      break;
      boolean bool = "task_last_refresh".equals(localKXUploadTask.getTag());
      i = 0;
      k = 0;
      if (bool)
        break label122;
      if (!paramBoolean)
        localKXUploadTask.setTag("task_finished");
      k = 1;
      i = 1;
      break label122;
      label295: paramTextView.setText(MessageCenterModel.formatTimestamp(1000L * paramMessageDetailItem.getCtime().longValue()));
      paramTextView.setTextColor(getResources().getColor(2130839395));
      paramTextView.setGravity(5);
      paramImageView.setVisibility(8);
      break label165;
      if ((paramMessageDetailItem.getStatues() == 1) || (paramMessageDetailItem.getStatues() == 0))
      {
        paramTextView.setText(str);
        paramTextView.setTextColor(getResources().getColor(2130839419));
        paramTextView.setGravity(3);
        paramTextView.setVisibility(0);
        paramImageView.setVisibility(8);
        i = 1;
        continue;
      }
      int j = paramMessageDetailItem.getStatues();
      i = 0;
      if (j != 3)
        continue;
      paramTextView.setText(2131427997);
      paramTextView.setTextColor(getResources().getColor(2130839389));
      paramTextView.setGravity(3);
      paramTextView.setVisibility(0);
      paramImageView.setImageResource(2130838883);
      paramImageView.setVisibility(0);
      paramView.setTag(paramMessageDetailItem);
      paramView.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          MessageDetailItem localMessageDetailItem = (MessageDetailItem)paramView.getTag();
          KXUploadTask localKXUploadTask = UploadTaskListEngine.getInstance().findTaskById(localMessageDetailItem.getTaskID());
          if ((localKXUploadTask == null) || (localKXUploadTask.getTaskStatus() != 3))
            return;
          String[] arrayOfString = new String[2];
          arrayOfString[0] = CircleMessageDetailFragment.this.getString(2131427999);
          arrayOfString[1] = CircleMessageDetailFragment.this.getString(2131428000);
          DialogUtil.showSelectListDlg(CircleMessageDetailFragment.this.getActivity(), 2131427996, arrayOfString, new DialogInterface.OnClickListener(localKXUploadTask)
          {
            public void onClick(DialogInterface paramDialogInterface, int paramInt)
            {
              UploadTaskListEngine localUploadTaskListEngine = UploadTaskListEngine.getInstance();
              if (paramInt == 0)
                localUploadTaskListEngine.deleteTask(this.val$task);
              do
                return;
              while (paramInt != 1);
              localUploadTaskListEngine.restartTask(this.val$task);
              CircleMessageDetailFragment.this.startSendingAnimation();
            }
          }
          , 1);
        }
      });
      i = 1;
    }
  }

  private void startSendingAnimation()
  {
    stopSendingAnimation();
    this.mSendingStatusStep = 0;
    this.mHandler.postDelayed(this.mUpdateSendingStatusRunnable, 500L);
  }

  private void stopSendingAnimation()
  {
    this.mHandler.removeCallbacks(this.mUpdateSendingStatusRunnable);
  }

  private void turnToOriginalContent()
  {
    if ((!TextUtils.isEmpty(this.mGid)) && (!TextUtils.isEmpty(this.mTid)))
    {
      Intent localIntent = new Intent(getActivity(), CircleDetailsFragment.class);
      Bundle localBundle = new Bundle();
      localBundle.putString("gid", this.mGid);
      localBundle.putString("tid", this.mTid);
      localBundle.putString("gname", this.mGName);
      localIntent.putExtras(localBundle);
      startFragment(localIntent, true, 1);
    }
  }

  private void updateData()
  {
    this.mListMessages.clear();
    ArrayList localArrayList = CircleMessageModel.getInstance().getActiveCircleMesasgeDetail();
    if (localArrayList != null)
      this.mListMessages.addAll(localArrayList);
    this.mListMessages.addAll(getDetailListFromUpQueue());
    if (this.mAdapter != null)
      this.mAdapter.notifyDataSetChanged();
  }

  private boolean updateSendingState()
  {
    int i = 0;
    int j = this.mListView.getChildCount();
    int k = this.mListView.getFirstVisiblePosition() - this.mListView.getHeaderViewsCount();
    int m = 0;
    int n = 0;
    if (n >= j)
    {
      if (m != 0)
        return i;
    }
    else
    {
      MessageDetailViewTag localMessageDetailViewTag = (MessageDetailViewTag)this.mListView.getChildAt(n).getTag();
      int i1 = n + k;
      MessageDetailItem localMessageDetailItem = null;
      if (i1 >= 0)
      {
        int i2 = n + k;
        int i3 = this.mListMessages.size();
        localMessageDetailItem = null;
        if (i2 < i3)
          localMessageDetailItem = (MessageDetailItem)this.mListMessages.get(n + k);
      }
      if ((localMessageDetailViewTag == null) || (localMessageDetailItem == null) || (localMessageDetailItem.getStatues() < 0));
      while (true)
      {
        n++;
        break;
        m = 1;
        if (!showMessageSendingState(localMessageDetailItem, localMessageDetailViewTag.mImgSendingIcon, localMessageDetailViewTag.mTxtMyTime, localMessageDetailViewTag.mLayoutTime, false))
          continue;
        i = 1;
      }
    }
    return true;
  }

  public String formatTimestamp(long paramLong)
  {
    Calendar localCalendar1 = Calendar.getInstance();
    int i = localCalendar1.get(1);
    int j = localCalendar1.get(6);
    Calendar localCalendar2 = Calendar.getInstance();
    localCalendar2.setTimeInMillis(paramLong);
    int k = localCalendar2.get(1);
    int m = localCalendar2.get(6);
    String str;
    if (k != i)
      str = "yyyy年MM月dd日 HH:mm";
    while (true)
    {
      Date localDate = new Date(paramLong);
      return new SimpleDateFormat(str).format(localDate);
      if (m != j)
      {
        str = "MM月dd日 HH:mm";
        continue;
      }
      str = "HH:mm";
    }
  }

  protected boolean handleMessage(Message paramMessage)
  {
    if (paramMessage == null)
      return false;
    if (paramMessage.what == 10001)
      updateData();
    while (true)
    {
      return true;
      if (paramMessage.what == 103)
        continue;
      if (paramMessage.what != -3002)
        break;
      Toast.makeText(getActivity().getApplicationContext(), getResources().getString(2131428072), 0).show();
    }
    return super.handleMessage(paramMessage);
  }

  protected void hideFooter()
  {
    this.mReplyLine.setVisibility(8);
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if ((paramInt2 == -1) && (paramInt1 == 10))
    {
      this.isReplyItem = true;
      refreshData();
      this.mListView.postDelayed(this.mListViewMoveToLastRowRunable, 0L);
    }
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
      CrashRecoverUtil.crashRecover(getActivity().getApplicationContext());
      IntentUtil.returnToDesktop(getActivity());
    }
    do
      return;
    while (!TextUtils.isEmpty(User.getInstance().getUID()));
    finish();
  }

  protected Dialog onCreateDialog(int paramInt)
  {
    if (1002 == paramInt)
      return DialogUtil.showSelectListDlg(getActivity(), 2131427509, getResources().getStringArray(2131492888), new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          ((ClipboardManager)CircleMessageDetailFragment.this.getActivity().getSystemService("clipboard")).setText(CircleMessageDetailFragment.this.mMsgContentForCopy);
        }
      }
      , 1);
    return super.onCreateDialog(paramInt);
  }

  public void onCreateOptionsMenu(Menu paramMenu, MenuInflater paramMenuInflater)
  {
    super.onCreateOptionsMenu(paramMenu, paramMenuInflater);
    paramMenu.add(0, 411, 0, 2131427698).setIcon(2130838609);
    paramMenu.add(0, 410, 0, 2131427690).setIcon(2130838607);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903220, paramViewGroup, false);
  }

  public void onDestroy()
  {
    try
    {
      dismissDialog(1002);
      label7: stopSendingAnimation();
      getActivity().removeDialog(1002);
      UploadTaskListEngine.getInstance().unRegister(this.mHandler);
      cancelTask();
      if ((this.getDetailTask != null) && (this.getDetailTask.getStatus() != AsyncTask.Status.FINISHED))
      {
        this.getDetailTask.cancel(true);
        CircleMsgEngine.getInstance().cancel();
      }
      cancelTask(this.mLeaveOrJoinTask);
      super.onDestroy();
      return;
    }
    catch (Exception localException)
    {
      break label7;
    }
  }

  public boolean onItemLongClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    if (this.mListMessages == null);
    do
      return false;
    while ((paramLong < 0L) || (paramLong >= this.mListMessages.size()));
    MessageDetailItem localMessageDetailItem = (MessageDetailItem)this.mListMessages.get((int)paramLong);
    ((Vibrator)getActivity().getSystemService("vibrator")).vibrate(50L);
    this.mMsgContentForCopy = localMessageDetailItem.getAbscont();
    showDialog(1002);
    return true;
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if ((paramInt == 4) && (paramKeyEvent.getRepeatCount() == 0) && ((this.mnMsgCount > this.beforeTotalCount) || (this.preQuitSession != this.sessionState)))
      setReturnResult();
    return super.onKeyDown(paramInt, paramKeyEvent);
  }

  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default:
      return super.onOptionsItemSelected(paramMenuItem);
    case 410:
      doGetData(true);
      return true;
    case 411:
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

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    UploadTaskListEngine.getInstance().register(this.mHandler);
    Bundle localBundle = getArguments();
    int i = 1;
    if (localBundle != null)
    {
      this.mDetailType = localBundle.getInt("type");
      this.mnMsgCount = localBundle.getInt("count");
      this.beforeTotalCount = this.mnMsgCount;
      localBundle.getInt("newcount");
      this.mGid = localBundle.getString("gid");
      this.mTid = localBundle.getString("tid");
      this.mGName = localBundle.getString("gname");
      this.mListTitle = localBundle.getString("listtitle");
      i = localBundle.getInt("canjump");
      this.userRole = localBundle.getInt("userrole");
      this.beforePosition = localBundle.getInt("position");
      if (localBundle.getInt("quitsession") == 1)
      {
        this.preQuitSession = false;
        this.sessionState = false;
      }
    }
    this.mListView = ((ListView)findViewById(2131363035));
    this.mHeader = getActivity().getLayoutInflater().inflate(2130903222, null);
    this.mReplyLine = getActivity().getLayoutInflater().inflate(2130903221, null);
    if (i != 0)
    {
      String str = localBundle.getString("appid");
      if ((str != null) && ((str.equals("回复我")) || (str.equals("我回复"))))
      {
        this.mCheckOriginalLayout = ((RelativeLayout)getActivity().getLayoutInflater().inflate(2130903071, null));
        this.mtvCheckOriginal = ((KXIntroView)this.mCheckOriginalLayout.findViewById(2131362026));
        ArrayList localArrayList = makeTitleList(this.mListTitle);
        this.mtvCheckOriginal.setTitleList(localArrayList);
        this.mCircleMsgFrom = ((TextView)this.mCheckOriginalLayout.findViewById(2131362027));
        this.mCircleMsgFrom.setText("来自 " + this.mGName);
        this.mCheckOriginalLayout.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramView)
          {
            if (CircleMessageDetailFragment.this.checkUserRole())
              CircleMessageDetailFragment.this.turnToOriginalContent();
          }
        });
        this.mCheckOriginalLayout.setVisibility(0);
        this.mListView.addHeaderView(this.mCheckOriginalLayout);
        this.msCheckOriginalContent = getString(2131427896);
      }
    }
    this.mListView.addHeaderView(this.mHeader);
    this.mListView.addFooterView(this.mReplyLine);
    try
    {
      this.mMessageDetailCheckMore = ((LinearLayout)this.mHeader.findViewById(2131363039));
      this.mLoadPreviousProgressBar = ((ProgressBar)this.mHeader.findViewById(2131363040));
      this.mLoadPreviousText = ((TextView)this.mHeader.findViewById(2131363041));
      setMsgDetailAbtainPreviousContent(0);
      this.mMessageDetailCheckMore.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          CircleMessageDetailFragment.this.loadMoreMsg();
        }
      });
      ((Button)this.mReplyLine.findViewById(2131363038)).setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          if (CircleMessageDetailFragment.this.checkUserRole())
            IntentUtil.replyCircleDetail(CircleMessageDetailFragment.this.getActivity(), CircleMessageDetailFragment.this, CircleMessageDetailFragment.this.mGid, CircleMessageDetailFragment.this.mTid);
        }
      });
      this.mReplyLine.setVisibility(8);
      this.mListView.setAdapter(this.mAdapter);
      this.mListView.setOnItemLongClickListener(this);
      this.mListView.setOnScrollListener(new AbsListView.OnScrollListener()
      {
        public void onScroll(AbsListView paramAbsListView, int paramInt1, int paramInt2, int paramInt3)
        {
        }

        public void onScrollStateChanged(AbsListView paramAbsListView, int paramInt)
        {
          CircleMessageDetailFragment.this.mnListViewTopId = paramAbsListView.getFirstVisiblePosition();
        }
      });
      this.mListView.setVisibility(8);
      setTitleBar();
      doGetData(true);
      return;
    }
    catch (Exception localException)
    {
      while (true)
        KXLog.e("Messagedetail", localException.toString(), localException);
    }
  }

  protected void setTitleBar()
  {
    ImageView localImageView = (ImageView)findViewById(2131362914);
    localImageView.setVisibility(0);
    localImageView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        if ((CircleMessageDetailFragment.this.mnMsgCount > CircleMessageDetailFragment.this.beforeTotalCount) || (CircleMessageDetailFragment.this.preQuitSession != CircleMessageDetailFragment.this.sessionState))
          CircleMessageDetailFragment.this.setReturnResult();
        CircleMessageDetailFragment.this.finishFragment(501);
        CircleMessageDetailFragment.this.finish();
      }
    });
    this.mRightButton = ((ImageView)findViewById(2131362928));
    this.mRightButton.setVisibility(8);
    ((ImageView)findViewById(2131362919)).setVisibility(8);
    TextView localTextView = (TextView)findViewById(2131362920);
    localTextView.setVisibility(0);
    int j;
    if (this.mDetailType == 207)
    {
      localTextView.setText(getResources().getText(2131428063));
      j = 2130839005;
    }
    while (true)
    {
      this.mRightButton.setImageResource(j);
      return;
      int i = this.mDetailType;
      j = 0;
      if (i != 208)
        continue;
      localTextView.setText(getResources().getText(2131428064));
      j = 2130839005;
    }
  }

  protected void showFooter()
  {
    this.mReplyLine.setVisibility(0);
  }

  private class CircleMessageDetailAdapter extends BaseAdapter
  {
    private int currentDay = Calendar.getInstance().get(6);
    private Calendar detailItemTime = Calendar.getInstance();

    public CircleMessageDetailAdapter()
    {
    }

    private void showAttachmentList(MessageDetailItem paramMessageDetailItem, TextView paramTextView, LinearLayout paramLinearLayout)
    {
      if ((paramMessageDetailItem == null) || (paramTextView == null) || (paramLinearLayout == null))
        return;
      ArrayList localArrayList = paramMessageDetailItem.getMessageAttmList();
      if ((localArrayList != null) && (localArrayList.size() != 0))
      {
        int i = localArrayList.size();
        paramTextView.setVisibility(0);
        paramTextView.setText(StringUtil.replaceTokenWith(CircleMessageDetailFragment.this.getResources().getString(2131427564), "*", String.valueOf(i)));
        paramLinearLayout.removeAllViews();
        for (int j = 0; ; j++)
        {
          if (j >= i)
          {
            paramLinearLayout.setVisibility(0);
            paramLinearLayout.setOnClickListener(new View.OnClickListener()
            {
              public void onClick(View paramView)
              {
                Toast.makeText(CircleMessageDetailFragment.this.getActivity(), 2131427853, 0).show();
              }
            });
            return;
          }
          MessageAttachmentItem localMessageAttachmentItem = (MessageAttachmentItem)localArrayList.get(j);
          String str1 = localMessageAttachmentItem.getmAttmName();
          String str2 = localMessageAttachmentItem.getmAttmSize();
          View localView = CircleMessageDetailFragment.this.getActivity().getLayoutInflater().inflate(2130903217, null);
          ((TextView)localView.findViewById(2131363028)).setText(str1);
          ((TextView)localView.findViewById(2131363029)).setText(str2);
          paramLinearLayout.addView(localView);
        }
      }
      paramTextView.setVisibility(8);
      paramLinearLayout.setVisibility(8);
    }

    private void showMessageDate(MessageDetailItem paramMessageDetailItem, int paramInt, TextView paramTextView)
    {
      if ((paramMessageDetailItem == null) || (paramTextView == null))
        return;
      long l1 = 1000L * paramMessageDetailItem.getCtime().longValue();
      this.detailItemTime.setTimeInMillis(l1);
      int i = this.detailItemTime.get(6);
      int j = 1;
      if (paramInt != 0)
      {
        long l2 = paramMessageDetailItem.getCtime().longValue();
        if (CountDayOff(((MessageDetailItem)CircleMessageDetailFragment.this.mListMessages.get(paramInt - 1)).getCtime().longValue(), l2) == 0)
          j = 0;
      }
      if (j != 0)
      {
        if (this.currentDay == i);
        for (String str = CircleMessageDetailFragment.this.getResources().getString(2131427563); ; str = DateFormat.format("yyyy-MM-dd", l1).toString())
        {
          paramTextView.setText(str);
          paramTextView.setVisibility(0);
          return;
        }
      }
      paramTextView.setVisibility(8);
    }

    private void showMessageTime(MessageDetailItem paramMessageDetailItem, ImageView paramImageView, TextView paramTextView, View paramView, boolean paramBoolean)
    {
      if ((paramMessageDetailItem == null) || (paramImageView == null) || (paramTextView == null) || (paramView == null))
        return;
      String str = CircleMessageDetailFragment.this.formatTimestamp(1000L * paramMessageDetailItem.getCtime().longValue());
      if (paramBoolean)
      {
        paramTextView.setText(str);
        return;
      }
      paramImageView.setVisibility(8);
      if (paramMessageDetailItem.getStatues() == -1)
      {
        paramTextView.setText(str);
        paramTextView.setTextColor(CircleMessageDetailFragment.this.getResources().getColor(2130839395));
        paramTextView.setGravity(5);
        return;
      }
      CircleMessageDetailFragment.this.showMessageSendingState(paramMessageDetailItem, paramImageView, paramTextView, paramView, true);
    }

    public int CountDayOff(long paramLong1, long paramLong2)
    {
      this.detailItemTime.setTimeInMillis(paramLong1 * 1000L);
      int i = this.detailItemTime.get(6);
      this.detailItemTime.setTimeInMillis(paramLong2 * 1000L);
      return i - this.detailItemTime.get(6);
    }

    public int getCount()
    {
      if (CircleMessageDetailFragment.this.mListMessages == null)
        return 0;
      return CircleMessageDetailFragment.this.mListMessages.size();
    }

    public Object getItem(int paramInt)
    {
      if (CircleMessageDetailFragment.this.mListMessages == null);
      do
        return null;
      while ((paramInt < 0) || (paramInt >= CircleMessageDetailFragment.this.mListMessages.size()));
      return CircleMessageDetailFragment.this.mListMessages.get(paramInt);
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      boolean bool = true;
      CircleMessageDetailFragment.MessageDetailViewTag localMessageDetailViewTag;
      if (paramView == null)
      {
        paramView = CircleMessageDetailFragment.this.getActivity().getLayoutInflater().inflate(2130903223, paramViewGroup, false);
        localMessageDetailViewTag = new CircleMessageDetailFragment.MessageDetailViewTag(paramView, null);
        paramView.setTag(localMessageDetailViewTag);
      }
      try
      {
        MessageDetailItem localMessageDetailItem = (MessageDetailItem)getItem(paramInt);
        String str1 = User.getInstance().getUID();
        if ((str1 != null) && (str1.equals(localMessageDetailItem.getFuid())))
          bool = false;
        String str2 = localMessageDetailItem.getFlogo();
        String str3 = localMessageDetailItem.getAbscont();
        String str4 = localMessageDetailItem.getFname();
        if ((str4 == null) || (str4.equals(CircleMessageDetailFragment.this.getString(2131427565))))
          str4 = User.getInstance().getRealName();
        showMessageDate(localMessageDetailItem, paramInt, localMessageDetailViewTag.mTxtTime);
        TextView localTextView1;
        TextView localTextView2;
        KXIntroView localKXIntroView;
        TextView localTextView3;
        ImageView localImageView;
        LinearLayout localLinearLayout;
        if (bool)
        {
          localMessageDetailViewTag.mLayoutFriendMessage.setVisibility(0);
          localMessageDetailViewTag.mLayoutMyMessage.setVisibility(8);
          localTextView1 = localMessageDetailViewTag.mTxtFriendName;
          localTextView2 = localMessageDetailViewTag.mTxtFriendTime;
          localKXIntroView = localMessageDetailViewTag.mViewFriendContent;
          localTextView3 = localMessageDetailViewTag.mTxtFriendAttachment;
          localImageView = localMessageDetailViewTag.mImgFriendAvatar;
          localLinearLayout = localMessageDetailViewTag.mLayoutFriendAttachmentList;
          label210: CircleMessageDetailFragment localCircleMessageDetailFragment = CircleMessageDetailFragment.this;
          localKXIntroView.setOnClickLinkListener(localCircleMessageDetailFragment);
          CircleMessageDetailFragment.FNameOnClickListener localFNameOnClickListener = new CircleMessageDetailFragment.FNameOnClickListener(CircleMessageDetailFragment.this, localMessageDetailItem.getFuid(), str4);
          localTextView1.setOnClickListener(localFNameOnClickListener);
          if (!bool)
            break label423;
          localTextView1.setText(str4);
        }
        while (true)
        {
          ArrayList localArrayList = localMessageDetailItem.makeTitleList(str3);
          localKXIntroView.setTitleList(localArrayList);
          CircleMessageDetailFragment.this.displayPicture(localImageView, str2, 2130838676);
          localImageView.setOnClickListener(new CircleMessageDetailFragment.FNameOnClickListener(CircleMessageDetailFragment.this, localMessageDetailItem.getFuid(), str4));
          showMessageTime(localMessageDetailItem, localMessageDetailViewTag.mImgSendingIcon, localTextView2, localMessageDetailViewTag.mLayoutTime, bool);
          showAttachmentList(localMessageDetailItem, localTextView3, localLinearLayout);
          return paramView;
          localMessageDetailViewTag = (CircleMessageDetailFragment.MessageDetailViewTag)paramView.getTag();
          break;
          localMessageDetailViewTag.mLayoutFriendMessage.setVisibility(8);
          localMessageDetailViewTag.mLayoutMyMessage.setVisibility(0);
          localTextView1 = localMessageDetailViewTag.mTxtMyName;
          localTextView2 = localMessageDetailViewTag.mTxtMyTime;
          localKXIntroView = localMessageDetailViewTag.mViewMyContent;
          localTextView3 = localMessageDetailViewTag.mTxtMyAttachement;
          localImageView = localMessageDetailViewTag.mImgMyAvatar;
          localLinearLayout = localMessageDetailViewTag.mLayoutMyAttachmentList;
          break label210;
          label423: localTextView1.setText(2131427565);
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
      return paramView;
    }

    public void notifyDataSetChanged()
    {
      super.notifyDataSetChanged();
      this.currentDay = Calendar.getInstance().get(6);
      this.detailItemTime = Calendar.getInstance();
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
      paramView.setBackgroundResource(2130838512);
      if ((this.fuid != null) && (this.fname != null))
        IntentUtil.showHomeFragment(CircleMessageDetailFragment.this, this.fuid, this.fname);
    }
  }

  private class GetCircleMessageDetailTask extends KXFragment.KXAsyncTask<String, Void, Boolean>
  {
    private GetCircleMessageDetailTask()
    {
      super();
    }

    protected Boolean doInBackgroundA(String[] paramArrayOfString)
    {
      Boolean.valueOf(false);
      if (paramArrayOfString.length < 2)
        return Boolean.valueOf(false);
      try
      {
        String str1 = paramArrayOfString[0];
        String str2 = paramArrayOfString[1];
        String str3 = paramArrayOfString[2];
        if (str3.equals("0"))
          CircleMessageModel.getInstance().clearCircleMessageDetailList();
        Boolean localBoolean2 = Boolean.valueOf(CircleMsgEngine.getInstance().doGetCircleMsgDetail(CircleMessageDetailFragment.this.getActivity().getApplicationContext(), str1, str2, str3, "desc"));
        localBoolean1 = localBoolean2;
        if (localBoolean1.booleanValue())
          CircleMessageModel.getInstance().getReturnDetailTotal();
        return localBoolean1;
      }
      catch (Exception localException)
      {
        while (true)
        {
          KXLog.e("CircleMessageDetailActivity", "doInBackground", localException);
          Boolean localBoolean1 = Boolean.valueOf(false);
        }
      }
    }

    protected void onPostExecuteA(Boolean paramBoolean)
    {
      CircleMessageDetailFragment.this.updateData();
      if (paramBoolean.booleanValue())
      {
        CircleMessageDetailFragment.this.showLoadingProgressBar(false, 0);
        CircleMessageDetailFragment.this.showLoadingProgressBar(false, 1);
        if (CircleMessageDetailFragment.this.mCheckOriginalLayout != null)
          CircleMessageDetailFragment.this.mCheckOriginalLayout.setVisibility(0);
        CircleMessageDetailFragment.this.mRightButton.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramView)
          {
            CircleMessageDetailFragment.this.doAction(CircleMessageDetailFragment.this.sessionState);
          }
        });
        CircleMessageDetailFragment.this.mRightButton.setClickable(true);
        CircleMessageDetailFragment.this.mRightButton.setVisibility(0);
        if (CircleMessageDetailFragment.this.mListMessages == null)
        {
          CircleMessageDetailFragment.this.showList(false);
          CircleMessageDetailFragment.this.showEmptyNotice(false);
        }
      }
      do
      {
        while (true)
        {
          return;
          int i = CircleMessageDetailFragment.this.mListMessages.size();
          CircleMessageDetailFragment.this.showEmptyNotice(false);
          CircleMessageDetailFragment.this.showList(true);
          int j = CircleMessageModel.getInstance().getReturnDetailTotal();
          int k = CircleMessageModel.getInstance().getRetDetailCount();
          if ((k > 0) && (!CircleMessageDetailFragment.this.isReplyItem))
            CircleMessageDetailFragment.this.mListView.setSelection(k + CircleMessageDetailFragment.this.mnListViewTopId);
          while (true)
          {
            CircleMessageDetailFragment.this.getDetailTask = null;
            if (k == 20)
            {
              if (i >= j)
              {
                CircleMessageDetailFragment.this.setMsgDetailAbtainPreviousContent(0);
                return;
                CircleMessageDetailFragment.this.mListView.setSelection(i - 1);
                CircleMessageDetailFragment.this.isReplyItem = false;
                continue;
              }
              if (i >= j)
                break;
              CircleMessageDetailFragment.this.setMsgDetailAbtainPreviousContent(1);
              return;
            }
          }
        }
        CircleMessageDetailFragment.this.setMsgDetailAbtainPreviousContent(0);
        return;
        if (CircleMessageDetailFragment.this.mMessageDetailCheckMore != null)
          CircleMessageDetailFragment.this.mMessageDetailCheckMore.setVisibility(8);
        Toast.makeText(CircleMessageDetailFragment.this.getActivity(), 2131427914, 0).show();
      }
      while ((CircleMessageDetailFragment.this.mListMessages != null) && (CircleMessageDetailFragment.this.mListMessages.size() != 0));
      CircleMessageDetailFragment.this.finish();
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private class LeaveOrRecoverConversationTask extends KXFragment.KXAsyncTask<String, Void, Integer>
  {
    private LeaveOrRecoverConversationTask()
    {
      super();
    }

    protected Integer doInBackgroundA(String[] paramArrayOfString)
    {
      if ((paramArrayOfString == null) || (paramArrayOfString.length == 0))
        return Integer.valueOf(-1);
      return Integer.valueOf(CircleMsgEngine.getInstance().doCircleDetailMessageLeaveOrJoin(CircleMessageDetailFragment.this.getActivity().getApplicationContext(), CircleMessageDetailFragment.this.mTid, CircleMessageDetailFragment.this.mGid, Integer.valueOf(paramArrayOfString[0]).intValue()));
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      boolean bool;
      if (paramInteger.intValue() == 1)
      {
        CircleMessageDetailFragment localCircleMessageDetailFragment = CircleMessageDetailFragment.this;
        if (CircleMessageDetailFragment.this.sessionState)
        {
          bool = false;
          localCircleMessageDetailFragment.sessionState = bool;
          CircleMessageDetailFragment.this.mRightButton.setOnClickListener(new View.OnClickListener()
          {
            public void onClick(View paramView)
            {
              CircleMessageDetailFragment.this.doAction(CircleMessageDetailFragment.this.sessionState);
            }
          });
          CircleMessageDetailFragment.this.mRightButton.setClickable(true);
          CircleMessageDetailFragment.this.mRightButton.setVisibility(0);
          if (CircleMessageDetailFragment.this.sessionState)
            break label115;
          Toast.makeText(CircleMessageDetailFragment.this.getActivity().getApplicationContext(), CircleMessageDetailFragment.this.getResources().getString(2131427910), 0).show();
        }
      }
      label115: 
      do
      {
        return;
        bool = true;
        break;
        Toast.makeText(CircleMessageDetailFragment.this.getActivity().getApplicationContext(), CircleMessageDetailFragment.this.getResources().getString(2131427915), 0).show();
        return;
        if ((paramInteger.intValue() != -2) && (paramInteger.intValue() != -1))
          continue;
        if (!CircleMessageDetailFragment.this.sessionState)
        {
          Toast.makeText(CircleMessageDetailFragment.this.getActivity().getApplicationContext(), CircleMessageDetailFragment.this.getResources().getString(2131427916), 0).show();
          return;
        }
        Toast.makeText(CircleMessageDetailFragment.this.getActivity().getApplicationContext(), CircleMessageDetailFragment.this.getResources().getString(2131427911), 0).show();
        return;
      }
      while (paramInteger.intValue() != 0);
      Toast.makeText(CircleMessageDetailFragment.this.getActivity().getApplicationContext(), CircleMessageDetailFragment.this.getResources().getString(2131428072), 0).show();
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private static class MessageDetailViewTag
  {
    public ImageView mImgFriendAvatar;
    public ImageView mImgMyAvatar;
    public ImageView mImgSendingIcon;
    public LinearLayout mLayoutFriendAttachmentList;
    public View mLayoutFriendMessage;
    public LinearLayout mLayoutMyAttachmentList;
    public View mLayoutMyMessage;
    public View mLayoutTime;
    public TextView mTxtFriendAttachment;
    public TextView mTxtFriendName;
    public TextView mTxtFriendTime;
    public TextView mTxtMyAttachement;
    public TextView mTxtMyName;
    public TextView mTxtMyTime;
    public TextView mTxtTime;
    public KXIntroView mViewFriendContent;
    public KXIntroView mViewMyContent;

    private MessageDetailViewTag(View paramView)
    {
      this.mLayoutFriendMessage = paramView.findViewById(2131363044);
      this.mLayoutMyMessage = paramView.findViewById(2131363052);
      this.mTxtTime = ((TextView)paramView.findViewById(2131363043));
      this.mTxtFriendName = ((TextView)paramView.findViewById(2131363047));
      this.mTxtFriendTime = ((TextView)paramView.findViewById(2131363048));
      this.mViewFriendContent = ((KXIntroView)paramView.findViewById(2131363049));
      this.mTxtFriendAttachment = ((TextView)paramView.findViewById(2131363050));
      this.mImgFriendAvatar = ((ImageView)paramView.findViewById(2131363045));
      this.mLayoutFriendAttachmentList = ((LinearLayout)paramView.findViewById(2131363051));
      this.mTxtMyName = ((TextView)paramView.findViewById(2131363053));
      this.mTxtMyTime = ((TextView)paramView.findViewById(2131363054));
      this.mViewMyContent = ((KXIntroView)paramView.findViewById(2131363057));
      this.mTxtMyAttachement = ((TextView)paramView.findViewById(2131363058));
      this.mImgMyAvatar = ((ImageView)paramView.findViewById(2131363060));
      this.mLayoutMyAttachmentList = ((LinearLayout)paramView.findViewById(2131363059));
      this.mImgSendingIcon = ((ImageView)paramView.findViewById(2131363056));
      this.mLayoutTime = paramView.findViewById(2131363055);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.CircleMessageDetailFragment
 * JD-Core Version:    0.6.0
 */