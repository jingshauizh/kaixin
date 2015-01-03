package com.umeng.common.net;

import android.util.Log;
import com.umeng.common.b.g;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;

final class i
  implements Runnable
{
  i(String[] paramArrayOfString, boolean paramBoolean, Map paramMap)
  {
  }

  public void run()
  {
    int i = new Random().nextInt(1000);
    if (this.a == null)
      Log.i(DownloadingService.a(), i + "service report: urls is null");
    while (true)
    {
      return;
      String[] arrayOfString = this.a;
      int j = arrayOfString.length;
      int k = 0;
      if (k >= j)
        continue;
      String str1 = arrayOfString[k];
      String str2 = g.a();
      String str3 = str2.split(" ")[0];
      String str4 = str2.split(" ")[1];
      long l = System.currentTimeMillis();
      StringBuilder localStringBuilder = new StringBuilder(str1);
      localStringBuilder.append("&data=" + str3);
      localStringBuilder.append("&time=" + str4);
      localStringBuilder.append("&ts=" + l);
      if (this.b)
        localStringBuilder.append("&action_type=" + 1);
      while (this.c != null)
      {
        Iterator localIterator = this.c.keySet().iterator();
        while (localIterator.hasNext())
        {
          String str5 = (String)localIterator.next();
          localStringBuilder.append("&" + str5 + "=" + (String)this.c.get(str5));
        }
        localStringBuilder.append("&action_type=" + -2);
      }
      try
      {
        Log.i(DownloadingService.a(), i + ": service report:\tget: " + localStringBuilder.toString());
        HttpGet localHttpGet = new HttpGet(localStringBuilder.toString());
        BasicHttpParams localBasicHttpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(localBasicHttpParams, 10000);
        HttpConnectionParams.setSoTimeout(localBasicHttpParams, 20000);
        HttpResponse localHttpResponse = new DefaultHttpClient(localBasicHttpParams).execute(localHttpGet);
        Log.i(DownloadingService.a(), i + ": service report:status code:  " + localHttpResponse.getStatusLine().getStatusCode());
        int m = localHttpResponse.getStatusLine().getStatusCode();
        if (m == 200)
          continue;
        k++;
      }
      catch (ClientProtocolException localClientProtocolException)
      {
        while (true)
          Log.d(DownloadingService.a(), i + ": service report:\tClientProtocolException,Failed to send message." + str1, localClientProtocolException);
      }
      catch (IOException localIOException)
      {
        while (true)
          Log.d(DownloadingService.a(), i + ": service report:\tIOException,Failed to send message." + str1, localIOException);
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.umeng.common.net.i
 * JD-Core Version:    0.6.0
 */