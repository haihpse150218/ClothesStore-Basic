package controllers;

import static controllers.App.LAYOUT;
import entities.Account;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sessionbeans.AccountFacade;

@WebServlet(name = "RegisterController", urlPatterns = {"/register"})
public class RegisterController extends HttpServlet {

    @EJB
    private AccountFacade af;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getAttribute("action").toString();
        switch (action) {
            case "index":
                index(request, response);
                break;
            case "insert":
                insert(request, response);
                break;
//            case "success":
//                success(request, response);
//                break;
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
        Account acc = (Account) request.getAttribute("account");
        if (acc == null) {
            request.setAttribute("account", acc);
        }
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private void insert(HttpServletRequest request, HttpServletResponse response) {
        try {
            Account account = null;
            String userName = request.getParameter("userName");
            request.setAttribute("userName", userName);

            String name = request.getParameter("name");
            request.setAttribute("name", name);
            String address = request.getParameter("address");
            request.setAttribute("address", address);
            String phone = request.getParameter("phone");
            System.out.println("regex: " + Pattern.matches("[0-9]{10}", phone));
//            check so dien thoai 10 digits bang regex
            if (!Pattern.matches("[0-9]{10}", phone)) {
                throw new Exception("Please Input Real Phone Number!!!");
            }
            request.setAttribute("phone", phone);
            String email = request.getParameter("email");
            request.setAttribute("email", email);
            String password = request.getParameter("password");
            String password2 = request.getParameter("password2");
            if (password.compareTo(password2) != 0) {
                request.setAttribute("action", "index");
                throw new Exception("Repeat your password not same!!!");
            }
            //check username da ton tai hay chua
            for (Account acc : af.findAll()) {
                if (acc.getUserName().compareToIgnoreCase(userName) == 0) {
                    request.setAttribute("action", "index");
                    throw new Exception("User name allready existed!!!");
                }
            }
            String encodePass = null;
            MessageDigest digest;
            digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            encodePass = bytesToHex(encodedhash);

            account = new Account(-1, name, phone, email, userName, encodePass, true, "ROLE_CUSTOMER");
            account.setAddress(address);
            request.setAttribute("account", account);
//            dua account vao trong database
            af.create(account);
            System.out.println("check ac"+account.toString());
            // ho tro manager acount tinh lai total page
            request.getSession().setAttribute("totalPageAcc", null);
            
            
            
        } catch (Exception ex) {
            request.setAttribute("action", "index");
            String msg = ex.getMessage();
            if (msg == null) {
                msg = "";
            }
            request.setAttribute("errorMessage", "Error when inserting this Account. <br/>" + msg);
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("action", "success");
        success(request, response);
    }

    private void success(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
         String uri = (String) session.getAttribute("uri");
        System.out.println("uri ne:" + uri);
        Account acc = (Account) request.getAttribute("account");
        session.setAttribute("user", acc);
        //dieu huong ve vi tri cu
        session.removeAttribute("uri");
        request.setAttribute("action", "index");
        if (uri == null || acc.getRole().compareToIgnoreCase("ROLE_CUSTOMER") != 0) {
            request.setAttribute("controller", "home");
        } else {
            request.setAttribute("controller", uri);
            System.out.println("uri ne2:" + uri);
        }
        try {
            request.getRequestDispatcher("/" + request.getAttribute("controller")).forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }
     }

}
