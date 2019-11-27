package com.flat.rock.quiz.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.flat.rock.quiz.R;
import com.flat.rock.quiz.adapters.SectionsPagerAdapter;
import com.flat.rock.quiz.database.QuizDAO;
import com.google.android.material.tabs.TabLayout;

public class TabsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        tabs.getTabAt(0).setIcon(R.mipmap.ic_action_quiz);
        tabs.getTabAt(1).setIcon(R.mipmap.ic_action_settings);
        tabs.getTabAt(2).setIcon(R.mipmap.ic_action_user);

        QuizDAO quizDAO = new QuizDAO(TabsActivity.this);
        quizDAO.addQuestions();

    }

}