package com.kaixin001.item;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class UserCard
  implements Parcelable
{
  public static final Parcelable.Creator<UserCard> CREATOR = new Parcelable.Creator()
  {
    public UserCard createFromParcel(Parcel paramParcel)
    {
      return new UserCard(paramParcel, null);
    }

    public UserCard[] newArray(int paramInt)
    {
      return new UserCard[paramInt];
    }
  };
  public static final String FROM_CARD_TABLE = "cardtable";
  public static final String FROM_KX = "kx";
  public static final int PRI_MASK_CITY = 4;
  public static final int PRI_MASK_EMAIL = 128;
  public static final int PRI_MASK_INC = 8;
  public static final int PRI_MASK_MOBILE = 64;
  public static final int PRI_MASK_PHONE = 32;
  public static final int PRI_MASK_POST = 16;
  public static final int TYPE_EMAIL_ID = 3;
  public static final int TYPE_MOBILE_ID = 2;
  public static final int TYPE_PHONE_ID = 1;
  public String city;
  public String company;
  public String email;
  public String from;
  public String logo;
  public String mobile;
  public String name;
  public String phone;
  public String post;
  public int privacy;

  public UserCard()
  {
  }

  private UserCard(Parcel paramParcel)
  {
    this.name = paramParcel.readString();
    this.logo = paramParcel.readString();
    this.city = paramParcel.readString();
    this.company = paramParcel.readString();
    this.post = paramParcel.readString();
    this.phone = paramParcel.readString();
    this.mobile = paramParcel.readString();
    this.email = paramParcel.readString();
    this.from = paramParcel.readString();
    this.privacy = paramParcel.readInt();
  }

  public void changePrivacy(int paramInt, boolean paramBoolean)
  {
    switch (paramInt)
    {
    default:
      return;
    case 3:
      if (paramBoolean)
      {
        this.privacy = (0x80 | this.privacy);
        return;
      }
      this.privacy = (0xFFFFFF7F & this.privacy);
      return;
    case 2:
      if (paramBoolean)
      {
        this.privacy = (0x40 | this.privacy);
        return;
      }
      this.privacy = (0xFFFFFFBF & this.privacy);
      return;
    case 1:
    }
    if (paramBoolean)
    {
      this.privacy = (0x20 | this.privacy);
      return;
    }
    this.privacy = (0xFFFFFFDF & this.privacy);
  }

  public int describeContents()
  {
    return 0;
  }

  public boolean hasBeenUpdatedCard()
  {
    return "cardtable".equalsIgnoreCase(this.from);
  }

  public boolean isCityHide()
  {
    return (0x4 & this.privacy) != 0;
  }

  public boolean isCompanyHide()
  {
    return (0x8 & this.privacy) != 0;
  }

  public boolean isEmailHide()
  {
    return (0x80 & this.privacy) != 0;
  }

  public boolean isMobileHide()
  {
    return (0x40 & this.privacy) != 0;
  }

  public boolean isPhoneHide()
  {
    return (0x20 & this.privacy) != 0;
  }

  public boolean isPostHide()
  {
    return (0x10 & this.privacy) != 0;
  }

  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(this.name).append("'s card>>").append("  [privacy]=").append(this.privacy).append("  [city]=").append(this.city).append("  [inc]=").append(this.company).append("  [post]=").append(this.post).append("  [phone]=").append(this.phone).append("  [mobile]=").append(this.mobile).append("  [email]=").append(this.email);
    return localStringBuffer.toString();
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.name);
    paramParcel.writeString(this.logo);
    paramParcel.writeString(this.city);
    paramParcel.writeString(this.company);
    paramParcel.writeString(this.post);
    paramParcel.writeString(this.phone);
    paramParcel.writeString(this.mobile);
    paramParcel.writeString(this.email);
    paramParcel.writeString(this.from);
    paramParcel.writeInt(this.privacy);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.item.UserCard
 * JD-Core Version:    0.6.0
 */