package com.library.controller;


import com.library.bean.Book;
import com.library.bean.BookCirculate;
import com.library.bean.User;
import com.library.service.BookService;
import com.library.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

@Controller
public class CirculateController {
    @Autowired
    private BookService bookService;
    @Autowired
    private LoginService loginService;

    @RequestMapping("/staff_cirin_apply.html")
    public ModelAndView staff_cirin_apply(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        ArrayList<Book> books = bookService.selectAllBook();
        ArrayList<BookCirculate> bookCirculates = new ArrayList<>();
        Iterator<Book> iterator = books.iterator();
        while (iterator.hasNext()) {
            Book book = iterator.next();
            BookCirculate bookCirculate = bookService.selectBookCirculateById(book.getBook_id());
            if (book.getBelongto().equals(user.getWork_name()) || (bookCirculate!=null && !bookCirculate.getCir_state().equals("free"))) { // 看别的公司的图书
                iterator.remove(); // 使用迭代器的 remove 方法
            }
            else if( book.getBook_state().equals("private")){
                iterator.remove(); // 使用迭代器的 remove 方法
            }
            else{
                if(bookCirculate==null){
                   bookCirculate = new BookCirculate();
                    bookCirculate.setCir_state("free");
                }
                bookCirculates.add(bookCirculate);
            }
        }

        ModelAndView modelAndView = new ModelAndView("staff_cirin_apply");
        modelAndView.addObject("books", books);
        modelAndView.addObject("bookCirculates", bookCirculates);
        return modelAndView;
    }

    @RequestMapping("/staff_cirin_apply_do.html")
    public ModelAndView staff_cirin_apply_do(HttpServletRequest request){
        int book_id = Integer.parseInt(request.getParameter("book_id"));
        Book book = bookService.selectBookById(book_id);

        ModelAndView modelAndView = new ModelAndView("staff_cirin_apply_do");
        modelAndView.addObject("thisbook", book);
        return modelAndView;
    }

    @RequestMapping("/staff_cirin_apply_submit.html")
    public String staff_cirin_apply_submit(HttpServletRequest request, RedirectAttributes redirectAttributes){
        int book_id = Integer.parseInt(request.getParameter("book_id"));
        BookCirculate bookCirculate = bookService.selectBookCirculateById(book_id);
        if( bookCirculate!=null ){
            bookService.deleteBookCirculateById(book_id);
        }
        bookCirculate = new BookCirculate();
        bookCirculate.setBook_id(book_id);
        User user = (User) request.getSession().getAttribute("user");
        int user_id = user.getUser_id();
        bookCirculate.setUser_id(user_id);
        bookCirculate.setCir_reason(request.getParameter("cir_reason"));
        bookCirculate.setCir_time(new Date());
        bookCirculate.setCir_state("waitCirInCheck");
        bookCirculate.setComment("");

        boolean isin = bookService.insertBookCirculate(bookCirculate);
        if( isin ){
            redirectAttributes.addFlashAttribute("updateSuccess", "申请成功！等待工作人员审核。");
        }
        else{
            redirectAttributes.addFlashAttribute("updateError", "申请失败！");
        }

        return "redirect:/staff_cirin_apply.html";
    }

    @RequestMapping("/staff_cirin_apply_waiting")
    public ModelAndView staff_cirin_apply_waiting(HttpServletRequest request){
        ArrayList<BookCirculate> bookCirculates = bookService.selectAllBookCirculate();
        ArrayList<Book> books = new ArrayList<>();

        User user = (User) request.getSession().getAttribute("user");
        Iterator<BookCirculate> iterator = bookCirculates.iterator();
        while (iterator.hasNext()){
            BookCirculate bookCirculate = iterator.next();

            if( !bookCirculate.getCir_state().equals("waitCirInCheck") || bookCirculate.getUser_id()!=user.getUser_id() ){
                iterator.remove();
            }
            else{
                Book book = bookService.selectBookById(bookCirculate.getBook_id());
                books.add(book);
            }
        }

        ModelAndView modelAndView =  new ModelAndView("staff_cirin_apply_waiting");
        modelAndView.addObject("books", books);
        modelAndView.addObject("bookCirculates", bookCirculates);
        return modelAndView;
    }

    @RequestMapping("/staff_cirout_check.html")
    public ModelAndView staff_cirout_check(HttpServletRequest request){
        ArrayList<BookCirculate> bookCirculates = bookService.selectAllBookCirculate();
        ArrayList<Book> books = new ArrayList<>();
        ArrayList<User> users = new ArrayList<>();

        User user = (User) request.getSession().getAttribute("user");
        Iterator<BookCirculate> iterator = bookCirculates.iterator();
        while (iterator.hasNext()){
            BookCirculate bookCirculate = iterator.next();
            Book book = bookService.selectBookById(bookCirculate.getBook_id());
            if( !user.getWork_name().equals(book.getBelongto()) ){
                iterator.remove();
            }
            else if( !bookCirculate.getCir_state().equals("waitCirInCheck") ){
                iterator.remove();
            }
            else{
                books.add(book);
                users.add(loginService.selectUserById(bookCirculate.getUser_id()));
            }
        }


        ModelAndView modelAndView = new ModelAndView("staff_cirout_check");
        modelAndView.addObject("books", books);
        modelAndView.addObject("bookCirculates", bookCirculates);
        modelAndView.addObject("users", users);
        return modelAndView;
    }

    @RequestMapping("/staff_cirout_check_agree.html")
    public String staff_cirout_check_agree(HttpServletRequest request,RedirectAttributes redirectAttributes){
        int book_id = Integer.parseInt(request.getParameter("book_id"));
        BookCirculate bookCirculate = bookService.selectBookCirculateById(book_id);
        bookCirculate.setCir_state("ciring");
        boolean isup = bookService.updateBookCirculate(bookCirculate);
        if( isup ){
            redirectAttributes.addFlashAttribute("updateSuccess", "已通过！");
        }
        else{
            redirectAttributes.addFlashAttribute("updateError", "出错了！");
        }
        return "redirect:/staff_cirout_check.html";
    }

    @RequestMapping("/staff_cirout_check_disagree.html")
    public String staff_cirout_check_disagree(HttpServletRequest request, RedirectAttributes redirectAttributes){
        int book_id = Integer.parseInt(request.getParameter("book_id"));
        BookCirculate bookCirculate = bookService.selectBookCirculateById(book_id);
        bookCirculate.setCir_state("free");
        bookCirculate.setComment("finished");
        boolean isup = bookService.updateBookCirculate(bookCirculate);
        if( isup ){
            redirectAttributes.addFlashAttribute("updateSuccess", "已拒绝！");
        }
        else{
            redirectAttributes.addFlashAttribute("updateError", "出错了！");
        }

        return "redirect:/staff_cirout_check.html";
    }

    @RequestMapping("/staff_cirout_check_ciring.html")
    public ModelAndView staff_cirout_check_ciring(HttpServletRequest request){
        ArrayList<BookCirculate> bookCirculates = bookService.selectAllBookCirculate();
        ArrayList<Book> books = new ArrayList<>();
        ArrayList<User> users = new ArrayList<>();

        User user = (User) request.getSession().getAttribute("user");
        Iterator<BookCirculate> iterator = bookCirculates.iterator();
        while (iterator.hasNext()){
            BookCirculate bookCirculate = iterator.next();
            Book book = bookService.selectBookById(bookCirculate.getBook_id());
            if( !user.getWork_name().equals(book.getBelongto()) ){
                iterator.remove();
            }
            else if( !bookCirculate.getCir_state().equals("ciring") ){
                iterator.remove();
            }
            else{
                books.add(book);
                users.add(loginService.selectUserById(bookCirculate.getUser_id()));
            }
        }


        ModelAndView modelAndView = new ModelAndView("staff_cirout_check_ciring");
        modelAndView.addObject("books", books);
        modelAndView.addObject("bookCirculates", bookCirculates);
        modelAndView.addObject("users", users);
        return modelAndView;
    }

    @RequestMapping("/staff_cirin_return.html")
    public ModelAndView staff_cirin_return(HttpServletRequest request){
        ArrayList<BookCirculate> bookCirculates = bookService.selectAllBookCirculate();
        ArrayList<Book> books = new ArrayList<>();

        User user = (User) request.getSession().getAttribute("user");
        Iterator<BookCirculate> iterator = bookCirculates.iterator();
        while (iterator.hasNext()){
            BookCirculate bookCirculate = iterator.next();

            if( !bookCirculate.getCir_state().equals("ciring") || bookCirculate.getUser_id()!=user.getUser_id() ){
                iterator.remove();
            }
            else{
                Book book = bookService.selectBookById(bookCirculate.getBook_id());
                books.add(book);
            }
        }

        ModelAndView modelAndView = new ModelAndView("staff_cirin_return");
        modelAndView.addObject("books", books);
        modelAndView.addObject("bookCirculates", bookCirculates);
        return modelAndView;
    }

    @RequestMapping("/staff_cirin_return_do.html")
    public String staff_cirin_return_do(HttpServletRequest request, RedirectAttributes redirectAttributes){
        int book_id = Integer.parseInt(request.getParameter("book_id"));
        BookCirculate bookCirculate = bookService.selectBookCirculateById(book_id);
        bookCirculate.setCir_state("waitReturnCheck");
        bookCirculate.setReturn_time(new Date());
        boolean isup = bookService.updateBookCirculate(bookCirculate);
        if( isup ){
            redirectAttributes.addFlashAttribute("updateSuccess", "申请成功！");
        }
        else{
            redirectAttributes.addFlashAttribute("updateError", "申请失败！");
        }


        return "redirect:/staff_cirin_return.html";
    }

    @RequestMapping("/staff_cirin_return_waiting.html")
    public ModelAndView staff_cirin_return_waiting(HttpServletRequest request){
        ArrayList<BookCirculate> bookCirculates = bookService.selectAllBookCirculate();
        ArrayList<Book> books = new ArrayList<>();

        User user = (User) request.getSession().getAttribute("user");
        Iterator<BookCirculate> iterator = bookCirculates.iterator();
        while (iterator.hasNext()){
            BookCirculate bookCirculate = iterator.next();

            if( !bookCirculate.getCir_state().equals("waitReturnCheck") || bookCirculate.getUser_id()!=user.getUser_id() ){
                iterator.remove();
            }
            else{
                Book book = bookService.selectBookById(bookCirculate.getBook_id());
                books.add(book);
            }
        }

        ModelAndView modelAndView =  new ModelAndView("staff_cirin_return_waiting");
        modelAndView.addObject("books", books);
        modelAndView.addObject("bookCirculates", bookCirculates);
        return modelAndView;
    }

    @RequestMapping("/staff_cirin_return_finished.html")
    public ModelAndView staff_cirin_return_finished(HttpServletRequest request){
        ArrayList<BookCirculate> bookCirculates = bookService.selectAllBookCirculate();
        ArrayList<Book> books = new ArrayList<>();

        User user = (User) request.getSession().getAttribute("user");
        Iterator<BookCirculate> iterator = bookCirculates.iterator();
        while (iterator.hasNext()){
            BookCirculate bookCirculate = iterator.next();

            if( !bookCirculate.getComment().equals("finished") || bookCirculate.getUser_id()!=user.getUser_id() ){
                iterator.remove();
            }
            else{
                Book book = bookService.selectBookById(bookCirculate.getBook_id());
                books.add(book);
            }
        }

        ModelAndView modelAndView =  new ModelAndView("staff_cirin_return_finished");
        modelAndView.addObject("books", books);
        modelAndView.addObject("bookCirculates", bookCirculates);
        return modelAndView;
    }

    @RequestMapping("/staff_cirout_return_check.html")
    public ModelAndView staff_cirout_return_check(HttpServletRequest request){
        ArrayList<BookCirculate> bookCirculates = bookService.selectAllBookCirculate();
        ArrayList<Book> books = new ArrayList<>();
        ArrayList<User> users = new ArrayList<>();

        User user = (User) request.getSession().getAttribute("user");
        Iterator<BookCirculate> iterator = bookCirculates.iterator();
        while (iterator.hasNext()){
            BookCirculate bookCirculate = iterator.next();
            Book book = bookService.selectBookById(bookCirculate.getBook_id());
            if( !user.getWork_name().equals(book.getBelongto()) ){
                iterator.remove();
            }
            else if( !bookCirculate.getCir_state().equals("waitReturnCheck") ){
                iterator.remove();
            }
            else{
                books.add(book);
                users.add(loginService.selectUserById(bookCirculate.getUser_id()));
            }
        }

        ModelAndView modelAndView =  new ModelAndView("staff_cirout_return_check");
        modelAndView.addObject("books", books);
        modelAndView.addObject("bookCirculates", bookCirculates);
        modelAndView.addObject("users", users);
        return modelAndView;
    }


    @RequestMapping("/staff_cirout_return_check_agree.html")
    public String staff_cirout_return_check_agree(HttpServletRequest request, RedirectAttributes redirectAttributes){
        int book_id = Integer.parseInt(request.getParameter("book_id"));
        BookCirculate bookCirculate = bookService.selectBookCirculateById(book_id);
        bookCirculate.setCir_state("free");
        bookCirculate.setComment("finished");
        boolean isup = bookService.updateBookCirculate(bookCirculate);
        if( isup ){
            redirectAttributes.addFlashAttribute("updateSuccess", "已通过！");
        }
        else{
            redirectAttributes.addFlashAttribute("updateError", "出错了！");
        }


        return "redirect:/staff_cirout_return_check.html";
    }

    @RequestMapping("/staff_cirout_return_check_disagree.html")
    public String staff_cirout_return_check_disagree(HttpServletRequest request, RedirectAttributes redirectAttributes){
        int book_id = Integer.parseInt(request.getParameter("book_id"));
        BookCirculate bookCirculate = bookService.selectBookCirculateById(book_id);
        bookCirculate.setComment("finished");
        boolean isup = bookService.updateBookCirculate(bookCirculate);
        if( isup ){
            redirectAttributes.addFlashAttribute("updateSuccess", "已拒绝！");
        }
        else{
            redirectAttributes.addFlashAttribute("updateError", "出错了！");
        }

        return "redirect:/staff_cirout_return_check.html";
    }

    @RequestMapping("/staff_cirout_return_check_finished.html")
    public ModelAndView staff_cirout_return_check_finished(HttpServletRequest request){
        ArrayList<BookCirculate> bookCirculates = bookService.selectAllBookCirculate();
        ArrayList<Book> books = new ArrayList<>();
        ArrayList<User> users = new ArrayList<>();

        User user = (User) request.getSession().getAttribute("user");
        Iterator<BookCirculate> iterator = bookCirculates.iterator();
        while (iterator.hasNext()){
            BookCirculate bookCirculate = iterator.next();
            Book book = bookService.selectBookById(bookCirculate.getBook_id());
            if( !bookCirculate.getComment().equals("finished") || !user.getWork_name().equals(book.getBelongto()) ){
                iterator.remove();
            }
            else{
                books.add(book);
                users.add(loginService.selectUserById(bookCirculate.getUser_id()));
            }
        }

        ModelAndView modelAndView =  new ModelAndView("staff_cirout_return_check_finished");
        modelAndView.addObject("books", books);
        modelAndView.addObject("bookCirculates", bookCirculates);
        modelAndView.addObject("users", users);
        return modelAndView;
    }

}
