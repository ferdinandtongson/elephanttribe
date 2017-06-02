package me.makeachoice.elephanttribe.utilities;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;

/**
 * Created by tongson on 4/20/17.
 */

public class DeprecatedUtility {

    @TargetApi(23)
    public static int getColor(Context context, int colorId){
        final int version = Build.VERSION.SDK_INT;

        if(version >= 23){
            return context.getColor(colorId);
        }else{
            return context.getResources().getColor(colorId);
        }
    }

    @TargetApi(21)
    public static Drawable getDrawable(Context context, int drawableId){
        final int version = Build.VERSION.SDK_INT;

        if(version >= 21){
            return context.getDrawable(drawableId);
        }else{
            return context.getResources().getDrawable(drawableId);
        }
    }

}
