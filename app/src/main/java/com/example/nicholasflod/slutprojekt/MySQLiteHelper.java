package com.example.nicholasflod.slutprojekt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
public class MySQLiteHelper extends SQLiteOpenHelper
{
    private static final String TAG = "MyActivity";
    public static final String DATABASE_NAME = "Inlagg.db";
    public static final String TABLE_NAME = "Inlagg_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "RUBRIK";
    public static final String COL_3 = "UNDERTEXT";
    public static final String COL_4 = "BILD";
    public static final String COL_5 = "POSITION";

    //Konstruktor
    public MySQLiteHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    //Skapar tabellen
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(" CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, RUBRIK TEXT, UNDERTEXT TEXT, BILD TEXT, POSITION TEXT) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /*Matar in data i tabellen. Väljer att inte föra in data i kolumn 5 tills vidare
     Då denna skall använda i syfte vid vidareutveckling där positioner för alla inlägg sparas*/
    public boolean infogaData(String rubrik, String underText, String bildfilvag)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
            contentValues.put(COL_2,rubrik);
            contentValues.put(COL_3, underText);
            contentValues.put(COL_4, bildfilvag);

        /* Funktionen insert ger ett felmeddelande "Error inserting" om insert inte fungerar
         * den retunerar också värdet -1 och jag kan därför utnyttja detta */
        long result = db.insert(TABLE_NAME,null,contentValues);
        if (result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    //Metod som hämtar datan från databasen
    public Cursor HamtaData()
    {
        Cursor cursor = getReadableDatabase().query(TABLE_NAME,
                new String[] { COL_1, COL_2, COL_3, COL_4, COL_5},
                null, null, null, null, null);


        return cursor;
    }

    //Metod för att ta bort en specifik rad ur databasen/Ett inlägg ur listviewn
    public void deleteSingleRow(int rowId)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM  " + TABLE_NAME +
                " WHERE ID = '" + rowId+"'");
        Log.e("DATABAS OPERATION", "Rad: "+rowId + " Borttagen");
        db.close();
    }


    public void insertPosition(int rowId, String position)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_5,position);
        db.update(TABLE_NAME,cv,"ID="+rowId,null);
    }


    //Metod som rensar databasen på all data och skapar en ny table
    public void deleteAllNotes() {
        //Hämta databasen
        SQLiteDatabase db = this.getWritableDatabase();
        //Ta bort alla värden ur databasen för att rensa det
        db.delete (TABLE_NAME, null, null);
        //Ta bort den gamla tabellen för att återställa ID
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        //Skapa en ny tabell efteråt
        onCreate(db);
        //Stänger tabellen
        db.close();
    }
}
