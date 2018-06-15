package com.demo.clock;

import android.app.AlertDialog;
import android.content.Context;

/**
 * Created by hayden on 18-6-9.
 */

public class MyDialog extends AlertDialog {


    protected MyDialog(Context context) {
        super(context);
    }

    protected MyDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    protected MyDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public Builder getfewrfewr(Context context){
        return new Builder(context);
    }
}
