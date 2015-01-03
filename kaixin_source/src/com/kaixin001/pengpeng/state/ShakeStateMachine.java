package com.kaixin001.pengpeng.state;

import com.kaixin001.pengpeng.IShakeUIAdapter;
import com.kaixin001.pengpeng.OnShakeListener;

public class ShakeStateMachine
  implements OnShakeListener
{
  private static final String TAG = "StateMachine";
  private int mCurrentStateIndex = -1;
  private IShakeUIAdapter mUIListener;
  private KXState[] seqStates;

  public ShakeStateMachine(IShakeUIAdapter paramIShakeUIAdapter)
  {
    this.mUIListener = paramIShakeUIAdapter;
    this.seqStates = new KXState[6];
    this.seqStates[0] = new InitializingState(this, this.mUIListener);
    this.seqStates[1] = new IdleState(this, this.mUIListener);
    this.seqStates[2] = new MatchingState(this, this.mUIListener);
    this.seqStates[3] = new ConfirmState(this, this.mUIListener);
    this.seqStates[4] = new WaitingState(this, this.mUIListener);
    this.seqStates[5] = new ExchangeState(this, this.mUIListener);
    startState(0);
  }

  public int getState()
  {
    return this.mCurrentStateIndex;
  }

  public void onShakeOccurred()
  {
    if (this.mCurrentStateIndex == 1)
      startState(2);
  }

  public void startState(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= 6))
      throw new RuntimeException("wrong state index [which]=" + paramInt);
    if ((this.mCurrentStateIndex > 0) && (this.mCurrentStateIndex < 6))
      this.seqStates[this.mCurrentStateIndex].deActive();
    this.mCurrentStateIndex = paramInt;
    this.seqStates[paramInt].active();
  }

  public void stopStateMachine()
  {
    if ((this.mCurrentStateIndex > 0) && (this.mCurrentStateIndex < 6))
      this.seqStates[this.mCurrentStateIndex].deActive();
    this.mCurrentStateIndex = -1;
  }

  public static final class StateSequence
  {
    public static final int CONFIRM_STATE = 3;
    public static final int EXCHANGE_STATE = 5;
    public static final int IDLE_STATE = 1;
    public static final int INITIALIZEING_STATE = 0;
    public static final int INVALIDATE_STATE = -1;
    public static final int MATCHING_STATE = 2;
    public static final int WAITING_STATE = 4;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.pengpeng.state.ShakeStateMachine
 * JD-Core Version:    0.6.0
 */