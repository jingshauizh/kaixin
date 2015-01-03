package com.tencent.mm.sdk.platformtools;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.StatFs;
import android.os.SystemClock;
import android.os.Vibrator;
import android.provider.Settings.System;
import android.telephony.TelephonyManager;
import android.view.View;
import com.tencent.mm.algorithm.MD5;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import junit.framework.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public final class Util
{
  public static final int BEGIN_TIME = 22;
  public static final int BIT_OF_KB = 10;
  public static final int BIT_OF_MB = 20;
  public static final int BYTE_OF_KB = 1024;
  public static final int BYTE_OF_MB = 1048576;
  public static final String CHINA = "zh_CN";
  public static final int DAY = 0;
  public static final int END_TIME = 8;
  public static final String ENGLISH = "en";
  private static final TimeZone GMT;
  public static final String HONGKONG = "zh_HK";
  public static final String LANGUAGE_DEFAULT = "language_default";
  public static final int MASK_16BIT = 65535;
  public static final int MASK_32BIT = -1;
  public static final int MASK_4BIT = 15;
  public static final int MASK_8BIT = 255;
  public static final long MAX_32BIT_VALUE = 4294967295L;
  public static final int MAX_ACCOUNT_LENGTH = 20;
  public static final int MAX_DECODE_PICTURE_SIZE = 2764800;
  public static final int MAX_PASSWORD_LENGTH = 9;
  public static final long MILLSECONDS_OF_DAY = 86400000L;
  public static final long MILLSECONDS_OF_HOUR = 3600000L;
  public static final long MILLSECONDS_OF_MINUTE = 60000L;
  public static final long MILLSECONDS_OF_SECOND = 1000L;
  public static final long MINUTE_OF_HOUR = 60L;
  public static final int MIN_ACCOUNT_LENGTH = 6;
  public static final int MIN_PASSWORD_LENGTH = 4;
  public static final String PHOTO_DEFAULT_EXT = ".jpg";
  public static final long SECOND_OF_MINUTE = 60L;
  public static final String TAIWAN = "zh_TW";
  private static final char[] bA;
  private static final char[] bB;
  private static final String[] bC;
  private static final long[] bz = { 300L, 200L, 300L, 200L };

  static
  {
    GMT = TimeZone.getTimeZone("GMT");
    bA = new char[] { 9, 10, 13 };
    bB = new char[] { 60, 62, 34, 39, 38 };
    bC = new String[] { "&lt;", "&gt;", "&quot;", "&apos;", "&amp;" };
  }

  public static String GetHostIp()
  {
    try
    {
      InetAddress localInetAddress;
      do
      {
        Enumeration localEnumeration1 = NetworkInterface.getNetworkInterfaces();
        Enumeration localEnumeration2;
        while (!localEnumeration2.hasMoreElements())
        {
          if (!localEnumeration1.hasMoreElements())
            break;
          localEnumeration2 = ((NetworkInterface)localEnumeration1.nextElement()).getInetAddresses();
        }
        localInetAddress = (InetAddress)localEnumeration2.nextElement();
      }
      while (localInetAddress.isLoopbackAddress());
      String str = localInetAddress.getHostAddress();
      return str;
    }
    catch (Exception localException)
    {
      return null;
    }
    catch (SocketException localSocketException)
    {
      label65: break label65;
    }
  }

  public static int UnZipFolder(String paramString1, String paramString2)
  {
    ZipInputStream localZipInputStream;
    while (true)
    {
      FileOutputStream localFileOutputStream;
      try
      {
        android.util.Log.v("XZip", "UnZipFolder(String, String)");
        localZipInputStream = new ZipInputStream(new FileInputStream(paramString1));
        ZipEntry localZipEntry = localZipInputStream.getNextEntry();
        if (localZipEntry == null)
          break;
        str1 = localZipEntry.getName();
        if (!localZipEntry.isDirectory())
          continue;
        String str2 = str1.substring(0, -1 + str1.length());
        new File(paramString2 + File.separator + str2).mkdirs();
        continue;
      }
      catch (FileNotFoundException localFileNotFoundException)
      {
        String str1;
        localFileNotFoundException.printStackTrace();
        return -1;
        File localFile = new File(paramString2 + File.separator + str1);
        localFile.createNewFile();
        localFileOutputStream = new FileOutputStream(localFile);
        byte[] arrayOfByte = new byte[1024];
        int i = localZipInputStream.read(arrayOfByte);
        if (i != -1)
        {
          localFileOutputStream.write(arrayOfByte, 0, i);
          localFileOutputStream.flush();
          continue;
        }
      }
      catch (IOException localIOException)
      {
        localIOException.printStackTrace();
        return -2;
      }
      localFileOutputStream.close();
    }
    localZipInputStream.close();
    return 0;
  }

  private static int a(char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    if (paramInt2 <= 0)
      return 0;
    if ((paramArrayOfChar[paramInt1] != '#') || ((paramInt2 > 1) && ((paramArrayOfChar[(paramInt1 + 1)] == 'x') || (paramArrayOfChar[(paramInt1 + 1)] == 'X'))));
    try
    {
      int j = Integer.parseInt(new String(paramArrayOfChar, paramInt1 + 2, paramInt2 - 2), 16);
      return j;
      try
      {
        int i = Integer.parseInt(new String(paramArrayOfChar, paramInt1 + 1, paramInt2 - 1), 10);
        return i;
        new String(paramArrayOfChar, paramInt1, paramInt2);
        return 0;
      }
      catch (NumberFormatException localNumberFormatException1)
      {
        return 0;
      }
    }
    catch (NumberFormatException localNumberFormatException2)
    {
    }
    return 0;
  }

  private static void a(Map<String, String> paramMap, String paramString, Node paramNode, int paramInt)
  {
    int i = 0;
    if (paramNode.getNodeName().equals("#text"))
      paramMap.put(paramString, paramNode.getNodeValue());
    while (true)
    {
      return;
      if (paramNode.getNodeName().equals("#cdata-section"))
      {
        paramMap.put(paramString, paramNode.getNodeValue());
        return;
      }
      StringBuilder localStringBuilder = new StringBuilder().append(paramString).append(".").append(paramNode.getNodeName());
      if (paramInt > 0);
      String str;
      for (Object localObject = Integer.valueOf(paramInt); ; localObject = "")
      {
        str = localObject;
        paramMap.put(str, paramNode.getNodeValue());
        NamedNodeMap localNamedNodeMap = paramNode.getAttributes();
        if (localNamedNodeMap == null)
          break;
        for (int k = 0; k < localNamedNodeMap.getLength(); k++)
        {
          Node localNode2 = localNamedNodeMap.item(k);
          paramMap.put(str + ".$" + localNode2.getNodeName(), localNode2.getNodeValue());
        }
      }
      HashMap localHashMap = new HashMap();
      NodeList localNodeList = paramNode.getChildNodes();
      while (i < localNodeList.getLength())
      {
        Node localNode1 = localNodeList.item(i);
        int j = nullAsNil((Integer)localHashMap.get(localNode1.getNodeName()));
        a(paramMap, str, localNode1, j);
        localHashMap.put(localNode1.getNodeName(), Integer.valueOf(j + 1));
        i++;
      }
    }
  }

  public static byte[] bmpToByteArray(Bitmap paramBitmap, boolean paramBoolean)
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    paramBitmap.compress(Bitmap.CompressFormat.JPEG, 100, localByteArrayOutputStream);
    if (paramBoolean)
      paramBitmap.recycle();
    byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
    try
    {
      localByteArrayOutputStream.close();
      return arrayOfByte;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return arrayOfByte;
  }

  public static boolean checkPermission(Context paramContext, String paramString)
  {
    Assert.assertNotNull(paramContext);
    String str1 = paramContext.getPackageName();
    int i;
    StringBuilder localStringBuilder;
    if (paramContext.getPackageManager().checkPermission(paramString, str1) == 0)
    {
      i = 1;
      localStringBuilder = new StringBuilder().append(str1).append(" has ");
      if (i == 0)
        break label78;
    }
    label78: for (String str2 = "permission "; ; str2 = "no permission ")
    {
      Log.d("MicroMsg.Util", str2 + paramString);
      return i;
      i = 0;
      break;
    }
  }

  public static boolean checkSDCardFull()
  {
    if (!"mounted".equals(Environment.getExternalStorageState()));
    int i;
    long l3;
    do
    {
      StatFs localStatFs;
      long l1;
      long l2;
      do
      {
        return false;
        localStatFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
        l1 = localStatFs.getBlockCount();
        l2 = localStatFs.getAvailableBlocks();
      }
      while ((l1 <= 0L) || (l1 - l2 < 0L));
      i = (int)(100L * (l1 - l2) / l1);
      l3 = localStatFs.getBlockSize() * localStatFs.getFreeBlocks();
      Log.d("MicroMsg.Util", "checkSDCardFull per:" + i + " blockCount:" + l1 + " availCount:" + l2 + " availSize:" + l3);
    }
    while ((95 > i) || (l3 > 52428800L));
    return true;
  }

  public static String convertStreamToString(InputStream paramInputStream)
  {
    BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(paramInputStream));
    StringBuilder localStringBuilder = new StringBuilder();
    try
    {
      while (true)
      {
        String str = localBufferedReader.readLine();
        if (str == null)
          break;
        localStringBuilder.append(str + "\n");
      }
    }
    catch (IOException localIOException2)
    {
      localIOException2.printStackTrace();
      try
      {
        paramInputStream.close();
        while (true)
        {
          return localStringBuilder.toString();
          try
          {
            paramInputStream.close();
          }
          catch (IOException localIOException4)
          {
            localIOException4.printStackTrace();
          }
        }
      }
      catch (IOException localIOException3)
      {
        while (true)
          localIOException3.printStackTrace();
      }
    }
    finally
    {
    }
    try
    {
      paramInputStream.close();
      throw localObject;
    }
    catch (IOException localIOException1)
    {
      while (true)
        localIOException1.printStackTrace();
    }
  }

  public static long currentDayInMills()
  {
    return 86400000L * (nowMilliSecond() / 86400000L);
  }

  public static long currentMonthInMills()
  {
    GregorianCalendar localGregorianCalendar1 = new GregorianCalendar();
    GregorianCalendar localGregorianCalendar2 = new GregorianCalendar(localGregorianCalendar1.get(1), localGregorianCalendar1.get(2), 1);
    localGregorianCalendar2.setTimeZone(GMT);
    return localGregorianCalendar2.getTimeInMillis();
  }

  public static long currentTicks()
  {
    return SystemClock.elapsedRealtime();
  }

  public static long currentWeekInMills()
  {
    GregorianCalendar localGregorianCalendar1 = new GregorianCalendar();
    GregorianCalendar localGregorianCalendar2 = new GregorianCalendar(localGregorianCalendar1.get(1), localGregorianCalendar1.get(2), localGregorianCalendar1.get(5));
    localGregorianCalendar2.setTimeZone(GMT);
    localGregorianCalendar2.add(6, -(localGregorianCalendar1.get(7) - localGregorianCalendar1.getFirstDayOfWeek()));
    return localGregorianCalendar2.getTimeInMillis();
  }

  public static long currentYearInMills()
  {
    GregorianCalendar localGregorianCalendar = new GregorianCalendar(new GregorianCalendar().get(1), 1, 1);
    localGregorianCalendar.setTimeZone(GMT);
    return localGregorianCalendar.getTimeInMillis();
  }

  public static byte[] decodeHexString(String paramString)
  {
    byte[] arrayOfByte;
    if ((paramString == null) || (paramString.length() <= 0))
      arrayOfByte = new byte[0];
    while (true)
    {
      return arrayOfByte;
      try
      {
        arrayOfByte = new byte[paramString.length() / 2];
        for (int i = 0; i < arrayOfByte.length; i++)
          arrayOfByte[i] = (byte)(0xFF & Integer.parseInt(paramString.substring(i * 2, 2 + i * 2), 16));
      }
      catch (NumberFormatException localNumberFormatException)
      {
        localNumberFormatException.printStackTrace();
      }
    }
    return new byte[0];
  }

  public static boolean deleteFile(String paramString)
  {
    if (isNullOrNil(paramString));
    File localFile;
    do
    {
      return false;
      localFile = new File(paramString);
      if (!localFile.exists())
        return true;
    }
    while (localFile.isDirectory());
    return localFile.delete();
  }

  public static void deleteOutOfDateFile(String paramString1, String paramString2, long paramLong)
  {
    if (isNullOrNil(paramString1));
    while (true)
    {
      return;
      File localFile1 = new File(paramString1);
      if ((!localFile1.exists()) || (!localFile1.isDirectory()))
        continue;
      for (File localFile2 : localFile1.listFiles())
      {
        if ((!localFile2.isFile()) || (!localFile2.getName().startsWith(paramString2)) || (nowMilliSecond() - localFile2.lastModified() - paramLong < 0L))
          continue;
        localFile2.delete();
      }
    }
  }

  public static String dumpArray(Object[] paramArrayOfObject)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    int i = paramArrayOfObject.length;
    for (int j = 0; j < i; j++)
    {
      localStringBuilder.append(paramArrayOfObject[j]);
      localStringBuilder.append(",");
    }
    return localStringBuilder.toString();
  }

  public static String dumpHex(byte[] paramArrayOfByte)
  {
    int i = 0;
    if (paramArrayOfByte == null)
      return "(null)";
    char[] arrayOfChar1 = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102 };
    int j = paramArrayOfByte.length;
    char[] arrayOfChar2 = new char[j * 3];
    int k = 0;
    while (i < j)
    {
      int m = paramArrayOfByte[i];
      int n = k + 1;
      arrayOfChar2[k] = ' ';
      int i1 = n + 1;
      arrayOfChar2[n] = arrayOfChar1[(0xF & m >>> 4)];
      k = i1 + 1;
      arrayOfChar2[i1] = arrayOfChar1[(m & 0xF)];
      i++;
    }
    return new String(arrayOfChar2);
  }

  public static String encodeHexString(byte[] paramArrayOfByte)
  {
    StringBuilder localStringBuilder = new StringBuilder("");
    if (paramArrayOfByte != null)
      for (int i = 0; i < paramArrayOfByte.length; i++)
      {
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Integer.valueOf(0xFF & paramArrayOfByte[i]);
        localStringBuilder.append(String.format("%02x", arrayOfObject));
      }
    return localStringBuilder.toString();
  }

  public static String escapeSqlValue(String paramString)
  {
    if (paramString != null)
      paramString = paramString.replace("\\[", "[[]").replace("%", "").replace("\\^", "").replace("'", "").replace("\\{", "").replace("\\}", "").replace("\"", "");
    return paramString;
  }

  public static String escapeStringForXml(String paramString)
  {
    if (paramString == null)
      return "";
    StringBuffer localStringBuffer = new StringBuffer();
    int i = paramString.length();
    int j = 0;
    char c;
    if (j < i)
    {
      c = paramString.charAt(j);
      if (((c < ' ') && (c != bA[0]) && (c != bA[1]) && (c != bA[2])) || (c > ''))
      {
        localStringBuffer.append("&#");
        localStringBuffer.append(Integer.toString(c));
        localStringBuffer.append(';');
      }
    }
    label178: 
    while (true)
    {
      j++;
      break;
      int k = -1 + bB.length;
      label118: if (k >= 0)
        if (bB[k] == c)
          localStringBuffer.append(bC[k]);
      for (int m = 0; ; m = 1)
      {
        if (m == 0)
          break label178;
        localStringBuffer.append(c);
        break;
        k--;
        break label118;
        return localStringBuffer.toString();
      }
    }
  }

  public static String expandEntities(String paramString)
  {
    int i = paramString.length();
    char[] arrayOfChar = new char[i];
    int j = 0;
    int k = 0;
    int m = -1;
    int n;
    int i1;
    if (j < i)
    {
      char c = paramString.charAt(j);
      n = k + 1;
      arrayOfChar[k] = c;
      if ((c == '&') && (m == -1))
        m = n;
      do
      {
        j++;
        k = n;
        break;
      }
      while ((m == -1) || (Character.isLetter(c)) || (Character.isDigit(c)) || (c == '#'));
      if (c == ';')
      {
        i1 = a(arrayOfChar, m, -1 + (n - m));
        if (i1 > 65535)
        {
          int i2 = i1 - 65536;
          arrayOfChar[(m - 1)] = (char)(55296 + (i2 >> 10));
          arrayOfChar[m] = (char)(56320 + (i2 & 0x3FF));
          m++;
        }
      }
    }
    while (true)
    {
      n = m;
      m = -1;
      break;
      if (i1 != 0)
      {
        arrayOfChar[(m - 1)] = (char)i1;
        continue;
        m = -1;
        break;
        return new String(arrayOfChar, 0, k);
      }
      m = n;
    }
  }

  public static String formatSecToMin(int paramInt)
  {
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = Long.valueOf(paramInt / 60L);
    arrayOfObject[1] = Long.valueOf(paramInt % 60L);
    return String.format("%d:%02d", arrayOfObject);
  }

  public static String formatUnixTime(long paramLong)
  {
    return new SimpleDateFormat("[yy-MM-dd HH:mm:ss]").format(new java.util.Date(1000L * paramLong));
  }

  public static void freeBitmapMap(Map<String, Bitmap> paramMap)
  {
    Iterator localIterator = paramMap.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Bitmap localBitmap = (Bitmap)((Map.Entry)localIterator.next()).getValue();
      if (localBitmap == null)
        continue;
      localBitmap.recycle();
    }
    paramMap.clear();
  }

  public static String getCutPasswordMD5(String paramString)
  {
    if (paramString == null)
      paramString = "";
    if (paramString.length() <= 16)
      return getFullPasswordMD5(paramString);
    return getFullPasswordMD5(paramString.substring(0, 16));
  }

  public static String getDeviceId(Context paramContext)
  {
    if (paramContext == null)
      return null;
    try
    {
      TelephonyManager localTelephonyManager = (TelephonyManager)paramContext.getSystemService("phone");
      if (localTelephonyManager == null)
        return null;
      String str1 = localTelephonyManager.getDeviceId();
      if (str1 == null)
        return null;
      String str2 = str1.trim();
      return str2;
    }
    catch (SecurityException localSecurityException)
    {
      Log.e("MicroMsg.Util", "getDeviceId failed, security exception");
      return null;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  public static String getFullPasswordMD5(String paramString)
  {
    return MD5.getMessageDigest(paramString.getBytes());
  }

  public static int getHex(String paramString, int paramInt)
  {
    if (paramString == null)
      return paramInt;
    try
    {
      long l = Long.decode(paramString).longValue();
      return (int)(l & 0xFFFFFFFF);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      localNumberFormatException.printStackTrace();
    }
    return paramInt;
  }

  public static BitmapFactory.Options getImageOptions(String paramString)
  {
    boolean bool;
    if ((paramString != null) && (!paramString.equals("")))
      bool = true;
    BitmapFactory.Options localOptions;
    while (true)
    {
      Assert.assertTrue(bool);
      localOptions = new BitmapFactory.Options();
      localOptions.inJustDecodeBounds = true;
      try
      {
        Bitmap localBitmap = BitmapFactory.decodeFile(paramString, localOptions);
        if (localBitmap != null)
          localBitmap.recycle();
        return localOptions;
        bool = false;
      }
      catch (OutOfMemoryError localOutOfMemoryError)
      {
        Log.e("MicroMsg.Util", "decode bitmap failed: " + localOutOfMemoryError.getMessage());
      }
    }
    return localOptions;
  }

  public static Intent getInstallPackIntent(String paramString, Context paramContext)
  {
    if ((paramString != null) && (!paramString.equals("")));
    for (boolean bool = true; ; bool = false)
    {
      Assert.assertTrue(bool);
      Intent localIntent = new Intent("android.intent.action.VIEW");
      localIntent.addFlags(268435456);
      localIntent.setDataAndType(Uri.fromFile(new File(paramString)), "application/vnd.android.package-archive");
      return localIntent;
    }
  }

  public static int getInt(String paramString, int paramInt)
  {
    if (paramString == null)
      return paramInt;
    try
    {
      int i = Integer.parseInt(paramString);
      return i;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      localNumberFormatException.printStackTrace();
    }
    return paramInt;
  }

  public static int getIntRandom(int paramInt1, int paramInt2)
  {
    if (paramInt1 > paramInt2);
    for (boolean bool = true; ; bool = false)
    {
      Assert.assertTrue(bool);
      return paramInt2 + new Random(System.currentTimeMillis()).nextInt(1 + (paramInt1 - paramInt2));
    }
  }

  public static String getLine1Number(Context paramContext)
  {
    if (paramContext == null);
    while (true)
    {
      return null;
      try
      {
        if ((TelephonyManager)paramContext.getSystemService("phone") != null)
          continue;
        Log.e("MicroMsg.Util", "get line1 number failed, null tm");
        return null;
      }
      catch (SecurityException localSecurityException)
      {
        Log.e("MicroMsg.Util", "getLine1Number failed, security exception");
        return null;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
    return null;
  }

  public static long getLong(String paramString, long paramLong)
  {
    if (paramString == null)
      return paramLong;
    try
    {
      long l = Long.parseLong(paramString);
      return l;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      localNumberFormatException.printStackTrace();
    }
    return paramLong;
  }

  public static Element getRootElementFromXML(byte[] paramArrayOfByte)
  {
    DocumentBuilderFactory localDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder localDocumentBuilder;
    try
    {
      localDocumentBuilder = localDocumentBuilderFactory.newDocumentBuilder();
      if (localDocumentBuilder == null)
      {
        Log.e("MicroMsg.Util", "new Document Builder failed");
        return null;
      }
    }
    catch (ParserConfigurationException localParserConfigurationException)
    {
      localParserConfigurationException.printStackTrace();
      return null;
    }
    Document localDocument;
    try
    {
      localDocument = localDocumentBuilder.parse(new ByteArrayInputStream(paramArrayOfByte));
      if (localDocument == null)
      {
        Log.e("MicroMsg.Util", "new Document failed");
        return null;
      }
    }
    catch (SAXException localSAXException)
    {
      localSAXException.printStackTrace();
      return null;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
      return null;
    }
    return localDocument.getDocumentElement();
  }

  public static Bitmap getRoundedCornerBitmap(Bitmap paramBitmap, boolean paramBoolean, float paramFloat)
  {
    Assert.assertNotNull(paramBitmap);
    Bitmap localBitmap = Bitmap.createBitmap(paramBitmap.getWidth(), paramBitmap.getHeight(), Bitmap.Config.ARGB_8888);
    Canvas localCanvas = new Canvas(localBitmap);
    Paint localPaint = new Paint();
    Rect localRect = new Rect(0, 0, paramBitmap.getWidth(), paramBitmap.getHeight());
    RectF localRectF = new RectF(localRect);
    localPaint.setAntiAlias(true);
    localPaint.setDither(true);
    localPaint.setFilterBitmap(true);
    localCanvas.drawARGB(0, 0, 0, 0);
    localPaint.setColor(-4144960);
    localCanvas.drawRoundRect(localRectF, paramFloat, paramFloat, localPaint);
    localPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    localCanvas.drawBitmap(paramBitmap, localRect, localRect, localPaint);
    if (paramBoolean)
      paramBitmap.recycle();
    return localBitmap;
  }

  public static int getSeconds(String paramString, int paramInt)
  {
    try
    {
      long l = new SimpleDateFormat("yyyy-MM-dd").parse(paramString).getTime() / 1000L;
      return (int)l;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return paramInt;
  }

  public static String getSizeKB(long paramLong)
  {
    if (paramLong >> 20 > 0L)
      return getSizeMB(paramLong);
    if (paramLong >> 9 > 0L)
    {
      float f = Math.round(10.0F * (float)paramLong / 1024.0F) / 10.0F;
      return f + "KB";
    }
    return paramLong + "B";
  }

  public static String getSizeMB(long paramLong)
  {
    float f = Math.round(10.0F * (float)paramLong / 1048576.0F) / 10.0F;
    return f + "MB";
  }

  public static String getStack()
  {
    StackTraceElement[] arrayOfStackTraceElement = new Throwable().getStackTrace();
    String str;
    if ((arrayOfStackTraceElement == null) || (arrayOfStackTraceElement.length < 2))
      str = "";
    while (true)
    {
      return str;
      str = "";
      for (int i = 1; (i < arrayOfStackTraceElement.length) && (arrayOfStackTraceElement[i].getClassName().contains("com.tencent.mm")); i++)
        str = str + "[" + arrayOfStackTraceElement[i].getClassName().substring(15) + ":" + arrayOfStackTraceElement[i].getMethodName() + "]";
    }
  }

  public static int getSystemVersion(Context paramContext, int paramInt)
  {
    if (paramContext == null)
      return paramInt;
    return Settings.System.getInt(paramContext.getContentResolver(), "sys.settings_system_version", paramInt);
  }

  public static String getTimeZone()
  {
    String str1 = getTimeZoneDef();
    int i = str1.indexOf('+');
    if (i == -1)
      i = str1.indexOf('-');
    String str2;
    if (i == -1)
      str2 = "";
    do
    {
      return str2;
      str2 = str1.substring(i, i + 3);
    }
    while (str2.charAt(1) != '0');
    return str2.substring(0, 1) + str2.substring(2, 3);
  }

  public static String getTimeZoneDef()
  {
    int i = (int)(TimeZone.getDefault().getRawOffset() / 60000L);
    char c = '+';
    if (i < 0)
    {
      c = '-';
      i = -i;
    }
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = Character.valueOf(c);
    arrayOfObject[1] = Long.valueOf(i / 60L);
    arrayOfObject[2] = Long.valueOf(i % 60L);
    return String.format("GMT%s%02d:%02d", arrayOfObject);
  }

  public static String getTimeZoneOffset()
  {
    TimeZone localTimeZone = TimeZone.getDefault();
    int i = localTimeZone.getRawOffset() / 1000;
    if ((localTimeZone.useDaylightTime()) && (localTimeZone.inDaylightTime(new java.sql.Date(System.currentTimeMillis()))));
    for (int j = 1; ; j = 0)
    {
      double d = i / 3600.0D + j;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Double.valueOf(d);
      return String.format("%.2f", arrayOfObject);
    }
  }

  public static String getTopActivityName(Context paramContext)
  {
    try
    {
      String str = ((ActivityManager.RunningTaskInfo)((ActivityManager)paramContext.getSystemService("activity")).getRunningTasks(1).get(0)).topActivity.getClassName();
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "(null)";
  }

  public static byte[] getUuidRandom()
  {
    return MD5.getRawDigest(UUID.randomUUID().toString().getBytes());
  }

  public static int guessHttpContinueRecvLength(int paramInt)
  {
    return paramInt + (52 + 52 * (1 + (paramInt - 1) / 1462));
  }

  public static int guessHttpRecvLength(int paramInt)
  {
    return paramInt + (208 + 52 * (1 + (paramInt - 1) / 1462));
  }

  public static int guessHttpSendLength(int paramInt)
  {
    return paramInt + (224 + 52 * (1 + (paramInt - 1) / 1462));
  }

  public static int guessTcpConnectLength()
  {
    return 172;
  }

  public static int guessTcpDisconnectLength()
  {
    return 156;
  }

  public static int guessTcpRecvLength(int paramInt)
  {
    return paramInt + (40 + 52 * (1 + (paramInt - 1) / 1462));
  }

  public static int guessTcpSendLength(int paramInt)
  {
    return paramInt + (40 + 52 * (1 + (paramInt - 1) / 1462));
  }

  public static void installPack(String paramString, Context paramContext)
  {
    paramContext.startActivity(getInstallPackIntent(paramString, paramContext));
  }

  public static boolean isAlpha(char paramChar)
  {
    return ((paramChar >= 'a') && (paramChar <= 'z')) || ((paramChar >= 'A') && (paramChar <= 'Z'));
  }

  public static boolean isChinese(char paramChar)
  {
    Character.UnicodeBlock localUnicodeBlock = Character.UnicodeBlock.of(paramChar);
    return (localUnicodeBlock == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS) || (localUnicodeBlock == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS) || (localUnicodeBlock == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A) || (localUnicodeBlock == Character.UnicodeBlock.GENERAL_PUNCTUATION) || (localUnicodeBlock == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION) || (localUnicodeBlock == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS);
  }

  public static boolean isDayTimeNow()
  {
    int i = new GregorianCalendar().get(11);
    return (i >= 6L) && (i < 18L);
  }

  public static boolean isImgFile(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0))
      Log.e("MicroMsg.Util", "isImgFile, invalid argument");
    BitmapFactory.Options localOptions;
    do
    {
      do
        return false;
      while ((paramString.length() < 3) || (!new File(paramString).exists()));
      localOptions = new BitmapFactory.Options();
      localOptions.inJustDecodeBounds = true;
      BitmapFactory.decodeFile(paramString, localOptions);
    }
    while ((localOptions.outWidth <= 0) || (localOptions.outHeight <= 0));
    return true;
  }

  public static boolean isIntentAvailable(Context paramContext, Intent paramIntent)
  {
    return paramContext.getPackageManager().queryIntentActivities(paramIntent, 65536).size() > 0;
  }

  public static boolean isLockScreen(Context paramContext)
  {
    try
    {
      boolean bool = ((KeyguardManager)paramContext.getSystemService("keyguard")).inKeyguardRestrictedInputMode();
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }

  public static boolean isNightTime(int paramInt1, int paramInt2, int paramInt3)
  {
    int i;
    if (paramInt2 > paramInt3)
      if (paramInt1 < paramInt2)
      {
        i = 0;
        if (paramInt1 > paramInt3);
      }
      else
      {
        i = 1;
      }
    while (true)
    {
      return i;
      if (paramInt2 >= paramInt3)
        break;
      i = 0;
      if (paramInt1 > paramInt3)
        continue;
      i = 0;
      if (paramInt1 >= paramInt2)
        return true;
    }
    return true;
  }

  public static boolean isNullOrNil(String paramString)
  {
    return (paramString == null) || (paramString.length() <= 0);
  }

  public static boolean isNullOrNil(byte[] paramArrayOfByte)
  {
    return (paramArrayOfByte == null) || (paramArrayOfByte.length <= 0);
  }

  public static boolean isNum(char paramChar)
  {
    return (paramChar >= '0') && (paramChar <= '9');
  }

  public static boolean isProcessRunning(Context paramContext, String paramString)
  {
    Iterator localIterator = ((ActivityManager)paramContext.getSystemService("activity")).getRunningAppProcesses().iterator();
    while (localIterator.hasNext())
    {
      ActivityManager.RunningAppProcessInfo localRunningAppProcessInfo = (ActivityManager.RunningAppProcessInfo)localIterator.next();
      if ((localRunningAppProcessInfo == null) || (localRunningAppProcessInfo.processName == null) || (!localRunningAppProcessInfo.processName.equals(paramString)))
        continue;
      Log.w("MicroMsg.Util", "process " + paramString + " is running");
      return true;
    }
    Log.w("MicroMsg.Util", "process " + paramString + " is not running");
    return false;
  }

  public static boolean isSDCardAvail()
  {
    try
    {
      boolean bool1 = Environment.getExternalStorageState().equals("mounted");
      int i = 0;
      if (bool1)
      {
        boolean bool2 = new File(Environment.getExternalStorageDirectory().getAbsolutePath()).canWrite();
        i = 0;
        if (bool2)
          i = 1;
      }
      return i;
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  public static boolean isSDCardHaveEnoughSpace(long paramLong)
  {
    if (!"mounted".equals(Environment.getExternalStorageState()));
    StatFs localStatFs;
    long l1;
    long l2;
    do
    {
      return false;
      localStatFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
      l1 = localStatFs.getBlockCount();
      l2 = localStatFs.getAvailableBlocks();
    }
    while ((l1 <= 0L) || (l1 - l2 < 0L) || (localStatFs.getBlockSize() * localStatFs.getFreeBlocks() < paramLong));
    return true;
  }

  public static boolean isServiceRunning(Context paramContext, String paramString)
  {
    Iterator localIterator = ((ActivityManager)paramContext.getSystemService("activity")).getRunningServices(2147483647).iterator();
    while (localIterator.hasNext())
    {
      ActivityManager.RunningServiceInfo localRunningServiceInfo = (ActivityManager.RunningServiceInfo)localIterator.next();
      if ((localRunningServiceInfo == null) || (localRunningServiceInfo.service == null) || (!localRunningServiceInfo.service.getClassName().toString().equals(paramString)))
        continue;
      Log.w("MicroMsg.Util", "service " + paramString + " is running");
      return true;
    }
    Log.w("MicroMsg.Util", "service " + paramString + " is not running");
    return false;
  }

  public static boolean isTopActivity(Context paramContext)
  {
    String str1 = paramContext.getClass().getName();
    String str2 = getTopActivityName(paramContext);
    Log.d("MicroMsg.Util", "top activity=" + str2 + ", context=" + str1);
    return str2.equalsIgnoreCase(str1);
  }

  public static boolean isTopApplication(Context paramContext)
  {
    try
    {
      String str1 = ((ActivityManager.RunningTaskInfo)((ActivityManager)paramContext.getSystemService("activity")).getRunningTasks(1).get(0)).topActivity.getClassName();
      String str2 = paramContext.getPackageName();
      Log.d("MicroMsg.Util", "top activity=" + str1 + ", context=" + str2);
      boolean bool = str1.contains(str2);
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }

  public static boolean isValidAccount(String paramString)
  {
    if (paramString == null);
    String str;
    do
    {
      return false;
      str = paramString.trim();
    }
    while ((str.length() < 6) || (str.length() > 20) || (!isAlpha(str.charAt(0))));
    for (int i = 0; ; i++)
    {
      if (i >= str.length())
        break label88;
      char c = str.charAt(i);
      if ((!isAlpha(c)) && (!isNum(c)) && (c != '-') && (c != '_'))
        break;
    }
    label88: return true;
  }

  public static boolean isValidEmail(String paramString)
  {
    if ((paramString == null) || (paramString.length() <= 0))
      return false;
    return paramString.trim().matches("^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$");
  }

  public static boolean isValidPassword(String paramString)
  {
    if (paramString == null);
    do
      return false;
    while (paramString.length() < 4);
    if (paramString.length() >= 9)
      return true;
    try
    {
      Integer.parseInt(paramString);
      return false;
    }
    catch (NumberFormatException localNumberFormatException)
    {
    }
    return true;
  }

  public static boolean isValidQQNum(String paramString)
  {
    if ((paramString == null) || (paramString.length() <= 0));
    while (true)
    {
      return false;
      String str = paramString.trim();
      try
      {
        long l = Long.valueOf(str).longValue();
        if ((l > 0L) && (l <= 4294967295L))
          return true;
      }
      catch (NumberFormatException localNumberFormatException)
      {
        localNumberFormatException.printStackTrace();
      }
    }
    return false;
  }

  public static boolean jump(Context paramContext, String paramString)
  {
    Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse(paramString));
    if (!isIntentAvailable(paramContext, localIntent))
    {
      Log.e("MicroMsg.Util", "jump to url failed, " + paramString);
      return false;
    }
    paramContext.startActivity(localIntent);
    return true;
  }

  public static String listToString(List<String> paramList, String paramString)
  {
    if (paramList == null)
      return "";
    StringBuilder localStringBuilder = new StringBuilder("");
    int i = 0;
    if (i < paramList.size())
    {
      if (i == -1 + paramList.size())
        localStringBuilder.append(((String)paramList.get(i)).trim());
      while (true)
      {
        i++;
        break;
        localStringBuilder.append(((String)paramList.get(i)).trim() + paramString);
      }
    }
    return localStringBuilder.toString();
  }

  public static String mapToXml(String paramString, LinkedHashMap<String, String> paramLinkedHashMap)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("<key>");
    Iterator localIterator = paramLinkedHashMap.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      Object localObject1 = localEntry.getKey();
      Object localObject2 = localEntry.getValue();
      if (localObject1 == null)
        localObject1 = "unknow";
      if (localObject2 == null)
        localObject2 = "unknow";
      localStringBuilder.append("<" + localObject1 + ">");
      localStringBuilder.append(localObject2);
      localStringBuilder.append("</" + localObject1 + ">");
    }
    localStringBuilder.append("</key>");
    return (String)(String)localStringBuilder.toString();
  }

  public static long milliSecondsToNow(long paramLong)
  {
    return System.currentTimeMillis() - paramLong;
  }

  public static long nowMilliSecond()
  {
    return System.currentTimeMillis();
  }

  public static long nowSecond()
  {
    return System.currentTimeMillis() / 1000L;
  }

  public static int nullAs(Integer paramInteger, int paramInt)
  {
    if (paramInteger == null)
      return paramInt;
    return paramInteger.intValue();
  }

  public static long nullAs(Long paramLong, long paramLong1)
  {
    if (paramLong == null)
      return paramLong1;
    return paramLong.longValue();
  }

  public static String nullAs(String paramString1, String paramString2)
  {
    if (paramString1 == null)
      return paramString2;
    return paramString1;
  }

  public static boolean nullAs(Boolean paramBoolean, boolean paramBoolean1)
  {
    if (paramBoolean == null)
      return paramBoolean1;
    return paramBoolean.booleanValue();
  }

  public static boolean nullAsFalse(Boolean paramBoolean)
  {
    if (paramBoolean == null)
      return false;
    return paramBoolean.booleanValue();
  }

  public static int nullAsInt(Object paramObject, int paramInt)
  {
    if (paramObject == null);
    do
    {
      return paramInt;
      if ((paramObject instanceof Integer))
        return ((Integer)paramObject).intValue();
    }
    while (!(paramObject instanceof Long));
    return ((Long)paramObject).intValue();
  }

  public static int nullAsNil(Integer paramInteger)
  {
    if (paramInteger == null)
      return 0;
    return paramInteger.intValue();
  }

  public static long nullAsNil(Long paramLong)
  {
    if (paramLong == null)
      return 0L;
    return paramLong.longValue();
  }

  public static String nullAsNil(String paramString)
  {
    if (paramString == null)
      paramString = "";
    return paramString;
  }

  public static boolean nullAsTrue(Boolean paramBoolean)
  {
    if (paramBoolean == null)
      return true;
    return paramBoolean.booleanValue();
  }

  public static Map<String, String> parseIni(String paramString)
  {
    HashMap localHashMap;
    if ((paramString == null) || (paramString.length() <= 0))
      localHashMap = null;
    while (true)
    {
      return localHashMap;
      localHashMap = new HashMap();
      for (String str1 : paramString.split("\n"))
      {
        if ((str1 == null) || (str1.length() <= 0))
          continue;
        String[] arrayOfString2 = str1.trim().split("=", 2);
        if ((arrayOfString2 == null) || (arrayOfString2.length < 2))
          continue;
        String str2 = arrayOfString2[0];
        String str3 = arrayOfString2[1];
        if ((str2 == null) || (str2.length() <= 0) || (!str2.matches("^[a-zA-Z0-9_]*")))
          continue;
        localHashMap.put(str2, str3);
      }
    }
  }

  // ERROR //
  public static Map<String, String> parseXml(String paramString1, String paramString2, String paramString3)
  {
    // Byte code:
    //   0: aload_0
    //   1: ifnull +10 -> 11
    //   4: aload_0
    //   5: invokevirtual 202	java/lang/String:length	()I
    //   8: ifgt +5 -> 13
    //   11: aconst_null
    //   12: areturn
    //   13: new 316	java/util/HashMap
    //   16: dup
    //   17: invokespecial 317	java/util/HashMap:<init>	()V
    //   20: astore_3
    //   21: invokestatic 806	javax/xml/parsers/DocumentBuilderFactory:newInstance	()Ljavax/xml/parsers/DocumentBuilderFactory;
    //   24: astore 4
    //   26: aload 4
    //   28: invokevirtual 810	javax/xml/parsers/DocumentBuilderFactory:newDocumentBuilder	()Ljavax/xml/parsers/DocumentBuilder;
    //   31: astore 6
    //   33: aload 6
    //   35: ifnonnull +23 -> 58
    //   38: ldc_w 389
    //   41: ldc_w 812
    //   44: invokestatic 700	com/tencent/mm/sdk/platformtools/Log:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   47: aconst_null
    //   48: areturn
    //   49: astore 5
    //   51: aload 5
    //   53: invokevirtual 813	javax/xml/parsers/ParserConfigurationException:printStackTrace	()V
    //   56: aconst_null
    //   57: areturn
    //   58: new 1284	org/xml/sax/InputSource
    //   61: dup
    //   62: new 815	java/io/ByteArrayInputStream
    //   65: dup
    //   66: aload_0
    //   67: invokevirtual 703	java/lang/String:getBytes	()[B
    //   70: invokespecial 818	java/io/ByteArrayInputStream:<init>	([B)V
    //   73: invokespecial 1285	org/xml/sax/InputSource:<init>	(Ljava/io/InputStream;)V
    //   76: astore 7
    //   78: aload_2
    //   79: ifnull +9 -> 88
    //   82: aload 7
    //   84: aload_2
    //   85: invokevirtual 1288	org/xml/sax/InputSource:setEncoding	(Ljava/lang/String;)V
    //   88: aload 6
    //   90: aload 7
    //   92: invokevirtual 1291	javax/xml/parsers/DocumentBuilder:parse	(Lorg/xml/sax/InputSource;)Lorg/w3c/dom/Document;
    //   95: astore 17
    //   97: aload 17
    //   99: astore 12
    //   101: aload 12
    //   103: invokeinterface 1294 1 0
    //   108: aload 12
    //   110: ifnonnull +54 -> 164
    //   113: ldc_w 389
    //   116: ldc_w 826
    //   119: invokestatic 700	com/tencent/mm/sdk/platformtools/Log:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   122: aconst_null
    //   123: areturn
    //   124: astore 11
    //   126: aconst_null
    //   127: astore 12
    //   129: aload 11
    //   131: invokevirtual 1295	org/w3c/dom/DOMException:printStackTrace	()V
    //   134: goto -26 -> 108
    //   137: astore 10
    //   139: aload 10
    //   141: invokevirtual 827	org/xml/sax/SAXException:printStackTrace	()V
    //   144: aconst_null
    //   145: areturn
    //   146: astore 9
    //   148: aload 9
    //   150: invokevirtual 248	java/io/IOException:printStackTrace	()V
    //   153: aconst_null
    //   154: areturn
    //   155: astore 8
    //   157: aload 8
    //   159: invokevirtual 362	java/lang/Exception:printStackTrace	()V
    //   162: aconst_null
    //   163: areturn
    //   164: aload 12
    //   166: invokeinterface 833 1 0
    //   171: astore 13
    //   173: aload 13
    //   175: ifnonnull +14 -> 189
    //   178: ldc_w 389
    //   181: ldc_w 1297
    //   184: invokestatic 700	com/tencent/mm/sdk/platformtools/Log:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   187: aconst_null
    //   188: areturn
    //   189: aload_1
    //   190: ifnull +116 -> 306
    //   193: aload_1
    //   194: aload 13
    //   196: invokeinterface 1300 1 0
    //   201: invokevirtual 277	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   204: ifeq +102 -> 306
    //   207: aload_3
    //   208: ldc_w 314
    //   211: aload 13
    //   213: iconst_0
    //   214: invokestatic 335	com/tencent/mm/sdk/platformtools/Util:a	(Ljava/util/Map;Ljava/lang/String;Lorg/w3c/dom/Node;I)V
    //   217: aload_3
    //   218: invokeinterface 652 1 0
    //   223: invokeinterface 658 1 0
    //   228: astore 15
    //   230: aload 15
    //   232: invokeinterface 663 1 0
    //   237: ifeq +139 -> 376
    //   240: aload 15
    //   242: invokeinterface 666 1 0
    //   247: checkcast 668	java/util/Map$Entry
    //   250: astore 16
    //   252: ldc_w 389
    //   255: new 210	java/lang/StringBuilder
    //   258: dup
    //   259: ldc_w 1302
    //   262: invokespecial 432	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   265: aload 16
    //   267: invokeinterface 1232 1 0
    //   272: checkcast 113	java/lang/String
    //   275: invokevirtual 215	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   278: ldc_w 1304
    //   281: invokevirtual 215	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   284: aload 16
    //   286: invokeinterface 671 1 0
    //   291: checkcast 113	java/lang/String
    //   294: invokevirtual 215	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   297: invokevirtual 221	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   300: invokestatic 1306	com/tencent/mm/sdk/platformtools/Log:v	(Ljava/lang/String;Ljava/lang/String;)V
    //   303: goto -73 -> 230
    //   306: aload 13
    //   308: aload_1
    //   309: invokeinterface 1310 2 0
    //   314: astore 14
    //   316: aload 14
    //   318: invokeinterface 324 1 0
    //   323: ifgt +14 -> 337
    //   326: ldc_w 389
    //   329: ldc_w 1312
    //   332: invokestatic 700	com/tencent/mm/sdk/platformtools/Log:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   335: aconst_null
    //   336: areturn
    //   337: aload 14
    //   339: invokeinterface 324 1 0
    //   344: iconst_1
    //   345: if_icmple +12 -> 357
    //   348: ldc_w 389
    //   351: ldc_w 1314
    //   354: invokestatic 1152	com/tencent/mm/sdk/platformtools/Log:w	(Ljava/lang/String;Ljava/lang/String;)V
    //   357: aload_3
    //   358: ldc_w 314
    //   361: aload 14
    //   363: iconst_0
    //   364: invokeinterface 325 2 0
    //   369: iconst_0
    //   370: invokestatic 335	com/tencent/mm/sdk/platformtools/Util:a	(Ljava/util/Map;Ljava/lang/String;Lorg/w3c/dom/Node;I)V
    //   373: goto -156 -> 217
    //   376: aload_3
    //   377: areturn
    //   378: astore 11
    //   380: goto -251 -> 129
    //
    // Exception table:
    //   from	to	target	type
    //   26	33	49	javax/xml/parsers/ParserConfigurationException
    //   58	78	124	org/w3c/dom/DOMException
    //   82	88	124	org/w3c/dom/DOMException
    //   88	97	124	org/w3c/dom/DOMException
    //   58	78	137	org/xml/sax/SAXException
    //   82	88	137	org/xml/sax/SAXException
    //   88	97	137	org/xml/sax/SAXException
    //   101	108	137	org/xml/sax/SAXException
    //   58	78	146	java/io/IOException
    //   82	88	146	java/io/IOException
    //   88	97	146	java/io/IOException
    //   101	108	146	java/io/IOException
    //   58	78	155	java/lang/Exception
    //   82	88	155	java/lang/Exception
    //   88	97	155	java/lang/Exception
    //   101	108	155	java/lang/Exception
    //   101	108	378	org/w3c/dom/DOMException
  }

  public static MediaPlayer playSound(Context paramContext, int paramInt, MediaPlayer.OnCompletionListener paramOnCompletionListener)
  {
    return playSound(paramContext, paramInt, false, paramOnCompletionListener);
  }

  public static MediaPlayer playSound(Context paramContext, int paramInt, boolean paramBoolean, MediaPlayer.OnCompletionListener paramOnCompletionListener)
  {
    try
    {
      String str = paramContext.getString(paramInt);
      AssetFileDescriptor localAssetFileDescriptor = paramContext.getAssets().openFd(str);
      MediaPlayer localMediaPlayer = new MediaPlayer();
      localMediaPlayer.setDataSource(localAssetFileDescriptor.getFileDescriptor(), localAssetFileDescriptor.getStartOffset(), localAssetFileDescriptor.getLength());
      localAssetFileDescriptor.close();
      localMediaPlayer.prepare();
      localMediaPlayer.setLooping(paramBoolean);
      localMediaPlayer.start();
      localMediaPlayer.setOnCompletionListener(paramOnCompletionListener);
      return localMediaPlayer;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  public static void playSound(Context paramContext, int paramInt)
  {
    playSound(paramContext, paramInt, new Util.1());
  }

  public static String processXml(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0));
    do
      return paramString;
    while (Build.VERSION.SDK_INT >= 8);
    return expandEntities(paramString);
  }

  // ERROR //
  public static byte[] readFromFile(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokestatic 509	com/tencent/mm/sdk/platformtools/Util:isNullOrNil	(Ljava/lang/String;)Z
    //   4: ifeq +14 -> 18
    //   7: ldc_w 389
    //   10: ldc_w 1381
    //   13: invokestatic 1152	com/tencent/mm/sdk/platformtools/Log:w	(Ljava/lang/String;Ljava/lang/String;)V
    //   16: aconst_null
    //   17: areturn
    //   18: new 208	java/io/File
    //   21: dup
    //   22: aload_0
    //   23: invokespecial 222	java/io/File:<init>	(Ljava/lang/String;)V
    //   26: astore_1
    //   27: aload_1
    //   28: invokevirtual 512	java/io/File:exists	()Z
    //   31: ifne +22 -> 53
    //   34: ldc_w 389
    //   37: ldc_w 1383
    //   40: iconst_1
    //   41: anewarray 4	java/lang/Object
    //   44: dup
    //   45: iconst_0
    //   46: aload_0
    //   47: aastore
    //   48: invokestatic 1386	com/tencent/mm/sdk/platformtools/Log:w	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   51: aconst_null
    //   52: areturn
    //   53: aload_1
    //   54: invokevirtual 1388	java/io/File:length	()J
    //   57: l2i
    //   58: istore 8
    //   60: new 180	java/io/FileInputStream
    //   63: dup
    //   64: aload_1
    //   65: invokespecial 1389	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   68: astore_3
    //   69: iload 8
    //   71: newarray byte
    //   73: astore 9
    //   75: aload_3
    //   76: aload 9
    //   78: invokevirtual 1390	java/io/FileInputStream:read	([B)I
    //   81: istore 10
    //   83: iload 10
    //   85: iload 8
    //   87: if_icmpeq +58 -> 145
    //   90: iconst_3
    //   91: anewarray 4	java/lang/Object
    //   94: astore 11
    //   96: aload 11
    //   98: iconst_0
    //   99: aload_0
    //   100: aastore
    //   101: aload 11
    //   103: iconst_1
    //   104: iload 8
    //   106: invokestatic 294	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   109: aastore
    //   110: aload 11
    //   112: iconst_2
    //   113: iload 10
    //   115: invokestatic 294	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   118: aastore
    //   119: ldc_w 389
    //   122: ldc_w 1392
    //   125: aload 11
    //   127: invokestatic 1386	com/tencent/mm/sdk/platformtools/Log:w	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   130: aload_3
    //   131: invokevirtual 1393	java/io/FileInputStream:close	()V
    //   134: aconst_null
    //   135: areturn
    //   136: astore 12
    //   138: aload 12
    //   140: invokevirtual 248	java/io/IOException:printStackTrace	()V
    //   143: aconst_null
    //   144: areturn
    //   145: ldc_w 389
    //   148: ldc_w 1395
    //   151: invokestatic 395	com/tencent/mm/sdk/platformtools/Log:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   154: aload_3
    //   155: invokevirtual 1393	java/io/FileInputStream:close	()V
    //   158: aload 9
    //   160: areturn
    //   161: astore 13
    //   163: aload 13
    //   165: invokevirtual 248	java/io/IOException:printStackTrace	()V
    //   168: goto -10 -> 158
    //   171: astore 6
    //   173: aconst_null
    //   174: astore_3
    //   175: aload 6
    //   177: invokevirtual 362	java/lang/Exception:printStackTrace	()V
    //   180: aload_3
    //   181: ifnull -165 -> 16
    //   184: aload_3
    //   185: invokevirtual 1393	java/io/FileInputStream:close	()V
    //   188: aconst_null
    //   189: areturn
    //   190: astore 7
    //   192: aload 7
    //   194: invokevirtual 248	java/io/IOException:printStackTrace	()V
    //   197: aconst_null
    //   198: areturn
    //   199: astore_2
    //   200: aconst_null
    //   201: astore_3
    //   202: aload_2
    //   203: astore 4
    //   205: aload_3
    //   206: ifnull +7 -> 213
    //   209: aload_3
    //   210: invokevirtual 1393	java/io/FileInputStream:close	()V
    //   213: aload 4
    //   215: athrow
    //   216: astore 5
    //   218: aload 5
    //   220: invokevirtual 248	java/io/IOException:printStackTrace	()V
    //   223: goto -10 -> 213
    //   226: astore 4
    //   228: goto -23 -> 205
    //   231: astore 6
    //   233: goto -58 -> 175
    //
    // Exception table:
    //   from	to	target	type
    //   130	134	136	java/io/IOException
    //   154	158	161	java/io/IOException
    //   53	69	171	java/lang/Exception
    //   184	188	190	java/io/IOException
    //   53	69	199	finally
    //   209	213	216	java/io/IOException
    //   69	83	226	finally
    //   90	130	226	finally
    //   145	154	226	finally
    //   175	180	226	finally
    //   69	83	231	java/lang/Exception
    //   90	130	231	java/lang/Exception
    //   145	154	231	java/lang/Exception
  }

  public static void saveBitmapToImage(Bitmap paramBitmap, int paramInt, Bitmap.CompressFormat paramCompressFormat, String paramString1, String paramString2, boolean paramBoolean)
  {
    boolean bool;
    if ((paramString1 != null) && (paramString2 != null))
      bool = true;
    while (true)
    {
      Assert.assertTrue(bool);
      Log.d("MicroMsg.Util", "saving to " + paramString1 + paramString2);
      File localFile = new File(paramString1 + paramString2);
      localFile.createNewFile();
      try
      {
        FileOutputStream localFileOutputStream = new FileOutputStream(localFile);
        paramBitmap.compress(paramCompressFormat, paramInt, localFileOutputStream);
        localFileOutputStream.flush();
        return;
        bool = false;
      }
      catch (FileNotFoundException localFileNotFoundException)
      {
        localFileNotFoundException.printStackTrace();
      }
    }
  }

  // ERROR //
  public static void saveBitmapToImage(Bitmap paramBitmap, int paramInt, Bitmap.CompressFormat paramCompressFormat, String paramString, boolean paramBoolean)
  {
    // Byte code:
    //   0: aload_3
    //   1: invokestatic 509	com/tencent/mm/sdk/platformtools/Util:isNullOrNil	(Ljava/lang/String;)Z
    //   4: ifne +81 -> 85
    //   7: iconst_1
    //   8: istore 5
    //   10: iload 5
    //   12: invokestatic 724	junit/framework/Assert:assertTrue	(Z)V
    //   15: ldc_w 389
    //   18: new 210	java/lang/StringBuilder
    //   21: dup
    //   22: ldc_w 1399
    //   25: invokespecial 432	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   28: aload_3
    //   29: invokevirtual 215	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   32: invokevirtual 221	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   35: invokestatic 395	com/tencent/mm/sdk/platformtools/Log:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   38: new 208	java/io/File
    //   41: dup
    //   42: aload_3
    //   43: invokespecial 222	java/io/File:<init>	(Ljava/lang/String;)V
    //   46: astore 6
    //   48: aload 6
    //   50: invokevirtual 231	java/io/File:createNewFile	()Z
    //   53: pop
    //   54: new 233	java/io/FileOutputStream
    //   57: dup
    //   58: aload 6
    //   60: invokespecial 236	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   63: astore 8
    //   65: aload_0
    //   66: aload_2
    //   67: iload_1
    //   68: aload 8
    //   70: invokevirtual 353	android/graphics/Bitmap:compress	(Landroid/graphics/Bitmap$CompressFormat;ILjava/io/OutputStream;)Z
    //   73: pop
    //   74: aload 8
    //   76: invokevirtual 247	java/io/FileOutputStream:flush	()V
    //   79: aload 8
    //   81: invokevirtual 251	java/io/FileOutputStream:close	()V
    //   84: return
    //   85: iconst_0
    //   86: istore 5
    //   88: goto -78 -> 10
    //   91: astore 9
    //   93: aconst_null
    //   94: astore 8
    //   96: aload 9
    //   98: invokevirtual 228	java/io/FileNotFoundException:printStackTrace	()V
    //   101: aload 8
    //   103: ifnull -19 -> 84
    //   106: aload 8
    //   108: invokevirtual 251	java/io/FileOutputStream:close	()V
    //   111: return
    //   112: astore 10
    //   114: aconst_null
    //   115: astore 8
    //   117: aload 8
    //   119: ifnull +8 -> 127
    //   122: aload 8
    //   124: invokevirtual 251	java/io/FileOutputStream:close	()V
    //   127: aload 10
    //   129: athrow
    //   130: astore 10
    //   132: goto -15 -> 117
    //   135: astore 9
    //   137: goto -41 -> 96
    //
    // Exception table:
    //   from	to	target	type
    //   54	65	91	java/io/FileNotFoundException
    //   54	65	112	finally
    //   65	79	130	finally
    //   96	101	130	finally
    //   65	79	135	java/io/FileNotFoundException
  }

  public static long secondsToMilliSeconds(int paramInt)
  {
    return 1000L * paramInt;
  }

  public static long secondsToNow(long paramLong)
  {
    return System.currentTimeMillis() / 1000L - paramLong;
  }

  public static void selectPicture(Context paramContext, int paramInt)
  {
    Intent localIntent1 = new Intent("android.intent.action.GET_CONTENT");
    localIntent1.setType("image/*");
    Intent localIntent2 = Intent.createChooser(localIntent1, null);
    ((Activity)paramContext).startActivityForResult(localIntent2, paramInt);
  }

  public static void shake(Context paramContext, boolean paramBoolean)
  {
    Vibrator localVibrator = (Vibrator)paramContext.getSystemService("vibrator");
    if (localVibrator == null)
      return;
    if (paramBoolean)
    {
      localVibrator.vibrate(bz, -1);
      return;
    }
    localVibrator.cancel();
  }

  public static int[] splitToIntArray(String paramString)
  {
    if (paramString == null)
      return null;
    String[] arrayOfString = paramString.split(":");
    ArrayList localArrayList = new ArrayList();
    int i = arrayOfString.length;
    int j = 0;
    while (true)
      if (j < i)
      {
        String str = arrayOfString[j];
        if ((str != null) && (str.length() > 0));
        try
        {
          localArrayList.add(Integer.valueOf(Integer.valueOf(str).intValue()));
          j++;
        }
        catch (Exception localException)
        {
          while (true)
          {
            localException.printStackTrace();
            Log.e("MicroMsg.Util", "invalid port num, ignore");
          }
        }
      }
    int[] arrayOfInt = new int[localArrayList.size()];
    for (int k = 0; k < arrayOfInt.length; k++)
      arrayOfInt[k] = ((Integer)localArrayList.get(k)).intValue();
    return arrayOfInt;
  }

  public static List<String> stringsToList(String[] paramArrayOfString)
  {
    if ((paramArrayOfString == null) || (paramArrayOfString.length == 0))
      return null;
    ArrayList localArrayList = new ArrayList();
    for (int i = 0; i < paramArrayOfString.length; i++)
      localArrayList.add(paramArrayOfString[i]);
    return localArrayList;
  }

  public static long ticksToNow(long paramLong)
  {
    return SystemClock.elapsedRealtime() - paramLong;
  }

  public static void transClickToSelect(View paramView1, View paramView2)
  {
    paramView1.setOnTouchListener(new Util.2(paramView2, paramView1));
  }

  public static void writeToFile(String paramString1, String paramString2)
  {
    File localFile2;
    if ((isNullOrNil(paramString1)) && (isNullOrNil(paramString2)))
    {
      File localFile1 = new File("/sdcard/Tencent/");
      if (!localFile1.exists())
        localFile1.mkdir();
      localFile2 = new File("/sdcard/Tencent/" + paramString2);
      if (localFile2.exists());
    }
    try
    {
      localFile2.createNewFile();
      try
      {
        label74: FileOutputStream localFileOutputStream = new FileOutputStream(localFile2);
        localFileOutputStream.write(paramString1.getBytes());
        localFileOutputStream.close();
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return;
      }
    }
    catch (IOException localIOException)
    {
      break label74;
    }
  }

  // ERROR //
  public static boolean writeToFile(String paramString, byte[] paramArrayOfByte)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokestatic 509	com/tencent/mm/sdk/platformtools/Util:isNullOrNil	(Ljava/lang/String;)Z
    //   4: ifne +10 -> 14
    //   7: aload_1
    //   8: invokestatic 1473	com/tencent/mm/sdk/platformtools/Util:isNullOrNil	([B)Z
    //   11: ifeq +14 -> 25
    //   14: ldc_w 389
    //   17: ldc_w 1475
    //   20: invokestatic 1152	com/tencent/mm/sdk/platformtools/Log:w	(Ljava/lang/String;Ljava/lang/String;)V
    //   23: iconst_0
    //   24: ireturn
    //   25: new 233	java/io/FileOutputStream
    //   28: dup
    //   29: aload_0
    //   30: invokespecial 1476	java/io/FileOutputStream:<init>	(Ljava/lang/String;)V
    //   33: astore_2
    //   34: aload_2
    //   35: aload_1
    //   36: invokevirtual 1470	java/io/FileOutputStream:write	([B)V
    //   39: aload_2
    //   40: invokevirtual 247	java/io/FileOutputStream:flush	()V
    //   43: aload_2
    //   44: invokevirtual 251	java/io/FileOutputStream:close	()V
    //   47: ldc_w 389
    //   50: ldc_w 1478
    //   53: invokestatic 395	com/tencent/mm/sdk/platformtools/Log:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   56: iconst_1
    //   57: ireturn
    //   58: astore 7
    //   60: aload 7
    //   62: invokevirtual 248	java/io/IOException:printStackTrace	()V
    //   65: goto -18 -> 47
    //   68: astore_3
    //   69: aconst_null
    //   70: astore_2
    //   71: aload_3
    //   72: invokevirtual 362	java/lang/Exception:printStackTrace	()V
    //   75: ldc_w 389
    //   78: ldc_w 1480
    //   81: invokestatic 1152	com/tencent/mm/sdk/platformtools/Log:w	(Ljava/lang/String;Ljava/lang/String;)V
    //   84: aload_2
    //   85: ifnull -62 -> 23
    //   88: aload_2
    //   89: invokevirtual 251	java/io/FileOutputStream:close	()V
    //   92: iconst_0
    //   93: ireturn
    //   94: astore 6
    //   96: aload 6
    //   98: invokevirtual 248	java/io/IOException:printStackTrace	()V
    //   101: iconst_0
    //   102: ireturn
    //   103: astore 4
    //   105: aconst_null
    //   106: astore_2
    //   107: aload_2
    //   108: ifnull +7 -> 115
    //   111: aload_2
    //   112: invokevirtual 251	java/io/FileOutputStream:close	()V
    //   115: aload 4
    //   117: athrow
    //   118: astore 5
    //   120: aload 5
    //   122: invokevirtual 248	java/io/IOException:printStackTrace	()V
    //   125: goto -10 -> 115
    //   128: astore 4
    //   130: goto -23 -> 107
    //   133: astore_3
    //   134: goto -63 -> 71
    //
    // Exception table:
    //   from	to	target	type
    //   43	47	58	java/io/IOException
    //   25	34	68	java/lang/Exception
    //   88	92	94	java/io/IOException
    //   25	34	103	finally
    //   111	115	118	java/io/IOException
    //   34	43	128	finally
    //   71	84	128	finally
    //   34	43	133	java/lang/Exception
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.platformtools.Util
 * JD-Core Version:    0.6.0
 */