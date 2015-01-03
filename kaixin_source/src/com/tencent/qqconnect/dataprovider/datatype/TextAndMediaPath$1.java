package com.tencent.qqconnect.dataprovider.datatype;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class TextAndMediaPath$1
  implements Parcelable.Creator<TextAndMediaPath>
{
  public TextAndMediaPath createFromParcel(Parcel paramParcel)
  {
    return new TextAndMediaPath(paramParcel, null);
  }

  public TextAndMediaPath[] newArray(int paramInt)
  {
    return new TextAndMediaPath[paramInt];
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.qqconnect.dataprovider.datatype.TextAndMediaPath.1
 * JD-Core Version:    0.6.0
 */