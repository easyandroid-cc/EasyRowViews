package cc.easyandroid.easyrowviews.core;


import cc.easyandroid.easyrowviews.widget.RowView;

public class Listener {

    public interface OnRowViewClickListener {
        void onRowViewClick(RowView rowView);
    }

    public interface OnChangedListener {
        void onChanged(RowView rowView);
    }
}
