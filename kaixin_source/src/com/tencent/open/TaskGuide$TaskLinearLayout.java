package com.tencent.open;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

class TaskGuide$TaskLinearLayout extends LinearLayout
{
  private Button rewardButton;
  private TaskGuide.StepInfo stepInfo;
  private TextView taskTextView;

  public TaskGuide$TaskLinearLayout(TaskGuide paramTaskGuide, Context paramContext, TaskGuide.StepInfo paramStepInfo)
  {
    super(paramContext);
    this.stepInfo = paramStepInfo;
    setOrientation(0);
    createChildView();
  }

  private void createChildView()
  {
    this.taskTextView = new TextView(TaskGuide.access$1700(this.this$0));
    this.taskTextView.setTextColor(Color.rgb(255, 255, 255));
    this.taskTextView.setTextSize(15.0F);
    this.taskTextView.setShadowLayer(1.0F, 1.0F, 1.0F, Color.rgb(242, 211, 199));
    this.taskTextView.setGravity(3);
    this.taskTextView.setEllipsize(TextUtils.TruncateAt.END);
    this.taskTextView.setIncludeFontPadding(false);
    this.taskTextView.setSingleLine(true);
    LinearLayout.LayoutParams localLayoutParams1 = new LinearLayout.LayoutParams(0, -2);
    localLayoutParams1.weight = 1.0F;
    localLayoutParams1.leftMargin = TaskGuide.access$1800(this.this$0, 4);
    addView(this.taskTextView, localLayoutParams1);
    this.rewardButton = new Button(TaskGuide.access$1900(this.this$0));
    this.rewardButton.setPadding(0, 0, 0, 0);
    this.rewardButton.setTextSize(16.0F);
    this.rewardButton.setTextColor(Color.rgb(255, 255, 255));
    this.rewardButton.setShadowLayer(1.0F, 1.0F, 1.0F, Color.rgb(242, 211, 199));
    this.rewardButton.setIncludeFontPadding(false);
    this.rewardButton.setOnClickListener(new TaskGuide.RewardOnClickListener(this.this$0, this.stepInfo.stepNumber));
    LinearLayout.LayoutParams localLayoutParams2 = new LinearLayout.LayoutParams(TaskGuide.access$1800(this.this$0, TaskGuide.access$2000()), TaskGuide.access$1800(this.this$0, TaskGuide.access$2100()));
    localLayoutParams2.leftMargin = TaskGuide.access$1800(this.this$0, 2);
    localLayoutParams2.rightMargin = TaskGuide.access$1800(this.this$0, 8);
    addView(this.rewardButton, localLayoutParams2);
  }

  public void setStepInfo(TaskGuide.StepInfo paramStepInfo)
  {
    this.stepInfo = paramStepInfo;
  }

  public void updateView(TaskGuide.TaskState paramTaskState)
  {
    if (!TextUtils.isEmpty(this.stepInfo.stepDesc))
      this.taskTextView.setText(this.stepInfo.stepDesc);
    switch (TaskGuide.4.$SwitchMap$com$tencent$open$TaskGuide$TaskState[paramTaskState.ordinal()])
    {
    default:
    case 1:
    case 2:
      do
      {
        return;
        this.rewardButton.setEnabled(false);
        return;
        if (this.stepInfo.status != 1)
          continue;
        this.rewardButton.setText(this.stepInfo.stepGift);
        this.rewardButton.setBackgroundDrawable(null);
        this.rewardButton.setTextColor(Color.rgb(255, 246, 0));
        this.rewardButton.setEnabled(false);
        return;
      }
      while (this.stepInfo.status != 2);
      this.rewardButton.setText("领取奖励");
      this.rewardButton.setTextColor(Color.rgb(255, 255, 255));
      this.rewardButton.setBackgroundDrawable(TaskGuide.access$2200(this.this$0));
      this.rewardButton.setEnabled(true);
      return;
    case 3:
      this.rewardButton.setText("领取中...");
      this.rewardButton.setEnabled(false);
      return;
    case 4:
    }
    this.rewardButton.setText("已领取");
    this.rewardButton.setBackgroundDrawable(TaskGuide.access$2300(this.this$0));
    this.rewardButton.setEnabled(false);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.open.TaskGuide.TaskLinearLayout
 * JD-Core Version:    0.6.0
 */