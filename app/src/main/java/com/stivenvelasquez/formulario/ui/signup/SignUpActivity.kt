package com.stivenvelasquez.formulario.ui.signup

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import com.google.android.material.snackbar.Snackbar
import com.stivenvelasquez.formulario.R
import com.stivenvelasquez.formulario.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var  signUpBinding: ActivitySignUpBinding

    private var txtFecha:EditText?=null
    private var btnFecha:ImageButton?=null
    private var dpFecha:DatePicker?=null
    private var spNacimiento:Spinner?=null
    private var tvSeleccion:TextView?=null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.signUpBinding =ActivitySignUpBinding.inflate(layoutInflater)
        val view= this.signUpBinding.root
        setContentView(view)

        txtFecha=signUpBinding.DateEditText
        btnFecha=signUpBinding.DateButton
        dpFecha=signUpBinding.dpDate
        spNacimiento=signUpBinding.spBirth
        tvSeleccion=signUpBinding.SeleccionTextView

        txtFecha?.setText(getFechaDtePicker())

        /*Fecha de Nacimiento*/
        dpFecha?.setOnDateChangedListener{
                dpFecha, _,_ ,_ ->
            txtFecha?.setText(getFechaDtePicker())
            dpFecha.visibility=View.GONE
        }

        /*Ciudad de origen*/
        val listaCiudades= arrayOf(getString(R.string.Seleccione), "Medellín", "Bogotá", "Cali", "Barranquilla", "Manizales")
        val adaptador: ArrayAdapter<String> =ArrayAdapter(this, android.R.layout.simple_spinner_item, listaCiudades)
        spNacimiento?.adapter=adaptador

        spNacimiento?.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                if(position>0){

                    val mensaje = getString(R.string.Seleccionaste).plus(" ").plus(spNacimiento?.selectedItem.toString())
                    tvSeleccion?.text = mensaje
                }else{
                    tvSeleccion?.text=getString(R.string.No_has_seleccionado)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                tvSeleccion?.text=getString(R.string.No_has_seleccionado)
            }

        }

        /*Boton de Guardar datos*/
        signUpBinding.registerButton.setOnClickListener{

            val name =signUpBinding.nameEditText.text.toString() //dat editable a string
            val email = signUpBinding.emailEditText.text.toString()
            val password =signUpBinding.passwordEditText.text.toString()
            val repPassword=signUpBinding.repPasswordEditText.text.toString()
            val fechaNacimiento=signUpBinding.DateEditText.text.toString()
            val lugarNacimiento=signUpBinding.SeleccionTextView.text.toString()

            if (name.isBlank() || email.isBlank() || password.isBlank() || repPassword.isBlank() || fechaNacimiento.isBlank()|| lugarNacimiento.isBlank()) {
                Toast.makeText(applicationContext, getString(R.string.completar_campos) , Toast.LENGTH_LONG).show()
                signUpBinding.infoTextView.text = " "
                return@setOnClickListener
            }

            val genre = if(signUpBinding.maleRadioButton.isChecked)
                getString(R.string.male)
            else
                getString(R.string.female)

            var favoritesGenre= ""
            if(signUpBinding.actionCheckBox.isChecked) favoritesGenre = getString(R.string.action)
            if(signUpBinding.dramaCheckBox.isChecked) favoritesGenre += " "+ getString(R.string.drama)
            if(signUpBinding.comicCheckBox.isChecked) favoritesGenre += " "+ getString(R.string.comic)
            if(signUpBinding.loveCheckBox.isChecked) favoritesGenre +=" "+ getString(R.string.love)


            val registerInfo = getString(R.string.name) + " : " + name
            val emailInfo = getString(R.string.email) + " : " + email
            val passwordInfo = getString(R.string.password) + " : " + password
            val generoInfo=getString(R.string.Genero) + " : " + genre
            val generosFavoritosInfo=getString(R.string.genre_favorites) + " : " + favoritesGenre
            val fechaNacimientoInfo=getString(R.string.DateBirth) + " : " + fechaNacimiento
            val lugarNacimientoInfo=getString(R.string.city_birth) + " : " + lugarNacimiento

            val info = "$registerInfo\n$emailInfo\n$passwordInfo\n" +
                    "$generoInfo\n" +
                    "$generosFavoritosInfo\n" +
                    "$fechaNacimientoInfo\n" +
                    "$lugarNacimientoInfo\n"

            //Comprobar contraseñas
            if(password == repPassword)
                signUpBinding.infoTextView.text = info
            else{
                signUpBinding.infoTextView.text = " "
                Snackbar.make(signUpBinding.linearLayout,getString(R.string.Contras_Incorrecta), Snackbar.LENGTH_INDEFINITE).setAction("Aceptar"){
                    signUpBinding.repPasswordEditText.setText("")
                    signUpBinding.repPasswordTextInputLayout.isErrorEnabled=false
                }
                    .show()
            }
        }

    }
    private fun getFechaDtePicker():String{
        val dia=dpFecha?.dayOfMonth.toString().padStart(2,'0')
        val mes=(dpFecha!!.month+1).toString().padStart(2,'0')
        val anio=dpFecha?.year.toString().padStart(4,'0')
        return "$dia/$mes/$anio"
    }

    fun muestraCalendario(view:View){
        dpFecha?.visibility=View.VISIBLE
    }
}