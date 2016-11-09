package cc.easyandroid.easycommonviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import cc.easyandroid.easyrowviews.widget.CheckBoxRowView;
import cc.easyandroid.easyrowviews.widget.EditTextRowView;
import cc.easyandroid.easyrowviews.widget.ListRowView;
import cc.easyandroid.easyrowviews.widget.SwitchRowView;

public class MainActivity extends AppCompatActivity {
    ListRowView listrowView;
    EditTextRowView editTextRowView;
    CheckBoxRowView checkBoxRowView;
    SwitchRowView switchRowView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SettingParaSetting.ParaUtil.initPrar(this);
        listrowView= (ListRowView) findViewById(R.id.listrowView);
        listrowView.initData(SettingParaSetting.DISPALY_PERCENTAGE);

        editTextRowView= (EditTextRowView) findViewById(R.id.editTextRowView);
        editTextRowView.initData(SettingParaSetting.DISPALY_PERCENTAGE1);

        checkBoxRowView= (CheckBoxRowView) findViewById(R.id.checkBoxRowView);
        checkBoxRowView.initData(SettingParaSetting.DISPALY_PERCENTAGE3);

        switchRowView= (SwitchRowView) findViewById(R.id.switchRowView);
        switchRowView.initData(SettingParaSetting.DISPALY_PERCENTAGE4);


//        listrowView.notifyDataChanged();
    }


}
