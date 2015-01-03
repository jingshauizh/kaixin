package com.kaixin001.lbs.cell;

public class NetworkRequest
{
  private static final String TAG = "TESTAPP";

  // ERROR //
  public static String doGet(String paramString)
  {
    // Byte code:
    //   0: ldc 8
    //   2: new 22	java/lang/StringBuilder
    //   5: dup
    //   6: ldc 24
    //   8: invokespecial 27	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   11: aload_0
    //   12: invokevirtual 31	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   15: invokevirtual 35	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   18: invokestatic 41	com/kaixin001/util/KXLog:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   21: aconst_null
    //   22: astore_1
    //   23: aconst_null
    //   24: astore_2
    //   25: new 43	org/apache/http/impl/client/DefaultHttpClient
    //   28: dup
    //   29: invokespecial 44	org/apache/http/impl/client/DefaultHttpClient:<init>	()V
    //   32: astore_3
    //   33: new 46	org/apache/http/client/methods/HttpGet
    //   36: dup
    //   37: aload_0
    //   38: invokespecial 47	org/apache/http/client/methods/HttpGet:<init>	(Ljava/lang/String;)V
    //   41: astore 4
    //   43: aload 4
    //   45: ldc 49
    //   47: ldc 51
    //   49: invokevirtual 54	org/apache/http/client/methods/HttpGet:setHeader	(Ljava/lang/String;Ljava/lang/String;)V
    //   52: aload_3
    //   53: aload 4
    //   55: invokevirtual 58	org/apache/http/impl/client/DefaultHttpClient:execute	(Lorg/apache/http/client/methods/HttpUriRequest;)Lorg/apache/http/HttpResponse;
    //   58: astore 9
    //   60: aload 9
    //   62: invokeinterface 64 1 0
    //   67: invokeinterface 70 1 0
    //   72: istore 10
    //   74: iload 10
    //   76: sipush 200
    //   79: if_icmpeq +54 -> 133
    //   82: new 20	java/lang/Exception
    //   85: dup
    //   86: new 22	java/lang/StringBuilder
    //   89: dup
    //   90: ldc 72
    //   92: invokespecial 27	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   95: iload 10
    //   97: invokevirtual 75	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   100: invokevirtual 35	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   103: invokespecial 76	java/lang/Exception:<init>	(Ljava/lang/String;)V
    //   106: athrow
    //   107: astore 8
    //   109: aload 4
    //   111: astore_2
    //   112: aload_3
    //   113: astore_1
    //   114: aload 8
    //   116: invokevirtual 79	org/apache/http/client/ClientProtocolException:printStackTrace	()V
    //   119: aload_2
    //   120: ifnull +7 -> 127
    //   123: aload_2
    //   124: invokevirtual 82	org/apache/http/client/methods/HttpGet:abort	()V
    //   127: aload_1
    //   128: ifnull +3 -> 131
    //   131: aconst_null
    //   132: areturn
    //   133: aload 9
    //   135: invokeinterface 86 1 0
    //   140: ldc 88
    //   142: invokestatic 93	org/apache/http/util/EntityUtils:toString	(Lorg/apache/http/HttpEntity;Ljava/lang/String;)Ljava/lang/String;
    //   145: astore 11
    //   147: ldc 8
    //   149: new 22	java/lang/StringBuilder
    //   152: dup
    //   153: ldc 95
    //   155: invokespecial 27	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   158: aload 11
    //   160: invokevirtual 31	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   163: invokevirtual 35	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   166: invokestatic 41	com/kaixin001/util/KXLog:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   169: aload 4
    //   171: ifnull +8 -> 179
    //   174: aload 4
    //   176: invokevirtual 82	org/apache/http/client/methods/HttpGet:abort	()V
    //   179: aload_3
    //   180: ifnull +140 -> 320
    //   183: aload 11
    //   185: areturn
    //   186: astore 5
    //   188: aload 5
    //   190: invokevirtual 96	java/io/IOException:printStackTrace	()V
    //   193: aload_2
    //   194: ifnull +7 -> 201
    //   197: aload_2
    //   198: invokevirtual 82	org/apache/http/client/methods/HttpGet:abort	()V
    //   201: aload_1
    //   202: ifnull +3 -> 205
    //   205: aconst_null
    //   206: areturn
    //   207: astore 7
    //   209: aload 7
    //   211: invokevirtual 97	java/lang/Exception:printStackTrace	()V
    //   214: aload_2
    //   215: ifnull +7 -> 222
    //   218: aload_2
    //   219: invokevirtual 82	org/apache/http/client/methods/HttpGet:abort	()V
    //   222: aload_1
    //   223: ifnull +3 -> 226
    //   226: aconst_null
    //   227: areturn
    //   228: astore 6
    //   230: aload_2
    //   231: ifnull +7 -> 238
    //   234: aload_2
    //   235: invokevirtual 82	org/apache/http/client/methods/HttpGet:abort	()V
    //   238: aload_1
    //   239: ifnull +3 -> 242
    //   242: aload 6
    //   244: athrow
    //   245: astore 6
    //   247: aload_3
    //   248: astore_1
    //   249: aconst_null
    //   250: astore_2
    //   251: goto -21 -> 230
    //   254: astore 6
    //   256: aload 4
    //   258: astore_2
    //   259: aload_3
    //   260: astore_1
    //   261: goto -31 -> 230
    //   264: astore 7
    //   266: aload_3
    //   267: astore_1
    //   268: aconst_null
    //   269: astore_2
    //   270: goto -61 -> 209
    //   273: astore 7
    //   275: aload 4
    //   277: astore_2
    //   278: aload_3
    //   279: astore_1
    //   280: goto -71 -> 209
    //   283: astore 5
    //   285: aload_3
    //   286: astore_1
    //   287: aconst_null
    //   288: astore_2
    //   289: goto -101 -> 188
    //   292: astore 5
    //   294: aload 4
    //   296: astore_2
    //   297: aload_3
    //   298: astore_1
    //   299: goto -111 -> 188
    //   302: astore 8
    //   304: aconst_null
    //   305: astore_1
    //   306: aconst_null
    //   307: astore_2
    //   308: goto -194 -> 114
    //   311: astore 8
    //   313: aload_3
    //   314: astore_1
    //   315: aconst_null
    //   316: astore_2
    //   317: goto -203 -> 114
    //   320: goto -137 -> 183
    //
    // Exception table:
    //   from	to	target	type
    //   43	74	107	org/apache/http/client/ClientProtocolException
    //   82	107	107	org/apache/http/client/ClientProtocolException
    //   133	169	107	org/apache/http/client/ClientProtocolException
    //   25	33	186	java/io/IOException
    //   25	33	207	java/lang/Exception
    //   25	33	228	finally
    //   114	119	228	finally
    //   188	193	228	finally
    //   209	214	228	finally
    //   33	43	245	finally
    //   43	74	254	finally
    //   82	107	254	finally
    //   133	169	254	finally
    //   33	43	264	java/lang/Exception
    //   43	74	273	java/lang/Exception
    //   82	107	273	java/lang/Exception
    //   133	169	273	java/lang/Exception
    //   33	43	283	java/io/IOException
    //   43	74	292	java/io/IOException
    //   82	107	292	java/io/IOException
    //   133	169	292	java/io/IOException
    //   25	33	302	org/apache/http/client/ClientProtocolException
    //   33	43	311	org/apache/http/client/ClientProtocolException
  }

  // ERROR //
  public static String doPost(String paramString1, String paramString2)
  {
    // Byte code:
    //   0: ldc 8
    //   2: new 22	java/lang/StringBuilder
    //   5: dup
    //   6: ldc 101
    //   8: invokespecial 27	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   11: aload_0
    //   12: invokevirtual 31	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   15: invokevirtual 35	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   18: invokestatic 41	com/kaixin001/util/KXLog:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   21: ldc 8
    //   23: new 22	java/lang/StringBuilder
    //   26: dup
    //   27: ldc 103
    //   29: invokespecial 27	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   32: aload_1
    //   33: invokevirtual 31	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   36: invokevirtual 35	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   39: invokestatic 41	com/kaixin001/util/KXLog:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   42: aconst_null
    //   43: astore_2
    //   44: aconst_null
    //   45: astore_3
    //   46: new 105	org/apache/http/params/BasicHttpParams
    //   49: dup
    //   50: invokespecial 106	org/apache/http/params/BasicHttpParams:<init>	()V
    //   53: astore 4
    //   55: aload 4
    //   57: ldc 107
    //   59: invokestatic 113	org/apache/http/params/HttpConnectionParams:setConnectionTimeout	(Lorg/apache/http/params/HttpParams;I)V
    //   62: aload 4
    //   64: ldc 107
    //   66: invokestatic 116	org/apache/http/params/HttpConnectionParams:setSoTimeout	(Lorg/apache/http/params/HttpParams;I)V
    //   69: new 43	org/apache/http/impl/client/DefaultHttpClient
    //   72: dup
    //   73: aload 4
    //   75: invokespecial 119	org/apache/http/impl/client/DefaultHttpClient:<init>	(Lorg/apache/http/params/HttpParams;)V
    //   78: astore 9
    //   80: new 121	org/apache/http/client/methods/HttpPost
    //   83: dup
    //   84: aload_0
    //   85: invokespecial 122	org/apache/http/client/methods/HttpPost:<init>	(Ljava/lang/String;)V
    //   88: astore 10
    //   90: aload 10
    //   92: new 124	org/apache/http/entity/StringEntity
    //   95: dup
    //   96: aload_1
    //   97: invokespecial 125	org/apache/http/entity/StringEntity:<init>	(Ljava/lang/String;)V
    //   100: invokevirtual 129	org/apache/http/client/methods/HttpPost:setEntity	(Lorg/apache/http/HttpEntity;)V
    //   103: aload 9
    //   105: aload 10
    //   107: invokevirtual 58	org/apache/http/impl/client/DefaultHttpClient:execute	(Lorg/apache/http/client/methods/HttpUriRequest;)Lorg/apache/http/HttpResponse;
    //   110: astore 11
    //   112: aload 11
    //   114: invokeinterface 64 1 0
    //   119: invokeinterface 70 1 0
    //   124: istore 12
    //   126: iload 12
    //   128: sipush 200
    //   131: if_icmpeq +55 -> 186
    //   134: new 20	java/lang/Exception
    //   137: dup
    //   138: new 22	java/lang/StringBuilder
    //   141: dup
    //   142: ldc 72
    //   144: invokespecial 27	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   147: iload 12
    //   149: invokevirtual 75	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   152: invokevirtual 35	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   155: invokespecial 76	java/lang/Exception:<init>	(Ljava/lang/String;)V
    //   158: athrow
    //   159: astore 5
    //   161: aload 10
    //   163: astore_3
    //   164: aload 9
    //   166: astore_2
    //   167: aload 5
    //   169: invokevirtual 79	org/apache/http/client/ClientProtocolException:printStackTrace	()V
    //   172: aload_3
    //   173: ifnull +7 -> 180
    //   176: aload_3
    //   177: invokevirtual 130	org/apache/http/client/methods/HttpPost:abort	()V
    //   180: aload_2
    //   181: ifnull +3 -> 184
    //   184: aconst_null
    //   185: areturn
    //   186: aload 11
    //   188: invokeinterface 86 1 0
    //   193: ldc 88
    //   195: invokestatic 93	org/apache/http/util/EntityUtils:toString	(Lorg/apache/http/HttpEntity;Ljava/lang/String;)Ljava/lang/String;
    //   198: astore 13
    //   200: ldc 8
    //   202: new 22	java/lang/StringBuilder
    //   205: dup
    //   206: ldc 132
    //   208: invokespecial 27	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   211: aload 13
    //   213: invokevirtual 31	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   216: invokevirtual 35	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   219: invokestatic 41	com/kaixin001/util/KXLog:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   222: aload 10
    //   224: ifnull +8 -> 232
    //   227: aload 10
    //   229: invokevirtual 130	org/apache/http/client/methods/HttpPost:abort	()V
    //   232: aload 9
    //   234: ifnull +147 -> 381
    //   237: aload 13
    //   239: areturn
    //   240: astore 8
    //   242: aload 8
    //   244: invokevirtual 96	java/io/IOException:printStackTrace	()V
    //   247: aload_3
    //   248: ifnull +7 -> 255
    //   251: aload_3
    //   252: invokevirtual 130	org/apache/http/client/methods/HttpPost:abort	()V
    //   255: aload_2
    //   256: ifnull -72 -> 184
    //   259: aconst_null
    //   260: areturn
    //   261: astore 7
    //   263: aload 7
    //   265: invokevirtual 97	java/lang/Exception:printStackTrace	()V
    //   268: aload_3
    //   269: ifnull +7 -> 276
    //   272: aload_3
    //   273: invokevirtual 130	org/apache/http/client/methods/HttpPost:abort	()V
    //   276: aload_2
    //   277: ifnull -93 -> 184
    //   280: aconst_null
    //   281: areturn
    //   282: astore 6
    //   284: aload_3
    //   285: ifnull +7 -> 292
    //   288: aload_3
    //   289: invokevirtual 130	org/apache/http/client/methods/HttpPost:abort	()V
    //   292: aload_2
    //   293: ifnull +3 -> 296
    //   296: aload 6
    //   298: athrow
    //   299: astore 6
    //   301: aload 9
    //   303: astore_2
    //   304: aconst_null
    //   305: astore_3
    //   306: goto -22 -> 284
    //   309: astore 6
    //   311: aload 10
    //   313: astore_3
    //   314: aload 9
    //   316: astore_2
    //   317: goto -33 -> 284
    //   320: astore 7
    //   322: aload 9
    //   324: astore_2
    //   325: aconst_null
    //   326: astore_3
    //   327: goto -64 -> 263
    //   330: astore 7
    //   332: aload 10
    //   334: astore_3
    //   335: aload 9
    //   337: astore_2
    //   338: goto -75 -> 263
    //   341: astore 8
    //   343: aload 9
    //   345: astore_2
    //   346: aconst_null
    //   347: astore_3
    //   348: goto -106 -> 242
    //   351: astore 8
    //   353: aload 10
    //   355: astore_3
    //   356: aload 9
    //   358: astore_2
    //   359: goto -117 -> 242
    //   362: astore 5
    //   364: aconst_null
    //   365: astore_2
    //   366: aconst_null
    //   367: astore_3
    //   368: goto -201 -> 167
    //   371: astore 5
    //   373: aload 9
    //   375: astore_2
    //   376: aconst_null
    //   377: astore_3
    //   378: goto -211 -> 167
    //   381: aload 13
    //   383: areturn
    //
    // Exception table:
    //   from	to	target	type
    //   90	126	159	org/apache/http/client/ClientProtocolException
    //   134	159	159	org/apache/http/client/ClientProtocolException
    //   186	222	159	org/apache/http/client/ClientProtocolException
    //   46	80	240	java/io/IOException
    //   46	80	261	java/lang/Exception
    //   46	80	282	finally
    //   167	172	282	finally
    //   242	247	282	finally
    //   263	268	282	finally
    //   80	90	299	finally
    //   90	126	309	finally
    //   134	159	309	finally
    //   186	222	309	finally
    //   80	90	320	java/lang/Exception
    //   90	126	330	java/lang/Exception
    //   134	159	330	java/lang/Exception
    //   186	222	330	java/lang/Exception
    //   80	90	341	java/io/IOException
    //   90	126	351	java/io/IOException
    //   134	159	351	java/io/IOException
    //   186	222	351	java/io/IOException
    //   46	80	362	org/apache/http/client/ClientProtocolException
    //   80	90	371	org/apache/http/client/ClientProtocolException
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.lbs.cell.NetworkRequest
 * JD-Core Version:    0.6.0
 */