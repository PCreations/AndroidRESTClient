package com.pcreations.restclient;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.ResultReceiver;

public class WebService {

<<<<<<< master
=======
import fr.chupee.jsonparser.parser.AbstractParser;

public abstract class WebService implements RestResultReceiver.Receiver{
	
	/*
	 * 1/ Instanciation du WebService (ex : VilleWebService, etc.)
	 * 2/ On effectue une requête get/post (via l'activité : villeWS.get(params);
	 * 3/ On gère la requête pour savoir si elle est déjà en cours ou non ou si elle a échoué (serviceHelper)
	 * 4/ Si elle est en cours on ne fait rien (serviceHelper)
	 * 5/ Si elle est terminée et qu'elle n'a pas échouée on gère le cache (serviceHelper)
	 * 		5a/ Si le résultat obtenu l'a été à une période supérieur au cache alors on relance la requête (serviceHelper)
	 * 		5b/ Sinon on se contente d'aller lire dans la bdd les résultats demandés (serviceHelper)
	 * 6/ Si elle est terminée et qu'elle a échouée on refait une requête (serviceHelper)
	 * 7/ On lance le service
	 * 8/ Le service se charge de réaliser les méthodes GET/POST sur le serveur via le HttpRequestManager
	 * 9/ Les résultats sont parsés et update la bdd dans le service via le parseur
	 * 10/ Le serviceHelper est notifié de la fin de la requête
	 * 
	 */
	
	private final static String URI_SCHEMA = "";
>>>>>>> local
	public final static String METHOD_KEY = "com.pcreations.restclient.webservice.METHOD_KEY";
	public final static String PARAMS_KEY = "com.pcreations.restclient.webservice.PARAMS_KEY";
	public final static String RECEIVER_KEY = "com.pcreations.restclient.webservice.RECEIVER_KEY";
	public final static String RESULT_KEY = "com.pcreations.restclient.webservice.RESULT_KEY";
	public final static int GET = 0;
	public final static int POST = 1;
	public final static int PUT = 2;
	public final static int DELETE = 3;
	
	private ResultReceiver mReceiver;
	private Context mContext;
	private Uri mUri;
	private Intent mIntent;
<<<<<<< master
	
	public WebService(Context context, ResultReceiver receiver) {
=======
	private RequestDao mRequestDao;
	
	public WebService(Context context) {
>>>>>>> local
		super();
		mContext = context;
		mReceiver = receiver;
		mIntent = new Intent(mContext, RestService.class);
	}
	
	public void get(String uri) {
<<<<<<< master
		initService(GET, uri);
		startService();
=======
		Request r = null;
		try {
			r = mRequestDao.findByUri(uri);
			if(null == r) {
				initService(WebService.GET, uri);
			}
			else {
				if(r.getState() == State.STATE_OK) {
					if(r.getResult() != 200) {
						initService(WebService.GET, uri);
						startService();
					}
				}
				else {
					// TODO gérer le cache
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
>>>>>>> local
	}
	
	private void initService(int method, String uri) {
		mUri = Uri.parse(uri);
		mIntent.setData(mUri);
		mIntent.putExtra(METHOD_KEY, method);
		mIntent.putExtra(RECEIVER_KEY, mReceiver);
	}
	
	private void startService() {
		mContext.startService(mIntent);
	}
	
	public ResultReceiver getReceiver() {
		return mReceiver;
	}
	
	public static InputStream getJSONResult(String result) {
		return new ByteArrayInputStream(result.getBytes());
	}

	public void setReceiver(ResultReceiver mReceiver) {
		this.mReceiver = mReceiver;
	}

	public Uri getUri() {
		return mUri;
	}

	public void setUri(Uri mUri) {
		this.mUri = mUri;
	}
	
	
	
	
	
}
