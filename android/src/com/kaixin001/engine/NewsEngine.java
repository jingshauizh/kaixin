package com.kaixin001.engine;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.methods.HttpGet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import java.net.HttpURLConnection;

import com.kaixin001.item.FilmItem;
import com.kaixin001.item.GiftItem;
import com.kaixin001.item.LogoItem;
import com.kaixin001.item.NewsInfo;
import com.kaixin001.item.PhotoItem;
import com.kaixin001.item.RepItem;
import com.kaixin001.item.VoteItem;
import com.kaixin001.model.HomePeopleInfoModel;
import com.kaixin001.model.MessageCenterModel;
import com.kaixin001.model.NewsModel;
import com.kaixin001.model.Setting;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpConnection;
import com.kaixin001.network.HttpMethod;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.JSONUtil;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.FileUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.view.media.KXMediaInfo;

public class NewsEngine extends KXEngine {
	public static final String HOME_ALBUM_FILE = "home_album.kx";
	public static final String HOME_INFO_FILE = "home_info.kx";
	public static final String HOME_NEWS_FILE = "home_news.kx";
	public static final String HOME_UPDATE_FILE = "home_time.kx";
	private static final String LOGTAG = "NewsEngine";
	public static final String NEWS_ALL_FILE = "news_all.kx";
	public static final String NEWS_UPDATE_FILE = "news_time.kx";
	public static final int NUM = 20;
	public static final int RESULT_FAILED = 0;
	private static NewsEngine instance;
	public String msg;

	public static NewsEngine getInstance() {

		if (instance == null)
			instance = new NewsEngine();
		NewsEngine localNewsEngine = instance;
		return localNewsEngine;

	}

	public static boolean isMobileClient(String paramString) {
		int i = 1;
		if (TextUtils.isEmpty(paramString))
			return false;
		String str = paramString.trim();
		try {
			int j = Integer.parseInt(str);
			if (((j >= 1001) && (j <= 1003)) || ((j >= 3001) && (j <= 3015))) {
				return true;
			}
			return false;
		} catch (Exception localException) {
			return false;
		}
	}

	private boolean parseNewsArray(JSONArray paramJSONArray,
			String paramString, NewsModel paramNewsModel) {
		ArrayList localArrayList1 = paramNewsModel.getNewsList();
		if (localArrayList1 == null)
			return false;
		while (true) {
			int i;
			JSONObject localJSONObject1 = null;
			NewsInfo localNewsInfo;
			JSONArray localJSONArray3 = null;
			int i12 = 0;
			JSONArray localJSONArray4 = null;
			int i8 = 0;
			JSONArray localJSONArray5 = null;
			int i6 = 0;
			JSONArray localJSONArray8 = null;
			int i4 = 0;
			JSONArray localJSONArray9 = null;
			int i2 = 0;
			JSONArray localJSONArray10 = null;
			int n = 0;
			try {
				ArrayList localArrayList2 = new ArrayList();
				i = 0;
				if (i < paramJSONArray.length())
					continue;
				int j = 0;
				if (j >= paramJSONArray.length()) {

					String str6 = ((JSONObject) paramJSONArray.get(i))
							.optString("fuid");
					if ((str6 == null) || (str6.equals(""))
							|| (localArrayList2.contains(str6)))

						localArrayList2.add(str6);

				}
				localJSONObject1 = (JSONObject) paramJSONArray.get(j);
				localNewsInfo = new NewsInfo();
				localNewsInfo.mFuid = localJSONObject1.optString("fuid");
				localNewsInfo.mFname = localJSONObject1.optString("fname");
				localNewsInfo.mFlogo = localJSONObject1.optString("flogo");
				localNewsInfo.mNtype = localJSONObject1.optString("ntype");
				localNewsInfo.mRid = localJSONObject1.optString("rid");
				localNewsInfo.mNtypename = localJSONObject1.optString(
						"ntypename", "");
				localNewsInfo.mTitle = localJSONObject1.optString("title", "");
				localNewsInfo.mIntro = localJSONObject1.optString("intro", "");
				localNewsInfo.mIntroShort = localJSONObject1.optString(
						"intro_short", "");
				localNewsInfo.mSubTitle = localJSONObject1.optString(
						"sub_title", "");
				localNewsInfo.mMapUrl = localJSONObject1
						.optString("mapurl", "");
				localNewsInfo.mGiftType = localJSONObject1
						.optString("gifttype");
				if (localJSONObject1.optInt("ismyfrined", 0) != 1)

					localNewsInfo.mIsFriend = true;
				localNewsInfo.mImageUrl = localJSONObject1.optString("imgurl",
						"");
				localNewsInfo.mRpNum = localJSONObject1.optInt("rpnum", 0);
				localNewsInfo.mVNum = localJSONObject1.optInt("vnum", 0);
				localNewsInfo.mAbstractContent = localJSONObject1.optString(
						"abstract", "");
				JSONObject localJSONObject2 = localJSONObject1
						.optJSONObject("sub_info");
				JSONArray localJSONArray1 = localJSONObject1
						.optJSONArray("video");
				if ((localJSONArray1 != null) || (localJSONObject2 == null))
					continue;
				localJSONArray1 = localJSONObject2.optJSONArray("video");
				if (localJSONArray1 == null)
					continue;
				if (localNewsInfo.mVideoSnapshotLIst != null)

					localNewsInfo.mVideoSnapshotLIst = new ArrayList();

				int m = localJSONArray1.length();

				localNewsInfo.mMediaInfo = null;
				String str1 = localJSONObject1.optString("audiourl");
				if (TextUtils.isEmpty(str1))
					continue;
				localNewsInfo.mMediaInfo = new KXMediaInfo();
				localNewsInfo.mMediaInfo.setId(localNewsInfo.mRid);
				localNewsInfo.mMediaInfo.setUrl(str1);
				localNewsInfo.mMediaInfo.setDuration(localJSONObject1
						.optString("aulen", "1"));
				if (localJSONObject2 == null)
					continue;
				localNewsInfo.mSubMediaInfo = null;
				String str2 = localJSONObject2.optString("audiourl");
				if (TextUtils.isEmpty(str2))
					continue;
				localNewsInfo.mSubMediaInfo = new KXMediaInfo();
				localNewsInfo.mSubMediaInfo.setId(localNewsInfo.mRid);
				localNewsInfo.mSubMediaInfo.setUrl(str2);
				localNewsInfo.mSubMediaInfo.setDuration(localJSONObject2
						.optString("aulen", "1"));
				localNewsInfo.mOrigRecordId = localJSONObject2.optString("rid");
				localNewsInfo.mOrigRecordIntro = localJSONObject2
						.optString("intro");
				localNewsInfo.mOrigRecordLocation = localJSONObject2
						.optString("location");
				localNewsInfo.mOrigRecordTitle = localJSONObject2.optString(
						"sub_title", "");
				JSONArray localJSONArray2 = localJSONObject2
						.optJSONArray("images");
				if ((localJSONArray2 == null)
						|| (localJSONArray2.length() <= 0))
					continue;
				localNewsInfo.mRecordImages = new String[2];
				JSONObject localJSONObject13 = localJSONArray2.getJSONObject(0);
				localNewsInfo.mRecordImages[0] = localJSONObject13.optString(
						"thumbnail", "");
				localNewsInfo.mRecordImages[1] = localJSONObject13.optString(
						"large", "");
				localNewsInfo.mCtime = localJSONObject1.optString("ctime", "");
				localNewsInfo.mStime = localJSONObject1.optString("stime", "");
				localNewsInfo.mThumbnail = localJSONObject1.optString(
						"thumbnail", "");
				if (localJSONObject1.isNull("id"))
					continue;
				localNewsInfo.mNewsId = localJSONObject1.optString("id");
				if (localJSONObject1.opt("star") != null) {

					localNewsInfo.mStar = localJSONObject1.optString("star",
							"0");
					localNewsInfo.mCnum = localJSONObject1.optString("cnum",
							"0");
					localNewsInfo.mTnum = localJSONObject1.optString("tnum",
							"0");
					localNewsInfo.mUpnum = localJSONObject1.optString("upnum",
							"0");
					localNewsInfo.mPrivacy = localJSONObject1.optString(
							"privacy", "");
					localNewsInfo.mCommentFlag = localJSONObject1.optString(
							"commentflag", "");
					if (localJSONObject1.isNull("fpri"))
						continue;
					localNewsInfo.mFpri = localJSONObject1.optString("fpri");
					localNewsInfo.mLocation = localJSONObject1
							.optString("location");
					localNewsInfo.mSource = localJSONObject1.optString(
							"source", "");
					localNewsInfo.mSourceId = localJSONObject1.optString(
							"source_id", "");
					localJSONArray3 = localJSONObject1.optJSONArray("imglist");
					if ((localJSONArray3 == null)
							|| (localJSONArray3.length() <= 0)
							|| ((!localNewsInfo.mNtype.equals("1"))
									&& (!localNewsInfo.mNtype.equals("1242")) && (!localNewsInfo.mNtype
										.equals("1192"))))
						continue;
					localNewsInfo.mPhotoList = new ArrayList();
					i12 = 0;

					localJSONArray4 = localJSONObject1.optJSONArray("replist");
					if ((localJSONArray4 == null)
							|| (localJSONArray4.length() <= 0))
						continue;
					localNewsInfo.mRepostList = new ArrayList();
					i8 = 0;
					int i9 = localJSONArray4.length();
					if (i8 < i9)

						localJSONArray5 = localJSONObject1
								.optJSONArray("vote_list");

					localNewsInfo.mVoteList = new ArrayList();
					i6 = 0;
					int i7 = localJSONArray5.length();

					JSONArray localJSONArray6 = localJSONObject1
							.optJSONArray("images");
					if ((localJSONArray6 == null)
							|| (localJSONArray6.length() <= 0))
						continue;
					localNewsInfo.mRecordImages = new String[2];
					JSONObject localJSONObject9 = localJSONArray6
							.getJSONObject(0);
					localNewsInfo.mRecordImages[0] = localJSONObject9
							.optString("thumbnail", "");
					localNewsInfo.mRecordImages[1] = localJSONObject9
							.optString("large", "");
					JSONArray localJSONArray7 = localJSONObject1
							.optJSONArray("imglist");
					if (!"2".equals(localNewsInfo.mNtype))
						continue;
					localNewsInfo.mContent = localJSONObject1.optString(
							"content", "");
					if ((localJSONArray7 == null)
							|| (localJSONArray7.length() <= 0))
						continue;
					localNewsInfo.mRecordImages = new String[2];
					JSONObject localJSONObject8 = localJSONArray7
							.getJSONObject(0);
					localNewsInfo.mRecordImages[0] = localJSONObject8
							.optString("thumbnail", "");
					localNewsInfo.mRecordImages[1] = localJSONObject8
							.optString("large", "");
					if ((!"1192".equals(localNewsInfo.mNtype))
							|| (localNewsInfo.mPhotoList == null)
							|| (localNewsInfo.mPhotoList.size() <= 0))
						continue;
					localNewsInfo.mRecordImages = new String[2];
					PhotoItem localPhotoItem1 = (PhotoItem) localNewsInfo.mPhotoList
							.get(0);
					localNewsInfo.mRecordImages[0] = localPhotoItem1.thumbnail;
					localNewsInfo.mRecordImages[1] = localPhotoItem1.large;
					localJSONArray8 = localJSONObject1
							.optJSONArray("sub_userlist");
					if ((localJSONArray8 == null)
							|| (localJSONArray8.length() <= 0))
						continue;
					localNewsInfo.mlogoList = new ArrayList();
					i4 = 0;
					int i5 = localJSONArray8.length();
					if (i4 < i5)

						localJSONArray9 = localJSONObject1
								.optJSONArray("friendlist");

					localNewsInfo.mlogoList = new ArrayList();
					i2 = 0;
					int i3 = localJSONArray9.length();

					localJSONArray10 = localJSONObject1
							.optJSONArray("sub_giftlist");
					if ((localJSONArray10 == null)
							|| (localJSONArray10.length() <= 0))
						continue;
					localNewsInfo.mgiftList = new ArrayList();
					n = 0;
					int i1 = localJSONArray10.length();

					JSONObject localJSONObject4 = localJSONObject1
							.optJSONObject("films");
					if ((localJSONObject4 == null)
							|| (localJSONObject4.length() <= 0))
						continue;
					FilmItem localFilmItem = new FilmItem();
					localFilmItem
							.setmMid(localJSONObject4.optString("mid", ""));
					localFilmItem.setmName(localJSONObject4.optString("name",
							""));
					localFilmItem.setmScore(localJSONObject4.optString("score",
							""));
					localFilmItem.setmYear(localJSONObject4.optString("year",
							""));
					localFilmItem.setmCover(localJSONObject4.optString("cover",
							""));
					localFilmItem.setmZone(localJSONObject4.optString("zone",
							""));
					localFilmItem.setmMType(localJSONObject4.optString("mtype",
							""));
					localFilmItem.setmDirector(localJSONObject4.optString(
							"director", ""));
					localFilmItem.setmActor(localJSONObject4.optString("actor",
							""));
					localFilmItem.setmType(localJSONObject4.optString("type",
							""));
					localFilmItem.setmComtitle(localJSONObject4.optString(
							"comtitle", ""));
					localFilmItem.setmWant(localJSONObject4.optString("want",
							""));
					localFilmItem.setmMyScore(localJSONObject4.optString(
							"myscore", ""));
					localFilmItem.setmComments(localJSONObject4.optString(
							"comments", ""));
					localNewsInfo.filmItem = localFilmItem;
					localArrayList1.add(localNewsInfo);
					j++;

					JSONObject localJSONObject3 = localJSONArray1
							.optJSONObject(j);

					localNewsInfo.mVideoSnapshotLIst.add(localJSONObject3
							.optString("thumb"));

					localNewsInfo.mNewsId = localJSONObject1.optString("rid",
							"");
					continue;
				}
			} catch (Exception localException) {
				KXLog.e("NewsEngine", "ParseNewsArray", localException);
				return false;
			}
			String str3 = localJSONObject1.optString("fstar", "0");

			JSONObject localJSONObject12 = null;
			try {
				localJSONObject12 = (JSONObject) localJSONArray3.get(i12);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			PhotoItem localPhotoItem2 = new PhotoItem();
			localPhotoItem2.privacy = localJSONObject12.optString("privacy");
			localPhotoItem2.index = localJSONObject12.optString("pos");
			localPhotoItem2.albumTitle = localJSONObject12
					.optString("albumtitle");
			localPhotoItem2.albumId = localJSONObject12.optString("albumid");
			localPhotoItem2.albumType = 2;
			localPhotoItem2.thumbnail = localJSONObject12
					.optString("thumbnail");
			localPhotoItem2.pid = localJSONObject12.optString("pid");
			localPhotoItem2.title = localJSONObject12.optString("title");
			localPhotoItem2.picnum = localJSONObject12.optString("picnum");
			localPhotoItem2.visible = localJSONObject12.optString("visible",
					"1");
			localPhotoItem2.fuid = localJSONObject12.optString("uid", null);
			if (localPhotoItem2.fuid == null)
				;
			for (String str5 = localNewsInfo.mFuid;; str5 = localPhotoItem2.fuid) {
				localPhotoItem2.fuid = str5;
				localPhotoItem2.large = localJSONObject12
						.optString("large", "");
				localNewsInfo.mPhotoList.add(localPhotoItem2);
				i12++;
				break;
			}
			JSONObject localJSONObject11 = null;
			try {
				localJSONObject11 = (JSONObject) localJSONArray4.get(i8);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			RepItem localRepItem = new RepItem();
			localRepItem.id = localJSONObject11.optString("id");
			localRepItem.category = localJSONObject11.optInt("category");
			localRepItem.title = localJSONObject11.optString("title");
			localRepItem.myview = localJSONObject11.optString("myview", "");
			localRepItem.ftitle = localJSONObject11.optString("ftitle", "");
			localRepItem.vthumb = localJSONObject11.optString("vthumb", "");
			JSONArray localJSONArray11 = localJSONObject11
					.optJSONArray("imglist");
			localRepItem.mThumbImg = null;
			if ((localJSONArray11 != null) && (localJSONArray11.length() > 0)) {
				try {
					localRepItem.mThumbImg = ((String) localJSONArray11.get(0));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				localRepItem.mRepostImgList = new ArrayList();
			}
			for (int i10 = 0;; i10++) {
				int i11 = localJSONArray11.length();
				if (i10 >= i11) {
					localRepItem.mContent = localJSONObject11.optString(
							"abstract", "");
					localNewsInfo.mRepostList.add(localRepItem);
					i8++;
					break;
				}
				String str4 = null;
				try {
					str4 = (String) localJSONArray11.get(i10);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				localRepItem.mRepostImgList.add(str4);
			}
			JSONObject localJSONObject10 = null;
			try {
				localJSONObject10 = (JSONObject) localJSONArray5.get(i6);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			VoteItem localVoteItem = new VoteItem();
			localVoteItem.mType = localJSONObject10.optString("type");
			localVoteItem.mUid = localJSONObject10.optString("uid");
			localVoteItem.mToUid = localJSONObject10.optString("touid");
			localVoteItem.mCtime = localJSONObject10.optString("ctime");
			localVoteItem.mId = localJSONObject10.optString("id");
			localVoteItem.mTitle = localJSONObject10.optString("title");
			localNewsInfo.mVoteList.add(localVoteItem);
			i6++;

			JSONObject localJSONObject7 = null;
			try {
				localJSONObject7 = (JSONObject) localJSONArray8.get(i4);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			LogoItem localLogoItem2 = new LogoItem();
			localLogoItem2.slogo = localJSONObject7.optString("slogo");
			localLogoItem2.sname = localJSONObject7.optString("sname");
			localLogoItem2.suid = localJSONObject7.optString("suid");
			localNewsInfo.mlogoList.add(localLogoItem2);
			i4++;

			JSONObject localJSONObject6 = null;
			try {
				localJSONObject6 = (JSONObject) localJSONArray9.get(i2);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			LogoItem localLogoItem1 = new LogoItem();
			localLogoItem1.slogo = localJSONObject6.optString("flogo");
			localLogoItem1.sname = localJSONObject6.optString("fname");
			localLogoItem1.suid = localJSONObject6.optString("fuid");
			localNewsInfo.mlogoList.add(localLogoItem1);
			i2++;

			JSONObject localJSONObject5 = null;
			try {
				localJSONObject5 = (JSONObject) localJSONArray10.get(n);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			GiftItem localGiftItem = new GiftItem(
					localJSONObject5.optString("gid"),
					localJSONObject5.optString("gname"),
					localJSONObject5.optString("gicon"));
			localNewsInfo.mgiftList.add(localGiftItem);
			n++;

			i++;

			int k = 0;

			boolean bool = false;

			k++;
		}
	}

	private void updateWidget(Context paramContext) {
		if (paramContext != null)
			paramContext
					.sendBroadcast(new Intent("com.kaixin001.WIDGET_UPDATE"));
	}

	public boolean getHomeForData(Context paramContext,
			NewsModel paramNewsModel, String paramString1, String paramString2,
			String paramString3, String paramString4, String paramString5,
			String paramString6) throws SecurityErrorException {
		this.ret = 0;
		int i = Integer.parseInt(paramString1);
		String str1 = Protocol.getInstance().makeNewsRequest(paramContext,
				true, paramString1, paramString2, User.getInstance().getUID(),
				paramString3, paramString4, paramString5, paramString6);
		HttpProxy localHttpProxy = new HttpProxy(paramContext);
		try {
			String str4 = localHttpProxy.httpGet(str1, null, null);
			String str2 = str4;
			String str3 = FileUtil.getKXCacheDir(paramContext);
			if (User.getInstance().getUID().compareTo(paramString6) == 0) {
				int j = 1;
				if (parseNewsJSON(paramContext, true, str2, paramNewsModel, i,
						paramString3, paramString5, paramString6))
					return false;
			}
		} catch (Exception localException) {
			String str2;
			String str3 = null;
			int j;

			KXLog.e("NewsEngine", "getHomeForData error", localException);
			str2 = null;

			j = 0;

			if (!getInstance().setHomeNewsData(paramContext, str2))
				return false;
			if ((j != 0)
					&& ((TextUtils.isEmpty(paramString1)) || (paramString1
							.compareTo("0") == 0))) {
				FileUtil.setCacheData(str3, paramString6, "home_news.kx", str2);
				setUpdateTime(NewsModel.getMyHomeModel(), str3, paramString6,
						"home_time.kx", 1);
			}
			if (this.ret == 1)
				return true;
		}
		return false;
	}

	public boolean getMutualFriendsData(Context paramContext,
			HomePeopleInfoModel paramHomePeopleInfoModel, String paramString1,
			String paramString2) throws SecurityErrorException {
		this.ret = 0;
		String str1 = Protocol.getInstance().makeMutualFriendsRequest(
				paramString2, paramString1);
		HttpProxy localHttpProxy = new HttpProxy(paramContext);
		try {
			String str3 = localHttpProxy.httpGet(str1, null, null);
			String str2 = str3;
			if (!parseMutualFriendsJSON(paramContext, paramHomePeopleInfoModel,
					str2, paramString1))
				return false;
		} catch (Exception localException) {
			while (true) {
				KXLog.e("NewsEngine", "getMutualFriendsData error",
						localException);
				String str2 = null;
			}
		}
		return true;
	}

	public boolean getNewsData(Context paramContext, NewsModel paramNewsModel,
			String paramString1, String paramString2, String paramString3,
			String paramString4, String paramString5, String paramString6)
			throws SecurityErrorException {
		this.ret = 0;		
		User localUser = User.getInstance();
		HttpProxy localHttpProxy = new HttpProxy(paramContext);	
		HttpConnection localHttpConnection = new HttpConnection(paramContext);
		String	str2 = Protocol.getInstance().makeLookNewsRequest(paramContext,
					true, paramString1, paramString2, localUser.getUID(),
					paramString3, paramString4, paramString5, paramString6);
		Log.i("NewsEngine",  "url="+str2);
		try {			
			//String	str4 = localHttpProxy.httpGet(str2, null, null);
			/*
			HashMap localHashMap = new HashMap();
			Log.i("NewsEngine",  "localHttpConnection="+localHttpConnection.toString());
			String str4 = localHttpConnection.httpRequest(str2,
					HttpMethod.GET, localHashMap, null, null, null);
			Log.i("NewsEngine",  "str4="+str4);
			String localObject1 = str4;
			parseNewsJSON(paramContext, true,
					(String) localObject1, paramNewsModel, 0, paramString3,
					paramString5, paramString6);
					
			
			StringBuilder url = new StringBuilder(str2);
			
			HttpURLConnection conn = (HttpURLConnection)new URL(url.toString()).openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("GET");
			if(conn.getResponseCode() == 200){
				Log.i("NewsEngine",  "get data");
			}*/
			
			List<Map<String, String>> objectMap =  JSONUtil.getJSONObject(str2);
			
		
			
		} catch (Exception localException1) {
			localException1.printStackTrace();
			Log.i("NewsEngine",  localException1.getMessage());
		}
		return true;

	}

	public boolean getNewsData_bakc(Context paramContext,
			NewsModel paramNewsModel, String paramString1, String paramString2,
			String paramString3, String paramString4, String paramString5,
			String paramString6) throws SecurityErrorException {
		this.ret = 0;
		int i = Integer.parseInt(paramString1);
		User localUser = User.getInstance();
		HttpProxy localHttpProxy = new HttpProxy(paramContext);
		boolean bool = false;
		if (!TextUtils.isEmpty(paramString5)) {
			String str8 = String.valueOf(100);
			if (paramString5.equals(str8))
				bool = true;
		}

		String str1 = String.valueOf(100);
		if (paramString5.equals(str1))
			paramString5 = "";
		if (i == 0) {
			String str5 = null;
			if ((localUser.GetLookAround()) || (paramString5.equals("11"))) {
				str5 = Protocol.getInstance().makeLookNewsRequest(paramContext,
						true, "0", paramString2, localUser.getUID(),
						paramString3, null, paramString5, paramString6);
			}
			try {
				if ((!localUser.GetLookAround())
						&& (!paramString5.equals("11")))
					localHttpProxy.httpGet(str5, null, null);
				String str6;

				bool = false;

				if (paramString5.equals("2")) {
					str5 = Protocol.getInstance()
							.makeNewsDiaryRequest(paramContext, "0",
									paramString2, localUser.getUID());

				}
				str5 = Protocol.getInstance().makeNewsRequest(paramContext,
						true, "0", paramString2, localUser.getUID(),
						paramString3, null, paramString5, paramString6);

				HttpGet localHttpGet2 = new HttpGet();
				localHttpGet2.setHeader("Connection", "Keep-Alive");
				localHttpGet2.setURI(new URI(Setting.getInstance().getNewHost()
						+ str5));
				str6 = localHttpProxy.httpGet(localHttpGet2);

			} catch (Exception localException2) {
				Object localObject2;

				KXLog.e("NewsEngine", "getNewsData error", localException2);
				localObject2 = null;

				NewsModel.setHasNew(false);
			}

			updateWidget(paramContext);

			String str2 = null;
			if ((localUser.GetLookAround()) || (paramString5.equals("11")))
				str2 = Protocol.getInstance().makeLookNewsRequest(paramContext,
						true, paramString1, paramString2, localUser.getUID(),
						paramString3, paramString4, paramString5, paramString6);
			try {
				String str4;
				if ((!localUser.GetLookAround())
						&& (!paramString5.equals("11")))
					str4 = localHttpProxy.httpGet(str2, null, null);
				String str3;

				if (paramString5.equals("2")) {
					str2 = Protocol.getInstance().makeNewsDiaryRequest(
							paramContext, paramString4, paramString2,
							localUser.getUID());

				}
				str2 = Protocol.getInstance().makeNewsRequest(paramContext,
						true, paramString1, paramString2, localUser.getUID(),
						paramString3, paramString4, paramString5, paramString6);

				HttpGet localHttpGet1 = new HttpGet();
				localHttpGet1.setHeader("Connection", "Keep-Alive");
				localHttpGet1.setURI(new URI(Setting.getInstance().getNewHost()
						+ str2));
				str3 = localHttpProxy.httpGet(localHttpGet1);

				return false;
			} catch (Exception localException1) {

				KXLog.e("NewsEngine", "getNewsData error", localException1);
				Object localObject1 = null;

			}
		}
		return bool;

	}

	public boolean loadHomeDataCache(Context paramContext, String paramString)
			throws SecurityErrorException {
		if ((paramContext == null) || (TextUtils.isEmpty(paramString)))
			;
		String str1;
		String str2;

		str1 = FileUtil.getKXCacheDir(paramContext);

		loadUpdateTime(NewsModel.getMyHomeModel(), str1, paramString,
				"home_time.kx");
		return true;
	}

	public boolean loadNewsCache(Context paramContext, String paramString)
			throws SecurityErrorException {
		if ((paramContext == null) || (TextUtils.isEmpty(paramString)))
			;
		String str1;
		String str2;

		str1 = FileUtil.getKXCacheDir(paramContext);
		str2 = FileUtil.getCacheData(str1, paramString, "news_all.kx");

		loadUpdateTime(NewsModel.getInstance(), str1, paramString,
				"news_time.kx");
		updateWidget(paramContext);
		return true;
	}

	public boolean loadUpdateTime(NewsModel paramNewsModel,
			String paramString1, String paramString2, String paramString3) {
		try {
			String str = FileUtil.getCacheData(paramString1, paramString2,
					paramString3);
			if (!TextUtils.isEmpty(str))
				str = MessageCenterModel.formatTimestamp(Long.parseLong(str));
			paramNewsModel.setUpdateTime(str);
			return true;
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return false;
	}

	public int parseFeedbackJSON(Context paramContext, String paramString1,
			String paramString2) throws SecurityErrorException {
		JSONObject localJSONObject = super
				.parseJSON(paramContext, paramString1);
		int i = 0;
		if (localJSONObject == null)
			;
		String str;

		i = localJSONObject.optInt("ret", 0);
		str = localJSONObject.optString("iconsrc");
		if (i == 0) {
			this.msg = localJSONObject.optString("msg", null);
			return i;
		}
		this.msg = null;

		User localUser = User.getInstance();
		if (localUser.getUID().compareTo(paramString2) == 0) {
			localUser.setLogo(str);
			localUser.saveUserInfo(paramContext);
			NewsModel.getMyHomeModel().setLogo120(str);
			return i;
		}
		NewsModel.getHomeModel().setLogo120(str);
		return i;
	}

	public boolean parseMutualFriendsJSON(Context paramContext,
			HomePeopleInfoModel paramHomePeopleInfoModel, String paramString1,
			String paramString2) throws SecurityErrorException {
		JSONObject localJSONObject1 = super.parseJSON(paramContext, true,
				paramString1);
		if (localJSONObject1 == null)
			return false;
		int i = Integer.parseInt(paramString2);
		ArrayList localArrayList = new ArrayList();
		int j = localJSONObject1.optInt("total");
		if (j <= 0)
			return false;
		try {
			JSONArray localJSONArray = localJSONObject1
					.getJSONArray("userinfo");
			for (int k = 0;; k++) {
				if (k >= localJSONArray.length()) {
					String str = ((HomePeopleInfoModel.PeopleInfo) localArrayList
							.get(-1 + localArrayList.size())).mUid;
					if (i == 0) {
						paramHomePeopleInfoModel.clear();
						paramHomePeopleInfoModel.mTotalNum = j;
						paramHomePeopleInfoModel.mLastUid = str;
					}
					paramHomePeopleInfoModel.getPeopleInfoList().addAll(
							localArrayList);
					return true;
				}
				JSONObject localJSONObject2 = (JSONObject) localJSONArray
						.get(k);
				localArrayList.add(new HomePeopleInfoModel.PeopleInfo(
						localJSONObject2.optString("uid", ""), localJSONObject2
								.optString("name", ""), localJSONObject2
								.optString("icon", "")));
			}
		} catch (Exception localException) {
			KXLog.e("NewsEngine", "parseMutualFriendsJSON", localException);
		}
		return false;
	}
	
	/**
	 * 
	 * 
	{"ret":1,"total":1301,"news":[
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
	 * @param paramContext
	 * @param paramBoolean
	 * @param paramString1
	 * @param paramNewsModel
	 * @param paramInt
	 * @param paramString2
	 * @param paramString3
	 * @param paramString4
	 * @return
	 * @throws SecurityErrorException
	 */

	public boolean parseNewsJSON(Context paramContext, boolean paramBoolean,
			String paramString1, NewsModel paramNewsModel, int paramInt,
			String paramString2, String paramString3, String paramString4)
			throws SecurityErrorException {
		JSONObject localJSONObject1 = super.parseJSON(paramContext,
				paramBoolean, paramString1);
		if (localJSONObject1 == null)
			return false;
		try {
			JSONObject localJSONObject2 = localJSONObject1
					.optJSONObject("upgradeInfo");
			if (localJSONObject2 != null) {
				paramNewsModel.setExp_award(localJSONObject2.optString(
						"exp_award", ""));
				paramNewsModel.setExp(localJSONObject2.optString("exp", ""));
				paramNewsModel
						.setTitle(localJSONObject2.optString("title", ""));
				paramNewsModel.setExp_to_upgrade(localJSONObject2.optString(
						"exp_to_upgrade", ""));
				paramNewsModel
						.setLevel(localJSONObject2.optString("level", ""));
				JSONObject localJSONObject3 = localJSONObject2
						.optJSONObject("image");
				if (localJSONObject3 != null) {
					paramNewsModel.setSmall(localJSONObject3.optString("small",
							""));
					paramNewsModel.setMiddle(localJSONObject3.optString(
							"middle", ""));
					paramNewsModel.setLarge(localJSONObject3.optString("large",
							""));
				}
			}
			if (User.getInstance().getUID().equals(paramString4)) {
				String str6 = localJSONObject1.optString("realname");
				User.getInstance().setName(str6);
			}
			paramNewsModel.setctime(localJSONObject1.optString("ctime",
					String.valueOf(new Date().getTime() / 1000L)));
			JSONArray localJSONArray1 = null;
			if (paramInt == 0) {
				localJSONArray1 = localJSONObject1.optJSONArray("applist");
				if (localJSONArray1 == null)
					;
			}
			for (int i = 0;; i++) {
				String str3;
				if (i >= localJSONArray1.length()) {
					paramNewsModel.setLastNum(localJSONObject1.optInt("n", 0));
					paramNewsModel.setTotalNum(
							localJSONObject1.optInt("total", 0), paramString3);
					String str1 = localJSONObject1.optString("logo", "");
					String str2 = localJSONObject1.optString("logo120", "");
					if (!TextUtils.isEmpty(str1)) {
						paramNewsModel.setLogo(str1);
						if ((paramString4 != null)
								&& (paramString4.equals(User.getInstance()
										.getUID())))
							User.getInstance().setLogo(str1);
					}
					if (!TextUtils.isEmpty(str2)) {
						paramNewsModel.setLogo120(str2);
						if ((paramString4 != null)
								&& (paramString4.equals(User.getInstance()
										.getUID())))
							User.getInstance().setLogo120(str2);
					}
					paramNewsModel.setStatus(localJSONObject1.optString("note",
							""));
					str3 = localJSONObject1.optString("stime");

					paramNewsModel.setStatustime("");
				}
				while (true) {
					paramNewsModel.setRealname(localJSONObject1
							.optString("realname"));
					paramNewsModel.setOnline(localJSONObject1
							.optString("online"));
					paramNewsModel
							.setIstar(localJSONObject1.optString("istar"));
					paramNewsModel.setIsmyfriend(localJSONObject1
							.optString("ismyfriend"));
					paramNewsModel.setPrivacy(localJSONObject1
							.optString("indexprivacy"));
					paramNewsModel.setStarintro(localJSONObject1
							.optString("starintro"));
					paramNewsModel.setGender(localJSONObject1
							.optString("gender"));
					paramNewsModel.setFansCount(localJSONObject1.optInt(
							"vcount", -1));
					paramNewsModel.setConstellation(localJSONObject1.optString(
							"constellation", ""));
					paramNewsModel.setHomeCity(localJSONObject1.optString(
							"city", ""));
					paramNewsModel.setSameFriends(localJSONObject1.optString(
							"samefriends", ""));
					JSONObject localJSONObject4 = localJSONObject1
							.optJSONObject("cover");
					if (localJSONObject4 != null) {
						String str4 = localJSONObject4.optString("id", "");
						String str5 = localJSONObject4.optString("url", "");
						if ((!TextUtils.isEmpty(str5))
								&& (!TextUtils.isEmpty(str4))) {
							paramNewsModel.setCoverId(str4);
							paramNewsModel.setCoverUrl(str5);
							if ((paramString4 != null)
									&& (User.getInstance().getUID()
											.equals(paramString4))) {
								User.getInstance().setCoverId(str4);
								User.getInstance().setCoverUrl(str5);
							}
						}
					}
					paramNewsModel.setFriendNum(localJSONObject1.optInt(
							"friendnum", 0));
					paramNewsModel.setNewPhotoUrl(localJSONObject1.optString(
							"new_photo", ""));
					JSONArray localJSONArray2 = localJSONObject1
							.getJSONArray("news");
					paramNewsModel.getNewsList().clear();
					paramNewsModel.setRefreshNum(localJSONArray2.length());
					if (parseNewsArray(localJSONArray2, paramString2,
							paramNewsModel))
						break;

					JSONObject localJSONObject5 = (JSONObject) localJSONArray1
							.get(i);
					int j = localJSONObject5.optInt("aid", 1);
					int k = localJSONObject5.optInt("num", 0);
					switch (j) {
					case 1:
						paramNewsModel.setAllPhotoNum(k);
						break;
					case 2:
						paramNewsModel.setDiaryNum(k);
						break;
					case 1018:
						paramNewsModel.setRecordNum(k);
						break;
					case 1088:
						paramNewsModel.setRepostNum(k);

						JSONArray localJSONArray3 = localJSONObject1
								.optJSONArray("news");
						if (localJSONArray3 == null)
							return false;
						paramNewsModel.setTotalNum(
								localJSONObject1.optInt("total", 0),
								paramString3);
						paramNewsModel.setRefreshNum(localJSONArray3.length());
						paramNewsModel.setRealname(localJSONObject1
								.optString("realname"));
						paramNewsModel.setOnline(localJSONObject1
								.optString("online"));
						paramNewsModel.setIstar(localJSONObject1
								.optString("istar"));
						paramNewsModel.setIsmyfriend(localJSONObject1
								.optString("ismyfriend"));
						paramNewsModel.setPrivacy(localJSONObject1
								.optString("indexprivacy"));
						paramNewsModel.setStarintro(localJSONObject1
								.optString("starintro"));
						paramNewsModel.setGender(localJSONObject1
								.optString("gender"));
						paramNewsModel.setFansCount(localJSONObject1.optInt(
								"vcount", -1));
						paramNewsModel.setConstellation(localJSONObject1
								.optString("constellation", ""));
						paramNewsModel.setHomeCity(localJSONObject1.optString(
								"city", ""));
						paramNewsModel.setSameFriends(localJSONObject1
								.optString("samefriends", ""));
						JSONObject localJSONObject6 = localJSONObject1
								.optJSONObject("cover");
						if (localJSONObject6 != null) {
							paramNewsModel.setCoverId(localJSONObject6
									.optString("id", ""));
							paramNewsModel.setCoverUrl(localJSONObject6
									.optString("url", ""));
						}
						paramNewsModel.setFriendNum(localJSONObject1.optInt(
								"friendnum", 0));
						paramNewsModel.setNewPhotoUrl(localJSONObject1
								.optString("new_photo", ""));
						boolean bool = parseNewsArray(localJSONArray3,
								paramString2, paramNewsModel);
						if (!bool)
							return false;
					}
				}
				return true;
			}
		} catch (Exception localException) {
		}
		return false;
	}
	
	
	/*
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
	 **/
	/**
	 * 
	 
	 * @param paramContext
	 * @param paramString1
	 * @param paramString2
	 * @return
	 * @throws SecurityErrorException
	 */

	public int postUserLogoRequest(Context paramContext, String paramString1,
			String paramString2) throws SecurityErrorException {
		String str1 = Protocol.getInstance().makeUpdateLogoRequest();
		File localFile = new File(paramString1);
		if (!localFile.exists())
			return -1;
		HashMap localHashMap = new HashMap();
		localHashMap.put("uid", User.getInstance().getUID());
		if (!TextUtils.isEmpty(paramString1))
			localHashMap.put("upload_img", localFile);
		HttpProxy localHttpProxy = new HttpProxy(paramContext);
		String str2;
		try {
			String str3 = localHttpProxy.httpPost(str1, localHashMap, null,
					null);
			str2 = str3;
			if (TextUtils.isEmpty(str2))
				return 0;
		} catch (Exception localException) {
			while (true) {
				KXLog.e("NewsEngine", "postInfoCompletedRequest error",
						localException);
				str2 = null;
			}
		}
		return parseFeedbackJSON(paramContext, str2, paramString2);
	}

	public boolean setHomeNewsData(Context paramContext, String paramString) {
		try {
			FileUtil.setCacheData(FileUtil.getKXCacheDir(paramContext), User
					.getInstance().getUID(), "home_info.kx", paramString);
			return true;
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return false;
	}

	public boolean setNewsCount(Context paramContext) {
		String str1 = User.getInstance().getUID();
		String str2 = Protocol.getInstance().getNewsCountUrl(str1,
				NewsModel.getInstance().getctime());
		HttpProxy localHttpProxy = new HttpProxy(paramContext);
		while (true) {
			String str3;
			try {
				String str4 = localHttpProxy.httpGet(str2, null, null);
				str3 = str4;
				if (TextUtils.isEmpty(str3))
					return false;
			} catch (Exception localException) {
				KXLog.e("NewsEngine", "updateNews error", localException);
				str3 = null;
				continue;
			}
			try {
				JSONObject localJSONObject2 = super.parseJSON(paramContext,
						str3);
				JSONObject localJSONObject1 = localJSONObject2;
				if (localJSONObject1 == null)
					continue;
				if (localJSONObject1.optInt("hasnew", 0) != 0) {
					NewsModel.getInstance().setNewsCount(
							localJSONObject1.optInt("newscount", 0));
					NewsModel.getInstance().setPublicMore(
							localJSONObject1.optInt("publicMore", 0));
					return true;
				}
			} catch (SecurityErrorException localSecurityErrorException) {

				localSecurityErrorException.printStackTrace();
				JSONObject localJSONObject1 = null;

				NewsModel.getInstance().setNewsCount(0);

			}
		}
	}

	public boolean setNewsData(Context paramContext, String paramString,
			boolean paramBoolean) {
		try {
			String str1 = FileUtil.getKXCacheDir(paramContext);
			String str2 = User.getInstance().getUID();
			if (!parseNewsJSON(paramContext, false, paramString,
					NewsModel.getInstance(), 0, "all", null, str2))
				return false;
			if (paramBoolean) {
				FileUtil.setCacheData(str1, str2, "news_all.kx", paramString);
				setUpdateTime(NewsModel.getInstance(), str1, str2,
						"news_time.kx", 1);
			} else {
				setUpdateTime(NewsModel.getInstance(), str1, str2,
						"news_time.kx", 0);
			}
		} catch (Exception localException) {
			localException.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean setUpdateTime(NewsModel paramNewsModel, String paramString1,
			String paramString2, String paramString3, int paramInt) {
		try {
			long l = new GregorianCalendar().getTimeInMillis();
			String str = String.valueOf(l);
			if (paramInt == 1)
				FileUtil.setCacheData(paramString1, paramString2, paramString3,
						str);
			paramNewsModel.setUpdateTime(MessageCenterModel.formatTimestamp(l));
			return true;
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return false;
	}
}

/*
 * Location:
 * C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name: com.kaixin001.engine.NewsEngine JD-Core Version: 0.6.0
 */