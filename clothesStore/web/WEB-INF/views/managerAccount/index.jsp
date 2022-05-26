
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<h1>List of Account</h1>
<hr/>
<table class="table table-striped">
    <thead>
        <tr>
            <th class="col">No.</th>
            <th class="col">ID</th>
            <th class="col">name</th>
            <th class="col">address</th>
            <th class=" col">phone</th>
            <th class=" col">email</th>
            <th class="col">userName</th>
            <th class="col">enabled</th>
            <th class="col">role</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="account" items="${list}" varStatus="loop" >
        <form>
            <tr >
                <td>${loop.count}</td>
                <td>${account.id}<input type="hidden" value="${account.id}" name="id" /></td>
                <td>${account.name}</td>
                <td>${account.address}</td>
                <td >${account.phone}</td>
                <td >${account.email}</td>
                <td >${account.userName}</td>
                <td >
                    <c:if test="${account.enabled == false}" >
                        <div class="form-check form-switch">
                            <input class="form-check-input" type="checkbox" disabled>
                        </div>
                    </c:if>
                    <c:if test="${account.enabled == true}" >
                        <div class="form-check form-switch">
                            <input class="form-check-input" type="checkbox"checked disabled>
                        </div>
                    </c:if>
                    </div>
                </td>
                <td >${account.role}</td>
                <td>
                    <c:if test="${account.role != 'ROLE_ADMIN'}" >
                        <button type="submit" formaction="<c:url value="/managerAccount/edit.do?id=${account.id}" />" class="btn btn-sm btn-outline-success" /><i class="bi bi-pencil-square"></i> Edit </button> 
                        <button type="submit" formaction="<c:url value="/managerAccount/confirm.do?id=${account.id}"/>" class="btn btn-sm btn-outline-danger" />
                        <c:if test="${account.enabled == true}" var="var">Disable</c:if> 
                        <c:if test="${account.enabled == false}" var="var">Enable</c:if> 
                        </button>
                    </c:if>

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
            <button type="submit" class="btn  btn-default" name="op" value="FirstPage" title="First Page" <c:if test="${pageAcc==1}">disabled</c:if>><i class="bi bi-chevron-bar-left"></i></button>
            <button type="submit" class="btn  btn-default" name="op" value="PreviousPage" title="Previous Page" <c:if test="${pageAcc==1}">disabled</c:if>><i class="bi bi-chevron-left"></i></button>
            <button type="submit" class="btn  btn-default" name="op" value="NextPage" title="Next Page" <c:if test="${pageAcc==totalPageAcc}">disabled</c:if>><i class="bi bi-chevron-right"></i></button>
            <button type="submit" class="btn  btn-default" name="op" value="LastPage" title="Last Page" <c:if test="${pageAcc==totalPageAcc}">disabled</c:if>><i class="bi bi-chevron-bar-right"></i></button>
            <input type="number" min="1" max="${totalPageAcc}" name="gotoPage" value="${pageAcc}" class="btn btn-sm btn-outline-default" style="text-align: right;width: 42px;" title="Enter page number"/>
            <button type="submit" class="btn  btn-default" name="op" value="GotoPage" title="Goto Page"><i class="bi bi-arrow-up-right-circle"></i></button>
        </form>
        Page ${pageAcc}/${totalPageAcc}
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

