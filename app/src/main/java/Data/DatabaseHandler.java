package Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;
import java.util.List;

import Model.Contact;
import Utils.Util;

public class DatabaseHandler extends SQLiteOpenHelper
{
    SQLiteDatabase sqLiteDatabase;
    public DatabaseHandler(Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }


    //CREATE TABLES
    @Override
    public void onCreate(SQLiteDatabase db)
    {       //SQL - Structured Query Language
        String CREATE_CONTACT_TABLE = "CREATE TABLE "+Util.TABLE_NAME + " ("+ Util.KEY_ID + " INTEGER PRIMARY KEY, " + Util.KEY_NAME + " TEXT, "
                + Util.KEY_PHONE_NO + " TEXT" +")";

        db.execSQL(CREATE_CONTACT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ Util.TABLE_NAME);
        //CREATE TABLE AGAIN
        onCreate(db);
    }

    /*CRUD Operations
    CREATE, READ, UPDATE, DELETE
     */
    public void addContact(Contact contact)
    {
        sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.KEY_NAME, contact.getName());
        contentValues.put(Util.KEY_PHONE_NO, contact.getPhoneNo());

        //INSERT TO ROW
        sqLiteDatabase.insert(Util.TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();
    }

    //GET CONTACT
    public Contact getContact(int id)
    {
        sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(Util.TABLE_NAME, new String[]
                {
                        Util.KEY_ID, Util.KEY_NAME, Util.KEY_PHONE_NO
                }, Util.KEY_ID  + "=?", new String[]{String.valueOf(id)}, null, null, null );

        if(cursor!=null)
            cursor.moveToFirst();

        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
                cursor.getString(2));

        return contact;
    }

    //GET ALL CONTACTS
    public List getAllContacts()
    {
        sqLiteDatabase = this.getReadableDatabase();
        List<Contact> contactList = new ArrayList<>();

        //SELECT ALL CONTACTS

        String selectAll = "SELECT * FROM "+ Util.TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(selectAll, null);

        //LOOP THROUGH OUR CONTACTS
        if(cursor.moveToFirst())
        {
            do {
                Contact contact = new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNo(cursor.getString(2));
                contactList.add(contact);
            }
            while(cursor.moveToNext());
        }
        return contactList;
    }
    public int updateContact(Contact contact)
    {
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Util.KEY_NAME, contact.getName());
        contentValues.put(Util.KEY_PHONE_NO, contact.getPhoneNo());

        //Update row
        return sqLiteDatabase.update(Util.TABLE_NAME, contentValues, Util.KEY_ID+"=?",
                new String[]{
                String.valueOf(contact.getId())
                });
    }

    //DELETE SINGLE CONTACT
    public void deleteContact(Contact contact)
    {
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(Util.TABLE_NAME, Util.KEY_ID + "=?",
                new String[]{String.valueOf(contact.getId())});
        sqLiteDatabase.close();
    }

    public int getCount()
    {
        sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        return cursor.getCount();

    }
}
