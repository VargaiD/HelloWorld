<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

 <t:page title="Registration" activeNavbarItem="Users">

<jsp:attribute name="head">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/formStyle.css" media="screen"/>
</jsp:attribute>

<jsp:attribute name="body">
        <div class="row">
            <div class="panel panel-info">
                <div class="panel-heading">
                    <h3 class="panel-title">Registration</h3>
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class=" col-md-9 col-lg-9 ">
                            <form:form class="form" method="POST" action="${pageContext.request.contextPath}/user/create" modelAttribute="newUser">

                                <label for="name">Name</label>
                                <input id="name" type="text" class="form-control" name="name" required/>

                                <label for="email">E-mail</label>
                                <input id="email" type="email" class="form-control" name="email" required/>

                                <c:if test="${showAdminField != null && showAdminField}">
                                    <label for="isAdmin">Is Admin</label>
                                    <select name="isAdmin" id="isAdmin" class="form-control" required>
                                        <option>Yes</option>
                                        <option>No</option>
                                    </select>
                                </c:if>

                                <label for="password">Password</label>
                                <input id="password" type="password" class="form-control" name="password" required/>

                                <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
                                <a class="btn btn-lg btn-default btn-block" href="${pageContext.request.contextPath}/">Cancel</a>
                            </form:form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
</jsp:attribute>

</t:page>