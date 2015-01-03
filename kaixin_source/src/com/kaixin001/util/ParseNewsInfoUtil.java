package com.kaixin001.util;

import android.text.TextUtils;
import com.kaixin001.item.KXLinkInfo;
import com.kaixin001.model.FaceModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import org.json.JSONArray;
import org.json.JSONObject;

public class ParseNewsInfoUtil
{
  private static final String TAG = "ParseNewsInfoUtil";

  private static void addCommentInfo(ArrayList<KXLinkInfo> paramArrayList, String paramString, TreeMap<Integer, String> paramTreeMap, int paramInt)
  {
    int i = 0;
    int j = paramInt;
    Iterator localIterator = paramTreeMap.keySet().iterator();
    while (true)
    {
      if (!localIterator.hasNext());
      int k;
      do
      {
        if (i == 0)
          break label181;
        if (j < paramInt + paramString.length())
          addNewInfo(paramArrayList, paramString.substring(j - paramInt));
        return;
        k = ((Integer)localIterator.next()).intValue();
        if (k < j)
          break;
      }
      while (k > paramInt + paramString.length());
      int m = k - j;
      if (m > 0)
      {
        int n = j - paramInt;
        addNewInfo(paramArrayList, paramString.substring(n, n + m));
      }
      i = 1;
      String str = (String)paramTreeMap.get(Integer.valueOf(k));
      KXLinkInfo localKXLinkInfo = new KXLinkInfo();
      localKXLinkInfo.setFace(true);
      localKXLinkInfo.setContent(str);
      paramArrayList.add(localKXLinkInfo);
      j = k + str.length();
    }
    label181: addNewInfo(paramArrayList, paramString);
  }

  private static void addNewInfo(ArrayList<KXLinkInfo> paramArrayList, String paramString)
  {
    KXLinkInfo localKXLinkInfo = new KXLinkInfo();
    localKXLinkInfo.setContent(paramString);
    paramArrayList.add(localKXLinkInfo);
  }

  public static String getReplistString(JSONArray paramJSONArray)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    try
    {
      int i = paramJSONArray.length();
      if (i == 0)
        return null;
      for (int j = 0; ; j++)
      {
        if (j >= i)
        {
          str = localStringBuffer.toString();
          return str;
        }
        localStringBuffer.append(paramJSONArray.getJSONObject(j).getString("title"));
        if (j == i - 1)
          continue;
        localStringBuffer.append('\n');
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        KXLog.e("ParseNewsInfoUtil", "getReplistString", localException);
        String str = localStringBuffer.toString();
      }
    }
    finally
    {
      localStringBuffer.toString();
    }
    throw localObject;
  }

  // ERROR //
  public static ArrayList<KXLinkInfo> parseCommentAndReplyLinkString(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokestatic 131	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   4: ifeq +5 -> 9
    //   7: aconst_null
    //   8: areturn
    //   9: aconst_null
    //   10: astore_1
    //   11: new 79	java/util/ArrayList
    //   14: dup
    //   15: invokespecial 132	java/util/ArrayList:<init>	()V
    //   18: astore_2
    //   19: new 16	java/util/TreeMap
    //   22: dup
    //   23: invokespecial 133	java/util/TreeMap:<init>	()V
    //   26: astore_3
    //   27: aload_2
    //   28: aload_0
    //   29: aload_3
    //   30: invokestatic 137	com/kaixin001/util/ParseNewsInfoUtil:parseFaceString	(Ljava/util/ArrayList;Ljava/lang/String;Ljava/util/TreeMap;)V
    //   33: aload_0
    //   34: ldc 139
    //   36: invokevirtual 143	java/lang/String:indexOf	(Ljava/lang/String;)I
    //   39: istore 5
    //   41: aload_0
    //   42: invokevirtual 38	java/lang/String:length	()I
    //   45: istore 6
    //   47: iconst_0
    //   48: istore 7
    //   50: iload 5
    //   52: ifle +16 -> 68
    //   55: aload_2
    //   56: aload_0
    //   57: iconst_0
    //   58: iload 5
    //   60: invokevirtual 58	java/lang/String:substring	(II)Ljava/lang/String;
    //   63: aload_3
    //   64: iconst_0
    //   65: invokestatic 145	com/kaixin001/util/ParseNewsInfoUtil:addCommentInfo	(Ljava/util/ArrayList;Ljava/lang/String;Ljava/util/TreeMap;I)V
    //   68: iload 5
    //   70: ifge +25 -> 95
    //   73: iload 7
    //   75: iload 6
    //   77: if_icmpge +278 -> 355
    //   80: aload_2
    //   81: aload_0
    //   82: iload 7
    //   84: invokevirtual 42	java/lang/String:substring	(I)Ljava/lang/String;
    //   87: aload_3
    //   88: iload 7
    //   90: invokestatic 145	com/kaixin001/util/ParseNewsInfoUtil:addCommentInfo	(Ljava/util/ArrayList;Ljava/lang/String;Ljava/util/TreeMap;I)V
    //   93: aload_2
    //   94: areturn
    //   95: iconst_m1
    //   96: istore 8
    //   98: iconst_m1
    //   99: istore 9
    //   101: aload_0
    //   102: ldc 147
    //   104: iload 5
    //   106: iconst_5
    //   107: iadd
    //   108: invokevirtual 150	java/lang/String:indexOf	(Ljava/lang/String;I)I
    //   111: istore 10
    //   113: iload 10
    //   115: iflt +15 -> 130
    //   118: aload_0
    //   119: ldc 147
    //   121: iload 10
    //   123: iconst_5
    //   124: iadd
    //   125: invokevirtual 150	java/lang/String:indexOf	(Ljava/lang/String;I)I
    //   128: istore 8
    //   130: iload 8
    //   132: iflt +15 -> 147
    //   135: aload_0
    //   136: ldc 152
    //   138: iload 8
    //   140: iconst_5
    //   141: iadd
    //   142: invokevirtual 150	java/lang/String:indexOf	(Ljava/lang/String;I)I
    //   145: istore 9
    //   147: iload 10
    //   149: iflt +124 -> 273
    //   152: iload 8
    //   154: iflt +119 -> 273
    //   157: iload 9
    //   159: iflt +114 -> 273
    //   162: new 68	com/kaixin001/item/KXLinkInfo
    //   165: dup
    //   166: invokespecial 69	com/kaixin001/item/KXLinkInfo:<init>	()V
    //   169: astore 14
    //   171: aload 14
    //   173: aload_0
    //   174: iload 5
    //   176: iconst_5
    //   177: iadd
    //   178: iload 10
    //   180: invokevirtual 58	java/lang/String:substring	(II)Ljava/lang/String;
    //   183: invokevirtual 77	com/kaixin001/item/KXLinkInfo:setContent	(Ljava/lang/String;)V
    //   186: aload 14
    //   188: aload_0
    //   189: iload 10
    //   191: iconst_5
    //   192: iadd
    //   193: iload 8
    //   195: invokevirtual 58	java/lang/String:substring	(II)Ljava/lang/String;
    //   198: invokevirtual 155	com/kaixin001/item/KXLinkInfo:setId	(Ljava/lang/String;)V
    //   201: aload 14
    //   203: aload_0
    //   204: iload 8
    //   206: iconst_5
    //   207: iadd
    //   208: iload 9
    //   210: invokevirtual 58	java/lang/String:substring	(II)Ljava/lang/String;
    //   213: invokevirtual 158	com/kaixin001/item/KXLinkInfo:setType	(Ljava/lang/String;)V
    //   216: aload_2
    //   217: aload 14
    //   219: invokevirtual 83	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   222: pop
    //   223: iload 9
    //   225: iconst_5
    //   226: iadd
    //   227: istore 7
    //   229: aload_0
    //   230: ldc 139
    //   232: iload 7
    //   234: invokevirtual 150	java/lang/String:indexOf	(Ljava/lang/String;I)I
    //   237: istore 5
    //   239: iload 5
    //   241: iflt -173 -> 68
    //   244: iload 7
    //   246: iload 5
    //   248: if_icmpeq -180 -> 68
    //   251: aload_2
    //   252: aload_0
    //   253: iload 7
    //   255: iload 5
    //   257: invokevirtual 58	java/lang/String:substring	(II)Ljava/lang/String;
    //   260: aload_3
    //   261: iload 7
    //   263: invokestatic 145	com/kaixin001/util/ParseNewsInfoUtil:addCommentInfo	(Ljava/util/ArrayList;Ljava/lang/String;Ljava/util/TreeMap;I)V
    //   266: iload 5
    //   268: istore 7
    //   270: goto -202 -> 68
    //   273: aload_0
    //   274: ldc 139
    //   276: iload 5
    //   278: iconst_5
    //   279: iadd
    //   280: invokevirtual 150	java/lang/String:indexOf	(Ljava/lang/String;I)I
    //   283: istore 11
    //   285: iload 11
    //   287: iflt +33 -> 320
    //   290: aload_0
    //   291: iload 5
    //   293: iload 11
    //   295: invokevirtual 58	java/lang/String:substring	(II)Ljava/lang/String;
    //   298: astore 12
    //   300: aload_2
    //   301: aload 12
    //   303: aload_3
    //   304: iload 5
    //   306: invokestatic 145	com/kaixin001/util/ParseNewsInfoUtil:addCommentInfo	(Ljava/util/ArrayList;Ljava/lang/String;Ljava/util/TreeMap;I)V
    //   309: iload 11
    //   311: istore 5
    //   313: iload 6
    //   315: istore 7
    //   317: goto -249 -> 68
    //   320: aload_0
    //   321: iload 5
    //   323: invokevirtual 42	java/lang/String:substring	(I)Ljava/lang/String;
    //   326: astore 13
    //   328: aload 13
    //   330: astore 12
    //   332: goto -32 -> 300
    //   335: astore 4
    //   337: ldc 8
    //   339: ldc 159
    //   341: aload 4
    //   343: invokestatic 123	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   346: aload_1
    //   347: areturn
    //   348: astore 4
    //   350: aload_2
    //   351: astore_1
    //   352: goto -15 -> 337
    //   355: aload_2
    //   356: areturn
    //
    // Exception table:
    //   from	to	target	type
    //   11	19	335	java/lang/Exception
    //   19	47	348	java/lang/Exception
    //   55	68	348	java/lang/Exception
    //   80	93	348	java/lang/Exception
    //   101	113	348	java/lang/Exception
    //   118	130	348	java/lang/Exception
    //   135	147	348	java/lang/Exception
    //   162	223	348	java/lang/Exception
    //   229	239	348	java/lang/Exception
    //   251	266	348	java/lang/Exception
    //   273	285	348	java/lang/Exception
    //   290	300	348	java/lang/Exception
    //   300	309	348	java/lang/Exception
    //   320	328	348	java/lang/Exception
  }

  // ERROR //
  public static ArrayList<KXLinkInfo> parseDiaryLinkString(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokestatic 131	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   4: ifeq +5 -> 9
    //   7: aconst_null
    //   8: areturn
    //   9: aconst_null
    //   10: astore_1
    //   11: new 79	java/util/ArrayList
    //   14: dup
    //   15: invokespecial 132	java/util/ArrayList:<init>	()V
    //   18: astore_2
    //   19: new 16	java/util/TreeMap
    //   22: dup
    //   23: invokespecial 133	java/util/TreeMap:<init>	()V
    //   26: astore_3
    //   27: aload_2
    //   28: aload_0
    //   29: aload_3
    //   30: invokestatic 137	com/kaixin001/util/ParseNewsInfoUtil:parseFaceString	(Ljava/util/ArrayList;Ljava/lang/String;Ljava/util/TreeMap;)V
    //   33: aload_0
    //   34: ldc 139
    //   36: invokevirtual 143	java/lang/String:indexOf	(Ljava/lang/String;)I
    //   39: istore 5
    //   41: aload_0
    //   42: invokevirtual 38	java/lang/String:length	()I
    //   45: istore 6
    //   47: iconst_0
    //   48: istore 7
    //   50: iload 5
    //   52: ifle +16 -> 68
    //   55: aload_2
    //   56: aload_0
    //   57: iconst_0
    //   58: iload 5
    //   60: invokevirtual 58	java/lang/String:substring	(II)Ljava/lang/String;
    //   63: aload_3
    //   64: iconst_0
    //   65: invokestatic 145	com/kaixin001/util/ParseNewsInfoUtil:addCommentInfo	(Ljava/util/ArrayList;Ljava/lang/String;Ljava/util/TreeMap;I)V
    //   68: iload 5
    //   70: ifge +25 -> 95
    //   73: iload 7
    //   75: iload 6
    //   77: if_icmpge +294 -> 371
    //   80: aload_2
    //   81: aload_0
    //   82: iload 7
    //   84: invokevirtual 42	java/lang/String:substring	(I)Ljava/lang/String;
    //   87: aload_3
    //   88: iload 7
    //   90: invokestatic 145	com/kaixin001/util/ParseNewsInfoUtil:addCommentInfo	(Ljava/util/ArrayList;Ljava/lang/String;Ljava/util/TreeMap;I)V
    //   93: aload_2
    //   94: areturn
    //   95: iconst_m1
    //   96: istore 8
    //   98: iconst_m1
    //   99: istore 9
    //   101: aload_0
    //   102: ldc 147
    //   104: iload 5
    //   106: ldc 139
    //   108: invokevirtual 38	java/lang/String:length	()I
    //   111: iadd
    //   112: invokevirtual 150	java/lang/String:indexOf	(Ljava/lang/String;I)I
    //   115: istore 10
    //   117: iload 10
    //   119: iflt +19 -> 138
    //   122: aload_0
    //   123: ldc 147
    //   125: iload 10
    //   127: ldc 147
    //   129: invokevirtual 38	java/lang/String:length	()I
    //   132: iadd
    //   133: invokevirtual 150	java/lang/String:indexOf	(Ljava/lang/String;I)I
    //   136: istore 8
    //   138: iload 8
    //   140: iflt +19 -> 159
    //   143: aload_0
    //   144: ldc 152
    //   146: iload 8
    //   148: ldc 147
    //   150: invokevirtual 38	java/lang/String:length	()I
    //   153: iadd
    //   154: invokevirtual 150	java/lang/String:indexOf	(Ljava/lang/String;I)I
    //   157: istore 9
    //   159: iload 10
    //   161: iflt +128 -> 289
    //   164: iload 8
    //   166: iflt +123 -> 289
    //   169: iload 9
    //   171: iflt +118 -> 289
    //   174: new 68	com/kaixin001/item/KXLinkInfo
    //   177: dup
    //   178: invokespecial 69	com/kaixin001/item/KXLinkInfo:<init>	()V
    //   181: astore 14
    //   183: aload 14
    //   185: aload_0
    //   186: iload 5
    //   188: iconst_5
    //   189: iadd
    //   190: iload 10
    //   192: invokevirtual 58	java/lang/String:substring	(II)Ljava/lang/String;
    //   195: invokevirtual 77	com/kaixin001/item/KXLinkInfo:setContent	(Ljava/lang/String;)V
    //   198: aload 14
    //   200: aload_0
    //   201: iload 10
    //   203: iconst_5
    //   204: iadd
    //   205: iload 8
    //   207: invokevirtual 58	java/lang/String:substring	(II)Ljava/lang/String;
    //   210: invokevirtual 155	com/kaixin001/item/KXLinkInfo:setId	(Ljava/lang/String;)V
    //   213: aload 14
    //   215: aload_0
    //   216: iload 8
    //   218: iconst_5
    //   219: iadd
    //   220: iload 9
    //   222: invokevirtual 58	java/lang/String:substring	(II)Ljava/lang/String;
    //   225: invokevirtual 158	com/kaixin001/item/KXLinkInfo:setType	(Ljava/lang/String;)V
    //   228: aload_2
    //   229: aload 14
    //   231: invokevirtual 83	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   234: pop
    //   235: iload 9
    //   237: ldc 152
    //   239: invokevirtual 38	java/lang/String:length	()I
    //   242: iadd
    //   243: istore 7
    //   245: aload_0
    //   246: ldc 139
    //   248: iload 7
    //   250: invokevirtual 150	java/lang/String:indexOf	(Ljava/lang/String;I)I
    //   253: istore 5
    //   255: iload 5
    //   257: iflt -189 -> 68
    //   260: iload 7
    //   262: iload 5
    //   264: if_icmpeq -196 -> 68
    //   267: aload_2
    //   268: aload_0
    //   269: iload 7
    //   271: iload 5
    //   273: invokevirtual 58	java/lang/String:substring	(II)Ljava/lang/String;
    //   276: aload_3
    //   277: iload 7
    //   279: invokestatic 145	com/kaixin001/util/ParseNewsInfoUtil:addCommentInfo	(Ljava/util/ArrayList;Ljava/lang/String;Ljava/util/TreeMap;I)V
    //   282: iload 5
    //   284: istore 7
    //   286: goto -218 -> 68
    //   289: aload_0
    //   290: ldc 139
    //   292: iload 5
    //   294: iconst_5
    //   295: iadd
    //   296: invokevirtual 150	java/lang/String:indexOf	(Ljava/lang/String;I)I
    //   299: istore 11
    //   301: iload 11
    //   303: iflt +33 -> 336
    //   306: aload_0
    //   307: iload 5
    //   309: iload 11
    //   311: invokevirtual 58	java/lang/String:substring	(II)Ljava/lang/String;
    //   314: astore 12
    //   316: aload_2
    //   317: aload 12
    //   319: aload_3
    //   320: iload 5
    //   322: invokestatic 145	com/kaixin001/util/ParseNewsInfoUtil:addCommentInfo	(Ljava/util/ArrayList;Ljava/lang/String;Ljava/util/TreeMap;I)V
    //   325: iload 11
    //   327: istore 5
    //   329: iload 6
    //   331: istore 7
    //   333: goto -265 -> 68
    //   336: aload_0
    //   337: iload 5
    //   339: invokevirtual 42	java/lang/String:substring	(I)Ljava/lang/String;
    //   342: astore 13
    //   344: aload 13
    //   346: astore 12
    //   348: goto -32 -> 316
    //   351: astore 4
    //   353: ldc 8
    //   355: ldc 161
    //   357: aload 4
    //   359: invokestatic 123	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   362: aload_1
    //   363: areturn
    //   364: astore 4
    //   366: aload_2
    //   367: astore_1
    //   368: goto -15 -> 353
    //   371: aload_2
    //   372: areturn
    //
    // Exception table:
    //   from	to	target	type
    //   11	19	351	java/lang/Exception
    //   19	47	364	java/lang/Exception
    //   55	68	364	java/lang/Exception
    //   80	93	364	java/lang/Exception
    //   101	117	364	java/lang/Exception
    //   122	138	364	java/lang/Exception
    //   143	159	364	java/lang/Exception
    //   174	255	364	java/lang/Exception
    //   267	282	364	java/lang/Exception
    //   289	301	364	java/lang/Exception
    //   306	316	364	java/lang/Exception
    //   316	325	364	java/lang/Exception
    //   336	344	364	java/lang/Exception
  }

  private static void parseFaceString(ArrayList<KXLinkInfo> paramArrayList, String paramString, TreeMap<Integer, String> paramTreeMap)
  {
    if (TextUtils.isEmpty(paramString))
      return;
    ArrayList localArrayList = FaceModel.getInstance().getAllFaceStringSortByLength();
    int i = localArrayList.size();
    int j = 0;
    label24: String str;
    if (j < i)
      str = (String)localArrayList.get(j);
    int m;
    for (int k = paramString.indexOf(str); ; k = paramString.indexOf(str, m))
    {
      if (k < 0)
      {
        j++;
        break label24;
        break;
      }
      m = k + str.length();
      paramTreeMap.put(Integer.valueOf(k), str);
    }
  }

  // ERROR //
  public static ArrayList<KXLinkInfo> parseNewsLinkString(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokestatic 131	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   4: ifeq +5 -> 9
    //   7: aconst_null
    //   8: areturn
    //   9: aconst_null
    //   10: astore_1
    //   11: new 79	java/util/ArrayList
    //   14: dup
    //   15: invokespecial 132	java/util/ArrayList:<init>	()V
    //   18: astore_2
    //   19: new 16	java/util/TreeMap
    //   22: dup
    //   23: invokespecial 133	java/util/TreeMap:<init>	()V
    //   26: astore_3
    //   27: aload_2
    //   28: aload_0
    //   29: aload_3
    //   30: invokestatic 137	com/kaixin001/util/ParseNewsInfoUtil:parseFaceString	(Ljava/util/ArrayList;Ljava/lang/String;Ljava/util/TreeMap;)V
    //   33: aload_0
    //   34: ldc 139
    //   36: invokevirtual 143	java/lang/String:indexOf	(Ljava/lang/String;)I
    //   39: istore 5
    //   41: aload_0
    //   42: invokevirtual 38	java/lang/String:length	()I
    //   45: istore 6
    //   47: iconst_0
    //   48: istore 7
    //   50: iload 5
    //   52: ifle +16 -> 68
    //   55: aload_2
    //   56: aload_0
    //   57: iconst_0
    //   58: iload 5
    //   60: invokevirtual 58	java/lang/String:substring	(II)Ljava/lang/String;
    //   63: aload_3
    //   64: iconst_0
    //   65: invokestatic 145	com/kaixin001/util/ParseNewsInfoUtil:addCommentInfo	(Ljava/util/ArrayList;Ljava/lang/String;Ljava/util/TreeMap;I)V
    //   68: iload 5
    //   70: ifge +25 -> 95
    //   73: iload 7
    //   75: iload 6
    //   77: if_icmpge +274 -> 351
    //   80: aload_2
    //   81: aload_0
    //   82: iload 7
    //   84: invokevirtual 42	java/lang/String:substring	(I)Ljava/lang/String;
    //   87: aload_3
    //   88: iload 7
    //   90: invokestatic 145	com/kaixin001/util/ParseNewsInfoUtil:addCommentInfo	(Ljava/util/ArrayList;Ljava/lang/String;Ljava/util/TreeMap;I)V
    //   93: aload_2
    //   94: areturn
    //   95: iconst_m1
    //   96: istore 8
    //   98: iconst_m1
    //   99: istore 9
    //   101: aload_0
    //   102: ldc 147
    //   104: iload 5
    //   106: iconst_5
    //   107: iadd
    //   108: invokevirtual 150	java/lang/String:indexOf	(Ljava/lang/String;I)I
    //   111: istore 10
    //   113: iload 10
    //   115: iflt +15 -> 130
    //   118: aload_0
    //   119: ldc 147
    //   121: iload 10
    //   123: iconst_5
    //   124: iadd
    //   125: invokevirtual 150	java/lang/String:indexOf	(Ljava/lang/String;I)I
    //   128: istore 8
    //   130: iload 8
    //   132: iflt +15 -> 147
    //   135: aload_0
    //   136: ldc 152
    //   138: iload 8
    //   140: iconst_5
    //   141: iadd
    //   142: invokevirtual 150	java/lang/String:indexOf	(Ljava/lang/String;I)I
    //   145: istore 9
    //   147: iload 10
    //   149: iflt +124 -> 273
    //   152: iload 8
    //   154: iflt +119 -> 273
    //   157: iload 9
    //   159: iflt +114 -> 273
    //   162: new 68	com/kaixin001/item/KXLinkInfo
    //   165: dup
    //   166: invokespecial 69	com/kaixin001/item/KXLinkInfo:<init>	()V
    //   169: astore 14
    //   171: aload 14
    //   173: aload_0
    //   174: iload 5
    //   176: iconst_5
    //   177: iadd
    //   178: iload 10
    //   180: invokevirtual 58	java/lang/String:substring	(II)Ljava/lang/String;
    //   183: invokevirtual 77	com/kaixin001/item/KXLinkInfo:setContent	(Ljava/lang/String;)V
    //   186: aload 14
    //   188: aload_0
    //   189: iload 10
    //   191: iconst_5
    //   192: iadd
    //   193: iload 8
    //   195: invokevirtual 58	java/lang/String:substring	(II)Ljava/lang/String;
    //   198: invokevirtual 155	com/kaixin001/item/KXLinkInfo:setId	(Ljava/lang/String;)V
    //   201: aload 14
    //   203: aload_0
    //   204: iload 8
    //   206: iconst_5
    //   207: iadd
    //   208: iload 9
    //   210: invokevirtual 58	java/lang/String:substring	(II)Ljava/lang/String;
    //   213: invokevirtual 158	com/kaixin001/item/KXLinkInfo:setType	(Ljava/lang/String;)V
    //   216: aload_2
    //   217: aload 14
    //   219: invokevirtual 83	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   222: pop
    //   223: iload 9
    //   225: iconst_5
    //   226: iadd
    //   227: istore 7
    //   229: aload_0
    //   230: ldc 139
    //   232: iload 7
    //   234: invokevirtual 150	java/lang/String:indexOf	(Ljava/lang/String;I)I
    //   237: istore 5
    //   239: iload 5
    //   241: iflt -173 -> 68
    //   244: iload 7
    //   246: iload 5
    //   248: if_icmpeq -180 -> 68
    //   251: aload_2
    //   252: aload_0
    //   253: iload 7
    //   255: iload 5
    //   257: invokevirtual 58	java/lang/String:substring	(II)Ljava/lang/String;
    //   260: aload_3
    //   261: iload 7
    //   263: invokestatic 145	com/kaixin001/util/ParseNewsInfoUtil:addCommentInfo	(Ljava/util/ArrayList;Ljava/lang/String;Ljava/util/TreeMap;I)V
    //   266: iload 5
    //   268: istore 7
    //   270: goto -202 -> 68
    //   273: aload_0
    //   274: ldc 139
    //   276: iload 5
    //   278: iconst_5
    //   279: iadd
    //   280: invokevirtual 150	java/lang/String:indexOf	(Ljava/lang/String;I)I
    //   283: istore 11
    //   285: iload 11
    //   287: iflt +33 -> 320
    //   290: aload_0
    //   291: iload 5
    //   293: iload 11
    //   295: invokevirtual 58	java/lang/String:substring	(II)Ljava/lang/String;
    //   298: astore 12
    //   300: aload_2
    //   301: aload 12
    //   303: aload_3
    //   304: iload 5
    //   306: invokestatic 145	com/kaixin001/util/ParseNewsInfoUtil:addCommentInfo	(Ljava/util/ArrayList;Ljava/lang/String;Ljava/util/TreeMap;I)V
    //   309: iload 11
    //   311: istore 5
    //   313: iload 6
    //   315: istore 7
    //   317: goto -249 -> 68
    //   320: aload_0
    //   321: iload 5
    //   323: invokevirtual 42	java/lang/String:substring	(I)Ljava/lang/String;
    //   326: astore 13
    //   328: aload 13
    //   330: astore 12
    //   332: goto -32 -> 300
    //   335: astore 4
    //   337: aload 4
    //   339: invokevirtual 185	java/lang/Exception:printStackTrace	()V
    //   342: aload_1
    //   343: areturn
    //   344: astore 4
    //   346: aload_2
    //   347: astore_1
    //   348: goto -11 -> 337
    //   351: aload_2
    //   352: areturn
    //
    // Exception table:
    //   from	to	target	type
    //   11	19	335	java/lang/Exception
    //   19	47	344	java/lang/Exception
    //   55	68	344	java/lang/Exception
    //   80	93	344	java/lang/Exception
    //   101	113	344	java/lang/Exception
    //   118	130	344	java/lang/Exception
    //   135	147	344	java/lang/Exception
    //   162	223	344	java/lang/Exception
    //   229	239	344	java/lang/Exception
    //   251	266	344	java/lang/Exception
    //   273	285	344	java/lang/Exception
    //   290	300	344	java/lang/Exception
    //   300	309	344	java/lang/Exception
    //   320	328	344	java/lang/Exception
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.util.ParseNewsInfoUtil
 * JD-Core Version:    0.6.0
 */