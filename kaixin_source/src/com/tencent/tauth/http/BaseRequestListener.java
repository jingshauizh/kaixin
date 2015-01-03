package com.tencent.tauth.http;

import java.io.FileNotFoundException;
import java.io.IOException;

public class BaseRequestListener
  implements IRequestListener
{
  private static final String TAG = "BaseRequestListener";

  public void onComplete(String paramString, Object paramObject)
  {
  }

  public void onFileNotFoundException(FileNotFoundException paramFileNotFoundException, Object paramObject)
  {
    TDebug.i("BaseRequestListener", "Resource not found:" + paramFileNotFoundException.getMessage());
  }

  public void onIOException(IOException paramIOException, Object paramObject)
  {
    TDebug.i("BaseRequestListener", "Network Error:" + paramIOException.getMessage());
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.tauth.http.BaseRequestListener
 * JD-Core Version:    0.6.0
 */