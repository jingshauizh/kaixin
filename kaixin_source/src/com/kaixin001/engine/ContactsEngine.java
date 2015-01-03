package com.kaixin001.engine;

import android.content.ContentProviderOperation;
import android.content.ContentProviderOperation.Builder;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.provider.ContactsContract.StatusUpdates;
import android.text.TextUtils;
import com.kaixin001.model.ContactsInsertInfo;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;

public class ContactsEngine
{
  private static volatile ContactsEngine instance;
  private ContentValues values = new ContentValues();

  public static void clear()
  {
    instance = null;
  }

  public static ContactsEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new ContactsEngine();
      ContactsEngine localContactsEngine = instance;
      return localContactsEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  // ERROR //
  public byte[] getBytesFromBitmap(Bitmap paramBitmap)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: new 31	java/io/ByteArrayOutputStream
    //   5: dup
    //   6: invokespecial 32	java/io/ByteArrayOutputStream:<init>	()V
    //   9: astore_3
    //   10: aload_1
    //   11: getstatic 38	android/graphics/Bitmap$CompressFormat:JPEG	Landroid/graphics/Bitmap$CompressFormat;
    //   14: bipush 100
    //   16: aload_3
    //   17: invokevirtual 44	android/graphics/Bitmap:compress	(Landroid/graphics/Bitmap$CompressFormat;ILjava/io/OutputStream;)Z
    //   20: pop
    //   21: aload_3
    //   22: invokevirtual 47	java/io/ByteArrayOutputStream:flush	()V
    //   25: aload_3
    //   26: invokevirtual 51	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   29: astore 9
    //   31: aload_3
    //   32: ifnull +7 -> 39
    //   35: aload_3
    //   36: invokevirtual 54	java/io/ByteArrayOutputStream:close	()V
    //   39: aload 9
    //   41: areturn
    //   42: astore 10
    //   44: ldc 56
    //   46: ldc 57
    //   48: aload 10
    //   50: invokestatic 63	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   53: goto -14 -> 39
    //   56: astore 11
    //   58: aload_2
    //   59: ifnull +7 -> 66
    //   62: aload_2
    //   63: invokevirtual 54	java/io/ByteArrayOutputStream:close	()V
    //   66: aconst_null
    //   67: areturn
    //   68: astore 5
    //   70: ldc 56
    //   72: ldc 57
    //   74: aload 5
    //   76: invokestatic 63	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   79: goto -13 -> 66
    //   82: astore 6
    //   84: aload_2
    //   85: ifnull +7 -> 92
    //   88: aload_2
    //   89: invokevirtual 54	java/io/ByteArrayOutputStream:close	()V
    //   92: aload 6
    //   94: athrow
    //   95: astore 7
    //   97: ldc 56
    //   99: ldc 57
    //   101: aload 7
    //   103: invokestatic 63	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   106: goto -14 -> 92
    //   109: astore 6
    //   111: aload_3
    //   112: astore_2
    //   113: goto -29 -> 84
    //   116: astore 4
    //   118: aload_3
    //   119: astore_2
    //   120: goto -62 -> 58
    //
    // Exception table:
    //   from	to	target	type
    //   35	39	42	java/io/IOException
    //   2	10	56	java/lang/Exception
    //   62	66	68	java/io/IOException
    //   2	10	82	finally
    //   88	92	95	java/io/IOException
    //   10	31	109	finally
    //   10	31	116	java/lang/Exception
  }

  public int getNumsByCid(ContentResolver paramContentResolver, long paramLong)
  {
    Cursor localCursor = null;
    try
    {
      Uri localUri = ContactsContract.RawContacts.CONTENT_URI;
      String[] arrayOfString1 = { "_count" };
      String[] arrayOfString2 = new String[1];
      arrayOfString2[0] = String.valueOf(paramLong);
      localCursor = paramContentResolver.query(localUri, arrayOfString1, "contact_id=?", arrayOfString2, null);
      i = 0;
      if (localCursor != null)
      {
        boolean bool = localCursor.moveToFirst();
        i = 0;
        if (bool)
        {
          int j = localCursor.getInt(0);
          i = j;
        }
      }
      return i;
    }
    catch (Exception localException)
    {
      KXLog.e("ContactsEngine", "getNumsByCid", localException);
      int i = 0;
      return 0;
    }
    finally
    {
      if (localCursor != null)
        localCursor.close();
    }
    throw localObject;
  }

  public int getPhotoBytesByRid(ContentResolver paramContentResolver, int paramInt)
  {
    Cursor localCursor = null;
    try
    {
      Uri localUri = ContactsContract.Data.CONTENT_URI;
      String[] arrayOfString1 = { "data15" };
      String[] arrayOfString2 = new String[2];
      arrayOfString2[0] = String.valueOf(paramInt);
      arrayOfString2[1] = "vnd.android.cursor.item/photo";
      localCursor = paramContentResolver.query(localUri, arrayOfString1, "raw_contact_id = ? and mimetype = ? ", arrayOfString2, null);
      i = 0;
      if (localCursor != null)
      {
        if (!localCursor.moveToFirst())
          break label101;
        byte[] arrayOfByte = localCursor.getBlob(0);
        i = 0;
        if (arrayOfByte == null);
      }
      label101: for (i = 1; ; i = -1)
        return i;
    }
    catch (Exception localException)
    {
      KXLog.e("ContactsEngine", "getPhotoBytesByRid", localException);
      int i = 0;
      return 0;
    }
    finally
    {
      if (localCursor != null)
        localCursor.close();
    }
    throw localObject;
  }

  public ContactsInsertInfo insertAContacts(ContentResolver paramContentResolver, String paramString1, Bitmap paramBitmap, String paramString2, String paramString3)
  {
    Cursor localCursor = null;
    long l1 = 0L;
    ContactsInsertInfo localContactsInsertInfo = new ContactsInsertInfo();
    try
    {
      ArrayList localArrayList = new ArrayList();
      ContentProviderOperation.Builder localBuilder1 = ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI);
      localBuilder1.withValue("aggregation_mode", Integer.valueOf(3));
      localArrayList.add(localBuilder1.build());
      ContentProviderOperation.Builder localBuilder2 = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI);
      localBuilder2.withValueBackReference("raw_contact_id", 0);
      localBuilder2.withValue("mimetype", "vnd.android.cursor.item/name");
      localBuilder2.withValue("data1", paramString1);
      localArrayList.add(localBuilder2.build());
      ContentProviderOperation.Builder localBuilder3 = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI);
      localBuilder3.withValueBackReference("raw_contact_id", 0);
      localBuilder3.withValue("mimetype", "vnd.android.cursor.item/photo");
      localBuilder3.withValue("data15", getBytesFromBitmap(paramBitmap));
      localArrayList.add(localBuilder3.build());
      ContentProviderResult[] arrayOfContentProviderResult = paramContentResolver.applyBatch("com.android.contacts", localArrayList);
      long l2 = Long.valueOf(arrayOfContentProviderResult[0].uri.getLastPathSegment()).longValue();
      localContactsInsertInfo.setStatusId(statusUpdates(paramContentResolver, l2, paramString2, paramString3));
      Uri localUri = ContactsContract.RawContacts.CONTENT_URI;
      String[] arrayOfString1 = { "contact_id" };
      String[] arrayOfString2 = new String[1];
      arrayOfString2[0] = l2;
      localCursor = paramContentResolver.query(localUri, arrayOfString1, "_id = ?", arrayOfString2, null);
      if ((localCursor != null) && (localCursor.moveToFirst()))
        l1 = localCursor.getLong(0);
      if (arrayOfContentProviderResult.length > 0)
        localContactsInsertInfo.setOriginalFlag(l1);
      while (true)
      {
        return localContactsInsertInfo;
        localContactsInsertInfo.setOriginalFlag(0L);
      }
    }
    catch (Exception localException)
    {
      KXLog.e("ContactsEngine", "insertAContents", localException);
      return localContactsInsertInfo;
    }
    finally
    {
      if (localCursor != null)
        localCursor.close();
    }
    throw localObject;
  }

  public boolean selectAContentsByName(ContentResolver paramContentResolver, String paramString)
  {
    Cursor localCursor = null;
    while (true)
    {
      try
      {
        String[] arrayOfString = { paramString };
        localCursor = paramContentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, "display_name = ?", arrayOfString, null);
        if (localCursor != null)
        {
          boolean bool = localCursor.moveToNext();
          return bool;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("ContactsEngine", "selectAContentsByName", localException);
        return false;
      }
      finally
      {
        if (localCursor == null)
          continue;
        localCursor.close();
      }
      if (localCursor == null)
        continue;
      localCursor.close();
    }
  }

  public ArrayList<Long> selectContactsIds(ContentResolver paramContentResolver)
  {
    ArrayList localArrayList = new ArrayList();
    Cursor localCursor = null;
    try
    {
      localCursor = paramContentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
      if (localCursor != null);
      while (true)
      {
        boolean bool = localCursor.moveToNext();
        if (!bool)
          return localArrayList;
        localArrayList.add(Long.valueOf(localCursor.getLong(localCursor.getColumnIndex("_id"))));
      }
    }
    catch (Exception localException)
    {
      KXLog.e("ContactsEngine", "selectContactsIds", localException);
      return localArrayList;
    }
    finally
    {
      if (localCursor != null)
        localCursor.close();
    }
    throw localObject;
  }

  // ERROR //
  public ArrayList<com.kaixin001.item.ContactsItem> selectContentsNames(ContentResolver paramContentResolver)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: iconst_2
    //   3: anewarray 73	java/lang/String
    //   6: dup
    //   7: iconst_0
    //   8: ldc 237
    //   10: aastore
    //   11: dup
    //   12: iconst_1
    //   13: ldc 248
    //   15: aastore
    //   16: astore_3
    //   17: ldc 250
    //   19: astore 4
    //   21: new 125	java/util/ArrayList
    //   24: dup
    //   25: invokespecial 126	java/util/ArrayList:<init>	()V
    //   28: astore 5
    //   30: aload_1
    //   31: getstatic 227	android/provider/ContactsContract$Contacts:CONTENT_URI	Landroid/net/Uri;
    //   34: aload_3
    //   35: aconst_null
    //   36: aconst_null
    //   37: ldc 252
    //   39: invokevirtual 87	android/content/ContentResolver:query	(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   42: astore_2
    //   43: aload_2
    //   44: ifnull +42 -> 86
    //   47: aload_2
    //   48: ldc 237
    //   50: invokeinterface 241 2 0
    //   55: istore 8
    //   57: aload_2
    //   58: ldc 248
    //   60: invokeinterface 241 2 0
    //   65: istore 9
    //   67: aconst_null
    //   68: astore 10
    //   70: aload_2
    //   71: invokeinterface 232 1 0
    //   76: istore 13
    //   78: iload 13
    //   80: ifne +19 -> 99
    //   83: aload 10
    //   85: pop
    //   86: aload_2
    //   87: ifnull +9 -> 96
    //   90: aload_2
    //   91: invokeinterface 98 1 0
    //   96: aload 5
    //   98: areturn
    //   99: aload_2
    //   100: iload 8
    //   102: invokeinterface 255 2 0
    //   107: astore 14
    //   109: aload_2
    //   110: iload 9
    //   112: invokeinterface 255 2 0
    //   117: astore 15
    //   119: aload 15
    //   121: aload 4
    //   123: invokevirtual 258	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   126: ifeq +77 -> 203
    //   129: iconst_m1
    //   130: aload 5
    //   132: invokevirtual 262	java/util/ArrayList:size	()I
    //   135: iadd
    //   136: istore 18
    //   138: iload 18
    //   140: iflt -70 -> 70
    //   143: aload 4
    //   145: aload 5
    //   147: iload 18
    //   149: invokevirtual 266	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   152: checkcast 268	com/kaixin001/item/ContactsItem
    //   155: invokevirtual 271	com/kaixin001/item/ContactsItem:getName	()Ljava/lang/String;
    //   158: invokevirtual 258	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   161: ifeq -91 -> 70
    //   164: aload 5
    //   166: iload 18
    //   168: invokevirtual 274	java/util/ArrayList:remove	(I)Ljava/lang/Object;
    //   171: pop
    //   172: goto -102 -> 70
    //   175: astore 6
    //   177: aload 10
    //   179: pop
    //   180: ldc 56
    //   182: ldc_w 275
    //   185: aload 6
    //   187: invokestatic 63	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   190: aload_2
    //   191: ifnull -95 -> 96
    //   194: aload_2
    //   195: invokeinterface 98 1 0
    //   200: aload 5
    //   202: areturn
    //   203: new 268	com/kaixin001/item/ContactsItem
    //   206: dup
    //   207: invokespecial 276	com/kaixin001/item/ContactsItem:<init>	()V
    //   210: astore 16
    //   212: aload 16
    //   214: aload 14
    //   216: invokevirtual 279	com/kaixin001/item/ContactsItem:setContactsId	(Ljava/lang/String;)V
    //   219: aload 16
    //   221: aload 15
    //   223: invokevirtual 282	com/kaixin001/item/ContactsItem:setName	(Ljava/lang/String;)V
    //   226: aload 5
    //   228: aload 16
    //   230: invokevirtual 153	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   233: pop
    //   234: aload 15
    //   236: astore 4
    //   238: aload 16
    //   240: astore 10
    //   242: goto -172 -> 70
    //   245: astore 7
    //   247: aload_2
    //   248: ifnull +9 -> 257
    //   251: aload_2
    //   252: invokeinterface 98 1 0
    //   257: aload 7
    //   259: athrow
    //   260: astore 7
    //   262: aload 10
    //   264: pop
    //   265: goto -18 -> 247
    //   268: astore 6
    //   270: goto -90 -> 180
    //
    // Exception table:
    //   from	to	target	type
    //   70	78	175	java/lang/Exception
    //   99	138	175	java/lang/Exception
    //   143	172	175	java/lang/Exception
    //   203	212	175	java/lang/Exception
    //   30	43	245	finally
    //   47	67	245	finally
    //   180	190	245	finally
    //   212	234	245	finally
    //   70	78	260	finally
    //   99	138	260	finally
    //   143	172	260	finally
    //   203	212	260	finally
    //   30	43	268	java/lang/Exception
    //   47	67	268	java/lang/Exception
    //   212	234	268	java/lang/Exception
  }

  public long statusUpdates(ContentResolver paramContentResolver, long paramLong, String paramString1, String paramString2)
  {
    Cursor localCursor = null;
    while (true)
    {
      try
      {
        Uri localUri = ContactsContract.Data.CONTENT_URI;
        String[] arrayOfString1 = { "_id" };
        String[] arrayOfString2 = new String[2];
        arrayOfString2[0] = "vnd.android.cursor.item/name";
        arrayOfString2[1] = String.valueOf(paramLong);
        localCursor = paramContentResolver.query(localUri, arrayOfString1, "mimetype = ? and raw_contact_id = ? ", arrayOfString2, null);
        if ((localCursor != null) && (localCursor.moveToNext()))
        {
          int i = localCursor.getInt(localCursor.getColumnIndex("_id"));
          this.values.clear();
          this.values.put("presence_data_id", i);
          this.values.put("status", paramString1);
          if (TextUtils.isEmpty(paramString2))
            continue;
          long l2 = Long.valueOf(paramString2).longValue();
          this.values.put("status_ts", Long.valueOf(1000L * l2));
          this.values.put("status_res_package", "com.kaixin001.activity");
          paramContentResolver.insert(ContactsContract.StatusUpdates.CONTENT_URI, this.values);
          long l1 = i;
          return l1;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("ContactsEngine", "statusUpdates", localException);
        return -1L;
      }
      finally
      {
        if (localCursor == null)
          continue;
        localCursor.close();
      }
      if (localCursor == null)
        continue;
      localCursor.close();
    }
  }

  // ERROR //
  public ContactsInsertInfo updateAContacts(ContentResolver paramContentResolver, Bitmap paramBitmap, long paramLong, String paramString1, String paramString2)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 7
    //   3: new 122	com/kaixin001/model/ContactsInsertInfo
    //   6: dup
    //   7: invokespecial 123	com/kaixin001/model/ContactsInsertInfo:<init>	()V
    //   10: astore 8
    //   12: getstatic 71	android/provider/ContactsContract$RawContacts:CONTENT_URI	Landroid/net/Uri;
    //   15: astore 12
    //   17: iconst_1
    //   18: anewarray 73	java/lang/String
    //   21: astore 13
    //   23: aload 13
    //   25: iconst_0
    //   26: lload_3
    //   27: invokestatic 79	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   30: aastore
    //   31: aload_1
    //   32: aload 12
    //   34: aconst_null
    //   35: ldc 81
    //   37: aload 13
    //   39: aconst_null
    //   40: invokevirtual 87	android/content/ContentResolver:query	(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   43: astore 14
    //   45: aload 14
    //   47: astore 7
    //   49: aload 7
    //   51: ifnonnull +18 -> 69
    //   54: aload 7
    //   56: ifnull +10 -> 66
    //   59: aload 7
    //   61: invokeinterface 98 1 0
    //   66: aload 8
    //   68: areturn
    //   69: aload 7
    //   71: invokeinterface 326 1 0
    //   76: istore 15
    //   78: iload 15
    //   80: ifne +18 -> 98
    //   83: aload 7
    //   85: ifnull +10 -> 95
    //   88: aload 7
    //   90: invokeinterface 98 1 0
    //   95: aload 8
    //   97: areturn
    //   98: aload 7
    //   100: invokeinterface 93 1 0
    //   105: pop
    //   106: aload 7
    //   108: ldc 237
    //   110: invokeinterface 241 2 0
    //   115: istore 17
    //   117: iload 17
    //   119: ifge +18 -> 137
    //   122: aload 7
    //   124: ifnull +10 -> 134
    //   127: aload 7
    //   129: invokeinterface 98 1 0
    //   134: aload 8
    //   136: areturn
    //   137: aload 7
    //   139: iload 17
    //   141: invokeinterface 97 2 0
    //   146: istore 18
    //   148: new 125	java/util/ArrayList
    //   151: dup
    //   152: invokespecial 126	java/util/ArrayList:<init>	()V
    //   155: astore 19
    //   157: aload_0
    //   158: aload_1
    //   159: iload 18
    //   161: invokevirtual 328	com/kaixin001/engine/ContactsEngine:getPhotoBytesByRid	(Landroid/content/ContentResolver;I)I
    //   164: iconst_m1
    //   165: if_icmpne +133 -> 298
    //   168: getstatic 104	android/provider/ContactsContract$Data:CONTENT_URI	Landroid/net/Uri;
    //   171: invokestatic 132	android/content/ContentProviderOperation:newInsert	(Landroid/net/Uri;)Landroid/content/ContentProviderOperation$Builder;
    //   174: astore 31
    //   176: aload 31
    //   178: ldc 155
    //   180: iload 18
    //   182: invokestatic 139	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   185: invokevirtual 145	android/content/ContentProviderOperation$Builder:withValue	(Ljava/lang/String;Ljava/lang/Object;)Landroid/content/ContentProviderOperation$Builder;
    //   188: pop
    //   189: aload 31
    //   191: ldc 161
    //   193: ldc 111
    //   195: invokevirtual 145	android/content/ContentProviderOperation$Builder:withValue	(Ljava/lang/String;Ljava/lang/Object;)Landroid/content/ContentProviderOperation$Builder;
    //   198: pop
    //   199: aload 31
    //   201: ldc 106
    //   203: aload_0
    //   204: aload_2
    //   205: invokevirtual 167	com/kaixin001/engine/ContactsEngine:getBytesFromBitmap	(Landroid/graphics/Bitmap;)[B
    //   208: invokevirtual 145	android/content/ContentProviderOperation$Builder:withValue	(Ljava/lang/String;Ljava/lang/Object;)Landroid/content/ContentProviderOperation$Builder;
    //   211: pop
    //   212: aload 19
    //   214: aload 31
    //   216: invokevirtual 149	android/content/ContentProviderOperation$Builder:build	()Landroid/content/ContentProviderOperation;
    //   219: invokevirtual 153	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   222: pop
    //   223: iconst_0
    //   224: anewarray 175	android/content/ContentProviderResult
    //   227: pop
    //   228: aload_1
    //   229: ldc 169
    //   231: aload 19
    //   233: invokevirtual 173	android/content/ContentResolver:applyBatch	(Ljava/lang/String;Ljava/util/ArrayList;)[Landroid/content/ContentProviderResult;
    //   236: pop
    //   237: aload_0
    //   238: aload_1
    //   239: lload_3
    //   240: aload 5
    //   242: aload 6
    //   244: invokevirtual 197	com/kaixin001/engine/ContactsEngine:statusUpdates	(Landroid/content/ContentResolver;JLjava/lang/String;Ljava/lang/String;)J
    //   247: lstore 27
    //   249: aload 8
    //   251: lload 27
    //   253: invokevirtual 201	com/kaixin001/model/ContactsInsertInfo:setStatusId	(J)V
    //   256: lload 27
    //   258: ldc2_w 320
    //   261: lcmp
    //   262: istore 29
    //   264: iconst_0
    //   265: istore 30
    //   267: iload 29
    //   269: ifeq +6 -> 275
    //   272: iconst_1
    //   273: istore 30
    //   275: aload 8
    //   277: iload 30
    //   279: i2l
    //   280: invokevirtual 220	com/kaixin001/model/ContactsInsertInfo:setOriginalFlag	(J)V
    //   283: aload 7
    //   285: ifnull +10 -> 295
    //   288: aload 7
    //   290: invokeinterface 98 1 0
    //   295: aload 8
    //   297: areturn
    //   298: getstatic 104	android/provider/ContactsContract$Data:CONTENT_URI	Landroid/net/Uri;
    //   301: invokestatic 331	android/content/ContentProviderOperation:newUpdate	(Landroid/net/Uri;)Landroid/content/ContentProviderOperation$Builder;
    //   304: astore 20
    //   306: iconst_2
    //   307: anewarray 73	java/lang/String
    //   310: astore 21
    //   312: aload 21
    //   314: iconst_0
    //   315: ldc 111
    //   317: aastore
    //   318: aload 21
    //   320: iconst_1
    //   321: iload 18
    //   323: invokestatic 109	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   326: aastore
    //   327: aload 20
    //   329: ldc_w 284
    //   332: aload 21
    //   334: invokevirtual 335	android/content/ContentProviderOperation$Builder:withSelection	(Ljava/lang/String;[Ljava/lang/String;)Landroid/content/ContentProviderOperation$Builder;
    //   337: pop
    //   338: aload 20
    //   340: ldc 106
    //   342: aload_0
    //   343: aload_2
    //   344: invokevirtual 167	com/kaixin001/engine/ContactsEngine:getBytesFromBitmap	(Landroid/graphics/Bitmap;)[B
    //   347: invokevirtual 145	android/content/ContentProviderOperation$Builder:withValue	(Ljava/lang/String;Ljava/lang/Object;)Landroid/content/ContentProviderOperation$Builder;
    //   350: pop
    //   351: aload 19
    //   353: aload 20
    //   355: invokevirtual 149	android/content/ContentProviderOperation$Builder:build	()Landroid/content/ContentProviderOperation;
    //   358: invokevirtual 153	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   361: pop
    //   362: goto -139 -> 223
    //   365: astore 10
    //   367: aload 8
    //   369: astore 11
    //   371: aload 10
    //   373: invokevirtual 338	java/lang/Exception:printStackTrace	()V
    //   376: aload 7
    //   378: ifnull +10 -> 388
    //   381: aload 7
    //   383: invokeinterface 98 1 0
    //   388: aload 11
    //   390: areturn
    //   391: astore 9
    //   393: aload 7
    //   395: ifnull +10 -> 405
    //   398: aload 7
    //   400: invokeinterface 98 1 0
    //   405: aload 9
    //   407: athrow
    //   408: astore 9
    //   410: goto -17 -> 393
    //   413: astore 10
    //   415: aconst_null
    //   416: astore 11
    //   418: aconst_null
    //   419: astore 7
    //   421: goto -50 -> 371
    //
    // Exception table:
    //   from	to	target	type
    //   12	45	365	java/lang/Exception
    //   69	78	365	java/lang/Exception
    //   98	117	365	java/lang/Exception
    //   137	223	365	java/lang/Exception
    //   223	256	365	java/lang/Exception
    //   275	283	365	java/lang/Exception
    //   298	362	365	java/lang/Exception
    //   3	12	391	finally
    //   371	376	391	finally
    //   12	45	408	finally
    //   69	78	408	finally
    //   98	117	408	finally
    //   137	223	408	finally
    //   223	256	408	finally
    //   275	283	408	finally
    //   298	362	408	finally
    //   3	12	413	java/lang/Exception
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.ContactsEngine
 * JD-Core Version:    0.6.0
 */