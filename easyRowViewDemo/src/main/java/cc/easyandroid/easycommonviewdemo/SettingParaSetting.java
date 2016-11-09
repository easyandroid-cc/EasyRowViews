package cc.easyandroid.easycommonviewdemo;

import android.content.Context;

import cc.easyandroid.easyrowviews.core.IsPara;
import cc.easyandroid.easyrowviews.core.Para;
import cc.easyandroid.easyrowviews.core.SettingsUtil;

public interface SettingParaSetting {
    @IsPara
    Para<Integer> DISPALY_PERCENTAGE = new Para<Integer>("dispaly_percentage", 2);
    @IsPara
    Para<String> DISPALY_PERCENTAGE1 = new Para<String>("dispaly_percentage1", "1");
    @IsPara
    Para<Boolean> DISPALY_PERCENTAGE3 = new Para<Boolean>("dispaly_percentage3", true);
    @IsPara
    Para<Boolean> DISPALY_PERCENTAGE4= new Para<Boolean>("dispaly_percentage4", true);

    class ParaUtil {
        public static void initPrar(Context context) {
            SettingsUtil.initPrar(context, SettingParaSetting.class);
        }
    }
}
