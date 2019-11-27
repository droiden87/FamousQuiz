package com.flat.rock.quiz.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String TAG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "UserManager.db";

    // User table name
    public static final String TABLE_USERS = "users";

    //Quiz table name
    public static final String TABLE_QUIZ = "quiz";

    // User Table Columns names
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_USER_NAME = "user_name";
    public static final String COLUMN_USER_EMAIL = "user_email";
    public static final String COLUMN_USER_PASSWORD = "user_password";
    public static final String COLUMN_IS_CURRENT_USER = "isCurrentUser";

    //Quiz Table Columns names
    public static final String COLUMN_QUIZ_ID = "id";
    public static final String COLUMN_QUIZ_QUESTION = "question";
    public static final String COLUMN_QUIZ_ANSWER = "answer";
    public static final String COLUMN_QUIZ_OPTA = "optA";
    public static final String COLUMN_QUIZ_OPTB = "optB";
    public static final String COLUMN_QUIZ_OPTC = "optC";



    // Table Create Statements
    // Users table create statement
    private String CREATE_USER_TABLE =  "CREATE TABLE IF NOT EXISTS " + TABLE_USERS + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT," + COLUMN_IS_CURRENT_USER + " BOOLEAN" + ")";

    //Quiz table create statement
    private String CREATE_QUIZ_TABLE =  "CREATE TABLE IF NOT EXISTS " + TABLE_QUIZ + "("
            + COLUMN_QUIZ_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_QUIZ_QUESTION + " TEXT,"
            + COLUMN_QUIZ_ANSWER + " TEXT," + COLUMN_QUIZ_OPTA + " TEXT,"
            + COLUMN_QUIZ_OPTB + " TEXT," + COLUMN_QUIZ_OPTC + " TEXT" + ")";

    /**
     * Constructor
     *
     * @param context
     */

    private static DatabaseHelper instance;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DatabaseHelper getHelper(Context context) {
        if (instance == null)
            instance = new DatabaseHelper(context);
        return instance;
    }


    @Override
    public void onConfigure(SQLiteDatabase db) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            //db.execSQL("PRAGMA foreign_keys=ON");
            //The case_sensitive_like pragma is enabled
            //Then the LIKE operator pays attention to case
            db.execSQL(" PRAGMA case_sensitive_like=ON;");

        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_QUIZ_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop User Table if exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUIZ);

        // Create tables again
        onCreate(db);

    }
}
