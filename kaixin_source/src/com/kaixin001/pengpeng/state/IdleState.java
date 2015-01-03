package com.kaixin001.pengpeng.state;

import com.kaixin001.pengpeng.IShakeUIAdapter;
import com.kaixin001.pengpeng.data.KXDataManager;
import com.kaixin001.pengpeng.data.KXDataUpdateListener;
import com.kaixin001.pengpeng.http.LinkManager;

public class IdleState extends KXState
  implements KXDataUpdateListener
{
  public IdleState(ShakeStateMachine paramShakeStateMachine, IShakeUIAdapter paramIShakeUIAdapter)
  {
    super(paramShakeStateMachine, paramIShakeUIAdapter);
  }

  protected void active()
  {
    super.active();
    LinkManager.getInstance(null).removeAllRequestFromQ();
    this.dataManagerInstance.resetRecord("randum");
    this.dataManagerInstance.resetRecord("again_reason");
    this.dataManagerInstance.resetRecord("abstime");
    this.dataManagerInstance.resetRecord("otheragree");
    this.dataManagerInstance.resetRecord("matched");
    this.dataManagerInstance.resetRecord("otherinfo");
    this.dataManagerInstance.setIntRecord("serveraffirm", 0);
    this.uiListener.setStatusIdle(0);
    this.dataManagerInstance.monitorRecord("ret", this);
  }

  public boolean onRecordUpdate(String paramString)
  {
    if (!this.isActive);
    return false;
  }

  public static class IdleReasonType
  {
    public static final int CONFIRM_CANCEL = 3;
    public static final int MATCHING_CANCEL = 2;
    public static final int NONE = 0;
    public static final int NOT_MATCHED = 1;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.pengpeng.state.IdleState
 * JD-Core Version:    0.6.0
 */