package com.library.controller;


import com.library.bean.Work;
import com.library.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Map;

@Controller
public class WorkController {
    private WorkService workService;

    @Autowired
    public void setWorkService(WorkService workService){
        this.workService = workService;
    }

    @RequestMapping("/admin_all_work.html")
    public ModelAndView admin_all_work(HttpServletRequest request){
        ArrayList<Work> works = workService.selectAllWork();

        ModelAndView modelAndView = new ModelAndView("admin_all_work");
        modelAndView.addObject("works", works);
        return modelAndView;
    }

    @RequestMapping("/admin_all_work_update.html")
    public ModelAndView admin_all_work_update(HttpServletRequest request){
        String work_name = request.getParameter("work_name");
        Work work = workService.selectByWorkName(work_name);

        ModelAndView modelAndView = new ModelAndView("admin_all_work_update");
        modelAndView.addObject("thiswork", work);
        request.getSession().setAttribute("tarwork", work);
        return modelAndView;
    }

    @RequestMapping("/admin_add_work")
    public ModelAndView admin_add_work(){
        ModelAndView modelAndView = new ModelAndView("admin_add_work");
        return modelAndView;
    }

    @RequestMapping("/admin_all_work_update_do.html")
    public String admin_all_work_update_do(@ModelAttribute Work work, HttpServletRequest request, RedirectAttributes redirectAttributes){
        Work old = (Work)request.getSession().getAttribute("tarwork");
        work.setWork_id(old.getWork_id());
        boolean isUp = workService.updateWork(work);
        if( isUp ){
            redirectAttributes.addFlashAttribute("updateSuccess", "更新成功！");
        }
        else{
            redirectAttributes.addFlashAttribute("updateError", "更新失败！");
        }
        return "redirect:/admin_all_work.html";
    }

    @RequestMapping("/admin_all_work_deleteone.html")
    public String admin_all_work_deleteone(HttpServletRequest request, RedirectAttributes redirectAttributes){
        boolean isDel = workService.deleteWorkByName(request.getParameter("work_name"));
        if( isDel ){
            redirectAttributes.addFlashAttribute("updateSuccess", "删除成功！");
        }
        else{
            redirectAttributes.addFlashAttribute("updateError", "删除失败！");
        }

        return "redirect:/admin_all_work.html";
    }

    @RequestMapping("/work_add_do.html")
    public String work_add_do(@ModelAttribute Work work, HttpServletRequest request, RedirectAttributes redirectAttributes){
        boolean isIn = workService.insertWork(work);
        if( isIn ){
            redirectAttributes.addFlashAttribute("updateSuccess", "添加成功！");
        }
        else{
            redirectAttributes.addFlashAttribute("updateError", "添加失败！");
        }

        return "redirect:/admin_all_work.html";
    }

    @RequestMapping("/querywork.html")
    public ModelAndView queryWork(String searchWord){
        ArrayList<Work> works = workService.queryWork(searchWord);

        ModelAndView modelAndView = new ModelAndView("admin_all_work_querywork");
        modelAndView.addObject("works", works);
        return modelAndView;
    }


}
