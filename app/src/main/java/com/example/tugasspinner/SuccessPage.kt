package com.example.tugasspinner


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tugasspinner.databinding.ActivitySuccessPageBinding


class SuccessPage : AppCompatActivity() {

    private lateinit var binding : ActivitySuccessPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySuccessPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_NAME) ?: "Guest"
        val jam = intent.getStringExtra(EXTRA_JAM) ?: "No Email"
        val tanggal = intent.getStringExtra(EXTRA_TANGGAL) ?: "No Phone"
        val tujuan = intent.getStringExtra(EXTRA_TUJUAN) ?: "No Phone"

        binding.name.text = username
        binding.jam.text = jam
        binding.tanggal.text = tanggal
        binding.tujuan.text = tujuan

    }


    companion object  {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_JAM = "extra_jam"
        const val EXTRA_TANGGAL = "extra_tanggal"
        const val EXTRA_TUJUAN = "extra_tujuan"
    }


}