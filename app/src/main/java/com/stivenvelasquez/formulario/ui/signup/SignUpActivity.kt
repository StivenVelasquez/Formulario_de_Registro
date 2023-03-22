package com.stivenvelasquez.formulario.ui.signup

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.stivenvelasquez.formulario.R
import com.stivenvelasquez.formulario.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var  signUpBinding: ActivitySignUpBinding
    private lateinit var signupViewModel: SignUpViewModel

    @SuppressLint("StringFormatInvalid")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflar el layout y obtener la instancia del binding
         signUpBinding = ActivitySignUpBinding.inflate(layoutInflater)

        // Inicializar el ViewModel
         signupViewModel = ViewModelProvider(this)[SignUpViewModel::class.java]

        // Obtener la fecha actual y actualizar el campo correspondiente
        val currentDateObserver= Observer<String>{ currentDate->
            signUpBinding.DateEditText.setText(currentDate)
        }
        signupViewModel.currentDate.observe(this, currentDateObserver)
        signupViewModel.currentDate("${signUpBinding.dpDate.dayOfMonth.toString().padStart(2, '0')}/${(signUpBinding.dpDate.month.toString().padStart(2, '0'))}/${signUpBinding.dpDate.year.toString().padStart(4, '0')}")

        // Observar los cambios en la fecha seleccionada y actualizar el campo correspondiente
        val updateSelectedDateObserver= Observer<String>{ date->
            signUpBinding.DateEditText.setText(date)
        }
        signupViewModel.selectedDate.observe(this, updateSelectedDateObserver)

        // Mostrar el DatePicker cuando se hace clic en el botón
        signUpBinding.DateButton.setOnClickListener {
            signUpBinding.dpDate.visibility = View.VISIBLE
        }

        // Actualizar la fecha seleccionada y ocultar el DatePicker cuando se selecciona una fecha
        signUpBinding.dpDate.setOnDateChangedListener { _, year, monthOfYear, dayOfMonth ->
            signupViewModel.updateSelectedDate("$dayOfMonth/${monthOfYear + 1}/$year")
            signUpBinding.dpDate.visibility = View.GONE
        }
        signupViewModel.inicializarCiudades(listOf(getString(R.string.Seleccione), "Medellín", "Bogotá", "Cali", "Barranquilla", "Manizales"))

       // Observar las ciudades de nacimiento disponibles
        val ciudadesDisponiblesObserver = Observer<List<String>> { ciudades ->
            signUpBinding.spBirth.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, ciudades)
        }
        signupViewModel.ciudades.observe(this, ciudadesDisponiblesObserver)

        //Observar ciudad seleccionada
        val ciudadSeleccionada= Observer<String>{ ciudadSeleccionada->

            signupViewModel.ciudadSeleccionada(ciudadSeleccionada,getString(R.string.Seleccione),
                signUpBinding.SeleccionTextView,getString(R.string.No_has_seleccionado),getString(R.string.Seleccionaste))

        }
        signupViewModel.ciudadSeleccionada.observe(this, ciudadSeleccionada)

        // Asignar un listener para cuando se selecciona una ciudad
        signUpBinding.spBirth.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                signupViewModel.actualizarCiudadSeleccionada(parent?.getItemAtPosition(position).toString())
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                signupViewModel.actualizarCiudadSeleccionada("")
            }
        }
//-----------------------------------------------------------------------------------------
        //Registrar Información

        signUpBinding.registerButton.setOnClickListener{

            //Validar que ningún campo este vacío
            signupViewModel.validateInputsAndSignUp(
                signUpBinding.nameEditText.text.toString(),
                signUpBinding.emailEditText.text.toString(),
                signUpBinding.passwordEditText.text.toString(),
                signUpBinding.repPasswordEditText.text.toString(),
                signUpBinding.DateEditText.text.toString(),
                signUpBinding.SeleccionTextView.text.toString(),
                applicationContext, signUpBinding.infoTextView
            )

            if (signupViewModel.camposVacios.value==true)
                return@setOnClickListener

            // set up gender selection logic
            signupViewModel.setGeneroInfo(signUpBinding.maleRadioButton, getString(R.string.male), getString(R.string.female))

            // Informar al ViewModel de los cambios en los datos del usuario
            signupViewModel.emailInfo(signUpBinding.emailEditText.text.toString())
            signupViewModel.registerInfo(signUpBinding.nameEditText.text.toString())
            signupViewModel.passwordInfo(signUpBinding.passwordEditText.text.toString())
            signupViewModel.fechaNacimientoInfo(signUpBinding.DateEditText.text.toString())
            signupViewModel.lugarNacimientoInfo(signUpBinding.SeleccionTextView.text.toString())

            val nameObserver = Observer<String>{ nameInfo ->
                val infoText = "${getString(R.string.name)} : $nameInfo"
                signUpBinding.infoTextView.text = infoText
            }
            signupViewModel.registerInfo.observe(this, nameObserver)

            val emailInfoObserver = Observer<String>{ emailInfo ->
                val infoText = "${getString(R.string.name)} : ${signupViewModel.registerInfo.value}\n" +
                        "${getString(R.string.email)} : $emailInfo"
                signUpBinding.infoTextView.text = infoText
            }
            signupViewModel.emailInfo.observe(this, emailInfoObserver)

            val passwordInfoObserver = Observer<String>{ password ->
                val infoText = "${getString(R.string.name)} : ${signupViewModel.registerInfo.value}\n" +
                        "${getString(R.string.email)} : ${signupViewModel.emailInfo.value}\n${getString(R.string.password)} : $password"
                signUpBinding.infoTextView.text = infoText
            }
            signupViewModel.passwordInfo.observe(this, passwordInfoObserver)

            val generoInfoObserver = Observer<String>{ genero ->
                val infoText = "${getString(R.string.name)} : ${signupViewModel.registerInfo.value}\n" +
                        "${getString(R.string.email)} : ${signupViewModel.emailInfo.value}\n" +
                        "${getString(R.string.password)} : ${signupViewModel.passwordInfo.value}\n" +
                        "${getString(R.string.Genero)} : $genero"
                signUpBinding.infoTextView.text = infoText
            }
            signupViewModel.generoInfo.observe(this, generoInfoObserver)

            val fechaNacimientoObserver = Observer<String>{ fechaNacimiento ->
                val infoText = "${getString(R.string.name)} : ${signupViewModel.registerInfo.value}\n" +
                        "${getString(R.string.email)} : ${signupViewModel.emailInfo.value}\n" +
                        "${getString(R.string.password)} : ${signupViewModel.passwordInfo.value}\n" +
                        "${getString(R.string.Genero)} : ${signupViewModel.generoInfo.value}\n${getString(R.string.dateBirth)} : $fechaNacimiento"
                signUpBinding.infoTextView.text = infoText
            }
            signupViewModel.fechaNacimientoInfo.observe(this, fechaNacimientoObserver)

            val lugarNacimientoObserver = Observer<String>{ lugarNacimiento ->
                val infoText = "${getString(R.string.name)} : ${signupViewModel.registerInfo.value}\n" +
                        "${getString(R.string.email)} : ${signupViewModel.emailInfo.value}\n" +
                        "${getString(R.string.password)} : ${signupViewModel.passwordInfo.value}\n" +
                        "${getString(R.string.Genero)} : ${signupViewModel.generoInfo.value}\n${getString(R.string.dateBirth)} : ${signupViewModel.fechaNacimientoInfo.value}\n" +
                        "${getString(R.string.city_birth)} : $lugarNacimiento"
                signUpBinding.infoTextView.text = infoText
            }
            signupViewModel.lugarNacimientoInfo.observe(this, lugarNacimientoObserver)

            //Al hacer click en los CheckBox de los generos
            signupViewModel.setGenerosFavoritosInfo(signUpBinding.actionCheckBox,signUpBinding.dramaCheckBox,signUpBinding.comicCheckBox,
                signUpBinding.loveCheckBox,getString(R.string.action),getString(R.string.drama),getString(R.string.comic),getString(R.string.love) )

            //Comprobar password y mostrar info en text view si ambas contraseñas son iguales
            signupViewModel.comprobarPassword(signUpBinding.passwordEditText.text.toString(),
                signUpBinding.repPasswordEditText.text.toString(),getString(R.string.name),
                getString(R.string.email),getString(R.string.password),getString(R.string.Genero),
                getString(R.string.city_birth), getString(R.string.genre_favorites),getString(R.string.dateBirth),
                signUpBinding.infoTextView,signUpBinding.linearLayout, getString(R.string.Contras_Incorrecta),
                signUpBinding.repPasswordEditText,signUpBinding.repPasswordTextInputLayout
            )

        }

        // Configurar el layout con la vista inflada del binding
        setContentView(signUpBinding.root)
    }
}


