package com.flat.rock.quiz.fragments;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.flat.rock.quiz.R;
import com.flat.rock.quiz.database.QuizDAO;
import com.flat.rock.quiz.model.Question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MultipleFragment extends Fragment  implements View.OnClickListener  {


    public static MultipleFragment newInstance() {
        return new MultipleFragment();
    }

    private List<Question> questionList;
    private int score = 0;
    private int questionId = 0;
    private Question currentQuestion;
    private QuizDAO quizDAO;

    private TextView txtQuestion;
    private Button btnOptA, btnOptB, btnOptC;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_multiple, container, false);

        quizDAO = new QuizDAO(getActivity());
        quizDAO.open();

        questionList = new ArrayList<>();

        questionList = quizDAO.getAllQuestions();
        Collections.shuffle(questionList);
        currentQuestion = questionList.get(questionId);

        txtQuestion = view.findViewById(R.id.question);

        btnOptA = view.findViewById(R.id.btnOptA);
        btnOptB = view.findViewById(R.id.btnOptB);
        btnOptC = view.findViewById(R.id.btnOptC);

        setQuestionView();


        btnOptA.setOnClickListener(this);
        btnOptB.setOnClickListener(this);
        btnOptC.setOnClickListener(this);


        return view;
    }

    private void setQuestionView() {
        txtQuestion.setText(currentQuestion.getQuestion());
        btnOptA.setText(currentQuestion.getOptA());
        btnOptB.setText(currentQuestion.getOptB());
        btnOptC.setText(currentQuestion.getOptC());
        questionId++;
    }


    @SuppressLint("ResourceType")
    public void btnCorrectClick(String answer) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setTitle(getString(R.string.alert_dialog_title));
        if (currentQuestion.getAnswer().equals(answer)) {
            score++;
            Log.d("Score", "Your score: " + score);
            builder.setMessage("Correct! The right answer is:" + currentQuestion.getAnswer());
            builder.setPositiveButton(getString(R.string.alert_dialog_ok), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    showQuestions();
                    dialog.dismiss();
                }
            });

            builder.show();

        } else {

            builder.setMessage("Sorry, you are wrong!\n" + "The right answer is:  " + currentQuestion.getAnswer());
            builder.setPositiveButton(getString(R.string.alert_dialog_ok), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    showQuestions();
                    dialog.dismiss();
                }
            });

            builder.show();
        }

    }

    @Override
    public void onResume() {
        quizDAO.open();
        super.onResume();

    }

    @Override
    public void onPause() {
        quizDAO.close();
        super.onPause();
    }


    private void showQuestions() {
        if (questionId < 11) {
            currentQuestion = questionList.get(questionId);
            setQuestionView();

        } else {

            ResultFragment resultFragment = ResultFragment.newInstance();
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            Bundle bundle = new Bundle();
            bundle.putInt("score", score);

            resultFragment.setArguments(bundle);

            ft.replace(R.id.quiz_fragment_default, resultFragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.addToBackStack(null);
            ft.commit();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnOptA:
                btnCorrectClick(btnOptA.getText().toString());
                break;
            case R.id.btnOptB:
                btnCorrectClick(btnOptB.getText().toString());
                break;
            case R.id.btnOptC:

                btnCorrectClick(btnOptC.getText().toString());
                break;

        }
    }

}
