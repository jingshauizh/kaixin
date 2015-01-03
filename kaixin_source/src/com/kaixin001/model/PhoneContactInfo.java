package com.kaixin001.model;

public class PhoneContactInfo
{
  private String contactName;
  private String contactNamePinyin;
  protected String[] fpy;
  private long id;
  private Boolean isChecked;
  protected String[] py;
  private String userNumber;

  public String getContactName()
  {
    return this.contactName;
  }

  public String getContactNamePinyin()
  {
    return this.contactNamePinyin;
  }

  public String[] getFpy()
  {
    return this.fpy;
  }

  public long getId()
  {
    return this.id;
  }

  public Boolean getIsChecked()
  {
    return this.isChecked;
  }

  public String[] getPy()
  {
    return this.py;
  }

  public String getUserNumber()
  {
    return this.userNumber;
  }

  public void setContactName(String paramString)
  {
    this.contactName = paramString;
  }

  public void setContactNamePinyin(String paramString)
  {
    this.contactNamePinyin = paramString;
  }

  public void setFpy(String[] paramArrayOfString)
  {
    this.fpy = paramArrayOfString;
  }

  public void setId(long paramLong)
  {
    this.id = paramLong;
  }

  public void setIsChecked(Boolean paramBoolean)
  {
    this.isChecked = paramBoolean;
  }

  public void setPy(String[] paramArrayOfString)
  {
    this.py = paramArrayOfString;
  }

  public void setUserNumber(String paramString)
  {
    this.userNumber = paramString;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.PhoneContactInfo
 * JD-Core Version:    0.6.0
 */