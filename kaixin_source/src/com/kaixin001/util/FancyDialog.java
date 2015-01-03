package com.kaixin001.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

public class FancyDialog extends Dialog
{
  public FancyDialog(Context paramContext)
  {
    super(paramContext);
  }

  public FancyDialog(Context paramContext, int paramInt)
  {
    super(paramContext, paramInt);
  }

  public static class AppDialogBuilder
  {
    private DialogInterface.OnClickListener btnListener;
    private Context context;
    private ImageView iAppLogo;
    private int iLogoResId;
    private ImageDownLoaderManager imageDownLoaderManager = ImageDownLoaderManager.getInstance();
    private String logoUrl;
    private DialogInterface.OnClickListener noListener;
    private boolean scroll;
    private String tAuthor;
    private String tDetail;
    private String tName;

    public AppDialogBuilder(Context paramContext)
    {
      this.context = paramContext;
    }

    public AppDialogBuilder(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, DialogInterface.OnClickListener paramOnClickListener1, DialogInterface.OnClickListener paramOnClickListener2)
    {
      this.context = paramContext;
      this.tName = paramString1;
      this.tAuthor = paramString2;
      this.tDetail = paramString3;
      this.logoUrl = paramString4;
      this.btnListener = paramOnClickListener1;
      this.noListener = paramOnClickListener2;
    }

    private int dp2px(int paramInt)
    {
      return (int)(0.5F + this.context.getResources().getDisplayMetrics().density * paramInt);
    }

    public FancyDialog create()
    {
      LayoutInflater localLayoutInflater = (LayoutInflater)this.context.getSystemService("layout_inflater");
      FancyDialog localFancyDialog = new FancyDialog(this.context, 2131230735);
      View localView = localLayoutInflater.inflate(2130903312, null);
      localFancyDialog.addContentView(localView, new ViewGroup.LayoutParams(-1, -2));
      TextView localTextView1 = (TextView)localView.findViewById(2131363520);
      ImageView localImageView = (ImageView)localView.findViewById(2131363519);
      TextView localTextView2 = (TextView)localView.findViewById(2131363521);
      TextView localTextView3 = (TextView)localView.findViewById(2131363525);
      ((ScrollView)localView.findViewById(2131363523)).setVerticalScrollBarEnabled(false);
      if (this.btnListener == null)
        this.btnListener = new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramDialogInterface, int paramInt)
          {
            paramDialogInterface.dismiss();
          }
        };
      if (this.tDetail != null)
        localTextView3.setText(this.tDetail);
      while (true)
      {
        if (this.tAuthor != null)
          localTextView2.setText(this.tAuthor);
        if (this.tName != null)
          localTextView1.setText(this.tName);
        if (this.logoUrl != null)
          this.imageDownLoaderManager.displayOtherShapPicture(this.context, localImageView, this.logoUrl, "round", 0, null);
        ((TextView)localView.findViewById(2131363527)).setText(this.context.getResources().getString(2131428562));
        ((TextView)localView.findViewById(2131363527)).setOnClickListener(new View.OnClickListener(localFancyDialog)
        {
          public void onClick(View paramView)
          {
            FancyDialog.AppDialogBuilder.this.btnListener.onClick(this.val$dialog, -1);
            this.val$dialog.dismiss();
          }
        });
        ((TextView)localView.findViewById(2131362221)).setOnClickListener(new View.OnClickListener(localFancyDialog)
        {
          public void onClick(View paramView)
          {
            FancyDialog.AppDialogBuilder.this.noListener.onClick(this.val$dialog, -2);
            this.val$dialog.dismiss();
          }
        });
        Window localWindow = localFancyDialog.getWindow();
        WindowManager.LayoutParams localLayoutParams = ((Activity)this.context).getWindow().getAttributes();
        localLayoutParams.width = (((Activity)this.context).getWindowManager().getDefaultDisplay().getWidth() - dp2px(44));
        localWindow.setAttributes(localLayoutParams);
        localFancyDialog.setContentView(localView);
        return localFancyDialog;
        localView.findViewById(2131363522).setVisibility(8);
        localView.findViewById(2131363526).setVisibility(8);
      }
    }

    public DialogInterface.OnClickListener getBtnListener()
    {
      return this.btnListener;
    }

    public ImageView getIvAppLogo()
    {
      return this.iAppLogo;
    }

    public String getLogoUrl()
    {
      return this.logoUrl;
    }

    public String getTvDetail()
    {
      return this.tDetail;
    }

    public int getiLogoResId()
    {
      return this.iLogoResId;
    }

    public Context getmContext()
    {
      return this.context;
    }

    public String gettAuthor()
    {
      return this.tAuthor;
    }

    public String gettName()
    {
      return this.tName;
    }

    public boolean isScroll()
    {
      return this.scroll;
    }

    public AppDialogBuilder setBtnListener(DialogInterface.OnClickListener paramOnClickListener)
    {
      this.btnListener = paramOnClickListener;
      return this;
    }

    public AppDialogBuilder setIvAppLogo(ImageView paramImageView)
    {
      this.iAppLogo = paramImageView;
      return this;
    }

    public AppDialogBuilder setLogoUrl(String paramString)
    {
      this.logoUrl = paramString;
      return this;
    }

    public AppDialogBuilder setScroll(boolean paramBoolean)
    {
      this.scroll = paramBoolean;
      return this;
    }

    public AppDialogBuilder setTvDetail(String paramString)
    {
      this.tDetail = paramString;
      return this;
    }

    public AppDialogBuilder setiLogoResId(int paramInt)
    {
      this.iLogoResId = paramInt;
      return this;
    }

    public AppDialogBuilder setmContext(Context paramContext)
    {
      this.context = paramContext;
      return this;
    }

    public AppDialogBuilder settAuthor(String paramString)
    {
      this.tAuthor = paramString;
      return this;
    }

    public AppDialogBuilder settName(String paramString)
    {
      this.tName = paramString;
      return this;
    }
  }

  public static class Builder
  {
    private FancyDialogAdapter adapter;
    private DialogInterface.OnClickListener adapterListener;
    private Context context;
    private boolean itemHorizontalCenter = false;
    private String[] items;
    private DialogInterface.OnClickListener itemsListener;
    private DialogInterface.OnClickListener noBtnListener;
    private String noBtnWord;
    private boolean scroll;
    private String stitle;
    private String title;
    private FancyDialog.FancyDialogType type;
    private DialogInterface.OnClickListener yesBtnListener;
    private String yesBtnWord;

    public Builder(Context paramContext)
    {
      this.context = paramContext;
    }

    private int dp2px(int paramInt)
    {
      return (int)(0.5F + this.context.getResources().getDisplayMetrics().density * paramInt);
    }

    public FancyDialog create()
    {
      LayoutInflater localLayoutInflater = (LayoutInflater)this.context.getSystemService("layout_inflater");
      FancyDialog localFancyDialog = new FancyDialog(this.context, 2131230735);
      View localView = localLayoutInflater.inflate(2130903103, null);
      localFancyDialog.addContentView(localView, new ViewGroup.LayoutParams(-1, -2));
      LinearLayout localLinearLayout = (LinearLayout)localView.findViewById(2131362218);
      ScrollView localScrollView = (ScrollView)localView.findViewById(2131362217);
      localScrollView.setVerticalScrollBarEnabled(false);
      if (this.yesBtnListener == null)
      {
        1 local1 = new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramDialogInterface, int paramInt)
          {
            paramDialogInterface.dismiss();
          }
        };
        this.yesBtnListener = local1;
      }
      if (this.noBtnListener == null)
      {
        2 local2 = new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramDialogInterface, int paramInt)
          {
            paramDialogInterface.dismiss();
          }
        };
        this.noBtnListener = local2;
      }
      label180: label232: int k;
      label284: int i;
      ViewGroup.LayoutParams localLayoutParams;
      if (this.stitle != null)
      {
        ((TextView)localView.findViewById(2131362215)).setText(this.stitle);
        if (this.type != FancyDialog.FancyDialogType.NOTICE)
          break label491;
        if (this.title == null)
          break label466;
        ((TextView)localView.findViewById(2131362219)).setText(this.title);
        if (this.yesBtnWord == null)
          break label516;
        ((Button)localView.findViewById(2131362223)).setText(this.yesBtnWord);
        Button localButton2 = (Button)localView.findViewById(2131362223);
        3 local3 = new View.OnClickListener(localFancyDialog)
        {
          public void onClick(View paramView)
          {
            FancyDialog.Builder.this.yesBtnListener.onClick(this.val$dialog, -1);
            this.val$dialog.dismiss();
          }
        };
        localButton2.setOnClickListener(local3);
        if (this.noBtnWord == null)
          break label533;
        ((Button)localView.findViewById(2131362221)).setText(this.noBtnWord);
        Button localButton1 = (Button)localView.findViewById(2131362221);
        4 local4 = new View.OnClickListener(localFancyDialog)
        {
          public void onClick(View paramView)
          {
            FancyDialog.Builder.this.noBtnListener.onClick(this.val$dialog, -2);
            this.val$dialog.dismiss();
          }
        };
        localButton1.setOnClickListener(local4);
        if ((this.yesBtnWord == null) || (this.noBtnWord == null))
          localView.findViewById(2131362222).setVisibility(8);
        if ((this.yesBtnWord == null) && (this.noBtnWord == null))
          localView.findViewById(2131362222).setVisibility(8);
        if (this.type == FancyDialog.FancyDialogType.SELECTLIST)
        {
          k = 0;
          if (k < this.items.length)
            break label550;
        }
        if (this.type == FancyDialog.FancyDialogType.VALUELIST)
        {
          localScrollView.setVerticalScrollBarEnabled(this.scroll);
          i = 0;
          if (i < this.items.length)
            break label776;
          if (this.items.length > 5)
          {
            localLayoutParams = localScrollView.getLayoutParams();
            if (localLayoutParams != null)
              break label1135;
            localLayoutParams = new ViewGroup.LayoutParams(-1, dp2px(250));
          }
        }
      }
      while (true)
      {
        localScrollView.setLayoutParams(localLayoutParams);
        localFancyDialog.setContentView(localView);
        return localFancyDialog;
        localView.findViewById(2131362214).setVisibility(8);
        localView.findViewById(2131362216).setVisibility(8);
        break;
        label466: localView.findViewById(2131362218).setVisibility(8);
        localView.findViewById(2131362220).setVisibility(8);
        break label180;
        label491: localView.findViewById(2131362219).setVisibility(8);
        localView.findViewById(2131362220).setVisibility(8);
        break label180;
        label516: ((Button)localView.findViewById(2131362223)).setVisibility(8);
        break label232;
        label533: ((Button)localView.findViewById(2131362221)).setVisibility(8);
        break label284;
        label550: TextView localTextView3 = new TextView(this.context);
        localTextView3.setBackgroundResource(2130837991);
        localTextView3.setText(this.items[k]);
        if (this.itemHorizontalCenter)
        {
          localTextView3.setGravity(17);
          localTextView3.setPadding(0, 0, 0, 0);
        }
        while (true)
        {
          localTextView3.setTextSize(18.0F);
          localTextView3.setTextColor(-16777216);
          localTextView3.setClickable(true);
          localTextView3.setLayoutParams(new ViewGroup.LayoutParams(-1, dp2px(50)));
          localLinearLayout.addView(localTextView3);
          int m = k;
          5 local5 = new View.OnClickListener(localFancyDialog, m)
          {
            public void onClick(View paramView)
            {
              FancyDialog.Builder.this.itemsListener.onClick(this.val$dialog, this.val$which);
              this.val$dialog.dismiss();
            }
          };
          localTextView3.setOnClickListener(local5);
          if ((this.yesBtnWord != null) || (this.noBtnWord != null) || (k != -1 + this.items.length))
          {
            TextView localTextView4 = new TextView(this.context);
            localTextView4.setBackgroundResource(2130838294);
            localTextView4.setLayoutParams(new ViewGroup.LayoutParams(-1, dp2px(1)));
            localLinearLayout.addView(localTextView4);
          }
          k++;
          break;
          localTextView3.setGravity(16);
          localTextView3.setPadding(dp2px(15), 0, 0, 0);
        }
        label776: RelativeLayout localRelativeLayout = new RelativeLayout(this.context);
        localRelativeLayout.setClickable(true);
        localRelativeLayout.setBackgroundResource(2130837991);
        localRelativeLayout.setLayoutParams(new ViewGroup.LayoutParams(-1, dp2px(50)));
        localRelativeLayout.setGravity(16);
        TextView localTextView1 = new TextView(this.context);
        localTextView1.setText(this.items[i]);
        localTextView1.setGravity(3);
        localTextView1.setTextSize(18.0F);
        localTextView1.setTextColor(-16777216);
        ViewGroup.MarginLayoutParams localMarginLayoutParams1 = new ViewGroup.MarginLayoutParams(-2, -2);
        localMarginLayoutParams1.setMargins(30, 0, 0, 0);
        RelativeLayout.LayoutParams localLayoutParams1 = new RelativeLayout.LayoutParams(localMarginLayoutParams1);
        localLayoutParams1.addRule(9);
        localTextView1.setLayoutParams(localLayoutParams1);
        localRelativeLayout.addView(localTextView1);
        ImageView localImageView = new ImageView(this.context);
        if (this.adapter.getSelectedIndex() == i)
          localImageView.setBackgroundResource(2130838024);
        while (true)
        {
          ViewGroup.MarginLayoutParams localMarginLayoutParams2 = new ViewGroup.MarginLayoutParams(-2, -2);
          localMarginLayoutParams2.setMargins(0, 0, 30, 0);
          RelativeLayout.LayoutParams localLayoutParams2 = new RelativeLayout.LayoutParams(localMarginLayoutParams2);
          localLayoutParams2.addRule(11);
          localImageView.setLayoutParams(localLayoutParams2);
          localRelativeLayout.addView(localImageView);
          int j = i;
          6 local6 = new View.OnClickListener(localFancyDialog, j)
          {
            public void onClick(View paramView)
            {
              FancyDialog.Builder.this.adapterListener.onClick(this.val$dialog, this.val$which);
              this.val$dialog.dismiss();
            }
          };
          localRelativeLayout.setOnClickListener(local6);
          localLinearLayout.addView(localRelativeLayout);
          if ((this.yesBtnWord != null) || (this.noBtnWord != null) || (i != -1 + this.items.length))
          {
            TextView localTextView2 = new TextView(this.context);
            localTextView2.setBackgroundResource(2130838294);
            localTextView2.setLayoutParams(new ViewGroup.LayoutParams(-1, dp2px(1)));
            localLinearLayout.addView(localTextView2);
          }
          i++;
          break;
          localImageView.setBackgroundResource(2130838023);
        }
        label1135: localLayoutParams.height = dp2px(250);
      }
    }

    public Builder setAdapter(FancyDialogAdapter paramFancyDialogAdapter)
    {
      this.adapter = paramFancyDialogAdapter;
      return this;
    }

    public Builder setAdapterListener(DialogInterface.OnClickListener paramOnClickListener)
    {
      this.adapterListener = paramOnClickListener;
      return this;
    }

    public void setItemHorizontalCenter(boolean paramBoolean)
    {
      this.itemHorizontalCenter = paramBoolean;
    }

    public Builder setItems(String[] paramArrayOfString)
    {
      this.items = paramArrayOfString;
      return this;
    }

    public Builder setItemsListener(DialogInterface.OnClickListener paramOnClickListener)
    {
      this.itemsListener = paramOnClickListener;
      return this;
    }

    public Builder setNoBtnListener(DialogInterface.OnClickListener paramOnClickListener)
    {
      this.noBtnListener = paramOnClickListener;
      return this;
    }

    public Builder setNoBtnWord(int paramInt)
    {
      if (paramInt == 0)
        return this;
      return setNoBtnWord(this.context.getResources().getString(paramInt));
    }

    public Builder setNoBtnWord(String paramString)
    {
      this.noBtnWord = paramString;
      return this;
    }

    public Builder setSTitle(int paramInt)
    {
      if (paramInt == 0)
        return this;
      return setSTitle(this.context.getResources().getString(paramInt));
    }

    public Builder setSTitle(String paramString)
    {
      this.stitle = paramString;
      return this;
    }

    public Builder setScroll(boolean paramBoolean)
    {
      this.scroll = paramBoolean;
      return this;
    }

    public Builder setTitle(int paramInt)
    {
      if (paramInt == 0)
        return this;
      return setTitle(this.context.getResources().getString(paramInt));
    }

    public Builder setTitle(String paramString)
    {
      this.title = paramString;
      return this;
    }

    public Builder setType(FancyDialog.FancyDialogType paramFancyDialogType)
    {
      this.type = paramFancyDialogType;
      return this;
    }

    public Builder setYesBtnListener(DialogInterface.OnClickListener paramOnClickListener)
    {
      this.yesBtnListener = paramOnClickListener;
      return this;
    }

    public Builder setYesBtnWord(int paramInt)
    {
      if (paramInt == 0)
        return this;
      return setYesBtnWord(this.context.getResources().getString(paramInt));
    }

    public Builder setYesBtnWord(String paramString)
    {
      this.yesBtnWord = paramString;
      return this;
    }
  }

  public static enum FancyDialogType
  {
    static
    {
      FancyDialogType[] arrayOfFancyDialogType = new FancyDialogType[3];
      arrayOfFancyDialogType[0] = NOTICE;
      arrayOfFancyDialogType[1] = SELECTLIST;
      arrayOfFancyDialogType[2] = VALUELIST;
      ENUM$VALUES = arrayOfFancyDialogType;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.util.FancyDialog
 * JD-Core Version:    0.6.0
 */