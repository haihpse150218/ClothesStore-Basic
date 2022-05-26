<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<form action=" <c:url value="/cart/customer.do"/>" >
      <div class="mb-3">
        <label for="exampleInputEmail1" class="form-label">User Name: ${user.name}</label>
    </div>
    <div class="mb-3">
        <label for="shipTo" class="form-label">Ship to address</label>
        <input type="text" name="shipTo" class="form-control" placeholder="Enter address" value="${account.address}" required>
        <div style="font-style: italic" class="mt-3 text-danger">${message}</div>
    </div>
    <button type="submit" class="btn btn-primary">Submit</button>
</form>
