package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.item.FilmCommentItem;
import com.kaixin001.item.FilmInfo;
import com.kaixin001.model.FilmDetailModel;
import com.kaixin001.model.FilmListModel;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;

public class FilmEngine extends KXEngine
{
  private static FilmEngine instance = new FilmEngine();

  public static FilmEngine getInstance()
  {
    return instance;
  }

  private int parseFilmComment(Context paramContext, FilmDetailModel paramFilmDetailModel, String paramString, int paramInt)
  {
    while (true)
    {
      ArrayList localArrayList;
      JSONArray localJSONArray;
      int i;
      boolean bool2;
      try
      {
        if (!TextUtils.isEmpty(paramString))
        {
          JSONObject localJSONObject1 = super.parseJSON(paramContext, paramString);
          if (localJSONObject1 == null)
            return -1;
          if (this.ret != 1)
            break label340;
          if (paramInt != 1)
            continue;
          if (localJSONObject1.optInt("hasmore", 0) != 1)
            break label355;
          bool1 = true;
          paramFilmDetailModel.setAllHasMore(bool1);
          if (paramInt != 1)
            break label146;
          localArrayList = paramFilmDetailModel.getAllComments();
          localJSONArray = localJSONObject1.optJSONArray("comment_list");
          if ((localJSONArray == null) || (localJSONArray.length() <= 0))
            break label353;
          i = 0;
          if (i < localJSONArray.length())
            break label155;
          break label353;
          if (localJSONObject1.optInt("hasmore", 0) != 1)
            break label140;
          bool2 = true;
          label122: paramFilmDetailModel.setFriendHasMore(bool2);
          continue;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
      label140: label146: label155: label340: int j;
      do
      {
        return -1;
        bool2 = false;
        break label122;
        localArrayList = paramFilmDetailModel.getFriendComments();
        break;
        JSONObject localJSONObject2 = localJSONArray.optJSONObject(i);
        if (localJSONObject2 == null)
          break label361;
        FilmCommentItem localFilmCommentItem = new FilmCommentItem();
        localFilmCommentItem.mFuid = localJSONObject2.optString("uid", "");
        localFilmCommentItem.mFname = localJSONObject2.optString("name", "");
        localFilmCommentItem.mNickname = localJSONObject2.optString("nickname", "");
        localFilmCommentItem.mOnline = localJSONObject2.optInt("online", 0);
        localFilmCommentItem.mIcon50 = localJSONObject2.optString("icon50", "");
        localFilmCommentItem.mIcon90 = localJSONObject2.optString("icon90", "");
        localFilmCommentItem.mIcon120 = localJSONObject2.optString("icon120", "");
        localFilmCommentItem.mComment = localJSONObject2.optString("content", "");
        localFilmCommentItem.mTime = localJSONObject2.optString("ctime", "");
        localFilmCommentItem.mScore = localJSONObject2.optInt("score", 0);
        localFilmCommentItem.mGender = localJSONObject2.optInt("gender", 0);
        localArrayList.add(localFilmCommentItem);
        break label361;
        j = this.ret;
      }
      while (j != 0);
      return 1;
      label353: return 1;
      label355: boolean bool1 = false;
      continue;
      label361: i++;
    }
  }

  private int parseFilmDetail(Context paramContext, FilmDetailModel paramFilmDetailModel, String paramString, int paramInt)
  {
    while (true)
    {
      FilmInfo localFilmInfo;
      JSONArray localJSONArray1;
      int i;
      JSONArray localJSONArray2;
      int j;
      try
      {
        if (!TextUtils.isEmpty(paramString))
        {
          JSONObject localJSONObject1 = super.parseJSON(paramContext, paramString);
          if (localJSONObject1 == null)
            return -1;
          if (this.ret == 1)
          {
            if (paramInt != 1)
              continue;
            if (localJSONObject1.optInt("hasmore", 0) != 1)
              break label717;
            bool1 = true;
            paramFilmDetailModel.setAllHasMore(bool1);
            paramFilmDetailModel.setAllCommentNum(localJSONObject1.optInt("total_comment", 0));
            paramFilmDetailModel.setFriendCommentNum(localJSONObject1.optInt("friend_comment", 0));
            JSONObject localJSONObject2 = localJSONObject1.optJSONObject("film_info");
            if (localJSONObject2 == null)
              break label715;
            localFilmInfo = paramFilmDetailModel.getInfo();
            if (localFilmInfo != null)
              continue;
            localFilmInfo = new FilmInfo();
            paramFilmDetailModel.setInfo(localFilmInfo);
            localFilmInfo.mFid = localJSONObject2.optString("mid", "");
            localFilmInfo.mName = localJSONObject2.optString("name", "");
            localFilmInfo.mAlias = localJSONObject2.optString("alias", "");
            localFilmInfo.mCover = localJSONObject2.optString("cover", "");
            localFilmInfo.mDirector = localJSONObject2.optString("director", "");
            localFilmInfo.mActors = localJSONObject2.optString("actor", "");
            localFilmInfo.mType = localJSONObject2.optString("mtype", "");
            localFilmInfo.mLanguage = localJSONObject2.optString("language ", "");
            localFilmInfo.mPublishTime = localJSONObject2.optString("pubtime ", "");
            localFilmInfo.mZone = localJSONObject2.optString("zone", "");
            localFilmInfo.mCostTime = localJSONObject2.optString("costtime", "");
            localFilmInfo.mWatched = localJSONObject2.optString("watched", "");
            localFilmInfo.mWantto = localJSONObject2.optString("wantto", "");
            localFilmInfo.mScore = localJSONObject2.optString("score", "3");
            if (localJSONObject1.optInt("haswached", 0) != 1)
              break label499;
            bool2 = true;
            localFilmInfo.mWanted = bool2;
            if (localFilmInfo.mStills != null)
              continue;
            localFilmInfo.mStills = new ArrayList();
            localJSONArray1 = localJSONObject2.optJSONArray("stills");
            if ((localJSONArray1 == null) || (localJSONArray1.length() <= 0))
              continue;
            i = 0;
            if (i < localJSONArray1.length())
              break label505;
            localFilmInfo.mIntro = localJSONObject2.optString("intro", "");
            if (paramInt != 1)
              break label531;
            localArrayList = paramFilmDetailModel.getAllComments();
            localJSONArray2 = localJSONObject2.optJSONArray("comment_list");
            if ((localJSONArray2 == null) || (localJSONArray2.length() <= 0))
              break label715;
            j = 0;
            if (j < localJSONArray2.length())
              break label540;
            break label715;
            if (localJSONObject1.optInt("hasmore", 0) != 1)
              break label493;
            bool3 = true;
            paramFilmDetailModel.setFriendHasMore(bool3);
            continue;
          }
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
      return -1;
      label493: boolean bool3 = false;
      continue;
      label499: boolean bool2 = false;
      continue;
      label505: String str = localJSONArray1.getString(i);
      localFilmInfo.mStills.add(str);
      i++;
      continue;
      label531: ArrayList localArrayList = paramFilmDetailModel.getFriendComments();
      continue;
      label540: JSONObject localJSONObject3 = localJSONArray2.optJSONObject(j);
      if (localJSONObject3 != null)
      {
        FilmCommentItem localFilmCommentItem = new FilmCommentItem();
        localFilmCommentItem.mFuid = localJSONObject3.optString("uid", "");
        localFilmCommentItem.mFname = localJSONObject3.optString("name", "");
        localFilmCommentItem.mNickname = localJSONObject3.optString("nickname", "");
        localFilmCommentItem.mOnline = localJSONObject3.optInt("online", 0);
        localFilmCommentItem.mIcon50 = localJSONObject3.optString("icon50", "");
        localFilmCommentItem.mIcon90 = localJSONObject3.optString("icon90", "");
        localFilmCommentItem.mIcon120 = localJSONObject3.optString("icon120", "");
        localFilmCommentItem.mComment = localJSONObject3.optString("content", "");
        localFilmCommentItem.mTime = localJSONObject3.optString("ctime", "");
        localFilmCommentItem.mScore = localJSONObject3.optInt("score", 0);
        localArrayList.add(localFilmCommentItem);
      }
      j++;
      continue;
      label715: return 1;
      label717: boolean bool1 = false;
    }
  }

  private int parseFilmList(Context paramContext, FilmListModel paramFilmListModel, String paramString)
  {
    int j;
    int k;
    do
    {
      try
      {
        if (!TextUtils.isEmpty(paramString))
        {
          JSONObject localJSONObject1 = super.parseJSON(paramContext, paramString);
          if (localJSONObject1 == null)
            return -1;
          if (this.ret == 1)
          {
            int i = localJSONObject1.optInt("hasmore", 0);
            boolean bool = false;
            if (i == 1)
              bool = true;
            paramFilmListModel.setHasMore(bool);
            JSONArray localJSONArray = localJSONObject1.optJSONArray("film_list");
            if (localJSONArray != null)
            {
              j = localJSONArray.length();
              if (j <= 0)
                break;
              k = 0;
              continue;
              JSONObject localJSONObject2 = localJSONArray.getJSONObject(k);
              FilmInfo localFilmInfo = new FilmInfo();
              localFilmInfo.mFid = localJSONObject2.optString("mid", "");
              localFilmInfo.mName = localJSONObject2.optString("name", "");
              localFilmInfo.mAlias = localJSONObject2.optString("alias", "");
              localFilmInfo.mCover = localJSONObject2.optString("cover", "");
              localFilmInfo.mDirector = localJSONObject2.optString("director", "");
              localFilmInfo.mActors = localJSONObject2.optString("actor", "");
              localFilmInfo.mType = localJSONObject2.optString("mtype", "");
              localFilmInfo.mLanguage = localJSONObject2.optString("language ", "");
              localFilmInfo.mPublishTime = localJSONObject2.optString("pubtime ", "");
              localFilmInfo.mZone = localJSONObject2.optString("zone", "");
              localFilmInfo.mCostTime = localJSONObject2.optString("costtime", "");
              localFilmInfo.mWatched = localJSONObject2.optString("watched", "");
              localFilmInfo.mWantto = localJSONObject2.optString("wantto", "");
              localFilmInfo.mScore = localJSONObject2.optString("score", "3");
              localFilmInfo.mIntro = localJSONObject2.optString("intro", "");
              paramFilmListModel.getFilmList().add(localFilmInfo);
              k++;
            }
          }
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
      return -1;
    }
    while (k < j);
    return 1;
  }

  private int parseWantto(Context paramContext, String paramString)
  {
    try
    {
      if (!TextUtils.isEmpty(paramString))
      {
        if (super.parseJSON(paramContext, paramString) == null)
          return -1;
        int i = this.ret;
        if (i == 1)
          return 1;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return -1;
  }

  private int parseWriteFilmComment(Context paramContext, String paramString)
  {
    try
    {
      if (!TextUtils.isEmpty(paramString))
      {
        if (super.parseJSON(paramContext, paramString) == null)
          return -1;
        int i = this.ret;
        if (i == 1)
          return 1;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return -1;
  }

  public int getFilmCommentList(Context paramContext, FilmDetailModel paramFilmDetailModel, String paramString, int paramInt1, int paramInt2, int paramInt3)
  {
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      int i = parseFilmComment(paramContext, paramFilmDetailModel, localHttpProxy.httpGet(Protocol.getInstance().makeGetFilmComment(paramString, paramInt1, paramInt2, paramInt3), null, null), paramInt3);
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return -1;
  }

  public int getFilmDetail(Context paramContext, FilmDetailModel paramFilmDetailModel, String paramString, int paramInt1, int paramInt2, int paramInt3)
  {
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      int i = parseFilmDetail(paramContext, paramFilmDetailModel, localHttpProxy.httpGet(Protocol.getInstance().makeGetFilmDetail(paramString, paramInt1, paramInt2, paramInt3), null, null), paramInt3);
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return -1;
  }

  public int getFilmList(Context paramContext, int paramInt1, int paramInt2)
  {
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str = localHttpProxy.httpGet(Protocol.getInstance().makeGetFilmList(paramInt1, paramInt2), null, null);
      int i = parseFilmList(paramContext, FilmListModel.getInstance(), str);
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return -1;
  }

  public int postFilmComment(Context paramContext, String paramString1, String paramString2, String paramString3)
  {
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str = Protocol.getInstance().makeWriteFilmComment();
      HashMap localHashMap = new HashMap();
      localHashMap.put("id", paramString1);
      localHashMap.put("content", paramString2);
      localHashMap.put("score", paramString3);
      int i = parseWriteFilmComment(paramContext, localHttpProxy.httpPost(str, localHashMap, null, null));
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return -1;
  }

  public int wantTo(Context paramContext, String paramString)
  {
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      int i = parseWantto(paramContext, localHttpProxy.httpGet(Protocol.getInstance().makeFilmWantto(paramString), null, null));
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return -1;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.FilmEngine
 * JD-Core Version:    0.6.0
 */