package net.sf.minuteProject.utils;

import java.text.CharacterIterator;
import java.text.SimpleDateFormat;
import java.text.StringCharacterIterator;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import net.sf.minuteProject.configuration.bean.GeneratorBean;
import net.sf.minuteProject.utils.format.FormatCache;
import net.sf.minuteProject.utils.format.FormatCacheEntry;
import net.sf.minuteProject.utils.java.JavaUtils;

public class FormatUtils {

	public static final String CONVERT_TO_JAVA_NAME = "CONVERT_TO_JAVA_NAME";
	public static final String CONVERT_TO_JAVA_VARIABLE_NAME = "CONVERT_TO_JAVA_VARIABLE_NAME";
	public static final String CONVERT_TO_JAVA_VARIABLE_NAME_CONVERTING_RESERVED_WORD = "CONVERT_TO_JAVA_VARIABLE_NAME_CONVERTING_RESERVED_WORD";

	public static String getJavaName(String name) {
		FormatCacheEntry fce = new FormatCacheEntry(name, CONVERT_TO_JAVA_NAME);
		String value = FormatCache.getInstance().getCacheEntry(fce);
		if (value == null) {
			value = performGetJavaName(name);
			FormatCache.getInstance().putCacheEntryValue(fce, value);
		}
		return value;
	}

	public static String getJavaNameVariable(String name) {
		FormatCacheEntry fce = new FormatCacheEntry(name, CONVERT_TO_JAVA_VARIABLE_NAME);
		String value = FormatCache.getInstance().getCacheEntry(fce);
		if (value == null) {
			value = performGetJavaNameVariable(name);
			FormatCache.getInstance().putCacheEntryValue(fce, value);
		}
		return value;
	}

	public static String getJavaNameVariableConvertReservedWord(String name) {
		FormatCacheEntry fce = new FormatCacheEntry(name, CONVERT_TO_JAVA_VARIABLE_NAME_CONVERTING_RESERVED_WORD);
		String value = FormatCache.getInstance().getCacheEntry(fce);
		if (value == null) {
			value = performGetJavaNameVariableConvertReservedWord(name);
			FormatCache.getInstance().putCacheEntryValue(fce, value);
		}
		return value;
	}

	public static String getUppercaseUnderscore (String name) {
		if (name!=null) {
			String underscoreName = StringUtils.replace(name, "-", "_");
			underscoreName = StringUtils.replace(name, " ", "_");
			return underscoreName.toUpperCase();
		}
		return "";
	}
	
	public static String getLowcaseHyphen (String name) {
		name = name.toLowerCase();
		return StringUtils.replace(name, "_", "-");
	}	
	public static String getDirFromPackage(String packageSt) {
		return getDirFromPackage(packageSt, true);
	}
	public static String getDirFromPackage(String packageSt, boolean toConvert) {
		if (!toConvert)
			return (packageSt != null) ? packageSt: "";
		return (packageSt != null) ? StringUtils.replace(packageSt, ".", "/"): "";
	}

	public static String getDirToPackage(String packageSt) {
		return StringUtils.replace(packageSt, "/", ".");
	}

	public static String performDisplayReadableName(String name) {
		String s = getStripIntoSpace(name);	
		return firstUpperCase(s);
	}
	
	public static String performDisplayReadableFirstUpperOnlyName(String name) {
		String s = getStripIntoSpace(name);
		s = s.toLowerCase();
		return firstUpperCase(s);
	}

	private static String getStripIntoSpace(String name) {
		if (StringUtils.isEmpty(name))
			return "NAME_RETURNS_NULL";
		String s = StringUtils.replace(name, "-", "_");
		s = StringUtils.replace(name, "_", " ");
		return s;
	}
	
	public static String performGetJavaName(String name) {
		String underscoreName = getInUnderscore(name);
		return getJavaNameViaCharStrip(underscoreName, "_");
	}

	public static String getInUnderscore(String name) {
		if (StringUtils.isEmpty(name))
			return "NAME_RETURNS_NULL";
		String underscoreName = StringUtils.replace(name, "-", "_");
		underscoreName = StringUtils.replace(underscoreName, " ", "_");
		return underscoreName;
	}

	private static String getJavaNameViaCharStrip(String name,
			String charToStrip) {
		StringTokenizer st = new StringTokenizer(name, charToStrip);
		StringBuffer sb = new StringBuffer();
		while (st.hasMoreTokens()) {
			sb.append(firstUpperCaseOnly(st.nextToken()));
		}
		return sb.toString();
	}

	private static String performGetJavaNameVariableConvertReservedWord(
			String name) {
		String value = performGetJavaNameVariable(name);
		if (JavaUtils.isReservedWord(name))
			value = convertReservedWord(value);
		return value;
	}

	private static String performGetJavaNameVariable(String name) {
		if (name == null)
			return "ERROR_GET_JAVANAMEVARIABLE_WITH_NULL";
		if (name.equals(""))
			return name;
		return getJavaNameForGetterAndSetter(name);
	}

	public static String getJavaVariableNameForGetterAndSetterFromJavaName(String name) {
		if (name == null)
			return "ERROR_GET_JAVANAMEVARIABLE_WITH_NULL";
		if (isSecondLetterUpperCase(name))
			return name;
		return firstLowerCase(name);
	}
	
	private static boolean isSecondLetterUpperCase(String name) {
		String secondLetter = getSecondLetter(name);
		if (secondLetter==null) return false;
		return isLetterUpperCase(secondLetter);
	}

	private static String getSecondLetter(String st) {
		if (st.length()>1)
			return st.substring(1, 2);
		return null;
	}

	private static boolean isLetterUpperCase(String letter) {
		for (char c :letter.toCharArray()) {
			if (Character.isUpperCase(c))
				return true;
			return false;
		}
		return false;
	}

	private static String getJavaNameForGetterAndSetter(String name) {
		String javaname = getJavaName(name);
		return getJavaNameForGetterAndSetter(name, javaname);
	}

	public static String getJavaNameForGetterAndSetter(String name,
			String javaname) {
		if (isStandardBean(name))
			return firstLowerCase(javaname);
		// trick java part
		// this is due to have correct getter
		// to create a correct getter apply the following rule
		// if second letter is a Capital then the first one should also be a
		// capital
		//
		return firstUpperCase(javaname);
	}

	public static String getJavaNameVariableFirstLetter(String name) {
		return getJavaVariableNameForGetterAndSetterFromJavaName(name);
//		return firstLowerCase(name);
	}

	public static String firstUpperCaseOnly(String st) {
		if (st == null)
			return "INPUT_STRING_IS_NULL";
		int len = Math.min (st.length(),1);
		return st.substring(0, len).toUpperCase()
				+ st.substring(len, st.length()).toLowerCase();
	}

	public static String fullLowerCase(String st) {
		if (st == null)
			return "INPUT_STRING_IS_NULL";
		return st.substring(0, 1).toLowerCase()
				+ st.substring(1, st.length()).toLowerCase();
	}
	
	public static String firstLowerCaseOnly(String st) {
		if (st == null)
			return "INPUT_STRING_IS_NULL";
		return st.substring(0, 1).toLowerCase()
				+ st.substring(1, st.length());
	}
	
	public static String lowerCase(String st) {
		return st.toLowerCase();
	}

	public static String firstLowerCase(String st) {
		if (StringUtils.isEmpty(st))
			return "INPUT_STRING_IS_NULL";
		return st.substring(0, 1).toLowerCase() + st.substring(1, st.length());
	}

	public static String firstUpperCase(String st) {
		if (st == null)
			return "INPUT_STRING_IS_NULL";
		return st.substring(0, 1).toUpperCase() + st.substring(1, st.length());
	}

	public static String remove_ID_patternFromColumnName(String input) {
		input = StringUtils.removeStart(input, "ID_");
		input = StringUtils.removeEnd(input, "_ID");
		return input;
	}

	public static String formatToSQLSingleString(String input) {
		input = StringUtils.replace(input, " ", "_");
		return upperCase(input);
	}

	public static String upperCase(String st) {
		return StringUtils.upperCase(st);
	}

	private static boolean isStandardBean(String name) {
		if (name.indexOf("_") == 1)
			return false;
		return true;
	}

	public static String getShortNameFromVerbose(String name) {
		name = getEachWordFirstLetterUpper(name, " ");
		name = getEachWordFirstLetterUpper(name, "-");
		return name;
	}

	private static String getEachWordFirstLetterUpper(String name, String token) {
		StringTokenizer st = new StringTokenizer(name, token);
		StringBuffer sb = new StringBuffer();
		while (st.hasMoreTokens()) {
			String s = st.nextToken();
			sb.append(firstUpperCase(s));
		}
		return sb.toString();
	}

	public static final String convertToValidJava(String input, String regEx,
			String replacement, boolean upperCase) {
		input = input.trim();
		String res = convertToValidJava(input, regEx, replacement);
		if (upperCase)
			res = res.toUpperCase();
		return res;
	}

	public static final String convertToValidJava(String input, String regEx,
			String replacement) {
		String res = input.trim();
		Pattern pattern = Pattern.compile(regEx);
		java.util.regex.Matcher matcher = pattern.matcher(res);
		while (matcher.find()) {
			String val = matcher.group();
			res = res.replace(val, replacement);
		}
		return res;
	}

	public static final String trimExpression(String inputExpression,
			String trimmedValue) {
		// TODO some validation needed
		if (inputExpression.startsWith(trimmedValue)) {
			int startIndex = inputExpression.indexOf(trimmedValue);
			inputExpression = inputExpression.substring(startIndex
					+ trimmedValue.length());
		}
		if (inputExpression.endsWith(trimmedValue)) {
			int lastIndex = inputExpression.lastIndexOf(trimmedValue);
			inputExpression = inputExpression.substring(0, lastIndex);
		}
		return inputExpression;
	}

	public static final String eliminateMultipleSequenceOfChar(
			String inputExpression, char... seq) {
		StringBuffer result = new StringBuffer();
		char prevChar = 0;
		for (int i = 0; i < inputExpression.length(); i++) {
			char currentChar = inputExpression.charAt(i);
			if (existCharInSequence(currentChar, seq)) {
				if (i > 0 && currentChar != prevChar) {
					result.append(currentChar);
				}
			} else {
				result.append(currentChar);
			}
			if (i > 0)
				prevChar = currentChar;
		}
		return result.toString();
	}

	private static boolean existCharInSequence(char c, char... seq) {
		for (int i = 0; i < seq.length; i++) {
			if (c == seq[i]) {
				return true;
			}
		}
		return false;
	}

	private static String convertReservedWord(String word) {
		return "_" + word;
	}

	public static String convertAttributeText(String description) {
		if (StringUtils.isEmpty(description)) return "";
		return StringUtils.replace(description, "\\", "\n");
	}
	
	public static String transformSlashIntoBackSlash(String description) {
		if (StringUtils.isEmpty(description)) return "";
		return StringUtils.replace(description, "/", "\\");
	}

	public static boolean isCamelCaseAlias(GeneratorBean bean) {
		return true;
//		return isCamelCaseAlias(bean.getName());
	}

	private static boolean isCamelCaseAlias(String name) {
		return name.matches("^[A-Z]$");
	}

	public static String insertUnderscoreforCamelCaseSeparation(String name) {
		StringBuffer sb = new StringBuffer();
		String[] split = StringUtils.splitByCharacterTypeCamelCase(name);
		for (int i=0; i<split.length;i++) {
			sb.append(split[i]);
			if (i+1!=split.length)
				sb.append("_");
		}
		return sb.toString();
	}
	
	public static String decamelCaseForSqlAliasing(String name) {
		StringBuffer sb = new StringBuffer();
		String[] split = StringUtils.splitByCharacterTypeCamelCase(name);
		for (int i=0; i<split.length;i++) {
			sb.append(getSqlAliasingForCamelChunk(split[i]));
			if (i+1!=split.length)
				sb.append("_");
		}
		return sb.toString();
	}

	private static String getSqlAliasingForCamelChunk(String string) {
		char[] chars = string.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i<chars.length; i++) {
			if (Character.isUpperCase(chars[i]) && i>0)
				sb.append("_"+chars[i]);
			else
				sb.append(Character.toUpperCase(chars[i]));
		}
		return sb.toString(); 
	}

	public static String renderCurrentTime() {
		return renderTime(new Date());
	}

	public static String renderTime(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd G 'at' HH:mm:ss z");
		return sdf.format(date);
	}
	
	public static String displayCurrentTime() {
		return displayTime(new Date());
	}
	
	public static String displayTime(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
	
	public static String windowizePath (String path) {
		return StringUtils.replace(path, "/", "\\");
	}
	
	public static String unixizePath (String path) {
	    final StringBuilder result = new StringBuilder();
	    final StringCharacterIterator iterator = new StringCharacterIterator(path);
	    char character =  iterator.current();
	    while (character != CharacterIterator.DONE ){
	     
	      if (character == '\\') {
	         result.append("/");
	      }
	       else {
	        result.append(character);
	      }

	      
	      character = iterator.next();
	    }
	    return result.toString();
//		return StringUtils.replace(path, "\\", "/");
	}

	public static String stripToSizeRemovingLeft(String input, int i) {
		return StringUtils.left(input, i);
	}
	
	public static String stripToSizeRemovingRight(String input, int i) {
		return StringUtils.right(input, i);
	}

	public static String removeStart(String input,
			String start) {
		if (input.startsWith(start))
			return StringUtils.removeStart(input, start);
		return input;
	}
	
	public static String addDoubleQuotes (String input) {
		return "\""+input+"\"";
	}

	public static String stripSpecialCharacterForJavaCompliance(String input) {
		String result = null;
		if (input!=null) {
			result = StringUtils.replace(input, "#", "_");
		}
		return result;
	}
	
	public static String escapeHtml (String input) {
		return StringEscapeUtils.escapeHtml(input);
	}
	
	public static String getSentence(String input) {
		input = input.toLowerCase();
		input = StringUtils.replace(input, "_", " ");
		input = StringUtils.replace(input, "-", " ");
		return firstUpperCaseOnly(input);
	}
	
	public static String escapePath(String path)
	{
		if (path==null) return "";
	    return path.replace("\\", "\\\\");
	}
}
