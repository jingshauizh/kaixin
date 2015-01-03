package com.tencent.mm.sdk.platformtools;

final class CharSequences$1
  implements CharSequence
{
  public final char charAt(int paramInt)
  {
    return (char)this.u[paramInt];
  }

  public final int length()
  {
    return this.u.length;
  }

  public final CharSequence subSequence(int paramInt1, int paramInt2)
  {
    return CharSequences.forAsciiBytes(this.u, paramInt1, paramInt2);
  }

  public final String toString()
  {
    return new String(this.u);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.platformtools.CharSequences.1
 * JD-Core Version:    0.6.0
 */