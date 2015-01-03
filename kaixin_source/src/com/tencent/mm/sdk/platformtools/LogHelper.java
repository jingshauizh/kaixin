package com.tencent.mm.sdk.platformtools;

import android.os.Build;
import android.os.Build.VERSION;
import java.io.PrintStream;
import java.text.SimpleDateFormat;

final class LogHelper
{
  private static final SimpleDateFormat aa = new SimpleDateFormat("MM-dd HH:mm:ss SSS");
  private static final byte[] ab = { 4, 0, 0, 0, -1, -1, -1, 0 };

  public static void initLogHeader(PrintStream paramPrintStream, String paramString1, String paramString2, long paramLong, int paramInt)
  {
    if ((paramPrintStream == null) || (Util.isNullOrNil(paramString2)) || (paramLong == 0L))
      return;
    paramPrintStream.println("1 " + paramString1);
    paramPrintStream.println("2 " + paramString2);
    paramPrintStream.println("3 " + paramLong);
    paramPrintStream.println("4 " + Integer.toHexString(paramInt));
    paramPrintStream.println("5 " + Build.VERSION.RELEASE);
    paramPrintStream.println("6 " + Build.VERSION.CODENAME);
    paramPrintStream.println("7 " + Build.VERSION.INCREMENTAL);
    paramPrintStream.println("8 " + Build.BOARD);
    paramPrintStream.println("9 " + Build.DEVICE);
    paramPrintStream.println("10 " + Build.DISPLAY);
    paramPrintStream.println("11 " + Build.FINGERPRINT);
    paramPrintStream.println("12 " + Build.HOST);
    paramPrintStream.println("13 " + Build.MANUFACTURER);
    paramPrintStream.println("14 " + Build.MODEL);
    paramPrintStream.println("15 " + Build.PRODUCT);
    paramPrintStream.println("16 " + Build.TAGS);
    paramPrintStream.println("17 " + Build.TYPE);
    paramPrintStream.println("18 " + Build.USER);
    paramPrintStream.println();
    paramPrintStream.flush();
  }

  // ERROR //
  public static void writeToStream(PrintStream paramPrintStream, byte[] paramArrayOfByte, String paramString1, String paramString2)
  {
    // Byte code:
    //   0: aload_0
    //   1: ifnull +24 -> 25
    //   4: aload_1
    //   5: invokestatic 155	com/tencent/mm/sdk/platformtools/Util:isNullOrNil	([B)Z
    //   8: ifne +17 -> 25
    //   11: aload_2
    //   12: invokestatic 35	com/tencent/mm/sdk/platformtools/Util:isNullOrNil	(Ljava/lang/String;)Z
    //   15: ifne +10 -> 25
    //   18: aload_3
    //   19: invokestatic 35	com/tencent/mm/sdk/platformtools/Util:isNullOrNil	(Ljava/lang/String;)Z
    //   22: ifeq +4 -> 26
    //   25: return
    //   26: aload_0
    //   27: monitorenter
    //   28: new 157	java/lang/StringBuffer
    //   31: dup
    //   32: invokespecial 158	java/lang/StringBuffer:<init>	()V
    //   35: astore 4
    //   37: aload 4
    //   39: getstatic 20	com/tencent/mm/sdk/platformtools/LogHelper:aa	Ljava/text/SimpleDateFormat;
    //   42: invokestatic 164	java/lang/System:currentTimeMillis	()J
    //   45: invokestatic 170	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   48: invokevirtual 174	java/text/SimpleDateFormat:format	(Ljava/lang/Object;)Ljava/lang/String;
    //   51: invokevirtual 177	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   54: pop
    //   55: aload 4
    //   57: ldc 179
    //   59: invokevirtual 177	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   62: aload_2
    //   63: invokevirtual 177	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   66: ldc 179
    //   68: invokevirtual 177	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   71: aload_3
    //   72: invokevirtual 177	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   75: pop
    //   76: aload 4
    //   78: invokevirtual 180	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   81: astore 8
    //   83: new 182	javax/crypto/spec/DESKeySpec
    //   86: dup
    //   87: aload_1
    //   88: invokespecial 185	javax/crypto/spec/DESKeySpec:<init>	([B)V
    //   91: astore 9
    //   93: ldc 187
    //   95: invokestatic 193	javax/crypto/SecretKeyFactory:getInstance	(Ljava/lang/String;)Ljavax/crypto/SecretKeyFactory;
    //   98: aload 9
    //   100: invokevirtual 197	javax/crypto/SecretKeyFactory:generateSecret	(Ljava/security/spec/KeySpec;)Ljavax/crypto/SecretKey;
    //   103: astore 11
    //   105: ldc 187
    //   107: invokestatic 202	javax/crypto/Cipher:getInstance	(Ljava/lang/String;)Ljavax/crypto/Cipher;
    //   110: astore 12
    //   112: aload 12
    //   114: iconst_1
    //   115: aload 11
    //   117: invokevirtual 206	javax/crypto/Cipher:init	(ILjava/security/Key;)V
    //   120: aload 12
    //   122: aload 8
    //   124: invokevirtual 212	java/lang/String:getBytes	()[B
    //   127: invokevirtual 216	javax/crypto/Cipher:doFinal	([B)[B
    //   130: astore 13
    //   132: aload_0
    //   133: aload 13
    //   135: arraylength
    //   136: invokestatic 222	com/tencent/mm/algorithm/TypeTransform:intToByteArrayLH	(I)[B
    //   139: invokevirtual 225	java/io/PrintStream:write	([B)V
    //   142: aload_0
    //   143: aload 13
    //   145: invokevirtual 225	java/io/PrintStream:write	([B)V
    //   148: aload_0
    //   149: getstatic 25	com/tencent/mm/sdk/platformtools/LogHelper:ab	[B
    //   152: invokevirtual 225	java/io/PrintStream:write	([B)V
    //   155: aload_0
    //   156: invokevirtual 148	java/io/PrintStream:flush	()V
    //   159: aload_0
    //   160: monitorexit
    //   161: return
    //   162: astore 5
    //   164: aload_0
    //   165: monitorexit
    //   166: aload 5
    //   168: athrow
    //   169: astore 10
    //   171: aload 10
    //   173: invokevirtual 228	java/lang/Exception:printStackTrace	()V
    //   176: goto -21 -> 155
    //
    // Exception table:
    //   from	to	target	type
    //   28	83	162	finally
    //   83	155	162	finally
    //   155	161	162	finally
    //   171	176	162	finally
    //   83	155	169	java/lang/Exception
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.platformtools.LogHelper
 * JD-Core Version:    0.6.0
 */