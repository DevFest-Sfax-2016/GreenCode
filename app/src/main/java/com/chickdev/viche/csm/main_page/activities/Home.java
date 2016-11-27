package com.chickdev.viche.csm.main_page.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.chickdev.viche.csm.R;
import com.chickdev.viche.csm.authentification.activities.SignInActivity;
import com.chickdev.viche.csm.user.activities.AjouterContact;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends AppCompatActivity {

    // Firebase instance variables
    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;

    private String mUsername;

    private GoogleApiClient mGoogleApiClient;

    private CardView cv_alergie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        cv_alergie = (CardView)findViewById(R.id.cv_parametre);


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();

                 if (user != null) {


                    Log.d("VICHE", "onAuthStateChanged:signed_in:" + user.getUid());

                    // = user.getDisplayName();
                } else {

                    Log.d("VICHE", "onAuthStateChanged:signed_out");

                    //Not signed in, launch the Sign In activity
                    startActivity(new Intent(Home.this, SignInActivity.class));

                    finish();

                    return;
                }

            }
        };

        cv_alergie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this, AjouterContact.class));
            }
        });

    }

    @Override
    public void onStart() {

        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();

        if (mAuthListener != null) {

            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.sign_out:

                mAuth.signOut();

                Auth.GoogleSignInApi.signOut(mGoogleApiClient);

               // mUsername = ANONYMOUS;

                startActivity(new Intent(this, SignInActivity.class));

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



}