
package controllers;
import static controllers.App.LAYOUT;
import entities.Account;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
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
import sessionbeans.AccountFacade;

@WebServlet(name = "ManagerAccountController", urlPatterns = {"/managerAccount"})
public class ManagerAccountController extends HttpServlet {
    @EJB
    private AccountFacade af; 
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getAttribute("action").toString();
        switch (action) {
            case "index":
                index(request, response);
                break;
            case "confirm":
                confirm(request, response);
                break;
            case "disable":
                disable(request, response);
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
            Logger.getLogger(ManagerAccountController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ManagerAccountController.class.getName()).log(Level.SEVERE, null, ex);
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

    private void index(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        int pageSize = 5;
        HttpSession session = request.getSession();
        //Xac dinh so thu tu cua trang hien tai
        Integer page = (Integer) session.getAttribute("pageAcc");
        if (page == null) {
            page = 1;
        }
        //Xac dinh tong so trang
        Integer totalPage = (Integer) session.getAttribute("totalPageAcc");
        if (totalPage == null) {
            int count = af.count();//Dem so luong records
            totalPage = (int) Math.ceil((double) (count - 1) / pageSize);
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
        int n1 = (page - 1) * pageSize;//Vi tri mau tin dau trang

        int n2 = n1 + pageSize - 1;//Vi tri mau tin cuoi trang
        List<Account> list = af.findRange(new int[]{n1, n2 + 1});//Doc mot trang
        //Luu thong tin vao session va request
        session.setAttribute("pageAcc", page);
        session.setAttribute("totalPageAcc", totalPage);
        request.setAttribute("list", list);
    }

    private void confirm(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        request.setAttribute("id", id);
        request.getSession().setAttribute("totalPageAcc", null);
    }

    private void disable(HttpServletRequest request, HttpServletResponse response) {
        try {
            String op = request.getParameter("op");
            if (op.equals("yes")) {
                Integer id = Integer.parseInt(request.getParameter("id")) ;
                Account acc = af.find(id);
                if (acc.getEnabled() == true) {
                    acc.setEnabled(false);
                } else {
                    acc.setEnabled(true);
                }
                af.edit(acc);
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
        }
    }

    private void edit(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        Integer id = Integer.parseInt(request.getParameter("id"));
        Account account = af.find(id);
        request.setAttribute("account", account);
        List<String> list = Arrays.asList("ROLE_ADMIN", "ROLE_EMPLOYEE", "ROLE_CUSTOMER");
        request.setAttribute("list", list);
    }

    private void update(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        String op = request.getParameter("op");
        try {
            if (op.equals("update")) {
                int id = Integer.parseInt(request.getParameter("id"));
                String name = request.getParameter("name");
                String address = request.getParameter("address");
                String phone = request.getParameter("phone");
                String email = request.getParameter("email");
                String role = request.getParameter("role");
                Account account = af.find(id);
                account.setName(name);
                account.setAddress(address);
                account.setPhone(phone);
                account.setEmail(email);
                account.setRole(role);
                af.edit(account);
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
            request.setAttribute("errorMessage", "Error when inserting this account. <br/>" + msg);
        }
    }

    private void insert(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void create(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
