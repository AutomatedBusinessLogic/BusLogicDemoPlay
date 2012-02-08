package util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class NumberFormat {

	/**
	 * Format the given number as money
	 */
	public static String formatMoney(BigDecimal bd) {
		if (bd == null)
			bd = BigDecimal.ZERO;
		DecimalFormat nf = new DecimalFormat();
		nf.setDecimalSeparatorAlwaysShown(true);
		nf.setGroupingUsed(true);
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
		return nf.format(bd);
	}
	
	/**
	 * Parse a money amount, ignoring $'s and thousand-separating commas.
	 */
	public static BigDecimal parseMoney(String s) {
		BigDecimal result = BigDecimal.ZERO;
		String cleanValue = s.trim().replaceAll("\\$", "");
		cleanValue = cleanValue.replaceAll(",", "");
		try {
			result = new BigDecimal(cleanValue);
		}
		catch(Exception ex) {
			throw new RuntimeException("Invalid value: " + s);
		}
		if (result.compareTo(BigDecimal.ZERO) < 0)
			throw new RuntimeException("Negative value not allowed: " + s);
		
		return result;
	}
	
	/**
	 * Parse a number, ignoring thousand-separating commas. A negative number
	 * will cause an exception.
	 */
	public static Integer parseNumber(String s) {
		int result = 0;
		String cleanValue = s.trim().replaceAll(",", "");
		try {
			result = Integer.valueOf(cleanValue);
		}
		catch(Exception ex) {
			throw new RuntimeException("Invalid value: " + s);
		}
		if (result < 0)
			throw new RuntimeException("Negative value not allowed: " + s);
		
		return result;
	}
}
