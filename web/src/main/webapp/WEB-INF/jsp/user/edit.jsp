<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!-- If User exists -->
<c:if test="${editUser != null}">

    <t:page title="${editUser.name}" activeNavbarItem="Users">

    <jsp:attribute name="head">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/formStyle.css" media="screen"/>
    </jsp:attribute>

    <jsp:attribute name="body">
            <div class="row">
                <div class="panel panel-info">
                    <div class="panel-heading">
                        <h3 class="panel-title">${editUser.name}</h3>
                        <h3 class="panel-title"></h3>
                    </div>
                    <div class="panel-body">
                        <div class="row">
                            <div class=" col-md-9 col-lg-9 ">
                                <form:form class="form" method="POST" action="${pageContext.request.contextPath}/user/edit/${editUser.id}" modelAttribute="editUser" >

                                    <label for="userId">ID</label>
                                    <input id="userId" type="text" class="form-control"  value="${editUser.id}" readonly/>

                                    <label for="name">Name</label>
                                    <input id="name" type="text" class="form-control" value="${editUser.name}" name="name" required/>

                                    <label for="email">E-mail</label>
                                    <input id="email" type="email" class="form-control" value="${editUser.email}" name="email" required/>

                                    <label for="isAdmin">Is Admin</label>
                                    <input id="isAdmin" type="text" class="form-control" value="${editUser.isAdmin ? 'Yes' : 'No'}" name="isAdmin" readonly/>

                                    <button class="btn btn-lg btn-primary btn-block" type="submit">Save</button>
                                    <a class="btn btn-lg btn-default btn-block" href="${pageContext.request.contextPath}/user/detail/${editUser.id}">Cancel</a>
                                </form:form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
    </jsp:attribute>

    </t:page>

</c:if>

<!-- If User doesn't exist -->
<c:if test="${editUser == null}">
    <t:page title="User doesn't exist" activeNavbarItem="Users">
        <jsp:attribute name="body">
            <div class="jumbotron">
                <h3>Sorry, no such user exists.</h3>
            </div>
        </jsp:attribute>
    </t:page>
</c:if>
