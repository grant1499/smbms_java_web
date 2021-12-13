package top.grantdrew.servlet.user;

import top.grantdrew.pojo.User;
import top.grantdrew.service.user.UserService;
import top.grantdrew.service.user.UserServiceImpl;
import top.grantdrew.util.Constant;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取前端表单的参数
        String userCode = req.getParameter("userCode");
        String userPassword = req.getParameter("userPassword");

        UserService userService = new UserServiceImpl();
        User user = userService.login(userCode,userPassword);

        if (user != null){
            req.getSession().setAttribute(Constant.USER_SESSION,user);
            resp.sendRedirect("jsp/frame.jsp"); // 登录成功跳转主页
        }else{
            req.setAttribute("error","用户名或密码不正确！");
            req.getRequestDispatcher("login.jsp").forward(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
