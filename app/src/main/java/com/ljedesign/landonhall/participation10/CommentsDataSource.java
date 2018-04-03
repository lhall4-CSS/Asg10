package com.ljedesign.landonhall.participation10;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

//This class is a helper class acting as a bridge between the MySQLHelper class and the app for working with the comments.

public class CommentsDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_COMMENT, MySQLiteHelper.COLUMN_RATING };
//Creates a new instance of the MYSQLHelper class and sets it as a variable
    public CommentsDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }
//Opens the database from the MYSQLHelper Class
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }
//Closes the database from the MYSQLHelper Class
    public void close() {
        dbHelper.close();
    }
//Inserts the comment into the database and then queries the newly inserted comment and returns it
    public Comment createComment(String comment) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_COMMENT, comment);
        long insertId = database.insert(MySQLiteHelper.TABLE_COMMENTS, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
                null, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Comment newComment = cursorToComment(cursor);
        cursor.close();
        return newComment;
    }
    public void addRating(Integer rating, Integer commentID) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_RATING, rating);
        database.update(MySQLiteHelper.TABLE_COMMENTS, values, MySQLiteHelper.COLUMN_ID + "=" + commentID, null);

    }
//Deletes the provided comment from the database
    public void deleteComment(Comment comment) {
        long id = comment.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_COMMENTS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }
//Gets all comments in the database and returns them as a list
    public List<Comment> getAllComments() {
        List<Comment> comments = new ArrayList<Comment>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Comment comment = cursorToComment(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;
    }
//moves the cursor to the correct comment
    private Comment cursorToComment(Cursor cursor) {
        Comment comment = new Comment();
        //comment.setId(cursor.getLong(0));
        comment.setId(cursor.getLong(cursor.getColumnIndex(MySQLiteHelper.COLUMN_ID)));
        comment.setComment(cursor.getString(1));
        comment.setRating(cursor.getString( cursor.getColumnIndex( MySQLiteHelper.COLUMN_RATING )));
        return comment;
    }
}