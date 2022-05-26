/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import static controllers.App.LAYOUT;
import entities.Account;
import entities.OrderHeader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import punchaseOrder.Order;
import sessionbeans.AccountFacade;
import sessionbeans.CustomerFacade;
import sessionbeans.OrderDetailFacade;
import sessionbeans.OrderHeaderFacade;

/**
 *
 * @author SE150218 Hoang Phi Hai
 */
@WebServlet(name = "PurchaseOrderController", urlPatterns = {"/purchaseOrder"})
public class PurchaseOrderController extends HttpServlet {

    @EJB
    private OrderDetailFacade od;

    @EJB
    private CustomerFacade cf;

    @EJB
    private AccountFacade af;

    @EJB
    private OrderHeaderFacade oh;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getAttribute("action").toString();
        switch (action) {
            case "index":
                index(request, response);
                break;
            case "update":
                update(request, response);
                break;
            default:
                request.setAttribute("acction", "error");
                break;
        }
        request.getRequestDispatcher(LAYOUT).forward(request, response);

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void index(HttpServletRequest request, HttpServletResponse response) {
        int pageSize = 5;
        HttpSession session = request.getSession();
        //Xac dinh so thu tu cua trang hien tai
        Integer page = (Integer) session.getAttribute("pagePO");

        if (page == null) {
            page = 1;
            System.out.println("checkl" + page);
        }
        //Xac dinh tong so trang
        Integer totalPage = (Integer) session.getAttribute("totalPagePO");
        if (totalPage == null) {
            int count = oh.count();//Dem so luong records
            totalPage = (int) Math.ceil((double) (count) / pageSize);
        }
        System.out.println("check2" + totalPage);
        String op = request.getParameter("op");
        if (op == null) {
            op = "FirstPage";
        }
        switch (op) {
            case "FirstPage":
                page = 1;
                break;
            case "PreviousPage":
                if (page > 1) {
                    page--;
                }
                break;
            case "NextPage":
                if (page < totalPage) {
                    page++;
                }
                break;
            case "LastPage":
                page = totalPage;
                break;
            case "GotoPage":
                page = Integer.parseInt(request.getParameter("gotoPage"));
                break;
        }
        System.out.println("check3");
        int n1 = (page - 1) * pageSize;//Vi tri mau tin dau trang
        int n2 = n1 + pageSize - 1;//Vi tri mau tin cuoi trang
        System.out.println("check4 " + n1 + ":" + n2);
        List<Order> list = new ArrayList<>();
        List<OrderHeader> tmp = oh.findRange(new int[]{n1, n2 + 1});//Doc mot trang
        System.out.println(tmp.toString());
        for (OrderHeader orderHeader : tmp) {
            int orderId = orderHeader.getId();
            Date date = orderHeader.getDate();
            Account customerName = af.find(orderHeader.getCustomerId().getId());
            Account employeeName = null;
            if (orderHeader.getEmployeeId() != null) {
                employeeName = af.find(orderHeader.getEmployeeId().getId());
            } else {
                employeeName = new Account();
            }
            String address = orderHeader.getCustomerId().getShipToAddress();
            double total = od.caculateTotalByHeaderId(orderId);
            String status = orderHeader.getStatus();
            Order order = new Order(orderId, date, customerName, employeeName, address, total, status);
            list.add(order);
        }
        List<String> listStatus = Arrays.asList("canceled", "paid", "delivered", "packaged", "confirmed", "new");
        //Luu thong tin vao session va request
        session.setAttribute("pagePO", page);
        session.setAttribute("totalPagePO", totalPage);
        request.setAttribute("list", list);
        request.setAttribute("listStatus", listStatus);
    }

    private void update(HttpServletRequest request, HttpServletResponse response) {
    }

}
