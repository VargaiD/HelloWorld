<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<t:page title="Log in">
<jsp:attribute name="head">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/formStyle.css" media="screen"/>
</jsp:attribute>
<jsp:attribute name="body">

<div class="row">
    <div class="col-6 col-md-6 col-md-offset-3 col-offset-3">
        <form class="form" method="POST" action="${pageContext.request.contextPath}/auth/login/">
            <h2 class="form-heading">Please sign in</h2>

            <label for="email" class="sr-only">Email:</label>
            <input type="email" id="email" name="email" class="form-control" placeholder="Enter E-mail..." required autofocus/>

            <label for="password" class="sr-only">Password:</label>
            <input type="password" id="password" name="password" class="form-control" placeholder="Enter Password..." required/>

            <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
        </form>
    </div>
</div>

<div class="row">
    <div class="col-8 col-md-8 col-md-offset-2">
        <h4>List of preregistered test users:</h4>
        <table class="table">
            <thead>
            <tr>
                <th>user mail</th>
                <th>user password</th>
                <th>user role</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>admin@google.com</td>
                <td>password</td>
                <td>admin</td>
            </tr>
            <tr>
                <td>user@google.com</td>
                <td>password</td>
                <td>user</td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
    <div class="row">
        <div class="col-4 col-md-4 col-md-offset-4">
            <label for="register">Not yet registered?</label>
            <a id="register" class="btn btn-lg btn-success" href="${pageContext.request.contextPath}/user/create">Register</a>
        </div>
    </div>
</jsp:attribute>
</t:page>