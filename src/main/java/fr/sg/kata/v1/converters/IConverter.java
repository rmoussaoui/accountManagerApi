package fr.sg.kata.v1.converters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A generic converter
 * @author r.moussaoui
 *
 * @param <T>
 * @param <K>
 */

public interface IConverter<T,K> {
	
	K convert(T s);
	
	default Collection<K> convertAll(Collection<T> csource) {
		List<K> result = new ArrayList<>();
		
		csource.forEach(item -> result.add(convert(item)));
		
		return result;
	}
}
