<%-- 
    Document   : error
    Created on : Sep 21, 2021, 12:33:18 AM
    Author     : NTD
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div>
            <form action="MainController">
                <c:set var="wellcomeUser" value="${sessionScope.USERLOGIN}"/>
                <div class="welcome-container"> 
                    <c:if test="${not empty wellcomeUser}">
                        <div>
                            <h3>Welcome, ${wellcomeUser.name}</h3>
                            <c:if test="${wellcomeUser.roleId == 1}">
                                <a href="adminPage.jsp">Back management page</a>
                            </c:if>
                            <c:if test="${wellcomeUser.roleId == 2}">
                                <a href="shopping.jsp">Back shopping page</a>
                            </c:if>
                        </div>
                        <div class="welcome-content">
                            <input type="submit" value="Logout" name="btnAction" />
                        </div>
                    </c:if>
                    <c:if test="${empty wellcomeUser}">
                        <div>
                            <h3>Welcome, Quest.</h3>
                            <a href="shopping.jsp">Back shopping page</a>
                        </div>
                        <div class="welcome-content">
                            <a href="login.jsp">Login</a>
                            <a href="createNewAccount.jsp">Sign Up</a>
                        </div>
                    </c:if>
                </div>
            </form>
        </div>
        <h1>Something went wrong!!!</h1>

    </body>
</html>
