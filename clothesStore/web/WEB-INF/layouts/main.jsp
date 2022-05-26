<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>ClothesStore</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!--boostrap 5-->
        <link href="${root}/css/boostrap.css" rel="stylesheet" type="text/css"/>
        <script src="${root}/js/bootstrap.bundle.js" type="text/javascript"></script>
        <link href="${root}/css/bootstrap-icons.css" rel="stylesheet" type="text/css"/>
        <link href="${root}/css/site.css" rel="stylesheet" type="text/css"/>
        <!--jquery-lib-3.6-->

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

    <img src="https://cdn.shopify.com/s/files/1/0177/2424/files/Nerm-attack-mouse.gif?v=1647045712" width="150"
         id="catchMouse" alt="bike" data-uw-rm-ima-original="bike"
         style="z-index: 1; position: absolute; top: 0px; left: 0px;">
</head>
<body>
    <div class="container-fluid">
        <nav class="navbar navbar-expand-xl bg-light navbar-light" style="font-size: 1.5rem; font-weight: bold;">
            <div class="container-fluid">
                <a class="navbar-brand" href="${root}">
                    <img src="${root}/images/ripndipclothinglogo_155x.gif" class="img-fluid" alt="logo-brand">
                </a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#collapsibleNavbar">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <c:if test="${controller != '/login'}">
                    <div class="collapse navbar-collapse" id="collapsibleNavbar">
                        <!-- left -->
                        <ul class="navbar-nav ms-lg-5">
                            <c:if test="${sessionScope.user.role == 'ROLE_ADMIN'}" >
                                <li class="nav-item">
                                    <a class="nav-link" aria-current="page" 
                                       href="<c:url value="/managerAccount/index.do"/>">
                                        Manager Account
                                    </a>
                                </li>
                            </c:if>
                            <!--hien thi Manager Product--> 
                            <c:if test="${sessionScope.user.role == 'ROLE_EMPLOYEE' || sessionScope.user.role == 'ROLE_ADMIN'}" >
                                <li class="nav-item">
                                    <a class="nav-link" aria-current="page" href="<c:url value="/managerProduct/index.do"/>">Manager Product</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" aria-current="page" href="<c:url value="/purchaseOrder/index.do"/>">Purchase Order</a>
                                </li>
                            </c:if>
                            <c:if test="${sessionScope.user == null }">
                                <li class="nav-item">
                                    <a class="nav-link" aria-current="page" href="<c:url value="/login/index.do"/>">
                                        <i class="bi bi-box-arrow-in-right"></i>Login
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" aria-current="page" href="<c:url value="/register/index.do"/>">
                                        <i class="bi bi-person-plus"></i>Register
                                    </a>
                                </li>
                            </c:if>    
                        </ul>
                        <!-- Right -->
                        <ul class="navbar-nav ms-xl-auto ms-lg-5">
                            <c:if test="${sessionScope.user != null}">   
                                <li class="nav-item p-2">
                                    <i class="bi bi-person-check"></i> Welcome ${sessionScope.user.name}
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" aria-current="page" href="<c:url value="/logout/logout.do"/>">
                                        <i class="bi bi-box-arrow-left"></i> Logout
                                    </a>
                                </li>
                            </c:if>
                            <c:if test="${sessionScope.user == null || sessionScope.user.role == 'ROLE_CUSTOMER'}"> 
                                <li class="nav-item">
                                    <a class="nav-link btn btn-outline-success position-relative" aria-current="page" href="<c:url value="/cart/index.do"/>" type="button">
                                        <span class="clearfix d-none d-md-inline-block">Cart </span>
                                        <i class="bi bi-cart-check-fill"></i>
                                        <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
                                            ${cart==null?0:cart.numOfProducts}
                                        </span>
                                    </a>
                                </li>
                            </c:if>
                        </ul>
                    </div>
                </c:if>
            </div>
        </nav>
    </div>
    <!--end-->
    <!--body-->
    <hr>

    <div class="row content py-3">
        <div class="col">         
            <jsp:include page="/WEB-INF/views/${controller}/${action}.jsp" />
        </div>
    </div>
    <!--chat bot-->


    <div style="position: fixed; bottom: 10px; right: 10px">
        <button id="startChatButton" class="btn btn-primary">Start Chat</button>
    </div>
    <iframe id="chatFrame" 
            style="display:none; 
            position: fixed; bottom: 10px; right: 10px; 
            width: 350px; height: 480px; border: solid 1px #333;" 
            src="https://www.google.com.vn/?hl=vi">

    </iframe>

    <!--footer-->
    <div class="row footer">
        <div class="col">
            <hr/>
            &copy; Copyright 2021-2022 PRJ. All Rights Reserved.${controller}
        </div>
    </div>
</div> 

<script>
    var catchMouse = document.getElementById('catchMouse');
    function tellPos(p) {
        catchMouse.style.top = p.pageY + "px";
        catchMouse.style.left = p.pageX + 5 + "px";
    }
    addEventListener('mousemove', tellPos, false);
//   chatbot script

</script>
<!--<script>
$(document).ready(function(){
  $("#startChatButton").click(function(){
    $("#startChatButton").hide();
    $("#chatFrame").show();
  });
});
</script>-->
</body>


</html>