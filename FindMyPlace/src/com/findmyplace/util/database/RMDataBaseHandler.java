package com.findmyplace.util.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class RMDataBaseHandler extends SQLiteOpenHelper{

	Context context;
	protected SQLiteDatabase db;

    public static final String DB_NAME = "REMEMBER_ME_DB";
    public static final int DB_VERSION = 4;
    protected static final String LOCATION_TABLE = "LOCATION_TABLE";
    
	// common column names
	public static final String ID = "id";
	public static final String LOCATION_LATITUDE = "latitude";
	public static final String LOCATION_LONGITUDE= "longitude";
	public static final String IMAGE_URL = "description";
	public static final String LOCATION_NAME = "name";
	public static final String TYPE = "type";
	public static final String LOCATION_ADDRESS = "address"; 
	
    public static final String CREATE_LOCATION_TABLE = "create table " + LOCATION_TABLE + "(" + 
    		ID + " text primary key, " +
    		LOCATION_ADDRESS + " text not null, " +
    		LOCATION_NAME + " text , " +
    		LOCATION_LATITUDE + " text not null, " +
    		LOCATION_LONGITUDE + " text not null," +
    		IMAGE_URL + " text ," +
    		TYPE + " text " +
	    ")";
	
	
	public RMDataBaseHandler(Context context) {
		this(context, null);
	}

	public RMDataBaseHandler(Context context,SQLiteDatabase.CursorFactory cursorFactory) {
		super(context, DB_NAME, cursorFactory,DB_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase) {
		//sometimes onCreate is invoked even when the db is already created
				try {
					sqLiteDatabase.execSQL(CREATE_LOCATION_TABLE); 
				} catch (Exception e) {
					Log.e("APDBHandler", "db creation failed " + "db version: " +  sqLiteDatabase.getVersion() + " current version: " + DB_VERSION + "\n" + e.getMessage());
				}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	public long insert(String id,String name,String address, String latitude,
			String longitude,String image,String type){
		ContentValues initialValues = new ContentValues();
		initialValues.put(ID, id);
		initialValues.put(LOCATION_ADDRESS, address);
        initialValues.put(LOCATION_NAME, name);
        initialValues.put(LOCATION_LATITUDE, latitude);
        initialValues.put(LOCATION_LONGITUDE, longitude);        
        initialValues.put(IMAGE_URL, image);        
        initialValues.put(TYPE, type);        
        return db.insert(LOCATION_TABLE, null, initialValues);		
	}

	
	
	public boolean delete(String id){
		return db.delete(LOCATION_TABLE, ID + "=" + id, null) > 0;		
	}
	
	public Cursor getAllLocations(String id){
		Cursor c = db.query(false, LOCATION_TABLE,
				new String[]{ID,LOCATION_ADDRESS,LOCATION_NAME,LOCATION_LATITUDE,LOCATION_LONGITUDE,IMAGE_URL,TYPE},
				null,
				null,
				null, 
				null, 
				null, 
				null);
		return c;
		
	}

}
