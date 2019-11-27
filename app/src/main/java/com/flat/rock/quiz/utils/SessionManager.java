package com.flat.rock.quiz.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.flat.rock.quiz.activities.LoginActivity;
import com.flat.rock.quiz.activities.TabsActivity;

import java.util.HashMap;

public class SessionManager {

        // Shared Preferences
      private   SharedPreferences pref;
        // Editor for Shared preferences
       private SharedPreferences.Editor editor;
        // Context
      private   Context context;

        // Shared pref mode
        private int PRIVATE_MODE = 0;
        // Sharedpref file name
        private static final String PREFS_NAME = "QuizPref";
        // All Shared Preferences Keys
        private static final String IS_LOGIN = "IsLoggedIn";
        private static final String KEY_PERSISTENT_USER_ID = "loggedInUser";
        // User name (make variable public to access from outside)
        public static final String KEY_NAME = "name";
        // Email address (make variable public to access from outside)
        public static final String KEY_EMAIL = "email";


        // Constructor
        @SuppressLint("CommitPrefEdits")
        public SessionManager(Context context) {
            this.context = context;
            pref = context.getSharedPreferences(PREFS_NAME, PRIVATE_MODE);
            editor = pref.edit();
        }

        /**
         * Create login session
         */
        public void createLoginSession(String name, String email) {
            // Storing login value as TRUE
            editor.putBoolean(IS_LOGIN, true);
            // Storing name in pref
            editor.putString(KEY_NAME, name);
            // Storing email in pref
            editor.putString(KEY_EMAIL, email);
            // commit changes
            editor.commit();
        }

        /**
         * Check login method will check user login status If false it will redirect
         * user to login page
         */
        public void checkLogin() {
            // Check login status
            if (!this.isLoggedIn()) {
                // user is not logged in redirect him to Login Activity
                Intent intentNotLogged = new Intent(context, LoginActivity.class);
                // Closing all the Activities
                intentNotLogged.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                // Add new Flag to start new Activity
                intentNotLogged.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // Staring Login Activity
                context.startActivity(intentNotLogged);
            }else {
                    Intent intentLogged = new Intent(context, TabsActivity.class);
                context.startActivity(intentLogged);

            }

        }

        /**
         * Get stored session data
         */
        public HashMap<String, String> getUserDetailsFromPrefs() {
            HashMap<String, String> user = new HashMap<String, String>();
            // user name
            user.put(KEY_NAME, pref.getString(KEY_NAME, null));

            // user email id
            user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

            // return user
            return user;
        }

        /**
         * Clear session details
         */
        public void logoutUser() {
            // Clearing all data from Shared Preferences
            editor.clear();
            editor.commit();

            // After logout redirect user to Loing Activity
            checkLogin();
        }

        /**
         * Quick check for login
         **/
        // Get Login State
        public boolean isLoggedIn() {
            return pref.getBoolean(IS_LOGIN, false);
        }


}
