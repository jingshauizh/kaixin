package com.kaixin001.network;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.kaixin001.model.Setting;
import com.kaixin001.model.User;
import com.kaixin001.util.KXLog;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Protocol
{
  public static final String ACCEPT_FRIEND_REQUET_API = "/friend/acceptfriend.php";
  public static final String ACTIVE_CLIENT_API = "/common/init.php";
  public static final String ADDVERIFY_API = "/friend/addverify.php";
  public static final String ADD_FRIEND_API = "/capi/rest.php?method=friend.addFriend";
  public static final String ADVERT_EVENT_API = "/capi/rest.php?method=advert.getPublish";
  public static final String ALBUM_LIST_API = "/photo/albumlist.php";
  public static final String ALBUM_PHOTOS_API = "/photo/albumphotos.php";
  public static final String API_VERSION = "3.9.9";
  public static final String CHAT_CLEAR_CIRCLE_UNREAD = "/capi/rest.php?method=Rgroup.clear";
  public static final String CHAT_CLEAR_UNREAD = "/capi/rest.php?method=IM.clear";
  public static final String CHAT_GET_HISTORY = "/capi/rest.php?method=IM.getHistory";
  public static final String CHAT_INIT_API = "/capi/rest.php?method=IM.initChat";
  public static final String CHAT_SENDING_GROUP = "/capi/rest.php?method=rgroup.tipType";
  public static final String CHAT_SENDING_MESSAGE = "/capi/rest.php?method=IM.tipType";
  public static final String CHAT_SEND_GROUP_MESSAGE = "/capi/rest.php?method=rgroup.sendMsg";
  public static final String CHAT_SEND_MESSAGE = "/capi/rest.php?method=IM.sendMsg";
  public static final String CHAT_SEND_MESSAGE_API = "/capi/rest.php?method=IM.sendMsg";
  public static final String CHAT_SEND_TYPING_STATE_API = "/capi/rest.php?method=IM.tipType";
  public static final String CHECK_UPGRADE_API = "/kxapi/upgrade.php";
  public static final String CIRCLE_GET_LAST_NEXT_PHOTO = "/capi/rest.php?method=rgroup.getLastNextPhoto";
  public static final String CIRCLE_GET_LIST = "/capi/rest.php?method=rgroup.getRgroupList";
  public static final String CIRCLE_GET_MEMBER_LIST = "/capi/rest.php?method=rgroup.getMemberList";
  public static final String CIRCLE_GET_PHOTO_DETAIL = "/capi/rest.php?method=rgroup.photoDetail";
  public static final String CIRCLE_GET_TALK_LIST = "/capi/rest.php?method=rgroup.getTalkList";
  public static final String CIRCLE_PHOTO_LIST = "/capi/rest.php?method=rgroup.getPhotoList";
  public static final String CIRCLE_REPLY_NEWS = "/capi/rest.php?method=rgroup.replytalk";
  public static final String CLEAR_NOTICE_FLAG_API = "/kxapi/msg/clearnotice.php";
  public static final String CLIENT_VERSION = "android-3.9.9";
  public static final String CLOUD_ALBUM_CHECK_FILES = "/capi/rest.php?method=cloudAlbum.checkPicsStatus";
  public static final String CLOUD_ALBUM_CHECK_STATUS = "/capi/rest.php?method=cloudAlbum.checkStatus";
  public static final String CLOUD_ALBUM_CHECK_WHITE_USER = "/capi/rest.php?method=cloudAlbum.checkWhiteUser";
  public static final String CLOUD_ALBUM_DELETE_PIC = "/capi/rest.php?method=cloudAlbum.deletePic";
  public static final String CLOUD_ALBUM_GET_LIST = "/capi/rest.php?method=cloudAlbum.getSyncPicList";
  public static final String CLOUD_ALBUM_GET_LIST_BY_ID = "/capi/rest.php?method=cloudAlbum.getSyncPicListByLastId";
  public static final String CLOUD_ALBUM_OPEN = "/capi/rest.php?method=cloudAlbum.open";
  public static final String CLOUD_ALBUM_UPLOAD_FILE = "/capi/rest.php?method=cloudAlbum.uploadPic";
  public static final String DELSTAR_API = "/friend/del.php";
  public static final String DETAILS_CHATGROUP_API = "/capi/rest.php?method=rgroup.getNoticeDetail";
  public static final String EXCHANGE_TRUTH_API = "/truth/exchange_submit.php";
  public static final String FEEDBACK_API = "/feedback/feedback.php";
  public static final String FILM_GET_COMMENT = "/capi/rest.php?method=film.getMoreComments";
  public static final String FILM_GET_DETAIL = "/capi/rest.php?method=film.getFilmDetail";
  public static final String FILM_GET_LIST = "/capi/rest.php?method=film.getFilmList";
  public static final String FILM_POST_COMMENT = "/capi/rest.php?method=film.postCommet";
  public static final String FILM_WANT_TO = "/capi/rest.php?method=film.wantTo";
  public static final String FIND_FRIEND_BY = "/capi/rest.php?method=friend.findby";
  public static final String FORWARD_RECORD_API = "/capi/rest.php?method=Record.forward";
  public static final String FRIENDS_API = "/kxapi/friend/friendlist_kx.php";
  public static final String FRIEND_USERINFO_API = "/friend/getuserinfos.php";
  public static final String GAME_CONFIGS = "/capi/rest.php?method=WapgameList.getList";
  public static final String GAME_MOUSE_GET_RANKS = "/capi/rest.php?method=games.getRanks";
  public static final String GAME_MOUSE_SEND_SCORE = "/capi/rest.php?method=games.setScore";
  public static final String GAME_RANKING = "/capi/rest.php?method=WapgameTop.getWapgameUsersScore";
  public static final String GAME_UPLOAD_SCORE = "/capi/rest.php?method=WapgameTop.addWapgameScore";
  public static final String GET_360_LIST = "/capi/rest.php?method=Games.get360List";
  public static final String GET_ADVERT = "/capi/rest.php?rt=json&method=advert.getAdvert";
  public static final String GET_ALL_APP_API = "/capi/rest.php?method=application.getAppList";
  public static final String GET_APPLIST_API = "/capi/rest.php?method=App.getAppList";
  public static final String GET_APPTOKEN_API = "/mobile/accesstokens.json";
  public static final String GET_CHAT_CONTEXT = "/capi/rest.php?method=rgroup.getChatContext";
  public static final String GET_CHAT_COOKIE_API = "/capi/rest.php?method=IM.getIMCookie";
  public static final String GET_CHAT_CTX_API = "/g/RANDOM/USER/ctx";
  public static final String GET_CHECKIN_LOGO_LIST = "/capi/rest.php?rt=json&method=checkin.getLogoList";
  public static final String GET_COMMENT_API = "/comment/index.php";
  public static final String GET_COMMENT_DETAIL_API = "/comment/index_view.php";
  public static final String GET_COVER_LIST = "/capi/rest.php?method=setting.getCover";
  public static final String GET_Common_APPLIST_API = "/capi/rest.php?method=application.getKxCommonList";
  public static final String GET_DEFAULT_TOKEN = "/users/defaulttoken";
  public static final String GET_DIALOG_NOICE = "/capi/rest.php?method=tips.getTips.php";
  public static final String GET_DIARY_DETAIL_API = "/diary/view.php";
  public static final String GET_DIARY_LIST_API = "/diary/list.php";
  public static final String GET_FRIENDS_OF_SOMEONE = "/capi/rest.php?method=friend.getFriendList";
  public static final String GET_GAME_BANNER = "/capi/rest.php?&method=advert.getGames";
  public static final String GET_GIFT_LIST_API = "/capi/rest.php?method=gift.getGiftList";
  public static final String GET_GIFT_NEWS = "/capi/rest.php?method=gift.getGiftFeedList";
  private static final String GET_GLOBAL_SEARCH_APP_URL = "capi/rest.php?method=search.searchAndroidApp";
  private static final String GET_GLOBAL_SEARCH_STAR_URL = "capi/rest.php?method=search.searchStar";
  public static final String GET_HOME_LOGO_LIST = "/capi/rest.php?method=visitor.getHomeVisitor";
  public static final String GET_HOME_VISITOR = "/capi/rest.php?method=visitor.getHomeVisitor";
  public static final String GET_HOROSCOPE = "/capi/rest.php?rt=json&method=lucky.getLuckyByStarnameForAnd";
  public static final String GET_KXCITY_PUSH = "/capi/rest.php?method=push.getCityPushInfo";
  public static final String GET_LOCATION_BUILDING_API = "/lbsgame/buildinglist.php";
  public static final String GET_LOCATION_LIST_API = "/capi/rest.php?method=lbs.getMyChecks";
  public static final String GET_MAYBE_FRIENDS_API = "/capi/rest.php?method=friend.getListByMobile";
  public static final String GET_MAYBE_KNOW = "/capi/rest.php?method=friend.getMaybeFriends";
  public static final String GET_MESSAGE_DETAIL_API = "/msg/view.php";
  public static final String GET_MUTUAL_FriendS = "/capi/rest.php?method=friend.getMutualFriends";
  public static final String GET_NEARBY_PERSON = "/capi/rest.php?method=lbs.getStrangerCheckin2";
  public static final String GET_OBJCOMMENT_API = "/comment/app.php";
  public static final String GET_PHOTO_API = "/capi/rest.php?method=photo.getPhotos";
  public static final String GET_PHOTO_DETAIL_API = "/capi/rest.php?method=photo.getDetail";
  public static final String GET_PHOTO_VISITOR_API = "/capi/rest.php?method=Photo.getVisitors";
  public static final String GET_PICTURE = "/capi/rest.php?rt=json&method=advert.getActivity";
  public static final String GET_PK_INFO = "/capi/rest.php?method=Topic.getPKInfoByTopicid";
  public static final String GET_PK_RECORD_LIST = "/capi/rest.php?method=Topic.getPKRecordsList";
  public static final String GET_PLAZA_API = "/capi/rest.php?method=photo.getPlaza";
  public static final String GET_PWD = "/users/sendpwd";
  public static final String GET_RECOMMENT_STAR = "/capi/rest.php?method=friend.recomendStars";
  public static final String GET_RECORD_LIST_API = "/capi/rest.php";
  public static final String GET_RECORD_LOGO_LIST = "/capi/rest.php?rt=json&method=record.getLogoList";
  public static final String GET_REPLY_COMMENT_API = "/comment/send.php";
  public static final String GET_REPLY_COMMENT_DETAIL_API = "/comment/send_view.php";
  public static final String GET_REPLY_NOTICE_LIST = "/capi/rest.php?method=rgroup.getNoticeList";
  public static final String GET_REPOSTLIST_API = "/capi/rest.php?";
  public static final String GET_REPOST_ALL_LIST_API = "/repaste/friend.php";
  public static final String GET_REPOST_ALL_LIST_HOT_API = "/repaste/hot.php";
  public static final String GET_REPOST_DETAIL_API = "/repaste/detail.php";
  public static final String GET_REPOST_LIST_API = "/repaste/index.php";
  public static final String GET_REPOST_RELEVANT_LINK_API = "/capi/rest.php?method=repaste.getRecommendList";
  public static final String GET_SENT_USER_COMMENT_API = "/comment/usend.php";
  public static final String GET_SENT_USER_COMMENT_DETAIL_API = "/comment/usend_view.php";
  public static final String GET_STARS_API = "/capi/rest.php?method=friend.getRandomStar";
  public static final String GET_STARS_TYPES = "/capi/rest.php?method=friend.getStarCategorys";
  public static final String GET_START_ADVERTISE = "/capi/rest.php?method=Loading.getLoadingList";
  public static final String GET_STRANGER_DETAIL = "/capi/rest.php?method=user.getStrangerInfo";
  public static final String GET_STYLE_BOX_DIARY_DETAIL_API = "/9diary/detail_client.php";
  private static final String GET_THIRDPARTY_URL = "/capi/rest.php?method=advert.getweburl";
  public static final String GET_THIRD_PARTY_APPLIST_API = "/capi/rest.php?method=App.getThirdList";
  public static final String GET_TOPIC_GROUP_API = "/capi/rest.php?method=topic.getTopicFeed";
  public static final String GET_TOUCH_LIST_API = "/move/gettypes.json";
  public static final String GET_TRUTH_DETAIL_API = "/truth/detail.php";
  public static final String GET_Third_APPLIST_API = "/capi/rest.php?method=application.getWapAppList";
  public static final String GET_USER_COMMENT_API = "/comment/uindex.php";
  public static final String GET_USER_COMMENT_DETAIL_API = "/comment/uindex_view.php";
  public static final String GET_VOTE_DETAIL_API = "/vote/detail.php";
  public static final String GET_VOTE_LIST_API = "/capi/rest.php?method=vote.getList";
  public static final String GET_WEB_LOGOUT_TIME = "/capi/rest.php?method=account.getlastonlinetime";
  public static final String GUIDE_SEND_NOTICE = "/capi/rest.php?method=version.sendUpdateRecord";
  private static final String HAS_NEW_ACTIVITY = "/capi/rest.php?rt=json&method=account.hasNewActivity";
  public static final String HAS_NEW_NEWS_API = "/capi/rest.php?method=news.hasNew";
  public static final String LBS_ADD_LOCATION_API = "/capi/rest.php?method=lbs.createPoi";
  public static final String LBS_CHECK_IN = "/capi/rest.php?method=lbs.checkIn";
  public static final String LBS_DEL_AWARDS = "/capi/rest.php?method=lbs.delAward";
  public static final String LBS_GET_AWARDS = "/capi/rest.php?method=lbs.getAwards";
  public static final String LBS_GET_CHECKIN_INFO = "/capi/rest.php?method=lbs.getCheckin";
  public static final String LBS_GET_CHECKIN_PHOTO_LIST_BY_POI = "/capi/rest.php?method=lbs.getPhotos";
  public static final String LBS_GET_FRIEND_CHECKIN_LIST_BY_POI = "/capi/rest.php?method=lbs.getFriendCheckin";
  public static final String LBS_GET_OTHER_FRIEND_CHECKIN_LIST_BY_POI = "/capi/rest.php?method=lbs.getOtherFriendCheckin";
  public static final String LBS_GET_POIS_ACTIVITY_API = "/capi/rest.php?method=lbs.getActivity";
  public static final String LBS_GET_POIS_API = "/capi/rest.php?method=lbs.getPois";
  public static final String LBS_GET_POIS_PHOTOES = "/capi/rest.php?method=lbs.getPoisPhoto";
  public static final String LBS_GET_POI_CHECKIN_API = "/capi/rest.php?method=lbs.getPoiCheckin";
  public static final String LBS_GET_POI_INFO_API = "/capi/rest.php?method=lbs.getPoiInfo";
  public static final String LBS_GET_POI_PHOTOES = "/capi/rest.php?method=lbs.getPhotos";
  public static final String LBS_GET_STRANGE_CHECKIN_LIST_BY_POI = "/capi/rest.php?method=lbs.getStrangerCheckin";
  public static final String LBS_SEARCH_POIS_API = "/capi/rest.php?method=lbs.searchPois";
  public static final String LBS_USE_AWARDS = "/capi/rest.php?method=lbs.useAward";
  public static final String LEAVE_OR_JOIN_CIRCLE_DETAIL_API = "/capi/rest.php?method=rgroup.quitsession";
  public static final String LEAVE_OR_JOIN_MESSAGE_API = "/capi/rest.php";
  public static final String LOGIN_API = "/login/login.php";
  public static final String LOGOUT_API = "/kxapi/login/loginout.php";
  public static final String LOGO_PHOTOS_API = "/photo/logolist.php";
  public static final String MESSAGE_CENTER_API = "/capi/rest.php?method=message.getNewNoticeNum";
  public static final String MESSAGE_INBOX_API = "/msg/inbox2.php";
  public static final String MESSAGE_SENT_API = "/msg/sendbox.php";
  public static final String NEWFRIEND_API = "/friend/new.php";
  public static final String NEWS_ACTIVITY_GETFRIENDCONTENT_API = "/capi/rest.php?method=friend.getFriendsDetail";
  public static final String NEWS_API = "/kxapi/home/index_kx.php";
  public static final String NEWS_KAIXIN_PLAZA_API = "/home/publicfeed.php";
  public static final String NEWS_LOOK_API = "/home/index_kx_nologin.php";
  public static final String NEWS_LOOK_AROUND_API = "/home/hotfeed.php";
  public static final String NEWS_LOOK_AROUND_FIND_FRIEND_API = "/home/recommend_friend.php";
  public static final String NEW_POST_RECORD_API = "/capi/rest.php?method=Record.post";
  public static final String NO_SUPPORT_FLASH_API_VERSION = "1.2";
  public static final String PHONE_REGISTER_API = "/home/register.php";
  public static final String PK_INFO_BY_TOPIC_NAME = "/capi/rest.php?method=Topic.getPKInfoByTopicName";
  public static final String PK_VOTE = "/capi/rest.php?method=Topic.pkvote";
  public static final String POST_CHATGROUP_API = "/capi/rest.php?method=rgroup.talk";
  public static final String POST_CLIENT_INFO = "/capi/rest.php?rt=json&method=stat.statClientLogin";
  public static final String POST_COMMENT_API = "/comment/post.php";
  public static final String POST_DIARY_API = "/diary/post.php";
  public static final String POST_DIARY_MULTIPART_API = "/diary/post_multipart.php";
  public static final String POST_HEADER_LOGO_API = "/photo/upload_logo.php";
  public static final String POST_MESSAGE_API = "/msg/post.php";
  public static final String POST_PHOTO_API = "/photo/post_pc.php";
  public static final String POST_RECORD_API = "/record/post.php";
  public static final String POST_REPLY_API = "/comment/reply.php";
  public static final String POST_UP_API = "/common/up.php";
  public static final String PROTOCOL_ACCESS_TOKEN = "/oauth/access_token";
  public static final String PROTOCOL_PROXY = "/mobile/agent.json";
  public static final String PROTOCOL_WAP_PROXY = "/mobile/wapagent.json";
  public static final String RECIEVIE_REPOST = "/home/unsubscribe.php?";
  public static final String RECOMMEND_APP_NOTIFY = "/capi/rest.php?method=application.hasNew";
  public static final String RECORD_MESSAGE_DETAIL = "/capi/rest.php?method=record.getDetailMore";
  public static final String REFUSE_FRIEND_REQUET_API = "/friend/refusefriend.php";
  public static final String REPLY_MESSAGE_API = "/msg/post.php";
  public static final String REPOST_DIARY_API = "/repaste/repaste_submit.php";
  public static final String REPOST_REPOST_API = "/repaste/repaste_submit.php";
  public static final String REPOST_VOTE_API = "/repaste/vote_submit.php";
  public static final String SEARCH_CLASSMATE_COLLEAGUE = "/capi/rest.php?method=friend.findUsers";
  public static final String SEARCH_FRIEND_API = "/friend/search.php";
  public static final String SEND_GIFT_API = "/capi/rest.php?method=gift.sendGift";
  public static final String SEND_TOUCH_API = "/move/send.json";
  public static final String SET_CLOSE_ADV = "/capi/rest.php?method=advert.closeAdvert";
  public static final String SET_COVER_LIST = "/capi/rest.php?method=setting.setCover";
  public static final String SET_HOROSCOPE_PUSH = "/capi/rest.php?method=lucky.setpushForAndroid";
  public static final String SET_STAR = "/capi/rest.php?&method=lucky.setConstellation";
  public static final String SHARE_QZONE = "/capi/rest.php?method=account.QzoneShare";
  public static final String SHARE_WEIBO = "/capi/rest.php?method=account.weiboShare";
  public static final String SHARE_WX_ADD_SCORE = "/capi/rest.php?method=uniongame.awardWeixinShareGifts";
  public static final String SHARE_WX_GET_INFO = "/capi/rest.php?method=uniongame.getWeixinFriendCircleShareInfo";
  public static final String SUBMIT_REPOST_TAG_API = "/repaste/tag_submit.php";
  public static final String SYSTEM_MESSAGE_API = "/capi/rest.php?method=message.getSysList";
  public static final String UNION_RECOMMEND_TIP = "/capi/rest.php?method=account.settingForYouMeng";
  public static final String UPDATEFACE_API = "/kxapi/home/face.php";
  public static final String UPDATE_API = "/kxapi/update.php";
  public static final String UPDATE_STATUS_API = "/home/update_status.php";
  public static final String UPLOAD_DIARY_PIC_API = "/photo/getimg.php";
  public static final String UPLOAD_USER_BEHAVIOR_LOG_API = "/capi/rest.php?method=system.writeLog";
  public static final String USERHABIT_MUTI = "/home/client_stat.php";
  public static final String USERINFO_ACTIVITY = "/capi/rest.php?method=account.getLevelActivityList";
  public static final String USERINFO_API = "/friend/getuserinfos.php";
  public static final String USERINFO_DAILY_TASK = "/capi/rest.php?method=account.getDailyTaskInfo";
  public static final String USERINFO_GET_RANKING = "/capi/rest.php?method=account.getTopUsers";
  public static final String USERINFO_MODIFY_API = "/capi/rest.php?&method=account.updateInfos";
  public static final String USERINFO_UPDATE_API = "/capi/rest.php?method=account.updateUserInfo";
  public static final String USER_CARD_GET = "/capi/rest.php?method=User.getcard";
  public static final String USER_CARD_UPDATE = "/capi/rest.php?method=User.updateCard";
  public static final String USER_NAME_VERIFY = "/capi/rest.php?method=register.checkIsValidName";
  public static final String VERSION = "110";
  public static final String VIDEO_TUDOU_CONVERT_LINK = "http://tdwap.tudou.com/sns/playurlconvert";
  public static final String VOTE_API = "/vote/submit.php";
  private static Protocol instance;
  public String mHostName = Setting.getInstance().getHost();
  protected String mWapHostName = Setting.getInstance().getHost();

  public static Protocol getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new Protocol();
      Protocol localProtocol = instance;
      return localProtocol;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public static String getShortVersion()
  {
    return "android-3.9.9".toLowerCase().replaceFirst("android-", "").trim();
  }

  private String getTouids(ArrayList<String> paramArrayList)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    int i;
    if (paramArrayList == null)
      i = 0;
    for (int j = 0; ; j++)
    {
      if (j >= i)
      {
        return localStringBuffer.toString();
        i = paramArrayList.size();
        break;
      }
      if (j != 0)
        localStringBuffer.append(",");
      localStringBuffer.append((String)paramArrayList.get(j));
    }
  }

  public static void setLocationXY(StringBuffer paramStringBuffer, String paramString1, String paramString2)
  {
    if (!TextUtils.isEmpty(paramString1))
    {
      paramStringBuffer.append("&x=").append(paramString1);
      paramStringBuffer.append("&y=").append(paramString2);
    }
  }

  public String getAccessToken()
  {
    StringBuffer localStringBuffer = new StringBuffer(this.mHostName);
    localStringBuffer.append("/oauth/access_token");
    return localStringBuffer.toString();
  }

  public String getAdvertEventUrl(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=advert.getPublish");
    localStringBuffer.append("&uid=").append(paramString);
    localStringBuffer.append("&api_version=").append("3.9.9");
    localStringBuffer.append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String getDailyTask(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=account.getDailyTaskInfo").append("&fuid=").append(paramString).append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String getDataByUrls(String[] paramArrayOfString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/kxapi/update.php");
    localStringBuffer.append("?urls=");
    for (int i = 0; ; i++)
    {
      if (i >= paramArrayOfString.length)
        return localStringBuffer.toString();
      if (i > 0)
        localStringBuffer.append(',');
      localStringBuffer.append(URLEncoder.encode(paramArrayOfString[i]));
    }
  }

  public String getDefaultToken()
  {
    StringBuffer localStringBuffer = new StringBuffer(this.mHostName);
    localStringBuffer.append("/users/defaulttoken");
    return localStringBuffer.toString();
  }

  public String getForgetPwd()
  {
    StringBuffer localStringBuffer = new StringBuffer(this.mHostName);
    localStringBuffer.append("/users/sendpwd");
    return localStringBuffer.toString();
  }

  public String getGameBannerUrl(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?&method=advert.getGames").append("&location=").append(paramString).append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String getHoroscopePush()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=push.getCityPushInfo").append("&pushtype=").append("1090").append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String getNewsCountUrl(String paramString1, String paramString2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=news.hasNew");
    localStringBuffer.append("&uid=").append(paramString1);
    if (!TextUtils.isEmpty(paramString2))
      localStringBuffer.append("&ctime=").append(paramString2);
    localStringBuffer.append("&manufacturer=").append(URLEncoder.encode(Build.MANUFACTURER));
    localStringBuffer.append("&model=").append(URLEncoder.encode(Build.MODEL));
    localStringBuffer.append("&sdk_release=").append(URLEncoder.encode(Build.VERSION.RELEASE));
    localStringBuffer.append("&api_version=").append("3.9.9");
    localStringBuffer.append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String getNewsUpdateUrls(String paramString, int paramInt)
  {
    String[] arrayOfString = new String[3];
    StringBuffer localStringBuffer = new StringBuffer("/kxapi/home/index_kx.php");
    localStringBuffer.append("?uid=").append(paramString);
    localStringBuffer.append("&n=").append(paramInt);
    localStringBuffer.append("&flag=all").append("&format=1");
    localStringBuffer.append("&face=1");
    localStringBuffer.append("&api_version=").append("3.9.9");
    localStringBuffer.append("&version=").append("android-3.9.9");
    arrayOfString[0] = localStringBuffer.toString();
    localStringBuffer.delete(0, localStringBuffer.length());
    localStringBuffer.append("/kxapi/home/index_kx.php");
    localStringBuffer.append("?uid=").append(paramString);
    localStringBuffer.append("&n=").append(paramInt);
    localStringBuffer.append("&format=1");
    localStringBuffer.append("&face=1");
    localStringBuffer.append("&api_version=").append("3.9.9");
    localStringBuffer.append("&version=").append("android-3.9.9");
    arrayOfString[1] = localStringBuffer.toString();
    localStringBuffer.delete(0, localStringBuffer.length());
    localStringBuffer.append("/kxapi/home/index_kx.php");
    localStringBuffer.append("?uid=").append(paramString);
    localStringBuffer.append("&n=").append(paramInt);
    localStringBuffer.append("&flag=star").append("&format=1");
    localStringBuffer.append("&face=1");
    localStringBuffer.append("&api_version=").append("3.9.9");
    localStringBuffer.append("&version=").append("android-3.9.9");
    arrayOfString[2] = localStringBuffer.toString();
    localStringBuffer.delete(0, localStringBuffer.length());
    return getDataByUrls(arrayOfString);
  }

  public String getPhoneRegisterUrl(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/home/register.php");
    localStringBuffer.append("?step=");
    localStringBuffer.append(paramString1);
    localStringBuffer.append("&code=");
    localStringBuffer.append(paramString2);
    localStringBuffer.append("&mobile=");
    localStringBuffer.append(paramString3);
    localStringBuffer.append("&password=");
    localStringBuffer.append(paramString4);
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String getRanksUrl()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=games.getRanks");
    return localStringBuffer.toString();
  }

  public String getSendNoticToken()
  {
    StringBuffer localStringBuffer = new StringBuffer(this.mHostName);
    localStringBuffer.append("/capi/rest.php?method=version.sendUpdateRecord");
    return localStringBuffer.toString();
  }

  public String getSendTouchToken()
  {
    StringBuffer localStringBuffer = new StringBuffer(this.mHostName);
    localStringBuffer.append("/move/send.json");
    return localStringBuffer.toString();
  }

  public String getSetStarUrl(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?&method=lucky.setConstellation").append("&starname=").append(paramString).append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String getStartAdvertUrl()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=Loading.getLoadingList").append("&verify=").append("71054051_71054051_1392002166_42e25792f2bae87221b9686b662b3b66_03502iphoneclient_kx").append("&action=").append("add").append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String getStrangerInfo(String paramString1, String paramString2, String paramString3)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=user.getStrangerInfo");
    localStringBuffer.append("&uid=").append(paramString1);
    localStringBuffer.append("&uids=").append(paramString2);
    localStringBuffer.append("&fields=").append(paramString3);
    localStringBuffer.append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String getThirdGameListUrl(int paramInt1, int paramInt2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=Games.get360List").append("&start=").append(paramInt1).append("&action=").append(paramInt2).append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String getTouchToken()
  {
    StringBuffer localStringBuffer = new StringBuffer(this.mHostName);
    localStringBuffer.append("/move/gettypes.json");
    return localStringBuffer.toString();
  }

  public String getUnionRecommendUrl()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=account.settingForYouMeng").append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String getUploadClientInfoUrl()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?rt=json&method=stat.statClientLogin");
    return localStringBuffer.toString();
  }

  public String getUploadHomePhotoUrl(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/photo/post_pc.php").append("?&uid=").append(paramString1).append("&albumname=").append(paramString2).append("&cover=").append(paramString3).append("&title=").append(paramString4).append("&srcfilename=").append(paramString5).append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String getUserLevelActivity(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=account.getLevelActivityList").append("&fuid=").append(paramString).append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String getUserRanking(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=account.getTopUsers").append("&fuid=").append(paramString).append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String getWebLoginTime(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=account.getlastonlinetime").append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeAcceptFriendRequest(String paramString1, String paramString2, String paramString3)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/friend/acceptfriend.php").append("?&uid=").append(paramString3).append("&fuid=").append(paramString1).append("&smid=").append(paramString2).append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeActiveClientRequest(String paramString1, String paramString2, String paramString3)
  {
    StringBuffer localStringBuffer = new StringBuffer("http://ksa2.kaixin001.com");
    localStringBuffer.append("/common/init.php");
    localStringBuffer.append("?imei=").append(paramString1);
    localStringBuffer.append("&from=").append(paramString2);
    localStringBuffer.append("&ctype=").append(paramString3);
    localStringBuffer.append("&api_version=").append("3.9.9");
    localStringBuffer.append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeAddFriendRequest(String paramString1, String paramString2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=friend.addFriend");
    localStringBuffer.append("&uid=").append(paramString1);
    localStringBuffer.append("&touid=").append(paramString2);
    localStringBuffer.append("&api_version=").append("3.9.9");
    localStringBuffer.append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeAddFriendRequest(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=friend.addFriend");
    localStringBuffer.append("&uid=").append(paramString1);
    localStringBuffer.append("&touid=").append(paramString2);
    localStringBuffer.append("&api_version=").append("3.9.9");
    localStringBuffer.append("&version=").append("android-3.9.9");
    localStringBuffer.append("&code=").append(URLEncoder.encode(paramString3));
    localStringBuffer.append("&rcode=").append(URLEncoder.encode(paramString4));
    return localStringBuffer.toString();
  }

  public String makeAddVerifyRequest(String paramString1, String paramString2, String paramString3)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/friend/addverify.php");
    localStringBuffer.append("?uid=").append(paramString1);
    localStringBuffer.append("&touid=").append(paramString2);
    if (!TextUtils.isEmpty(paramString3))
      localStringBuffer.append("&content=").append(URLEncoder.encode(paramString3));
    localStringBuffer.append("&api_version=").append("3.9.9");
    localStringBuffer.append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeAdvertCloseRequest(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=advert.closeAdvert");
    localStringBuffer.append("&advertid=");
    localStringBuffer.append(paramString);
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeAlbumListRequest(boolean paramBoolean, String paramString1, String paramString2, int paramInt)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/photo/albumlist.php");
    localStringBuffer.append("?uid=").append(paramString1);
    localStringBuffer.append("&fuid=").append(paramString2);
    localStringBuffer.append("&n=").append(paramInt);
    localStringBuffer.append("&api_version=").append("3.9.9");
    localStringBuffer.append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeAlbumPhotoListRequest(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt1, int paramInt2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/photo/albumphotos.php");
    localStringBuffer.append("?uid=").append(paramString1);
    localStringBuffer.append("&albumid=").append(paramString2);
    localStringBuffer.append("&fuid=").append(paramString3);
    localStringBuffer.append("&passwd=").append(paramString4);
    localStringBuffer.append("&start=").append(paramInt1);
    localStringBuffer.append("&n=").append(paramInt2);
    localStringBuffer.append("&api_version=").append("3.9.9");
    localStringBuffer.append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeChatClearCircleUnread(String paramString1, String paramString2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=Rgroup.clear").append("&format=").append("json").append("&gid=").append(paramString1).append("&chatID=").append(paramString2).append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeChatClearUnread(String paramString1, String paramString2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=IM.clear").append("&format=").append("json").append("&otheruid=").append(paramString1).append("&cmids=").append(paramString2).append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeChatGetHistoryRequest(String paramString1, String paramString2, int paramInt)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=IM.getHistory");
    localStringBuffer.append("&otheruid=").append(paramString1);
    if (!TextUtils.isEmpty(paramString2))
      localStringBuffer.append("&before=").append(paramString2);
    localStringBuffer.append("&n=").append(paramInt);
    localStringBuffer.append("&api_version=").append("3.9.9");
    localStringBuffer.append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeChatGroupDetailsRequest(String paramString1, String paramString2, String paramString3)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=rgroup.getNoticeDetail");
    localStringBuffer.append("&verify=");
    localStringBuffer.append(paramString1);
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    localStringBuffer.append("&tid=");
    localStringBuffer.append(paramString2);
    localStringBuffer.append("&gid=");
    localStringBuffer.append(paramString3);
    return localStringBuffer.toString();
  }

  public String makeChatInitRequest(String paramString, int paramInt1, int paramInt2, int paramInt3)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=IM.initChat");
    localStringBuffer.append("&uid=").append(paramString);
    localStringBuffer.append("&init=").append(paramInt1);
    localStringBuffer.append("&buddy=").append(paramInt2);
    localStringBuffer.append("&getmsg=").append(paramInt3);
    localStringBuffer.append("&api_version=").append("3.9.9");
    localStringBuffer.append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeChatSendGroupMsgRequest(String paramString1, String paramString2, String paramString3)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=rgroup.sendMsg").append("&uid=").append(paramString1).append("&gid=").append(paramString2).append("&content=").append(paramString3);
    localStringBuffer.append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeChatSendMessageRequest(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=IM.sendMsg").append("&uid=").append(paramString1).append("&verify=").append(paramString2).append("&content=").append(paramString3).append("&otheruid=").append(paramString4);
    localStringBuffer.append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeChatSendMsgRequest(String paramString1, String paramString2, String paramString3)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=IM.sendMsg").append("&uid=").append(paramString1).append("&otheruid=").append(paramString2).append("&content=").append(paramString3);
    localStringBuffer.append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeChatSendTipTypeRequest(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=IM.tipType").append("&uid=").append(paramString1).append("&verify=").append(paramString2).append("&otheruid=").append(paramString4);
    localStringBuffer.append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeChatSendTypingGroupMsg(String paramString1, String paramString2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=rgroup.tipType").append("&uid=").append(paramString1).append("&gid=").append(paramString2);
    localStringBuffer.append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeChatSendTypingRequest(String paramString1, String paramString2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=IM.tipType").append("&uid=").append(paramString1).append("&otheruid=").append(paramString2).append("&content=");
    localStringBuffer.append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeCheckUpgradeRequest(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/kxapi/upgrade.php").append("?uid=").append(paramString).append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeCheckinPhotoListbyPoi(double paramDouble1, double paramDouble2, String paramString1, String paramString2, int paramInt1, int paramInt2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=lbs.getPhotos");
    localStringBuffer.append("&uid=").append(paramString1);
    localStringBuffer.append("&verify=").append(paramString2);
    localStringBuffer.append("&lat=").append(paramDouble1);
    localStringBuffer.append("&lon=").append(paramDouble2);
    localStringBuffer.append("&start=").append(paramInt1);
    localStringBuffer.append("&n=").append(paramInt2);
    localStringBuffer.append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeCircleAlbumList(String paramString, int paramInt1, int paramInt2)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("/capi/rest.php?method=rgroup.getPhotoList");
    localStringBuilder.append("&gid=").append(paramString);
    localStringBuilder.append("&start=").append(paramInt1);
    localStringBuilder.append("&num=").append(paramInt2).append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuilder.toString();
  }

  public String makeCircleDetailLeaveOrJoinRequest(String paramString1, String paramString2, int paramInt)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=rgroup.quitsession");
    localStringBuffer.append("&tid=").append(paramString1).append("&gid=").append(paramString2).append("&type=").append(paramInt).append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeCircleDetailsRequest(String paramString1, String paramString2, String paramString3)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=rgroup.getNoticeDetail");
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    localStringBuffer.append("&tid=");
    localStringBuffer.append(paramString1);
    localStringBuffer.append("&gid=");
    localStringBuffer.append(paramString2);
    localStringBuffer.append("&start=");
    localStringBuffer.append(paramString3);
    localStringBuffer.append("&order=desc");
    return localStringBuffer.toString();
  }

  public String makeCircleDetailsRequest(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(makeCircleDetailsRequest(paramString1, paramString2, paramString3)).append("&order=").append(paramString4);
    return localStringBuffer.toString();
  }

  public String makeCircleGetNextPreviousPhoto(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=rgroup.getLastNextPhoto");
    localStringBuffer.append("&gid=").append(paramString2);
    localStringBuffer.append("&pid=").append(paramString3);
    localStringBuffer.append("&action=").append(paramString1);
    if (!TextUtils.isEmpty(paramString4))
      localStringBuffer.append("&aid=").append(paramString4);
    localStringBuffer.append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeCircleList(int paramInt1, int paramInt2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=rgroup.getRgroupList");
    localStringBuffer.append("&start=").append(paramInt1);
    localStringBuffer.append("&num=").append(paramInt2);
    localStringBuffer.append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeCircleMemberList(String paramString, int paramInt1, int paramInt2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=rgroup.getMemberList");
    localStringBuffer.append("&start=").append(paramInt1);
    localStringBuffer.append("&num=").append(paramInt2);
    localStringBuffer.append("&gid=").append(paramString);
    localStringBuffer.append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeCircleTalkList(String paramString, int paramInt1, int paramInt2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=rgroup.getTalkList");
    localStringBuffer.append("&start=").append(paramInt1);
    localStringBuffer.append("&num=").append(paramInt2);
    localStringBuffer.append("&gid=").append(paramString);
    localStringBuffer.append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeCircleTalkReply(String paramString1, String paramString2, String paramString3)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=rgroup.replytalk");
    localStringBuffer.append("&stid=").append(paramString2);
    localStringBuffer.append("&message=").append(URLEncoder.encode(paramString3));
    localStringBuffer.append("&gid=").append(paramString1);
    localStringBuffer.append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeClearNoticeFlagRequest(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/kxapi/msg/clearnotice.php").append("?&uid=").append(paramString).append("&type=0").append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeCloudAlbumCheckFiles()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=cloudAlbum.checkPicsStatus");
    return localStringBuffer.toString();
  }

  public String makeCloudAlbumCheckStatus()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=cloudAlbum.checkStatus");
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeCloudAlbumCheckWhiteUser()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=cloudAlbum.checkWhiteUser");
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeCloudAlbumDeletePic(String paramString1, String paramString2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=cloudAlbum.deletePic");
    if (!TextUtils.isEmpty(paramString1))
      localStringBuffer.append("&cpid=").append(paramString1);
    if (!TextUtils.isEmpty(paramString2))
      localStringBuffer.append("&md5=").append(paramString2);
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeCloudAlbumGetList(int paramInt1, int paramInt2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=cloudAlbum.getSyncPicList");
    localStringBuffer.append("&num=").append(paramInt2);
    localStringBuffer.append("&start=").append(paramInt1);
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeCloudAlbumGetListById(String paramString, int paramInt)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=cloudAlbum.getSyncPicListByLastId");
    localStringBuffer.append("&num=").append(paramInt);
    if (!TextUtils.isEmpty(paramString))
      localStringBuffer.append("&cpid=").append(paramString);
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeCloudAlbumOpen()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=cloudAlbum.open");
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeCloudAlbumUploadPic()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=cloudAlbum.uploadPic");
    return localStringBuffer.toString();
  }

  public String makeDelStarRequest(String paramString1, String paramString2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/friend/del.php");
    localStringBuffer.append("?uid=");
    localStringBuffer.append(paramString1);
    localStringBuffer.append("&touid=");
    localStringBuffer.append(paramString2);
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeDiaryListRequest(String paramString1, String paramString2, int paramInt1, int paramInt2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/diary/list.php").append("?uid=").append(paramString1).append("&fuid=").append(paramString2).append("&start=").append(paramInt1).append("&n=").append(paramInt2).append("&face=1").append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeExchangeTruthRequest(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/truth/exchange_submit.php").append("?uid=").append(paramString5).append("&privacy=").append(paramString3).append("&truth=").append(URLEncoder.encode(paramString4)).append("&fuid=").append(paramString2).append("&tid=").append(paramString1).append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeFaceRequest(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/kxapi/home/face.php");
    localStringBuffer.append("?uid=");
    localStringBuffer.append(paramString);
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeFeedbackRequest(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/feedback/feedback.php").append("?uid=").append(paramString).append("?api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeFilmWantto(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=film.wantTo");
    localStringBuffer.append("&id=").append(paramString);
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeFindFriendRequest(String paramString1, String paramString2, String paramString3, int paramInt, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=friend.findby");
    localStringBuffer.append(paramString1);
    localStringBuffer.append("&" + paramString2 + "=");
    localStringBuffer.append(URLEncoder.encode(paramString3));
    if (paramInt > 0)
    {
      localStringBuffer.append("&num=");
      localStringBuffer.append(paramInt);
    }
    if (TextUtils.isEmpty(paramString4))
      localStringBuffer.append("&format=json");
    while (true)
    {
      if (!TextUtils.isEmpty(paramString5))
      {
        localStringBuffer.append("&city=");
        localStringBuffer.append(paramString5);
      }
      if (!TextUtils.isEmpty(paramString6))
      {
        localStringBuffer.append("&agespan=");
        localStringBuffer.append(paramString6);
      }
      if (!TextUtils.isEmpty(paramString7))
      {
        localStringBuffer.append("&gender=");
        localStringBuffer.append(paramString7);
      }
      if (!TextUtils.isEmpty(paramString8))
      {
        localStringBuffer.append("&start=");
        localStringBuffer.append(paramString8);
      }
      localStringBuffer.append("&api_version=");
      localStringBuffer.append("3.9.9");
      localStringBuffer.append("&version=");
      localStringBuffer.append("android-3.9.9");
      return localStringBuffer.toString();
      localStringBuffer.append("&format=");
      localStringBuffer.append(paramString4);
    }
  }

  public String makeFriendsPhotoRequest(String paramString, int paramInt1, int paramInt2, long paramLong)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=photo.getPhotos");
    localStringBuffer.append("&uid=");
    localStringBuffer.append(paramString);
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    localStringBuffer.append("&start=");
    localStringBuffer.append(paramInt1);
    localStringBuffer.append("&num=");
    localStringBuffer.append(paramInt2);
    localStringBuffer.append("&before=");
    localStringBuffer.append(paramLong);
    return localStringBuffer.toString();
  }

  public String makeFriendsRequest(String paramString, int paramInt1, int paramInt2, int paramInt3, long paramLong, boolean paramBoolean)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/kxapi/friend/friendlist_kx.php");
    localStringBuffer.append("?uid=");
    localStringBuffer.append(paramString);
    localStringBuffer.append("&lg=1");
    localStringBuffer.append("&n=");
    localStringBuffer.append(paramInt1);
    localStringBuffer.append("&vn=");
    localStringBuffer.append(paramInt2);
    localStringBuffer.append("&type=");
    localStringBuffer.append(paramInt3);
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    localStringBuffer.append("&hidepy=1");
    localStringBuffer.append("&hidestate=1");
    if ((!paramBoolean) && (paramInt3 == 1))
    {
      localStringBuffer.append("&usecache=1");
      if (paramLong > 0L)
      {
        localStringBuffer.append("&uniq=");
        localStringBuffer.append(paramLong);
      }
    }
    return localStringBuffer.toString();
  }

  public String makeFriendsUserInfoRequest(String paramString1, String paramString2, String paramString3)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/friend/getuserinfos.php");
    localStringBuffer.append("?uid=");
    localStringBuffer.append(paramString1);
    localStringBuffer.append("&uids=");
    localStringBuffer.append(paramString2);
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeGameConfigUrl(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("/capi/rest.php?method=WapgameList.getList");
    localStringBuilder.append("&share_url=").append(paramString);
    return localStringBuilder.toString();
  }

  public String makeGetAdvertRequest()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?rt=json&method=advert.getAdvert");
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeGetApplistRequest(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=App.getAppList");
    localStringBuffer.append("&uid=").append(paramString);
    return localStringBuffer.toString();
  }

  public String makeGetChatContext(String paramString1, String paramString2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=rgroup.getChatContext");
    localStringBuffer.append("&uid=").append(paramString1);
    localStringBuffer.append("&gid=").append(paramString2);
    localStringBuffer.append("&api_version=").append("3.9.9");
    localStringBuffer.append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeGetChatCookieRequest(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=IM.getIMCookie");
    localStringBuffer.append("&uid=").append(paramString);
    localStringBuffer.append("&api_version=").append("3.9.9");
    localStringBuffer.append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeGetChatCtxRequest(String paramString1, String paramString2, int paramInt)
  {
    StringBuffer localStringBuffer = new StringBuffer(paramString1);
    localStringBuffer.append("/g/").append(paramInt).append("/").append(paramString2).append("/").append("ctx");
    return localStringBuffer.toString();
  }

  public String makeGetCirclePhotoDetail(String paramString1, String paramString2, String paramString3)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=rgroup.photoDetail");
    localStringBuffer.append("&tid=").append(paramString1);
    localStringBuffer.append("&gid=").append(paramString2);
    localStringBuffer.append("&pid=").append(paramString3);
    localStringBuffer.append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeGetCircleReplyListRequest(int paramInt1, String paramString, int paramInt2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=rgroup.getNoticeList").append("&type=").append(paramInt1).append("&start=").append(paramString).append("&num=").append(paramInt2).append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeGetCommentDetailRequest(int paramInt, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/comment/index_view.php").append("?&uid=").append(paramString2).append("&n=").append(paramInt).append("&thread_cid=").append(paramString1).append("&pos=").append(paramString3).append("&order=1").append("&face=1").append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    if (!TextUtils.isEmpty(paramString4))
      localStringBuffer.append("&after=").append(paramString4);
    if (!TextUtils.isEmpty(paramString5))
      localStringBuffer.append("&before=").append(paramString5);
    return localStringBuffer.toString();
  }

  public String makeGetCommentRequest(String paramString1, int paramInt, String paramString2, boolean paramBoolean)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/comment/index.php").append("?uid=").append(paramInt).append("&n=").append(paramInt).append("&before=").append(paramString1).append("&face=1");
    if (paramBoolean)
      localStringBuffer.append("&unread=1");
    localStringBuffer.append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeGetCommonApplistRequest(int paramInt1, int paramInt2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=application.getAppList");
    localStringBuffer.append("&menutype=1");
    localStringBuffer.append("&begin=").append(paramInt1);
    localStringBuffer.append("&n=").append(paramInt2);
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeGetCoverList()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=setting.getCover");
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeGetDefaultToken(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("appid=").append(paramString);
    localStringBuffer.append("&scope=").append("basic");
    return localStringBuffer.toString();
  }

  public String makeGetDialogNotice()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=tips.getTips.php");
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeGetDiaryDetailRequest(String paramString1, String paramString2, String paramString3)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/diary/view.php").append("?&uid=").append(paramString3).append("&did=").append(paramString1).append("&fuid=").append(paramString2).append("&v=").append("110").append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeGetFilmComment(String paramString, int paramInt1, int paramInt2, int paramInt3)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=film.getMoreComments");
    localStringBuffer.append("&id=").append(paramString);
    localStringBuffer.append("&start=").append(paramInt1);
    localStringBuffer.append("&num=").append(paramInt2);
    localStringBuffer.append("&n=").append(paramInt2);
    localStringBuffer.append("&ctype=").append(paramInt3);
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeGetFilmDetail(String paramString, int paramInt1, int paramInt2, int paramInt3)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=film.getFilmDetail");
    localStringBuffer.append("&id=").append(paramString);
    localStringBuffer.append("&start=").append(paramInt1);
    localStringBuffer.append("&n=").append(paramInt2);
    localStringBuffer.append("&ctype=").append(paramInt3);
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeGetFilmList(int paramInt1, int paramInt2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=film.getFilmList");
    localStringBuffer.append("&start=").append(paramInt1);
    localStringBuffer.append("&num=").append(paramInt2);
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeGetFindFriendRequest()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=friend.getFriendsDetail");
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeGetFriendsOfSomeone(String paramString1, String paramString2, String paramString3)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=friend.getFriendList");
    localStringBuffer.append("&fuid=");
    localStringBuffer.append(paramString1);
    localStringBuffer.append("&start=");
    localStringBuffer.append(paramString2);
    localStringBuffer.append("&n=");
    localStringBuffer.append(paramString3);
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeGetGiftListRequest(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=gift.getGiftList");
    localStringBuffer.append("&start=0");
    localStringBuffer.append("&n=").append(paramString);
    localStringBuffer.append("&bigimg=1");
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeGetGiftNewsRequest(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=gift.getGiftFeedList");
    localStringBuffer.append("&start=").append(paramString);
    localStringBuffer.append("&n=20");
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeGetGlobalSearchAppUrl(String paramString, int paramInt1, int paramInt2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("capi/rest.php?method=search.searchAndroidApp");
    localStringBuffer.append("&keyword=").append(paramString);
    localStringBuffer.append("&start=").append(paramInt1);
    localStringBuffer.append("&n=").append(paramInt2);
    localStringBuffer.append("&api_version=").append("3.9.9");
    localStringBuffer.append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeGetGlobalSearchStarUrl(String paramString, int paramInt1, int paramInt2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("capi/rest.php?method=search.searchStar");
    localStringBuffer.append("&keyword=").append(paramString);
    localStringBuffer.append("&start=").append(paramInt1);
    localStringBuffer.append("&n=").append(paramInt2);
    localStringBuffer.append("&api_version=").append("3.9.9");
    localStringBuffer.append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeGetHomeVisitor(String paramString1, String paramString2, String paramString3)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=visitor.getHomeVisitor");
    localStringBuffer.append("&fuid=");
    localStringBuffer.append(paramString1);
    localStringBuffer.append("&start=");
    localStringBuffer.append(paramString2);
    localStringBuffer.append("&n=");
    localStringBuffer.append(paramString3);
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeGetHoroscope(String paramString1, String paramString2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?rt=json&method=lucky.getLuckyByStarnameForAnd").append("&starname=").append(URLEncoder.encode(paramString2)).append("&timetype=").append(paramString1).append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeGetKXApplistRequest(int paramInt1, int paramInt2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=application.getAppList");
    localStringBuffer.append("&menutype=2");
    localStringBuffer.append("&begin=").append(paramInt1);
    localStringBuffer.append("&n=").append(paramInt2);
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeGetKXCityPush()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=push.getCityPushInfo");
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeGetLocationBuildingRequest(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/lbsgame/buildinglist.php").append("?uid=").append(paramString5).append("&lonlat=").append(paramString2).append(",").append(paramString1).append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    if (!TextUtils.isEmpty(paramString3))
    {
      localStringBuffer.append("&x=").append(paramString3);
      localStringBuffer.append("&y=").append(paramString4);
    }
    return localStringBuffer.toString();
  }

  public String makeGetMayBeFriendsRequest(int paramInt1, int paramInt2, String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=friend.getListByMobile");
    localStringBuffer.append("&start=").append(paramInt1);
    localStringBuffer.append("&n=").append(paramInt2);
    localStringBuffer.append("&api_version=").append("3.9.9");
    localStringBuffer.append("&version=").append("android-3.9.9");
    localStringBuffer.append("&uid=").append(paramString);
    return localStringBuffer.toString();
  }

  public String makeGetMaybeKnowRequest(int paramInt1, int paramInt2, String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=friend.getMaybeFriends");
    localStringBuffer.append("&start=").append(paramInt1);
    localStringBuffer.append("&num=").append(paramInt2);
    localStringBuffer.append("&api_version=").append("3.9.9");
    localStringBuffer.append("&version=").append("android-3.9.9");
    localStringBuffer.append("&uid=").append(paramString);
    return localStringBuffer.toString();
  }

  public String makeGetMessageDetailRequest(int paramInt, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/msg/view.php").append("?&uid=").append(paramString2).append("&n=").append(paramInt).append("&mid=").append(paramString1).append("&pos=").append(paramString3).append("&order=1").append("&face=1").append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    if (!TextUtils.isEmpty(paramString4))
      localStringBuffer.append("&after=").append(paramString4);
    if (!TextUtils.isEmpty(paramString5))
      localStringBuffer.append("&before=").append(paramString5);
    return localStringBuffer.toString();
  }

  public String makeGetMessageInboxRequest(String paramString1, int paramInt, String paramString2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/msg/inbox2.php").append("?uid=").append(paramString2).append("&n=").append(paramInt);
    if (!TextUtils.isEmpty(paramString1))
      localStringBuffer.append("&before=").append(paramString1);
    localStringBuffer.append("&face=1").append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeGetMessageSendboxRequest(String paramString1, int paramInt, String paramString2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/msg/sendbox.php").append("?uid=").append(paramString2).append("&n=").append(paramInt).append("&before=").append(paramString1).append("&face=1").append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeGetMoreDataRepostListRequest(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("/repaste/index.php").append("?fuid=").append(paramString1).append("&uid=").append(paramString3);
    if (!TextUtils.isEmpty(paramString2))
      localStringBuilder.append("&fp=").append(paramString2);
    localStringBuilder.append("&start=").append(paramInt2);
    localStringBuilder.append("&n=").append(paramInt1);
    localStringBuilder.append("&api_version=");
    localStringBuilder.append("3.9.9");
    localStringBuilder.append("&version=");
    localStringBuilder.append("android-3.9.9");
    return localStringBuilder.toString();
  }

  public String makeGetMoreRepostListRequest(String paramString1, Integer paramInteger, String paramString2, int paramInt)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("/repaste/friend.php");
    localStringBuilder.append("?uid=").append(paramString1).append("&num=").append(paramInteger);
    localStringBuilder.append("&fuid=").append(paramString2);
    localStringBuilder.append("&start=").append(paramInt);
    localStringBuilder.append("&n=10");
    localStringBuilder.append("&api_version=");
    localStringBuilder.append("3.9.9");
    localStringBuilder.append("&version=");
    localStringBuilder.append("android-3.9.9");
    return localStringBuilder.toString();
  }

  public String makeGetNearbyPersonRequest(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt1, int paramInt2, String paramString5)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=lbs.getStrangerCheckin2");
    localStringBuffer.append("&lat=").append(paramString1);
    localStringBuffer.append("&lon=").append(paramString2);
    setLocationXY(localStringBuffer, paramString3, paramString4);
    localStringBuffer.append("&start=").append(paramInt1);
    localStringBuffer.append("&n=").append(paramInt2);
    localStringBuffer.append("&api_version=").append("3.9.9");
    localStringBuffer.append("&version=").append("android-3.9.9");
    localStringBuffer.append("&uid=").append(paramString5);
    return localStringBuffer.toString();
  }

  public String makeGetNewChatMessageRequest(String paramString1, String paramString2, int paramInt1, int paramInt2)
  {
    StringBuffer localStringBuffer = new StringBuffer(paramString1);
    localStringBuffer.append("/k/").append(paramInt1).append("/").append(paramString2).append("/").append(paramInt2);
    return localStringBuffer.toString();
  }

  public String makeGetPhotoCloseRequest(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=advert.closeAdvert");
    localStringBuffer.append("&advertid=");
    localStringBuffer.append(paramString);
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeGetPhotoRequest()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?rt=json&method=advert.getActivity");
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeGetPhotoVisitorRequest(String paramString1, int paramInt, String paramString2, String paramString3)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=Photo.getVisitors");
    localStringBuffer.append("&before=").append(paramString1);
    localStringBuffer.append("&num=").append(paramInt);
    localStringBuffer.append("&api_version=").append("3.9.9");
    localStringBuffer.append("&version=").append("android-3.9.9");
    localStringBuffer.append("&pid=").append(paramString3);
    localStringBuffer.append("&ouid=").append(paramString2);
    return localStringBuffer.toString();
  }

  public String makeGetPwdUrl(String paramString1, String paramString2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("appid=").append(paramString2);
    localStringBuffer.append("&username=").append(paramString1);
    return localStringBuffer.toString();
  }

  public String makeGetRecommentStarsRequest(int paramInt1, int paramInt2, String paramString1, String paramString2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=friend.recomendStars");
    localStringBuffer.append("&start=").append(paramInt1);
    localStringBuffer.append("&num=").append(paramInt2);
    if (!TextUtils.isEmpty(paramString2))
      localStringBuffer.append("&type=").append(paramString2);
    localStringBuffer.append("&api_version=").append("3.9.9");
    localStringBuffer.append("&version=").append("android-3.9.9");
    localStringBuffer.append("&uid=").append(paramString1);
    return localStringBuffer.toString();
  }

  public String makeGetRecordDetailMoreRequest(String paramString1, String paramString2, String paramString3)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=record.getDetailMore").append("&uid=").append(paramString1).append("&rid=").append(paramString2).append("&fuid=").append(paramString3).append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    localStringBuffer.append("&video=").append("1");
    return localStringBuffer.toString();
  }

  public String makeGetRecordDetailRequest(String paramString1, String paramString2, String paramString3)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php").append("?method=Record.getDetail").append("&uid=").append(paramString1).append("&rid=").append(paramString2).append("&fuid=").append(paramString3).append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    localStringBuffer.append("&video=").append("1");
    return localStringBuffer.toString();
  }

  public String makeGetReplyCommentDetailRequest(int paramInt, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/comment/send_view.php").append("?&uid=").append(paramString3).append("&n=").append(paramInt).append("&thread_cid=").append(paramString1).append("&fuid=").append(paramString2).append("&pos=").append(paramString4).append("&order=1").append("&face=1").append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    if (!TextUtils.isEmpty(paramString5))
      localStringBuffer.append("&after=").append(paramString5);
    if (!TextUtils.isEmpty(paramString6))
      localStringBuffer.append("&before=").append(paramString6);
    return localStringBuffer.toString();
  }

  public String makeGetReplyCommentRequest(String paramString1, int paramInt, String paramString2, boolean paramBoolean)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/comment/send.php").append("?uid=").append(paramString2).append("&n=").append(paramInt).append("&before=").append(paramString1).append("&face=1").append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    if (paramBoolean)
      localStringBuffer.append("&unread=1");
    return localStringBuffer.toString();
  }

  public String makeGetRepostDetailRequest(Context paramContext, String paramString1, String paramString2, String paramString3, int paramInt)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/repaste/detail.php").append("?&uid=").append(paramString3).append("&urpid=").append(paramString1).append("&fuid=").append(paramString2).append("&width=").append(paramInt).append("&api_version=").append("3.9.9").append("&v=110&version=").append("android-3.9.9");
    localStringBuffer.append("&video=").append("1");
    return localStringBuffer.toString();
  }

  public String makeGetRepostListRequest(String paramString1, String paramString2, String paramString3, Integer paramInteger, String paramString4)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    if ("hot".equals(paramString3))
      localStringBuilder.append("/repaste/hot.php");
    while (true)
    {
      localStringBuilder.append("?uid=").append(paramString1).append("&num=").append(paramInteger);
      if (!TextUtils.isEmpty(paramString2))
        localStringBuilder.append("&repaste_id=").append(paramString2);
      if ((!"hot".equals(paramString3)) && (!TextUtils.isEmpty(paramString4)) && (!paramString4.equals(paramString1)))
        localStringBuilder.append("&fuid=").append(paramString4);
      localStringBuilder.append("&start=0");
      localStringBuilder.append("&n=10");
      localStringBuilder.append("&api_version=");
      localStringBuilder.append("3.9.9");
      localStringBuilder.append("&version=");
      localStringBuilder.append("android-3.9.9");
      return localStringBuilder.toString();
      localStringBuilder.append("/repaste/friend.php");
    }
  }

  public String makeGetRepostListRequest(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("/repaste/index.php").append("?fuid=").append(paramString1).append("&uid=").append(paramString4);
    if (!TextUtils.isEmpty(paramString2))
      localStringBuilder.append("&lasturpid=").append(paramString2);
    if (!TextUtils.isEmpty(paramString3))
      localStringBuilder.append("&fp=").append(paramString3);
    localStringBuilder.append("&n=").append(paramInt);
    localStringBuilder.append("&api_version=");
    localStringBuilder.append("3.9.9");
    localStringBuilder.append("&version=");
    localStringBuilder.append("android-3.9.9");
    return localStringBuilder.toString();
  }

  public String makeGetRepostMoreListRequest(String paramString1, String paramString2, Integer paramInteger, String paramString3, int paramInt)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    if ("hot".equals(paramString2))
      localStringBuilder.append("/repaste/hot.php");
    while (true)
    {
      localStringBuilder.append("?uid=").append(paramString1).append("&num=").append(paramInteger);
      if ((!"hot".equals(paramString2)) && (!TextUtils.isEmpty(paramString3)) && (!paramString3.equals(paramString1)))
        localStringBuilder.append("&fuid=").append(paramString3);
      localStringBuilder.append("&start=").append(paramInt);
      localStringBuilder.append("&n=10");
      localStringBuilder.append("&api_version=");
      localStringBuilder.append("3.9.9");
      localStringBuilder.append("&version=");
      localStringBuilder.append("android-3.9.9");
      return localStringBuilder.toString();
      localStringBuilder.append("/repaste/friend.php");
    }
  }

  public String makeGetRepostPush(String paramString1, String paramString2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/home/unsubscribe.php?");
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    localStringBuffer.append("&uid=");
    localStringBuffer.append(paramString1);
    localStringBuffer.append("&flag=");
    localStringBuffer.append(paramString2);
    return localStringBuffer.toString();
  }

  public String makeGetSentUserCommentDetailRequest(int paramInt, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/comment/usend_view.php").append("?&uid=").append(paramString2).append("&n=").append(paramInt).append("&thread_cid=").append(paramString1).append("&pos=").append(paramString3).append("&order=1").append("&face=1").append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    if (!TextUtils.isEmpty(paramString4))
      localStringBuffer.append("&after=").append(paramString4);
    if (!TextUtils.isEmpty(paramString5))
      localStringBuffer.append("&before=").append(paramString5);
    return localStringBuffer.toString();
  }

  public String makeGetSentUserCommentRequest(String paramString1, int paramInt, String paramString2, boolean paramBoolean)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/comment/usend.php").append("?&uid=").append(paramString2).append("&n=").append(paramInt).append("&before=").append(paramString1).append("&face=1").append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    if (paramBoolean)
      localStringBuffer.append("&unread=1");
    return localStringBuffer.toString();
  }

  public String makeGetStarsRequest(int paramInt1, int paramInt2, String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=friend.getRandomStar");
    localStringBuffer.append("&start=").append(paramInt1);
    localStringBuffer.append("&n=").append(paramInt2);
    localStringBuffer.append("&api_version=").append("3.9.9");
    localStringBuffer.append("&version=").append("android-3.9.9");
    localStringBuffer.append("&uid=").append(paramString);
    return localStringBuffer.toString();
  }

  public String makeGetStyleBoxDiaryRequest(String paramString1, String paramString2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/9diary/detail_client.php");
    localStringBuffer.append("?uid=").append(paramString1);
    localStringBuffer.append("&did=").append(paramString2);
    return localStringBuffer.toString();
  }

  public String makeGetSystemMentionMeMessageRequest(String paramString1, int paramInt, String paramString2, String paramString3)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=message.getSysList").append("&uid=").append(paramString3).append("&verify=").append(paramString2).append("&n=").append(paramInt).append("&before=").append(paramString1).append("&face=1&format=1").append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9").append("&type=2");
    return localStringBuffer.toString();
  }

  public String makeGetSystemtMessageByTypeRequest(String paramString1, int paramInt, String paramString2, String paramString3)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=message.getSysList").append("&uid=").append(paramString2).append("&n=").append(paramInt);
    if (!TextUtils.isEmpty(paramString1))
      localStringBuffer.append("&before=").append(paramString1);
    localStringBuffer.append("&face=1").append("&format=1").append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9").append("&type=").append(paramString3);
    localStringBuffer.append("&video=").append("1");
    return localStringBuffer.toString();
  }

  public String makeGetSystemtMessageRequest(String paramString1, int paramInt, String paramString2)
  {
    String[] arrayOfString = new String[3];
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=message.getSysList").append("&uid=").append(paramString2).append("&n=").append(paramInt).append("&before=").append(paramString1).append("&face=1").append("&format=1").append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9").append("&type=1");
    arrayOfString[0] = localStringBuffer.toString();
    localStringBuffer.delete(0, localStringBuffer.length());
    localStringBuffer.append("/capi/rest.php?method=message.getSysList").append("&uid=").append(paramString2).append("&n=").append(paramInt).append("&before=").append(paramString1).append("&face=1").append("&format=1").append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9").append("&type=2");
    arrayOfString[1] = localStringBuffer.toString();
    localStringBuffer.delete(0, localStringBuffer.length());
    localStringBuffer.append("/capi/rest.php?method=message.getSysList").append("&uid=").append(paramString2).append("&n=").append(paramInt).append("&before=").append(paramString1).append("&face=1").append("&format=1").append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9").append("&type=3");
    arrayOfString[2] = localStringBuffer.toString();
    return getDataByUrls(arrayOfString);
  }

  public String makeGetSystemtRepostMessageRequest(String paramString1, int paramInt, String paramString2, String paramString3)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=message.getSysList").append("&uid=").append(paramString3).append("&verify=").append(paramString2).append("&n=").append(paramInt).append("&before=").append(paramString1).append("&face=1&format=1").append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9").append("&type=3");
    return localStringBuffer.toString();
  }

  public String makeGetThirdAppTokenRequest(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/mobile/accesstokens.json");
    localStringBuffer.append("?appids=").append(paramString);
    return localStringBuffer.toString();
  }

  public String makeGetThirdApplistRequest(int paramInt1, int paramInt2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=application.getWapAppList");
    localStringBuffer.append("&start=").append(paramInt1);
    localStringBuffer.append("&n=").append(paramInt2);
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeGetThirdPartyApplistRequest(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=App.getThirdList").append("&uid=").append(paramString).append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeGetThirdPartyUrl(String paramString1, String paramString2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=advert.getweburl");
    localStringBuffer.append("&uid=").append(paramString1);
    localStringBuffer.append("&thirdpartyname=").append(paramString2);
    localStringBuffer.append("&api_version=").append("3.9.9");
    localStringBuffer.append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeGetTopicGroupRequest(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=topic.getTopicFeed");
    localStringBuffer.append("&search=");
    localStringBuffer.append(paramString2);
    localStringBuffer.append("&uid=");
    localStringBuffer.append(paramString1);
    localStringBuffer.append("&start=");
    localStringBuffer.append(paramString3);
    localStringBuffer.append("&cnt=");
    localStringBuffer.append(paramString4);
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeGetTouchListRequest()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/move/gettypes.json");
    return localStringBuffer.toString();
  }

  public String makeGetTruthDetailRequest(String paramString1, String paramString2, String paramString3)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/truth/detail.php").append("?uid=").append(paramString3).append("&tid=").append(paramString1).append("&fuid=").append(paramString2).append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeGetUserCardRequest()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=User.getcard");
    localStringBuffer.append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeGetUserCommentDetailRequest(int paramInt, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/comment/uindex_view.php").append("?&uid=").append(paramString2).append("&n=").append(paramInt).append("&thread_cid=").append(paramString1).append("&pos=").append(paramString3).append("&order=1").append("&face=1").append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    if (!TextUtils.isEmpty(paramString4))
      localStringBuffer.append("&after=").append(paramString4);
    if (!TextUtils.isEmpty(paramString5))
      localStringBuffer.append("&before=").append(paramString5);
    return localStringBuffer.toString();
  }

  public String makeGetUserCommentRequest(String paramString1, int paramInt, String paramString2, boolean paramBoolean)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/comment/uindex.php").append("?uid=").append(paramString2).append("&n=").append(paramInt);
    if (!TextUtils.isEmpty(paramString1))
      localStringBuffer.append("&before=").append(paramString1);
    if (paramBoolean)
      localStringBuffer.append("&unread=1");
    localStringBuffer.append("&face=1").append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeGetVisitorsRequest(String paramString, int paramInt1, int paramInt2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=visitor.getHomeVisitor").append("&start=").append(paramInt1).append("&n=").append(paramInt2).append("&fuid=").append(paramString).append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeGetVisitorsRequest(String paramString1, String paramString2, String paramString3)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (paramString2.endsWith("1018"))
      localStringBuffer.append("/capi/rest.php?rt=json&method=record.getLogoList").append("&record_id=").append(paramString1).append("&ownerid=").append(paramString3).append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    while (true)
    {
      return localStringBuffer.toString();
      localStringBuffer.append("/capi/rest.php?rt=json&method=checkin.getLogoList").append("&checkin_id=").append(paramString1).append("&ownerid=").append(paramString3).append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    }
  }

  public String makeGetVoteListRequest(String paramString1, String paramString2, int paramInt)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=vote.getList").append("&fuid=").append(paramString1).append("&n=").append(paramInt);
    if (!TextUtils.isEmpty(paramString2))
      localStringBuffer.append("&lastvid=").append(paramString2);
    localStringBuffer.append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeGetVoteRequest(String paramString1, String paramString2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/vote/detail.php").append("?&uid=").append(paramString2).append("&vid=").append(paramString1).append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeGuideSendNotice(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=version.sendUpdateRecord");
    localStringBuffer.append("&uid=").append(paramString);
    localStringBuffer.append("&api_version=").append("3.9.9");
    localStringBuffer.append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeHasNewTaskUrl()
  {
    String str = User.getInstance().getUID();
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?rt=json&method=account.hasNewActivity").append("&fuid=").append(str).append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeInfoCompletedRequest(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=account.updateUserInfo");
    localStringBuffer.append("&real_name=").append(URLEncoder.encode(paramString1));
    localStringBuffer.append("&gender=").append(URLEncoder.encode(paramString2));
    localStringBuffer.append("&year=").append(paramString3);
    localStringBuffer.append("&month=").append(paramString4);
    localStringBuffer.append("&day=").append(paramString5);
    localStringBuffer.append("&hometown=").append(URLEncoder.encode(paramString6));
    localStringBuffer.append("&company=").append(URLEncoder.encode(paramString7));
    localStringBuffer.append("&school=").append(URLEncoder.encode(paramString8));
    localStringBuffer.append("&api_version=").append("3.9.9");
    localStringBuffer.append("&version=").append("android-3.9.9");
    localStringBuffer.append("&uid=").append(paramString9);
    return localStringBuffer.toString();
  }

  public String makeInfoUpdatedRequest(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?&method=account.updateInfos");
    localStringBuffer.append("&mobile=").append(URLEncoder.encode(paramString1));
    localStringBuffer.append("&year=").append(paramString2);
    localStringBuffer.append("&month=").append(paramString3);
    localStringBuffer.append("&day=").append(paramString4);
    localStringBuffer.append("&hometown=").append(URLEncoder.encode(paramString5));
    localStringBuffer.append("&city=").append(URLEncoder.encode(paramString6));
    localStringBuffer.append("&gender=").append(paramString9);
    localStringBuffer.append("&api_version=").append("3.9.9");
    localStringBuffer.append("&version=").append("android-3.9.9");
    localStringBuffer.append("&uid=").append(paramString8);
    if (!TextUtils.isEmpty(paramString7))
      localStringBuffer.append("&verify=").append(paramString7);
    return localStringBuffer.toString();
  }

  public String makeLBSCheckInRequest(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=lbs.checkIn");
    localStringBuffer.append("&uid=").append(paramString);
    localStringBuffer.append("&api_version=").append("3.9.9");
    localStringBuffer.append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeLBSCommentTitleRequest(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=lbs.getCheckin");
    localStringBuffer.append("&wid=");
    localStringBuffer.append(paramString);
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeLBSDeleteAwardRequest(String paramString1, String paramString2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=lbs.delAward");
    localStringBuffer.append("&uid=").append(paramString1);
    localStringBuffer.append("&rid=").append(paramString2);
    localStringBuffer.append("&api_version=").append("3.9.9");
    localStringBuffer.append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeLBSGetAwardsRequest(String paramString, int paramInt1, int paramInt2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=lbs.getAwards");
    localStringBuffer.append("&uid=").append(paramString);
    localStringBuffer.append("&start=").append(paramInt1).append("&n=").append(paramInt2);
    localStringBuffer.append("&api_version=").append("3.9.9");
    localStringBuffer.append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeLBSUseAwardRequest(String paramString1, String paramString2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=lbs.useAward");
    localStringBuffer.append("&uid=").append(paramString1);
    localStringBuffer.append("&rid=").append(paramString2);
    localStringBuffer.append("&api_version=").append("3.9.9");
    localStringBuffer.append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeLbsAddLocationRequest(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=lbs.createPoi");
    localStringBuffer.append("&uid=");
    localStringBuffer.append(paramString7);
    localStringBuffer.append("&lon=");
    localStringBuffer.append(paramString3);
    localStringBuffer.append("&lat=");
    localStringBuffer.append(paramString4);
    localStringBuffer.append("&name=");
    localStringBuffer.append(URLEncoder.encode(paramString1));
    setLocationXY(localStringBuffer, paramString5, paramString6);
    localStringBuffer.append("&addr=");
    localStringBuffer.append(URLEncoder.encode(paramString2));
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeLbsGetCheckInRequest(String paramString1, String paramString2, int paramInt1, int paramInt2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=lbs.getPoiCheckin").append("&poiid=").append(paramString1);
    if (TextUtils.isEmpty(paramString2))
      paramString2 = "0";
    localStringBuffer.append("&type=").append(paramString2).append("&start=").append(paramInt1).append("&n=").append(paramInt2);
    localStringBuffer.append("&api_version=").append("3.9.9");
    localStringBuffer.append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeLbsGetPoiInfoRequest(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=lbs.getPoiInfo").append("&poiid=").append(paramString1);
    if ((!TextUtils.isEmpty(paramString2)) && (!TextUtils.isEmpty(paramString3)))
      localStringBuffer.append("&lon=").append(paramString2).append("&lat=").append(paramString3);
    setLocationXY(localStringBuffer, paramString4, paramString5);
    localStringBuffer.append("&api_version=").append("3.9.9");
    localStringBuffer.append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeLbsGetPoisActivityListRequest(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, int paramInt1, int paramInt2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=lbs.getActivity");
    localStringBuffer.append("&uid=").append(paramString1);
    if (!TextUtils.isEmpty(paramString2))
      localStringBuffer.append("&lon=").append(paramString2);
    if (!TextUtils.isEmpty(paramString3))
      localStringBuffer.append("&lat=").append(paramString3);
    setLocationXY(localStringBuffer, paramString4, paramString5);
    localStringBuffer.append("&start=").append(paramInt1);
    localStringBuffer.append("&n=").append(paramInt2);
    localStringBuffer.append("&api_version=").append("3.9.9");
    localStringBuffer.append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeLbsGetPoisRequest(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, int paramInt1, int paramInt2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=lbs.getPois");
    localStringBuffer.append("&uid=").append(paramString1);
    if (!TextUtils.isEmpty(paramString2))
      localStringBuffer.append("&lon=").append(paramString2);
    if (!TextUtils.isEmpty(paramString3))
      localStringBuffer.append("&lat=").append(paramString3);
    setLocationXY(localStringBuffer, paramString4, paramString5);
    if (!TextUtils.isEmpty(paramString6))
      localStringBuffer.append("&cellid=").append(paramString6);
    if (TextUtils.isEmpty(paramString7))
      localStringBuffer.append("&filter=100");
    while (true)
    {
      localStringBuffer.append("&start=").append(paramInt1);
      localStringBuffer.append("&n=").append(paramInt2);
      localStringBuffer.append("&api_version=").append("3.9.9");
      localStringBuffer.append("&version=").append("android-3.9.9");
      return localStringBuffer.toString();
      localStringBuffer.append("&filter=").append(paramString7);
    }
  }

  public String makeLbsSearchPoisRequest(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, int paramInt1, int paramInt2, String paramString7)
  {
    if (TextUtils.isEmpty(paramString7))
      return makeLbsGetPoisRequest(paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, null, paramInt1, paramInt2);
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=lbs.searchPois");
    localStringBuffer.append("&uid=").append(paramString1);
    if (!TextUtils.isEmpty(paramString2))
      localStringBuffer.append("&lon=").append(paramString2);
    if (!TextUtils.isEmpty(paramString3))
      localStringBuffer.append("&lat=").append(paramString3);
    if (!TextUtils.isEmpty(paramString6))
      localStringBuffer.append("&cellid=").append(paramString6);
    setLocationXY(localStringBuffer, paramString4, paramString5);
    localStringBuffer.append("&key=").append(URLEncoder.encode(paramString7));
    localStringBuffer.append("&start=").append(paramInt1);
    localStringBuffer.append("&n=").append(paramInt2);
    localStringBuffer.append("&api_version=").append("3.9.9");
    localStringBuffer.append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeLocationListRequest(Context paramContext, String paramString1, String paramString2, int paramInt1, int paramInt2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=lbs.getMyChecks");
    localStringBuffer.append("&uid=");
    localStringBuffer.append(paramString1);
    localStringBuffer.append("&fuid=");
    localStringBuffer.append(paramString2);
    localStringBuffer.append("&start=");
    localStringBuffer.append(paramInt1);
    localStringBuffer.append("&num=");
    localStringBuffer.append(paramInt2);
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeLogoPhotoListRequest(String paramString1, String paramString2, int paramInt1, int paramInt2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/photo/logolist.php");
    localStringBuffer.append("?uid=").append(paramString1);
    localStringBuffer.append("&fuid=").append(paramString2);
    localStringBuffer.append("&start=").append(paramInt1);
    localStringBuffer.append("&n=").append(paramInt2);
    localStringBuffer.append("&api_version=").append("3.9.9");
    localStringBuffer.append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeLogoutRequest(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/kxapi/login/loginout.php");
    localStringBuffer.append("?uid=");
    localStringBuffer.append(paramString);
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeLookFindFriendRequest(String paramString1, String paramString2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/home/recommend_friend.php");
    if (!TextUtils.isEmpty(paramString1))
    {
      localStringBuffer.append("?start=");
      localStringBuffer.append(paramString1);
    }
    if (!TextUtils.isEmpty(paramString2))
    {
      localStringBuffer.append("&n=");
      localStringBuffer.append(paramString2);
    }
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeLookNewsRequest(Context paramContext, boolean paramBoolean, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/home/publicfeed.php");
    if (!TextUtils.isEmpty(paramString1))
    {
      localStringBuffer.append("?start=");
      localStringBuffer.append(paramString1);
    }
    if (!TextUtils.isEmpty(paramString2))
    {
      localStringBuffer.append("&n=");
      localStringBuffer.append(paramString2);
    }
    if (!TextUtils.isEmpty(User.getInstance().getUID()))
    {
      localStringBuffer.append("&uid=");
      localStringBuffer.append(User.getInstance().getUID());
    }
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeMP4VideoRequest()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("http://tdwap.tudou.com/sns/playurlconvert");
    return localStringBuffer.toString();
  }

  public String makeMessageCenterRequest(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=message.getNewNoticeNum").append("&uid=").append(paramString).append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeMessageLeaveOrJoinRequest(String paramString1, String paramString2, boolean paramBoolean)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php");
    localStringBuffer.append("?uid=");
    localStringBuffer.append(paramString1);
    if (paramBoolean)
      localStringBuffer.append("&method=Message.leave");
    while (true)
    {
      localStringBuffer.append("&thread_cid=");
      localStringBuffer.append(paramString2);
      localStringBuffer.append("&api_version=");
      localStringBuffer.append("3.9.9");
      localStringBuffer.append("&version=");
      localStringBuffer.append("android-3.9.9");
      return localStringBuffer.toString();
      localStringBuffer.append("&method=Message.join");
    }
  }

  public String makeMoreCommentRequest(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/comment/app.php");
    localStringBuffer.append("?uid=");
    localStringBuffer.append(paramString1);
    localStringBuffer.append("&type=");
    localStringBuffer.append(paramString3);
    localStringBuffer.append("&id=");
    localStringBuffer.append(paramString2);
    localStringBuffer.append("&fuid=");
    localStringBuffer.append(paramString4);
    localStringBuffer.append("&face=1");
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    localStringBuffer.append("&after_tid=").append(paramString5);
    localStringBuffer.append("&num=").append(paramInt);
    return localStringBuffer.toString();
  }

  public String makeMutualFriendsRequest(String paramString1, String paramString2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=friend.getMutualFriends");
    localStringBuffer.append("&fuid=");
    localStringBuffer.append(paramString1);
    localStringBuffer.append("&start=");
    localStringBuffer.append(paramString2);
    localStringBuffer.append("&n=");
    localStringBuffer.append("20");
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeNameVerify(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=register.checkIsValidName");
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeNearbyFriendCheckinListbyPoi(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt1, int paramInt2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=lbs.getFriendCheckin");
    localStringBuffer.append("&uid=").append(paramString5);
    localStringBuffer.append("&lat=").append(paramString1);
    localStringBuffer.append("&lon=").append(paramString2);
    setLocationXY(localStringBuffer, paramString3, paramString4);
    localStringBuffer.append("&start=").append(paramInt1);
    localStringBuffer.append("&n=").append(paramInt2);
    localStringBuffer.append("&api_version=").append("3.9.9");
    localStringBuffer.append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeNewFriendRequest(String paramString1, String paramString2, String paramString3)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/friend/new.php");
    localStringBuffer.append("?uid=");
    localStringBuffer.append(paramString1);
    localStringBuffer.append("&touid=");
    localStringBuffer.append(paramString2);
    if (!TextUtils.isEmpty(paramString3))
    {
      localStringBuffer.append("&type=");
      localStringBuffer.append(paramString3);
    }
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeNewsDiaryRequest(Context paramContext, String paramString1, String paramString2, String paramString3)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=news.getList");
    localStringBuffer.append("&uid=");
    localStringBuffer.append(paramString3);
    if (!TextUtils.isEmpty(paramString2))
    {
      localStringBuffer.append("&n=");
      localStringBuffer.append(paramString2);
    }
    if (!TextUtils.isEmpty(paramString1))
    {
      localStringBuffer.append("&before=");
      localStringBuffer.append(paramString1);
    }
    localStringBuffer.append("&type=");
    localStringBuffer.append("2%2c1210");
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeNewsRequest(Context paramContext, boolean paramBoolean, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/kxapi/home/index_kx.php");
    localStringBuffer.append("?uid=");
    localStringBuffer.append(paramString3);
    localStringBuffer.append("&face=1");
    localStringBuffer.append("&format=1");
    if (!TextUtils.isEmpty(paramString2))
    {
      localStringBuffer.append("&n=");
      localStringBuffer.append(paramString2);
    }
    if (!TextUtils.isEmpty(paramString4))
    {
      localStringBuffer.append("&flag=");
      localStringBuffer.append(paramString4);
    }
    if (!TextUtils.isEmpty(paramString5))
    {
      localStringBuffer.append("&before=");
      localStringBuffer.append(paramString5);
    }
    while (true)
    {
      if (!TextUtils.isEmpty(paramString6))
      {
        localStringBuffer.append("&type=");
        localStringBuffer.append(paramString6);
      }
      if (!TextUtils.isEmpty(paramString7))
      {
        localStringBuffer.append("&fuid=");
        localStringBuffer.append(paramString7);
      }
      localStringBuffer.append("&video=").append("1");
      localStringBuffer.append("&api_version=");
      localStringBuffer.append("3.9.9");
      localStringBuffer.append("&version=");
      localStringBuffer.append("android-3.9.9");
      return localStringBuffer.toString();
      if (TextUtils.isEmpty(paramString1))
        continue;
      localStringBuffer.append("&start=");
      localStringBuffer.append(paramString1);
    }
  }

  public String makeObjCommentRequest(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/comment/app.php");
    localStringBuffer.append("?uid=");
    localStringBuffer.append(paramString1);
    localStringBuffer.append("&type=");
    localStringBuffer.append(paramString3);
    localStringBuffer.append("&id=");
    localStringBuffer.append(paramString2);
    localStringBuffer.append("&fuid=");
    localStringBuffer.append(paramString4);
    localStringBuffer.append("&face=1");
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeOtherFriendCheckinListbyPoi(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt1, int paramInt2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=lbs.getOtherFriendCheckin");
    localStringBuffer.append("&uid=").append(paramString5);
    localStringBuffer.append("&lat=").append(paramString1);
    localStringBuffer.append("&lon=").append(paramString2);
    localStringBuffer.append("&start=").append(paramInt1);
    localStringBuffer.append("&n=").append(paramInt2);
    localStringBuffer.append("&api_version=").append("3.9.9");
    localStringBuffer.append("&version=").append("android-3.9.9");
    setLocationXY(localStringBuffer, paramString3, paramString4);
    return localStringBuffer.toString();
  }

  public String makePKInfoByTitleRequest(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("/capi/rest.php?method=Topic.getPKInfoByTopicName");
    localStringBuilder.append("&topic=").append("%23" + paramString + "%23");
    return localStringBuilder.toString();
  }

  public String makePKInfoRequest(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("/capi/rest.php?method=Topic.getPKInfoByTopicid").append("&topicid=").append(paramString);
    return localStringBuilder.toString();
  }

  public String makePKRecordListRequest(int paramInt1, String paramString, int paramInt2, int paramInt3)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("/capi/rest.php?method=Topic.getPKRecordsList");
    localStringBuilder.append("&topicid=").append(paramString);
    localStringBuilder.append("&pktype=").append(paramInt1);
    localStringBuilder.append("&start=").append(paramInt2);
    localStringBuilder.append("&num=").append(paramInt3);
    return localStringBuilder.toString();
  }

  public String makePKVoteRequest(String paramString, int paramInt)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("/capi/rest.php?method=Topic.pkvote");
    localStringBuilder.append("&topicid=").append(paramString);
    localStringBuilder.append("&pktype=").append(paramInt);
    return localStringBuilder.toString();
  }

  public String makePhotoDetailRequest(String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=photo.getDetail");
    localStringBuffer.append("&uid=");
    localStringBuffer.append(paramString2);
    localStringBuffer.append("&verify=");
    localStringBuffer.append(paramString1);
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    localStringBuffer.append("&ouid=");
    localStringBuffer.append(paramInt1);
    localStringBuffer.append("&pid=");
    localStringBuffer.append(paramInt2);
    return localStringBuffer.toString();
  }

  public String makePlazaPhotoRequest(String paramString, int paramInt1, int paramInt2, long paramLong)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=photo.getPlaza");
    localStringBuffer.append("&uid=");
    localStringBuffer.append(paramString);
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    localStringBuffer.append("&start=");
    localStringBuffer.append(paramInt1);
    localStringBuffer.append("&num=");
    localStringBuffer.append(paramInt2);
    localStringBuffer.append("&before=");
    localStringBuffer.append(paramLong);
    return localStringBuffer.toString();
  }

  public String makePoiPhotoes(String paramString1, String paramString2, int paramInt1, int paramInt2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=lbs.getPhotos");
    localStringBuffer.append("&uid=").append(paramString2);
    localStringBuffer.append("&poiid=").append(paramString1);
    localStringBuffer.append("&start=").append(paramInt1);
    localStringBuffer.append("&n=").append(paramInt2);
    localStringBuffer.append("&api_version=").append("3.9.9");
    localStringBuffer.append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makePoisPhotoes(String paramString1, String paramString2, int paramInt)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=lbs.getPoisPhoto");
    localStringBuffer.append("&uid=").append(paramString2);
    localStringBuffer.append("&poiids=").append(paramString1);
    localStringBuffer.append("&n=").append(paramInt);
    localStringBuffer.append("&api_version=").append("3.9.9");
    localStringBuffer.append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makePostCharGroupRecordRequest(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=rgroup.talk");
    localStringBuffer.append("&verify=");
    localStringBuffer.append(paramString);
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makePostCircleRecordRequest()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=rgroup.talk");
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makePostCommentRequest(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/comment/post.php");
    localStringBuffer.append("?uid=");
    localStringBuffer.append(paramString1);
    localStringBuffer.append("&type=");
    localStringBuffer.append(paramString2);
    localStringBuffer.append("&id=");
    localStringBuffer.append(paramString3);
    localStringBuffer.append("&ouid=");
    localStringBuffer.append(paramString4);
    localStringBuffer.append("&hidden=");
    localStringBuffer.append(paramString5);
    localStringBuffer.append("&content=");
    localStringBuffer.append(URLEncoder.encode(paramString6));
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makePostDiaryRequest(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/diary/post_multipart.php").append("?uid=").append(paramString1).append("&title=").append(URLEncoder.encode(paramString2)).append("&location=").append(URLEncoder.encode(paramString3)).append("&lat=").append(paramString4).append("&lon=").append(paramString5).append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makePostDiaryRequest(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/diary/post.php").append("?&uid=").append(paramString7).append("&title=").append(URLEncoder.encode(paramString1)).append("&content=").append(URLEncoder.encode(paramString2)).append("&did=").append(paramString6).append("&location=").append(URLEncoder.encode(paramString3)).append("&lat=").append(paramString4).append("&lon=").append(paramString5).append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makePostGiftRequest(String paramString1, String paramString2, String paramString3, String paramString4, boolean paramBoolean1, boolean paramBoolean2, int paramInt)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=gift.sendGift");
    localStringBuffer.append("&uid=");
    localStringBuffer.append(paramString1);
    localStringBuffer.append("&fuid=");
    localStringBuffer.append(paramString2);
    localStringBuffer.append("&smid=");
    localStringBuffer.append(paramString3);
    localStringBuffer.append("&msg=");
    localStringBuffer.append(URLEncoder.encode(paramString4));
    localStringBuffer.append("&quiet=");
    String str1;
    if (paramBoolean1)
    {
      str1 = "1";
      localStringBuffer.append(str1);
      localStringBuffer.append("&mobile=");
      if (!paramBoolean2)
        break label205;
    }
    label205: for (String str2 = "1"; ; str2 = "0")
    {
      localStringBuffer.append(str2);
      localStringBuffer.append("&gid=");
      localStringBuffer.append(paramInt);
      localStringBuffer.append("&api_version=");
      localStringBuffer.append("3.9.9");
      localStringBuffer.append("&version=");
      localStringBuffer.append("android-3.9.9");
      return localStringBuffer.toString();
      str1 = "0";
      break;
    }
  }

  public String makePostMessageRequest(ArrayList<String> paramArrayList, String paramString1, String paramString2)
  {
    String str = getTouids(paramArrayList);
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/msg/post.php").append("?&uid=").append(paramString2).append("&touid=").append(str).append("&msgcont=").append(URLEncoder.encode(paramString1)).append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makePostRecordRequest()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=Record.post");
    return localStringBuffer.toString();
  }

  public String makePostRecordRequest(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/record/post.php");
    localStringBuffer.append("?uid=");
    localStringBuffer.append(paramString1);
    localStringBuffer.append("&privacy=");
    localStringBuffer.append(paramString2);
    localStringBuffer.append("&reccont=");
    localStringBuffer.append(URLEncoder.encode(paramString3));
    localStringBuffer.append("&location=");
    localStringBuffer.append(URLEncoder.encode(paramString4));
    localStringBuffer.append("&lat=");
    localStringBuffer.append(paramString5);
    localStringBuffer.append("&lon");
    localStringBuffer.append(paramString6);
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makePostReleLinkRequest(String paramString1, String paramString2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=repaste.getRecommendList").append("&rpid=").append(paramString1).append("&ruid=").append(paramString2);
    return localStringBuffer.toString();
  }

  public String makePostReplyRequest(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/comment/reply.php");
    localStringBuffer.append("?uid=");
    localStringBuffer.append(paramString1);
    localStringBuffer.append("&fuid=");
    localStringBuffer.append(paramString3);
    localStringBuffer.append("&thread_cid=");
    localStringBuffer.append(paramString2);
    localStringBuffer.append("&replycont=");
    localStringBuffer.append(URLEncoder.encode(paramString4));
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makePostTouchRequest(String paramString1, String paramString2, String paramString3, boolean paramBoolean, int paramInt)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/move/send.json");
    localStringBuffer.append("?touid=");
    localStringBuffer.append(paramString1);
    localStringBuffer.append("&is_privacy=");
    if (paramBoolean);
    for (String str = "1"; ; str = "0")
    {
      localStringBuffer.append(str);
      localStringBuffer.append("&type=");
      localStringBuffer.append(paramInt);
      localStringBuffer.append("&smid=");
      localStringBuffer.append(paramString2);
      localStringBuffer.append("&is_diff_action=1");
      return localStringBuffer.toString();
    }
  }

  public String makePostUpRequest(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/common/up.php");
    localStringBuffer.append("?uid=");
    localStringBuffer.append(paramString1);
    localStringBuffer.append("&appid=");
    localStringBuffer.append(paramString2);
    localStringBuffer.append("&id=");
    localStringBuffer.append(paramString3);
    localStringBuffer.append("&touid=");
    localStringBuffer.append(paramString4);
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeRecommendAppNotify()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=application.hasNew");
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeRecordListRequest(Context paramContext, String paramString1, String paramString2, int paramInt1, int paramInt2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php");
    localStringBuffer.append("?method=Record.getList");
    localStringBuffer.append("&uid=");
    localStringBuffer.append(paramString1);
    localStringBuffer.append("&fuid=");
    localStringBuffer.append(paramString2);
    localStringBuffer.append("&start=");
    localStringBuffer.append(paramInt1);
    localStringBuffer.append("&n=");
    localStringBuffer.append(paramInt2);
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    localStringBuffer.append("&video=").append("1");
    return localStringBuffer.toString();
  }

  public String makeRecordRequest(String paramString1, String paramString2, String paramString3)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/record/post.php");
    localStringBuffer.append("?uid=");
    localStringBuffer.append(paramString1);
    localStringBuffer.append("&privacy=");
    localStringBuffer.append(paramString2);
    localStringBuffer.append("&reccont=");
    localStringBuffer.append(URLEncoder.encode(paramString3));
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeRefuseFriendRequest(String paramString1, String paramString2, String paramString3)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/friend/refusefriend.php").append("?&uid=").append(paramString3).append("&fuid=").append(paramString1).append("&smid=").append(paramString2).append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeReplyMessageRequest(String paramString1, String paramString2, String paramString3)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/msg/post.php").append("?&uid=").append(paramString3).append("&mid=").append(paramString1).append("&msgcont=").append(URLEncoder.encode(paramString2)).append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeRepostDiaryRequest(String paramString1, String paramString2, String paramString3)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/repaste/repaste_submit.php").append("?&uid=").append(paramString3).append("&urpid=").append(paramString1).append("&fuid=").append(paramString2).append("&reptype=diary").append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeRepostListRequest(String paramString, int paramInt1, int paramInt2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?");
    localStringBuffer.append("?rt=json");
    localStringBuffer.append("&method=record.getForwardList");
    localStringBuffer.append("&record_id=").append(paramString);
    localStringBuffer.append("&start=").append(paramInt1);
    localStringBuffer.append("&num=").append(paramInt2);
    localStringBuffer.append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeRepostRequest(String paramString1, String paramString2, String paramString3)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/repaste/repaste_submit.php").append("?&uid=").append(paramString3).append("&urpid=").append(paramString1).append("&fuid=").append(paramString2).append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeRepostVoteRequest(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/repaste/vote_submit.php").append("?&uid=").append(paramString6).append("&urpid=").append(paramString1).append("&voteuid=").append(paramString2).append("&suid=").append(paramString3).append("&surpid=").append(paramString4).append("&answernum=").append(paramString5).append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeRepostWeiboRequest(String paramString1, String paramString2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=Record.forward");
    return localStringBuffer.toString();
  }

  public String makeSearchClassmateRequest(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2, String paramString4)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=friend.findUsers");
    localStringBuffer.append("&school=").append(URLEncoder.encode(paramString1));
    if (!TextUtils.isEmpty(paramString2))
      localStringBuffer.append("&college=").append(URLEncoder.encode(paramString2));
    if (!TextUtils.isEmpty(paramString3))
      localStringBuffer.append("&year=").append(URLEncoder.encode(paramString3));
    localStringBuffer.append("&start=").append(paramInt1);
    localStringBuffer.append("&num=").append(paramInt2);
    localStringBuffer.append("&api_version=").append("3.9.9");
    localStringBuffer.append("&version=").append("android-3.9.9");
    localStringBuffer.append("&uid=").append(paramString4);
    return localStringBuffer.toString();
  }

  public String makeSearchCollegueRequest(String paramString1, int paramInt1, int paramInt2, String paramString2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=friend.findUsers");
    localStringBuffer.append("&company=").append(URLEncoder.encode(paramString1));
    localStringBuffer.append("&start=").append(paramInt1);
    localStringBuffer.append("&num=").append(paramInt2);
    localStringBuffer.append("&api_version=").append("3.9.9");
    localStringBuffer.append("&version=").append("android-3.9.9");
    localStringBuffer.append("&uid=").append(paramString2);
    return localStringBuffer.toString();
  }

  public String makeSearchFriendRequest(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/friend/search.php");
    localStringBuffer.append("?email=").append(paramString);
    localStringBuffer.append("&api_version=").append("3.9.9");
    localStringBuffer.append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeSetCoverList(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=setting.setCover");
    localStringBuffer.append("&id=");
    localStringBuffer.append(paramString);
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeShareWXAddScore(String paramString1, String paramString2, String paramString3)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=uniongame.awardWeixinShareGifts");
    localStringBuffer.append("&uid=").append(paramString1);
    localStringBuffer.append("&appid=").append(paramString2);
    localStringBuffer.append("&giftkey=").append(paramString3);
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeShareWXGetInfo(String paramString1, String paramString2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=uniongame.getWeixinFriendCircleShareInfo");
    localStringBuffer.append("&uid=").append(paramString1);
    localStringBuffer.append("&appid=").append(paramString2);
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeStarsTypeRequest(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=friend.getStarCategorys");
    localStringBuffer.append("&api_version=").append("3.9.9");
    localStringBuffer.append("&version=").append("android-3.9.9");
    localStringBuffer.append("&uid=").append(paramString);
    return localStringBuffer.toString();
  }

  public String makeStatusRequest(String paramString1, String paramString2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/home/update_status.php");
    localStringBuffer.append("?uid=");
    localStringBuffer.append(paramString1);
    localStringBuffer.append("&state=");
    localStringBuffer.append(URLEncoder.encode(paramString2));
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeStrangeCheckinListbyPoi(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt1, int paramInt2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=lbs.getStrangerCheckin");
    localStringBuffer.append("&uid=").append(paramString5);
    localStringBuffer.append("&lat=").append(paramString1);
    localStringBuffer.append("&lon=").append(paramString2);
    setLocationXY(localStringBuffer, paramString3, paramString4);
    localStringBuffer.append("&start=").append(paramInt1);
    localStringBuffer.append("&n=").append(paramInt2);
    localStringBuffer.append("&api_version=").append("3.9.9");
    localStringBuffer.append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeSubmitRepostTagRequest(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/repaste/tag_submit.php").append("?uid=").append(paramString5).append("&tagid=").append(paramString1).append("&urpid=").append(paramString3).append("&suid=").append(paramString4).append("&tag=").append(URLEncoder.encode(paramString2)).append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeUpdateLogoRequest()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/photo/upload_logo.php");
    return localStringBuffer.toString();
  }

  public String makeUpdateLogoRequest(String paramString1, String paramString2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/photo/upload_logo.php");
    localStringBuffer.append("&uid=").append(paramString2);
    return localStringBuffer.toString();
  }

  public String makeUpdateUserCardPrivacy(int paramInt, boolean paramBoolean)
  {
    int i = 1;
    StringBuffer localStringBuffer1 = new StringBuffer();
    localStringBuffer1.append("/capi/rest.php?method=User.updateCard");
    localStringBuffer1.append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    switch (paramInt)
    {
    default:
      return localStringBuffer1.toString();
    case 3:
      StringBuffer localStringBuffer4 = localStringBuffer1.append("&privacy_email=");
      if (paramBoolean);
      while (true)
      {
        localStringBuffer4.append(i);
        break;
        i = 0;
      }
    case 2:
      StringBuffer localStringBuffer3 = localStringBuffer1.append("&privacy_mobile=");
      if (paramBoolean);
      while (true)
      {
        localStringBuffer3.append(i);
        break;
        i = 0;
      }
    case 1:
    }
    StringBuffer localStringBuffer2 = localStringBuffer1.append("&privacy_phone=");
    if (paramBoolean);
    while (true)
    {
      localStringBuffer2.append(i);
      break;
      i = 0;
    }
  }

  public String makeUpdateUserCardRequest(HashMap<String, String> paramHashMap)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=User.updateCard");
    localStringBuffer.append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    Iterator localIterator;
    if (paramHashMap != null)
      localIterator = paramHashMap.entrySet().iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return localStringBuffer.toString();
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      localStringBuffer.append("&").append((String)localEntry.getKey()).append("=").append(URLEncoder.encode((String)localEntry.getValue()));
    }
  }

  public String makeUploadDiaryPictureRequest(String paramString1, String paramString2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/photo/getimg.php").append("?uid=").append(paramString2).append("&srcfilename=").append(URLEncoder.encode(paramString1)).append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeUploadPhotoRequest(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/photo/post_pc.php");
    return localStringBuffer.toString();
  }

  public String makeUploadScore(String paramString1, String paramString2)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("/capi/rest.php?method=WapgameTop.addWapgameScore");
    localStringBuilder.append("&url=").append(paramString2);
    localStringBuilder.append("&score=").append(paramString1);
    localStringBuilder.append("&uid=").append(User.getInstance().getUID());
    return localStringBuilder.toString();
  }

  public String makeUploadUserBehaviorRequest()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=system.writeLog");
    localStringBuffer.append("&api_version=").append("3.9.9");
    localStringBuffer.append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeUserHabitMultiUrl(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/home/client_stat.php");
    localStringBuffer.append("?uid=").append(paramString);
    localStringBuffer.append("&api_version=").append("3.9.9");
    localStringBuffer.append("&version=").append("android-3.9.9");
    localStringBuffer.append("&ismulti=1");
    return localStringBuffer.toString();
  }

  public String makeUserInfoRequest(boolean paramBoolean, String paramString1, String paramString2, String paramString3)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/friend/getuserinfos.php");
    localStringBuffer.append("?uid=");
    localStringBuffer.append(paramString1);
    localStringBuffer.append("&uids=");
    localStringBuffer.append(paramString2);
    if (!TextUtils.isEmpty(paramString3))
    {
      localStringBuffer.append("&ctime=");
      localStringBuffer.append(paramString3);
    }
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeVoteRequest(String paramString1, String paramString2, String paramString3)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/vote/submit.php").append("?&uid=").append(paramString3).append("&vid=").append(paramString1).append("&vote=").append(paramString2).append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String makeWapRanking(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("/capi/rest.php?method=WapgameTop.getWapgameUsersScore");
    localStringBuilder.append("&url=").append(paramString);
    localStringBuilder.append("&uid=").append(User.getInstance().getUID());
    return localStringBuilder.toString();
  }

  public String makeWriteFilmComment()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=film.postCommet");
    localStringBuffer.append("&api_version=");
    localStringBuffer.append("3.9.9");
    localStringBuffer.append("&version=");
    localStringBuffer.append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public ArrayList<String> putSystemMessageData(String paramString)
  {
    ArrayList localArrayList;
    if (TextUtils.isEmpty(paramString))
      localArrayList = null;
    while (true)
    {
      return localArrayList;
      localArrayList = new ArrayList();
      try
      {
        JSONArray localJSONArray = new JSONObject(paramString).getJSONArray("contents");
        for (int i = 0; i < localJSONArray.length(); i++)
          localArrayList.add(localJSONArray.getJSONObject(i).getString("content"));
      }
      catch (JSONException localJSONException)
      {
        KXLog.e("Updater", "putServerData", localJSONException);
      }
    }
    return null;
  }

  public String sendScoreUrl(String paramString1, String paramString2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=games.setScore").append("&score=").append(paramString1).append("&playtime=").append(paramString2).append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String setStarsPush(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=lucky.setpushForAndroid").append("&value=").append(paramString).append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String shareQZone()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=account.QzoneShare");
    return localStringBuffer.toString();
  }

  public String shareWeibo(String paramString1, String paramString2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?method=account.weiboShare").append("&accesstoken=").append(paramString1).append("&status=").append(paramString2).append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }

  public String uploadClientInfoUrl(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("/capi/rest.php?rt=json&method=stat.statClientLogin");
    localStringBuffer.append("&mac=").append(paramString1).append("&imei=").append(paramString2).append("&imsi=").append(paramString3).append("&station=").append(paramString4).append("&community=").append(paramString5).append("&mobile=").append(paramString6).append("&device=").append(paramString8).append("&language=").append(paramString8).append("&location=").append(paramString9).append("&ostype=").append(paramString10).append("&lat=").append(paramString11).append("&lon=").append(paramString12);
    localStringBuffer.append("&api_version=").append("3.9.9").append("&version=").append("android-3.9.9");
    return localStringBuffer.toString();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.network.Protocol
 * JD-Core Version:    0.6.0
 */