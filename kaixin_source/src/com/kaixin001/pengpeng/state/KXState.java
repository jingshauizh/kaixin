package com.kaixin001.pengpeng.state;

import android.os.Handler;
import com.kaixin001.pengpeng.IShakeUIAdapter;
import com.kaixin001.pengpeng.data.KXDataManager;
import com.kaixin001.util.KXLog;

public abstract class KXState
{
  private static final String TAG = "StateMachine";
  protected KXDataManager dataManagerInstance = KXDataManager.getInstance();
  protected boolean isActive = false;
  protected ShakeStateMachine stateMachine;
  protected Handler uiHandler;
  protected IShakeUIAdapter uiListener;

  public KXState(ShakeStateMachine paramShakeStateMachine, IShakeUIAdapter paramIShakeUIAdapter)
  {
    this.stateMachine = paramShakeStateMachine;
    this.uiListener = paramIShakeUIAdapter;
  }

  protected void active()
  {
    this.isActive = true;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = getClass().getName();
    KXLog.w("StateMachine", "**************  State %s ACTIVATE *******", arrayOfObject);
  }

  protected void deActive()
  {
    this.isActive = false;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = getClass().getName();
    KXLog.w("StateMachine", "**************  State %s DEACTIVATE *******", arrayOfObject);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.pengpeng.state.KXState
 * JD-Core Version:    0.6.0
 */