package com.tencent.qqconnect.dataprovider.datatype;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class TextOnly
  implements Parcelable
{
  public static final Parcelable.Creator<TextOnly> CREATOR = new TextOnly.1();
  private String mText;

  private TextOnly(Parcel paramParcel)
  {
    this.mText = paramParcel.readString();
  }

  public TextOnly(String paramString)
  {
    this.mText = paramString;
  }

  public int describeContents()
  {
    return 0;
  }

  public String getText()
  {
    return this.mText;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.mText);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.qqconnect.dataprovider.datatype.TextOnly
 * JD-Core Version:    0.6.0
 */