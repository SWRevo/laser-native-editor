package id.indosw.lasernativeeditor.Utilities;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.widget.EditText;

@SuppressWarnings("unused")
public class MyTextWatcher implements TextWatcher {
    public EditText editText;
    //constructor
    @SuppressLint("SetTextI18n")
    public MyTextWatcher(EditText et){
        super();
        editText = et;
        editText.setOnKeyListener((v, keyCode, event) -> {
            if(keyCode == KeyEvent.KEYCODE_DEL){
                editText.setText("yippe k");
            }
            return false;
        });
    }
    //Some manipulation with text
    @SuppressLint("SetTextI18n")
    public void afterTextChanged(Editable s) {
        if(editText.getText().length() == 12){
            editText.setText(editText.getText().delete(editText.getText().length() - 1, editText.getText().length()));
            editText.setSelection(editText.getText().toString().length());
        }
        if (editText.getText().length()==2||editText.getText().length()==5||editText.getText().length()==8){
            editText.setText(editText.getText()+"/");
            editText.setSelection(editText.getText().toString().length());
        }
    }
    public void beforeTextChanged(CharSequence s, int start, int count, int after){
    }
    public void onTextChanged(CharSequence s, int start, int before, int count) {



    }
}