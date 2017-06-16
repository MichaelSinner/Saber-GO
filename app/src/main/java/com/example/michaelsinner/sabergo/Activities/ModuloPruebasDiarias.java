package com.example.michaelsinner.sabergo.Activities;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.michaelsinner.sabergo.Fragments.PreguntaDiaria_CN;
import com.example.michaelsinner.sabergo.Fragments.PreguntaDiaria_CS;
import com.example.michaelsinner.sabergo.Fragments.PreguntaDiaria_IN;
import com.example.michaelsinner.sabergo.Fragments.PreguntaDiaria_LC;
import com.example.michaelsinner.sabergo.Fragments.PreguntaDiaria_MT;
import com.example.michaelsinner.sabergo.Fragments.PreguntaDiaria_RN;
import com.example.michaelsinner.sabergo.R;

import java.util.ArrayList;
import java.util.List;

public class ModuloPruebasDiarias extends AppCompatActivity
{
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    String[] datos = new String[]{"Element1","Element2","Element3","Element4","Element5","Element6","Element7","Element8","Element9"};



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modulo_pruebas_diarias);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        viewPager = (ViewPager) findViewById(R.id.viewPagerPD);
        setupViewPager(viewPager);



        tabLayout = (TabLayout) findViewById(R.id.tabsPreguntas);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new PreguntaDiaria_CN(),"Ciencias Naturales");
        adapter.addFragment(new PreguntaDiaria_CS(),"Ciencias Sociales");
        adapter.addFragment(new PreguntaDiaria_IN(),"Inglés");
        adapter.addFragment(new PreguntaDiaria_LC(),"Lectura Crítica");
        adapter.addFragment(new PreguntaDiaria_MT(),"Matemáticas");
        adapter.addFragment(new PreguntaDiaria_RN(),"Aleatorio");
        viewPager.setAdapter(adapter);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter{
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentsTitle = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentsTitle.get(position);
        }

        public void addFragment(Fragment fragment, String title){
            mFragments.add(fragment);
            mFragmentsTitle.add(title);
        }
    }

}


