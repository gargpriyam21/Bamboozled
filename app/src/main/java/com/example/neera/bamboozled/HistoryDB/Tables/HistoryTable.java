package com.example.neera.bamboozled.HistoryDB.Tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.neera.bamboozled.HistoryDB.Model.HistoryData;

import java.util.ArrayList;

import static com.example.neera.bamboozled.HistoryDB.Tables.DBConstants.CMD_CREATE_TABLE_INE;
import static com.example.neera.bamboozled.HistoryDB.Tables.DBConstants.COMMA;
import static com.example.neera.bamboozled.HistoryDB.Tables.DBConstants.LBR;
import static com.example.neera.bamboozled.HistoryDB.Tables.DBConstants.RBR;
import static com.example.neera.bamboozled.HistoryDB.Tables.DBConstants.SEMI;
import static com.example.neera.bamboozled.HistoryDB.Tables.DBConstants.TYPE_AI;
import static com.example.neera.bamboozled.HistoryDB.Tables.DBConstants.TYPE_INT;
import static com.example.neera.bamboozled.HistoryDB.Tables.DBConstants.TYPE_PK;
import static com.example.neera.bamboozled.HistoryDB.Tables.DBConstants.TYPE_TEXT;

/**
 * Created by Neera on 07/10/17.
 */

public class HistoryTable {

    public static final String TABLE_NAME = "history";
    public static final String CMD_CREATE =
            CMD_CREATE_TABLE_INE + TABLE_NAME +
                    LBR +
                    Coloumns.ID + TYPE_INT + TYPE_PK + TYPE_AI + COMMA +
                    Coloumns.SCORE + TYPE_TEXT + COMMA +
                    Coloumns.DATE + TYPE_TEXT +
                    RBR +
                    SEMI;

    public static ArrayList<HistoryData> getHistory(SQLiteDatabase db) {
        ArrayList<HistoryData> history = new ArrayList<>();

        Cursor c = db.query(
                TABLE_NAME,
                new String[]{Coloumns.ID, Coloumns.SCORE, Coloumns.DATE},
                null,
                null,
                null,
                null,
                null
        );
        int colForID = c.getColumnIndex(Coloumns.ID);
        int colForScore = c.getColumnIndex(Coloumns.SCORE);
        int colForDate = c.getColumnIndex(Coloumns.DATE);
        while (c.moveToNext()) {
            history.add(
                    new HistoryData(
                            c.getInt(colForID),
                            c.getString(colForScore),
                            c.getString(colForDate)
                    )
            );
        }
        return history;
    }

    public static long insertHistory(SQLiteDatabase db, HistoryData history) {
        ContentValues historyData = new ContentValues();
        historyData.put(Coloumns.SCORE, history.getScore());
        historyData.put(Coloumns.DATE, history.getDate());
        return db.insert(
                TABLE_NAME,
                null,
                historyData
        );
    }

    public static void deleteHistory(SQLiteDatabase db, int historyID) {
        db.delete(
                TABLE_NAME,
                Coloumns.ID + "=?",
                new String[]{Integer.toString(historyID)}
        );
    }


    public interface Coloumns {
        String ID = "id";
        String SCORE = "score";
        String DATE = "date";
    }

}
