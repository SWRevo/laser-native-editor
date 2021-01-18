package id.indosw.lib.lasernativeeditor;

import android.view.View;

import org.jsoup.nodes.Element;

import java.util.ArrayList;

import id.indosw.lib.lasernativeeditor.Components.ComponentsWrapper;
import id.indosw.lib.lasernativeeditor.models.EditorContent;
import id.indosw.lib.lasernativeeditor.models.EditorType;
import id.indosw.lib.lasernativeeditor.models.Node;

public abstract class EditorComponent {
    private final EditorCore editorCore;
    protected ComponentsWrapper componentsWrapper;
    public abstract Node getContent(View view);
    public abstract String getContentAsHTML(Node node, EditorContent content);
    public abstract void renderEditorFromState(Node node, EditorContent content);
    @SuppressWarnings("UnusedReturnValue")
    public abstract Node buildNodeFromHTML(Element element);
    public abstract void init(ComponentsWrapper componentsWrapper);

    public EditorComponent(EditorCore editorCore){
        this.editorCore = editorCore;
    }

    @SuppressWarnings("UnnecessaryLocalVariable")
    protected Node getNodeInstance(View view){
        Node node = new Node();
        EditorType type = editorCore.getControlType(view);
        node.type = type;
        node.content = new ArrayList<>();
        return node;
    }
    @SuppressWarnings("SameParameterValue")
    protected Node getNodeInstance(EditorType type){
        Node node = new Node();
        node.type = type;
        node.content = new ArrayList<>();
        return node;
    }

}