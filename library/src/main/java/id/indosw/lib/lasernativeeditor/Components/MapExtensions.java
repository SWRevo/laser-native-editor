package id.indosw.lib.lasernativeeditor.Components;

import android.app.Activity;
import android.text.Editable;
import android.view.View;
import android.widget.ImageView;

import org.jsoup.nodes.Element;

import java.util.Objects;

import id.indosw.lib.lasernativeeditor.EditorComponent;
import id.indosw.lib.lasernativeeditor.EditorCore;
import id.indosw.lib.lasernativeeditor.R;
import id.indosw.lib.lasernativeeditor.Utilities.Utilities;
import id.indosw.lib.lasernativeeditor.models.EditorContent;
import id.indosw.lib.lasernativeeditor.models.EditorControl;
import id.indosw.lib.lasernativeeditor.models.EditorType;
import id.indosw.lib.lasernativeeditor.models.Node;
import id.indosw.lib.lasernativeeditor.models.RenderType;

/**
 * Created by mkallingal on 5/1/2016.
 */
@SuppressWarnings("unused")
public class MapExtensions extends EditorComponent {
    EditorCore editorCore;
    private int mapExtensionTemplate= R.layout.tmpl_image_view;

    @Override
    public Node getContent(View view) {
        Node node = getNodeInstance(view);
        EditorControl mapTag = (EditorControl) view.getTag();
        Editable desc = ((CustomEditText) view.findViewById(R.id.desc)).getText();
        node.content.add(mapTag.Cords);
        node.content.add(Objects.requireNonNull(desc).length() > 0 ? desc.toString() : "");
        return node;
    }

    @Override
    public String getContentAsHTML(Node node, EditorContent content) {
      return componentsWrapper.getHtmlExtensions().getTemplateHtml(node.type).replace("{{$content}}",
              componentsWrapper.getMapExtensions().getCordsAsUri(node.content.get(0))).replace("{{$desc}}", node.content.get(1));
    }

    @Override
    public void renderEditorFromState(Node node, EditorContent content) {
        insertMap(node.content.get(0), node.content.get(1), true);
    }

    @Override
    public Node buildNodeFromHTML(Element element) {
        return null;
    }

    @Override
    public void init(ComponentsWrapper componentsWrapper) {
        this.componentsWrapper = componentsWrapper;
    }

    public MapExtensions(EditorCore editorCore){
        super(editorCore);
        this.editorCore = editorCore;
    }

    public void setMapViewTemplate(int drawable)
    {
        this.mapExtensionTemplate= drawable;
    }



    @SuppressWarnings("StringBufferReplaceableByString")
    public String getMapStaticImgUri(String cords, int width){
        StringBuilder builder = new StringBuilder();
        builder.append("http://maps.google.com/maps/api/staticmap?");
        builder.append("size=").append(width).append("x400&zoom=15&sensor=true&markers=").append(cords);
        return builder.toString();
    }

    public void insertMap(String cords, String desc, boolean insertEditText) {
        String[] x= cords.split(",");
        String lat = x[0];
        String lng = x[1];
        int[]size= Utilities.getScreenDimension(editorCore.getContext());
        int width=size[0];

        final View childLayout = ((Activity) this.editorCore.getContext()).getLayoutInflater().inflate(this.mapExtensionTemplate, null);
        ImageView imageView = childLayout.findViewById(R.id.imageView);
        componentsWrapper.getImageExtensions().loadImageUsingLib(getMapStaticImgUri(lat +","+ lng,width), imageView);

        CustomEditText editText = childLayout.findViewById(R.id.desc);
        if(editorCore.getRenderType()== RenderType.Renderer){
            editText.setText(desc);
            editText.setEnabled(false);
        }

        final View btn =  childLayout.findViewById(R.id.btn_remove);
        imageView.setOnClickListener(v -> btn.setVisibility(View.VISIBLE));
        imageView.setOnFocusChangeListener((v, hasFocus) -> btn.setVisibility(hasFocus ? View.VISIBLE : View.INVISIBLE));
        btn.setOnClickListener(v -> editorCore.getParentView().removeView(childLayout));
        EditorControl control = editorCore.createTag(EditorType.map);
        control.Cords= cords;
        childLayout.setTag(control);
        int Index= editorCore.determineIndex(EditorType.map);
        editorCore.getParentView().addView(childLayout, Index);
        if(insertEditText){
          componentsWrapper.getInputExtensions().insertEditText(Index + 1, null, null);
        }
    }

    public void loadMapActivity(){
    }

    public CharSequence getCordsAsUri(String s) {
        return getMapStaticImgUri(s,800);
    }
}
