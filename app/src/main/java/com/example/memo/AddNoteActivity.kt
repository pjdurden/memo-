package com.example.memo

import android.content.ContentValues
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Half
import android.view.View
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_note.*


class AddNoteActivity : AppCompatActivity() {

    val memoTable = "Memos"
    var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        try {
            val bundle:Bundle = intent.extras
            id = bundle.getInt("ID", 0)
            if (id!=0){
                titleEt.setText(bundle.getString("name"))
                descEt.setText(bundle.getString("des"))

            }
        }catch (ex:Exception){}



    }
    fun autosave() {
        var dbManager = DbManager(this)

        var values = ContentValues()
        values.put("Title", titleEt.text.toString())
        values.put("Description", descEt.text.toString())
        if (id ==0){
            val ID = dbManager.insert(values)
            if (ID>0){

                val toast = Toast.makeText(this, "memo added", Toast.LENGTH_SHORT)
                val view = toast.view

                view.background.setColorFilter(Color.DKGRAY, PorterDuff.Mode.SRC_IN)


                val text = view.findViewById<TextView>(android.R.id.message)
                text.setTextColor(Color.WHITE)
                text.setShadowLayer(Half.toFloat(0), Half.toFloat(0), Half.toFloat(0), Color.TRANSPARENT);

                toast.show()



                finish()
            }
            else{
                val toast = Toast.makeText(this, "error adding memo", Toast.LENGTH_SHORT)
                val view = toast.view

                view.background.setColorFilter(Color.DKGRAY, PorterDuff.Mode.SRC_IN)


                val text = view.findViewById<TextView>(android.R.id.message)
                text.setTextColor(Color.WHITE)
                text.setShadowLayer(Half.toFloat(0), Half.toFloat(0), Half.toFloat(0), Color.TRANSPARENT);

                toast.show()

            }
        }
        else {
            var selectionArgs = arrayOf(id.toString())
            val ID = dbManager.update(values, "ID=?", selectionArgs)
            if (ID > 0) {
                val toast = Toast.makeText(this, "memo added", Toast.LENGTH_SHORT)
                val view = toast.view

                view.background.setColorFilter(Color.DKGRAY, PorterDuff.Mode.SRC_IN)


                val text = view.findViewById<TextView>(android.R.id.message)
                text.setTextColor(Color.WHITE)
                text.setShadowLayer(Half.toFloat(0), Half.toFloat(0), Half.toFloat(0), Color.TRANSPARENT);

                toast.show()

                finish()
            }
            else {
                val toast = Toast.makeText(this, "error adding memo", Toast.LENGTH_SHORT)
                val view = toast.view

                view.background.setColorFilter(Color.DKGRAY, PorterDuff.Mode.SRC_IN)


                val text = view.findViewById<TextView>(android.R.id.message)
                text.setTextColor(Color.WHITE)
                text.setShadowLayer(Half.toFloat(0), Half.toFloat(0), Half.toFloat(0), Color.TRANSPARENT);

                toast.show()

            }
        }
    }


    fun addFunc(view: View){
        var dbManager = DbManager(this)

        var values = ContentValues()
        values.put("Title", titleEt.text.toString())
        values.put("Description", descEt.text.toString())
        if (id ==0){
            val ID = dbManager.insert(values)
            if (ID>0){

                val toast = Toast.makeText(this, "memo added", Toast.LENGTH_SHORT)
                val view = toast.view

                view.background.setColorFilter(Color.DKGRAY, PorterDuff.Mode.SRC_IN)


                val text = view.findViewById<TextView>(android.R.id.message)
                text.setTextColor(Color.WHITE)
                text.setShadowLayer(Half.toFloat(0), Half.toFloat(0), Half.toFloat(0), Color.TRANSPARENT);

                toast.show()



                finish()
            }
            else{
                val toast = Toast.makeText(this, "error adding memo", Toast.LENGTH_SHORT)
                val view = toast.view

                view.background.setColorFilter(Color.DKGRAY, PorterDuff.Mode.SRC_IN)


                val text = view.findViewById<TextView>(android.R.id.message)
                text.setTextColor(Color.WHITE)
                text.setShadowLayer(Half.toFloat(0), Half.toFloat(0), Half.toFloat(0), Color.TRANSPARENT);

                toast.show()

            }
        }
        else {
            var selectionArgs = arrayOf(id.toString())
            val ID = dbManager.update(values, "ID=?", selectionArgs)
            if (ID > 0) {
                val toast = Toast.makeText(this, "memo added", Toast.LENGTH_SHORT)
                val view = toast.view

                view.background.setColorFilter(Color.DKGRAY, PorterDuff.Mode.SRC_IN)


                val text = view.findViewById<TextView>(android.R.id.message)
                text.setTextColor(Color.WHITE)
                text.setShadowLayer(Half.toFloat(0), Half.toFloat(0), Half.toFloat(0), Color.TRANSPARENT);

                toast.show()

                finish()
            } else {
                val toast = Toast.makeText(this, "error adding memo", Toast.LENGTH_SHORT)
                val view = toast.view

                view.background.setColorFilter(Color.DKGRAY, PorterDuff.Mode.SRC_IN)


                val text = view.findViewById<TextView>(android.R.id.message)
                text.setTextColor(Color.WHITE)
                text.setShadowLayer(Half.toFloat(0), Half.toFloat(0), Half.toFloat(0), Color.TRANSPARENT);

                toast.show()

            }
        }
    }

    override fun onBackPressed() {
        autosave()
        super.onBackPressed()
    }

}
