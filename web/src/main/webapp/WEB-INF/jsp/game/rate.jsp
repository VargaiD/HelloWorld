<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<t:page title="Rate Games" activeNavbarItem="Rate">

<jsp:attribute name="body">
    <div class="container">
        <h1>Choose one game you like, step: ${step + 1}/10</h1>
            <table>
            <c:forEach items="${games}" var="game">
                <tr>
                    <td>
                        <form:form class="form" method="POST" action="${pageContext.request.contextPath}/game/rate/${step}/${game.id}">
                          <button class="btn btn-lg btn-primary btn-block" type="submit">${game.name}</button>
                        </form:form>
                    </td>
                </tr>
            </c:forEach>
            </table>
    </div>
</jsp:attribute>

</t:page>