package com.umeng.common.b;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class b
{
  private static byte[] a = "uLi4/f4+Pb39.T19".getBytes();
  private static byte[] b = "nmeug.f9/Om+L823".getBytes();

  public static String a(String paramString1, String paramString2)
    throws Exception
  {
    Cipher localCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    localCipher.init(1, new SecretKeySpec(a, "AES"), new IvParameterSpec(b));
    return c.d(localCipher.doFinal(paramString1.getBytes(paramString2)));
  }

  public static String b(String paramString1, String paramString2)
    throws Exception
  {
    Cipher localCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    localCipher.init(2, new SecretKeySpec(a, "AES"), new IvParameterSpec(b));
    return new String(localCipher.doFinal(c.b(paramString1)), paramString2);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.umeng.common.b.b
 * JD-Core Version:    0.6.0
 */