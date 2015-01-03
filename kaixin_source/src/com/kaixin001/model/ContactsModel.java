package com.kaixin001.model;

import java.util.ArrayList;

public class ContactsModel
{
  public static final int CONTACTS_LINKED_FLAG = 11;
  public static final int CONTACTS_UNLINKED_FLAG = 12;
  private static ContactsModel instance;
  private int mActiveLinkType = 11;
  private ArrayList<ContactsItemInfo> mLinkedFriendList;
  private ArrayList<ContactsItemInfo> mUnLinkedFriendList;

  public static ContactsModel getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new ContactsModel();
      ContactsModel localContactsModel = instance;
      return localContactsModel;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public static void setInstance(ContactsModel paramContactsModel)
  {
    instance = paramContactsModel;
  }

  public void clear()
  {
    this.mLinkedFriendList = null;
    this.mUnLinkedFriendList = null;
    this.mActiveLinkType = 11;
  }

  public int getActiveLinkType()
  {
    return this.mActiveLinkType;
  }

  public ArrayList<ContactsItemInfo> getLinkedFriendList()
  {
    return this.mLinkedFriendList;
  }

  public ArrayList<ContactsItemInfo> getUnLinkedFriendList()
  {
    return this.mUnLinkedFriendList;
  }

  public void setActiveLinkType(int paramInt)
  {
    this.mActiveLinkType = paramInt;
  }

  public void setLinkedFriendList(ArrayList<ContactsItemInfo> paramArrayList)
  {
    this.mLinkedFriendList = paramArrayList;
  }

  public void setUnLinkedFriendList(ArrayList<ContactsItemInfo> paramArrayList)
  {
    this.mUnLinkedFriendList = paramArrayList;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.ContactsModel
 * JD-Core Version:    0.6.0
 */