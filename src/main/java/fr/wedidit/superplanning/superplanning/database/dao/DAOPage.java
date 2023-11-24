package fr.wedidit.superplanning.superplanning.database.dao;

import java.util.List;

/**
 * Limits the selection of items on a SELECT.
 *
 * @param pageNumber Number of pages
 * @param pageSize Size of pages
 * @param resultList List of result
 * @param <T> Type of object handled
 */
public record DAOPage<T>(int pageNumber, int pageSize, List<T> resultList) {

    public static final int DEFAULT_PAGE_SIZE = 10;

}
