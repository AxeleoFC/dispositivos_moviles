package com.example.aplicacioncomida.logic.validator

import com.example.aplicacioncomida.data.entities.LoginUser

class LoginValidator {
    fun checkLogin(name: String,password:String): Boolean{
        val admin=LoginUser()
        return admin.name==name && admin.pass==password
    }
}