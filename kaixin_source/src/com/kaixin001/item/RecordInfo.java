package com.kaixin001.item;

import android.text.TextUtils;
import com.kaixin001.util.ParseNewsInfoUtil;
import com.kaixin001.view.media.KXMediaInfo;
import java.io.Serializable;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class RecordInfo
  implements Serializable
{
  private static final long serialVersionUID = -4088115379782565544L;
  public int fpri = 1;
  private boolean isRepost = false;
  public int mAudioRecord = -1;
  public KXMediaInfo mMediaInfo;
  public KXMediaInfo mSubMediaInfo;
  public int privacy;
  private String recordFeedCnum = null;
  private String recordFeedFlogo = null;
  private String recordFeedFname = null;
  private String recordFeedFuid = null;
  private String recordFeedLocation = null;
  private String recordFeedNType = null;
  private String recordFeedRid = null;
  private String recordFeedSource = null;
  private String recordFeedStar = null;
  private String recordFeedSubLocation = null;
  private String recordFeedSubRid = null;
  private String recordFeedSubTitle = null;
  private String recordFeedTime = null;
  private String recordFeedTitle = null;
  private String recordFeedTnum = null;
  private String recordFeedUpnum = null;
  private String recordLarge = null;
  private String recordSubLarge = null;
  private String recordSubThumbnail = null;
  private String recordThumbnail = null;
  private ArrayList<String> recordVideoUriList = null;
  private String sourceId = null;

  public static long getSerialversionuid()
  {
    return -4088115379782565544L;
  }

  public static ArrayList<KXLinkInfo> parseObjCommentUtil(String paramString)
  {
    return ParseNewsInfoUtil.parseDiaryLinkString(paramString);
  }

  public static ArrayList<KXLinkInfo> parseRecordTitleUtil(String paramString)
  {
    return ParseNewsInfoUtil.parseNewsLinkString(paramString);
  }

  public static RecordInfo valueOf(SystemMessageItem paramSystemMessageItem)
  {
    if (paramSystemMessageItem == null)
      return null;
    RecordInfo localRecordInfo = new RecordInfo();
    localRecordInfo.setRecordFeedFuid(paramSystemMessageItem.getFuid());
    localRecordInfo.setRecordFeedFlogo(paramSystemMessageItem.getFlogo());
    localRecordInfo.setRecordFeedTime(paramSystemMessageItem.getStrCtime());
    localRecordInfo.setRecordFeedTitle(paramSystemMessageItem.getContent());
    localRecordInfo.setRecordFeedFname(paramSystemMessageItem.getRealName());
    localRecordInfo.setRecordFeedLocation(paramSystemMessageItem.getLocation());
    localRecordInfo.setRecordFeedSource(paramSystemMessageItem.getSource());
    localRecordInfo.setRecordFeedRid(paramSystemMessageItem.getId());
    localRecordInfo.setRecordFeedNType(paramSystemMessageItem.getType());
    localRecordInfo.setRecordThumbnail(paramSystemMessageItem.getRecordThumbnail());
    localRecordInfo.setRecordLarge(paramSystemMessageItem.getRecordLarge());
    localRecordInfo.setRecordFeedCnum(paramSystemMessageItem.getCnum());
    localRecordInfo.setRecordFeedTnum(paramSystemMessageItem.getTnum());
    if ((paramSystemMessageItem.getVImgSnapShot(0) != null) && (paramSystemMessageItem.getVImgSnapShot(0).length() > 0))
      localRecordInfo.appendRecordVideoLogo(paramSystemMessageItem.getVImgSnapShot(0));
    String str1 = paramSystemMessageItem.getFpri();
    if (!TextUtils.isEmpty(str1));
    try
    {
      localRecordInfo.fpri = Integer.parseInt(str1);
      label166: JSONObject localJSONObject1 = paramSystemMessageItem.getJSONSubInfo();
      if ((localJSONObject1 != null) && (localJSONObject1.length() > 0))
      {
        JSONArray localJSONArray = localJSONObject1.optJSONArray("images");
        if ((localJSONArray != null) && (localJSONArray.length() > 0))
        {
          JSONObject localJSONObject2 = (JSONObject)localJSONArray.opt(0);
          String str5 = localJSONObject2.optString("thumbnail");
          String str6 = localJSONObject2.optString("large");
          if (!TextUtils.isEmpty(str5))
            localRecordInfo.setRecordSubThumbnail(str5);
          if (!TextUtils.isEmpty(str6))
            localRecordInfo.setRecordSubLarge(str6);
        }
        String str2 = localJSONObject1.optString("rid");
        String str3 = localJSONObject1.optString("intro");
        String str4 = localJSONObject1.optString("location");
        localRecordInfo.setRecordFeedSubRid(str2);
        localRecordInfo.setRecordFeedSubTitle(str3);
        if (!TextUtils.isEmpty(str4))
          localRecordInfo.setRecordFeedSubLocation(str4);
        localRecordInfo.setRepost(true);
        return localRecordInfo;
      }
      localRecordInfo.setRepost(false);
      return localRecordInfo;
    }
    catch (Exception localException)
    {
      break label166;
    }
  }

  public void appendRecordVideoLogo(String paramString)
  {
    if (this.recordVideoUriList == null)
      this.recordVideoUriList = new ArrayList();
    this.recordVideoUriList.add(paramString);
  }

  public String getRecordFeedCnum()
  {
    return this.recordFeedCnum;
  }

  public String getRecordFeedFlogo()
  {
    return this.recordFeedFlogo;
  }

  public String getRecordFeedFname()
  {
    return this.recordFeedFname;
  }

  public String getRecordFeedFuid()
  {
    return this.recordFeedFuid;
  }

  public String getRecordFeedLocation()
  {
    return this.recordFeedLocation;
  }

  public String getRecordFeedNType()
  {
    return this.recordFeedNType;
  }

  public String getRecordFeedRid()
  {
    return this.recordFeedRid;
  }

  public String getRecordFeedSource()
  {
    return this.recordFeedSource;
  }

  public String getRecordFeedStar()
  {
    return this.recordFeedStar;
  }

  public String getRecordFeedSubLocation()
  {
    return this.recordFeedSubLocation;
  }

  public String getRecordFeedSubRid()
  {
    return this.recordFeedSubRid;
  }

  public String getRecordFeedSubTitle()
  {
    return this.recordFeedSubTitle;
  }

  public String getRecordFeedTime()
  {
    return this.recordFeedTime;
  }

  public String getRecordFeedTitle()
  {
    return this.recordFeedTitle;
  }

  public String getRecordFeedTnum()
  {
    return this.recordFeedTnum;
  }

  public String getRecordFeedUpnum()
  {
    return this.recordFeedUpnum;
  }

  public String getRecordLarge()
  {
    return this.recordLarge;
  }

  public String getRecordSubLarge()
  {
    return this.recordSubLarge;
  }

  public String getRecordSubThumbnail()
  {
    return this.recordSubThumbnail;
  }

  public String getRecordThumbnail()
  {
    return this.recordThumbnail;
  }

  public String getRecordVideoLogo(int paramInt)
  {
    if ((this.recordVideoUriList != null) && (this.recordVideoUriList.size() > paramInt))
      return (String)this.recordVideoUriList.get(paramInt);
    return null;
  }

  public String getSourceId()
  {
    return this.sourceId;
  }

  public boolean isRepost()
  {
    return this.isRepost;
  }

  public void setRecordFeedCnum(String paramString)
  {
    this.recordFeedCnum = paramString;
  }

  public void setRecordFeedFlogo(String paramString)
  {
    this.recordFeedFlogo = paramString;
  }

  public void setRecordFeedFname(String paramString)
  {
    this.recordFeedFname = paramString;
  }

  public void setRecordFeedFuid(String paramString)
  {
    this.recordFeedFuid = paramString;
  }

  public void setRecordFeedLocation(String paramString)
  {
    this.recordFeedLocation = paramString;
  }

  public void setRecordFeedNType(String paramString)
  {
    this.recordFeedNType = paramString;
  }

  public void setRecordFeedRid(String paramString)
  {
    this.recordFeedRid = paramString;
  }

  public void setRecordFeedSource(String paramString)
  {
    this.recordFeedSource = paramString;
  }

  public void setRecordFeedStar(String paramString)
  {
    this.recordFeedStar = paramString;
  }

  public void setRecordFeedSubLocation(String paramString)
  {
    this.recordFeedSubLocation = paramString;
  }

  public void setRecordFeedSubRid(String paramString)
  {
    this.recordFeedSubRid = paramString;
  }

  public void setRecordFeedSubTitle(String paramString)
  {
    this.recordFeedSubTitle = paramString;
  }

  public void setRecordFeedTime(String paramString)
  {
    this.recordFeedTime = paramString;
  }

  public void setRecordFeedTitle(String paramString)
  {
    this.recordFeedTitle = paramString;
  }

  public void setRecordFeedTnum(String paramString)
  {
    this.recordFeedTnum = paramString;
  }

  public void setRecordFeedUpnum(String paramString)
  {
    this.recordFeedUpnum = paramString;
  }

  public void setRecordLarge(String paramString)
  {
    this.recordLarge = paramString;
  }

  public void setRecordSubLarge(String paramString)
  {
    this.recordSubLarge = paramString;
  }

  public void setRecordSubThumbnail(String paramString)
  {
    this.recordSubThumbnail = paramString;
  }

  public void setRecordThumbnail(String paramString)
  {
    this.recordThumbnail = paramString;
  }

  public void setRecordVideoLogo(ArrayList<String> paramArrayList)
  {
    this.recordVideoUriList = paramArrayList;
  }

  public void setRepost(boolean paramBoolean)
  {
    this.isRepost = paramBoolean;
  }

  public void setSourceId(String paramString)
  {
    this.sourceId = paramString;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.item.RecordInfo
 * JD-Core Version:    0.6.0
 */