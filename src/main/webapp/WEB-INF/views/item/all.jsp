<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="../../../static/css/bootstrap.min.css">
<script src="../../../static/js/bootstrap-native.min.js"></script>

<html>
<head>
    <title>Hello Page</title>
</head>
<script language="JavaScript1.6" type="application/javascript">

    let currentPage = 1;

    function removeItem(id, element) {
        const rowIndex = element.parentElement.parentElement.rowIndex;
        const table = document.getElementById('items_table');
        fetch('/items/delete/' + id, {method: 'DELETE'})
            .then(function (response) {
                return response.text();
            }).then(function (data) {
            const result = JSON.parse(data);
            const size = result.total;
            updateTable(result.items);
            createPaginationButtons(size, result.currentPage);
        });
    }

    function addItem() {

        // initialize modal
        const modal = document.getElementById('modalAddItem');
        const modalInstance = new Modal(modal);

        const form = document.forms["addItemForm"];
        const itemId = form.elements["itemId"].value;
        const bookId = form.elements["bookId"].value;
        const userId = form.elements["userId"].value;
        const status = form.elements["status"].value;

        fetch('/items/add', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                itemId: itemId,
                bookId: bookId,
                userId: userId,
                status: status
            })
        }).then(function (response) {
            return response.text();
        }).then(function (data) {
            modalInstance.hide();
            const result = JSON.parse(data);
            const size = result.total;
            updateTable(result.items);
            createPaginationButtons(size, result.currentPage);
        })
    }

    function createRow(item) {
        const table = document.getElementById('items_table');
        const newRow = table.insertRow();
        newRow.insertCell(0).innerHTML = item.book.name;
        if (item.user.name) {
            newRow.insertCell(1).innerHTML = item.user.name + ' ' + item.user.lastName;
        } else {
            newRow.insertCell(1);
        }
        newRow.insertCell(2).innerHTML = item.status.name;
        newRow.insertCell(3).innerHTML = item.date;
        const removeButtonCell = newRow.insertCell(4);
        const updateButtonCell = newRow.insertCell(5);

        const removeButton = document.createElement('button');
        removeButton.className = 'btn btn-danger';
        removeButton.innerHTML = "Delete Item";
        removeButton.onclick = () => removeItem(item.id, removeButton);
        removeButtonCell.appendChild(removeButton);

        // initialize modal
        const updateButton = document.createElement('button');
        updateButton.className = 'btn btn-primary';
        updateButton.type = "button";
        updateButton.setAttribute("data-toggle", "modal");
        updateButton.setAttribute("data-target", "#modalEditItem");
        updateButton.innerHTML = "Edit Item";
        updateButton.onclick = () => updateItem(item.id, updateButton);
        updateButtonCell.appendChild(updateButton);
        const modalInstance = new Modal(updateButton);
    }

    function updateItem(id, element) {

        const form = document.getElementById('updateItemForm');
        let item;
        fetch('/items/get/' + id, {
            method: 'GET'
        }).then(function (response) {
            return response.text();
        }).then(function (data) {
            item = JSON.parse(data);
            form.elements["itemId"].value = item.id;
            form.elements["bookId"].value = item.book.id;
            form.elements["userId"].value = item.user.id > 0 ? item.user.id : -1 ;
            form.elements["status"].value = item.status.id;
        });
    }

    function submitUpdateItem() {

        // initialize modal
        const modal = document.getElementById('modalEditItem');
        const modalInstance = new Modal(modal);

        const form = document.getElementById('updateItemForm');

        const itemId = form.elements["itemId"].value;
        const bookId = form.elements["bookId"].value;
        const userId = form.elements["userId"].value;
        const status = form.elements["status"].value;


        fetch('/items/update/' + itemId, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                itemId: itemId,
                bookId: bookId,
                userId: userId,
                status: status
            })
        }).then(function (response) {
            return response.text();
        }).then(function (data) {
            modalInstance.hide();
            const items = JSON.parse(data);
            updateTable(items);
        })

    }

    function updateTable(items) {
        const table = document.getElementById('items_table');
        const rowsCount = table.rows.length;
        for (let i = rowsCount - 1; i > 0; i--) {
            table.deleteRow(i);
        }

        for (let i = 0; i < items.length; i++) {
            createRow(items[i]);
        }
    }

    function cancelUpdate() {
        const form = document.getElementById('updateItemForm');
        form.elements["itemId"].value = -1;
        form.elements["bookId"].value = -1;
        form.elements["userId"].value = -1;
        form.elements["status"].value = -1;
    }

    function onload() {

        let size;
        fetch('/items/getAll', {
            method: 'GET'
        }).then(function (response) {
            return response.text();
        }).then(function (data) {
            const result = JSON.parse(data);
            size = result.total;
            currentPage = result.currentPage;
            if (size) {
                createPaginationButtons(size, currentPage);
            }
        });
    }

    function createPaginationButtons(size, currentPage) {

        const paginationUL = document.getElementById("itemsPagination");

        while (paginationUL.firstChild) {
            paginationUL.removeChild(paginationUL.firstChild);
        }

        const countLI = Math.ceil(size / 10);

        for (let i = 0; i < countLI; i++) {
            const pageNumber = i + 1;
            const li = document.createElement('li');
            if (pageNumber === currentPage) {
                li.className = 'active'
            }
            const a = document.createElement('a');
            a.innerHTML = pageNumber;
            a.onclick = () => getAll(pageNumber);
            li.appendChild(a);
            paginationUL.appendChild(li);
        }

        paginationUL.className = "pagination";

    }

    function getAll(pageNumber) {

        const paginationUL = document.getElementById("itemsPagination");

        fetch('/items/getAll/' + pageNumber, {
            method: 'GET'
        }).then(function (response) {
            return response.text();
        }).then(function (data) {
            const result = JSON.parse(data);
            const size = result.total;
            const items = result.items;
            currentPage = result.currentPage;
            updateTable(items);
            createPaginationButtons(size, currentPage);

        });
    }

</script>

<body onload="onload()">

<jsp:include page='/WEB-INF/views/header.jsp'>
    <jsp:param name="articleId" value=""/>
</jsp:include>

<div class="container">
    <h2>Library items</h2>

    <button id="addItemBtn" class="btn btn-primary" type="button" data-toggle="modal" data-target="#modalAddItem">Add Item
    </button>

    <div>
        <table id="items_table" class="table table-striped">
            <thead>
            <tr>
                <td>Book</td>
                <td>User</td>
                <td>Status</td>
                <td>Date</td>
                <td></td>
                <td></td>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${items}" var="item">
                <tr>
                    <td><c:out value="${item.book.name}"/></td>
                    <td><c:out value="${item.user.name} ${item.user.lastName}"/></td>
                    <td><c:out value="${item.status.name}"/></td>
                    <td><c:out value="${item.date}"/></td>
                    <td>
                        <button class="btn btn-danger" onclick="removeItem(${item.id}, this)">Delete item
                        </button>
                    </td>
                    <td>
                        <button class="btn btn-primary" type="button" data-toggle="modal" data-target="#modalEditItem" onclick="updateItem(${item.id}, this)">Edit Item
                        </button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <div>
            <ul id="itemsPagination" class="pagination">
            </ul>
        </div>
    </div>


    <div id="modalEditItem" class="modal fade" role="dialog" aria-labelledby="modalEditItemLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">×</span></button>
                    <h4 class="modal-title" id="modalEditItemLabel">Edit item data</h4>
                </div>
                <div class="modal-body">
                    <form id="updateItemForm">
                        <input hidden name="itemId" value="-1">
                        <div class="form-group">
                            <label for="editBookId">Select book:</label>
                            <select class="form-control" name="bookId" id="editBookId">
                                <option value="-1">Choose book</option>
                                <c:forEach items="${books}" var="book">
                                    <option value="${book.id}">${book.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="editUserId">Select user:</label>
                            <select class="form-control" name="userId" id="editUserId">
                                <option value="-1">Choose user</option>
                                <c:forEach items="${users}" var="user">
                                    <option value="${user.id}">${user.name} ${user.lastName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="editStatusId">Select status:</label>
                            <select class="form-control" name="status" id="editStatusId">
                                <option value="-1">Select status</option>
                                <c:forEach items="${statuses}" var="status">
                                    <option value="${status.id}">${status.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" onclick="cancelUpdate()" data-dismiss="modal">Close</button>
                            <button type="button" class="btn btn-primary" onclick="submitUpdateItem()">Update item data</button>
                        </div>
                    </form>
                </div>
            </div>

        </div>
    </div>
</div>


<div id="modalAddItem" class="modal fade" role="dialog" aria-labelledby="modalAddItemLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">×</span></button>
                <h4 class="modal-title" id="modalAddItemLabel">Add new Item</h4>
            </div>
            <div class="modal-body">
                <form id="addItemForm">
                    <input hidden name="itemId" value="-1">
                    <div class="form-group">
                        <label for="addBookId">Select book:</label>
                        <select class="form-control" name="bookId" id="addBookId">
                            <option value="-1">Choose book</option>
                            <c:forEach items="${books}" var="book">
                                <option value="${book.id}">${book.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="addUserId">Select user:</label>
                        <select class="form-control" name="userId" id="addUserId">
                            <option value="-1">Choose user</option>
                            <c:forEach items="${users}" var="user">
                                <option value="${user.id}">${user.name} ${user.lastName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="addStatusId">Select status:</label>
                        <select class="form-control" name="status" id="addStatusId">
                            <option value="-1">Select status</option>
                            <c:forEach items="${statuses}" var="status">
                                <option value="${status.id}">${status.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary" onclick="addItem()">Save Item</button>
                    </div>
                </form>
            </div>

        </div>
    </div>
</div>

</body>
</html>