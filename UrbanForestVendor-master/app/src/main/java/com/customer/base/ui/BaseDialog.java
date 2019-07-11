package com.customer.base.ui;

import android.app.Dialog;
import android.content.Context;

/**
 * Created by shubhamlamba on 24/05/17.
 */

public class BaseDialog extends Dialog {
    public BaseDialog(Context context) {
        super(context);
    }

    public BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected BaseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
