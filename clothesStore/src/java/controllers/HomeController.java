package controllers;

import entities.Category;
import entities.Product;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sessionbeans.CategoryFacade;
import sessionbeans.ProductFacade;

@WebServlet(name = "HomeController", urlPatterns = {"/home"})
public class HomeController extends HttpServlet {
    @EJB
    private CategoryFacade cf;
    @EJB
    private ProductFacade pf;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String action = request.getAttribute("action").toString();
        
        switch (action) {
            case "index":
                index(request, response);
                break;
            default:
                request.setAttribute("controller", "error");
                request.setAttribute("action", "index");
        }
        request.getRequestDispatcher(App.LAYOUT).forward(request, response);
    }

    private void index(HttpServletRequest request, HttpServletResponse response) {
        int pageSize = 6;//Kich thuoc trang                        
        HttpSession session = request.getSession();
        String sortingByCateId = request.getParameter("sortingByCateID");
        Integer cateId = (Integer) session.getAttribute("cateId");
        Integer page = (Integer) session.getAttribute("page");
        Integer totalPage = (Integer) session.getAttribute("totalPage");
        if (sortingByCateId != null) {
            cateId = Integer.parseInt(sortingByCateId);
            session.setAttribute("page", null);
            session.setAttribute("totalPage", null);
        }
        if (cateId == null) {
            cateId = -1; // cate = All
        }
        if (cateId > 0) {
            if (page == null) {
                page = 1;
            }
            int count = pf.countListCate(cateId);//Dem so luong records theo cateID
            System.out.println("countC" + count);
            totalPage = (int) Math.ceil((double) (count - 1) / pageSize);//Tinh tong so trang

        } else {
            if (page == null) {
                page = 1;
            }
            //Xac dinh tong so trang
            int count = pf.count();//Dem so luong records
            System.out.println("count" + count);
            totalPage = (int) Math.ceil((double) (count - 1) / pageSize);//Tinh tong so trang

        }

        //Xac dinh so thu tu cua trang hien tai
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
        List<Product> list = null;
        if (cateId > 0) {
            list = pf.findByCateIdRange(cateId, new int[]{n1, n2});

        } else {
            list = pf.findRange(new int[]{n1, n2}); //Doc mot trang

        }
//        lay list category ra
        List<Category> listC = cf.findAll();
        //Luu thong tin vao session va request
        session.setAttribute("cateId", cateId);
        session.setAttribute("page", page);
        session.setAttribute("totalPage", totalPage);
        request.setAttribute("list", list);
        request.setAttribute("listC", listC);
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

}
