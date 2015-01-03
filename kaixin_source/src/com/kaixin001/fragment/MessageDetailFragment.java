package com.kaixin001.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.os.Message;
import android.os.Vibrator;
import android.support.v4.app.FragmentActivity;
import android.text.ClipboardManager;
import android.text.TextUtils;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.adapter.MessageDetailAdapter;
import com.kaixin001.businesslogic.ViewMessageOriginalContent;
import com.kaixin001.engine.CommentEngine;
import com.kaixin001.engine.MessageEngine;
import com.kaixin001.engine.UploadTaskListEngine;
import com.kaixin001.engine.UserCommentEngine;
import com.kaixin001.item.KXLinkInfo;
import com.kaixin001.item.KXUploadTask;
import com.kaixin001.item.MessageDetailInfo;
import com.kaixin001.item.MessageDetailItem;
import com.kaixin001.item.MessageUploadTask;
import com.kaixin001.model.MessageCenterModel;
import com.kaixin001.model.User;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.CrashRecoverUtil;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.StringUtil;
import com.kaixin001.view.KXIntroView.OnClickLinkListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MessageDetailFragment extends BaseFragment
  implements AdapterView.OnItemLongClickListener, KXIntroView.OnClickLinkListener
{
  private static final String COLON_NOUN = ":\n";
  public static final String COMMENT_COUNT = "comment_count";
  protected static final int DLG_SHOW_COPY = 1002;
  public static final int LIST_CALLBACK = 501;
  public static final int LIST_MODE = 2;
  protected static final int MENU_FORWARD_ID = 414;
  protected static final int MENU_HOME_ID = 412;
  protected static final int MENU_HOME_ME_ID = 413;
  protected static final int MENU_REFRESH_ID = 410;
  protected static final int MENU_TO_TOP_ID = 411;
  public static final String MESSAGE_COUNT = "count";
  private static String MESSAGE_TYPE_PRIVATE;
  public static final String NEW_MESSAGE_COUNT = "newcount";
  public static final int NORMAL_MODE = 0;
  public static final int POST_MESSAGE_MODE = 1;
  private static final int SCROLL_TIMER_EVENT = 103;
  public static final int SELF_CALLBACK = 502;
  private static final String SEPERATOR_LINE = "\n--------------------\n";
  private static final String TAG = "MessageDetailActivity";
  public static final String TAG_TASK_FINISHED = "task_finished";
  public static final String TAG_TASK_LAST_REFRESH = "task_last_refresh";
  private final Comparator<MessageDetailItem> cwjComparator = new Comparator()
  {
    public final int compare(MessageDetailItem paramMessageDetailItem1, MessageDetailItem paramMessageDetailItem2)
    {
      int i = paramMessageDetailItem1.getTaskID();
      int j = paramMessageDetailItem2.getTaskID();
      if (i > j)
        return 1;
      if (i < j)
        return -1;
      return 0;
    }
  };
  private MessageDetailAdapter mAdapter;
  private RelativeLayout mCheckOriginalLayout = null;
  private TextView mCircleMsgFrom = null;
  private int mCommentCount = 0;
  private String mDetailFuid = "";
  private MessageDetailInfo mDetailInfo = null;
  private String mDetailThreadId = null;
  private int mDetailType;
  private String mFriendName = null;
  private String mGName = null;
  GetMessageDetailTask mGetMessageDetailTask = null;
  private String mGid = null;
  private View mHeader;
  private LeaveOrRecoverConversationTask mLeaveOrRecoverConversationTask = null;
  private final ArrayList<MessageDetailItem> mListMessages = new ArrayList();
  private String mListTitle = null;
  private ListView mListView = null;
  private Runnable mListViewMoveToLastRowRunable = new Runnable()
  {
    public void run()
    {
      MessageDetailFragment.this.mListView.setSelection(-1 + MessageDetailFragment.this.mListMessages.size());
    }
  };
  private ProgressBar mLoadPreviousProgressBar = null;
  private TextView mLoadPreviousText = null;
  MessageCenterModel mMessageCenterModel = MessageCenterModel.getInstance();
  private LinearLayout mMessageDetailCheckMore = null;
  private int mMode = 0;
  private String mMsgContentForCopy = "";
  private View mReplyLine;
  private ImageView mRightButton = null;
  private ArrayList<MessageDetailItem> mServerMessageDetailList = new ArrayList();
  private String mTid = null;
  private int mnListViewTopId = 0;
  private int mnMsgCount = 0;
  private int mnNewMsgCount = 0;
  private int mnReturnNum = 0;
  private String msCheckOriginalContent = null;
  private String msToMyContent = "对我的";
  private TextView mtvCheckOriginal = null;
  private String mtype;
  ViewMessageOriginalContent viewMessageOriginalContent = null;

  static
  {
    if (!MessageDetailFragment.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      $assertionsDisabled = bool;
      MESSAGE_TYPE_PRIVATE = "1";
      return;
    }
  }

  private void GotoReplyMessageActivity()
  {
    int i = this.mDetailInfo.getDetailType();
    Intent localIntent = new Intent(getActivity(), InputFragment.class);
    Bundle localBundle = new Bundle();
    if ((i == 201) || (i == 202))
    {
      String str1 = this.mDetailInfo.getId();
      localBundle.putInt("mode", 5);
      localBundle.putString("mid", str1);
      localIntent.putExtras(localBundle);
      AnimationUtil.startFragmentForResult(this, localIntent, 5, 3);
      return;
    }
    if ((i == 204) || (i == 206))
    {
      String str2 = User.getInstance().getUID();
      String str3 = this.mDetailInfo.getId();
      localBundle.putInt("mode", 3);
      localBundle.putString("thread_cid", str3);
      localBundle.putString("fuid", str2);
      localBundle.putString("mtype", this.mtype);
      localIntent.putExtras(localBundle);
      AnimationUtil.startFragmentForResult(this, localIntent, 3, 3);
      return;
    }
    if (i == 206)
    {
      String str6 = User.getInstance().getUID();
      String str7 = this.mDetailInfo.getId();
      localBundle.putInt("mode", 3);
      localBundle.putString("thread_cid", str7);
      localBundle.putString("fuid", str6);
      localIntent.putExtras(localBundle);
      AnimationUtil.startFragmentForResult(this, localIntent, 3, 3);
      return;
    }
    String str4 = User.getInstance().getUID();
    String str5 = this.mDetailInfo.getId();
    localBundle.putInt("mode", 3);
    localBundle.putString("thread_cid", str5);
    localBundle.putString("fuid", str4);
    localBundle.putString("mtype", this.mtype);
    localIntent.putExtras(localBundle);
    AnimationUtil.startFragmentForResult(this, localIntent, 3, 3);
  }

  private void SetNameListDesView()
  {
    if (this.mDetailInfo == null)
      break label7;
    label7: JSONArray localJSONArray2;
    JSONArray localJSONArray3;
    String str1;
    int i;
    int m;
    while (true)
    {
      return;
      if ((this.mDetailInfo.getDetailType() != 201) && (this.mDetailInfo.getDetailType() != 202))
        continue;
      JSONArray localJSONArray1 = this.mDetailInfo.getFuids();
      localJSONArray2 = this.mDetailInfo.getFnames();
      localJSONArray3 = this.mDetailInfo.getDetailList();
      if ((localJSONArray1 == null) || (localJSONArray2 == null) || (localJSONArray3 == null))
        break;
      str1 = User.getInstance().getRealName();
      i = this.mDetailInfo.getUserNum();
      if (i == 2)
      {
        m = 0;
        label96: if (m >= localJSONArray2.length())
          break label473;
        try
        {
          String str4 = localJSONArray2.getString(m);
          if (!str4.equals(str1))
          {
            this.mFriendName = str4;
            if (this.mFriendName.length() <= 6)
              continue;
            this.mFriendName = (this.mFriendName.substring(0, 6) + "...");
            return;
          }
        }
        catch (JSONException localJSONException2)
        {
          localJSONException2.printStackTrace();
        }
      }
    }
    int k;
    int j;
    while (true)
    {
      String str2;
      Resources localResources;
      StringBuffer localStringBuffer;
      try
      {
        str2 = localJSONArray3.getJSONObject(0).getString("fname");
        localResources = getResources();
        localStringBuffer = new StringBuffer();
        if (str2.equals(str1))
        {
          localStringBuffer.append(localResources.getString(2131427565)).append(localResources.getString(2131427568));
          if (localJSONArray2.length() != 1)
            break label475;
          localStringBuffer.append(localResources.getString(2131427566));
          break label475;
          if (k < localJSONArray2.length())
            break label404;
          if (j >= i)
            continue;
          localStringBuffer.append(StringUtil.replaceTokenWith(localResources.getString(2131427569), "*", String.valueOf(i)));
          localStringBuffer.append(localResources.getString(2131427567));
          TextView localTextView = (TextView)this.mHeader.findViewById(2131363042);
          localTextView.setText(localStringBuffer);
          localTextView.setVisibility(0);
          return;
        }
      }
      catch (JSONException localJSONException1)
      {
        localJSONException1.printStackTrace();
        return;
      }
      localStringBuffer.append(str2).append(localResources.getString(2131427568)).append(localResources.getString(2131427565));
      j = 2;
      break label478;
      label404: String str3 = localJSONArray2.getString(k);
      if ((str3.equals(str1)) || (str3.equals(str2)))
        break label484;
      if (j == 1)
      {
        localStringBuffer.append(str3);
        break label490;
      }
      localStringBuffer.append(",").append(str3);
      break label490;
      m++;
      break label96;
      label473: break;
      label475: j = 1;
      label478: k = 0;
    }
    while (true)
    {
      label484: k++;
      break;
      label490: j++;
    }
  }

  private void SetTypeDesView()
  {
    if (this.mDetailInfo == null);
    int i;
    do
    {
      do
        return;
      while ((this.mDetailInfo.getDetailType() == 201) || (this.mDetailInfo.getDetailType() == 202));
      i = this.mDetailInfo.getType();
    }
    while ((!MESSAGE_TYPE_PRIVATE.equals(this.mtype)) && (i != 1));
    TextView localTextView = (TextView)this.mHeader.findViewById(2131363042);
    localTextView.setTextSize(20.0F);
    localTextView.setVisibility(0);
    localTextView.setText(2131427553);
    ((Button)this.mReplyLine.findViewById(2131363038)).setText(2131427654);
  }

  private void cancelTask()
  {
    if ((this.mGetMessageDetailTask != null) && (this.mGetMessageDetailTask.isCancelled()) && (this.mGetMessageDetailTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.mGetMessageDetailTask.cancel(true);
      this.mGetMessageDetailTask = null;
    }
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
    if (paramArrayList == null)
      return;
    if (isMessageThread());
    for (int i = 5; ; i = 3)
    {
      UploadTaskListEngine localUploadTaskListEngine = UploadTaskListEngine.getInstance();
      for (int j = 0; j < paramArrayList.size(); j++)
      {
        KXUploadTask localKXUploadTask = (KXUploadTask)paramArrayList.get(j);
        if (((localKXUploadTask.getTaskType() != 5) && (localKXUploadTask.getTaskType() != 4)) || (!(localKXUploadTask instanceof MessageUploadTask)))
          continue;
        MessageUploadTask localMessageUploadTask = (MessageUploadTask)localKXUploadTask;
        if ((localMessageUploadTask.getThreadId() == null) || (!localMessageUploadTask.getThreadId().equals(this.mDetailThreadId)) || (localMessageUploadTask.getMode() != i) || (localMessageUploadTask.getTaskStatus() != 2))
          continue;
        localUploadTaskListEngine.deleteTask(localMessageUploadTask);
      }
      break;
    }
  }

  private void doAction(String paramString)
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
      if (!paramString.equals("0"))
        break label202;
      if (this.msCheckOriginalContent == null)
        break label171;
      arrayOfString3 = new String[3];
      arrayOfString3[0] = getString(2131427562);
      arrayOfString3[1] = this.msCheckOriginalContent;
      arrayOfString3[2] = getString(2131427905);
      label132: String[] arrayOfString4 = arrayOfString3;
      DialogUtil.showSelectListDlg(getActivity(), 2131427907, arrayOfString3, new DialogInterface.OnClickListener(localHashMap, arrayOfString4)
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          switch (((Integer)this.val$dialogString.get(this.val$itemString[paramInt])).intValue())
          {
          default:
            return;
          case 0:
            MessageDetailFragment.this.GotoReplyMessageActivity();
            return;
          case 1:
            MessageDetailFragment.this.turnToOriginalContent();
            return;
          case 2:
          }
          MessageDetailFragment.this.recoverConversation();
        }
      }
      , 1);
    }
    label171: label202: 
    do
    {
      return;
      str = this.msCheckOriginalContent;
      break;
      arrayOfString3 = new String[2];
      arrayOfString3[0] = getString(2131427562);
      arrayOfString3[1] = getString(2131427905);
      break label132;
      if (!paramString.equals("1"))
        continue;
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
            switch (((Integer)this.val$dialogString.get(this.val$itemString[paramInt])).intValue())
            {
            default:
              return;
            case 0:
              MessageDetailFragment.this.GotoReplyMessageActivity();
              return;
            case 1:
              MessageDetailFragment.this.turnToOriginalContent();
              return;
            case 2:
            }
            MessageDetailFragment.this.leaveConversation();
          }
        }
        , 1);
        return;
        arrayOfString1 = new String[2];
        arrayOfString1[0] = getString(2131427562);
        arrayOfString1[1] = getString(2131427906);
      }
    }
    while (!paramString.equals(""));
    int i = this.mDetailInfo.getDetailType();
    if ((i == 201) || (i == 202))
    {
      DialogUtil.showSelectListDlg(getActivity(), 2131427907, getResources().getStringArray(2131492893), new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          switch (paramInt)
          {
          default:
            return;
          case 0:
            MessageDetailFragment.this.GotoReplyMessageActivity();
            return;
          case 1:
          }
          MessageDetailFragment.this.forwardConversation();
        }
      }
      , 1);
      return;
    }
    GotoReplyMessageActivity();
  }

  private void doGetData(boolean paramBoolean)
  {
    cancelTask();
    if ((this.mDetailThreadId == null) && (isCircleThread()));
    while (true)
    {
      return;
      if (!paramBoolean)
      {
        setTitleBar();
        this.mRightButton.setVisibility(8);
      }
      while (super.checkNetworkAndHint(true))
      {
        deleteFinishedDetailInfo();
        if (!paramBoolean)
          break label73;
        showLoadingProgressBar(true, 1);
        downloadMessage("0");
        return;
        hideFooter();
      }
    }
    label73: showLoadingProgressBar(true, 0);
    loadMoreMsg();
  }

  private void downloadMessage(String paramString)
  {
    if ((this.mGetMessageDetailTask != null) && (this.mGetMessageDetailTask.isCancelled()) && (this.mGetMessageDetailTask.getStatus() != AsyncTask.Status.FINISHED))
      return;
    if (TextUtils.isEmpty(paramString))
      paramString = "0";
    this.mGetMessageDetailTask = new GetMessageDetailTask(null);
    String[] arrayOfString = new String[3];
    if (isCircleThread())
    {
      arrayOfString[0] = this.mGid;
      arrayOfString[1] = this.mTid;
      arrayOfString[2] = paramString;
    }
    while (true)
    {
      this.mGetMessageDetailTask.execute(arrayOfString);
      return;
      arrayOfString[0] = this.mDetailThreadId;
      arrayOfString[1] = this.mDetailFuid;
      arrayOfString[2] = paramString;
    }
  }

  private void finishActivity()
  {
    Intent localIntent;
    if (this.mMode == 2)
    {
      localIntent = new Intent();
      localIntent.putExtra("comment_count", this.mCommentCount);
      if (this.mListMessages != null)
      {
        localIntent.putExtra("count", this.mnMsgCount);
        if (this.mnNewMsgCount > this.mListMessages.size())
        {
          localIntent.putExtra("newcount", this.mnNewMsgCount - this.mListMessages.size());
          setResult(-1, localIntent);
          finishFragment(501);
        }
      }
    }
    while (true)
    {
      finish();
      return;
      localIntent.putExtra("newcount", "0");
      break;
      localIntent.putExtra("newcount", this.mnNewMsgCount);
      localIntent.putExtra("count", this.mnMsgCount);
      break;
      if (this.mMode != 1)
        continue;
      setResult(-1, null);
      finishFragment(502);
    }
  }

  private void forwardConversation()
  {
    if (this.mListMessages == null)
      return;
    StringBuffer localStringBuffer = new StringBuffer();
    int i = 0;
    while (true)
    {
      if (i >= this.mListMessages.size())
      {
        Intent localIntent = new Intent(getActivity(), PostMessageFragment.class);
        localIntent.putExtra("PreviousActivityName", "MessageDetailActivity");
        localIntent.putExtra("MessageContent", localStringBuffer.toString());
        startFragment(localIntent, true, 1);
        return;
      }
      try
      {
        MessageDetailItem localMessageDetailItem = (MessageDetailItem)this.mListMessages.get(i);
        if (localStringBuffer.length() > 0)
          localStringBuffer.append("\n--------------------\n");
        localStringBuffer.append(localMessageDetailItem.getFname()).append(":\n").append(localMessageDetailItem.getAbscont());
        i++;
      }
      catch (Exception localException)
      {
        while (true)
          localException.printStackTrace();
      }
    }
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

  private boolean hasOrginalContent(String paramString)
  {
    if (paramString == null);
    do
      return false;
    while ((!paramString.equals("1018")) && (!paramString.equals("2")) && (!paramString.equals("1")) && (!paramString.equals("1088")) && (!isCircleContent(paramString)));
    return true;
  }

  private boolean isCircleContent(String paramString)
  {
    return (paramString.equals("回复我")) || (paramString.equals("我回复"));
  }

  private boolean isCircleThread()
  {
    return (this.mDetailType == 207) || (this.mDetailType == 208);
  }

  private boolean isCommentThread()
  {
    return (this.mDetailType == 204) || (this.mDetailType == 203);
  }

  private boolean isMessageThread()
  {
    return (this.mDetailType == 201) || (this.mDetailType == 202);
  }

  private void leaveConversation()
  {
    String[] arrayOfString = { "1" };
    this.mLeaveOrRecoverConversationTask = new LeaveOrRecoverConversationTask(null);
    this.mLeaveOrRecoverConversationTask.execute(arrayOfString);
  }

  private void loadMoreMsg()
  {
    if (this.mListMessages != null)
      setMsgDetailAbtainPreviousContent(2);
    if ((this.mGetMessageDetailTask != null) && (this.mGetMessageDetailTask.isCancelled()) && (this.mGetMessageDetailTask.getStatus() != AsyncTask.Status.FINISHED))
      return;
    if ((this.mListMessages != null) && (this.mListMessages.size() > 0));
    for (String str = ((MessageDetailItem)this.mListMessages.get(0)).getMessageId(); ; str = "0")
    {
      downloadMessage(str);
      return;
    }
  }

  private void recoverConversation()
  {
    String[] arrayOfString = { "0" };
    this.mLeaveOrRecoverConversationTask = new LeaveOrRecoverConversationTask(null);
    this.mLeaveOrRecoverConversationTask.execute(arrayOfString);
  }

  private void setMessageDatafromUpQueue(ArrayList<MessageDetailItem> paramArrayList, ArrayList<KXUploadTask> paramArrayList1)
  {
    if ((paramArrayList1 == null) || (paramArrayList == null))
      return;
    while (true)
    {
      try
      {
        if (!isMessageThread())
          continue;
        int i = 5;
        break label237;
        if (j >= paramArrayList1.size())
          break;
        KXUploadTask localKXUploadTask = (KXUploadTask)paramArrayList1.get(j);
        if ((localKXUploadTask == null) || (!(localKXUploadTask instanceof MessageUploadTask)) || ((localKXUploadTask.getTaskType() != 5) && (localKXUploadTask.getTaskType() != 4)))
          continue;
        MessageUploadTask localMessageUploadTask = (MessageUploadTask)localKXUploadTask;
        String str = localMessageUploadTask.getThreadId();
        if ((str == null) || (!str.equals(this.mDetailThreadId)) || (localMessageUploadTask.getMode() != i))
          continue;
        long l = Calendar.getInstance().getTimeInMillis() / 1000L;
        MessageDetailItem localMessageDetailItem = new MessageDetailItem();
        localMessageDetailItem.setAbscont(localMessageUploadTask.getContent());
        localMessageDetailItem.setCtime(Long.valueOf(l));
        localMessageDetailItem.setFlogo(User.getInstance().getLogo());
        localMessageDetailItem.setFuid(User.getInstance().getUID());
        localMessageDetailItem.setFname(getResources().getString(2131427565));
        localMessageDetailItem.setStatus(localMessageUploadTask.getTaskStatus());
        localMessageDetailItem.setTaskID(localMessageUploadTask.getTaskId());
        paramArrayList.add(localMessageDetailItem);
        j++;
        continue;
        i = 3;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return;
      }
      label237: int j = 0;
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
    View localView = findViewById(2131363037);
    if (paramBoolean)
    {
      localView.setVisibility(0);
      return;
    }
    localView.setVisibility(8);
  }

  private void turnToOriginalContent()
  {
    if (!checkNetworkAndHint(true))
      return;
    if (this.viewMessageOriginalContent == null)
      this.viewMessageOriginalContent = new ViewMessageOriginalContent(getActivity(), this);
    this.viewMessageOriginalContent.turnToOriginalContent(this.mDetailInfo, this.mDetailType, this.mDetailFuid);
  }

  protected void addOneReply(String paramString)
  {
    updateData();
    this.mCommentCount = (1 + this.mCommentCount);
    this.mnMsgCount = (1 + this.mnMsgCount);
    this.mListView.postDelayed(this.mListViewMoveToLastRowRunable, 0L);
    this.mAdapter.startSendingAnimation();
  }

  protected boolean handleMessage(Message paramMessage)
  {
    if (paramMessage == null)
      return false;
    if (paramMessage.what == 10001)
      updateData();
    do
      return true;
    while (paramMessage.what == 103);
    return super.handleMessage(paramMessage);
  }

  protected void hideFooter()
  {
    this.mReplyLine.setVisibility(8);
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if (paramInt2 == -1)
    {
      if (paramInt1 != 3)
        break label29;
      addOneReply(paramIntent.getStringExtra("content"));
    }
    label29: 
    do
      return;
    while (paramInt1 != 5);
    addOneReply(paramIntent.getStringExtra("content"));
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
          ((ClipboardManager)MessageDetailFragment.this.getActivity().getSystemService("clipboard")).setText(MessageDetailFragment.this.mMsgContentForCopy);
        }
      }
      , 1);
    return super.onCreateDialog(paramInt);
  }

  public void onCreateOptionsMenu(Menu paramMenu, MenuInflater paramMenuInflater)
  {
    super.onCreateOptionsMenu(paramMenu, paramMenuInflater);
    paramMenu.add(0, 411, 0, 2131427698).setIcon(2130838609);
    if (this.mDetailInfo != null)
    {
      int i = this.mDetailInfo.getDetailType();
      if ((i == 201) || (i == 202))
        paramMenu.add(0, 414, 0, 2131427855).setIcon(2130838606);
    }
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
      label7: this.mAdapter.stopSendingAnimation();
      getActivity().removeDialog(1002);
      UploadTaskListEngine.getInstance().unRegister(this.mHandler);
      cancelTask();
      if ((this.mGetMessageDetailTask != null) && (this.mGetMessageDetailTask.getStatus() != AsyncTask.Status.FINISHED))
      {
        this.mGetMessageDetailTask.cancel(true);
        if (this.mDetailType != 201)
          break label88;
        MessageEngine.getInstance().cancel();
      }
      while (true)
      {
        super.onDestroy();
        return;
        label88: if (this.mDetailType == 202)
        {
          MessageEngine.getInstance().cancel();
          continue;
        }
        if (this.mDetailType == 203)
        {
          CommentEngine.getInstance().cancel();
          continue;
        }
        if (this.mDetailType == 204)
        {
          CommentEngine.getInstance().cancel();
          continue;
        }
        if (this.mDetailType == 205)
        {
          UserCommentEngine.getInstance().cancel();
          continue;
        }
        if (this.mDetailType != 206)
          continue;
        UserCommentEngine.getInstance().cancel();
      }
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
    if (paramInt == 4)
    {
      finishActivity();
      return true;
    }
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
      IntentUtil.showMyHomeFragment(this);
      return true;
    case 414:
    }
    forwardConversation();
    return false;
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    UploadTaskListEngine.getInstance().register(this.mHandler);
    Bundle localBundle = getArguments();
    if (localBundle != null)
    {
      this.mDetailThreadId = localBundle.getString("id");
      this.mDetailType = localBundle.getInt("type");
      this.mtype = localBundle.getString("mtype");
      String str4 = localBundle.getString("fuid");
      this.mMode = localBundle.getInt("mode");
      this.mnMsgCount = localBundle.getInt("count");
      this.mnNewMsgCount = localBundle.getInt("newcount");
      if (str4 != null)
        this.mDetailFuid = str4;
      this.mGid = localBundle.getString("gid");
      this.mTid = localBundle.getString("tid");
      this.mGName = localBundle.getString("gname");
      this.mListTitle = localBundle.getString("listtitle");
    }
    this.mListView = ((ListView)findViewById(2131363035));
    this.mHeader = getActivity().getLayoutInflater().inflate(2130903222, null);
    this.mReplyLine = getActivity().getLayoutInflater().inflate(2130903221, null);
    if ((isCommentThread()) || (isCircleThread()))
    {
      String str1 = localBundle.getString("appid");
      if (hasOrginalContent(str1))
      {
        if (!isCircleContent(str1))
          break label554;
        this.mCheckOriginalLayout = ((RelativeLayout)getActivity().getLayoutInflater().inflate(2130903071, null));
        this.mtvCheckOriginal = ((TextView)this.mCheckOriginalLayout.findViewById(2131362026));
        this.mtvCheckOriginal.setText(this.mListTitle);
        this.mCircleMsgFrom = ((TextView)this.mCheckOriginalLayout.findViewById(2131362027));
        this.mCircleMsgFrom.setText("来自 " + this.mGName);
        this.mCheckOriginalLayout.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramView)
          {
            MessageDetailFragment.this.turnToOriginalContent();
          }
        });
      }
    }
    while (true)
    {
      this.mListView.addHeaderView(this.mHeader);
      this.mListView.addFooterView(this.mReplyLine);
      this.mAdapter = new MessageDetailAdapter(this, this.mHandler, this.mListView, this.mListMessages);
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
            MessageDetailFragment.this.loadMoreMsg();
          }
        });
        ((Button)this.mReplyLine.findViewById(2131363038)).setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramView)
          {
            MessageDetailFragment.this.GotoReplyMessageActivity();
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
            MessageDetailFragment.this.mnListViewTopId = paramAbsListView.getFirstVisiblePosition();
          }
        });
        this.mListView.setVisibility(8);
        setTitleBar();
        doGetData(true);
        return;
        label554: this.mCheckOriginalLayout = ((RelativeLayout)getActivity().getLayoutInflater().inflate(2130903232, null));
        this.mtvCheckOriginal = ((TextView)this.mCheckOriginalLayout.findViewById(2131363099));
        if (!User.getInstance().getUID().equals(this.mDetailFuid))
        {
          String str3 = localBundle.getString("fname");
          this.msToMyContent = this.msToMyContent.replace("我", str3);
        }
        String str2 = this.msToMyContent + localBundle.getString("appname") + "\"" + localBundle.getString("apphtml") + "\"";
        this.mtvCheckOriginal.setText(str2);
        this.mCheckOriginalLayout.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramView)
          {
            MessageDetailFragment.this.turnToOriginalContent();
          }
        });
        this.mCheckOriginalLayout.setVisibility(0);
        this.mListView.addHeaderView(this.mCheckOriginalLayout);
      }
      catch (Exception localException)
      {
        while (true)
          KXLog.e("Messagedetail", localException.toString());
      }
    }
  }

  public void requestFinish()
  {
    finishActivity();
  }

  protected void setTitleBar()
  {
    ImageView localImageView = (ImageView)findViewById(2131362914);
    localImageView.setVisibility(0);
    localImageView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        MessageDetailFragment.this.finishActivity();
      }
    });
    this.mRightButton = ((ImageView)findViewById(2131362928));
    this.mRightButton.setVisibility(8);
    ((ImageView)findViewById(2131362919)).setVisibility(8);
    TextView localTextView = (TextView)findViewById(2131362920);
    localTextView.setVisibility(0);
    int j;
    if (this.mDetailType == 201)
      if (this.mFriendName != null)
      {
        localTextView.setText(this.mFriendName);
        j = 2130839005;
      }
    while (true)
    {
      if (this.mMode != 0);
      this.mRightButton.setImageResource(j);
      return;
      localTextView.setText(getResources().getText(2131427551));
      break;
      if (this.mDetailType == 202)
      {
        if (this.mFriendName != null)
          localTextView.setText(this.mFriendName);
        while (true)
        {
          j = 2130839005;
          break;
          localTextView.setText(getResources().getText(2131427552));
        }
      }
      if (this.mDetailType == 203)
      {
        localTextView.setText(getResources().getText(2131427539));
        j = 2130839005;
        continue;
      }
      if (this.mDetailType == 204)
      {
        localTextView.setText(getResources().getText(2131427540));
        j = 2130839005;
        continue;
      }
      if (this.mDetailType == 205)
      {
        localTextView.setText(getResources().getText(2131427541));
        j = 2130839056;
        continue;
      }
      if (this.mDetailType == 206)
      {
        localTextView.setText(getResources().getText(2131427542));
        j = 2130839056;
        continue;
      }
      if (this.mDetailType == 207)
      {
        localTextView.setText(getResources().getText(2131428063));
        j = 2130839005;
        continue;
      }
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

  protected void updateData()
  {
    this.mListMessages.clear();
    this.mDetailInfo = this.mMessageCenterModel.getActiveMesasgeDetail();
    if (this.mDetailType == 202)
      if (this.mnReturnNum > 0)
      {
        this.mServerMessageDetailList.clear();
        this.mServerMessageDetailList.addAll(this.mMessageCenterModel.getMessageDetailList());
      }
    for (ArrayList localArrayList = this.mServerMessageDetailList; ; localArrayList = this.mMessageCenterModel.getMessageDetailList())
    {
      if (localArrayList != null)
        this.mListMessages.addAll(localArrayList);
      this.mListMessages.addAll(getDetailListFromUpQueue());
      if (this.mAdapter != null)
        this.mAdapter.notifyDataSetChanged();
      return;
      this.mServerMessageDetailList = null;
      break;
    }
  }

  private class GetMessageDetailTask extends KXFragment.KXAsyncTask<String, Void, Integer>
  {
    int resultCode;

    private GetMessageDetailTask()
    {
      super();
    }

    protected Integer doInBackgroundA(String[] paramArrayOfString)
    {
      if (paramArrayOfString.length < 2)
        return Integer.valueOf(0);
      try
      {
        String str1 = paramArrayOfString[0];
        String str2 = paramArrayOfString[1];
        String str3 = paramArrayOfString[2];
        if (str3.equals("0"))
          MessageCenterModel.getInstance().clearMessageDetailList();
        if (MessageDetailFragment.this.mDetailType == 201)
        {
          if (str3.equals("0"))
            return Integer.valueOf(MessageEngine.getInstance().doGetMessageDetail(MessageDetailFragment.this.getActivity().getApplicationContext(), str1, true, str3, -1));
          return Integer.valueOf(MessageEngine.getInstance().doGetMoreMessageDetail(MessageDetailFragment.this.getActivity().getApplicationContext(), str1, true, str3));
        }
        if (MessageDetailFragment.this.mDetailType == 202)
        {
          if (str3.equals("0"))
            return Integer.valueOf(MessageEngine.getInstance().doGetMessageDetail(MessageDetailFragment.this.getActivity().getApplicationContext(), str1, false, str3, -1));
          return Integer.valueOf(MessageEngine.getInstance().doGetMoreMessageDetail(MessageDetailFragment.this.getActivity().getApplicationContext(), str1, false, str3));
        }
        if (MessageDetailFragment.this.mDetailType == 203)
          return Integer.valueOf(CommentEngine.getInstance().doGetCommentDetail(MessageDetailFragment.this.getActivity().getApplicationContext(), str1, str3, -1));
        if (MessageDetailFragment.this.mDetailType == 204)
          return Integer.valueOf(CommentEngine.getInstance().doGetReplyCommentDetail(MessageDetailFragment.this.getActivity().getApplicationContext(), str1, str2, str3, -1));
        if (MessageDetailFragment.this.mDetailType == 205)
        {
          this.resultCode = UserCommentEngine.getInstance().doGetUserCommentDetail(MessageDetailFragment.this.getActivity().getApplicationContext(), str1, str3, -1);
          return Integer.valueOf(this.resultCode);
        }
        if (MessageDetailFragment.this.mDetailType == 206)
        {
          this.resultCode = UserCommentEngine.getInstance().doGetSentUserCommentDetail(MessageDetailFragment.this.getActivity().getApplicationContext(), str1, str3, -1);
          return Integer.valueOf(this.resultCode);
        }
        if (MessageDetailFragment.this.isCircleThread())
          return Integer.valueOf(0);
        Integer localInteger = Integer.valueOf(0);
        return localInteger;
      }
      catch (Exception localException)
      {
        KXLog.e("MessageDetailActivity", "onPostExecute", localException);
      }
      return Integer.valueOf(0);
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      while (true)
      {
        String str;
        TextView localTextView;
        try
        {
          if (paramInteger.intValue() != 1)
            continue;
          MessageDetailFragment.this.mnReturnNum = MessageCenterModel.getInstance().getReturnNum();
          MessageDetailFragment.this.updateData();
          if (paramInteger.intValue() != 1)
            break label878;
          MessageDetailFragment.this.showLoadingProgressBar(false, 0);
          MessageDetailFragment.this.showLoadingProgressBar(false, 1);
          MessageDetailFragment.this.SetNameListDesView();
          str = MessageDetailFragment.this.mDetailInfo.getmAppid();
          if ((TextUtils.isEmpty(str)) || (MessageDetailFragment.this.mCheckOriginalLayout == null))
            break label401;
          if (MessageDetailFragment.this.hasOrginalContent(str))
          {
            MessageDetailFragment.this.mCheckOriginalLayout.setVisibility(0);
            if (TextUtils.isEmpty(str))
              break label524;
            if ((!str.equals("1018")) && (!str.equals("2")) && (!str.equals("1")) && (!str.equals("1088")) && (!str.equals("6")))
              break label513;
            MessageDetailFragment.this.mMessageDetailCheckMore.setVisibility(0);
            if (!str.equals("1018"))
              break label426;
            MessageDetailFragment.this.msCheckOriginalContent = MessageDetailFragment.this.getString(2131427896);
            localTextView = (TextView)MessageDetailFragment.this.findViewById(2131362920);
            localTextView.setVisibility(0);
            if (MessageDetailFragment.this.mDetailType != 201)
              break label556;
            if (MessageDetailFragment.this.mFriendName == null)
              break label535;
            localTextView.setText(MessageDetailFragment.this.mFriendName);
            MessageDetailFragment.this.mRightButton.setOnClickListener(new View.OnClickListener()
            {
              public void onClick(View paramView)
              {
                MessageDetailFragment.this.doAction(MessageDetailFragment.this.mDetailInfo.getmAtstate());
              }
            });
            MessageDetailFragment.this.mRightButton.setClickable(true);
            if ((MessageDetailFragment.this.mDetailType != 205) && (MessageDetailFragment.this.mDetailType != 206))
              break label615;
            MessageDetailFragment.this.mRightButton.setVisibility(8);
            MessageDetailFragment.this.SetTypeDesView();
            if (MessageDetailFragment.this.mListMessages != null)
              break;
            MessageDetailFragment.this.showList(false);
            MessageDetailFragment.this.showEmptyNotice(false);
            return;
            MessageDetailFragment.this.mnReturnNum = 0;
            continue;
          }
        }
        catch (Exception localException)
        {
          KXLog.e("MessageDetailActivity", "onPostExecute", localException);
          return;
        }
        MessageDetailFragment.this.mCheckOriginalLayout.setVisibility(8);
        continue;
        label401: if (MessageDetailFragment.this.mCheckOriginalLayout == null)
          continue;
        MessageDetailFragment.this.mCheckOriginalLayout.setVisibility(8);
        continue;
        label426: if (str.equals("2"))
        {
          MessageDetailFragment.this.msCheckOriginalContent = MessageDetailFragment.this.getString(2131427894);
          continue;
        }
        if (str.equals("1"))
        {
          MessageDetailFragment.this.msCheckOriginalContent = MessageDetailFragment.this.getString(2131427895);
          continue;
        }
        if (!str.equals("1088"))
          continue;
        MessageDetailFragment.this.msCheckOriginalContent = MessageDetailFragment.this.getString(2131427893);
        continue;
        label513: MessageDetailFragment.this.msCheckOriginalContent = null;
        continue;
        label524: MessageDetailFragment.this.msCheckOriginalContent = null;
        continue;
        label535: localTextView.setText(MessageDetailFragment.this.getResources().getText(2131427551));
        continue;
        label556: if (MessageDetailFragment.this.mDetailType != 202)
          continue;
        if (MessageDetailFragment.this.mFriendName != null)
        {
          localTextView.setText(MessageDetailFragment.this.mFriendName);
          continue;
        }
        localTextView.setText(MessageDetailFragment.this.getResources().getText(2131427552));
        continue;
        label615: MessageDetailFragment.this.mRightButton.setVisibility(0);
      }
      if (MessageDetailFragment.this.mListMessages.size() == 0)
      {
        MessageDetailFragment.this.showEmptyNotice(true);
        MessageDetailFragment.this.showList(false);
        return;
      }
      int j = MessageDetailFragment.this.mListMessages.size();
      MessageDetailFragment.this.showEmptyNotice(false);
      MessageDetailFragment.this.showList(true);
      if ((MessageDetailFragment.this.mnNewMsgCount > 10) && (j > 10))
        MessageDetailFragment.this.mListView.setSelection(j - 1);
      while (true)
      {
        MessageDetailFragment.this.mGetMessageDetailTask = null;
        if (j >= MessageDetailFragment.this.mnNewMsgCount)
          break;
        MessageDetailFragment.this.setMsgDetailAbtainPreviousContent(2);
        MessageDetailFragment.this.loadMoreMsg();
        return;
        if (MessageCenterModel.getInstance().getReturnNum() > 0)
        {
          MessageDetailFragment.this.mListView.setSelection(MessageDetailFragment.this.mnListViewTopId + MessageCenterModel.getInstance().getReturnNum());
          continue;
        }
        MessageDetailFragment.this.mListView.setSelection(j - 1);
      }
      if (MessageDetailFragment.this.mnReturnNum == 10)
      {
        if (((MessageDetailItem)MessageDetailFragment.this.mListMessages.get(0)).getMessageId().equals(MessageDetailFragment.this.mDetailThreadId))
        {
          MessageDetailFragment.this.setMsgDetailAbtainPreviousContent(0);
          return;
        }
        MessageDetailFragment.this.setMsgDetailAbtainPreviousContent(1);
        return;
      }
      MessageDetailFragment.this.setMsgDetailAbtainPreviousContent(0);
      return;
      label878: if (MessageDetailFragment.this.mMessageDetailCheckMore != null)
        MessageDetailFragment.this.mMessageDetailCheckMore.setVisibility(8);
      int i = 2131427914;
      if (((MessageDetailFragment.this.mDetailType == 205) || (MessageDetailFragment.this.mDetailType == 206)) && (this.resultCode == 2))
        i = 2131427912;
      Toast.makeText(MessageDetailFragment.this.getActivity(), i, 0).show();
      if ((MessageDetailFragment.this.mListMessages == null) || (MessageDetailFragment.this.mListMessages.size() == 0))
        MessageDetailFragment.this.finish();
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private class LeaveOrRecoverConversationTask extends AsyncTask<String, Void, Boolean>
  {
    private LeaveOrRecoverConversationTask()
    {
    }

    protected Boolean doInBackground(String[] paramArrayOfString)
    {
      if ((paramArrayOfString == null) || (paramArrayOfString.length == 0))
        return Boolean.valueOf(false);
      boolean bool2;
      if (paramArrayOfString[0].equals("1"))
        bool2 = true;
      while (true)
      {
        return Boolean.valueOf(MessageEngine.getInstance().doMessageLeaveOrJoin(MessageDetailFragment.this.getActivity().getApplicationContext(), MessageDetailFragment.this.mDetailInfo.getId(), bool2));
        boolean bool1 = paramArrayOfString[0].equals("0");
        bool2 = false;
        if (!bool1)
          continue;
        bool2 = false;
      }
    }

    protected void onPostExecute(Boolean paramBoolean)
    {
      if (paramBoolean.booleanValue())
      {
        if (MessageDetailFragment.this.mDetailInfo.getmAtstate().equals("1"));
        for (String str = "0"; ; str = "1")
        {
          MessageDetailFragment.this.mDetailInfo.setmAtstate(str);
          MessageDetailFragment.this.mRightButton.setOnClickListener(new View.OnClickListener()
          {
            public void onClick(View paramView)
            {
              MessageDetailFragment.this.doAction(MessageDetailFragment.this.mDetailInfo.getmAtstate());
            }
          });
          MessageDetailFragment.this.mRightButton.setClickable(true);
          MessageDetailFragment.this.mRightButton.setVisibility(0);
          if (!str.equals("0"))
            break;
          Toast.makeText(MessageDetailFragment.this.getActivity(), MessageDetailFragment.this.getResources().getString(2131427910), 0).show();
          return;
        }
        Toast.makeText(MessageDetailFragment.this.getActivity(), MessageDetailFragment.this.getResources().getString(2131427915), 0).show();
        return;
      }
      if (MessageDetailFragment.this.mDetailInfo.getmAtstate().equals("0"))
      {
        Toast.makeText(MessageDetailFragment.this.getActivity(), MessageDetailFragment.this.getResources().getString(2131427916), 0).show();
        return;
      }
      Toast.makeText(MessageDetailFragment.this.getActivity(), MessageDetailFragment.this.getResources().getString(2131427911), 0).show();
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.MessageDetailFragment
 * JD-Core Version:    0.6.0
 */