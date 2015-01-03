package com.kaixin001.item;

import android.content.Context;
import android.os.Message;
import com.kaixin001.activity.MessageHandlerHolder;
import com.kaixin001.engine.ChatEngine;
import com.kaixin001.model.ChatModel;
import java.net.URLDecoder;

public class ChatMessageTask extends KXUploadTask
{
  private static final int RET_AUTHORIZED = 8001;
  private static final int RET_SUCCESS = 1;
  private ChatInfoItem mItem = null;
  String mnDesID = null;
  String msContent = null;

  public ChatMessageTask(Context paramContext)
  {
    super(paramContext);
  }

  public void deleteDraft()
  {
  }

  public void doCancel()
  {
  }

  public boolean doUpload()
  {
    boolean bool1;
    int i;
    boolean bool2;
    if ((getTaskType() == 12) || (getTaskType() == 11))
    {
      ChatEngine localChatEngine1 = ChatEngine.getInstance();
      Context localContext1 = getContext();
      String str1 = this.mnDesID;
      if (getTaskType() == 11)
      {
        bool1 = true;
        i = localChatEngine1.sendClearUnreadFlag(localContext1, str1, bool1);
        if (i == 0)
        {
          i = 1;
          ChatEngine localChatEngine2 = ChatEngine.getInstance();
          Context localContext2 = getContext();
          String str2 = this.mnDesID;
          if (getTaskType() != 11)
            break label132;
          bool2 = true;
          label91: localChatEngine2.sendClearUnreadFlag(localContext2, str2, bool2);
        }
      }
    }
    while (true)
    {
      if (i != 1)
        break label165;
      if (this.mItem != null)
        this.mItem.mSendStatus = 0;
      return true;
      bool1 = false;
      break;
      label132: bool2 = false;
      break label91;
      i = ChatEngine.getInstance().chatSendMsg(getContext(), this.msContent, this.mnDesID, getTaskType());
    }
    label165: if (getTaskType() == 8)
      if (i == 8001)
      {
        Message localMessage = Message.obtain();
        localMessage.what = 90014;
        MessageHandlerHolder.getInstance().fireMessage(localMessage);
      }
    while (true)
    {
      if (this.mItem != null)
        this.mItem.mSendStatus = 2;
      return false;
      ChatModel.getInstance().addSendChatInfo(this.mnDesID, URLDecoder.decode(this.msContent), true, 2).mSendStatus = 3;
      continue;
      if (getTaskType() != 6)
        continue;
      ChatModel.getInstance().addSendChatInfo(this.mnDesID, URLDecoder.decode(this.msContent), false, 2).mSendStatus = 3;
    }
  }

  public void setChatInfoItem(ChatInfoItem paramChatInfoItem)
  {
    this.mItem = paramChatInfoItem;
  }

  public void setInitData(String paramString1, String paramString2, int paramInt)
  {
    setTaskType(paramInt);
    this.mnDesID = paramString2;
    this.msContent = paramString1;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.item.ChatMessageTask
 * JD-Core Version:    0.6.0
 */