package com.flat.rock.quiz.adapters;


import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.flat.rock.quiz.R;
import com.flat.rock.quiz.fragments.QuizFragment;
import com.flat.rock.quiz.fragments.SettingsFragment;
import com.flat.rock.quiz.fragments.UserProfileFragment;


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentStatePagerAdapter{

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_quiz,R.string.tab_text_settings,R.string.tab_text_user_profile};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
         case 0:
             return QuizFragment.newInstance();
            case 1:
                return SettingsFragment.newInstance();
            case 2:
                return UserProfileFragment.newInstance();
            default:
                return null;
     }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }
}