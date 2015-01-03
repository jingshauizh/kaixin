package com.tencent.mm.sdk.platformtools;

public class CharSequences
{
  static void a(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt1 < 0)
      throw new IndexOutOfBoundsException();
    if (paramInt2 < 0)
      throw new IndexOutOfBoundsException();
    if (paramInt2 > paramInt3)
      throw new IndexOutOfBoundsException();
    if (paramInt1 > paramInt2)
      throw new IndexOutOfBoundsException();
  }

  public static int compareToIgnoreCase(CharSequence paramCharSequence1, CharSequence paramCharSequence2)
  {
    int i = 0;
    int j = paramCharSequence1.length();
    int k = paramCharSequence2.length();
    int m;
    if (j < k)
      m = j;
    int i1;
    for (int n = 0; ; n = i1)
    {
      int i3;
      if (n < m)
      {
        i1 = n + 1;
        int i2 = Character.toLowerCase(paramCharSequence1.charAt(n));
        i3 = i + 1;
        int i4 = i2 - Character.toLowerCase(paramCharSequence2.charAt(i));
        if (i4 != 0)
        {
          return i4;
          m = k;
          break;
        }
      }
      else
      {
        return j - k;
      }
      i = i3;
    }
  }

  public static boolean equals(CharSequence paramCharSequence1, CharSequence paramCharSequence2)
  {
    if (paramCharSequence1.length() != paramCharSequence2.length())
      return false;
    int i = paramCharSequence1.length();
    for (int j = 0; ; j++)
    {
      if (j >= i)
        break label54;
      if (paramCharSequence1.charAt(j) != paramCharSequence2.charAt(j))
        break;
    }
    label54: return true;
  }

  public static CharSequence forAsciiBytes(byte[] paramArrayOfByte)
  {
    return new CharSequences.1(paramArrayOfByte);
  }

  public static CharSequence forAsciiBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    a(paramInt1, paramInt2, paramArrayOfByte.length);
    return new CharSequences.2(paramArrayOfByte, paramInt1, paramInt2);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.platformtools.CharSequences
 * JD-Core Version:    0.6.0
 */