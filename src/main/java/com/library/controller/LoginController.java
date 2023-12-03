package com.library.controller;

import com.library.bean.*;
import com.library.dao.CharacterDao;
import com.library.service.LoginService;
import com.library.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;

@Controller
public class LoginController {

    private LoginService loginService;
    private ProfileService profileService;


    @Autowired
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }
    @Autowired
    public void setProfileService(ProfileService profileService){
        this.profileService = profileService;
    }

    @RequestMapping(value = {"/", "/login.html"})
    public String toLogin(HttpServletRequest request) {
        request.getSession().invalidate();
        return "index";
    }

    @RequestMapping("/logout.html")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/login.html";
    }


    @RequestMapping("admin_login.html")
    public String adminlogin(HttpServletRequest request){
        request.getSession().invalidate();
        return "index_admin";
    }

    @RequestMapping("staff_login.html")
    public String stafflogin(HttpServletRequest request){
        request.getSession().invalidate();
        return "index_staff";
    }






    //负责处理loginCheck.html请求
    //请求参数会根据参数名称默认契约自动绑定到相应方法的入参中
    @RequestMapping(value = "/api/loginCheck", method = RequestMethod.POST)
    public @ResponseBody
    Object loginCheck(HttpServletRequest request) {

        String user_name = request.getParameter("id");
        String passwd = request.getParameter("passwd");


        HashMap<String, String> res = new HashMap<>();
        User user = loginService.selectUserByUserName(user_name, passwd);

        if(user!=null){
            if( user.getUser_state().equals("usable") ){
                user.setIsregister("yes");
                user.setLastlogin_time(new Date());
                profileService.updateUser(user);

                UserCharacter userCharacter = loginService.selectByUserId(user.getUser_id());
                character cha = loginService.selectCharacterById(userCharacter.getCharacter_id());
                request.getSession().setAttribute("user", user);
                request.getSession().setAttribute("userCharacter", userCharacter);
                request.getSession().setAttribute("cha", cha);
                // admin_main页面的myModel 只显示一次
                request.getSession().setAttribute("myModalShow", false);

                if( cha.getCharacter_name().equals("admin")){
                    res.put("stateCode", "2");
                    res.put("msg", "管理员登录成功！");
                }
                else if(cha.getCharacter_name().equals("staff") ){
                    res.put("stateCode", "3");
                    res.put("msg", "工作人员登录成功！");
                }
                else if(cha.getCharacter_name().equals("reader")){
                    res.put("stateCode", "4");
                    res.put("msg", "读者登录成功！");
                }
            }
            else{
                res.put("stateCode", "1");
                res.put("msg", "账号不可用！请联系管理员");
            }

        }
        else{
            res.put("stateCode", "0");
            res.put("msg", "账号或密码错误！");
        }

        return res;
    }

    @RequestMapping(value = "/mymodal-view-status", method = RequestMethod.POST)
    public ResponseEntity<?> myModalViewStatus(HttpServletRequest request) {
        request.getSession().setAttribute("myModalShow", true);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping("/admin_main.html")
    public ModelAndView toAdminMain(HttpServletResponse response) {
        return new ModelAndView("admin_main");
    }

    @RequestMapping("/staff_main.html")
    public ModelAndView toStaffMain(HttpServletResponse response) {
        return new ModelAndView("staff_main");
    }

    @RequestMapping("/reader_main.html")
    public ModelAndView toReaderMain(HttpServletResponse response) {
        return new ModelAndView("reader_main");
    }

    @RequestMapping("/admin_header.html")
    public ModelAndView admin_header() {
        return new ModelAndView("admin_header");
    }

    @RequestMapping("/reader_header.html")
    public ModelAndView reader_header() {
        return new ModelAndView("reader_header");
    }

    @RequestMapping("/staff_header.html")
    public ModelAndView staff_header(){
        return new ModelAndView("staff_header");
    }

    @RequestMapping("reader_register.html")
    public ModelAndView reader_register(){return new ModelAndView("reader_register");}


    @RequestMapping("/admin_repasswd.html")
    public ModelAndView reAdminPasswd() {
        return new ModelAndView("admin_repasswd");
    }

    @RequestMapping("/find_password.html")
    public ModelAndView find_password(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView("find_password");
        return modelAndView;
    }

    //配置404页面
    @RequestMapping("*")
    public String notFind() {
        return "404";
    }

}