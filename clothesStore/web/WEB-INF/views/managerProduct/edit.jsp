

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<h1>Edit Product</h1>
<hr/>
<div class="row">
    <div class="col">
        <form action="${pageContext.request.contextPath}/managerProduct/update.do">
            <div class="mb-3 mt-3">
                <label for="id" class="form-label h3">Id: ${product.id}</label>
                <input type="hidden" class="form-control" value="${product.id}" id="id" placeholder="Enter id" name="id">
            </div>
            
            <div class="mb-3">
                <label for="description" class="form-label">Description:</label>
                <input type="text" class="form-control" value="${product.description}" id="description" placeholder="Enter description" name="description">
            </div>
            <div class="mb-3">
                <label for="price" class="form-label">price:</label>
                <input type="text" class="form-control" value="${product.price}" id="price" placeholder="Enter price" name="price">
            </div>
            <div class="mb-3">
                <label for="discount" class="form-label">discount</label>
                <input type="text" class="form-control" value="${product.discount}" id="discount" placeholder="Enter discount" name="discount">
            </div>
            <div class="mb-3">
                <label for="category_id" class="form-label">Category:</label>
                <select id="category_id" name="category_id" class="form-select" aria-label="Default select example">
                    <c:forEach var="category" items="${listC}" >
                        <option  <c:if test="${category.id == product.categoryId.id}" >selected</c:if> value="${category.id}">${category.name}</option>
                    </c:forEach>

                </select>
            </div>
            <button type="submit" class="btn btn-sm btn-outline-success" name="op" value="update"><i class="bi bi-check-circle"></i> Update</button>
            <button type="submit" class="btn btn-sm btn-outline-danger" name="op" value="cancel"><i class="bi bi-x-circle"></i> Cancel</button>
        </form>
        <div style="font-style: italic" class="mt-3 text-danger">${errorMessage}</div>
    </div>
    <div class="col"></div>
</div>