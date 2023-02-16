package com.example.noteepaad

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import java.io.File


class MainActivity : AppCompatActivity() {

    private val FILENAME = "sample.txt" // имя файла

    private lateinit var editText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.editText)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_open -> {
                openFile(FILENAME)
                true
            }
            R.id.action_save -> {
                saveFile(FILENAME)
                true
            }

            R.id.action_settings -> {
                val intent = Intent()
                intent.setClass(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }

            else -> true

        }


    }

    // Метод для открытия файла
    private fun openFile(fileName: String) {

        val textFromFile =
            File(applicationContext.filesDir, fileName)
                .bufferedReader()
                .use { it.readText(); }
        editText.setText(textFromFile)
    }

    // Метод для сохранения файла
    private fun saveFile(fileName: String) {
        applicationContext.openFileOutput(fileName, Context.MODE_PRIVATE).use {
            it.write(editText.text.toString().toByteArray())
        }
        Toast.makeText(applicationContext, "Файл был сохранён", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()

        val prefs = PreferenceManager
            .getDefaultSharedPreferences(this)
        // читаем установленное значение из CheckBoxPreference
        if (prefs.getBoolean(getString(R.string.pref_openmode), false)) {
            openFile(FILENAME)
        }

        if (prefs.getBoolean(getString(R.string.pref_underline), false)) {
            editText.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        }
        else{
            editText.paintFlags = Paint.ANTI_ALIAS_FLAG
        }


        val fontSize = prefs.getString(
            getString(R.string.pref_size), "20")!!.toFloat()
        editText.textSize = fontSize


        val regular = prefs.getString(getString(R.string.pref_style), "")
        var typeface = Typeface.NORMAL


        if (regular!!.contains("Полужирный")) typeface += Typeface.BOLD

        if (regular.contains("Курсив")) typeface += Typeface.ITALIC



        val colorText = prefs.getString(getString(R.string.pref_color), "")

        if (colorText!!.contains("Черный")) editText.setTextColor(Color.BLACK)
        if (colorText!!.contains("Красный")) editText.setTextColor(Color.RED)
        if (colorText!!.contains("Синий")) editText.setTextColor(Color.BLUE)
        if (colorText!!.contains("Зеленый")) editText.setTextColor(Color.GREEN)
        if (colorText!!.contains("Желтый")) editText.setTextColor(Color.YELLOW)
        if (colorText!!.contains("Серый")) editText.setTextColor(Color.GRAY)
        if (colorText!!.contains("Фиолетовый")) editText.setTextColor(Color.MAGENTA)


        val colorBackground = prefs.getString(getString(R.string.pref_background), "")

        if (colorBackground!!.contains("Белый")) editText.setBackgroundColor(Color.WHITE)
        if (colorBackground!!.contains("Серый")) editText.setBackgroundColor(Color.LTGRAY)
        if (colorBackground!!.contains("Черный")) editText.setBackgroundColor(Color.BLACK)



        // editText.setBackgroundColor(Color.RED)
            //

        editText.setTypeface(null, typeface)
    }
}