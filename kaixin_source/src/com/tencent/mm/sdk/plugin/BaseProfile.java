package com.tencent.mm.sdk.plugin;

import android.content.ContentValues;
import android.database.Cursor;
import com.tencent.mm.sdk.storage.IAutoDBItem;
import com.tencent.mm.sdk.storage.IAutoDBItem.MAutoDBInfo;
import java.lang.reflect.Field;
import java.util.Map;

public abstract class BaseProfile extends IAutoDBItem
{
  public static final String COL_ALIAS = "alias";
  public static final String COL_AVATAR = "avatar";
  public static final String COL_BINDEMAIL = "bindemail";
  public static final String COL_BINDMOBILE = "bindmobile";
  public static final String COL_BINDQQ = "bindqq";
  public static final String COL_CITY = "city";
  public static final String COL_NICKNAME = "nickname";
  public static final String COL_PROVINCE = "province";
  public static final String COL_SIGNATURE = "signature";
  public static final String COL_USERNAME = "username";
  public static final String COL_WEIBO = "weibo";
  public static final String[] INDEX_CREATE = new String[0];
  public static final String TABLE_NAME = "Profile";
  public String field_alias;
  public String field_avatar;
  public String field_bindemail;
  public String field_bindmobile;
  public long field_bindqq;
  public String field_city;
  public String field_nickname;
  public String field_province;
  public String field_signature;
  public String field_username;
  public String field_weibo;

  public static IAutoDBItem.MAutoDBInfo initAutoDBInfo(Class<?> paramClass)
  {
    IAutoDBItem.MAutoDBInfo localMAutoDBInfo = new IAutoDBItem.MAutoDBInfo();
    localMAutoDBInfo.fields = new Field[11];
    localMAutoDBInfo.columns = new String[12];
    StringBuilder localStringBuilder = new StringBuilder();
    localMAutoDBInfo.columns[0] = "username";
    localMAutoDBInfo.colsMap.put("username", "TEXT");
    localStringBuilder.append(" username TEXT");
    localStringBuilder.append(", ");
    localMAutoDBInfo.columns[1] = "bindqq";
    localMAutoDBInfo.colsMap.put("bindqq", "LONG");
    localStringBuilder.append(" bindqq LONG");
    localStringBuilder.append(", ");
    localMAutoDBInfo.columns[2] = "bindmobile";
    localMAutoDBInfo.colsMap.put("bindmobile", "TEXT");
    localStringBuilder.append(" bindmobile TEXT");
    localStringBuilder.append(", ");
    localMAutoDBInfo.columns[3] = "bindemail";
    localMAutoDBInfo.colsMap.put("bindemail", "TEXT");
    localStringBuilder.append(" bindemail TEXT");
    localStringBuilder.append(", ");
    localMAutoDBInfo.columns[4] = "alias";
    localMAutoDBInfo.colsMap.put("alias", "TEXT");
    localStringBuilder.append(" alias TEXT");
    localStringBuilder.append(", ");
    localMAutoDBInfo.columns[5] = "nickname";
    localMAutoDBInfo.colsMap.put("nickname", "TEXT");
    localStringBuilder.append(" nickname TEXT");
    localStringBuilder.append(", ");
    localMAutoDBInfo.columns[6] = "signature";
    localMAutoDBInfo.colsMap.put("signature", "TEXT");
    localStringBuilder.append(" signature TEXT");
    localStringBuilder.append(", ");
    localMAutoDBInfo.columns[7] = "province";
    localMAutoDBInfo.colsMap.put("province", "TEXT");
    localStringBuilder.append(" province TEXT");
    localStringBuilder.append(", ");
    localMAutoDBInfo.columns[8] = "city";
    localMAutoDBInfo.colsMap.put("city", "TEXT");
    localStringBuilder.append(" city TEXT");
    localStringBuilder.append(", ");
    localMAutoDBInfo.columns[9] = "weibo";
    localMAutoDBInfo.colsMap.put("weibo", "TEXT");
    localStringBuilder.append(" weibo TEXT");
    localStringBuilder.append(", ");
    localMAutoDBInfo.columns[10] = "avatar";
    localMAutoDBInfo.colsMap.put("avatar", "TEXT");
    localStringBuilder.append(" avatar TEXT");
    localMAutoDBInfo.columns[11] = "rowid";
    localMAutoDBInfo.sql = localStringBuilder.toString();
    return localMAutoDBInfo;
  }

  public void convertFrom(Cursor paramCursor)
  {
    int i = paramCursor.getColumnIndex("username");
    if (i >= 0)
      this.field_username = paramCursor.getString(i);
    int j = paramCursor.getColumnIndex("bindqq");
    if (j >= 0)
      this.field_bindqq = paramCursor.getLong(j);
    int k = paramCursor.getColumnIndex("bindmobile");
    if (k >= 0)
      this.field_bindmobile = paramCursor.getString(k);
    int m = paramCursor.getColumnIndex("bindemail");
    if (m >= 0)
      this.field_bindemail = paramCursor.getString(m);
    int n = paramCursor.getColumnIndex("alias");
    if (n >= 0)
      this.field_alias = paramCursor.getString(n);
    int i1 = paramCursor.getColumnIndex("nickname");
    if (i1 >= 0)
      this.field_nickname = paramCursor.getString(i1);
    int i2 = paramCursor.getColumnIndex("signature");
    if (i2 >= 0)
      this.field_signature = paramCursor.getString(i2);
    int i3 = paramCursor.getColumnIndex("province");
    if (i3 >= 0)
      this.field_province = paramCursor.getString(i3);
    int i4 = paramCursor.getColumnIndex("city");
    if (i4 >= 0)
      this.field_city = paramCursor.getString(i4);
    int i5 = paramCursor.getColumnIndex("weibo");
    if (i5 >= 0)
      this.field_weibo = paramCursor.getString(i5);
    int i6 = paramCursor.getColumnIndex("avatar");
    if (i6 >= 0)
      this.field_avatar = paramCursor.getString(i6);
    int i7 = paramCursor.getColumnIndex("rowid");
    if (i7 >= 0)
      this.systemRowid = paramCursor.getLong(i7);
  }

  public ContentValues convertTo()
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("username", this.field_username);
    localContentValues.put("bindqq", Long.valueOf(this.field_bindqq));
    localContentValues.put("bindmobile", this.field_bindmobile);
    localContentValues.put("bindemail", this.field_bindemail);
    localContentValues.put("alias", this.field_alias);
    localContentValues.put("nickname", this.field_nickname);
    localContentValues.put("signature", this.field_signature);
    localContentValues.put("province", this.field_province);
    localContentValues.put("city", this.field_city);
    localContentValues.put("weibo", this.field_weibo);
    localContentValues.put("avatar", this.field_avatar);
    if (this.systemRowid > 0L)
      localContentValues.put("rowid", Long.valueOf(this.systemRowid));
    return localContentValues;
  }

  public void reset()
  {
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.plugin.BaseProfile
 * JD-Core Version:    0.6.0
 */