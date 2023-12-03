package com.library.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.bean.Book;
import com.library.bean.BookBorrow;
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
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class BorrowController {
    @Autowired
    private BookService bookService;
    @Autowired
    private LoginService loginService;

    @RequestMapping("/reader_book_borrow.html")
    public ModelAndView reader_book_borrow(){
        ArrayList<Book> books = bookService.selectAllBook();
        ArrayList<BookBorrow> bookBorrows = new ArrayList<>();
        Iterator<Book> iterator = books.iterator();
        while (iterator.hasNext()) {
            Book book = iterator.next();
            if ( book.getBook_state().equals("private")) {
                iterator.remove(); // 使用迭代器的 remove 方法
            }
            else{
                BookBorrow bookBorrow = bookService.selectBookBorrowById(book.getBook_id());
                if( bookBorrow==null ){
                    bookBorrow = new BookBorrow();
                    bookBorrow.setIsbrwed("no");
                }
                bookBorrows.add(bookBorrow);
            }
        }

        ModelAndView modelAndView = new ModelAndView("reader_book_borrow");
        modelAndView.addObject("books", books);
        modelAndView.addObject("bookBorrows", bookBorrows);
        return modelAndView;
    }

    @RequestMapping("/reader_book_borrow_querybook.html")
    public ModelAndView reader_book_borrow_querybook(String searchWord){
        ArrayList<Book> books = bookService.queryBook(searchWord);

        ArrayList<BookBorrow> bookBorrows = new ArrayList<>();
        Iterator<Book> iterator = books.iterator();
        while (iterator.hasNext()) {
            Book book = iterator.next();
            if ( book.getBook_state().equals("private")) {
                iterator.remove(); // 使用迭代器的 remove 方法
            }
            else{
                BookBorrow bookBorrow = bookService.selectBookBorrowById(book.getBook_id());
                if( bookBorrow==null ){
                    bookBorrow = new BookBorrow();
                    bookBorrow.setIsbrwed("no");
                }
                bookBorrows.add(bookBorrow);
            }
        }

        ModelAndView modelAndView = new ModelAndView("reader_book_borrow_querybook");
        modelAndView.addObject("books", books);
        modelAndView.addObject("bookBorrows", bookBorrows);
        return modelAndView;
    }

    @RequestMapping("/reader_book_borrow_do.html")
    public ModelAndView reader_book_borrow_do(HttpServletRequest request){
        int book_id = Integer.parseInt(request.getParameter("book_id"));
        Book book = bookService.selectBookById(book_id);

        ModelAndView modelAndView = new ModelAndView("reader_book_borrow_do");
        modelAndView.addObject("thisbook", book);
        return modelAndView;
    }

    @RequestMapping("/reader_book_borrow_submit.html")
    public String reader_book_borrow_submit(HttpServletRequest request, RedirectAttributes redirectAttributes){
        int book_id = Integer.parseInt(request.getParameter("book_id"));

        BookBorrow bookBorrow = bookService.selectBookBorrowById(book_id);
        if( bookBorrow!=null ){
            bookService.deleteBookBorrowById(book_id);
        }
        bookBorrow = new BookBorrow();
        bookBorrow.setBook_id(book_id);
        User user = (User) request.getSession().getAttribute("user");
        int user_id = user.getUser_id();
        bookBorrow.setUser_id(user_id);
        bookBorrow.setIsbrwed("yes");
        bookBorrow.setBrw_time(new Date());
        bookBorrow.setBrw_reason(request.getParameter("brw_reason"));
        bookBorrow.setComment("waitingCheck");

        boolean isIn = bookService.insertBookBorrow(bookBorrow);

        if( isIn ){
            redirectAttributes.addFlashAttribute("updateSuccess", "申请成功！等待工作人员审核。");
        }
        else{
            redirectAttributes.addFlashAttribute("updateError", "申请失败！");
        }

        return "redirect:/reader_book_borrow.html";
    }

    @RequestMapping("/staff_book_borrow_check.html")
    public ModelAndView staff_book_borrow_check(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        String work_name = user.getWork_name();
        ArrayList<BookBorrow> bookBorrows = bookService.selectAllBookBorrow();
        ArrayList<Book> books = new ArrayList<>();
        ArrayList<User> users = new ArrayList<>();
        Iterator<BookBorrow> iterator = bookBorrows.iterator();
        while (iterator.hasNext()){
            BookBorrow bookBorrow = iterator.next();
            int book_id = bookBorrow.getBook_id();
            Book book = bookService.selectBookById(book_id);
            if( book.getBelongto().equals(work_name) && (bookBorrow.getComment()!=null && bookBorrow.getComment().equals("waitingCheck")) ){
                users.add(loginService.selectUserById(bookBorrow.getUser_id()));
                books.add(book);
            }
            else{
                iterator.remove(); // 使用迭代器的 remove 方法
            }
        }

        ModelAndView modelAndView = new ModelAndView("staff_book_borrow_check");
        modelAndView.addObject("books", books);
        modelAndView.addObject("bookBorrows", bookBorrows);
        modelAndView.addObject("users", users);
        return modelAndView;
    }

    @RequestMapping("/staff_book_borrow_check_ing.html")
    public ModelAndView staff_book_borrow_check_ing(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        String work_name = user.getWork_name();
        ArrayList<BookBorrow> bookBorrows = bookService.selectAllBookBorrow();
        ArrayList<Book> books = new ArrayList<>();
        ArrayList<User> users = new ArrayList<>();
        Iterator<BookBorrow> iterator = bookBorrows.iterator();
        while (iterator.hasNext()){
            BookBorrow bookBorrow = iterator.next();
            int book_id = bookBorrow.getBook_id();
            Book book = bookService.selectBookById(book_id);
            if( book.getBelongto().equals(work_name) && (bookBorrow.getComment()!=null && bookBorrow.getComment().equals("") && bookBorrow.getIsbrwed().equals("yes")) ){
                users.add(loginService.selectUserById(bookBorrow.getUser_id()));
                books.add(book);
            }
            else{
                iterator.remove(); // 使用迭代器的 remove 方法
            }
        }

        ModelAndView modelAndView = new ModelAndView("staff_book_borrow_check_ing");
        modelAndView.addObject("books", books);
        modelAndView.addObject("bookBorrows", bookBorrows);
        modelAndView.addObject("users", users);
        return modelAndView;
    }

    @RequestMapping("/staff_book_borrow_check_agree.html")
    public String staff_book_borrow_check_agree(HttpServletRequest request, RedirectAttributes redirectAttributes){
        int book_id = Integer.parseInt(request.getParameter("book_id"));
        BookBorrow bookBorrow = bookService.selectBookBorrowById(book_id);
        bookBorrow.setComment("");
        boolean isup = bookService.updateBookBorrow(bookBorrow);
        if( isup ){
            redirectAttributes.addFlashAttribute("updateSuccess", "已通过！");
        }
        else{
            redirectAttributes.addFlashAttribute("updateError", "出错了！");
        }
        return "redirect:/staff_book_borrow_check.html";
    }

    @RequestMapping("/staff_book_borrow_check_disagree.html")
    public String staff_book_borrow_check_disagree(HttpServletRequest request, RedirectAttributes redirectAttributes){
        int book_id = Integer.parseInt(request.getParameter("book_id"));
        BookBorrow bookBorrow = bookService.selectBookBorrowById(book_id);
        bookBorrow.setIsbrwed("no");
        bookBorrow.setComment("finished");
        boolean isup = bookService.updateBookBorrow(bookBorrow);
        if( isup ){
            redirectAttributes.addFlashAttribute("updateSuccess", "已拒绝！");
        }
        else{
            redirectAttributes.addFlashAttribute("updateError", "出错了！");
        }
        return "redirect:/staff_book_borrow_check.html";
    }

    @RequestMapping("/reader_book_return.html")
    public ModelAndView reader_book_return(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");

        ArrayList<BookBorrow> bookBorrows = bookService.selectAllBookBorrow();
        ArrayList<Book> books = new ArrayList<>();
        Iterator<BookBorrow> iterator = bookBorrows.iterator();
        while (iterator.hasNext()){
            BookBorrow bookBorrow = iterator.next();
            if( bookBorrow.getUser_id()!=user.getUser_id() || bookBorrow.getIsbrwed().equals("no") ){
                iterator.remove();
            }
            else if( !bookBorrow.getComment().equals("") ){
                iterator.remove();
            }
            else{
                Book book = bookService.selectBookById(bookBorrow.getBook_id());
                books.add(book);
            }
        }

        ModelAndView modelAndView = new ModelAndView("reader_book_return");
        modelAndView.addObject("books", books);
        modelAndView.addObject("bookBorrows", bookBorrows);
        return modelAndView;
    }

    @RequestMapping("/reader_book_borrow_waiting.html")
    public ModelAndView reader_book_borrow_waiting(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");

        ArrayList<BookBorrow> bookBorrows = bookService.selectAllBookBorrow();
        ArrayList<Book> books = new ArrayList<>();
        Iterator<BookBorrow> iterator = bookBorrows.iterator();
        while (iterator.hasNext()){
            BookBorrow bookBorrow = iterator.next();
            if( bookBorrow.getUser_id()!=user.getUser_id() || bookBorrow.getIsbrwed().equals("no") ){
                iterator.remove();
            }
            else if( !bookBorrow.getComment().equals("waitingCheck")){
                iterator.remove();
            }
            else{
                Book book = bookService.selectBookById(bookBorrow.getBook_id());
                books.add(book);
            }
        }

        ModelAndView modelAndView = new ModelAndView("reader_book_borrow_waiting");
        modelAndView.addObject("books", books);
        modelAndView.addObject("bookBorrows", bookBorrows);
        return modelAndView;
    }

    @RequestMapping("/reader_book_return_waiting.html")
    public ModelAndView reader_book_return_waiting(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");

        ArrayList<BookBorrow> bookBorrows = bookService.selectAllBookBorrow();
        ArrayList<Book> books = new ArrayList<>();
        Iterator<BookBorrow> iterator = bookBorrows.iterator();
        while (iterator.hasNext()){
            BookBorrow bookBorrow = iterator.next();
            if( bookBorrow.getUser_id()!=user.getUser_id() || bookBorrow.getIsbrwed().equals("no") ){
                iterator.remove();
            }
            else if( !bookBorrow.getComment().equals("waitingReturnCheck")){
                iterator.remove();
            }
            else{
                Book book = bookService.selectBookById(bookBorrow.getBook_id());
                books.add(book);
            }
        }

        ModelAndView modelAndView = new ModelAndView("reader_book_return_waiting");
        modelAndView.addObject("books", books);
        modelAndView.addObject("bookBorrows", bookBorrows);
        return modelAndView;
    }

    @RequestMapping("/reader_book_return_finished")
    public ModelAndView reader_book_return_finished(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");

        ArrayList<BookBorrow> bookBorrows = bookService.selectAllBookBorrow();
        ArrayList<Book> books = new ArrayList<>();
        Iterator<BookBorrow> iterator = bookBorrows.iterator();
        while (iterator.hasNext()){
            BookBorrow bookBorrow = iterator.next();
            if( bookBorrow.getUser_id()!=user.getUser_id() ){
                iterator.remove();
            }
            else if( !bookBorrow.getComment().equals("finished")){
                iterator.remove();
            }
            else{
                Book book = bookService.selectBookById(bookBorrow.getBook_id());
                books.add(book);
            }
        }

        ModelAndView modelAndView = new ModelAndView("reader_book_return_finished");
        modelAndView.addObject("books", books);
        modelAndView.addObject("bookBorrows", bookBorrows);
        return modelAndView;
    }



    @RequestMapping("/reader_book_return_do.html")
    public String reader_book_return_do(HttpServletRequest request, RedirectAttributes redirectAttributes){
        int book_id = Integer.parseInt(request.getParameter("book_id"));
        BookBorrow bookBorrow = bookService.selectBookBorrowById(book_id);
        bookBorrow.setComment("waitingReturnCheck");
        bookBorrow.setReturn_time(new Date());
        boolean isup = bookService.updateBookBorrow(bookBorrow);

        if( isup ){
            redirectAttributes.addFlashAttribute("updateSuccess", "申请成功！等待工作人员审核");
        }
        else{
            redirectAttributes.addFlashAttribute("updateError", "申请失败！");
        }
        return "redirect:/reader_book_return.html";
    }

    @RequestMapping("/staff_book_return_check.html")
    public ModelAndView staff_book_return_check(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        String work_name = user.getWork_name();
        ArrayList<BookBorrow> bookBorrows = bookService.selectAllBookBorrow();
        ArrayList<Book> books = new ArrayList<>();
        ArrayList<User> users = new ArrayList<>();
        Iterator<BookBorrow> iterator = bookBorrows.iterator();
        while (iterator.hasNext()){
            BookBorrow bookBorrow = iterator.next();
            int book_id = bookBorrow.getBook_id();
            Book book = bookService.selectBookById(book_id);
            if( book.getBelongto().equals(work_name) && (bookBorrow.getComment()!=null && bookBorrow.getComment().equals("waitingReturnCheck")) ){
                users.add(loginService.selectUserById(bookBorrow.getUser_id()));
                books.add(book);
            }
            else{
                iterator.remove(); // 使用迭代器的 remove 方法
            }
        }

        ModelAndView modelAndView = new ModelAndView("staff_book_return_check");
        modelAndView.addObject("books", books);
        modelAndView.addObject("bookBorrows", bookBorrows);
        modelAndView.addObject("users", users);
        return modelAndView;
    }

    @RequestMapping("/staff_book_return_finished.html")
    public ModelAndView staff_book_return_finished(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        String work_name = user.getWork_name();
        ArrayList<BookBorrow> bookBorrows = bookService.selectAllBookBorrow();
        ArrayList<Book> books = new ArrayList<>();
        ArrayList<User> users = new ArrayList<>();
        Iterator<BookBorrow> iterator = bookBorrows.iterator();
        while (iterator.hasNext()){
            BookBorrow bookBorrow = iterator.next();
            int book_id = bookBorrow.getBook_id();
            Book book = bookService.selectBookById(book_id);
            if( book.getBelongto().equals(work_name) && (bookBorrow.getComment()!=null && bookBorrow.getComment().equals("finished")) ){
                users.add(loginService.selectUserById(bookBorrow.getUser_id()));
                books.add(book);
            }
            else{
                iterator.remove(); // 使用迭代器的 remove 方法
            }
        }

        ModelAndView modelAndView = new ModelAndView("staff_book_return_finished");
        modelAndView.addObject("books", books);
        modelAndView.addObject("bookBorrows", bookBorrows);
        modelAndView.addObject("users", users);
        return modelAndView;
    }

    @RequestMapping("/staff_book_return_check_agree.html")
    public String staff_book_return_check_agree(HttpServletRequest request, RedirectAttributes redirectAttributes){
        int book_id = Integer.parseInt(request.getParameter("book_id"));
        BookBorrow bookBorrow = bookService.selectBookBorrowById(book_id);
        bookBorrow.setIsbrwed("no");
        bookBorrow.setComment("finished");
        boolean isup = bookService.updateBookBorrow(bookBorrow);

        if( isup ){
            redirectAttributes.addFlashAttribute("updateSuccess", "已通过！");
        }
        else{
            redirectAttributes.addFlashAttribute("updateError", "出错了！");
        }
        return "redirect:/staff_book_return_check.html";
    }

    @RequestMapping("/staff_book_return_check_disagree.html")
    public String staff_book_return_check_disagree(HttpServletRequest request, RedirectAttributes redirectAttributes){
        int book_id = Integer.parseInt(request.getParameter("book_id"));
        BookBorrow bookBorrow = bookService.selectBookBorrowById(book_id);
        bookBorrow.setComment("");
        boolean isup = bookService.updateBookBorrow(bookBorrow);

        if( isup ){
            redirectAttributes.addFlashAttribute("updateSuccess", "已拒绝！");
        }
        else{
            redirectAttributes.addFlashAttribute("updateError", "出错了！");
        }

        return "redirect:/staff_book_return_check.html";
    }

    @RequestMapping("/staff_statistics_bookborrow.html")
    public ModelAndView staff_statistics_bookborrow(HttpServletRequest request) throws JsonProcessingException {
        ArrayList<BookBorrow> bookBorrows = bookService.selectAllBookBorrow();
        // 创建一个HashMap来存储每年的计数
        Map<String, Integer> borrowCountByYear = new HashMap<>();

        // 获取当前年份
        int currentYear = Year.now().getValue();

        // 初始化计数器
        for (int i = 0; i < 3; i++) {
            borrowCountByYear.put(String.valueOf(currentYear - i), 0);
        }
        User user = (User) request.getSession().getAttribute("user");
        String work_name = user.getWork_name();
        Iterator<BookBorrow> iterator = bookBorrows.iterator();
        while (iterator.hasNext()){
            BookBorrow bookBorrow = iterator.next();
            int book_id = bookBorrow.getBook_id();
            Book book = bookService.selectBookById(book_id);
            if( !book.getBelongto().equals(work_name) ){
                iterator.remove(); // 使用迭代器的 remove 方法
            }
        }


        Map<String, Long> yearlyCount = bookBorrows.stream()
                .filter(borrow -> borrow.getBrw_time() != null)
                .collect(Collectors.groupingBy(
                        borrow -> String.valueOf(borrow.getBrw_time().getYear()+1900), // 获取年份
                        Collectors.counting() // 计数
                ));
        List<Map<String, Object>> booksBorrowed = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Map<String, Object> map = new HashMap<>();
            String year = String.valueOf(currentYear - i);
            map.put("year", year);
            map.put("count", yearlyCount.getOrDefault(year, 0L)); // 获取统计数量，如果没有则为0
            booksBorrowed.add(map);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String booksBorrowedJson = objectMapper.writeValueAsString(booksBorrowed);

        // 本单位总图书数
        ArrayList<Book> books = bookService.selectAllBook();
        int myWorkBookNum = 0;
        for(Book book : books){
            if(book.getBelongto().equals(work_name)){
                myWorkBookNum = myWorkBookNum+1;
            }
        }

        // 当前在库图书数
        int num = 0;
        for (BookBorrow bookBorrow:bookBorrows){
            if(bookBorrow.getIsbrwed().equals("yes")){
                num = num + 1;
            }
        }
        int myWorkBookNowNum = myWorkBookNum - num;

        ModelAndView modelAndView = new ModelAndView("staff_statistics_bookborrow");
        modelAndView.addObject("myWorkBookNum", myWorkBookNum);
        modelAndView.addObject("myWorkBookNowNum", myWorkBookNowNum);
        modelAndView.addObject("booksBorrowedJson", booksBorrowedJson);
        return modelAndView;
    }

    @RequestMapping("/staff_statistics_bookborrow_month.html")
    public ModelAndView staff_statistics_bookborrow_month(HttpServletRequest request) throws JsonProcessingException {
        ArrayList<BookBorrow> bookBorrows = bookService.selectAllBookBorrow();

        // 初始化月度借书统计数据结构
        Map<String, Long> monthlyCount = new LinkedHashMap<>();
        for (int month = 1; month <= 12; month++) {
            monthlyCount.put(String.format("%02d", month), 0L); // 使用0填充每个月份
        }

        User user = (User) request.getSession().getAttribute("user");
        String work_name = user.getWork_name();

        // 过滤并统计属于当前单位的借阅记录
        bookBorrows.removeIf(bookBorrow -> {
            Book book = bookService.selectBookById(bookBorrow.getBook_id());
            return !book.getBelongto().equals(work_name);
        });

        // 按月统计
        Map<String, Long> borrowCountByMonth = bookBorrows.stream()
                .filter(borrow -> borrow.getBrw_time() != null)
                .collect(Collectors.groupingBy(
                        borrow -> String.format("%02d", borrow.getBrw_time().getMonth() + 1), // 获取月份
                        Collectors.counting() // 计数
                ));

        // 结合初始化的数据结构
        monthlyCount.forEach((key, value) -> monthlyCount.put(key, borrowCountByMonth.getOrDefault(key, 0L)));

        ObjectMapper objectMapper = new ObjectMapper();
        String booksBorrowedJson = objectMapper.writeValueAsString(monthlyCount);

        // 本单位总图书数
        ArrayList<Book> books = bookService.selectAllBook();
        int myWorkBookNum = 0;
        for(Book book : books){
            if(book.getBelongto().equals(work_name)){
                myWorkBookNum = myWorkBookNum+1;
            }
        }

        // 当前在库图书数
        int num = 0;
        for (BookBorrow bookBorrow:bookBorrows){
            if(bookBorrow.getIsbrwed().equals("yes")){
                num = num + 1;
            }
        }
        int myWorkBookNowNum = myWorkBookNum - num;

        ModelAndView modelAndView = new ModelAndView("staff_statistics_bookborrow_month");
        modelAndView.addObject("myWorkBookNum", myWorkBookNum);
        modelAndView.addObject("myWorkBookNowNum", myWorkBookNowNum);
        modelAndView.addObject("booksBorrowedJson", booksBorrowedJson);
        return modelAndView;
    }

}
