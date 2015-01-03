package com.google.common.util.concurrent;

public class UncheckedTimeoutException extends RuntimeException
{
  private static final long serialVersionUID;

  public UncheckedTimeoutException()
  {
  }

  public UncheckedTimeoutException(String paramString)
  {
    super(paramString);
  }

  public UncheckedTimeoutException(String paramString, Throwable paramThrowable)
  {
    super(paramString, paramThrowable);
  }

  public UncheckedTimeoutException(Throwable paramThrowable)
  {
    super(paramThrowable);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.common.util.concurrent.UncheckedTimeoutException
 * JD-Core Version:    0.6.0
 */