package com.kaixin001.model;

import java.util.ArrayList;

public class ThirdGameInfo
{
  private static ThirdGameInfo instance;
  public String ctime = "";
  public String num = "";
  public String start = "";
  private ArrayList<ThirdGameModel> thirdGameList;
  public String total = "";

  public static ThirdGameInfo getInstance()
  {
    if (instance == null)
      instance = new ThirdGameInfo();
    return instance;
  }

  public void addItem(ThirdGameModel paramThirdGameModel)
  {
    if (this.thirdGameList == null)
      this.thirdGameList = new ArrayList();
    this.thirdGameList.add(paramThirdGameModel);
  }

  public ArrayList<ThirdGameModel> getThirdGameList()
  {
    if (this.thirdGameList == null)
      this.thirdGameList = new ArrayList();
    return this.thirdGameList;
  }

  public ThirdGameModel parseKXRecommendTo360Game(KXGamesInfo.RecommendGameModel paramRecommendGameModel)
  {
    ThirdGameModel localThirdGameModel = new ThirdGameModel();
    localThirdGameModel.type = ThirdGameType.KAIXIN_RECOMMEND;
    localThirdGameModel.id = paramRecommendGameModel.appid;
    localThirdGameModel.packageName = paramRecommendGameModel.package_name;
    localThirdGameModel.minVersion = "";
    localThirdGameModel.minVersionCode = "";
    localThirdGameModel.name = paramRecommendGameModel.name;
    localThirdGameModel.description = paramRecommendGameModel.des;
    localThirdGameModel.developer = paramRecommendGameModel.dev;
    localThirdGameModel.iconUrl = paramRecommendGameModel.largelogo;
    localThirdGameModel.screenshotsUrl = "";
    localThirdGameModel.incomeShare = "";
    localThirdGameModel.rating = "";
    localThirdGameModel.versionName = paramRecommendGameModel.v;
    localThirdGameModel.versionCode = "";
    localThirdGameModel.priceInfo = "";
    localThirdGameModel.tag = "";
    localThirdGameModel.downloadTimes = "";
    localThirdGameModel.downloadUrl = paramRecommendGameModel.detailurl;
    localThirdGameModel.apkMd5 = "";
    localThirdGameModel.apkSize = paramRecommendGameModel.size;
    localThirdGameModel.createTime = "";
    localThirdGameModel.updateTime = "";
    localThirdGameModel.signatureMd5 = "";
    localThirdGameModel.updateInfo = paramRecommendGameModel.name;
    localThirdGameModel.language = "";
    localThirdGameModel.brief = paramRecommendGameModel.intro;
    localThirdGameModel.appPermission = "";
    localThirdGameModel.isAd = "";
    return localThirdGameModel;
  }

  public class ThirdGameModel
  {
    public String apkMd5 = "";
    public String apkSize = "";
    public String appPermission = "";
    public String brief = "";
    public String createTime = "";
    public String description = "";
    public String developer = "";
    public String downloadTimes = "";
    public String downloadUrl = "";
    public String iconUrl = "";
    public String id = "";
    public String incomeShare = "";
    public String isAd = "";
    public String language = "";
    public String minVersion = "";
    public String minVersionCode = "";
    public String name = "";
    public String packageName = "";
    public String priceInfo = "";
    public String rating = "";
    public String screenshotsUrl = "";
    public String signatureMd5 = "";
    public String tag = "";
    public ThirdGameInfo.ThirdGameType type;
    public String updateInfo = "";
    public String updateTime = "";
    public String versionCode = "";
    public String versionName = "";

    public ThirdGameModel()
    {
    }
  }

  public static enum ThirdGameType
  {
    static
    {
      KAIXIN_RECOMMEND = new ThirdGameType("KAIXIN_RECOMMEND", 1);
      ThirdGameType[] arrayOfThirdGameType = new ThirdGameType[2];
      arrayOfThirdGameType[0] = THIRD_360;
      arrayOfThirdGameType[1] = KAIXIN_RECOMMEND;
      ENUM$VALUES = arrayOfThirdGameType;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.ThirdGameInfo
 * JD-Core Version:    0.6.0
 */