package com.tencent.record.a;

import android.os.StatFs;
import java.io.File;

public class e
{
  private File a;
  private long b;
  private long c;

  public static e b(File paramFile)
  {
    e locale = new e();
    locale.a(paramFile);
    StatFs localStatFs = new StatFs(paramFile.getAbsolutePath());
    long l1 = localStatFs.getBlockSize();
    long l2 = localStatFs.getBlockCount();
    long l3 = localStatFs.getAvailableBlocks();
    locale.a(l2 * l1);
    locale.b(l3 * l1);
    return locale;
  }

  public File a()
  {
    return this.a;
  }

  public void a(long paramLong)
  {
    this.b = paramLong;
  }

  public void a(File paramFile)
  {
    this.a = paramFile;
  }

  public long b()
  {
    return this.b;
  }

  public void b(long paramLong)
  {
    this.c = paramLong;
  }

  public long c()
  {
    return this.c;
  }

  public String toString()
  {
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = a().getAbsolutePath();
    arrayOfObject[1] = Long.valueOf(c());
    arrayOfObject[2] = Long.valueOf(b());
    return String.format("[%s : %d / %d]", arrayOfObject);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.record.a.e
 * JD-Core Version:    0.6.0
 */