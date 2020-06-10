package com.example.onlinebankapp;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView;

import java.util.Calendar;

import android.app.AlertDialog;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String nic, account, fname, lname, email, addr, phn, date;
    private EditText s_nic, s_acc, s_fname, s_lname, s_email, s_addr, s_phn, s_date;
    Button btn2, btn1;
    private static final String TAG = "MainActivity";
    private DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        s_nic = findViewById(R.id.nic);
        s_acc = findViewById(R.id.account);
        s_fname = findViewById(R.id.fname);
        s_lname = findViewById(R.id.lname);
        s_addr = findViewById(R.id.addr);
        s_phn = findViewById(R.id.phn);
        btn2 = findViewById(R.id.btn2);
        Button btn1 = (Button) findViewById(R.id.btn1);
        s_email = findViewById(R.id.email);
        s_date = (EditText) findViewById(R.id.date);


        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

//                submit();
                fuck();
//                System.out.println("==========================================================");
//                startService(new Intent(MainActivity.this, Postmaster.class));
            }
        });

        //reset button
        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                s_nic.setText("");
                s_acc.setText("");
                s_addr.setText("");
                s_email.setText("");
                s_fname.setText("");
                s_lname.setText("");
                s_phn.setText("");

            }
        });

        //spinner
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.saluation, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        //select date picker
        s_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        MainActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                s_date.setText(date);
            }
        };


    }


    //submit button
    public void submit() {
        initialize();
        if (!validateNIC() | !validateAccnum() | !validateFname() | !validateLname() | !validatePhn() | !validateEmail
                () | !validateAddress() | !validateDate()) {
            Toast.makeText(this, "Your Submission failed, Please Try again", Toast.LENGTH_SHORT).show();
        } else {
            openDialog();


        }
    }

    public void fuck() {
        String formDetails = s_acc.getText() + "/" + s_addr.getText() + "/" + s_date.getText() + "/" + s_email.getText() + "/" + s_fname.getText() + "/" + s_lname.getText();
        Intent intent = new Intent(this, Postmaster.class);
        intent.putExtra("name", formDetails);
        startService(intent);
    }

    public void openDialog() {
        AlertDialog.Builder a_builder = new AlertDialog.Builder(MainActivity.this);
        a_builder.setMessage("Confirmation \n Are You sure you want to submit? ")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        AlertDialog alert = a_builder.create();
        alert.setTitle("Hi");
        alert.show();
    }


    //NIC validation (mandotory, maximum characters 20)
    public boolean validateNIC() {
        boolean valid = true;

        if (nic.isEmpty()) {
            s_nic.setError("NIC number is required");
            valid = false;
        } else {
            s_nic.setError(null);
        }
        return valid;
    }

    //Account Number validation (max 20 digits, first char non zero - mandatory)
    public boolean validateAccnum() {
        boolean valid = true;

        if (account.isEmpty()) {
            s_acc.setError("Account number is required");
            valid = false;
        } else if (account.charAt(0) == 0) {
            s_acc.setError("Not a valid number");
            valid = false;
        } else {
            s_acc.setError(null);
        }
        return valid;
    }


    //First Name validation (max 50 , alphabet digits,  mandatory)
    public boolean validateFname() {
        boolean valid = true;

        if (fname.isEmpty()) {
            s_fname.setError("Name is required");
            valid = false;
        } else if (fname.length() > 50) {
            s_fname.setError("Not valid, too long");
            valid = false;
        } else {
            s_fname.setError(null);
        }
        return valid;
    }

    //Last Name validation (max 50 digits, alphabet)
    public boolean validateLname() {
        boolean valid = true;

        if (lname.length() > 50) {
            s_lname.setError("Not valid, too long");
            valid = false;
        } else {
            s_lname.setError(null);
        }
        return valid;
    }


    //birth date validationDate (mandotary)
    public boolean validateDate() {
        boolean valid = true;

        if (date.isEmpty()) {
            s_date.setError("DATE is required");
            valid = false;
        }
        return valid;
    }

    //Phone number validation (max 10 )
    public boolean validatePhn() {
        boolean valid = true;

        if (phn.length() > 10) {
            s_phn.setError("Not valid phone number");
            valid = false;
        } else {
            s_phn.setError(null);
        }
        return valid;
    }

    //Email validation (mandatory)
    public boolean validateEmail() {
        boolean valid = true;

        if (email.isEmpty()) {
            s_email.setError("Email is required");
            valid = false;
        } else if (!Patterns.EMAIL_ADDRESS.
                matcher(email).matches()) {
            s_email.setError("Not valid email");
            valid = false;
        } else {
            s_email.setError(null);
        }
        return valid;
    }

    //address validation (max 250 , mandatory)
    public boolean validateAddress() {
        boolean valid = true;

        if (addr.isEmpty()) {
            s_addr.setError("Address is required");
            valid = false;
        } else if (addr.length() > 250) {
            s_addr.setError("Not valid, too long");
            valid = false;
        } else {
            s_addr.setError(null);
        }
        return valid;
    }

    public void initialize() {
        fname = s_fname.getText().toString().trim();
        email = s_email.getText().toString().trim();
        lname = s_lname.getText().toString().trim();
        nic = s_nic.getText().toString().trim();
        phn = s_phn.getText().toString().trim();
        account = s_acc.getText().toString().trim();
        addr = s_addr.getText().toString().trim();
        date = s_date.getText().toString().trim();

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
