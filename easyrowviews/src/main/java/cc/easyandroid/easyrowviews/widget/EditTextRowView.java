package cc.easyandroid.easyrowviews.widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import cc.easyandroid.easyrowviews.core.Listener;
import cc.easyandroid.easyrowviews.R;


public class EditTextRowView extends DialogRowView {
    private TextView value_TextView;
    /**
     * The edit text shown in the dialog.
     */
    private EditText mEditText;

    private String mText;

    public EditTextRowView(Context context) {
        this(context, null);
    }

    public EditTextRowView(Context context, AttributeSet attrs) {
        this(context, attrs, R.styleable.RowViews_EasyRowViewStyle);
    }

    public EditTextRowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        mEditText = new EditText(context);

        // Give it an ID so it can be saved/restored
        mEditText.setId(android.R.id.edit);

		/*
         * The preference framework and view framework both have an 'enabled' attribute. Most likely, the 'enabled' specified in this XML is for the preference framework, but it was also given to the view framework. We reset the enabled state.
		 */
        mEditText.setEnabled(true);

        mEditText.setMinLines(2);

        mEditText.setGravity(Gravity.BOTTOM);

        value_TextView = (TextView) findViewById(R.id.rowViewValue);
    }


    ViewGroup container;

    @Override
    protected View onCreateDialogView() {
        container = new LinearLayout(getContext());
        return container;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (hasKey() && getKey().equals(key)) {

        }
    }

    @Override
    public void onRowViewClick() {
        //super.onRowViewClick();
        showDialog();
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
        if (positiveResult) {
            String value = mEditText.getText().toString();
            setText(value, true);
        }
    }

    public EditText getEditText() {
        return mEditText;
    }

    private void initValueData() {
        value_TextView.setText(getText());
    }

    /**
     * Saves the text to the {@link SharedPreferences}.
     *
     * @param text The text to save
     */
    public void setText(String text, boolean saveToSp) {
        if (valueChanged(text)) {
            mText = text;
            if (saveToSp) {
                persistString(text);//保存值  赋值
                if (onChangedListener != null) {
                    onChangedListener.onChanged(this);
                }
            }
            initValueData();
        }
    }

    Listener.OnChangedListener onChangedListener;

    public void setOnChangedListener(Listener.OnChangedListener onChangedListener) {
        this.onChangedListener = onChangedListener;
    }

    boolean valueChanged(String text) {
        return text != null && !text.equals(mText);
    }

    public String getText() {
        return mText;
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);

        EditText editText = mEditText;
        editText.setText(getText());

        ViewParent oldParent = editText.getParent();
        if (oldParent != view) {
            if (oldParent != null) {
                ((ViewGroup) oldParent).removeView(editText);
            }
            onAddEditTextToDialogView(view, editText);
        }
    }

    protected void onAddEditTextToDialogView(View dialogView, EditText editText) {
        if (container != null) {
            container.addView(editText, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    /**
     * 初始化set的值
     */
    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
        setText(restoreValue ? getPersistedString(mText) : defaultValue.toString(), false);
    }

    @Override
    protected boolean needInputMethod() {
        // We want the input method to show, if possible, when dialog is displayed
        return true;
    }


}
