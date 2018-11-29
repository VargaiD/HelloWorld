<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<t:page title="Game Recommender" activeNavbarItem="Game">

<jsp:attribute name="body">
    <div class="container">
        <h1>Download a game</h1>
        <form:form class="form" method="POST" action="${pageContext.request.contextPath}/game/download">
            <label for="steamId" class="sr-only">SteamId:</label>
            <input type="text" id="steamId" name="steamId" class="form-control" placeholder="Enter SteamId" required autofocus/>

            <label for="minReviews" class="sr-only">Min Reviews:</label>
            <input type="number" id="minReviews" name="minReviews" class="form-control" placeholder="Enter minReviews count" required/>

            <button class="btn btn-lg btn-primary btn-block" type="submit">Download</button>
        </form:form>
    </div>
</jsp:attribute>

</t:page>