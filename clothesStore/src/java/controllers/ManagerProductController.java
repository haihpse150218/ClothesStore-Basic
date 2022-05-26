/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import static controllers.App.LAYOUT;
import entities.Category;
import entities.Product;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sessionbeans.CategoryFacade;
import sessionbeans.ProductFacade;

@WebServlet(name = "ManagerProductController", urlPatterns = {"/managerProduct"})
public class ManagerProductController extends HttpServlet {

    @EJB
    private CategoryFacade cf;
    @EJB
    private ProductFacade pf;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getAttribute("action").toString();
        switch (action) {
            case "index":
                index(request, response);
                break;
            case "create":
                create(request, response);//form submit
                break;
            case "insert":
                insert(request, response);// update vao insert
                break;
            case "confirm":
                confirm(request, response);//form yes/no
                break;
            case "delete":
                delete(request, response);//yes => delete
                break;
            case "edit":
                edit(request, response);
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ManagerProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ManagerProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
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

//    
    private void index(HttpServletRequest request, HttpServletResponse response) throws SQLException {
//        phan trang
        int pageSize = 5;
        HttpSession session = request.getSession();
        //Xac dinh so thu tu cua trang hien tai
        Integer page = (Integer) session.getAttribute("pageMan");
        if (page == null) {
            page = 1;
        }
        //Xac dinh tong so trang
        Integer totalPage = (Integer) session.getAttribute("totalPageMan");
        if (totalPage == null) {
            int count = pf.count();//Dem so luong records tu database 19

            totalPage = (int) Math.ceil((double) (count) / pageSize);
        }
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
        //Lay trang du lieu duoc yeu cau
        int n1 = (page - 1) * pageSize;//Vi tri mau tin dau trang
        int n2 = n1 + pageSize - 1;//Vi tri mau tin cuoi trang
        List<Product> list = pf.findRange(new int[]{n1, n2 + 1});//lay san pham len tu database

        //Luu thong tin vao session va request
        session.setAttribute("pageMan", page);
        session.setAttribute("totalPageMan", totalPage);
        request.setAttribute("list", list);
//        ket thuc phan trang
    }

    private void create(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        List<Category> listC = cf.findAll();
        Product product = new Product();
        request.setAttribute("listC", listC);
        request.setAttribute("product", product);

    }

    private void delete(HttpServletRequest request, HttpServletResponse response) {
        try {
            String op = request.getParameter("op");
            if (op.equals("yes")) {
                String id = request.getParameter("id");
                pf.remove(pf.find(Integer.parseInt(id)));
                //            refresh count total page in session
                request.getSession().setAttribute("totalPageMan", null);
                request.getSession().setAttribute("totalPage", null);
            }
            //hien lai view index
            index(request, response);
            request.setAttribute("action", "index");

        } catch (Exception ex) {
            request.setAttribute("action", "confirm");
            String msg = ex.getMessage();
            if (msg == null) {
                msg = "";
            }
            request.setAttribute("errorMessage", "Error when processing this product. <br/>" + msg);
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void edit(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        Product product = pf.find(Integer.parseInt(request.getParameter("id")));
        List<Category> listC = cf.findAll();
        request.setAttribute("product", product);
        request.setAttribute("listC", listC);
    }

    private void confirm(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        request.setAttribute("id", id);
        request.getSession().setAttribute("totalPageMan", null);
        request.getSession().setAttribute("totalPage", null);
    }

    private void update(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        String op = request.getParameter("op");
        try {
            if (op.equals("update")) {
                int id = Integer.parseInt(request.getParameter("id"));
                String description = request.getParameter("description");
                Double price = Double.parseDouble(request.getParameter("price"));
                Double discount = Double.parseDouble(request.getParameter("discount"));
                Integer category_id = Integer.parseInt(request.getParameter("category_id"));
                Category category = cf.find(category_id);
                Product product = new Product(id, description, price, discount);
                product.setCategoryId(category);
                pf.edit(product);

            }
            request.setAttribute("action", "index");
            index(request, response);

        } catch (Exception e) {
            request.setAttribute("action", "edit");
            edit(request, response);
            String msg = e.getMessage();
            if (msg == null) {
                msg = "";
            }
            request.setAttribute("errorMessage", "Error when inserting this product. <br/>" + msg);
        }

    }
//  dung de dua san pham moi tao vao database

    private void insert(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        String op = request.getParameter("op");
        try {
            Product product = null;
            if (op.equals("update")) {
                // lay thong tin tu front-end
                String description = request.getParameter("description");
                request.setAttribute("description", description);
                double price = Double.parseDouble(request.getParameter("price"));
                request.setAttribute("price", price);
                double discount = Double.parseDouble(request.getParameter("discount"));
                request.setAttribute("discount", discount);
                Integer category_id = Integer.parseInt(request.getParameter("category_id"));
                Category category = cf.find(category_id);
                // gui len lai bao toan thong tin
                request.setAttribute("category_id", category_id);

                // tao product roi dua vao database
                product = new Product(-1, description, price, discount);
                product.setCategoryId(category);
                request.setAttribute("product", product);
                pf.create(product);
            }
//            refresh count total page in session
            request.getSession().setAttribute("totalPageMan", null);
            request.getSession().setAttribute("totalPage", null);
            // tra ve trang index product lai
            request.setAttribute("action", "index");
            index(request, response);
        } catch (Exception ex) {
            List<Category> listC = cf.findAll();
            // truong hop loi van dua lai list category len front-end
            request.setAttribute("listC", listC);
            request.setAttribute("action", "create");
            String msg = ex.getMessage();
            if (msg == null) {
                msg = "";
            }
            request.setAttribute("errorMessage", "Error when inserting this product. <br/>" + msg);
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
