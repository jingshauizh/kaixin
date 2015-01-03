package com.kaixin001.engine;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import com.kaixin001.fragment.BaseFragment;
import com.kaixin001.item.RepItem;
import com.kaixin001.model.RepostModel;
import com.kaixin001.model.SharedPostModel;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.FileUtil;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RepostEngine extends KXEngine
{
  private static final String LOGTAG = "RepostEngine";
  public static final int NUM = 10;
  private static RepostEngine instance = null;
  private String FRIENDS_FREPOST_FILE = "friends_repost.kx";
  private String HOT_REPOST_FILE = "hot_repost.kx";
  private String mLastError = "";

  public static RepostEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new RepostEngine();
      RepostEngine localRepostEngine = instance;
      return localRepostEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public boolean doGetMoreDataRepostList(Context paramContext, String paramString, int paramInt)
  {
    String str1 = Protocol.getInstance().makeGetMoreDataRepostListRequest(paramString, "", User.getInstance().getUID(), 10, paramInt);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str2 = localHttpProxy.httpGet(str1, null, null);
      if (TextUtils.isEmpty(str2))
        return false;
      JSONObject localJSONObject1 = parseJSON(paramContext, str2);
      if ((localJSONObject1 != null) && (this.ret == 1))
      {
        JSONArray localJSONArray = localJSONObject1.getJSONArray("replists");
        ArrayList localArrayList = new ArrayList();
        for (int i = 0; ; i++)
        {
          if (i >= localJSONArray.length())
            return true;
          JSONObject localJSONObject2 = (JSONObject)localJSONArray.get(i);
          RepItem localRepItem = new RepItem();
          localRepItem.id = localJSONObject2.getString("id");
          localRepItem.title = localJSONObject2.getString("title");
          localRepItem.category = localJSONObject2.getInt("category");
          localRepItem.stime = localJSONObject2.optString("strctime", "");
          localRepItem.mContent = localJSONObject2.optString("abstract", "");
          localRepItem.mRepostNum = localJSONObject2.optInt("rpnum");
          localRepItem.mThumbImg = localJSONObject2.optString("thumbimg", "");
          if ("null".equals(localRepItem.mThumbImg))
            localRepItem.mThumbImg = null;
          localRepItem.mHideUserInfo = true;
          localArrayList.add(localRepItem);
          RepostModel.getInstance().setMoreRepItemsData(localArrayList);
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return false;
    }
    return false;
  }

  public boolean doGetMoreDataRepostList(Context paramContext, String paramString1, Integer paramInteger, String paramString2, int paramInt)
  {
    String str1 = Protocol.getInstance().makeGetRepostMoreListRequest(User.getInstance().getUID(), paramString1, paramInteger, paramString2, paramInt);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str2 = localHttpProxy.httpGet(str1, null, null);
      if (TextUtils.isEmpty(str2))
        return false;
      JSONObject localJSONObject1 = parseJSON(paramContext, str2);
      if ((localJSONObject1 != null) && (this.ret == 1))
      {
        JSONArray localJSONArray = localJSONObject1.getJSONArray("replists");
        ArrayList localArrayList = new ArrayList();
        for (int i = 0; ; i++)
        {
          if (i >= localJSONArray.length())
            return true;
          JSONObject localJSONObject2 = (JSONObject)localJSONArray.get(i);
          RepItem localRepItem = new RepItem();
          localRepItem.id = localJSONObject2.getString("id");
          localRepItem.title = localJSONObject2.getString("title");
          localRepItem.category = localJSONObject2.getInt("category");
          localRepItem.stime = localJSONObject2.optString("strctime", "");
          localRepItem.mContent = localJSONObject2.optString("abstract", "");
          localRepItem.mRepostNum = localJSONObject2.optInt("rpnum");
          localRepItem.mThumbImg = localJSONObject2.optString("thumbimg", "");
          if ("null".equals(localRepItem.mThumbImg))
            localRepItem.mThumbImg = null;
          localRepItem.mHideUserInfo = true;
          localArrayList.add(localRepItem);
          SharedPostModel.getInstance(paramString1).setMoreItems(localArrayList);
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return false;
    }
    return false;
  }

  // ERROR //
  public boolean doGetMoreRepost(Context paramContext, Integer paramInteger, String paramString, int paramInt)
  {
    // Byte code:
    //   0: iconst_0
    //   1: istore 5
    //   3: invokestatic 47	com/kaixin001/network/Protocol:getInstance	()Lcom/kaixin001/network/Protocol;
    //   6: invokestatic 52	com/kaixin001/model/User:getInstance	()Lcom/kaixin001/model/User;
    //   9: invokevirtual 56	com/kaixin001/model/User:getUID	()Ljava/lang/String;
    //   12: aload_2
    //   13: aload_3
    //   14: iload 4
    //   16: invokevirtual 204	com/kaixin001/network/Protocol:makeGetMoreRepostListRequest	(Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;I)Ljava/lang/String;
    //   19: astore 6
    //   21: new 62	com/kaixin001/network/HttpProxy
    //   24: dup
    //   25: aload_1
    //   26: invokespecial 65	com/kaixin001/network/HttpProxy:<init>	(Landroid/content/Context;)V
    //   29: astore 7
    //   31: aload 7
    //   33: aload 6
    //   35: aconst_null
    //   36: aconst_null
    //   37: invokevirtual 69	com/kaixin001/network/HttpProxy:httpGet	(Ljava/lang/String;Lcom/kaixin001/network/HttpRequestState;Lcom/kaixin001/network/HttpProgressListener;)Ljava/lang/String;
    //   40: astore 20
    //   42: aload 20
    //   44: astore 9
    //   46: aload 9
    //   48: ifnonnull +6 -> 54
    //   51: iconst_0
    //   52: istore 5
    //   54: aload_0
    //   55: aload_1
    //   56: aload 9
    //   58: invokevirtual 79	com/kaixin001/engine/RepostEngine:parseJSON	(Landroid/content/Context;Ljava/lang/String;)Lorg/json/JSONObject;
    //   61: astore 12
    //   63: aload 12
    //   65: ldc 205
    //   67: invokevirtual 145	org/json/JSONObject:optInt	(Ljava/lang/String;)I
    //   70: istore 13
    //   72: aload 12
    //   74: ifnull +9 -> 83
    //   77: iload 13
    //   79: iconst_1
    //   80: if_icmpeq +22 -> 102
    //   83: iconst_0
    //   84: istore 5
    //   86: iload 5
    //   88: ireturn
    //   89: astore 8
    //   91: aload 8
    //   93: invokevirtual 180	java/lang/Exception:printStackTrace	()V
    //   96: aconst_null
    //   97: astore 9
    //   99: goto -53 -> 46
    //   102: aload 12
    //   104: ldc 84
    //   106: invokevirtual 90	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   109: astore 14
    //   111: new 92	java/util/ArrayList
    //   114: dup
    //   115: invokespecial 93	java/util/ArrayList:<init>	()V
    //   118: astore 15
    //   120: iconst_0
    //   121: istore 16
    //   123: iload 16
    //   125: aload 14
    //   127: invokevirtual 99	org/json/JSONArray:length	()I
    //   130: if_icmpge -44 -> 86
    //   133: aload 14
    //   135: iload 16
    //   137: invokevirtual 103	org/json/JSONArray:get	(I)Ljava/lang/Object;
    //   140: checkcast 86	org/json/JSONObject
    //   143: astore 17
    //   145: new 105	com/kaixin001/item/RepItem
    //   148: dup
    //   149: invokespecial 106	com/kaixin001/item/RepItem:<init>	()V
    //   152: astore 18
    //   154: aload 18
    //   156: aload 17
    //   158: ldc 108
    //   160: invokevirtual 112	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   163: putfield 114	com/kaixin001/item/RepItem:id	Ljava/lang/String;
    //   166: aload 18
    //   168: aload 17
    //   170: ldc 116
    //   172: invokevirtual 112	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   175: putfield 118	com/kaixin001/item/RepItem:title	Ljava/lang/String;
    //   178: aload 18
    //   180: aload 17
    //   182: ldc 120
    //   184: invokevirtual 124	org/json/JSONObject:getInt	(Ljava/lang/String;)I
    //   187: putfield 126	com/kaixin001/item/RepItem:category	I
    //   190: aload 18
    //   192: aload 17
    //   194: ldc 128
    //   196: invokevirtual 112	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   199: putfield 135	com/kaixin001/item/RepItem:stime	Ljava/lang/String;
    //   202: aload 18
    //   204: aload 17
    //   206: ldc 207
    //   208: invokevirtual 211	org/json/JSONObject:optLong	(Ljava/lang/String;)J
    //   211: putfield 214	com/kaixin001/item/RepItem:ctime	J
    //   214: aload 18
    //   216: aload 17
    //   218: ldc 216
    //   220: invokevirtual 220	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
    //   223: ldc 222
    //   225: invokevirtual 112	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   228: putfield 225	com/kaixin001/item/RepItem:flogo	Ljava/lang/String;
    //   231: aload 18
    //   233: aload 17
    //   235: ldc 227
    //   237: invokevirtual 112	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   240: putfield 230	com/kaixin001/item/RepItem:fname	Ljava/lang/String;
    //   243: aload 18
    //   245: aload 17
    //   247: ldc 232
    //   249: invokevirtual 112	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   252: putfield 235	com/kaixin001/item/RepItem:fuid	Ljava/lang/String;
    //   255: aload 18
    //   257: aload 17
    //   259: ldc 137
    //   261: ldc 25
    //   263: invokevirtual 132	org/json/JSONObject:optString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   266: putfield 140	com/kaixin001/item/RepItem:mContent	Ljava/lang/String;
    //   269: aload 18
    //   271: getfield 140	com/kaixin001/item/RepItem:mContent	Ljava/lang/String;
    //   274: invokestatic 75	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   277: ifeq +17 -> 294
    //   280: aload 18
    //   282: aload 17
    //   284: ldc 237
    //   286: ldc 25
    //   288: invokevirtual 132	org/json/JSONObject:optString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   291: putfield 140	com/kaixin001/item/RepItem:mContent	Ljava/lang/String;
    //   294: aload 18
    //   296: aload 17
    //   298: ldc 239
    //   300: ldc 25
    //   302: invokevirtual 132	org/json/JSONObject:optString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   305: putfield 153	com/kaixin001/item/RepItem:mThumbImg	Ljava/lang/String;
    //   308: aload 18
    //   310: getfield 153	com/kaixin001/item/RepItem:mThumbImg	Ljava/lang/String;
    //   313: invokestatic 75	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   316: ifne +16 -> 332
    //   319: ldc 155
    //   321: aload 18
    //   323: getfield 153	com/kaixin001/item/RepItem:mThumbImg	Ljava/lang/String;
    //   326: invokevirtual 161	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   329: ifeq +17 -> 346
    //   332: aload 18
    //   334: aload 17
    //   336: ldc 150
    //   338: ldc 25
    //   340: invokevirtual 132	org/json/JSONObject:optString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   343: putfield 153	com/kaixin001/item/RepItem:mThumbImg	Ljava/lang/String;
    //   346: ldc 155
    //   348: aload 18
    //   350: getfield 153	com/kaixin001/item/RepItem:mThumbImg	Ljava/lang/String;
    //   353: invokevirtual 161	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   356: ifeq +9 -> 365
    //   359: aload 18
    //   361: aconst_null
    //   362: putfield 153	com/kaixin001/item/RepItem:mThumbImg	Ljava/lang/String;
    //   365: aload 18
    //   367: aload 17
    //   369: ldc 142
    //   371: invokevirtual 145	org/json/JSONObject:optInt	(Ljava/lang/String;)I
    //   374: putfield 148	com/kaixin001/item/RepItem:mRepostNum	I
    //   377: aload 15
    //   379: aload 18
    //   381: invokeinterface 242 2 0
    //   386: pop
    //   387: ldc 244
    //   389: invokestatic 190	com/kaixin001/model/SharedPostModel:getInstance	(Ljava/lang/String;)Lcom/kaixin001/model/SharedPostModel;
    //   392: aload 15
    //   394: invokevirtual 194	com/kaixin001/model/SharedPostModel:setMoreItems	(Ljava/util/List;)V
    //   397: iconst_1
    //   398: istore 5
    //   400: iinc 16 1
    //   403: goto -280 -> 123
    //   406: astore 11
    //   408: aload 11
    //   410: invokevirtual 245	com/kaixin001/engine/SecurityErrorException:printStackTrace	()V
    //   413: iload 5
    //   415: ireturn
    //   416: astore 10
    //   418: aload 10
    //   420: invokevirtual 246	org/json/JSONException:printStackTrace	()V
    //   423: iload 5
    //   425: ireturn
    //
    // Exception table:
    //   from	to	target	type
    //   31	42	89	java/lang/Exception
    //   54	72	406	com/kaixin001/engine/SecurityErrorException
    //   102	120	406	com/kaixin001/engine/SecurityErrorException
    //   123	294	406	com/kaixin001/engine/SecurityErrorException
    //   294	332	406	com/kaixin001/engine/SecurityErrorException
    //   332	346	406	com/kaixin001/engine/SecurityErrorException
    //   346	365	406	com/kaixin001/engine/SecurityErrorException
    //   365	397	406	com/kaixin001/engine/SecurityErrorException
    //   54	72	416	org/json/JSONException
    //   102	120	416	org/json/JSONException
    //   123	294	416	org/json/JSONException
    //   294	332	416	org/json/JSONException
    //   332	346	416	org/json/JSONException
    //   346	365	416	org/json/JSONException
    //   365	397	416	org/json/JSONException
  }

  public boolean doGetRepostDetail(Context paramContext, String paramString1, String paramString2)
    throws SecurityErrorException
  {
    int i = paramContext.getResources().getDisplayMetrics().widthPixels;
    String str1 = Protocol.getInstance().makeGetRepostDetailRequest(paramContext, paramString1, paramString2, User.getInstance().getUID(), i);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str8 = localHttpProxy.httpGet(str1, null, null);
      str2 = str8;
      if (str2 == null)
      {
        j = 0;
        if (j == 0)
          RepostModel.getInstance().clear();
        return j;
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        int j;
        KXLog.e("RepostEngine", "doGetRepostDetail error", localException);
        String str2 = null;
        continue;
        JSONObject localJSONObject = super.parseJSON(paramContext, str2);
        if (localJSONObject == null)
        {
          j = 0;
          continue;
        }
        try
        {
          int k = this.ret;
          if (k == 1)
          {
            RepostModel.getInstance().setRepostId(paramString1);
            RepostModel.getInstance().setRepostFuid(paramString2);
            String str3 = localJSONObject.getString("content");
            int m = localJSONObject.getInt("vnum");
            RepostModel.getInstance().mTitle = localJSONObject.optString("title", "");
            RepostModel.getInstance().mDes = localJSONObject.optString("des", "");
            if (TextUtils.isEmpty(RepostModel.getInstance().mDes))
              RepostModel.getInstance().mDes = RepostModel.getInstance().mTitle;
            RepostModel.getInstance().mImageUrl = localJSONObject.optString("image", "");
            RepostModel.getInstance().mWapUrl = localJSONObject.optString("wapUrl", "");
            JSONArray localJSONArray1 = localJSONObject.getJSONArray("answerlist");
            JSONArray localJSONArray2 = localJSONObject.getJSONArray("resultlist");
            JSONArray localJSONArray3 = localJSONObject.getJSONArray("taglist");
            String str4 = localJSONObject.getString("urpid");
            String str5 = localJSONObject.getString("voteuid");
            String str6 = localJSONObject.getString("suid");
            String str7 = localJSONObject.getString("surpid");
            int n = localJSONObject.getInt("newflag");
            int i1 = localJSONObject.getInt("cnum");
            RepostModel.getInstance().setRepostContent(str3);
            RepostModel.getInstance().setRpNum(m);
            RepostModel.getInstance().setAnswerList(localJSONArray1);
            RepostModel.getInstance().setResultList(localJSONArray2);
            RepostModel.getInstance().setTagList(localJSONArray3);
            RepostModel.getInstance().setRepostSuid(str6);
            RepostModel.getInstance().setRepostSurpid(str7);
            RepostModel.getInstance().setRepostUrpid(str4);
            RepostModel.getInstance().setRepostVoteuid(str5);
            RepostModel.getInstance().setNewflag(n);
            RepostModel.getInstance().setCNum(i1);
            j = 1;
            continue;
          }
          if (k == 4001)
          {
            RepostModel.getInstance().setErrorNum(localJSONObject.optInt("errno"));
            j = 0;
            continue;
          }
          if (k == -5)
          {
            RepostModel.getInstance().setErrorNum(localJSONObject.optInt("errno"));
            j = 0;
            continue;
          }
          j = 0;
        }
        catch (JSONException localJSONException)
        {
          localJSONException.printStackTrace();
          j = 0;
        }
      }
    }
  }

  public Boolean doGetRepostList(Context paramContext, String paramString1, String paramString2, Integer paramInteger, String paramString3)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeGetRepostListRequest(User.getInstance().getUID(), paramString1, paramString2, paramInteger, paramString3);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    String str2;
    JSONArray localJSONArray;
    try
    {
      String str6 = localHttpProxy.httpGet(str1, null, null);
      str2 = str6;
      if (TextUtils.isEmpty(str2))
        return Boolean.valueOf(false);
    }
    catch (Exception localException)
    {
      while (true)
      {
        KXLog.e("RepostEngine", "doGetRepostList error", localException);
        str2 = null;
      }
      JSONObject localJSONObject;
      try
      {
        Thread.sleep(0L);
        localJSONObject = parseJSON(paramContext, str2);
        if ((localJSONObject == null) || (this.ret != 1))
          return Boolean.valueOf(false);
      }
      catch (InterruptedException localInterruptedException)
      {
        return Boolean.valueOf(false);
      }
      try
      {
        int i = localJSONObject.optInt("total", 10);
        SharedPostModel.getInstance(paramString2).setTotal(i);
        localJSONArray = localJSONObject.getJSONArray("replists");
        int j = localJSONArray.length();
        if ((j == 0) && (paramString3 == null))
          return null;
        if (j == 0)
        {
          SharedPostModel localSharedPostModel = SharedPostModel.getInstance(paramString2);
          ArrayList localArrayList = localSharedPostModel.getRepostList();
          if (localArrayList != null)
            localSharedPostModel.setTotal(localArrayList.size());
          while (true)
          {
            return Boolean.valueOf(true);
            localSharedPostModel.setTotal(0);
          }
        }
      }
      catch (JSONException localJSONException1)
      {
        localJSONException1.printStackTrace();
        return Boolean.valueOf(false);
      }
      if (paramString1 != null);
    }
    while (true)
    {
      try
      {
        procRepostsWithType(localJSONArray, paramString2, false, paramInteger.intValue());
        if (paramString1 != null)
          continue;
        String str3 = FileUtil.getKXCacheDir(paramContext);
        String str4 = User.getInstance().getUID();
        str5 = "";
        if ("normal".equals(paramString2))
        {
          str5 = this.FRIENDS_FREPOST_FILE;
          FileUtil.setCacheData(str3, str4, str5, str2);
          return Boolean.valueOf(true);
          procRepostsWithType(localJSONArray, paramString2, true, paramInteger.intValue());
          continue;
        }
      }
      catch (JSONException localJSONException2)
      {
        localJSONException2.printStackTrace();
        return Boolean.valueOf(false);
      }
      if (!"hot".equals(paramString2))
        continue;
      String str5 = this.HOT_REPOST_FILE;
    }
  }

  public boolean doGetRepostList(Context paramContext, String paramString1, String paramString2)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeGetRepostListRequest(paramString1, paramString2, "", User.getInstance().getUID(), 10);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (TextUtils.isEmpty(str2))
        return false;
    }
    catch (Exception localException)
    {
      String str2;
      while (true)
      {
        KXLog.e("RepostEngine", "doGetRepostList error", localException);
        str2 = null;
      }
      JSONObject localJSONObject = parseJSON(paramContext, str2);
      if ((localJSONObject == null) || (this.ret != 1))
        return false;
      try
      {
        JSONArray localJSONArray = localJSONObject.getJSONArray("replists");
        int i = localJSONObject.optInt("total", 0);
        RepostModel.getInstance().setAllTotal(i);
        procNormailReposts(localJSONArray, paramString2);
        return true;
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
      }
    }
    return false;
  }

  public boolean doGetRepostRelevantLink(Context paramContext, String paramString1, String paramString2)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makePostReleLinkRequest(paramString1, paramString2);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (str2 == null)
      {
        i = 0;
        if (i == 0)
          RepostModel.getInstance().clearDataList();
        return i;
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        localException.printStackTrace();
        String str2 = null;
        continue;
        JSONObject localJSONObject = super.parseJSON(paramContext, str2);
        if (localJSONObject == null)
        {
          i = 0;
          continue;
        }
        int j = this.ret;
        int i = 0;
        if (j != 1)
          continue;
        i = 1;
        try
        {
          JSONArray localJSONArray = localJSONObject.getJSONArray("data");
          RepostModel.getInstance().setDataList(localJSONArray);
        }
        catch (JSONException localJSONException)
        {
          localJSONException.printStackTrace();
        }
      }
    }
  }

  public boolean doRepost(BaseFragment paramBaseFragment, String paramString1, String paramString2)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeRepostRequest(paramString1, paramString2, User.getInstance().getUID());
    Context localContext = paramBaseFragment.getActivity().getApplicationContext();
    HttpProxy localHttpProxy = new HttpProxy(localContext);
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (str2 == null)
        return false;
    }
    catch (Exception localException1)
    {
      String str2;
      while (true)
      {
        KXLog.e("RepostEngine", "doRepost error", localException1);
        str2 = null;
      }
      JSONObject localJSONObject1 = super.parseJSON(localContext, str2);
      try
      {
        JSONObject localJSONObject2 = localJSONObject1.optJSONObject("upgradeInfo");
        if (localJSONObject2 != null)
          paramBaseFragment.setUpgradeInfoData(localJSONObject2);
        while (true)
        {
          paramBaseFragment.getHandler().sendEmptyMessageDelayed(1, 200L);
          break;
          BaseFragment.getBaseFragment().isShowLevelToast(false);
        }
      }
      catch (Exception localException2)
      {
        BaseFragment.getBaseFragment().isShowLevelToast(false);
        localException2.printStackTrace();
        while (localJSONObject1 != null)
          return this.ret == 1;
      }
    }
    return false;
  }

  public boolean doRepostDiary(Context paramContext, String paramString1, String paramString2)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeRepostDiaryRequest(paramString1, paramString2, User.getInstance().getUID());
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (str2 == null)
        return false;
    }
    catch (Exception localException)
    {
      String str2;
      while (true)
      {
        KXLog.e("RepostEngine", "doRepostDiary error", localException);
        str2 = null;
      }
      if (super.parseJSON(paramContext, str2) == null)
        return false;
      if (this.ret == 1)
        return true;
    }
    return false;
  }

  public boolean doRepostVote(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeRepostVoteRequest(paramString1, paramString2, paramString3, paramString4, paramString5, User.getInstance().getUID());
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    JSONObject localJSONObject;
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (str2 == null)
        return false;
    }
    catch (Exception localException)
    {
      String str2;
      while (true)
      {
        KXLog.e("RepostEngine", "doRepostVote error", localException);
        str2 = null;
      }
      localJSONObject = super.parseJSON(paramContext, str2);
      if (localJSONObject == null)
        return false;
      if (this.ret == 1)
        return true;
    }
    try
    {
      this.mLastError = localJSONObject.getString("error");
      return false;
    }
    catch (JSONException localJSONException)
    {
      while (true)
        localJSONException.printStackTrace();
    }
  }

  public boolean doSubmitRepostTag(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeSubmitRepostTagRequest(paramString1, paramString2, paramString3, paramString4, User.getInstance().getUID());
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    JSONObject localJSONObject;
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (str2 == null)
        return false;
    }
    catch (Exception localException)
    {
      String str2;
      while (true)
      {
        KXLog.e("RepostEngine", "doSubmitRepostTag error", localException);
        str2 = null;
      }
      localJSONObject = super.parseJSON(paramContext, str2);
      if (localJSONObject == null)
        return false;
      if (this.ret == 1)
        return true;
    }
    try
    {
      this.mLastError = localJSONObject.getString("error");
      return false;
    }
    catch (JSONException localJSONException)
    {
      while (true)
        localJSONException.printStackTrace();
    }
  }

  public String getLastError()
  {
    return this.mLastError;
  }

  public boolean loadRepostCache(Context paramContext, String paramString1, String paramString2, String paramString3)
    throws SecurityErrorException
  {
    if ((paramContext == null) || (paramString1 != null) || (TextUtils.isEmpty(paramString3)))
      return false;
    String str1 = FileUtil.getKXCacheDir(paramContext);
    String str2 = "";
    if ("normal".equals(paramString2))
      str2 = this.FRIENDS_FREPOST_FILE;
    while (true)
    {
      String str3 = FileUtil.getCacheData(str1, paramString3, str2);
      if (TextUtils.isEmpty(str3))
        break;
      JSONObject localJSONObject = parseJSON(paramContext, str3);
      if ((localJSONObject == null) || (this.ret != 1))
        break;
      JSONArray localJSONArray = localJSONObject.optJSONArray("replists");
      int i;
      if (localJSONArray == null)
      {
        i = 0;
        if (i == 0)
          break;
        SharedPostModel.getInstance(paramString2).setTotal(i);
      }
      try
      {
        procRepostsWithType(localJSONArray, paramString2, true, 10);
        return true;
        if (!"hot".equals(paramString2))
          continue;
        str2 = this.HOT_REPOST_FILE;
        continue;
        i = localJSONArray.length();
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
      }
    }
    return false;
  }

  protected void procNormailReposts(JSONArray paramJSONArray, String paramString)
    throws JSONException
  {
    if ((paramJSONArray == null) || (paramString == null))
      return;
    ArrayList localArrayList = new ArrayList();
    int i = paramJSONArray.length();
    for (int j = 0; ; j++)
    {
      if (j >= i)
      {
        RepostModel.getInstance().setMoreRepostList(localArrayList, paramString);
        return;
      }
      JSONObject localJSONObject = (JSONObject)paramJSONArray.get(j);
      RepItem localRepItem = new RepItem();
      localRepItem.id = localJSONObject.getString("id");
      localRepItem.title = localJSONObject.getString("title");
      localRepItem.category = localJSONObject.getInt("category");
      localRepItem.stime = localJSONObject.optString("strctime", "");
      localRepItem.mContent = localJSONObject.optString("abstract", "");
      localRepItem.mRepostNum = localJSONObject.optInt("rpnum");
      localRepItem.mThumbImg = localJSONObject.optString("thumbimg", "");
      if ("null".equals(localRepItem.mThumbImg))
        localRepItem.mThumbImg = null;
      localRepItem.mHideUserInfo = true;
      localArrayList.add(localRepItem);
    }
  }

  protected void procRepostsWithType(JSONArray paramJSONArray, String paramString, boolean paramBoolean, int paramInt)
    throws JSONException
  {
    if (paramJSONArray == null)
      return;
    SharedPostModel localSharedPostModel = SharedPostModel.getInstance(paramString);
    int i = paramJSONArray.length();
    ArrayList localArrayList = new ArrayList();
    for (int j = 0; ; j++)
    {
      if (j >= i)
      {
        if (!paramBoolean)
          break;
        localSharedPostModel.appendPosts(localArrayList, paramInt);
        return;
      }
      JSONObject localJSONObject = (JSONObject)paramJSONArray.get(j);
      RepItem localRepItem = new RepItem();
      localRepItem.id = localJSONObject.getString("id");
      localRepItem.title = localJSONObject.getString("title");
      localRepItem.category = localJSONObject.getInt("category");
      localRepItem.stime = localJSONObject.getString("strctime");
      localRepItem.ctime = localJSONObject.optLong("ctime");
      localRepItem.flogo = localJSONObject.getJSONObject("friend_logo").getString("logo50");
      localRepItem.fname = localJSONObject.getString("friend_name");
      localRepItem.fuid = localJSONObject.getString("friend_uid");
      localRepItem.mContent = localJSONObject.optString("abstract", "");
      if (TextUtils.isEmpty(localRepItem.mContent))
        localRepItem.mContent = localJSONObject.optString("intro", "");
      localRepItem.mThumbImg = localJSONObject.optString("img", "");
      if ((TextUtils.isEmpty(localRepItem.mThumbImg)) || ("null".equals(localRepItem.mThumbImg)))
        localRepItem.mThumbImg = localJSONObject.optString("thumbimg", "");
      if ("null".equals(localRepItem.mThumbImg))
        localRepItem.mThumbImg = null;
      localRepItem.mRepostNum = localJSONObject.optInt("rpnum");
      localRepItem.mHideUserInfo = "hot".equals(paramString);
      localArrayList.add(localRepItem);
    }
    localSharedPostModel.setPosts(localArrayList, paramInt);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.RepostEngine
 * JD-Core Version:    0.6.0
 */