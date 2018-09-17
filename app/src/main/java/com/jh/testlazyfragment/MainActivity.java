package com.jh.testlazyfragment;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * https://www.2cto.com/kf/201708/663941.html
 * Android之ViewPager+Fragment实现懒加载，在开发的过程中，
 * 我们可能会遇到一个Activity中ViewPager与多个Fragment组合使用的情况。
 * ViewPager有一个“预加载”机制，默认会把ViewPager当前位置的左右相邻页面预先初始化（俗称预加载），它的默认值是1，
 * 这样做的好处就是ViewPager左右滑动会更加流畅。但是当有多个Fragment且每个Fragment都需要加载数据时可能就会导致页面卡顿，影响用户体验效果。
 * 此时我们希望在Fragment可见时才去加载数据，这样不仅能够降低开销，同时也提高了用户的体验效果。Fragment提供了setUserVisibleHint()方法去判断当前Fragment是否可见，
 * 实现在Fragment可见时才进行数据加载操作，即Fragment的懒加载。但是这样还会出现一个问题，就是每次可见时都会去重复加载数据，当然若需要的话可以每次都去加载，
 * 但是如果希望只是第一次可见时才去加载，那么还需要添加一些判断，下面我们封装一个基类来满足我们的需要。
 *
 */

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<String> names = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();
    FragmentPagerAdapter fragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        names.add("首页");
        names.add("理财");
        names.add("账户");
        names.add("我的");

        tabLayout.addTab(tabLayout.newTab().setText("首页"));
        tabLayout.addTab(tabLayout.newTab().setText("理财"));
        tabLayout.addTab(tabLayout.newTab().setText("账户"));
        tabLayout.addTab(tabLayout.newTab().setText("我的"));

        fragments.add(HomeFragment.getInstance());
        fragments.add(LiCaiFragment.getInstance());
        fragments.add(CountFragment.getInstance());
        fragments.add(MineFragment.getInstance());

        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return names.get(position);
            }
        };

        viewPager.setAdapter(fragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }
}
