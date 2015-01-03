package com.kaixin001.businesslogic;

public abstract interface IPostUpCommand
{
  public abstract void onPreExec();

  public abstract void onResultFailed();

  public abstract void onResultSuccess();
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.businesslogic.IPostUpCommand
 * JD-Core Version:    0.6.0
 */