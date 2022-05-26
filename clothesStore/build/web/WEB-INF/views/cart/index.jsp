<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<h2> Your Cart </h2>
<hr>
<table class="table table-striped ">
    <thead>
        <tr>
            <th class="col">No.</th>
            <th class="col">ID</th>
            <th class="col">Image</th>
            <th class="col">Description</th>
            <th class="text-right col">Old Price</th>
            <th class="text-right col">Discount</th>
            <th class="text-right col">New Price</th>
            <th class="text-right col"> Quantity</th>
            <th class="text-right col">Cost</th>
            <th class="col">Operation</th>
        </tr>
    </thead>
    <tbody>
        <c:set var="total" value="0" />
        <c:forEach var="item" items="${cart.items}" varStatus="loop" >
        <form >
            <tr >
                <td>${loop.count}</td>
                <td>${item.id}<input type="hidden" value="${item.id}" name="id" /></td>
                <td><img src="<c:url value="/products/${item.id}.jpg"  />" height="60" /></td>
                <td>${item.description}</td>
                <td class="text-right"> <fmt:formatNumber value="${item.price}" pattern="$ #,##0.00" /> </td>
                <td class="text-right"><fmt:formatNumber value="${item.discount}" type="percent" /></td>
                <td class="text-right"><fmt:formatNumber value="${item.newPrice}" pattern="$ #,##0.00" /></td>
                <td class="text-right"><input type="number" min="1" max="50" class="form-control" value="${item.quantity}" name="quantity" /></td>
                <td class="text-right"><fmt:formatNumber value="${item.cost}" pattern="$ #,##0.00" /></td>
                <td>
                    <button type="submit" formaction="<c:url value="/cart/update.do" />" class="btn btn-sm btn-outline-success" /><i class="bi bi-arrow-repeat"></i> Update </button> <hr>
                    <button type="submit" formaction="<c:url value="/cart/delete.do" />" class="btn btn-sm btn-outline-danger" /><i class="bi bi-cart-dash"></i> Delete </button>
                </td>
            </tr>
        </form>
        <c:set var="total" value="${total + item.cost}" />
    </c:forEach>
</tbody>
<!--cach 1-->
<tfoot>
    <tr>
        <th></th>
        <th></th>
        <th></th>
        <th></th>
        <th></th>
        <th></th>
        <th></th>
        <th class="text-right">Total</th>
        <th class="text-right"><fmt:formatNumber value="${total}" type="currency" /></th>
        <th><a href="<c:url value="/cart/empty.do" />" class="btn btn-sm btn-outline-danger" />
            <i class="bi bi-cart-dash"></i> 
            Empty your cart 
            </a> 
        </th>
        <th>
            <a href="<c:url value="/cart/checkOut.do" />" class="btn btn-sm btn-outline-success" />
            <i class="bi bi-bag-check-fill"></i>
            Check out
            </a> 
        </th>
    </tr>
</tfoot>
</table>
<h5 style="font-style: italic" class="d-flex justify-content-center text-danger">${message}</h5>


