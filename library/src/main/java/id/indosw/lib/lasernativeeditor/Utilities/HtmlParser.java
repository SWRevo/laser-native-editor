package id.indosw.lib.lasernativeeditor.Utilities;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import id.indosw.lib.lasernativeeditor.models.HtmlTag;


@SuppressWarnings({"unused", "FieldCanBeLocal", "FieldMayBeFinal"})
public class HtmlParser {
    private Context context;
    LinearLayout parentView;
    public HtmlParser(Context _context){
        this.context=_context;
        parentView=new LinearLayout(this.context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        parentView.setLayoutParams(params);
    }



    public static boolean matchesTag(String test) {
        for (HtmlTag tag : HtmlTag.values()) {
            if (tag.name().equals(test)) {
                return true;
            }
        }
        return false;
    }
}

