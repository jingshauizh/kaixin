package com.kaixin001.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import com.kaixin001.model.ContactsRelatedModel;
import java.util.ArrayList;

public class ContactsDBAdapter extends KXBaseDBAdapter
{
  public static final String CREATE_TABLE_SQL = "create table contacts (uid TEXT not null, cid INTEGER not null, cname TEXT not null, fname TEXT not null, fuid TEXT not null, flogo TEXT, status_update_data_id INTEGER not null);";
  public static final String DROP_TABLE_SQL = "DROP TABLE IF EXISTS contacts";
  public static final String _CONTACTS_COLUMN_CID = "cid";
  public static final String _CONTACTS_COLUMN_FNAME = "fname";
  public static final String _CONTACTS_COLUMN_FUID = "fuid";
  public static final String _CONTACTS_COLUMN_LOGO = "flogo";
  public static final String _CONTACTS_COLUMN_NAME = "cname";
  public static final String _CONTACTS_COLUMN_STATUS_ID = "status_update_data_id";
  public static final String _CONTACTS_COLUMN_UID = "uid";
  public static final String _CONTACTS_TABLE_NAME = "contacts";

  public ContactsDBAdapter(Context paramContext)
  {
    super(paramContext);
  }

  private ContentValues getContentValues(String paramString1, long paramLong1, String paramString2, String paramString3, String paramString4, String paramString5, long paramLong2)
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("uid", paramString1);
    localContentValues.put("cid", Long.valueOf(paramLong1));
    localContentValues.put("cname", paramString2);
    localContentValues.put("fname", paramString3);
    localContentValues.put("fuid", paramString4);
    localContentValues.put("flogo", paramString5);
    localContentValues.put("status_update_data_id", Long.valueOf(paramLong2));
    return localContentValues;
  }

  public boolean deleteKXFriends(String paramString, Long paramLong)
  {
    return super.delete("contacts", "uid = '" + paramString + "' and " + "cid" + " = " + paramLong, null) > 0;
  }

  public boolean deleteKXFriends(String paramString1, String paramString2)
  {
    return super.delete("contacts", "uid = ? and fuid = ?", new String[] { paramString1, paramString2 }) > 0;
  }

  public ArrayList<Long> getCids()
    throws SQLException
  {
    ArrayList localArrayList = new ArrayList();
    Cursor localCursor = super.query(true, "contacts", new String[] { "cid" }, null, null, null, null, null, null);
    if (localCursor != null);
    while (true)
    {
      if (!localCursor.moveToNext())
      {
        if (localCursor != null)
          localCursor.close();
        return localArrayList;
      }
      localArrayList.add(Long.valueOf(localCursor.getLong(0)));
    }
  }

  public ArrayList<String> getCnames()
    throws SQLException
  {
    ArrayList localArrayList = new ArrayList();
    Cursor localCursor = super.query(true, "contacts", new String[] { "cname" }, null, null, null, null, null, null);
    if (localCursor != null);
    while (true)
    {
      if (!localCursor.moveToNext())
      {
        if (localCursor != null)
          localCursor.close();
        return localArrayList;
      }
      localArrayList.add(localCursor.getString(0));
    }
  }

  public ArrayList<String> getFuids()
    throws SQLException
  {
    ArrayList localArrayList = new ArrayList();
    Cursor localCursor = super.query(true, "contacts", new String[] { "fuid" }, null, null, null, null, null, null);
    if (localCursor != null);
    while (true)
    {
      if (!localCursor.moveToNext())
      {
        if (localCursor != null)
          localCursor.close();
        return localArrayList;
      }
      localArrayList.add(localCursor.getString(0));
    }
  }

  public ArrayList<ContactsRelatedModel> getRelatedObjects()
    throws SQLException
  {
    ArrayList localArrayList = new ArrayList();
    Cursor localCursor = super.query(true, "contacts", null, null, null, null, null, null, null);
    if (localCursor != null);
    while (true)
    {
      if (!localCursor.moveToNext())
      {
        if (localCursor != null)
          localCursor.close();
        return localArrayList;
      }
      ContactsRelatedModel localContactsRelatedModel = new ContactsRelatedModel();
      String str1 = localCursor.getString(localCursor.getColumnIndex("fuid"));
      String str2 = localCursor.getString(localCursor.getColumnIndex("fname"));
      String str3 = localCursor.getString(localCursor.getColumnIndex("cname"));
      localContactsRelatedModel.setCid(Long.valueOf(localCursor.getLong(localCursor.getColumnIndex("cid"))));
      localContactsRelatedModel.setCname(str3);
      localContactsRelatedModel.setFuid(str1);
      localContactsRelatedModel.setFname(str2);
      localArrayList.add(localContactsRelatedModel);
    }
  }

  public long insertContacts(String paramString1, long paramLong1, String paramString2, String paramString3, String paramString4, String paramString5, long paramLong2)
  {
    if (paramString4.length() == 0)
      return -1L;
    return super.insert("contacts", null, getContentValues(paramString1, paramLong1, paramString2, paramString3, paramString4, paramString5, paramLong2));
  }

  public boolean updateAContacts(String paramString1, long paramLong1, String paramString2, String paramString3, String paramString4, String paramString5, long paramLong2)
  {
    return super.update("contacts", getContentValues(paramString1, paramLong1, paramString2, paramString3, paramString4, paramString5, paramLong2), "cid=" + paramLong1, null) > 0L;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.db.ContactsDBAdapter
 * JD-Core Version:    0.6.0
 */