package com.tencent.sdkutil;

import android.util.Base64;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

public class Security
{
  private static char[] hexChar;
  private static String pubkey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDRKTBPJPh0dJzOgBHFTitk0Ru6b/arlJwz8SdAZMba+aqF/molPehmgasFCyBofZoMnEbG4Ov2SJM1HaKLTLT0+tMHH768HXctZ8mwAvx9ssscYDRLsg31W86GfTNEJ9WATEpeSIOtgz7NB2Gpls+mR2lQZX+tITPM1idKGfYfNwIDAQAB";

  static
  {
    hexChar = new char[] { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102 };
  }

  public static byte[] InputStreamToByte(InputStream paramInputStream)
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    while (true)
    {
      int i = paramInputStream.read();
      if (i == -1)
        break;
      localByteArrayOutputStream.write(i);
    }
    byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
    localByteArrayOutputStream.close();
    return arrayOfByte;
  }

  public static String getFileSHA1(String paramString)
  {
    try
    {
      String str = getHash(paramString, "SHA1");
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "";
  }

  private static String getHash(String paramString1, String paramString2)
  {
    FileInputStream localFileInputStream = new FileInputStream(paramString1);
    byte[] arrayOfByte = new byte[1024];
    MessageDigest localMessageDigest = MessageDigest.getInstance(paramString2);
    while (true)
    {
      int i = localFileInputStream.read(arrayOfByte);
      if (i <= 0)
        break;
      localMessageDigest.update(arrayOfByte, 0, i);
    }
    localFileInputStream.close();
    return toHexString(localMessageDigest.digest());
  }

  public static String inputStream2String(InputStream paramInputStream)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    byte[] arrayOfByte = new byte[4096];
    while (true)
    {
      int i = paramInputStream.read(arrayOfByte);
      if (i == -1)
        break;
      localStringBuffer.append(new String(arrayOfByte, 0, i));
    }
    return localStringBuffer.toString();
  }

  private static String toHexString(byte[] paramArrayOfByte)
  {
    StringBuilder localStringBuilder = new StringBuilder(2 * paramArrayOfByte.length);
    for (int i = 0; i < paramArrayOfByte.length; i++)
    {
      localStringBuilder.append(hexChar[((0xF0 & paramArrayOfByte[i]) >>> 4)]);
      localStringBuilder.append(hexChar[(0xF & paramArrayOfByte[i])]);
    }
    return localStringBuilder.toString();
  }

  public static boolean verify(String paramString)
  {
    if (verifySecurity(paramString))
      return verifySHA1(paramString);
    return false;
  }

  public static boolean verify(String paramString, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    try
    {
      byte[] arrayOfByte = Base64.decode(paramString.getBytes("UTF-8"), 0);
      KeyFactory localKeyFactory = KeyFactory.getInstance("RSA");
      Signature localSignature = Signature.getInstance("SHA1withRSA");
      localSignature.initVerify(localKeyFactory.generatePublic(new X509EncodedKeySpec(arrayOfByte)));
      localSignature.update(paramArrayOfByte1);
      boolean bool = localSignature.verify(paramArrayOfByte2);
      return bool;
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  // ERROR //
  public static boolean verifySHA1(String paramString)
  {
    // Byte code:
    //   0: new 184	java/io/File
    //   3: dup
    //   4: aload_0
    //   5: invokespecial 185	java/io/File:<init>	(Ljava/lang/String;)V
    //   8: astore_1
    //   9: new 187	org/json/JSONObject
    //   12: dup
    //   13: new 74	java/io/FileInputStream
    //   16: dup
    //   17: new 184	java/io/File
    //   20: dup
    //   21: new 117	java/lang/StringBuilder
    //   24: dup
    //   25: invokespecial 188	java/lang/StringBuilder:<init>	()V
    //   28: aload_0
    //   29: invokevirtual 191	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   32: ldc 193
    //   34: invokevirtual 191	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   37: invokevirtual 123	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   40: invokespecial 185	java/io/File:<init>	(Ljava/lang/String;)V
    //   43: invokespecial 196	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   46: invokestatic 198	com/tencent/sdkutil/Security:inputStream2String	(Ljava/io/InputStream;)Ljava/lang/String;
    //   49: invokespecial 199	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   52: astore_2
    //   53: aload_2
    //   54: invokevirtual 202	org/json/JSONObject:length	()I
    //   57: istore 9
    //   59: iload 9
    //   61: istore 5
    //   63: aload_1
    //   64: invokevirtual 206	java/io/File:isDirectory	()Z
    //   67: ifeq +146 -> 213
    //   70: aload_1
    //   71: invokevirtual 210	java/io/File:listFiles	()[Ljava/io/File;
    //   74: astore 11
    //   76: aload 11
    //   78: arraylength
    //   79: istore 12
    //   81: iconst_0
    //   82: istore 13
    //   84: iconst_0
    //   85: istore 10
    //   87: iload 13
    //   89: iload 12
    //   91: if_icmpge +125 -> 216
    //   94: aload 11
    //   96: iload 13
    //   98: aaload
    //   99: invokevirtual 213	java/io/File:getName	()Ljava/lang/String;
    //   102: astore 17
    //   104: aload 17
    //   106: iconst_1
    //   107: aload 17
    //   109: ldc 215
    //   111: invokevirtual 219	java/lang/String:indexOf	(Ljava/lang/String;)I
    //   114: iadd
    //   115: aload 17
    //   117: invokevirtual 220	java/lang/String:length	()I
    //   120: invokevirtual 224	java/lang/String:substring	(II)Ljava/lang/String;
    //   123: astore 18
    //   125: aload 17
    //   127: ldc 215
    //   129: ldc 226
    //   131: invokevirtual 230	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    //   134: astore 19
    //   136: new 117	java/lang/StringBuilder
    //   139: dup
    //   140: invokespecial 188	java/lang/StringBuilder:<init>	()V
    //   143: aload_0
    //   144: invokevirtual 191	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   147: getstatic 233	java/io/File:separator	Ljava/lang/String;
    //   150: invokevirtual 191	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   153: aload 17
    //   155: invokevirtual 191	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   158: invokevirtual 123	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   161: invokestatic 235	com/tencent/sdkutil/Security:getFileSHA1	(Ljava/lang/String;)Ljava/lang/String;
    //   164: astore 20
    //   166: aload 18
    //   168: ldc 237
    //   170: invokevirtual 241	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   173: ifne +13 -> 186
    //   176: aload 18
    //   178: ldc 243
    //   180: invokevirtual 247	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   183: ifeq +24 -> 207
    //   186: aload_2
    //   187: aload 19
    //   189: invokevirtual 250	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   192: aload 20
    //   194: invokevirtual 241	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   197: istore 21
    //   199: iload 21
    //   201: ifeq +3 -> 204
    //   204: iinc 10 1
    //   207: iinc 13 1
    //   210: goto -123 -> 87
    //   213: iconst_0
    //   214: istore 10
    //   216: iload 10
    //   218: istore 4
    //   220: iconst_0
    //   221: istore 6
    //   223: iload 4
    //   225: iload 5
    //   227: if_icmpne +6 -> 233
    //   230: iconst_1
    //   231: istore 6
    //   233: iload 6
    //   235: ireturn
    //   236: astore 8
    //   238: iconst_0
    //   239: istore 4
    //   241: iconst_0
    //   242: istore 5
    //   244: aload 8
    //   246: invokevirtual 251	java/io/FileNotFoundException:printStackTrace	()V
    //   249: goto -29 -> 220
    //   252: astore 7
    //   254: iconst_0
    //   255: istore 4
    //   257: iconst_0
    //   258: istore 5
    //   260: aload 7
    //   262: invokevirtual 252	org/json/JSONException:printStackTrace	()V
    //   265: goto -45 -> 220
    //   268: astore_3
    //   269: iconst_0
    //   270: istore 4
    //   272: iconst_0
    //   273: istore 5
    //   275: aload_3
    //   276: invokevirtual 253	java/io/IOException:printStackTrace	()V
    //   279: goto -59 -> 220
    //   282: astore_3
    //   283: iconst_0
    //   284: istore 4
    //   286: goto -11 -> 275
    //   289: astore 16
    //   291: iload 10
    //   293: istore 4
    //   295: aload 16
    //   297: astore_3
    //   298: goto -23 -> 275
    //   301: astore 7
    //   303: iconst_0
    //   304: istore 4
    //   306: goto -46 -> 260
    //   309: astore 15
    //   311: iload 10
    //   313: istore 4
    //   315: aload 15
    //   317: astore 7
    //   319: goto -59 -> 260
    //   322: astore 8
    //   324: iconst_0
    //   325: istore 4
    //   327: goto -83 -> 244
    //   330: astore 14
    //   332: iload 10
    //   334: istore 4
    //   336: aload 14
    //   338: astore 8
    //   340: goto -96 -> 244
    //
    // Exception table:
    //   from	to	target	type
    //   9	59	236	java/io/FileNotFoundException
    //   9	59	252	org/json/JSONException
    //   9	59	268	java/io/IOException
    //   63	81	282	java/io/IOException
    //   94	186	289	java/io/IOException
    //   186	199	289	java/io/IOException
    //   63	81	301	org/json/JSONException
    //   94	186	309	org/json/JSONException
    //   186	199	309	org/json/JSONException
    //   63	81	322	java/io/FileNotFoundException
    //   94	186	330	java/io/FileNotFoundException
    //   186	199	330	java/io/FileNotFoundException
  }

  public static boolean verifySecurity(String paramString)
  {
    try
    {
      byte[] arrayOfByte1 = InputStreamToByte(new FileInputStream(new File(paramString + "/verify.json")));
      byte[] arrayOfByte2 = InputStreamToByte(new FileInputStream(new File(paramString + "/verify.signature")));
      boolean bool = verify(pubkey, arrayOfByte1, arrayOfByte2);
      return bool;
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      localFileNotFoundException.printStackTrace();
      return false;
    }
    catch (IOException localIOException)
    {
      while (true)
        localIOException.printStackTrace();
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.sdkutil.Security
 * JD-Core Version:    0.6.0
 */