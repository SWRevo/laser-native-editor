package id.indosw.lib.lasernativeeditor;

import android.graphics.Bitmap;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import java.util.Map;

public interface EditorListener{
    void onTextChanged(EditText editText, Editable text);
    void onUpload(Bitmap image, String uuid);
    View onRenderMacro(String name, Map<String, Object> props, int index);
}