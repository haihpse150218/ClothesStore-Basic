package controllers;

import entities.Account;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

@WebServlet(name = "LoginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

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
            case "login":
                login(request, response);
                break;
            default:
                request.setAttribute("controller", "error");
                request.setAttribute("action", "index");
        }
        request.getRequestDispatcher(App.LAYOUT).forward(request, response);

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

    private void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = request.getParameter("user");
        String pass = request.getParameter("pass");// 1
        
        HttpSession session = request.getSession();
        String encodePass = null;
        MessageDigest digest;
        
        try {
            digest = MessageDigest.getInstance("SHA-256"); //   
            byte[] encodedhash = digest.digest(pass.getBytes(StandardCharsets.UTF_8));
            encodePass = bytesToHex(encodedhash);// chuyen sang hexa de luu cho gon

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<Account> list = null;
        list = af.findAll();
        Account acc = null;
        request.setAttribute("message", "Account not found");
        for (Account account : list) {
            if (account.getUserName().compareToIgnoreCase(user) == 0) {
                if (account.getPassword().compareToIgnoreCase(encodePass) == 0) {
                    request.removeAttribute("message");
                    if (account.getEnabled()) {
                        acc = account;
                        session.setAttribute("user", acc);
                        //dieu huong ve vi tri cu
                        String uri = (String) session.getAttribute("uri");
                        session.removeAttribute("uri");
                        request.setAttribute("action", "index");
                        if (uri == null || acc.getRole().compareToIgnoreCase("ROLE_CUSTOMER") != 0) {
                            request.setAttribute("controller", "home");
                        } else {
                            request.setAttribute("controller", uri);
                        }
                        request.getRequestDispatcher("/" + request.getAttribute("controller")).forward(request, response);
                        return;
                    } else {
                        request.setAttribute("message", "Account was Banned");
                    }
                } else {
                    request.setAttribute("message", "Wrong Password");
                }
                break;
            }
        }
        request.setAttribute("user", user);
        request.setAttribute("action", "index");

    }

}
