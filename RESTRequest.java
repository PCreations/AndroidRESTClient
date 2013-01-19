package com.pcreations.restclient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.http.message.BasicHeader;

import android.os.Bundle;

enum HTTPVerb { GET, POST, PUT, DELETE };

public class RESTRequest implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3975518541858101876L;
	
	private UUID mID;
	private HTTPVerb mVerb;
	private String mUrl;
	private Bundle mExtraParams;
	private List<SerializableHeader> mHeaders;
	
	public RESTRequest(HTTPVerb verb, UUID id, String url) {
		super();
		mVerb = verb;
		mID = id;
		mUrl = url;
		mHeaders = new ArrayList<SerializableHeader>();
	}
	
	public RESTRequest(HTTPVerb verb, UUID id, String url, Bundle extraParams) {
		super();
		mVerb = verb;
		mID = id;
		mUrl = url;
		mExtraParams = extraParams;
		mHeaders = new ArrayList<SerializableHeader>();
	}

	protected OnFinishedRequestListener mOnFinishedRequestListener;
	
	public void setOnFinishedRequestListener(OnFinishedRequestListener listener) {
		mOnFinishedRequestListener = listener;
	}

	public UUID getID() {
		return mID;
	}
	
	public List<SerializableHeader> getHeaders() {
		return mHeaders;
	}

	public OnFinishedRequestListener getListener() {
		return mOnFinishedRequestListener;
	}
	
	public interface OnFinishedRequestListener {
        public abstract void onFinishedRequest(int resultCode);
    }

	public String getUrl() {
		return mUrl;
	}

	public void setUrl(String url) {
		this.mUrl = url;
	}

	public HTTPVerb getVerb() {
		return mVerb;
	}

	public void setVerb(HTTPVerb mVerb) {
		this.mVerb = mVerb;
	}
	
	public class SerializableHeader extends BasicHeader implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = -3589739936804187767L;
		
		public SerializableHeader(String name, String value) {
			super(name, value);
			// TODO Auto-generated constructor stub
		}
		
	}
	
	
}
