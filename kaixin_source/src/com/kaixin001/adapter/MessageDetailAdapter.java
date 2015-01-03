package com.kaixin001.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.os.Handler;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.engine.UploadTaskListEngine;
import com.kaixin001.fragment.BaseFragment;
import com.kaixin001.item.KXUploadTask;
import com.kaixin001.item.MessageAttachmentItem;
import com.kaixin001.item.MessageDetailItem;
import com.kaixin001.model.MessageCenterModel;
import com.kaixin001.model.User;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.StringUtil;
import com.kaixin001.view.KXIntroView;
import com.kaixin001.view.KXIntroView.OnClickLinkListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;

public class MessageDetailAdapter extends BaseAdapter
{
  private Activity context;
  private int currentDay = Calendar.getInstance().get(6);
  public final Comparator<MessageDetailItem> cwjComparator = new Comparator()
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
  private Calendar detailItemTime = Calendar.getInstance();
  private BaseFragment mFragment;
  Handler mHandler;
  private boolean mIsPushDialogActivity = false;
  private ArrayList<MessageDetailItem> mListMessages;
  ListView mListView;
  private int mSendingStatusStep = 0;
  private Runnable mUpdateSendingStatusRunnable = new Runnable()
  {
    public void run()
    {
      if (MessageDetailAdapter.this.context == null);
      do
        return;
      while (!MessageDetailAdapter.this.updateSendingState());
      MessageDetailAdapter.this.mSendingStatusStep = ((1 + MessageDetailAdapter.this.mSendingStatusStep) % 4);
      MessageDetailAdapter.this.mHandler.postDelayed(MessageDetailAdapter.this.mUpdateSendingStatusRunnable, 500L);
    }
  };

  public MessageDetailAdapter(BaseFragment paramBaseFragment, Handler paramHandler, ListView paramListView, ArrayList<MessageDetailItem> paramArrayList)
  {
    this.mListMessages = paramArrayList;
    this.mFragment = paramBaseFragment;
    this.context = paramBaseFragment.getActivity();
    this.mHandler = paramHandler;
    this.mListView = paramListView;
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
      paramTextView.setText(StringUtil.replaceTokenWith(this.context.getResources().getString(2131427564), "*", String.valueOf(i)));
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
              Toast.makeText(MessageDetailAdapter.this.context, 2131427853, 0).show();
            }
          });
          return;
        }
        MessageAttachmentItem localMessageAttachmentItem = (MessageAttachmentItem)localArrayList.get(j);
        String str1 = localMessageAttachmentItem.getmAttmName();
        String str2 = localMessageAttachmentItem.getmAttmSize();
        View localView = this.context.getLayoutInflater().inflate(2130903217, null);
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
      if (countDayOff(((MessageDetailItem)this.mListMessages.get(paramInt - 1)).getCtime().longValue(), l2) == 0)
        j = 0;
    }
    if (this.mIsPushDialogActivity)
      j = 0;
    if (j != 0)
    {
      if (this.currentDay == i);
      for (String str = this.context.getString(2131427563); ; str = DateFormat.format("yyyy-MM-dd", l1).toString())
      {
        paramTextView.setText(str);
        paramTextView.setVisibility(0);
        return;
      }
    }
    paramTextView.setVisibility(8);
  }

  private boolean showMessageSendingState(MessageDetailItem paramMessageDetailItem, ImageView paramImageView, TextView paramTextView, View paramView, boolean paramBoolean)
  {
    if ((paramMessageDetailItem == null) || (paramImageView == null) || (paramTextView == null) || (paramView == null))
      return false;
    String str = this.context.getString(2131427995);
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
          label123: if (k == 0)
            break label300;
          paramTextView.setText(2131427592);
          paramTextView.setTextColor(this.context.getResources().getColor(2130839419));
          paramTextView.setGravity(3);
          paramImageView.setImageResource(2130838884);
          paramImageView.setVisibility(0);
          label169: paramTextView.setVisibility(0);
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
        break label123;
      if (!paramBoolean)
        localKXUploadTask.setTag("task_finished");
      k = 1;
      i = 1;
      break label123;
      label300: paramTextView.setText(MessageCenterModel.formatTimestamp(1000L * paramMessageDetailItem.getCtime().longValue()));
      paramTextView.setTextColor(this.context.getResources().getColor(2130839395));
      paramTextView.setGravity(5);
      paramImageView.setVisibility(8);
      break label169;
      if ((paramMessageDetailItem.getStatues() == 1) || (paramMessageDetailItem.getStatues() == 0))
      {
        paramTextView.setText(str);
        paramTextView.setTextColor(this.context.getResources().getColor(2130839419));
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
      paramTextView.setTextColor(this.context.getResources().getColor(2130839389));
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
          Activity localActivity = MessageDetailAdapter.this.context;
          String[] arrayOfString = new String[2];
          arrayOfString[0] = MessageDetailAdapter.access$0(MessageDetailAdapter.this).getString(2131427999);
          arrayOfString[1] = MessageDetailAdapter.access$0(MessageDetailAdapter.this).getString(2131428000);
          DialogUtil.showSelectListDlg(localActivity, 2131427996, arrayOfString, new DialogInterface.OnClickListener(localKXUploadTask)
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
              MessageDetailAdapter.this.startSendingAnimation();
            }
          }
          , 1);
        }
      });
      i = 1;
    }
  }

  private void showMessageTime(MessageDetailItem paramMessageDetailItem, ImageView paramImageView, TextView paramTextView, View paramView, boolean paramBoolean)
  {
    if ((paramMessageDetailItem == null) || (paramImageView == null) || (paramTextView == null) || (paramView == null))
      return;
    String str = MessageCenterModel.formatTimestamp(1000L * paramMessageDetailItem.getCtime().longValue());
    if (paramBoolean)
    {
      paramTextView.setText(str);
      return;
    }
    paramImageView.setVisibility(8);
    if (paramMessageDetailItem.getStatues() == -1)
    {
      paramTextView.setTextColor(this.context.getResources().getColor(2130839395));
      paramTextView.setText(str);
      paramTextView.setGravity(5);
      return;
    }
    showMessageSendingState(paramMessageDetailItem, paramImageView, paramTextView, paramView, true);
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

  public int countDayOff(long paramLong1, long paramLong2)
  {
    this.detailItemTime.setTimeInMillis(paramLong1 * 1000L);
    int i = this.detailItemTime.get(6);
    this.detailItemTime.setTimeInMillis(paramLong2 * 1000L);
    return i - this.detailItemTime.get(6);
  }

  public int getCount()
  {
    if (this.mListMessages == null)
      return 0;
    return this.mListMessages.size();
  }

  public Object getItem(int paramInt)
  {
    if (this.mListMessages == null);
    do
      return null;
    while ((paramInt < 0) || (paramInt >= this.mListMessages.size()));
    return this.mListMessages.get(paramInt);
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    boolean bool = true;
    MessageDetailViewTag localMessageDetailViewTag;
    if (paramView == null)
    {
      paramView = this.context.getLayoutInflater().inflate(2130903223, paramViewGroup, false);
      localMessageDetailViewTag = new MessageDetailViewTag(paramView, null);
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
      String str4 = localMessageDetailItem.getFuid();
      String str5 = localMessageDetailItem.getFname();
      if ((str5 == null) || (str5.equals(this.context.getString(2131427565))))
        str5 = User.getInstance().getRealName();
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
        label216: KXIntroView.OnClickLinkListener localOnClickLinkListener1 = (KXIntroView.OnClickLinkListener)this.mFragment;
        localKXIntroView.setOnClickLinkListener(localOnClickLinkListener1);
        FNameOnClickListener localFNameOnClickListener = new FNameOnClickListener(this.mFragment, str4, str5);
        localTextView1.setOnClickListener(localFNameOnClickListener);
        if (!bool)
          break label442;
        localTextView1.setText(str5);
      }
      while (true)
      {
        ArrayList localArrayList = localMessageDetailItem.makeTitleList(str3);
        localKXIntroView.setTitleList(localArrayList);
        KXIntroView.OnClickLinkListener localOnClickLinkListener2 = (KXIntroView.OnClickLinkListener)this.mFragment;
        localKXIntroView.setOnClickLinkListener(localOnClickLinkListener2);
        this.mFragment.displayPicture(localImageView, str2, 2130838676);
        localImageView.setOnClickListener(new FNameOnClickListener(this.mFragment, str4, str5));
        showMessageTime(localMessageDetailItem, localMessageDetailViewTag.mImgSendingIcon, localTextView2, localMessageDetailViewTag.mLayoutTime, bool);
        showAttachmentList(localMessageDetailItem, localTextView3, localLinearLayout);
        return paramView;
        localMessageDetailViewTag = (MessageDetailViewTag)paramView.getTag();
        break;
        localMessageDetailViewTag.mLayoutFriendMessage.setVisibility(8);
        localMessageDetailViewTag.mLayoutMyMessage.setVisibility(0);
        localTextView1 = localMessageDetailViewTag.mTxtMyName;
        localTextView2 = localMessageDetailViewTag.mTxtMyTime;
        localKXIntroView = localMessageDetailViewTag.mViewMyContent;
        localTextView3 = localMessageDetailViewTag.mTxtMyAttachement;
        localImageView = localMessageDetailViewTag.mImgMyAvatar;
        localLinearLayout = localMessageDetailViewTag.mLayoutMyAttachmentList;
        break label216;
        label442: localTextView1.setText(2131427565);
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

  public void setIsPushDialogActivity(boolean paramBoolean)
  {
    this.mIsPushDialogActivity = paramBoolean;
  }

  public void startSendingAnimation()
  {
    stopSendingAnimation();
    this.mSendingStatusStep = 0;
    this.mHandler.postDelayed(this.mUpdateSendingStatusRunnable, 500L);
  }

  public void stopSendingAnimation()
  {
    this.mHandler.removeCallbacks(this.mUpdateSendingStatusRunnable);
  }

  static class FNameOnClickListener
    implements View.OnClickListener
  {
    Activity context;
    private String fname;
    private BaseFragment fragment;
    private String fuid;

    public FNameOnClickListener(BaseFragment paramBaseFragment, String paramString1, String paramString2)
    {
      this.fuid = paramString1;
      this.fname = paramString2;
      this.fragment = paramBaseFragment;
      this.context = paramBaseFragment.getActivity();
    }

    public void onClick(View paramView)
    {
      paramView.setBackgroundResource(2130838512);
      if ((this.fuid != null) && (this.fname != null))
        IntentUtil.showHomeFragment(this.fragment, this.fuid, this.fname);
    }
  }

  static class MessageDetailViewTag
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
 * Qualified Name:     com.kaixin001.adapter.MessageDetailAdapter
 * JD-Core Version:    0.6.0
 */