<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<t:page title="Rate Games" activeNavbarItem="Rate">

<jsp:attribute name="body">
    <div class="container">
        <h1>Choose one game you like, step: ${step + 1}/9</h1>
            <table class="table">
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Description</th>
                    <th>Genres</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${games}" var="game">
                    <tr>
                        <td>${game.name}</td>
                        <td>${game.shortDescription}</td>
                        <td>${genres.get(game.id)}</td>
                        <td>
                            <form:form class="form" method="POST" action="${pageContext.request.contextPath}/game/rate/${step}/${game.id}">
                              <button class="btn btn-lg btn-primary btn-block" type="submit">Select</button>
                            </form:form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
    </div>
</jsp:attribute>

</t:page>