package com.tencent.mm.platformtools;

public class CnToSpell
{
  public static String getFullSpell(String paramString)
  {
    if ((paramString == null) || ("".equals(paramString.trim())))
      return paramString;
    char[] arrayOfChar = paramString.toCharArray();
    StringBuffer localStringBuffer = new StringBuffer();
    int i = 0;
    int j = arrayOfChar.length;
    while (i < j)
    {
      String str = SpellMap.getSpell(arrayOfChar[i]);
      if (str != null)
        localStringBuffer.append(str);
      i++;
    }
    return localStringBuffer.toString();
  }

  public static String getInitial(String paramString)
  {
    if ((paramString == null) || ("".equals(paramString.trim())))
      return paramString;
    char[] arrayOfChar = paramString.toCharArray();
    StringBuffer localStringBuffer = new StringBuffer();
    int i = arrayOfChar.length;
    for (int j = 0; j < i; j++)
    {
      if (Character.isSpace(arrayOfChar[j]))
        continue;
      String str = SpellMap.getSpell(arrayOfChar[j]);
      if ((str == null) || (str.length() <= 0))
        continue;
      localStringBuffer.append(str.charAt(0));
    }
    return localStringBuffer.toString().toUpperCase();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.platformtools.CnToSpell
 * JD-Core Version:    0.6.0
 */