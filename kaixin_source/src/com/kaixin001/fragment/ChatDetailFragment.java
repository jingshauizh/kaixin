package com.kaixin001.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v4.app.FragmentActivity;
import android.text.ClipboardManager;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.adapter.ChatListItemAdapter;
import com.kaixin001.adapter.ChatListItemAdapter.ChatDetailCache;
import com.kaixin001.adapter.ChatListItemAdapter.ItemEventObserver;
import com.kaixin001.engine.UploadTaskListEngine;
import com.kaixin001.item.ChatInfoItem;
import com.kaixin001.item.ChatInfoItem.ChatMsg;
import com.kaixin001.item.ChatInfoItem.UserTyping;
import com.kaixin001.item.ChatMessageTask;
import com.kaixin001.model.ChatModel;
import com.kaixin001.model.FaceModel;
import com.kaixin001.model.Setting;
import com.kaixin001.model.User;
import com.kaixin001.util.ActivityUtil;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.ScreenUtil;
import com.kaixin001.util.StringUtil;
import com.kaixin001.view.FaceKeyboardView;
import com.kaixin001.view.FaceKeyboardView.OnFaceSelectedListener;
import com.kaixin001.view.KXListView;
import com.kaixin001.view.KXListView.OnResizeListener;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

public class ChatDetailFragment extends BaseFragment
  implements ChatListItemAdapter.ItemEventObserver, AdapterView.OnItemLongClickListener
{
  private static final int CHAT_CONTENT_MAX_LENGTH = 200;
  protected static final int DLG_SHOW_COPY = 1002;
  private static final String RESPONSOR = "responsor";
  private static final String TAG = "ChatDetailActivity";
  public static final int TYPINGTIME = 5000;
  private static volatile Timer mTimer = new Timer();
  private KXListView chatListView = null;
  private boolean hasChatted = false;
  private ChatListItemAdapter mAdapter = null;
  private ImageView mBtnRightBtn = null;
  private ImageView mBtnSend = null;
  private boolean mCanSendTyping = true;
  private final ArrayList<ChatInfoItem> mChatList = new ArrayList();
  private int mCircleType = 0;
  private EditText mEditChatContent = null;
  private FaceKeyboardView mFaceKeyboardView;
  private FaceModel mFaceModel;
  private boolean mFiredTimer = false;
  private String mFriendName = null;
  private String mId = null;
  private boolean mIsCircle = false;
  private int mKeyBoardHidden = 0;
  private Runnable mListViewMoveToLastRowRunable = new Runnable()
  {
    public void run()
    {
      if (ChatDetailFragment.this.isNeedReturn())
        return;
      ChatDetailFragment.this.chatListView.setSelection(-1 + ChatDetailFragment.this.mChatList.size());
    }
  };
  private String mMsgContentForCopy = null;
  private String mName = null;
  View.OnClickListener mOnClickLtner = new View.OnClickListener()
  {
    public void onClick(View paramView)
    {
      if (paramView == ChatDetailFragment.this.mBtnSend)
        ChatDetailFragment.this.onSendEvent();
      do
        return;
      while (paramView != ChatDetailFragment.this.mBtnRightBtn);
      ChatDetailFragment.this.onPressedPopUpBtn();
    }
  };
  private int mSendingStatusStep = 0;
  TextWatcher mTextWatcher = new TextWatcher()
  {
    public void afterTextChanged(Editable paramEditable)
    {
      if (ChatDetailFragment.this.mEditChatContent.getText().toString().trim().length() == 200)
      {
        Toast localToast = Toast.makeText(ChatDetailFragment.this.getActivity(), 2131427353, 0);
        localToast.setGravity(17, 0, -localToast.getYOffset());
        localToast.show();
      }
      if (ChatDetailFragment.this.mEditChatContent.getText().toString().trim().length() > 0)
      {
        ChatDetailFragment.this.mBtnSend.setImageResource(2130838281);
        return;
      }
      ChatDetailFragment.this.mBtnSend.setImageResource(2130838280);
    }

    public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
    {
    }

    public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
    {
      if (ChatDetailFragment.this.isNeedReturn());
      do
        return;
      while (paramInt1 != 0);
      ChatMessageTask localChatMessageTask = new ChatMessageTask(ChatDetailFragment.this.getActivity().getApplicationContext());
      if (ChatDetailFragment.this.mIsCircle)
        localChatMessageTask.setInitData(null, ChatDetailFragment.this.mId, 9);
      while (true)
      {
        UploadTaskListEngine.getInstance().addUploadTask(localChatMessageTask);
        ChatDetailFragment.this.mCanSendTyping = false;
        return;
        localChatMessageTask.setInitData(null, ChatDetailFragment.this.mId, 7);
      }
    }
  };
  private TimerTask mTimerTask = new MyTimerTask(null);
  private Runnable mUpdateSendingStatusRunnable = new Runnable()
  {
    public void run()
    {
      if (ChatDetailFragment.this.isNeedReturn());
      do
        return;
      while (!ChatDetailFragment.this.updateSendingState());
      ChatDetailFragment.this.mSendingStatusStep = ((1 + ChatDetailFragment.this.mSendingStatusStep) % 4);
      ChatDetailFragment.this.mHandler.postDelayed(ChatDetailFragment.this.mUpdateSendingStatusRunnable, 500L);
    }
  };
  private TextView mtvTitleBarCenter = null;

  private void clearNewMessage()
  {
    ChatMessageTask localChatMessageTask = new ChatMessageTask(getActivity().getApplicationContext());
    if (this.mIsCircle)
      localChatMessageTask.setInitData(null, this.mId, 11);
    while (true)
    {
      UploadTaskListEngine.getInstance().addUploadTask(localChatMessageTask);
      return;
      localChatMessageTask.setInitData(null, this.mId, 12);
    }
  }

  private void initFaceKeyBoardLayout(View paramView)
  {
    this.mFaceKeyboardView = ((FaceKeyboardView)paramView.findViewById(2131361976));
    this.mFaceKeyboardView.setOnFaceSelectedListener(new FaceKeyboardView.OnFaceSelectedListener()
    {
      public void FaceSelected(int paramInt, String paramString, Bitmap paramBitmap)
      {
        switch (paramInt)
        {
        default:
          ChatDetailFragment.this.insertFaceIcon(paramString, paramBitmap);
        case -2:
        case -1:
        }
        int i;
        do
        {
          return;
          i = ChatDetailFragment.this.mEditChatContent.getSelectionStart();
        }
        while (i <= 0);
        String str1 = ChatDetailFragment.this.mEditChatContent.getText().toString();
        ArrayList localArrayList = FaceModel.getInstance().getAllFaceStringSortByLength();
        int j = 1;
        Iterator localIterator = localArrayList.iterator();
        if (!localIterator.hasNext());
        while (true)
        {
          ChatDetailFragment.this.mEditChatContent.getText().delete(i - j, i);
          return;
          String str2 = (String)localIterator.next();
          if (!str1.endsWith(str2))
            break;
          j = str2.length();
        }
      }
    });
    this.mFaceKeyboardView.init(getActivity());
    showFaceKeyboardView(false);
  }

  private void onPressedPopUpBtn()
  {
    if (this.mIsCircle)
    {
      if (ChatModel.getInstance().isCircleBlocked(this.mId));
      for (int i = 2131492867; ; i = 2131492866)
      {
        DialogUtil.showSelectListDlg(getActivity(), 2131427329, getResources().getStringArray(i), new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramDialogInterface, int paramInt)
          {
            if (!Setting.getInstance().isTestVersion())
            {
              if (paramInt == 0)
                IntentUtil.showCircleMembersFragment(ChatDetailFragment.this, ChatDetailFragment.this.mId, ChatDetailFragment.this.mFriendName, ChatDetailFragment.this.mCircleType);
            }
            else
              return;
            if (ChatModel.getInstance().isCircleBlocked(ChatDetailFragment.this.mId))
            {
              ChatModel.getInstance().delBlockedCircleItem(ChatDetailFragment.this.mId);
              Toast.makeText(ChatDetailFragment.this.getActivity(), ChatDetailFragment.this.getString(2131428060), 0).show();
              return;
            }
            ChatModel.getInstance().addBlockedCircleItem(ChatDetailFragment.this.mId);
            Toast.makeText(ChatDetailFragment.this.getActivity(), ChatDetailFragment.this.getString(2131428059), 0).show();
          }
        }
        , 1);
        return;
      }
    }
    DialogUtil.showSelectListDlg(getActivity(), 2131427329, getResources().getStringArray(2131492865), new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        if ((!Setting.getInstance().isTestVersion()) && (paramInt == 0))
        {
          Intent localIntent = new Intent(ChatDetailFragment.this.getActivity(), ChatHistoryFragment.class);
          localIntent.putExtra("fname", ChatDetailFragment.this.mFriendName);
          localIntent.putExtra("fuid", ChatDetailFragment.this.mId);
          localIntent.putExtra("is_circle", ChatDetailFragment.this.mIsCircle);
          ChatDetailFragment.this.startFragment(localIntent, true, 1);
        }
      }
    }
    , 1);
  }

  private void onSendEvent()
  {
    if (this.mEditChatContent.getText().length() == 0);
    do
    {
      return;
      if (this.mEditChatContent.getText().toString().trim().length() != 0)
        continue;
      this.mEditChatContent.setText("");
      return;
    }
    while (!super.checkNetworkAndHint(true));
    this.hasChatted = true;
    String str1 = this.mEditChatContent.getText().toString();
    String str2 = URLEncoder.encode(StringUtil.toUtf8(str1));
    ChatInfoItem localChatInfoItem = ChatModel.getInstance().addSendChatInfo(this.mId, str1, this.mIsCircle, 1);
    ChatMessageTask localChatMessageTask = new ChatMessageTask(getActivity().getApplicationContext());
    if (this.mIsCircle)
      localChatMessageTask.setInitData(str2, this.mId, 8);
    while (true)
    {
      localChatMessageTask.setChatInfoItem(localChatInfoItem);
      UploadTaskListEngine.getInstance().addUploadTask(localChatMessageTask);
      this.mEditChatContent.setText("");
      updateAdapter(true);
      this.mCanSendTyping = true;
      startSendingAnimation();
      hideKeyboard();
      return;
      localChatMessageTask.setInitData(str2, this.mId, 6);
    }
  }

  private void setButtonHandler(View paramView)
  {
    ((ImageView)paramView.findViewById(2131361973)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        ChatDetailFragment.this.showFaceListDialog(true);
      }
    });
  }

  private void setChatList(View paramView)
  {
    this.chatListView = ((KXListView)paramView.findViewById(2131361970));
    this.chatListView.setAdapter(this.mAdapter);
    this.chatListView.setOnItemLongClickListener(this);
    this.chatListView.setOnScrollListener(new AbsListView.OnScrollListener()
    {
      public void onScroll(AbsListView paramAbsListView, int paramInt1, int paramInt2, int paramInt3)
      {
      }

      public void onScrollStateChanged(AbsListView paramAbsListView, int paramInt)
      {
        if (paramInt == 1)
          ActivityUtil.hideInputMethod(ChatDetailFragment.this.getActivity());
      }
    });
    this.chatListView.setOnResizeListener(new KXListView.OnResizeListener()
    {
      public void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
      {
        ChatDetailFragment.this.chatListView.postDelayed(ChatDetailFragment.this.mListViewMoveToLastRowRunable, 10L);
      }
    });
    updateAdapter(true);
    clearNewMessage();
  }

  private void setTitleBar(View paramView)
  {
    ImageView localImageView = (ImageView)paramView.findViewById(2131362914);
    localImageView.setVisibility(0);
    localImageView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        ChatDetailFragment.this.finish();
      }
    });
    this.mBtnRightBtn = ((ImageView)paramView.findViewById(2131362928));
    this.mBtnRightBtn.setImageResource(2130839005);
    this.mBtnRightBtn.setVisibility(0);
    this.mBtnRightBtn.setOnClickListener(this.mOnClickLtner);
    ((ImageView)paramView.findViewById(2131362919)).setVisibility(8);
    this.mtvTitleBarCenter = ((TextView)paramView.findViewById(2131362920));
    this.mName = this.mFriendName;
    if (User.getInstance().getUID().compareTo(this.mId) == 0)
      this.mName = getResources().getString(2131427565);
    this.mtvTitleBarCenter.setText(this.mName);
    this.mtvTitleBarCenter.setVisibility(0);
  }

  private void showAuthorizedError()
  {
    DialogUtil.showKXAlertDialog(getActivity(), 2131428058, null);
  }

  private void showFaceKeyboardView(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.mFaceKeyboardView.setVisibility(0);
      ActivityUtil.hideInputMethod(getActivity());
      return;
    }
    this.mFaceKeyboardView.setVisibility(8);
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

  private void updateAdapter(boolean paramBoolean)
  {
    this.mChatList.clear();
    if ((this.mId != null) && (ChatModel.getInstance().getChatInfoList(this.mId, this.mIsCircle) != null))
    {
      ArrayList localArrayList = ChatModel.getInstance().getChatInfoList(this.mId, this.mIsCircle);
      if ((localArrayList != null) && (localArrayList.size() > 0))
        this.mChatList.addAll(localArrayList);
    }
    if (this.mAdapter != null)
      this.mAdapter.notifyDataSetChanged();
    if (paramBoolean)
      this.chatListView.postDelayed(this.mListViewMoveToLastRowRunable, 0L);
  }

  private boolean updateSendingState()
  {
    int i = this.chatListView.getChildCount();
    int j = this.chatListView.getFirstVisiblePosition() - this.chatListView.getHeaderViewsCount();
    int k = 0;
    int m = 0;
    if (m >= i)
      return k;
    ChatListItemAdapter.ChatDetailCache localChatDetailCache = (ChatListItemAdapter.ChatDetailCache)this.chatListView.getChildAt(m).getTag();
    int n = m + j;
    ChatInfoItem localChatInfoItem = null;
    if (n >= 0)
    {
      int i1 = m + j;
      int i2 = this.mChatList.size();
      localChatInfoItem = null;
      if (i1 < i2)
        localChatInfoItem = (ChatInfoItem)this.mChatList.get(m + j);
    }
    if ((localChatDetailCache == null) || (localChatInfoItem == null));
    while (true)
    {
      m++;
      break;
      k = 1;
      this.mAdapter.showMessageSendingState(localChatInfoItem, localChatDetailCache.mTxtMyTime, localChatDetailCache.mSendingMsg, this.mSendingStatusStep);
    }
  }

  public void finish()
  {
    super.finish();
    AnimationUtil.finishActivity(getActivity(), 4);
  }

  public boolean handleMessage(Message paramMessage)
  {
    if (paramMessage == null)
      return false;
    boolean bool1;
    switch (paramMessage.what)
    {
    default:
      bool1 = super.handleMessage(paramMessage);
    case 90009:
    case 90003:
    case 90004:
    case 90008:
    case 10003:
    case 90014:
    case 90013:
    }
    while (true)
    {
      return bool1;
      String str3 = paramMessage.obj.toString();
      boolean bool2 = this.mIsCircle;
      int m = 0;
      if (bool2)
        m = 1;
      if ((this.mId.equals(str3)) && (paramMessage.arg1 == m))
      {
        this.hasChatted = true;
        updateAdapter(true);
        clearNewMessage();
      }
      bool1 = true;
      continue;
      if (ChatModel.getInstance().getUnreadNum(this.mId, this.mIsCircle) > 0)
      {
        this.hasChatted = true;
        updateAdapter(true);
        clearNewMessage();
      }
      bool1 = true;
      continue;
      ArrayList localArrayList = (ArrayList)paramMessage.obj;
      int i = 0;
      label197: int j = localArrayList.size();
      ChatInfoItem.UserTyping localUserTyping = null;
      int k = 0;
      label216: String str1;
      if (i >= j)
      {
        if ((k == 0) && (this.mIsCircle))
          break;
        if (!this.mIsCircle)
          break label418;
        if (localUserTyping.mRealName.length() > 4)
          break label384;
        str1 = localUserTyping.mRealName;
        label254: String str2 = str1 + getString(2131427352);
        this.mtvTitleBarCenter.setText(str2);
        if (this.mFiredTimer);
      }
      try
      {
        mTimer.schedule(this.mTimerTask, 5000L);
        this.mFiredTimer = true;
        bool1 = true;
        continue;
        if ((((ChatInfoItem.UserTyping)localArrayList.get(i)).mIsCircle == this.mIsCircle) && (((ChatInfoItem.UserTyping)localArrayList.get(i)).mGID.equals(this.mId)))
        {
          k = 1;
          localUserTyping = (ChatInfoItem.UserTyping)localArrayList.get(i);
          break label216;
        }
        i++;
        break label197;
        label384: str1 = localUserTyping.mRealName.substring(0, 3) + "...";
        break label254;
        label418: this.mtvTitleBarCenter.setText(2131427352);
      }
      catch (Exception localException)
      {
        while (true)
          KXLog.d("ChatDetailActivity", localException.toString());
      }
      this.mtvTitleBarCenter.setText(this.mName);
      bool1 = true;
      continue;
      updateAdapter(true);
      bool1 = true;
      continue;
      if (this.mIsCircle)
        showAuthorizedError();
      updateAdapter(true);
      if ((this.mId != null) && (ChatModel.getInstance().getChatInfoList(this.mId, this.mIsCircle) != null))
        clearNewMessage();
      bool1 = true;
      continue;
      if ((this.mIsCircle) && (paramMessage.obj != null) && (!TextUtils.isEmpty(this.mId)) && (this.mId.equals(paramMessage.obj.toString())))
        showAuthorizedError();
      bool1 = true;
    }
  }

  protected void insertFaceIcon(int paramInt)
  {
    ArrayList localArrayList;
    String str;
    Editable localEditable;
    int i;
    int j;
    if (this.mFaceModel.getStateFaceStrings() != null)
    {
      localArrayList = this.mFaceModel.getStateFaceIcons();
      str = (String)this.mFaceModel.getStateFaceStrings().get(paramInt);
      localEditable = this.mEditChatContent.getText();
      i = Selection.getSelectionStart(localEditable);
      j = Selection.getSelectionEnd(localEditable);
      if ((i == -1) || (j == -1))
      {
        i = localEditable.length();
        j = localEditable.length();
      }
      if (localEditable.length() <= 200 - str.length())
        break label105;
    }
    label105: 
    do
    {
      return;
      localEditable.replace(i, j, str.subSequence(0, str.length()));
    }
    while ((1 != ScreenUtil.getOrientation(getActivity())) && (!Setting.getInstance().isShowFaceIconInLandScape()));
    localEditable.setSpan(new ImageSpan((Bitmap)localArrayList.get(paramInt)), i, i + str.length(), 33);
  }

  protected void insertFaceIcon(String paramString, Bitmap paramBitmap)
  {
    if ((TextUtils.isEmpty(paramString)) || (paramBitmap == null));
    Editable localEditable;
    int i;
    do
    {
      return;
      localEditable = this.mEditChatContent.getText();
      i = Selection.getSelectionStart(localEditable);
      int j = Selection.getSelectionEnd(localEditable);
      if ((i == -1) || (j == -1))
      {
        i = localEditable.length();
        j = localEditable.length();
      }
      if (localEditable.length() > 200 - paramString.length())
      {
        Toast.makeText(getActivity(), 2131427899, 0).show();
        return;
      }
      localEditable.replace(i, j, paramString.subSequence(0, paramString.length()));
    }
    while ((1 != ScreenUtil.getOrientation(getActivity())) && (!Setting.getInstance().isShowFaceIconInLandScape()));
    localEditable.setSpan(new ImageSpan(paramBitmap), i, i + paramString.length(), 33);
  }

  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    if (this.mKeyBoardHidden != paramConfiguration.keyboardHidden)
    {
      this.mKeyBoardHidden = paramConfiguration.keyboardHidden;
      if (1 + this.chatListView.getLastVisiblePosition() == this.mChatList.size())
        this.chatListView.postDelayed(this.mListViewMoveToLastRowRunable, 10L);
    }
    super.onConfigurationChanged(paramConfiguration);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
  }

  protected Dialog onCreateDialog(int paramInt)
  {
    if (1002 == paramInt)
      return DialogUtil.showSelectListDlg(getActivity(), 2131427509, getResources().getStringArray(2131492888), new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          ((ClipboardManager)ChatDetailFragment.this.getActivity().getSystemService("clipboard")).setText(ChatDetailFragment.this.mMsgContentForCopy);
        }
      }
      , 1);
    return super.onCreateDialog(paramInt);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903063, paramViewGroup, false);
  }

  public void onDestroy()
  {
    hideKeyboard();
    UploadTaskListEngine.getInstance().unRegister(this.mHandler);
    stopSendingAnimation();
    super.onDestroy();
  }

  public void onItemEvent(int paramInt, String paramString1, String paramString2, String paramString3)
  {
    ChatInfoItem.ChatMsg localChatMsg = null;
    try
    {
      long l = Long.valueOf(paramString1).longValue();
      for (int i = 0; ; i++)
      {
        int j = this.mChatList.size();
        ChatInfoItem localChatInfoItem1 = null;
        if (i >= j);
        while (true)
          if ((localChatInfoItem1 != null) && (localChatInfoItem1.mSendStatus == 5))
          {
            return;
            localChatMsg = (ChatInfoItem.ChatMsg)((ChatInfoItem)this.mChatList.get(i)).mSubObject;
            if (localChatMsg.mUniqueID != l)
              break;
            localChatInfoItem1 = (ChatInfoItem)this.mChatList.get(i);
            continue;
          }
          else
          {
            localChatInfoItem1.mSendStatus = 5;
            ChatInfoItem localChatInfoItem2 = ChatModel.getInstance().addSendChatInfo(this.mId, localChatMsg.mContent, this.mIsCircle, 4);
            ChatMessageTask localChatMessageTask = new ChatMessageTask(getActivity().getApplicationContext());
            if (this.mIsCircle)
              localChatMessageTask.setInitData(URLEncoder.encode(StringUtil.toUtf8(localChatMsg.mContent)), this.mId, 8);
            while (true)
            {
              localChatMessageTask.setChatInfoItem(localChatInfoItem2);
              UploadTaskListEngine.getInstance().addUploadTask(localChatMessageTask);
              updateAdapter(true);
              startSendingAnimation();
              return;
              localChatMessageTask.setInitData(URLEncoder.encode(StringUtil.toUtf8(localChatMsg.mContent)), this.mId, 6);
            }
          }
      }
    }
    catch (Exception localException)
    {
    }
  }

  public boolean onItemLongClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    if (this.mChatList == null);
    do
      return false;
    while ((paramLong < 0L) || (paramLong >= this.mChatList.size()));
    ActivityUtil.hideInputMethod(getActivity());
    ChatInfoItem localChatInfoItem = (ChatInfoItem)this.mChatList.get((int)paramLong);
    ((Vibrator)getActivity().getSystemService("vibrator")).vibrate(50L);
    this.mMsgContentForCopy = ((ChatInfoItem.ChatMsg)localChatInfoItem.mSubObject).mContent;
    showDialog(1002);
    return true;
  }

  public void onPause()
  {
    ChatModel.getInstance().clearMsgInMemory(this.mId, this.mIsCircle);
    if (this.hasChatted);
    for (int i = -1; ; i = 0)
    {
      setResult(i, null);
      stopSendingAnimation();
      super.onPause();
      return;
    }
  }

  public void onResume()
  {
    startSendingAnimation();
    super.onResume();
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    setBundleData(getArguments());
    this.mAdapter = new ChatListItemAdapter(this, this.mChatList, this);
    this.mEditChatContent = ((EditText)paramView.findViewById(2131361974));
    this.mEditChatContent.addTextChangedListener(this.mTextWatcher);
    this.mBtnSend = ((ImageView)paramView.findViewById(2131361975));
    this.mBtnSend.setOnClickListener(this.mOnClickLtner);
    this.mFaceModel = FaceModel.getInstance();
    setTitleBar(paramView);
    setChatList(paramView);
    initFaceKeyBoardLayout(paramView);
    setButtonHandler(paramView);
    UploadTaskListEngine.getInstance().register(this.mHandler);
  }

  public void setBundleData(Bundle paramBundle)
  {
    if (paramBundle != null)
    {
      this.mFriendName = paramBundle.getString("fname");
      this.mId = paramBundle.getString("fuid");
      this.mIsCircle = paramBundle.getBoolean("isGroup");
      if (this.mIsCircle)
        this.mCircleType = paramBundle.getInt("type", 0);
    }
  }

  public void showFaceListDialog(boolean paramBoolean)
  {
    ImageView localImageView = (ImageView)getView().findViewById(2131361973);
    if (this.mFaceKeyboardView.getVisibility() == 0)
    {
      localImageView.setImageResource(2130838130);
      showFaceKeyboardView(false);
      ActivityUtil.showInputMethod(getActivity());
      return;
    }
    localImageView.setImageResource(2130838135);
    showFaceKeyboardView(true);
  }

  private class MyTimerTask extends TimerTask
  {
    private MyTimerTask()
    {
    }

    public void run()
    {
      try
      {
        if (ChatDetailFragment.this.isNeedReturn())
          return;
        Message localMessage = Message.obtain();
        localMessage.what = 90008;
        ChatDetailFragment.this.mHandler.sendMessage(localMessage);
        ChatDetailFragment.this.mFiredTimer = false;
        ChatDetailFragment.this.mTimerTask = new MyTimerTask(ChatDetailFragment.this);
        return;
      }
      catch (Exception localException)
      {
        KXLog.e("KaixinAppWidgetProvider", "run", localException);
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.ChatDetailFragment
 * JD-Core Version:    0.6.0
 */