package id.indosw.lasernativeeditor;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class SerializedFragment extends Fragment {
    private static final String SERIALIZED = "";

    private String mSerialized;

    public SerializedFragment() {
    }

    public static SerializedFragment newInstance(String serialized) {
        SerializedFragment fragment = new SerializedFragment();
        Bundle args = new Bundle();
        args.putString(SERIALIZED, serialized);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSerialized = getArguments().getString(SERIALIZED);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_serialized, container, false);
        ((TextView)view.findViewById(R.id.lblRendered)).setText(mSerialized);
        return view;
    }

}
