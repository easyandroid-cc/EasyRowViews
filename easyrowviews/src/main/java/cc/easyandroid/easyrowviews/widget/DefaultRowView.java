package cc.easyandroid.easyrowviews.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import cc.easyandroid.easyrowviews.R;


/**
 * 点击的item
 *
 * @author aaa
 */
public class DefaultRowView extends RowView {

    private TextView value_TextView;

    public DefaultRowView(Context context) {
        this(context, null);
    }

    public DefaultRowView(Context context, AttributeSet attrs) {
        this(context, attrs, R.styleable.RowViews_EasyRowViewStyle);
    }

    public DefaultRowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        value_TextView = (TextView) findViewById(R.id.rowViewValue);
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
        super.onSetInitialValue(restorePersistedValue, defaultValue);
        value_TextView.setText((String) defaultValue);// 这里回调回来设置的默认值
    }

    @Override
    protected int getRowViewLayout() {
        return R.layout.easy_default_rowview;
    }

}
