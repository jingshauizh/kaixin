package com.kaixin001.pengpeng.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

public class KXDataManager
  implements KXDataUpdateListener
{
  public static final int AFFIRM_MISSION_OK = 3;
  public static final int AFIRM_RESULT_OK = 1;
  public static final int GET_AFFIRM_RESULT_OK = 2;
  private static final String TAG = "KXDataManager";
  private static volatile KXDataManager instance;
  private Hashtable<String, KXRecord> mBumpRecords = new Hashtable();
  private Hashtable<String, Vector<KXDataUpdateListener>> mRecListeners = new Hashtable();

  private KXDataManager()
  {
    initRecords();
  }

  private void createRecord(String paramString, int paramInt)
  {
    KXRecord localKXRecord = new KXRecord(paramString, paramInt, this);
    this.mBumpRecords.put(paramString, localKXRecord);
  }

  public static KXDataManager getInstance()
  {
    if (instance == null)
      monitorenter;
    try
    {
      if (instance == null)
        instance = new KXDataManager();
      return instance;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private void initRecords()
  {
    createRecord("ret", KXRecord.RecordType.TYPE_INT);
    createRecord("retryidle", KXRecord.RecordType.TYPE_INT);
    createRecord("deviceid", KXRecord.RecordType.TYPE_STRING);
    createRecord("method", KXRecord.RecordType.TYPE_STRING);
    createRecord("verify", KXRecord.RecordType.TYPE_STRING);
    createRecord("randnum", KXRecord.RecordType.TYPE_LONG);
    createRecord("again_reason", KXRecord.RecordType.TYPE_STRING);
    createRecord("myuid", KXRecord.RecordType.TYPE_STRING);
    createRecord("method", KXRecord.RecordType.TYPE_STRING);
    createRecord("systime", KXRecord.RecordType.TYPE_DOUBLE);
    createRecord("abstime", KXRecord.RecordType.TYPE_DOUBLE);
    createRecord("handsettime", KXRecord.RecordType.TYPE_DOUBLE);
    createRecord("upoffset", KXRecord.RecordType.TYPE_DOUBLE);
    createRecord("downoffset", KXRecord.RecordType.TYPE_DOUBLE);
    createRecord("latency", KXRecord.RecordType.TYPE_DOUBLE);
    createRecord("randnumT", KXRecord.RecordType.TYPE_LONG);
    createRecord("agree", KXRecord.RecordType.TYPE_INT);
    createRecord("matched", KXRecord.RecordType.TYPE_INT);
    createRecord("otheragree", KXRecord.RecordType.TYPE_INT);
    createRecord("lat", KXRecord.RecordType.TYPE_DOUBLE);
    createRecord("lon", KXRecord.RecordType.TYPE_DOUBLE);
    createRecord("accuracy", KXRecord.RecordType.TYPE_DOUBLE);
    createRecord("othercards", KXRecord.RecordType.TYPE_ARRAY_LIST);
    createRecord("otherinfo", KXRecord.RecordType.TYPE_OBJECT);
    createRecord("serveraffirm", KXRecord.RecordType.TYPE_INT);
  }

  public static String timeDoubleScale(double paramDouble)
  {
    return new BigDecimal(paramDouble).setScale(3, 5).toString();
  }

  public String buildRequestUrl()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?");
    localStringBuffer.append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    Object localObject = null;
    Iterator localIterator = this.mBumpRecords.keySet().iterator();
    if (!localIterator.hasNext())
      if (localObject != null)
      {
        if (!"bump.match".equals(localObject.getStringValue()))
          break label296;
        localObject.setStringValue("bump.getSystime");
        setRecordSendFlag("abstime", 0);
        setRecordSendFlag("lon", 0);
        setRecordSendFlag("lat", 0);
        setRecordSendFlag("accuracy", 0);
      }
    while (true)
    {
      return localStringBuffer.toString();
      String str1 = (String)localIterator.next();
      KXRecord localKXRecord = (KXRecord)this.mBumpRecords.get(str1);
      if ((localKXRecord.sendFlag != 1) || (localKXRecord.hasDataFlag != 1))
        break;
      String str2 = localKXRecord.recordName();
      localStringBuffer.append("&").append(str2).append("=");
      switch (localKXRecord.getRecordType())
      {
      default:
      case 1:
      case 2:
      case 3:
      case 4:
      }
      while (true)
      {
        if (!"method".equals(str2))
          break label294;
        localObject = localKXRecord;
        break;
        localStringBuffer.append(localKXRecord.getIntValue());
        continue;
        localStringBuffer.append(timeDoubleScale(localKXRecord.getDoubleValue()));
        continue;
        localStringBuffer.append(localKXRecord.getLongValue());
        continue;
        localStringBuffer.append(localKXRecord.getStringValue());
      }
      label294: break;
      label296: if (!"bump.affirm".equals(localObject.getStringValue()))
        continue;
      localObject.setStringValue("bump.getAffirm");
      setRecordSendFlag("agree", 0);
    }
  }

  public void clearRecdMonitors()
  {
    if (this.mRecListeners != null)
      this.mRecListeners.clear();
  }

  public ArrayList getArrayRecord(String paramString)
  {
    KXRecord localKXRecord = (KXRecord)this.mBumpRecords.get(paramString);
    if (localKXRecord != null)
      return localKXRecord.getArrayValue();
    return null;
  }

  public double getDoubleRecord(String paramString)
  {
    KXRecord localKXRecord = (KXRecord)this.mBumpRecords.get(paramString);
    if (localKXRecord != null)
      return localKXRecord.getDoubleValue();
    return -1.0D;
  }

  public int getIntRecord(String paramString)
  {
    KXRecord localKXRecord = (KXRecord)this.mBumpRecords.get(paramString);
    if (localKXRecord != null)
      return localKXRecord.getIntValue();
    return -1;
  }

  public long getLongRecord(String paramString)
  {
    KXRecord localKXRecord = (KXRecord)this.mBumpRecords.get(paramString);
    if (localKXRecord != null)
      return localKXRecord.getLongValue();
    return 0L;
  }

  public Object getObjectRecord(String paramString)
  {
    KXRecord localKXRecord = (KXRecord)this.mBumpRecords.get(paramString);
    if (localKXRecord != null)
      return localKXRecord.getObjectValue();
    return Long.valueOf(0L);
  }

  public KXRecord getRecordForName(String paramString)
  {
    return (KXRecord)this.mBumpRecords.get(paramString);
  }

  public int getRecordHasdataFlag(String paramString)
  {
    KXRecord localKXRecord = (KXRecord)this.mBumpRecords.get(paramString);
    if (localKXRecord != null)
      return localKXRecord.hasDataFlag;
    return -1;
  }

  public int getRecordSendFlag(String paramString)
  {
    KXRecord localKXRecord = (KXRecord)this.mBumpRecords.get(paramString);
    if (localKXRecord != null)
      return localKXRecord.sendFlag;
    return -1;
  }

  public String getStringRecord(String paramString)
  {
    KXRecord localKXRecord = (KXRecord)this.mBumpRecords.get(paramString);
    if (localKXRecord != null)
      return localKXRecord.getStringValue();
    return "";
  }

  public void initSessionData()
  {
    setIntRecord("retryidle", 0);
    resetRecord("randum");
    resetRecord("again_reason");
    resetRecord("abstime");
    resetRecord("randnumT");
    resetRecord("agree");
    resetRecord("otheragree");
    resetRecord("matched");
    resetRecord("otherinfo");
    resetRecord("serveraffirm");
    setIntRecord("serveraffirm", 0);
  }

  public void monitorRecord(String paramString, KXDataUpdateListener paramKXDataUpdateListener)
  {
    Vector localVector = (Vector)this.mRecListeners.get(paramString);
    if (localVector == null)
    {
      localVector = new Vector();
      this.mRecListeners.put(paramString, localVector);
    }
    localVector.add(paramKXDataUpdateListener);
  }

  public boolean onRecordUpdate(String paramString)
  {
    Vector localVector = (Vector)this.mRecListeners.get(paramString);
    int i;
    if (localVector != null)
      i = localVector.size();
    for (int j = 0; ; j++)
    {
      if (j >= i)
        return true;
      ((KXDataUpdateListener)localVector.get(j)).onRecordUpdate(paramString);
    }
  }

  public void resetRecord(String paramString)
  {
    KXRecord localKXRecord = (KXRecord)this.mBumpRecords.get(paramString);
    if (localKXRecord != null)
      localKXRecord.clear();
  }

  public void setArrayRecord(String paramString, ArrayList paramArrayList)
  {
    KXRecord localKXRecord = (KXRecord)this.mBumpRecords.get(paramString);
    if (localKXRecord != null)
      localKXRecord.setArrayValue(paramArrayList);
  }

  public void setDoubleRecord(String paramString, double paramDouble)
  {
    KXRecord localKXRecord = (KXRecord)this.mBumpRecords.get(paramString);
    if (localKXRecord != null)
      localKXRecord.setDoubleValue(paramDouble);
  }

  public void setIntRecord(String paramString, int paramInt)
  {
    KXRecord localKXRecord = (KXRecord)this.mBumpRecords.get(paramString);
    if (localKXRecord != null)
      localKXRecord.setIntValue(paramInt);
  }

  public void setLongRecord(String paramString, long paramLong)
  {
    KXRecord localKXRecord = (KXRecord)this.mBumpRecords.get(paramString);
    if (localKXRecord != null)
      localKXRecord.setLongValue(paramLong);
  }

  public void setObjectRecord(String paramString, Object paramObject)
  {
    KXRecord localKXRecord = (KXRecord)this.mBumpRecords.get(paramString);
    if (localKXRecord != null)
      localKXRecord.setObjectValue(paramObject);
  }

  public void setRecordHasdataFlag(String paramString, int paramInt)
  {
    KXRecord localKXRecord = (KXRecord)this.mBumpRecords.get(paramString);
    if (localKXRecord != null)
      localKXRecord.hasDataFlag = paramInt;
  }

  public void setRecordSendFlag(String paramString, int paramInt)
  {
    KXRecord localKXRecord = (KXRecord)this.mBumpRecords.get(paramString);
    if (localKXRecord != null)
      localKXRecord.sendFlag = paramInt;
  }

  public void setStringRecord(String paramString1, String paramString2)
  {
    KXRecord localKXRecord = (KXRecord)this.mBumpRecords.get(paramString1);
    if (localKXRecord != null)
      localKXRecord.setStringValue(paramString2);
  }

  public boolean unMonitorRecord(String paramString, KXDataUpdateListener paramKXDataUpdateListener)
  {
    Vector localVector = (Vector)this.mRecListeners.get(paramString);
    if (localVector != null)
      return localVector.remove(paramKXDataUpdateListener);
    return false;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.pengpeng.data.KXDataManager
 * JD-Core Version:    0.6.0
 */