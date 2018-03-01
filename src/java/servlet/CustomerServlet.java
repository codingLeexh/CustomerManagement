package servlet;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import entity.Customer;
import entity.PageBean;
import service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author Lixinhua
 * @email codingLixh@163.com
 * @date 2018/3/1 9:11
 */
//控制层
public class CustomerServlet extends BaseServlet {

    //注入service层
    private CustomerService customerService = new CustomerService();

    //获取当前页码
    private int getPageCode(HttpServletRequest request) {
        String value = request.getParameter("pagecode");
        if (value == null || value.trim().isEmpty()) {
            //返回当前页为第一页
            return 1;
        }
        //否则返回具体页数
        return Integer.parseInt(value);
    }

    //获取分页的页码URL
    private String getUrl(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        String servletPath = request.getServletPath();
        String queryString = request.getQueryString();
        if (queryString.contains("&pagecode")) {
            int index = queryString.lastIndexOf("pagecode");
            queryString = queryString.substring(0, index);
        }
        return contextPath + servletPath + "?" + queryString;
    }

    //字符格式转换
    private Customer encoding(Customer customer) throws UnsupportedEncodingException {
        String name = customer.getName();
        String gender = customer.getGender();
        String phone = customer.getPhone();
        String email = customer.getEmail();
        if (name != null && !name.trim().isEmpty()) {
            name = new String(name.getBytes("ISO-8859-1"), "utf-8");
            customer.setName(name);
        }
        if (gender != null && !gender.trim().isEmpty()) {
            gender = new String(gender.getBytes("ISO-8859-1"), "utf-8");
            customer.setGender(gender);
        }
        if (phone != null && !phone.trim().isEmpty()) {
            phone = new String(phone.getBytes("ISO-8859-1"), "utf-8");
            customer.setPhone(phone);
        }
        if (email != null && !email.trim().isEmpty()) {
            email = new String(email.getBytes("ISO-8859-1"), "utf-8");
            customer.setEmail(email);
        }
        return customer;
    }

//    public void setCustomerService(CustomerService customerService) {
//        this.customerService = customerService;
//    }

    /**
     * 添加
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String addCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //得到封装好的用户
        Customer customer = CommonUtils.toBean(request.getParameterMap(), Customer.class);
        //将用户的id用uuid随机生成的数表示
        customer.setId(CommonUtils.uuid());
        if (customer != null) {
            customerService.addCustomer(customer);
            request.setAttribute("msg", "恭喜，成功添加客户");
            return "/msg.jsp";
        }
        request.setAttribute("msg", "error，添加客户异常");
        return "/msg.jsp";
    }

    /**
     * 查询
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String findAllCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取页面传递的pc
        int pagecode = getPageCode(request);
        //设定每页显示数据总数,每页只显示十条数据
        int pagerecords = 10;

        PageBean<Customer> pageBean = customerService.findAllCustomer(pagecode, pagerecords);
        pageBean.setUrl(getUrl(request));
        request.setAttribute("pageBean", pageBean);

        return "f:/list.jsp";
    }

    /**
     * 编辑
     *
     * @param request
     * @param response
     * @return
     */
    public String editFindCustomerById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取用户ID
        String id = request.getParameter("id");
        Customer customer = customerService.findCustomerById(id);

        request.setAttribute("customer", customer);

        return "/edit.jsp";
    }

    /**
     * 修改
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String editCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Customer customer = CommonUtils.toBean(request.getParameterMap(), Customer.class);
        if (customer != null) {
            customerService.editCustomer(customer);
            request.setAttribute("msg", "编辑客户信息成功！");
            return "/msg.jsp";
        }
        request.setAttribute("msg", "编辑客户信息失败！");
        return "/msg.jsp";
    }

    /**
     * 删除
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String deleteCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取用户ID
        String id = request.getParameter("id");
        if (id != null) {
            customerService.deleteCustomerById(id);
            request.setAttribute("msg", "恭喜，删除客户成功");
            return "/msg.jsp";
        }
        request.setAttribute("msg", "error，删除客户失败");
        return "/msg.jsp";
    }

    public String query(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //得到封装好的用户
        Customer customer = CommonUtils.toBean(request.getParameterMap(), Customer.class);
        //转换字符格式
        customer =encoding(customer);

        //获取当前页码
        int pagecode= getPageCode(request);
        //规定每页记录数
        int pagerecords = 10;
        //获取页面数据
        PageBean<Customer> pageBean =customerService.query(customer,pagecode,pagerecords);
        if(pageBean!=null){
            //获取页面URL
            pageBean.setUrl(getUrl(request));
            request.setAttribute("pageBean",pageBean);
            return "/list.jsp";
        }
        request.setAttribute("msg", "查询数据异常");
        return "/msg.jsp";
    }
}
