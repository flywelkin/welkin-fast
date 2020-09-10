package io.gitee.welkinfast.common.page;


/**
 * @Description 分页参数封装
 * @Author yuanjg
 * @CreateTime 2020/8/20 16:08
 * @Version 1.0.0
 */
public class PageRequest<T> {
    /**
     * 当前页码
     */
    private int current = 1;
    /**
     * 每页数量
     */
    private int size = 10;
    /**
     * 查询参数
     */
    private T params;

    public int getCurrent() {
        if (this.current < 1) {
            current = 1;
        }
        return this.current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getSize() {
        if (this.size < 1) {
            this.size = 10;
        }
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public T getParams() {
        return params;
    }

    public void setParams(T params) {
        this.params = params;
    }

    public boolean paramsIsNotEmpty() {
        if (this.params != null) {
            return true;
        }
        return false;
    }
}
