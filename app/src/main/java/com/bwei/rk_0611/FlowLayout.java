package com.bwei.rk_0611;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class FlowLayout extends ViewGroup {
    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //获取测量屏幕宽高的方法

        measureChildren(widthMeasureSpec,heightMeasureSpec);

        //获取屏幕的宽高 和模式

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        //定义需要用到的变量

        int width = 0;
        int height = 0;
        int lineWidth = 0;
        int lineHeight = 0;

        int childWidth = 0;
        int childHeight = 0;

        View childView;

        int toalHeight = 0;

        MarginLayoutParams layoutParams;

        //循环

        for (int i = 0; i < getChildCount(); i++) {

            //获取每个子View
            childView = getChildAt(i);

            //获取当前子View的宽高
            childWidth = childView.getMeasuredWidth();
            childHeight = childView.getMeasuredHeight();

            //判断 如果一个子View就占满了FlowLayout(父控件)的一行 就报错
            if (childWidth > widthSize){

                throw new IllegalStateException("一个子View不能占一行");

            }

            //判断 一行内有多个子View 需不需要换行

            if (lineWidth +childWidth> widthSize - getPaddingLeft() - getPaddingRight()){

                //换行

                //认为当前行是最宽的
                width = widthSize;

                //累加高度
                toalHeight += lineHeight;

                //记录高度
                lineHeight = childHeight;
                //记录宽度
                lineWidth = childWidth;
            }else{

                //不换行

                //累加宽度
                lineWidth += childWidth;

                //判断高度
                lineHeight = Math.max(lineHeight,childHeight);

                //判断宽度
                width = Math.max(width,lineWidth);

            }

            //判断 如果是最后一行 就加上子View的高度

            if (i == getChildCount() -1){

                toalHeight +=lineHeight;

                height=toalHeight;

            }

            width = widthMode == MeasureSpec.EXACTLY ? widthSize :width;
            height = heightMode == MeasureSpec.EXACTLY ? heightSize :height;

            setMeasuredDimension(width,height);

        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int lineWidth = 0;
        int lineHeight = 0;

        int childWidth = 0;
        int childHeight = 0;

        View childView;

        int toalHeight = 0;


        //循环
        for (int i = 0; i < getChildCount(); i++) {

            //获取每个子View
            childView = getChildAt(i);

            //获取当前子View的宽高
            childWidth = childView.getMeasuredWidth();
            childHeight = childView.getMeasuredHeight();

            //判断 如果一个子View就占满了FlowLayout(父控件)的一行 就报错
            if (childWidth > getMeasuredWidth()){

                throw new IllegalStateException("一个子View不能占一行");

            }

            //判断 一行内有多个子View 需不需要换行

            if (lineWidth +childWidth> getMeasuredWidth()){

                //换行

                //累加高度
                toalHeight += lineHeight;

                lineWidth = 0;

                layoutView(childView,lineWidth,toalHeight,lineWidth+childWidth,toalHeight+childHeight);

                //记录高度
                lineHeight = childHeight;
                //记录宽度
                lineWidth = childWidth;
            }else{

                //不换行

                layoutView(childView,lineWidth,toalHeight,lineWidth+childWidth,toalHeight+childHeight);

                //累加宽度
                lineWidth+=childWidth;

                //判断高度
                lineHeight = Math.max(lineHeight,childHeight);

            }

        }

    }

    public void layoutView(View child,int l,int t,int r,int b){

        l+=child.getPaddingLeft();
        t+=child.getPaddingTop();
        r+=child.getPaddingRight();
        b+=child.getPaddingBottom();

        child.layout(l,t,r,b);

    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(),attrs);
    }
}
