package com.kaixin001.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.util.KXLog;
import java.net.URLEncoder;
import org.json.JSONObject;

public class KXTestSettingActivity2 extends Activity
{
  private Button btnAdd;
  int currentSelectionHost;
  private EditText evAid;
  private EditText evDetail;
  private EditText evStatus;
  private EditText evTitle;
  private EditText evType;

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903179);
    this.evAid = ((EditText)findViewById(2131362869));
    this.evTitle = ((EditText)findViewById(2131362870));
    this.evDetail = ((EditText)findViewById(2131362871));
    this.evType = ((EditText)findViewById(2131362872));
    this.evStatus = ((EditText)findViewById(2131362873));
    this.btnAdd = ((Button)findViewById(2131362874));
    this.btnAdd.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        String str1 = KXTestSettingActivity2.this.evAid.getText().toString();
        String str2 = KXTestSettingActivity2.this.evTitle.getText().toString();
        String str3 = KXTestSettingActivity2.this.evDetail.getText().toString();
        String str4 = KXTestSettingActivity2.this.evType.getText().toString();
        String str5 = KXTestSettingActivity2.this.evStatus.getText().toString();
        if (TextUtils.isEmpty(str2))
          Toast.makeText(KXTestSettingActivity2.this, "标题不能为空", 0).show();
        if (TextUtils.isEmpty(str3))
          Toast.makeText(KXTestSettingActivity2.this, "内容不能为空", 0).show();
        if (TextUtils.isEmpty(str4))
          Toast.makeText(KXTestSettingActivity2.this, "类型不能为空", 0).show();
        if ((!"0".equals(str4)) && (!"1".equals(str4)))
          Toast.makeText(KXTestSettingActivity2.this, "类型只能是1或0", 0).show();
        if ((!"0".equals(str5)) && (!"1".equals(str5)))
          Toast.makeText(KXTestSettingActivity2.this, "状态只能是1或0", 0).show();
        String str6 = "capi/rest.php?method=lbs.insertAward&aid=" + str1 + "&title=" + URLEncoder.encode(str2) + "&detail=" + URLEncoder.encode(str3) + "&type=" + str4 + "&status=" + str5;
        HttpProxy localHttpProxy = new HttpProxy(KXTestSettingActivity2.this);
        try
        {
          String str7 = localHttpProxy.httpGet(str6, null, null);
          if (str7 == null)
          {
            Toast.makeText(KXTestSettingActivity2.this, "添加失败", 0).show();
            return;
          }
          KXLog.d("GetAwardsRequest", "strContent=" + str7);
          if (new JSONObject(str7).getInt("ret") == 1)
          {
            Toast.makeText(KXTestSettingActivity2.this, "添加成功", 0).show();
            return;
          }
        }
        catch (Exception localException)
        {
          KXLog.e("KXTestSettingActivity2", "GetAwardsRequest error", localException);
          Toast.makeText(KXTestSettingActivity2.this, "添加失败", 0).show();
        }
      }
    });
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.activity.KXTestSettingActivity2
 * JD-Core Version:    0.6.0
 */