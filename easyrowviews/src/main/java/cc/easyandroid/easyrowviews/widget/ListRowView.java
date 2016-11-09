package cc.easyandroid.easyrowviews.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import cc.easyandroid.easyrowviews.R;


public class ListRowView extends DialogRowView {
    private TextView value_TextView;
    private CharSequence[] mEntries;// 要显示的值
    private int[] mEntryValues;// 真正要保存的值
    private int mValue;// 当前 的值
    private int mClickedDialogEntryIndex;

    public ListRowView(Context context) {
        this(context, null);
    }

    public ListRowView(Context context, AttributeSet attrs) {
        this(context, attrs, R.styleable.RowViews_EasyRowViewStyle);
    }

    public ListRowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ListRowView, defStyleAttr, 0);
        mEntries = a.getTextArray(R.styleable.ListRowView_entries);
        mEntryValues = a.getResources().getIntArray(a.getResourceId(R.styleable.ListRowView_entryValues, 0));

        a.recycle();

        value_TextView = (TextView) findViewById(R.id.rowViewValue);
    }


    /**
     * 根据值 查找位置index
     *
     * @param value
     * @return
     */
    public int findIndexOfValue(int value) {
        if (mEntryValues != null) {
            for (int i = mEntryValues.length - 1; i >= 0; i--) {
                if (mEntryValues[i] == value) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * 获取当前显示的值
     *
     * @return
     */
    public CharSequence getEntry() {
        int index = getValueIndex();
        return index >= 0 && mEntries != null ? mEntries[index] : null;
    }

    /**
     * 获取当前值的index
     *
     * @return
     */
    private int getValueIndex() {
        return findIndexOfValue(mValue);
    }

    /**
     * 设置真正要保存的值的数组
     *
     * @param entryValues
     */
    public ListRowView setEntryValues(int... entryValues) {
        mEntryValues = entryValues;
        return this;
    }

    /**
     * Sets the value of the key. This should be one of the entries in
     * {@link #getEntryValues()}.
     *
     * @param value The value to set for the key.
     */
    public void setValue(int value, boolean saveToSp) {
        if (mValue != value) {
            mValue = value;
            if (saveToSp) {
                persistInt(value);
            }
            setValueData();
        }
    }

    public int[] getEntryValues() {
        return mEntryValues;
    }

    public ListRowView setEntries(CharSequence... entries) {
        mEntries = entries;
        return this;
    }

    public CharSequence[] getEntries() {
        return mEntries;
    }

    @Override
    protected void onPrepareDialogBuilder(AlertDialog.Builder builder) {
        super.onPrepareDialogBuilder(builder);
        if (mEntries == null || mEntryValues == null) {
            throw new IllegalStateException("ListPreference requires an entries array and an entryValues array.");
        }

        mClickedDialogEntryIndex = getValueIndex();
        builder.setSingleChoiceItems(mEntries, mClickedDialogEntryIndex, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mClickedDialogEntryIndex = which;

				/*
                 * Clicking on an item simulates the positive button click, and
				 * dismisses the dialog.
				 */
                ListRowView.this.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                dialog.dismiss();
            }
        });
        // 不要确定按钮
        builder.setPositiveButton(null, null);
    }

    /**
     * dialog 关闭后执行
     */
    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
        if (positiveResult && mClickedDialogEntryIndex >= 0 && mEntryValues != null) {
            int value = mEntryValues[mClickedDialogEntryIndex];
            setValue(value, true);
        }
    }

    @Override
    public void onRowViewClick() {
//        super.onRowViewClick();
        showDialog();
    }

    private void setValueData() {
        value_TextView.setText(getEntry());
    }

    /**
     * 初始化set的值
     */
    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
        setValue(restoreValue ? getPersistedInt(mValue) : (Integer) defaultValue, false);
    }
}
