package org.nanotek;

import org.nanotek.PostConstructor;
import org.nanotek.Strategy;


/**
 *
 * This class has the objective to concentrate, the mapping strategy for the CsvReader on
 * OpnCSV component, the CSV parser uses an Array to manage the mapping between items on a CSV row and
 * the POJO that will be used as a spectrum of the line. ;-)
 *
 * @author josecanovamauger
 *
 * @param <T>
 */
@FunctionalInterface
public interface PostConstructorStrategy<T> extends Strategy<T>, PostConstructor<T> {

	void buildMapping();

}
