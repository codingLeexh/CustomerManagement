package service;

import dao.CustomerDao;
import entity.Customer;
import entity.PageBean;

/**
 * @author Lixinhua
 * @email codingLixh@163.com
 * @date 2018/3/1 8:27
 */
//业务逻辑层
public class CustomerService {
    //注入Dao层
    private CustomerDao customerDao =new CustomerDao();


//    public void setCustomerDao(CustomerDao customerDao) {
//        this.customerDao = customerDao;
//    }

    //重写dao方法

    /**
     * 添加用户
     * @param customer
     */
    public void addCustomer(Customer customer){
        customerDao.addCustomer(customer);
    }

    /**
     * 分页查询所有数据
     * @param pagecode
     * @param pagerecords
     * @return
     */
    public PageBean<Customer> findAllCustomer(int pagecode,int pagerecords){
        return customerDao.findAllCustomer(pagecode,pagerecords);
    }

    /**
     * 按ID查询用户
     * @param id
     * @return
     */
    public Customer findCustomerById(String id){
        return customerDao.findCustomerById(id);
    }

    /**
     * 修改指定用户信息
     * @param customer
     */
    public void editCustomer(Customer customer){
        customerDao.editCustomer(customer);
    }

    /**
     * 删除指定用户
     * @param id
     */
    public void deleteCustomerById(String id){
        customerDao.deleteCustomer(id);
    }

    /**
     * 条件查询
     * 高级搜索
     * 数据分页
     * @param customer
     * @param pagecode
     * @param pagerecords
     * @return
     */
    public PageBean<Customer> query(Customer customer,int pagecode,int pagerecords){
        return customerDao.query(customer,pagecode,pagerecords);
    }
}
