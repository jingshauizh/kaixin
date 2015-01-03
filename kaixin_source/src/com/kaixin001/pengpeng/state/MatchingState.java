package com.kaixin001.pengpeng.state;

import com.kaixin001.pengpeng.IShakeUIAdapter;
import com.kaixin001.pengpeng.data.KXDataManager;
import com.kaixin001.pengpeng.data.KXDataUpdateListener;
import com.kaixin001.pengpeng.http.LinkManager;
import com.kaixin001.util.KXLog;

public class MatchingState extends KXState
  implements KXDataUpdateListener
{
  private static final String TAG = "MatchingState";

  public MatchingState(ShakeStateMachine paramShakeStateMachine, IShakeUIAdapter paramIShakeUIAdapter)
  {
    super(paramShakeStateMachine, paramIShakeUIAdapter);
  }

  protected void active()
  {
    super.active();
    this.dataManagerInstance.monitorRecord("matched", this);
    LinkManager.getInstance(null).removeAllRequestFromQ();
    this.dataManagerInstance.setRecordSendFlag("lat", 1);
    this.dataManagerInstance.setRecordSendFlag("lon", 1);
    this.dataManagerInstance.setRecordSendFlag("accuracy", 1);
    this.dataManagerInstance.setRecordSendFlag("abstime", 1);
    this.dataManagerInstance.setStringRecord("method", "bump.match");
    this.uiListener.setStatusMatching();
  }

  protected void deActive()
  {
    super.deActive();
    this.dataManagerInstance.unMonitorRecord("matched", this);
  }

  public boolean onRecordUpdate(String paramString)
  {
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = paramString;
    arrayOfObject[1] = Integer.valueOf(this.dataManagerInstance.getIntRecord("matched"));
    KXLog.w("MatchingState", "----- onRecordUpdated >>%s=%d -------", arrayOfObject);
    if (!this.isActive);
    do
      return false;
    while (!"matched".equals(paramString));
    if (this.dataManagerInstance.getIntRecord("matched") == 1)
    {
      this.dataManagerInstance.setStringRecord("method", "bump.getAffirm");
      this.dataManagerInstance.setRecordSendFlag("randnumT", 1);
      this.dataManagerInstance.setRecordSendFlag("randnum", 0);
      this.stateMachine.startState(3);
      return false;
    }
    this.uiListener.setNotMatched();
    this.dataManagerInstance.setStringRecord("method", "bump.getSystime");
    this.stateMachine.startState(1);
    return false;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.pengpeng.state.MatchingState
 * JD-Core Version:    0.6.0
 */