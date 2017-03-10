package com.example.posmedicine;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by foram on 20/2/17.
 */

public class TextView_Poiret_One extends TextView {

    public TextView_Poiret_One(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TextView_Poiret_One(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextView_Poiret_One(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/PoiretOne-Regular.ttf");
            setTypeface(tf);
        }
    }

}
