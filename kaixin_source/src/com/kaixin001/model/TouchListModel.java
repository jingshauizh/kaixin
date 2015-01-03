package com.kaixin001.model;

import com.kaixin001.item.TouchItem;

public class TouchListModel extends KXModel
{
  private static TouchListModel instance;
  private static final long serialVersionUID = 7110804609109427939L;
  public long ctime = -1L;
  public boolean loadSuceess = false;
  public TouchItem[] touchs = null;
  public String version = null;

  public static TouchListModel getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new TouchListModel();
      TouchListModel localTouchListModel = instance;
      return localTouchListModel;
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
      while ((this.touchs == null) || (this.touchs.length == 0) || (this.ctime == -1L));
      l = System.currentTimeMillis() - this.ctime;
    }
    while ((l > 86400000L) || (l < 0L));
    return false;
  }

  // ERROR //
  public boolean loadTouchData(android.content.Context paramContext)
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
    //   25: aload 5
    //   27: invokestatic 77	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   30: istore 6
    //   32: iload 6
    //   34: ifeq +9 -> 43
    //   37: aload_3
    //   38: invokevirtual 80	com/kaixin001/db/ConfigDBAdapter:close	()V
    //   41: iconst_0
    //   42: ireturn
    //   43: aload 5
    //   45: invokestatic 86	java/lang/Long:valueOf	(Ljava/lang/String;)Ljava/lang/Long;
    //   48: invokevirtual 89	java/lang/Long:longValue	()J
    //   51: lstore 7
    //   53: aload_3
    //   54: invokestatic 61	com/kaixin001/model/User:getInstance	()Lcom/kaixin001/model/User;
    //   57: invokevirtual 65	com/kaixin001/model/User:getUID	()Ljava/lang/String;
    //   60: ldc 91
    //   62: invokevirtual 71	com/kaixin001/db/ConfigDBAdapter:getConfig	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   65: astore 9
    //   67: aload 9
    //   69: invokestatic 77	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   72: ifne -35 -> 37
    //   75: aload 9
    //   77: invokestatic 96	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   80: invokevirtual 100	java/lang/Integer:intValue	()I
    //   83: istore 10
    //   85: aload_3
    //   86: invokevirtual 80	com/kaixin001/db/ConfigDBAdapter:close	()V
    //   89: new 102	com/kaixin001/db/TouchDBAdapter
    //   92: dup
    //   93: aload_1
    //   94: invokespecial 103	com/kaixin001/db/TouchDBAdapter:<init>	(Landroid/content/Context;)V
    //   97: astore 11
    //   99: aload 11
    //   101: invokevirtual 107	com/kaixin001/db/TouchDBAdapter:getAllTouchs	()Landroid/database/Cursor;
    //   104: astore_2
    //   105: aload_2
    //   106: ifnull +16 -> 122
    //   109: aload_2
    //   110: invokeinterface 112 1 0
    //   115: istore 14
    //   117: iload 14
    //   119: ifne +23 -> 142
    //   122: aload_2
    //   123: invokestatic 117	com/kaixin001/util/CloseUtil:close	(Landroid/database/Cursor;)V
    //   126: aload 11
    //   128: invokevirtual 118	com/kaixin001/db/TouchDBAdapter:close	()V
    //   131: iconst_0
    //   132: ireturn
    //   133: astore 4
    //   135: aload_3
    //   136: invokevirtual 80	com/kaixin001/db/ConfigDBAdapter:close	()V
    //   139: aload 4
    //   141: athrow
    //   142: iload 10
    //   144: anewarray 120	com/kaixin001/item/TouchItem
    //   147: astore 15
    //   149: iconst_0
    //   150: istore 16
    //   152: aload_2
    //   153: invokeinterface 123 1 0
    //   158: istore 17
    //   160: iload 17
    //   162: ifeq +19 -> 181
    //   165: iload 16
    //   167: ifne +82 -> 249
    //   170: aload_2
    //   171: invokestatic 117	com/kaixin001/util/CloseUtil:close	(Landroid/database/Cursor;)V
    //   174: aload 11
    //   176: invokevirtual 118	com/kaixin001/db/TouchDBAdapter:close	()V
    //   179: iconst_0
    //   180: ireturn
    //   181: aload 15
    //   183: iload 16
    //   185: new 120	com/kaixin001/item/TouchItem
    //   188: dup
    //   189: aload_2
    //   190: iconst_0
    //   191: invokeinterface 127 2 0
    //   196: aload_2
    //   197: iconst_1
    //   198: invokeinterface 127 2 0
    //   203: aload_2
    //   204: iconst_2
    //   205: invokeinterface 127 2 0
    //   210: invokespecial 130	com/kaixin001/item/TouchItem:<init>	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   213: aastore
    //   214: iinc 16 1
    //   217: aload_2
    //   218: invokeinterface 133 1 0
    //   223: pop
    //   224: goto -72 -> 152
    //   227: astore 13
    //   229: ldc 135
    //   231: ldc 136
    //   233: aload 13
    //   235: invokestatic 142	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   238: aload_2
    //   239: invokestatic 117	com/kaixin001/util/CloseUtil:close	(Landroid/database/Cursor;)V
    //   242: aload 11
    //   244: invokevirtual 118	com/kaixin001/db/TouchDBAdapter:close	()V
    //   247: iconst_0
    //   248: ireturn
    //   249: aload_0
    //   250: lload 7
    //   252: putfield 31	com/kaixin001/model/TouchListModel:ctime	J
    //   255: aload_0
    //   256: aload 15
    //   258: putfield 25	com/kaixin001/model/TouchListModel:touchs	[Lcom/kaixin001/item/TouchItem;
    //   261: aload_0
    //   262: iconst_1
    //   263: putfield 23	com/kaixin001/model/TouchListModel:loadSuceess	Z
    //   266: aload_2
    //   267: invokestatic 117	com/kaixin001/util/CloseUtil:close	(Landroid/database/Cursor;)V
    //   270: aload 11
    //   272: invokevirtual 118	com/kaixin001/db/TouchDBAdapter:close	()V
    //   275: iconst_1
    //   276: ireturn
    //   277: astore 12
    //   279: aload_2
    //   280: invokestatic 117	com/kaixin001/util/CloseUtil:close	(Landroid/database/Cursor;)V
    //   283: aload 11
    //   285: invokevirtual 118	com/kaixin001/db/TouchDBAdapter:close	()V
    //   288: aload 12
    //   290: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   11	32	133	finally
    //   43	85	133	finally
    //   99	105	227	java/lang/Exception
    //   109	117	227	java/lang/Exception
    //   142	149	227	java/lang/Exception
    //   152	160	227	java/lang/Exception
    //   181	214	227	java/lang/Exception
    //   217	224	227	java/lang/Exception
    //   249	266	227	java/lang/Exception
    //   99	105	277	finally
    //   109	117	277	finally
    //   142	149	277	finally
    //   152	160	277	finally
    //   181	214	277	finally
    //   217	224	277	finally
    //   229	238	277	finally
    //   249	266	277	finally
  }

  // ERROR //
  public boolean saveTouchData(android.content.Context paramContext)
  {
    // Byte code:
    //   0: new 53	com/kaixin001/db/ConfigDBAdapter
    //   3: dup
    //   4: aload_1
    //   5: invokespecial 56	com/kaixin001/db/ConfigDBAdapter:<init>	(Landroid/content/Context;)V
    //   8: astore_2
    //   9: aload_0
    //   10: getfield 31	com/kaixin001/model/TouchListModel:ctime	J
    //   13: invokestatic 148	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   16: astore 5
    //   18: aload_0
    //   19: getfield 25	com/kaixin001/model/TouchListModel:touchs	[Lcom/kaixin001/item/TouchItem;
    //   22: arraylength
    //   23: invokestatic 150	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   26: astore 6
    //   28: aload_2
    //   29: invokestatic 61	com/kaixin001/model/User:getInstance	()Lcom/kaixin001/model/User;
    //   32: invokevirtual 65	com/kaixin001/model/User:getUID	()Ljava/lang/String;
    //   35: ldc 67
    //   37: aload 5
    //   39: ldc 152
    //   41: invokevirtual 156	com/kaixin001/db/ConfigDBAdapter:addConfig	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)J
    //   44: pop2
    //   45: aload_2
    //   46: invokestatic 61	com/kaixin001/model/User:getInstance	()Lcom/kaixin001/model/User;
    //   49: invokevirtual 65	com/kaixin001/model/User:getUID	()Ljava/lang/String;
    //   52: ldc 91
    //   54: aload 6
    //   56: ldc 152
    //   58: invokevirtual 156	com/kaixin001/db/ConfigDBAdapter:addConfig	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)J
    //   61: pop2
    //   62: aload_2
    //   63: invokevirtual 80	com/kaixin001/db/ConfigDBAdapter:close	()V
    //   66: new 102	com/kaixin001/db/TouchDBAdapter
    //   69: dup
    //   70: aload_1
    //   71: invokespecial 103	com/kaixin001/db/TouchDBAdapter:<init>	(Landroid/content/Context;)V
    //   74: astore 11
    //   76: aload 11
    //   78: invokevirtual 159	com/kaixin001/db/TouchDBAdapter:deleteTouch	()I
    //   81: pop
    //   82: iconst_0
    //   83: istore 15
    //   85: aload_0
    //   86: getfield 25	com/kaixin001/model/TouchListModel:touchs	[Lcom/kaixin001/item/TouchItem;
    //   89: arraylength
    //   90: istore 16
    //   92: iload 15
    //   94: iload 16
    //   96: if_icmplt +34 -> 130
    //   99: aload 11
    //   101: invokevirtual 118	com/kaixin001/db/TouchDBAdapter:close	()V
    //   104: iconst_1
    //   105: ireturn
    //   106: astore 4
    //   108: ldc 135
    //   110: ldc 160
    //   112: aload 4
    //   114: invokestatic 142	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   117: aload_2
    //   118: invokevirtual 80	com/kaixin001/db/ConfigDBAdapter:close	()V
    //   121: iconst_0
    //   122: ireturn
    //   123: astore_3
    //   124: aload_2
    //   125: invokevirtual 80	com/kaixin001/db/ConfigDBAdapter:close	()V
    //   128: aload_3
    //   129: athrow
    //   130: aload 11
    //   132: aload_0
    //   133: getfield 25	com/kaixin001/model/TouchListModel:touchs	[Lcom/kaixin001/item/TouchItem;
    //   136: iload 15
    //   138: aaload
    //   139: getfield 163	com/kaixin001/item/TouchItem:gid	Ljava/lang/String;
    //   142: aload_0
    //   143: getfield 25	com/kaixin001/model/TouchListModel:touchs	[Lcom/kaixin001/item/TouchItem;
    //   146: iload 15
    //   148: aaload
    //   149: getfield 166	com/kaixin001/item/TouchItem:gname	Ljava/lang/String;
    //   152: aload_0
    //   153: getfield 25	com/kaixin001/model/TouchListModel:touchs	[Lcom/kaixin001/item/TouchItem;
    //   156: iload 15
    //   158: aaload
    //   159: getfield 169	com/kaixin001/item/TouchItem:pic	Ljava/lang/String;
    //   162: invokevirtual 173	com/kaixin001/db/TouchDBAdapter:insertTouch	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)J
    //   165: pop2
    //   166: iinc 15 1
    //   169: goto -84 -> 85
    //   172: astore 13
    //   174: ldc 135
    //   176: ldc 160
    //   178: aload 13
    //   180: invokestatic 142	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   183: aload 11
    //   185: invokevirtual 118	com/kaixin001/db/TouchDBAdapter:close	()V
    //   188: iconst_0
    //   189: ireturn
    //   190: astore 12
    //   192: aload 11
    //   194: invokevirtual 118	com/kaixin001/db/TouchDBAdapter:close	()V
    //   197: aload 12
    //   199: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   9	62	106	java/lang/Exception
    //   9	62	123	finally
    //   108	117	123	finally
    //   76	82	172	java/lang/Exception
    //   85	92	172	java/lang/Exception
    //   130	166	172	java/lang/Exception
    //   76	82	190	finally
    //   85	92	190	finally
    //   130	166	190	finally
    //   174	183	190	finally
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.TouchListModel
 * JD-Core Version:    0.6.0
 */