package com.autonavi.aps.api;

import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

public class Des
{
  private static String a = "DESede";
  private Cipher b = null;
  private Cipher c = null;

  public Des(String paramString)
  {
    try
    {
      SecureRandom localSecureRandom = new SecureRandom();
      DESedeKeySpec localDESedeKeySpec = new DESedeKeySpec(paramString.getBytes("utf-8"));
      SecretKey localSecretKey = SecretKeyFactory.getInstance(a).generateSecret(localDESedeKeySpec);
      this.b = Cipher.getInstance(a);
      this.b.init(1, localSecretKey, localSecureRandom);
      this.c = Cipher.getInstance(a);
      this.c.init(2, localSecretKey, localSecureRandom);
      return;
    }
    catch (Exception localException)
    {
      Utils.printException(localException);
    }
  }

  public String byte2hex(byte[] paramArrayOfByte)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    int i = 0;
    if (i >= paramArrayOfByte.length)
      return localStringBuilder.toString();
    String str = Integer.toHexString(0xFF & paramArrayOfByte[i]);
    if (str.length() == 1)
      localStringBuilder.append("0").append(str);
    while (true)
    {
      i++;
      break;
      localStringBuilder.append(str);
    }
  }

  public String decrypt(String paramString1, String paramString2)
  {
    Utils.writeLogCat("encrypted response data:" + paramString1);
    return new String(this.c.doFinal(hex2byte(paramString1)), paramString2);
  }

  public String encrypt(String paramString)
  {
    byte[] arrayOfByte = paramString.getBytes("utf-8");
    return byte2hex(this.b.doFinal(arrayOfByte));
  }

  public byte[] hex2byte(String paramString)
  {
    if (paramString == null);
    String str;
    int i;
    do
    {
      return null;
      str = paramString.trim();
      i = str.length();
    }
    while ((i == 0) || (i % 2 == 1));
    byte[] arrayOfByte = new byte[i / 2];
    int j = 0;
    try
    {
      while (true)
      {
        if (j >= str.length())
          return arrayOfByte;
        arrayOfByte[(j / 2)] = (byte)Integer.decode("0X" + str.substring(j, j + 2)).intValue();
        j += 2;
      }
    }
    catch (Exception localException)
    {
      Utils.printException(localException);
    }
    return null;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.autonavi.aps.api.Des
 * JD-Core Version:    0.6.0
 */