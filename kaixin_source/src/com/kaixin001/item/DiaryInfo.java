package com.kaixin001.item;

public class DiaryInfo
{
  public String diaryFeedContent = null;
  public String diaryFeedCtime = null;
  public String diaryFeedDid = null;
  public String[] diaryFeedImages = null;
  public String diaryFeedPrivacy = null;
  public String diaryFeedStrctime = null;
  public String diaryFeedTitle = null;

  public DiaryInfo clone(DiaryInfo paramDiaryInfo)
  {
    this.diaryFeedDid = paramDiaryInfo.diaryFeedDid;
    this.diaryFeedTitle = paramDiaryInfo.diaryFeedTitle;
    this.diaryFeedContent = paramDiaryInfo.diaryFeedContent;
    this.diaryFeedCtime = paramDiaryInfo.diaryFeedCtime;
    this.diaryFeedStrctime = paramDiaryInfo.diaryFeedStrctime;
    this.diaryFeedPrivacy = paramDiaryInfo.diaryFeedPrivacy;
    this.diaryFeedImages = paramDiaryInfo.diaryFeedImages;
    return this;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.item.DiaryInfo
 * JD-Core Version:    0.6.0
 */