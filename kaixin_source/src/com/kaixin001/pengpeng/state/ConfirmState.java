package com.kaixin001.pengpeng.state;

import com.kaixin001.pengpeng.IShakeUIAdapter;
import com.kaixin001.pengpeng.data.BumpFriend;
import com.kaixin001.pengpeng.data.KXDataManager;
import com.kaixin001.pengpeng.data.KXDataUpdateListener;
import com.kaixin001.util.KXLog;

public class ConfirmState extends KXState
  implements KXDataUpdateListener
{
  private static final String TAG = "ConfirmState";

  public ConfirmState(ShakeStateMachine paramShakeStateMachine, IShakeUIAdapter paramIShakeUIAdapter)
  {
    super(paramShakeStateMachine, paramIShakeUIAdapter);
  }

  protected void active()
  {
    super.active();
    this.uiListener.setStatusConfirm((BumpFriend)this.dataManagerInstance.getObjectRecord("otherinfo"));
    if (!onRecordUpdate("otheragree"))
      this.dataManagerInstance.monitorRecord("otheragree", this);
    this.dataManagerInstance.monitorRecord("ret", this);
  }

  protected void deActive()
  {
    super.deActive();
    this.dataManagerInstance.unMonitorRecord("otheragree", this);
    this.dataManagerInstance.unMonitorRecord("ret", this);
  }

  public boolean onRecordUpdate(String paramString)
  {
    if (!this.isActive);
    while (true)
    {
      return false;
      if ("otheragree".equals(paramString))
      {
        int j = this.dataManagerInstance.getIntRecord("otheragree");
        if (j == 0)
        {
          this.dataManagerInstance.setStringRecord("againreason", "对方拒绝了你的匹配");
          this.dataManagerInstance.setStringRecord("method", "bump.getSystime");
          this.uiListener.setOtherUserCanceled((BumpFriend)this.dataManagerInstance.getObjectRecord("otherinfo"));
          this.stateMachine.startState(1);
          return true;
        }
        if (j != 1)
          continue;
        KXLog.w("ConfirmState", "Confiremstate has get otherAgree == 1");
        return true;
      }
      if (!"ret".endsWith(paramString))
        break;
      int i = this.dataManagerInstance.getIntRecord(paramString);
      if (i != 9001)
        continue;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(i);
      KXLog.w("ConfirmState", "%%%%%%  MatchingState monited [ret]=%d", arrayOfObject);
      this.dataManagerInstance.setRecordSendFlag("randnumT", 0);
      this.dataManagerInstance.setRecordSendFlag("randnum", 0);
      this.dataManagerInstance.setRecordSendFlag("lon", 0);
      this.dataManagerInstance.setRecordSendFlag("lat", 0);
      this.dataManagerInstance.setRecordSendFlag("accuracy", 0);
      this.dataManagerInstance.setStringRecord("method", "bump.getSystime");
      this.uiListener.setBumpTimeout();
      return true;
    }
    return true;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.pengpeng.state.ConfirmState
 * JD-Core Version:    0.6.0
 */