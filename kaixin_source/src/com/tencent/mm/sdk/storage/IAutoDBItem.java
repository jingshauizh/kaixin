package com.tencent.mm.sdk.storage;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import com.tencent.mm.sdk.platformtools.Log;
import com.tencent.mm.sdk.platformtools.Util;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import junit.framework.Assert;

public abstract class IAutoDBItem
  implements MDBItem
{
  public static final String COL_ROWID = "rowid";
  public static final String FIELD_PREFIX = "field_";
  public static final String SYSTEM_ROWID_FIELD = "rowid";
  public long systemRowid = -1L;

  private static String[] a(Field[] paramArrayOfField)
  {
    String[] arrayOfString = new String[1 + paramArrayOfField.length];
    int i = 0;
    if (i < paramArrayOfField.length)
    {
      arrayOfString[i] = getColName(paramArrayOfField[i]);
      String str = "getFullColumns failed:" + paramArrayOfField[i].getName();
      if (!Util.isNullOrNil(arrayOfString[i]));
      for (boolean bool = true; ; bool = false)
      {
        Assert.assertTrue(str, bool);
        i++;
        break;
      }
    }
    arrayOfString[paramArrayOfField.length] = "rowid";
    return arrayOfString;
  }

  private static Map<String, String> b(Field[] paramArrayOfField)
  {
    HashMap localHashMap = new HashMap();
    int i = 0;
    if (i < paramArrayOfField.length)
    {
      Field localField = paramArrayOfField[i];
      String str1 = CursorFieldHelper.type(localField.getType());
      if (str1 == null)
        Log.e("MicroMsg.SDK.IAutoDBItem", "failed identify on column: " + localField.getName() + ", skipped");
      while (true)
      {
        i++;
        break;
        String str2 = getColName(localField);
        if (Util.isNullOrNil(str2))
          continue;
        localHashMap.put(str2, str1);
      }
    }
    return localHashMap;
  }

  private static String c(Field[] paramArrayOfField)
  {
    StringBuilder localStringBuilder1 = new StringBuilder();
    int i = 0;
    Field localField;
    String str1;
    String str2;
    String str3;
    if (i < paramArrayOfField.length)
    {
      localField = paramArrayOfField[i];
      str1 = CursorFieldHelper.type(localField.getType());
      if (str1 == null)
        Log.e("MicroMsg.SDK.IAutoDBItem", "failed identify on column: " + localField.getName() + ", skipped");
      do
      {
        i++;
        break;
        str2 = getColName(localField);
      }
      while (Util.isNullOrNil(str2));
      if (!localField.isAnnotationPresent(MAutoDBFieldAnnotation.class))
        break label239;
      str3 = " default '" + ((MAutoDBFieldAnnotation)localField.getAnnotation(MAutoDBFieldAnnotation.class)).defValue() + "' ";
    }
    for (int j = ((MAutoDBFieldAnnotation)localField.getAnnotation(MAutoDBFieldAnnotation.class)).primaryKey(); ; j = 0)
    {
      StringBuilder localStringBuilder2 = new StringBuilder().append(str2).append(" ").append(str1).append(str3);
      String str4;
      if (j == 1)
      {
        str4 = " PRIMARY KEY ";
        label183: localStringBuilder1.append(str4);
        if (i != -1 + paramArrayOfField.length)
          break label227;
      }
      label227: for (String str5 = ""; ; str5 = ", ")
      {
        localStringBuilder1.append(str5);
        break;
        str4 = "";
        break label183;
      }
      return localStringBuilder1.toString();
      label239: str3 = "";
    }
  }

  public static boolean checkIOEqual(ContentValues paramContentValues, Cursor paramCursor)
  {
    if (paramContentValues == null)
      return paramCursor == null;
    if ((paramCursor == null) || (paramCursor.getCount() != 1))
      return false;
    paramCursor.moveToFirst();
    int i = paramCursor.getColumnCount();
    int j = paramContentValues.size();
    if (paramContentValues.containsKey("rowid"))
      j--;
    if (paramCursor.getColumnIndex("rowid") != -1)
      i--;
    if (j != i)
      return false;
    label213: label349: label355: label361: label365: 
    while (true)
    {
      byte[] arrayOfByte1;
      byte[] arrayOfByte2;
      int m;
      int n;
      try
      {
        Iterator localIterator = paramContentValues.valueSet().iterator();
        if (localIterator.hasNext())
        {
          String str = (String)((Map.Entry)localIterator.next()).getKey();
          if (str.equals("rowid"))
            continue;
          int k = paramCursor.getColumnIndex(str);
          if (k == -1)
            return false;
          if (!(paramContentValues.get(str) instanceof byte[]))
            continue;
          arrayOfByte1 = (byte[])paramContentValues.get(str);
          arrayOfByte2 = paramCursor.getBlob(k);
          if (arrayOfByte1 != null)
            break label313;
          if (arrayOfByte2 == null)
            break label361;
          break label313;
          if (arrayOfByte1.length == arrayOfByte2.length)
            break label349;
          m = 0;
          break label326;
          if (n >= arrayOfByte1.length)
            break label361;
          if (arrayOfByte1[n] == arrayOfByte2[n])
            break label355;
          m = 0;
          break label326;
          if ((paramCursor.getString(k) == null) && (paramContentValues.get(str) != null))
            return false;
          if (paramContentValues.get(str) == null)
            return false;
          boolean bool = paramContentValues.get(str).toString().equals(paramCursor.getString(k));
          if (!bool)
            return false;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return false;
      }
      return true;
      if ((arrayOfByte1 == null) && (arrayOfByte2 != null))
        m = 0;
      while (true)
      {
        if (m != 0)
          break label365;
        return false;
        if ((arrayOfByte1 == null) || (arrayOfByte2 != null))
          break;
        m = 0;
        continue;
        n = 0;
        break label213;
        n++;
        break label213;
        m = 1;
      }
    }
  }

  public static String getColName(Field paramField)
  {
    if (paramField == null);
    String str;
    do
    {
      return null;
      str = paramField.getName();
    }
    while ((str == null) || (str.length() <= 0));
    if (str.startsWith("field_"))
      return str.substring(6);
    return str;
  }

  public static Cursor getCursorForProjection(ContentValues paramContentValues, String[] paramArrayOfString)
  {
    Object[] arrayOfObject = new Object[paramArrayOfString.length];
    for (int i = 0; i < arrayOfObject.length; i++)
      arrayOfObject[i] = paramContentValues.get(paramArrayOfString[i]);
    MatrixCursor localMatrixCursor = new MatrixCursor(paramArrayOfString);
    localMatrixCursor.addRow(arrayOfObject);
    return localMatrixCursor;
  }

  public static Field[] getValidFields(Class<?> paramClass)
  {
    return initAutoDBInfo(paramClass).fields;
  }

  public static MAutoDBInfo initAutoDBInfo(Class<?> paramClass)
  {
    MAutoDBInfo localMAutoDBInfo = new MAutoDBInfo();
    LinkedList localLinkedList = new LinkedList();
    Field[] arrayOfField = paramClass.getDeclaredFields();
    int i = arrayOfField.length;
    int j = 0;
    if (j < i)
    {
      Field localField = arrayOfField[j];
      int k = localField.getModifiers();
      String str1 = localField.getName();
      String str2;
      if ((str1 != null) && (Modifier.isPublic(k)) && (!Modifier.isFinal(k)))
      {
        if (!str1.startsWith("field_"))
          break label170;
        str2 = str1.substring(6);
        label95: if (!localField.isAnnotationPresent(MAutoDBFieldAnnotation.class))
          break label177;
        if (((MAutoDBFieldAnnotation)localField.getAnnotation(MAutoDBFieldAnnotation.class)).primaryKey() == 1)
          localMAutoDBInfo.primaryKey = str2;
      }
      while (true)
      {
        if (!Util.isNullOrNil(str2))
        {
          if (str2.equals("rowid"))
            Assert.assertTrue("field_rowid reserved by MAutoDBItem, change now!", false);
          localLinkedList.add(localField);
        }
        label170: label177: 
        do
        {
          j++;
          break;
          str2 = str1;
          break label95;
        }
        while (!str1.startsWith("field_"));
      }
    }
    localMAutoDBInfo.fields = ((Field[])localLinkedList.toArray(new Field[0]));
    localMAutoDBInfo.columns = a(localMAutoDBInfo.fields);
    localMAutoDBInfo.colsMap = b(localMAutoDBInfo.fields);
    localMAutoDBInfo.sql = c(localMAutoDBInfo.fields);
    return localMAutoDBInfo;
  }

  public abstract void convertFrom(Cursor paramCursor);

  public abstract ContentValues convertTo();

  protected abstract MAutoDBInfo getDBInfo();

  public static class MAutoDBInfo
  {
    public Map<String, String> colsMap = new HashMap();
    public String[] columns;
    public Field[] fields;
    public String primaryKey;
    public String sql;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.storage.IAutoDBItem
 * JD-Core Version:    0.6.0
 */