package com.exercise.tbchomeworksix

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.exercise.tbchomeworksix.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var addUserCount = 0
    private var removedUserCount = 0
    private lateinit var message: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        message = binding.tvMessage

        binding.buttonAddUser.setOnClickListener {
            addUser()
        }

        binding.buttonRemoveUser.setOnClickListener {
            removeUser()
        }

        binding.buttonUpdateUser.setOnClickListener {
            checkUserToUpdate()
        }
    }

    private fun addUser() {
        val userName = binding.tvFirstName.text.toString()
        val userSurname = binding.tvLastName.text.toString()
        val userAge = binding.tvAge.text.toString()
        val userEmail = binding.tvEmail.text.toString()
        val newUser = User(userName,userSurname,userAge,userEmail)
        if(isValid(userName,userSurname,userAge,userEmail)){
            if (UserManager.users.contains(newUser)){
                message.text = getString(R.string.email_already_exists)
                message.setTextColor(ContextCompat.getColor(this,R.color.red))
            }else{
                UserManager.users.add(newUser)
                addUserCount++
                binding.activeUsers.text = addUserCount.toString()
                message.text = getString(R.string.user_added)
                message.setTextColor(ContextCompat.getColor(this,R.color.green))
                clearTextFields()
            }
        }else{
            message.text = getString(R.string.not_valid_info)
            message.setTextColor(ContextCompat.getColor(this,R.color.red))
        }
    }

    private fun removeUser() {
        val userName = binding.tvFirstName.text.toString()
        val userSurname = binding.tvLastName.text.toString()
        val userAge = binding.tvAge.text.toString()
        val userEmail = binding.tvEmail.text.toString()
        val currentUser = User(userName,userSurname,userAge,userEmail)
        if (isValid(userName,userSurname,userAge,userEmail)){
            if (UserManager.users.contains(currentUser)){
                UserManager.users.remove(currentUser)
                addUserCount--
                removedUserCount++
                binding.activeUsers.text = addUserCount.toString()
                binding.removedUsers.text = removedUserCount.toString()

                message.text = getString(R.string.user_delete)
                message.setTextColor(ContextCompat.getColor(this,R.color.green))
                clearTextFields()
            }else{
                message.text = getString(R.string.user_not_exists)
                message.setTextColor(ContextCompat.getColor(this,R.color.red))
            }
        }else{
            message.text = getString(R.string.not_valid_info)
            message.setTextColor(ContextCompat.getColor(this,R.color.red))
        }
    }

    private fun checkUserToUpdate(){
        val userName = binding.tvFirstName.text.toString()
        val userSurname = binding.tvLastName.text.toString()
        val userAge = binding.tvAge.text.toString()
        val userEmail = binding.tvEmail.text.toString()
        val currentUser = User(userName,userSurname,userAge,userEmail)
        if (isValid(userName,userSurname,userAge,userEmail)){
            if (UserManager.users.contains(currentUser)){
                val intent = Intent(this,UpdateActivity::class.java)
                intent.putExtra("user_name", userName)
                intent.putExtra("user_surname", userSurname)
                intent.putExtra("user_age", userAge)
                intent.putExtra("user_email", userEmail)
                startActivity(intent)
                clearTextFields()
            }else{
                message.text = getString(R.string.user_not_exists)
                message.setTextColor(ContextCompat.getColor(this,R.color.red))
            }
        }else{
            message.text = getString(R.string.not_valid_info)
            message.setTextColor(ContextCompat.getColor(this,R.color.red))
        }
    }


    private fun isValid(name: String, surname:String, age:String, email:String): Boolean{
        return name.isNotEmpty() && name.length < 12
                && surname.isNotEmpty() && surname.length < 20
                && age.isNotEmpty() && valid(email)
    }

    private fun valid(email: String): Boolean {
        val emailPattern = Regex("^[A-Za-z0-9+_.-]+@(.+)\$")
        return emailPattern.matches(email)
    }

    private fun clearTextFields(){
        binding.tvFirstName.text?.clear()
        binding.tvLastName.text?.clear()
        binding.tvAge.text?.clear()
        binding.tvEmail.text?.clear()
    }


}