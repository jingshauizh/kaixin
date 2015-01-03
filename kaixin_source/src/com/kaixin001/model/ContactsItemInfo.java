package com.kaixin001.model;

public class ContactsItemInfo
{
  private long mCid;
  private String mCname;
  private FriendsModel.Friend mFriend;
  private boolean mIsAdd;
  private boolean mIsLinkDialog;

  public ContactsItemInfo()
  {
  }

  public ContactsItemInfo(FriendsModel.Friend paramFriend, boolean paramBoolean, String paramString, long paramLong)
  {
    this.mFriend = paramFriend;
    this.mIsAdd = paramBoolean;
    this.mCname = paramString;
    this.mCid = paramLong;
  }

  public long getCid()
  {
    return this.mCid;
  }

  public String getCname()
  {
    return this.mCname;
  }

  public FriendsModel.Friend getFriend()
  {
    return this.mFriend;
  }

  public boolean isAdd()
  {
    return this.mIsAdd;
  }

  public boolean isLinkDialog()
  {
    return this.mIsLinkDialog;
  }

  public void setAdd(boolean paramBoolean)
  {
    this.mIsAdd = paramBoolean;
  }

  public void setCid(long paramLong)
  {
    this.mCid = paramLong;
  }

  public void setCname(String paramString)
  {
    this.mCname = paramString;
  }

  public void setFriend(FriendsModel.Friend paramFriend)
  {
    this.mFriend = paramFriend;
  }

  public void setLinkDialog(boolean paramBoolean)
  {
    this.mIsLinkDialog = paramBoolean;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.ContactsItemInfo
 * JD-Core Version:    0.6.0
 */