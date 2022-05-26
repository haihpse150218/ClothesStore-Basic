
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<h1>List of Product</h1>
<hr/>
<a class="btn btn-sm btn-outline-success" href="${pageContext.request.contextPath}/managerProduct/create.do">
    <i class="bi bi-plus-circle"></i> Create New
</a> 
<table class="table table-striped">
    <thead>
        <tr>
            <th class="col">No.</th>
            <th class="col">ID</th>
            <th class="col">Image</th>
            <th class="col">Description</th>
            <th class="text-right col">Price</th>
            <th class="text-right col">Discount</th>
            <th class="col">Category</th>
            <th class="col">Operation</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="item" items="${list}" varStatus="loop" >
        <form>
            <tr >
                <td>${loop.count}</td>
                <td>${item.id}<input type="hidden" value="${item.id}" name="id" /></td>
                <td><img src="<c:url value="/products/${item.id}.jpg"  />" height="60" /></td>
                <td>${item.description}</td>
                <td class="text-right"> <fmt:formatNumber value="${item.price}" pattern="$ #,##0.00" /> </td>
                <td class="text-right"><fmt:formatNumber value="${item.discount}" type="percent" /></td>
                <td>${item.categoryId.name}</td>
                <td>
                    <button type="submit" formaction="<c:url value="/managerProduct/edit.do?id=${item.id}" />" class="btn btn-sm btn-outline-success" /><i class="bi bi-pencil-square"></i> Edit </button> 
                    <button type="submit" formaction="<c:url value="/managerProduct/confirm.do?id=${item.id}" />" class="btn btn-sm btn-outline-danger" /><i class="bi bi-cart-dash"></i> Delete </button>
                </td>
            </tr>
        </form>
    </c:forEach>
</tbody>
</table>
    <!--phan trang-->
<div class="row">
    <div class="col justify-content-between" style="text-align: center;">
        <br/>
        <form action="<c:url value="/managerProduct/index.do" />">
            <button type="submit" class="btn  btn-default" name="op" value="FirstPage" title="First Page" <c:if test="${pageMan==1}">disabled</c:if>><i class="bi bi-chevron-bar-left"></i></button>
            <button type="submit" class="btn  btn-default" name="op" value="PreviousPage" title="Previous Page" <c:if test="${pageMan==1}">disabled</c:if>><i class="bi bi-chevron-left"></i></button>
            <button type="submit" class="btn  btn-default" name="op" value="NextPage" title="Next Page" <c:if test="${pageMan==totalPageMan}">disabled</c:if>><i class="bi bi-chevron-right"></i></button>
            <button type="submit" class="btn  btn-default" name="op" value="LastPage" title="Last Page" <c:if test="${pageMan==totalPageMan}">disabled</c:if>><i class="bi bi-chevron-bar-right"></i></button>
            <input type="number" min="1" max="${totalPageMan}" name="gotoPage" value="${pageMan}" class="btn btn-sm btn-outline-default" style="text-align: right;width: 42px;" title="Enter page number"/>
            <button type="submit" class="btn  btn-default" name="op" value="GotoPage" title="Goto Page"><i class="bi bi-arrow-up-right-circle"></i></button>
        </form>
        Page ${pageMan}/${totalPageMan}
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

