package com.bignerdranch.android.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

public class CrimePagerActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private List<Crime> mCrimes;
    private static final String EXTRA_CRIME_ID = "com.bignerdranch.android.criminalintent.crime_id";

//    return an intent with the uuid passer as argument to this method as an extra
    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

//      Get the UUID of the source crime
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        //this line connects the mViewPager variable to the layout
        mViewPager = (ViewPager) findViewById(R.id.crime_view_pager);
        //gets a list of crimes in the CrimeLab singleton
        mCrimes = CrimeLab.get(this).getCrimes();

        //get the fragment manager
        FragmentManager fragmentManager = getSupportFragmentManager();

        //an anonymous adapter class which manages the activity's relationship with the model layer
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            //gets the fragment this activity needs to host
            public Fragment getItem(int i) {
                Crime crime = mCrimes.get(i);
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            //total number of fragments
            public int getCount() {
                return mCrimes.size();
            }
        });

        for(int i=0; i<mCrimes.size(); i++) {
            if(mCrimes.get(i).getId().equals(crimeId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
