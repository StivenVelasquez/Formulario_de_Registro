package com.stivenvelasquez.formulario.ui.signup

import android.content.Context
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.stivenvelasquez.formulario.R

class SignUpViewModel : ViewModel() {

    val currentDate: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val selectedDate: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val ciudades: MutableLiveData<List<String>> by lazy {
        MutableLiveData<List<String>>()
    }

    val ciudadSeleccionada: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val name: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val registerInfo: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

   val  emailInfo: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val  passwordInfo: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val  generoInfo: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    private val  generosFavoritosInfo: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val  fechaNacimientoInfo: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val  lugarNacimientoInfo: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val  camposVacios: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    //Metodos
    fun currentDate(fecha: String){
        currentDate.value=fecha
    }

    fun updateSelectedDate(date: String) {
        selectedDate.value = date
    }

    fun actualizarCiudadSeleccionada(ciudad: String) {
        ciudadSeleccionada.value = ciudad
    }

    fun inicializarCiudades(listaCiudades: List<String>) {
        ciudades.value = listaCiudades
        ciudadSeleccionada.value = listaCiudades.first()
    }

    fun registerInfo(register: String) {
        registerInfo.value = register
    }

   fun emailInfo(email: String) {
        emailInfo.value = email
    }
    fun passwordInfo(password: String) {
        passwordInfo.value = password
    }
    fun fechaNacimientoInfo(fechaNacimiento: String) {
        fechaNacimientoInfo.value = fechaNacimiento
    }

    fun lugarNacimientoInfo(lugarNacimiento: String) {
        lugarNacimientoInfo.value = lugarNacimiento
    }
    fun setGeneroInfo(maleRadioButton: RadioButton, maleText: String, femaleText: String) {
        if (maleRadioButton.isChecked)
            generoInfo.value = maleText
        else
            generoInfo.value = femaleText
    }
    fun setGenerosFavoritosInfo(actionCheckBox:CheckBox, dramaCheckBox: CheckBox, comicCheckBox: CheckBox, loveCheckBox: CheckBox, action: String, drama: String, comic: String, love: String ) {
        if(actionCheckBox.isChecked) generosFavoritosInfo.value = action
        if(dramaCheckBox.isChecked) generosFavoritosInfo.value += " "+ drama
        if(comicCheckBox.isChecked) generosFavoritosInfo.value += " "+ comic
        if(loveCheckBox.isChecked) generosFavoritosInfo.value +=" "+ love
    }
    fun comprobarPassword(password: String, rep_Password: String, name:String,
                          email: String, Password: String, Genero: String,
                          cityBirth: String, genre_favorites:String, dateBirth: String,
                          infoTextView: TextView, linear_layout:LinearLayout,
                          Contras_Incorrecta: String, repPasswordEditText: EditText,
                          repPasswordTextInputLayout: TextInputLayout ){

        if(password==rep_Password){
                val infoText = "$name : ${registerInfo.value}\n" +
                        "$email : ${emailInfo.value}\n" +
                        "$Password : ${passwordInfo.value}\n" +
                        "$Genero : ${generoInfo.value}\n$dateBirth : ${fechaNacimientoInfo.value}\n" +
                        "$cityBirth : ${lugarNacimientoInfo.value}\n" +
                        "$genre_favorites : ${generosFavoritosInfo.value}"
                infoTextView.text = infoText
        }

        else{
            infoTextView.text = " "
            Snackbar.make(linear_layout,Contras_Incorrecta, Snackbar.LENGTH_LONG).setAction("Aceptar"){
                repPasswordEditText.setText("")
                repPasswordTextInputLayout.isErrorEnabled=false
            }
                .show()
        }
    }

    fun ciudadSeleccionada(ciudadSeleccionada: String, seleccione:String, SeleccionTextView:TextView,
                           No_has_seleccionado: String, seleccionaste: String ){
        if (ciudadSeleccionada==seleccione)
            SeleccionTextView.text = No_has_seleccionado
         else
            SeleccionTextView.text = seleccionaste.plus(" ").plus(ciudadSeleccionada)
    }

    fun validateInputsAndSignUp(name: String, email: String, password: String,repPassword: String,
                                date: String, seleccion: String, context: Context, infoTextView: TextView) {
        camposVacios.value=false
        if (name.isBlank() || email.isBlank() || password.isBlank() || repPassword.isBlank()
            || date.isBlank() || seleccion == context.getString(R.string.No_has_seleccionado)) {
            Toast.makeText(context, context.getString(R.string.completar_campos), Toast.LENGTH_LONG).show()
            infoTextView.text = " "
            camposVacios.value=true
            }
    }

}