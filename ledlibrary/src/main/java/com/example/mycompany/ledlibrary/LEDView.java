// LEDExample/ledlibrary/src/main/java/com/example/mycompany/ledlibrary/LEDView.java

/*
<?xml version="1.0" encoding="utf-8"?>

<vector
    xmlns:android="http://schemas.android.com/apk/res/android"
            android:width="24dp"
            android:height="32dp"
            android:viewportWidth="24.0"
            android:viewportHeight="32.0" >

<group android:name="led_on">
<path
            android:name="bulb"
                    android:strokeColor="@color/colorLed"
                    android:fillColor="@color/colorLedFill"
                    android:strokeWidth="1"
                    android:pathData="M 21,20
                    v -10
                    a 9,9 0 0 0 -18,0
                    v 10" />
<path
            android:name="reflection"
                    android:strokeColor="@android:color/white"
                    android:strokeWidth="1"
                    android:strokeLineCap="round"
                    android:pathData="M 12,4
                    a 6,6 0 0 0 -6,6
                    v 7" />
<path
            android:name="base"
                    android:strokeColor="@color/colorLed"
                    android:fillColor="@color/colorLedFill"
                    android:strokeWidth="1"
                    android:pathData="M 1,24
                    h 22, v -4 h -22 Z" />
<path
            android:name="lead"
                    android:strokeColor="@color/colorLed"
                    android:fillColor="@color/colorLed"
                    android:strokeWidth="1"
                    android:pathData="M 7,24
                    v 8
                    M 17,24
                    v 8" />
<path
            android:name="leadwide"
                    android:strokeColor="@color/colorLed"
                    android:fillColor="@color/colorLed"
                    android:strokeWidth="3"
                    android:pathData="M 7,24
                    v 3
                    M 17,24
                    v 3" />
</group>
</vector>
*/

package com.example.mycompany.ledlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static android.graphics.Color.argb;

public class LEDView extends View {
    // <Add>
    private static @ColorInt int COLOR_BLACK = argb(0xFF, 0x00, 0x00, 0x00);

    @IntDef({Orientation.UP, Orientation.DOWN, Orientation.RIGHT, Orientation.LEFT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Orientation {
        public static final int UP = 0;
        public static final int DOWN = 1;
        public static final int RIGHT = 2;
        public static final int LEFT = 3;
    }

    private Context context_ = null;

    private Paint paintBorder_ = null;
    private Paint paintFill_ = null;

    public LEDView(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public LEDView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public LEDView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

/*
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LEDView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }
*/

    void init(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {

        final @ColorInt int DEFAULT_COLOR_BORDER = COLOR_BLACK;
        final @ColorInt int DEFAULT_COLOR_FILL = COLOR_BLACK;
        final boolean DEFAULT_LIGHT_OFF = false;
        final boolean DEFAULT_LIGHT_ON = false;
        final int DEFAULT_BLINK_DURATION = 1000;
        final @Orientation int DEFAULT_ORIENTATION = Orientation.UP;

        @ColorInt int colorBorder = DEFAULT_COLOR_BORDER;
        @ColorInt int colorFill = DEFAULT_COLOR_FILL;
        boolean lightOff = DEFAULT_LIGHT_OFF;
        boolean lightOn = DEFAULT_LIGHT_ON;
        int blinkDuration = DEFAULT_BLINK_DURATION;
        @Orientation int orientation = DEFAULT_ORIENTATION;

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.LEDView,
                0, 0);

        try {
            colorBorder = a.getColor(R.styleable.LEDView_color_border, colorBorder);
            colorFill = a.getColor(R.styleable.LEDView_color_fill, colorFill);
            lightOff = a.getBoolean(R.styleable.LEDView_light_off, lightOff);
            lightOn = a.getBoolean(R.styleable.LEDView_light_on, lightOn);
            blinkDuration = a.getInt(R.styleable.LEDView_blink_duration, blinkDuration);
            { // orientation = (Orientation) a.getInt(R.styleable.LEDView_orientation, orientation);
                final @Orientation int tmpOrientation = a.getInt(R.styleable.LEDView_orientation, orientation);
                orientation = tmpOrientation;
            }
        } finally {
            a.recycle();
        }

        context_ = context;

        paintBorder_ = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintBorder_.setStyle(Paint.Style.STROKE);
        paintBorder_.setColor(colorBorder);

        paintFill_ = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintFill_.setStyle(Paint.Style.FILL);
        paintFill_.setColor(colorFill);

        refreshDrawableState(); // Required if custom states are used.
    }

// <//Add>

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec); // Not required. // TODO: Comment out.

        final int minw = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();
        final int w = resolveSize(minw, widthMeasureSpec);

        final int minh = getPaddingTop() + getPaddingBottom() + getSuggestedMinimumHeight();
        final int h = resolveSize(minh, heightMeasureSpec);

        setMeasuredDimension(w, h);
    }
}
