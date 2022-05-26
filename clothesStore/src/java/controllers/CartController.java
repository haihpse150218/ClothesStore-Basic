package controllers;

import cart.Cart;
import cart.Item;
import static controllers.App.LAYOUT;
import entities.Account;
import entities.Customer;
import entities.OrderDetail;
import entities.OrderHeader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
import sessionbeans.CustomerFacade;
import sessionbeans.EmployeeFacade;
import sessionbeans.OrderDetailFacade;
import sessionbeans.OrderHeaderFacade;
import sessionbeans.ProductFacade;

@WebServlet(name = "CartController", urlPatterns = {"/cart"})
public class CartController extends HttpServlet {

    @EJB
    private OrderDetailFacade od;
    @EJB
    private OrderHeaderFacade oh;
    @EJB
    private EmployeeFacade ef;
    @EJB
    private CustomerFacade cf;
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
            case "add":
                add(request, response);
                break;
            case "delete":
                delete(request, response);
                break;
            case "update":
                update(request, response);
                break;
            case "empty":
                empty(request, response);
                break;
            case "checkOut":
                checkOut(request, response);
                break;
            case "customer":
                customer(request, response);
                break;
            case "shipTo":
                shipTo(request, response);
                break;
        }
        request.getRequestDispatcher(LAYOUT).forward(request, response);
    }

    private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) { // Neu trong session chua co gio hang thi tao moi cart
            cart = new Cart();
        }
        cart.add(id, quantity);
//        de cart vao lai session
        session.setAttribute("cart", cart);
//        Cho hien lai view home index
        request.setAttribute("controller", "home");
        request.setAttribute("action", "index");
//        doc danh sach san pham
        request.getRequestDispatcher("/" + request.getAttribute("controller")).forward(request, response);

    }

    private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
//        update trong cart
        cart.update(id, quantity);
//        cho hien cart index.jsp
        request.setAttribute("controller", "cart");
        request.setAttribute("action", "index");

    }

    private void empty(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        cart.empty();
//        cho hien cart index.jsp
        request.setAttribute("controller", "cart");
        request.setAttribute("action", "index");
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
            Logger.getLogger(CartController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(CartController.class.getName()).log(Level.SEVERE, null, ex);
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

    private void view(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("id"));
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
//        update trong cart
        cart.delete(id);
//        cho hien cart index.jsp
        request.setAttribute("controller", "cart");
        request.setAttribute("action", "index");
    }

    private void index(HttpServletRequest request, HttpServletResponse response) {
    }

    private void checkOut(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        HttpSession session = request.getSession();
        Account acc = (Account) session.getAttribute("user");
        Cart cart = (Cart) session.getAttribute("cart");
        System.out.println("cart: " + cart);
        if (cart == null) {
            request.setAttribute("message", "Cart is empty");
            request.setAttribute("action", "index");
            return;
        } else {
            if (session.getAttribute("user") == null) {
                request.setAttribute("controller", "login");
                request.setAttribute("action", "index");
                String uri = request.getServletPath();
                String controller = uri.substring(1);
                session.setAttribute("uri", controller);
                return;
            } else {
                if (cf.find(acc.getId()) == null) {
                    request.setAttribute("account", acc);
                    request.setAttribute("action", "shipTo");
                    return;
                } else {
                    request.setAttribute("action", "success");
                    session.setAttribute("cart", null);
                }
            }
        }
        //create order header
        OrderHeader orderHeader = new OrderHeader(-1, new Date());
        orderHeader.setStatus("new"); // 'canceled' OR 'paid' 'delivered' 'packaged' 'confirmed''new')
        orderHeader.setCustomerId(cf.find(acc.getId()));
        orderHeader.setEmployeeId(null);
        oh.create(orderHeader);
        List<OrderDetail> list = new ArrayList();
        for (Item item : cart.getItems()) {
            OrderDetail orderDetail = new OrderDetail(
                    -1, item.getQuantity(), item.getCost(), item.getDiscount());
            orderDetail.setOrderId(orderHeader);
            orderDetail.setProductId(pf.find(item.getId()));
            od.create(orderDetail);
            list.add(orderDetail);
        }
        orderHeader.setOrderDetailList(list);
    }

    private void customer(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        String shipToAdress = request.getParameter("shipTo");
        HttpSession session = request.getSession();
        Account acc = (Account) session.getAttribute("user");
        Customer customer = new Customer(acc.getId(), "Copper", shipToAdress);
        cf.create(customer);
        request.setAttribute("action", "index");
    }

    private void shipTo(HttpServletRequest request, HttpServletResponse response) {
    }
}
