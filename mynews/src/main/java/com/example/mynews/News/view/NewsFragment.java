package com.example.mynews.News.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mynews.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by frank on 2016/9/14.
 */
public class NewsFragment extends Fragment {
    public static final int NEWS_TYPE_TOP=0;
    public static final int NEWS_TYPE_SPORT=1;
    public static final int NEWS_TYPE_CARS=2;
    public static final int NEWS_TYPE_JOKE=3;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_news,null);
        mTabLayout= (TabLayout) view.findViewById(R.id.news_tablayout);
        mViewPager= (ViewPager) view.findViewById(R.id.news_viewpager);
        mViewPager.setOffscreenPageLimit(3);
        setupViewPager(mViewPager);
        mTabLayout.addTab(mTabLayout.newTab().setText("头条"));
        mTabLayout.addTab(mTabLayout.newTab().setText("体育"));
        mTabLayout.addTab(mTabLayout.newTab().setText("汽车"));
        mTabLayout.addTab(mTabLayout.newTab().setText("笑话"));
        mTabLayout.setupWithViewPager(mViewPager);
        return view;
    }

    private void setupViewPager(ViewPager mViewPager) {
        MyPagerAdapter pagerAdapter=new MyPagerAdapter(getChildFragmentManager());
        pagerAdapter.addFragment(NewsListFragment.newInstance(NEWS_TYPE_TOP),getString(R.string.top));
        pagerAdapter.addFragment(NewsListFragment.newInstance(NEWS_TYPE_SPORT), getString(R.string.sport));
        pagerAdapter.addFragment(NewsListFragment.newInstance(NEWS_TYPE_CARS), getString(R.string.cars));
        pagerAdapter.addFragment(NewsListFragment.newInstance(NEWS_TYPE_JOKE), getString(R.string.jokes));
        mViewPager.setAdapter(pagerAdapter);
    }
    public static class MyPagerAdapter extends FragmentPagerAdapter{

        public List<Fragment> fragments=new ArrayList<>();
        public List<String> fragmentTitles=new ArrayList<>();
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment,String title){
            fragments.add(fragment);
            fragmentTitles.add(title);
        }
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitles.get(position);
        }
    }
}
