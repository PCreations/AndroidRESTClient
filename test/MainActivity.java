package com.pcreations.restclient.test;

import java.sql.SQLException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.pcreations.rest.R;
import com.pcreations.restclient.DaoAccess;
import com.pcreations.restclient.RESTRequest;
import com.pcreations.restclient.RESTRequest.OnFinishedRequestListener;
import com.pcreations.restclient.ResourceRepresentation;
import com.pcreations.restclient.RestService;

public class MainActivity extends Activity  {

	private TestWebService ws;
	private RESTRequest<Note> addNoteRequest;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(RestService.TAG, "START");
        setContentView(R.layout.activity_main);
        ws = new TestWebService(this);
        addNoteRequest = ws.newRequest(Note.class);
        ORMLiteDaoFactory daoFactory = new ORMLiteDaoFactory();
        DaoAccess<ResourceRepresentation<?>> daoAddress = daoFactory.getDao(Address.class);
        try {
			Address a = (Address) daoAddress.findById(2);
			Log.i(RestService.TAG, "Address = " + a.toString());
			Note n = new Note(5, "blabla note", 0, true, a.getId());
			ws.addNote(addNoteRequest, n);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        /*try {
			Address a = new Address(2, null, null);
			Note n = new Note(5, "Je suis une NOTE", 0, true, a);
			Collection<Note> cn = new ArrayList<Note>();
			cn.add(n);
			a.setNotes(cn);
			DatabaseManager.getInstance().getHelper().getAddressDao().updateOrCreate(a);
			ws.addNote(addNoteRequest, n);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatabaseManagerNotInitializedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        
        /*DatabaseManager.init(getApplicationContext());
        ORMLiteDaoFactory daoFactory = new ORMLiteDaoFactory();
        DaoAccess<ResourceRepresentation<?>> daoAddress = daoFactory.getDao(Address.class);
        DaoAccess<ResourceRepresentation<?>> daoNote = daoFactory.getDao(Note.class);
        try {
			List<ResourceRepresentation<?>> notesList = daoNote.queryForAll();
			Log.i(RestService.TAG, "notesList is empty ? " + String.valueOf(notesList.isEmpty()));
			for(ResourceRepresentation<?> note : notesList) {
				Log.i(RestService.TAG, note.toString());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			List<ResourceRepresentation<?>> addressList = daoAddress.queryForAll();
			Log.i(RestService.TAG, "addressList is empty ? " + String.valueOf(addressList.isEmpty()));
			for(ResourceRepresentation<?> address : addressList) {
				Log.i(RestService.TAG, address.toString());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
    }
    
    public void testRequest(View button) {
    	Log.d(RestService.TAG, "CLIQUE");
    }
    
    public void onResume() {
    	super.onResume();
    	
    	addNoteRequest.setOnFinishedRequestListener(new OnFinishedRequestListener() {

			@Override
			public void onFinishedRequest(int resultCode) {
				// TODO Auto-generated method stub
				Log.d(RestService.TAG, "POST REQUEST resultCode = " + String.valueOf(resultCode));
				Log.d(RestService.TAG, "POST REQUEST terminée : " + addNoteRequest.toString());
			}
    	});
    	
    }
    
    public void onPause() {
    	super.onPause();
    	addNoteRequest.setOnFinishedRequestListener(null);
    }

}
