package id.indosw.lasernativeeditor;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import id.indosw.lib.lasernativeeditor.Editor;
import id.indosw.lib.lasernativeeditor.EditorListener;
import id.indosw.lib.lasernativeeditor.models.EditorTextStyle;
import top.defaults.colorpicker.ColorPickerPopup;

@SuppressWarnings("deprecation")
public class EditorTestActivity extends AppCompatActivity {
    Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_test);
        editor =  findViewById(R.id.editor);
        setUpEditor();
    }

    private void setUpEditor() {
        findViewById(R.id.action_h1).setOnClickListener(v -> editor.updateTextStyle(EditorTextStyle.H1));
        findViewById(R.id.action_h2).setOnClickListener(v -> editor.updateTextStyle(EditorTextStyle.H2));
        findViewById(R.id.action_h3).setOnClickListener(v -> editor.updateTextStyle(EditorTextStyle.H3));
        findViewById(R.id.action_bold).setOnClickListener(v -> editor.updateTextStyle(EditorTextStyle.BOLD));
        findViewById(R.id.action_Italic).setOnClickListener(v -> editor.updateTextStyle(EditorTextStyle.ITALIC));
        findViewById(R.id.action_indent).setOnClickListener(v -> editor.updateTextStyle(EditorTextStyle.INDENT));
        findViewById(R.id.action_blockquote).setOnClickListener(v -> editor.updateTextStyle(EditorTextStyle.BLOCKQUOTE));
        findViewById(R.id.action_outdent).setOnClickListener(v -> editor.updateTextStyle(EditorTextStyle.OUTDENT));
        findViewById(R.id.action_bulleted).setOnClickListener(v -> editor.insertList(false));
        findViewById(R.id.action_unordered_numbered).setOnClickListener(v -> editor.insertList(true));
        findViewById(R.id.action_hr).setOnClickListener(v -> editor.insertDivider());
        findViewById(R.id.action_color).setOnClickListener(view -> new ColorPickerPopup.Builder(EditorTestActivity.this)
                .initialColor(Color.RED) // Set initial color
                .enableAlpha(true) // Enable alpha slider or not
                .okTitle("Choose")
                .cancelTitle("Cancel")
                .showIndicator(true)
                .showValue(true)
                .build()
                .show(findViewById(android.R.id.content), new ColorPickerPopup.ColorPickerObserver() {
                    @Override
                    public void onColorPicked(int color) {
                        Toast.makeText(EditorTestActivity.this, "picked" + colorHex(color), Toast.LENGTH_LONG).show();
                        editor.updateTextColor(colorHex(color));
                    }
                }));

        findViewById(R.id.action_insert_image).setOnClickListener(v -> editor.openImagePicker());
        findViewById(R.id.action_insert_link).setOnClickListener(v -> editor.insertLink());
        findViewById(R.id.action_erase).setOnClickListener(v -> editor.clearAllContents());
        //editor.dividerBackground=R.drawable.divider_background_dark;
        //editor.setFontFace(R.string.fontFamily__serif);
        Map<Integer, String> headingTypeface = getHeadingTypeface();
        Map<Integer, String> contentTypeface = getContentface();
        editor.setHeadingTypeface(headingTypeface);
        editor.setContentTypeface(contentTypeface);
        editor.setDividerLayout(R.layout.tmpl_divider_layout);
        editor.setEditorImageLayout(R.layout.tmpl_image_view);
        editor.setListItemLayout(R.layout.tmpl_list_item);
        //editor.setNormalTextSize(10);
        //editor.setEditorTextColor("#FF3333");
        //editor.StartEditor();
        editor.setEditorListener(new EditorListener() {
            @Override
            public void onTextChanged(EditText editText, Editable text) {
                // Toast.makeText(EditorTestActivity.this, text, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUpload(Bitmap image, String uuid) {
                Toast.makeText(EditorTestActivity.this, uuid, Toast.LENGTH_LONG).show();
                editor.onImageUploadComplete("http://www.videogamesblogger.com/wp-content/uploads/2015/08/metal-gear-solid-5-the-phantom-pain-cheats-640x325.jpg", uuid);
                //editor.onImageUploadFailed(uuid);
            }

                @SuppressLint("InflateParams")
                @Override
                public View onRenderMacro(String name, Map<String, Object> props, int index) {
                    return getLayoutInflater().inflate(R.layout.layout_authored_by, null);
                }

        });

        String text = "<h1 data-tag=\"input\" style=\"color:#c00000;\"><span style=\"color:#C00000;\">textline 1 a great time and I will branch office is closed on Sundays</span></h1><hr data-tag=\"hr\"/><p data-tag=\"input\" style=\"color:#000000;\">the only one that you have received the stream free and open minded person to discuss a business opportunity to discuss my background.</p><div data-tag=\"img\"><img src=\"http://www.videogamesblogger.com/wp-content/uploads/2015/08/metal-gear-solid-5-the-phantom-pain-cheats-640x325.jpg\" /><p data-tag=\"img-sub\" style=\"color:#FF0000;\" class=\"editor-image-subtitle\"><b>it is a great weekend and we will have the same to me that the same a great time</b></p></div><p data-tag=\"input\" style=\"color:#000000;\">I have a place where I have a great time and I will branch manager state to boast a new job in a few weeks and we can host or domain to get to know.</p><div data-tag=\"img\"><img src=\"http://www.videogamesblogger.com/wp-content/uploads/2015/08/metal-gear-solid-5-the-phantom-pain-cheats-640x325.jpg\" /><p data-tag=\"img-sub\" style=\"color:#5E5E5E;\" class=\"editor-image-subtitle\">the stream of water in a few weeks and we can host in the stream free and no ippo</p></div><p data-tag=\"input\" style=\"color:#000000;\">it is that I can get it done today will online at location and I am not a big difference to me so that we are headed <a href=\"www.google.com\">www.google.com</a> it was the only way I.</p><blockquote data-tag=\"input\" style=\"color:#000000;\">I have to do the negotiation and a half years old story and I am looking forward in a few days.</blockquote><p data-tag=\"input\" style=\"color:#000000;\">it is not a good day to get the latest version to blame it to the product the.</p><ol data-tag=\"ol\"><li data-tag=\"list-item-ol\"><span style=\"color:#000000;\">it is that I can send me your email to you and I am not able a great time and consideration I have to do the needful.</span></li><li data-tag=\"list-item-ol\"><span style=\"color:#000000;\">I have to do the needful and send to me and</span></li><li data-tag=\"list-item-ol\"><span style=\"color:#000000;\">I will be a while ago to a great weekend a great time with the same.</span></li></ol><p data-tag=\"input\" style=\"color:#000000;\">it was u can do to make an offer for a good day I u u have been working with a new job to the stream free and no.</p><p data-tag=\"input\" style=\"color:#000000;\">it was u disgraced our new home in time to get the chance I could not find a good idea for you have a great.</p><p data-tag=\"input\" style=\"color:#000000;\">I have to do a lot to do the same a great time and I have a great.</p><p data-tag=\"input\" style=\"color:#000000;\"></p>";
        //editor.render("<p>Hello man, whats up!</p>");
        //String text = "<p data-tag=\"input\" style=\"color:#000000;\">I have to do the needful and send to me and my husband is in a Apple has to offer a variety is not a.</p><p data-tag=\"input\" style=\"color:#000000;\">I have to go with you will be highly grateful if we can get the latest</p><blockquote data-tag=\"input\" style=\"color:#000000;\">I have to do the negotiation and a half years old story and I am looking forward in a few days.</blockquote><p data-tag=\"input\" style=\"color:#000000;\">I have to do the needful at your to the product and the other to a new job is going well and that the same old stuff and a half day city is the stream and a good idea to get onboard the stream.</p>";
        editor.render(text);
        findViewById(R.id.btnRender).setOnClickListener(v -> {
            String text1 = editor.getContentAsSerialized();
            editor.getContentAsHTML();
            Intent intent = new Intent(getApplicationContext(), RenderTestActivity.class);
            intent.putExtra("content", text1);
            startActivity(intent);
        });
    }

    @SuppressWarnings("unused")
    private View insertMacro() {
        @SuppressLint("InflateParams") View view = getLayoutInflater().inflate(R.layout.layout_authored_by, null);
        Map<String, Object> map = new HashMap<>();
        map.put("author-name", "Alex Wong");
        map.put("date","12 July 2018");
        editor.insertMacro("author-tag",view, map);
        return view;
    }

    private String colorHex(int color) {
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        return String.format(Locale.getDefault(), "#%02X%02X%02X", r, g, b);
    }

    public static void setGhost(Button button) {
        int radius = 4;
        GradientDrawable background = new GradientDrawable();
        background.setShape(GradientDrawable.RECTANGLE);
        background.setStroke(4, Color.WHITE);
        background.setCornerRadius(radius);
        button.setBackgroundDrawable(background);
    }

    @SuppressWarnings("unused")
    private void render() {
        String x = "<h2 id=\"installation\" style=\"font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;color:#c00000;background-color:#333;text-align:center; margin-top: -80px !important;\">Installation</h2>" +
                "<h3 id=\"requires-html5-doctype\" style=\"font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;color:#ff0000; margin-bottom: 8px; margin-right: 0px; margin-left: 0px;\">Requires HTML5 doctype</h3>" +
                "<p style=\"font-size: 14px; color: rgb(104, 116, 127);\">Bootstrap uses certain HTML elements and CSS properties which require HTML5 doctype. Include&nbsp;<code style=\"font-size: 12.6px;\">&lt;!DOCTYPE html&gt;</code>&nbsp;in the beginning of all your projects.</p>" +
                "<img src=\"http://www.scifibloggers.com/wp-content/uploads/TOR_2.jpg\" />" +
                "<h2 id=\"integration\" style=\"font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; margin-top: -80px !important;\">Integration</h2>" +
                "<p style=\"font-size: 14px; color: rgb(104, 116, 127);\">3rd parties available in django, rails, angular and so on.</p>" +
                "<h3 id=\"django\" style=\"font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; margin-bottom: 8px; margin-right: 0px; margin-left: 0px;\">Django</h3>" +
                "<p style=\"font-size: 14px; color: rgb(104, 116, 127);\">Handy update for your django admin page.</p>" +
                "<ul style=\"color: rgb(51, 51, 51);\"><li style=\"font-size: 14px; color: #c00000;\">django-summernote</li><li style=\"font-size: 14px; color: rgb(104, 116, 127);\"><a href=\"https://pypi.python.org/pypi/django-summernote\" target=\"_blank\">summernote plugin for Django</a></li></ul>" +
                "<h3 id=\"ruby-on-rails\" style=\"font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; margin-bottom: 8px; margin-right: 0px; margin-left: 0px;\">Ruby On Rails</h3>" +
                "<p style=\"font-size: 14px; color: rgb(104, 116, 127);\">This gem was built to gemify the assets used in Summernote.</p>" +
                "<ul style=\"color: rgb(51, 51, 51);\"><li style=\"font-size: 14px; color: rgb(104, 116, 127);\"><a href=\"https://github.com/summernote/summernote-rails\" target=\"_blank\">summernote-rails</a></li></ul>" +
                "<h3 id=\"angularjs\" style=\"font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; margin-bottom: 8px; margin-right: 0px; margin-left: 0px;\">AngularJS</h3>" +
                "<p style=\"font-size: 14px; color: rgb(104, 116, 127);\">AngularJS directive to Summernote.</p>" +
                "<ul style=\"color: rgb(51, 51, 51);\"><li style=\"font-size: 14px; color: rgb(104, 116, 127);\"><a href=\"https://github.com/summernote/angular-summernote\">angular-summernote</a></li></ul>" +
                "<h3 id=\"apache-wicket\" style=\"font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; margin-bottom: 8px; margin-right: 0px; margin-left: 0px;\">Apache Wicket</h3>" +
                "<p style=\"font-size: 14px; color: rgb(104, 116, 127);\">Summernote widget for Wicket Bootstrap.</p>" +
                "<ul style=\"color: rgb(51, 51, 51);\"><li style=\"font-size: 14px; color: rgb(104, 116, 127);\"><a href=\"http://wb-mgrigorov.rhcloud.com/summernote\" target=\"_blank\">demo</a></li><li style=\"font-size: 14px; color: rgb(104, 116, 127);\"><a href=\"https://github.com/l0rdn1kk0n/wicket-bootstrap/tree/4f97ca783f7279ca43f9e2ee790703161f59fa40/bootstrap-extensions/src/main/java/de/agilecoders/wicket/extensions/markup/html/bootstrap/editor\" target=\"_blank\">source code</a></li></ul>" +
                "<h3 id=\"webpack\" style=\"font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; margin-bottom: 8px; margin-right: 0px; margin-left: 0px;\">Webpack</h3>" +
                "<p style=\"font-size: 14px; color: rgb(104, 116, 127);\">Example about using summernote with webpack.</p>" +
                "<ul style=\"color: rgb(51, 51, 51);\"><li style=\"font-size: 14px; color: rgb(104, 116, 127);\"><a href=\"https://github.com/hackerwins/summernote-webpack-example\" target=\"_blank\">summernote-webpack-example</a></li></ul>" +
                "<h3 id=\"meteor\" style=\"font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; margin-bottom: 8px; margin-right: 0px; margin-left: 0px;\">Meteor</h3>" +
                "<p style=\"font-size: 14px; color: rgb(104, 116, 127);\">Example about using summernote with meteor.</p>" +
                "<ul style=\"color: rgb(51, 51, 51);\"><li style=\"font-size: 14px; color: rgb(104, 116, 127);\"><a href=\"https://github.com/hackerwins/summernote-meteor-example\" target=\"_blank\">summernote-meteor-example</a></li></ul>" +
                "<p style=\"font-size: 14px; color: rgb(104, 116, 127);\"><br></p>";
        editor.render(x);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == editor.PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                editor.insertImage(bitmap);
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            //Write your code if there's no result
            Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            // editor.RestoreState();
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Exit Editor?")
                .setMessage("Are you sure you want to exit the editor?")
                .setPositiveButton("Yes", (dialog, which) -> finish())
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setGhost(findViewById(R.id.btnRender));
    }

    public Map<Integer, String> getHeadingTypeface() {
        Map<Integer, String> typefaceMap = new HashMap<>();
        typefaceMap.put(Typeface.NORMAL, "fonts/GreycliffCF-Bold.ttf");
        typefaceMap.put(Typeface.BOLD, "fonts/GreycliffCF-Heavy.ttf");
        typefaceMap.put(Typeface.ITALIC, "fonts/GreycliffCF-Heavy.ttf");
        typefaceMap.put(Typeface.BOLD_ITALIC, "fonts/GreycliffCF-Bold.ttf");
        return typefaceMap;
    }

    public Map<Integer, String> getContentface() {
        Map<Integer, String> typefaceMap = new HashMap<>();
        typefaceMap.put(Typeface.NORMAL, "fonts/Lato-Medium.ttf");
        typefaceMap.put(Typeface.BOLD, "fonts/Lato-Bold.ttf");
        typefaceMap.put(Typeface.ITALIC, "fonts/Lato-MediumItalic.ttf");
        typefaceMap.put(Typeface.BOLD_ITALIC, "fonts/Lato-BoldItalic.ttf");
        return typefaceMap;
    }
}