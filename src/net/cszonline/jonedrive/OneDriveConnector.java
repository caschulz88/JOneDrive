package net.cszonline.jonedrive;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import net.cszonline.jonedrive.login.AuthenticationScope;

public class OneDriveConnector {
	
	private final String clientId;
	
	private OneDriveConnector( String clientId ) {
		this.clientId = clientId;
	}
	
	public static OneDriveConnector create( final String clientId ) {
		return new OneDriveConnector(clientId);
	}
	
	// ------ Methods ------
	
	public void loginToken( final AuthenticationScope... authScopes ) throws IOException {
		URL url = new URL("https://login.live.com/oauth20_authorize.srf?client_id=" + clientId + "&scope=" + AuthenticationScope.parse(authScopes) + "&response_type=token&redirect_uri=https://login.live.com/oauth20_desktop.srf");

		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		
		InputStream is;
		if (httpConn.getResponseCode() >= 400) {
		    is = httpConn.getErrorStream();
		} else {
		    is = httpConn.getInputStream();
		}
		
		StringBuilder builder = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
		    for (String line; (line = reader.readLine()) != null;) {
		    	builder.append(line);
		    }
		}
		
		File file = File.createTempFile("onedriveauth", "html");
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		bw.write(builder.toString());
		bw.flush();
		bw.close();
		
		Desktop.getDesktop().browse(file.toURI());
	}
	
	public void logout() throws IOException {
		URL url = new URL("https://login.live.com/oauth20_logout.srf?client_id=" + clientId + "&redirect_uri=https://login.live.com/oauth20_desktop.srf");

		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		
		InputStream is;
		if (httpConn.getResponseCode() >= 400) {
		    is = httpConn.getErrorStream();
		} else {
		    is = httpConn.getInputStream();
		}
		
		StringBuilder builder = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
		    for (String line; (line = reader.readLine()) != null;) {
		    	builder.append(line);
		    }
		}
		
		File file = File.createTempFile("onedriveauthlogout", "html");
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		bw.write(builder.toString());
		bw.flush();
		bw.close();
		
		Desktop.getDesktop().browse(file.toURI());
	}
	
	/**
	 * TODO: For test purposes only. Will be removed later one!
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		OneDriveConnector connector = OneDriveConnector.create("000000004415B35E");
		connector.loginToken(AuthenticationScope.WL_SIGNIN,
				AuthenticationScope.WL_OFFLINEACCESS,
				AuthenticationScope.ONEDRIVE_READWRITE);
	}
	
}
