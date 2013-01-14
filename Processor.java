package com.pcreations.restclient;

import android.content.Context;
import android.os.Bundle;

public abstract class Processor {

	protected Context mContext;
	
	public Processor(Context context) {
		mContext = context;
	}

	protected abstract void execute(Bundle resultData);
	
}
