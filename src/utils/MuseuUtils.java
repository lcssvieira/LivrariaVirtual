package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MuseuUtils {
	static SimpleDateFormat padrao = new SimpleDateFormat("dd/MM/yyyy");

	public static String converteDateEmString(Date data) throws ParseException {
		if (data != null)
			return padrao.format(data);
		else
			return null;
	}

	public static Date converteStringEmData(String data) throws ParseException {
		if (data != null && data.length() > 0) {
			try {
				padrao.setLenient(false);
				return padrao.parse(data);
			} catch (Exception e) {
				return new Date();
			}
		} else
			return new Date();

	}

}
