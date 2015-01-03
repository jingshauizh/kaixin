package com.kaixin001.item;

import java.util.HashMap;

public class HoroscopeDataProvider
{
  private static HashMap<String, Integer> titleIconIDMap;
  private static HashMap<String, String> titleTimeMap = new HashMap();

  static
  {
    titleIconIDMap = new HashMap();
    titleTimeMap.put("摩羯座", "12/22-01/19");
    titleTimeMap.put("水瓶座", "01/20-02/18");
    titleTimeMap.put("双鱼座", "02/19-03/20");
    titleTimeMap.put("白羊座", "03/21-04/19");
    titleTimeMap.put("金牛座", "04/20-05/20");
    titleTimeMap.put("双子座", "05/21-06/21");
    titleTimeMap.put("巨蟹座", "06/22-07/22");
    titleTimeMap.put("狮子座", "07/23-08/22");
    titleTimeMap.put("处女座", "08/23-09/22");
    titleTimeMap.put("天秤座", "09/23-10/23");
    titleTimeMap.put("天蝎座", "10/24-11/22");
    titleTimeMap.put("射手座", "11/23-12/21");
    titleIconIDMap.put("摩羯座", Integer.valueOf(2130838358));
    titleIconIDMap.put("水瓶座", Integer.valueOf(2130838364));
    titleIconIDMap.put("双鱼座", Integer.valueOf(2130838363));
    titleIconIDMap.put("白羊座", Integer.valueOf(2130838349));
    titleIconIDMap.put("金牛座", Integer.valueOf(2130838356));
    titleIconIDMap.put("双子座", Integer.valueOf(2130838370));
    titleIconIDMap.put("巨蟹座", Integer.valueOf(2130838357));
    titleIconIDMap.put("狮子座", Integer.valueOf(2130838362));
    titleIconIDMap.put("处女座", Integer.valueOf(2130838350));
    titleIconIDMap.put("天秤座", Integer.valueOf(2130838368));
    titleIconIDMap.put("天蝎座", Integer.valueOf(2130838369));
    titleIconIDMap.put("射手座", Integer.valueOf(2130838361));
  }

  public static Integer getHoroscopeIconID(String paramString)
  {
    return (Integer)titleIconIDMap.get(paramString);
  }

  public static String getHoroscopeTime(String paramString)
  {
    return (String)titleTimeMap.get(paramString);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.item.HoroscopeDataProvider
 * JD-Core Version:    0.6.0
 */