<%-- Document : index Created on : Mar 1, 2022, 4:39:55 PM Author : PHT --%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<nav class="navbar navbar-expand-xl bg-light navbar-light mb-5" style="font-weight: bold;">
    <div class="container-fluid">
        <span class="navbar-brand">Categories:</span>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navCate">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navCate">
            <!-- left -->
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link active" href="${root}/home/index.do?sortingByCateID=${-1}">All
                    </a>
                </li>
                <c:forEach var="cate" items="${listC}">
                    <li class="nav-item">
                        <a class="nav-link active" href="${root}/home/index.do?sortingByCateID=${cate.id}" style="text-transform: capitalize">${cate.name}
                        </a>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
</nav>
<div class="row">
    <c:forEach var="product" items="${list}">
        <div class="col-4">
            <form action="<c:url value="/cart/add.do" />" >
                <div class="card my-3" style="width: 100%; overflow: hidden; border: 2px solid #d58512;">
                    <img src="<c:url value= "/products/${product.id}.jpg" />" width="100%" >
                    <hr>
                    <div class="card-body">
                        <input type="hidden" value="${product.id}" name="id">
                        <h5 class="card-subtitle mb-2 text-muted">${product.description}</h5>

                        <h6 class="card-text">
                            Price:
                            <fmt:formatNumber value="${product.price}" type="currency" />
                            <span class="badge bg-danger">
                                Discount:
                                <fmt:formatNumber value="-${product.discount}" type="percent" />
                            </span>
                        </h6>
                        <div class="row">
                            <div class="d-flex justify-content-between">
                                <div>
                                    <p class="text-dark">Quantity</p>
                                </div>
                                <div class="input-group w-auto justify-content-end align-items-center">
                                    <input id="button" type="button" value="-"
                                           class=" btn button-minus btn-danger icon-shape icon-sm mx-1 px-2 disabled"
                                           data-field="quantity">
                                    <input type="number" step="1" max="50" value="1" name="quantity"
                                           class="quantity-field border-0 text-center w-25">
                                    <input type="button" value="+"
                                           class="btn button-plus btn-danger icon-shape icon-sm "
                                           data-field="quantity">
                                </div>
                            </div>
                        </div>
                        <button type="submit" class="btn btn-success text-center"><i class="bi bi-cart-plus"></i> Add to Cart</button>
                    </div>
                </div>
            </form>
        </div>
    </c:forEach>
</div>
<div class="row">
    <div class="col justify-content-between" style="text-align: center;">
        <br/>
        <form action="<c:url value="/home/index.do" />">
            <button type="submit" class="btn  btn-default" name="op" value="FirstPage" title="First Page" <c:if test="${page==1}">disabled</c:if>><i class="bi bi-chevron-bar-left"></i></button>
            <button type="submit" class="btn  btn-default" name="op" value="PreviousPage" title="Previous Page" <c:if test="${page==1}">disabled</c:if>><i class="bi bi-chevron-left"></i></button>
            <button type="submit" class="btn  btn-default" name="op" value="NextPage" title="Next Page" <c:if test="${page==totalPage}">disabled</c:if>><i class="bi bi-chevron-right"></i></button>
            <button type="submit" class="btn  btn-default" name="op" value="LastPage" title="Last Page" <c:if test="${page==totalPage}">disabled</c:if>><i class="bi bi-chevron-bar-right"></i></button>
            <input type="number" min="1" max="${totalPage}" name="gotoPage" value="${page}" class="btn btn-sm btn-outline-default" style="text-align: right;width: 42px;" title="Enter page number"/>
            <button type="submit" class="btn  btn-default" name="op" value="GotoPage" title="Goto Page"><i class="bi bi-arrow-up-right-circle"></i></button>
        </form>
        Page ${page}/${totalPage}
    </div>
</div>

<script>
//                    quantity form
    var quantityInputs = document.getElementsByClassName("quantity-field");
    var btnMinus = document.getElementsByClassName("button-minus");
    var btnPlus = document.getElementsByClassName("button-plus");
    for (let index = 0; index < btnMinus.length; index++) {
        quantityInputs[index].addEventListener('change', function () {
            var input = event.target;
            if (isNaN(input.value) || input.value <= 0) {
                input.value = 1;

            }
            if (input.value == 1) {
                btnMinus[index].classList.add("disabled");
            } else {
                btnMinus[index].classList.remove("disabled");
            }

        });
        btnMinus[index].addEventListener("click", function () {
            var current = (quantityInputs[index].value);
            current--;
            quantityInputs[index].value = current;
            if (quantityInputs[index].value == 1) {
                console.log(btnMinus[index])
                btnMinus[index].classList.add("disabled");
            } else {
                btnMinus[index].classList.remove("disabled");
            }
        });

        btnPlus[index].addEventListener("click", function () {
            var current = (quantityInputs[index].value);
            current++;
            quantityInputs[index].value = current;
            if (quantityInputs[index].value == 1) {
                btnMinus[index].classList.add("disabled");
            } else {
                btnMinus[index].classList.remove("disabled");
            }
        });

    }
</script>