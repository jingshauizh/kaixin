package com.kaixin001.util;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredPostal;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.text.TextUtils;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ContactUtil
{
  private static final String TAG = "ContactUtil";

  private static void CreateNewContact(ContentResolver paramContentResolver, Cursor paramCursor, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, Bitmap paramBitmap)
  {
    ContentValues localContentValues = new ContentValues();
    long l = ContentUris.parseId(paramContentResolver.insert(ContactsContract.RawContacts.CONTENT_URI, localContentValues));
    insertDisplayName(paramContentResolver, localContentValues, paramString4, l);
    insertMobileNum(paramContentResolver, localContentValues, paramString1, l);
    insertPhoneNum(paramContentResolver, localContentValues, paramString2, l);
    insertEmail(paramContentResolver, localContentValues, paramString3, l);
    insertWorkINC(paramContentResolver, localContentValues, paramString5, paramString6, l);
    insertCity(paramContentResolver, localContentValues, paramString7, l);
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    paramBitmap.compress(Bitmap.CompressFormat.PNG, 100, localByteArrayOutputStream);
    setContactPhoto(paramContentResolver, localByteArrayOutputStream.toByteArray(), String.valueOf(l));
  }

  public static String findContactIdByMobile(ContentResolver paramContentResolver, String paramString)
  {
    Object[] arrayOfObject = new Object[2];
    String str1;
    Object localObject1;
    if (paramContentResolver == null)
    {
      str1 = "null";
      arrayOfObject[0] = str1;
      arrayOfObject[1] = paramString;
      KXLog.w("ContactUtil", "find contact id with [ContentResolver]=%s [mobileNum]=%s", arrayOfObject);
      localObject1 = null;
      if (paramContentResolver != null)
      {
        boolean bool = TextUtils.isEmpty(paramString);
        localObject1 = null;
        if (!bool)
          break label60;
      }
    }
    while (true)
    {
      return localObject1;
      str1 = paramContentResolver.toString();
      break;
      label60: String str2 = "%" + paramString.trim() + "%";
      String[] arrayOfString = { "contact_id" };
      Cursor localCursor = paramContentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, arrayOfString, "data1 like ?", new String[] { str2 }, null);
      try
      {
        if (localCursor.moveToFirst())
        {
          String str3 = localCursor.getString(localCursor.getColumnIndex("contact_id"));
          localObject1 = str3;
          return localObject1;
        }
        localObject1 = null;
        return null;
      }
      finally
      {
        if (localCursor != null)
          localCursor.close();
      }
    }
    throw localObject2;
  }

  public static String findContactIdByName(ContentResolver paramContentResolver, String paramString)
  {
    Object localObject1;
    if ((paramContentResolver == null) || (TextUtils.isEmpty(paramString)))
    {
      localObject1 = null;
      return localObject1;
    }
    String[] arrayOfString = { "_id", "display_name" };
    Cursor localCursor = null;
    while (true)
    {
      try
      {
        localCursor = paramContentResolver.query(ContactsContract.Contacts.CONTENT_URI, arrayOfString, "display_name = ?", new String[] { paramString }, null);
        StringBuffer localStringBuffer = new StringBuffer();
        if (localCursor != null)
        {
          int i = localCursor.getColumnIndex("_id");
          int j = localCursor.getColumnIndex("display_name");
          boolean bool = localCursor.moveToFirst();
          if (!bool)
            return null;
          localStringBuffer.append(localCursor.getLong(i));
          localStringBuffer.append("\t");
          localStringBuffer.append(localCursor.getString(j)).append("\n");
          if (localCursor.moveToNext())
            continue;
          String str = localStringBuffer.toString();
          localObject1 = str;
          return localObject1;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return null;
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

  public static String getAllPhoneNumbers(ContentResolver paramContentResolver)
  {
    ArrayList localArrayList = getPhoneList(paramContentResolver);
    if (localArrayList == null);
    for (int i = 0; i == 0; i = localArrayList.size())
      return null;
    StringBuffer localStringBuffer = new StringBuffer();
    int j = 0;
    if (j >= i)
      return localStringBuffer.toString();
    if (j == 0)
      localStringBuffer.append((String)localArrayList.get(j));
    while (true)
    {
      j++;
      break;
      localStringBuffer.append(",").append((String)localArrayList.get(j));
    }
  }

  private static String getContactCity(ContentResolver paramContentResolver, Cursor paramCursor, String paramString)
  {
    Cursor localCursor = paramContentResolver.query(ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI, null, "contact_id=" + paramString, null, null);
    boolean bool = localCursor.moveToFirst();
    String str = null;
    if (bool)
      str = localCursor.getString(localCursor.getColumnIndex("data7"));
    return str;
  }

  private static String getContactCompany(ContentResolver paramContentResolver, Cursor paramCursor, String paramString)
  {
    Cursor localCursor = paramContentResolver.query(ContactsContract.Data.CONTENT_URI, null, "contact_id=" + paramString + " AND " + "mimetype" + "='" + "vnd.android.cursor.item/organization" + "'", null, null);
    boolean bool = localCursor.moveToFirst();
    String str = null;
    if (bool)
      str = localCursor.getString(localCursor.getColumnIndex("data1"));
    return str;
  }

  private static String getContactEmail(ContentResolver paramContentResolver, Cursor paramCursor, String paramString)
  {
    Cursor localCursor = paramContentResolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, "contact_id=" + paramString, null, null);
    boolean bool = localCursor.moveToFirst();
    String str = null;
    if (bool);
    while (true)
    {
      if (localCursor.isAfterLast())
        return str;
      if (localCursor.getInt(localCursor.getColumnIndex("data2")) == 2)
        str = localCursor.getString(localCursor.getColumnIndex("data1"));
      localCursor.moveToNext();
    }
  }

  private static Bitmap getContactLogo(ContentResolver paramContentResolver, Cursor paramCursor, String paramString)
  {
    boolean bool = paramContentResolver.query(ContactsContract.Data.CONTENT_URI, null, "contact_id=" + paramString, null, null).moveToFirst();
    Bitmap localBitmap = null;
    if (bool)
      localBitmap = BitmapFactory.decodeStream(ContactsContract.Contacts.openContactPhotoInputStream(paramContentResolver, ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.parseLong(paramString))));
    return localBitmap;
  }

  private static String getContactName(ContentResolver paramContentResolver, Cursor paramCursor, String paramString)
  {
    Cursor localCursor = paramContentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, "contact_id=" + paramString, null, null);
    boolean bool = localCursor.moveToFirst();
    String str = null;
    if (bool)
      str = localCursor.getString(localCursor.getColumnIndex("display_name"));
    return str;
  }

  private static String getContactPhoneNum(ContentResolver paramContentResolver, Cursor paramCursor, String paramString)
  {
    Cursor localCursor = paramContentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, "contact_id=" + paramString, null, null);
    boolean bool = localCursor.moveToFirst();
    String str = null;
    if (bool);
    while (true)
    {
      if (localCursor.isAfterLast())
        return str;
      if (localCursor.getInt(localCursor.getColumnIndex("data2")) == 3)
        str = localCursor.getString(localCursor.getColumnIndex("data1"));
      localCursor.moveToNext();
    }
  }

  private static String getContactTitle(ContentResolver paramContentResolver, Cursor paramCursor, String paramString)
  {
    Cursor localCursor = paramContentResolver.query(ContactsContract.Data.CONTENT_URI, null, "contact_id=" + paramString + " AND " + "mimetype" + "='" + "vnd.android.cursor.item/organization" + "'", null, null);
    boolean bool = localCursor.moveToFirst();
    String str = null;
    if (bool)
      str = localCursor.getString(localCursor.getColumnIndex("data4"));
    return str;
  }

  public static ArrayList<String> getPhoneList(ContentResolver paramContentResolver)
  {
    Cursor localCursor = null;
    try
    {
      String[] arrayOfString = { "data1" };
      localCursor = paramContentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, arrayOfString, null, null, null);
      int i = localCursor.getColumnIndex("data1");
      boolean bool1 = localCursor.moveToFirst();
      if (!bool1)
      {
        if (localCursor != null)
          localCursor.close();
        localArrayList = null;
        return localArrayList;
      }
      ArrayList localArrayList = new ArrayList();
      String str = localCursor.getString(i);
      if (TextUtils.isEmpty(str));
      while (true)
      {
        boolean bool2 = localCursor.moveToNext();
        if (bool2)
          break;
        return localArrayList;
        if (str.startsWith("+86"))
          str = str.substring("+86".length());
        localArrayList.add(str);
      }
    }
    catch (Exception localException)
    {
      KXLog.e("getList", "failed to get items", localException);
      return null;
    }
    finally
    {
      if (localCursor != null)
        localCursor.close();
    }
    throw localObject;
  }

  private static void insertCity(ContentResolver paramContentResolver, ContentValues paramContentValues, String paramString, long paramLong)
  {
    if (TextUtils.isEmpty(paramString))
      return;
    paramContentValues.clear();
    paramContentValues.put("raw_contact_id", Long.valueOf(paramLong));
    paramContentValues.put("mimetype", "vnd.android.cursor.item/postal-address_v2");
    paramContentValues.put("data7", paramString);
    paramContentResolver.insert(ContactsContract.Data.CONTENT_URI, paramContentValues);
  }

  private static void insertDisplayName(ContentResolver paramContentResolver, ContentValues paramContentValues, String paramString, long paramLong)
  {
    if (TextUtils.isEmpty(paramString))
      return;
    paramContentValues.clear();
    paramContentValues.put("raw_contact_id", Long.valueOf(paramLong));
    paramContentValues.put("mimetype", "vnd.android.cursor.item/name");
    paramContentValues.put("data1", paramString);
    paramContentResolver.insert(ContactsContract.Data.CONTENT_URI, paramContentValues);
  }

  private static void insertEmail(ContentResolver paramContentResolver, ContentValues paramContentValues, String paramString, long paramLong)
  {
    if (TextUtils.isEmpty(paramString))
      return;
    paramContentValues.clear();
    paramContentValues.put("raw_contact_id", Long.valueOf(paramLong));
    paramContentValues.put("mimetype", "vnd.android.cursor.item/email_v2");
    paramContentValues.put("data2", Integer.valueOf(2));
    paramContentValues.put("data1", paramString);
    paramContentResolver.insert(ContactsContract.Data.CONTENT_URI, paramContentValues);
  }

  private static void insertMobileNum(ContentResolver paramContentResolver, ContentValues paramContentValues, String paramString, long paramLong)
  {
    if (TextUtils.isEmpty(paramString))
      return;
    paramContentValues.clear();
    paramContentValues.put("raw_contact_id", Long.valueOf(paramLong));
    paramContentValues.put("mimetype", "vnd.android.cursor.item/phone_v2");
    paramContentValues.put("data2", Integer.valueOf(2));
    paramContentValues.put("data1", paramString);
    paramContentResolver.insert(ContactsContract.Data.CONTENT_URI, paramContentValues);
  }

  private static void insertPhoneNum(ContentResolver paramContentResolver, ContentValues paramContentValues, String paramString, long paramLong)
  {
    if (TextUtils.isEmpty(paramString))
      return;
    paramContentValues.clear();
    paramContentValues.put("raw_contact_id", Long.valueOf(paramLong));
    paramContentValues.put("mimetype", "vnd.android.cursor.item/phone_v2");
    paramContentValues.put("data2", Integer.valueOf(3));
    paramContentValues.put("data1", paramString);
    paramContentResolver.insert(ContactsContract.Data.CONTENT_URI, paramContentValues);
  }

  private static void insertWorkINC(ContentResolver paramContentResolver, ContentValues paramContentValues, String paramString1, String paramString2, long paramLong)
  {
    paramContentValues.clear();
    paramContentValues.put("raw_contact_id", Long.valueOf(paramLong));
    paramContentValues.put("mimetype", "vnd.android.cursor.item/organization");
    paramContentValues.put("data1", paramString1);
    paramContentValues.put("data4", paramString2);
    paramContentResolver.insert(ContactsContract.Data.CONTENT_URI, paramContentValues);
  }

  private static void setContactPhoto(ContentResolver paramContentResolver, byte[] paramArrayOfByte, String paramString)
  {
    ContentValues localContentValues = new ContentValues();
    int i = -1;
    String str = "contact_id=" + paramString + " AND " + "mimetype" + "=='" + "vnd.android.cursor.item/photo" + "'";
    KXLog.w("ContactUtil", str);
    Cursor localCursor = paramContentResolver.query(ContactsContract.Data.CONTENT_URI, null, str, null, null);
    int j = localCursor.getColumnIndexOrThrow("_id");
    if (localCursor.moveToFirst())
      i = localCursor.getInt(j);
    localCursor.close();
    localContentValues.put("raw_contact_id", paramString);
    localContentValues.put("is_super_primary", Integer.valueOf(1));
    localContentValues.put("data15", paramArrayOfByte);
    localContentValues.put("mimetype", "vnd.android.cursor.item/photo");
    if (i >= 0)
    {
      paramContentResolver.update(ContactsContract.Data.CONTENT_URI, localContentValues, "_id = " + i, null);
      return;
    }
    paramContentResolver.insert(ContactsContract.Data.CONTENT_URI, localContentValues);
  }

  public static int storeCardToLocalContacts(ContentResolver paramContentResolver, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, Bitmap paramBitmap)
  {
    KXLog.w("ContactUtil", "[mobileNum]=%s [phoneNum]=%s [email]=%s [name]=%s [workInc]=%s [title]=%s [city]=%s ", new Object[] { paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, paramString7 });
    Cursor localCursor = null;
    try
    {
      String str1 = findContactIdByMobile(paramContentResolver, paramString1);
      KXLog.w("ContactUtil", " get contact id=%s of mobilenum=%s", new Object[] { str1, paramString1 });
      if (!TextUtils.isEmpty(str1))
      {
        String str2 = getContactPhoneNum(paramContentResolver, null, str1);
        String str3 = getContactEmail(paramContentResolver, null, str1);
        String str4 = getContactName(paramContentResolver, null, str1);
        String str5 = getContactCompany(paramContentResolver, null, str1);
        String str6 = getContactTitle(paramContentResolver, null, str1);
        String str7 = getContactCity(paramContentResolver, null, str1);
        Bitmap localBitmap = getContactLogo(paramContentResolver, null, str1);
        KXLog.w("ContactUtil", "localPhoneNum=%s localEmail=%s localContactName=%s localCompany=%s localTitle=%s localCity=%s localLogo=%s", new Object[] { str2, str3, str4, str5, str6, str7, localBitmap });
        ContentValues localContentValues = new ContentValues();
        localCursor = paramContentResolver.query(ContactsContract.RawContacts.CONTENT_URI, null, "contact_id=" + str1, null, null);
        long l = 0L;
        if (localCursor.moveToFirst())
          l = localCursor.getLong(localCursor.getColumnIndex("_id"));
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Long.valueOf(l);
        KXLog.w("ContactUtil", "^^^^^  find contact raw=%d ^^^^^^^^^", arrayOfObject);
        int i = 0;
        if (paramString2 != null)
        {
          boolean bool = TextUtils.isEmpty(str2);
          i = 0;
          if (bool)
          {
            KXLog.w("ContactUtil", "^^^^^^^^   UPDATE  PHONE with new phone=%s", new Object[] { paramString2 });
            insertPhoneNum(paramContentResolver, localContentValues, paramString2, l);
            i = 1;
          }
        }
        if ((paramString3 != null) && (TextUtils.isEmpty(str3)))
        {
          insertEmail(paramContentResolver, localContentValues, paramString3, l);
          i = 1;
        }
        if ((paramString4 != null) && (TextUtils.isEmpty(str4)))
        {
          insertDisplayName(paramContentResolver, localContentValues, paramString4, l);
          i = 1;
        }
        if (((paramString5 != null) && (TextUtils.isEmpty(str5))) || ((paramString6 != null) && (TextUtils.isEmpty(str6))))
        {
          insertWorkINC(paramContentResolver, localContentValues, paramString5, paramString6, l);
          i = 1;
        }
        if ((paramString7 != null) && (TextUtils.isEmpty(str7)))
        {
          insertCity(paramContentResolver, localContentValues, paramString7, l);
          i = 1;
        }
        if ((paramBitmap != null) && (localBitmap == null))
        {
          ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
          paramBitmap.compress(Bitmap.CompressFormat.PNG, 100, localByteArrayOutputStream);
          setContactPhoto(paramContentResolver, localByteArrayOutputStream.toByteArray(), String.valueOf(l));
          i = 1;
        }
        if (i != 0)
          return 2;
        return 0;
      }
      if (TextUtils.isEmpty(paramString1))
        if (TextUtils.isEmpty(findContactIdByName(paramContentResolver, paramString4)))
          CreateNewContact(paramContentResolver, null, paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, paramString7, paramBitmap);
      while (true)
      {
        if (0 != 0)
          null.close();
        return 1;
        if (0 != 0)
          null.close();
        return 0;
        CreateNewContact(paramContentResolver, null, paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, paramString7, paramBitmap);
      }
    }
    catch (Exception localException)
    {
      return 0;
    }
    finally
    {
      if (localCursor != null)
        localCursor.close();
    }
    throw localObject;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.util.ContactUtil
 * JD-Core Version:    0.6.0
 */