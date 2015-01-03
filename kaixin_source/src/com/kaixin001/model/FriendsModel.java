package com.kaixin001.model;

import android.text.TextUtils;
import com.kaixin001.item.KXItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class FriendsModel extends KXModel
{
  public static final String FLAG_ONLINE = "1";
  public static final String FLAG_ONLINE_MOBILE = "2";
  public static final int NUM = 5000;
  protected static final String UPDATE_TO_ZERO = "0";
  private static FriendsModel sInstance = null;
  private final ArrayList<Friend> birthFriends = new ArrayList();
  private String btotal = "0";
  private final ArrayList<Friend> focusFriends = new ArrayList();
  private final ArrayList<Friend> friends = new ArrayList();
  private String ftotal = "0";
  private String n = "0";
  private final ArrayList<Friend> onlines = new ArrayList();
  private String onlinetotal = "0";
  private int selectedIndex = 0;
  private final ArrayList<Friend> starFriends = new ArrayList();
  private String stotal = "0";
  private String total = "0";
  private String uid = "0";
  private final ArrayList<Friend> visitors = new ArrayList();
  private String vtotal = "0";

  public static FriendsModel getInstance()
  {
    monitorenter;
    try
    {
      if (sInstance == null)
        sInstance = new FriendsModel();
      FriendsModel localFriendsModel = sInstance;
      return localFriendsModel;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private Friend searchFriendList(String paramString, ArrayList<Friend> paramArrayList)
  {
    if ((TextUtils.isEmpty(paramString)) || (paramArrayList == null) || (paramArrayList.size() == 0))
      return null;
    Iterator localIterator = paramArrayList.iterator();
    Friend localFriend;
    do
    {
      if (!localIterator.hasNext())
        return null;
      localFriend = (Friend)localIterator.next();
    }
    while ((localFriend == null) || (!paramString.equals(localFriend.fuid)));
    return localFriend;
  }

  private ArrayList<Friend> searchListData(ArrayList<Friend> paramArrayList, String paramString)
  {
    int i = paramArrayList.size();
    ArrayList localArrayList = new ArrayList();
    int j = 0;
    while (true)
    {
      if (j >= i)
        return localArrayList;
      try
      {
        Friend localFriend = (Friend)paramArrayList.get(j);
        if (localFriend.isMatch(paramString))
          localArrayList.add(localFriend);
        j++;
      }
      catch (Exception localException)
      {
      }
    }
    return localArrayList;
  }

  public void addOnline(String paramString)
  {
    if (searchOnlineFriends(paramString) == null)
    {
      Friend localFriend = searchAllFriends(paramString);
      if (localFriend != null)
      {
        localFriend.online = "1";
        this.onlines.add(localFriend);
      }
    }
  }

  public void clear()
  {
    this.uid = "0";
    this.n = "0";
    this.total = "0";
    this.onlinetotal = "0";
    this.vtotal = "0";
    this.stotal = "0";
    this.selectedIndex = 0;
    this.friends.clear();
    this.visitors.clear();
    this.onlines.clear();
    this.starFriends.clear();
    this.focusFriends.clear();
    this.birthFriends.clear();
  }

  public int friendPYCompare(String paramString1, String paramString2)
  {
    int i = 1;
    if ((paramString1 == null) && (paramString2 == null))
      i = 0;
    do
    {
      return i;
      if ((paramString1 == null) && (paramString2 != null))
        return -1;
    }
    while (((paramString1 != null) && (paramString2 == null)) || (paramString1 == null) || (paramString2 == null));
    return paramString1.compareTo(paramString2);
  }

  public ArrayList<Friend> getBirthFriends()
  {
    return this.birthFriends;
  }

  public String getBtotal()
  {
    return this.btotal;
  }

  public ArrayList<Friend> getFocusFriends()
  {
    return this.focusFriends;
  }

  public ArrayList<Friend> getFriends()
  {
    return this.friends;
  }

  public String getFtotal()
  {
    return this.ftotal;
  }

  public String getN()
  {
    return this.n;
  }

  public ArrayList<Friend> getOnlines()
  {
    return this.onlines;
  }

  public String getOnlinetotal()
  {
    return this.onlinetotal;
  }

  public int getSelectedIndex()
  {
    return this.selectedIndex;
  }

  public ArrayList<Friend> getStarFriends()
  {
    return this.starFriends;
  }

  public String getStotal()
  {
    return this.stotal;
  }

  public String getTotal()
  {
    return this.total;
  }

  public String getUid()
  {
    return this.uid;
  }

  public ArrayList<Friend> getVisitors()
  {
    return this.visitors;
  }

  public String getVtotal()
  {
    return this.vtotal;
  }

  public void removeOnline(String paramString)
  {
    Friend localFriend = searchOnlineFriends(paramString);
    if (localFriend != null)
    {
      localFriend.online = "0";
      this.onlines.remove(localFriend);
    }
  }

  public Friend searchAllFriends(String paramString)
  {
    return searchFriendList(paramString, this.friends);
  }

  public ArrayList<Friend> searchAllFriendsByPinyin(String paramString)
  {
    return searchListData(this.friends, paramString);
  }

  public Friend searchOnlineFriends(String paramString)
  {
    return searchFriendList(paramString, this.onlines);
  }

  public ArrayList<Friend> searchOnlineFriendsByPinyin(String paramString)
  {
    return searchListData(this.onlines, paramString);
  }

  public Friend searchStars(String paramString)
  {
    return searchFriendList(paramString, this.starFriends);
  }

  public ArrayList<Friend> searchStarsByPinyin(String paramString)
  {
    return searchListData(this.starFriends, paramString);
  }

  public void setBirthFriends(ArrayList<Friend> paramArrayList)
  {
    this.birthFriends.clear();
    if (paramArrayList != null)
      this.birthFriends.addAll(paramArrayList);
  }

  public void setBtotal(String paramString)
  {
    this.btotal = paramString;
  }

  public void setFocusFriends(ArrayList<Friend> paramArrayList)
  {
    this.focusFriends.clear();
    if (paramArrayList != null)
      this.focusFriends.addAll(paramArrayList);
  }

  public void setFriends(ArrayList<Friend> paramArrayList)
  {
    this.friends.clear();
    if (paramArrayList != null)
      this.friends.addAll(paramArrayList);
  }

  public void setFtotal(String paramString)
  {
    this.ftotal = paramString;
  }

  public void setN(String paramString)
  {
    this.n = paramString;
  }

  public void setOnlines(ArrayList<Friend> paramArrayList)
  {
    this.onlines.clear();
    if (paramArrayList != null)
      this.onlines.addAll(paramArrayList);
  }

  public void setOnlinetotal(String paramString)
  {
    this.onlinetotal = paramString;
  }

  public void setSelectedIndex(int paramInt)
  {
    this.selectedIndex = paramInt;
  }

  public void setStarFriends(ArrayList<Friend> paramArrayList)
  {
    this.starFriends.clear();
    if (paramArrayList != null)
      this.starFriends.addAll(paramArrayList);
  }

  public void setStotal(String paramString)
  {
    this.stotal = paramString;
  }

  public void setTotal(String paramString)
  {
    this.total = paramString;
  }

  public void setUid(String paramString)
  {
    this.uid = paramString;
  }

  public void setVisitors(ArrayList<Friend> paramArrayList)
  {
    this.visitors.clear();
    if (paramArrayList != null)
      this.visitors.addAll(paramArrayList);
  }

  public void setVtotal(String paramString)
  {
    this.vtotal = paramString;
  }

  public void sortBasePy(ArrayList<Friend> paramArrayList)
  {
    Collections.sort(paramArrayList, new Comparator()
    {
      public int compare(FriendsModel.Friend paramFriend1, FriendsModel.Friend paramFriend2)
      {
        if ((paramFriend1 == null) && (paramFriend2 == null))
          return 0;
        if ((paramFriend1 == null) && (paramFriend2 != null))
          return -1;
        if ((paramFriend1 != null) && (paramFriend2 == null))
          return 1;
        if ((paramFriend1 != null) && (paramFriend2 != null))
        {
          String[] arrayOfString1 = paramFriend1.getPy();
          String[] arrayOfString2 = paramFriend2.getPy();
          if ((arrayOfString1.length > 2) && (arrayOfString2.length > 2))
            return FriendsModel.this.friendPYCompare(arrayOfString1[2], arrayOfString2[2]);
          return FriendsModel.this.friendPYCompare(arrayOfString1[0], arrayOfString2[0]);
        }
        return 0;
      }
    });
  }

  public void updateOnlineFlag()
  {
    if ((this.friends == null) || (this.onlines == null))
      return;
    int i = this.onlines.size();
    int j = this.friends.size();
    int k = 0;
    label33: int m;
    label41: Friend localFriend1;
    String str;
    int i1;
    if (k >= j)
    {
      m = 0;
      if (m < i)
      {
        localFriend1 = (Friend)this.onlines.get(m);
        str = localFriend1.getFuid();
        i1 = this.friends.size();
      }
    }
    label155: for (int i2 = 0; ; i2++)
    {
      if (i2 >= i1);
      while (true)
      {
        m++;
        break label41;
        break;
        ((Friend)this.friends.get(k)).setOnline("0");
        k++;
        break label33;
        Friend localFriend2 = (Friend)this.friends.get(i2);
        if (str.compareTo(localFriend2.getFuid()) != 0)
          break label155;
        localFriend2.setOnline(localFriend1.getOnline());
      }
    }
  }

  public class Friend extends KXItem
  {
    public static final String NO_SEARCH_DATA = "search_data_null";
    protected String birthDisplay;
    private ArrayList<Friend> birthFriends;
    protected String flogo;
    protected String fname;
    private String footer;
    protected String[] fpy;
    protected String fuid;
    protected String gender;
    protected String isFriend;
    protected String isSendBirthGift;
    private String noSearchFriend;
    protected String online;
    protected String[] py;
    private String searchTxt = null;
    protected String state;
    protected String strvtime;
    protected String vtime;

    public Friend()
    {
    }

    public Friend(Friend arg2)
    {
      Object localObject;
      this.fuid = localObject.fuid;
      this.fname = localObject.fname;
      this.gender = localObject.gender;
      this.state = localObject.state;
      this.online = localObject.online;
      this.py = localObject.py;
      this.fpy = localObject.fpy;
      this.flogo = localObject.flogo;
    }

    private boolean someOneMatch(String[] paramArrayOfString, String paramString)
    {
      int i;
      if (paramArrayOfString != null)
        i = paramArrayOfString.length;
      for (int j = 0; ; j++)
      {
        if (j >= i)
          return false;
        String str = paramArrayOfString[j];
        if ((str != null) && (str.startsWith(paramString)))
          return true;
      }
    }

    public String getBirthDisplay()
    {
      return this.birthDisplay;
    }

    public ArrayList<Friend> getBirthFriends()
    {
      return this.birthFriends;
    }

    public String getFlogo()
    {
      return this.flogo;
    }

    public String getFname()
    {
      return this.fname;
    }

    public String getFooter()
    {
      return this.footer;
    }

    public String[] getFpy()
    {
      return this.fpy;
    }

    public String getFuid()
    {
      return this.fuid;
    }

    public String getGender()
    {
      return this.gender;
    }

    public String getIsFriend()
    {
      return this.isFriend;
    }

    public String getIsSendBirthGift()
    {
      return this.isSendBirthGift;
    }

    public String getOnline()
    {
      return this.online;
    }

    public String[] getPy()
    {
      return this.py;
    }

    public String getSearchFriend()
    {
      return this.noSearchFriend;
    }

    public String getSearchTxt()
    {
      return this.searchTxt;
    }

    public String getState()
    {
      return this.state;
    }

    public String getStrvtime()
    {
      return this.strvtime;
    }

    public String getVtime()
    {
      return this.vtime;
    }

    public boolean isMatch(String paramString)
    {
      String str = getFname();
      boolean bool1 = TextUtils.isEmpty(paramString);
      int i = 0;
      if (!bool1)
      {
        boolean bool2 = str.startsWith(paramString);
        i = 0;
        if (bool2)
          i = 1;
      }
      return (i != 0) || (someOneMatch(getFpy(), paramString)) || (someOneMatch(getPy(), paramString));
    }

    public boolean isOnline()
    {
      return !"0".equals(this.online);
    }

    public void setBirthDisplay(String paramString)
    {
      this.birthDisplay = paramString;
    }

    public void setBirthFriends(ArrayList<Friend> paramArrayList)
    {
      this.birthFriends = paramArrayList;
    }

    public void setFlogo(String paramString)
    {
      this.flogo = paramString;
    }

    public void setFname(String paramString)
    {
      this.fname = paramString;
    }

    public void setFooter(String paramString)
    {
      this.footer = paramString;
    }

    public void setFpy(String[] paramArrayOfString)
    {
      this.fpy = paramArrayOfString;
    }

    public void setFuid(String paramString)
    {
      this.fuid = paramString;
    }

    public void setGender(String paramString)
    {
      this.gender = paramString;
    }

    public void setIsFriend(String paramString)
    {
      this.isFriend = paramString;
    }

    public void setIsSendBirthGift(String paramString)
    {
      this.isSendBirthGift = paramString;
    }

    public void setOnline(String paramString)
    {
      this.online = paramString;
    }

    public void setPy(String[] paramArrayOfString)
    {
      this.py = paramArrayOfString;
    }

    public void setSearchFriend(String paramString)
    {
      this.noSearchFriend = paramString;
    }

    public void setSearchTxt(String paramString)
    {
      this.searchTxt = paramString;
    }

    public void setState(String paramString)
    {
      this.state = paramString;
    }

    public void setStrvtime(String paramString)
    {
      this.strvtime = paramString;
    }

    public void setVtime(String paramString)
    {
      this.vtime = paramString;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.FriendsModel
 * JD-Core Version:    0.6.0
 */