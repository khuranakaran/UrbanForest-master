package com.customer.base.ui.dialog_builder;

import android.content.Context;

public class Dialog {

    private Context context;
    private int drawable;
    private String text;
    private String btnText;
    private boolean isKnowMoreVisible;
    private String header;
    private int headerBackgroundColor;
    private String activity, cancelActivity, blueButtonText, homeActivity;


    public Dialog(DialogBuilder dialogBuilder) {
        this.context = context;
        this.drawable = drawable;
        this.text = text;
        this.btnText = btnText;
        this.isKnowMoreVisible = isKnowMoreVisible;
        this.header = header;
        this.headerBackgroundColor = headerBackgroundColor;
        this.activity = activity;
        this.cancelActivity = cancelActivity;
        this.blueButtonText = blueButtonText;
        this.homeActivity = homeActivity;
    }

    public static class DialogBuilder {

        private Context context;
        private int drawable;
        private String text;
        private String btnText;
        private boolean isKnowMoreVisible;
        private String header;
        private int headerBackgroundColor;
        private String activity, cancelActivity, blueButtonText, homeActivity;

        public DialogBuilder() {

        }

        public DialogBuilder with(Context context) {
            this.context = context;
            return this;
        }

        public DialogBuilder icon(int drawable) {
            this.drawable = drawable;
            return this;
        }

        public DialogBuilder setHeaderBackgroundColor(int headerBackgroundColor) {
            this.headerBackgroundColor = headerBackgroundColor;
            return this;
        }


        public DialogBuilder setHeader(String header) {
            this.header = header;
            return this;
        }

        public DialogBuilder text(String text) {
            this.text = text;
            return this;
        }

        public DialogBuilder isKnowMoreVisible(boolean isKnowMoreVisible) {
            this.isKnowMoreVisible = isKnowMoreVisible;
            return this;

        }

        public DialogBuilder buttonText(String btnText) {
            this.btnText = btnText;
            return this;
        }

        public DialogBuilder openActivityOnGreyButtonClicked(String activity) {
            this.activity = activity;
            return this;
        }

        public DialogBuilder homeActivity(String homeActivity) {
            this.homeActivity = homeActivity;
            return this;
        }


        public DialogBuilder openActivityOnBlueButtonClicked(String cancelActivity) {
            this.cancelActivity = cancelActivity;

            return this;
        }

        public DialogBuilder setBlueButtonText(String blueButtonText) {
            this.blueButtonText = blueButtonText;

            return this;
        }


        public Dialog build() {
            return new Dialog(this);
        }
    }

    private Class<?> getActivityFromClassName(String activity) {

        Class<?> c = null;
        try {
            c = Class.forName(activity);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return c;
    }
}