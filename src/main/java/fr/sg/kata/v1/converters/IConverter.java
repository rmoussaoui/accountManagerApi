package fr.sg.kata.v1.converters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A generic converter
 * @author r.moussaoui
 *
 * @param <SOURCE>
 * @param <TARGET>
 */

public interface IConverter<SOURCE,TARGET> {
	
	TARGET convert(SOURCE s);
	
	default Collection<TARGET> convertAll(Collection<SOURCE> csource) {
		List<TARGET> result = new ArrayList<>();
		
		csource.forEach(item -> result.add(convert(item)));
		
		return result;
	}
}
