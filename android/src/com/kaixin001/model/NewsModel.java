package com.kaixin001.model;

import android.text.TextUtils;

import com.kaixin001.fragment.BaseFragment;
import com.kaixin001.item.KXLinkInfo;
import com.kaixin001.item.NewsInfo;
import com.kaixin001.util.ParseNewsInfoUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class NewsModel extends KXModel
{
  public static final int HAS_NEW_FLAG = 1;
  public static final String NEWS_ALL = "all";
  public static final String NEWS_STAR = "star";
  private static NewsModel instance;
  private static volatile boolean mHasNew = false;
  private static ArrayList<String> mHistory;
  private static HashMap<String, NewsModel> mModelMap = new HashMap();
  private static NewsModel myHomeModel;
  private NewsInfo activeNewsInfo;
  private String city = "";
  private String constellation = "";
  private String ctime;
  private String exp = "";
  private String exp_award = "";
  private String exp_to_upgrade = "";
  private int fDiaryNum = 0;
  private int fPhotoNum = 0;
  private int fRecordNum = 0;
  private int fRepostNum = 0;
  private String gender = "0";
  private volatile int iNewsCount = 0;
  private int iTotalNum = 0;
  private String indexprivacy = "";
  private boolean isFirstRefresh = true;
  private String ismyfriend = "";
  private String istar = "";
  private String large = "";
  private String level = "";
  private String logo = "";
  private String logo120 = "";
  private int mAllNewAppsCount = 0;
  private boolean mAppsHasNew = false;
  private String mCoverId;
  private String mCoverUrl;
  private int mFansCount = -1;
  private int mFriendNum;
  private int mNewAppsCount = 0;
  private int mNewGamesCount = 0;
  private String mNewPhotoUrl;
  private volatile int mPlazaHasNew = 0;
  private String middle = "";
  private int n = 0;
  public ArrayList<NewsInfo> newsListAll = new ArrayList();
  private String online = "";
  private String realname = "";
  private int refreshNum = 0;
  private String samefriends = "";
  private String small = "";
  private String starintro = "";
  private ArrayList<KXLinkInfo> stateList = null;
  private String status = "";
  private String statustime = "";
  private String title = "";
  private String updateTime = "";

  static
  {
    mHistory = new ArrayList();
  }

  public static void clearAllHomeModels()
  {
    mModelMap.clear();
    mHistory.clear();
  }

  public static void clearHomeModel(BaseFragment paramBaseFragment)
  {
    if (paramBaseFragment == null){
    	return;
    }
    String str1;
    String str2;
  
      
      Iterator localIterator = null;
      localIterator = mHistory.iterator();
      while (!localIterator.hasNext())
      {
        str1 = paramBaseFragment.toString();
        mModelMap.remove(str1);
       
      }
      str2 = (String)localIterator.next();
   
   
    mHistory.remove(str2);
  }

  public static boolean getHasNew()
  {
    return mHasNew;
  }

  public static NewsModel getHomeModel()
  {
  
   
      NewsModel localNewsModel = (NewsModel)mModelMap.get(mHistory.get(0));
     
      return localNewsModel;
  
   
   
  }

  public static NewsModel getHomeModel(BaseFragment paramBaseFragment)
  {
    
    NewsModel localNewsModel;
    if (paramBaseFragment == null)
    {
      localNewsModel = null;
     
      return localNewsModel;
    }
    while (true)
    {
      String str1;
      Iterator localIterator = null;
      try
      {
        str1 = paramBaseFragment.toString();
        localNewsModel = (NewsModel)mModelMap.get(str1);
        if (localNewsModel != null)
          continue;
        localNewsModel = new NewsModel();
        mModelMap.put(str1, localNewsModel);
        localIterator = mHistory.iterator();
        if (!localIterator.hasNext())
        {
          mHistory.add(0, str1);
          break;
        }
      }
      finally
      {
    	  String str2 = (String)localIterator.next();
         
            
          mHistory.remove(str2);
      }
     
    }
	return localNewsModel;
  }

  public static NewsModel getInstance()
  {
   
   
      if (instance == null)
        instance = new NewsModel();
      NewsModel localNewsModel = instance;
      return localNewsModel;
   
   
  }
  public static NewsModel getMyHomeModel()
  {
  
   
      if (myHomeModel == null)
        myHomeModel = new NewsModel();
      NewsModel localNewsModel = myHomeModel;
      return localNewsModel;
    
   
  }

  public static void setHasNew(boolean paramBoolean)
  {
    mHasNew = paramBoolean;
  }

  private static void setNoNewsFlag()
  {
    mHasNew = false;
  }

  public void clear()
  {
    this.n = 0;
    this.iTotalNum = 0;
    this.logo = "";
    this.logo120 = "";
    this.status = "";
    this.realname = "";
    this.statustime = "";
    this.online = "";
    this.indexprivacy = "";
    this.istar = "";
    this.ismyfriend = "";
    this.starintro = "";
    this.updateTime = "";
    this.stateList = null;
    this.isFirstRefresh = true;
    this.refreshNum = 0;
    setNoNewsFlag();
    this.fPhotoNum = 0;
    this.fDiaryNum = 0;
    this.fRecordNum = 0;
    this.fRepostNum = 0;
    if ((this.newsListAll != null) && (this.newsListAll.size() > 0))
      this.newsListAll.clear();
  }

  public NewsInfo getActiveItem()
  {
    return this.activeNewsInfo;
  }

  public int getAllNewAppsCount()
  {
    return this.mAllNewAppsCount;
  }

  public int getAllPhotoNum()
  {
    return this.fPhotoNum;
  }

  public String getConstellation()
  {
    return this.constellation;
  }

  public String getCoverId()
  {
    return this.mCoverId;
  }

  public String getCoverUrl()
  {
    return this.mCoverUrl;
  }

  public int getDiaryNum()
  {
    return this.fDiaryNum;
  }

  public String getExp()
  {
    return this.exp;
  }

  public String getExp_award()
  {
    return this.exp_award;
  }

  public String getExp_to_upgrade()
  {
    return this.exp_to_upgrade;
  }

  public int getFansCount()
  {
    return this.mFansCount;
  }

  public int getFriendNum()
  {
    return this.mFriendNum;
  }

  public String getGender()
  {
    return this.gender;
  }

  public String getHomeCity()
  {
    return this.city;
  }

  public String getIsmyfriend()
  {
    return this.ismyfriend;
  }

  public String getIstar()
  {
    return this.istar;
  }

  public String getLarge()
  {
    return this.large;
  }

  public int getLastNum()
  {
    return this.n;
  }

  public String getLatestCTime()
  {
    ArrayList localArrayList = getInstance().getNewsList();
    if ((localArrayList == null) || (localArrayList.size() == 0))
      return null;
    return ((NewsInfo)localArrayList.get(0)).mCtime;
  }

  public String getLevel()
  {
    return this.level;
  }

  public String getLogo()
  {
    return this.logo;
  }

  public String getLogo120()
  {
    return this.logo120;
  }

  public String getMiddle()
  {
    return this.middle;
  }

  public int getNewAppsCount()
  {
    return this.mNewAppsCount;
  }

  public int getNewGamesCount()
  {
    return this.mNewGamesCount;
  }

  public String getNewPhotoUrl()
  {
    return this.mNewPhotoUrl;
  }

  public int getNewsCount()
  {
    return this.iNewsCount;
  }

  public ArrayList<NewsInfo> getNewsList()
  {
    return this.newsListAll;
  }

  public String getOnline()
  {
    return this.online;
  }

  public String getPrivacy()
  {
    return this.indexprivacy;
  }

  public int getPublicMore()
  {
    return this.mPlazaHasNew;
  }

  public String getRealname()
  {
    return this.realname;
  }

  public int getRecordNum()
  {
    return this.fRecordNum;
  }

  public int getRefreshNum()
  {
    return this.refreshNum;
  }

  public int getRepostNum()
  {
    return this.fRepostNum;
  }

  public String getSameFriends()
  {
    return this.samefriends;
  }

  public String getSmall()
  {
    return this.small;
  }

  public String getStarintro()
  {
    return this.starintro;
  }

  public ArrayList<KXLinkInfo> getStateList()
  {
    return this.stateList;
  }

  public String getStatus()
  {
    return this.status;
  }

  public String getStatustime()
  {
    return this.statustime;
  }

  public String getTitle()
  {
    return this.title;
  }

  public int getTotalNum(String paramString)
  {
    return this.iTotalNum;
  }

  public String getUpdateTime()
  {
    return this.updateTime;
  }

  public String getctime()
  {
    return this.ctime;
  }

  public boolean isAppsHasNew()
  {
    return this.mAppsHasNew;
  }

  public boolean isFirstRefresh()
  {
    return this.isFirstRefresh;
  }

  public void setActiveItem(NewsInfo paramNewsInfo)
  {
    this.activeNewsInfo = paramNewsInfo;
  }

  public void setAllNewAppsCount(int paramInt)
  {
    this.mAllNewAppsCount = paramInt;
  }

  public void setAllPhotoNum(int paramInt)
  {
    this.fPhotoNum = paramInt;
  }

  public void setAppsHasNew(boolean paramBoolean)
  {
    this.mAppsHasNew = paramBoolean;
  }

  public void setConstellation(String paramString)
  {
    this.constellation = paramString;
  }

  public void setCoverId(String paramString)
  {
    this.mCoverId = paramString;
  }

  public void setCoverUrl(String paramString)
  {
    this.mCoverUrl = paramString;
  }

  public void setDiaryNum(int paramInt)
  {
    this.fDiaryNum = paramInt;
  }

  public void setExp(String paramString)
  {
    this.exp = paramString;
  }

  public void setExp_award(String paramString)
  {
    this.exp_award = paramString;
  }

  public void setExp_to_upgrade(String paramString)
  {
    this.exp_to_upgrade = paramString;
  }

  public void setFansCount(int paramInt)
  {
    this.mFansCount = paramInt;
  }

  public void setFirstRefresh(boolean paramBoolean)
  {
    this.isFirstRefresh = paramBoolean;
  }

  public void setFriendNum(int paramInt)
  {
    this.mFriendNum = paramInt;
  }

  public void setGender(String paramString)
  {
    this.gender = paramString;
  }

  public void setHomeCity(String paramString)
  {
    this.city = paramString;
  }

  public void setIsmyfriend(String paramString)
  {
    this.ismyfriend = paramString;
  }

  public void setIstar(String paramString)
  {
    this.istar = paramString;
  }

  public void setLarge(String paramString)
  {
    this.large = paramString;
  }

  public void setLastNum(int paramInt)
  {
    this.n = paramInt;
  }

  public void setLevel(String paramString)
  {
    this.level = paramString;
  }

  public void setLogo(String paramString)
  {
    this.logo = paramString;
  }

  public void setLogo120(String paramString)
  {
    this.logo120 = paramString;
  }

  public void setMiddle(String paramString)
  {
    this.middle = paramString;
  }

  public void setNewAppsCount(int paramInt)
  {
    this.mNewAppsCount = paramInt;
  }

  public void setNewGamesCount(int paramInt)
  {
    this.mNewGamesCount = paramInt;
  }

  public void setNewPhotoUrl(String paramString)
  {
    this.mNewPhotoUrl = paramString;
  }

  public void setNewsCount(int paramInt)
  {
    this.iNewsCount = paramInt;
  }

  public void setOnline(String paramString)
  {
    this.online = paramString;
  }

  public void setPrivacy(String paramString)
  {
    this.indexprivacy = paramString;
  }

  public void setPublicMore(int paramInt)
  {
  }

  public void setRealname(String paramString)
  {
    this.realname = paramString;
  }

  public void setRecordNum(int paramInt)
  {
    this.fRecordNum = paramInt;
  }

  public void setRefreshNum(int paramInt)
  {
    this.refreshNum = paramInt;
  }

  public void setRepostNum(int paramInt)
  {
    this.fRepostNum = paramInt;
  }

  public void setSameFriends(String paramString)
  {
    this.samefriends = paramString;
  }

  public void setSmall(String paramString)
  {
    this.small = paramString;
  }

  public void setStarintro(String paramString)
  {
    this.starintro = paramString;
  }

  public void setStatus(String paramString)
  {
    this.status = paramString;
    if (!TextUtils.isEmpty(paramString))
      this.stateList = ParseNewsInfoUtil.parseNewsLinkString(paramString);
  }

  public void setStatustime(String paramString)
  {
    this.statustime = paramString;
  }

  public void setTitle(String paramString)
  {
    this.title = paramString;
  }

  public void setTotalNum(int paramInt, String paramString)
  {
    this.iTotalNum = paramInt;
  }

  public void setUpdateTime(String paramString)
  {
    this.updateTime = paramString;
  }

  public void setctime(String paramString)
  {
    this.ctime = paramString;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.NewsModel
 * JD-Core Version:    0.6.0
 */