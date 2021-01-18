package id.indosw.lasernativeeditor.Utilities;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import id.indosw.lasernativeeditor.HTMLRenderedFragment;
import id.indosw.lasernativeeditor.PreviewFragment;
import id.indosw.lasernativeeditor.SerializedFragment;

public class RendererPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    final String content;
    private final String[] tabTitles = new String[] { "Rendered", "Serialized", "HTML" };

    public RendererPagerAdapter(FragmentManager fm, String content) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.content = content;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return PreviewFragment.newInstance(content);
        }else if(position==1){
            return SerializedFragment.newInstance(content);
        }else{
            return HTMLRenderedFragment.newInstance(content);
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

}
