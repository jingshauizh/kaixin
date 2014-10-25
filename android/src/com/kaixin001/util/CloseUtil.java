package com.kaixin001.util;

import android.database.Cursor;
import android.os.Build;
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

  }
}

