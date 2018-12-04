<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<t:page title="Game Recommender" activeNavbarItem="Home">

<jsp:attribute name="body">
    <div class="container">
        <h1>Welcome to Steam Game Recommender</h1>
        <h2>Create an account, rate 9 games and get a recommendation !</h2>
        <form:form class="form" method="POST" action="${pageContext.request.contextPath}">
            <button class="btn btn-lg btn-primary btn-block" type="submit">Download</button>
        </form:form>
        <form:form class="form" method="POST" action="${pageContext.request.contextPath}/descriptions">
            <button class="btn btn-lg btn-primary btn-block" type="submit">Download Descriptions</button>
        </form:form>
    </div>
</jsp:attribute>

</t:page>