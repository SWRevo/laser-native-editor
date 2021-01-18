package id.indosw.lasernativeeditor;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import id.indosw.lasernativeeditor.Utilities.RendererPagerAdapter;

public class RenderTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_render_test);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String serialized= getIntent().getStringExtra("content");
        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new RendererPagerAdapter(getSupportFragmentManager(),
                serialized));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}
