package hoods.com.jetlogin.ui.signup

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import hoods.com.jetlogin.ui.components.HeaderText
import hoods.com.jetlogin.ui.components.LoginTextField
import hoods.com.jetlogin.ui.components.defaultPadding
import hoods.com.jetlogin.ui.components.itemSpacing

@Composable
fun SignUpScreen(
    onSignUpClick: () -> Unit,
    onLoginClick: () -> Unit,
    onPolicyClick: () -> Unit,
    onPrivacyClick: () -> Unit
) {
    val (firstName, onFirstNameChange) = rememberSaveable { mutableStateOf("") }
    val (lastName, onLastNameChange) = rememberSaveable { mutableStateOf("") }
    val (email, onEmailChange) = rememberSaveable { mutableStateOf("") }
    val (password, onPasswordChange) = rememberSaveable { mutableStateOf("") }
    val (confirmPassword, onConfirmPasswordChange) = rememberSaveable { mutableStateOf("") }
    val (agree, onAgreeChange) = rememberSaveable { mutableStateOf(false) }

    var isPasswordSame by remember { mutableStateOf(false) }
    val isFieldsNotEmpty = firstName.isNotEmpty() && lastName.isNotEmpty() &&
            email.isNotEmpty() && password.isNotEmpty() &&
            confirmPassword.isNotEmpty() && agree

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(defaultPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(isPasswordSame) {
            Text(
                "Password Is not Matching",
                color = MaterialTheme.colorScheme.error,
            )
        }
        HeaderText(
            text = "Sign Up",
            modifier = Modifier
                .padding(vertical = defaultPadding)
                .align(alignment = Alignment.Start)
        )

        LoginTextField(
            value = firstName,
            onValueChange = onFirstNameChange,
            labelText = "First Name",
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(itemSpacing))

        LoginTextField(
            value = lastName,
            onValueChange = onLastNameChange,
            labelText = "Last Name",
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(itemSpacing))

        LoginTextField(
            value = email,
            onValueChange = onEmailChange,
            labelText = "Email",
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(itemSpacing))

        LoginTextField(
            value = password,
            onValueChange = onPasswordChange,
            labelText = "Password",
            modifier = Modifier
                .fillMaxWidth(),
            keyboardType = KeyboardType.Password
        )
        Spacer(modifier = Modifier.height(itemSpacing))

        LoginTextField(
            value = confirmPassword,
            onValueChange = onConfirmPasswordChange,
            labelText = "Confirm Password",
            modifier = Modifier
                .fillMaxWidth(),
            keyboardType = KeyboardType.Password
        )
        Spacer(modifier = Modifier.height(itemSpacing))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val privateText = "Privacy"
            val policyText = "Policy"
            val annotatedString = buildAnnotatedString {
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
                    append("I Agree with")
                }
                append(" ")
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    pushStringAnnotation(tag = privateText, privateText)
                    append(privateText)
                }
                append(" And ")
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    pushStringAnnotation(tag = policyText, policyText)
                    append(policyText)
                }
            }

            Checkbox(checked = agree, onCheckedChange = onAgreeChange)
            ClickableText(text = annotatedString) { offset ->
                annotatedString.getStringAnnotations(offset, offset).forEach {
                    when (it.tag) {
                        privateText -> onPrivacyClick()
                        policyText -> onPolicyClick()
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(itemSpacing * 2))

        Button(
            onClick = {
                isPasswordSame = password != confirmPassword
                if (!isPasswordSame) {
                    onSignUpClick()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = isFieldsNotEmpty
        ) {
            Text(text = "Sign Up")
        }
        Spacer(modifier = Modifier.height(itemSpacing))

        val signTx = "Sign In"
        val signInAnnotation = buildAnnotatedString {
            withStyle(SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
                append("Already have an account?")
            }
            append(" ")
            withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                pushStringAnnotation(signTx, signTx)
                append(signTx)
            }
        }

        ClickableText(text = signInAnnotation) { offset ->
            signInAnnotation.getStringAnnotations(offset, offset).forEach {
                if (it.tag == signTx)
                    onLoginClick()
            }
        }
    }
}