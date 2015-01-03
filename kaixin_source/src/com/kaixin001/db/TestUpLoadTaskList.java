package com.kaixin001.db;

import android.content.ContentValues;
import android.content.Context;
import java.util.ArrayList;

public class TestUpLoadTaskList
{
  Context m_context = null;
  ArrayList<UpLoadTaskListDBAdapter.TaskParameters> m_dataList = null;
  int m_nInCreament = 0;

  public TestUpLoadTaskList(Context paramContext)
  {
    this.m_context = paramContext;
  }

  public void case1()
  {
    insertOneRow();
    this.m_dataList = query();
    insertOneRow();
    deleteOneRow(2);
    insertOneRow();
    deleteOneRow(8);
    insertOneRow();
    deleteOneRow(5);
    insertOneRow();
    insertOneRow();
    deleteOneRow(1);
    insertOneRow();
    this.m_dataList = query();
  }

  public void case2()
  {
    for (int i = 0; ; i++)
    {
      if (i >= 4)
      {
        deleteRows();
        this.m_dataList = query();
        return;
      }
      insertOneRowHaveTime();
    }
  }

  public void deleteOneRow(int paramInt)
  {
    UpLoadTaskListDBAdapter localUpLoadTaskListDBAdapter = new UpLoadTaskListDBAdapter(this.m_context);
    try
    {
      localUpLoadTaskListDBAdapter.deleteATask(paramInt);
      return;
    }
    finally
    {
      localUpLoadTaskListDBAdapter.close();
    }
    throw localObject;
  }

  public void deleteRows()
  {
  }

  public void execute()
  {
    case1();
    case2();
  }

  public long insertOneRow()
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("DATA15", "123456");
    localContentValues.put("DATA8", "2");
    localContentValues.put("DATA16", "1088");
    UpLoadTaskListDBAdapter localUpLoadTaskListDBAdapter = new UpLoadTaskListDBAdapter(this.m_context);
    try
    {
      long l = localUpLoadTaskListDBAdapter.insertTask(localContentValues);
      return l;
    }
    finally
    {
      localUpLoadTaskListDBAdapter.close();
    }
    throw localObject;
  }

  public long insertOneRowHaveTime()
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("DATA15", "123456");
    localContentValues.put("DATA8", "2");
    localContentValues.put("DATA16", "1088");
    int i = this.m_nInCreament;
    this.m_nInCreament = (i + 1);
    localContentValues.put("DATA11", Integer.toString(20110510 + i));
    int j = this.m_nInCreament;
    this.m_nInCreament = (j + 1);
    localContentValues.put("DATA12", Integer.toString(20110511 + j));
    UpLoadTaskListDBAdapter localUpLoadTaskListDBAdapter = new UpLoadTaskListDBAdapter(this.m_context);
    try
    {
      long l = localUpLoadTaskListDBAdapter.insertTask(localContentValues);
      return l;
    }
    finally
    {
      localUpLoadTaskListDBAdapter.close();
    }
    throw localObject;
  }

  public ArrayList<UpLoadTaskListDBAdapter.TaskParameters> query()
  {
    UpLoadTaskListDBAdapter localUpLoadTaskListDBAdapter = new UpLoadTaskListDBAdapter(this.m_context);
    try
    {
      ArrayList localArrayList = localUpLoadTaskListDBAdapter.getTasks(Integer.toString(20110513), null, null, -1, -1, -1);
      return localArrayList;
    }
    finally
    {
      localUpLoadTaskListDBAdapter.close();
    }
    throw localObject;
  }

  public void upDateOneRow(int paramInt)
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("DATA8", "20");
    UpLoadTaskListDBAdapter localUpLoadTaskListDBAdapter = new UpLoadTaskListDBAdapter(this.m_context);
    try
    {
      localUpLoadTaskListDBAdapter.updateTask(paramInt, localContentValues);
      return;
    }
    finally
    {
      localUpLoadTaskListDBAdapter.close();
    }
    throw localObject;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.db.TestUpLoadTaskList
 * JD-Core Version:    0.6.0
 */