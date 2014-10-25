package com.kaixin001.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class KaixinDBHelper extends SQLiteOpenHelper
{
  private static final String DATABASE_NAME = "kaixin.db";
  private static final int DATABASE_VERSION = 3;
  private static KaixinDBHelper sInstance = null;

  private KaixinDBHelper(Context paramContext)
  {
    super(paramContext, "kaixin.db", null, 3);
  }

  public static final KaixinDBHelper getInstance(Context paramContext)
  {
   
    
      if (sInstance == null)
        sInstance = new KaixinDBHelper(paramContext);
      KaixinDBHelper localKaixinDBHelper = sInstance;
      return localKaixinDBHelper;
   
   
  }

  public void onCreate(SQLiteDatabase paramSQLiteDatabase)
  {
    paramSQLiteDatabase.execSQL("create table users (_id INTEGER primary key autoincrement, account TEXT not null, password TEXT, uid TEXT not null, name TEXT, state TEXT, coverUrl TEXT, gender INTEGER, hometown TEXT, city TEXT, status INTEGER, logo50 TEXT, logo120 TEXT, access_token TEXT, refresh_token TEXT, expires_in INTEGER, oauth_token_secret TEXT, reserved TEXT, recent_time_login INTEGER, islogin INTEGER);");
    paramSQLiteDatabase.execSQL("create table friends (_id INTEGER primary key autoincrement, uid TEXT not null, fuid TEXT not null, name TEXT not null, gender INTEGER, hometown TEXT, city TEXT, status INTEGER, logo50 TEXT, logo120 TEXT, birthday TEXT, bodyform INTEGER, blood INTEGER, phone TEXT, marriage INTEGER, company TEXT, school TEXT, isstar INTEGER, pinyin TEXT, fpy TEXT, online INTEGER, reserved TEXT);");
    paramSQLiteDatabase.execSQL("create table circles ( _rowid INTEGER primary key autoincrement, uid TEXT not null, circleid TEXT not null, flag INTEGER not null, reserved TEXT);");
    paramSQLiteDatabase.execSQL("create table gift (gid TEXT not null, gname INTEGER not null, pic TEXT not null, defaultPs TEXT not null);");
    paramSQLiteDatabase.execSQL("create table config (_rowid INTEGER primary key autoincrement, uid TEXT not null, field TEXT not null, value TEXT not null, reserved TEXT);");
    paramSQLiteDatabase.execSQL("create table uploadTaskListDBTable (nID INTEGER primary key autoincrement, DATA1 TEXT, DATA2 TEXT, DATA3 TEXT, DATA4 TEXT, DATA5 TEXT, DATA6 TEXT, DATA7 TEXT, DATA8 INTEGER, DATA9 TEXT, DATA10 INTEGER, DATA11 TEXT, DATA12 TEXT, DATA15 TEXT not null, DATA16 INTEGER, DATA17 TEXT, DATA18 TEXT, DATA19 TEXT, DATA20 TEXT, DATA21 TEXT);");
    paramSQLiteDatabase.execSQL("create table contacts (uid TEXT not null, cid INTEGER not null, cname TEXT not null, fname TEXT not null, fuid TEXT not null, flogo TEXT, status_update_data_id INTEGER not null);");
    paramSQLiteDatabase.execSQL("create table active (_rowid INTEGER primary key autoincrement, uid TEXT not null, activeflag TEXT not null, flag TEXT)");
    paramSQLiteDatabase.execSQL("create table cloudalbum (pid TEXT not null, md5 TEXT not null);");
  }

  public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
  {
    paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS users");
    paramSQLiteDatabase.execSQL("create table users (_id INTEGER primary key autoincrement, account TEXT not null, password TEXT, uid TEXT not null, name TEXT, state TEXT, coverUrl TEXT, gender INTEGER, hometown TEXT, city TEXT, status INTEGER, logo50 TEXT, logo120 TEXT, access_token TEXT, refresh_token TEXT, expires_in INTEGER, oauth_token_secret TEXT, reserved TEXT, recent_time_login INTEGER, islogin INTEGER);");
    paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS friends");
    paramSQLiteDatabase.execSQL("create table friends (_id INTEGER primary key autoincrement, uid TEXT not null, fuid TEXT not null, name TEXT not null, gender INTEGER, hometown TEXT, city TEXT, status INTEGER, logo50 TEXT, logo120 TEXT, birthday TEXT, bodyform INTEGER, blood INTEGER, phone TEXT, marriage INTEGER, company TEXT, school TEXT, isstar INTEGER, pinyin TEXT, fpy TEXT, online INTEGER, reserved TEXT);");
    paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS circles");
    paramSQLiteDatabase.execSQL("create table circles ( _rowid INTEGER primary key autoincrement, uid TEXT not null, circleid TEXT not null, flag INTEGER not null, reserved TEXT);");
    paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS config");
    paramSQLiteDatabase.execSQL("create table config (_rowid INTEGER primary key autoincrement, uid TEXT not null, field TEXT not null, value TEXT not null, reserved TEXT);");
    paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS gift");
    paramSQLiteDatabase.execSQL("create table gift (gid TEXT not null, gname INTEGER not null, pic TEXT not null, defaultPs TEXT not null);");
    paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS uploadTaskListDBTable");
    paramSQLiteDatabase.execSQL("create table uploadTaskListDBTable (nID INTEGER primary key autoincrement, DATA1 TEXT, DATA2 TEXT, DATA3 TEXT, DATA4 TEXT, DATA5 TEXT, DATA6 TEXT, DATA7 TEXT, DATA8 INTEGER, DATA9 TEXT, DATA10 INTEGER, DATA11 TEXT, DATA12 TEXT, DATA15 TEXT not null, DATA16 INTEGER, DATA17 TEXT, DATA18 TEXT, DATA19 TEXT, DATA20 TEXT, DATA21 TEXT);");
    paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS contacts");
    paramSQLiteDatabase.execSQL("create table contacts (uid TEXT not null, cid INTEGER not null, cname TEXT not null, fname TEXT not null, fuid TEXT not null, flogo TEXT, status_update_data_id INTEGER not null);");
    paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS active");
    paramSQLiteDatabase.execSQL("create table active (_rowid INTEGER primary key autoincrement, uid TEXT not null, activeflag TEXT not null, flag TEXT)");
    paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS cloudalbum");
    paramSQLiteDatabase.execSQL("create table cloudalbum (pid TEXT not null, md5 TEXT not null);");
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.db.KaixinDBHelper
 * JD-Core Version:    0.6.0
 */