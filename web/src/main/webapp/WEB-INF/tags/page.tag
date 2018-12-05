<%@ tag pageEncoding="utf-8" dynamic-attributes="dynattrs" trimDirectiveWhitespaces="true" %>
<%@ attribute name="title" required="false" %>
<%@ attribute name="activeNavbarItem" required="false" %>
<%@ attribute name="head" fragment="true" %>
<%@ attribute name="body" fragment="true" required="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="${pageContext.request.locale}">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><c:out value="${title}"/></title>
    <!-- bootstrap loaded from content delivery network -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <jsp:invoke fragment="head"/>
</head>
<body>

<!--navbar-->
    <nav class="navbar navbar-inverse">
      <div class="container-fluid">
        <div class="navbar-header">
          <a class="navbar-brand" href="${pageContext.request.contextPath}/">Game Recommender</a>
        </div>

        <ul class="nav navbar-nav">
          <li class="${activeNavbarItem == 'Home' ? 'active' : ''}"><a href="${pageContext.request.contextPath}/">Home</a></li>
          <%--<li class="${activeNavbarItem == 'Game' ? 'active' : ''}"><a href="${pageContext.request.contextPath}/game/download">Games</a></li>--%>
          <!--admin view-->
          <c:if test="${authUser.isAdmin}">
          <li class="${activeNavbarItem == 'Users' ? 'active' : ''}"><a href="${pageContext.request.contextPath}/user/list">Users</a></li>
          </c:if>

          <!--user view-->
          <c:if test="${authUser != null && !authUser.isAdmin}">
              <li class="${activeNavbarItem == 'Rate' ? 'active' : ''}"><a href="${pageContext.request.contextPath}/game/rate/0">Rate Games</a></li>
              <li class="${activeNavbarItem == 'Recommend' ? 'active' : ''}"><a href="${pageContext.request.contextPath}/game/recommend">Get Recommendation</a></li>
              <li class="${activeNavbarItem == 'Games' ? 'active' : ''}"><a href="${pageContext.request.contextPath}/game/rated">Rated Games</a></li>
          </c:if>
        </ul>
        <ul class="nav navbar-nav navbar-right">

          <!--Login-->
          <c:if test="${empty authUser}">
            <li>
              <a href="${pageContext.request.contextPath}/auth/login">
              <span class="glyphicon glyphicon-log-in"></span>  Login</a>
            </li>
          </c:if>

          <!--Logout-->
          <c:if test="${not empty authUser}">
            <li class="${activeNavbarItem == 'Userpage' ? 'active' : ''}">
              <a href="${pageContext.request.contextPath}/user/detail/${authUser.id}">
              <span class="glyphicon glyphicon-user"></span>  ${authUser.name}</a>
            </li>
            <li>
              <a href="${pageContext.request.contextPath}/auth/logout">
              <span class="glyphicon glyphicon-log-out"></span>  Logout</a>
            </li>
          </c:if>
        </ul>

       </div>
     </nav>
<!--/navbar-->


<div class="container">
    <!--alerts-->
    <c:if test="${not empty alert_danger}">
        <div class="alert alert-danger fade in" role="alert">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
            <c:out value="${alert_danger}"/></div>
    </c:if>
    <c:if test="${not empty alert_info}">
        <div class="alert alert-info fade in" role="alert">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            <c:out value="${alert_info}"/>
        </div>
    </c:if>
    <c:if test="${not empty alert_success}">
        <div class="alert alert-success fade in" role="alert">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            <c:out value="${alert_success}"/>
        </div>
    </c:if>
    <c:if test="${not empty alert_warning}">
        <div class="alert alert-warning fade in" role="alert">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            <c:out value="${alert_warning}"/>
        </div>
    </c:if>

    <!--page body (required)-->
    <jsp:invoke fragment="body"/>
</div>


</body>
</html>