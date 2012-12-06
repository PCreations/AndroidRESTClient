package com.pcreations.restclient;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.SQLException;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.pcreations.restclient.daos.RequestDao;
import com.pcreations.restclient.db.DatabaseManager;
import com.pcreations.restclient.model.Request;
import com.pcreations.restclient.model.Request.State;

import fr.chupee.jsonparser.parser.AbstractParser;

public class WebService implements RestResultReceiver.Receiver{
	
	/*
	 * 1/ Instanciation du WebService (ex : VilleWebService, etc.)
	 * 2/ On effectue une requ�te get/post (via l'activit� : villeWS.get(params);
	 * 3/ On g�re la requ�te pour savoir si elle est d�j� en cours ou non ou si elle a �chou� (serviceHelper)
	 * 4/ Si elle est en cours on ne fait rien (serviceHelper)
	 * 5/ Si elle est termin�e et qu'elle n'a pas �chou�e on g�re le cache (serviceHelper)
	 * 		5a/ Si le r�sultat obtenu l'a �t� � une p�riode sup�rieur au cache alors on relance la requ�te (serviceHelper)
	 * 		5b/ Sinon on se contente d'aller lire dans la bdd les r�sultats demand�s (serviceHelper)
	 * 6/ Si elle est termin�e et qu'elle a �chou�e on refait une requ�te (serviceHelper)
	 * 7/ On lance le service
	 * 8/ Le service se charge de r�aliser les m�thodes GET/POST sur le serveur via le HttpRequestManager
	 * 9/ Les r�sultats sont pars�s et update la bdd dans le service via le parseur
	 * 10/ Le serviceHelper est notifi� de la fin de la requ�te
	 * 
	 */
	
	enum Method { GET, POST, PUT, DELETE }
	
	public final static String METHOD_KEY = "com.pcreations.restclient.webservice.METHOD_KEY";
	public final static String PARAMS_KEY = "com.pcreations.restclient.webservice.PARAMS_KEY";
	public final static String RECEIVER_KEY = "com.pcreations.restclient.webservice.RECEIVER_KEY";
	public final static String RESULT_KEY = "com.pcreations.restclient.webservice.RESULT_KEY";
	public final static int GET = 0;
	public final static int POST = 1;
	public final static int PUT = 2;
	public final static int DELETE = 3;
	
	private Context mContext;
	private Uri mUri;
	private Intent mIntent;
	private static WebService ws;
	private RequestDao mRequestDao;
	private AbstractParser<?> mParser;
	
	public static WebService getInstance(Context context) {
		if(null == ws) {
			ws = new WebService(context);
		}
		ws.setContext(context);
		return ws;
	}
	
	private WebService(Context context) {
		super();
		mContext = context;
		DatabaseManager.init(context);
		mRequestDao = DatabaseManager.getInstance().getHelper().getRequestDao();
		mIntent = new Intent(mContext, RestService.class);
	}
	
	public void setParser(AbstractParser<?> parser) {
		mParser = parser;
	}
	
	public void get(String uri) {
		Request r = null;
		try {
			r = mRequestDao.findByUri(uri);
			if(null == r) {
				initService(Method.GET, uri);
			}
			else {
				if(r.getState() == State.STATE_OK) {
					if(r.getResult() != 200) {
						initService(Method.GET, uri);
						startService();
					}
				}
				else {
					// TODO g�rer le cache
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void initService(Method method, String uri) {
		mUri = Uri.parse(uri);
		mIntent.setData(mUri);
		mIntent.putExtra(METHOD_KEY, method);
	}
	
	private void startService() {
		mContext.startService(mIntent);
	}
	
	public static InputStream getJSONResult(String result) {
		return new ByteArrayInputStream(result.getBytes());
	}

	public void setContext(Context context) {
		mContext = context;
	}

	public Uri getUri() {
		return mUri;
	}

	public void setUri(Uri mUri) {
		this.mUri = mUri;
	}

	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
		Log.d("RECEIVE RESULT : resultCode = ", String.valueOf(resultCode));
		Log.d("RECEIVE RESULT : resultData = ", resultData.getString(HttpRequestHandler.RESPONSE_KEY));
		// TODO Auto-generated method stub
	}
	
	
	
	
	
}
