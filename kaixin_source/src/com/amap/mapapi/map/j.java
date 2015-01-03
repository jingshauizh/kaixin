package com.amap.mapapi.map;

import android.content.Context;
import android.os.Environment;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

class j
{
  private h a = null;
  private String b = "/sdcard/Amap/RMap";
  private final int c = 128;

  public j(Context paramContext, boolean paramBoolean, w paramw)
  {
    if (paramw == null)
      return;
    if (paramBoolean == true)
      this.b = paramContext.getFilesDir().getPath();
    while (true)
    {
      this.b = (this.b + "/Amap/" + paramw.a);
      return;
      boolean bool1 = paramw.l.equals("");
      boolean bool2 = false;
      if (!bool1)
      {
        File localFile = new File(paramw.l);
        bool2 = localFile.exists();
        if (!bool2)
          bool2 = localFile.mkdirs();
      }
      if (bool2)
        continue;
      if (!Environment.getExternalStorageState().equals("mounted"))
      {
        this.b = paramContext.getFilesDir().getPath();
        continue;
      }
      this.b = Environment.getExternalStorageDirectory().getPath();
    }
  }

  private int a(int paramInt1, int paramInt2)
  {
    int i = paramInt1 % 128;
    return paramInt2 % 128 + i * 128;
  }

  private void a(byte[] paramArrayOfByte)
  {
    if ((paramArrayOfByte == null) || (paramArrayOfByte.length != 4))
      return;
    int i = paramArrayOfByte[0];
    paramArrayOfByte[0] = paramArrayOfByte[3];
    paramArrayOfByte[3] = i;
    int j = paramArrayOfByte[1];
    paramArrayOfByte[1] = paramArrayOfByte[2];
    paramArrayOfByte[2] = j;
  }

  private byte[] a(int paramInt)
  {
    byte[] arrayOfByte = new byte[4];
    arrayOfByte[0] = (byte)(paramInt & 0xFF);
    arrayOfByte[1] = (byte)((0xFF00 & paramInt) >> 8);
    arrayOfByte[2] = (byte)((0xFF0000 & paramInt) >> 16);
    arrayOfByte[3] = (byte)((0xFF000000 & paramInt) >> 24);
    return arrayOfByte;
  }

  private String[] a(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
  {
    int i = paramInt1 / 128;
    int j = paramInt2 / 128;
    int k = i / 10;
    int m = j / 10;
    String[] arrayOfString = new String[2];
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.b);
    localStringBuilder.append("/");
    localStringBuilder.append(paramInt3);
    localStringBuilder.append("/");
    localStringBuilder.append(k);
    localStringBuilder.append("/");
    localStringBuilder.append(m);
    localStringBuilder.append("/");
    if (!paramBoolean)
    {
      File localFile = new File(localStringBuilder.toString());
      if (!localFile.exists())
        localFile.mkdirs();
    }
    localStringBuilder.append(paramInt3);
    localStringBuilder.append("_");
    localStringBuilder.append(i);
    localStringBuilder.append("_");
    localStringBuilder.append(j);
    arrayOfString[0] = (localStringBuilder.toString() + ".idx");
    arrayOfString[1] = (localStringBuilder.toString() + ".dat");
    return arrayOfString;
  }

  private int b(byte[] paramArrayOfByte)
  {
    return 0xFF & paramArrayOfByte[0] | 0xFF00 & paramArrayOfByte[1] << 8 | 0xFF0000 & paramArrayOfByte[2] << 16 | 0xFF000000 & paramArrayOfByte[3] << 24;
  }

  public int a(av.a parama)
  {
    String[] arrayOfString = a(parama.b, parama.c, parama.d, true);
    if ((arrayOfString == null) || (arrayOfString.length != 2) || (arrayOfString[0].equals("")) || (arrayOfString.equals("")));
    while (true)
    {
      return -1;
      File localFile1 = new File(arrayOfString[0]);
      if (!localFile1.exists())
        continue;
      int i = a(parama.b, parama.c);
      if (i < 0)
        continue;
      RandomAccessFile localRandomAccessFile3;
      int k;
      byte[] arrayOfByte2;
      try
      {
        localRandomAccessFile1 = new RandomAccessFile(localFile1, "r");
        if (localRandomAccessFile1 == null)
          continue;
        l1 = i * 4;
      }
      catch (IOException localIOException4)
      {
        try
        {
          long l1;
          localRandomAccessFile1.seek(l1);
          arrayOfByte1 = new byte[4];
        }
        catch (IOException localIOException4)
        {
          try
          {
            localRandomAccessFile1.read(arrayOfByte1, 0, 4);
            a(arrayOfByte1);
            j = b(arrayOfByte1);
          }
          catch (IOException localIOException4)
          {
            try
            {
              localRandomAccessFile1.close();
              if (j < 0)
                continue;
              localFile2 = new File(arrayOfString[1]);
              if (!localFile2.exists())
                continue;
            }
            catch (IOException localIOException4)
            {
              try
              {
                int j;
                File localFile2;
                RandomAccessFile localRandomAccessFile2 = new RandomAccessFile(localFile2, "r");
                localRandomAccessFile3 = localRandomAccessFile2;
                if (localRandomAccessFile3 == null)
                  continue;
                l2 = j;
              }
              catch (IOException localIOException4)
              {
                try
                {
                  long l2;
                  localRandomAccessFile3.seek(l2);
                }
                catch (IOException localIOException4)
                {
                  try
                  {
                    while (true)
                    {
                      byte[] arrayOfByte1;
                      localRandomAccessFile3.read(arrayOfByte1, 0, 4);
                      a(arrayOfByte1);
                      k = b(arrayOfByte1);
                      if (k > 0)
                        break;
                      try
                      {
                        localRandomAccessFile3.close();
                        return -1;
                      }
                      catch (IOException localIOException8)
                      {
                        localIOException8.printStackTrace();
                        return -1;
                      }
                      localFileNotFoundException2 = localFileNotFoundException2;
                      localFileNotFoundException2.printStackTrace();
                      RandomAccessFile localRandomAccessFile1 = null;
                      continue;
                      localIOException1 = localIOException1;
                      localIOException1.printStackTrace();
                      continue;
                      localIOException2 = localIOException2;
                      localIOException2.printStackTrace();
                      continue;
                      localIOException3 = localIOException3;
                      localIOException3.printStackTrace();
                      continue;
                      localFileNotFoundException1 = localFileNotFoundException1;
                      localFileNotFoundException1.printStackTrace();
                      localRandomAccessFile3 = null;
                      continue;
                      localIOException4 = localIOException4;
                      localIOException4.printStackTrace();
                    }
                  }
                  catch (IOException localIOException5)
                  {
                    while (true)
                      localIOException5.printStackTrace();
                    arrayOfByte2 = new byte[k];
                  }
                }
              }
            }
          }
        }
      }
      try
      {
        localRandomAccessFile3.read(arrayOfByte2, 0, k);
      }
      catch (IOException localIOException6)
      {
        try
        {
          while (true)
          {
            localRandomAccessFile3.close();
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append(parama.b);
            localStringBuilder.append("-");
            localStringBuilder.append(parama.c);
            localStringBuilder.append("-");
            localStringBuilder.append(parama.d);
            if (this.a == null)
              break;
            return this.a.a(arrayOfByte2, null, true, null, localStringBuilder.toString());
            localIOException6 = localIOException6;
            localIOException6.printStackTrace();
          }
        }
        catch (IOException localIOException7)
        {
          while (true)
            localIOException7.printStackTrace();
        }
      }
    }
  }

  public void a(h paramh)
  {
    this.a = paramh;
  }

  // ERROR //
  public boolean a(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: iconst_0
    //   3: istore 5
    //   5: aload_1
    //   6: ifnonnull +8 -> 14
    //   9: aload_0
    //   10: monitorexit
    //   11: iload 5
    //   13: ireturn
    //   14: aload_1
    //   15: arraylength
    //   16: istore 7
    //   18: iconst_0
    //   19: istore 5
    //   21: iload 7
    //   23: ifle -14 -> 9
    //   26: aload_0
    //   27: iload_2
    //   28: iload_3
    //   29: iload 4
    //   31: iconst_0
    //   32: invokespecial 116	com/amap/mapapi/map/j:a	(IIIZ)[Ljava/lang/String;
    //   35: astore 8
    //   37: iconst_0
    //   38: istore 5
    //   40: aload 8
    //   42: ifnull -33 -> 9
    //   45: aload 8
    //   47: arraylength
    //   48: istore 9
    //   50: iconst_0
    //   51: istore 5
    //   53: iload 9
    //   55: iconst_2
    //   56: if_icmpne -47 -> 9
    //   59: aload 8
    //   61: iconst_0
    //   62: aaload
    //   63: ldc 56
    //   65: invokevirtual 62	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   68: istore 10
    //   70: iconst_0
    //   71: istore 5
    //   73: iload 10
    //   75: ifne -66 -> 9
    //   78: aload 8
    //   80: ldc 56
    //   82: invokevirtual 117	java/lang/Object:equals	(Ljava/lang/Object;)Z
    //   85: istore 11
    //   87: iconst_0
    //   88: istore 5
    //   90: iload 11
    //   92: ifne -83 -> 9
    //   95: new 31	java/io/File
    //   98: dup
    //   99: aload 8
    //   101: iconst_1
    //   102: aaload
    //   103: invokespecial 65	java/io/File:<init>	(Ljava/lang/String;)V
    //   106: astore 12
    //   108: aload 12
    //   110: invokevirtual 69	java/io/File:exists	()Z
    //   113: istore 13
    //   115: iload 13
    //   117: ifne +22 -> 139
    //   120: aload 12
    //   122: invokevirtual 157	java/io/File:createNewFile	()Z
    //   125: istore 52
    //   127: iload 52
    //   129: istore 51
    //   131: iconst_0
    //   132: istore 5
    //   134: iload 51
    //   136: ifeq -127 -> 9
    //   139: new 121	java/io/RandomAccessFile
    //   142: dup
    //   143: aload 12
    //   145: ldc 159
    //   147: invokespecial 126	java/io/RandomAccessFile:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   150: astore 14
    //   152: iconst_0
    //   153: istore 5
    //   155: aload 14
    //   157: ifnull -148 -> 9
    //   160: aload_0
    //   161: iload 7
    //   163: invokespecial 161	com/amap/mapapi/map/j:a	(I)[B
    //   166: astore 15
    //   168: aload_0
    //   169: aload 15
    //   171: invokespecial 136	com/amap/mapapi/map/j:a	([B)V
    //   174: aload 14
    //   176: invokevirtual 165	java/io/RandomAccessFile:length	()J
    //   179: lstore 47
    //   181: lload 47
    //   183: lstore 17
    //   185: aload 14
    //   187: lload 17
    //   189: invokevirtual 130	java/io/RandomAccessFile:seek	(J)V
    //   192: aload 14
    //   194: aload 15
    //   196: invokevirtual 168	java/io/RandomAccessFile:write	([B)V
    //   199: aload 14
    //   201: aload_1
    //   202: invokevirtual 168	java/io/RandomAccessFile:write	([B)V
    //   205: aload 14
    //   207: invokevirtual 141	java/io/RandomAccessFile:close	()V
    //   210: new 31	java/io/File
    //   213: dup
    //   214: aload 8
    //   216: iconst_0
    //   217: aaload
    //   218: invokespecial 65	java/io/File:<init>	(Ljava/lang/String;)V
    //   221: astore 23
    //   223: aload 23
    //   225: invokevirtual 69	java/io/File:exists	()Z
    //   228: istore 24
    //   230: iload 24
    //   232: ifne +22 -> 254
    //   235: aload 23
    //   237: invokevirtual 157	java/io/File:createNewFile	()Z
    //   240: istore 46
    //   242: iload 46
    //   244: istore 45
    //   246: iconst_0
    //   247: istore 5
    //   249: iload 45
    //   251: ifeq -242 -> 9
    //   254: new 121	java/io/RandomAccessFile
    //   257: dup
    //   258: aload 23
    //   260: ldc 159
    //   262: invokespecial 126	java/io/RandomAccessFile:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   265: astore 25
    //   267: aload 25
    //   269: astore 26
    //   271: iconst_0
    //   272: istore 5
    //   274: aload 26
    //   276: ifnull -267 -> 9
    //   279: aload 26
    //   281: invokevirtual 165	java/io/RandomAccessFile:length	()J
    //   284: lstore 41
    //   286: lload 41
    //   288: lstore 28
    //   290: lload 28
    //   292: lconst_0
    //   293: lcmp
    //   294: ifne +22 -> 316
    //   297: ldc 169
    //   299: newarray byte
    //   301: astore 39
    //   303: aload 39
    //   305: iconst_m1
    //   306: invokestatic 175	java/util/Arrays:fill	([BB)V
    //   309: aload 26
    //   311: aload 39
    //   313: invokevirtual 168	java/io/RandomAccessFile:write	([B)V
    //   316: aload_0
    //   317: iload_2
    //   318: iload_3
    //   319: invokespecial 119	com/amap/mapapi/map/j:a	(II)I
    //   322: istore 30
    //   324: iload 30
    //   326: ifge +162 -> 488
    //   329: aload 26
    //   331: invokevirtual 141	java/io/RandomAccessFile:close	()V
    //   334: iconst_0
    //   335: istore 5
    //   337: goto -328 -> 9
    //   340: astore 38
    //   342: aload 38
    //   344: invokevirtual 144	java/io/IOException:printStackTrace	()V
    //   347: iconst_0
    //   348: istore 5
    //   350: goto -341 -> 9
    //   353: astore 6
    //   355: aload_0
    //   356: monitorexit
    //   357: aload 6
    //   359: athrow
    //   360: astore 50
    //   362: aload 50
    //   364: invokevirtual 144	java/io/IOException:printStackTrace	()V
    //   367: iconst_0
    //   368: istore 51
    //   370: goto -239 -> 131
    //   373: astore 49
    //   375: aload 49
    //   377: invokevirtual 145	java/io/FileNotFoundException:printStackTrace	()V
    //   380: aconst_null
    //   381: astore 14
    //   383: goto -231 -> 152
    //   386: astore 16
    //   388: aload 16
    //   390: invokevirtual 144	java/io/IOException:printStackTrace	()V
    //   393: lconst_0
    //   394: lstore 17
    //   396: goto -211 -> 185
    //   399: astore 19
    //   401: aload 19
    //   403: invokevirtual 144	java/io/IOException:printStackTrace	()V
    //   406: goto -214 -> 192
    //   409: astore 20
    //   411: aload 20
    //   413: invokevirtual 144	java/io/IOException:printStackTrace	()V
    //   416: goto -217 -> 199
    //   419: astore 21
    //   421: aload 21
    //   423: invokevirtual 144	java/io/IOException:printStackTrace	()V
    //   426: goto -221 -> 205
    //   429: astore 22
    //   431: aload 22
    //   433: invokevirtual 144	java/io/IOException:printStackTrace	()V
    //   436: goto -226 -> 210
    //   439: astore 44
    //   441: aload 44
    //   443: invokevirtual 144	java/io/IOException:printStackTrace	()V
    //   446: iconst_0
    //   447: istore 45
    //   449: goto -203 -> 246
    //   452: astore 43
    //   454: aload 43
    //   456: invokevirtual 145	java/io/FileNotFoundException:printStackTrace	()V
    //   459: aconst_null
    //   460: astore 26
    //   462: goto -191 -> 271
    //   465: astore 27
    //   467: aload 27
    //   469: invokevirtual 144	java/io/IOException:printStackTrace	()V
    //   472: lconst_0
    //   473: lstore 28
    //   475: goto -185 -> 290
    //   478: astore 40
    //   480: aload 40
    //   482: invokevirtual 144	java/io/IOException:printStackTrace	()V
    //   485: goto -169 -> 316
    //   488: iload 30
    //   490: iconst_4
    //   491: imul
    //   492: i2l
    //   493: lstore 31
    //   495: aload 26
    //   497: lload 31
    //   499: invokevirtual 130	java/io/RandomAccessFile:seek	(J)V
    //   502: lload 17
    //   504: l2i
    //   505: istore 34
    //   507: aload_0
    //   508: iload 34
    //   510: invokespecial 161	com/amap/mapapi/map/j:a	(I)[B
    //   513: astore 35
    //   515: aload_0
    //   516: aload 35
    //   518: invokespecial 136	com/amap/mapapi/map/j:a	([B)V
    //   521: aload 26
    //   523: aload 35
    //   525: invokevirtual 168	java/io/RandomAccessFile:write	([B)V
    //   528: aload 26
    //   530: invokevirtual 141	java/io/RandomAccessFile:close	()V
    //   533: iconst_1
    //   534: istore 5
    //   536: goto -527 -> 9
    //   539: astore 33
    //   541: aload 33
    //   543: invokevirtual 144	java/io/IOException:printStackTrace	()V
    //   546: goto -44 -> 502
    //   549: astore 36
    //   551: aload 36
    //   553: invokevirtual 144	java/io/IOException:printStackTrace	()V
    //   556: goto -28 -> 528
    //   559: astore 37
    //   561: aload 37
    //   563: invokevirtual 144	java/io/IOException:printStackTrace	()V
    //   566: goto -33 -> 533
    //
    // Exception table:
    //   from	to	target	type
    //   329	334	340	java/io/IOException
    //   14	18	353	finally
    //   26	37	353	finally
    //   45	50	353	finally
    //   59	70	353	finally
    //   78	87	353	finally
    //   95	115	353	finally
    //   120	127	353	finally
    //   139	152	353	finally
    //   160	174	353	finally
    //   174	181	353	finally
    //   185	192	353	finally
    //   192	199	353	finally
    //   199	205	353	finally
    //   205	210	353	finally
    //   210	230	353	finally
    //   235	242	353	finally
    //   254	267	353	finally
    //   279	286	353	finally
    //   297	309	353	finally
    //   309	316	353	finally
    //   316	324	353	finally
    //   329	334	353	finally
    //   342	347	353	finally
    //   362	367	353	finally
    //   375	380	353	finally
    //   388	393	353	finally
    //   401	406	353	finally
    //   411	416	353	finally
    //   421	426	353	finally
    //   431	436	353	finally
    //   441	446	353	finally
    //   454	459	353	finally
    //   467	472	353	finally
    //   480	485	353	finally
    //   495	502	353	finally
    //   507	521	353	finally
    //   521	528	353	finally
    //   528	533	353	finally
    //   541	546	353	finally
    //   551	556	353	finally
    //   561	566	353	finally
    //   120	127	360	java/io/IOException
    //   139	152	373	java/io/FileNotFoundException
    //   174	181	386	java/io/IOException
    //   185	192	399	java/io/IOException
    //   192	199	409	java/io/IOException
    //   199	205	419	java/io/IOException
    //   205	210	429	java/io/IOException
    //   235	242	439	java/io/IOException
    //   254	267	452	java/io/FileNotFoundException
    //   279	286	465	java/io/IOException
    //   309	316	478	java/io/IOException
    //   495	502	539	java/io/IOException
    //   521	528	549	java/io/IOException
    //   528	533	559	java/io/IOException
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.map.j
 * JD-Core Version:    0.6.0
 */