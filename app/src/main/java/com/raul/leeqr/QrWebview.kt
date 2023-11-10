package com.raul.leeqr

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class QrWebview : AppCompatActivity() {
    private var mnav: WebView? = null
    private var txt: TextView? = null
    private var txtcont: TextView?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lay_qr_webview)
        txt = findViewById(R.id.txturl)
        txtcont = findViewById(R.id.txtCont)
        val btncopia = findViewById<Button>(R.id.btncopia)
        val urlwv = intent.extras!!.getString("url")
        mnav = findViewById<View>(R.id.wvwQResult) as WebView
        if (urlwv != null) {
            if (urlwv.contains("http")||urlwv.contains("www.")||urlwv.contains(".com")||urlwv.contains(".COM")||urlwv.contains("HTTP")||urlwv.contains("MATMSG")||urlwv.contains("matmsg")){

                if (urlwv.contains("VCARD")||urlwv.contains("vcard")){
                    txt!!.text = getString(R.string.txt_tarjCont)
                    txtcont!!.text = urlwv
                    txtcont!!.visibility = View.VISIBLE
                    mnav!!.visibility = View.GONE
                }else{
                    if(urlwv.contains("MATMSG")||urlwv.contains("matmsg")){
                        txt!!.text = getString(R.string.txt_envEmail)
                        txtcont!!.text = urlwv
                        txtcont!!.visibility = View.VISIBLE
                        mnav!!.visibility = View.GONE
                    }else{
                        mnav!!.loadUrl(urlwv)
                        txt!!.text = urlwv
                        txtcont!!.visibility = View.GONE
                        mnav!!.visibility = View.VISIBLE
                    }
                }
            }else{
                txt!!.text = getString(R.string.txt_noWeb)
                txtcont!!.text = urlwv
                txtcont!!.visibility = View.VISIBLE
                mnav!!.visibility = View.GONE
            }
        }else{
            mnav?.loadUrl("www.google.es")
            txt!!.text = "fail"
        }
        mnav!!.settings.builtInZoomControls = true
        mnav!!.settings.javaScriptEnabled=true
        mnav!!.settings.cacheMode = WebSettings.LOAD_NO_CACHE

        btncopia.setOnClickListener {
            val portapapeles: ClipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val copiado = ClipData.newPlainText("texto", urlwv)
            portapapeles.setPrimaryClip(copiado)
            //Toast.makeText(this,"¡Copiado!",Toast.LENGTH_LONG).show()
        }
    }
}