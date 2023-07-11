package com.example.virginmoneychallenge

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.example.virginmoneychallenge.databinding.ActivityLoginBinding
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity: AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private val RC_SIGN_IN: Int = 123
    private lateinit var signInRequest: BeginSignInRequest
    private lateinit var auth: FirebaseAuth
    private val TAG: String? = "MAIN_ACTIVITY"
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var callbackManager: CallbackManager
    private lateinit var analytics: FirebaseAnalytics



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()
        val loginButton = binding.ibLoginFacebook

        analytics = Firebase.analytics


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        auth = Firebase.auth
        callbackManager = CallbackManager.Factory.create()

        val emailInput = findViewById<EditText>(R.id.et_email)
        val passwordInput = findViewById<EditText>(R.id.et_password)

        findViewById<Button>(R.id.btn_login).setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) {task ->
                    if(task.isSuccessful){
                        val user = auth.currentUser
                        Toast.makeText(
                            baseContext,
                            "Login user" + user?.email,
                            Toast.LENGTH_LONG,
                        ).show()
                        navigateToNextPage()
                    } else{
                        Toast.makeText(
                            baseContext,
                            "Authentication failed",
                            Toast.LENGTH_LONG,
                        ).show()
                    }

                }
        }


        findViewById<ImageButton>(R.id.ib_login_google).setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }


        //Facebook login callback
         loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                //Handle successful login
                val accessToken = loginResult.accessToken
                firebaseAuthWithFacebook(accessToken.token)
            }

            override fun onCancel() {
                Toast.makeText(this@LoginActivity,"You have cancelled Facebook login",Toast.LENGTH_SHORT)
            }

            override fun onError(error: FacebookException) {
                Toast.makeText(this@LoginActivity,"Error while trying to login with Facebook",Toast.LENGTH_SHORT)
            }

        })

        //login facebook btn_login
        binding.ibLoginFacebook.setOnClickListener() {

            //analytics
            analytics.logEvent(
                FirebaseAnalytics.Event.SELECT_CONTENT,
                bundleOf(
                    FirebaseAnalytics.Param.ITEM_ID to "789",
                    FirebaseAnalytics.Param.ITEM_NAME to "CLICK",
                    FirebaseAnalytics.Param.CONTENT_TYPE to "BUTTON_PRESS"

                )
            )

        }


    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)


        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }


    // [START auth_with_google]
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    //  Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    //Toast.makeText(this, "Success: ${user?.email}", Toast.LENGTH_SHORT).show()
                    //updateUI(user)
                    navigateToNextPage()
                } else {
                    // If sign in fails, display a message to the user.
                    // Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()

                    //updateUI(null)
                }
            }
    }

    private fun firebaseAuthWithFacebook(accessToken: String) {
        val credential = FacebookAuthProvider.getCredential(accessToken)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    Toast.makeText(this, "Success: ${user?.email}", Toast.LENGTH_LONG).show()
                    navigateToNextPage()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(this, "Failure: ${task.exception.toString()}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun navigateToNextPage() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}