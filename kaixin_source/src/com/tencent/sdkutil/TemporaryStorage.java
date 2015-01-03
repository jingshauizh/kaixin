package com.tencent.sdkutil;

import android.util.SparseArray;
import com.tencent.tauth.IUiListener;
import java.util.HashMap;
import java.util.UUID;

public class TemporaryStorage
{
  private static HashMap<String, Object> hashMap = new HashMap();
  public static int mNextRequestCode = 5656;
  private static SparseArray<IUiListener> mRecords = new SparseArray();

  public static Object get(String paramString)
  {
    return hashMap.remove(paramString);
  }

  public static String getId()
  {
    return UUID.randomUUID().toString();
  }

  public static IUiListener getListener(int paramInt)
  {
    IUiListener localIUiListener = (IUiListener)mRecords.get(paramInt);
    mRecords.remove(paramInt);
    return localIUiListener;
  }

  public static int nextRequestCode()
  {
    return mNextRequestCode;
  }

  public static Object set(String paramString, Object paramObject)
  {
    return hashMap.put(paramString, paramObject);
  }

  public static void setListener(IUiListener paramIUiListener)
  {
    mNextRequestCode = 1 + mNextRequestCode;
    if (mNextRequestCode == 6656)
      mNextRequestCode = 5656;
    mRecords.put(mNextRequestCode, paramIUiListener);
  }

  public static void setListener(IUiListener paramIUiListener, int paramInt)
  {
    mRecords.put(paramInt, paramIUiListener);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.sdkutil.TemporaryStorage
 * JD-Core Version:    0.6.0
 */