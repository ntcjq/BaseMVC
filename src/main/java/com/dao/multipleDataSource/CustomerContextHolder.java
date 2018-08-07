package com.dao.multipleDataSource;

import org.springframework.util.StringUtils;

public class CustomerContextHolder {
	//默认的datasource的key
    public static final String DATA_SOURCE_DEFAULT = "ds_default";
    //用ThreadLocal来设置当前线程使用哪个dataSource
    private static final ThreadLocal<String> contextHolder  = new ThreadLocal<String>();
    public static void setCustomerType(String customerType) {
    	contextHolder.set(customerType);
    }
    public static String getCustomerType() {
        String dataSource = contextHolder.get();
        if (StringUtils.isEmpty(dataSource)) {
            return DATA_SOURCE_DEFAULT;
        }else {
            return dataSource;
        }
    }
    public static void clearCustomerType() {
    	contextHolder.remove();
    }
}
