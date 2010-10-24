package net.supware.boxee;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;
import android.util.Log;

public class Settings {
	private static final String TAG = Settings.class.toString();
	
	/**
	 * Current page (i.e. gesture or dpad)
	 */
	public static final String PAGE_KEY = "page";
	public static final String SERVER_NAME_KEY = "name";
	public static final String IS_MANUAL_KEY = "is_manual";
	public static final String HOST_KEY = "host";
	public static final String PORT_KEY = "port";
	public static final String AUTH_REQUIRED_KEY = "auth";
	public static final String USER_KEY = "user";
	public static final String PASSWORD_KEY = "password";
	public static final String SENSITIVITY_KEY = "sensitivity";
	public static final String REQUIRE_WIFI_KEY = "require_wifi";
	public static final String TIMEOUT_KEY = "timeout";
	
	private SharedPreferences mPreferences;

	public Settings(Context context) {
		mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		
		// Attempt to set default values if they have not yet been set
		PreferenceManager.setDefaultValues(context, R.xml.preferences, false);
	}

	public void putPage(int page) {
		SharedPreferences.Editor editor = mPreferences.edit();
		editor.putInt(PAGE_KEY, page);
		editor.commit();
	}
	
	public int getPage() {
		return mPreferences.getInt(PAGE_KEY, 0);
	}

	public String getServerName() {
		return mPreferences.getString(SERVER_NAME_KEY, "");
	}
	
	public String getHost() {
		return mPreferences.getString(HOST_KEY, "");
	}
	
	public int getPort() {
		return mPreferences.getInt(PORT_KEY, 8800);
	}
	
	public String getUser() {
		return mPreferences.getString(USER_KEY, "");
	}
	
	public String getPassword() {
		return mPreferences.getString(PASSWORD_KEY, "");
	}
	
	public boolean isManual() {
		return mPreferences.getBoolean(IS_MANUAL_KEY, false);
	}
	
	public int getTimeout() {
		return Integer.parseInt(mPreferences.getString(TIMEOUT_KEY, "1000"));
	}
	
	public boolean isAuthRequired() {
		return mPreferences.getBoolean(AUTH_REQUIRED_KEY, false);
	}
	
	public boolean requiresWifi() {
		return mPreferences.getBoolean(REQUIRE_WIFI_KEY, true);
	}
	
	public BoxeeServer constructServer() {
		return new BoxeeServer(getServerName(), getHost(), getPort(), isAuthRequired());
	}
	
	public void putServer(BoxeeServer server, boolean isManual) {
		putServer(server.address().getHostAddress(), server.port(), server.name(), server.authRequired(), isManual);
	}
	
	public void putServer(String address, int port, String name, boolean auth, boolean isManual) {
		Log.i(TAG, "Storing server as: " + name + ", manual: " + isManual);
		SharedPreferences.Editor editor = mPreferences.edit();
		editor.putString(HOST_KEY, address);
		editor.putInt(PORT_KEY, port);
		editor.putString(SERVER_NAME_KEY, name);
		editor.putBoolean(AUTH_REQUIRED_KEY, auth);
		editor.putBoolean(IS_MANUAL_KEY, isManual);
		editor.commit();	
	}

	public void listen(OnSharedPreferenceChangeListener listener) {
		mPreferences.registerOnSharedPreferenceChangeListener(listener);
	}

	public void unlisten(OnSharedPreferenceChangeListener listener) {
		mPreferences.unregisterOnSharedPreferenceChangeListener(listener);
	}
}