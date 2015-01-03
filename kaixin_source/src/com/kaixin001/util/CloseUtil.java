package com.kaixin001.util;

import android.database.Cursor;
import android.os.Build.VERSION;
import java.io.Closeable;
import java.io.IOException;

public class CloseUtil
{
  private static final String TAG = "CloseUtil";

  public static void close(Cursor paramCursor)
  {
    if ((paramCursor != null) && (Integer.parseInt(Build.VERSION.SDK) < 14))
      paramCursor.close();
  }

  public static void close(Closeable paramCloseable)
  {
    if (paramCloseable != null);
    try
    {
      paramCloseable.close();
      return;
    }
    catch (IOException localIOException)
    {
      KXLog.e("CloseUtil", "close", localIOException);
    }
  }

  // ERROR //
  public static void close(java.net.HttpURLConnection paramHttpURLConnection)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 53	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
    //   4: invokestatic 55	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   7: aload_0
    //   8: invokevirtual 59	java/net/HttpURLConnection:getOutputStream	()Ljava/io/OutputStream;
    //   11: invokestatic 55	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   14: return
    //   15: astore_1
    //   16: ldc 8
    //   18: ldc 36
    //   20: aload_1
    //   21: invokestatic 42	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   24: goto -17 -> 7
    //   27: astore_3
    //   28: ldc 8
    //   30: ldc 36
    //   32: aload_3
    //   33: invokestatic 42	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   36: return
    //   37: astore_2
    //   38: return
    //
    // Exception table:
    //   from	to	target	type
    //   0	7	15	java/lang/Exception
    //   7	14	27	java/lang/Exception
    //   7	14	37	java/net/ProtocolException
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.util.CloseUtil
 * JD-Core Version:    0.6.0
 */