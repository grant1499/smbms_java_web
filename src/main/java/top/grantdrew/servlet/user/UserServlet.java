package top.grantdrew.servlet.user;

import com.alibaba.fastjson.JSONArray;
import top.grantdrew.pojo.Role;
import top.grantdrew.pojo.User;
import top.grantdrew.service.role.RoleService;
import top.grantdrew.service.role.RoleServiceImpl;
import top.grantdrew.service.user.UserService;
import top.grantdrew.service.user.UserServiceImpl;
import top.grantdrew.util.Constant;
import top.grantdrew.util.PageSupport;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        System.out.println("method:" + method);
        if (method != null && method.equals("savepwd")){
            this.updatePwd(req,resp);
        }else if (method != null && method.equals("pwdmodify")){
            this.pwdModify(req,resp);
        }else if (method != null && method.equals("query")){
            this.query(req,resp);
        }else if (method != null && method.equals("ucexist")){
            this.ifExist(req,resp);
        }else if (method != null && method.equals("add")){
            this.add(req,resp);
        }else if (method != null && method.equals("deluser")){
            this.deleUser(req,resp);
        } else if (method != null && method.equals("modifyexe")){
            this.modifyUser(req,resp);
        }else if (method != null && method.equals("modify")){
            this.findById(req,resp,"usermodify.jsp");
        }else if (method != null && method.equals("view")){
            this.findById(req,resp,"userview.jsp");
        }
    }

    private void findById(HttpServletRequest req, HttpServletResponse resp,String url) throws ServletException, IOException {
        //从前端获取 要修改的用户 的id
        String uid = req.getParameter("uid");

        if (uid != null && uid.length() != 0){
            UserServiceImpl userService = new UserServiceImpl();
            //查询要更改的用户信息
            User user = null;
            try {
                user = userService.getUserById(uid);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            //将用户信息保存至 request中 让usermodify.jsp显示
            req.setAttribute("user",user);
            req.getRequestDispatcher(url).forward(req,resp);
        }
    }

    private void modifyUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String id = request.getParameter("uid");
        String userName = request.getParameter("userName");
        String gender = request.getParameter("gender");
        String birthday = request.getParameter("birthday");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String userRole = request.getParameter("userRole");

        User user = new User();
        user.setId(Integer.valueOf(id));
        user.setUserName(userName);
        user.setGender(Integer.valueOf(gender));
        try {
            user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(birthday));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        user.setPhone(phone);
        user.setAddress(address);
        user.setUserRole(Integer.valueOf(userRole));
        user.setModifyBy(((User)request.getSession().getAttribute(Constant.USER_SESSION)).getId());
        user.setModifyDate(new Date());

        UserService userService = new UserServiceImpl();
        if(userService.modifyUser(user)){
            response.sendRedirect(request.getContextPath()+"/jsp/user.do?method=query");
        }else{
            request.getRequestDispatcher("usermodify.jsp").forward(request, response);
        }
    }

    private void deleUser(HttpServletRequest req, HttpServletResponse resp) {
        //从前端获取 要删除的用户 的信息
        String userid = req.getParameter("uid");
        int delId = 0;
        //先转换
        try {
            delId = Integer.parseInt(userid);
        }catch (Exception e){
            e.printStackTrace();
            delId = 0;
        }
        //将结果存放在map集合中 让Ajax使用
        Map<String, String> resultMap = new HashMap<>();
        if(delId<=0){
            resultMap.put("delResult","notexist");
        }else {
            UserServiceImpl userService = new UserServiceImpl();
            if(userService.deleUser(delId)){
                resultMap.put("delResult","true");
            }else {
                resultMap.put("delResult", "false");
            }
        }
        //上面已经封装好 现在需要传给Ajax 格式为json 所以我们得转换格式
        resp.setContentType("application/json");//将应用的类型变成json
        PrintWriter writer = null;
        try {
            writer = resp.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //JSONArray 阿里巴巴的JSON工具类 用途就是：转换格式
        writer.write(JSONArray.toJSONString(resultMap));
        writer.flush();
        writer.close();
    }

    // 修改密码
    public void updatePwd(HttpServletRequest req,HttpServletResponse resp){
        Object o = req.getSession().getAttribute(Constant.USER_SESSION);
        String password = req.getParameter("newpassword");
        Boolean flag = false;

        if (o != null && password != null && password.length() != 0){
            UserService userService = new UserServiceImpl();
            flag = userService.updatePwd(((User)o).getId(),password);
            if (flag){
                req.getSession().setAttribute("message","修改密码成功，请使用新密码登录！");
                req.getSession().removeAttribute(Constant.USER_SESSION);
            }else{
                req.getSession().setAttribute("message","修改密码失败，请重新修改！");
            }
        }else{
            req.getSession().setAttribute("message","新密码不符合要求，请重新输入！");
        }

        try {
            req.getRequestDispatcher("pwdmodify.jsp").forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 验证旧密码，与ajax对应
    public void pwdModify(HttpServletRequest req,HttpServletResponse resp){
        Object o = req.getSession().getAttribute(Constant.USER_SESSION);
        String oldpqssword = req.getParameter("oldpassword");

        Map<String,String> resultMap = new HashMap<>();

        if (o == null){
            resultMap.put("result","sessionerror");
        }else if (!(oldpqssword != null && oldpqssword.length() != 0)){
            resultMap.put("result","error");
        } else {
            String password = ((User)o).getUserPassword();
            System.out.println("oldpassword: " + oldpqssword);
            System.out.println("password " + password);
            if (password != null && password.equals(oldpqssword)){
                resultMap.put("result","true");
            }else{
                resultMap.put("result","false");
            }
        }

        resp.setContentType("application/json"); // ajax异步请求
        try {
            PrintWriter printWriter = resp.getWriter();
            // JSONArray 阿里巴巴的JSON工具类，转换格式
            // Map -- > json
            printWriter.write(JSONArray.toJSONString(resultMap));
            printWriter.flush();
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    // 重难点
    public void query(HttpServletRequest req,HttpServletResponse resp) {
        //从前端获取数据 userlist.jsp
        String queryUserName = req.getParameter("queryname");
        String temp = req.getParameter("queryUserRole");//值为0 、1、2、3
        String pageIndex = req.getParameter("pageIndex");
        int queryUserRole = 0;

        UserServiceImpl userService = new UserServiceImpl();
        RoleServiceImpl roleService = new RoleServiceImpl();

        // 设置当前页和页面用户数量
        int currentPageNo = 1;
        int pageSize = 5;

        if (queryUserName == null) {
            queryUserName = "";
        }
        if (temp != null && !temp.equals("")) {
            queryUserRole = Integer.parseInt(temp);
        }
        if (pageIndex != null) {
            currentPageNo = Integer.parseInt(pageIndex);
        }
        System.out.println("queryUserName : " + queryUserName + ", queryUserRole : " + queryUserRole);
        int totalCount = userService.getUserCount(queryUserName, queryUserRole);

        PageSupport pageSupport = new PageSupport();
        pageSupport.setCurrentPageNo(currentPageNo);
        pageSupport.setPageSize(pageSize);
        pageSupport.setTotalCount(totalCount);

        int totalPageCount = pageSupport.getTotalPageCount();

        //控制首页和尾页
        //如果页数小于1，就显示第一页  页数大于 最后一页就 显示最后一页
        if (currentPageNo < 1) {
            currentPageNo = 1;
        } else if (currentPageNo > totalPageCount) {
            currentPageNo = totalPageCount;
        }
        // 获取用户列表展示，传给前端展示
        List<User> userList = userService.getUserList(queryUserName, queryUserRole, currentPageNo, pageSize);
        req.getSession().setAttribute("userList",userList);
        for (User u : userList){
            System.out.println(u.getUserName());
        }

        List<Role> roleList = roleService.getRoleList();
        req.getSession().setAttribute("roleList",roleList);

        req.getSession().setAttribute("totalCount",totalCount);
        req.getSession().setAttribute("currentPageNo",currentPageNo);
        req.getSession().setAttribute("totalPageCount",totalPageCount);

        req.getSession().setAttribute("queryUserName",queryUserName);
        req.getSession().setAttribute("queryUserRole",queryUserRole);

        try {
            req.getRequestDispatcher("userlist.jsp").forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 用户管理模块 子模块(验证用户编码是否已经存在)
    public void ifExist(HttpServletRequest req, HttpServletResponse resp){
        //获取前端输入 的用户编码
        String userCode = req.getParameter("userCode");
        UserServiceImpl userService = new UserServiceImpl();
        Boolean flag = userService.getUser(userCode);

        //将结果存放在map集合中 让Ajax使用
        Map<String, String> resultMap = new HashMap<>();
        if (flag){
            resultMap.put("userCode","exist");
        }
        //上面已经封装好 现在需要传给Ajax 格式为json 所以我们得转换格式
        resp.setContentType("application/json");//将应用的类型变成json
        PrintWriter writer = null;
        try {
            writer = resp.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //JSONArray 阿里巴巴的JSON工具类 用途就是：转换格式
        writer.write(JSONArray.toJSONString(resultMap));
        writer.flush();
        writer.close();
    }

    public void add(HttpServletRequest req, HttpServletResponse resp){
        String userCode = req.getParameter("userCode");
        System.out.println("    add userCode : " + userCode);
        String userName = req.getParameter("userName");
        String userPassword = req.getParameter("userPassword");
        String gender = req.getParameter("gender");
        String birthday = req.getParameter("birthday");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        String userRole = req.getParameter("userRole");

        RoleServiceImpl roleService = new RoleServiceImpl();
        List<Role> roleList = roleService.getRoleList();
        req.getSession().setAttribute("roleList",roleList);

        User user = new User();
        user.setUserCode(userCode);
        user.setUserName(userName);
        user.setUserPassword(userPassword);
        user.setAddress(address);
        try {
            user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(birthday));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        user.setGender(Integer.valueOf(gender));
        user.setPhone(phone);
        user.setUserRole(Integer.valueOf(userRole));
        user.setCreationDate(new Date());
        user.setCreatedBy(((User)req.getSession().getAttribute(Constant.USER_SESSION)).getId());

        UserService userService = new UserServiceImpl();
        if(userService.addUser(user)){
            try {
                resp.sendRedirect(req.getContextPath()+"/jsp/user.do?method=query");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            try {
                req.getRequestDispatcher("useradd.jsp").forward(req, resp);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
