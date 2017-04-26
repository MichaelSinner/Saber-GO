package com.example.michaelsinner.sabergo.Activities;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.michaelsinner.sabergo.R;


import com.facebook.*;


import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Login extends AppCompatActivity {


    private LoginButton btnFBLogin;
    private CallbackManager callbackManager;
    //private ProfilePictureView profilePictureView;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener fireAuthStateListener;
    private ProgressBar progressBarFireBase;
    private TextView tvTitleLogin, tvprueba;
    private Button btnLogIn, btnSignup;
    private EditText etEmail, etPassword;
    private TextView tvemailUser, tvnamedUser;
    String emailUser, nameUser;
    private Bitmap imageUser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBarFireBase = (ProgressBar) findViewById(R.id.prgBarFirebase);

        callbackManager = CallbackManager.Factory.create();
        btnFBLogin = (LoginButton) findViewById(R.id.button_fbLogin);
        getSupportActionBar().hide();

        LayoutInflater inflater = LayoutInflater.from(this);
        View customView = inflater.inflate(R.layout.actionbar_home, null);

        tvemailUser = (TextView) findViewById(R.id.tvUserEmail);
        tvnamedUser = (TextView) findViewById(R.id.tvUserName);

        tvTitleLogin = (TextView) findViewById(R.id.tvTitleLogin);
        Typeface font = Typeface.createFromAsset(getAssets(),"fonts/Sanlabello.ttf");
        tvTitleLogin.setTypeface(font);

        List<String> permissions = new ArrayList<>();
        permissions.add("public_profile");
        permissions.add("email");
        permissions.add("user_birthday");
        btnFBLogin.setReadPermissions(permissions);
        btnFBLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                final com.facebook.Profile profile = Profile.getCurrentProfile();
                handleFacebookAccesToken(loginResult.getAccessToken());
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {

                            private JSONObject object;
                            private GraphResponse response;

                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                this.object = object;
                                this.response = response;
                                Log.v("Main", response.toString());
                                setProfileToView(object);
                                try {
                                    emailUser = object.getString("email");
                                    nameUser = object.getString("name");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(Login.this, "Bienvenido "+profile.getFirstName(), Toast.LENGTH_SHORT);

                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();


                handleFacebookAccesToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(Login.this, "Login Cancel", Toast.LENGTH_LONG);
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(Login.this, "Login Cancel", Toast.LENGTH_LONG);
            }
        });
        btnFBLogin.setTypeface(font);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        tvprueba = (TextView) findViewById(R.id.textViewPrueba);


        btnLogIn = (Button) findViewById(R.id.btnIniciarSesion);
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(etEmail.equals("")||etPassword.equals("")){
                   Toast.makeText(getApplicationContext(),"Escribe tu correo y contraseña",Toast.LENGTH_SHORT);
                }else {
                    toLogin(etEmail.getText().toString(),etPassword.getText().toString());
                    tvprueba.setText(etEmail.getText());
                }

            }
        });
        btnLogIn.setTypeface(font);

        firebaseAuth = FirebaseAuth.getInstance();
        fireAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){

                    toMenuPrincipal(nameUser,emailUser,imageUser);
                }
            }
        };
        btnSignup = (Button) findViewById(R.id.btnToRegister);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toRegister();   
            }
        });
        btnSignup.setTypeface(font);

    }

    private void setProfileToView(JSONObject object)
    {
        try {

            tvemailUser.setText(object.getString("email"));
            emailUser = object.getString("email");
            tvnamedUser.setText(object.getString("name"));
            imageUser = getFacebookProfilePicture(object.getString("id"));
            //profilePictureView.setPresetSize(ProfilePictureView.NORMAL);
            //profilePictureView.setProfileId(object.getString("id"));

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void handleFacebookAccesToken(AccessToken accessToken) {
        progressBarFireBase.setVisibility(View.VISIBLE);
        btnFBLogin.setVisibility(View.GONE);

        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Error Firebase",Toast.LENGTH_LONG).show();
                }
                progressBarFireBase.setVisibility(View.GONE);
                btnFBLogin.setVisibility(View.VISIBLE);
            }
        });
    }

    private void toRegister()
    {
        Intent toRegister = new Intent(this , SignUp.class);
        toRegister.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(toRegister);
    }


    public void toMenuPrincipal(String nombre, String email, Bitmap image)
    {
        Intent toMenuPrincipal = new Intent(Login.this , MainMenu.class);
        toMenuPrincipal.putExtra("NOMBRE",nombre);
        toMenuPrincipal.putExtra("EMAIL",email);
        toMenuPrincipal.putExtra("IMAGE",image);
        toMenuPrincipal.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(toMenuPrincipal);
    }
    public static Bitmap getFacebookProfilePicture(String userID) throws SocketException, SocketTimeoutException, MalformedURLException, IOException, Exception
    {
        String imageURL;
        Bitmap bitmap = null;
        imageURL = "http://graph.facebook.com/"+userID+"/picture?type=large";
        InputStream in = (InputStream) new URL(imageURL).getContent();
        bitmap = BitmapFactory.decodeStream(in);

        return bitmap;
    }

    private void toLogin(String emailEditText, String passwordEditText)
    {

        firebaseAuth.signInWithEmailAndPassword(emailEditText,passwordEditText).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            public static final String TAG = "1" ;

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Log.d(TAG, "FAILED");
                    Toast.makeText(getApplicationContext(), "Error email auntenticacion",Toast.LENGTH_LONG);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(fireAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(fireAuthStateListener);
    }
}
