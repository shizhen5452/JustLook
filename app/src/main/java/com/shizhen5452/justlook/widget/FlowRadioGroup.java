package com.shizhen5452.justlook.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioGroup;

/**
 * Create by EminemShi on 2017/2/11
 */

public class FlowRadioGroup extends RadioGroup {

    public FlowRadioGroup(Context context) {
        super(context);
    }

    public FlowRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        measureChildren(widthMeasureSpec,heightMeasureSpec);

        int totalWidth=0;
        int totalHeight=0;
        int maxLineWidth=0;
        int maxLineHeight=0;
        int oldWidth;
        int oldHeight;

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            oldWidth=maxLineWidth;
            oldHeight=maxLineHeight;
            MarginLayoutParams params= (MarginLayoutParams) child.getLayoutParams();

            int deltaX = child.getMeasuredWidth() + params.leftMargin + params.rightMargin;
            if (totalWidth + deltaX + getPaddingLeft() + getPaddingRight() > widthSize) {
                maxLineWidth = Math.max(oldWidth, totalWidth);
                totalWidth = deltaX;
                totalHeight += oldHeight;
                maxLineHeight = child.getMeasuredHeight() + params.topMargin + params.bottomMargin;
            } else {
                totalWidth+=deltaX;
                int deltaY=child.getMeasuredHeight()+params.topMargin + params.bottomMargin;
                maxLineHeight=Math.max(deltaY,maxLineHeight);
            }
            if (i == (childCount - 1)) {
                totalHeight+=maxLineHeight;
                maxLineWidth=Math.max(totalWidth,oldWidth);
            }
        }
        maxLineWidth+=getPaddingLeft()+getPaddingRight();
        totalHeight+=getPaddingTop()+getPaddingBottom();
        setMeasuredDimension(widthMode==MeasureSpec.EXACTLY?widthSize:maxLineWidth,heightMode==MeasureSpec.EXACTLY?heightSize:totalHeight);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int preLeft=getPaddingLeft();
        int preTop=getPaddingTop();
        int maxHeight=0;

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();
            if (preLeft + params.leftMargin + child.getMeasuredWidth() + params.rightMargin + getPaddingRight() > (r - 1)) {
                preLeft=getPaddingLeft();
                preTop+=maxHeight;
                maxHeight=child.getMeasuredHeight()+params.topMargin+params.bottomMargin;
            } else {
                maxHeight=Math.max(maxHeight,params.topMargin+child.getMeasuredHeight()+params.bottomMargin);
            }
            int left=preLeft+params.leftMargin;
            int top=preTop+params.topMargin;
            int right=left+child.getMeasuredWidth();
            int bottom=top+child.getMeasuredHeight();
            child.layout(left,top,right,bottom);
            preLeft+=params.leftMargin+child.getMeasuredWidth()+params.rightMargin;
        }

    }
}
