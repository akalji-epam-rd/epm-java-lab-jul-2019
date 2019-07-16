<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="../../static/css/bootstrap.min.css">

<html>
<head>
    <title>Hello Page</title>
</head>
<script language="JavaScript1.6" type="application/javascript">

    function removeItem(id, element) {

        const rowIndex = element.parentElement.parentElement.rowIndex;
        const table = document.getElementById('items_table');
        fetch('/items/delete/' + id, {method: 'DELETE'})
            .then(function (response) {
                return response.text();
            }).then(function (json) {
            console.log(json);
            table.deleteRow(rowIndex);
        });
    }

    function addItem() {

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
            console.log(data);
            const item = JSON.parse(data);
            createRow(item);
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
        removeButton.onclick = () => removeItem(item.id, removeButton);
        const span = document.createElement('span');
        span.className = 'fa fa-remove';
        removeButton.appendChild(span);
        removeButtonCell.appendChild(removeButton);

        const updateButton = document.createElement('button');
        updateButton.className = 'btn-default';
        updateButton.onclick = () => updateItem(item.id, updateButton);
        const updateSpan = document.createElement('span');
        updateSpan.className = 'glyphicon-pencil';
        updateButton.appendChild(updateSpan);
        updateButtonCell.appendChild(updateButton);
    }

    function updateItem(id, element) {

        const form = document.getElementById('updateItemForm');
        let item;
        fetch('/items/get/' + id, {
            method: 'GET'
        }).then(function (response) {
            return response.text();
        }).then(function (data) {
            console.log(data);
            item = JSON.parse(data);
            form.elements["itemId"].value = item.id;
            form.elements["bookId"].value = item.book.id;
            form.elements["userId"].value = item.user.id;
            form.elements["status"].value = item.status.id;
        });
    }

    function submitUpdateItem() {

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
            console.log(data);
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

</script>

<body>

<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">Library</a>
        </div>
        <ul class="nav navbar-nav">
            <li class="active"><a href="<c:url value='/book/all'/>">Books</a></li>
            <li><a href="<c:url value='/allItems'/>">Items</a></li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <li><a href="<c:url value='/logout'/>"><span class="glyphicon glyphicon-log-in"></span>Logout</a></li>
        </ul>
    </div>
</nav>

<div class="container">
    <h2>Library items</h2>


    <div>
        <table id="items_table" class="table table-striped">
            <tr>
                <td>Book</td>
                <td>User</td>
                <td>Status</td>
                <td>Date</td>
                <td></td>
                <td></td>
            </tr>
            <c:forEach items="${items}" var="item">
                <tr>
                    <td><c:out value="${item.book.name}"/></td>
                    <td><c:out value="${item.user.name} ${item.user.lastName}"/></td>
                    <td><c:out value="${item.status.name}"/></td>
                    <td><c:out value="${item.date}"/></td>
                    <td>
                        <button class="btn btn-danger" onclick="removeItem(${item.id}, this)">
                            <span class="fa fa-remove"></span>
                        </button>
                    </td>
                    <td>
                        <button class="btn-default" onclick="updateItem(${item.id}, this)">
                            <span class="glyphicon-pencil"></span>
                        </button>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>


    <div>
        <form id="addItemForm">
            <input hidden name="itemId" value="-1">
            <label>
                Book:
                <select name="bookId">
                    <option value="-1">Choose book</option>
                    <c:forEach items="${books}" var="book">
                        <option value="${book.id}">${book.name}</option>
                    </c:forEach>
                </select>
            </label>
            <label>
                User:
                <select name="userId">
                    <option value="-1">Choose user</option>
                    <c:forEach items="${users}" var="user">
                        <option value="${user.id}">${user.name} ${user.lastName}</option>
                    </c:forEach>
                </select>
            </label>
            <label>
                Status:
                <select name="status">
                    <option value="-1">Choose status</option>
                    <c:forEach items="${statuses}" var="status">
                        <option value="${status.id}">${status.name}</option>
                    </c:forEach>
                </select>
            </label>
            <button type="button" class="btn btn-default" onclick="addItem()">Add Item</button>
        </form>
    </div>

    <div>
        <form id="updateItemForm">
            <input hidden name="itemId" value="-1">
            <label>
                Book:
                <select name="bookId">
                    <option value="-1">Choose book</option>
                    <c:forEach items="${books}" var="book">
                        <option value="${book.id}">${book.name}</option>
                    </c:forEach>
                </select>
            </label>
            <label>
                User:
                <select name="userId">
                    <option value="-1">Choose user</option>
                    <c:forEach items="${users}" var="user">
                        <option value="${user.id}">${user.name} ${user.lastName}</option>
                    </c:forEach>
                </select>
            </label>
            <label>
                Status:
                <select name="status">
                    <option value="-1">Choose status</option>
                    <c:forEach items="${statuses}" var="status">
                        <option value="${status.id}">${status.name}</option>
                    </c:forEach>
                </select>
            </label>
            <button type="button" class="btn btn-default" onclick="submitUpdateItem()">Update Item</button>
            <button type="button" class="btn btn-default" onclick="cancelUpdate()">Cancel</button>
        </form>
    </div>
</div>
</body>
</html>