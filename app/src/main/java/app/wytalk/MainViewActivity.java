package app.wytalk;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

//import android.support.design.widget.TabLayout;

public class MainViewActivity extends AppCompatActivity {

    Main_FriendFragment mainFragment1;
    Main_ChatFragment mainFragment2;
    Main_PlusFragment mainFragment3;

    private ViewPager viewPager;
    private TabLayout tabLayout;

    private DrawerLayout drawerLayout = null;
    private ActionBarDrawerToggle toggle = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);



        drawerLayout = (DrawerLayout)findViewById(R.id.main_drawer_layout);
        tabLayout = (TabLayout)findViewById(R.id.main_tabLayout);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.app_name, R.string.app_name);

        drawerLayout.addDrawerListener(toggle);
        tabLayout.addTab(tabLayout.newTab().setText("친구"));
        tabLayout.addTab(tabLayout.newTab().setText("채팅"));
        tabLayout.addTab(tabLayout.newTab().setText("더보기"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager)findViewById(R.id.main_viewPager);

        mainFragment1 = new Main_FriendFragment();
        mainFragment2 = new Main_ChatFragment();
        mainFragment3 = new Main_PlusFragment();

        viewPager.setAdapter(new PageAdapter(getSupportFragmentManager()));
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public class PageAdapter extends FragmentStatePagerAdapter {
        public PageAdapter(FragmentManager manager){
            super(manager);
        }
        @Override
        public int getCount() {
            return 3;
        }


        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch(position){
                case 0 : return mainFragment1;
                case 1 : return  mainFragment2;
                case 2 : return  mainFragment3;
            }
            return null;
        }
    }



}
