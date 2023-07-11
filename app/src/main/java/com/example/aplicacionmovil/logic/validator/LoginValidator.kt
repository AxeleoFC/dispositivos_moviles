package com.flores.aplicacionmoviles.logic.validator

import com.flores.aplicacionmoviles.data.entities.LoginUser

class LoginValidator {

    fun checkLogin(name:String, pass:String):Boolean {
        val admin = LoginUser()
        return (admin.name == name && admin.pass == pass)
    }
}