package com.tencent.jsutil;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.tencent.mta.TencentStat;

class ReportUtils$1 extends Handler
{
  public void handleMessage(Message paramMessage)
  {
    switch (paramMessage.what)
    {
    default:
      return;
    case 0:
      TencentStat.c(this.this$0.mContext, this.this$0.mQqToken);
      return;
    case 1:
      Bundle localBundle2 = paramMessage.getData();
      String str3 = localBundle2.getString("eventId");
      String[] arrayOfString = localBundle2.getString("array").split(",");
      TencentStat.a(this.this$0.mContext, this.this$0.mQqToken, str3, arrayOfString);
      return;
    case 2:
    }
    Bundle localBundle1 = paramMessage.getData();
    long l = localBundle1.getLong("costTime");
    String str1 = localBundle1.getString("reportId");
    String str2 = localBundle1.getString("appId");
    ReportUtils.reportBernoulli(this.this$0.mContext, str1, l, str2);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.jsutil.ReportUtils.1
 * JD-Core Version:    0.6.0
 */