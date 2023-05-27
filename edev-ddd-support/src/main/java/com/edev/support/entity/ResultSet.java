/* 
 * Created by 2019年1月25日
 */
package com.edev.support.entity;

import lombok.Data;

import java.util.Collection;
import java.util.Map;

/**
 * The result set of query.
 * @author fangang
 */
@Data
public class ResultSet {
	private Collection<?> data;
	private Integer page;
	private Integer size;
	private Long count;
	private Map<String, Object> aggregation;
}
