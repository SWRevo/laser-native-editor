package id.indosw.lib.lasernativeeditor.Components;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.indosw.lib.lasernativeeditor.EditorComponent;
import id.indosw.lib.lasernativeeditor.EditorCore;
import id.indosw.lib.lasernativeeditor.R;
import id.indosw.lib.lasernativeeditor.Utilities.Utilities;
import id.indosw.lib.lasernativeeditor.models.EditorContent;
import id.indosw.lib.lasernativeeditor.models.EditorControl;
import id.indosw.lib.lasernativeeditor.models.EditorType;
import id.indosw.lib.lasernativeeditor.models.Node;
import id.indosw.lib.lasernativeeditor.models.RenderType;

public class MacroExtensions extends EditorComponent {
    private final EditorCore editorCore;

    public MacroExtensions(EditorCore editorCore) {
        super(editorCore);
        this.editorCore = editorCore;
    }

    @SuppressLint("ClickableViewAccessibility")
    public void insertMacro(String name, View view, Map<String, Object> settings, int index) {
        final FrameLayout frameLayout = new FrameLayout(editorCore.getContext());
        frameLayout.addView(view);
        final FrameLayout overlay = new FrameLayout(frameLayout.getContext());
        overlay.setVisibility(View.GONE);
        overlay.setPadding(0, 0, 20, 0);
        overlay.setBackgroundColor(Color.argb(50, 0, 0, 0));
        ImageView imageView = new ImageView(overlay.getContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(Utilities.dpToPx(frameLayout.getContext(), 40), Utilities.dpToPx(frameLayout.getContext(), 40));
        params.gravity = Gravity.END | Gravity.CENTER_VERTICAL;
        imageView.setLayoutParams(params);
        imageView.setImageResource(R.drawable.ic_close_white_36dp);
        overlay.addView(imageView);
        frameLayout.addView(overlay);
        imageView.setOnClickListener(view13 -> editorCore.getParentView().removeView(frameLayout));

        EditorControl control = editorCore.createTag(EditorType.macro);
        control.macroSettings = settings;
        control.macroName = name;
        if (index == -1) {
            index = editorCore.determineIndex(EditorType.macro);
        }
        frameLayout.setTag(control);

        editorCore.getParentView().addView(frameLayout, index);

        if(editorCore.getRenderType() == RenderType.Renderer) return;

        view.setOnTouchListener((view12, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                int paddingTop = view12.getPaddingTop();
                int paddingBottom = view12.getPaddingBottom();
                int height = view12.getHeight();
                if (event.getY() < paddingTop) {
                    editorCore.___onViewTouched(0, editorCore.getParentView().indexOfChild(frameLayout));
                }
                else if (event.getY() > height - paddingBottom) {
                    editorCore.___onViewTouched(1,  editorCore.getParentView().indexOfChild(frameLayout));
                } else {
                    if (overlay.getVisibility() == View.VISIBLE) {
                        overlay.setVisibility(View.GONE);
                    } else {
                        overlay.setVisibility(View.VISIBLE);
                    }
                }
                return false;
            }
            return true;
        });

        frameLayout.setOnFocusChangeListener((view1, b) -> {
            if(!b){
                overlay.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public Node getContent(View view) {
        Node node = this.getNodeInstance(view);
        EditorControl macroTag = (EditorControl) view.getTag();
        node.content.add(macroTag.macroName);
        node.macroSettings = macroTag.macroSettings;
        return node;
    }

    @Override
    public String getContentAsHTML(Node node, EditorContent content) {
        return getAsHtml(node.content.get(0), node.macroSettings);
    }

    private String getAsHtml(String name, Map<String, Object> macroSettings){
        String template = "<{{$tag}} data-tag=\"macro\" {{$settings}}></{{$tag}}>";
        template = template.replace("{{$tag}}", name);
        StringBuilder dataTags = new StringBuilder();
        for (Map.Entry<String, Object> item : macroSettings.entrySet()) {

            if(item.getKey().equalsIgnoreCase("data-tag")) continue;


            dataTags.append(" ");
            if(item.getKey().contains("data-")){
                dataTags.append(item.getKey());
            }else {
                dataTags.append("data-").append(item.getKey());
            }
            dataTags.append("=\"").append(item.getValue()).append("\"");
        }
        if (TextUtils.isEmpty(dataTags)) {
            template = template.replace("{{$settings}}", "");
        } else {
            template = template.replace("{{$settings}}", dataTags.toString());
        }
        return template;
    }

    @Override
    public void renderEditorFromState(Node node, EditorContent content) {
        int index = editorCore.getChildCount();
        View view = editorCore.getEditorListener().onRenderMacro(node.content.get(0), node.macroSettings, editorCore.getChildCount());

        if(view == null)
            view = getEmptyMacro(node.content.get(0),node.macroSettings);

        insertMacro(node.content.get(0), view, node.macroSettings, index);
    }

    @SuppressLint("SetTextI18n")
    private View getEmptyMacro(String name, Map<String, Object> macroSettings){
        @SuppressLint("InflateParams") final View layout = ((Activity) editorCore.getContext()).getLayoutInflater().inflate(R.layout.default_macro, null);
        TextView message = layout.findViewById(R.id.txtMessage);
        message.setText("Unhandled macro "+ "\""+getAsHtml(name,macroSettings)+"\"");
        return layout;
    }

    @Override
    public Node buildNodeFromHTML(Element element) {
        String tag = element.tagName().toLowerCase();
        Node node = getNodeInstance(EditorType.macro);
        node.content.add(tag);

        List<Attribute> attrs = element.attributes().asList();

        if (!attrs.isEmpty()) {
            node.macroSettings = new HashMap<>();
            for (Attribute attr : attrs) {
                node.macroSettings.put(attr.getKey(), attr.getValue());
            }
        }
        int index = editorCore.getChildCount();
        View view = editorCore.getEditorListener().onRenderMacro(tag, node.macroSettings, editorCore.getChildCount());

        if(view == null)
            view = getEmptyMacro(node.content.get(0), node.macroSettings);

        insertMacro(tag, view, node.macroSettings, index);
        return null;
    }

    @Override
    public void init(ComponentsWrapper componentsWrapper) {
        this.componentsWrapper = componentsWrapper;
    }

}
