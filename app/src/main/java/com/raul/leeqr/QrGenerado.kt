package com.raul.leeqr

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import java.io.File
import java.io.FileOutputStream


class QrGenerado : AppCompatActivity() {
    private var txtreci: TextView? = null
    private var foto: ImageView?= null
    private var btnGuarda: Button?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_generado)
        val contprue = intent.extras!!.getString("todo")
        txtreci = findViewById(R.id.txtrecib)
        foto = findViewById(R.id.fotoqr)
        btnGuarda = findViewById(R.id.btnguardaQR)
        txtreci!!.text = contprue

        val qrCodeWriter = QRCodeWriter()
        val bitMatrix = qrCodeWriter.encode(contprue, BarcodeFormat.QR_CODE, 1000, 1000)

//        var aleat = (0..100000).random()
//        var nombrefich = "QR_generado_"+aleat

        val ancho = bitMatrix.width
        val largo = bitMatrix.height
        val bmp = Bitmap.createBitmap(ancho,largo, Bitmap.Config.RGB_565)

        for (x in 0 until ancho) {
            for (y in 0 until largo) {
                bmp.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
            }
        }

        try {
            foto!!.setImageBitmap(bmp)
        } catch (e: Exception) {
            Toast.makeText(this, "Error: "+e, Toast.LENGTH_SHORT).show()
        }

        fun guardafoto() {
            val salidaFich: FileOutputStream?
            val ubicacionDisp = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) //Aquí sacamos la ubicación en el dispositivo, concretamente la ruta de imágenes (pictures/images/imágenes)
            val dir = File(ubicacionDisp.absolutePath + "/QrsGen") //aquí la ruta de nuestra carpeta, añadida al final del directorio base
            dir.mkdirs() //lo crea en caso de no existir
            val nombreFotoqr = String.format("%d.jpg", System.currentTimeMillis()) //nuestro archivo tendrá por defecto el nombre del timestamp
            val archivofinal = File(dir, nombreFotoqr)

            try {
                salidaFich = FileOutputStream(archivofinal)
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, salidaFich)
                salidaFich.flush()
                salidaFich.close()
                Toast.makeText(this,
                    getString(R.string.mens_QR_gen_bien, nombreFotoqr), Toast.LENGTH_SHORT).show() //feedback para el usuario del guardado de imagen
            } catch (e: Exception) {
                e.printStackTrace()
                println("aquí la cagadita")
            }
        } //Esta función es la que genera y guarda el archivo jpg del QR generado
        fun llamaMens() {
            val creamens = AlertDialog.Builder(this)
            creamens.setMessage(getString(R.string.mens_guardaQR))
                .setCancelable(true)
                .setPositiveButton(getString(R.string.btn_mens_guardar)){ dialog, id ->
                    guardafoto()
                }
                .setNegativeButton(getString(R.string.btn_mens_cancelar)){ dialog, id ->
                    dialog.dismiss()
                }
            val mens =creamens.create()
            mens.show()
        } //Esta función simplemente saca el Dialog para llamar a la función guardafoto

        //tenemos 2 formas de guardar la foto, por si el usuario es imbécil
        foto!!.setOnLongClickListener{
            llamaMens()
            true
        }
        btnGuarda!!.setOnClickListener {
            llamaMens()
        }
    }



}