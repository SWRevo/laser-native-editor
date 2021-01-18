package id.indosw.lib.lasernativeeditor.models;


import java.util.List;
import java.util.Map;

public class EditorControl {
    public EditorType Type;
    public String path;
    public String Cords;
    public TextSettings textSettings;
    public List<EditorTextStyle> editorTextStyles;
    public Map<String, Object> macroSettings;
    public String macroName;
}
