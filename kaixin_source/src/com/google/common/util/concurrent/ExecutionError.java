package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;

@Beta
public class ExecutionError extends Error
{
  private static final long serialVersionUID;

  protected ExecutionError()
  {
  }

  public ExecutionError(Error paramError)
  {
    super(paramError);
  }

  protected ExecutionError(String paramString)
  {
    super(paramString);
  }

  public ExecutionError(String paramString, Error paramError)
  {
    super(paramString, paramError);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.common.util.concurrent.ExecutionError
 * JD-Core Version:    0.6.0
 */