package id.indosw.lasernativeeditor;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import id.indosw.lib.lasernativeeditor.Editor;

public class HTMLRenderedFragment extends Fragment {
    private static final String SERIALIZED = "";

    private String mSerializedHtml;

    public HTMLRenderedFragment() {
    }

    public static HTMLRenderedFragment newInstance(String serialized) {
        HTMLRenderedFragment fragment = new HTMLRenderedFragment();
        Bundle args = new Bundle();
        args.putString(SERIALIZED, serialized);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // TODO: Rename and change types of parameters
            String mSerialized = getArguments().getString(SERIALIZED);
            Editor editor=new Editor(getContext(),null);
            mSerializedHtml= editor.getContentAsHTML(mSerialized);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_htmlrendered, container, false);
        ((TextView)view.findViewById(R.id.lblHtmlRendered)).setText(mSerializedHtml);
        return  view;
    }

}
