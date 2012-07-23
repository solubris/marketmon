package solubris.marketmon.requestor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Helper {
	static private final Logger logger = LoggerFactory.getLogger(Helper.class);

	public static String randomizeUrl(String url) {
		// randomise the order of the parameters, to help obscure automation
		String[] urlParts = url.split("[?]", 2);
		if (urlParts.length < 2 || urlParts[1] == null) {
			// URL doesn't have any params, so nothing to randomise
			return url;
		}
		List<String> urlParams = Arrays.asList(urlParts[1].split("&"));
		urlParams = new ArrayList<String>(urlParams); // need to make ArrayList so
		// can add items to the array
		// add a dumy param to obufy request
		urlParams.add("randomparamishere=" + System.currentTimeMillis());
		Collections.shuffle(urlParams);

		urlParts[1] = join(urlParams.toArray(new String[] {}), "&");
		// .(type[]) collection.toArray(new type[collection.size()]);

		return join(urlParts, "?");
	}

	public static String join(String[] strings, String separator) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < strings.length; i++) {
			if (i != 0)
				sb.append(separator);
			sb.append(strings[i]);
		}
		return sb.toString();
	}

	public static String randomizeKeyword(String keyword, String operator) {
		// this breaks google search when quotes are used
		// String randWord = generateWord(10);
		// keyword = keyword + operator + randWord;
		return keyword;
	}

	final static String alphabet = "abcdefghijklmnopqrstuvwxyz";
	static GpwData data = null;

	private static String generateWord(int pwl) {
		int c1, c2, c3;
		long sum = 0;
		int nchar;
		long ranno;
		double pik;
		StringBuilder password;
		Random ran = new Random(); // new random source seeded by clock

		// Pick a random starting point.
		password = new StringBuilder(pwl);
		pik = ran.nextDouble(); // random number [0,1]
		ranno = (long) (pik * data.getSigma()); // weight by sum of
		// frequencies
		sum = 0;
		for (c1 = 0; c1 < 26; c1++) {
			for (c2 = 0; c2 < 26; c2++) {
				for (c3 = 0; c3 < 26; c3++) {
					sum += data.get(c1, c2, c3);
					if (sum > ranno) {
						password.append(alphabet.charAt(c1));
						password.append(alphabet.charAt(c2));
						password.append(alphabet.charAt(c3));
						c1 = 26; // Found start. Break all 3 loops.
						c2 = 26;
						c3 = 26;
					} // if sum
				} // for c3
			} // for c2
		} // for c1

		// Now do a random walk.
		nchar = 3;
		while (nchar < pwl) {
			c1 = alphabet.indexOf(password.charAt(nchar - 2));
			c2 = alphabet.indexOf(password.charAt(nchar - 1));
			sum = 0;
			for (c3 = 0; c3 < 26; c3++)
				sum += data.get(c1, c2, c3);
			if (sum == 0) {
				break; // exit while loop
			}
			pik = ran.nextDouble();
			ranno = (long) (pik * sum);
			sum = 0;
			for (c3 = 0; c3 < 26; c3++) {
				sum += data.get(c1, c2, c3);
				if (sum > ranno) {
					password.append(alphabet.charAt(c3));
					c3 = 26; // break for loop
				} // if sum
			} // for c3
			nchar++;
		} // while nchar
		return (password.toString()); // Password generated
	} // generate()

	static {
		if (data == null) {
			data = new GpwData();
		}
	}

	/**
	 * @return
	 */
	static public Integer extractNumber(String pattern, String content,
			StringBuilder match) {
		if(content==null)
			return null;

		Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(content);
		if (m.find()) {
			match.append(m.group());
			logger.info("found measure match " + m.group() + " groups="
					+ m.groupCount());
			for (int i = 0; i < m.groupCount(); i++) {
				logger.info("g[" + i + "]=" + m.group(i + 1));
				if (m.group(i + 1) != null) {
					String filtered = m.group(i + 1).replaceAll(",", "");
					return (Integer.parseInt(filtered));
				}
			}
		}
		return null;
	}

	public static String trimXmlCdata(String s) {
		if (s != null && s.startsWith("<![CDATA[") && s.endsWith("]]>")) {
			s = s.substring(9, s.length() - 3);
		}
		return s;
	}

	public static String extractGroupFromMatch(Matcher m, int groupNumber, String defaultValue) {
		if(m.groupCount()>=groupNumber) {
			return trimXmlCdata(m.group(groupNumber));
		}

		return defaultValue;
	}
}
