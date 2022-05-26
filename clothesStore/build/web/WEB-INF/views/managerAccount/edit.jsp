

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<h2 class="d-flex justify-content-center">Edit Account</h2>
<hr/>
<div class="row d-flex justify-content-center">
    <div class="px-5 col-lg-6 ">
        <form action="${pageContext.request.contextPath}/managerAccount/update.do">
            <div class="mb-3 mt-3">
                <label class="form-label h3">Id: ${account.id}</label>
                <input type="hidden" class="form-control" value="${account.id}"name="id">
            </div>
            <div class="mb-3 mt-3">
                <label class="form-label h3">UserName ${account.userName}</label>
            </div>
            <div class="mb-3">
                <label for="name" class="form-label">Name: </label>
                <input type="text" class="form-control" value="${account.name}" id="name" placeholder="Enter description" name="name">
            </div>
            <div class="mb-3">
                <label for="address" class="form-label">address</label>
                <input type="text" class="form-control" value="${account.address}" id="address" placeholder="Enter price" name="address">
            </div>
            <div class="mb-3">
                <label for="phone" class="form-label">phone</label>
                <input type="text" class="form-control" value="${account.phone}" id="phone" placeholder="Enter discount" name="phone">
            </div>
            <div class="mb-3">
                <label for="email" class="form-label">email</label>
                <input type="text" class="form-control" value="${account.email}" id="email" placeholder="Enter price" name="email">
            </div>
            <div class="mb-3">
                <label for="role" class="form-label">role</label>
                <select id="role" name="role" class="form-select" aria-label="Default select example">
                    ${list}
                    <c:forEach var="role" items="${list}" >
                        <option <c:if test="${role == account.role}" >selected</c:if> value="${role}">${role}</option>
                    </c:forEach>
                </select>
            </div>

            <button type="submit" class="btn btn-sm btn-outline-success" name="op" value="update"><i class="bi bi-check-circle"></i> Update</button>
            <button type="submit" class="btn btn-sm btn-outline-danger" name="op" value="cancel"><i class="bi bi-x-circle"></i> Cancel</button>
        </form>
        <div style="font-style: italic" class="mt-3 text-danger">${errorMessage}</div>
    </div>
</div>