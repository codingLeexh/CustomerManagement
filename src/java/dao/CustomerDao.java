package dao;

import cn.itcast.jdbc.TxQueryRunner;
import entity.Customer;
import entity.PageBean;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lixinhua
 * @email codingLixh@163.com
 * @date 2018/2/28 21:45
 */
//数据访问层
public class CustomerDao {

    private QueryRunner queryRunner =new TxQueryRunner();//创建一个作为数据库查询的对象


    /**
     * 添加用户
     * @param customer
     */
    public void addCustomer(Customer customer){

        String sql="insert into t_customer values(?,?,?,?,?,?)";
        Object[] params={customer.getId(),customer.getName(),customer.getGender(),customer.getPhone(),customer.getEmail(),customer.getDescription()};

        try {
            queryRunner.update(sql,params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 分页查询所有数据
     * @param pagecode
     * @param pagerecords
     * @return
     */
    public PageBean<Customer> findAllCustomer(int pagecode,int pagerecords){


        try {
            PageBean<Customer> pageBean =new PageBean<>();
            pageBean.setPagecode(pagecode);
            pageBean.setPagerecords(pagerecords);

            String sql ="select count(*) from t_customer";
            Number number =(Number) queryRunner.query(sql,new ScalarHandler<>());
            int tatalrecords=number.intValue();
            pageBean.setTatalrecords(tatalrecords);

            //限制每次查询的记录数是每页规定显示的记录数，并且按名字排序
            sql="select * from t_customer order by name limit ?,?";
            Object[] params={(pagecode-1)*pagerecords,pagerecords};
            List<Customer> beanList =queryRunner.query(sql,new BeanListHandler<>(Customer.class),params);
            pageBean.setBeanList(beanList);//当前页的记录
            return pageBean;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 按ID查询用户
     * @param id
     * @return
     */
    public Customer findCustomerById(String id){
        //条件SQL，按Id查询
        String sql="select * from t_customer where id=?";
        try {
            //返回被查询的ID所对应的用户信息。
            return queryRunner.query(sql,new BeanHandler<>(Customer.class),id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改指定用户信息
     * @param customer
     */
    public void editCustomer(Customer customer){
        //更新SQL，指定ID所对应的用户
        String sql="update t_customer set name=?,gender=?,phone=?,email=?,description=? where id=?";
        Object[] params={customer.getName(),customer.getGender(),customer.getPhone(),customer.getEmail(),customer.getDescription(),customer.getId()};

        try {
            queryRunner.update(sql,params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除指定用户
     * @param id
     */
    public void deleteCustomer(String id){
        String sql = "delete from t_customer where id=?";
        try {
            queryRunner.update(sql, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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



        try {
            PageBean<Customer> pageBean =new PageBean<>();
            pageBean.setPagecode(pagecode);
            pageBean.setPagerecords(pagerecords);

            StringBuilder cntSql = new StringBuilder("select count(*) from t_customer ");
            StringBuilder whereSql=new StringBuilder(" where 1=1 ");
            List<Object> params =new ArrayList<>();
            String name=customer.getName();
            if (name != null && !name.trim().isEmpty()) {
                whereSql.append("and name like ?");
                params.add("%"+name+"%");
            }
            String gender = customer.getGender();
            if (gender != null && !gender.trim().isEmpty()) {
                whereSql.append("and gender=?");
                params.add(gender);
            }

            String phone = customer.getPhone();
            if (phone != null && !phone.trim().isEmpty()) {
                whereSql.append("and phone like ?");
                params.add("%"+phone+"%");
            }

            String email = customer.getEmail();
            if (email != null && !email.trim().isEmpty()) {
                whereSql.append("and email like ?");
                params.add("%"+email+"%");
            }
            //把符合条件的用户数据全部查出来
            Number number=queryRunner.query(cntSql.append(whereSql).toString(),new ScalarHandler<>(),params.toArray());
            int tatalrecords=number.intValue();
            pageBean.setTatalrecords(tatalrecords);

            StringBuilder sql=new StringBuilder("select * from t_customer ");
            StringBuilder limitSql=new StringBuilder(" limit ?,?");//分页

            params.add((pagecode-1)* pagerecords);//页面总数
            params.add(pagerecords);

            //将从条件查询获得的数据进行分页
            List<Customer> beanList=queryRunner.query(sql.append(whereSql).append(limitSql).toString(),new BeanListHandler<Customer>(Customer.class),params.toArray());
            pageBean.setBeanList(beanList);
            return pageBean;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
