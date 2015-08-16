package net.cszonline.jonedrive.login;

public enum AuthenticationScope {
	WL_SIGNIN, WL_OFFLINEACCESS, ONEDRIVE_READONLY, ONEDRIVE_READWRITE, ONEDRIVE_APPFOLDER;

	public static String parse(AuthenticationScope... authScopes) {
		StringBuilder builder = new StringBuilder();

		for (AuthenticationScope scope : authScopes) {
			if (builder.length() > 0) {
				// there is already one existing entry on the builder so append
				// a space as separator
				builder.append("%20");
			}
			switch (scope) {
			case WL_SIGNIN:
				builder.append("wl.signin");
				break;
			case WL_OFFLINEACCESS:
				builder.append("wl.offline_access");
				break;
			case ONEDRIVE_READONLY:
				builder.append("onedrive.readonly");
				break;
			case ONEDRIVE_READWRITE:
				builder.append("onedrive.readwrite");
				break;
			case ONEDRIVE_APPFOLDER:
				builder.append("onedrive.appfolder");
				break;
			default:
				// TODO: Error handling
			}
		}
		
		return builder.toString();
	}
}
