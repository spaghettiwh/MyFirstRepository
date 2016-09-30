package com.example.mynews.main;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.mynews.About.fragment_about;
import com.example.mynews.Image.view.ImageFragment;
import com.example.mynews.News.view.NewsFragment;
import com.example.mynews.R;
import com.example.mynews.Weather.view.WeatherFragment;
import com.example.mynews.main.presenter.IMainPresenter;
import com.example.mynews.main.presenter.MainPresenterImpl;
import com.example.mynews.main.view.IMainView;

public class MainActivity extends AppCompatActivity implements IMainView{

    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolbar;

    private IMainPresenter iMainPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView= (NavigationView) findViewById(R.id.navigation_view);
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mDrawerToggle=new ActionBarDrawerToggle(this,mDrawerLayout,mToolbar,R.string.drawer_open,R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        setupDrawerContent(mNavigationView);
        iMainPresenter=new MainPresenterImpl(this);
        switchToNews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_settings){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView mNavigationView) {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                iMainPresenter.switchNavigation(item.getItemId());
                item.setChecked(true);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

    }

    @Override
    public void switchToNews() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,new NewsFragment()).commit();
        mToolbar.setTitle("新闻");
    }

    @Override
    public void switchToImage() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,new ImageFragment()).commit();
        mToolbar.setTitle("图片");
    }

    @Override
    public void switchToWeather() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,new WeatherFragment()).commit();
        mToolbar.setTitle("天气");
    }

    @Override
    public void switchToAbout() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,new fragment_about()).commit();
        mToolbar.setTitle("关于");
    }
}
