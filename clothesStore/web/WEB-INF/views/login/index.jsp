<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container h-100">
    <div class="row d-flex justify-content-center align-items-center h-100">
        <div class="col-12 col-md-8 col-lg-6 col-xl-5">
            <div class="card shadow-2-strong" style="border-radius: 1rem;">
                <div class="card-body p-5 text-center">
                    <p class="text-center h1 fw-bold mb-5 mx-1 mx-md-4 mt-4">Login</p>
                    <form action="<c:url value="/login/login.do"/>" method="post" >
                        <div class="mb-3">
                            <!--get user-->
                            <input placeholder="User Name" type="text" class="form-control" name="user" value="${user}" autocomplete="off">
                        </div>
                        <div class="mb-3">
                            <!--get password-->
                            <input placeholder="Password" type="password" name="pass" class="form-control" autocomplete="off">
                            
                            <!--error message-->
                            <div style="font-style: italic" class="mt-3 text-danger">${message}</div>
                        </div>
                        <div>
                            <p>
                                Are you new member? 
                                <a class="nav-link" aria-current="page" href="<c:url value="/register/index.do"/>">Register</a>
                            </p>
                        </div>
                        <button type="submit" class="btn btn-primary">Login</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>


