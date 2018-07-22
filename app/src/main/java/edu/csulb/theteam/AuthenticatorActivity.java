package edu.csulb.theteam;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobile.auth.core.DefaultSignInResultHandler;
import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.auth.core.IdentityProvider;
import com.amazonaws.mobile.auth.ui.AuthUIConfiguration;
import com.amazonaws.mobile.auth.ui.SignInActivity;
/*import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;*/

public class AuthenticatorActivity extends AppCompatActivity {

    /*LoginButton login;
    CallbackManager cbManager;
    LoginButton register;
    AccessToken accessToken;
    String facebookID;*/
    TextView testText;
    EditText etUsername, etPassword;
    TextView tvRegisterLink;
    SharedPreferences sh;
    SharedPreferences.Editor editor;
    //ProfileMapperClass retrievedProfile;

    ProgressDialog progress;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticator);
        sh = getApplicationContext().getSharedPreferences("Pref",0);
        sh.getString("token",null);


        final IdentityManager identityManager = AWSProvider.getInstance().getIdentityManager();
        // Set up the callbacks to handle the authentication response
        identityManager.login(this, new DefaultSignInResultHandler() {
            @Override
            public void onSuccess(Activity activity, IdentityProvider identityProvider) {
                Toast.makeText(AuthenticatorActivity.this,
                        String.format("Logged in as %s", identityManager.getCachedUserID()),
                        Toast.LENGTH_LONG).show();



                /*editor.putString("token",identityProvider.getToken());
                editor.commit();*/
                // Go to the main activity
                final Intent intent = new Intent(activity, MainActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                activity.startActivity(intent);
                activity.finish();
            }

            @Override
            public boolean onCancel(Activity activity) {
                return false;
            }
        });

        // Start the authentication UI
        int lightPurple = 0xFFDFC5FF;
        AuthUIConfiguration config = new AuthUIConfiguration.Builder()
                .userPools(true)
                .backgroundColor(lightPurple)
                .logoResId(R.drawable.leaguelook_icon)
                .build();
        SignInActivity.startSignInActivity(this, config);
        AuthenticatorActivity.this.finish();
    }
}
