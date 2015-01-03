package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;

@Beta
public class UncheckedExecutionException extends RuntimeException
{
  private static final long serialVersionUID;

  protected UncheckedExecutionException()
  {
  }

  protected UncheckedExecutionException(String paramString)
  {
    super(paramString);
  }

  public UncheckedExecutionException(String paramString, Throwable paramThrowable)
  {
    super(paramString, paramThrowable);
  }

  public UncheckedExecutionException(Throwable paramThrowable)
  {
    super(paramThrowable);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.common.util.concurrent.UncheckedExecutionException
 * JD-Core Version:    0.6.0
 */