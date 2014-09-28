package com.lyfelink.linkup;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

/**
 * Package: com.lyfelink.linkup, Project: LinkUp.
 * Created by Jan on 09.10.2014.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "linksManager",
    TABLE_LINKS ="links",
    KEY_ID ="id",
    KEY_NAME ="name",
    KEY_COMPANY ="company",
    KEY_JOBTITLE="jobTitle",
    KEY_EMAIL = "email",
    KEY_PHONE ="phone",
    KEY_WEBSITE ="website",
    KEY_ADDRESS ="address",
    KEY_IMAGEURL ="imageUri";

    //Constructor
    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE "+TABLE_LINKS+" ("+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+KEY_NAME+" TEXT, "+KEY_COMPANY+" TEXT, "+KEY_JOBTITLE+" TEXT, "+
                                                    KEY_EMAIL+" TEXT, "+KEY_PHONE+" TEXT, "+KEY_WEBSITE+" TEXT, " +KEY_ADDRESS+" TEXT, "+KEY_IMAGEURL+" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_LINKS);

        onCreate(db);
    }

    //C.R.U.D
    public void createContact(Contact contact){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values =new ContentValues();

        values.put(KEY_NAME, contact.getName());
        values.put(KEY_COMPANY, contact.getCompany());
        values.put(KEY_JOBTITLE, contact.getJobTitle());
        values.put(KEY_EMAIL, contact.getEmail());
        values.put(KEY_PHONE, contact.getPhone());
        values.put(KEY_WEBSITE, contact.getWebsite());
        values.put(KEY_ADDRESS, contact.getAddress());
        values.put(KEY_IMAGEURL, String.valueOf(contact.getImageUri()));

        db.insert(TABLE_LINKS, null, values);
        db.close();
    }

    public Contact getContact(int id){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_LINKS, new String[] {KEY_ID, KEY_NAME, KEY_COMPANY, KEY_JOBTITLE, KEY_EMAIL, KEY_PHONE, KEY_WEBSITE, KEY_ADDRESS, KEY_IMAGEURL },
                                    KEY_ID+"=?", new String[] {String.valueOf(id)}, null, null, null, null );

        if(cursor == null){
            cursor.moveToFirst();
        }

        Contact contact =new Contact(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),
                                        cursor.getString(5), cursor.getString(6), cursor.getString(7), Uri.parse(cursor.getString(8)));
        db.close();
        cursor.close();

        return contact;
    }

    public int updateContact(Contact contact){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values =new ContentValues();

        values.put(KEY_NAME, contact.getName());
        values.put(KEY_COMPANY, contact.getCompany());
        values.put(KEY_JOBTITLE, contact.getJobTitle());
        values.put(KEY_EMAIL, contact.getEmail());
        values.put(KEY_PHONE, contact.getPhone());
        values.put(KEY_WEBSITE, contact.getWebsite());
        values.put(KEY_ADDRESS, contact.getAddress());
        values.put(KEY_IMAGEURL, String.valueOf(contact.getImageUri()));

        int rowsAffected = db.update(TABLE_LINKS, values, KEY_ID+"=?", new String[] {String.valueOf(contact.getId()) });
        db.close();

        return rowsAffected;

    }

    public void deleteContact(Contact contact){
        SQLiteDatabase db = getWritableDatabase();

        db.delete(TABLE_LINKS, KEY_ID+"=?", new String[] { String.valueOf(contact.getId()) } );
        db.close();

    }

    public  int getContactCount(){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_LINKS, null);
        int cnt = cursor.getCount();

        db.close();
        db.close();

        return  cnt;
    }

    public List<Contact> getAllContacts(){
        List<Contact> contacts =new ArrayList<Contact>();

        SQLiteDatabase db = getReadableDatabase(); //getWritableDatabase()?
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_LINKS, null);

        if(cursor.moveToFirst()){
            do{
                contacts.add(new Contact(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),
                        cursor.getString(5), cursor.getString(6), cursor.getString(7), Uri.parse(cursor.getString(8))));
            }while (cursor.moveToFirst());
        }
        cursor.close();
        db.close();

        return contacts;
    }
}
