package com.tencent.tauth;

class UploadFileToWeiyun$3 extends Thread
{
  // ERROR //
  public void run()
  {
    // Byte code:
    //   0: new 22	org/apache/http/params/BasicHttpParams
    //   3: dup
    //   4: invokespecial 23	org/apache/http/params/BasicHttpParams:<init>	()V
    //   7: astore_1
    //   8: aload_1
    //   9: sipush 15000
    //   12: invokestatic 29	org/apache/http/params/HttpConnectionParams:setConnectionTimeout	(Lorg/apache/http/params/HttpParams;I)V
    //   15: aload_1
    //   16: sipush 20000
    //   19: invokestatic 32	org/apache/http/params/HttpConnectionParams:setSoTimeout	(Lorg/apache/http/params/HttpParams;I)V
    //   22: aload_1
    //   23: getstatic 38	org/apache/http/HttpVersion:HTTP_1_1	Lorg/apache/http/HttpVersion;
    //   26: invokestatic 44	org/apache/http/params/HttpProtocolParams:setVersion	(Lorg/apache/http/params/HttpParams;Lorg/apache/http/ProtocolVersion;)V
    //   29: aload_1
    //   30: ldc 46
    //   32: invokestatic 50	org/apache/http/params/HttpProtocolParams:setContentCharset	(Lorg/apache/http/params/HttpParams;Ljava/lang/String;)V
    //   35: aload_1
    //   36: ldc 52
    //   38: invokestatic 55	org/apache/http/params/HttpProtocolParams:setUserAgent	(Lorg/apache/http/params/HttpParams;Ljava/lang/String;)V
    //   41: new 57	org/apache/http/impl/client/DefaultHttpClient
    //   44: dup
    //   45: aload_1
    //   46: invokespecial 60	org/apache/http/impl/client/DefaultHttpClient:<init>	(Lorg/apache/http/params/HttpParams;)V
    //   49: astore_2
    //   50: aload_0
    //   51: getfield 10	com/tencent/tauth/UploadFileToWeiyun$3:this$0	Lcom/tencent/tauth/UploadFileToWeiyun;
    //   54: iconst_0
    //   55: invokestatic 66	com/tencent/tauth/UploadFileToWeiyun:access$1302	(Lcom/tencent/tauth/UploadFileToWeiyun;I)I
    //   58: pop
    //   59: ldc 67
    //   61: newarray byte
    //   63: astore 4
    //   65: new 69	java/io/FileInputStream
    //   68: dup
    //   69: aload_0
    //   70: getfield 10	com/tencent/tauth/UploadFileToWeiyun$3:this$0	Lcom/tencent/tauth/UploadFileToWeiyun;
    //   73: invokestatic 73	com/tencent/tauth/UploadFileToWeiyun:access$700	(Lcom/tencent/tauth/UploadFileToWeiyun;)Ljava/lang/String;
    //   76: invokespecial 76	java/io/FileInputStream:<init>	(Ljava/lang/String;)V
    //   79: astore 5
    //   81: aload_0
    //   82: getfield 10	com/tencent/tauth/UploadFileToWeiyun$3:this$0	Lcom/tencent/tauth/UploadFileToWeiyun;
    //   85: invokestatic 80	com/tencent/tauth/UploadFileToWeiyun:access$1300	(Lcom/tencent/tauth/UploadFileToWeiyun;)I
    //   88: aload_0
    //   89: getfield 10	com/tencent/tauth/UploadFileToWeiyun$3:this$0	Lcom/tencent/tauth/UploadFileToWeiyun;
    //   92: invokestatic 83	com/tencent/tauth/UploadFileToWeiyun:access$1000	(Lcom/tencent/tauth/UploadFileToWeiyun;)I
    //   95: if_icmpge +58 -> 153
    //   98: aload 5
    //   100: aload 4
    //   102: invokevirtual 87	java/io/FileInputStream:read	([B)I
    //   105: istore 9
    //   107: aload_0
    //   108: getfield 10	com/tencent/tauth/UploadFileToWeiyun$3:this$0	Lcom/tencent/tauth/UploadFileToWeiyun;
    //   111: aload_0
    //   112: getfield 10	com/tencent/tauth/UploadFileToWeiyun$3:this$0	Lcom/tencent/tauth/UploadFileToWeiyun;
    //   115: aload 4
    //   117: iload 9
    //   119: aload_0
    //   120: getfield 10	com/tencent/tauth/UploadFileToWeiyun$3:this$0	Lcom/tencent/tauth/UploadFileToWeiyun;
    //   123: invokestatic 80	com/tencent/tauth/UploadFileToWeiyun:access$1300	(Lcom/tencent/tauth/UploadFileToWeiyun;)I
    //   126: invokestatic 91	com/tencent/tauth/UploadFileToWeiyun:access$1500	(Lcom/tencent/tauth/UploadFileToWeiyun;[BII)[B
    //   129: invokestatic 95	com/tencent/tauth/UploadFileToWeiyun:access$1402	(Lcom/tencent/tauth/UploadFileToWeiyun;[B)[B
    //   132: pop
    //   133: aload_0
    //   134: getfield 10	com/tencent/tauth/UploadFileToWeiyun$3:this$0	Lcom/tencent/tauth/UploadFileToWeiyun;
    //   137: iload 9
    //   139: invokestatic 98	com/tencent/tauth/UploadFileToWeiyun:access$1312	(Lcom/tencent/tauth/UploadFileToWeiyun;I)I
    //   142: pop
    //   143: aload_0
    //   144: getfield 10	com/tencent/tauth/UploadFileToWeiyun$3:this$0	Lcom/tencent/tauth/UploadFileToWeiyun;
    //   147: invokestatic 102	com/tencent/tauth/UploadFileToWeiyun:access$1400	(Lcom/tencent/tauth/UploadFileToWeiyun;)[B
    //   150: ifnonnull +96 -> 246
    //   153: return
    //   154: astore 27
    //   156: aload 27
    //   158: invokevirtual 105	java/io/FileNotFoundException:printStackTrace	()V
    //   161: aload_0
    //   162: getfield 10	com/tencent/tauth/UploadFileToWeiyun$3:this$0	Lcom/tencent/tauth/UploadFileToWeiyun;
    //   165: invokestatic 109	com/tencent/tauth/UploadFileToWeiyun:access$1200	(Lcom/tencent/tauth/UploadFileToWeiyun;)Landroid/os/Handler;
    //   168: invokevirtual 115	android/os/Handler:obtainMessage	()Landroid/os/Message;
    //   171: astore 28
    //   173: aload 28
    //   175: iconst_m1
    //   176: putfield 121	android/os/Message:what	I
    //   179: aload 28
    //   181: ldc 123
    //   183: putfield 127	android/os/Message:obj	Ljava/lang/Object;
    //   186: aload_0
    //   187: getfield 10	com/tencent/tauth/UploadFileToWeiyun$3:this$0	Lcom/tencent/tauth/UploadFileToWeiyun;
    //   190: invokestatic 109	com/tencent/tauth/UploadFileToWeiyun:access$1200	(Lcom/tencent/tauth/UploadFileToWeiyun;)Landroid/os/Handler;
    //   193: aload 28
    //   195: invokevirtual 131	android/os/Handler:sendMessage	(Landroid/os/Message;)Z
    //   198: pop
    //   199: return
    //   200: astore 6
    //   202: aload 6
    //   204: invokevirtual 132	java/io/IOException:printStackTrace	()V
    //   207: aload_0
    //   208: getfield 10	com/tencent/tauth/UploadFileToWeiyun$3:this$0	Lcom/tencent/tauth/UploadFileToWeiyun;
    //   211: invokestatic 109	com/tencent/tauth/UploadFileToWeiyun:access$1200	(Lcom/tencent/tauth/UploadFileToWeiyun;)Landroid/os/Handler;
    //   214: invokevirtual 115	android/os/Handler:obtainMessage	()Landroid/os/Message;
    //   217: astore 7
    //   219: aload 7
    //   221: iconst_m1
    //   222: putfield 121	android/os/Message:what	I
    //   225: aload 7
    //   227: ldc 134
    //   229: putfield 127	android/os/Message:obj	Ljava/lang/Object;
    //   232: aload_0
    //   233: getfield 10	com/tencent/tauth/UploadFileToWeiyun$3:this$0	Lcom/tencent/tauth/UploadFileToWeiyun;
    //   236: invokestatic 109	com/tencent/tauth/UploadFileToWeiyun:access$1200	(Lcom/tencent/tauth/UploadFileToWeiyun;)Landroid/os/Handler;
    //   239: aload 7
    //   241: invokevirtual 131	android/os/Handler:sendMessage	(Landroid/os/Message;)Z
    //   244: pop
    //   245: return
    //   246: new 136	org/apache/http/client/methods/HttpPost
    //   249: dup
    //   250: new 138	java/lang/StringBuilder
    //   253: dup
    //   254: invokespecial 139	java/lang/StringBuilder:<init>	()V
    //   257: ldc 141
    //   259: invokevirtual 145	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   262: aload_0
    //   263: getfield 10	com/tencent/tauth/UploadFileToWeiyun$3:this$0	Lcom/tencent/tauth/UploadFileToWeiyun;
    //   266: invokestatic 148	com/tencent/tauth/UploadFileToWeiyun:access$500	(Lcom/tencent/tauth/UploadFileToWeiyun;)Ljava/lang/String;
    //   269: invokevirtual 145	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   272: ldc 150
    //   274: invokevirtual 145	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   277: aload_0
    //   278: getfield 10	com/tencent/tauth/UploadFileToWeiyun$3:this$0	Lcom/tencent/tauth/UploadFileToWeiyun;
    //   281: invokestatic 153	com/tencent/tauth/UploadFileToWeiyun:access$900	(Lcom/tencent/tauth/UploadFileToWeiyun;)Ljava/lang/String;
    //   284: invokevirtual 145	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   287: invokevirtual 157	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   290: invokespecial 158	org/apache/http/client/methods/HttpPost:<init>	(Ljava/lang/String;)V
    //   293: astore 12
    //   295: aload 12
    //   297: ldc 160
    //   299: ldc 162
    //   301: invokevirtual 166	org/apache/http/client/methods/HttpPost:addHeader	(Ljava/lang/String;Ljava/lang/String;)V
    //   304: aload 12
    //   306: ldc 168
    //   308: ldc 170
    //   310: invokevirtual 173	org/apache/http/client/methods/HttpPost:setHeader	(Ljava/lang/String;Ljava/lang/String;)V
    //   313: aload 12
    //   315: ldc 175
    //   317: ldc 177
    //   319: invokevirtual 173	org/apache/http/client/methods/HttpPost:setHeader	(Ljava/lang/String;Ljava/lang/String;)V
    //   322: aload 12
    //   324: ldc 179
    //   326: ldc 181
    //   328: invokevirtual 173	org/apache/http/client/methods/HttpPost:setHeader	(Ljava/lang/String;Ljava/lang/String;)V
    //   331: aload 12
    //   333: new 183	org/apache/http/entity/ByteArrayEntity
    //   336: dup
    //   337: aload_0
    //   338: getfield 10	com/tencent/tauth/UploadFileToWeiyun$3:this$0	Lcom/tencent/tauth/UploadFileToWeiyun;
    //   341: invokestatic 102	com/tencent/tauth/UploadFileToWeiyun:access$1400	(Lcom/tencent/tauth/UploadFileToWeiyun;)[B
    //   344: invokespecial 186	org/apache/http/entity/ByteArrayEntity:<init>	([B)V
    //   347: invokevirtual 190	org/apache/http/client/methods/HttpPost:setEntity	(Lorg/apache/http/HttpEntity;)V
    //   350: aload_2
    //   351: aload 12
    //   353: invokeinterface 196 2 0
    //   358: invokeinterface 202 1 0
    //   363: invokeinterface 208 1 0
    //   368: istore 26
    //   370: iload 26
    //   372: istore 15
    //   374: iload 15
    //   376: sipush 200
    //   379: if_icmpne +230 -> 609
    //   382: aload_0
    //   383: getfield 10	com/tencent/tauth/UploadFileToWeiyun$3:this$0	Lcom/tencent/tauth/UploadFileToWeiyun;
    //   386: invokestatic 80	com/tencent/tauth/UploadFileToWeiyun:access$1300	(Lcom/tencent/tauth/UploadFileToWeiyun;)I
    //   389: aload_0
    //   390: getfield 10	com/tencent/tauth/UploadFileToWeiyun$3:this$0	Lcom/tencent/tauth/UploadFileToWeiyun;
    //   393: invokestatic 83	com/tencent/tauth/UploadFileToWeiyun:access$1000	(Lcom/tencent/tauth/UploadFileToWeiyun;)I
    //   396: if_icmpge +164 -> 560
    //   399: ldc2_w 209
    //   402: aload_0
    //   403: getfield 10	com/tencent/tauth/UploadFileToWeiyun$3:this$0	Lcom/tencent/tauth/UploadFileToWeiyun;
    //   406: invokestatic 80	com/tencent/tauth/UploadFileToWeiyun:access$1300	(Lcom/tencent/tauth/UploadFileToWeiyun;)I
    //   409: i2l
    //   410: lmul
    //   411: aload_0
    //   412: getfield 10	com/tencent/tauth/UploadFileToWeiyun$3:this$0	Lcom/tencent/tauth/UploadFileToWeiyun;
    //   415: invokestatic 83	com/tencent/tauth/UploadFileToWeiyun:access$1000	(Lcom/tencent/tauth/UploadFileToWeiyun;)I
    //   418: i2l
    //   419: ldiv
    //   420: l2i
    //   421: istore 22
    //   423: aload_0
    //   424: getfield 10	com/tencent/tauth/UploadFileToWeiyun$3:this$0	Lcom/tencent/tauth/UploadFileToWeiyun;
    //   427: invokestatic 109	com/tencent/tauth/UploadFileToWeiyun:access$1200	(Lcom/tencent/tauth/UploadFileToWeiyun;)Landroid/os/Handler;
    //   430: invokevirtual 115	android/os/Handler:obtainMessage	()Landroid/os/Message;
    //   433: astore 23
    //   435: aload 23
    //   437: iconst_2
    //   438: putfield 121	android/os/Message:what	I
    //   441: aload 23
    //   443: new 138	java/lang/StringBuilder
    //   446: dup
    //   447: invokespecial 139	java/lang/StringBuilder:<init>	()V
    //   450: iload 22
    //   452: invokevirtual 213	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   455: ldc 215
    //   457: invokevirtual 145	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   460: invokevirtual 157	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   463: putfield 127	android/os/Message:obj	Ljava/lang/Object;
    //   466: aload_0
    //   467: getfield 10	com/tencent/tauth/UploadFileToWeiyun$3:this$0	Lcom/tencent/tauth/UploadFileToWeiyun;
    //   470: invokestatic 109	com/tencent/tauth/UploadFileToWeiyun:access$1200	(Lcom/tencent/tauth/UploadFileToWeiyun;)Landroid/os/Handler;
    //   473: aload 23
    //   475: invokevirtual 131	android/os/Handler:sendMessage	(Landroid/os/Message;)Z
    //   478: pop
    //   479: ldc 217
    //   481: new 138	java/lang/StringBuilder
    //   484: dup
    //   485: invokespecial 139	java/lang/StringBuilder:<init>	()V
    //   488: ldc 219
    //   490: invokevirtual 145	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   493: iload 22
    //   495: invokevirtual 213	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   498: ldc 215
    //   500: invokevirtual 145	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   503: invokevirtual 157	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   506: invokestatic 225	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   509: pop
    //   510: goto -429 -> 81
    //   513: astore 13
    //   515: aload 13
    //   517: invokevirtual 226	java/lang/Exception:printStackTrace	()V
    //   520: ldc 217
    //   522: new 138	java/lang/StringBuilder
    //   525: dup
    //   526: invokespecial 139	java/lang/StringBuilder:<init>	()V
    //   529: ldc 228
    //   531: invokevirtual 145	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   534: aload 13
    //   536: invokevirtual 231	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   539: invokevirtual 145	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   542: ldc 215
    //   544: invokevirtual 145	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   547: invokevirtual 157	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   550: invokestatic 234	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   553: pop
    //   554: iconst_0
    //   555: istore 15
    //   557: goto -183 -> 374
    //   560: aload_0
    //   561: getfield 10	com/tencent/tauth/UploadFileToWeiyun$3:this$0	Lcom/tencent/tauth/UploadFileToWeiyun;
    //   564: invokestatic 109	com/tencent/tauth/UploadFileToWeiyun:access$1200	(Lcom/tencent/tauth/UploadFileToWeiyun;)Landroid/os/Handler;
    //   567: invokevirtual 115	android/os/Handler:obtainMessage	()Landroid/os/Message;
    //   570: astore 19
    //   572: aload 19
    //   574: iconst_3
    //   575: putfield 121	android/os/Message:what	I
    //   578: aload 19
    //   580: ldc 215
    //   582: putfield 127	android/os/Message:obj	Ljava/lang/Object;
    //   585: aload_0
    //   586: getfield 10	com/tencent/tauth/UploadFileToWeiyun$3:this$0	Lcom/tencent/tauth/UploadFileToWeiyun;
    //   589: invokestatic 109	com/tencent/tauth/UploadFileToWeiyun:access$1200	(Lcom/tencent/tauth/UploadFileToWeiyun;)Landroid/os/Handler;
    //   592: aload 19
    //   594: invokevirtual 131	android/os/Handler:sendMessage	(Landroid/os/Message;)Z
    //   597: pop
    //   598: ldc 217
    //   600: ldc 236
    //   602: invokestatic 225	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   605: pop
    //   606: goto -525 -> 81
    //   609: ldc 217
    //   611: new 138	java/lang/StringBuilder
    //   614: dup
    //   615: invokespecial 139	java/lang/StringBuilder:<init>	()V
    //   618: ldc 238
    //   620: invokevirtual 145	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   623: iload 15
    //   625: invokevirtual 213	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   628: ldc 215
    //   630: invokevirtual 145	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   633: invokevirtual 157	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   636: invokestatic 234	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   639: pop
    //   640: aload_0
    //   641: getfield 10	com/tencent/tauth/UploadFileToWeiyun$3:this$0	Lcom/tencent/tauth/UploadFileToWeiyun;
    //   644: invokestatic 109	com/tencent/tauth/UploadFileToWeiyun:access$1200	(Lcom/tencent/tauth/UploadFileToWeiyun;)Landroid/os/Handler;
    //   647: invokevirtual 115	android/os/Handler:obtainMessage	()Landroid/os/Message;
    //   650: astore 17
    //   652: aload 17
    //   654: bipush 254
    //   656: putfield 121	android/os/Message:what	I
    //   659: aload 17
    //   661: new 138	java/lang/StringBuilder
    //   664: dup
    //   665: invokespecial 139	java/lang/StringBuilder:<init>	()V
    //   668: ldc 240
    //   670: invokevirtual 145	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   673: iload 15
    //   675: invokevirtual 213	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   678: ldc 215
    //   680: invokevirtual 145	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   683: invokevirtual 157	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   686: putfield 127	android/os/Message:obj	Ljava/lang/Object;
    //   689: aload_0
    //   690: getfield 10	com/tencent/tauth/UploadFileToWeiyun$3:this$0	Lcom/tencent/tauth/UploadFileToWeiyun;
    //   693: invokestatic 109	com/tencent/tauth/UploadFileToWeiyun:access$1200	(Lcom/tencent/tauth/UploadFileToWeiyun;)Landroid/os/Handler;
    //   696: aload 17
    //   698: invokevirtual 131	android/os/Handler:sendMessage	(Landroid/os/Message;)Z
    //   701: pop
    //   702: return
    //
    // Exception table:
    //   from	to	target	type
    //   65	81	154	java/io/FileNotFoundException
    //   98	143	200	java/io/IOException
    //   350	370	513	java/lang/Exception
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.tauth.UploadFileToWeiyun.3
 * JD-Core Version:    0.6.0
 */