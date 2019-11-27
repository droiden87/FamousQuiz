package com.flat.rock.quiz.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.flat.rock.quiz.R;

public class ResultFragment extends Fragment implements View.OnClickListener {
    public static ResultFragment newInstance() {
        return new ResultFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        TextView scoreTxtView = view.findViewById(R.id.score);
        Button btnStartAgain = view.findViewById(R.id.btnStartAgain);
        btnStartAgain.setOnClickListener(this);
        if (getArguments() != null) {
            scoreTxtView.setText(String.valueOf(getArguments().getInt("score") - 1));
        }

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnStartAgain:
                setFragment(QuizFragment.newInstance());
                break;
        }
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction transaction =
                getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.quiz_fragment_default, fragment);
        transaction.commit();
    }
}
