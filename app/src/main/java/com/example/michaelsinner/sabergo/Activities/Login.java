package com.example.michaelsinner.sabergo.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.michaelsinner.sabergo.Data.User;
import com.example.michaelsinner.sabergo.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class Login extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener
{
    //google objects
    private GoogleApiClient googleApiClient;
    private SignInButton signInButton;
    public static final int  SIGN_IN_CODE_GMAIL = 777;

    private LoginButton btnFBLogin;
    private CallbackManager callbackManager;
    //private ProfilePictureView profilePictureView;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener fireAuthStateListener;

    private ProgressBar progressBarFireBase;
    private TextView tvTitleLogin, tvprueba;
    private Button btnLogIn, btnSignup;
    private EditText etEmail, etPassword;
    String emailUser, nameUser;
    private Bitmap imageUser;

    //-- User Object ---------------------------------------------------------------------------- //
    private DatabaseReference mDatabase;
    private User userSaberGO;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        LayoutInflater inflater = LayoutInflater.from(this);
        View customView = inflater.inflate(R.layout.actionbar_home, null);


        progressBarFireBase = (ProgressBar) findViewById(R.id.prgBarFirebase);
        callbackManager = CallbackManager.Factory.create();

        final GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                requestIdToken(getString(R.string.default_web_client_id)).
                requestEmail().build();

        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).
                addApi(Auth.GOOGLE_SIGN_IN_API,googleSignInOptions).build();

        signInButton = (SignInButton) findViewById(R.id.btnSignInGoogle);
        btnFBLogin = (LoginButton) findViewById(R.id.button_fbLogin);


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
                                    userSaberGO = new User(object.getString("id"),object.getString("email"),object.getString("name"));
                                    emailUser = object.getString("email");
                                    nameUser = object.getString("name");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            //    Toast.makeText(Login.this, "Bienvenido "+profile.getFirstName(), Toast.LENGTH_SHORT);

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


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent,SIGN_IN_CODE_GMAIL);
            }
        });
        signInButton.setColorScheme(SignInButton.COLOR_DARK);


        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        tvprueba = (TextView) findViewById(R.id.textViewPrueba);


        btnLogIn = (Button) findViewById(R.id.btnIniciarSesion);
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String strUserEmail = etEmail.getText().toString();
                String strUserPass = etPassword.getText().toString();

                if(TextUtils.isEmpty(strUserEmail)||TextUtils.isEmpty(strUserPass)){
                    Toast.makeText(getApplicationContext(),"Escribe tu correo y contraseña para iniciar sesión",Toast.LENGTH_SHORT).show();
                    etEmail.setError("Escribe tu correo aquí");
                    etPassword.setError("Escribe tu contraseña");
                    return;

                }else if(isOnline(getApplicationContext())){
                    toLogin(etEmail.getText().toString(),etPassword.getText().toString());
                    tvprueba.setText(etEmail.getText());
                }else{
                    toNoInternet();
                }


            }
        });
        btnLogIn.setTypeface(font);

     //--- Listener State Firebase ------------------------------------------------------------//
        firebaseAuth = FirebaseAuth.getInstance();
        fireAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                Profile userFB = Profile.getCurrentProfile();
                //String provider = user.getProviderId();

                if(user != null && userFB == null){

                    userSaberGO = new User(user.getUid(),user.getEmail(), user.getDisplayName());

                    Log.e("TAG","User : "+userSaberGO.getUserID());
                    Log.e("TAG","email : "+userSaberGO.getUserEmail());
                    Log.e("TAG","whololo : "+user.toString());
                    Log.e("TAG","name : "+userSaberGO.getUserName());
                    toMenuPrincipal(userSaberGO);
                } else if(userFB!= null){

                    userSaberGO.setUserID(user.getUid());

                    Log.e("TAG","User : "+userSaberGO.getUserID());
                    Log.e("TAG","email : "+userSaberGO.getUserEmail());
                    //  Log.e("TAG","provider : "+provider);
                    Log.e("TAG","whololo : "+user.toString());
                    Log.e("TAG","name : "+userSaberGO.getUserName());
                    toMenuPrincipal(userSaberGO);
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
            emailUser = object.getString("email");
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
    public static boolean isOnline(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }

    private void handleFacebookAccesToken(final AccessToken accessToken) {
        progressBarFireBase.setVisibility(View.VISIBLE);
        btnFBLogin.setVisibility(View.GONE);
        signInButton.setVisibility(View.GONE);
        btnSignup.setVisibility(View.GONE);
        btnLogIn.setVisibility(View.GONE);
        etEmail.setVisibility(View.GONE);
        etPassword.setVisibility(View.GONE);

        final AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Error Firebase",Toast.LENGTH_LONG).show();
                }



                progressBarFireBase.setVisibility(View.GONE);
                btnFBLogin.setVisibility(View.VISIBLE);
                btnSignup.setVisibility(View.VISIBLE);
                btnLogIn.setVisibility(View.VISIBLE);
                etEmail.setVisibility(View.VISIBLE);
                signInButton.setVisibility(View.VISIBLE);
                etPassword.setVisibility(View.VISIBLE);
                btnFBLogin.setVisibility(View.VISIBLE);
            }
        });
    }

    private void handleGoogleAccesToken(GoogleSignInResult result)
    {
        if(result.isSuccess()){
            firebasAuthwithGoogle(result.getSignInAccount());
        }else{
            Toast.makeText(getApplicationContext(), "Error gmail autenticacion",Toast.LENGTH_LONG).show();
        }
    }

    private void firebasAuthwithGoogle(GoogleSignInAccount signInAccount)
    {
        progressBarFireBase.setVisibility(View.VISIBLE);
        signInButton.setVisibility(View.GONE);
        btnFBLogin.setVisibility(View.GONE);
        btnSignup.setVisibility(View.GONE);
        btnLogIn.setVisibility(View.GONE);
        etEmail.setVisibility(View.GONE);
        etPassword.setVisibility(View.GONE);

        AuthCredential credential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"No se realizo la autenticacion Firebase",Toast.LENGTH_LONG).show();
                }
                progressBarFireBase.setVisibility(View.GONE);
                btnFBLogin.setVisibility(View.VISIBLE);
                signInButton.setVisibility(View.VISIBLE);
                btnSignup.setVisibility(View.VISIBLE);
                btnLogIn.setVisibility(View.VISIBLE);
                etEmail.setVisibility(View.VISIBLE);
                etPassword.setVisibility(View.VISIBLE);
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

    private void toNoInternet()
    {
        Intent toNoInternet = new Intent(this , NoInternet.class);
        toNoInternet.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(toNoInternet);
    }


    public void toMenuPrincipal(final User userSaberGO)
    {
        final Intent toMenuPrincipal = new Intent(Login.this , MainMenu.class);

        toMenuPrincipal.putExtra("ID",userSaberGO.getUserID());
        toMenuPrincipal.putExtra("NOMBRE",userSaberGO.getUserName());
        toMenuPrincipal.putExtra("EMAIL",userSaberGO.getUserEmail());
        toMenuPrincipal.putExtra("IMAGE",userSaberGO.getUserImageProfile());
        Toast.makeText(this, "Bienvenido "+userSaberGO.getUserName(),Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getApplicationContext(), "Error email auntenticacion",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);

        if(requestCode == SIGN_IN_CODE_GMAIL){
            GoogleSignInResult result  = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleGoogleAccesToken(result);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(fireAuthStateListener);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        if(firebaseAuth != null) firebaseAuth.removeAuthStateListener(fireAuthStateListener);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
