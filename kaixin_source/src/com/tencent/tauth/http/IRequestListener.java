package com.tencent.tauth.http;

import java.io.FileNotFoundException;
import java.io.IOException;

public abstract interface IRequestListener
{
  public abstract void onComplete(String paramString, Object paramObject);

  public abstract void onFileNotFoundException(FileNotFoundException paramFileNotFoundException, Object paramObject);

  public abstract void onIOException(IOException paramIOException, Object paramObject);
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.tauth.http.IRequestListener
 * JD-Core Version:    0.6.0
 */