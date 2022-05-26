<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="row d-flex justify-content-center align-items-center">
    <form class="col-lg-6" action=" <c:url value="/cart/customer.do"/>" >
      <div class="row">
        <label for="exampleInputEmail1" class="form-label">User Name: ${user.name}</label>
    </div>
    <div class="row">
        <label for="shipTo" class="form-label">Ship to address</label>
        <input type="text" name="shipTo" class="form-control" placeholder="Enter address" value="${account.address}" required>
        <div style="font-style: italic" class="mt-3 text-danger">${message}</div>
    </div>
    <button type="submit" class="btn btn-primary">Submit</button>
</form>
</div>

