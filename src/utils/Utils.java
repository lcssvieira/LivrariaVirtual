package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
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
	
	public static Double converteMoneyTextEmDouble(String text){
		if (text != "" && text.trim() != null){
			String cleanText = text.replace("R$ ","").replace(",",".");
			return Double.parseDouble(cleanText);
		}
		return 0.0;
	}
	
	public static String converteDoubleEmMoneyText(Double valor){
		if (valor != null){
			String text = (valor < 1 ?"0":"") + valor.toString();
			text="R$ " + text;
			String result= text.substring(0,text.length() -2) + "," + text.substring(text.length() -1, text.length());
			if (result =="R$ ,00")
				result = "R$ 0,00";
			
			return result;
		}
		return "R$ 0,00";
		
	}

}
