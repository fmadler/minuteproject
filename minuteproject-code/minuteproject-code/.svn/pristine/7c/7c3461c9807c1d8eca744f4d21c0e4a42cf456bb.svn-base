package net.sf.minuteProject.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CollectionUtils {

	public static Map<String, List<String>> getGroup (List<String> input, String fieldPatternType, String ...fieldPattern) {
		Map<String, List<String>> collect = 
			input.stream()
				.filter(getStringPredicate(fieldPatternType, fieldPattern))
				.collect(
					Collectors.toMap(
						e -> getKey(e, fieldPatternType, fieldPattern)
						,e -> fillList(e)
						,(e1, e2) -> {
							e1.addAll(e2);
							return e1;
						}
					)
				);
		return collect;
	}
	
	private static List<String> fillList(String e) {
		List<String> list = new ArrayList<>();
		list.add(e);
		return list;
	}

	private static String getKey(String element, String fieldPatternType, String ...fieldPattern) {
		String s = Arrays.stream(fieldPattern)
				.filter(Objects::nonNull)
				.filter(u -> {
					if (fieldPatternType.equals("ends-with") && element.endsWith(u))
						return true;
					return false;
				})
				.map(u -> {
					if (fieldPatternType.equals("ends-with")) {
						return element.replace(u, "");
					} else
						return u;
				}).findFirst().get();
		return s;

	}

	private static Predicate<String> getStringPredicate(String fieldPatternType, String[] fieldPattern) {
		return e -> {
			return Arrays.stream(fieldPattern)
					.filter(Objects::nonNull)
					.anyMatch(k -> {
				return checkExpression (fieldPatternType, e, k);
			});
		};
	}

	private static boolean checkExpression(String fieldPatternType, String element, String pattern) {
		if (fieldPatternType.equals("ends-with")) {
			return element.endsWith(pattern);
		}
		return false;
	}
	
}
