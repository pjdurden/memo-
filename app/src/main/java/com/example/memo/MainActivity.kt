package com.example.memo

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.SearchView
import android.text.ClipboardManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.row.view.*

class MainActivity : AppCompatActivity() {

    var listmemo = ArrayList<memo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        LoadQuery("%")
    }

    override fun onResume() {
        super.onResume()
        LoadQuery("%")
    }

    private fun LoadQuery(title: String) {
        var dbManager = DbManager(this)
        val projections = arrayOf("ID", "Title", "Description")
        val selectionArgs = arrayOf(title)
        val cursor = dbManager.Query(projections, "Title like ?", selectionArgs, "Title")
        listmemo.clear()
        if (cursor.moveToFirst()) {

            do {
                val ID = cursor.getInt(cursor.getColumnIndex("ID"))
                val Title = cursor.getString(cursor.getColumnIndex("Title"))
                val Description = cursor.getString(cursor.getColumnIndex("Description"))

                listmemo.add(memo(ID, Title, Description))

            } while (cursor.moveToNext())
        }


        var mymemoAdapter = MymemoAdapter(this, listmemo)

        memo1.adapter = mymemoAdapter


        val total = memo1.count

        val mActionBar = supportActionBar
        if (mActionBar != null) {

            mActionBar.subtitle = "You have $total memo(s)"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)


        val sv: SearchView = menu!!.findItem(R.id.app_bar_search).actionView as SearchView

        val sm = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        sv.setSearchableInfo(sm.getSearchableInfo(componentName))
        sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                LoadQuery("%" + query + "%")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                LoadQuery("%" + newText + "%")
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) {
            when (item.itemId) {
                R.id.addNote -> {
                    startActivity(Intent(this, AddNoteActivity::class.java))
                }
                R.id.action_settings -> {
                    startActivity(Intent(this, Main2Activity::class.java))
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    inner class MymemoAdapter : BaseAdapter {
        var listmemoAdapter = ArrayList<memo>()
        var context: Context? = null

        constructor(context: Context, listmemoAdapter: ArrayList<memo>) : super() {
            this.listmemoAdapter = listmemoAdapter
            this.context = context
        }


        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            var myView = layoutInflater.inflate(R.layout.row, null)
            val myNote = listmemoAdapter[position]
            myView.titleTv.text = myNote.memoName
            myView.descTv.text = myNote.memoDes

            myView.deleteBtn.setOnClickListener {
                var dbManager = DbManager(this.context!!)
                val selectionArgs = arrayOf(myNote.memoID.toString())
                dbManager.delete("ID=?", selectionArgs)
                LoadQuery("%")
            }
            myView.editBtn.setOnClickListener {
                GoToUpdateFun(myNote)
            }
            myView.copyBtn.setOnClickListener {
                val title = myView.titleTv.text.toString()
                val desc = myView.descTv.text.toString()
                val s = title + "\n" + desc
                val cb = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                cb.text = s
                Toast.makeText(this@MainActivity, "Copied...", Toast.LENGTH_SHORT).show()
            }

            myView.shareBtn.setOnClickListener {

                val title = myView.titleTv.text.toString()

                val desc = myView.descTv.text.toString()

                val s = title + "\n" + desc

                val shareIntent = Intent()
                shareIntent.action = Intent.ACTION_SEND
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_TEXT, s)
                startActivity(Intent.createChooser(shareIntent, s))
            }

            return myView
        }

        override fun getItem(position: Int): Any {
            return listmemoAdapter[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return listmemoAdapter.size
        }

    }

    private fun GoToUpdateFun(myMemo: memo) {
        var intent = Intent(this, AddNoteActivity::class.java)
        intent.putExtra("ID", myMemo.memoID)
        intent.putExtra("name", myMemo.memoName)
        intent.putExtra("des", myMemo.memoDes)
        startActivity(intent)
    }
}