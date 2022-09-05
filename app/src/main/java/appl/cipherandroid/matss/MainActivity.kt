package appl.cipherandroid.matss

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import appl.cipherandroid.matss.constants.Constants
import appl.cipherandroid.matss.databinding.ActivityMainBinding
import appl.cipherandroid.matss.utils.Utils
import com.google.android.material.textfield.TextInputEditText
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var etCipher: TextInputEditText
    private lateinit var tvResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        etCipher = binding.etCipher
        tvResult = binding.tvResults
        binding.btnEncrypt.setOnClickListener {
            val cipher = etCipher.text.toString()
            tvResult.text = if(cipher.isEmpty()){
                "Cannot accept empty data"
            } else {
                Utils.toEncrypt(cipher)
            }
        }
        binding.btnDecrypt.setOnClickListener {
            val cipher = etCipher.text.toString()
            tvResult.text = if(cipher.isEmpty()){
                "Cannot accept empty data"
            } else {
                Utils.toDecrypt(etCipher.text.toString())
            }
        }
        binding.fabSave.setOnClickListener {
            writeLocalFile()
        }
    }

    private fun writeLocalFile() {
        val intent = Intent().apply {
            this.action = "android.intent.action.CREATE_DOCUMENT"
            this.addCategory("android.intent.category.OPENABLE")
            this.type = "*text/plain"
            this.putExtra("android.intent.extra.TITLE", "cipher_file.${Constants.FILE_EXTENSIONS}")
        }
        startActivityIntent.launch(intent)
    }

    private val startActivityIntent = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            try {
                val fileOutputStream =
                    contentResolver.openOutputStream(data?.data!!) as FileOutputStream?
                fileOutputStream!!.write(tvResult.text.toString().toByteArray())
                fileOutputStream.flush()
                fileOutputStream.close()
                Toast.makeText(this, "File created, successfully!", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

}