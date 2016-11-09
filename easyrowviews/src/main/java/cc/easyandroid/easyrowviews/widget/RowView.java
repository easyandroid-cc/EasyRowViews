package cc.easyandroid.easyrowviews.widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cc.easyandroid.easyrowviews.core.ClickFilter;
import cc.easyandroid.easyrowviews.core.Listener;
import cc.easyandroid.easyrowviews.core.Para;
import cc.easyandroid.easyrowviews.R;

public class RowView extends LinearLayout implements OnSharedPreferenceChangeListener {

    public static final String TAG = RowView.class.getSimpleName();

    protected SharedPreferences mSharedPreferences;

    protected String mKey;

    public CharSequence mTitle;

    public ImageView mRowViewIcon;

    public TextView mRowViewTitle;

    private Listener.OnRowViewClickListener listen;

    public RowView(Context context) {
        this(context, null);
    }

    public RowView(Context context, AttributeSet attrs) {
        this(context, attrs, R.styleable.RowViews_EasyRowViewStyle);
    }


    public RowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initRowView(context, attrs, defStyleAttr);
    }


    public void initRowView(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RowView, defStyleAttr, 0);

        int rowViewLayout = a.getResourceId(R.styleable.RowView_rowViewLayout, getRowViewLayout());// 正在加载的view

        LayoutInflater.from(context).inflate(rowViewLayout, this);

        initView();

        Drawable iconDrawable = a.getDrawable(R.styleable.RowView_rowViewIcon);// 正在加载的view

        CharSequence title = a.getText(R.styleable.RowView_rowViewTitle);// title

        setTitle(title);

        setIcon(iconDrawable);

        a.recycle();

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        setOrientation(VERTICAL);
        setOnClickListener(onClickListener);

        dispatchSetInitialValue();
    }

    protected int getRowViewLayout() {
        return R.layout.easy_default_rowview;
    }

    private void initView() {
        View rowViewIcon = findViewById(R.id.rowViewIcon);
        View rowViewTitle = findViewById(R.id.rowViewTitle);

        if (rowViewIcon != null) {
            mRowViewIcon = (ImageView) rowViewIcon;
        }
        if (rowViewTitle != null) {
            mRowViewTitle = (TextView) rowViewTitle;
        }
    }

    public void setKey(String key) {
        if (!TextUtils.isEmpty(key)) {
            this.mKey = key;
        }
    }

    public void setTitle(CharSequence title) {
        if (!TextUtils.isEmpty(title)) {
            this.mTitle = title;
            if (mRowViewTitle != null) {
                this.mRowViewTitle.setText(title);
            } else {
                Log.e(TAG, "mRowViewTitle is null");
            }

        }
    }


    /**
     * Checks whether this Preference has a valid key.
     *
     * @return True if the key exists and is not a blank string, false otherwise.
     */
    public boolean hasKey() {
        return !TextUtils.isEmpty(mKey);
    }

    public String getKey() {
        return mKey;
    }

    public CharSequence getTitle() {
        return mTitle;
    }

    public void setIcon(Drawable icon) {
        if (icon != null) {
            if (mRowViewIcon != null) {
                mRowViewIcon.setImageDrawable(icon);
            } else {
                Log.e(TAG, "mRowViewIcon is null");
            }
        }
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        PreferenceManager.getDefaultSharedPreferences(getContext()).registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        PreferenceManager.getDefaultSharedPreferences(getContext()).unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        // TODO:让子类根据自己需求重写
    }


    private void dispatchSetInitialValue() {
        if (!mSharedPreferences.contains(mKey)) {//没有保存过数据
            if (this.para != null && this.para.value != null) {
                onSetInitialValue(false, this.para.value);
            }
        } else {
            if (this.para != null && this.para.value != null) {
                onSetInitialValue(false, this.para.value);
            } else {
                onSetInitialValue(true, null);
            }
        }
    }


    public void onRowViewClick() {
        if (listen != null) {
            listen.onRowViewClick(this);
        }
    }


    OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ClickFilter.isDoubleClick()) {
                return;
            }
            onRowViewClick();
        }
    };

    public void setOnRowViewClickListener(Listener.OnRowViewClickListener listen) {
        this.listen = listen;
    }

    /**
     * 存储值
     *
     * @param value int
     * @return boolean
     */
    protected boolean persistInt(int value) {
        if (hasKey()) {
            if (value == getPersistedInt(~value)) {// 判断是不是保存了这个值，如果是，就不再保存（~value按位取反，防止和value相等）
                // It's already there, so the same as persisting
                return true;
            }
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putInt(mKey, value);
            tryCommit(editor, value);
            return true;
        }
        return false;
    }

    /**
     * 存储值
     *
     * @param value float
     * @return boolean
     */
    protected boolean persistFloat(float value) {
        if (hasKey()) {
            if (value == getPersistedFloat(value - 1)) {// 判断是不是保存了这个值，如果是，就不再保存（~value按位取反，防止和value相等）
                // It's already there, so the same as persisting
                return true;
            }
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putFloat(mKey, value);
            tryCommit(editor, value);
            return true;
        }
        return false;
    }

    /**
     * 存储值
     *
     * @param value String
     * @return boolean
     */
    protected boolean persistLong(long value) {
        if (hasKey()) {
            if (value == getPersistedLong(~value)) {// 判断是不是保存了这个值，如果是，就不再保存（~value按位取反，防止和value相等）
                // It's already there, so the same as persisting
                return true;
            }
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putLong(mKey, value);
            tryCommit(editor, value);
            return true;
        }
        return false;
    }

    private void tryCommit(SharedPreferences.Editor editor, Object value) {
        try {
            handlePara(value);
            editor.apply();
        } catch (AbstractMethodError unused) {
            editor.commit();
        }

    }

    private void handlePara(Object value) {
        if (para != null) {
            para.value = value;
        }
    }

    protected boolean persistString(String value) {
        if (hasKey()) {
            // Shouldn't store null
            if (value == getPersistedString(null)) {
                // It's already there, so the same as persisting
                return true;
            }

            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putString(mKey, value);
            tryCommit(editor, value);
            return true;
        }
        return false;
    }

    // private Object mDefaultValue;
    private Para<Object> para = null;//传递对象，方便调用的时候直接那实例

    /**
     * 初始化set数据
     *
     * @param restorePersistedValue restorePersistedValue
     * @param defaultValue          defaultValue
     */
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {

    }

    protected boolean persistBoolean(boolean value) {
        if (hasKey()) {
            if (value == getPersistedBoolean(!value)) {
                // It's already there, so the same as persisting
                return true;
            }
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putBoolean(mKey, value);
            tryCommit(editor, value);
            System.out.println("保存到xml  mKey=" + mKey);
            System.out.println("保存到value=" + value);

            Log.d(TAG, "mKey = " + mKey);
            Log.d(TAG, "value = " + value);
            return true;
        }
        return false;
    }

    /**
     * 获取sharedPreferences中保存的 boolean 值
     *
     * @param defaultReturnValue
     * @return boolean
     */
    protected boolean getPersistedBoolean(boolean defaultReturnValue) {

        return mSharedPreferences.getBoolean(mKey, defaultReturnValue);
    }

    /**
     * 获取sharedPreferences中保存的 String 值
     *
     * @param defaultReturnValue 默认
     * @return String
     */
    protected String getPersistedString(String defaultReturnValue) {

        return mSharedPreferences.getString(mKey, defaultReturnValue);
    }

    /**
     * 获取sharedPreferences中保存的 int 值
     *
     * @param defaultReturnValue 默认
     * @return int
     */
    protected int getPersistedInt(int defaultReturnValue) {

        return mSharedPreferences.getInt(mKey, defaultReturnValue);
    }

    /**
     * 获取sharedPreferences中保存的 Float 值
     *
     * @param defaultReturnValue 默认
     * @return float
     */
    protected float getPersistedFloat(float defaultReturnValue) {

        return mSharedPreferences.getFloat(mKey, defaultReturnValue);
    }

    /**
     * 获取sharedPreferences中保存的 long 值
     *
     * @param defaultReturnValue 默认
     * @return float
     */
    protected float getPersistedLong(long defaultReturnValue) {

        return mSharedPreferences.getLong(mKey, defaultReturnValue);
    }

    public void changValue(Object object) {
        onSetInitialValue(true, object);
    }

    /**
     * 赋值给控件
     */
    public void initData(Para para) {
        if (para != null) {
            this.para = para;
            setKey(para.key);
            dispatchSetInitialValue();
        }
    }

}
