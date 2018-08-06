package com.dao.multipleDataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 该方式配置多数据源，在加了事务的方法中切换是不生效的，因为DataSourceTransactionManager只能管理一个数据源的事务，需要采用atomikos+JTA进行分布式事务管理
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		return CustomerContextHolder.getCustomerType();
	}

}
