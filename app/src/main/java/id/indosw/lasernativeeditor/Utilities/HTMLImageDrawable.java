package id.indosw.lasernativeeditor.Utilities;

import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

@SuppressWarnings("deprecation")
public class HTMLImageDrawable extends BitmapDrawable {

    protected Drawable drawable;

    @Override
    public void draw(Canvas canvas) {
        if (drawable != null) {
            drawable.draw(canvas);
        }
    }
}
