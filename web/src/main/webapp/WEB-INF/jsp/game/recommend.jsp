<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<t:page title="Game Recommender" activeNavbarItem="Recommend">

<jsp:attribute name="body">
    <div class="container">
        <h1>Choose an algorithm</h1>
        <a href="${pageContext.request.contextPath}/game/collaborativePearson">
            <button class="btn btn-lg btn-primary btn-block">Collaborative Pearson</button>
        </a>
        <a href="${pageContext.request.contextPath}/game/collaborativeDice">
            <button class="btn btn-lg btn-primary btn-block">Collaborative Dice</button>
        </a>
        <a href="${pageContext.request.contextPath}/game/genreBased">
            <button class="btn btn-lg btn-primary btn-block">Genre Based</button>
        </a>
        <a href="${pageContext.request.contextPath}/game/genreBasedFrequent">
            <button class="btn btn-lg btn-primary btn-block">Genre Based Frequent</button>
        </a>
        <a href="${pageContext.request.contextPath}/game/descriptionBased">
            <button class="btn btn-lg btn-primary btn-block">Description Based</button>
        </a>
    </div>
</jsp:attribute>

</t:page>