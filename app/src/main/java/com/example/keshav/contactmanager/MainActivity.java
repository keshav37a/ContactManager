package com.example.keshav.contactmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import Data.DatabaseHandler;
import Model.Contact;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHandler db = new DatabaseHandler(this);

        //Insert Contacts
        Log.i("Insert:", "Inserting ");
        db.addContact(new Contact("KM", "233178"));
        db.addContact(new Contact("SM", "245180"));
        db.addContact(new Contact("AM", "567821"));
        db.addContact(new Contact("BM", "037327"));

        //Read contacts
        Log.i("Reading:", "Reading all contacts");
        List<Contact> contactList = db.getAllContacts();

        for(Contact c: contactList)
        {
            String log = "Id: " + c.getId() + " Name: "+ c.getName()+ " PhoneNo: "+ c.getPhoneNo();
            Log.i("Contact ", log);
        }

        //Get 1 contact
        Contact oneContact = db.getContact(1);
        oneContact.setName("lolllllllllz");
        oneContact.setPhoneNo("23423424");

        //Update Contact
        int newContact = db.updateContact(oneContact);
        Log.i("OneContact ", "Updated Row: " + String.valueOf(newContact) + "Name: "+ oneContact.getName()+
                " PhoneNo "+ oneContact.getPhoneNo());

        //Delete Contact
//        Contact oneMoreContact = db.getContact(2);
//        db.deleteContact(oneMoreContact);
        for(Contact c: contactList)
        {
            String log = "Id: " + c.getId() + " Name: "+ c.getName()+ " PhoneNo: "+ c.getPhoneNo();
            Log.i("Contact ", log);
        }

        //GET COUNT
        Log.i("Number of contacts", String.valueOf(db.getCount()));

    }
}
