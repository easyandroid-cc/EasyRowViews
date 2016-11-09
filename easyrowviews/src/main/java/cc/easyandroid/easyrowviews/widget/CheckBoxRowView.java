package cc.easyandroid.easyrowviews.widget;

import android.content.Context;
import android.util.AttributeSet;

import cc.easyandroid.easyrowviews.R;


public class CheckBoxRowView extends CompoundButtonRowView {

    public CheckBoxRowView(Context context) {
        this(context, null);
    }

    public CheckBoxRowView(Context context, AttributeSet attrs) {
        this(context, attrs, R.styleable.RowViews_EasyRowViewStyle);
    }

    public CheckBoxRowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getRowViewLayout() {
        return R.layout.easy_check_rowview;
    }
}
