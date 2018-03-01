package entity;

import java.util.List;

/**
 * @author Lixinhua
 * @email codingLixh@163.com
 * @date 2018/2/28 21:34
 */
public class PageBean<Object> {
    private int pagecode;//当前页码page code
    private int tatalrecords;//总纪录数tatal records
    private int pagerecords;//每页纪录数page records
    private List<Object> beanList;//当前页的纪录
    private String url;//跳转链接

    public int getPagecode() {
        return pagecode;
    }

    public void setPagecode(int pagecode) {
        this.pagecode = pagecode;
    }

    public int getTatalrecords() {
        return tatalrecords;
    }

    public void setTatalrecords(int tatalrecords) {
        this.tatalrecords = tatalrecords;
    }

    public int getPagerecords() {
        return pagerecords;
    }

    public void setPagerecords(int pagerecords) {
        this.pagerecords = pagerecords;
    }

    public List<Object> getBeanList() {
        return beanList;
    }

    public void setBeanList(List<Object> beanList) {
        this.beanList = beanList;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getTatalpage() {
        int tatalpage = tatalrecords / pagerecords; //总页数
        return tatalrecords % pagerecords == 0 ? tatalpage : tatalpage + 1;
    }
}
