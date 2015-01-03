package com.kaixin001.adapter;

import android.app.Activity;
import android.content.res.Resources;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.kaixin001.fragment.BaseFragment;
import com.kaixin001.item.ChatInfoItem;
import com.kaixin001.item.ChatInfoItem.ChatMsg;
import com.kaixin001.item.KXLinkInfo;
import com.kaixin001.model.FriendsModel;
import com.kaixin001.model.FriendsModel.Friend;
import com.kaixin001.model.User;
import com.kaixin001.util.ImageDownloader.RoundCornerType;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.ParseNewsInfoUtil;
import com.kaixin001.view.KXIntroView;
import com.kaixin001.view.KXIntroView.OnClickLinkListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class ChatListItemAdapter extends BaseAdapter
{
  public static final int RESENDEVENT;
  protected ArrayList<ChatInfoItem> mChatList = null;
  KXIntroView.OnClickLinkListener mClickListener = new KXIntroView.OnClickLinkListener()
  {
    public void onClick(KXLinkInfo paramKXLinkInfo)
    {
      if (paramKXLinkInfo.getType().equals("0"))
        ChatListItemAdapter.this.mObsever.onItemEvent(0, paramKXLinkInfo.getId(), null, null);
    }
  };
  private Activity mContext = null;
  private BaseFragment mFragment;
  private LayoutInflater mLayoutInflater = null;
  private final HashMap<String, FriendsModel.Friend> mMapFriends = new HashMap();
  private ItemEventObserver mObsever = null;
  private int mSendingStep = 0;

  public ChatListItemAdapter(BaseFragment paramBaseFragment, ArrayList<ChatInfoItem> paramArrayList, ItemEventObserver paramItemEventObserver)
  {
    this.mObsever = paramItemEventObserver;
    this.mFragment = paramBaseFragment;
    this.mContext = paramBaseFragment.getActivity();
    this.mLayoutInflater = ((LayoutInflater)this.mContext.getSystemService("layout_inflater"));
    this.mChatList = paramArrayList;
  }

  private ArrayList<KXLinkInfo> getErrorMsgStr(ChatInfoItem paramChatInfoItem)
  {
    ChatInfoItem.ChatMsg localChatMsg = (ChatInfoItem.ChatMsg)paramChatInfoItem.mSubObject;
    String str1;
    ArrayList localArrayList;
    int i;
    String str2;
    if (localChatMsg.mContent.length() > 10)
    {
      str1 = localChatMsg.mContent.substring(0, 10) + "...";
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(this.mContext.getString(2131427354));
      localStringBuilder.append(str1);
      localStringBuilder.append(this.mContext.getString(2131427355));
      localArrayList = ParseNewsInfoUtil.parseCommentAndReplyLinkString(localStringBuilder.toString());
      i = localArrayList.size();
      str2 = String.valueOf(10066329);
    }
    for (int j = 0; ; j++)
    {
      if (j >= i)
      {
        if (paramChatInfoItem.mSendStatus != 3)
          break label234;
        KXLinkInfo localKXLinkInfo1 = new KXLinkInfo();
        localKXLinkInfo1.setContent(this.mContext.getString(2131427356));
        localKXLinkInfo1.setType("0");
        localKXLinkInfo1.setId(Long.toString(localChatMsg.mUniqueID));
        localArrayList.add(localKXLinkInfo1);
        return localArrayList;
        str1 = localChatMsg.mContent;
        break;
      }
      ((KXLinkInfo)localArrayList.get(j)).setType("-101");
      ((KXLinkInfo)localArrayList.get(j)).setId(str2);
    }
    label234: KXLinkInfo localKXLinkInfo2 = new KXLinkInfo();
    localKXLinkInfo2.setContent(this.mContext.getString(2131427356));
    localKXLinkInfo2.setType("-101");
    localKXLinkInfo2.setId(str2);
    localArrayList.add(localKXLinkInfo2);
    return localArrayList;
  }

  private FriendsModel.Friend getFriend(String paramString)
  {
    FriendsModel.Friend localFriend;
    if (TextUtils.isEmpty(paramString))
      localFriend = null;
    do
    {
      do
      {
        return localFriend;
        localFriend = (FriendsModel.Friend)this.mMapFriends.get(paramString);
      }
      while (localFriend != null);
      localFriend = FriendsModel.getInstance().searchOnlineFriends(paramString);
      if (localFriend != null)
        continue;
      localFriend = FriendsModel.getInstance().searchAllFriends(paramString);
      if (localFriend != null)
        continue;
      localFriend = FriendsModel.getInstance().searchStars(paramString);
    }
    while (localFriend == null);
    this.mMapFriends.put(paramString, localFriend);
    return localFriend;
  }

  private float getKXIntroViewWidth(KXIntroView paramKXIntroView)
  {
    try
    {
      ArrayList localArrayList = paramKXIntroView.getTitleList();
      StringBuilder localStringBuilder = new StringBuilder();
      for (int i = 0; ; i++)
      {
        if (i >= localArrayList.size())
          return paramKXIntroView.getPaint().measureText(localStringBuilder.toString());
        localStringBuilder.append(((KXLinkInfo)localArrayList.get(i)).getContent());
      }
    }
    catch (Exception localException)
    {
      KXLog.d("ChatAdapter", localException.toString());
    }
    return 0.0F;
  }

  private float getTextViewWidth(TextView paramTextView)
  {
    try
    {
      float f = paramTextView.getPaint().measureText(paramTextView.getText().toString());
      return f;
    }
    catch (Exception localException)
    {
      KXLog.d("ChatAdapter", localException.toString());
    }
    return 0.0F;
  }

  public void clear()
  {
    if (this.mChatList != null)
      this.mChatList.clear();
    this.mMapFriends.clear();
  }

  protected void fillView(ChatDetailCache paramChatDetailCache, ChatInfoItem paramChatInfoItem)
  {
    if ((paramChatDetailCache == null) || (paramChatInfoItem == null))
      return;
    ChatInfoItem.ChatMsg localChatMsg = (ChatInfoItem.ChatMsg)paramChatInfoItem.mSubObject;
    ArrayList localArrayList = ParseNewsInfoUtil.parseCommentAndReplyLinkString(localChatMsg.mContent);
    float f1 = this.mContext.getResources().getDisplayMetrics().density;
    if (!localChatMsg.misSend)
    {
      paramChatDetailCache.mLayoutFriend.setVisibility(0);
      paramChatDetailCache.mLayoutMe.setVisibility(8);
      FriendsModel.Friend localFriend = getFriend(localChatMsg.mSenderUID);
      if (localFriend != null)
      {
        paramChatDetailCache.mTxtFriendName.setText(localFriend.getFname());
        this.mFragment.displayRoundPicture(paramChatDetailCache.mImgFriendAvatar, localFriend.getFlogo(), ImageDownloader.RoundCornerType.hdpi_small, 2130838334);
      }
      float f5;
      while (true)
      {
        showTime(paramChatDetailCache.mTxtFriendTime, localChatMsg.mTime);
        paramChatDetailCache.mViewFriendChat.setTitleList(localArrayList);
        float f4 = getTextViewWidth(paramChatDetailCache.mTxtFriendName) + (0.5F + 10.0F * f1) + getTextViewWidth(paramChatDetailCache.mTxtFriendTime);
        f5 = getKXIntroViewWidth(paramChatDetailCache.mViewFriendChat);
        if (f4 <= f5)
          break;
        paramChatDetailCache.friendLayout.getLayoutParams().width = (int)(f4 + (0.5F + 27.0F * f1));
        return;
        if ((localChatMsg.mRealName != null) && (localChatMsg.mLogo != null))
        {
          paramChatDetailCache.mTxtFriendName.setText(localChatMsg.mRealName);
          this.mFragment.displayRoundPicture(paramChatDetailCache.mImgFriendAvatar, localChatMsg.mLogo, ImageDownloader.RoundCornerType.hdpi_small, 2130838334);
          continue;
        }
        paramChatDetailCache.mTxtFriendName.setText(null);
      }
      paramChatDetailCache.friendLayout.getLayoutParams().width = (int)(f5 + (0.5F + 27.0F * f1));
      return;
    }
    paramChatDetailCache.mLayoutFriend.setVisibility(8);
    paramChatDetailCache.mLayoutMe.setVisibility(0);
    this.mFragment.displayPicture(paramChatDetailCache.mImgMyAvatar, User.getInstance().getLogo(), 2130838676);
    paramChatDetailCache.mTxtMyName.setText(2131427565);
    showMessageSendingState(paramChatInfoItem, paramChatDetailCache.mTxtMyTime, paramChatDetailCache.mSendingMsg, this.mSendingStep);
    if ((paramChatInfoItem.mSendStatus == 3) || (5 == paramChatInfoItem.mSendStatus))
    {
      paramChatDetailCache.mViewMyChat.setTitleList(getErrorMsgStr(paramChatInfoItem));
      paramChatDetailCache.mViewMyChat.setOnClickLinkListener(this.mClickListener);
    }
    float f3;
    while (true)
    {
      float f2 = getTextViewWidth(paramChatDetailCache.mTxtMyName) + (0.5F + 10.0F * f1) + getTextViewWidth(paramChatDetailCache.mTxtMyTime);
      f3 = getKXIntroViewWidth(paramChatDetailCache.mViewMyChat);
      if (f2 <= f3)
        break;
      paramChatDetailCache.mylayout.getLayoutParams().width = (int)(f2 + (0.5F + 27.0F * f1));
      return;
      paramChatDetailCache.mViewMyChat.setTitleList(localArrayList);
    }
    paramChatDetailCache.mylayout.getLayoutParams().width = (int)(f3 + (0.5F + 27.0F * f1));
  }

  public int getCount()
  {
    if (this.mChatList == null)
      return 0;
    return this.mChatList.size();
  }

  public Object getItem(int paramInt)
  {
    if (this.mChatList == null);
    do
      return null;
    while ((paramInt < 0) || (paramInt >= this.mChatList.size()));
    return this.mChatList.get(paramInt);
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    ChatInfoItem localChatInfoItem = (ChatInfoItem)getItem(paramInt);
    if (localChatInfoItem == null)
      return paramView;
    ChatDetailCache localChatDetailCache;
    if (paramView == null)
    {
      paramView = this.mLayoutInflater.inflate(2130903064, null);
      localChatDetailCache = new ChatDetailCache(paramView);
      paramView.setTag(localChatDetailCache);
    }
    while (true)
    {
      fillView(localChatDetailCache, localChatInfoItem);
      return paramView;
      localChatDetailCache = (ChatDetailCache)paramView.getTag();
    }
  }

  public boolean showMessageSendingState(ChatInfoItem paramChatInfoItem, TextView paramTextView, ProgressBar paramProgressBar, int paramInt)
  {
    if ((paramChatInfoItem == null) || (paramTextView == null));
    ChatInfoItem.ChatMsg localChatMsg;
    do
    {
      return false;
      if ((paramChatInfoItem.mSendStatus == 1) || (paramChatInfoItem.mSendStatus == 4))
        return true;
      localChatMsg = (ChatInfoItem.ChatMsg)paramChatInfoItem.mSubObject;
    }
    while ((localChatMsg.mTime == null) || (localChatMsg.mTime.equals(paramTextView.getText().toString())));
    showTime(paramTextView, localChatMsg.mTime);
    if (paramProgressBar.getVisibility() == 0)
      paramProgressBar.setVisibility(4);
    notifyDataSetChanged();
    return false;
  }

  void showTime(TextView paramTextView, String paramString)
  {
    if (paramTextView == null)
      return;
    if (TextUtils.isEmpty(paramString))
    {
      paramTextView.setText(paramString);
      return;
    }
    Calendar localCalendar = Calendar.getInstance();
    Date localDate = new Date(localCalendar.getTimeInMillis());
    String str = new SimpleDateFormat("MM-dd ").format(localDate);
    localCalendar.getTimeInMillis();
    if (paramString.startsWith(str))
      try
      {
        paramTextView.setText(paramString.substring(str.length()));
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return;
      }
    paramTextView.setText(paramString);
  }

  public static class ChatDetailCache
  {
    public RelativeLayout friendLayout = null;
    public ImageView mImgFriendAvatar = null;
    public ImageView mImgMyAvatar = null;
    public View mLayoutFriend = null;
    public View mLayoutMe = null;
    public ProgressBar mSendingMsg = null;
    public TextView mTxtFriendName = null;
    public TextView mTxtFriendTime = null;
    public TextView mTxtMyName = null;
    public TextView mTxtMyTime = null;
    public KXIntroView mViewFriendChat = null;
    public KXIntroView mViewMyChat = null;
    public RelativeLayout mylayout = null;

    public ChatDetailCache(View paramView)
    {
      if (paramView == null)
        throw new NullPointerException();
      this.mLayoutFriend = paramView.findViewById(2131361978);
      this.mLayoutMe = paramView.findViewById(2131361986);
      this.mImgFriendAvatar = ((ImageView)paramView.findViewById(2131361980));
      this.mImgMyAvatar = ((ImageView)paramView.findViewById(2131361988));
      this.mTxtFriendName = ((TextView)paramView.findViewById(2131361983));
      this.mTxtFriendTime = ((TextView)paramView.findViewById(2131361984));
      this.mTxtMyName = ((TextView)paramView.findViewById(2131361992));
      this.mTxtMyTime = ((TextView)paramView.findViewById(2131361991));
      this.mViewFriendChat = ((KXIntroView)paramView.findViewById(2131361985));
      this.mViewMyChat = ((KXIntroView)paramView.findViewById(2131361993));
      this.mylayout = ((RelativeLayout)paramView.findViewById(2131361989));
      this.friendLayout = ((RelativeLayout)paramView.findViewById(2131361981));
      this.mSendingMsg = ((ProgressBar)paramView.findViewById(2131361994));
    }
  }

  public static abstract interface ItemEventObserver
  {
    public abstract void onItemEvent(int paramInt, String paramString1, String paramString2, String paramString3);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.adapter.ChatListItemAdapter
 * JD-Core Version:    0.6.0
 */