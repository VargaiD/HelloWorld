<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<t:page title="Games" activeNavbarItem="Recommend">

<jsp:attribute name="body">
    <div class="container">

        <table class="table">
            <thead>
            <tr>
                <th>Name</th>
                <th>Description</th>
                <th>Genres</th>
                <th>Image</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${games}" var="game">
                <tr>
                    <td>${game.name}</td>
                    <td>${game.shortDescription}</td>
                    <td>${genres.get(game.id)}</td>
                    <td><img src="${pictures.get(game.id)}"></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</jsp:attribute>

</t:page>
