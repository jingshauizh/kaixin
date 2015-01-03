package com.kaixin001.fragment;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.activity.StartActivity;
import com.kaixin001.adapter.MessageDetailAdapter;
import com.kaixin001.businesslogic.ViewMessageOriginalContent;
import com.kaixin001.engine.UploadTaskListEngine;
import com.kaixin001.item.CommentItem;
import com.kaixin001.item.KXLinkInfo;
import com.kaixin001.item.KXUploadTask;
import com.kaixin001.item.MessageDetailInfo;
import com.kaixin001.item.MessageDetailItem;
import com.kaixin001.item.MessageItem;
import com.kaixin001.item.MessageUploadTask;
import com.kaixin001.item.UserCommentItem;
import com.kaixin001.model.FaceModel;
import com.kaixin001.model.KaixinConst;
import com.kaixin001.model.MessageNotifyModel;
import com.kaixin001.model.MessageNotifyModel.MessageNotifyItem;
import com.kaixin001.model.Setting;
import com.kaixin001.model.User;
import com.kaixin001.util.ActivityUtil;
import com.kaixin001.util.CrashRecoverUtil;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.ScreenUtil;
import com.kaixin001.util.StringUtil;
import com.kaixin001.view.KXIntroView.OnClickLinkListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MessagePushDialogFragment extends BaseFragment
  implements AdapterView.OnItemLongClickListener, KXIntroView.OnClickLinkListener
{
  private static final int CONTENT_MAX_LENGTH = 200;
  private static String MESSAGE_TYPE_PRIVATE = "1";
  private MessageDetailAdapter mAdapter = null;
  private RelativeLayout mCheckOriginalLayout = null;
  private String mDetailFuid = "";
  private MessageDetailInfo mDetailInfo = null;
  private MessageNotifyModel.MessageNotifyItem mDetailItem = null;
  private String mDetailThreadId = null;
  private int mDetailType;
  EditText mEtContent = null;
  Button mFaceButton = null;
  private String mFriendName = null;
  GetMessageDetailTask mGetMessageDetailTask = null;
  private View mHeader;
  private ArrayList<MessageDetailItem> mListMessages = new ArrayList();
  private ListView mListView = null;
  private MessageNotifyModel mModel = MessageNotifyModel.getInstance();
  private ProgressBar mPgbLoadingUnread;
  Button mReplyButton = null;
  TextWatcher mTextWatcher = new TextWatcher()
  {
    public void afterTextChanged(Editable paramEditable)
    {
    }

    public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
    {
    }

    public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
    {
      if (!TextUtils.isEmpty(MessagePushDialogFragment.this.mEtContent.getText().toString().trim()))
      {
        MessagePushDialogFragment.this.mReplyButton.setEnabled(true);
        return;
      }
      MessagePushDialogFragment.this.mReplyButton.setEnabled(false);
    }
  };
  TextView mTvNextStepButton = null;
  TextView mTvNumberTip = null;
  private TextView mTvUnreadHint;
  private View mViewUnreadHint;
  private String msToMyContent = "对我的";
  private TextView mtvCheckOriginal = null;
  private String mtype;
  ViewMessageOriginalContent viewMessageOriginalContent = null;

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
    if ((this.mDetailType == 201) || (this.mDetailType == 202));
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

  private void doGetData(boolean paramBoolean)
  {
    cancelTask();
    if (this.mDetailThreadId == null);
    do
      return;
    while (!super.checkNetworkAndHint(true));
    downloadMessage();
    showLoadingProgressBar(true);
  }

  private void downloadMessage()
  {
    if ((this.mGetMessageDetailTask != null) && (this.mGetMessageDetailTask.isCancelled()) && (this.mGetMessageDetailTask.getStatus() != AsyncTask.Status.FINISHED))
      return;
    showUnreadHint(2, 0);
    this.mGetMessageDetailTask = new GetMessageDetailTask(null);
    String[] arrayOfString = new String[3];
    arrayOfString[0] = "";
    arrayOfString[1] = String.valueOf(this.mDetailType);
    this.mGetMessageDetailTask.execute(arrayOfString);
  }

  private void finishActivity()
  {
    deleteFinishedDetailInfo();
    this.mModel.removeTop(this.mDetailThreadId);
    if (this.mModel.getSize() == 0)
      ((NotificationManager)getActivity().getSystemService("notification")).cancel(KaixinConst.ID_NEW_MESSAGE_NOTIFICATION);
    finish();
  }

  private ArrayList<MessageDetailItem> getDetailListFromUpQueue()
  {
    ArrayList localArrayList = new ArrayList();
    UploadTaskListEngine localUploadTaskListEngine = UploadTaskListEngine.getInstance();
    setMessageDatafromUpQueue(localArrayList, localUploadTaskListEngine.getWaitTaskList());
    setMessageDatafromUpQueue(localArrayList, localUploadTaskListEngine.getUploadingTaskList());
    setMessageDatafromUpQueue(localArrayList, localUploadTaskListEngine.getFinishedTaskList());
    Collections.sort(localArrayList, this.mAdapter.cwjComparator);
    return localArrayList;
  }

  private boolean isUpdateEnabled()
  {
    return (this.mReplyButton != null) && (!this.mReplyButton.isEnabled()) && (ActivityUtil.isTopActivity(getActivity(), "com.kaixin001.activity.MessagePushDialogActivity", "com.kaixin001.activity.MessagePushDialogActivity"));
  }

  private void replySubmit()
  {
    int i = this.mDetailInfo.getDetailType();
    String str = String.valueOf(this.mEtContent.getText());
    MessageUploadTask localMessageUploadTask = new MessageUploadTask(getActivity().getApplicationContext(), 5);
    localMessageUploadTask.setContent(str);
    localMessageUploadTask.setMainThreadFuid(User.getInstance().getUID());
    if (i == 201)
    {
      localMessageUploadTask.setTaskType(5);
      localMessageUploadTask.setnMode(5);
    }
    while (true)
    {
      localMessageUploadTask.setThreadId(this.mDetailInfo.getId());
      UploadTaskListEngine.getInstance().addUploadTask(localMessageUploadTask);
      addOneReply(str);
      this.mEtContent.setText(null);
      this.mReplyButton.setEnabled(false);
      return;
      localMessageUploadTask.setTaskType(4);
      localMessageUploadTask.setnMode(3);
    }
  }

  private void setDataForActivity()
  {
    KXLog.d("TEST", "setDataForActivity");
    if (this.mModel.getSize() == 0)
    {
      startActivity(new Intent(getActivity(), StartActivity.class));
      finish();
      return;
    }
    this.mListView = ((ListView)findViewById(2131363035));
    this.mAdapter = new MessageDetailAdapter(this, this.mHandler, this.mListView, this.mListMessages);
    this.mAdapter.setIsPushDialogActivity(true);
    this.mHeader = getActivity().getLayoutInflater().inflate(2130903222, null);
    ((LinearLayout)this.mHeader.findViewById(2131363039)).setVisibility(8);
    this.mViewUnreadHint = getActivity().getLayoutInflater().inflate(2130903228, null);
    this.mTvUnreadHint = ((TextView)this.mViewUnreadHint.findViewById(2131363082));
    this.mTvUnreadHint.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        MessagePushDialogFragment.this.doGetData(true);
      }
    });
    this.mPgbLoadingUnread = ((ProgressBar)this.mViewUnreadHint.findViewById(2131363083));
    this.mCheckOriginalLayout = ((RelativeLayout)getActivity().getLayoutInflater().inflate(2130903232, null));
    this.mtvCheckOriginal = ((TextView)this.mCheckOriginalLayout.findViewById(2131363099));
    float f = getResources().getDisplayMetrics().density;
    this.mtvCheckOriginal.setPadding(this.mtvCheckOriginal.getPaddingLeft() + (int)(10.0F * f), this.mtvCheckOriginal.getPaddingTop(), this.mtvCheckOriginal.getPaddingRight(), this.mtvCheckOriginal.getPaddingBottom());
    this.mListView.addHeaderView(this.mCheckOriginalLayout);
    this.mListView.addHeaderView(this.mHeader);
    this.mListView.addFooterView(this.mViewUnreadHint);
    this.mViewUnreadHint.setVisibility(8);
    this.mListView.setAdapter(this.mAdapter);
    this.mListView.setOnItemLongClickListener(this);
    updateData();
    setTitleBar();
    setSendMsgBar();
    setOriginalText();
    setNameListDesView();
    setTypeDesView();
    if (this.mDetailItem.unreadNum > 0)
    {
      KXLog.d("Test", "自己加载");
      showUnreadHint(1, this.mDetailItem.unreadNum);
    }
    while (true)
    {
      setNextStepButton();
      return;
      showUnreadHint(0, 0);
    }
  }

  private void setMessageDatafromUpQueue(ArrayList<MessageDetailItem> paramArrayList, ArrayList<KXUploadTask> paramArrayList1)
  {
    if ((paramArrayList1 == null) || (paramArrayList == null))
      return;
    int i;
    label31: int j;
    label34: MessageUploadTask localMessageUploadTask;
    String str;
    int k;
    if ((this.mDetailType == 201) || (this.mDetailType == 202))
    {
      i = 5;
      j = 0;
      if (j < paramArrayList1.size())
      {
        KXUploadTask localKXUploadTask = (KXUploadTask)paramArrayList1.get(j);
        if ((localKXUploadTask != null) && ((localKXUploadTask.getTaskType() == 5) || (localKXUploadTask.getTaskType() == 4)))
        {
          localMessageUploadTask = (MessageUploadTask)paramArrayList1.get(j);
          str = localMessageUploadTask.getThreadId();
          k = 0;
          label98: if (k < this.mListMessages.size())
            break label135;
          label110: if (k > -1 + this.mListMessages.size())
            break label167;
        }
      }
    }
    while (true)
    {
      j++;
      break label34;
      break;
      i = 3;
      break label31;
      label135: if (((MessageDetailItem)this.mListMessages.get(k)).getMessageId().equals(localMessageUploadTask.getUploadThreadId()))
        break label110;
      k++;
      break label98;
      label167: if ((!this.mDetailThreadId.equals(localMessageUploadTask.getThreadId())) || (str == null) || (!str.equals(this.mDetailThreadId)) || (localMessageUploadTask.getMode() != i))
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
    }
  }

  private void setNameListDesView()
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
          break label460;
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
      StringBuffer localStringBuffer;
      try
      {
        str2 = localJSONArray3.getJSONObject(0).getString("fname");
        localStringBuffer = new StringBuffer();
        if (str2.equals(str1))
        {
          localStringBuffer.append(getString(2131427565)).append(getString(2131427568));
          if (localJSONArray2.length() != 1)
            break label462;
          localStringBuffer.append(getString(2131427566));
          break label462;
          if (k < localJSONArray2.length())
            break label391;
          if (j >= i)
            continue;
          localStringBuffer.append(StringUtil.replaceTokenWith(getString(2131427569), "*", String.valueOf(i)));
          localStringBuffer.append(getString(2131427567));
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
      localStringBuffer.append(str2).append(getString(2131427568)).append(getString(2131427565));
      j = 2;
      break label465;
      label391: String str3 = localJSONArray2.getString(k);
      if ((str3.equals(str1)) || (str3.equals(str2)))
        break label471;
      if (j == 1)
      {
        localStringBuffer.append(str3);
        break label477;
      }
      localStringBuffer.append(",").append(str3);
      break label477;
      m++;
      break label96;
      label460: break;
      label462: j = 1;
      label465: k = 0;
    }
    while (true)
    {
      label471: k++;
      break;
      label477: j++;
    }
  }

  private void setNextStepButton()
  {
    if (this.mModel.getSize() > 1)
    {
      showNextHint(1, -1 + this.mModel.getSize());
      return;
    }
    showNextHint(0, 0);
  }

  private void setOriginalText()
  {
    String str1 = this.mDetailInfo.getmAppid();
    if ((this.mDetailType == 204) || (this.mDetailType == 203))
    {
      if ((str1 != null) && ((str1.equals("2")) || (str1.equals("1")) || (str1.equals("1018")) || (str1.equals("1088"))))
      {
        String str2 = this.mDetailItem.getFname();
        String str3 = this.mDetailInfo.getAppName();
        if (!User.getInstance().getUID().equals(this.mDetailFuid))
          this.msToMyContent = this.msToMyContent.replace("我", str2);
        String str4 = this.mDetailInfo.getAppHtml();
        String str5 = this.msToMyContent + str3 + "\"" + str4 + "\"";
        this.mtvCheckOriginal.setText(str5);
        this.mtvCheckOriginal.setTextColor(getResources().getColor(2130839398));
        ((ImageView)this.mCheckOriginalLayout.findViewById(2131362919)).setVisibility(8);
        this.mCheckOriginalLayout.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramView)
          {
            MessagePushDialogFragment.this.turnToOriginalContent();
          }
        });
        this.mCheckOriginalLayout.setVisibility(0);
        return;
      }
      this.mCheckOriginalLayout.setVisibility(8);
      this.mListView.removeHeaderView(this.mCheckOriginalLayout);
      return;
    }
    this.mCheckOriginalLayout.setVisibility(8);
    this.mListView.removeHeaderView(this.mCheckOriginalLayout);
  }

  private void setSendMsgBar()
  {
    this.mFaceButton = ((Button)findViewById(2131363077));
    this.mReplyButton = ((Button)findViewById(2131363078));
    this.mEtContent = ((EditText)findViewById(2131363079));
    this.mEtContent.addTextChangedListener(this.mTextWatcher);
    this.mFaceButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        MessagePushDialogFragment.this.showFaceListDialog(false);
      }
    });
    this.mReplyButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        MessagePushDialogFragment.this.replySubmit();
      }
    });
    if (TextUtils.isEmpty(this.mEtContent.getText()))
      this.mReplyButton.setEnabled(false);
  }

  private void setTypeDesView()
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
    localTextView.setTextColor(getResources().getColor(2130839389));
    localTextView.setTextSize(14.0F);
    localTextView.setVisibility(0);
    localTextView.setText(2131427553);
  }

  private void showLeftMenuList(boolean paramBoolean)
  {
    BaseFragment localBaseFragment;
    FragmentTransaction localFragmentTransaction;
    if (getActivity() != null)
    {
      localBaseFragment = (BaseFragment)getActivity().getSupportFragmentManager().findFragmentById(2131363004);
      if (localBaseFragment != null)
      {
        localFragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        if (localFragmentTransaction != null)
        {
          if (!paramBoolean)
            break label59;
          localFragmentTransaction.show(localBaseFragment);
        }
      }
    }
    while (true)
    {
      localFragmentTransaction.commit();
      return;
      label59: if (paramBoolean)
        continue;
      localFragmentTransaction.hide(localBaseFragment);
    }
  }

  private void showLoadingProgressBar(boolean paramBoolean)
  {
    View localView = findViewById(2131363037);
    if ((paramBoolean) && (this.mListMessages.size() == 0))
    {
      localView.setVisibility(0);
      return;
    }
    localView.setVisibility(8);
  }

  private void showNextHint(int paramInt1, int paramInt2)
  {
    if (paramInt1 > 0)
    {
      this.mTvNextStepButton.setBackgroundResource(2130839045);
      if (paramInt2 > 0)
      {
        this.mTvNumberTip.setVisibility(0);
        this.mTvNumberTip.setText(String.valueOf(paramInt2));
      }
      while (true)
      {
        this.mTvNextStepButton.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramView)
          {
            MessagePushDialogFragment.this.deleteFinishedDetailInfo();
            MessagePushDialogFragment.this.mModel.removeTop(MessagePushDialogFragment.this.mDetailThreadId);
            MessagePushDialogFragment.this.updateView();
          }
        });
        return;
        this.mTvNumberTip.setVisibility(8);
      }
    }
    this.mTvNextStepButton.setBackgroundResource(2130839039);
    this.mTvNumberTip.setVisibility(8);
    this.mTvNextStepButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        MessagePushDialogFragment.this.finishActivity();
      }
    });
  }

  private void turnToOriginalContent()
  {
    if (!checkNetworkAndHint(true))
      return;
    if (this.viewMessageOriginalContent == null)
      this.viewMessageOriginalContent = new ViewMessageOriginalContent(getActivity(), this);
    this.viewMessageOriginalContent.turnToOriginalContent(this.mDetailInfo, this.mDetailType, this.mDetailFuid);
  }

  private void updateList()
  {
    this.mListMessages.clear();
    ArrayList localArrayList = this.mDetailItem.getDetailItems();
    if (localArrayList != null)
      this.mListMessages.addAll(localArrayList);
    while (true)
    {
      this.mListMessages.addAll(getDetailListFromUpQueue());
      if (this.mAdapter != null)
        this.mAdapter.notifyDataSetChanged();
      this.mListView.setSelection(-1 + this.mListMessages.size());
      return;
      KXLog.d("TEST", "详细列表为空");
      KXLog.d("TEST", "Unread message:" + String.valueOf(this.mDetailItem.unreadNum));
    }
  }

  protected void addOneReply(String paramString)
  {
    updateList();
    this.mListView.setSelection(-1 + this.mListMessages.size());
    this.mAdapter.startSendingAnimation();
  }

  protected boolean handleMessage(Message paramMessage)
  {
    int i = 1;
    if (paramMessage == null)
      i = 0;
    while (true)
    {
      return i;
      if (paramMessage.what != 10001)
        break;
      if ((paramMessage.arg1 == i) && (paramMessage.obj == null))
        continue;
      updateList();
      return i;
    }
    if (paramMessage.what == 12011)
    {
      if (((String)paramMessage.obj).equals(this.mDetailThreadId))
      {
        updateData();
        return i;
      }
      if (isUpdateEnabled())
      {
        updateView();
        return i;
      }
      setNextStepButton();
      return i;
    }
    return super.handleMessage(paramMessage);
  }

  protected void insertFaceIcon(int paramInt)
  {
    FaceModel localFaceModel = FaceModel.getInstance();
    ArrayList localArrayList;
    String str;
    Editable localEditable;
    int i;
    int j;
    if (localFaceModel.getStateFaceStrings() != null)
    {
      localArrayList = localFaceModel.getStateFaceIcons();
      str = (String)localFaceModel.getStateFaceStrings().get(paramInt);
      localEditable = this.mEtContent.getText();
      i = Selection.getSelectionStart(localEditable);
      j = Selection.getSelectionEnd(localEditable);
      if ((i == -1) || (j == -1))
      {
        i = localEditable.length();
        j = localEditable.length();
      }
      if (localEditable.length() <= 200 - str.length())
        break label102;
    }
    label102: 
    do
    {
      return;
      localEditable.replace(i, j, str.subSequence(0, str.length()));
    }
    while ((1 != ScreenUtil.getOrientation(getActivity())) && (!Setting.getInstance().isShowFaceIconInLandScape()));
    localEditable.setSpan(new ImageSpan((Bitmap)localArrayList.get(paramInt)), i, i + str.length(), 33);
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
  }

  public void onAttach(Activity paramActivity)
  {
    paramActivity.getWindow().setType(2004);
    paramActivity.getWindow().setType(2009);
    super.onAttach(paramActivity);
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
    KXLog.d("TEST", "PushDialog onCreate");
    super.onCreate(paramBundle);
    if ((0x100000 & getArguments().getInt("flags")) == 1048576)
    {
      IntentUtil.returnToDesktop(getActivity());
      this.mHandler.postDelayed(new Runnable()
      {
        public void run()
        {
          if (MessagePushDialogFragment.this.isNeedReturn())
            return;
          KXLog.d("TEST", "长按Home引发退出");
          MessagePushDialogFragment.this.finish();
        }
      }
      , 2000L);
    }
    do
    {
      return;
      if (!CrashRecoverUtil.isCrashBefore())
        continue;
      CrashRecoverUtil.crashRecover(getActivity().getApplicationContext());
      IntentUtil.returnToDesktop(getActivity());
      KXLog.d("TEST", "isCrashBefore");
      return;
    }
    while (!TextUtils.isEmpty(User.getInstance().getUID()));
    KXLog.d("TEST", "用户无效");
    finish();
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903226, paramViewGroup, false);
  }

  public void onDestroy()
  {
    MessageNotifyModel.getInstance().setHandler(null);
    if (this.mAdapter != null)
      this.mAdapter.stopSendingAnimation();
    UploadTaskListEngine.getInstance().unRegister(this.mHandler);
    cancelTask();
    if ((this.mGetMessageDetailTask != null) && (this.mGetMessageDetailTask.getStatus() != AsyncTask.Status.FINISHED))
      this.mGetMessageDetailTask.cancel(true);
    super.onDestroy();
  }

  public void onDestroyView()
  {
    super.onDestroyView();
    showLeftMenuList(true);
  }

  public boolean onItemLongClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    return true;
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 4)
    {
      finishActivity();
      return true;
    }
    if (paramKeyEvent.getKeyCode() == 3)
    {
      finishActivity();
      return true;
    }
    return super.onKeyDown(paramInt, paramKeyEvent);
  }

  public void onPause()
  {
    super.onPause();
    if (this.mAdapter != null)
      this.mAdapter.stopSendingAnimation();
  }

  public void onResume()
  {
    super.onResume();
    if (this.mAdapter != null)
    {
      this.mAdapter.notifyDataSetChanged();
      this.mAdapter.startSendingAnimation();
    }
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    showLeftMenuList(false);
    UploadTaskListEngine.getInstance().register(this.mHandler);
    MessageNotifyModel.getInstance().setHandler(this.mHandler);
    setDataForActivity();
  }

  protected void setTitleBar()
  {
    ImageView localImageView = (ImageView)findViewById(2131362914);
    localImageView.setVisibility(0);
    localImageView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        Setting.getInstance().setNeedRefreshFlag(true);
        switch (MessagePushDialogFragment.this.mDetailType)
        {
        case 202:
        default:
          return;
        case 201:
          Intent localIntent5 = new Intent(MessagePushDialogFragment.this.getActivity(), MessageListFragment.class);
          MessagePushDialogFragment.this.startFragment(localIntent5);
          return;
        case 205:
          Intent localIntent4 = new Intent(MessagePushDialogFragment.this.getActivity(), UserCommentListFragment.class);
          localIntent4.putExtra("receivecomment", true);
          MessagePushDialogFragment.this.startFragment(localIntent4);
          return;
        case 206:
          Intent localIntent3 = new Intent(MessagePushDialogFragment.this.getActivity(), UserCommentListFragment.class);
          localIntent3.putExtra("receivecomment", false);
          MessagePushDialogFragment.this.startFragment(localIntent3);
          return;
        case 203:
          Intent localIntent2 = new Intent(MessagePushDialogFragment.this.getActivity(), CommentListFragment.class);
          localIntent2.putExtra("comment", true);
          MessagePushDialogFragment.this.startFragment(localIntent2);
          return;
        case 204:
        }
        Intent localIntent1 = new Intent(MessagePushDialogFragment.this.getActivity(), CommentListFragment.class);
        localIntent1.putExtra("comment", false);
        MessagePushDialogFragment.this.startFragment(localIntent1);
      }
    });
    this.mTvNextStepButton = ((TextView)findViewById(2131363080));
    this.mTvNumberTip = ((TextView)findViewById(2131363081));
    TextView localTextView = (TextView)findViewById(2131362920);
    int j;
    if (this.mDetailType == 201)
      j = 2131427551;
    while (true)
    {
      localTextView.setText(j);
      return;
      if (this.mDetailType == 203)
      {
        j = 2131427539;
        continue;
      }
      if (this.mDetailType == 204)
      {
        j = 2131427540;
        continue;
      }
      if (this.mDetailType == 205)
      {
        j = 2131427576;
        continue;
      }
      int i = this.mDetailType;
      j = 0;
      if (i != 206)
        continue;
      j = 2131427542;
    }
  }

  void showUnreadHint(int paramInt1, int paramInt2)
  {
    if ((paramInt1 == 1) && (paramInt2 > 0))
    {
      this.mViewUnreadHint.setVisibility(0);
      this.mTvUnreadHint.setVisibility(0);
      this.mPgbLoadingUnread.setVisibility(8);
      this.mTvUnreadHint.setText(this.mTvUnreadHint.getText().toString().replace("*", String.valueOf(paramInt2)));
      return;
    }
    if (paramInt1 == 2)
    {
      this.mViewUnreadHint.setVisibility(0);
      this.mTvUnreadHint.setVisibility(8);
      this.mPgbLoadingUnread.setVisibility(0);
      return;
    }
    if (paramInt1 <= 0)
    {
      this.mViewUnreadHint.setVisibility(8);
      return;
    }
    this.mTvUnreadHint.setText("下载失败且number<= 0,这种情况应该不会发生");
  }

  protected void updateData()
  {
    if (this.mModel.getTop() != null)
    {
      this.mDetailItem = this.mModel.getTop();
      this.mDetailInfo = this.mModel.getTop().mDetailInfo;
      this.mDetailThreadId = this.mDetailItem.getId();
      this.mDetailType = this.mDetailItem.threadType;
      this.mtype = this.mDetailItem.getMType();
      String str = this.mDetailItem.getFuid();
      if (str != null)
        this.mDetailFuid = str;
      this.msToMyContent = "对我的";
      updateList();
    }
  }

  protected void updateView()
  {
    startFragment(new Intent(getActivity(), MessagePushDialogFragment.class));
  }

  private class GetMessageDetailTask extends KXFragment.KXAsyncTask<String, Void, Integer>
  {
    MessageNotifyModel.MessageNotifyItem mItem = null;
    int resultCode;

    private GetMessageDetailTask()
    {
      super();
    }

    protected Integer doInBackgroundA(String[] paramArrayOfString)
    {
      if (paramArrayOfString.length < 2)
        return Integer.valueOf(0);
      while (true)
      {
        try
        {
          int i = Integer.parseInt(paramArrayOfString[1]);
          KXLog.d("mDetailType", "mDetailType=" + MessagePushDialogFragment.this.mDetailType);
          if (i == 201)
          {
            this.mItem = MessageNotifyModel.getInstance().getMessageNotifyItem(MessagePushDialogFragment.this.getActivity().getApplicationContext(), (MessageItem)MessagePushDialogFragment.this.mDetailItem.getThreadItem(), i);
            if (this.mItem.unreadNum > 0)
            {
              return Integer.valueOf(1);
              label101: this.mItem = MessageNotifyModel.getInstance().getMessageNotifyItem(MessagePushDialogFragment.this.getActivity().getApplicationContext(), (CommentItem)MessagePushDialogFragment.this.mDetailItem.getThreadItem(), i);
              continue;
            }
          }
        }
        catch (Exception localException)
        {
          KXLog.e("MessagePushDialogActivity", "onPostExecute", localException);
          return Integer.valueOf(0);
        }
        do
        {
          if ((i == 205) || (i == 206))
          {
            this.mItem = MessageNotifyModel.getInstance().getMessageNotifyItem(MessagePushDialogFragment.this.getActivity().getApplicationContext(), (UserCommentItem)MessagePushDialogFragment.this.mDetailItem.getThreadItem(), i);
            break;
          }
          return Integer.valueOf(0);
          Integer localInteger = Integer.valueOf(0);
          return localInteger;
          if (i == 204)
            break label101;
        }
        while (i != 203);
      }
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      try
      {
        MessagePushDialogFragment.this.updateData();
        MessagePushDialogFragment.this.showLoadingProgressBar(false);
        if (paramInteger.intValue() == 1)
        {
          MessagePushDialogFragment.this.showUnreadHint(0, 0);
          if (MessagePushDialogFragment.this.mListMessages == null)
            return;
          if (MessagePushDialogFragment.this.mListMessages.size() == 0)
            return;
          MessagePushDialogFragment.this.mGetMessageDetailTask = null;
          return;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("MessagePushDialogActivity", "onPostExecute", localException);
        return;
      }
      MessagePushDialogFragment.this.showUnreadHint(1, MessagePushDialogFragment.this.mDetailItem.unreadNum);
      int i = 2131427914;
      if (((MessagePushDialogFragment.this.mDetailType == 205) || (MessagePushDialogFragment.this.mDetailType == 206)) && (this.resultCode == 2))
        i = 2131427912;
      Toast.makeText(MessagePushDialogFragment.this.getActivity(), i, 0).show();
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
 * Qualified Name:     com.kaixin001.fragment.MessagePushDialogFragment
 * JD-Core Version:    0.6.0
 */