package com.tencent.qqconnect.dataprovider.datatype;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class TextOnly$1
  implements Parcelable.Creator<TextOnly>
{
  public TextOnly createFromParcel(Parcel paramParcel)
  {
    return new TextOnly(paramParcel, null);
  }

  public TextOnly[] newArray(int paramInt)
  {
    return new TextOnly[paramInt];
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.qqconnect.dataprovider.datatype.TextOnly.1
 * JD-Core Version:    0.6.0
 */