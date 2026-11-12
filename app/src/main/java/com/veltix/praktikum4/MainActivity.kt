package com.veltix.praktikum4

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var db: DatabaseHelper
    private lateinit var adapter: MahasiswaAdapter
    private val dataMahasiswa = mutableListOf<Mahasiswa>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = DatabaseHelper(this)
        adapter = MahasiswaAdapter(this, dataMahasiswa)
        val listView = findViewById<ListView>(R.id.listMahasiswa)
        listView.adapter = adapter

        findViewById<Button>(R.id.btnTambah).setOnClickListener {
            startActivity(Intent(this, FormActivity::class.java))
        }

        // Ketuk baris: kirim id ke DetailActivity.
        listView.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_ID, dataMahasiswa[position].id)
            startActivity(intent)
        }
    }

    // Dipanggil lagi setiap DetailActivity atau FormActivity ditutup.
    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData() {
        dataMahasiswa.clear()
        dataMahasiswa.addAll(db.getAllMahasiswa())
        adapter.notifyDataSetChanged()
    }
}