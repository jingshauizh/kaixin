package com.tencent.sdkutil;

import java.util.HashMap;

public class BrowserAuth
{
  private static final String TAG = "BrowserAuth";
  public static BrowserAuth instance;
  private static int serial;
  public final String KEY_CHAR_LIST = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
  public HashMap<String, BrowserAuth.Auth> authMap = new HashMap();

  static
  {
    if (!BrowserAuth.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      $assertionsDisabled = bool;
      serial = 0;
      return;
    }
  }

  private String f(String paramString1, String paramString2)
  {
    int i = 0;
    assert (paramString1.length() % 2 == 0);
    StringBuilder localStringBuilder = new StringBuilder();
    int j = paramString2.length();
    int k = paramString1.length() / 2;
    int m = 0;
    while (i < k)
    {
      localStringBuilder.append((char)(Integer.parseInt(paramString1.substring(i * 2, 2 + i * 2), 16) ^ paramString2.charAt(m)));
      m = (m + 1) % j;
      i++;
    }
    return localStringBuilder.toString();
  }

  public static BrowserAuth getInstance()
  {
    if (instance == null)
      instance = new BrowserAuth();
    return instance;
  }

  public static int getSerial()
  {
    int i = 1 + serial;
    serial = i;
    return i;
  }

  public String decode(String paramString1, String paramString2)
  {
    return f(paramString1, paramString2);
  }

  public BrowserAuth.Auth get(String paramString)
  {
    return (BrowserAuth.Auth)this.authMap.get(paramString);
  }

  public String makeKey()
  {
    int i = (int)Math.ceil(3.0D + 20.0D * Math.random());
    char[] arrayOfChar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
    int j = arrayOfChar.length;
    StringBuffer localStringBuffer = new StringBuffer();
    for (int k = 0; k < i; k++)
      localStringBuffer.append(arrayOfChar[(int)(Math.random() * j)]);
    return localStringBuffer.toString();
  }

  public void remove(String paramString)
  {
    this.authMap.remove(paramString);
  }

  public String set(BrowserAuth.Auth paramAuth)
  {
    int i = getSerial();
    try
    {
      this.authMap.put("" + i, paramAuth);
      return "" + i;
    }
    catch (Throwable localThrowable)
    {
      while (true)
        localThrowable.printStackTrace();
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.sdkutil.BrowserAuth
 * JD-Core Version:    0.6.0
 */