package com.umeng.analytics;

public enum Gender
{
  static
  {
    Female = new Gender("Female", 1);
    Unknown = new Gender("Unknown", 2);
    Gender[] arrayOfGender = new Gender[3];
    arrayOfGender[0] = Male;
    arrayOfGender[1] = Female;
    arrayOfGender[2] = Unknown;
    a = arrayOfGender;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.umeng.analytics.Gender
 * JD-Core Version:    0.6.0
 */