package com.kaixin001.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import com.kaixin001.activity.InputFaceActivity;
import com.kaixin001.fragment.BaseFragment;
import com.kaixin001.fragment.FriendsFragment;
import com.kaixin001.model.FaceModel;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.ImageCache;
import java.util.ArrayList;
import java.util.HashMap;

public class KXInputView extends FrameLayout
  implements View.OnClickListener
{
  private Activity mActivity;
  private ImageView mAtBtn;
  private Bitmap mBackground;
  private EditText mEditText;
  private ImageView mFaceBtn;
  private BaseFragment mFragment;
  private KXInputListener mInputListener;
  private int mMaxLength = 140;
  private HashMap<String, Bitmap> mNameBmpMap = new HashMap();
  private Button mSendBtn;

  public KXInputView(Context paramContext)
  {
    super(paramContext);
    initView(paramContext);
  }

  public KXInputView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    initView(paramContext);
  }

  public KXInputView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    initView(paramContext);
  }

  public static BitmapDrawable corpImage(Bitmap paramBitmap, int paramInt1, int paramInt2)
  {
    try
    {
      int i = paramBitmap.getWidth();
      if (paramInt2 > paramBitmap.getHeight())
        return resizeImage(paramBitmap, i, paramInt2);
      BitmapDrawable localBitmapDrawable = new BitmapDrawable(Bitmap.createBitmap(paramBitmap, 0, 0, i, paramInt2));
      return localBitmapDrawable;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  private void initView(Context paramContext)
  {
    View localView = LayoutInflater.from(paramContext).inflate(2130903191, null);
    addView(localView);
    this.mActivity = ((Activity)paramContext);
    this.mEditText = ((EditText)localView.findViewById(2131362940));
    this.mFaceBtn = ((ImageView)localView.findViewById(2131362941));
    this.mFaceBtn.setOnClickListener(this);
    this.mAtBtn = ((ImageView)localView.findViewById(2131362942));
    this.mAtBtn.setOnClickListener(this);
    this.mSendBtn = ((Button)localView.findViewById(2131362943));
    this.mSendBtn.setOnClickListener(this);
    this.mBackground = BitmapFactory.decodeResource(paramContext.getResources(), 2130838414);
    setHintText("请输入评论");
  }

  private void insertFriendIntoContent(int paramInt1, int paramInt2, String paramString1, String paramString2)
  {
    if ((paramInt1 < 0) || (paramInt1 > paramInt2) || (TextUtils.isEmpty(paramString1)) || (TextUtils.isEmpty(paramString2)))
      return;
    String str = "@" + paramString1 + "(#" + paramString2 + "#)";
    Bitmap localBitmap = (Bitmap)this.mNameBmpMap.get(str);
    if (localBitmap == null)
      localBitmap = ImageCache.createStringBitmap("@" + paramString2, this.mEditText.getPaint());
    if (localBitmap != null)
      this.mNameBmpMap.put(str, localBitmap);
    Editable localEditable = this.mEditText.getText().replace(paramInt1, paramInt2, str + " ");
    if (paramInt1 + str.length() <= this.mMaxLength)
    {
      localEditable.setSpan(new ImageSpan(localBitmap), paramInt1, paramInt1 + str.length(), 33);
      return;
    }
    Toast.makeText(this.mActivity, 2131427900, 0).show();
  }

  public static BitmapDrawable resizeImage(Bitmap paramBitmap, int paramInt1, int paramInt2)
  {
    int i = paramBitmap.getWidth();
    int j = paramBitmap.getHeight();
    float f1 = paramInt1 / i;
    float f2 = paramInt2 / j;
    Matrix localMatrix = new Matrix();
    localMatrix.postScale(f1, f2);
    return new BitmapDrawable(Bitmap.createBitmap(paramBitmap, 0, 0, i, j, localMatrix, true));
  }

  private void selectFace()
  {
    if (this.mActivity != null)
    {
      Intent localIntent = new Intent(this.mActivity, InputFaceActivity.class);
      this.mActivity.startActivityForResult(localIntent, 209);
    }
  }

  private void selectFriend()
  {
    Intent localIntent = new Intent(this.mActivity, FriendsFragment.class);
    Bundle localBundle = new Bundle();
    localBundle.putInt("MODE", 2);
    localIntent.putExtras(localBundle);
    if (this.mFragment != null)
      AnimationUtil.startFragmentForResult(this.mFragment, localIntent, 4, 1);
  }

  protected void insertFaceIcon(int paramInt)
  {
    ArrayList localArrayList1 = FaceModel.getInstance().getStateFaceStrings();
    ArrayList localArrayList2 = FaceModel.getInstance().getStateFaceIcons();
    String str;
    Editable localEditable;
    int i;
    int j;
    if (localArrayList1 != null)
    {
      str = (String)localArrayList1.get(paramInt);
      localEditable = this.mEditText.getText();
      i = Selection.getSelectionStart(localEditable);
      j = Selection.getSelectionEnd(localEditable);
      if ((i == -1) || (j == -1))
      {
        i = localEditable.length();
        j = localEditable.length();
      }
      int k = j - i;
      if (localEditable.length() > this.mMaxLength - str.length() - k)
        Toast.makeText(this.mActivity, 2131427899, 0).show();
    }
    else
    {
      return;
    }
    localEditable.replace(i, j, str);
    localEditable.setSpan(new ImageSpan((Bitmap)localArrayList2.get(paramInt)), i, i + str.length(), 33);
  }

  public void onActivityResultOK(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if (paramInt1 == 209)
    {
      int j = paramIntent.getIntExtra("face", -1);
      if (j >= 0)
        insertFaceIcon(j);
    }
    do
      return;
    while (paramInt1 != 4);
    String str1 = paramIntent.getStringExtra("fuid");
    String str2 = paramIntent.getStringExtra("fname");
    int i = this.mEditText.getSelectionStart();
    insertFriendIntoContent(i, i, str1, str2);
  }

  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    default:
    case 2131362941:
    case 2131362942:
    case 2131362943:
    }
    do
    {
      return;
      selectFace();
      return;
      selectFriend();
      return;
    }
    while (this.mInputListener == null);
    String str = this.mEditText.getText().toString();
    this.mInputListener.onInputViewComplete(str);
  }

  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    if (this.mBackground != null)
    {
      BitmapDrawable localBitmapDrawable = corpImage(this.mBackground, this.mBackground.getWidth(), paramInt2);
      if (localBitmapDrawable != null)
      {
        localBitmapDrawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        setBackgroundDrawable(localBitmapDrawable);
      }
    }
  }

  public void setDefautText(String paramString)
  {
    this.mEditText.setText(paramString);
  }

  public void setFragment(BaseFragment paramBaseFragment)
  {
    this.mFragment = paramBaseFragment;
  }

  public void setHintText(String paramString)
  {
    this.mEditText.setHint(paramString);
  }

  public void setInputListener(KXInputListener paramKXInputListener)
  {
    this.mInputListener = paramKXInputListener;
  }

  public void showAtButton(boolean paramBoolean)
  {
    ImageView localImageView = this.mAtBtn;
    if (paramBoolean);
    for (int i = 0; ; i = 8)
    {
      localImageView.setVisibility(i);
      return;
    }
  }

  public void showFaceButton(boolean paramBoolean)
  {
    ImageView localImageView = this.mFaceBtn;
    if (paramBoolean);
    for (int i = 0; ; i = 8)
    {
      localImageView.setVisibility(i);
      return;
    }
  }

  public static abstract interface KXInputListener
  {
    public static final int TYPE_COMMENT = 0;
    public static final int TYPE_FORWARD = 1;

    public abstract void onInputViewCancel(String paramString);

    public abstract boolean onInputViewComplete(String paramString);

    public abstract boolean onInputViewShow(int paramInt, Object paramObject);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.KXInputView
 * JD-Core Version:    0.6.0
 */