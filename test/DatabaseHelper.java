package com.pcreations.restclient.test;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.pcreations.restclient.DaoAccess;
import com.pcreations.restclient.ResourceDaoGetter;
import com.pcreations.restclient.ResourceRepresentation;


public class DatabaseHelper extends OrmLiteSqliteOpenHelper implements ResourceDaoGetter<ResourceRepresentation> {
	
	private static final String DATABASE_NAME = "testDB.db";
	// Si on change la version la base doit se mettre � jour et r�installe toutes les tables. Cela permet de ne pas avoir � effacer les donn�es manuellement sur le t�l�phone
	private static final int DATABASE_VERSION = 1;
	// DAO pour l'objet Personne - la cl� dans la base est un int donc on met Integer en second
	private DaoAccess<ResourceRepresentation> testResourceDao = null;
	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource) {
		Log.i("DatabaseHelper", "onCreate");
		int i = 1;
		try {
			// Ici on doit mettre toutes les tables de notre base en lui envoyant sa classe associ�e
			TableUtils.createTable(connectionSource, TestResource.class); i++;
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Impossible de cr�er la table num " + i, e);
		}
		Log.i(DatabaseHelper.class.getName(), "Les diff�rentes tables ont �t� cr��es");
	}
	
	

	@Override
	public void onUpgrade(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource, int oldVer, int newVer) {
		try {
			// On d�truit toutes les tables et leur contenu
			TableUtils.dropTable(connectionSource, TestResource.class, true);

			// Puis on les recr�e
			onCreate(sqliteDatabase, connectionSource);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Impossible de mettre � jour la base de donn�es de la version " + oldVer + " � " + newVer, e);
		}
	}

	@Override
	public DaoAccess<ResourceRepresentation> getResourceDao() {
		if(null == testResourceDao) {
			try {
				testResourceDao = DaoManager.createDao(getConnectionSource(), TestResource.class);
			}catch(java.sql.SQLException e) {
				e.printStackTrace();
			}
		}
		return testResourceDao;
	}
}

