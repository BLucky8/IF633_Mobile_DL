package com.example.expensesmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class income_expanse extends AppCompatActivity implements ContactAdapter.OnEditListener {

    RecyclerView contactList;
    ContactAdapter contactAdapterList;

    ArrayList<ContactModelClass> contactModelClassArrayList;
    private EditText userName, userContact, userDate;
    private MaterialButton btnAdd;
    private Button keHome;

    AlertDialog alertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contactModelClassArrayList = new ArrayList<>();
        setContentView(R.layout.income_expanse);
        userContact = findViewById(R.id.userMobile);
        userName = findViewById(R.id.userName);
        userDate = findViewById(R.id.userDate);
        keHome = findViewById(R.id.toHome);
        btnAdd = findViewById(R.id.btnAdd);
        contactList = findViewById(R.id.contactListid);
        contactList.setHasFixedSize(true);
        contactList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        keHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toHome();
            }
        });

        btnAdd.setOnClickListener(v -> {
            String strUserName = "", strUserContact = "", strUserDate = "";
            if (userName.getText() != null) {
                strUserName = userName.getText().toString();
            }
            if (strUserName.equals("")) {
                Toast.makeText(this, "please enter your income", Toast.LENGTH_LONG).show();
                return;
            }
            if (userContact.getText() != null) {
                strUserContact = userContact.getText().toString();
            }
            if (strUserContact.equals("")) {
                Toast.makeText(this, "Please Enter description", Toast.LENGTH_LONG).show();
                return;
            }
            if (userDate.getText() != null) {
                strUserDate = userDate.getText().toString();
            }
            if (strUserDate.equals("")) {
                Toast.makeText(this, "Please enter the date", Toast.LENGTH_LONG).show();
                return;
            }
            addContact(strUserName, strUserContact, strUserDate);
        });
    }

    public void addContact(String strUserName, String strUserContact, String strUserDate) {

        ContactModelClass obj = new ContactModelClass();
        obj.setUserName(strUserName);
        obj.setUserNumber(strUserContact);
        obj.setUserDate(strUserDate);


        contactModelClassArrayList.add(obj);

        contactAdapterList = new ContactAdapter(this, contactModelClassArrayList, this::onEditClick);
        contactList.setAdapter(contactAdapterList);
    }

    @Override
    public void onEditClick(ContactModelClass listCurrentData, int currentPosition) {

        AlertDialog.Builder builderObj = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.layout_edit_contact, null);

        EditText userNameEtn = view.findViewById(R.id.userName);
        EditText userContactEtn = view.findViewById(R.id.userMobile);
        EditText userDateEtn = view.findViewById(R.id.userDate);
        MaterialButton btnEdit = view.findViewById(R.id.btnEdit);

        userContactEtn.setText(listCurrentData.getUserNumber());
        userNameEtn.setText(listCurrentData.getUserName());
        userDateEtn.setText(listCurrentData.getUserDate());

        ImageView closeAlert = view.findViewById(R.id.closeAlert);
        builderObj.setCancelable(false);
        builderObj.setView(view);

        closeAlert.setOnClickListener(v -> {
            alertDialog.cancel();
        });

        btnEdit.setOnClickListener(v -> {
            String strUserName = "", strUserContact = "", strUserDate = "";
            if (userNameEtn.getText() != null) {
                strUserName = userNameEtn.getText().toString();
            }
            if (strUserName.equals("")) {
                Toast.makeText(this, "please enter your income", Toast.LENGTH_LONG).show();
                return;
            }
            if (userContactEtn.getText() != null) {
                strUserContact = userContactEtn.getText().toString();
            }
            if (strUserContact.equals("")) {
                Toast.makeText(this, "Please enter the description", Toast.LENGTH_LONG).show();
                return;
            }
            if (userDateEtn.getText() != null) {
                strUserDate = userDateEtn.getText().toString();
            }
            if (strUserDate.equals("")) {
                Toast.makeText(this, "Please enter date", Toast.LENGTH_LONG).show();
                return;
            }
            editContact(strUserName, strUserContact, strUserDate, currentPosition);
        });

        alertDialog = builderObj.create();
        alertDialog.show();

    }

    public void editContact(String strUserName, String strUserContact, String strUserDate, int currentPosition) {

        ContactModelClass obj = new ContactModelClass();
        obj.setUserName(strUserName);
        obj.setUserNumber(strUserContact);
        obj.setUserDate(strUserDate);


        contactAdapterList.editData(obj, currentPosition);
        alertDialog.cancel();
    }

    private void toHome() {
        Intent intent = new Intent(this, Homepage.class);
        startActivity(intent);
    }
}