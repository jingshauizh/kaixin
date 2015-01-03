package com.kaixin001.model;

import com.kaixin001.item.GiftItem;

public class GiftListModel extends KXModel
{
  private static GiftListModel instance;
  private static final long serialVersionUID = 7110804609109427939L;
  public long ctime = -1L;
  public GiftItem[] gifts = null;
  public boolean loadSuceess = false;
  public String version = null;

  public static GiftListModel getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new GiftListModel();
      GiftListModel localGiftListModel = instance;
      return localGiftListModel;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public void clear()
  {
  }

  public boolean isNeedGetLatest()
  {
    if (!this.loadSuceess);
    long l;
    do
    {
      do
        return true;
      while ((this.gifts == null) || (this.gifts.length == 0) || (this.version == null) || (this.ctime == -1L));
      l = System.currentTimeMillis() - this.ctime;
    }
    while ((l > 86400000L) || (l < 0L));
    return false;
  }

  // ERROR //
  public boolean loadGiftData(android.content.Context paramContext)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: new 53	com/kaixin001/db/ConfigDBAdapter
    //   5: dup
    //   6: aload_1
    //   7: invokespecial 56	com/kaixin001/db/ConfigDBAdapter:<init>	(Landroid/content/Context;)V
    //   10: astore_3
    //   11: aload_3
    //   12: invokestatic 61	com/kaixin001/model/User:getInstance	()Lcom/kaixin001/model/User;
    //   15: invokevirtual 65	com/kaixin001/model/User:getUID	()Ljava/lang/String;
    //   18: ldc 67
    //   20: invokevirtual 71	com/kaixin001/db/ConfigDBAdapter:getConfig	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   23: astore 5
    //   25: aload_3
    //   26: invokestatic 61	com/kaixin001/model/User:getInstance	()Lcom/kaixin001/model/User;
    //   29: invokevirtual 65	com/kaixin001/model/User:getUID	()Ljava/lang/String;
    //   32: ldc 73
    //   34: invokevirtual 71	com/kaixin001/db/ConfigDBAdapter:getConfig	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   37: astore 6
    //   39: aload 6
    //   41: invokestatic 79	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   44: istore 7
    //   46: iload 7
    //   48: ifeq +9 -> 57
    //   51: aload_3
    //   52: invokevirtual 82	com/kaixin001/db/ConfigDBAdapter:close	()V
    //   55: iconst_0
    //   56: ireturn
    //   57: aload 6
    //   59: invokestatic 88	java/lang/Long:valueOf	(Ljava/lang/String;)Ljava/lang/Long;
    //   62: invokevirtual 91	java/lang/Long:longValue	()J
    //   65: lstore 8
    //   67: aload_3
    //   68: invokestatic 61	com/kaixin001/model/User:getInstance	()Lcom/kaixin001/model/User;
    //   71: invokevirtual 65	com/kaixin001/model/User:getUID	()Ljava/lang/String;
    //   74: ldc 93
    //   76: invokevirtual 71	com/kaixin001/db/ConfigDBAdapter:getConfig	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   79: astore 10
    //   81: aload 10
    //   83: invokestatic 79	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   86: ifne -35 -> 51
    //   89: aload 10
    //   91: invokestatic 98	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   94: invokevirtual 102	java/lang/Integer:intValue	()I
    //   97: istore 11
    //   99: aload_3
    //   100: invokevirtual 82	com/kaixin001/db/ConfigDBAdapter:close	()V
    //   103: new 104	com/kaixin001/db/GiftDBAdapter
    //   106: dup
    //   107: aload_1
    //   108: invokespecial 105	com/kaixin001/db/GiftDBAdapter:<init>	(Landroid/content/Context;)V
    //   111: astore 12
    //   113: aload 12
    //   115: invokevirtual 109	com/kaixin001/db/GiftDBAdapter:getAllGifts	()Landroid/database/Cursor;
    //   118: astore_2
    //   119: aload_2
    //   120: ifnull +16 -> 136
    //   123: aload_2
    //   124: invokeinterface 114 1 0
    //   129: istore 15
    //   131: iload 15
    //   133: ifne +23 -> 156
    //   136: aload_2
    //   137: invokestatic 119	com/kaixin001/util/CloseUtil:close	(Landroid/database/Cursor;)V
    //   140: aload 12
    //   142: invokevirtual 120	com/kaixin001/db/GiftDBAdapter:close	()V
    //   145: iconst_0
    //   146: ireturn
    //   147: astore 4
    //   149: aload_3
    //   150: invokevirtual 82	com/kaixin001/db/ConfigDBAdapter:close	()V
    //   153: aload 4
    //   155: athrow
    //   156: iload 11
    //   158: anewarray 122	com/kaixin001/item/GiftItem
    //   161: astore 16
    //   163: iconst_0
    //   164: istore 17
    //   166: aload_2
    //   167: invokeinterface 125 1 0
    //   172: istore 18
    //   174: iload 18
    //   176: ifeq +19 -> 195
    //   179: iload 17
    //   181: ifne +109 -> 290
    //   184: aload_2
    //   185: invokestatic 119	com/kaixin001/util/CloseUtil:close	(Landroid/database/Cursor;)V
    //   188: aload 12
    //   190: invokevirtual 120	com/kaixin001/db/GiftDBAdapter:close	()V
    //   193: iconst_0
    //   194: ireturn
    //   195: aload_2
    //   196: iconst_0
    //   197: invokeinterface 129 2 0
    //   202: astore 19
    //   204: aload_2
    //   205: iconst_1
    //   206: invokeinterface 129 2 0
    //   211: astore 20
    //   213: aload_2
    //   214: iconst_2
    //   215: invokeinterface 129 2 0
    //   220: astore 21
    //   222: aload_2
    //   223: iconst_3
    //   224: invokeinterface 129 2 0
    //   229: astore 22
    //   231: new 122	com/kaixin001/item/GiftItem
    //   234: dup
    //   235: aload 19
    //   237: aload 20
    //   239: aload 21
    //   241: aload 22
    //   243: invokespecial 132	com/kaixin001/item/GiftItem:<init>	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   246: astore 23
    //   248: aload 16
    //   250: iload 17
    //   252: aload 23
    //   254: aastore
    //   255: iinc 17 1
    //   258: aload_2
    //   259: invokeinterface 135 1 0
    //   264: pop
    //   265: goto -99 -> 166
    //   268: astore 14
    //   270: ldc 137
    //   272: ldc 138
    //   274: aload 14
    //   276: invokestatic 144	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   279: aload_2
    //   280: invokestatic 119	com/kaixin001/util/CloseUtil:close	(Landroid/database/Cursor;)V
    //   283: aload 12
    //   285: invokevirtual 120	com/kaixin001/db/GiftDBAdapter:close	()V
    //   288: iconst_0
    //   289: ireturn
    //   290: aload_0
    //   291: aload 5
    //   293: putfield 27	com/kaixin001/model/GiftListModel:version	Ljava/lang/String;
    //   296: aload_0
    //   297: lload 8
    //   299: putfield 31	com/kaixin001/model/GiftListModel:ctime	J
    //   302: aload_0
    //   303: aload 16
    //   305: putfield 25	com/kaixin001/model/GiftListModel:gifts	[Lcom/kaixin001/item/GiftItem;
    //   308: aload_0
    //   309: iconst_1
    //   310: putfield 23	com/kaixin001/model/GiftListModel:loadSuceess	Z
    //   313: aload_2
    //   314: invokestatic 119	com/kaixin001/util/CloseUtil:close	(Landroid/database/Cursor;)V
    //   317: aload 12
    //   319: invokevirtual 120	com/kaixin001/db/GiftDBAdapter:close	()V
    //   322: iconst_1
    //   323: ireturn
    //   324: astore 13
    //   326: aload_2
    //   327: invokestatic 119	com/kaixin001/util/CloseUtil:close	(Landroid/database/Cursor;)V
    //   330: aload 12
    //   332: invokevirtual 120	com/kaixin001/db/GiftDBAdapter:close	()V
    //   335: aload 13
    //   337: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   11	46	147	finally
    //   57	99	147	finally
    //   113	119	268	java/lang/Exception
    //   123	131	268	java/lang/Exception
    //   156	163	268	java/lang/Exception
    //   166	174	268	java/lang/Exception
    //   195	255	268	java/lang/Exception
    //   258	265	268	java/lang/Exception
    //   290	313	268	java/lang/Exception
    //   113	119	324	finally
    //   123	131	324	finally
    //   156	163	324	finally
    //   166	174	324	finally
    //   195	255	324	finally
    //   258	265	324	finally
    //   270	279	324	finally
    //   290	313	324	finally
  }

  // ERROR //
  public boolean saveGiftData(android.content.Context paramContext)
  {
    // Byte code:
    //   0: new 53	com/kaixin001/db/ConfigDBAdapter
    //   3: dup
    //   4: aload_1
    //   5: invokespecial 56	com/kaixin001/db/ConfigDBAdapter:<init>	(Landroid/content/Context;)V
    //   8: astore_2
    //   9: aload_0
    //   10: getfield 31	com/kaixin001/model/GiftListModel:ctime	J
    //   13: invokestatic 150	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   16: astore 5
    //   18: aload_0
    //   19: getfield 25	com/kaixin001/model/GiftListModel:gifts	[Lcom/kaixin001/item/GiftItem;
    //   22: arraylength
    //   23: invokestatic 152	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   26: astore 6
    //   28: aload_2
    //   29: invokestatic 61	com/kaixin001/model/User:getInstance	()Lcom/kaixin001/model/User;
    //   32: invokevirtual 65	com/kaixin001/model/User:getUID	()Ljava/lang/String;
    //   35: ldc 67
    //   37: aload_0
    //   38: getfield 27	com/kaixin001/model/GiftListModel:version	Ljava/lang/String;
    //   41: ldc 154
    //   43: invokevirtual 158	com/kaixin001/db/ConfigDBAdapter:addConfig	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)J
    //   46: pop2
    //   47: aload_2
    //   48: invokestatic 61	com/kaixin001/model/User:getInstance	()Lcom/kaixin001/model/User;
    //   51: invokevirtual 65	com/kaixin001/model/User:getUID	()Ljava/lang/String;
    //   54: ldc 73
    //   56: aload 5
    //   58: ldc 154
    //   60: invokevirtual 158	com/kaixin001/db/ConfigDBAdapter:addConfig	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)J
    //   63: pop2
    //   64: aload_2
    //   65: invokestatic 61	com/kaixin001/model/User:getInstance	()Lcom/kaixin001/model/User;
    //   68: invokevirtual 65	com/kaixin001/model/User:getUID	()Ljava/lang/String;
    //   71: ldc 93
    //   73: aload 6
    //   75: ldc 154
    //   77: invokevirtual 158	com/kaixin001/db/ConfigDBAdapter:addConfig	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)J
    //   80: pop2
    //   81: aload_2
    //   82: invokevirtual 82	com/kaixin001/db/ConfigDBAdapter:close	()V
    //   85: new 104	com/kaixin001/db/GiftDBAdapter
    //   88: dup
    //   89: aload_1
    //   90: invokespecial 105	com/kaixin001/db/GiftDBAdapter:<init>	(Landroid/content/Context;)V
    //   93: astore 13
    //   95: aload 13
    //   97: invokevirtual 161	com/kaixin001/db/GiftDBAdapter:deleteGift	()I
    //   100: pop
    //   101: iconst_0
    //   102: istore 17
    //   104: aload_0
    //   105: getfield 25	com/kaixin001/model/GiftListModel:gifts	[Lcom/kaixin001/item/GiftItem;
    //   108: arraylength
    //   109: istore 18
    //   111: iload 17
    //   113: iload 18
    //   115: if_icmplt +34 -> 149
    //   118: aload 13
    //   120: invokevirtual 120	com/kaixin001/db/GiftDBAdapter:close	()V
    //   123: iconst_1
    //   124: ireturn
    //   125: astore 4
    //   127: ldc 137
    //   129: ldc 162
    //   131: aload 4
    //   133: invokestatic 144	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   136: aload_2
    //   137: invokevirtual 82	com/kaixin001/db/ConfigDBAdapter:close	()V
    //   140: iconst_0
    //   141: ireturn
    //   142: astore_3
    //   143: aload_2
    //   144: invokevirtual 82	com/kaixin001/db/ConfigDBAdapter:close	()V
    //   147: aload_3
    //   148: athrow
    //   149: aload 13
    //   151: aload_0
    //   152: getfield 25	com/kaixin001/model/GiftListModel:gifts	[Lcom/kaixin001/item/GiftItem;
    //   155: iload 17
    //   157: aaload
    //   158: getfield 165	com/kaixin001/item/GiftItem:gid	Ljava/lang/String;
    //   161: aload_0
    //   162: getfield 25	com/kaixin001/model/GiftListModel:gifts	[Lcom/kaixin001/item/GiftItem;
    //   165: iload 17
    //   167: aaload
    //   168: getfield 168	com/kaixin001/item/GiftItem:gname	Ljava/lang/String;
    //   171: aload_0
    //   172: getfield 25	com/kaixin001/model/GiftListModel:gifts	[Lcom/kaixin001/item/GiftItem;
    //   175: iload 17
    //   177: aaload
    //   178: getfield 171	com/kaixin001/item/GiftItem:pic	Ljava/lang/String;
    //   181: aload_0
    //   182: getfield 25	com/kaixin001/model/GiftListModel:gifts	[Lcom/kaixin001/item/GiftItem;
    //   185: iload 17
    //   187: aaload
    //   188: getfield 174	com/kaixin001/item/GiftItem:defaultPs	Ljava/lang/String;
    //   191: invokevirtual 177	com/kaixin001/db/GiftDBAdapter:insertGift	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)J
    //   194: pop2
    //   195: iinc 17 1
    //   198: goto -94 -> 104
    //   201: astore 15
    //   203: ldc 137
    //   205: ldc 162
    //   207: aload 15
    //   209: invokestatic 144	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   212: aload 13
    //   214: invokevirtual 120	com/kaixin001/db/GiftDBAdapter:close	()V
    //   217: iconst_0
    //   218: ireturn
    //   219: astore 14
    //   221: aload 13
    //   223: invokevirtual 120	com/kaixin001/db/GiftDBAdapter:close	()V
    //   226: aload 14
    //   228: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   9	81	125	java/lang/Exception
    //   9	81	142	finally
    //   127	136	142	finally
    //   95	101	201	java/lang/Exception
    //   104	111	201	java/lang/Exception
    //   149	195	201	java/lang/Exception
    //   95	101	219	finally
    //   104	111	219	finally
    //   149	195	219	finally
    //   203	212	219	finally
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.GiftListModel
 * JD-Core Version:    0.6.0
 */