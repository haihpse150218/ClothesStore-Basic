<h1 class="row d-flex justify-content-center align-items-center">Delete Confirmation</h1>
<hr/>
<div class="row d-flex justify-content-center align-items-center">
    <div class="col">
        <form action="delete.do">
            <br/>
            <h5>Do you want to delete that record? ${id}</h5>
            <br/>
            <input type="hidden" name="id" value="${id}" />
            <button type="submit" class="btn btn-sm btn-outline-success" name="op" value="yes"><i class="bi bi-check-circle"></i> Yes</button>
            <button type="submit" class="btn btn-sm btn-outline-danger" name="op" value="no"><i class="bi bi-x-circle"></i> No</button>
        </form>
        <div style="font-style: italic" class="mt-3 text-danger">${errorMessage}</div>
    </div>
    <div class="col"></div>
</div>    
