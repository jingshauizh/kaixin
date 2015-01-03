package com.tencent.mm.sdk.platformtools;

import java.io.File;

public final class FilesCopy
{
  public static boolean copy(String paramString1, String paramString2, boolean paramBoolean)
  {
    int i = 0;
    File localFile1 = new File(paramString1);
    if (!localFile1.exists());
    File localFile2;
    while (true)
    {
      return false;
      localFile2 = new File(paramString2);
      if (localFile1.isFile())
      {
        if ((!localFile2.isFile()) && (localFile2.exists()))
          continue;
        copyFile(paramString1, paramString2);
        if (!paramBoolean)
          break;
        localFile1.delete();
      }
    }
    while (true)
    {
      return true;
      if (!localFile1.isDirectory())
        continue;
      if (!localFile2.exists())
        localFile2.mkdir();
      if (!localFile2.isDirectory())
        break;
      String[] arrayOfString = localFile1.list();
      while (i < arrayOfString.length)
      {
        copy(paramString1 + "/" + arrayOfString[i], paramString2 + "/" + arrayOfString[i], paramBoolean);
        i++;
      }
    }
  }

  // ERROR //
  public static boolean copyAssets(android.content.Context paramContext, String paramString1, String paramString2)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 66	android/content/Context:getAssets	()Landroid/content/res/AssetManager;
    //   4: aload_1
    //   5: invokevirtual 72	android/content/res/AssetManager:open	(Ljava/lang/String;)Ljava/io/InputStream;
    //   8: astore 8
    //   10: new 74	java/io/FileOutputStream
    //   13: dup
    //   14: aload_2
    //   15: invokespecial 75	java/io/FileOutputStream:<init>	(Ljava/lang/String;)V
    //   18: astore 4
    //   20: sipush 16384
    //   23: newarray byte
    //   25: astore 9
    //   27: aload 8
    //   29: aload 9
    //   31: invokevirtual 81	java/io/InputStream:read	([B)I
    //   34: istore 10
    //   36: iload 10
    //   38: iconst_m1
    //   39: if_icmpeq +33 -> 72
    //   42: aload 4
    //   44: aload 9
    //   46: iconst_0
    //   47: iload 10
    //   49: invokevirtual 85	java/io/FileOutputStream:write	([BII)V
    //   52: goto -25 -> 27
    //   55: astore_3
    //   56: aload_3
    //   57: invokevirtual 88	java/lang/Exception:printStackTrace	()V
    //   60: aload 4
    //   62: ifnull +171 -> 233
    //   65: aload 4
    //   67: invokevirtual 91	java/io/FileOutputStream:close	()V
    //   70: iconst_0
    //   71: ireturn
    //   72: new 12	java/io/File
    //   75: dup
    //   76: aload_2
    //   77: invokespecial 15	java/io/File:<init>	(Ljava/lang/String;)V
    //   80: astore 11
    //   82: aload 8
    //   84: invokevirtual 92	java/io/InputStream:close	()V
    //   87: aload_0
    //   88: invokevirtual 66	android/content/Context:getAssets	()Landroid/content/res/AssetManager;
    //   91: aload_1
    //   92: invokevirtual 72	android/content/res/AssetManager:open	(Ljava/lang/String;)Ljava/io/InputStream;
    //   95: invokevirtual 96	java/io/InputStream:available	()I
    //   98: istore 12
    //   100: aload 11
    //   102: invokevirtual 19	java/io/File:exists	()Z
    //   105: ifeq +53 -> 158
    //   108: iload 12
    //   110: i2l
    //   111: lstore 13
    //   113: aload 11
    //   115: invokevirtual 100	java/io/File:length	()J
    //   118: lstore 15
    //   120: lload 13
    //   122: lload 15
    //   124: lcmp
    //   125: ifne +33 -> 158
    //   128: iconst_1
    //   129: istore 17
    //   131: aload 4
    //   133: invokevirtual 91	java/io/FileOutputStream:close	()V
    //   136: iload 17
    //   138: ireturn
    //   139: astore 18
    //   141: ldc 102
    //   143: aconst_null
    //   144: iconst_1
    //   145: anewarray 4	java/lang/Object
    //   148: dup
    //   149: iconst_0
    //   150: aload 18
    //   152: aastore
    //   153: invokestatic 108	com/tencent/mm/sdk/platformtools/Log:e	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   156: iconst_0
    //   157: ireturn
    //   158: iconst_0
    //   159: istore 17
    //   161: goto -30 -> 131
    //   164: astore 7
    //   166: ldc 102
    //   168: aconst_null
    //   169: iconst_1
    //   170: anewarray 4	java/lang/Object
    //   173: dup
    //   174: iconst_0
    //   175: aload 7
    //   177: aastore
    //   178: invokestatic 108	com/tencent/mm/sdk/platformtools/Log:e	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   181: iconst_0
    //   182: ireturn
    //   183: astore 5
    //   185: aconst_null
    //   186: astore 4
    //   188: aload 4
    //   190: ifnull +8 -> 198
    //   193: aload 4
    //   195: invokevirtual 91	java/io/FileOutputStream:close	()V
    //   198: aload 5
    //   200: athrow
    //   201: astore 6
    //   203: ldc 102
    //   205: aconst_null
    //   206: iconst_1
    //   207: anewarray 4	java/lang/Object
    //   210: dup
    //   211: iconst_0
    //   212: aload 6
    //   214: aastore
    //   215: invokestatic 108	com/tencent/mm/sdk/platformtools/Log:e	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   218: goto -20 -> 198
    //   221: astore 5
    //   223: goto -35 -> 188
    //   226: astore_3
    //   227: aconst_null
    //   228: astore 4
    //   230: goto -174 -> 56
    //   233: iconst_0
    //   234: ireturn
    //
    // Exception table:
    //   from	to	target	type
    //   20	27	55	java/lang/Exception
    //   27	36	55	java/lang/Exception
    //   42	52	55	java/lang/Exception
    //   72	108	55	java/lang/Exception
    //   113	120	55	java/lang/Exception
    //   131	136	139	java/io/IOException
    //   65	70	164	java/io/IOException
    //   0	20	183	finally
    //   193	198	201	java/io/IOException
    //   20	27	221	finally
    //   27	36	221	finally
    //   42	52	221	finally
    //   56	60	221	finally
    //   72	108	221	finally
    //   113	120	221	finally
    //   0	20	226	java/lang/Exception
  }

  // ERROR //
  public static boolean copyFile(String paramString1, String paramString2)
  {
    // Byte code:
    //   0: new 110	java/io/FileInputStream
    //   3: dup
    //   4: aload_0
    //   5: invokespecial 111	java/io/FileInputStream:<init>	(Ljava/lang/String;)V
    //   8: astore_2
    //   9: new 74	java/io/FileOutputStream
    //   12: dup
    //   13: aload_1
    //   14: invokespecial 75	java/io/FileOutputStream:<init>	(Ljava/lang/String;)V
    //   17: astore_3
    //   18: sipush 16384
    //   21: newarray byte
    //   23: astore 11
    //   25: aload_2
    //   26: aload 11
    //   28: invokevirtual 112	java/io/FileInputStream:read	([B)I
    //   31: istore 12
    //   33: iload 12
    //   35: iconst_m1
    //   36: if_icmpeq +45 -> 81
    //   39: aload_3
    //   40: aload 11
    //   42: iconst_0
    //   43: iload 12
    //   45: invokevirtual 85	java/io/FileOutputStream:write	([BII)V
    //   48: goto -23 -> 25
    //   51: astore 7
    //   53: aload_2
    //   54: astore 8
    //   56: aload 7
    //   58: invokevirtual 88	java/lang/Exception:printStackTrace	()V
    //   61: aload 8
    //   63: ifnull +8 -> 71
    //   66: aload 8
    //   68: invokevirtual 113	java/io/FileInputStream:close	()V
    //   71: aload_3
    //   72: ifnull +149 -> 221
    //   75: aload_3
    //   76: invokevirtual 91	java/io/FileOutputStream:close	()V
    //   79: iconst_0
    //   80: ireturn
    //   81: iconst_1
    //   82: istore 13
    //   84: aload_2
    //   85: invokevirtual 113	java/io/FileInputStream:close	()V
    //   88: aload_3
    //   89: invokevirtual 91	java/io/FileOutputStream:close	()V
    //   92: iload 13
    //   94: ireturn
    //   95: astore 15
    //   97: aload 15
    //   99: invokevirtual 114	java/io/IOException:printStackTrace	()V
    //   102: iconst_0
    //   103: ireturn
    //   104: astore 14
    //   106: aload 14
    //   108: invokevirtual 114	java/io/IOException:printStackTrace	()V
    //   111: iconst_0
    //   112: istore 13
    //   114: goto -26 -> 88
    //   117: astore 10
    //   119: aload 10
    //   121: invokevirtual 114	java/io/IOException:printStackTrace	()V
    //   124: goto -53 -> 71
    //   127: astore 9
    //   129: aload 9
    //   131: invokevirtual 114	java/io/IOException:printStackTrace	()V
    //   134: iconst_0
    //   135: ireturn
    //   136: astore 4
    //   138: aconst_null
    //   139: astore_3
    //   140: aconst_null
    //   141: astore_2
    //   142: aload_2
    //   143: ifnull +7 -> 150
    //   146: aload_2
    //   147: invokevirtual 113	java/io/FileInputStream:close	()V
    //   150: aload_3
    //   151: ifnull +7 -> 158
    //   154: aload_3
    //   155: invokevirtual 91	java/io/FileOutputStream:close	()V
    //   158: aload 4
    //   160: athrow
    //   161: astore 6
    //   163: aload 6
    //   165: invokevirtual 114	java/io/IOException:printStackTrace	()V
    //   168: goto -18 -> 150
    //   171: astore 5
    //   173: aload 5
    //   175: invokevirtual 114	java/io/IOException:printStackTrace	()V
    //   178: goto -20 -> 158
    //   181: astore 4
    //   183: aconst_null
    //   184: astore_3
    //   185: goto -43 -> 142
    //   188: astore 4
    //   190: goto -48 -> 142
    //   193: astore 4
    //   195: aload 8
    //   197: astore_2
    //   198: goto -56 -> 142
    //   201: astore 7
    //   203: aconst_null
    //   204: astore_3
    //   205: aconst_null
    //   206: astore 8
    //   208: goto -152 -> 56
    //   211: astore 7
    //   213: aload_2
    //   214: astore 8
    //   216: aconst_null
    //   217: astore_3
    //   218: goto -162 -> 56
    //   221: iconst_0
    //   222: ireturn
    //
    // Exception table:
    //   from	to	target	type
    //   18	25	51	java/lang/Exception
    //   25	33	51	java/lang/Exception
    //   39	48	51	java/lang/Exception
    //   88	92	95	java/io/IOException
    //   84	88	104	java/io/IOException
    //   66	71	117	java/io/IOException
    //   75	79	127	java/io/IOException
    //   0	9	136	finally
    //   146	150	161	java/io/IOException
    //   154	158	171	java/io/IOException
    //   9	18	181	finally
    //   18	25	188	finally
    //   25	33	188	finally
    //   39	48	188	finally
    //   56	61	193	finally
    //   0	9	201	java/lang/Exception
    //   9	18	211	java/lang/Exception
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.platformtools.FilesCopy
 * JD-Core Version:    0.6.0
 */