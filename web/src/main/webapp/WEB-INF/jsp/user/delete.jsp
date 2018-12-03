<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<!-- If User exists -->
<c:if test="${user != null}">

    <t:page title="${user.name}" activeNavbarItem="Users">

        <jsp:attribute name="body">
                <div class="row">
                    <form action="${pageContext.request.contextPath}/user/delete/${user.id}" method="POST">
                        <h2>Do you want to delete user ${user.name}</h2>
                        <button class="btn btn-primary" type="submit">Delete</button>
                        <a class="btn" href="${pageContext.request.contextPath}/user/list">Cancel</a>
                    </form>
                </div>
        </jsp:attribute>

    </t:page>

</c:if>

<!-- If User doesn't exist -->
<c:if test="${user == null}">
    <t:page title="User doesn't exist" activeNavbarItem="Users">
<jsp:attribute name="body">
    <div class="jumbotron">
        <h3>Sorry, no such user exists.</h3>
    </div>
</jsp:attribute>
    </t:page>
</c:if>