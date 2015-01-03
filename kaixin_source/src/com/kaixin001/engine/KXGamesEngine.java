package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.item.AdvGameItem;
import com.kaixin001.model.AdvGameModel;
import com.kaixin001.model.KXGamesInfo;
import com.kaixin001.model.KXGamesInfo.KXGameModel;
import com.kaixin001.model.KXGamesInfo.RecommendGameModel;
import com.kaixin001.model.ThirdGameInfo;
import com.kaixin001.model.ThirdGameInfo.ThirdGameModel;
import com.kaixin001.model.ThirdGameInfo.ThirdGameType;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.FileUtil;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class KXGamesEngine extends KXEngine
{
  public static String KX_APPLIST_FILE = "recommend_kxapp.kx";
  public static String KX_GAMELIST_FILE = "recommend_kxgame.kx";
  public static final String LOCATION_APP = "3";
  public static final String LOCATION_GAME = "2";
  public static String RECOMMEND_GAME_FILE;
  private static final String TAG = "KXGamesEngine";
  public static String THIRD_GAMELIST_FILE = "recommend_thirdgame.kx";
  private static KXGamesEngine instance;

  static
  {
    RECOMMEND_GAME_FILE = "recommend_games_list.kx";
  }

  public static KXGamesEngine getInstance()
  {
    if (instance == null)
      instance = new KXGamesEngine();
    return instance;
  }

  private String loadKXCacheFile(Context paramContext, KXGAME_TYPE paramKXGAME_TYPE)
  {
    int i = $SWITCH_TABLE$com$kaixin001$engine$KXGamesEngine$KXGAME_TYPE()[paramKXGAME_TYPE.ordinal()];
    String str = null;
    switch (i)
    {
    default:
    case 1:
    case 2:
    case 3:
    case 4:
    }
    while (true)
    {
      return FileUtil.getCacheData(FileUtil.getKXCacheDir(paramContext), User.getInstance().getUID(), str);
      str = KX_APPLIST_FILE;
      continue;
      str = KX_GAMELIST_FILE;
      continue;
      str = THIRD_GAMELIST_FILE;
      continue;
      str = RECOMMEND_GAME_FILE;
    }
  }

  private void parseBannerJson(JSONObject paramJSONObject)
  {
    while (true)
    {
      int i;
      try
      {
        JSONArray localJSONArray = paramJSONObject.optJSONArray("data");
        if (localJSONArray != null)
        {
          i = 0;
          if (i >= localJSONArray.length())
            return;
          JSONObject localJSONObject = localJSONArray.getJSONObject(i);
          AdvGameItem localAdvGameItem = new AdvGameItem();
          localAdvGameItem.setId(localJSONObject.optString("id", ""));
          localAdvGameItem.setType(localJSONObject.optString("type", ""));
          localAdvGameItem.setAdvertImageUrl(localJSONObject.optString("picurl", ""));
          localAdvGameItem.setAdvertClickUrl(localJSONObject.optString("clickurl", ""));
          localAdvGameItem.setDisplayTime(localJSONObject.optInt("displayTime", 3));
          String str = localJSONObject.optString("location", "");
          if (!str.equals("2"))
            continue;
          AdvGameModel.getInstance().addGameBannerItem(localAdvGameItem);
          break label169;
          if (!str.equals("3"))
            break label169;
          AdvGameModel.getInstance().addAppBannerItem(localAdvGameItem);
        }
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
      }
      return;
      label169: i++;
    }
  }

  private void parseKXApplistJSON(JSONObject paramJSONObject)
  {
    int i;
    int j;
    do
      try
      {
        JSONArray localJSONArray = paramJSONObject.optJSONArray("data");
        if (localJSONArray == null)
          i = 0;
        while (true)
        {
          if (i <= 0)
            return;
          KXGamesInfo.getInstance().getKXAppList().clear();
          j = 0;
          break;
          i = localJSONArray.length();
          continue;
          JSONObject localJSONObject = (JSONObject)localJSONArray.get(j);
          KXGamesInfo localKXGamesInfo = KXGamesInfo.getInstance();
          localKXGamesInfo.getClass();
          KXGamesInfo.KXGameModel localKXGameModel = new KXGamesInfo.KXGameModel(localKXGamesInfo);
          localKXGameModel.name = localJSONObject.optString("name", "");
          localKXGameModel.wapurl = localJSONObject.optString("wapurl", "");
          localKXGameModel.logo = localJSONObject.optString("logo", "");
          localKXGameModel.appid = localJSONObject.optString("appid", "");
          localKXGameModel.status = localJSONObject.optString("status", "");
          localKXGameModel.orderid = localJSONObject.optString("orderid", "");
          localKXGameModel.intro = localJSONObject.optString("intro", "");
          localKXGameModel.time = localJSONObject.optString("time", "");
          localKXGameModel.isnew = localJSONObject.optString("isnew", "");
          localKXGameModel.weixin = localJSONObject.optString("weixin", "");
          KXGamesInfo.getInstance().addKXAppItem(localKXGameModel);
          j++;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return;
      }
    while (j < i);
  }

  private void parseRecommendGamesJson(JSONObject paramJSONObject)
  {
    int i;
    int m;
    do
      try
      {
        JSONArray localJSONArray = paramJSONObject.optJSONArray("data");
        if (localJSONArray == null)
          i = 0;
        while (true)
        {
          int j = ThirdGameInfo.getInstance().getThirdGameList().size();
          int k = 0;
          if (j < 9)
            k = 1;
          if (i <= 0)
            return;
          KXGamesInfo.getInstance().getRecommendGameList().clear();
          ThirdGameInfo.getInstance().getThirdGameList().clear();
          m = 0;
          break;
          i = localJSONArray.length();
          continue;
          JSONObject localJSONObject1 = (JSONObject)localJSONArray.get(m);
          KXGamesInfo localKXGamesInfo = KXGamesInfo.getInstance();
          localKXGamesInfo.getClass();
          KXGamesInfo.RecommendGameModel localRecommendGameModel = new KXGamesInfo.RecommendGameModel(localKXGamesInfo);
          localRecommendGameModel.name = localJSONObject1.optString("name", "");
          localRecommendGameModel.appid = localJSONObject1.optString("appid", "");
          localRecommendGameModel.des = localJSONObject1.optString("des", "");
          localRecommendGameModel.online = localJSONObject1.optString("online", "");
          localRecommendGameModel.time = localJSONObject1.optString("time", "");
          localRecommendGameModel.hotstart = localJSONObject1.optString("hotstart", "");
          localRecommendGameModel.hotend = localJSONObject1.optString("hotend", "");
          localRecommendGameModel.kaixin = localJSONObject1.optString("kaixin", "");
          localRecommendGameModel.order = localJSONObject1.optString("order", "");
          localRecommendGameModel.type = localJSONObject1.optString("type", "");
          localRecommendGameModel.intro = localJSONObject1.optString("intro", "");
          localRecommendGameModel.isnew = localJSONObject1.optString("isnew", "");
          localRecommendGameModel.ctype = localJSONObject1.optString("ctype", "");
          localRecommendGameModel.weixin = localJSONObject1.optString("weixin", "");
          localRecommendGameModel.click_type = localJSONObject1.optString("click_type", "");
          localRecommendGameModel.largelogo = localJSONObject1.optString("largelogo", "");
          localRecommendGameModel.smalllogo = localJSONObject1.optString("smalllogo", "");
          localRecommendGameModel.detailurl = localJSONObject1.optString("detailurl", "");
          JSONObject localJSONObject2 = (JSONObject)localJSONObject1.opt("detail");
          if (localJSONObject2 != null)
          {
            localRecommendGameModel.key = localJSONObject2.optString("key", "");
            localRecommendGameModel.shortDes = localJSONObject2.optString("shortDes", "");
            localRecommendGameModel.link = localJSONObject2.optString("link", "");
            localRecommendGameModel.size = localJSONObject2.optString("size", "");
            localRecommendGameModel.v = localJSONObject2.optString("v", "");
            localRecommendGameModel.support = localJSONObject2.optString("support", "");
            localRecommendGameModel.dev = localJSONObject2.optString("dev", "");
            localRecommendGameModel.download = localJSONObject2.optString("download", "");
            localRecommendGameModel.package_name = localJSONObject2.optString("package_name", "");
          }
          KXGamesInfo.getInstance().addRecommendItem(localRecommendGameModel);
          if (k != 0)
          {
            ThirdGameInfo.ThirdGameModel localThirdGameModel = ThirdGameInfo.getInstance().parseKXRecommendTo360Game(localRecommendGameModel);
            ThirdGameInfo.getInstance().addItem(localThirdGameModel);
          }
          m++;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return;
      }
    while (m < i);
  }

  private void parseThirdGamesJson(JSONObject paramJSONObject)
  {
    if (paramJSONObject != null)
    {
      int i;
      int j;
      do
        try
        {
          JSONObject localJSONObject1 = paramJSONObject.optJSONObject("data");
          if (localJSONObject1 == null)
            break;
          ThirdGameInfo.getInstance().total = localJSONObject1.optString("total", "");
          ThirdGameInfo.getInstance().start = localJSONObject1.optString("start", "");
          ThirdGameInfo.getInstance().num = localJSONObject1.optString("num", "");
          ThirdGameInfo.getInstance().ctime = localJSONObject1.optString("ctime", "");
          JSONArray localJSONArray = localJSONObject1.optJSONArray("items");
          i = 0;
          if (localJSONArray == null);
          while (true)
          {
            if (i <= 0)
              return;
            ThirdGameInfo.getInstance().getThirdGameList().clear();
            j = 0;
            break;
            i = localJSONArray.length();
            continue;
            JSONObject localJSONObject2 = (JSONObject)localJSONArray.get(j);
            ThirdGameInfo localThirdGameInfo = ThirdGameInfo.getInstance();
            localThirdGameInfo.getClass();
            ThirdGameInfo.ThirdGameModel localThirdGameModel = new ThirdGameInfo.ThirdGameModel(localThirdGameInfo);
            localThirdGameModel.type = ThirdGameInfo.ThirdGameType.THIRD_360;
            localThirdGameModel.id = localJSONObject2.optString("id", "");
            localThirdGameModel.packageName = localJSONObject2.optString("packageName", "");
            localThirdGameModel.minVersion = localJSONObject2.optString("minVersion", "");
            localThirdGameModel.minVersionCode = localJSONObject2.optString("minVersionCode", "");
            localThirdGameModel.name = localJSONObject2.optString("name", "");
            localThirdGameModel.description = localJSONObject2.optString("description", "");
            localThirdGameModel.developer = localJSONObject2.optString("developer", "");
            localThirdGameModel.iconUrl = localJSONObject2.optString("iconUrl", "");
            localThirdGameModel.screenshotsUrl = localJSONObject2.optString("screenshotsUrl", "");
            localThirdGameModel.incomeShare = localJSONObject2.optString("incomeShare", "");
            localThirdGameModel.rating = localJSONObject2.optString("rating", "");
            localThirdGameModel.versionName = localJSONObject2.optString("versionName", "");
            localThirdGameModel.versionCode = localJSONObject2.optString("versionCode", "");
            localThirdGameModel.priceInfo = localJSONObject2.optString("priceInfo", "");
            localThirdGameModel.tag = localJSONObject2.optString("tag", "");
            localThirdGameModel.downloadTimes = localJSONObject2.optString("downloadTimes", "");
            localThirdGameModel.downloadUrl = localJSONObject2.optString("downloadUrl", "");
            localThirdGameModel.apkMd5 = localJSONObject2.optString("apkMd5", "");
            localThirdGameModel.apkSize = (localJSONObject2.optInt("apkSize", 0) / 1000000 + "M");
            localThirdGameModel.createTime = localJSONObject2.optString("createTime", "");
            localThirdGameModel.updateTime = localJSONObject2.optString("updateTime", "");
            localThirdGameModel.signatureMd5 = localJSONObject2.optString("signatureMd5", "");
            localThirdGameModel.updateInfo = localJSONObject2.optString("updateInfo", "");
            localThirdGameModel.language = localJSONObject2.optString("language", "");
            localThirdGameModel.brief = localJSONObject2.optString("brief", "");
            localThirdGameModel.appPermission = localJSONObject2.optString("appPermission", "");
            localThirdGameModel.isAd = localJSONObject2.optString("isAd", "");
            ThirdGameInfo.getInstance().addItem(localThirdGameModel);
            j++;
          }
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
          return;
        }
      while (j < i);
    }
  }

  private boolean saveKXCacheFile(Context paramContext, String paramString, KXGAME_TYPE paramKXGAME_TYPE)
  {
    int i = $SWITCH_TABLE$com$kaixin001$engine$KXGamesEngine$KXGAME_TYPE()[paramKXGAME_TYPE.ordinal()];
    String str = null;
    switch (i)
    {
    default:
    case 1:
    case 2:
    case 3:
    case 4:
    }
    while (true)
    {
      return FileUtil.setCacheData(FileUtil.getKXCacheDir(paramContext), User.getInstance().getUID(), str, paramString);
      str = KX_APPLIST_FILE;
      continue;
      str = KX_GAMELIST_FILE;
      continue;
      str = THIRD_GAMELIST_FILE;
      continue;
      str = RECOMMEND_GAME_FILE;
    }
  }

  // ERROR //
  public void getBannerData(Context paramContext, String paramString)
  {
    // Byte code:
    //   0: invokestatic 173	com/kaixin001/model/AdvGameModel:getInstance	()Lcom/kaixin001/model/AdvGameModel;
    //   3: invokevirtual 551	com/kaixin001/model/AdvGameModel:clear	()V
    //   6: invokestatic 556	com/kaixin001/network/Protocol:getInstance	()Lcom/kaixin001/network/Protocol;
    //   9: aload_2
    //   10: invokevirtual 560	com/kaixin001/network/Protocol:getGameBannerUrl	(Ljava/lang/String;)Ljava/lang/String;
    //   13: astore_3
    //   14: new 562	com/kaixin001/network/HttpProxy
    //   17: dup
    //   18: aload_1
    //   19: invokespecial 565	com/kaixin001/network/HttpProxy:<init>	(Landroid/content/Context;)V
    //   22: astore 4
    //   24: aload 4
    //   26: aload_3
    //   27: aconst_null
    //   28: aconst_null
    //   29: invokevirtual 569	com/kaixin001/network/HttpProxy:httpGet	(Ljava/lang/String;Lcom/kaixin001/network/HttpRequestState;Lcom/kaixin001/network/HttpProgressListener;)Ljava/lang/String;
    //   32: astore 6
    //   34: aload 6
    //   36: ifnull +23 -> 59
    //   39: aload_0
    //   40: aload_1
    //   41: aload 6
    //   43: invokespecial 573	com/kaixin001/engine/KXEngine:parseJSON	(Landroid/content/Context;Ljava/lang/String;)Lorg/json/JSONObject;
    //   46: astore 8
    //   48: aload 8
    //   50: ifnull +9 -> 59
    //   53: aload_0
    //   54: aload 8
    //   56: invokespecial 575	com/kaixin001/engine/KXGamesEngine:parseBannerJson	(Lorg/json/JSONObject;)V
    //   59: return
    //   60: astore 5
    //   62: aload 5
    //   64: invokevirtual 260	java/lang/Exception:printStackTrace	()V
    //   67: return
    //   68: astore 7
    //   70: aload 7
    //   72: invokevirtual 576	com/kaixin001/engine/SecurityErrorException:printStackTrace	()V
    //   75: return
    //
    // Exception table:
    //   from	to	target	type
    //   24	34	60	java/lang/Exception
    //   39	48	68	com/kaixin001/engine/SecurityErrorException
  }

  public void getKXAppslistData(Context paramContext)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeGetCommonApplistRequest(0, 100);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    Object localObject = null;
    try
    {
      localObject = localHttpProxy.httpGet(str1, null, null);
      if (!TextUtils.isEmpty((CharSequence)localObject))
        saveKXCacheFile(paramContext, (String)localObject, KXGAME_TYPE.KX_APP);
    }
    catch (Exception localException)
    {
      try
      {
        while (true)
        {
          JSONObject localJSONObject = super.parseJSON(paramContext, (String)localObject);
          if (localJSONObject != null)
            parseKXApplistJSON(localJSONObject);
          return;
          String str2 = loadKXCacheFile(paramContext, KXGAME_TYPE.KX_APP);
          localObject = str2;
          continue;
          localException = localException;
          KXLog.e("KXGamesEngine", "get kxapp list error", localException);
        }
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
        localSecurityErrorException.printStackTrace();
      }
    }
  }

  public void getKXGameslistData(Context paramContext)
  {
    String str1 = Protocol.getInstance().makeGetKXApplistRequest(0, 100);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    Object localObject = null;
    try
    {
      localObject = localHttpProxy.httpGet(str1, null, null);
      if (!TextUtils.isEmpty((CharSequence)localObject))
        saveKXCacheFile(paramContext, (String)localObject, KXGAME_TYPE.KX_GAME);
    }
    catch (Exception localException)
    {
      try
      {
        while (true)
        {
          JSONObject localJSONObject = super.parseJSON(paramContext, (String)localObject);
          if (localJSONObject != null)
            parseKXGamelistJSON(localJSONObject);
          return;
          String str2 = loadKXCacheFile(paramContext, KXGAME_TYPE.KX_GAME);
          localObject = str2;
          continue;
          localException = localException;
          KXLog.e("KXGamesEngine", "get games list error", localException);
        }
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
        localSecurityErrorException.printStackTrace();
      }
    }
  }

  // ERROR //
  public void getRecommendGamesList(Context paramContext)
  {
    // Byte code:
    //   0: invokestatic 556	com/kaixin001/network/Protocol:getInstance	()Lcom/kaixin001/network/Protocol;
    //   3: iconst_0
    //   4: bipush 100
    //   6: invokevirtual 614	com/kaixin001/network/Protocol:makeGetThirdApplistRequest	(II)Ljava/lang/String;
    //   9: astore_2
    //   10: new 562	com/kaixin001/network/HttpProxy
    //   13: dup
    //   14: aload_1
    //   15: invokespecial 565	com/kaixin001/network/HttpProxy:<init>	(Landroid/content/Context;)V
    //   18: astore_3
    //   19: aload_3
    //   20: aload_2
    //   21: aconst_null
    //   22: aconst_null
    //   23: invokevirtual 569	com/kaixin001/network/HttpProxy:httpGet	(Ljava/lang/String;Lcom/kaixin001/network/HttpRequestState;Lcom/kaixin001/network/HttpProgressListener;)Ljava/lang/String;
    //   26: astore 5
    //   28: aload 5
    //   30: invokestatic 587	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   33: ifne +35 -> 68
    //   36: aload_0
    //   37: aload_1
    //   38: aload 5
    //   40: getstatic 47	com/kaixin001/engine/KXGamesEngine$KXGAME_TYPE:RECOMMMEND_GAME	Lcom/kaixin001/engine/KXGamesEngine$KXGAME_TYPE;
    //   43: invokespecial 589	com/kaixin001/engine/KXGamesEngine:saveKXCacheFile	(Landroid/content/Context;Ljava/lang/String;Lcom/kaixin001/engine/KXGamesEngine$KXGAME_TYPE;)Z
    //   46: pop
    //   47: aload_0
    //   48: aload_1
    //   49: aload 5
    //   51: invokespecial 573	com/kaixin001/engine/KXEngine:parseJSON	(Landroid/content/Context;Ljava/lang/String;)Lorg/json/JSONObject;
    //   54: astore 8
    //   56: aload 8
    //   58: ifnull +9 -> 67
    //   61: aload_0
    //   62: aload 8
    //   64: invokespecial 616	com/kaixin001/engine/KXGamesEngine:parseRecommendGamesJson	(Lorg/json/JSONObject;)V
    //   67: return
    //   68: aload_0
    //   69: aload_1
    //   70: getstatic 47	com/kaixin001/engine/KXGamesEngine$KXGAME_TYPE:RECOMMMEND_GAME	Lcom/kaixin001/engine/KXGamesEngine$KXGAME_TYPE;
    //   73: invokespecial 593	com/kaixin001/engine/KXGamesEngine:loadKXCacheFile	(Landroid/content/Context;Lcom/kaixin001/engine/KXGamesEngine$KXGAME_TYPE;)Ljava/lang/String;
    //   76: astore 6
    //   78: aload 6
    //   80: astore 5
    //   82: goto -35 -> 47
    //   85: astore 4
    //   87: aload 4
    //   89: invokevirtual 260	java/lang/Exception:printStackTrace	()V
    //   92: return
    //   93: astore 7
    //   95: aload 7
    //   97: invokevirtual 260	java/lang/Exception:printStackTrace	()V
    //   100: return
    //
    // Exception table:
    //   from	to	target	type
    //   19	47	85	java/lang/Exception
    //   68	78	85	java/lang/Exception
    //   47	56	93	java/lang/Exception
  }

  public void getThirdGameslistData(Context paramContext)
  {
    String str1 = Protocol.getInstance().getThirdGameListUrl(0, 100);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    Object localObject = null;
    try
    {
      localObject = localHttpProxy.httpGet(str1, null, null);
      if (!TextUtils.isEmpty((CharSequence)localObject))
        saveKXCacheFile(paramContext, (String)localObject, KXGAME_TYPE.THIRD_GAME);
    }
    catch (Exception localException1)
    {
      try
      {
        while (true)
        {
          JSONObject localJSONObject = super.parseJSON(paramContext, (String)localObject);
          if (localJSONObject != null)
            parseThirdGamesJson(localJSONObject);
          return;
          String str2 = loadKXCacheFile(paramContext, KXGAME_TYPE.THIRD_GAME);
          localObject = str2;
          continue;
          localException1 = localException1;
          KXLog.e("KXGamesEngine", "get 360 game list error", localException1);
        }
      }
      catch (Exception localException2)
      {
        localException2.printStackTrace();
      }
    }
  }

  public void parseKXGamelistJSON(JSONObject paramJSONObject)
  {
    int i;
    int j;
    do
      try
      {
        JSONArray localJSONArray = paramJSONObject.optJSONArray("data");
        if (localJSONArray == null)
          i = 0;
        while (true)
        {
          if (i <= 0)
            return;
          KXGamesInfo.getInstance().getKXGameList().clear();
          j = 0;
          break;
          i = localJSONArray.length();
          continue;
          JSONObject localJSONObject = (JSONObject)localJSONArray.get(j);
          KXGamesInfo localKXGamesInfo = KXGamesInfo.getInstance();
          localKXGamesInfo.getClass();
          KXGamesInfo.KXGameModel localKXGameModel = new KXGamesInfo.KXGameModel(localKXGamesInfo);
          localKXGameModel.name = localJSONObject.optString("name", "");
          localKXGameModel.wapurl = localJSONObject.optString("wapurl", "");
          localKXGameModel.logo = localJSONObject.optString("logo", "");
          localKXGameModel.appid = localJSONObject.optString("appid", "");
          localKXGameModel.status = localJSONObject.optString("status", "");
          localKXGameModel.orderid = localJSONObject.optString("orderid", "");
          localKXGameModel.intro = localJSONObject.optString("intro", "");
          localKXGameModel.time = localJSONObject.optString("time", "");
          localKXGameModel.isnew = localJSONObject.optString("isnew", "");
          localKXGameModel.weixin = localJSONObject.optString("weixin", "");
          KXGamesInfo.getInstance().addKXGameItem(localKXGameModel);
          j++;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return;
      }
    while (j < i);
  }

  private static enum KXGAME_TYPE
  {
    static
    {
      RECOMMMEND_GAME = new KXGAME_TYPE("RECOMMMEND_GAME", 3);
      KXGAME_TYPE[] arrayOfKXGAME_TYPE = new KXGAME_TYPE[4];
      arrayOfKXGAME_TYPE[0] = KX_APP;
      arrayOfKXGAME_TYPE[1] = KX_GAME;
      arrayOfKXGAME_TYPE[2] = THIRD_GAME;
      arrayOfKXGAME_TYPE[3] = RECOMMMEND_GAME;
      ENUM$VALUES = arrayOfKXGAME_TYPE;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.KXGamesEngine
 * JD-Core Version:    0.6.0
 */