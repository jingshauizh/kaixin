package com.kaixin001.pengpeng.data;

import java.util.ArrayList;

public class KXRecord
{
  public int hasDataFlag = 0;
  private KXDataUpdateListener mUpdateListner;
  private ArrayList rec_array = null;
  private double rec_double = -1.0D;
  private int rec_int = -1;
  private long rec_long = -1L;
  private Object rec_obj = null;
  private String rec_string = "";
  private String recordName;
  private int recordType;
  public int recvFlag = 0;
  public int sendFlag = 0;

  public KXRecord(String paramString, int paramInt, KXDataUpdateListener paramKXDataUpdateListener)
  {
    this.recordName = paramString;
    this.recordType = paramInt;
    this.mUpdateListner = paramKXDataUpdateListener;
  }

  public void clear()
  {
    this.rec_string = null;
    this.rec_array = null;
    this.rec_int = -1;
    this.rec_double = -1.0D;
    this.rec_long = -1L;
    this.sendFlag = 0;
    this.recvFlag = 0;
    this.hasDataFlag = 0;
  }

  public ArrayList getArrayValue()
  {
    return this.rec_array;
  }

  public double getDoubleValue()
  {
    return this.rec_double;
  }

  public int getIntValue()
  {
    return this.rec_int;
  }

  public long getLongValue()
  {
    return this.rec_long;
  }

  public Object getObjectValue()
  {
    return this.rec_obj;
  }

  public int getRecordType()
  {
    return this.recordType;
  }

  public String getStringValue()
  {
    return this.rec_string;
  }

  public String recordName()
  {
    return this.recordName;
  }

  public void setArrayValue(ArrayList paramArrayList)
  {
    this.hasDataFlag = 1;
    this.rec_array = paramArrayList;
    this.mUpdateListner.onRecordUpdate(this.recordName);
  }

  public void setDoubleValue(double paramDouble)
  {
    this.hasDataFlag = 1;
    this.rec_double = paramDouble;
  }

  public void setIntValue(int paramInt)
  {
    this.hasDataFlag = 1;
    this.rec_int = paramInt;
    this.mUpdateListner.onRecordUpdate(this.recordName);
  }

  public void setLongValue(long paramLong)
  {
    this.hasDataFlag = 1;
    this.rec_long = paramLong;
  }

  public void setObjectValue(Object paramObject)
  {
    this.hasDataFlag = 1;
    this.rec_obj = paramObject;
    this.mUpdateListner.onRecordUpdate(this.recordName);
  }

  public void setStringValue(String paramString)
  {
    this.hasDataFlag = 1;
    this.rec_string = paramString;
    this.mUpdateListner.onRecordUpdate(this.recordName);
  }

  public String toString()
  {
    StringBuffer localStringBuffer1 = new StringBuffer();
    localStringBuffer1.append("[KXRecord]>> [type]=").append(this.recordType);
    localStringBuffer1.append(" [recName]=").append(this.recordName);
    localStringBuffer1.append(" [hasDataFlag]=").append(this.hasDataFlag);
    localStringBuffer1.append(" [sendFlag]=").append(this.sendFlag);
    localStringBuffer1.append(" [rec_int]=").append(this.rec_int);
    localStringBuffer1.append(" [rec_double]=").append(this.rec_double);
    StringBuffer localStringBuffer2 = localStringBuffer1.append(" [rec_array]=").append(this.rec_array).append("<size:");
    if (this.rec_array == null);
    for (int i = 0; ; i = this.rec_array.size())
    {
      localStringBuffer2.append(i).append(">");
      return localStringBuffer1.toString();
    }
  }

  public static class RecordType
  {
    public static int TYPE_ARRAY_LIST;
    public static int TYPE_DOUBLE;
    public static int TYPE_INT = 1;
    public static int TYPE_LONG;
    public static int TYPE_OBJECT;
    public static int TYPE_STRING;

    static
    {
      TYPE_DOUBLE = 2;
      TYPE_LONG = 3;
      TYPE_STRING = 4;
      TYPE_OBJECT = 5;
      TYPE_ARRAY_LIST = 6;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.pengpeng.data.KXRecord
 * JD-Core Version:    0.6.0
 */