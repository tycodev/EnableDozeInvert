package com.dozeedits.enableDozeInvert;

import android.view.View;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
import de.robv.android.xposed.XposedHelpers;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class Main implements IXposedHookLoadPackage {

    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {

        if (!lpparam.packageName.equals("com.android.systemui"))
            return;

        findAndHookMethod("com.android.systemui.ViewInvertHelper", lpparam.classLoader, "update", Boolean.TYPE, new XC_MethodReplacement() {
            @Override
            protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
               Object mTargetHooked = XposedHelpers.getObjectField(param.thisObject, "mTarget");
               ((View)mTargetHooked).setLayerType(View.LAYER_TYPE_NONE, null); 
               return null;
            }
        });
    } // End Hooks
}
