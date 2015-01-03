package com.kaixin001.pengpeng.state;

import com.kaixin001.pengpeng.IShakeUIAdapter;

public class InitializingState extends KXState
{
  public InitializingState(ShakeStateMachine paramShakeStateMachine, IShakeUIAdapter paramIShakeUIAdapter)
  {
    super(paramShakeStateMachine, paramIShakeUIAdapter);
  }

  protected void active()
  {
    super.active();
    this.uiListener.setStatusIntializeing();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.pengpeng.state.InitializingState
 * JD-Core Version:    0.6.0
 */