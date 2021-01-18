package id.indosw.lasernativeeditor;

import android.app.Application;

public class MyApplication extends Application {

    private String someVariable;

    public String getSomeVariable() {
        return someVariable;
    }

    public void setSomeVariable(String someVariable) {
        this.someVariable = someVariable;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}