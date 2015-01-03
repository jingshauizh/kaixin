package com.kaixin001.view;

import android.content.Context;
import android.graphics.Canvas;
import android.text.Layout;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EllipsizeTextView extends TextView
{
  private static final String ELLIPSIS = "...";
  private static final String TAG = "EllipsizeText";
  private final List<EllipsizeListener> ellipsizeListeners = new ArrayList();
  private String fullText;
  private boolean isEllipsized;
  private boolean isStale;
  private float lineAdditionalVerticalPadding = 0.0F;
  private float lineSpacingMultiplier = 1.0F;
  private int maxLines = 2;
  private boolean programmaticChange;

  public EllipsizeTextView(Context paramContext)
  {
    super(paramContext);
  }

  public EllipsizeTextView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public EllipsizeTextView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  private Layout createWorkingLayout(String paramString)
  {
    return new StaticLayout(paramString, getPaint(), getWidth() - getPaddingLeft() - getPaddingRight(), Layout.Alignment.ALIGN_NORMAL, this.lineSpacingMultiplier, this.lineAdditionalVerticalPadding, false);
  }

  private void resetText()
  {
    int i = getMaxLines();
    String str1 = this.fullText;
    boolean bool = false;
    String str2;
    if (i != -1)
    {
      Layout localLayout1 = createWorkingLayout(str1);
      int j = localLayout1.getLineCount();
      bool = false;
      if (j > i)
      {
        str2 = this.fullText.substring(0, -1 + localLayout1.getLineEnd(i - 1)).trim();
        Layout localLayout2 = createWorkingLayout(str2 + "...");
        if (localLayout2.getLineCount() > i)
          break label186;
        label97: str1 = str2 + "...";
        bool = true;
      }
    }
    if (!str1.equals(getText()))
      this.programmaticChange = true;
    while (true)
    {
      Iterator localIterator;
      try
      {
        setText(str1);
        this.programmaticChange = false;
        this.isStale = false;
        if (bool == this.isEllipsized)
          continue;
        this.isEllipsized = bool;
        localIterator = this.ellipsizeListeners.iterator();
        return;
        label186: int k = str2.lastIndexOf(' ');
        if (k == -1)
          break label97;
        str2 = str2.substring(0, k);
      }
      finally
      {
        this.programmaticChange = false;
      }
    }
  }

  public void addEllipsizeListener(EllipsizeListener paramEllipsizeListener)
  {
    if (paramEllipsizeListener == null)
      throw new NullPointerException();
    this.ellipsizeListeners.add(paramEllipsizeListener);
  }

  public int getMaxLines()
  {
    return this.maxLines;
  }

  public boolean isEllipsized()
  {
    return this.isEllipsized;
  }

  protected void onDraw(Canvas paramCanvas)
  {
    if (this.isStale)
    {
      super.setEllipsize(null);
      resetText();
    }
    super.onDraw(paramCanvas);
  }

  protected void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
  {
    super.onTextChanged(paramCharSequence, paramInt1, paramInt2, paramInt3);
    if (!this.programmaticChange)
    {
      this.fullText = paramCharSequence.toString();
      this.isStale = true;
    }
  }

  public void removeEllipsizeListener(EllipsizeListener paramEllipsizeListener)
  {
    this.ellipsizeListeners.remove(paramEllipsizeListener);
  }

  public void setEllipsize(TextUtils.TruncateAt paramTruncateAt)
  {
  }

  public void setLineSpacing(float paramFloat1, float paramFloat2)
  {
    this.lineAdditionalVerticalPadding = paramFloat1;
    this.lineSpacingMultiplier = paramFloat2;
    super.setLineSpacing(paramFloat1, paramFloat2);
  }

  public void setMaxLines(int paramInt)
  {
    super.setMaxLines(paramInt);
    this.maxLines = paramInt;
    this.isStale = true;
  }

  public static abstract interface EllipsizeListener
  {
    public abstract void ellipsizeStateChanged(boolean paramBoolean);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.EllipsizeTextView
 * JD-Core Version:    0.6.0
 */