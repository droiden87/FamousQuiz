package com.flat.rock.quiz.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.flat.rock.quiz.model.Question;

import java.util.ArrayList;
import java.util.List;

public class QuizDAO {

    private static SQLiteDatabase sqLiteDatabase;
    private DatabaseHelper databaseHelper;
    private Context context;
    private int rowCount = 0;

    public QuizDAO(Context context) {
        this.context = context;
        databaseHelper = DatabaseHelper.getHelper(context);
        open();

    }

    public void open() throws SQLException {
        if (databaseHelper == null)
            databaseHelper = DatabaseHelper.getHelper(context);
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        //sqLiteDatabase.setForeignKeyConstraintsEnabled(true);
    }

    public void close() {
        databaseHelper.close();
    }



    public static void addQuestionToDB(Question question){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_QUIZ_QUESTION, question.getQuestion());
        values.put(DatabaseHelper.COLUMN_QUIZ_ANSWER,question.getAnswer());
        values.put(DatabaseHelper.COLUMN_QUIZ_OPTA,question.getOptA());
        values.put(DatabaseHelper.COLUMN_QUIZ_OPTB,question.getOptB());
        values.put(DatabaseHelper.COLUMN_QUIZ_OPTC,question.getOptC());
        sqLiteDatabase.insert(DatabaseHelper.TABLE_QUIZ, null, values);
    }

    public List<Question> getAllQuestions(){
        List <Question> questionList = new ArrayList<Question>();

        String selectQuery = "SELECT * FROM quiz";
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);
        rowCount = cursor.getCount();

        if(cursor.moveToFirst()){
            do{
                Question question = new Question();
                question.setId(cursor.getInt(0));
                question.setQuestion(cursor.getString(1));
                question.setAnswer(cursor.getString(2));
                question.setOptA(cursor.getString(3));
                question.setOptB(cursor.getString(4));
                question.setOptC(cursor.getString(5));

                questionList.add(question);

            }while (cursor.moveToNext());
        }
        return questionList;
    }

    public int getRowCount(){
        return rowCount;
    }

    public void addQuestions() {
        Question q1 = new Question(
                "We have no right to express an opinion until we know all of the answers.",
                "Kurt Cobain",
                "Richard Feynman",
                "Milan Kundera",
                "Kurt Cobain");
       QuizDAO.addQuestionToDB(q1);
        Question q2 = new Question(
                "The will to win, the desire to succeed, the urge to reach your full potential... these are the keys that will unlock the door to personal excellence.",
                "Mark Twain",
                "Eleanor Roosevelt",
                "Confucius",
                "Confucius");
        QuizDAO.addQuestionToDB(q2);
        Question q3 = new Question(
                "What you get by achieving your goals is not as important as what you become by achieving your goals.",
                "Thomas A. Edison",
                "Bill Gates",
                "Zig Ziglar",
                "Zig Ziglar");
        QuizDAO.addQuestionToDB(q3);
        Question q4 = new Question(
                "There is no passion to be found playing small - in settling for a life that is less than the one you are capable of living. ",
                "Ricky Ponting",
                "Brian Lara",
                "Nelson Mandela",
                "Nelson Mandela");
        QuizDAO.addQuestionToDB(q4);
        Question q5 = new Question(
                "If you can dream it, you can do it.",
                "Walt Disney",
                "Reno",
                "Delhi",
                "Walt Disney");
        QuizDAO.addQuestionToDB(q5);
        Question q6 = new Question(
                "Well done is better than well said.",
                "Thomas A. Edison",
                "Eleanor Roosevelt",
                "Benjamin Franklin",
                "Benjamin Franklin");
        QuizDAO.addQuestionToDB(q6);
        Question q7 = new Question(
                "When something is important enough, you do it even if the odds are not in your favor.",
                "Elon Musk",
                "Steve Jobs",
                "Bill Gates",
                "Elon Musk");
        QuizDAO.addQuestionToDB(q7);
        Question q8 = new Question(
                "You can't build a reputation on what you are going to do.",
                "Kurt Cobain",
                "Nelson Mandela",
                "Henry Ford",
                "Henry Ford");
        QuizDAO.addQuestionToDB(q8);
        Question q9 = new Question(
                "I have no special talent. I am only passionately curious.",
                "Milan Kundera",
                "Confucius",
                "Albert Einstein",
                "Albert Einstein");
        QuizDAO.addQuestionToDB(q9);
        Question q10 = new Question(
                "Kids are born curious about the world. What adults primarily do in the presence of kids is unwittingly thwart the curiosity of children.",
                "Elon Musk",
                "Henry Ford",
                "Neil deGrasse Tyson",
                "Neil deGrasse Tyson");
        QuizDAO.addQuestionToDB(q10);
    }

}
