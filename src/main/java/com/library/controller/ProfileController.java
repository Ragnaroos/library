package com.library.controller;


import com.library.bean.User;
import com.library.bean.UserCharacter;
import com.library.bean.Work;
import com.library.bean.character;
import com.library.service.LoginService;
import com.library.service.ProfileService;
import com.library.service.WorkService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;


@Controller
public class ProfileController {
    @Autowired
    private ProfileService profileService;
    @Autowired
    private LoginService loginService;

    @Autowired
    private WorkService workService;


    @RequestMapping("/profile.html")
    public ModelAndView profileShow(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("profile");
        return modelAndView;
    }

    @RequestMapping("/repassword.html")
    public ModelAndView repasswordShow(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView("repassword");
        return modelAndView;
    }

    @RequestMapping("/verifyemail.html")
    public ModelAndView verifyEmailShow(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView("verifyemail");
        return modelAndView;
    }

    @RequestMapping("/renewpassword.html")
    public ModelAndView reNewPasswordShow(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView("renewpassword");
        return modelAndView;
    }

    @RequestMapping("/admin_add_staff.html")
    public ModelAndView admin_add_staff(HttpServletRequest request){
        ArrayList<Work> works = workService.selectAllWork();
        ModelAndView modelAndView = new ModelAndView("admin_add_staff");
        modelAndView.addObject("works", works);
        return modelAndView;
    }

    @RequestMapping("/admin_all_user.html")
    public ModelAndView admin_all_user(HttpServletRequest request){
        ArrayList<User> users = profileService.selectAllUser();
        ModelAndView modelAndView = new ModelAndView("admin_all_user");
        modelAndView.addObject("users", users);
        return modelAndView;
    }

    @RequestMapping("/admin_all_user_info.html")
    public ModelAndView admin_all_user_info(HttpServletRequest request){
        User user = loginService.selectUserByUserName2(request.getParameter("user_name"));
        UserCharacter uc = loginService.selectByUserId(user.getUser_id());
        character cha = loginService.selectCharacterById(uc.getCharacter_id());
        ModelAndView modelAndView = new ModelAndView("admin_all_user_info");
        modelAndView.addObject("thisuser", user);
        modelAndView.addObject("thischa", cha);
        return modelAndView;
    }

    @RequestMapping("/admin_all_user_update.html")
    public ModelAndView admin_all_user_update(HttpServletRequest request){
        User user = loginService.selectUserByUserName2(request.getParameter("user_name"));
        UserCharacter uc = loginService.selectByUserId(user.getUser_id());
        character cha = loginService.selectCharacterById(uc.getCharacter_id());
        ArrayList<Work> works = workService.selectAllWork();

        ModelAndView modelAndView = new ModelAndView("admin_all_user_update");
        modelAndView.addObject("thisuser", user);
        modelAndView.addObject("thischa", cha);
        modelAndView.addObject("works", works);
        request.getSession().setAttribute("taruser", user);
        request.getSession().setAttribute("tarcha", cha);
        return modelAndView;
    }



    @RequestMapping(value = "/verifyPassword", method = RequestMethod.POST)
    public String verifyPassword(HttpServletRequest request, RedirectAttributes redirectAttributes){
        String user_name = request.getParameter("user_name");
        String passwd = request.getParameter("password");
        User user = loginService.selectUserByUserName(user_name, passwd);
        if( user!=null ){
            // 给user.email发邮箱验证
            sendVerifyToken(user, request);

            return "redirect:/verifyemail.html";
        }
        else{
            redirectAttributes.addFlashAttribute("vertifyPError", "密码错误！");
            return "redirect:/repassword.html";
        }
    }

    public void sendVerifyToken(User user, HttpServletRequest request){
        String token = generateToken(); // 生成验证码
        request.getSession().setAttribute("token", token); // 放入会话


        String recipientEmail = user.getEmail();
        String subject = "Email Verification for Library System";
        String message = "Please input this code to verify your email: "
                + token;


        sendEmail(recipientEmail, subject, message);
    }


    private void sendEmail(String to, String subject, String text) {
        final String from = "m15009177369@163.com";
        final String password = "HHWMGELVIYRFXWCI";

        // 设置邮件服务器属性
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true"); // 开启认证
        props.put("mail.smtp.starttls.enable", "true"); // 启用TLS
        props.put("mail.smtp.host", "smtp.163.com"); // SMTP服务器地址
        props.put("mail.smtp.port", "994"); // SMTP服务器端口，网易邮箱推荐使用SSL，端口一般为994
        props.put("mail.smtp.ssl.enable", "true"); // 启用SSL加密


        // 创建Session
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            // 创建邮件消息
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(text);

            // 发送邮件
            Transport.send(message);

            System.out.println("Mail sent successfully!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateToken() {
        // 生成一个四位随机数字的字符串
        int randomNum = (int) (Math.random() * 9000) + 1000; // 生成1000到9999之间的随机数
        return String.valueOf(randomNum);
    }



    @RequestMapping(value = "/verifyEmail", method = RequestMethod.POST)
    public String verifyEmail(HttpServletRequest request, RedirectAttributes redirectAttributes){
        String genToken = (String) request.getSession().getAttribute("token");
        String token = request.getParameter("token");
        if( token.equals(genToken) ){
            return "redirect:/renewpassword.html";
        }
        else{
            redirectAttributes.addFlashAttribute("vertifyTError", "验证码错误！");
            return "redirect:/verifyemail.html";
        }
    }

    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public String updatePassword(HttpServletRequest request, RedirectAttributes redirectAttributes){
        String password = request.getParameter("newPasswd");
        User user = (User) request.getSession().getAttribute("user");
        user.setPassword(password);
        boolean isUpdate = profileService.updatePassword(user);
        if( isUpdate ){
            character cha = (character) request.getSession().getAttribute("cha");
            redirectAttributes.addFlashAttribute("vertifySucc", "密码修改成功！");
            if(cha.getCharacter_name().equals("admin") ){
                return "redirect:/admin_main.html";
            }
            else if(cha.getCharacter_name().equals("staff")){
                return "redirect:/staff_main.html";
            }
            else{
                return "redirect:/reader_main.html";
            }
        }
        else{
            redirectAttributes.addFlashAttribute("vertifyError", "密码修改失败！");
            return "redirect:/renewpassword.html";
        }

    }


    @RequestMapping(value = "/updateProfile", method = RequestMethod.POST)
    public String updateProfile(@RequestParam("picture") MultipartFile file, HttpServletRequest request, RedirectAttributes redirectAttributes){

        User user = (User)request.getSession().getAttribute("user");
        user.setUser_name(request.getParameter("user_name"));
        user.setUser_realname(request.getParameter("user_realname"));
        user.setPhone(request.getParameter("phone"));
        user.setAddress(request.getParameter("address"));
        user.setEmail(request.getParameter("email"));
        user.setSex(request.getParameter("sex"));
        try {
            // 文件存储路径
            String uploadsDir = "/uploads/";
            String realPathtoUploads = request.getServletContext().getRealPath(uploadsDir);
            if (!new File(realPathtoUploads).exists()) {
                new File(realPathtoUploads).mkdir();
            }

            // 原始文件名
            String orgName = file.getOriginalFilename();
            String filePath = realPathtoUploads + orgName;
            File dest = new File(filePath);

            // 保存文件
            file.transferTo(dest);

            // 设置用户照片路径
            user.setPhoto_path(uploadsDir + orgName);

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("updatePError", "图片上传失败！");
            return "redirect:/profile.html";
        }


        boolean isUpdate = profileService.updateUser(user);
        if (isUpdate) {
            // 更新成功
            request.getSession().setAttribute("user", user);
            redirectAttributes.addFlashAttribute("updatePSuccess", "个人信息更新成功！");
        } else {
            // 更新失败
            redirectAttributes.addFlashAttribute("updatePError", "个人信息更新失败！");
        }
        return "redirect:/profile.html";
    }

    @RequestMapping(value = "/api/user_name_check", method = RequestMethod.POST)
    public @ResponseBody
    Object user_name_check(HttpServletRequest request){
        String user_name = request.getParameter("user_name");
        User user = loginService.selectUserByUserName2(user_name);
        HashMap<String, String> res = new HashMap<>();

        if(user!=null){
            if(user.getEmail()!=null && !user.getEmail().equals("")){
                // 给user.email发邮箱验证
                sendVerifyToken(user, request);
                request.getSession().setAttribute("user", user);
                res.put("stateCode", "2");
                res.put("msg", "验证码已发送！");
            }
            else{
                res.put("stateCode", "1");
                res.put("msg", "用户没有填写邮箱！");
            }
        }
        else{
            res.put("stateCode", "0");
            res.put("msg", "用户不存在！");
        }


        return res;
    }

    @RequestMapping("/find_password_verifytoken.html")
    public ModelAndView find_password_verifytoken(HttpServletRequest request){

        ModelAndView modelAndView = new ModelAndView("find_password_verifytoken");
        return modelAndView;
    }

    @RequestMapping(value = "/api/token_check", method = RequestMethod.POST)
    public @ResponseBody
    Object token_check(HttpServletRequest request){
        String code = request.getParameter("code");
        String token = (String) request.getSession().getAttribute("token");
        HashMap<String, String> res = new HashMap<>();
        if( code.equals(token) ){
            res.put("stateCode", "1");
            res.put("msg", "验证码正确！");
        }
        else{
            res.put("stateCode", "0");
            res.put("msg", "验证码错误！");
        }

        return res;
    }

    @RequestMapping("/find_password_newpassword")
    public ModelAndView find_password_newpassword(){
        ModelAndView modelAndView = new ModelAndView("find_password_newpassword");
        return modelAndView;
    }

    @RequestMapping(value = "/api/password_new", method = RequestMethod.POST)
    public @ResponseBody
    Object password_new(HttpServletRequest request){
        String password = request.getParameter("newPasswd");
        HashMap<String, String> res = new HashMap<>();

        User user = (User) request.getSession().getAttribute("user");
        user.setPassword(password);
        boolean isup = profileService.updatePassword(user);
        if(isup){
            request.getSession().setAttribute("user", user);
            res.put("stateCode", "1");
            res.put("msg", "更新成功！");
        }else {
            res.put("stateCode", "0");
            res.put("msg", "更新失败！");
        }

        return res;
    }

    @RequestMapping(value = "/api/registerCheck", method = RequestMethod.POST)
    public @ResponseBody
    Object registerCheck(HttpServletRequest request){
        String user_name = request.getParameter("user_name");
        String passwd = request.getParameter("newPasswd");

        HashMap<String, String> res = new HashMap<>();

        User isExist = loginService.selectUserByUserName2(user_name);
        if( isExist==null ){
            User user = new User();
            user.setUser_name(user_name);
            user.setPassword(passwd);
            user.setUser_state("usable");
            user.setIsregister("yes");
            user.setCreated_time(new Date());
            user.setLastlogin_time(new Date());

            character cha = new character();
            cha.setCharacter_name("reader");
            cha.setCreated_time(user.getCreated_time());
            cha.setCharacter_state("unlimited");

            boolean isUserAdd = profileService.addUser(user);
            boolean isChaAdd = profileService.addCharacter(cha);
            if( isUserAdd && isChaAdd ){
                UserCharacter uc = new UserCharacter();
                uc.setUser_id(user.getUser_id());
                uc.setCharacter_id(cha.getCharacter_id());
                profileService.addUC(uc);

                request.getSession().setAttribute("user", user);
                request.getSession().setAttribute("userCharacter", uc);
                request.getSession().setAttribute("cha", cha);

                res.put("stateCode", "2");
                res.put("msg", "注册成功！");
            }
            else{
                res.put("stateCode", "1");
                res.put("msg", "注册失败！");
            }

        }
        else{
            res.put("stateCode", "0");
            res.put("msg", "用户已存在！");
        }



        return res;
    }

    @RequestMapping("/admin_user_update_do.html")
    public String admin_user_update_do(@ModelAttribute User user, HttpServletRequest request, RedirectAttributes redirectAttributes){

        User old = (User)request.getSession().getAttribute("taruser");
        user.setPassword(old.getPassword());
        user.setIsregister(old.getIsregister());
        user.setLastlogin_time(old.getLastlogin_time());
        user.setCreated_time(old.getCreated_time());
        boolean isUpUser = profileService.updateUser(user);


        character cha = (character) request.getSession().getAttribute("tarcha");
        cha.setCharacter_name(request.getParameter("cha_name"));
        boolean isUpCha = profileService.updateCharacter(cha);
        if(isUpUser&&isUpCha){
            redirectAttributes.addFlashAttribute("updateSuccess", "更新成功！");
        }
        else{
            redirectAttributes.addFlashAttribute("updateError", "更新失败！");
        }

        return "redirect:/admin_all_user.html";
    }

    @RequestMapping("/staff_add_do.html")
    public String staffAddDo(HttpServletRequest request, RedirectAttributes redirectAttributes){
        User user = new User();
        user.setWork_num(request.getParameter("work_num"));
        String workNum = request.getParameter("work_num");
        String lastFourDigits = workNum.substring(workNum.length() - 4);
        user.setUser_name(workNum);
        user.setPassword(lastFourDigits);
        user.setWork_name(request.getParameter("work_name"));
        user.setIsregister("no");
        user.setUser_realname(request.getParameter("user_realname"));
        user.setSex(request.getParameter("sex"));
        user.setAddress(request.getParameter("address"));
        user.setPhone(request.getParameter("phone"));
        user.setEmail(request.getParameter("email"));
        user.setUser_state("usable");
        user.setCreated_time(new Date());


        User isExist = loginService.selectUserByUserName2(user.getUser_name());
        if( isExist==null ){
            character cha = new character();
            cha.setCharacter_name("staff");
            cha.setCreated_time(user.getCreated_time());
            cha.setCharacter_state("unlimited");

            boolean isUserAdd = profileService.addUser(user);
            boolean isChaAdd = profileService.addCharacter(cha);
            if( isUserAdd && isChaAdd ){
                UserCharacter uc = new UserCharacter();
                uc.setUser_id(user.getUser_id());
                uc.setCharacter_id(cha.getCharacter_id());
                profileService.addUC(uc);
                redirectAttributes.addFlashAttribute("sucStaff", "添加成功！");
            }
            else {
                redirectAttributes.addFlashAttribute("errStaff", "添加失败！");
            }
        }
        else{
            redirectAttributes.addFlashAttribute("errStaff", "存在重复用户，添加失败！");
        }
        return "redirect:/admin_add_staff.html";
    }

    @RequestMapping("/admin_all_user_deleteone.html")
    public String admin_all_user_deleteone(HttpServletRequest request, RedirectAttributes redirectAttributes){
        User user = loginService.selectUserByUserName2(request.getParameter("user_name"));
        user.setUser_state("unusable");
        boolean isDelete = profileService.updateUser(user);
        if( isDelete ){
            redirectAttributes.addFlashAttribute("updateSuccess", "删除成功！");
        }
        else{
            redirectAttributes.addFlashAttribute("updateError", "删除失败！");
        }

        return "redirect:/admin_all_user.html";
    }

    @RequestMapping("queryuser.html")
    public ModelAndView queryuser(String searchWord){
        ArrayList<User> users = profileService.queryUser(searchWord);

        ModelAndView modelAndView = new ModelAndView("admin_all_user_queryuser");
        modelAndView.addObject("users", users);
        return modelAndView;
    }

}
