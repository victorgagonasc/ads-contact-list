package com.unimetrocamp.ads.listadecontatos;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.HashMap;
import java.util.Map;

public class ContactActivity extends AppCompatActivity {
    private static final String TAG = "ContactActivity";

    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "phone";

    private EditText mName, mPhone, mEmail;

    private FirebaseFirestore fb = FirebaseFirestore.getInstance();

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            checkFieldsForEmptyValues();
        }
    };

    private void checkFieldsForEmptyValues() {
        Button button = (Button) findViewById(R.id.actionButton);

        String name = mName.getText().toString();
        String email = mEmail.getText().toString();
        String phone = mPhone.getText().toString();

        if (name.equals("") || email.equals("") || phone.equals("")) {
            button.setEnabled(false);
        } else {
            button.setEnabled(true);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        Intent intent = getIntent();
        final String operation = intent.getStringExtra("operation");

        Button button = (Button) findViewById(R.id.actionButton);

        if (operation == "update") {
            setTitle("Alterar Contato");
            button.setText("Alterar");
        }

        mName = findViewById(R.id.name);
        mEmail = findViewById(R.id.email);
        mPhone = findViewById(R.id.phone);

        checkFieldsForEmptyValues();

        mName.addTextChangedListener(mTextWatcher);
        mEmail.addTextChangedListener(mTextWatcher);
        mPhone.addTextChangedListener(mTextWatcher);
        mPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        this.fb.setFirestoreSettings(settings);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SaveContact();
            }
        });
    }

    private void SaveContact() {
        Map<String, Object> contact = new HashMap<>();
        contact.put(KEY_NAME, mName.getText().toString());
        contact.put(KEY_EMAIL, mEmail.getText().toString());
        contact.put(KEY_PHONE, mPhone.getText().toString());


        CollectionReference contactRef = fb.collection("contacts");

        contactRef.add(contact)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(ContactActivity.this, "Contato adicionado com sucesso",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ContactActivity.this, "Erro: " + e.toString(),
                        Toast.LENGTH_SHORT).show();
                Log.d(TAG, e.toString());
            }
        });
    }
}