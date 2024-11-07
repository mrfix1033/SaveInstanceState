package ru.mrfix1033.saveinstancestate

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import ru.mrfix1033.listview.data.User

class MainActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    lateinit var listView: ListView
    lateinit var editTextName: EditText
    lateinit var editTextAge: EditText
    lateinit var buttonAdd: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        listView = findViewById(R.id.listView)
        editTextName = findViewById(R.id.editTextName)
        editTextAge = findViewById(R.id.editTextAge)
        buttonAdd = findViewById(R.id.buttonAdd)

        // задаём заголовок ToolBar'а
        setSupportActionBar(toolbar)
        title = getString(R.string.catalog_of_users)
        // создаём ViewModelProvider
        val userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        // создаём адаптер
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            userViewModel.currentList.value!!
        )
        listView.adapter = adapter

        // регистрируем Observer
        userViewModel.currentList.observe(this) {
            adapter.notifyDataSetChanged()
        }

        // настраиваем кнопку
        buttonAdd.setOnClickListener {
            val age = editTextAge.text.toString().toIntOrNull() ?: return@setOnClickListener
            userViewModel.currentList.value!!.add(
                User(
                    editTextName.text.toString(),
                    age
                )
            )
            editTextName.text.clear()
            editTextAge.text.clear()
            triggerObserver(userViewModel.currentList)
        }

        // назначаем действие при клике на элемент ListView
        listView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                userViewModel.currentList.value!!.removeAt(position)
                triggerObserver(userViewModel.currentList)
            }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuItemExit -> finish()
        }
        return true
    }

    fun <T> triggerObserver(mutableLiveData: MutableLiveData<T>) {
        mutableLiveData.value = mutableLiveData.value
    }
}