package com.tencent.sdkutil;

import java.net.Socket;
import java.security.KeyStore;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

public class HttpUtils$CustomSSLSocketFactory extends org.apache.http.conn.ssl.SSLSocketFactory
{
  private SSLContext sslContext = SSLContext.getInstance("TLS");

  public HttpUtils$CustomSSLSocketFactory(KeyStore paramKeyStore)
  {
    super(paramKeyStore);
    try
    {
      localMyX509TrustManager = new HttpUtils.MyX509TrustManager();
      this.sslContext.init(null, new TrustManager[] { localMyX509TrustManager }, null);
      return;
    }
    catch (Exception localException)
    {
      while (true)
        HttpUtils.MyX509TrustManager localMyX509TrustManager = null;
    }
  }

  public Socket createSocket()
  {
    return this.sslContext.getSocketFactory().createSocket();
  }

  public Socket createSocket(Socket paramSocket, String paramString, int paramInt, boolean paramBoolean)
  {
    return this.sslContext.getSocketFactory().createSocket(paramSocket, paramString, paramInt, paramBoolean);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.sdkutil.HttpUtils.CustomSSLSocketFactory
 * JD-Core Version:    0.6.0
 */