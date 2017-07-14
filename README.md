# LEDExample

Create an example custom view that resembles an LED that can be on, off or blinking. 

---
Created on: 2017-07-05<br />
IDE: Android Studio 2.3.3<br />
OS: LinuxMint 17.2<br />

This project assumes a basic understanding of Java, Android and Android Studio.

Where not otherwise noted, Android Studio defaults were chosen.

Where noted, use the <***Sync Checkpoint***> <***Build Checkpoint***> and <***Run Checkpoint***>'s to confirm your progress.

---
## Step 1: Create New Project

Select menu: File | New | New Project...

---
**Configure your new project**

Application name: LED Example<br />
Company Domain: mycompany.example.com<br />
Include C++ Support: Check Off<br />

---
**Add an Activity to Mobile**

Basic Activity

---
**Customize the Activity**

Use a Fragment: Check On

---
Select menu: Build | Make Project

---
<***Run Checkpoint***>

---
## Step 3: Add New Library Module

Select menu: File | New | New Module...

---
**New Module**

Select: Android Library

---
**Android Library**

Application/Library name: LED Library<br />
Module name: ledlibrary<br />

---
<***Run Checkpoint***>

---
## Step 3: Design Custom View

In this example, the design is to mimic a [Light-Emitting Diode (LED)](https://en.wikipedia.org/wiki/Light-emitting_diode).

The LED can be on, off, or blinking. The blink rate can also be adjusted.
The LED can be oriented UP, DOWN, LEFT, or RIGHT.
The LED can be any color, except black. Border and Fill are independent colors.

---
## Step 4: Create Attributes

In Android Studio's Project View, select the Android filter.

Right click ledlibrary/res/values.

Select menu option New | Values resource file

---
**New Resource File**

File name: attrs
Directory name: values

Click OK

---
modify the file:
```

<?xml version="1.0" encoding="utf-8"?>

<!-- ./src/main/res/values/attrs.xml -->

<resources>

<declare-styleable name="LEDView">
    <attr name="color_border" format="color" />
    <attr name="color_fill" format="color" />
    <attr name="light_off" format="boolean" />
    <attr name="light_on" format="boolean" />
    <attr name="blink_duration" format="integer" />
    <attr name="orientation" format="enum">
        <enum name="up" value="0" />
        <enum name="down" value="1" />
        <enum name="right" value="2" />
        <enum name="left" value="3" />
    </attr>
</declare-styleable>

</resources>

---
## Step 5: Create a View Class

Read: [Creating a View Class](https://developer.android.com/training/custom-views/create-view.html)

---
In Android Studio's Project View, under the Android filter, right click ledlibrary menu: New | Java Class

---
**Choose Destination Directory**

Select under Directory Structure tab:

ledlibrary
   .../ledlibrary/src/main/java

Click OK

---
**Create New Class**

Name: LEDView
Kind: Class
Superclass: android.view.View
Interfaces(s):
Package: com.example.mycompany.ledlibrary
Visibilty: Public
Modifiers: None

Show Select Overrides Dialog: Check On

Click OK

---
**Select Methods to Override/Implement**

Ctrl click onSizeChanged(), onDraw() and onMeasure() methods.

Insert @Override: Check On

Click OK

---
## Step 6: Read Attributes
---

Using the embeded tags<br />
```// <Add>```<br />
and <br />
```// </Add>``` <br />
modify the file:
```
// LEDExample/ledlibrary/src/main/java/com/example/mycompany/ledlibrary/LEDView.java

package com.example.mycompany.ledlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
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
                final int intOrientation = a.getInt(R.styleable.LEDView_orientation, orientation);
                final @Orientation int oOrientation = intOrientation;
                orientation = oOrientation;
            }
        } finally {
            a.recycle();
        }

        context_ = context;

        refreshDrawableState(); // Required if custom states are used.
    }

// <//Add>

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}

---
## Step 5: Custom Drawing

Read: [Custom Drawing](https://developer.android.com/training/custom-views/custom-drawing.html)

---
mPiePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
mPiePaint.setStyle(Paint.Style.FILL);

---
## Step 6: Add Dependency

TODO: ???? Move to after step 7 and Add Custom LEDView to Layout?

TODO: Why doesn't this happen automatically when adding a library?

Project view

right click app

Open Module Settings

Project structure dialog
Select - Modules: app
Dependencies Tab
+
Module dependency
:ledlibrary

Should change: /home/bessermt/dev/LEDExample/app/build.gradle


dependencies {
    compile fileTree(include: ['*.jar'], dir: 'libs')
    androidTestCompile('com.android.support.test.espresso:espresso-core:2.2.2', {
        exclude group: 'com.android.support', module: 'support-annotations'
    })
    compile 'com.android.support:appcompat-v7:25.3.1'
    compile 'com.android.support.constraint:constraint-layout:1.0.2'
    compile 'com.android.support:design:25.3.1'
    testCompile 'junit:junit:4.12'
    compile project(':ledlibrary') // *** Added ***
}

---
## Step 7: Add Custom LEDView to Layout

[Modifying an Existing View Type - Use the Custom Component](https://developer.android.com/guide/topics/ui/custom-components.html#modifying)

Add the following to fragment_main.xml

    <com.example.mycompany.ledlibrary.LEDView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent" />


