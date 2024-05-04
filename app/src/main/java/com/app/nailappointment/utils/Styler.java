package com.app.nailappointment.utils;

import android.graphics.Paint;
import android.widget.Button;

public class Styler {

    public static void underlineButton(Button button) {
        button.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        button.setBackgroundResource(0);
    }

}
