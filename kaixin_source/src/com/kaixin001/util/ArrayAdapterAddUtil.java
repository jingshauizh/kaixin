package com.kaixin001.util;

import com.kaixin001.fragment.HoroscopeFragment.HoroscopeDataAdapter;
import java.util.ArrayList;
import org.apache.http.message.BasicNameValuePair;

public class ArrayAdapterAddUtil
{
  public static void addAll(HoroscopeFragment.HoroscopeDataAdapter paramHoroscopeDataAdapter, ArrayList<BasicNameValuePair> paramArrayList)
  {
    for (int i = 0; ; i++)
    {
      if (i >= paramArrayList.size())
        return;
      paramHoroscopeDataAdapter.add((BasicNameValuePair)paramArrayList.get(i));
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.util.ArrayAdapterAddUtil
 * JD-Core Version:    0.6.0
 */