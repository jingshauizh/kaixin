package com.kaixin001.pengpeng.state;

import com.kaixin001.pengpeng.IShakeUIAdapter;
import com.kaixin001.pengpeng.data.BumpFriend;
import com.kaixin001.pengpeng.data.KXDataManager;
import com.kaixin001.pengpeng.data.KXDataUpdateListener;
import com.kaixin001.util.KXLog;

public class ExchangeState extends KXState
  implements KXDataUpdateListener
{
  private static final String TAG = "ExchangeState";

  public ExchangeState(ShakeStateMachine paramShakeStateMachine, IShakeUIAdapter paramIShakeUIAdapter)
  {
    super(paramShakeStateMachine, paramIShakeUIAdapter);
  }

  protected void active()
  {
    super.active();
    if (!onRecordUpdate("serveraffirm"))
      this.dataManagerInstance.monitorRecord("serveraffirm", this);
  }

  protected void deActive()
  {
    super.deActive();
    this.dataManagerInstance.unMonitorRecord("serveraffirm", this);
  }

  public boolean onRecordUpdate(String paramString)
  {
    if (!this.isActive);
    int i;
    do
    {
      do
        return false;
      while (!"serveraffirm".equals(paramString));
      i = this.dataManagerInstance.getIntRecord("serveraffirm");
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(i);
      KXLog.w("ExchangeState", "------- onRecordUpdate: [serveraffirm]=%d", arrayOfObject);
    }
    while (i != 3);
    this.dataManagerInstance.setRecordSendFlag("randnumT", 0);
    this.dataManagerInstance.setStringRecord("method", "bump.getSystime");
    BumpFriend localBumpFriend = (BumpFriend)this.dataManagerInstance.getObjectRecord("otherinfo");
    this.uiListener.setStatusExchange(localBumpFriend);
    return true;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.pengpeng.state.ExchangeState
 * JD-Core Version:    0.6.0
 */