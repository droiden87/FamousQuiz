package com.flat.rock.quiz.fragments;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.flat.rock.quiz.R;
import com.flat.rock.quiz.database.UserDAO;
import com.flat.rock.quiz.model.User;
import com.flat.rock.quiz.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class UserProfileFragment extends Fragment {

    private static UserDAO userDAO;
    User user;
    private List<User> listUsers;
    private TextView txtName,txtEmail;
    private Button btnLogout;
    SessionManager sessionManager;

    public UserProfileFragment() {
    }


    public static androidx.fragment.app.Fragment newInstance() {
        return new UserProfileFragment();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view =inflater.inflate(R.layout.fragment_user_profile, container, false);
       listUsers = new ArrayList<>();
        userDAO = new UserDAO(getActivity());
        userDAO.open();
       sessionManager = new SessionManager(getActivity());
        user = new User();

        txtName = view.findViewById(R.id.txtName);
        txtEmail = view.findViewById(R.id.txtEmail);
        btnLogout = view.findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear the session data
                // This will clear all session data and
                // redirect user to LoginActivity

              sessionManager.logoutUser();

            }
        });


        getDataFromSQLite();

        return view;


    }

    @SuppressLint("StaticFieldLeak")
    private void getDataFromSQLite() {

        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {

                listUsers.addAll(userDAO.getAllUser());
                for (int i = 0; i < listUsers.size(); i++){
                    user.setName(listUsers.get(i).getName());
                    user.setEmail(listUsers.get(i).getEmail());
                    user.setId(listUsers.get(i).getId());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                txtName.setText(user.getName());
                txtEmail.setText(user.getEmail());
            }
        }.execute();
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



    }

}
