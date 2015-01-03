package com.kaixin001.network;

public class HttpException extends Exception
{
  private static final long serialVersionUID = 1L;

  public HttpException(String paramString)
  {
    super(paramString);
  }

  public HttpException(String paramString, Throwable paramThrowable)
  {
    super(paramString, paramThrowable);
  }

  public HttpException(Throwable paramThrowable)
  {
    super(paramThrowable);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.network.HttpException
 * JD-Core Version:    0.6.0
 */