package com.kaixin001.view;

import android.content.Context;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils.TruncateAt;
import android.text.method.ArrowKeyMovementMethod;
import android.text.method.MovementMethod;
import android.util.AttributeSet;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import com.kaixin001.model.FaceModel;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KXEditTextView extends TextView
{
  private static final int editTextStyle = 16842862;
  private Context mContext;

  public KXEditTextView(Context paramContext)
  {
    this(paramContext, null);
    this.mContext = paramContext;
  }

  public KXEditTextView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 16842862);
  }

  public KXEditTextView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  public void contentPicMatch(int paramInt1, int paramInt2, String paramString)
  {
    Matcher localMatcher1 = Pattern.compile("\\(#([^)]*)\\)").matcher(paramString);
    Matcher localMatcher2;
    if (!localMatcher1.find())
      localMatcher2 = Pattern.compile("\\[\\|sp\\|\\]\\d+\\[\\|ep\\|\\]").matcher(paramString);
    while (true)
    {
      if (!localMatcher2.find())
      {
        return;
        String str = localMatcher1.group();
        if (FaceModel.getInstance().getFaceIcon(str) == null)
          break;
        if ((paramInt1 > localMatcher1.start()) && (paramInt1 < localMatcher1.end()))
        {
          if ((paramInt2 > localMatcher1.start()) && (paramInt2 < localMatcher1.end()))
          {
            setSelection(localMatcher1.start(), localMatcher1.end());
            return;
          }
          setSelection(localMatcher1.start(), paramInt2);
          return;
        }
        if ((paramInt2 <= localMatcher1.start()) || (paramInt2 >= localMatcher1.end()))
          break;
        setSelection(paramInt1, localMatcher1.end());
        break;
      }
      if ((paramInt1 > localMatcher2.start()) && (paramInt1 < localMatcher2.end()))
      {
        if ((paramInt2 > localMatcher2.start()) && (paramInt2 < localMatcher2.end()))
        {
          setSelection(localMatcher2.start(), localMatcher2.end());
          return;
        }
        setSelection(localMatcher2.start(), paramInt2);
        return;
      }
      if ((paramInt2 <= localMatcher2.start()) || (paramInt2 >= localMatcher2.end()))
        continue;
      setSelection(paramInt1, localMatcher2.end());
    }
  }

  public void deletePicIfBefore()
  {
    String str1 = getText().toString();
    int i = getSelectionStart();
    int j = getSelectionEnd();
    String str2;
    Matcher localMatcher;
    if (i == j)
    {
      str2 = str1.substring(0, j) + ")" + str1.substring(j, str1.length());
      localMatcher = Pattern.compile("\\(#([^)]*)\\)").matcher(str2);
    }
    while (true)
    {
      if (!localMatcher.find())
        return;
      String str3 = localMatcher.group();
      if ((FaceModel.getInstance().getFaceIcon(str3) == null) || (i + 1 != localMatcher.end()))
        continue;
      setText(str2.substring(0, localMatcher.start()) + str2.substring(localMatcher.end(), str2.length()));
      setSelection(localMatcher.start(), localMatcher.start());
    }
  }

  public void extendSelection(int paramInt)
  {
    Selection.extendSelection(getText(), paramInt);
  }

  protected boolean getDefaultEditable()
  {
    return true;
  }

  protected MovementMethod getDefaultMovementMethod()
  {
    return ArrowKeyMovementMethod.getInstance();
  }

  public Editable getText()
  {
    return (Editable)super.getText();
  }

  protected void onSelectionChanged(int paramInt1, int paramInt2)
  {
    contentPicMatch(paramInt1, paramInt2, getText().toString());
  }

  public void selectAll()
  {
    Selection.selectAll(getText());
  }

  public void setEllipsize(TextUtils.TruncateAt paramTruncateAt)
  {
    if (paramTruncateAt == TextUtils.TruncateAt.MARQUEE)
      throw new IllegalArgumentException("KXEditTextView cannot use the ellipsize mode TextUtils.TruncateAt.MARQUEE");
    super.setEllipsize(paramTruncateAt);
  }

  public void setSelection(int paramInt)
  {
    Selection.setSelection(getText(), paramInt);
  }

  public void setSelection(int paramInt1, int paramInt2)
  {
    Selection.setSelection(getText(), paramInt1, paramInt2);
  }

  public void setText(CharSequence paramCharSequence, TextView.BufferType paramBufferType)
  {
    super.setText(paramCharSequence, TextView.BufferType.EDITABLE);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.KXEditTextView
 * JD-Core Version:    0.6.0
 */