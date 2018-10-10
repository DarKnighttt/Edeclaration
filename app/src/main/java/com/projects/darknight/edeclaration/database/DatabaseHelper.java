package com.projects.darknight.edeclaration.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.projects.darknight.edeclaration.pojo.Worker;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper implements IDatabaseHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "edeclarationDB";
    private static final String TABLE_FAVORITES = "favorites";
    private static final String KEY_ID = "id";
    private static final String KEY_FIRST_NAME = "first_name";
    private static final String KEY_LAST_NAME = "last_name";
    private static final String KEY_WORK_PLACE = "work_place";
    private static final String KEY_POSITION = "position";
    private static final String KEY_PDF_LINK = "pdf_link";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_WORKERS_TABLE = "CREATE TABLE " + TABLE_FAVORITES + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_FIRST_NAME + " TEXT,"
                + KEY_LAST_NAME + " TEXT,"
                + KEY_WORK_PLACE + " TEXT,"
                + KEY_POSITION + " TEXT,"
                + KEY_PDF_LINK + " TEXT)";
        db.execSQL(CREATE_WORKERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_WORKERS_TABLE = "DROP TABLE IF EXISTS " + TABLE_FAVORITES;
        db.execSQL(DROP_WORKERS_TABLE);
        onCreate(db);
    }

    @Override
    public List<Worker> getAllWorkers() {
        List<Worker> workersList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_FAVORITES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Worker worker = new Worker();
                worker.setId(cursor.getString(0));
                worker.setFirstName(cursor.getString(1));
                worker.setLastName(cursor.getString(2));
                worker.setWorkPlace(cursor.getString(3));
                worker.setPosition(cursor.getString(4));
                worker.setPdfLink(cursor.getString(5));
                workersList.add(worker);
            } while (cursor.moveToNext());
        }

        return workersList;
    }

    @Override
    public Worker getWorker(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_FAVORITES, new String[] { KEY_ID,
                        KEY_FIRST_NAME, KEY_LAST_NAME, KEY_WORK_PLACE, KEY_POSITION, KEY_PDF_LINK }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        Worker worker = new Worker(cursor.getString(0),
                                                    cursor.getString(1),
                                                    cursor.getString(2),
                                                    cursor.getString(3),
                                                    cursor.getString(4),
                                                    cursor.getString(5));

        return worker;
    }

    @Override
    public void addWorker(Worker worker) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FIRST_NAME, worker.getFirstName());
        values.put(KEY_LAST_NAME, worker.getLastName());
        values.put(KEY_WORK_PLACE, worker.getWorkPlace());
        values.put(KEY_POSITION, worker.getPosition());
        values.put(KEY_PDF_LINK, worker.getPdfLink());

        db.insert(TABLE_FAVORITES, null, values);
        db.close();
    }

    @Override
    public void deleteWorker(Worker worker) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FAVORITES, KEY_ID + " = ?", new String[] { String.valueOf(worker.getId()) });
        db.close();
    }

    @Override
    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FAVORITES, null, null);
        db.close();
    }
}
