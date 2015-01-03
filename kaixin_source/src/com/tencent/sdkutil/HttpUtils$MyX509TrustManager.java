package com.tencent.sdkutil;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class HttpUtils$MyX509TrustManager
  implements X509TrustManager
{
  X509TrustManager sunJSSEX509TrustManager;

  HttpUtils$MyX509TrustManager()
  {
    try
    {
      KeyStore localKeyStore2 = KeyStore.getInstance("JKS");
      localKeyStore1 = localKeyStore2;
      new TrustManager[0];
      if (localKeyStore1 != null)
      {
        localKeyStore1.load(new FileInputStream("trustedCerts"), "passphrase".toCharArray());
        TrustManagerFactory localTrustManagerFactory2 = TrustManagerFactory.getInstance("SunX509", "SunJSSE");
        localTrustManagerFactory2.init(localKeyStore1);
        arrayOfTrustManager = localTrustManagerFactory2.getTrustManagers();
        i = 0;
        if (i >= arrayOfTrustManager.length)
          break label137;
        if (!(arrayOfTrustManager[i] instanceof X509TrustManager))
          break label131;
        this.sunJSSEX509TrustManager = ((X509TrustManager)arrayOfTrustManager[i]);
        return;
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        int i;
        KeyStore localKeyStore1 = null;
        continue;
        TrustManagerFactory localTrustManagerFactory1 = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        localTrustManagerFactory1.init((KeyStore)null);
        TrustManager[] arrayOfTrustManager = localTrustManagerFactory1.getTrustManagers();
        continue;
        label131: i++;
      }
    }
    label137: throw new Exception("Couldn't initialize");
  }

  public void checkClientTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString)
  {
    try
    {
      this.sunJSSEX509TrustManager.checkClientTrusted(paramArrayOfX509Certificate, paramString);
      return;
    }
    catch (CertificateException localCertificateException)
    {
    }
  }

  public void checkServerTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString)
  {
    try
    {
      this.sunJSSEX509TrustManager.checkServerTrusted(paramArrayOfX509Certificate, paramString);
      return;
    }
    catch (CertificateException localCertificateException)
    {
    }
  }

  public X509Certificate[] getAcceptedIssuers()
  {
    return this.sunJSSEX509TrustManager.getAcceptedIssuers();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.sdkutil.HttpUtils.MyX509TrustManager
 * JD-Core Version:    0.6.0
 */