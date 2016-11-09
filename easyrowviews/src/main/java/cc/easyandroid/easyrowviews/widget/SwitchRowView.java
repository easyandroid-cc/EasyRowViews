package cc.easyandroid.easyrowviews.widget;

import android.content.Context;
import android.util.AttributeSet;

import cc.easyandroid.easyrowviews.R;


public class SwitchRowView extends CompoundButtonRowView {
    public SwitchRowView(Context context) {
        this(context, null);
    }

    public SwitchRowView(Context context, AttributeSet attrs) {
        this(context, attrs, R.styleable.RowViews_EasyRowViewStyle);
    }

    public SwitchRowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getRowViewLayout() {
        return R.layout.easy_switch_rowview;
    }
}
