package Test;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import dao.CustomerDao;
import entity.Customer;
import org.apache.commons.dbutils.QueryRunner;

/**
 * @author Lixinhua
 * @email codingLixh@163.com
 * @date 2018/3/1 11:24
 */
public class test {
    QueryRunner qr=new TxQueryRunner();
    public static void main(String[] args)
    {
        CustomerDao customerDao=new CustomerDao();

        for (int i=0;i<1000;i++)
        {
            Customer customer=new Customer();
            customer.setId(CommonUtils.uuid());
            customer.setName("customer"+i);
            customer.setGender(i%2==0?"male":"female");
            customer.setPhone("13476"+i);
            customer.setEmail("customer"+i+"@163.com");
            customer.setDescription("helle world");

            customerDao.addCustomer(customer);
        }
    }
}
