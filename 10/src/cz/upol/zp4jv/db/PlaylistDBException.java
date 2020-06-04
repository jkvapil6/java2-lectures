package cz.upol.zp4jv.db;

public class PlaylistDBException extends Exception {

	private static final long serialVersionUID = 4922795077754323787L;

	public PlaylistDBException(String message) {
		super(message);
	}

	public PlaylistDBException(String message, Throwable cause) {
		super(message, cause);
	}
}
