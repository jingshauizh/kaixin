package com.tencent.tauth.http;

import android.os.Bundle;
import java.io.FileNotFoundException;
import java.io.IOException;

public class AsyncHttpRequestRunner
{
  public void request(String paramString, Bundle paramBundle, IRequestListener paramIRequestListener)
  {
    request(paramString, paramBundle, "GET", paramIRequestListener, null);
  }

  public void request(String paramString1, Bundle paramBundle, String paramString2, IRequestListener paramIRequestListener, Object paramObject)
  {
    new Thread(paramString1, paramString2, paramBundle, paramIRequestListener, paramObject)
    {
      public void run()
      {
        try
        {
          String str = ClientHttpRequest.openUrl(this.val$url, this.val$httpMethod, this.val$parameters, 0);
          this.val$listener.onComplete(str, this.val$state);
          return;
        }
        catch (FileNotFoundException localFileNotFoundException)
        {
          this.val$listener.onFileNotFoundException(localFileNotFoundException, this.val$state);
          return;
        }
        catch (IOException localIOException)
        {
          this.val$listener.onIOException(localIOException, this.val$state);
        }
      }
    }
    .start();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.tauth.http.AsyncHttpRequestRunner
 * JD-Core Version:    0.6.0
 */