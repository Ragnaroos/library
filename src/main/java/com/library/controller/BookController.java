package com.library.controller;

import com.library.bean.*;
import com.library.service.BookService;
import com.library.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private WorkService workService;

    private String format(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(date);
        return formattedDate;
    }

    @RequestMapping("/admin_all_book.html")
    public ModelAndView admin_all_book(HttpServletRequest request){
        ArrayList<Book> books = bookService.selectAllBook();

        ModelAndView modelAndView = new ModelAndView("admin_all_book");
        modelAndView.addObject("books", books);
        return modelAndView;
    }

    @RequestMapping("/admin_all_book_deleteone.html")
    public String admin_all_book_deleteone(HttpServletRequest request, RedirectAttributes redirectAttributes){
        int book_id = Integer.parseInt(request.getParameter("book_id"));
        BookBorrow bookBorrow = bookService.selectBookBorrowById(book_id);
        BookCirculate bookCirculate = bookService.selectBookCirculateById(book_id);

        if(bookBorrow != null && bookBorrow.getIsbrwed().equals("yes")){
            redirectAttributes.addFlashAttribute("updateError", "删除失败！书籍处于在借状态");
        }
        else if(bookCirculate!=null && !bookCirculate.getCir_state().equals("free")){
            redirectAttributes.addFlashAttribute("updateError", "删除失败！书籍处于流通状态");
        }
        else{
            if( bookBorrow!=null ){
                boolean isBB = bookService.deleteBookBorrowById(book_id);
            }
            boolean isB = bookService.deleteBookById(book_id);

            if( isB ){
                redirectAttributes.addFlashAttribute("updateSuccess", "删除成功！");
            }
            else{
                redirectAttributes.addFlashAttribute("updateError", "删除失败！");
            }
        }

        return "redirect:/admin_all_book.html";
    }

    @RequestMapping("/admin_all_book_info.html")
    public ModelAndView admin_all_book_info(HttpServletRequest request){
        int book_id = Integer.parseInt(request.getParameter("book_id"));
        Book book = bookService.selectBookById(book_id);
        BookBorrow bookBorrow = bookService.selectBookBorrowById(book_id);

        ModelAndView modelAndView = new ModelAndView("admin_all_book_info");
        modelAndView.addObject("thisbook", book);
        modelAndView.addObject("thisbookBorrow", bookBorrow);
        return modelAndView;
    }

    @RequestMapping("/admin_all_book_update.html")
    public ModelAndView admin_book_update(HttpServletRequest request){
        int book_id = Integer.parseInt(request.getParameter("book_id"));
        Book book = bookService.selectBookById(book_id);
        BookBorrow bookBorrow = bookService.selectBookBorrowById(book_id);
        ArrayList<Work> works = workService.selectAllWork();
        String formattedDate = format(book.getPublish_time());


        ModelAndView modelAndView = new ModelAndView("admin_all_book_update");
        modelAndView.addObject("thisbook", book);
        modelAndView.addObject("thisbookBorrow", bookBorrow);
        modelAndView.addObject("formattedPublishTime", formattedDate);
        modelAndView.addObject("works", works);
        return modelAndView;
    }

    @RequestMapping("/admin_book_update_do.html")
    public String admin_book_update_do(@ModelAttribute Book book, @RequestParam("picture") MultipartFile file, HttpServletRequest request, RedirectAttributes redirectAttributes){
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
            book.setPhoto_path(uploadsDir + orgName);

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("updatePError", "图片上传失败！");
            return "redirect:/admin_all_book.html";
        }

        boolean isUp = bookService.updateBook(book);
        if( isUp ){
            redirectAttributes.addFlashAttribute("updateSuccess", "更新成功！");
        }
        else{
            redirectAttributes.addFlashAttribute("updateError", "更新失败！");
        }
        return "redirect:/admin_all_book.html";
    }

    @RequestMapping("/querybook.html")
    public ModelAndView querybook(String searchWord){
        ArrayList<Book> books = bookService.queryBook(searchWord);

        ModelAndView modelAndView = new ModelAndView("admin_all_book_querybook");
        modelAndView.addObject("books", books);
        return modelAndView;
    }

    @RequestMapping("/staff_querybook.html")
    public  ModelAndView staff_querybook(String searchWord, HttpServletRequest request){
        ArrayList<Book> books = bookService.queryBook(searchWord);
        Iterator<Book> iterator = books.iterator();
        User user = (User) request.getSession().getAttribute("user");
        while (iterator.hasNext()) {
            Book book = iterator.next();
            if (!book.getBelongto().equals(user.getWork_name())) {
                iterator.remove(); // 使用迭代器的 remove 方法
            }
        }


        ModelAndView modelAndView = new ModelAndView("staff_all_book_querybook");
        modelAndView.addObject("books", books);
        return modelAndView;
    }

    @RequestMapping("/staff_all_book.html")
    public ModelAndView staff_all_book(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        ArrayList<Book> books = bookService.selectAllBook();
        Iterator<Book> iterator = books.iterator();
        while (iterator.hasNext()) {
            Book book = iterator.next();
            if (!book.getBelongto().equals(user.getWork_name())) {
                iterator.remove(); // 使用迭代器的 remove 方法
            }
        }

        ModelAndView modelAndView = new ModelAndView("staff_all_book");
        modelAndView.addObject("books", books);
        return modelAndView;
    }

    @RequestMapping("/staff_all_book_info.html")
    public ModelAndView staff_all_book_info(HttpServletRequest request){
        int book_id = Integer.parseInt(request.getParameter("book_id"));
        Book book = bookService.selectBookById(book_id);
        BookBorrow bookBorrow = bookService.selectBookBorrowById(book_id);

        ModelAndView modelAndView = new ModelAndView("staff_all_book_info");
        modelAndView.addObject("thisbook", book);
        modelAndView.addObject("thisbookBorrow", bookBorrow);
        return modelAndView;
    }

    @RequestMapping("/staff_all_book_deleteone.html")
    public String staff_all_book_deleteone(HttpServletRequest request, RedirectAttributes redirectAttributes){
        int book_id = Integer.parseInt(request.getParameter("book_id"));
        BookBorrow bookBorrow = bookService.selectBookBorrowById(book_id);
        if(bookBorrow != null && bookBorrow.getIsbrwed().equals("yes")){
            redirectAttributes.addFlashAttribute("updateError", "删除失败！书籍处于在借状态");
        }
        else{
            if( bookBorrow!=null ){
                boolean isBB = bookService.deleteBookBorrowById(book_id);
            }
            boolean isB = bookService.deleteBookById(book_id);

            if( isB ){
                redirectAttributes.addFlashAttribute("updateSuccess", "删除成功！");
            }
            else{
                redirectAttributes.addFlashAttribute("updateError", "删除失败！");
            }
        }
        return "redirect:/staff_all_book.html";
    }

    @RequestMapping("/staff_all_book_update.html")
    public ModelAndView staff_all_book_update( HttpServletRequest request){
        int book_id = Integer.parseInt(request.getParameter("book_id"));
        Book book = bookService.selectBookById(book_id);
        BookBorrow bookBorrow = bookService.selectBookBorrowById(book_id);
        ArrayList<Work> works = workService.selectAllWork();
        String formattedDate = format(book.getPublish_time());

        ModelAndView modelAndView = new ModelAndView("staff_all_book_update");
        modelAndView.addObject("thisbook", book);
        modelAndView.addObject("thisbookBorrow", bookBorrow);
        modelAndView.addObject("formattedPublishTime", formattedDate);
        modelAndView.addObject("works", works);
        return modelAndView;
    }

    @RequestMapping("/staff_book_update_do.html")
    public String staff_book_update_do(@ModelAttribute Book book, @RequestParam("picture") MultipartFile file, HttpServletRequest request, RedirectAttributes redirectAttributes){
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
            book.setPhoto_path(uploadsDir + orgName);

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("updatePError", "图片上传失败！");
            return "redirect:/staff_all_book.html";
        }

        boolean isUp = bookService.updateBook(book);
        if( isUp ){
            redirectAttributes.addFlashAttribute("updateSuccess", "更新成功！");
        }
        else{
            redirectAttributes.addFlashAttribute("updateError", "更新失败！");
        }
        return "redirect:/staff_all_book.html";
    }

    @RequestMapping("/staff_add_book.html")
    public ModelAndView staff_add_book(){
        ModelAndView modelAndView = new ModelAndView("staff_add_book");
        return modelAndView;
    }

    @RequestMapping("/staff_add_book_do.html")
    public String staff_add_book_do(@ModelAttribute Book book, @RequestParam("picture") MultipartFile file, HttpServletRequest request, RedirectAttributes redirectAttributes){

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
            book.setPhoto_path(uploadsDir + orgName);

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("updateError", "图片上传失败！");
            return "redirect:/staff_all_book.html";
        }
        boolean isIn = bookService.insertBook(book);

        return "redirect:/staff_all_book.html";
    }

    @RequestMapping("/staff_add_batchbook.html")
    public ModelAndView modelAndView(){
        ModelAndView modelAndView = new ModelAndView("staff_add_batchbook");
        return modelAndView;
    }

    @RequestMapping("/uploadCSV.html")
    public String uploadCSV(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes){
        // 确保文件不为空
        if (file.isEmpty()) {
            // 处理空文件的情况
            redirectAttributes.addFlashAttribute("updateError", "文件为空，批量添加失败！");
            return "redirect:/staff_all_book.html";
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            br.readLine(); // 跳过标题行

            while ((line = br.readLine()) != null) {
                String[] data = line.split(","); // 假设CSV文件用逗号分隔
                Book book = new Book();

                book.setBook_name(data[0]);
                try {
                    book.setPublish_time(dateFormat.parse(data[1])); // 假设日期格式为 yyyy-MM-dd
                } catch (ParseException e) {
                    // 处理日期解析异常
                    e.printStackTrace();
                }
                book.setWriter(data[2]);
                book.setPublish_house(data[3]);
                book.setBook_class(data[4]);
                book.setPages(Integer.parseInt(data[5]));
                book.setPrice(new BigDecimal(data[6]));
                book.setPhoto_path(data[7]);
                book.setBook_state(data[8]);
                book.setBelongto(data[9]);

                bookService.insertBook(book);
            }

        } catch (IOException e) {
            // 处理IO异常
            e.printStackTrace();
        } catch (NumberFormatException e) {
            // 处理数字格式异常
            e.printStackTrace();
        }


        return "redirect:/staff_all_book.html";
    }

    @RequestMapping("/reader_all_book.html")
    public ModelAndView reader_all_book(){
        ArrayList<Book> books = bookService.selectAllBook();
        Iterator<Book> iterator = books.iterator();
        while (iterator.hasNext()) {
            Book book = iterator.next();
            if ( book.getBook_state().equals("private")) {
                iterator.remove(); // 使用迭代器的 remove 方法
            }
        }

        ModelAndView modelAndView = new ModelAndView("reader_all_book");
        modelAndView.addObject("books", books);
        return modelAndView;
    }

    @RequestMapping("/reader_querybook.html")
    public ModelAndView reader_querybook(String searchWord){
        ArrayList<Book> books = bookService.queryBook(searchWord);

        Iterator<Book> iterator = books.iterator();
        while (iterator.hasNext()) {
            Book book = iterator.next();
            if ( book.getBook_state().equals("private")) {
                iterator.remove(); // 使用迭代器的 remove 方法
            }
        }

        ModelAndView modelAndView = new ModelAndView("reader_all_book_querybook");
        modelAndView.addObject("books", books);
        return modelAndView;
    }

    @RequestMapping("/reader_all_book_info.html")
    public ModelAndView reader_all_book_info(HttpServletRequest request){
        int book_id = Integer.parseInt(request.getParameter("book_id"));
        Book book = bookService.selectBookById(book_id);
        BookBorrow bookBorrow = bookService.selectBookBorrowById(book_id);

        ModelAndView modelAndView = new ModelAndView("reader_all_book_info");
        modelAndView.addObject("thisbook", book);
        modelAndView.addObject("thisbookBorrow", bookBorrow);
        return modelAndView;
    }

}
