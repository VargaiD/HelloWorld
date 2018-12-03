<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<t:page title="Users" activeNavbarItem="Users">

<jsp:attribute name="body">
    <div class="container">

        <table class="table">
            <thead>
            <tr>
                <th>#</th>
                <th>Name</th>
                <th>E-mail</th>
                <th>Details</th>
                <th>Edit</th>
                <th>Delete</th>
                <th style="width: 36px;"></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${allUsers}" var="user">
                <tr>
                    <td>${user.id}</td>
                    <td>${user.name}</td>
                    <td>${user.email}</td>
                    <td><a href="${pageContext.request.contextPath}/user/detail/${user.id}" class="btn btn-sm btn-primary glyphicon glyphicon-search"></a></td>
                    <td><a href="${pageContext.request.contextPath}/user/edit/${user.id}" class="btn btn-sm btn-primary glyphicon glyphicon-pencil"></a></td>
                    <td><a href="${pageContext.request.contextPath}/user/delete/${user.id}" class="btn btn-sm btn-danger glyphicon glyphicon-remove"></a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <c:if test="${showCreateNewUser != null && showCreateNewUser}">
        <div class="row">
            <a href="${pageContext.request.contextPath}/user/create" class="btn btn-success glyphicon glyphicon-plus-sign"><font face="arial">  New user</font></button></a>

        </div>
    </c:if>
</jsp:attribute>

</t:page>
