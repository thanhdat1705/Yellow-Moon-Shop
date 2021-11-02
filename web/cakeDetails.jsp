<%-- 
    Document   : cakeDetails
    Created on : Sep 13, 2021, 2:04:49 AM
    Author     : NTD
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/simpleLayout.css" rel="stylesheet" type="text/css">
        <title>Cake Page</title>
        <style>
            .label {
                margin-bottom: 5px;
            }
        </style>
    </head>
    <body>
        <div class="wrapper">
            <c:set var="viewResult" value="${requestScope.VIEWRESULT}"/>
            <c:set var="createSuccMess" value="${requestScope.CREATE_SUCC}"/>   
            <c:set var="upadteSuccMess" value="${requestScope.UPDATE_SUCC}"/> 
            <%--<c:if test="${empty viewResult}">
                <jsp:forward page="MainController?btnAction=ViewDetail"></jsp:forward>
            </c:if>--%>
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
                                    <a href="cartPage.jsp">View your cart</a><br/>
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
                                <a href="cartPage.jsp">View your cart</a><br/>
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
            <div style="border: solid black 1px;">
                <h2>Cake Information</h2>
                <c:if test="${not empty viewResult}">
                    <c:set var="cake" value="${viewResult}"/>   
                    <c:if test="${not empty createSuccMess}">
                        <font color="green" style="font-size: 16px;">
                        ${createSuccMess}
                        </font><br/>
                    </c:if>
                    <c:if test="${not empty upadteSuccMess}">
                        <font color="green" style="font-size: 16px;">
                        ${upadteSuccMess}
                        </font><br/>
                    </c:if>
                    <form action="MainController">
                        <div>
                            <p><b>Brand: </b>${cake.brand}</p>
                            <p><b>Name: </b>${cake.name}</p>
                            <p><b>Price ($): </b>$${cake.price}</p>            
                            <p><b>Quantity: </b>${cake.quantity}</p>
                            <p><b>Category: </b>${cake.category}</p> 
                            <c:if test="${wellcomeUser.roleId == 1}">
                                <p><b>Create Date: </b>${cake.createDate}</p>
                                <p><b>Last Update Date: </b>${cake.lastUpdateDate}</p>
                            </c:if>
                            <p><b>Expiration Date: </b>${cake.expirationDate}</p>
                            <c:if test="${wellcomeUser.roleId == 1}">
                                <p><b>Status: </b>${cake.status == true ? 'Active' : 'Inactive'}</p>
                                <p><b>User update: </b>${cake.userUpdate}</p>
                            </c:if>
                            <p><b>Description: </b>${empty cake.description ? 'No description' : cake.description}</p>
                            <b>Image: </b> 
                            <div style="height: 180px; width: 280px;">
                                <img alt="${cake.name}" src="${pageContext.request.contextPath}/images/${cake.image}" width="100%" height="100%"/>
                            </div>
                        </div>
                        <c:if test="${not empty wellcomeUser}">
                            <c:if test="${wellcomeUser.roleId == 1}">
                                <div style="margin-top: 10px;">
                                    <input type="hidden" value="${cake.id}" name="cakeId"/>
                                    <input style="margin-left: 20px; width: 6%;" type="submit" value="Update" name="btnAction"/>
                                </div>
                            </c:if>
                        </c:if>
                        <c:if test="${wellcomeUser.roleId != 1}">
                            <div style="margin-top: 10px;">
                                <input type="hidden" value="${cake.id}" name="cakeId"/>
                                <input type="hidden" value="1" name="txtQuantity"/>
                                <input style="margin-left: 20px; width: 7%;" type="submit" value="Add to card" name="btnAction"/>
                            </div>
                        </c:if>
                    </form><br/>
                </c:if>
            </div>
        </div>
    </body>
</html>
