package com.kaixin001.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build.VERSION;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckSupportFlashUtil
{
  public static final int DONOTSUPPORTFLASH = -1;
  public static final int NEEDINSTALLFLASH = 0;
  public static final int SUPPORTFLASH = 1;
  private static CheckSupportFlashUtil _Instance = null;
  private Context mContext = null;
  private int mnInstalledFlash = -1;

  private boolean canCpuSupportFlash()
  {
    String str = fetch_cpu_Processor();
    if (str == null);
    Matcher localMatcher;
    do
    {
      do
        return false;
      while (!str.contains("ARMv"));
      localMatcher = Pattern.compile("ARMv[1-9]\\d*").matcher(str);
    }
    while ((!localMatcher.find()) || (Integer.parseInt(localMatcher.group().substring(4)) <= 6));
    return true;
  }

  private String fetch_cpu_Processor()
  {
    String str1 = fetch_cpu_info();
    String[] arrayOfString1;
    int i;
    if (str1 != null)
    {
      arrayOfString1 = str1.split("\n");
      i = arrayOfString1.length;
    }
    for (int j = 0; ; j++)
    {
      if (j >= i);
      while (true)
      {
        return null;
        String str2 = arrayOfString1[j];
        if (!str2.contains("Processor"))
          break;
        String[] arrayOfString2 = str2.split(":");
        if (arrayOfString2.length > 1)
          return arrayOfString2[1];
      }
    }
  }

  private String fetch_cpu_info()
  {
    CMDExecute localCMDExecute = new CMDExecute(null);
    try
    {
      String str = localCMDExecute.run(new String[] { "/system/bin/cat", "/proc/cpuinfo" }, "/system/bin/");
      return str;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    return null;
  }

  public static CheckSupportFlashUtil getInstance(Context paramContext)
  {
    if (_Instance == null)
    {
      _Instance = new CheckSupportFlashUtil();
      _Instance.mContext = paramContext;
    }
    return _Instance;
  }

  private boolean isFlashInstalled()
  {
    int i = -1;
    List localList;
    if (this.mnInstalledFlash != 1)
      localList = this.mContext.getPackageManager().getInstalledPackages(0);
    for (int j = 0; ; j++)
    {
      if (j >= localList.size());
      while (true)
      {
        if (i == -1)
          break label90;
        return true;
        PackageInfo localPackageInfo = (PackageInfo)localList.get(j);
        if ((!localPackageInfo.packageName.contains("adobe")) || (!localPackageInfo.packageName.contains("flash")))
          break;
        i = 1;
      }
    }
    label90: return false;
  }

  public int getFlashStatus()
  {
    return this.mnInstalledFlash;
  }

  public int getSupportFlash()
  {
    if ((Build.VERSION.SDK_INT < 8) || (!canCpuSupportFlash()))
      this.mnInstalledFlash = -1;
    while (true)
    {
      return this.mnInstalledFlash;
      if (isFlashInstalled())
      {
        this.mnInstalledFlash = 1;
        continue;
      }
      this.mnInstalledFlash = 0;
    }
  }

  public int isSupportFlash()
  {
    if (this.mnInstalledFlash != 1)
      this.mnInstalledFlash = getSupportFlash();
    return this.mnInstalledFlash;
  }

  private static class CMDExecute
  {
    // ERROR //
    public String run(String[] paramArrayOfString, String paramString)
      throws IOException
    {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: new 18	java/lang/StringBuffer
      //   5: dup
      //   6: invokespecial 19	java/lang/StringBuffer:<init>	()V
      //   9: astore_3
      //   10: aconst_null
      //   11: astore 4
      //   13: new 21	java/lang/ProcessBuilder
      //   16: dup
      //   17: aload_1
      //   18: invokespecial 24	java/lang/ProcessBuilder:<init>	([Ljava/lang/String;)V
      //   21: astore 5
      //   23: aconst_null
      //   24: astore 4
      //   26: aload_2
      //   27: ifnull +61 -> 88
      //   30: aload 5
      //   32: new 26	java/io/File
      //   35: dup
      //   36: aload_2
      //   37: invokespecial 29	java/io/File:<init>	(Ljava/lang/String;)V
      //   40: invokevirtual 33	java/lang/ProcessBuilder:directory	(Ljava/io/File;)Ljava/lang/ProcessBuilder;
      //   43: pop
      //   44: aload 5
      //   46: iconst_1
      //   47: invokevirtual 37	java/lang/ProcessBuilder:redirectErrorStream	(Z)Ljava/lang/ProcessBuilder;
      //   50: pop
      //   51: aload 5
      //   53: invokevirtual 41	java/lang/ProcessBuilder:start	()Ljava/lang/Process;
      //   56: invokevirtual 47	java/lang/Process:getInputStream	()Ljava/io/InputStream;
      //   59: astore 4
      //   61: sipush 1024
      //   64: newarray byte
      //   66: astore 12
      //   68: aload 4
      //   70: ifnull +18 -> 88
      //   73: aload 4
      //   75: aload 12
      //   77: invokevirtual 53	java/io/InputStream:read	([B)I
      //   80: istore 13
      //   82: iload 13
      //   84: iconst_m1
      //   85: if_icmpne +19 -> 104
      //   88: aload 4
      //   90: invokestatic 59	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
      //   93: aload_3
      //   94: invokevirtual 63	java/lang/StringBuffer:toString	()Ljava/lang/String;
      //   97: astore 9
      //   99: aload_0
      //   100: monitorexit
      //   101: aload 9
      //   103: areturn
      //   104: aload_3
      //   105: new 65	java/lang/String
      //   108: dup
      //   109: aload 12
      //   111: invokespecial 68	java/lang/String:<init>	([B)V
      //   114: invokevirtual 72	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   117: pop
      //   118: goto -45 -> 73
      //   121: astore 8
      //   123: aload 8
      //   125: invokevirtual 75	java/lang/Exception:printStackTrace	()V
      //   128: aload 4
      //   130: invokestatic 59	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
      //   133: goto -40 -> 93
      //   136: astore 7
      //   138: aload_0
      //   139: monitorexit
      //   140: aload 7
      //   142: athrow
      //   143: astore 6
      //   145: aload 4
      //   147: invokestatic 59	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
      //   150: aload 6
      //   152: athrow
      //
      // Exception table:
      //   from	to	target	type
      //   13	23	121	java/lang/Exception
      //   30	68	121	java/lang/Exception
      //   73	82	121	java/lang/Exception
      //   104	118	121	java/lang/Exception
      //   2	10	136	finally
      //   88	93	136	finally
      //   93	99	136	finally
      //   128	133	136	finally
      //   145	153	136	finally
      //   13	23	143	finally
      //   30	68	143	finally
      //   73	82	143	finally
      //   104	118	143	finally
      //   123	128	143	finally
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.util.CheckSupportFlashUtil
 * JD-Core Version:    0.6.0
 */