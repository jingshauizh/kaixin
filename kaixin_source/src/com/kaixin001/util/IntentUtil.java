package com.kaixin001.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.LinearLayout;
import com.kaixin001.activity.FlashPlayerActivity;
import com.kaixin001.activity.GuideActivity;
import com.kaixin001.activity.LoginActivity;
import com.kaixin001.activity.MMPlayerActivity;
import com.kaixin001.activity.MainActivity;
import com.kaixin001.db.ConfigDBAdapter;
import com.kaixin001.fragment.AlbumViewFragment;
import com.kaixin001.fragment.BaseFragment;
import com.kaixin001.fragment.CheckInFragment;
import com.kaixin001.fragment.CircleAlbumViewFragment;
import com.kaixin001.fragment.CircleDetailsFragment;
import com.kaixin001.fragment.CircleMainFragment;
import com.kaixin001.fragment.HomeFragment;
import com.kaixin001.fragment.IFEditPhotoFragment;
import com.kaixin001.fragment.InputFragment;
import com.kaixin001.fragment.LbsActivityListFragment;
import com.kaixin001.fragment.NewsDetailFragment;
import com.kaixin001.fragment.PKFragment;
import com.kaixin001.fragment.PoiFragment;
import com.kaixin001.fragment.PoiListFragment;
import com.kaixin001.fragment.TopicGroupFragment;
import com.kaixin001.fragment.UploadPhotoFragment;
import com.kaixin001.fragment.ViewAlbumPhotoFragment;
import com.kaixin001.fragment.ViewCirclePhotoFragment;
import com.kaixin001.fragment.ViewPhotoFragment;
import com.kaixin001.fragment.VisitorHistroyFragment;
import com.kaixin001.fragment.VoiceRecordFragment;
import com.kaixin001.fragment.VoteDetailFragment;
import com.kaixin001.fragment.WebPageFragment;
import com.kaixin001.fragment.WriteCircleRecordFragment;
import com.kaixin001.item.CheckInItem;
import com.kaixin001.item.CheckInUser;
import com.kaixin001.item.DetailItem;
import com.kaixin001.item.NewsInfo;
import com.kaixin001.lbs.LocationService;
import com.kaixin001.model.AlbumPhotoModel;
import com.kaixin001.model.EventModel.EventData;
import com.kaixin001.model.KaixinUser;
import com.kaixin001.model.User;

public class IntentUtil
{
  private static Context mCtx;
  private static LinearLayout mMP4WaitPro = null;

  static
  {
    mCtx = null;
  }

  public static void addCommentActivity(Activity paramActivity, BaseFragment paramBaseFragment, String paramString1, String paramString2, String paramString3, String paramString4)
  {
    Intent localIntent = new Intent(paramActivity, InputFragment.class);
    Bundle localBundle = new Bundle();
    localBundle.putString("type", paramString1);
    localBundle.putString("ntypename", paramString2);
    localBundle.putString("id", paramString3);
    localBundle.putString("ouid", paramString4);
    localBundle.putInt("mode", 2);
    localIntent.putExtras(localBundle);
    AnimationUtil.startFragmentForResult(paramBaseFragment, localIntent, 2);
  }

  public static Bundle getHomeActivityIntent(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("fuid", paramString1);
    localBundle.putString("fname", paramString2);
    localBundle.putString("from", paramString4);
    if (paramString3 != null)
      localBundle.putString("flogo", paramString3);
    return localBundle;
  }

  public static void launchEditPhotoForResult(Context paramContext, BaseFragment paramBaseFragment, int paramInt, Bundle paramBundle)
  {
    if (!(paramContext instanceof Activity))
      throw new RuntimeException("You must use Activity context to lauch me!");
    Intent localIntent = new Intent(paramContext, IFEditPhotoFragment.class);
    localIntent.putExtras(paramBundle);
    AnimationUtil.startFragmentForResult(paramBaseFragment, localIntent, paramInt);
  }

  public static void refreshLeftMenu(Activity paramActivity)
  {
    ((MainActivity)paramActivity).refreshLeftMenuInfo();
  }

  public static void replyCircleDetail(Activity paramActivity, BaseFragment paramBaseFragment, String paramString1, String paramString2)
  {
    Intent localIntent = new Intent(paramActivity, InputFragment.class);
    Bundle localBundle = new Bundle();
    localBundle.putInt("mode", 10);
    localBundle.putString("gid", paramString1);
    localBundle.putString("stid", paramString2);
    localIntent.putExtras(localBundle);
    AnimationUtil.startFragmentForResult(paramBaseFragment, localIntent, 10);
  }

  public static void replyCircleTalk(Activity paramActivity, BaseFragment paramBaseFragment, String paramString1, String paramString2)
  {
    Intent localIntent = new Intent(paramActivity, InputFragment.class);
    Bundle localBundle = new Bundle();
    localBundle.putInt("mode", 9);
    localBundle.putString("gid", paramString1);
    localBundle.putString("stid", paramString2);
    localIntent.putExtras(localBundle);
    AnimationUtil.startFragmentForResult(paramBaseFragment, localIntent, 9);
  }

  public static void returnToDesktop(Activity paramActivity)
  {
    returnToDesktop(paramActivity, true);
  }

  public static void returnToDesktop(Activity paramActivity, boolean paramBoolean)
  {
    String str = ConfigDBAdapter.getConfig(paramActivity, User.getInstance().getUID(), "guided:android-3.9.9");
    try
    {
      boolean bool = TextUtils.isEmpty(str);
      i = 0;
      if (!bool)
      {
        int j = Integer.valueOf(str).intValue();
        i = j;
      }
      if ((i == 0) && (!User.getInstance().GetLookAround()) && (!LoginActivity.isSwitchAccountLogin))
      {
        showGuideActivity(paramActivity);
        if (paramBoolean)
          paramActivity.finish();
        return;
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        localException.printStackTrace();
        int i = 0;
        continue;
        Intent localIntent = new Intent(paramActivity, MainActivity.class);
        localIntent.setFlags(67108864);
        localIntent.putExtra("returnDesktop", "1");
        paramActivity.startActivity(localIntent);
      }
    }
  }

  public static void returnToLogin(Activity paramActivity)
  {
    KXLog.d("LaunchMode", paramActivity.getClass().toString() + " onCreate");
    Intent localIntent = new Intent(paramActivity, LoginActivity.class);
    localIntent.setFlags(67108864);
    paramActivity.startActivity(localIntent);
    paramActivity.finish();
  }

  public static void returnToLogin(Activity paramActivity, Bundle paramBundle, boolean paramBoolean)
  {
    KXLog.d("LaunchMode", paramActivity.getClass().toString() + " onCreate");
    Intent localIntent = new Intent(paramActivity, LoginActivity.class);
    if (paramBundle != null)
      localIntent.putExtras(paramBundle);
    localIntent.setFlags(67108864);
    paramActivity.startActivity(localIntent);
    if (paramBoolean)
      paramActivity.finish();
  }

  public static void returnToLogin(Activity paramActivity, boolean paramBoolean)
  {
    KXLog.d("LaunchMode", paramActivity.getClass().toString() + " onCreate");
    Intent localIntent = new Intent(paramActivity, LoginActivity.class);
    localIntent.setFlags(67108864);
    paramActivity.startActivity(localIntent);
    if (paramBoolean)
      paramActivity.finish();
  }

  public static Intent setWebPageIntent(Context paramContext, String paramString1, String paramString2, EventModel.EventData paramEventData, int paramInt)
  {
    Intent localIntent = new Intent(paramContext, WebPageFragment.class);
    localIntent.setFlags(536870912);
    if ((paramString1 != null) && (paramString1.startsWith("http://")))
      localIntent.putExtra("url", paramString1);
    while (true)
    {
      localIntent.putExtra("title", paramString2);
      localIntent.putExtra("event_activity", paramEventData);
      return localIntent;
      localIntent.putExtra("url", "http://" + paramString1);
    }
  }

  public static void showCheckInFragment(Activity paramActivity, BaseFragment paramBaseFragment, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, boolean paramBoolean)
  {
    showCheckInFragment(paramActivity, paramBaseFragment, paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, paramBoolean, null);
  }

  public static void showCheckInFragment(Activity paramActivity, BaseFragment paramBaseFragment, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, boolean paramBoolean, String paramString7)
  {
    Intent localIntent = new Intent(paramActivity, CheckInFragment.class);
    Bundle localBundle = new Bundle();
    localBundle.putString("poiid", paramString1);
    localBundle.putString("poiname", paramString2);
    localBundle.putString("poi_location", paramString3);
    localBundle.putString("lon", paramString4);
    localBundle.putString("lat", paramString5);
    localBundle.putString("pic_path", paramString6);
    if (!TextUtils.isEmpty(paramString7))
      localBundle.putString("default_word", paramString7);
    localBundle.putBoolean("poi_show_detail", paramBoolean);
    localIntent.putExtras(localBundle);
    AnimationUtil.startFragmentForResult(paramBaseFragment, localIntent, 12, 3);
  }

  public static void showCircleMainFragment(Activity paramActivity, BaseFragment paramBaseFragment, String paramString1, String paramString2, int paramInt)
  {
    Intent localIntent = new Intent(paramActivity, CircleMainFragment.class);
    Bundle localBundle = new Bundle();
    localBundle.putString("gid", paramString1);
    localBundle.putString("gname", paramString2);
    localBundle.putInt("type", paramInt);
    localIntent.putExtras(localBundle);
    paramBaseFragment.startFragmentForResultNew(localIntent, 410, 1, true);
  }

  public static void showCircleMembersFragment(BaseFragment paramBaseFragment, String paramString1, String paramString2, int paramInt)
  {
    Intent localIntent = new Intent(paramBaseFragment.getActivity(), CircleMainFragment.class);
    Bundle localBundle = new Bundle();
    localBundle.putString("gid", paramString1);
    localBundle.putString("gname", paramString2);
    localBundle.putInt("type", paramInt);
    localBundle.putInt("mode", 1);
    localIntent.putExtras(localBundle);
    paramBaseFragment.startFragmentForResultNew(localIntent, 0, 1, true);
  }

  public static void showGuideActivity(Activity paramActivity)
  {
    KXLog.d("LaunchMode", paramActivity.getClass().toString() + " onCreate");
    Intent localIntent = new Intent(paramActivity, GuideActivity.class);
    localIntent.setFlags(67108864);
    paramActivity.startActivity(localIntent);
    paramActivity.finish();
  }

  public static void showHomeFragment(BaseFragment paramBaseFragment, String paramString1, String paramString2)
  {
    showHomeFragment(paramBaseFragment, paramString1, paramString2, null, null);
  }

  public static void showHomeFragment(BaseFragment paramBaseFragment, String paramString1, String paramString2, String paramString3)
  {
    showHomeFragment(paramBaseFragment, paramString1, paramString2, paramString3, null);
  }

  public static void showHomeFragment(BaseFragment paramBaseFragment, String paramString1, String paramString2, String paramString3, String paramString4)
  {
    Intent localIntent = new Intent(paramBaseFragment.getActivity(), HomeFragment.class);
    localIntent.putExtras(getHomeActivityIntent(paramString1, paramString2, paramString3, paramString4));
    AnimationUtil.startFragment(paramBaseFragment, localIntent, 1);
  }

  public static void showLbsActivityListFragment(BaseFragment paramBaseFragment, String paramString1, String paramString2)
  {
    Intent localIntent = new Intent(paramBaseFragment.getActivity(), LbsActivityListFragment.class);
    Bundle localBundle = new Bundle();
    if (!TextUtils.isEmpty(paramString1))
      localBundle.putString("lon", paramString1);
    if (!TextUtils.isEmpty(paramString2))
      localBundle.putString("lat", paramString2);
    localIntent.putExtras(localBundle);
    AnimationUtil.startFragment(paramBaseFragment, localIntent, 1);
  }

  public static void showLbsCheckInCommentFragment(BaseFragment paramBaseFragment, CheckInItem paramCheckInItem)
  {
    String str1 = KXTextUtil.getLbsPoiText(paramCheckInItem.poiName, paramCheckInItem.poiId);
    String str2 = KXTextUtil.formatTimestamp(1000L * paramCheckInItem.ctime);
    showLbsCheckInCommentFragment(paramBaseFragment, paramCheckInItem.wid, paramCheckInItem.checkInUser.user.uid, paramCheckInItem.checkInUser.user.realname, paramCheckInItem.checkInUser.user.logo, "1", paramCheckInItem.content, str2, paramCheckInItem.thumbnail, paramCheckInItem.large, str1, paramCheckInItem.mSource, paramCheckInItem.mMapUrl);
  }

  public static void showLbsCheckInCommentFragment(BaseFragment paramBaseFragment, String paramString)
  {
    showLbsCheckInCommentFragment(paramBaseFragment, paramString, "", "", "", "", "", "", "", "", "", "", "");
  }

  public static void showLbsCheckInCommentFragment(BaseFragment paramBaseFragment, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12)
  {
    showLbsCheckInCommentFragment(paramBaseFragment, paramString1, paramString2, paramString3, paramString4, "0", paramString5, paramString6, paramString7, paramString8, paramString9, paramString10, paramString11, -1, -1, paramString12, null);
  }

  public static void showLbsCheckInCommentFragment(BaseFragment paramBaseFragment, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, int paramInt1, int paramInt2, String paramString13, NewsInfo paramNewsInfo)
  {
    Intent localIntent = new Intent(paramBaseFragment.getActivity(), NewsDetailFragment.class);
    Bundle localBundle = new Bundle();
    DetailItem localDetailItem = DetailItem.makeCheckInDetailItem("1192", paramString6, paramString2, paramString3, paramString4, paramString5, paramString1, paramString7, paramString8, paramString9, paramString10, paramString13, paramString11, paramString12, true, paramInt1, paramInt2);
    localDetailItem.setTagNewsInfo(paramNewsInfo);
    localBundle.putSerializable("data", localDetailItem);
    localIntent.putExtras(localBundle);
    AnimationUtil.startFragment(paramBaseFragment, localIntent, 1);
  }

  public static void showLbsPositionDetailFragment(BaseFragment paramBaseFragment, String paramString1, String paramString2, String paramString3, String paramString4)
  {
    showLbsPositionDetailFragment(paramBaseFragment, paramString1, paramString2, paramString3, paramString4, true);
  }

  public static void showLbsPositionDetailFragment(BaseFragment paramBaseFragment, String paramString1, String paramString2, String paramString3, String paramString4, boolean paramBoolean)
  {
    Intent localIntent = new Intent(paramBaseFragment.getActivity(), PoiFragment.class);
    localIntent.putExtra("poi_id", paramString1);
    localIntent.putExtra("poi_name", paramString2);
    localIntent.putExtra("poi_location", paramString3);
    try
    {
      boolean bool2 = TextUtils.isEmpty(paramString4);
      if (bool2)
      {
        bool1 = true;
        localIntent.putExtra("poi_nearby", bool1);
        AnimationUtil.startFragment(paramBaseFragment, localIntent, 1, paramBoolean);
        return;
      }
      double d = Double.parseDouble(paramString4);
      if (d < 1000.0D);
      for (bool1 = true; ; bool1 = false)
        break;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      while (true)
        boolean bool1 = true;
    }
  }

  public static void showMyHomeFragment(BaseFragment paramBaseFragment)
  {
    showHomeFragment(paramBaseFragment, User.getInstance().getUID(), User.getInstance().getRealName(), User.getInstance().getLogo());
  }

  public static void showPkFragment(BaseFragment paramBaseFragment, String paramString)
  {
    if (!(paramBaseFragment instanceof PKFragment))
    {
      Intent localIntent = new Intent(paramBaseFragment.getActivity(), PKFragment.class);
      localIntent.putExtra("pkid", paramString);
      AnimationUtil.startFragment(paramBaseFragment, localIntent, 1);
    }
  }

  public static void showPoiListFragment(BaseFragment paramBaseFragment, Bundle paramBundle)
  {
    Intent localIntent = new Intent(paramBaseFragment.getActivity(), PoiListFragment.class);
    if (paramBundle == null)
      paramBundle = new Bundle();
    if ((paramBundle.getString("lon") == null) || (paramBundle.getString("lat") == null))
    {
      Location localLocation = LocationService.getLocationService().lastNoticedLocation;
      if (LocationService.getLocationService().isLocationValid(localLocation))
      {
        paramBundle.putString("lon", String.valueOf(localLocation.getLongitude()));
        paramBundle.putString("lat", String.valueOf(localLocation.getLatitude()));
      }
    }
    localIntent.putExtras(paramBundle);
    AnimationUtil.startFragment(paramBaseFragment, localIntent, 3);
  }

  public static void showPoiListFragment(BaseFragment paramBaseFragment, String paramString1, String paramString2)
  {
    Intent localIntent = new Intent(paramBaseFragment.getActivity(), PoiListFragment.class);
    Bundle localBundle = new Bundle();
    if (!TextUtils.isEmpty(paramString1))
      localBundle.putString("lon", paramString1);
    if (!TextUtils.isEmpty(paramString2))
      localBundle.putString("lat", paramString2);
    localIntent.putExtras(localBundle);
    AnimationUtil.startFragment(paramBaseFragment, localIntent, 1);
  }

  public static void showTopicGroupActivity(BaseFragment paramBaseFragment, String paramString)
  {
    if (!(paramBaseFragment instanceof TopicGroupFragment))
    {
      Intent localIntent = new Intent(paramBaseFragment.getActivity(), TopicGroupFragment.class);
      localIntent.putExtra("search", paramString);
      AnimationUtil.startFragment(paramBaseFragment, localIntent, 1);
    }
  }

  public static void showUploadPhotoFragment(BaseFragment paramBaseFragment, Bundle paramBundle)
  {
    Intent localIntent = new Intent(paramBaseFragment.getActivity(), UploadPhotoFragment.class);
    localIntent.putExtras(paramBundle);
    AnimationUtil.startFragment(paramBaseFragment, localIntent, 1);
  }

  public static void showVideo(Context paramContext, String paramString1, String paramString2, String paramString3)
  {
    if ("-201".equals(paramString2))
    {
      Intent localIntent1 = new Intent(paramContext, FlashPlayerActivity.class);
      localIntent1.addFlags(268435456);
      Bundle localBundle1 = new Bundle();
      if (paramString3 == null)
        paramString3 = "视频";
      localBundle1.putString("title", paramString3);
      localBundle1.putString("url", paramString1);
      localIntent1.putExtras(localBundle1);
      paramContext.startActivity(localIntent1);
    }
    do
    {
      return;
      if (!"-202".equals(paramString2))
        continue;
      Intent localIntent2 = new Intent(paramContext, FlashPlayerActivity.class);
      localIntent2.addFlags(268435456);
      Bundle localBundle2 = new Bundle();
      if (paramString3 == null)
        paramString3 = "视频";
      localBundle2.putString("title", paramString3);
      localBundle2.putString("url", paramString1);
      localIntent2.putExtras(localBundle2);
      paramContext.startActivity(localIntent2);
      return;
    }
    while (!"-204".equals(paramString2));
    Intent localIntent3 = new Intent(paramContext, MMPlayerActivity.class);
    localIntent3.addFlags(268435456);
    Bundle localBundle3 = new Bundle();
    localBundle3.putString("MMSOURCE", paramString1);
    localIntent3.putExtras(localBundle3);
    paramContext.startActivity(localIntent3);
  }

  public static void showVideoPage(Context paramContext, String paramString1, String paramString2, String paramString3)
  {
    mCtx = paramContext;
    if (paramString1.contains("tudou.com"))
    {
      GetMP4TypeOfVideoTask localGetMP4TypeOfVideoTask = new GetMP4TypeOfVideoTask();
      localGetMP4TypeOfVideoTask.setContext(mCtx);
      localGetMP4TypeOfVideoTask.execute(new String[] { paramString1, paramString2, paramString3 });
      if (mMP4WaitPro != null)
        mMP4WaitPro.setVisibility(0);
      return;
    }
    showVideo(paramContext, paramString1, paramString2, paramString3);
  }

  public static void showViewAlbum(Activity paramActivity, BaseFragment paramBaseFragment, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, boolean paramBoolean)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("albumId", paramString1);
    localBundle.putString("title", paramString2);
    localBundle.putString("fuid", paramString3);
    localBundle.putString("fname", paramString4);
    localBundle.putString("pwd", paramString6);
    localBundle.putString("pic_count", paramString5);
    localBundle.putBoolean("isFromViewPhoto", paramBoolean);
    if (paramBoolean)
    {
      Intent localIntent1 = new Intent(paramActivity, AlbumViewFragment.class);
      localIntent1.putExtras(localBundle);
      AnimationUtil.startFragmentForResult(paramBaseFragment, localIntent1, 11);
      return;
    }
    if (AlbumPhotoModel.getInstance() != null)
      AlbumPhotoModel.getInstance().clear();
    Intent localIntent2 = new Intent(paramActivity, AlbumViewFragment.class);
    localIntent2.putExtras(localBundle);
    AnimationUtil.startFragment(paramBaseFragment, localIntent2, 1);
  }

  public static void showViewAlbumFromViewPhoto(Activity paramActivity, BaseFragment paramBaseFragment, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6)
  {
    showViewAlbum(paramActivity, paramBaseFragment, paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, true);
  }

  public static void showViewAlbumNotFromViewPhoto(Activity paramActivity, BaseFragment paramBaseFragment, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6)
  {
    showViewAlbum(paramActivity, paramBaseFragment, paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, false);
  }

  public static void showViewPhotoActivity(Activity paramActivity, BaseFragment paramBaseFragment, int paramInt1, String paramString1, String paramString2, String paramString3, int paramInt2, String paramString4, boolean paramBoolean)
  {
    Bundle localBundle = new Bundle();
    localBundle.putInt("imgIndex", paramInt1 - 1);
    localBundle.putString("fuid", paramString1);
    localBundle.putString("title", paramString2);
    localBundle.putString("albumId", paramString3);
    if (!TextUtils.isEmpty(paramString4))
      localBundle.putString("password", paramString4);
    localBundle.putInt("albumType", paramInt2);
    showViewPhotoActivity(paramActivity, paramBaseFragment, localBundle, paramBoolean);
  }

  public static void showViewPhotoActivity(Activity paramActivity, BaseFragment paramBaseFragment, Bundle paramBundle, boolean paramBoolean)
  {
    paramBundle.putBoolean("isFromViewAlbum", paramBoolean);
    if (paramBundle.getInt("albumType", 2) == 2);
    for (Intent localIntent = new Intent(paramActivity, ViewAlbumPhotoFragment.class); ; localIntent = new Intent(paramActivity, ViewPhotoFragment.class))
    {
      localIntent.putExtras(paramBundle);
      if (!paramBoolean)
        break;
      AnimationUtil.startFragmentForResult(paramBaseFragment, localIntent, 10, 1);
      return;
    }
    AnimationUtil.startFragment(paramBaseFragment, localIntent, 1);
  }

  public static void showViewPhotoActivity4Circle(Activity paramActivity, BaseFragment paramBaseFragment, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, boolean paramBoolean)
  {
    Bundle localBundle = new Bundle();
    localBundle.putInt("imgIndex", -1 + Integer.parseInt(paramString1));
    localBundle.putString("title", paramString2);
    localBundle.putString("albumId", paramString3);
    localBundle.putString("gid", paramString4);
    localBundle.putString("gname", paramString5);
    localBundle.putInt("albumType", 3);
    showViewPhotoActivity(paramActivity, paramBaseFragment, localBundle, paramBoolean);
  }

  public static void showViewPhotoActivity4Poi(Activity paramActivity, BaseFragment paramBaseFragment, int paramInt, String paramString1, String paramString2, boolean paramBoolean)
  {
    Bundle localBundle = new Bundle();
    localBundle.putInt("imgIndex", paramInt);
    localBundle.putString("albumId", paramString1);
    localBundle.putInt("albumType", 4);
    localBundle.putString("poiName", paramString2);
    showViewPhotoActivity(paramActivity, paramBaseFragment, localBundle, paramBoolean);
  }

  public static void showViewPhotoActivityFromAlbum(Activity paramActivity, BaseFragment paramBaseFragment, int paramInt1, String paramString1, String paramString2, String paramString3, int paramInt2, String paramString4)
  {
    showViewPhotoActivity(paramActivity, paramBaseFragment, paramInt1, paramString1, paramString2, paramString3, paramInt2, paramString4, true);
  }

  public static void showViewPhotoActivityNotFromAlbum(Activity paramActivity, BaseFragment paramBaseFragment, int paramInt1, String paramString1, String paramString2, String paramString3, int paramInt2, String paramString4)
  {
    showViewPhotoActivity(paramActivity, paramBaseFragment, paramInt1, paramString1, paramString2, paramString3, paramInt2, paramString4, false);
  }

  public static void showViewPhotoForActivity(BaseFragment paramBaseFragment, Bundle paramBundle)
  {
    Intent localIntent = new Intent(paramBaseFragment.getActivity(), ViewAlbumPhotoFragment.class);
    localIntent.putExtras(paramBundle);
    AnimationUtil.startFragment(paramBaseFragment, localIntent, 1);
  }

  public static void showVisitorHistroy(BaseFragment paramBaseFragment, String paramString1, String paramString2, int paramInt)
  {
    Intent localIntent = new Intent(paramBaseFragment.getActivity(), VisitorHistroyFragment.class);
    Bundle localBundle = new Bundle();
    localBundle.putString("pid", paramString1);
    localBundle.putString("ouid", paramString2);
    localBundle.putInt("visitor_count", paramInt);
    localIntent.putExtras(localBundle);
    paramBaseFragment.startFragment(localIntent, true, 1);
  }

  public static void showVoiceRecordActivityForResult(Activity paramActivity, BaseFragment paramBaseFragment, Bundle paramBundle, int paramInt)
  {
    Intent localIntent = new Intent(paramActivity, VoiceRecordFragment.class);
    if (paramBundle != null)
      localIntent.putExtras(paramBundle);
    AnimationUtil.startFragmentForResult(paramBaseFragment, localIntent, paramInt, 1);
  }

  public static void showVoiceRecordFragment(BaseFragment paramBaseFragment, Bundle paramBundle)
  {
    Intent localIntent = new Intent(paramBaseFragment.getActivity(), VoiceRecordFragment.class);
    if (paramBundle != null)
      localIntent.putExtras(paramBundle);
    AnimationUtil.startFragment(paramBaseFragment, localIntent, 3);
  }

  public static void showVoteDetailActivity(Context paramContext, String paramString)
  {
    Intent localIntent = new Intent(paramContext, VoteDetailFragment.class);
    Bundle localBundle = new Bundle();
    localBundle.putString("vid", paramString);
    localIntent.putExtras(localBundle);
    paramContext.startActivity(localIntent);
  }

  public static void showWebPage(Context paramContext, BaseFragment paramBaseFragment, String paramString1, String paramString2)
  {
    showWebPage(paramContext, paramBaseFragment, paramString1, paramString2, null);
  }

  public static void showWebPage(Context paramContext, BaseFragment paramBaseFragment, String paramString1, String paramString2, EventModel.EventData paramEventData)
  {
    showWebPage(paramContext, paramBaseFragment, paramString1, paramString2, paramEventData, -1);
  }

  public static void showWebPage(Context paramContext, BaseFragment paramBaseFragment, String paramString1, String paramString2, EventModel.EventData paramEventData, int paramInt)
  {
    Intent localIntent = setWebPageIntent(paramContext, paramString1, paramString2, paramEventData, paramInt);
    if (paramInt >= 0)
    {
      AnimationUtil.startFragmentForResult(paramBaseFragment, localIntent, paramInt);
      return;
    }
    if ((paramString1.contains("3g.youku.com")) && (paramString1.contains("video.jsp")))
    {
      AnimationUtil.startFragment(paramBaseFragment, new Intent("android.intent.action.VIEW", Uri.parse(paramString1)), 1);
      return;
    }
    AnimationUtil.startFragment(paramBaseFragment, localIntent, 1);
  }

  public static void showWriteCircleRecordActivity(Context paramContext, String paramString)
  {
    Intent localIntent = new Intent(paramContext, WriteCircleRecordFragment.class);
    Bundle localBundle = new Bundle();
    localBundle.putString("gid", paramString);
    localIntent.putExtras(localBundle);
    paramContext.startActivity(localIntent);
  }

  public static void startActivity(Context paramContext, Bundle paramBundle, Class<?> paramClass)
  {
    Intent localIntent = new Intent(paramContext, paramClass);
    localIntent.putExtras(paramBundle);
    paramContext.startActivity(localIntent);
  }

  public static void startActivityForResult(Activity paramActivity, Bundle paramBundle, Class<?> paramClass, int paramInt)
  {
    Intent localIntent = new Intent(paramActivity, paramClass);
    localIntent.putExtras(paramBundle);
    paramActivity.startActivityForResult(localIntent, paramInt);
  }

  public static void viewCircleAlbum(BaseFragment paramBaseFragment, String paramString1, String paramString2)
  {
    Intent localIntent = new Intent(paramBaseFragment.getActivity(), CircleAlbumViewFragment.class);
    Bundle localBundle = new Bundle();
    localBundle.putString("gid", paramString1);
    localBundle.putString("albumTitle", paramString2);
    localIntent.putExtras(localBundle);
    paramBaseFragment.startFragment(localIntent, true, 1);
  }

  public static void viewCirclePhoto(BaseFragment paramBaseFragment, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    Intent localIntent = new Intent(paramBaseFragment.getActivity(), ViewCirclePhotoFragment.class);
    Bundle localBundle = new Bundle();
    localBundle.putString("pid", paramString1);
    localBundle.putString("gid", paramString2);
    localBundle.putString("aid", paramString5);
    localBundle.putString("smallPhoto", paramString3);
    localBundle.putString("largePhoto", paramString4);
    localIntent.putExtras(localBundle);
    paramBaseFragment.startFragment(localIntent, true, 1);
  }

  public static void viewCircleTalkRepliesDetail(Activity paramActivity, BaseFragment paramBaseFragment, String paramString1, String paramString2, String paramString3)
  {
    Intent localIntent = new Intent(paramActivity, CircleDetailsFragment.class);
    Bundle localBundle = new Bundle();
    localBundle.putString("tid", paramString1);
    localBundle.putString("gid", paramString2);
    localBundle.putString("gname", paramString3);
    localIntent.putExtras(localBundle);
    AnimationUtil.startFragmentForResult(paramBaseFragment, localIntent, 9);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.util.IntentUtil
 * JD-Core Version:    0.6.0
 */