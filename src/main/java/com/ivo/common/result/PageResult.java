package com.ivo.common.result;

/**
 * 响应数据分页增强
 * @author wj
 * @version 1.0
 */
public class PageResult<T> extends Result<T> {

    /**
     * 响应数据的总量，用于分页
     */
    private long count;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
