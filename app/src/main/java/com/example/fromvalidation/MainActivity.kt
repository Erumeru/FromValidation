package com.example.fromvalidation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AlertDialog
import com.example.fromvalidation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        emailFocusListener()
        passwordFocusListener()
        phoneFocusListener()
        binding.submitButton.setOnClickListener{submitForm()}
    }

    private fun submitForm(){
        binding.emailContainer.helperText=validEmail()
        binding.passwordContainer.helperText=validPassword()
        binding.phoneContainer.helperText=validPhone()

        val validEmail=binding.emailContainer.helperText==null
        val validPass=binding.passwordContainer.helperText==null
        val validPhone=binding.phoneContainer.helperText==null

        if(validEmail && validPass && validPhone){
            resetForm()
        }else{
            invalidForm()
        }

    }
    private fun resetForm(){
        var message="Email:"+binding.emailEditText.text
        message+="\nPassword:"+binding.passwordEditText.text
        message+="\nPhone number:"+binding.phoneEditText.text
        AlertDialog.Builder(this)
            .setTitle("Form submitted")
            .setMessage(message)
            .setPositiveButton("Okay"){_,_->
    binding.emailEditText.text=null
                binding.passwordEditText.text=null
                binding.phoneEditText.text=null

                binding.emailContainer.helperText=getString(R.string.email)
                binding.passwordContainer.helperText=getString(R.string.password)
                binding.phoneContainer.helperText=getString(R.string.phone_number)
            }
            .show()
    }

    private fun invalidForm(){
        var message=""
        if(binding.emailContainer.helperText!=null) message+="\n\nEmail:"+binding.emailContainer.helperText
        if(binding.passwordContainer.helperText!=null) message+="\n\nPassword:"+binding.passwordContainer.helperText
        if(binding.phoneContainer.helperText!=null) message+="\n\nPhone number:"+binding.phoneContainer.helperText

        AlertDialog.Builder(this)
            .setTitle("Invalid form")
            .setMessage(message)
            .setPositiveButton("Okay"){_,_->

            }.show()
    }

    private fun emailFocusListener(){
        binding.emailEditText.setOnFocusChangeListener{_,focused->
            if(!focused){
                binding.emailContainer.helperText=validEmail()
            }
        }
    }

    private fun validEmail(): String?{
        val emailText=binding.emailEditText.text.toString()
        if(!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()){
            return "Invalid Email Address"
        }
        return null
    }

    private fun passwordFocusListener(){
        binding.passwordEditText.setOnFocusChangeListener{_,focused->
            if(!focused){
                binding.passwordContainer.helperText=validPassword()
            }
        }
    }

    private fun validPassword(): String?{
        val passText=binding.passwordEditText.text.toString()
        if(passText.length<8){
            return "Minimun 8 characters"
        }
        if(!passText.matches(".*[A-Z].*".toRegex())){
            return "Must contain 1 upper-case character"
        }
        if(!passText.matches(".*[@#\$%^&+=].*".toRegex())){
            return "Must contain 1 special character (@#$%^&+=)"
        }
        return null
    }

    private fun phoneFocusListener(){
        binding.phoneEditText.setOnFocusChangeListener{_,focused->
            if(!focused){
                binding.phoneContainer.helperText=validPhone()
            }
        }
    }

    private fun validPhone(): String?{
        val phoneText=binding.phoneEditText.text.toString()
        if(phoneText.length!=10){
            return "Phone must be 10 digits"
        }
        if(!phoneText.matches(".*[0-9].*".toRegex())){
            return "Must be all digits"
        }
        return null
    }
}