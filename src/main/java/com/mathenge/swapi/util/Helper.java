package com.mathenge.swapi.util;

import com.mathenge.swapi.pagination.PaginationCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author Mathenge on 4/14/2020
 */
@Component
public class Helper {

    private Logger logger = LoggerFactory.getLogger(Helper.class);

    /*
    Build Response entity Object
     */
    public ResponseEntity<Object> buildSuccessResponseEntity(CustomResponse responseBody, HttpStatus status) {
        return new ResponseEntity<Object>(
                responseBody, new HttpHeaders(), status);
    }

    /*
    Build Response entity Object With List
     */
    public static ResponseEntity<Object> buildSuccessResponseEntityWithList(CustomResponseWithList responseBody, HttpStatus status) {
        return new ResponseEntity<Object>(
                responseBody, new HttpHeaders(), status);
    }

    private static boolean isCollectionEmpty(Collection<?> collection) {
        if (collection == null || collection.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean isObjectEmpty(Object object) {
        if(object == null) return true;
        else if(object instanceof String) {
            if (((String)object).trim().length() == 0) {
                return true;
            }
        } else if(object instanceof Collection) {
            return isCollectionEmpty((Collection<?>)object);
        }
        return false;
    }

    public static String buildPaginatedQuery(String baseQuery, PaginationCriteria paginationCriteria) {
        //String queryTemplate = "SELECT FILTERED_ORDERD_RESULTS.* FROM (SELECT BASEINFO.* FROM ( %s ) BASEINFO %s  %s ) FILTERED_ORDERD_RESULTS LIMIT %d, %d";
        StringBuilder sb = new StringBuilder("SELECT FILTERED_ORDERD_RESULTS.* FROM (SELECT BASEINFO.* FROM ( #BASE_QUERY# ) BASEINFO #WHERE_CLAUSE#  #ORDER_CLASUE# ) FILTERED_ORDERD_RESULTS LIMIT #PAGE_NUMBER#, #PAGE_SIZE#");
        String finalQuery = null;
        if(!isObjectEmpty(paginationCriteria)) {
            finalQuery = sb.toString().replaceAll("#BASE_QUERY#", baseQuery)
                    .replaceAll("#WHERE_CLAUSE#", ((isObjectEmpty(paginationCriteria.getFilterByClause())) ? "" : " WHERE ") + paginationCriteria.getFilterByClause())
                    .replaceAll("#ORDER_CLASUE#", paginationCriteria.getOrderByClause())
                    .replaceAll("#PAGE_NUMBER#", paginationCriteria.getPageNumber().toString())
                    .replaceAll("#PAGE_SIZE#", paginationCriteria.getPageSize().toString());
        }
        return (null == finalQuery) ?  baseQuery : finalQuery;
    }
}
