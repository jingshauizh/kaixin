package com.kaixin001.util;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.activity.KXApplication;
import java.util.ArrayList;
import java.util.Iterator;

public class PinYinUtils
{
  private static final int CHINESE_END = 40891;
  private static final int CHINESE_START = 19968;
  private static PinYinUtils instance = null;
  private KaixinPinyin def = null;

  private PinYinUtils(Context paramContext)
  {
    this.def = new KaixinPinyin(paramContext.getApplicationContext());
  }

  private void addToArray(ArrayList<String> paramArrayList, String[] paramArrayOfString, boolean paramBoolean)
  {
    if (paramArrayList.size() == 0)
      paramArrayList.add("");
    ArrayList localArrayList = new ArrayList();
    localArrayList.addAll(paramArrayList);
    paramArrayList.clear();
    Iterator localIterator;
    for (int i = 0; ; i++)
    {
      if (i >= paramArrayOfString.length)
        return;
      localIterator = localArrayList.iterator();
      if (localIterator.hasNext())
        break;
    }
    String str1 = (String)localIterator.next();
    if (paramBoolean);
    for (String str2 = str1 + paramArrayOfString[i].charAt(0); ; str2 = str1 + paramArrayOfString[i])
    {
      paramArrayList.add(str2);
      break;
    }
  }

  private String arrayToString(ArrayList<String> paramArrayList)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    int i;
    if (paramArrayList == null)
      i = 0;
    for (int j = 0; ; j++)
    {
      if (j >= i)
      {
        return localStringBuffer.toString();
        i = paramArrayList.size();
        break;
      }
      localStringBuffer.append((String)paramArrayList.get(j));
      if (j >= i - 1)
        continue;
      localStringBuffer.append(",");
    }
  }

  private String formatChinese(String paramString)
  {
    if (paramString == null)
      return null;
    StringBuilder localStringBuilder = new StringBuilder();
    int i = 0;
    if (i >= paramString.length())
      return localStringBuilder.toString();
    int j = paramString.charAt(i);
    if ((j < 19968) || (j > 40891));
    while (true)
    {
      i++;
      break;
      localStringBuilder.append(j);
    }
  }

  public static PinYinUtils getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new PinYinUtils(KXApplication.getInstance().getApplicationContext());
      PinYinUtils localPinYinUtils = instance;
      return localPinYinUtils;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public String getPinYinString(String paramString)
  {
    String str = formatChinese(paramString);
    String[] arrayOfString = new String[6];
    int i = 0;
    boolean bool2;
    String[][] arrayOfString;;
    int m;
    label52: label59: ArrayList localArrayList1;
    ArrayList localArrayList2;
    ArrayList localArrayList3;
    ArrayList localArrayList4;
    int n;
    label98: label105: StringBuilder localStringBuilder;
    if (i >= arrayOfString.length)
    {
      boolean bool1 = TextUtils.isEmpty(str);
      bool2 = false;
      if (!bool1)
      {
        int k = str.length();
        arrayOfString; = new String[k][];
        m = 0;
        if (m < k)
          break label238;
        localArrayList1 = new ArrayList();
        localArrayList2 = new ArrayList();
        localArrayList3 = new ArrayList();
        localArrayList4 = new ArrayList();
        n = 0;
        if (n < k)
          break label290;
        arrayOfString[0] = arrayToString(localArrayList3);
        arrayOfString[2] = arrayToString(localArrayList1);
        if (!bool2)
          break label366;
        arrayOfString[1] = arrayToString(localArrayList4);
        arrayOfString[3] = arrayToString(localArrayList2);
        label146: localArrayList1.clear();
        localArrayList2.clear();
        localArrayList3.clear();
        localArrayList4.clear();
      }
      arrayOfString[4] = paramString.replaceAll(" ", "");
      if ((!bool2) || (TextUtils.isEmpty(str)))
        break label381;
      arrayOfString[5] = str.substring(1, str.length());
      label201: localStringBuilder = new StringBuilder();
    }
    for (int j = 0; ; j++)
    {
      if (j >= arrayOfString.length)
      {
        return localStringBuilder.toString();
        arrayOfString[i] = "";
        i++;
        break;
        label238: arrayOfString;[m] = this.def.get_pinyins(str.charAt(m));
        if (m == 0)
          bool2 = this.def.is_baijiaxing_for_char(str.charAt(m));
        if (arrayOfString;[m] == null)
          break label59;
        m++;
        break label52;
        label290: if ((arrayOfString;[n] == null) || (arrayOfString;[n].length == 0))
          break label105;
        addToArray(localArrayList1, arrayOfString;[n], false);
        addToArray(localArrayList3, arrayOfString;[n], true);
        if (n > 0)
        {
          addToArray(localArrayList2, arrayOfString;[n], false);
          addToArray(localArrayList4, arrayOfString;[n], true);
        }
        n++;
        break label98;
        label366: arrayOfString[1] = arrayOfString[0];
        arrayOfString[3] = arrayOfString[2];
        break label146;
        label381: arrayOfString[5] = arrayOfString[4].toLowerCase();
        break label201;
      }
      if (TextUtils.isEmpty(arrayOfString[j]))
        continue;
      localStringBuilder.append(arrayOfString[j]);
      if (j >= -1 + arrayOfString.length)
        continue;
      localStringBuilder.append("|");
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.util.PinYinUtils
 * JD-Core Version:    0.6.0
 */