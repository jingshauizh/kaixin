package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.fragment.BaseFragment;
import com.kaixin001.item.VoiceRecordUploadTask;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProgressListener;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.StringUtil;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class RecordEngine extends KXEngine
{
  public static final int CODE_ERROR_DUPLICATED = 216;
  public static final int CODE_ERROR_OTHER = 0;
  public static final int CODE_ERROR_TOO_FREQUENT = 218;
  public static final int CODE_OK = 1;
  private static final String LOGTAG = "RecordEngine";
  private static RecordEngine instance;
  private String msRetRecordId = null;

  public static RecordEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new RecordEngine();
      RecordEngine localRecordEngine = instance;
      return localRecordEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  // ERROR //
  private boolean parseRecordDetailJSON(Context paramContext, String paramString)
    throws JSONException
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: aload_2
    //   3: invokespecial 43	com/kaixin001/engine/KXEngine:parseJSON	(Landroid/content/Context;Ljava/lang/String;)Lorg/json/JSONObject;
    //   6: astore 4
    //   8: aload 4
    //   10: ifnonnull +5 -> 15
    //   13: iconst_0
    //   14: ireturn
    //   15: aload_0
    //   16: getfield 46	com/kaixin001/engine/RecordEngine:ret	I
    //   19: iconst_1
    //   20: if_icmpne +815 -> 835
    //   23: aload 4
    //   25: ldc 48
    //   27: invokevirtual 54	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
    //   30: astore 5
    //   32: new 56	com/kaixin001/item/RecordInfo
    //   35: dup
    //   36: invokespecial 57	com/kaixin001/item/RecordInfo:<init>	()V
    //   39: astore 6
    //   41: aload 6
    //   43: aload 5
    //   45: ldc 59
    //   47: invokevirtual 63	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   50: invokevirtual 67	com/kaixin001/item/RecordInfo:setRecordFeedFuid	(Ljava/lang/String;)V
    //   53: aload 6
    //   55: aload 5
    //   57: ldc 69
    //   59: invokevirtual 63	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   62: invokevirtual 72	com/kaixin001/item/RecordInfo:setRecordFeedFname	(Ljava/lang/String;)V
    //   65: aload 6
    //   67: aload 5
    //   69: ldc 74
    //   71: invokevirtual 63	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   74: invokevirtual 77	com/kaixin001/item/RecordInfo:setRecordFeedFlogo	(Ljava/lang/String;)V
    //   77: aload 6
    //   79: aload 5
    //   81: ldc 79
    //   83: invokevirtual 63	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   86: invokevirtual 82	com/kaixin001/item/RecordInfo:setRecordFeedNType	(Ljava/lang/String;)V
    //   89: aload 6
    //   91: aload 5
    //   93: ldc 84
    //   95: invokevirtual 63	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   98: invokevirtual 87	com/kaixin001/item/RecordInfo:setRecordFeedTitle	(Ljava/lang/String;)V
    //   101: aload 5
    //   103: ldc 89
    //   105: invokevirtual 93	org/json/JSONObject:optJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   108: astore 7
    //   110: aload 7
    //   112: ifnull +24 -> 136
    //   115: aload 7
    //   117: invokevirtual 99	org/json/JSONArray:length	()I
    //   120: ifle +16 -> 136
    //   123: iconst_0
    //   124: istore 8
    //   126: iload 8
    //   128: aload 7
    //   130: invokevirtual 99	org/json/JSONArray:length	()I
    //   133: if_icmplt +475 -> 608
    //   136: aload 6
    //   138: aconst_null
    //   139: putfield 103	com/kaixin001/item/RecordInfo:mMediaInfo	Lcom/kaixin001/view/media/KXMediaInfo;
    //   142: aload 5
    //   144: ldc 105
    //   146: invokevirtual 108	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   149: astore 9
    //   151: aload 6
    //   153: iconst_0
    //   154: putfield 111	com/kaixin001/item/RecordInfo:mAudioRecord	I
    //   157: aload 9
    //   159: invokestatic 117	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   162: ifne +61 -> 223
    //   165: aload 6
    //   167: new 119	com/kaixin001/view/media/KXMediaInfo
    //   170: dup
    //   171: invokespecial 120	com/kaixin001/view/media/KXMediaInfo:<init>	()V
    //   174: putfield 103	com/kaixin001/item/RecordInfo:mMediaInfo	Lcom/kaixin001/view/media/KXMediaInfo;
    //   177: aload 6
    //   179: getfield 103	com/kaixin001/item/RecordInfo:mMediaInfo	Lcom/kaixin001/view/media/KXMediaInfo;
    //   182: aload 6
    //   184: invokevirtual 124	com/kaixin001/item/RecordInfo:getRecordFeedRid	()Ljava/lang/String;
    //   187: invokevirtual 127	com/kaixin001/view/media/KXMediaInfo:setId	(Ljava/lang/String;)V
    //   190: aload 6
    //   192: getfield 103	com/kaixin001/item/RecordInfo:mMediaInfo	Lcom/kaixin001/view/media/KXMediaInfo;
    //   195: aload 9
    //   197: invokevirtual 130	com/kaixin001/view/media/KXMediaInfo:setUrl	(Ljava/lang/String;)V
    //   200: aload 6
    //   202: getfield 103	com/kaixin001/item/RecordInfo:mMediaInfo	Lcom/kaixin001/view/media/KXMediaInfo;
    //   205: aload 5
    //   207: ldc 132
    //   209: ldc 134
    //   211: invokevirtual 137	org/json/JSONObject:optString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   214: invokevirtual 140	com/kaixin001/view/media/KXMediaInfo:setDuration	(Ljava/lang/String;)V
    //   217: aload 6
    //   219: iconst_1
    //   220: putfield 111	com/kaixin001/item/RecordInfo:mAudioRecord	I
    //   223: aload 5
    //   225: ldc 142
    //   227: invokevirtual 145	org/json/JSONObject:optJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
    //   230: astore 10
    //   232: aload 10
    //   234: ifnull +84 -> 318
    //   237: aload 6
    //   239: aconst_null
    //   240: putfield 148	com/kaixin001/item/RecordInfo:mSubMediaInfo	Lcom/kaixin001/view/media/KXMediaInfo;
    //   243: aload 10
    //   245: ldc 105
    //   247: invokevirtual 108	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   250: astore 11
    //   252: aload 11
    //   254: invokestatic 117	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   257: ifne +61 -> 318
    //   260: aload 6
    //   262: new 119	com/kaixin001/view/media/KXMediaInfo
    //   265: dup
    //   266: invokespecial 120	com/kaixin001/view/media/KXMediaInfo:<init>	()V
    //   269: putfield 148	com/kaixin001/item/RecordInfo:mSubMediaInfo	Lcom/kaixin001/view/media/KXMediaInfo;
    //   272: aload 6
    //   274: getfield 148	com/kaixin001/item/RecordInfo:mSubMediaInfo	Lcom/kaixin001/view/media/KXMediaInfo;
    //   277: aload 6
    //   279: invokevirtual 124	com/kaixin001/item/RecordInfo:getRecordFeedRid	()Ljava/lang/String;
    //   282: invokevirtual 127	com/kaixin001/view/media/KXMediaInfo:setId	(Ljava/lang/String;)V
    //   285: aload 6
    //   287: getfield 148	com/kaixin001/item/RecordInfo:mSubMediaInfo	Lcom/kaixin001/view/media/KXMediaInfo;
    //   290: aload 11
    //   292: invokevirtual 130	com/kaixin001/view/media/KXMediaInfo:setUrl	(Ljava/lang/String;)V
    //   295: aload 6
    //   297: getfield 148	com/kaixin001/item/RecordInfo:mSubMediaInfo	Lcom/kaixin001/view/media/KXMediaInfo;
    //   300: aload 10
    //   302: ldc 132
    //   304: ldc 134
    //   306: invokevirtual 137	org/json/JSONObject:optString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   309: invokevirtual 140	com/kaixin001/view/media/KXMediaInfo:setDuration	(Ljava/lang/String;)V
    //   312: aload 6
    //   314: iconst_1
    //   315: putfield 111	com/kaixin001/item/RecordInfo:mAudioRecord	I
    //   318: aload 5
    //   320: ldc 150
    //   322: invokevirtual 93	org/json/JSONObject:optJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   325: astore 12
    //   327: aload 12
    //   329: ifnull +319 -> 648
    //   332: aload 12
    //   334: invokevirtual 99	org/json/JSONArray:length	()I
    //   337: ifle +311 -> 648
    //   340: aload 12
    //   342: iconst_0
    //   343: invokevirtual 154	org/json/JSONArray:get	(I)Ljava/lang/Object;
    //   346: checkcast 50	org/json/JSONObject
    //   349: astore 27
    //   351: aload 27
    //   353: ifnull +35 -> 388
    //   356: aload 27
    //   358: invokevirtual 155	org/json/JSONObject:length	()I
    //   361: ifle +27 -> 388
    //   364: aload 6
    //   366: aload 27
    //   368: ldc 157
    //   370: invokevirtual 63	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   373: invokevirtual 160	com/kaixin001/item/RecordInfo:setRecordThumbnail	(Ljava/lang/String;)V
    //   376: aload 6
    //   378: aload 27
    //   380: ldc 162
    //   382: invokevirtual 63	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   385: invokevirtual 165	com/kaixin001/item/RecordInfo:setRecordLarge	(Ljava/lang/String;)V
    //   388: aload 6
    //   390: aload 5
    //   392: ldc 167
    //   394: ldc 169
    //   396: invokevirtual 137	org/json/JSONObject:optString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   399: invokevirtual 172	com/kaixin001/item/RecordInfo:setRecordFeedLocation	(Ljava/lang/String;)V
    //   402: aload 6
    //   404: aload 5
    //   406: ldc 174
    //   408: invokevirtual 63	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   411: invokevirtual 177	com/kaixin001/item/RecordInfo:setRecordFeedSource	(Ljava/lang/String;)V
    //   414: aload 6
    //   416: aload 5
    //   418: ldc 179
    //   420: invokevirtual 63	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   423: invokevirtual 182	com/kaixin001/item/RecordInfo:setRecordFeedTime	(Ljava/lang/String;)V
    //   426: aload 6
    //   428: aload 5
    //   430: ldc 184
    //   432: invokevirtual 63	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   435: invokevirtual 187	com/kaixin001/item/RecordInfo:setRecordFeedTnum	(Ljava/lang/String;)V
    //   438: aload 6
    //   440: aload 5
    //   442: ldc 189
    //   444: invokevirtual 63	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   447: invokevirtual 192	com/kaixin001/item/RecordInfo:setRecordFeedCnum	(Ljava/lang/String;)V
    //   450: aload 5
    //   452: ldc 142
    //   454: invokevirtual 145	org/json/JSONObject:optJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
    //   457: astore 23
    //   459: aload 23
    //   461: ifnull +125 -> 586
    //   464: aload 23
    //   466: invokevirtual 155	org/json/JSONObject:length	()I
    //   469: ifle +117 -> 586
    //   472: aload 6
    //   474: iconst_1
    //   475: invokevirtual 196	com/kaixin001/item/RecordInfo:setRepost	(Z)V
    //   478: aload 6
    //   480: aload 23
    //   482: ldc 198
    //   484: invokevirtual 63	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   487: invokevirtual 201	com/kaixin001/item/RecordInfo:setRecordFeedSubRid	(Ljava/lang/String;)V
    //   490: aload 6
    //   492: aload 23
    //   494: ldc 84
    //   496: invokevirtual 63	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   499: invokevirtual 204	com/kaixin001/item/RecordInfo:setRecordFeedSubTitle	(Ljava/lang/String;)V
    //   502: aload 23
    //   504: ldc 206
    //   506: invokevirtual 93	org/json/JSONObject:optJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   509: astore 24
    //   511: aload 24
    //   513: ifnull +59 -> 572
    //   516: aload 24
    //   518: invokevirtual 99	org/json/JSONArray:length	()I
    //   521: ifle +51 -> 572
    //   524: aload 24
    //   526: iconst_0
    //   527: invokevirtual 154	org/json/JSONArray:get	(I)Ljava/lang/Object;
    //   530: checkcast 50	org/json/JSONObject
    //   533: astore 26
    //   535: aload 26
    //   537: ifnull +35 -> 572
    //   540: aload 26
    //   542: invokevirtual 155	org/json/JSONObject:length	()I
    //   545: ifle +27 -> 572
    //   548: aload 6
    //   550: aload 26
    //   552: ldc 157
    //   554: invokevirtual 63	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   557: invokevirtual 209	com/kaixin001/item/RecordInfo:setRecordSubThumbnail	(Ljava/lang/String;)V
    //   560: aload 6
    //   562: aload 26
    //   564: ldc 162
    //   566: invokevirtual 63	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   569: invokevirtual 212	com/kaixin001/item/RecordInfo:setRecordSubLarge	(Ljava/lang/String;)V
    //   572: aload 6
    //   574: aload 23
    //   576: ldc 167
    //   578: ldc 169
    //   580: invokevirtual 137	org/json/JSONObject:optString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   583: invokevirtual 215	com/kaixin001/item/RecordInfo:setRecordFeedSubLocation	(Ljava/lang/String;)V
    //   586: aload 6
    //   588: aload 5
    //   590: ldc 198
    //   592: invokevirtual 63	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   595: invokevirtual 218	com/kaixin001/item/RecordInfo:setRecordFeedRid	(Ljava/lang/String;)V
    //   598: invokestatic 223	com/kaixin001/model/RecordModel:getInstance	()Lcom/kaixin001/model/RecordModel;
    //   601: aload 6
    //   603: invokevirtual 227	com/kaixin001/model/RecordModel:setRecordInfo	(Lcom/kaixin001/item/RecordInfo;)V
    //   606: iconst_1
    //   607: ireturn
    //   608: aload 7
    //   610: iload 8
    //   612: invokevirtual 154	org/json/JSONArray:get	(I)Ljava/lang/Object;
    //   615: checkcast 50	org/json/JSONObject
    //   618: astore 28
    //   620: aload 28
    //   622: ifnull +253 -> 875
    //   625: aload 28
    //   627: invokevirtual 155	org/json/JSONObject:length	()I
    //   630: ifle +245 -> 875
    //   633: aload 6
    //   635: aload 28
    //   637: ldc 229
    //   639: invokevirtual 108	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   642: invokevirtual 232	com/kaixin001/item/RecordInfo:appendRecordVideoLogo	(Ljava/lang/String;)V
    //   645: goto +230 -> 875
    //   648: aload 5
    //   650: ldc 142
    //   652: invokevirtual 145	org/json/JSONObject:optJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
    //   655: astore 13
    //   657: aload 13
    //   659: ifnull -271 -> 388
    //   662: aload 13
    //   664: ldc 150
    //   666: invokevirtual 93	org/json/JSONObject:optJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   669: astore 14
    //   671: aload 14
    //   673: ifnull +51 -> 724
    //   676: aload 14
    //   678: iconst_0
    //   679: invokevirtual 154	org/json/JSONArray:get	(I)Ljava/lang/Object;
    //   682: checkcast 50	org/json/JSONObject
    //   685: astore 15
    //   687: aload 15
    //   689: ifnull +35 -> 724
    //   692: aload 15
    //   694: invokevirtual 155	org/json/JSONObject:length	()I
    //   697: ifle +27 -> 724
    //   700: aload 6
    //   702: aload 15
    //   704: ldc 157
    //   706: invokevirtual 63	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   709: invokevirtual 209	com/kaixin001/item/RecordInfo:setRecordSubThumbnail	(Ljava/lang/String;)V
    //   712: aload 6
    //   714: aload 15
    //   716: ldc 162
    //   718: invokevirtual 63	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   721: invokevirtual 212	com/kaixin001/item/RecordInfo:setRecordSubLarge	(Ljava/lang/String;)V
    //   724: aload 13
    //   726: ldc 89
    //   728: invokevirtual 93	org/json/JSONObject:optJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   731: astore 16
    //   733: aload 16
    //   735: ifnull -347 -> 388
    //   738: aload 16
    //   740: invokevirtual 99	org/json/JSONArray:length	()I
    //   743: ifle -355 -> 388
    //   746: iconst_0
    //   747: istore 17
    //   749: iload 17
    //   751: aload 16
    //   753: invokevirtual 99	org/json/JSONArray:length	()I
    //   756: if_icmpge -368 -> 388
    //   759: aload 16
    //   761: iload 17
    //   763: invokevirtual 154	org/json/JSONArray:get	(I)Ljava/lang/Object;
    //   766: checkcast 50	org/json/JSONObject
    //   769: astore 18
    //   771: aload 18
    //   773: ifnull +108 -> 881
    //   776: aload 18
    //   778: invokevirtual 155	org/json/JSONObject:length	()I
    //   781: ifle +100 -> 881
    //   784: aload 6
    //   786: aload 18
    //   788: ldc 229
    //   790: invokevirtual 108	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   793: invokevirtual 232	com/kaixin001/item/RecordInfo:appendRecordVideoLogo	(Ljava/lang/String;)V
    //   796: goto +85 -> 881
    //   799: astore 19
    //   801: aload 6
    //   803: ldc 169
    //   805: invokevirtual 172	com/kaixin001/item/RecordInfo:setRecordFeedLocation	(Ljava/lang/String;)V
    //   808: goto -406 -> 402
    //   811: astore_3
    //   812: ldc 17
    //   814: aload_3
    //   815: invokevirtual 235	com/kaixin001/engine/SecurityErrorException:getMessage	()Ljava/lang/String;
    //   818: invokestatic 241	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   821: iconst_0
    //   822: ireturn
    //   823: astore 25
    //   825: aload 6
    //   827: ldc 169
    //   829: invokevirtual 215	com/kaixin001/item/RecordInfo:setRecordFeedSubLocation	(Ljava/lang/String;)V
    //   832: goto -246 -> 586
    //   835: aload 4
    //   837: ldc 243
    //   839: iconst_m1
    //   840: invokevirtual 247	org/json/JSONObject:optInt	(Ljava/lang/String;I)I
    //   843: sipush 4001
    //   846: if_icmpne +12 -> 858
    //   849: invokestatic 223	com/kaixin001/model/RecordModel:getInstance	()Lcom/kaixin001/model/RecordModel;
    //   852: sipush 4001
    //   855: invokevirtual 251	com/kaixin001/model/RecordModel:setErrorNum	(I)V
    //   858: iconst_0
    //   859: ireturn
    //   860: astore 22
    //   862: goto -412 -> 450
    //   865: astore 21
    //   867: goto -429 -> 438
    //   870: astore 20
    //   872: goto -458 -> 414
    //   875: iinc 8 1
    //   878: goto -752 -> 126
    //   881: iinc 17 1
    //   884: goto -135 -> 749
    //
    // Exception table:
    //   from	to	target	type
    //   388	402	799	java/lang/Exception
    //   0	8	811	com/kaixin001/engine/SecurityErrorException
    //   15	110	811	com/kaixin001/engine/SecurityErrorException
    //   115	123	811	com/kaixin001/engine/SecurityErrorException
    //   126	136	811	com/kaixin001/engine/SecurityErrorException
    //   136	223	811	com/kaixin001/engine/SecurityErrorException
    //   223	232	811	com/kaixin001/engine/SecurityErrorException
    //   237	318	811	com/kaixin001/engine/SecurityErrorException
    //   318	327	811	com/kaixin001/engine/SecurityErrorException
    //   332	351	811	com/kaixin001/engine/SecurityErrorException
    //   356	388	811	com/kaixin001/engine/SecurityErrorException
    //   388	402	811	com/kaixin001/engine/SecurityErrorException
    //   402	414	811	com/kaixin001/engine/SecurityErrorException
    //   414	426	811	com/kaixin001/engine/SecurityErrorException
    //   426	438	811	com/kaixin001/engine/SecurityErrorException
    //   438	450	811	com/kaixin001/engine/SecurityErrorException
    //   450	459	811	com/kaixin001/engine/SecurityErrorException
    //   464	511	811	com/kaixin001/engine/SecurityErrorException
    //   516	535	811	com/kaixin001/engine/SecurityErrorException
    //   540	572	811	com/kaixin001/engine/SecurityErrorException
    //   572	586	811	com/kaixin001/engine/SecurityErrorException
    //   586	606	811	com/kaixin001/engine/SecurityErrorException
    //   608	620	811	com/kaixin001/engine/SecurityErrorException
    //   625	645	811	com/kaixin001/engine/SecurityErrorException
    //   648	657	811	com/kaixin001/engine/SecurityErrorException
    //   662	671	811	com/kaixin001/engine/SecurityErrorException
    //   676	687	811	com/kaixin001/engine/SecurityErrorException
    //   692	724	811	com/kaixin001/engine/SecurityErrorException
    //   724	733	811	com/kaixin001/engine/SecurityErrorException
    //   738	746	811	com/kaixin001/engine/SecurityErrorException
    //   749	771	811	com/kaixin001/engine/SecurityErrorException
    //   776	796	811	com/kaixin001/engine/SecurityErrorException
    //   801	808	811	com/kaixin001/engine/SecurityErrorException
    //   825	832	811	com/kaixin001/engine/SecurityErrorException
    //   835	858	811	com/kaixin001/engine/SecurityErrorException
    //   572	586	823	java/lang/Exception
    //   438	450	860	java/lang/Exception
    //   426	438	865	java/lang/Exception
    //   402	414	870	java/lang/Exception
  }

  public boolean doGetRecordDetailMoreRequest(Context paramContext, String paramString1, String paramString2)
    throws SecurityErrorException
  {
    User localUser = User.getInstance();
    String str1 = Protocol.getInstance().makeGetRecordDetailMoreRequest(localUser.getUID(), paramString1, paramString2);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      boolean bool1 = TextUtils.isEmpty(str2);
      i = 0;
      if (bool1);
    }
    catch (Exception localException)
    {
      try
      {
        boolean bool2 = parseRecordDetailJSON(paramContext, str2);
        int i = bool2;
        return i;
        localException = localException;
        KXLog.e("RecordEngine", "doGetRecordDetailMoreRequest error", localException);
        String str2 = null;
      }
      catch (JSONException localJSONException)
      {
        KXLog.e("RecordEngine", localJSONException.getMessage());
      }
    }
    return false;
  }

  public boolean doGetRecordDetailRequest(Context paramContext, String paramString1, String paramString2)
    throws SecurityErrorException
  {
    User localUser = User.getInstance();
    String str1 = Protocol.getInstance().makeGetRecordDetailRequest(localUser.getUID(), paramString1, paramString2);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      boolean bool1 = TextUtils.isEmpty(str2);
      i = 0;
      if (bool1);
    }
    catch (Exception localException)
    {
      try
      {
        boolean bool2 = parseRecordDetailJSON(paramContext, str2);
        int i = bool2;
        return i;
        localException = localException;
        KXLog.e("RecordEngine", "doGetRecordDetailRequest error", localException);
        String str2 = null;
      }
      catch (JSONException localJSONException)
      {
        KXLog.e("RecordEngine", localJSONException.getMessage());
      }
    }
    return false;
  }

  public String getRetRecordId()
  {
    return this.msRetRecordId;
  }

  public int parseRecordJSON(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    JSONObject localJSONObject1 = super.parseJSON(paramContext, paramString);
    try
    {
      JSONObject localJSONObject2 = localJSONObject1.optJSONObject("upgradeInfo");
      BaseFragment.getBaseFragment().setUpgradeInfoData(localJSONObject2);
      if (localJSONObject1 == null)
        return 0;
    }
    catch (Exception localException)
    {
      int i;
      do
      {
        while (true)
        {
          BaseFragment.getBaseFragment().isShowLevelToast(false);
          localException.printStackTrace();
        }
        if (this.ret == 1)
        {
          this.msRetRecordId = localJSONObject1.optString("rid");
          return 1;
        }
        i = localJSONObject1.optInt("code", -1);
        if (i == 218)
          return 218;
      }
      while (i != 216);
    }
    return 216;
  }

  public int postRecord(Context paramContext, String paramString1, String paramString2)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeRecordRequest(User.getInstance().getUID(), paramString1, paramString2);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    String str2;
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (TextUtils.isEmpty(str2))
        return 0;
    }
    catch (Exception localException)
    {
      while (true)
      {
        KXLog.e("RecordEngine", "postRecord error", localException);
        str2 = null;
      }
    }
    return parseRecordJSON(paramContext, str2);
  }

  public int postRecordRequest(Context paramContext, VoiceRecordUploadTask paramVoiceRecordUploadTask, HttpProgressListener paramHttpProgressListener)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makePostRecordRequest();
    HashMap localHashMap = new HashMap();
    localHashMap.put("content", StringUtil.toUtf8(paramVoiceRecordUploadTask.getTitle()));
    localHashMap.put("uid", User.getInstance().getUID());
    localHashMap.put("privacy", paramVoiceRecordUploadTask.getPrivacy());
    localHashMap.put("location", paramVoiceRecordUploadTask.getLocationName());
    localHashMap.put("lat", paramVoiceRecordUploadTask.getLatitude());
    localHashMap.put("lon", paramVoiceRecordUploadTask.getLongitude());
    localHashMap.put("status", paramVoiceRecordUploadTask.getStatus());
    localHashMap.put("api_version", "3.9.9");
    localHashMap.put("version", "android-3.9.9");
    if (!TextUtils.isEmpty(paramVoiceRecordUploadTask.getImageFileName()))
      localHashMap.put("upload_pic", new File(paramVoiceRecordUploadTask.getImageFileName()));
    localHashMap.put("audio_length", paramVoiceRecordUploadTask.getAudioLength());
    localHashMap.put("audio_format", paramVoiceRecordUploadTask.getAudioFormat());
    localHashMap.put("upload_audio", new File(paramVoiceRecordUploadTask.getUploadAudio()));
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    String str2;
    try
    {
      String str3 = localHttpProxy.httpPost(str1, localHashMap, null, paramHttpProgressListener);
      str2 = str3;
      if (TextUtils.isEmpty(str2))
        return 0;
    }
    catch (Exception localException)
    {
      while (true)
      {
        KXLog.e("RecordEngine", "postRecordRequest error", localException);
        str2 = null;
      }
    }
    return parseRecordJSON(paramContext, str2);
  }

  @Deprecated
  public int postRecordRequest(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makePostRecordRequest(User.getInstance().getUID(), paramString1, paramString2, paramString3, paramString4, paramString5);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    String str2;
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (TextUtils.isEmpty(str2))
        return 0;
    }
    catch (Exception localException)
    {
      while (true)
      {
        KXLog.e("RecordEngine", "postRecordRequest error", localException);
        str2 = null;
      }
    }
    return parseRecordJSON(paramContext, str2);
  }

  public int postRecordRequest(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, HttpProgressListener paramHttpProgressListener)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makePostRecordRequest();
    HashMap localHashMap = new HashMap();
    localHashMap.put("content", StringUtil.toUtf8(paramString2));
    localHashMap.put("uid", User.getInstance().getUID());
    localHashMap.put("privacy", paramString1);
    localHashMap.put("location", paramString3);
    localHashMap.put("lat", paramString4);
    localHashMap.put("lon", paramString5);
    localHashMap.put("status", paramString7);
    localHashMap.put("api_version", "3.9.9");
    localHashMap.put("version", "android-3.9.9");
    if (!TextUtils.isEmpty(paramString6))
      localHashMap.put("upload_pic", new File(paramString6));
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    String str2;
    try
    {
      String str3 = localHttpProxy.httpPost(str1, localHashMap, null, paramHttpProgressListener);
      str2 = str3;
      if (TextUtils.isEmpty(str2))
        return 0;
    }
    catch (Exception localException)
    {
      while (true)
      {
        KXLog.e("RecordEngine", "postRecordRequest error", localException);
        str2 = null;
      }
    }
    return parseRecordJSON(paramContext, str2);
  }

  public void setRetRecordId(String paramString)
  {
    this.msRetRecordId = paramString;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.RecordEngine
 * JD-Core Version:    0.6.0
 */