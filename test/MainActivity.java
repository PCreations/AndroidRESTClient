package com.pcreations.restclient.test;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.pcreations.rest.R;
import com.pcreations.restclient.RESTRequest;
import com.pcreations.restclient.RESTRequest.OnFinishedRequestListener;
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
        ws.addNote(addNoteRequest, new Note(5, "Je suis une NOTE", 0, true, null));
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
