package com.raul.leeqr

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class CreaQR : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lay_crea_qr)
        val btnborra = findViewById<Button>(R.id.btndelet)
        val btnnueQR = findViewById<Button>(R.id.btngeneraQR)
        val hueco="\n"
        val tarjcont="BEGIN:VCARD"+"\n"+"VERSION:3.0"+"\n"
        val envemail="MATMSG:TO: "
        val nombre="N:"
        val telf="TEL;TYPE=VOICE:"
        val web="URL:"
        val email="EMAIL:"
        val empresa="ORG:"
        val notas="NOTE:"
        val finvc="END:VCARD"
        val rbdgrp = findViewById<RadioGroup>(R.id.rbdgroup)
        val lemail = findViewById<View>(R.id.layemail)
        val lVcard = findViewById<View>(R.id.layVcard)
        val lurl = findViewById<View>(R.id.layTxturl)
        val lbla = findViewById<TextView>(R.id.lblaviso)

        val urltexto = findViewById<EditText>(R.id.txturltxt)

        val vnombre = findViewById<EditText>(R.id.txtnom)
        val vapell = findViewById<EditText>(R.id.txtapell)
        val vtelf = findViewById<EditText>(R.id.txttelf)
        val vmail = findViewById<EditText>(R.id.txtmailvcard)
        val vweb = findViewById<EditText>(R.id.txtweb)
        val vempresa = findViewById<EditText>(R.id.txtempresa)
        val vnotas = findViewById<EditText>(R.id.txtnot)

        val edest = findViewById<EditText>(R.id.txtdestin)
        val easun = findViewById<EditText>(R.id.txtasunto)
        val econte = findViewById<EditText>(R.id.txtconten)

        var selec = 0

        var final = ""

        rbdgrp.setOnCheckedChangeListener { group, checkedId ->
            selec = rbdgrp.checkedRadioButtonId
            when (selec) {
                R.id.rbdUrlTxt -> {
                    lemail.visibility = View.GONE
                    lVcard.visibility = View.GONE
                    lurl.visibility = View.VISIBLE
                    lbla.setText(R.string.avisoUrl)
                }

                R.id.rbdEmail -> {
                    lemail.visibility = View.VISIBLE
                    lVcard.visibility = View.GONE
                    lurl.visibility = View.GONE
                    lbla.setText(R.string.avisoEmail)
                }

                R.id.rbdVcard -> {
                    lemail.visibility = View.GONE
                    lVcard.visibility = View.VISIBLE
                    lurl.visibility = View.GONE
                    lbla.setText(R.string.avisoVcard)
                }

                else -> {
                    println("e")
                }
            }
        }
        btnborra.setOnClickListener {
         val creamens = AlertDialog.Builder(this)
         creamens.setMessage("Se borrará todo el contenido, ¿estás seguro?")
             .setCancelable(true)
             .setPositiveButton("Borrar"){ dialog, id ->
                 when (selec){
                     R.id.rbdUrlTxt ->
                     {
                         urltexto.setText("")
                     }
                     R.id.rbdEmail ->
                     {
                         easun.setText("")
                         edest.setText("")
                         econte.setText("")
                     }
                     R.id.rbdVcard ->
                     {
                         vnombre.setText("")
                         vapell.setText("")
                         vmail.setText("")
                         vempresa.setText("")
                         vtelf.setText("")
                         vweb.setText("")
                         vnotas.setText("")
                     }
                 }
             }
             .setNegativeButton("Cancelar"){ dialog, id ->
                 dialog.dismiss()
             }
         val mens =creamens.create()
         mens.show()
     }
        fun llamacreaqr(){
            val i = Intent(this,QrGenerado::class.java)
            i.putExtra("todo",final)
            startActivity(i)
        }
        btnnueQR.setOnClickListener {

            when (selec){
                R.id.rbdUrlTxt ->
                {
                    if (urltexto.text.toString().trim().isEmpty() ||urltexto.text.toString().trim()==""){
                        Toast.makeText(this, "El campo está vacío, introduce algún valor", Toast.LENGTH_SHORT).show()
                    }else{
                        final = (urltexto.text).toString()
                        llamacreaqr()
                        Toast.makeText(this, "Creando su QR...", Toast.LENGTH_SHORT).show()
                    }
                }
                R.id.rbdEmail ->
                {
                    if (edest.text.toString().trim().isEmpty() || easun.text.toString().trim()
                            .isEmpty() || econte.text.toString().trim().isEmpty()
                    ){
                        Toast.makeText(this, "No puede haber un campo vacío, introduce algún valor", Toast.LENGTH_SHORT).show()
                    }else{
                        final = envemail+(edest.text)+";SUB:"+(easun.text)+";BODY:"+(econte.text)+".;;"
                        llamacreaqr()
                        Toast.makeText(this, "Creando su QR...", Toast.LENGTH_SHORT).show()
                    }

                }
                R.id.rbdVcard ->
                {
                    if(vnombre.text.toString().trim().isEmpty() ||vtelf.text.toString().trim().length<9||vnombre.text.toString().trim()==""||vtelf.text.toString().trim()==""){
                        Toast.makeText(this, "Debes introducir como mínimo, un nombre y un teléfono válido", Toast.LENGTH_SHORT).show()
                    }else{
                        final = tarjcont+nombre
                        if (vapell.text.toString().trim().isNotEmpty()){
                            final= final+(vapell.text)+";"
                        }
                        final=final+(vnombre.text)+hueco+"FN:"+(vnombre.text)+" "+(vapell.text)+hueco
                        if (vempresa.text.toString().trim().isNotEmpty()){
                            final=final+empresa+(vempresa.text)+hueco
                        }
                        if (vweb.text.toString().trim().isNotEmpty()){
                            final=final+web+(vweb.text)+hueco
                        }
                        if (vmail.text.toString().trim().isNotEmpty()){
                            final=final+email+(vmail.text)+hueco
                        }
                        final=final+telf+(vtelf.text)+hueco
                        if (vnotas.text.toString().trim().isNotEmpty()){
                            final=final+notas+(vnotas.text)+hueco
                        }
                        final += finvc
                        llamacreaqr()
                        Toast.makeText(this, "Creando su QR...", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }
}