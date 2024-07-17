<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>View Customers</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css" />">
</head>
<body>
    <h1>Customer List</h1>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Street</th>
                <th>Address</th>
                <th>City</th>
                <th>State</th>
                <th>Email</th>
                <th>Phone</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${customers.content}" var="customer">
                <tr>
                    <td>${customer.id}</td>
                    <td>${customer.firstName}</td>
                    <td>${customer.lastName}</td>
                    <td>${customer.street}</td>
                    <td>${customer.address}</td>
                    <td>${customer.city}</td>
                    <td>${customer.state}</td>
                    <td>${customer.email}</td>
                    <td>${customer.phone}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <div>
        <spring:eval var="page" expression="${customers}" />
        <c:if test="${not empty page}">
            <c:url value="" var="url">
                <c:param name="size" value="${page.size}" />
                <c:forEach items="${page.parameters}" var="param">
                    <c:param name="${param.key}" value="${param.value}" />
                </c:forEach>
            </c:url>
            <c:if test="${page.number > 0}">
                <a href="${url}&page=${page.number - 1}">Previous</a>
            </c:if>
            <c:forEach begin="0" end="${page.totalPages - 1}" varStatus="i">
                <c:choose>
                    <c:when test="${i.index == page.number}">
                        ${i.index + 1}
                    </c:when>
                    <c:otherwise>
                        <a href="${url}&page=${i.index}">${i.index + 1}</a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <c:if test="${page.number + 1 < page.totalPages}">
                <a href="${url}&page=${page.number + 1}">Next</a>
            </c:if>
        </c:if>
    </div>

</body>
</html>
