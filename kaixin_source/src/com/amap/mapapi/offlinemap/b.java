package com.amap.mapapi.offlinemap;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class b extends Thread
{
  String a;
  long b;
  long c;
  int d;
  boolean e = false;
  boolean f = false;
  a g = null;

  public b(String paramString1, String paramString2, long paramLong1, long paramLong2, int paramInt)
    throws IOException
  {
    this.a = paramString1;
    this.b = paramLong1;
    this.c = paramLong2;
    this.d = paramInt;
    this.g = new a(paramString2, this.b);
  }

  public void a()
  {
    this.f = true;
  }

  public void run()
  {
    while ((this.b < this.c) && (!this.f))
    {
      try
      {
        HttpURLConnection localHttpURLConnection = (HttpURLConnection)new URL(this.a).openConnection();
        localHttpURLConnection.setRequestMethod("GET");
        localHttpURLConnection.setRequestProperty("Content-Type", "text/xml;");
        String str = "bytes=" + this.b + "-";
        localHttpURLConnection.setRequestProperty("RANGE", str);
        h.a(str);
        InputStream localInputStream = localHttpURLConnection.getInputStream();
        byte[] arrayOfByte = new byte[1024];
        while (true)
        {
          int i = localInputStream.read(arrayOfByte, 0, 1024);
          if ((i <= 0) || (this.b >= this.c) || (this.f))
            break;
          this.b += this.g.a(arrayOfByte, 0, i);
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
      continue;
      h.a("Thread " + this.d + " is over!");
      this.e = true;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.offlinemap.b
 * JD-Core Version:    0.6.0
 */