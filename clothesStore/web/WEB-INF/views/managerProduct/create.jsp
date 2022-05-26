

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<h1 class="row d-flex justify-content-center align-items-center">Create Product</h1>
<hr/>
<div class="row d-flex justify-content-center align-items-center">
    <div class="col">
        <form action="${pageContext.request.contextPath}/managerProduct/insert.do">
            <div class="mb-3">
                <label for="description" class="form-label">Description:</label>
                <!--dung toan tu 3 ngoi truong hop product null tra ve lai description-->
                <input type="text" class="form-control" value="${product.description?product.description:description}" id="description" placeholder="Enter description" name="description">
            </div>
            <div class="mb-3">
                <label for="price" class="form-label">price:</label>
                <input type="text" class="form-control" value="${product.price == 0?product.price:price}" id="price" placeholder="Enter price" name="price">
            </div>
            <div class="mb-3">
                <label for="discount" class="form-label">discount</label>
                <input type="text" class="form-control" value="${product.discount == 0?product.discount:discount}" id="discount" placeholder="Enter discount" name="discount">
            </div>
            <div class="mb-3">
                <label for="category_id" class="form-label">Category:</label>
                <!--lay category tu database len-->
                <select id="category_id" name="category_id" class="form-select" aria-label="Default select example">
                    <c:forEach var="category" items="${listC}" >
                        <c:if test="${category.id == category_id}" >
                            <option selected value="${category.id}">${category.name}</option>
                        </c:if>
                        <option value="${category.id}">${category.name}</option>
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