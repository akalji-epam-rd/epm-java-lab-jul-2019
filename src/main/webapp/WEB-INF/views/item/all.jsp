<%@ page pageEncoding="utf-8" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="../../../static/css/bootstrap.min.css">
<script src="../../../static/js/bootstrap-native.min.js"></script>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Items</title>
</head>

<body onload="onload()">

<jsp:include page='/WEB-INF/views/header.jsp'>
    <jsp:param name="articleId" value=""/>
</jsp:include>


<div class="container">
    <h2 class="trn">Library items</h2>


    <button id="addItemBtn" class="btn btn-primary trn" data-trn-key="Add Item" type="button" data-toggle="modal"
            data-target="#modalAddItem">Add
        Item
    </button>

    <table id="items_table" class="table table-striped">
        <thead>
        <tr>
            <td class="trn" data-trn-key="Book">Book</td>
            <td class="trn" data-trn-key="User">User</td>
            <td class="trn" data-trn-key="Status">Status</td>
            <td class="trn" data-trn-key="Date">Date</td>
            <td></td>
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
                    <button class="btn btn-danger trn" data-trn-key="Delete Item"
                            onclick="removeItem(${item.id}, this)">Delete item
                    </button>
                </td>
                <td>
                    <button class="btn btn-primary trn" data-trn-key="Edit Item" type="button" data-toggle="modal"
                            data-target="#modalEditItem"
                            onclick="updateItem(${item.id}, this)">Edit Item
                    </button>
                </td>
                <td>
                    <c:if test="${item.status.name == 'ordered'}">
                        <button class="btn btn-default" onclick="confirmOrder(${item.id})">
                            Confirm order
                        </button>
                    </c:if>
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
                <h4 class="modal-title trn" id="modalEditItemLabel" data-trn-key="Edit Item">Edit item</h4>
            </div>
            <div class="modal-body">
                <form id="updateItemForm">
                    <input hidden name="itemId" value="-1">
                    <div class="form-group">
                        <label for="editBookId" class="trn" data-trn-key="Select book">Select book:</label>
                        <select class="form-control" name="bookId" id="editBookId">
                            <option value="-1" class="trn" data-trn-key="Select book">Select book</option>
                            <c:forEach items="${books}" var="book">
                                <option value="${book.id}">${book.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="editUserId" class="trn" data-trn-key="Select user">Select user:</label>
                        <select class="form-control" name="userId" id="editUserId">
                            <option value="-1" class="trn" data-trn-key="Select user">Select user</option>
                            <c:forEach items="${users}" var="user">
                                <option value="${user.id}">${user.name} ${user.lastName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="editStatusId" class="trn" data-trn-key="Select status">Select status:</label>
                        <select class="form-control" name="status" id="editStatusId">
                            <option value="-1" class="trn" data-trn-key="Select status">Select status</option>
                            <c:forEach items="${statuses}" var="status">
                                <option value="${status.id}">${status.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default trn" data-trn-key="Close"
                                onclick="cancelUpdate()" data-dismiss="modal">
                            Close
                        </button>
                        <button type="button" class="btn btn-primary trn" data-trn-key="Edit item"
                                onclick="submitUpdateItem()">Edit item
                        </button>
                    </div>
                </form>
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
                <h4 class="modal-title trn" id="modalAddItemLabel" data-trn-key="Add new Item">Add new Item</h4>
            </div>
            <div class="modal-body">
                <form id="addItemForm">
                    <input hidden name="itemId" value="-1">
                    <div class="form-group">
                        <label for="addBookId" class="trn" data-trn-key="Select book">Select book:</label>
                        <select class="form-control" name="bookId" id="addBookId">
                            <option value="-1" class="trn" data-trn-key="Select book">Select book</option>
                            <c:forEach items="${books}" var="book">
                                <option value="${book.id}">${book.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="addUserId" class="trn" data-trn-key="Select user">Select user:</label>
                        <select class="form-control" name="userId" id="addUserId">
                            <option value="-1" class="trn" data-trn-key="Select user">Select user</option>
                            <c:forEach items="${users}" var="user">
                                <option value="${user.id}">${user.name} ${user.lastName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="addStatusId" class="trn" data-trn-key="Select status">Select status:</label>
                        <select class="form-control" name="status" id="addStatusId">
                            <option value="-1" class="trn" data-trn-key="Select status">Select status</option>
                            <c:forEach items="${statuses}" var="status">
                                <option value="${status.id}">${status.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default trn" data-dismiss="modal" data-trn-key="Close">
                            Close
                        </button>
                        <button type="button" class="btn btn-primary trn" onclick="addItem()" data-trn-key="Save Item">
                            Save Item
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script src="../../../static/js/bootstrap-native.min.js"></script>
<script language="JavaScript1.6" type="application/javascript">

    let currentPage = 1;

    function removeItem(id, element) {

        fetch('/items/delete/' + id, {
            method: 'DELETE'
        }).then(function (response) {
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
        const confirmationButtonCell = newRow.insertCell(6);

        const removeButton = document.createElement('button');
        removeButton.className = 'btn btn-danger trn';
        removeButton.setAttribute("data-trn-key", "Delete Item");
        removeButton.innerHTML = "Delete Item";
        removeButton.onclick = () => removeItem(item.id, removeButton);
        removeButtonCell.appendChild(removeButton);

        // initialize modal
        const updateButton = document.createElement('button');
        updateButton.className = 'btn btn-primary trn';
        updateButton.setAttribute("data-trn-key", "Edit Item");
        updateButton.type = "button";
        updateButton.setAttribute("data-toggle", "modal");
        updateButton.setAttribute("data-target", "#modalEditItem");
        updateButton.innerHTML = "Edit Item";
        updateButton.onclick = () => updateItem(item.id, updateButton);
        updateButtonCell.appendChild(updateButton);
        const modalInstance = new Modal(updateButton);

        if (item.status.name === 'ordered') {
            const confirmationButton = document.createElement('button');
            confirmationButton.className = 'btn btn-default';
            confirmationButton.type = "button";
            confirmationButton.innerHTML = "Confirm order";
            confirmationButton.onclick = () => confirmOrder(item.id);
            confirmationButtonCell.appendChild(confirmationButton);
        }
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
            form.elements["userId"].value = item.user.id > 0 ? item.user.id : -1;
            form.elements["status"].value = item.status.id;
        });
    }

    function confirmOrder(id) {

        fetch('/items/confirmOrder/' + id, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                itemId: id
            })
        }).then(function (response) {
            return response.text();
        }).then(function (data) {
            const items = JSON.parse(data);
            updateTable(items);
        })

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

</body>
</html>