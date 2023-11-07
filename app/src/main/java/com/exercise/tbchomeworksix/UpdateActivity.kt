package com.exercise.tbchomeworksix

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.exercise.tbchomeworksix.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateBinding
    private var currentUser: User? = null
    private lateinit var message: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        message = binding.tvMessage

        val currentName = intent.getStringExtra("user_name")
        val currentSurname = intent.getStringExtra("user_surname")
        val currentAge = intent.getStringExtra("user_age")
        val currentEmail = intent.getStringExtra("user_email")
        currentUser = User(currentName,currentSurname,currentAge,currentEmail)

        binding.updateButton.setOnClickListener {
            updateUser()
        }

    }

    private fun updateUser() {
        val updatedFirstName = binding.updateFirstName.text.toString()
        val updatedLastName = binding.updateLastName.text.toString()
        val updatedAge = binding.tvAge.text.toString()
        val curEmail = currentUser?.email

        if (isValid(updatedFirstName,updatedLastName,updatedAge)){
            val updateUser = User(updatedFirstName,updatedLastName,updatedAge,curEmail)

            for (user in UserManager.users){
                if (user == currentUser){
                    UserManager.users.remove(user)
                    UserManager.users.add(updateUser)
                    currentUser = updateUser
                    message.text = getString(R.string.user_updated)
                    message.setTextColor(ContextCompat.getColor(this,R.color.green))
                    finish()
                    return
                }
            }
            message.text = getString(R.string.user_not_found)
            message.setTextColor(ContextCompat.getColor(this,R.color.red))
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()

        }else{
            message.text = getString(R.string.not_valid_info)
            message.setTextColor(ContextCompat.getColor(this,R.color.red))
        }

    }

    private fun isValid(name: String, surname:String, age:String): Boolean{
        return name.isNotEmpty() && name.length < 12
                && surname.isNotEmpty() && surname.length < 20
                && age.isNotEmpty()
    }
}