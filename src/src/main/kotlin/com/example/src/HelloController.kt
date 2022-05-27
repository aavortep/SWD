package com.example.src

import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField

class WelcomeController {
    @FXML
    private lateinit var welcomeText: Label

    @FXML
    private fun onHelloButtonClick() {
        welcomeText.text = "Welcome to JavaFX Application!"
    }
    @FXML
    private fun onSignInButtonClick() {
        SignIn().sign_in()
    }
    @FXML
    private fun onSignUpButtonClick() {
        SignIn().sign_up()
    }
}

class SignInController
{
    @FXML
    private lateinit var passwordField: PasswordField
    @FXML
    private lateinit var mailField: TextField
    @FXML
    private lateinit var error: Label

    @FXML
    private fun onSubmitButtonClick() {
        val mail: String = mailField.text
        val password: String = passwordField.text
        if (mail == "" || password == "") {
            error.text = "Please, fill all the gaps"
            return
        }
        val acc = Actions().findAcc(mail, password)
        if (acc.id == -1) {
            error.text = "Invalid email or password. Try again"
            return
        }
        //main page
    }
}

class SignUpController
{
    @FXML
    private lateinit var passwordField: PasswordField
    @FXML
    private lateinit var mailField: TextField
    @FXML
    private lateinit var fioField: TextField
    @FXML
    private lateinit var phoneField: TextField
    @FXML
    private lateinit var error: Label

    @FXML
    private fun onSubmitButtonClick() {
        val acc = Account()
        acc.fio = fioField.text
        acc.phone = phoneField.text
        acc.mail = mailField.text
        acc.password = passwordField.text
        if (acc.mail == "" || acc.password == "" || acc.fio == "" || acc.phone == "") {
            error.text = "Please, fill all the gaps"
            return
        }
        Actions().saveAcc(acc)
        //main page
    }
}

class MainPageController
{

}