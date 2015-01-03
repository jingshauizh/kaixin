package com.kaixin001.adapter;

import android.app.Activity;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.kaixin001.fragment.BaseFragment;
import com.kaixin001.item.AlbumInfo;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.KXTextUtil;
import java.util.ArrayList;

public class AlbumlistAdapter extends ArrayAdapter<AlbumInfo>
{
  private ArrayList<AlbumInfo> items;
  private Activity mContext;
  private BaseFragment mFragment;
  private String strIsmyfriend;

  public AlbumlistAdapter(BaseFragment paramBaseFragment, int paramInt, ArrayList<AlbumInfo> paramArrayList, String paramString)
  {
    super(paramBaseFragment.getActivity(), paramInt, paramArrayList);
    this.mContext = paramBaseFragment.getActivity();
    this.items = paramArrayList;
    this.strIsmyfriend = paramString;
    this.mFragment = paramBaseFragment;
  }

  // ERROR //
  public View getView(int paramInt, View paramView, android.view.ViewGroup paramViewGroup)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 29	com/kaixin001/adapter/AlbumlistAdapter:items	Ljava/util/ArrayList;
    //   4: iload_1
    //   5: invokevirtual 49	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   8: checkcast 51	com/kaixin001/item/AlbumInfo
    //   11: astore 4
    //   13: aload_2
    //   14: ifnonnull +53 -> 67
    //   17: aload_0
    //   18: getfield 27	com/kaixin001/adapter/AlbumlistAdapter:mContext	Landroid/app/Activity;
    //   21: ldc 53
    //   23: invokevirtual 59	android/app/Activity:getSystemService	(Ljava/lang/String;)Ljava/lang/Object;
    //   26: checkcast 61	android/view/LayoutInflater
    //   29: ldc 62
    //   31: aconst_null
    //   32: invokevirtual 66	android/view/LayoutInflater:inflate	(ILandroid/view/ViewGroup;)Landroid/view/View;
    //   35: astore_2
    //   36: new 68	com/kaixin001/adapter/AlbumlistAdapter$AlbumlistItemViewTag
    //   39: dup
    //   40: aload_0
    //   41: aload_2
    //   42: invokespecial 71	com/kaixin001/adapter/AlbumlistAdapter$AlbumlistItemViewTag:<init>	(Lcom/kaixin001/adapter/AlbumlistAdapter;Landroid/view/View;)V
    //   45: astore 7
    //   47: aload_2
    //   48: aload 7
    //   50: invokevirtual 77	android/view/View:setTag	(Ljava/lang/Object;)V
    //   53: aload 7
    //   55: astore 6
    //   57: aload 6
    //   59: aload_2
    //   60: aload 4
    //   62: invokevirtual 81	com/kaixin001/adapter/AlbumlistAdapter$AlbumlistItemViewTag:setAlbumItem	(Landroid/view/View;Lcom/kaixin001/item/AlbumInfo;)V
    //   65: aload_2
    //   66: areturn
    //   67: aload_2
    //   68: invokevirtual 85	android/view/View:getTag	()Ljava/lang/Object;
    //   71: checkcast 68	com/kaixin001/adapter/AlbumlistAdapter$AlbumlistItemViewTag
    //   74: astore 6
    //   76: goto -19 -> 57
    //   79: astore 5
    //   81: ldc 87
    //   83: ldc 88
    //   85: aload 5
    //   87: invokestatic 94	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   90: aload_2
    //   91: areturn
    //   92: astore 5
    //   94: goto -13 -> 81
    //
    // Exception table:
    //   from	to	target	type
    //   17	47	79	java/lang/Exception
    //   57	65	79	java/lang/Exception
    //   67	76	79	java/lang/Exception
    //   47	53	92	java/lang/Exception
  }

  private class AlbumlistItemViewTag
  {
    private ImageView ivCover;
    private ImageView ivPrivacy;
    private TextView tvTime;
    private TextView tvTitle;

    public AlbumlistItemViewTag(View arg2)
    {
      Object localObject;
      this.ivCover = ((ImageView)localObject.findViewById(2131362517));
      this.tvTitle = ((TextView)localObject.findViewById(2131362518));
      this.ivPrivacy = ((ImageView)localObject.findViewById(2131362519));
      this.tvTime = ((TextView)localObject.findViewById(2131362520));
    }

    public void setAlbumItem(View paramView, AlbumInfo paramAlbumInfo)
    {
      while (true)
      {
        String str1;
        try
        {
          str1 = paramAlbumInfo.albumsFeedPrivacy;
          if ((str1.compareTo("0") != 0) && ((str1.compareTo("1") != 0) || (AlbumlistAdapter.this.strIsmyfriend.compareTo("1") != 0)))
            continue;
          BaseFragment localBaseFragment = AlbumlistAdapter.this.mFragment;
          ImageView localImageView = this.ivCover;
          if (!TextUtils.isEmpty(paramAlbumInfo.albumsFeedCoverpic130))
            continue;
          String str2 = paramAlbumInfo.albumsFeedCoverpic;
          localBaseFragment.displayPicture(localImageView, str2, 2130838779);
          this.ivPrivacy.setVisibility(8);
          String str3 = paramAlbumInfo.albumsFeedAlbumtitle;
          String str4 = paramAlbumInfo.albumsFeedPicnum;
          if (TextUtils.isEmpty(str4))
            continue;
          str3 = str3 + "(" + str4 + ")";
          this.tvTitle.setText(str3);
          String str5 = paramAlbumInfo.albumsFeedMtime;
          String str6 = KXTextUtil.getNewsDate(AlbumlistAdapter.this.mContext, str5) + " " + AlbumlistAdapter.this.mContext.getResources().getString(2131427745);
          this.tvTime.setText(str6);
          return;
          str2 = paramAlbumInfo.albumsFeedCoverpic130;
          continue;
          if (str1.compareTo("2") == 0)
          {
            AlbumlistAdapter.this.mFragment.displayPicture(this.ivCover, null, 2130837545);
            this.ivPrivacy.setImageResource(2130838728);
            this.ivPrivacy.setVisibility(0);
            continue;
          }
        }
        catch (Exception localException)
        {
          KXLog.e("AlbumlistItemViewTag", "setAlbumItem", localException);
          return;
        }
        if (str1.compareTo("1") == 0)
        {
          AlbumlistAdapter.this.mFragment.displayPicture(this.ivCover, null, 2130837544);
          this.ivPrivacy.setImageResource(2130838731);
          this.ivPrivacy.setVisibility(0);
          continue;
        }
        if (str1.compareTo("3") != 0)
          continue;
        AlbumlistAdapter.this.mFragment.displayPicture(this.ivCover, null, 2130837546);
        this.ivPrivacy.setImageResource(2130838726);
        this.ivPrivacy.setVisibility(0);
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.adapter.AlbumlistAdapter
 * JD-Core Version:    0.6.0
 */