package com.example.quizpolitik.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.quizpolitik.R;
import com.example.quizpolitik.common.Common;
import com.example.quizpolitik.model.Ranking;
import com.example.quizpolitik.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rey.material.widget.CheckBox;

import dmax.dialog.SpotsDialog;
import io.paperdb.Paper;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.text.TextUtils.isEmpty;

public class SignIn extends AppCompatActivity {

    Button btnLogin;
    MaterialEditText edtUsername, edtPassword;
    CheckBox ckbRemember;
    RelativeLayout rootLayout;
    DatabaseReference table_user, table_ranking;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/SegoeUI.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        setContentView(R.layout.activity_sign_in);

        //Views
        btnLogin = findViewById(R.id.btnLogin);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        ckbRemember = findViewById(R.id.ckbRemember);
        rootLayout = findViewById(R.id.rootLayout);
        Paper.init(this);

        table_user = FirebaseDatabase.getInstance().getReference("User");
        table_ranking = FirebaseDatabase.getInstance().getReference("Ranking");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ckbRemember.isChecked()){
                    Paper.book().write(Common.USER_KEY, edtUsername.getText().toString());
                    Paper.book().write(Common.PWD_KEY, edtPassword.getText().toString());
                }

                table_user.addValueEventListener(new ValueEventListener() {
                 @Override
                 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                     //disable button login
                     btnLogin.setEnabled(false);

                     //ceck validation
                     if(isEmpty(edtUsername.getText().toString())|| isEmpty(edtPassword.getText().toString())){
                        Snackbar.make(rootLayout,"Field cann't be blank",Snackbar.LENGTH_SHORT).show();
                         btnLogin.setEnabled(true);
                     }else{
                         //show progressbar
                         btnLogin.setVisibility(View.INVISIBLE);

                         //login
                         if(dataSnapshot.child(edtUsername.getText().toString()).exists()){
                             User user = dataSnapshot.child(edtUsername.getText().toString()).getValue(User.class);
                             assert user != null;
                             if(user.getPassword().equals(edtPassword.getText().toString())){
                                 Common.username = edtUsername.getText().toString();
                                 startActivity(new Intent(SignIn.this, BaseActivity.class));
                                 finish();
                             }else{
                                 btnLogin.setVisibility(View.VISIBLE);
                                 btnLogin.setEnabled(true);
                                 Snackbar.make(rootLayout,"Incorrect Password",Snackbar.LENGTH_SHORT).show();
                             }
                         }else{
                             btnLogin.setVisibility(View.VISIBLE);
                             btnLogin.setEnabled(true);
                             Snackbar.make(rootLayout,"User doesn't exist",Snackbar.LENGTH_SHORT).show();
                         }
                     }
                 }

                 @Override
                 public void onCancelled(@NonNull DatabaseError databaseError) {

                 }
             });
            }
        });
    }

    public void showRegisterDialog(View view) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Register");

        LayoutInflater inflater = LayoutInflater.from(this);
        View register_layout = inflater.inflate(R.layout.layout_register,null);

        final MaterialEditText edtUsername = register_layout.findViewById(R.id.edtUsername);
        final MaterialEditText edtPassword = register_layout.findViewById(R.id.edtPassword);

        dialog.setView(register_layout);

        //set Button
        dialog.setPositiveButton("Register", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(isEmpty(edtUsername.getText().toString() )|| isEmpty(edtPassword.getText().toString())){
                    Snackbar.make(rootLayout,"Field cann't be blank",Snackbar.LENGTH_SHORT).show();
                }else{
                    table_user.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if(dataSnapshot.child(edtUsername.getText().toString()).exists()){
                                Snackbar.make(rootLayout,"Username already exist!!",Snackbar.LENGTH_SHORT).show();
                            }
                            else{
                                User user = new User(edtPassword.getText().toString());
                                Ranking ranking = new Ranking(0,edtUsername.getText().toString());
                                table_user.child(edtUsername.getText().toString()).setValue(user);
                                table_ranking.child(edtUsername.getText().toString()).setValue(ranking);
                                Toast.makeText(SignIn.this,"Registration successful",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                dialog.dismiss();
            }
        });

        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
