package id.indosw.lib.lasernativeeditor.Components;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import org.jsoup.nodes.Element;

import id.indosw.lib.lasernativeeditor.EditorComponent;
import id.indosw.lib.lasernativeeditor.EditorCore;
import id.indosw.lib.lasernativeeditor.R;
import id.indosw.lib.lasernativeeditor.models.EditorContent;
import id.indosw.lib.lasernativeeditor.models.EditorType;
import id.indosw.lib.lasernativeeditor.models.Node;
import id.indosw.lib.lasernativeeditor.models.RenderType;

@SuppressWarnings("UnnecessaryLocalVariable")
public class DividerExtensions extends EditorComponent {
    private int dividerLayout = R.layout.tmpl_divider_layout;
    EditorCore editorCore;

    @Override
    public Node getContent(View view) {
        Node node = this.getNodeInstance(view);
        return node;
    }

    @Override
    public String getContentAsHTML(Node node, EditorContent content) {
        return componentsWrapper.getHtmlExtensions().getTemplateHtml(EditorType.hr);
    }

    @Override
    public void renderEditorFromState(Node node, EditorContent content) {
        insertDivider(content.nodes.indexOf(node));
    }

    @Override
    public Node buildNodeFromHTML(Element element) {
        int count = editorCore.getChildCount();
        insertDivider(count);
        return null;
    }

    @Override
    public void init(ComponentsWrapper componentsWrapper) {
        this.componentsWrapper = componentsWrapper;
    }


    public DividerExtensions(EditorCore editorCore) {
        super(editorCore);
        this.editorCore = editorCore;
    }

    public void setDividerLayout(int layout) {
        this.dividerLayout = layout;
    }

    @SuppressLint("ClickableViewAccessibility")
    public void insertDivider(int index) {
        View view = ((Activity) editorCore.getContext()).getLayoutInflater().inflate(this.dividerLayout, null);
        view.setTag(editorCore.createTag(EditorType.hr));
        if (index == -1) {
            index = editorCore.determineIndex(EditorType.hr);
        }
        if (index == 0) {
            Toast.makeText(editorCore.getContext(), "divider cannot be inserted on line zero", Toast.LENGTH_SHORT).show();
            return;
        }
        editorCore.getParentView().addView(view, index);

        if (editorCore.getRenderType() == RenderType.Editor) {

            if (editorCore.getControlType(editorCore.getParentView().getChildAt(index + 1 )) == EditorType.INPUT) {
                CustomEditText customEditText = (CustomEditText) editorCore.getChildAt(index + 1 );
                componentsWrapper.getInputExtensions().removeFocus(customEditText);
            }
            view.setOnTouchListener((view1, event) -> {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    int paddingTop = view1.getPaddingTop();
                    int paddingBottom = view1.getPaddingBottom();
                    int height = view1.getHeight();
                    if (event.getY() < paddingTop) {
                        editorCore.___onViewTouched(0, editorCore.getParentView().indexOfChild(view1));
                    } else if (event.getY() > height - paddingBottom) {
                        editorCore.___onViewTouched(1, editorCore.getParentView().indexOfChild(view1));
                    }
                    return false;
                }
                return true;
            });

            View focus = editorCore.getActivity().getCurrentFocus();
            if (focus != null) {
                InputMethodManager imm = (InputMethodManager)editorCore.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                if(focus instanceof CustomEditText){
                    CustomEditText editText = (CustomEditText)focus;
                    editText.clearFocus();
                    editorCore.getParentView().requestFocus();
                }
            }

        }
    }

    @SuppressWarnings("unused")
    public boolean deleteHr(int indexOfDeleteItem) {
        View view = editorCore.getParentView().getChildAt(indexOfDeleteItem);
        if (view == null ||editorCore.getControlType(view) == EditorType.hr) {
            editorCore.getParentView().removeView(view);
            return true;
        }
        return false;
    }

    public void removeAllDividersBetweenDeletedAndFocusNext(int indexOfDeleteItem, int nextFocusIndex) {
        for(int i = nextFocusIndex; i <indexOfDeleteItem;i++){
            if (editorCore.getControlType(editorCore.getParentView().getChildAt(i)) == EditorType.hr){
                editorCore.getParentView().removeViewAt(i);
            }
        }
    }
}