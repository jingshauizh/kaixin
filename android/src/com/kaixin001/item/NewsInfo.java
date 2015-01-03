package com.kaixin001.item;

import com.kaixin001.util.ParseNewsInfoUtil;
import com.kaixin001.view.media.KXMediaInfo;
import java.io.Serializable;
import java.util.ArrayList;
import org.json.JSONObject;

/*
 * 
 	{"fuid":105873460,
	"fname":"\u5e7d\u9ed8\u5927\u5e08",
	"flogo":"http:\/\/pic.kaixin001.com.cn\/logo\/87\/34\/50_105873460_1.jpg",
	"ntype":1088,
	"ntypename":"plaza_reposte",
	"ctime":1420204443,
	"stime":"01\u670802\u65e5 21:14",
	"intro":"\u8f6c\u5e16\u7ed9\u5927\u5bb6\uff1a",
	"privacy":"",
	"thumbnail":"",
	"id":9664611293,
	"star":1,
	"imglist":"",
	"imgurl":"http:\/\/pic.kaixin001.com.cn\/pic\/app\/41\/86\/1192_200418640_repaste-news.gif",
	"cnum":1,
	"commentflag":"1",
	"title":"\u88c5\u903c\u5230\u725b\u903c\u53ea\u662f\u4e00\u77ac\u95f4\uff01",
	"upnum":3920,
	"rpnum":1290,
	"vnum":1279,
	"group_data":"",
	"source":"\u6765\u81ea\u7f51\u9875",
	"source_id":"",
	"abstract":"","ismyfrined":null}]}
 * */
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