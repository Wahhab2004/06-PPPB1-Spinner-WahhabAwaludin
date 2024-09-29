package com.example.tugasspinner

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.example.tugasspinner.databinding.ActivityMainBinding
import com.example.tugasspinner.databinding.DialogExitBinding

import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val countries = arrayOf(
        "Tujuan",
        "Indonesia",
        "United States",
        "United Kingdom",
        "Germany",
        "France",
        "Australia",
        "Japan",
        "China",
        "Brazil",
        "Canada"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mengatur adapter untuk spinnerCountry
        val adapterCountry = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            countries
        )
        adapterCountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCountry.adapter = adapterCountry

        binding.spinnerCountry.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                // Menampilkan tujuan yang dipilih
                binding.spinnerText.text = countries[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                binding.spinnerText.visibility = View.VISIBLE // Sembunyikan TextView
            }
        }

        // Tambahkan listener klik untuk TextView
        binding.spinnerText.setOnClickListener {
            binding.spinnerCountry.setSelection(AdapterView.INVALID_POSITION) // Reset spinner
            binding.spinnerText.visibility = View.GONE // Sembunyikan TextView
            binding.spinnerCountry.visibility = View.VISIBLE // Tampilkan Spinner
        }

        // Mengatur onclick listener untuk EditText jamKeberangkatan
        binding.jamKeberangkatan.setOnClickListener {
            showTimePickerDialog()
        }

        // Mengatur onclick listener untuk EditText tanggalKeberangkatan
        binding.tanggalKeberangkatan.setOnClickListener {
            showDatePickerDialog()
        }

        binding.showCustomDialog.setOnClickListener {
            // Validasi input
            if (binding.username.text.isEmpty() || binding.jamKeberangkatan.text.isEmpty() ||
                binding.tanggalKeberangkatan.text.isEmpty() || binding.spinnerText.text.isEmpty()) {
                Toast.makeText(this, "Harap lengkapi semua informasi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val dialog = DialogExit(
                binding.username.text.toString(),
                binding.jamKeberangkatan.text.toString(),
                binding.tanggalKeberangkatan.text.toString(),
                binding.spinnerText.text.toString()
            )
            dialog.show(supportFragmentManager, "dialogExit")
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            binding.tanggalKeberangkatan.setText("$selectedDay/${selectedMonth + 1}/$selectedYear")
        }, year, month, day)

        datePickerDialog.show()
    }

    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
            binding.jamKeberangkatan.setText(String.format("%02d:%02d", selectedHour, selectedMinute))
        }, hour, minute, true)

        timePickerDialog.show()
    }
}



class DialogExit(
    private val username: String,
    private val jamKeberangkatan: String,
    private val tanggalKeberangkatan: String,
    private val tujuan: String
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val binding = DialogExitBinding.inflate(inflater)

        // Mengatur aksi untuk tombol
        with(binding) {
            btnYes.setOnClickListener {
                // Debugging: Log output sebelum memulai activity
                Log.d("DialogExit", "Data yang akan dikirim: Username=$username, Jam=$jamKeberangkatan, Tanggal=$tanggalKeberangkatan, Tujuan=$tujuan")

                val intent = Intent(requireActivity(), SuccessPage::class.java).apply {
                    putExtra(SuccessPage.EXTRA_NAME, username)
                    putExtra(SuccessPage.EXTRA_JAM, jamKeberangkatan)
                    putExtra(SuccessPage.EXTRA_TANGGAL, tanggalKeberangkatan)
                    putExtra(SuccessPage.EXTRA_TUJUAN, tujuan)
                }

                // Memulai activity
                startActivity(intent)
                dismiss() // Menutup dialog setelah memulai activity
            }
            btnNo.setOnClickListener {
                dismiss() // Menutup dialog
            }
        }

        builder.setView(binding.root) // Mengatur tampilan dialog
        return builder.create()
    }
}


