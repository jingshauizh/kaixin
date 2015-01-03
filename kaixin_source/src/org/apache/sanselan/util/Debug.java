package org.apache.sanselan.util;

import java.io.File;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

public final class Debug
{
  private static long counter = 0L;
  public static String newline = "\r\n";
  private static final SimpleDateFormat timestamp = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss:SSS");

  private static final String byteQuadToString(int paramInt)
  {
    int i = (byte)(0xFF & paramInt >> 24);
    int j = (byte)(0xFF & paramInt >> 16);
    int k = (byte)(0xFF & paramInt >> 8);
    int m = (byte)(0xFF & paramInt >> 0);
    int n = (char)i;
    int i1 = (char)j;
    int i2 = (char)k;
    int i3 = (char)m;
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(new String(new char[] { n, i1, i2, i3 }));
    localStringBuffer.append(" bytequad: " + paramInt);
    localStringBuffer.append(" b1: " + i);
    localStringBuffer.append(" b2: " + j);
    localStringBuffer.append(" b3: " + k);
    localStringBuffer.append(" b4: " + m);
    return localStringBuffer.toString();
  }

  public static boolean compare(String paramString, Map paramMap1, Map paramMap2)
  {
    return compare(paramString, paramMap1, paramMap2, null, null);
  }

  public static boolean compare(String paramString, Map paramMap1, Map paramMap2, ArrayList paramArrayList, StringBuffer paramStringBuffer)
  {
    if ((paramMap1 == null) && (paramMap2 == null))
    {
      log(paramStringBuffer, paramString + " both maps null");
      i = 1;
      return i;
    }
    if (paramMap1 == null)
    {
      log(paramStringBuffer, paramString + " map a: null, map b: map");
      return false;
    }
    if (paramMap2 == null)
    {
      log(paramStringBuffer, paramString + " map a: map, map b: null");
      return false;
    }
    ArrayList localArrayList1 = new ArrayList(paramMap1.keySet());
    ArrayList localArrayList2 = new ArrayList(paramMap2.keySet());
    if (paramArrayList != null)
    {
      localArrayList1.removeAll(paramArrayList);
      localArrayList2.removeAll(paramArrayList);
    }
    int i = 1;
    int j = 0;
    int k;
    if (j >= localArrayList1.size())
      k = 0;
    while (true)
    {
      if (k >= localArrayList2.size())
      {
        if (i == 0)
          break;
        log(paramStringBuffer, paramString + "a is the same as  b");
        return i;
        Object localObject1 = localArrayList1.get(j);
        if (!localArrayList2.contains(localObject1))
        {
          log(paramStringBuffer, paramString + "b is missing key '" + localObject1 + "' from a");
          i = 0;
        }
        while (true)
        {
          j++;
          break;
          localArrayList2.remove(localObject1);
          Object localObject2 = paramMap1.get(localObject1);
          Object localObject3 = paramMap2.get(localObject1);
          if (localObject2.equals(localObject3))
            continue;
          log(paramStringBuffer, paramString + "key(" + localObject1 + ") value a: " + localObject2 + ") !=  b: " + localObject3 + ")");
          i = 0;
        }
      }
      Object localObject4 = localArrayList2.get(k);
      log(paramStringBuffer, paramString + "a is missing key '" + localObject4 + "' from b");
      k++;
      i = 0;
    }
  }

  public static void debug()
  {
    newline();
  }

  public static void debug(Class paramClass, Throwable paramThrowable)
  {
    debug(paramClass.getName(), paramThrowable);
  }

  public static void debug(Object paramObject)
  {
    PrintStream localPrintStream = System.out;
    if (paramObject == null);
    for (String str = "null"; ; str = paramObject.toString())
    {
      localPrintStream.println(str);
      return;
    }
  }

  public static void debug(String paramString)
  {
    System.out.println(paramString);
  }

  public static void debug(String paramString, double paramDouble)
  {
    debug(paramString + ": " + paramDouble);
  }

  public static void debug(String paramString, int paramInt)
  {
    debug(paramString + ": " + paramInt);
  }

  public static void debug(String paramString, long paramLong)
  {
    debug(paramString + " " + Long.toString(paramLong));
  }

  public static void debug(String paramString, File paramFile)
  {
    StringBuilder localStringBuilder = new StringBuilder(String.valueOf(paramString)).append(": ");
    if (paramFile == null);
    for (String str = "null"; ; str = paramFile.getPath())
    {
      debug(str);
      return;
    }
  }

  public static void debug(String paramString, Object paramObject)
  {
    if (paramObject == null)
    {
      debug(paramString, "null");
      return;
    }
    if ((paramObject instanceof char[]))
    {
      debug(paramString, (char[])paramObject);
      return;
    }
    if ((paramObject instanceof byte[]))
    {
      debug(paramString, (byte[])paramObject);
      return;
    }
    if ((paramObject instanceof int[]))
    {
      debug(paramString, (int[])paramObject);
      return;
    }
    if ((paramObject instanceof String))
    {
      debug(paramString, (String)paramObject);
      return;
    }
    if ((paramObject instanceof List))
    {
      debug(paramString, (List)paramObject);
      return;
    }
    if ((paramObject instanceof Map))
    {
      debug(paramString, (Map)paramObject);
      return;
    }
    if ((paramObject instanceof File))
    {
      debug(paramString, (File)paramObject);
      return;
    }
    if ((paramObject instanceof Date))
    {
      debug(paramString, (Date)paramObject);
      return;
    }
    if ((paramObject instanceof Calendar))
    {
      debug(paramString, (Calendar)paramObject);
      return;
    }
    debug(paramString, paramObject.toString());
  }

  public static void debug(String paramString1, String paramString2)
  {
    debug(paramString1 + " " + paramString2);
  }

  public static void debug(String paramString, Throwable paramThrowable)
  {
    debug(getDebug(paramString, paramThrowable));
  }

  public static void debug(String paramString, Calendar paramCalendar)
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    if (paramCalendar == null);
    for (String str = "null"; ; str = localSimpleDateFormat.format(paramCalendar.getTime()))
    {
      debug(paramString, str);
      return;
    }
  }

  public static void debug(String paramString, Date paramDate)
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    if (paramDate == null);
    for (String str = "null"; ; str = localSimpleDateFormat.format(paramDate))
    {
      debug(paramString, str);
      return;
    }
  }

  public static void debug(String paramString, List paramList)
  {
    StringBuilder localStringBuilder = new StringBuilder(" [");
    long l = counter;
    counter = 1L + l;
    String str = l + "]";
    debug(paramString + " (" + paramList.size() + ")" + str);
    for (int i = 0; ; i++)
    {
      if (i >= paramList.size())
      {
        debug();
        return;
      }
      debug("\t" + paramList.get(i).toString() + str);
    }
  }

  public static void debug(String paramString, Map paramMap)
  {
    debug(getDebug(paramString, paramMap));
  }

  public static void debug(String paramString, boolean paramBoolean)
  {
    StringBuilder localStringBuilder = new StringBuilder(String.valueOf(paramString)).append(" ");
    if (paramBoolean);
    for (String str = "true"; ; str = "false")
    {
      debug(str);
      return;
    }
  }

  public static void debug(String paramString, byte[] paramArrayOfByte)
  {
    debug(getDebug(paramString, paramArrayOfByte));
  }

  public static void debug(String paramString, byte[] paramArrayOfByte, int paramInt)
  {
    debug(getDebug(paramString, paramArrayOfByte, paramInt));
  }

  public static void debug(String paramString, char[] paramArrayOfChar)
  {
    debug(getDebug(paramString, paramArrayOfChar));
  }

  public static void debug(String paramString, int[] paramArrayOfInt)
  {
    debug(getDebug(paramString, paramArrayOfInt));
  }

  public static void debug(String paramString, Object[] paramArrayOfObject)
  {
    if (paramArrayOfObject == null)
      debug(paramString, "null");
    debug(paramString, paramArrayOfObject.length);
    for (int i = 0; ; i++)
    {
      if ((i >= paramArrayOfObject.length) || (i >= 10))
      {
        if (paramArrayOfObject.length > 10)
          debug("\t...");
        debug();
        return;
      }
      debug("\t" + i, paramArrayOfObject[i]);
    }
  }

  public static void debug(Throwable paramThrowable)
  {
    debug(getDebug(paramThrowable));
  }

  public static void debug(Throwable paramThrowable, int paramInt)
  {
    debug(getDebug(paramThrowable, paramInt));
  }

  public static void debugByteQuad(String paramString, int paramInt)
  {
    int i = 0xFF & paramInt >> 24;
    int j = 0xFF & paramInt >> 16;
    int k = 0xFF & paramInt >> 8;
    int m = 0xFF & paramInt >> 0;
    System.out.println(paramString + ": " + "alpha: " + i + ", " + "red: " + j + ", " + "green: " + k + ", " + "blue: " + m);
  }

  public static void debugIPQuad(String paramString, int paramInt)
  {
    int i = 0xFF & paramInt >> 24;
    int j = 0xFF & paramInt >> 16;
    int k = 0xFF & paramInt >> 8;
    int m = 0xFF & paramInt >> 0;
    System.out.println(paramString + ": " + "b1: " + i + ", " + "b2: " + j + ", " + "b3: " + k + ", " + "b4: " + m);
  }

  public static void debugIPQuad(String paramString, byte[] paramArrayOfByte)
  {
    System.out.print(paramString + ": ");
    if (paramArrayOfByte == null)
      System.out.print("null");
    while (true)
    {
      System.out.println();
      return;
      for (int i = 0; i < paramArrayOfByte.length; i++)
      {
        if (i > 0)
          System.out.print(".");
        System.out.print(0xFF & paramArrayOfByte[i]);
      }
    }
  }

  public static void dump(String paramString, Object paramObject)
  {
    if (paramObject == null)
      debug(paramString, "null");
    while (true)
    {
      return;
      if ((paramObject instanceof Object[]))
      {
        Object[] arrayOfObject = (Object[])paramObject;
        debug(paramString, arrayOfObject);
        for (int i4 = 0; i4 < arrayOfObject.length; i4++)
          dump(paramString + "\t" + i4 + ": ", arrayOfObject[i4]);
        continue;
      }
      if ((paramObject instanceof int[]))
      {
        int[] arrayOfInt = (int[])paramObject;
        debug(paramString, arrayOfInt);
        for (int i3 = 0; i3 < arrayOfInt.length; i3++)
          debug(paramString + "\t" + i3 + ": ", arrayOfInt[i3]);
        continue;
      }
      if ((paramObject instanceof char[]))
      {
        char[] arrayOfChar = (char[])paramObject;
        debug(paramString, "[" + new String(arrayOfChar) + "]");
        return;
      }
      if ((paramObject instanceof long[]))
      {
        long[] arrayOfLong = (long[])paramObject;
        debug(paramString, arrayOfLong);
        for (int i2 = 0; i2 < arrayOfLong.length; i2++)
          debug(paramString + "\t" + i2 + ": ", arrayOfLong[i2]);
        continue;
      }
      if ((paramObject instanceof boolean[]))
      {
        boolean[] arrayOfBoolean = (boolean[])paramObject;
        debug(paramString, arrayOfBoolean);
        for (int i1 = 0; i1 < arrayOfBoolean.length; i1++)
          debug(paramString + "\t" + i1 + ": ", arrayOfBoolean[i1]);
        continue;
      }
      if ((paramObject instanceof byte[]))
      {
        byte[] arrayOfByte = (byte[])paramObject;
        debug(paramString, arrayOfByte);
        for (int n = 0; n < arrayOfByte.length; n++)
          debug(paramString + "\t" + n + ": ", arrayOfByte[n]);
        continue;
      }
      if ((paramObject instanceof float[]))
      {
        float[] arrayOfFloat = (float[])paramObject;
        debug(paramString, arrayOfFloat);
        for (int m = 0; m < arrayOfFloat.length; m++)
          debug(paramString + "\t" + m + ": ", arrayOfFloat[m]);
        continue;
      }
      if ((paramObject instanceof byte[]))
      {
        double[] arrayOfDouble = (double[])paramObject;
        debug(paramString, arrayOfDouble);
        for (int k = 0; k < arrayOfDouble.length; k++)
          debug(paramString + "\t" + k + ": ", arrayOfDouble[k]);
        continue;
      }
      if ((paramObject instanceof List))
      {
        List localList = (List)paramObject;
        debug(paramString, "list");
        for (int j = 0; j < localList.size(); j++)
          dump(paramString + "\t" + "list: " + j + ": ", localList.get(j));
        continue;
      }
      if (!(paramObject instanceof Map))
        break;
      Map localMap = (Map)paramObject;
      debug(paramString, "map");
      ArrayList localArrayList = new ArrayList(localMap.keySet());
      Collections.sort(localArrayList);
      for (int i = 0; i < localArrayList.size(); i++)
      {
        Object localObject = localArrayList.get(i);
        dump(paramString + "\t" + "map: " + localObject + " -> ", localMap.get(localObject));
      }
    }
    debug(paramString, paramObject.toString());
    debug(paramString + "\t", paramObject.getClass().getName());
  }

  public static void dumpStack()
  {
    debug(getStackTrace(new Exception("Stack trace"), -1, 1));
  }

  public static void dumpStack(int paramInt)
  {
    debug(getStackTrace(new Exception("Stack trace"), paramInt, 1));
  }

  public static String getDebug(Class paramClass, Throwable paramThrowable)
  {
    if (paramClass == null);
    for (String str = "[Unknown]"; ; str = paramClass.getName())
      return getDebug(str, paramThrowable);
  }

  public static String getDebug(String paramString)
  {
    return paramString;
  }

  public static String getDebug(String paramString, double paramDouble)
  {
    return getDebug(paramString + ": " + paramDouble);
  }

  public static String getDebug(String paramString, int paramInt)
  {
    return getDebug(paramString + ": " + paramInt);
  }

  public static String getDebug(String paramString, long paramLong)
  {
    return getDebug(paramString + " " + Long.toString(paramLong));
  }

  public static String getDebug(String paramString, File paramFile)
  {
    StringBuilder localStringBuilder = new StringBuilder(String.valueOf(paramString)).append(": ");
    if (paramFile == null);
    for (String str = "null"; ; str = paramFile.getPath())
      return getDebug(str);
  }

  public static String getDebug(String paramString, Object paramObject)
  {
    if (paramObject == null)
      return getDebug(paramString, "null");
    if ((paramObject instanceof Calendar))
      return getDebug(paramString, (Calendar)paramObject);
    if ((paramObject instanceof Date))
      return getDebug(paramString, (Date)paramObject);
    if ((paramObject instanceof File))
      return getDebug(paramString, (File)paramObject);
    if ((paramObject instanceof Map))
      return getDebug(paramString, (Map)paramObject);
    if ((paramObject instanceof Map))
      return getDebug(paramString, (Map)paramObject);
    if ((paramObject instanceof String))
      return getDebug(paramString, (String)paramObject);
    if ((paramObject instanceof byte[]))
      return getDebug(paramString, (byte[])paramObject);
    if ((paramObject instanceof char[]))
      return getDebug(paramString, (char[])paramObject);
    if ((paramObject instanceof int[]))
      return getDebug(paramString, (int[])paramObject);
    if ((paramObject instanceof List))
      return getDebug(paramString, (List)paramObject);
    return getDebug(paramString, paramObject.toString());
  }

  public static String getDebug(String paramString1, String paramString2)
  {
    return getDebug(paramString1 + " " + paramString2);
  }

  public static String getDebug(String paramString, Throwable paramThrowable)
  {
    return paramString + newline + getDebug(paramThrowable);
  }

  public static String getDebug(String paramString, Calendar paramCalendar)
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    if (paramCalendar == null);
    for (String str = "null"; ; str = localSimpleDateFormat.format(paramCalendar.getTime()))
      return getDebug(paramString, str);
  }

  public static String getDebug(String paramString, Date paramDate)
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    if (paramDate == null);
    for (String str = "null"; ; str = localSimpleDateFormat.format(paramDate))
      return getDebug(paramString, str);
  }

  public static String getDebug(String paramString, List paramList)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    StringBuilder localStringBuilder = new StringBuilder(" [");
    long l = counter;
    counter = 1L + l;
    String str = l + "]";
    localStringBuffer.append(getDebug(new StringBuilder(String.valueOf(paramString)).append(" (").append(paramList.size()).append(")").append(str).toString()) + newline);
    for (int i = 0; ; i++)
    {
      if (i >= paramList.size())
      {
        localStringBuffer.append(newline);
        return localStringBuffer.toString();
      }
      localStringBuffer.append(getDebug(new StringBuilder("\t").append(paramList.get(i).toString()).append(str).toString()) + newline);
    }
  }

  public static String getDebug(String paramString, Map paramMap)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (paramMap == null)
      return getDebug(paramString + " map: " + null);
    ArrayList localArrayList = new ArrayList(paramMap.keySet());
    localStringBuffer.append(getDebug(new StringBuilder(String.valueOf(paramString)).append(" map: ").append(localArrayList.size()).toString()) + newline);
    for (int i = 0; ; i++)
    {
      if (i >= localArrayList.size())
      {
        localStringBuffer.append(newline);
        return localStringBuffer.toString();
      }
      Object localObject1 = localArrayList.get(i);
      Object localObject2 = paramMap.get(localObject1);
      localStringBuffer.append(getDebug(new StringBuilder("\t").append(i).append(": '").append(localObject1).append("' -> '").append(localObject2).append("'").toString()) + newline);
    }
  }

  public static String getDebug(String paramString, boolean paramBoolean)
  {
    StringBuilder localStringBuilder = new StringBuilder(String.valueOf(paramString)).append(" ");
    if (paramBoolean);
    for (String str = "true"; ; str = "false")
      return getDebug(str);
  }

  public static String getDebug(String paramString, byte[] paramArrayOfByte)
  {
    return getDebug(paramString, paramArrayOfByte, 250);
  }

  public static String getDebug(String paramString, byte[] paramArrayOfByte, int paramInt)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (paramArrayOfByte == null)
      localStringBuffer.append(paramString + " (" + null + ")" + newline);
    int i;
    while (true)
    {
      return localStringBuffer.toString();
      localStringBuffer.append(paramString + " (" + paramArrayOfByte.length + ")" + newline);
      i = 0;
      if ((i < paramInt) && (i < paramArrayOfByte.length))
        break;
      if (paramArrayOfByte.length > paramInt)
        localStringBuffer.append("\t..." + newline);
      localStringBuffer.append(newline);
    }
    int j = 0xFF & paramArrayOfByte[i];
    if ((j == 0) || (j == 10) || (j == 11) || (j == 13));
    for (char c = ' '; ; c = (char)j)
    {
      localStringBuffer.append("\t" + i + ": " + j + " (" + c + ", 0x" + Integer.toHexString(j) + ")" + newline);
      i++;
      break;
    }
  }

  public static String getDebug(String paramString, char[] paramArrayOfChar)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (paramArrayOfChar == null)
    {
      localStringBuffer.append(getDebug(new StringBuilder(String.valueOf(paramString)).append(" (").append(null).append(")").toString()) + newline);
      return localStringBuffer.toString();
    }
    localStringBuffer.append(getDebug(new StringBuilder(String.valueOf(paramString)).append(" (").append(paramArrayOfChar.length).append(")").toString()) + newline);
    for (int i = 0; ; i++)
    {
      if (i >= paramArrayOfChar.length)
      {
        localStringBuffer.append(newline);
        break;
      }
      localStringBuffer.append(getDebug(new StringBuilder("\t").append(paramArrayOfChar[i]).append(" (").append(0xFF & paramArrayOfChar[i]).toString()) + ")" + newline);
    }
  }

  public static String getDebug(String paramString, int[] paramArrayOfInt)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (paramArrayOfInt == null)
    {
      localStringBuffer.append(paramString + " (" + null + ")" + newline);
      return localStringBuffer.toString();
    }
    localStringBuffer.append(paramString + " (" + paramArrayOfInt.length + ")" + newline);
    for (int i = 0; ; i++)
    {
      if (i >= paramArrayOfInt.length)
      {
        localStringBuffer.append(newline);
        break;
      }
      localStringBuffer.append("\t" + paramArrayOfInt[i] + newline);
    }
  }

  public static String getDebug(String paramString, Object[] paramArrayOfObject)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (paramArrayOfObject == null)
      localStringBuffer.append(getDebug(paramString, "null") + newline);
    localStringBuffer.append(getDebug(paramString, paramArrayOfObject.length));
    for (int i = 0; ; i++)
    {
      if ((i >= paramArrayOfObject.length) || (i >= 10))
      {
        if (paramArrayOfObject.length > 10)
          localStringBuffer.append(getDebug("\t...") + newline);
        localStringBuffer.append(newline);
        return localStringBuffer.toString();
      }
      localStringBuffer.append(getDebug(new StringBuilder("\t").append(i).toString(), paramArrayOfObject[i]) + newline);
    }
  }

  public static String getDebug(Throwable paramThrowable)
  {
    return getDebug(paramThrowable, -1);
  }

  public static String getDebug(Throwable paramThrowable, int paramInt)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    String str1 = timestamp.format(new Date()).toLowerCase();
    localStringBuffer.append(newline);
    StringBuilder localStringBuilder1 = new StringBuilder("Throwable: ");
    String str2;
    StringBuilder localStringBuilder2;
    if (paramThrowable == null)
    {
      str2 = "";
      localStringBuffer.append(str2 + ":" + str1 + newline);
      localStringBuilder2 = new StringBuilder("Throwable: ");
      if (paramThrowable != null)
        break label231;
    }
    label231: for (String str3 = "null"; ; str3 = paramThrowable.getLocalizedMessage())
    {
      localStringBuffer.append(str3 + newline);
      localStringBuffer.append(newline);
      localStringBuffer.append(getStackTrace(paramThrowable, paramInt));
      localStringBuffer.append("Caught here:" + newline);
      localStringBuffer.append(getStackTrace(new Exception(), paramInt, 1));
      localStringBuffer.append(newline);
      return localStringBuffer.toString();
      str2 = "(" + paramThrowable.getClass().getName() + ")";
      break;
    }
  }

  public static String getStackTrace(Throwable paramThrowable)
  {
    return getStackTrace(paramThrowable, -1);
  }

  public static String getStackTrace(Throwable paramThrowable, int paramInt)
  {
    return getStackTrace(paramThrowable, paramInt, 0);
  }

  public static String getStackTrace(Throwable paramThrowable, int paramInt1, int paramInt2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    StackTraceElement[] arrayOfStackTraceElement;
    if (paramThrowable != null)
    {
      arrayOfStackTraceElement = paramThrowable.getStackTrace();
      if (arrayOfStackTraceElement == null);
    }
    for (int i = paramInt2; ; i++)
    {
      if ((i >= arrayOfStackTraceElement.length) || ((paramInt1 >= 0) && (i >= paramInt1)))
      {
        if ((paramInt1 >= 0) && (arrayOfStackTraceElement.length > paramInt1))
          localStringBuffer.append("\t..." + newline);
        localStringBuffer.append(newline);
        return localStringBuffer.toString();
      }
      StackTraceElement localStackTraceElement = arrayOfStackTraceElement[i];
      localStringBuffer.append("\tat " + localStackTraceElement.getClassName() + "." + localStackTraceElement.getMethodName() + "(" + localStackTraceElement.getFileName() + ":" + localStackTraceElement.getLineNumber() + ")" + newline);
    }
  }

  public static String getType(Object paramObject)
  {
    if (paramObject == null)
      return "null";
    if ((paramObject instanceof Object[]))
      return "[Object[]: " + ((Object[])paramObject).length + "]";
    if ((paramObject instanceof char[]))
      return "[char[]: " + ((char[])paramObject).length + "]";
    if ((paramObject instanceof byte[]))
      return "[byte[]: " + ((byte[])paramObject).length + "]";
    if ((paramObject instanceof short[]))
      return "[short[]: " + ((short[])paramObject).length + "]";
    if ((paramObject instanceof int[]))
      return "[int[]: " + ((int[])paramObject).length + "]";
    if ((paramObject instanceof long[]))
      return "[long[]: " + ((long[])paramObject).length + "]";
    if ((paramObject instanceof float[]))
      return "[float[]: " + ((float[])paramObject).length + "]";
    if ((paramObject instanceof double[]))
      return "[double[]: " + ((double[])paramObject).length + "]";
    if ((paramObject instanceof boolean[]))
      return "[boolean[]: " + ((boolean[])paramObject).length + "]";
    return paramObject.getClass().getName();
  }

  public static boolean isArray(Object paramObject)
  {
    if (paramObject == null);
    do
    {
      return false;
      if ((paramObject instanceof Object[]))
        return true;
      if ((paramObject instanceof char[]))
        return true;
      if ((paramObject instanceof byte[]))
        return true;
      if ((paramObject instanceof short[]))
        return true;
      if ((paramObject instanceof int[]))
        return true;
      if ((paramObject instanceof long[]))
        return true;
      if ((paramObject instanceof float[]))
        return true;
      if ((paramObject instanceof double[]))
        return true;
    }
    while (!(paramObject instanceof boolean[]));
    return true;
  }

  private static void log(StringBuffer paramStringBuffer, String paramString)
  {
    debug(paramString);
    if (paramStringBuffer != null)
      paramStringBuffer.append(paramString + newline);
  }

  public static void newline()
  {
    System.out.print(newline);
  }

  public static final void purgeMemory()
  {
    try
    {
      System.runFinalization();
      Thread.sleep(50L);
      System.gc();
      Thread.sleep(50L);
      return;
    }
    catch (Throwable localThrowable)
    {
      debug(localThrowable);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.util.Debug
 * JD-Core Version:    0.6.0
 */