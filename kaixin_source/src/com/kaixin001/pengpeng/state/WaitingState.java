package com.kaixin001.pengpeng.state;

import com.kaixin001.pengpeng.IShakeUIAdapter;
import com.kaixin001.pengpeng.data.BumpFriend;
import com.kaixin001.pengpeng.data.KXDataManager;
import com.kaixin001.pengpeng.data.KXDataUpdateListener;
import com.kaixin001.util.KXLog;

public class WaitingState extends KXState
  implements KXDataUpdateListener
{
  private static final String TAG = "WaitingState";

  public WaitingState(ShakeStateMachine paramShakeStateMachine, IShakeUIAdapter paramIShakeUIAdapter)
  {
    super(paramShakeStateMachine, paramIShakeUIAdapter);
  }

  protected void active()
  {
    super.active();
    BumpFriend localBumpFriend = (BumpFriend)this.dataManagerInstance.getObjectRecord("otherinfo");
    this.uiListener.setStatusWaiting(localBumpFriend);
    if (!onRecordUpdate("otheragree"))
      this.dataManagerInstance.monitorRecord("otheragree", this);
    this.dataManagerInstance.monitorRecord("otherinfo", this);
    this.dataManagerInstance.monitorRecord("ret", this);
  }

  protected void deActive()
  {
    super.deActive();
    this.dataManagerInstance.unMonitorRecord("otheragree", this);
    this.dataManagerInstance.unMonitorRecord("otherinfo", this);
    this.dataManagerInstance.unMonitorRecord("ret", this);
  }

  public boolean onRecordUpdate(String paramString)
  {
    if (!this.isActive);
    int i;
    do
    {
      while (true)
      {
        return false;
        if (!"otheragree".equals(paramString))
          break;
        int j = this.dataManagerInstance.getIntRecord("otheragree");
        if (j == 0)
        {
          this.dataManagerInstance.setStringRecord("againreason", "对方拒绝了你的匹配");
          this.dataManagerInstance.setStringRecord("method", "bump.getSystime");
          this.stateMachine.startState(1);
          this.uiListener.setOtherUserCanceled((BumpFriend)this.dataManagerInstance.getObjectRecord("otherinfo"));
          return true;
        }
        if (j == 1)
        {
          if (this.dataManagerInstance.getObjectRecord("otherinfo") != null)
            this.stateMachine.startState(5);
          return true;
        }
        if (j == 2)
          return false;
      }
      if ("otherinfo".equals(paramString))
        if (this.dataManagerInstance.getIntRecord("otheragree") == 1)
        {
          this.stateMachine.startState(5);
          BumpFriend localBumpFriend = (BumpFriend)this.dataManagerInstance.getObjectRecord("otherinfo");
          this.uiListener.setStatusExchange(localBumpFriend);
        }
      do
        return true;
      while (!"ret".endsWith(paramString));
      i = this.dataManagerInstance.getIntRecord(paramString);
    }
    while (i != 9001);
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.valueOf(i);
    KXLog.w("WaitingState", "%%%%%%  WaitingState monited [ret]=%d", arrayOfObject);
    this.dataManagerInstance.setRecordSendFlag("randnumT", 0);
    this.dataManagerInstance.setStringRecord("method", "bump.getSystime");
    this.uiListener.setBumpTimeout();
    return true;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.pengpeng.state.WaitingState
 * JD-Core Version:    0.6.0
 */