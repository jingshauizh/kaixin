package com.kaixin001.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.StringUtil;
import java.io.ByteArrayOutputStream;

public class ShareFragment extends BaseFragment
{
  private static final String LOGTAG = "ShareActivity";
  public static final int RSHARE_ERROR_ALLOWID = 8;
  public static final int RSHARE_ERROR_APPID = 7;
  public static final int RSHARE_ERROR_LINK = 11;
  public static final int RSHARE_ERROR_PIC = 12;
  public static final int RSHARE_ERROR_RPARA = 6;
  public static final int RSHARE_ERROR_SIGN = 1;
  public static final int RSHARE_ERROR_SITEID = 14;
  public static final int RSHARE_ERROR_SITEID_TEST = 15;
  public static final int RSHARE_ERROR_TEXTTYPE = 9;
  public static final int RSHARE_ERROR_TIME = 5;
  public static final int RSHARE_ERROR_TITLE = 10;
  public static final int RSHARE_ERROR_TYPE = 4;
  public static final int RSHARE_ERROR_V = 3;
  public static final String TAG_ACTION = "action";
  public static final String TAG_BETA = "beta";
  public static final String TAG_FEED_KEY = "feedkey";
  public static final String TAG_LINK = "link";
  public static final String TAG_PIC_URL = "pic";
  public static final String TAG_PRODUCT_ID = "iid";
  public static final String TAG_SHARE_ACTION = "shareaction";
  public static final String TAG_SITE_ID = "id";
  public static final String TAG_SITE_NAME = "sitename";
  public static final String TAG_TEXT_TYPE = "texttype";
  public static final String TAG_TIME = "time";
  public static final String TAG_TITLE = "title";
  public static final String TAG_TYPE = "type";
  public static final String TAG_VALUE = "value";
  public static final String TAG_VALUE_NAME = "valuename";
  public static final String TAG_VALUE_UNIT = "valueunit";
  public static final String TAG_VERSION = "v";
  public static final String TAG_WAP_LINK = "waplink";
  private ImageView btnLeft;
  private ImageView btnRight;
  private EditText edtWords;
  private ShareParam mShareParam = new ShareParam(null);
  private TextView mTxtContent;
  private TextView mTxtPrompt;
  private TextView mTxtSiteLink;
  private TextView tvTitle;

  private void initTextViewData()
  {
    this.mTxtContent = ((TextView)findViewById(2131363622));
    String str1 = this.mShareParam.getParam("sitename");
    if (TextUtils.isEmpty(str1))
      str1 = "网站";
    String str2 = "我在" + str1 + "上";
    String str3 = this.mShareParam.getParam("action");
    if (TextUtils.isEmpty(str3))
      str3 = "看到了";
    String str4 = str2 + str3;
    String str5 = this.mShareParam.getParam("title");
    String str6 = str4 + str5;
    String str7 = this.mShareParam.getParam("shareaction");
    String str8 = str6 + "，" + str7;
    this.mTxtContent.setText(str8);
    this.mTxtSiteLink = ((TextView)findViewById(2131363623));
    String str9 = "去" + str1 + "看看";
    this.mTxtSiteLink.setText(str9);
    this.mTxtSiteLink.setClickable(true);
    this.mTxtSiteLink.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        String str = ShareFragment.this.mShareParam.getParam("link");
        if (TextUtils.isEmpty(str))
          str = ShareFragment.this.mShareParam.getParam("waplink");
        if (str == null)
          str = "";
        Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse(str));
        ShareFragment.this.startActivity(localIntent);
      }
    });
    this.mTxtPrompt = ((TextView)findViewById(2131363624));
    String str10 = User.getInstance().getRealName() + "，对好友说点什么吧...";
    this.mTxtPrompt.setText(str10);
  }

  private void test()
  {
    ShareParam localShareParam = new ShareParam(null);
    long l = System.currentTimeMillis() / 1000L;
    localShareParam.setParam("pic", "http://img02.taobaocdn.com/bao/uploaded/i2/T14_NBXltIXXcxx4AZ_032522.jpg_310x310.jpg").setParam("iid", "4459711228").setParam("waplink", "http://item.taobao.com/item.htm?id=4459711228").setParam("title", "WII 韩版主机双人套装").setParam("value", "1799元").setParam("texttype", "459").setParam("id", "100000526").setParam("feedkey", "235c19467df27c90b82f2ea106b2504f").setParam("time", String.valueOf(l)).setParam("beta", "0");
    String str = localShareParam.makeParamString();
    HttpProxy localHttpProxy = new HttpProxy(getActivity());
    try
    {
      localHttpProxy.httpGet(str, null, null);
      return;
    }
    catch (Exception localException)
    {
      KXLog.e("ShareActivity", "test error", localException);
    }
  }

  protected boolean initParamData()
  {
    Bundle localBundle = getArguments();
    if (localBundle == null)
      return false;
    String str1 = localBundle.getString("id");
    if (!TextUtils.isEmpty(str1))
      this.mShareParam.setParam("id", str1);
    String str2 = localBundle.getString("v");
    if (!TextUtils.isEmpty(str2))
      this.mShareParam.setParam("v", str2);
    String str3 = localBundle.getString("beta");
    if (!TextUtils.isEmpty(str3))
      this.mShareParam.setParam("beta", str3);
    String str4 = localBundle.getString("type");
    if (!TextUtils.isEmpty(str4))
      this.mShareParam.setParam("type", str4);
    String str5 = localBundle.getString("texttype");
    if (!TextUtils.isEmpty(str5))
      this.mShareParam.setParam("texttype", str5);
    String str6 = localBundle.getString("feedkey");
    if (!TextUtils.isEmpty(str6))
      this.mShareParam.setParam("feedkey", str6);
    String str7 = localBundle.getString("time");
    if (!TextUtils.isEmpty(str7))
      this.mShareParam.setParam("time", str7);
    String str8 = localBundle.getString("iid");
    if (!TextUtils.isEmpty(str8))
      this.mShareParam.setParam("iid", str8);
    String str9 = localBundle.getString("title");
    if (!TextUtils.isEmpty(str9))
      this.mShareParam.setParam("title", str9);
    String str10 = localBundle.getString("pic");
    if (!TextUtils.isEmpty(str10))
      this.mShareParam.setParam("pic", str10);
    String str11 = localBundle.getString("link");
    if (!TextUtils.isEmpty(str11))
      this.mShareParam.setParam("link", str11);
    String str12 = localBundle.getString("waplink");
    if (!TextUtils.isEmpty(str12))
      this.mShareParam.setParam("waplink", str12);
    String str13 = localBundle.getString("value");
    if (!TextUtils.isEmpty(str13))
      this.mShareParam.setParam("value", str13);
    String str14 = localBundle.getString("action");
    if (!TextUtils.isEmpty(str14))
      this.mShareParam.setParam("action", str14);
    String str15 = localBundle.getString("sitename");
    if (!TextUtils.isEmpty(str15))
      this.mShareParam.setParam("sitename", str15);
    String str16 = localBundle.getString("shareaction");
    if (!TextUtils.isEmpty(str16))
      this.mShareParam.setParam("shareaction", str16);
    String str17 = localBundle.getString("valuename");
    if (!TextUtils.isEmpty(str17))
      this.mShareParam.setParam("valuename", str17);
    String str18 = localBundle.getString("valueunit");
    if (!TextUtils.isEmpty(str18))
      this.mShareParam.setParam("valueunit", str18);
    return true;
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903334, paramViewGroup, false);
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    setTitleBar();
    if (!initParamData())
      Toast.makeText(getActivity(), 2131427857, 0).show();
    initTextViewData();
    this.edtWords = ((EditText)findViewById(2131363625));
    this.edtWords.clearFocus();
  }

  protected void setTitleBar()
  {
    this.btnLeft = ((ImageView)findViewById(2131362914));
    this.btnLeft.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        ShareFragment.this.finish();
      }
    });
    this.tvTitle = ((TextView)findViewById(2131362920));
    this.tvTitle.setText(2131427821);
    this.tvTitle.setVisibility(0);
    ((ImageView)findViewById(2131362919)).setVisibility(8);
    this.btnRight = ((ImageView)findViewById(2131362928));
    this.btnRight.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        ShareFragment.this.test();
      }
    });
    this.btnRight.setImageResource(2130838153);
  }

  public static class Base64
  {
    private static byte[] base64DecodeChars;
    private static final char[] base64EncodeChars = { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47 };

    static
    {
      byte[] arrayOfByte = new byte[''];
      arrayOfByte[0] = -1;
      arrayOfByte[1] = -1;
      arrayOfByte[2] = -1;
      arrayOfByte[3] = -1;
      arrayOfByte[4] = -1;
      arrayOfByte[5] = -1;
      arrayOfByte[6] = -1;
      arrayOfByte[7] = -1;
      arrayOfByte[8] = -1;
      arrayOfByte[9] = -1;
      arrayOfByte[10] = -1;
      arrayOfByte[11] = -1;
      arrayOfByte[12] = -1;
      arrayOfByte[13] = -1;
      arrayOfByte[14] = -1;
      arrayOfByte[15] = -1;
      arrayOfByte[16] = -1;
      arrayOfByte[17] = -1;
      arrayOfByte[18] = -1;
      arrayOfByte[19] = -1;
      arrayOfByte[20] = -1;
      arrayOfByte[21] = -1;
      arrayOfByte[22] = -1;
      arrayOfByte[23] = -1;
      arrayOfByte[24] = -1;
      arrayOfByte[25] = -1;
      arrayOfByte[26] = -1;
      arrayOfByte[27] = -1;
      arrayOfByte[28] = -1;
      arrayOfByte[29] = -1;
      arrayOfByte[30] = -1;
      arrayOfByte[31] = -1;
      arrayOfByte[32] = -1;
      arrayOfByte[33] = -1;
      arrayOfByte[34] = -1;
      arrayOfByte[35] = -1;
      arrayOfByte[36] = -1;
      arrayOfByte[37] = -1;
      arrayOfByte[38] = -1;
      arrayOfByte[39] = -1;
      arrayOfByte[40] = -1;
      arrayOfByte[41] = -1;
      arrayOfByte[42] = -1;
      arrayOfByte[43] = 62;
      arrayOfByte[44] = -1;
      arrayOfByte[45] = -1;
      arrayOfByte[46] = -1;
      arrayOfByte[47] = 63;
      arrayOfByte[48] = 52;
      arrayOfByte[49] = 53;
      arrayOfByte[50] = 54;
      arrayOfByte[51] = 55;
      arrayOfByte[52] = 56;
      arrayOfByte[53] = 57;
      arrayOfByte[54] = 58;
      arrayOfByte[55] = 59;
      arrayOfByte[56] = 60;
      arrayOfByte[57] = 61;
      arrayOfByte[58] = -1;
      arrayOfByte[59] = -1;
      arrayOfByte[60] = -1;
      arrayOfByte[61] = -1;
      arrayOfByte[62] = -1;
      arrayOfByte[63] = -1;
      arrayOfByte[64] = -1;
      arrayOfByte[66] = 1;
      arrayOfByte[67] = 2;
      arrayOfByte[68] = 3;
      arrayOfByte[69] = 4;
      arrayOfByte[70] = 5;
      arrayOfByte[71] = 6;
      arrayOfByte[72] = 7;
      arrayOfByte[73] = 8;
      arrayOfByte[74] = 9;
      arrayOfByte[75] = 10;
      arrayOfByte[76] = 11;
      arrayOfByte[77] = 12;
      arrayOfByte[78] = 13;
      arrayOfByte[79] = 14;
      arrayOfByte[80] = 15;
      arrayOfByte[81] = 16;
      arrayOfByte[82] = 17;
      arrayOfByte[83] = 18;
      arrayOfByte[84] = 19;
      arrayOfByte[85] = 20;
      arrayOfByte[86] = 21;
      arrayOfByte[87] = 22;
      arrayOfByte[88] = 23;
      arrayOfByte[89] = 24;
      arrayOfByte[90] = 25;
      arrayOfByte[91] = -1;
      arrayOfByte[92] = -1;
      arrayOfByte[93] = -1;
      arrayOfByte[94] = -1;
      arrayOfByte[95] = -1;
      arrayOfByte[96] = -1;
      arrayOfByte[97] = 26;
      arrayOfByte[98] = 27;
      arrayOfByte[99] = 28;
      arrayOfByte[100] = 29;
      arrayOfByte[101] = 30;
      arrayOfByte[102] = 31;
      arrayOfByte[103] = 32;
      arrayOfByte[104] = 33;
      arrayOfByte[105] = 34;
      arrayOfByte[106] = 35;
      arrayOfByte[107] = 36;
      arrayOfByte[108] = 37;
      arrayOfByte[109] = 38;
      arrayOfByte[110] = 39;
      arrayOfByte[111] = 40;
      arrayOfByte[112] = 41;
      arrayOfByte[113] = 42;
      arrayOfByte[114] = 43;
      arrayOfByte[115] = 44;
      arrayOfByte[116] = 45;
      arrayOfByte[117] = 46;
      arrayOfByte[118] = 47;
      arrayOfByte[119] = 48;
      arrayOfByte[120] = 49;
      arrayOfByte[121] = 50;
      arrayOfByte[122] = 51;
      arrayOfByte[123] = -1;
      arrayOfByte[124] = -1;
      arrayOfByte[125] = -1;
      arrayOfByte[126] = -1;
      arrayOfByte[127] = -1;
      base64DecodeChars = arrayOfByte;
    }

    public static byte[] decode(String paramString)
    {
      byte[] arrayOfByte1 = paramString.getBytes();
      int i = arrayOfByte1.length;
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(i);
      int k;
      for (int j = 0; ; j = k)
      {
        if (j >= i);
        int i4;
        int i7;
        do
        {
          int i1;
          do
          {
            int m;
            do
            {
              do
              {
                return localByteArrayOutputStream.toByteArray();
                do
                {
                  j = k;
                  byte[] arrayOfByte2 = base64DecodeChars;
                  k = j + 1;
                  m = arrayOfByte2[arrayOfByte1[j]];
                }
                while ((k < i) && (m == -1));
              }
              while (m == -1);
              do
              {
                int n = k;
                byte[] arrayOfByte3 = base64DecodeChars;
                k = n + 1;
                i1 = arrayOfByte3[arrayOfByte1[n]];
              }
              while ((k < i) && (i1 == -1));
            }
            while (i1 == -1);
            localByteArrayOutputStream.write(m << 2 | (i1 & 0x30) >>> 4);
            do
            {
              int i2 = k;
              k = i2 + 1;
              int i3 = arrayOfByte1[i2];
              if (i3 == 61)
              {
                byte[] arrayOfByte5 = localByteArrayOutputStream.toByteArray();
                return arrayOfByte5;
              }
              i4 = base64DecodeChars[i3];
            }
            while ((k < i) && (i4 == -1));
          }
          while (i4 == -1);
          localByteArrayOutputStream.write((i1 & 0xF) << 4 | (i4 & 0x3C) >>> 2);
          do
          {
            int i5 = k;
            k = i5 + 1;
            int i6 = arrayOfByte1[i5];
            if (i6 == 61)
            {
              byte[] arrayOfByte4 = localByteArrayOutputStream.toByteArray();
              return arrayOfByte4;
            }
            i7 = base64DecodeChars[i6];
          }
          while ((k < i) && (i7 == -1));
        }
        while (i7 == -1);
        localByteArrayOutputStream.write(i7 | (i4 & 0x3) << 6);
      }
    }

    public static String encode(byte[] paramArrayOfByte)
    {
      StringBuffer localStringBuffer = new StringBuffer();
      int i = paramArrayOfByte.length;
      int i2;
      for (int j = 0; ; j = i2)
      {
        if (j >= i);
        int m;
        int n;
        int i1;
        while (true)
        {
          return localStringBuffer.toString();
          int k = j + 1;
          m = 0xFF & paramArrayOfByte[j];
          if (k == i)
          {
            localStringBuffer.append(base64EncodeChars[(m >>> 2)]);
            localStringBuffer.append(base64EncodeChars[((m & 0x3) << 4)]);
            localStringBuffer.append("==");
            continue;
          }
          n = k + 1;
          i1 = 0xFF & paramArrayOfByte[k];
          if (n != i)
            break;
          localStringBuffer.append(base64EncodeChars[(m >>> 2)]);
          localStringBuffer.append(base64EncodeChars[((m & 0x3) << 4 | (i1 & 0xF0) >>> 4)]);
          localStringBuffer.append(base64EncodeChars[((i1 & 0xF) << 2)]);
          localStringBuffer.append("=");
        }
        i2 = n + 1;
        int i3 = 0xFF & paramArrayOfByte[n];
        localStringBuffer.append(base64EncodeChars[(m >>> 2)]);
        localStringBuffer.append(base64EncodeChars[((m & 0x3) << 4 | (i1 & 0xF0) >>> 4)]);
        localStringBuffer.append(base64EncodeChars[((i1 & 0xF) << 2 | (i3 & 0xC0) >>> 6)]);
        localStringBuffer.append(base64EncodeChars[(i3 & 0x3F)]);
      }
    }
  }

  private static class ShareParam
  {
    private String mAction = "";
    private String mBeta = "1";
    private String mFeedKey = "";
    private String mLink = "";
    private String mPicURL = "";
    private String mProductID = "";
    private String mShareAction = "";
    private String mSiteID = "";
    private String mSiteName = "";
    private String mTextType = "";
    private String mTime;
    private String mTitle = "";
    private String mType = "1";
    private String mValue = "";
    private String mValueName = "";
    private String mValueUnit = "";
    private String mVersion = "1";
    private String mWapLink = "";

    public String getParam(String paramString)
    {
      if ("id".equals(paramString))
        return this.mSiteID;
      if ("v".equals(paramString))
        return this.mVersion;
      if ("beta".equals(paramString))
        return this.mBeta;
      if ("type".equals(paramString))
        return this.mType;
      if ("texttype".equals(paramString))
        return this.mTextType;
      if ("feedkey".equals(paramString))
        return this.mFeedKey;
      if ("time".equals(paramString))
        return this.mTime;
      if ("iid".equals(paramString))
        return this.mProductID;
      if ("title".equals(paramString))
        return this.mTitle;
      if ("pic".equals(paramString))
        return this.mPicURL;
      if ("link".equals(paramString))
        return this.mLink;
      if ("waplink".equals(paramString))
        return this.mWapLink;
      if ("value".equals(paramString))
        return this.mValue;
      if ("action".equals(paramString))
        return this.mAction;
      if ("sitename".equals(paramString))
        return this.mSiteName;
      if ("shareaction".equals(paramString))
        return this.mShareAction;
      if ("valuename".equals(paramString))
        return this.mValueName;
      if ("valueunit".equals(paramString))
        return this.mValueUnit;
      return null;
    }

    public String makeParamString()
    {
      String[] arrayOfString1 = { "+", "/", "=" };
      String[] arrayOfString2 = { "*", "-", "" };
      String[] arrayOfString3 = { "iid", "link", "pic", "texttype", "time", "title", "type", "value", "v", "waplink" };
      StringBuffer localStringBuffer1 = new StringBuffer();
      String str2;
      for (int i = 0; ; i++)
      {
        if (i >= arrayOfString3.length)
        {
          StringBuffer localStringBuffer2 = new StringBuffer();
          String str1 = StringUtil.MD5Encode(localStringBuffer1.toString() + "_" + this.mFeedKey);
          localStringBuffer2.append("http://wap.kaixin001.com/rshare/share.php?rpara=");
          localStringBuffer2.append(str1);
          localStringBuffer2.append("_").append(this.mSiteID);
          localStringBuffer2.append("_").append(this.mBeta);
          localStringBuffer2.append("_").append(localStringBuffer1.toString());
          return localStringBuffer2.toString();
        }
        str2 = getParam(arrayOfString3[i]);
        if (!TextUtils.isEmpty(str2))
          break;
      }
      Object localObject1 = ShareFragment.Base64.encode(arrayOfString3[i].getBytes());
      Object localObject2 = ShareFragment.Base64.encode(str2.getBytes());
      int j = 0;
      while (true)
      {
        if (j >= arrayOfString1.length)
        {
          if (i != 0)
            localStringBuffer1.append("_");
          localStringBuffer1.append((String)localObject1).append("=").append((String)localObject2);
          break;
        }
        try
        {
          String str4 = ((String)localObject1).replace(arrayOfString1[j], arrayOfString2[j]);
          localObject1 = str4;
          try
          {
            label340: String str3 = ((String)localObject2).replace(arrayOfString1[j], arrayOfString2[j]);
            localObject2 = str3;
            label359: j++;
          }
          catch (NullPointerException localNullPointerException2)
          {
            break label359;
          }
        }
        catch (NullPointerException localNullPointerException1)
        {
          break label340;
        }
      }
    }

    public ShareParam setParam(String paramString1, String paramString2)
    {
      if ("id".equals(paramString1))
        this.mSiteID = paramString2;
      do
      {
        return this;
        if ("v".equals(paramString1))
        {
          this.mVersion = paramString2;
          return this;
        }
        if ("beta".equals(paramString1))
        {
          this.mBeta = paramString2;
          return this;
        }
        if ("type".equals(paramString1))
        {
          this.mType = paramString2;
          return this;
        }
        if ("texttype".equals(paramString1))
        {
          this.mTextType = paramString2;
          return this;
        }
        if ("feedkey".equals(paramString1))
        {
          this.mFeedKey = paramString2;
          return this;
        }
        if ("time".equals(paramString1))
        {
          this.mTime = paramString2;
          return this;
        }
        if ("iid".equals(paramString1))
        {
          this.mProductID = paramString2;
          return this;
        }
        if ("title".equals(paramString1))
        {
          this.mTitle = paramString2;
          return this;
        }
        if ("pic".equals(paramString1))
        {
          this.mPicURL = paramString2;
          return this;
        }
        if ("link".equals(paramString1))
        {
          this.mLink = paramString2;
          return this;
        }
        if ("waplink".equals(paramString1))
        {
          this.mWapLink = paramString2;
          return this;
        }
        if ("value".equals(paramString1))
        {
          this.mValue = paramString2;
          return this;
        }
        if ("action".equals(paramString1))
        {
          this.mAction = paramString2;
          return this;
        }
        if ("sitename".equals(paramString1))
        {
          this.mSiteName = paramString2;
          return this;
        }
        if ("shareaction".equals(paramString1))
        {
          this.mShareAction = paramString2;
          return this;
        }
        if (!"valuename".equals(paramString1))
          continue;
        this.mValueName = paramString2;
        return this;
      }
      while (!"valueunit".equals(paramString1));
      this.mValueUnit = paramString2;
      return this;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.ShareFragment
 * JD-Core Version:    0.6.0
 */