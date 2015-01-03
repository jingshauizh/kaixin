package com.kaixin001.engine;

import com.kaixin001.model.KaixinUser;
import org.json.JSONObject;

public class LbsEngine extends KXEngine
{
  private static final String ACTIVITY_AT_NUM = "atnum";
  private static final String ACTIVITY_AWARD_ID = "awardid";
  private static final String ACTIVITY_DEFAULT_WORD = "word";
  private static final String ACTIVITY_DETAIL = "detail";
  private static final String ACTIVITY_ID = "aid";
  private static final String ACTIVITY_JOIN = "join";
  private static final String ACTIVITY_NAME = "name";
  private static final String ACTIVITY_POI_ID = "poiid";
  private static final String ACTIVITY_POI_NAME = "poiname";
  private static final String ACTIVITY_STYPE = "stype";
  private static final String ACTIVITY_TYPE = "stype";
  private static final String ACTIVITY_URL = "wap_url";
  private static final String ACTTVITY_IS_MY_CHECKED = "my_checked";
  private static final String ACTTVITY_REWARD_TOTAL = "my_total_rewards";
  private static final String CHECKIN_ID = "wid";
  private static final String CHECKS = "checks";
  private static final String LOGTAG = "LbsEngine";
  private static final String MUTUAL_FRIEND = "mutualFriends";
  public static final int NUM = 50;
  private static final String POI = "poi";
  private static final String POIS = "pois";
  private static final String POI_ACTIVITY = "activity";
  private static final String POI_DISTANCE = "distance";
  private static final String POI_HAS_ACTIVITY = "have_activity";
  private static final String POI_ID = "poiid";
  private static final String POI_LOCAL_CHECKIN = "local_checkin";
  private static final String POI_LOCATION = "location";
  private static final String POI_MAP = "map";
  private static final String POI_NAME = "name";
  private static final String POI_X = "x";
  private static final String POI_Y = "y";
  private static final String TOTAL = "total";
  private static final String USERS = "users";
  private static LbsEngine sInstance = null;

  private static KaixinUser convertToKaixinUser(JSONObject paramJSONObject)
  {
    if (paramJSONObject == null)
      return null;
    KaixinUser localKaixinUser = new KaixinUser();
    localKaixinUser.uid = paramJSONObject.optString("uid", "");
    localKaixinUser.realname = paramJSONObject.optString("name", "");
    localKaixinUser.gender = paramJSONObject.optInt("gender", 0);
    localKaixinUser.online = paramJSONObject.optInt("online", 0);
    localKaixinUser.logo = paramJSONObject.optString("logo");
    localKaixinUser.relation = paramJSONObject.optInt("isFriend", -1);
    return localKaixinUser;
  }

  public static final LbsEngine getInstance()
  {
    monitorenter;
    try
    {
      if (sInstance == null)
        sInstance = new LbsEngine();
      LbsEngine localLbsEngine = sInstance;
      return localLbsEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  // ERROR //
  public boolean getPoiCheckIn(com.kaixin001.fragment.BaseFragment paramBaseFragment, String paramString1, String paramString2, int paramInt1, int paramInt2, com.kaixin001.model.LbsModel.CheckInList paramCheckInList)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual 164	com/kaixin001/fragment/BaseFragment:getActivity	()Landroid/support/v4/app/FragmentActivity;
    //   4: invokevirtual 170	android/support/v4/app/FragmentActivity:getApplicationContext	()Landroid/content/Context;
    //   7: astore 7
    //   9: aload 6
    //   11: ifnonnull +5 -> 16
    //   14: iconst_0
    //   15: ireturn
    //   16: iload 4
    //   18: ifne +8 -> 26
    //   21: aload 6
    //   23: invokevirtual 175	com/kaixin001/model/LbsModel$CheckInList:clear	()V
    //   26: invokestatic 180	com/kaixin001/network/Protocol:getInstance	()Lcom/kaixin001/network/Protocol;
    //   29: aload_2
    //   30: aload_3
    //   31: iload 4
    //   33: iload 5
    //   35: invokevirtual 184	com/kaixin001/network/Protocol:makeLbsGetCheckInRequest	(Ljava/lang/String;Ljava/lang/String;II)Ljava/lang/String;
    //   38: astore 8
    //   40: new 186	com/kaixin001/network/HttpProxy
    //   43: dup
    //   44: aload 7
    //   46: invokespecial 189	com/kaixin001/network/HttpProxy:<init>	(Landroid/content/Context;)V
    //   49: astore 9
    //   51: aload 9
    //   53: aload 8
    //   55: aconst_null
    //   56: aconst_null
    //   57: invokevirtual 193	com/kaixin001/network/HttpProxy:httpGet	(Ljava/lang/String;Lcom/kaixin001/network/HttpRequestState;Lcom/kaixin001/network/HttpProgressListener;)Ljava/lang/String;
    //   60: astore 34
    //   62: aload 34
    //   64: astore 11
    //   66: aload_0
    //   67: aload 7
    //   69: aload 11
    //   71: invokespecial 197	com/kaixin001/engine/KXEngine:parseJSON	(Landroid/content/Context;Ljava/lang/String;)Lorg/json/JSONObject;
    //   74: astore 13
    //   76: aload 13
    //   78: ifnull +15 -> 93
    //   81: aload_0
    //   82: invokevirtual 201	com/kaixin001/engine/LbsEngine:getRet	()I
    //   85: istore 14
    //   87: iload 14
    //   89: iconst_1
    //   90: if_icmpeq +22 -> 112
    //   93: iconst_0
    //   94: ireturn
    //   95: astore 10
    //   97: ldc 54
    //   99: ldc 203
    //   101: aload 10
    //   103: invokestatic 209	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   106: aconst_null
    //   107: astore 11
    //   109: goto -43 -> 66
    //   112: aload 13
    //   114: ldc 95
    //   116: iconst_0
    //   117: invokevirtual 133	org/json/JSONObject:optInt	(Ljava/lang/String;I)I
    //   120: istore 15
    //   122: aload 6
    //   124: iload 15
    //   126: invokevirtual 213	com/kaixin001/model/LbsModel$CheckInList:setTotal	(I)V
    //   129: aload 13
    //   131: ldc 79
    //   133: iconst_0
    //   134: invokevirtual 133	org/json/JSONObject:optInt	(Ljava/lang/String;I)I
    //   137: iconst_1
    //   138: if_icmpne +442 -> 580
    //   141: iconst_1
    //   142: istore 16
    //   144: aload 6
    //   146: iload 16
    //   148: invokevirtual 217	com/kaixin001/model/LbsModel$CheckInList:setLocalCheckin	(Z)V
    //   151: aload 13
    //   153: ldc 219
    //   155: invokevirtual 223	org/json/JSONObject:optJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
    //   158: astore 17
    //   160: aload 17
    //   162: ifnull +106 -> 268
    //   165: aload_1
    //   166: aload 17
    //   168: invokevirtual 227	com/kaixin001/fragment/BaseFragment:setUpgradeInfoData	(Lorg/json/JSONObject;)V
    //   171: aload_1
    //   172: invokevirtual 231	com/kaixin001/fragment/BaseFragment:getHandler	()Landroid/os/Handler;
    //   175: iconst_1
    //   176: ldc2_w 232
    //   179: invokevirtual 239	android/os/Handler:sendEmptyMessageDelayed	(IJ)Z
    //   182: pop
    //   183: aload 13
    //   185: ldc 98
    //   187: invokevirtual 243	org/json/JSONObject:optJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   190: astore 19
    //   192: new 245	java/util/HashMap
    //   195: dup
    //   196: invokespecial 246	java/util/HashMap:<init>	()V
    //   199: astore 20
    //   201: aload 19
    //   203: ifnull +16 -> 219
    //   206: aload 19
    //   208: invokevirtual 251	org/json/JSONArray:length	()I
    //   211: istore 21
    //   213: iconst_0
    //   214: istore 22
    //   216: goto +342 -> 558
    //   219: aload 13
    //   221: ldc 51
    //   223: invokevirtual 243	org/json/JSONObject:optJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   226: astore 23
    //   228: aload 23
    //   230: ifnull +16 -> 246
    //   233: aload 23
    //   235: invokevirtual 251	org/json/JSONArray:length	()I
    //   238: istore 24
    //   240: iconst_0
    //   241: istore 25
    //   243: goto +325 -> 568
    //   246: aload 6
    //   248: invokevirtual 254	com/kaixin001/model/LbsModel$CheckInList:size	()I
    //   251: ifeq +8 -> 259
    //   254: iload 15
    //   256: ifne +322 -> 578
    //   259: aload 6
    //   261: iconst_0
    //   262: invokevirtual 213	com/kaixin001/model/LbsModel$CheckInList:setTotal	(I)V
    //   265: goto +313 -> 578
    //   268: invokestatic 258	com/kaixin001/fragment/BaseFragment:getBaseFragment	()Lcom/kaixin001/fragment/BaseFragment;
    //   271: iconst_0
    //   272: invokevirtual 261	com/kaixin001/fragment/BaseFragment:isShowLevelToast	(Z)V
    //   275: goto -104 -> 171
    //   278: astore 12
    //   280: aload 12
    //   282: invokevirtual 264	java/lang/Exception:printStackTrace	()V
    //   285: iconst_0
    //   286: ireturn
    //   287: aload 19
    //   289: iload 22
    //   291: invokevirtual 267	org/json/JSONArray:optJSONObject	(I)Lorg/json/JSONObject;
    //   294: astore 30
    //   296: aload 30
    //   298: ifnonnull +6 -> 304
    //   301: goto +285 -> 586
    //   304: new 269	com/kaixin001/item/CheckInUser
    //   307: dup
    //   308: invokespecial 270	com/kaixin001/item/CheckInUser:<init>	()V
    //   311: astore 31
    //   313: aload 31
    //   315: aload 30
    //   317: invokestatic 272	com/kaixin001/engine/LbsEngine:convertToKaixinUser	(Lorg/json/JSONObject;)Lcom/kaixin001/model/KaixinUser;
    //   320: putfield 276	com/kaixin001/item/CheckInUser:user	Lcom/kaixin001/model/KaixinUser;
    //   323: aload 31
    //   325: getfield 276	com/kaixin001/item/CheckInUser:user	Lcom/kaixin001/model/KaixinUser;
    //   328: ifnull +258 -> 586
    //   331: aload 30
    //   333: ldc 57
    //   335: invokevirtual 223	org/json/JSONObject:optJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
    //   338: astore 32
    //   340: aload 32
    //   342: ifnull +13 -> 355
    //   345: aload 31
    //   347: aload 32
    //   349: invokestatic 272	com/kaixin001/engine/LbsEngine:convertToKaixinUser	(Lorg/json/JSONObject;)Lcom/kaixin001/model/KaixinUser;
    //   352: putfield 279	com/kaixin001/item/CheckInUser:mutualFriend	Lcom/kaixin001/model/KaixinUser;
    //   355: aload 20
    //   357: aload 31
    //   359: getfield 276	com/kaixin001/item/CheckInUser:user	Lcom/kaixin001/model/KaixinUser;
    //   362: getfield 124	com/kaixin001/model/KaixinUser:uid	Ljava/lang/String;
    //   365: aload 31
    //   367: invokevirtual 283	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   370: pop
    //   371: goto +215 -> 586
    //   374: aload 23
    //   376: iload 25
    //   378: invokevirtual 267	org/json/JSONArray:optJSONObject	(I)Lorg/json/JSONObject;
    //   381: astore 26
    //   383: aload 26
    //   385: ifnonnull +6 -> 391
    //   388: goto +204 -> 592
    //   391: aload 20
    //   393: aload 26
    //   395: ldc 114
    //   397: invokevirtual 144	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   400: invokevirtual 287	java/util/HashMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   403: checkcast 269	com/kaixin001/item/CheckInUser
    //   406: astore 27
    //   408: new 289	com/kaixin001/item/CheckInItem
    //   411: dup
    //   412: invokespecial 290	com/kaixin001/item/CheckInItem:<init>	()V
    //   415: astore 28
    //   417: aload 28
    //   419: aload 26
    //   421: ldc 48
    //   423: invokevirtual 144	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   426: putfield 292	com/kaixin001/item/CheckInItem:wid	Ljava/lang/String;
    //   429: aload 28
    //   431: aload 26
    //   433: ldc 29
    //   435: invokevirtual 144	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   438: putfield 295	com/kaixin001/item/CheckInItem:poiId	Ljava/lang/String;
    //   441: aload 28
    //   443: aload 26
    //   445: ldc 32
    //   447: invokevirtual 144	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   450: putfield 298	com/kaixin001/item/CheckInItem:poiName	Ljava/lang/String;
    //   453: aload 28
    //   455: aload 26
    //   457: ldc_w 300
    //   460: invokevirtual 144	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   463: putfield 302	com/kaixin001/item/CheckInItem:content	Ljava/lang/String;
    //   466: aload 28
    //   468: aload 26
    //   470: ldc_w 304
    //   473: ldc_w 306
    //   476: invokevirtual 122	org/json/JSONObject:optString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   479: putfield 308	com/kaixin001/item/CheckInItem:privacy	Ljava/lang/String;
    //   482: aload 28
    //   484: aload 26
    //   486: ldc_w 310
    //   489: lconst_0
    //   490: invokevirtual 314	org/json/JSONObject:optLong	(Ljava/lang/String;J)J
    //   493: putfield 317	com/kaixin001/item/CheckInItem:ctime	J
    //   496: aload 28
    //   498: aload 26
    //   500: ldc_w 319
    //   503: invokevirtual 144	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   506: putfield 321	com/kaixin001/item/CheckInItem:thumbnail	Ljava/lang/String;
    //   509: aload 28
    //   511: aload 26
    //   513: ldc_w 323
    //   516: invokevirtual 144	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   519: putfield 325	com/kaixin001/item/CheckInItem:large	Ljava/lang/String;
    //   522: aload 28
    //   524: aload 26
    //   526: ldc_w 327
    //   529: invokevirtual 144	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   532: putfield 330	com/kaixin001/item/CheckInItem:mMapUrl	Ljava/lang/String;
    //   535: aload 27
    //   537: ifnull +55 -> 592
    //   540: aload 28
    //   542: aload 27
    //   544: putfield 334	com/kaixin001/item/CheckInItem:checkInUser	Lcom/kaixin001/item/CheckInUser;
    //   547: aload 6
    //   549: aload 28
    //   551: invokevirtual 338	com/kaixin001/model/LbsModel$CheckInList:add	(Ljava/lang/Object;)Z
    //   554: pop
    //   555: goto +37 -> 592
    //   558: iload 22
    //   560: iload 21
    //   562: if_icmplt -275 -> 287
    //   565: goto -346 -> 219
    //   568: iload 25
    //   570: iload 24
    //   572: if_icmplt -198 -> 374
    //   575: goto -329 -> 246
    //   578: iconst_1
    //   579: ireturn
    //   580: iconst_0
    //   581: istore 16
    //   583: goto -439 -> 144
    //   586: iinc 22 1
    //   589: goto -31 -> 558
    //   592: iinc 25 1
    //   595: goto -27 -> 568
    //
    // Exception table:
    //   from	to	target	type
    //   51	62	95	java/lang/Exception
    //   66	76	278	java/lang/Exception
    //   81	87	278	java/lang/Exception
    //   112	141	278	java/lang/Exception
    //   144	160	278	java/lang/Exception
    //   165	171	278	java/lang/Exception
    //   171	201	278	java/lang/Exception
    //   206	213	278	java/lang/Exception
    //   219	228	278	java/lang/Exception
    //   233	240	278	java/lang/Exception
    //   246	254	278	java/lang/Exception
    //   259	265	278	java/lang/Exception
    //   268	275	278	java/lang/Exception
    //   287	296	278	java/lang/Exception
    //   304	340	278	java/lang/Exception
    //   345	355	278	java/lang/Exception
    //   355	371	278	java/lang/Exception
    //   374	383	278	java/lang/Exception
    //   391	535	278	java/lang/Exception
    //   540	555	278	java/lang/Exception
  }

  // ERROR //
  public com.kaixin001.item.PoiItem getPoiInfo(android.content.Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 7
    //   3: invokestatic 345	com/kaixin001/model/User:getInstance	()Lcom/kaixin001/model/User;
    //   6: invokevirtual 349	com/kaixin001/model/User:getOauthToken	()Ljava/lang/String;
    //   9: invokestatic 355	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   12: ifne +14 -> 26
    //   15: aload_2
    //   16: invokestatic 355	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   19: ifne +7 -> 26
    //   22: aload_1
    //   23: ifnonnull +5 -> 28
    //   26: aconst_null
    //   27: areturn
    //   28: invokestatic 180	com/kaixin001/network/Protocol:getInstance	()Lcom/kaixin001/network/Protocol;
    //   31: aload_2
    //   32: aload_3
    //   33: aload 4
    //   35: aload 5
    //   37: aload 6
    //   39: invokevirtual 359	com/kaixin001/network/Protocol:makeLbsGetPoiInfoRequest	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   42: astore 8
    //   44: new 186	com/kaixin001/network/HttpProxy
    //   47: dup
    //   48: aload_1
    //   49: invokespecial 189	com/kaixin001/network/HttpProxy:<init>	(Landroid/content/Context;)V
    //   52: astore 9
    //   54: aload 9
    //   56: aload 8
    //   58: aconst_null
    //   59: aconst_null
    //   60: invokevirtual 193	com/kaixin001/network/HttpProxy:httpGet	(Ljava/lang/String;Lcom/kaixin001/network/HttpRequestState;Lcom/kaixin001/network/HttpProgressListener;)Ljava/lang/String;
    //   63: astore 18
    //   65: aload 18
    //   67: astore 11
    //   69: aload_0
    //   70: aload_1
    //   71: aload 11
    //   73: invokespecial 197	com/kaixin001/engine/KXEngine:parseJSON	(Landroid/content/Context;Ljava/lang/String;)Lorg/json/JSONObject;
    //   76: astore 13
    //   78: aload_0
    //   79: getfield 362	com/kaixin001/engine/LbsEngine:ret	I
    //   82: istore 14
    //   84: aconst_null
    //   85: astore 7
    //   87: iload 14
    //   89: iconst_1
    //   90: if_icmpne +277 -> 367
    //   93: aconst_null
    //   94: astore 7
    //   96: aload 13
    //   98: ifnull +269 -> 367
    //   101: aload 13
    //   103: ldc 63
    //   105: invokevirtual 366	org/json/JSONObject:opt	(Ljava/lang/String;)Ljava/lang/Object;
    //   108: checkcast 118	org/json/JSONObject
    //   111: astore 15
    //   113: aload 15
    //   115: ifnonnull +23 -> 138
    //   118: aconst_null
    //   119: areturn
    //   120: astore 10
    //   122: ldc 54
    //   124: ldc_w 368
    //   127: aload 10
    //   129: invokestatic 209	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   132: aconst_null
    //   133: astore 11
    //   135: goto -66 -> 69
    //   138: new 370	com/kaixin001/item/PoiItem
    //   141: dup
    //   142: invokespecial 371	com/kaixin001/item/PoiItem:<init>	()V
    //   145: astore 16
    //   147: aload 16
    //   149: aload_2
    //   150: putfield 372	com/kaixin001/item/PoiItem:poiId	Ljava/lang/String;
    //   153: aload 16
    //   155: aload 15
    //   157: ldc 85
    //   159: invokevirtual 144	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   162: putfield 375	com/kaixin001/item/PoiItem:mapUrl	Ljava/lang/String;
    //   165: aload 16
    //   167: aload 15
    //   169: ldc 26
    //   171: invokevirtual 144	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   174: putfield 377	com/kaixin001/item/PoiItem:name	Ljava/lang/String;
    //   177: aload 16
    //   179: aload 15
    //   181: ldc 82
    //   183: invokevirtual 144	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   186: putfield 379	com/kaixin001/item/PoiItem:location	Ljava/lang/String;
    //   189: aload 16
    //   191: aload 15
    //   193: ldc 72
    //   195: invokevirtual 144	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   198: putfield 381	com/kaixin001/item/PoiItem:distance	Ljava/lang/String;
    //   201: aload 16
    //   203: aload 15
    //   205: ldc 89
    //   207: invokevirtual 144	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   210: putfield 383	com/kaixin001/item/PoiItem:x	Ljava/lang/String;
    //   213: aload 16
    //   215: aload 15
    //   217: ldc 92
    //   219: invokevirtual 144	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   222: putfield 385	com/kaixin001/item/PoiItem:y	Ljava/lang/String;
    //   225: aload 15
    //   227: ldc 69
    //   229: invokevirtual 223	org/json/JSONObject:optJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
    //   232: astore 17
    //   234: aload 17
    //   236: ifnull +153 -> 389
    //   239: aload 16
    //   241: aload 17
    //   243: ldc 20
    //   245: ldc 116
    //   247: invokevirtual 122	org/json/JSONObject:optString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   250: putfield 388	com/kaixin001/item/PoiItem:mActivityId	Ljava/lang/String;
    //   253: aload 16
    //   255: aload 17
    //   257: ldc 26
    //   259: ldc 116
    //   261: invokevirtual 122	org/json/JSONObject:optString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   264: putfield 391	com/kaixin001/item/PoiItem:mActivityName	Ljava/lang/String;
    //   267: aload 16
    //   269: aload 17
    //   271: ldc 39
    //   273: ldc 116
    //   275: invokevirtual 122	org/json/JSONObject:optString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   278: putfield 394	com/kaixin001/item/PoiItem:mWapUrl	Ljava/lang/String;
    //   281: aload 16
    //   283: aload 17
    //   285: ldc 17
    //   287: ldc 116
    //   289: invokevirtual 122	org/json/JSONObject:optString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   292: putfield 397	com/kaixin001/item/PoiItem:mActivityDetail	Ljava/lang/String;
    //   295: aload 16
    //   297: aload 17
    //   299: ldc 35
    //   301: ldc 116
    //   303: invokevirtual 122	org/json/JSONObject:optString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   306: putfield 400	com/kaixin001/item/PoiItem:mActivityType	Ljava/lang/String;
    //   309: aload 16
    //   311: aload 17
    //   313: ldc 14
    //   315: ldc 116
    //   317: invokevirtual 122	org/json/JSONObject:optString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   320: putfield 403	com/kaixin001/item/PoiItem:mActivityDefaultWord	Ljava/lang/String;
    //   323: aload 16
    //   325: aload 17
    //   327: ldc 11
    //   329: ldc 116
    //   331: invokevirtual 122	org/json/JSONObject:optString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   334: putfield 406	com/kaixin001/item/PoiItem:mAwardId	Ljava/lang/String;
    //   337: aload 16
    //   339: aload 17
    //   341: ldc 23
    //   343: iconst_0
    //   344: invokevirtual 410	org/json/JSONObject:optBoolean	(Ljava/lang/String;Z)Z
    //   347: putfield 414	com/kaixin001/item/PoiItem:mJoin	Z
    //   350: aload 16
    //   352: aload 17
    //   354: ldc 8
    //   356: iconst_0
    //   357: invokevirtual 133	org/json/JSONObject:optInt	(Ljava/lang/String;I)I
    //   360: putfield 417	com/kaixin001/item/PoiItem:mAtNum	I
    //   363: aload 16
    //   365: astore 7
    //   367: aload 7
    //   369: areturn
    //   370: astore 12
    //   372: aload 12
    //   374: invokevirtual 264	java/lang/Exception:printStackTrace	()V
    //   377: goto -10 -> 367
    //   380: astore 12
    //   382: aload 16
    //   384: astore 7
    //   386: goto -14 -> 372
    //   389: aload 16
    //   391: astore 7
    //   393: goto -26 -> 367
    //
    // Exception table:
    //   from	to	target	type
    //   54	65	120	java/lang/Exception
    //   69	84	370	java/lang/Exception
    //   101	113	370	java/lang/Exception
    //   138	147	370	java/lang/Exception
    //   147	234	380	java/lang/Exception
    //   239	363	380	java/lang/Exception
  }

  // ERROR //
  public boolean getPois(android.content.Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt1, int paramInt2, String paramString6)
  {
    // Byte code:
    //   0: aload 9
    //   2: invokestatic 355	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   5: ifeq +73 -> 78
    //   8: invokestatic 180	com/kaixin001/network/Protocol:getInstance	()Lcom/kaixin001/network/Protocol;
    //   11: invokestatic 345	com/kaixin001/model/User:getInstance	()Lcom/kaixin001/model/User;
    //   14: invokevirtual 422	com/kaixin001/model/User:getUID	()Ljava/lang/String;
    //   17: aload_2
    //   18: aload_3
    //   19: aload 4
    //   21: aload 5
    //   23: aload 6
    //   25: ldc_w 424
    //   28: iload 7
    //   30: iload 8
    //   32: invokevirtual 428	com/kaixin001/network/Protocol:makeLbsGetPoisRequest	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;II)Ljava/lang/String;
    //   35: astore 10
    //   37: new 186	com/kaixin001/network/HttpProxy
    //   40: dup
    //   41: aload_1
    //   42: invokespecial 189	com/kaixin001/network/HttpProxy:<init>	(Landroid/content/Context;)V
    //   45: astore 11
    //   47: aload 11
    //   49: aload 10
    //   51: aconst_null
    //   52: aconst_null
    //   53: invokevirtual 193	com/kaixin001/network/HttpProxy:httpGet	(Ljava/lang/String;Lcom/kaixin001/network/HttpRequestState;Lcom/kaixin001/network/HttpProgressListener;)Ljava/lang/String;
    //   56: astore 27
    //   58: aload 27
    //   60: astore 13
    //   62: aload_0
    //   63: aload_1
    //   64: aload 13
    //   66: invokespecial 197	com/kaixin001/engine/KXEngine:parseJSON	(Landroid/content/Context;Ljava/lang/String;)Lorg/json/JSONObject;
    //   69: astore 15
    //   71: aload 15
    //   73: ifnonnull +54 -> 127
    //   76: iconst_0
    //   77: ireturn
    //   78: invokestatic 180	com/kaixin001/network/Protocol:getInstance	()Lcom/kaixin001/network/Protocol;
    //   81: invokestatic 345	com/kaixin001/model/User:getInstance	()Lcom/kaixin001/model/User;
    //   84: invokevirtual 422	com/kaixin001/model/User:getUID	()Ljava/lang/String;
    //   87: aload_2
    //   88: aload_3
    //   89: aload 4
    //   91: aload 5
    //   93: aload 6
    //   95: iload 7
    //   97: iload 8
    //   99: aload 9
    //   101: invokevirtual 432	com/kaixin001/network/Protocol:makeLbsSearchPoisRequest	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IILjava/lang/String;)Ljava/lang/String;
    //   104: astore 10
    //   106: goto -69 -> 37
    //   109: astore 12
    //   111: ldc 54
    //   113: ldc_w 434
    //   116: aload 12
    //   118: invokestatic 209	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   121: aconst_null
    //   122: astore 13
    //   124: goto -62 -> 62
    //   127: invokestatic 439	com/kaixin001/model/LbsModel:getInstance	()Lcom/kaixin001/model/LbsModel;
    //   130: astore 16
    //   132: aload 16
    //   134: aload 15
    //   136: ldc 95
    //   138: iconst_0
    //   139: invokevirtual 133	org/json/JSONObject:optInt	(Ljava/lang/String;I)I
    //   142: invokevirtual 440	com/kaixin001/model/LbsModel:setTotal	(I)V
    //   145: aload 16
    //   147: invokevirtual 444	com/kaixin001/model/LbsModel:getPoiList	()Ljava/util/ArrayList;
    //   150: astore 17
    //   152: iload 7
    //   154: ifne +8 -> 162
    //   157: aload 17
    //   159: invokevirtual 447	java/util/ArrayList:clear	()V
    //   162: aload 15
    //   164: ldc 66
    //   166: invokevirtual 243	org/json/JSONObject:optJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   169: astore 18
    //   171: aload 18
    //   173: ifnull +213 -> 386
    //   176: aload 18
    //   178: invokevirtual 251	org/json/JSONArray:length	()I
    //   181: istore 19
    //   183: iconst_0
    //   184: istore 20
    //   186: goto +193 -> 379
    //   189: aload 18
    //   191: iload 20
    //   193: invokevirtual 450	org/json/JSONArray:opt	(I)Ljava/lang/Object;
    //   196: checkcast 118	org/json/JSONObject
    //   199: astore 21
    //   201: aload 21
    //   203: ifnonnull +6 -> 209
    //   206: goto +182 -> 388
    //   209: new 370	com/kaixin001/item/PoiItem
    //   212: dup
    //   213: invokespecial 371	com/kaixin001/item/PoiItem:<init>	()V
    //   216: astore 22
    //   218: aload 22
    //   220: aload 21
    //   222: ldc 29
    //   224: invokevirtual 144	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   227: putfield 372	com/kaixin001/item/PoiItem:poiId	Ljava/lang/String;
    //   230: aload 22
    //   232: aload 21
    //   234: ldc 26
    //   236: invokevirtual 144	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   239: putfield 377	com/kaixin001/item/PoiItem:name	Ljava/lang/String;
    //   242: aload 22
    //   244: aload 21
    //   246: ldc 82
    //   248: invokevirtual 144	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   251: putfield 379	com/kaixin001/item/PoiItem:location	Ljava/lang/String;
    //   254: aload 22
    //   256: aload 21
    //   258: ldc 89
    //   260: invokevirtual 144	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   263: putfield 383	com/kaixin001/item/PoiItem:x	Ljava/lang/String;
    //   266: aload 22
    //   268: aload 21
    //   270: ldc 92
    //   272: invokevirtual 144	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   275: putfield 385	com/kaixin001/item/PoiItem:y	Ljava/lang/String;
    //   278: aload 22
    //   280: aload 21
    //   282: ldc 72
    //   284: invokevirtual 144	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   287: putfield 381	com/kaixin001/item/PoiItem:distance	Ljava/lang/String;
    //   290: aload 21
    //   292: ldc 75
    //   294: iconst_0
    //   295: invokevirtual 133	org/json/JSONObject:optInt	(Ljava/lang/String;I)I
    //   298: iconst_1
    //   299: if_icmpne +68 -> 367
    //   302: iconst_1
    //   303: istore 23
    //   305: aload 22
    //   307: iload 23
    //   309: putfield 453	com/kaixin001/item/PoiItem:hasActivity	Z
    //   312: aload 21
    //   314: ldc 42
    //   316: invokevirtual 223	org/json/JSONObject:optJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
    //   319: astore 24
    //   321: aload 24
    //   323: ifnull +24 -> 347
    //   326: aload 24
    //   328: ldc 95
    //   330: iconst_0
    //   331: invokevirtual 133	org/json/JSONObject:optInt	(Ljava/lang/String;I)I
    //   334: ifeq +39 -> 373
    //   337: iconst_1
    //   338: istore 25
    //   340: aload 22
    //   342: iload 25
    //   344: putfield 456	com/kaixin001/item/PoiItem:isMyChecked	Z
    //   347: aload 17
    //   349: aload 22
    //   351: invokevirtual 457	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   354: pop
    //   355: goto +33 -> 388
    //   358: astore 14
    //   360: aload 14
    //   362: invokevirtual 264	java/lang/Exception:printStackTrace	()V
    //   365: iconst_0
    //   366: ireturn
    //   367: iconst_0
    //   368: istore 23
    //   370: goto -65 -> 305
    //   373: iconst_0
    //   374: istore 25
    //   376: goto -36 -> 340
    //   379: iload 20
    //   381: iload 19
    //   383: if_icmplt -194 -> 189
    //   386: iconst_1
    //   387: ireturn
    //   388: iinc 20 1
    //   391: goto -12 -> 379
    //
    // Exception table:
    //   from	to	target	type
    //   47	58	109	java/lang/Exception
    //   62	71	358	java/lang/Exception
    //   127	152	358	java/lang/Exception
    //   157	162	358	java/lang/Exception
    //   162	171	358	java/lang/Exception
    //   176	183	358	java/lang/Exception
    //   189	201	358	java/lang/Exception
    //   209	302	358	java/lang/Exception
    //   305	321	358	java/lang/Exception
    //   326	337	358	java/lang/Exception
    //   340	347	358	java/lang/Exception
    //   347	355	358	java/lang/Exception
  }

  // ERROR //
  public int getPoisActivityList(android.content.Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt1, int paramInt2)
  {
    // Byte code:
    //   0: iload 8
    //   2: ifge +76 -> 78
    //   5: iconst_1
    //   6: istore 8
    //   8: iconst_0
    //   9: istore 9
    //   11: invokestatic 180	com/kaixin001/network/Protocol:getInstance	()Lcom/kaixin001/network/Protocol;
    //   14: invokestatic 345	com/kaixin001/model/User:getInstance	()Lcom/kaixin001/model/User;
    //   17: invokevirtual 422	com/kaixin001/model/User:getUID	()Ljava/lang/String;
    //   20: aload_2
    //   21: aload_3
    //   22: aload 4
    //   24: aload 5
    //   26: aload 6
    //   28: iload 7
    //   30: iload 8
    //   32: invokevirtual 463	com/kaixin001/network/Protocol:makeLbsGetPoisActivityListRequest	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;II)Ljava/lang/String;
    //   35: astore 10
    //   37: new 186	com/kaixin001/network/HttpProxy
    //   40: dup
    //   41: aload_1
    //   42: invokespecial 189	com/kaixin001/network/HttpProxy:<init>	(Landroid/content/Context;)V
    //   45: astore 11
    //   47: aload 11
    //   49: aload 10
    //   51: aconst_null
    //   52: aconst_null
    //   53: invokevirtual 193	com/kaixin001/network/HttpProxy:httpGet	(Ljava/lang/String;Lcom/kaixin001/network/HttpRequestState;Lcom/kaixin001/network/HttpProgressListener;)Ljava/lang/String;
    //   56: astore 24
    //   58: aload 24
    //   60: astore 13
    //   62: aload_0
    //   63: aload_1
    //   64: aload 13
    //   66: invokespecial 197	com/kaixin001/engine/KXEngine:parseJSON	(Landroid/content/Context;Ljava/lang/String;)Lorg/json/JSONObject;
    //   69: astore 15
    //   71: aload 15
    //   73: ifnonnull +29 -> 102
    //   76: iconst_0
    //   77: ireturn
    //   78: iconst_1
    //   79: istore 9
    //   81: goto -70 -> 11
    //   84: astore 12
    //   86: ldc 54
    //   88: ldc_w 465
    //   91: aload 12
    //   93: invokestatic 209	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   96: aconst_null
    //   97: astore 13
    //   99: goto -37 -> 62
    //   102: invokestatic 439	com/kaixin001/model/LbsModel:getInstance	()Lcom/kaixin001/model/LbsModel;
    //   105: astore 16
    //   107: aload 16
    //   109: aload 15
    //   111: ldc 95
    //   113: iconst_0
    //   114: invokevirtual 133	org/json/JSONObject:optInt	(Ljava/lang/String;I)I
    //   117: invokevirtual 468	com/kaixin001/model/LbsModel:setActivityTotal	(I)V
    //   120: aload 16
    //   122: aload 15
    //   124: ldc 45
    //   126: iconst_0
    //   127: invokevirtual 133	org/json/JSONObject:optInt	(Ljava/lang/String;I)I
    //   130: invokevirtual 471	com/kaixin001/model/LbsModel:setRewardTotal	(I)V
    //   133: aload 16
    //   135: invokevirtual 474	com/kaixin001/model/LbsModel:getActivityList	()Ljava/util/ArrayList;
    //   138: astore 17
    //   140: iload 9
    //   142: ifeq +168 -> 310
    //   145: iload 7
    //   147: ifne +8 -> 155
    //   150: aload 17
    //   152: invokevirtual 447	java/util/ArrayList:clear	()V
    //   155: aload 15
    //   157: ldc 66
    //   159: invokevirtual 243	org/json/JSONObject:optJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   162: astore 18
    //   164: aload 18
    //   166: ifnull +144 -> 310
    //   169: aload 18
    //   171: invokevirtual 251	org/json/JSONArray:length	()I
    //   174: istore 19
    //   176: iconst_0
    //   177: istore 20
    //   179: goto +124 -> 303
    //   182: aload 18
    //   184: iload 20
    //   186: invokevirtual 450	org/json/JSONArray:opt	(I)Ljava/lang/Object;
    //   189: checkcast 118	org/json/JSONObject
    //   192: astore 21
    //   194: aload 21
    //   196: ifnonnull +6 -> 202
    //   199: goto +113 -> 312
    //   202: new 476	com/kaixin001/item/PoiActivityItem
    //   205: dup
    //   206: invokespecial 477	com/kaixin001/item/PoiActivityItem:<init>	()V
    //   209: astore 22
    //   211: aload 22
    //   213: aload 21
    //   215: ldc 29
    //   217: invokevirtual 144	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   220: putfield 480	com/kaixin001/item/PoiActivityItem:mPoiId	Ljava/lang/String;
    //   223: aload 22
    //   225: aload 21
    //   227: ldc 32
    //   229: invokevirtual 144	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   232: putfield 483	com/kaixin001/item/PoiActivityItem:mPoiName	Ljava/lang/String;
    //   235: aload 22
    //   237: aload 21
    //   239: ldc 20
    //   241: invokevirtual 144	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   244: putfield 484	com/kaixin001/item/PoiActivityItem:mActivityId	Ljava/lang/String;
    //   247: aload 22
    //   249: aload 21
    //   251: ldc 26
    //   253: invokevirtual 144	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   256: putfield 485	com/kaixin001/item/PoiActivityItem:mActivityName	Ljava/lang/String;
    //   259: aload 22
    //   261: aload 21
    //   263: ldc 39
    //   265: invokevirtual 144	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   268: putfield 488	com/kaixin001/item/PoiActivityItem:mUrl	Ljava/lang/String;
    //   271: aload 22
    //   273: aload 21
    //   275: ldc 35
    //   277: invokevirtual 144	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   280: putfield 491	com/kaixin001/item/PoiActivityItem:mType	Ljava/lang/String;
    //   283: aload 17
    //   285: aload 22
    //   287: invokevirtual 457	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   290: pop
    //   291: goto +21 -> 312
    //   294: astore 14
    //   296: aload 14
    //   298: invokevirtual 264	java/lang/Exception:printStackTrace	()V
    //   301: iconst_0
    //   302: ireturn
    //   303: iload 20
    //   305: iload 19
    //   307: if_icmplt -125 -> 182
    //   310: iconst_1
    //   311: ireturn
    //   312: iinc 20 1
    //   315: goto -12 -> 303
    //
    // Exception table:
    //   from	to	target	type
    //   47	58	84	java/lang/Exception
    //   62	71	294	java/lang/Exception
    //   102	140	294	java/lang/Exception
    //   150	155	294	java/lang/Exception
    //   155	164	294	java/lang/Exception
    //   169	176	294	java/lang/Exception
    //   182	194	294	java/lang/Exception
    //   202	291	294	java/lang/Exception
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.LbsEngine
 * JD-Core Version:    0.6.0
 */