package com.kaixin001.item;

import com.kaixin001.util.ParseNewsInfoUtil;
import com.kaixin001.view.media.KXMediaInfo;
import java.io.Serializable;
import java.util.ArrayList;
import org.json.JSONObject;

public class NewsInfo
  implements Serializable
{
  public String acttype;
  public int btn_render;
  public JSONObject ext;
  public FilmItem filmItem = null;
  public String html_snippet;
  public String img;
  public String mAbstractContent = null;
  public String mCnum = null;
  public String mCommentFlag = null;
  public String mContent = null;
  public String mCtime = null;
  public String mFlogo = null;
  public String mFname = null;
  public String mFpri = null;
  public String mFuid = null;
  public String mGiftType = null;
  public Boolean mHasUp = Boolean.valueOf(false);
  public String mImageUrl = null;
  public String mIntro = null;
  public String mIntroShort = null;
  public boolean mIsFriend = false;
  public String mLocation = null;
  public String mMapUrl;
  public KXMediaInfo mMediaInfo;
  public String mNewsId = null;
  public String mNtype = null;
  public String mNtypename = null;
  public String mOrigRecordId = null;
  public String mOrigRecordIntro = null;
  public String mOrigRecordLocation = null;
  public String mOrigRecordTitle = null;
  public ArrayList<PhotoItem> mPhotoList = null;
  public String mPrivacy = null;
  public String[] mRecordImages = null;
  public ArrayList<RepItem> mRepostList = null;
  public String mRid = null;
  public int mRpNum = 0;
  public String mSource = null;
  public String mSourceId = null;
  public String mStar = null;
  public String mStime = null;
  public KXMediaInfo mSubMediaInfo;
  public String mSubTitle = null;
  public String mThumbnail = null;
  public String mTitle = null;
  public String mTnum = null;
  public String mUpnum = null;
  public int mVNum = 0;
  public ArrayList<String> mVideoSnapshotLIst = null;
  public ArrayList<VoteItem> mVoteList = null;
  public ArrayList<GiftItem> mgiftList = null;
  public ArrayList<LogoItem> mlogoList = null;
  public String rl;
  public String targetid;
  public String txt;
  public String viewid;

  public static ArrayList<KXLinkInfo> makeDiaryIntroList(String paramString)
  {
    return ParseNewsInfoUtil.parseDiaryLinkString(paramString);
  }

  public static ArrayList<KXLinkInfo> makeIntroList(String paramString)
  {
    return ParseNewsInfoUtil.parseNewsLinkString(paramString);
  }

  public static ArrayList<KXLinkInfo> makeTitleList(String paramString)
  {
    return ParseNewsInfoUtil.parseNewsLinkString(paramString);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.item.NewsInfo
 * JD-Core Version:    0.6.0
 */