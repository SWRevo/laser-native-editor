package id.indosw.lib.lasernativeeditor.models;


import org.jsoup.nodes.Element;

@SuppressWarnings("unused")
public class HtmlElement {
    public String TagName;
    public Element _Element;

    public  HtmlElement(String _TagName, Element _Element){
        this.TagName= _TagName;
        this._Element= _Element;
    }
}

