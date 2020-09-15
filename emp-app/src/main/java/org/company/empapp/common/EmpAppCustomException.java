package org.company.empapp.common;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;

public class EmpAppCustomException extends RuntimeException {

	private static final long serialVersionUID = -7214458159477737441L;

	public EmpAppCustomException(Class<?> clazz, String message, Object... paramsMap) {
		super(generateMessage(clazz.getSimpleName(), message, toMap(String.class, String.class, paramsMap)));
	}

	public EmpAppCustomException(String message, Throwable e) {
		super(message, e);
	}

	private static String generateMessage(String entity, String message, Map<String, String> params) {
		return StringUtils.capitalize(entity) + message + params;
	}

	private static <K, V> Map<K, V> toMap(Class<K> keyType, Class<V> valueType, Object... entries) {
		if (entries.length % 2 == 1)
			throw new IllegalArgumentException("Invalid entries");
		return IntStream.range(0, entries.length / 2).map(i -> i * 2).collect(HashMap::new,
				(m, i) -> m.put(keyType.cast(entries[i]), valueType.cast(entries[i + 1])), Map::putAll);
	}

}
