package id.indosw.lib.lasernativeeditor.Components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;

import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;


@SuppressWarnings("unused")
public class CustomEditText extends TextInputEditText {
    public static final int KEYCODE_REMOVE=100;
    public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomEditText(Context context) {
        super(context);
    }



    @Override
    public InputConnection onCreateInputConnection(@NotNull EditorInfo outAttrs) {
        return new CustomInputConnection(super.onCreateInputConnection(outAttrs),
                true);
    }

    private class CustomInputConnection extends InputConnectionWrapper {

        public CustomInputConnection(InputConnection target, boolean mutable) {
            super(target, mutable);
        }

        @Override
        public boolean sendKeyEvent(KeyEvent event) {
           if(event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                return super.sendKeyEvent(event);
            }else if(event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
               return super.sendKeyEvent(event);
           }
            return false;
        }


        @SuppressWarnings("UnnecessaryLocalVariable")
        @Override
        public boolean deleteSurroundingText(int beforeLength, int afterLength) {
            // magic: in latest Android, deleteSurroundingText(1, 0) will be called for backspace
            if (beforeLength == 1 && afterLength == 0) {
                // backspace
                int len = Objects.requireNonNull(getText()).length();
                if(len==0){
                    boolean isBackspace = sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL))
                            && sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DEL));
                    return isBackspace;
                }
                int selection= getSelectionStart();
                if(selection==0)
                    return false;
            }
            return super.deleteSurroundingText(beforeLength, afterLength);
        }

    }
}

