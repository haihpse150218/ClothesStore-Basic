
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<h1>List of Purchaser Order</h1>
<hr/>
<table class="table table-striped">
    <thead>
        <tr>
            <th class="col">No.</th>
            <th class="col">Order ID</th>
            <th class="col">Date</th>
            <th class="col">Customer Name</th>
            <th class=" col">Address</th>
            <th class=" col">Total</th>
            <th class=" col">Status</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="order" items="${list}" varStatus="loop" >
        <form>
            <tr >
                <td>${loop.count}</td>
                <td>${order.orderId}<input type="hidden" value="${order.orderId}" name="id" /></td>
                <td><fmt:formatDate value="${order.date}" pattern="dd-MM-yyyy" /></td>
                <td>${order.customerName.name}</td>
                <td >${order.address}</td>
                <td >${order.total}</td>
                <td >
                    <select id="status" name="status" class="form-select" >
                        <c:forEach var="status" items="${listStatus}" >
                            <option  <c:if test="${order.status == status}" >selected</c:if> value="${status}">${status}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
        </form>
    </c:forEach>
</tbody>
</table>
<div class="row">
    <div class="col justify-content-between" style="text-align: center;">
        <br/>
        <form action="<c:url value="/managerAccount/index.do" />">
            <button type="submit" class="btn  btn-default" name="op" value="FirstPage" title="First Page" <c:if test="${pagePO==1}">disabled</c:if>><i class="bi bi-chevron-bar-left"></i></button>
            <button type="submit" class="btn  btn-default" name="op" value="PreviousPage" title="Previous Page" <c:if test="${pagePO==1}">disabled</c:if>><i class="bi bi-chevron-left"></i></button>
            <button type="submit" class="btn  btn-default" name="op" value="NextPage" title="Next Page" <c:if test="${pagePO==totalPagePO}">disabled</c:if>><i class="bi bi-chevron-right"></i></button>
            <button type="submit" class="btn  btn-default" name="op" value="LastPage" title="Last Page" <c:if test="${pagePO==totalPagePO}">disabled</c:if>><i class="bi bi-chevron-bar-right"></i></button>
            <input type="number" min="1" max="${totalPagePO}" name="gotoPage" value="${pagePO}" class="btn btn-sm btn-outline-default" style="text-align: right;width: 42px;" title="Enter page number"/>
            <button type="submit" class="btn  btn-default" name="op" value="GotoPage" title="Goto Page"><i class="bi bi-arrow-up-right-circle"></i></button>
        </form>
        Page ${pagePO}/${totalPagePO}
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

