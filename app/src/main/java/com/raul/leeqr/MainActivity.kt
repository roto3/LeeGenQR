package com.raul.leeqr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions

class MainActivity : AppCompatActivity() {
    private var url=""
    private val qrscLauncher = registerForActivityResult(ScanContract()){ result: ScanIntentResult ->
    run {
        if (result.contents != null) {
            setResult(result.contents)
        } else {
            Toast.makeText(this,"Error",Toast.LENGTH_LONG)
        }
    }
    }
    private fun setResult(resultado: String){
        url=resultado
        val i = Intent(this@MainActivity,QrWebview::class.java)
        i.putExtra("url",url)
        startActivity(i)
        //Toast.makeText(this,url,Toast.LENGTH_LONG).show()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnscaneo = findViewById<Button>(R.id.btnscan)
        val btncreaqr = findViewById<Button>(R.id.btncrea)
        btnscaneo.setOnClickListener {
            try {
                escanea()
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_LONG).show()
            }
        }
        btncreaqr.setOnClickListener {
            val i = Intent(this@MainActivity,CreaQR::class.java)
            startActivity(i)
        }
    }

    private fun escanea() {
        val opciones = ScanOptions()
        opciones.setBeepEnabled(false)
        opciones.setPrompt("escanea el QR")
        opciones.setOrientationLocked(false)
        qrscLauncher.launch(opciones)
    }
}