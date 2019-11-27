package com.flat.rock.quiz.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.flat.rock.quiz.R;
import com.google.android.material.tabs.TabLayout;


public class SettingsFragment extends Fragment {

    public static androidx.fragment.app.Fragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        final Switch switchButton = view.findViewById(R.id.switchButton);
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                   setFragment(MultipleFragment.newInstance());
                } else {
                   setFragment(QuizFragment.newInstance());
                }
            }
        });

        return view;
    }

    private void setFragment(Fragment fragment){
        TabLayout tabs = getActivity().findViewById(R.id.tabs);
        tabs.getTabAt(0).select();
        FragmentTransaction transaction =
                getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.quiz_fragment_default, fragment);
        transaction.commit();
    }
}
