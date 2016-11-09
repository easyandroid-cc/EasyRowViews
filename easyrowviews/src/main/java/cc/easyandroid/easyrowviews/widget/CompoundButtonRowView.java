package cc.easyandroid.easyrowviews.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CompoundButton;

import cc.easyandroid.easyrowviews.core.Listener;
import cc.easyandroid.easyrowviews.R;


public class CompoundButtonRowView extends RowView {
    protected CompoundButton compoundButton;
    private boolean mChecked = false;// 当前值

    public CompoundButtonRowView(Context context) {
        this(context, null);
    }

    public CompoundButtonRowView(Context context, AttributeSet attrs) {
        this(context, attrs, R.styleable.RowViews_EasyRowViewStyle);
    }

    public CompoundButtonRowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        compoundButton = (CompoundButton) findViewById(R.id.rowViewWidget);
        compoundButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                System.out.println("isChecked=" + isChecked);
                setChecked(isChecked, true);
            }
        });
    }

    @Override
    public void onRowViewClick() {
        simulationCheckBoxClick();
        //super.onRowViewClick();;//这里不调用父类点击方法，交给checkBox点击时间调用
    }

    // 模拟CheckBox 点击
    public void simulationCheckBoxClick() {
        if (compoundButton != null) {
            compoundButton.toggle();
        }
    }

    /**
     * Returns the checked state.
     *
     * @return The checked state.
     */
    public boolean isChecked() {
        return mChecked;
    }

    /**
     * 设置状态
     *
     * @param checked  状态
     * @param saveToSp 是否要保存到sp
     */
    public void setChecked(boolean checked, boolean saveToSp) {
        if (mChecked != checked) {
            mChecked = checked;
            if (saveToSp) {
                persistBoolean(checked);
                if (onChangedListener != null) {
                    onChangedListener.onChanged(this);
                }
            }
            initCheckBoxData();
        }
    }

    Listener.OnChangedListener onChangedListener;

    public void setOnChangedListener(Listener.OnChangedListener onChangedListener) {
        this.onChangedListener = onChangedListener;
    }

    private void initCheckBoxData() {
        compoundButton.setChecked(mChecked);

    }

    /**
     * 初始化set的值，这里返回的值不保存到sp
     */
    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
        setChecked(restoreValue ? getPersistedBoolean(mChecked) : (Boolean) defaultValue, false);
    }

}
