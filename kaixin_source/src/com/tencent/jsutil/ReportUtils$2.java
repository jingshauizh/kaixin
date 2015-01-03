package com.tencent.jsutil;

import android.content.Context;
import android.os.Bundle;
import com.tencent.sdkutil.HttpUtils;

final class ReportUtils$2 extends Thread
{
  public void run()
  {
    try
    {
      HttpUtils.openUrl(this.val$context, "http://cgi.qplus.com/report/report", "GET", this.val$params);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.jsutil.ReportUtils.2
 * JD-Core Version:    0.6.0
 */