package com.tencent.mm.sdk.platformtools;

final class CharSequences$2
  implements CharSequence
{
  public final char charAt(int paramInt)
  {
    return (char)this.u[(paramInt + this.v)];
  }

  public final int length()
  {
    return this.w - this.v;
  }

  public final CharSequence subSequence(int paramInt1, int paramInt2)
  {
    int i = paramInt1 - this.v;
    int j = paramInt2 - this.v;
    CharSequences.a(i, j, length());
    return CharSequences.forAsciiBytes(this.u, i, j);
  }

  public final String toString()
  {
    return new String(this.u, this.v, length());
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.platformtools.CharSequences.2
 * JD-Core Version:    0.6.0
 */