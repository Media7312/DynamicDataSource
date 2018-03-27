package com.example.demo.conf;

/**
 * @Author: 侯玢
 * @Description:
 * @Date: Created in 15:18 2018/3/25
 */
public final class DyamicDataSourceHolder {
    private static final ThreadLocal<DyamicDataSourceType> thread = new ThreadLocal<>();
    private DyamicDataSourceHolder() {
        //
    }
    public static void putDataSource(DyamicDataSourceType dyamicDataSourceType){
        thread.set(dyamicDataSourceType);
    }
    public static DyamicDataSourceType getDataSource(){
        return thread.get();
    }
    public static void cleanDataSource(){
        thread.remove();
    }
}
