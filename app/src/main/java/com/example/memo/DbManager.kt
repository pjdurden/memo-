package com.example.memo

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.widget.Toast
import android.widget.TextView
import android.graphics.PorterDuff

import android.R.attr.duration
import android.R.id.message
import android.graphics.Color
import android.util.Half.toFloat


class DbManager {

    var Memodb = "MyMemo"
    var memoTable = "Memo"
    var colID = "ID"
    var colTitle = "Title"
    var colDes = "Description"
    var dbVersion = 1

    val sqlCreateTable = "CREATE TABLE IF NOT EXISTS " + memoTable + " (" + colID + " INTEGER PRIMARY KEY AUTOINCREMENT," + colTitle + " TEXT, " + colDes + " TEXT);"




    var sqlDB: SQLiteDatabase? = null

    constructor(context: Context) {
        var db = DatabaseHelpermemo(context)
        sqlDB = db.writableDatabase

    }

    inner class DatabaseHelpermemo : SQLiteOpenHelper {
        var context: Context? = null

        constructor(context: Context) : super(context, Memodb, null, dbVersion) {
            this.context = context
        }

        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL(sqlCreateTable)
            val contentValues = ContentValues()
            contentValues.put("Title", "WELCOME")
            contentValues.put("Description", " What if there were no hypothetical questions?" +
                    "Hey hello there, Its a me , memorio. Make new memo by clicking on +(plus) button. Copy memo and share memo by clicking on icons below the Description box on the main screen." +
                    "P.S. This app uses autosave, So dont worry about not cliking on add button(which is down under). See ya")
            db.insert("Memo", null, contentValues)


            val toast = Toast.makeText(this.context, "database created", Toast.LENGTH_SHORT)
            val view = toast.view

            view.background.setColorFilter(Color.DKGRAY, PorterDuff.Mode.SRC_IN)


            val text = view.findViewById<TextView>(message)
            text.setTextColor(Color.WHITE)
            text.setShadowLayer(toFloat(0), toFloat(0), toFloat(0), Color.TRANSPARENT);

            toast.show()

        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL("Drop table if Exists" + memoTable)
        }


    }

    fun insert(values: ContentValues): Long {
        val ID = sqlDB!!.insert(memoTable, "", values)
        return ID
    }

    fun Query(projection: Array<String>, selection: String, selectionArgs: Array<String>, sorOrder: String): Cursor {
        val qb = SQLiteQueryBuilder()
        qb.tables = memoTable
        val cursor = qb.query(sqlDB, projection, selection, selectionArgs, null, null, sorOrder)
        return cursor
    }

    fun delete(selection: String, selectionArgs: Array<String>): Int {
        val count = sqlDB!!.delete(memoTable, selection, selectionArgs)
        return count
    }

    fun update(values: ContentValues, selection: String, selectionArgs: Array<String>): Int {
        val count = sqlDB!!.update(memoTable, values, selection, selectionArgs)
        return count
    }


}
