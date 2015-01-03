package com.kaixin001.model;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.kaixin001.activity.MainActivity;
import com.kaixin001.engine.CommentEngine;
import com.kaixin001.engine.MessageEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.engine.UserCommentEngine;
import com.kaixin001.item.CommentItem;
import com.kaixin001.item.MessageDetailInfo;
import com.kaixin001.item.MessageDetailItem;
import com.kaixin001.item.MessageItem;
import com.kaixin001.item.UserCommentItem;
import com.kaixin001.util.ActivityUtil;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;
import java.util.Iterator;

public class MessageNotifyModel extends KXModel
{
  public static final int EVENT_UPDATE = 12011;
  private static MessageNotifyModel instance = null;
  private Handler mHandler = null;
  private ArrayList<MessageNotifyItem> mNotifyItems = new ArrayList();

  private MessageNotifyItem combineTwoItems(MessageNotifyItem paramMessageNotifyItem1, MessageNotifyItem paramMessageNotifyItem2)
  {
    ArrayList localArrayList1 = paramMessageNotifyItem1.getDetailItems();
    ArrayList localArrayList2 = paramMessageNotifyItem2.getDetailItems();
    MessageDetailItem localMessageDetailItem1 = (MessageDetailItem)localArrayList1.get(-1 + localArrayList1.size());
    for (int i = 0; ; i++)
    {
      if (i >= localArrayList2.size())
        return paramMessageNotifyItem1;
      MessageDetailItem localMessageDetailItem2 = (MessageDetailItem)localArrayList2.get(i);
      if (localMessageDetailItem2.getCtime().longValue() <= localMessageDetailItem1.getCtime().longValue())
        continue;
      localArrayList1.add(localMessageDetailItem2);
    }
  }

  private int findMessageNotifyItemById(String paramString)
  {
    if (TextUtils.isEmpty(paramString));
    MessageNotifyItem localMessageNotifyItem;
    do
    {
      Iterator localIterator;
      while (!localIterator.hasNext())
      {
        return -1;
        localIterator = this.mNotifyItems.iterator();
      }
      localMessageNotifyItem = (MessageNotifyItem)localIterator.next();
    }
    while (!paramString.equals(localMessageNotifyItem.getId()));
    return this.mNotifyItems.indexOf(localMessageNotifyItem);
  }

  private int getFetchNum(int paramInt)
  {
    if (paramInt < 5)
      return paramInt + 3;
    if (paramInt < 20)
      return paramInt + 5;
    return paramInt + 8;
  }

  public static MessageNotifyModel getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new MessageNotifyModel();
      MessageNotifyModel localMessageNotifyModel = instance;
      return localMessageNotifyModel;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public boolean addItem(Context paramContext, MessageNotifyItem paramMessageNotifyItem)
  {
    while (true)
    {
      int i;
      long l;
      int j;
      synchronized (this.mNotifyItems)
      {
        KXLog.d("TEST", "MessageNotifyModel addItem()...");
        if ((paramMessageNotifyItem == null) || (paramMessageNotifyItem.getDetailItems() == null) || (paramMessageNotifyItem.getDetailItems().size() == 0))
        {
          return false;
          if (i < this.mNotifyItems.size())
            continue;
          if (this.mNotifyItems.size() != 0)
            continue;
          this.mNotifyItems.add(paramMessageNotifyItem);
          KXLog.d("TEST", "mNotifyItems.size() = " + this.mNotifyItems.size());
          Message localMessage = Message.obtain();
          localMessage.what = 12011;
          localMessage.obj = paramMessageNotifyItem.getId();
          if (this.mHandler == null)
            continue;
          this.mHandler.sendMessage(localMessage);
          checkPushMessageActivity(paramContext);
          return true;
          MessageNotifyItem localMessageNotifyItem = (MessageNotifyItem)this.mNotifyItems.get(i);
          if (!paramMessageNotifyItem.getId().equals(localMessageNotifyItem.getId()))
            break label290;
          this.mNotifyItems.remove(i);
          paramMessageNotifyItem = combineTwoItems(localMessageNotifyItem, paramMessageNotifyItem);
          continue;
          l = paramMessageNotifyItem.getLatestDetailItemTime();
          j = -1 + this.mNotifyItems.size();
          break label296;
          label215: if (k != 0)
            continue;
          this.mNotifyItems.add(0, paramMessageNotifyItem);
        }
      }
      label290: label296: 
      do
      {
        if (l > ((MessageNotifyItem)this.mNotifyItems.get(j)).getLatestDetailItemTime())
        {
          this.mNotifyItems.add(j + 1, paramMessageNotifyItem);
          k = 1;
          break label215;
        }
        j--;
        continue;
        i = 0;
        break;
        i++;
        break;
      }
      while (j >= 0);
      int k = 0;
    }
  }

  public void checkPushMessageActivity(Context paramContext)
  {
    KXLog.d("TEST", "checkPushMessageActivity()...");
    if (this.mHandler == null)
    {
      boolean bool = ActivityUtil.checkKXActivityExist(paramContext);
      KXLog.d("TEST", "checkPushMessageActivity(): isAnyKaixinActivityExist = " + bool);
      if ((!bool) && (getSize() > 0))
      {
        Intent localIntent = new Intent(paramContext, MainActivity.class);
        localIntent.setFlags(268435456);
        localIntent.putExtra("fragment", "MessagePushDialogFragment");
        paramContext.startActivity(localIntent);
        KXLog.d("TEST", "startActivity:MessagePushDialogActivity");
      }
    }
  }

  public void clear()
  {
    synchronized (this.mNotifyItems)
    {
      this.mNotifyItems.clear();
      return;
    }
  }

  public MessageNotifyItem getMessageNotifyItem(Context paramContext, CommentItem paramCommentItem, int paramInt)
    throws SecurityErrorException
  {
    int i = findMessageNotifyItemById(paramCommentItem.getThread_cid());
    int j = -1;
    String str = null;
    if (i == -1)
    {
      j = getFetchNum(paramCommentItem.getUnread());
      if (paramInt != 203)
        break label214;
    }
    MessageNotifyItem localMessageNotifyItem;
    label214: for (int k = CommentEngine.getInstance().doGetCommentDetail(paramContext, paramCommentItem.getThread_cid(), "0", j, str, null); ; k = CommentEngine.getInstance().doGetReplyCommentDetail(paramContext, paramCommentItem.getThread_cid(), paramCommentItem.getFuid(), "0", j, str, null))
    {
      localMessageNotifyItem = new MessageNotifyItem();
      localMessageNotifyItem.threadType = paramInt;
      localMessageNotifyItem.setId(paramCommentItem.getThread_cid());
      localMessageNotifyItem.setThreadItem(paramCommentItem);
      if (k != 1)
        break label241;
      ArrayList localArrayList2 = MessageCenterModel.getInstance().getMessageDetailList();
      ArrayList localArrayList3 = new ArrayList();
      localArrayList3.addAll(localArrayList2);
      localMessageNotifyItem.setDetailItems(localArrayList3);
      localMessageNotifyItem.mDetailInfo = MessageCenterModel.getInstance().getActiveMesasgeDetail();
      localMessageNotifyItem.setMType(paramCommentItem.getMtype());
      return localMessageNotifyItem;
      String.valueOf(paramCommentItem.getCtime());
      ArrayList localArrayList1 = ((MessageNotifyItem)this.mNotifyItems.get(i)).getDetailItems();
      str = String.valueOf(((MessageDetailItem)localArrayList1.get(-1 + localArrayList1.size())).getCtime());
      break;
    }
    label241: localMessageNotifyItem.unreadNum = paramCommentItem.getUnread();
    return localMessageNotifyItem;
  }

  public MessageNotifyItem getMessageNotifyItem(Context paramContext, MessageItem paramMessageItem, int paramInt)
    throws SecurityErrorException
  {
    MessageCenterModel localMessageCenterModel = MessageCenterModel.getInstance();
    String str1 = paramMessageItem.getMid();
    int i = findMessageNotifyItemById(str1);
    int j = -1;
    String str2 = null;
    if (i == -1)
      j = getFetchNum(paramMessageItem.getUnread());
    MessageNotifyItem localMessageNotifyItem;
    while (true)
    {
      int k = MessageEngine.getInstance().doGetMessageDetail(paramContext, str1, true, "0", j, str2, null);
      localMessageNotifyItem = new MessageNotifyItem();
      localMessageNotifyItem.threadType = paramInt;
      localMessageNotifyItem.setId(str1);
      localMessageNotifyItem.setThreadItem(paramMessageItem);
      if (k != 1)
        break;
      ArrayList localArrayList2 = localMessageCenterModel.getMessageDetailList();
      ArrayList localArrayList3 = new ArrayList();
      localArrayList3.addAll(localArrayList2);
      localMessageNotifyItem.setDetailItems(localArrayList3);
      localMessageNotifyItem.mDetailInfo = MessageCenterModel.getInstance().getActiveMesasgeDetail();
      localMessageNotifyItem.setMType(paramMessageItem.getMtype());
      return localMessageNotifyItem;
      String.valueOf(paramMessageItem.getCtime());
      ArrayList localArrayList1 = ((MessageNotifyItem)this.mNotifyItems.get(i)).getDetailItems();
      str2 = String.valueOf(((MessageDetailItem)localArrayList1.get(-1 + localArrayList1.size())).getCtime());
    }
    localMessageNotifyItem.unreadNum = paramMessageItem.getUnread();
    return localMessageNotifyItem;
  }

  public MessageNotifyItem getMessageNotifyItem(Context paramContext, UserCommentItem paramUserCommentItem, int paramInt)
    throws SecurityErrorException
  {
    int i = findMessageNotifyItemById(paramUserCommentItem.getThread_cid());
    int j = -1;
    String str = null;
    if (i == -1)
    {
      j = getFetchNum(paramUserCommentItem.getUnread());
      if (paramInt != 205)
        break label214;
    }
    MessageNotifyItem localMessageNotifyItem;
    label214: for (int k = UserCommentEngine.getInstance().doGetUserCommentDetail(paramContext, paramUserCommentItem.getThread_cid(), "0", j, str, null); ; k = UserCommentEngine.getInstance().doGetSentUserCommentDetail(paramContext, paramUserCommentItem.getThread_cid(), "0", j, str, null))
    {
      localMessageNotifyItem = new MessageNotifyItem();
      localMessageNotifyItem.threadType = paramInt;
      localMessageNotifyItem.setId(paramUserCommentItem.getThread_cid());
      localMessageNotifyItem.setThreadItem(paramUserCommentItem);
      if (k != 1)
        break label237;
      ArrayList localArrayList2 = MessageCenterModel.getInstance().getMessageDetailList();
      ArrayList localArrayList3 = new ArrayList();
      localArrayList3.addAll(localArrayList2);
      localMessageNotifyItem.setDetailItems(localArrayList3);
      localMessageNotifyItem.mDetailInfo = MessageCenterModel.getInstance().getActiveMesasgeDetail();
      localMessageNotifyItem.setMType(paramUserCommentItem.getMtype());
      return localMessageNotifyItem;
      String.valueOf(paramUserCommentItem.getCtime());
      ArrayList localArrayList1 = ((MessageNotifyItem)this.mNotifyItems.get(i)).getDetailItems();
      str = String.valueOf(((MessageDetailItem)localArrayList1.get(-1 + localArrayList1.size())).getCtime());
      break;
    }
    label237: localMessageNotifyItem.unreadNum = paramUserCommentItem.getUnread();
    return localMessageNotifyItem;
  }

  public int getSize()
  {
    synchronized (this.mNotifyItems)
    {
      int i = this.mNotifyItems.size();
      return i;
    }
  }

  public MessageNotifyItem getTop()
  {
    synchronized (this.mNotifyItems)
    {
      int i = this.mNotifyItems.size();
      MessageNotifyItem localMessageNotifyItem = null;
      if (i > 0)
        localMessageNotifyItem = (MessageNotifyItem)this.mNotifyItems.get(-1 + this.mNotifyItems.size());
      return localMessageNotifyItem;
    }
  }

  public void removeTop()
  {
    synchronized (this.mNotifyItems)
    {
      if (this.mNotifyItems.size() > 0)
        this.mNotifyItems.remove(-1 + this.mNotifyItems.size());
      return;
    }
  }

  public void removeTop(String paramString)
  {
    int i;
    synchronized (this.mNotifyItems)
    {
      if (this.mNotifyItems.size() > 0)
      {
        i = -1 + this.mNotifyItems.size();
      }
      else
      {
        return;
        label34: if (!((MessageNotifyItem)this.mNotifyItems.get(i)).getId().equals(paramString))
          break label82;
        this.mNotifyItems.remove(i);
      }
    }
    while (true)
    {
      if (i >= 0)
        break label34;
      break;
      label82: i--;
    }
  }

  public void setHandler(Handler paramHandler)
  {
    this.mHandler = paramHandler;
  }

  public static class MessageNotifyItem
  {
    private Context mContext;
    public MessageDetailInfo mDetailInfo;
    private ArrayList<MessageDetailItem> mDetailItems;
    private String mId;
    private String mType = "0";
    private Object threadItem;
    public int threadType;
    public int unreadNum;

    public Context getContext()
    {
      return this.mContext;
    }

    public ArrayList<MessageDetailItem> getDetailItems()
    {
      return this.mDetailItems;
    }

    public String getFname()
    {
      if (this.threadType == 201)
        return ((MessageItem)getThreadItem()).getFname();
      if ((this.threadType == 205) || (this.threadType == 206))
        return ((UserCommentItem)getThreadItem()).getFname();
      if (this.threadType == 203)
        return User.getInstance().getRealName();
      if (this.threadType == 204)
        return ((CommentItem)getThreadItem()).getFname();
      return null;
    }

    public String getFuid()
    {
      if (this.threadType == 201)
        return ((MessageItem)getThreadItem()).getFuid();
      if ((this.threadType == 205) || (this.threadType == 206))
        return ((UserCommentItem)getThreadItem()).getFuid();
      if (this.threadType == 203)
        return User.getInstance().getUID();
      if (this.threadType == 204)
        return ((CommentItem)getThreadItem()).getFuid();
      return null;
    }

    public String getId()
    {
      return this.mId;
    }

    public long getLatestDetailItemTime()
    {
      if ((this.mDetailItems == null) || (this.mDetailItems.size() == 0))
        return 0L;
      return ((MessageDetailItem)this.mDetailItems.get(-1 + this.mDetailItems.size())).getCtime().longValue();
    }

    public String getMType()
    {
      return this.mType;
    }

    public Object getThreadItem()
    {
      return this.threadItem;
    }

    public void setContext(Context paramContext)
    {
      this.mContext = paramContext;
    }

    public void setDetailItems(ArrayList<MessageDetailItem> paramArrayList)
    {
      this.mDetailItems = paramArrayList;
    }

    public void setId(String paramString)
    {
      this.mId = paramString;
    }

    public void setMType(String paramString)
    {
      this.mType = paramString;
    }

    public void setThreadItem(Object paramObject)
    {
      this.threadItem = paramObject;
      if ((paramObject != null) && ((paramObject instanceof MessageDetailInfo)))
        this.mType = ((MessageDetailInfo)paramObject).getType();
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.MessageNotifyModel
 * JD-Core Version:    0.6.0
 */