package com.kaixin001.item;

import com.kaixin001.util.ParseNewsInfoUtil;
import java.util.ArrayList;

public class CircleMsgItem
{
  public String circleName;
  public int circleUserRole;
  public ArrayList<CircleReplyContent> content_last;
  public Long ctime;
  public String detailInfo;
  public String fname_last;
  public String fuid_last;
  public String gid;
  public String logo120_last;
  public String logo50_last;
  public int quitSession;
  public String reserved;
  public ArrayList<CircleReplyContent> sContent;
  public String sfuid;
  public String slogo120;
  public String slogo50;
  public String sname;
  public String stid;
  public int talk_type;
  public String tid_last;
  public int totalCount;
  public String uid;

  public ArrayList<KXLinkInfo> makeTitleList(String paramString)
  {
    return ParseNewsInfoUtil.parseCommentAndReplyLinkString(paramString);
  }

  public static class CircleReplyContent
  {
    public String atFname = "";
    public String atUid = "";
    public String txt = "";
    public int type = 0;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.item.CircleMsgItem
 * JD-Core Version:    0.6.0
 */