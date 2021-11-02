<%-- 
    Document   : orderTracking
    Created on : Sep 20, 2021, 1:04:03 AM
    Author     : NTD
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/simpleLayout.css" rel="stylesheet" type="text/css">
        <link rel="stylesheet" href="js/notification.js" type="text/javascript">
        <script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.1.min.js"></script>
        <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.9/jquery.validate.js" type="text/javascript"></script>
        <script src="https://ajax.aspnetcdn.com/ajax/jquery.validate/1.9/additional-methods.js" type="text/javascript"></script>
        <script>
            $(function () {
                $("#searchForm").validate({
                    rules: {
                        txtOrderId: {
                            digits: true,
                        },
                    },
                    messages: {
                        txtOrderId: {
                            digits: "Order Id must be digit."
                        }
                    },
                    errorElement: 'div',
                    errorLabelContainer: '.errorTxt'
                })
            });
        </script>
        <style>
            .errorTxt{
                color: red;
                font-style: italic;
            }
        </style>
        <title>Order Tracking</title>
    </head>
    <body onload="showNotification('${requestScope.NOTIFICATION}', 'notify')">
        <div class="wrapper">
            <c:set var="result" value="${requestScope.SEARCH_ORDER_RESULT}"/>
            <c:if test="${result == null}">
                <jsp:forward page="MainController?btnAction=Search order"></jsp:forward>
            </c:if>
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
                    </div>
                </form>
            </div>
            <div class="container">
                <div style="border: solid black 1px;">
                    <c:set var="listCategory" value="${requestScope.LISTCATEGORY}"/>
                    <c:set var="currentCategory" value="${requestScope.CURRENTCATEGORY}"/>
                    <form action="MainController" id="searchForm">
                        <h2>Order Tracking</h2>
                        Order Id: <input style="width: 200px" type="text" name="txtOrderId" value="${param.txtOrderId}" placeholder="Input order Id"/>
                        <c:if test="${not empty wellcomeUser}">
                            <c:if test="${wellcomeUser.roleId == 1}">
                                <span style="margin-left: 20px;">
                                    Customer: <input style="width: 200px" type="text" name="txtCusName" value="${param.txtCusName}" placeholder="Input customer name"/>
                                </span>
                            </c:if>
                        </c:if>
                        <input type="submit" value="Search order" name="btnAction" /><br/>
                        <div style="height: 20px;">
                            <div class="errorTxt"></div>
                        </div>
                    </form>
                </div>
                <div style="border: solid black 1px;">
                    <c:if test="${not empty result}">
                        <div class="order-wrapper">
                            <c:forEach var="dto" items="${result}" varStatus="counter">
                                <div class="order-container">
                                    <div class="order-content">
                                        <label>Order Id:</label><span style="color: blue">${dto.id}</span>
                                    </div>
                                    <div class="order-content">
                                        <label>Customer:</label><span>${dto.cusName}</span>
                                    </div>
                                    <div class="order-content">
                                        <label>Order Date:</label><span>${dto.createDate}</span>
                                    </div>
                                    <div class="order-content">
                                        <label>Total Price:</label><span>$<fmt:formatNumber var="allPrice" type="number" groupingUsed = "false" minFractionDigits="0" maxFractionDigits="2" value="${dto.totalPrice}"/>${allPrice}</span>
                                    </div>
                                    <div class="order-content">
                                        <label>Shipping address:</label><span>${dto.address}</span>
                                    </div>
                                    <div class="order-content">
                                        <label>Payment method:</label><span>${dto.paymentMethod}</span>
                                    </div>
                                    <div class="order-content">
                                        <label>Payment status:</label><span>${dto.paymentStatus}</span>
                                    </div>
                                    <div class="order-content">
                                        <label>List of products:</label><br/>
                                        <table border="1">
                                            <thead>
                                                <tr>
                                                    <th>Image</th>
                                                    <th>Brand</th>
                                                    <th>Name</th>
                                                    <th>Amount</th>
                                                    <th>Expiration</th>
                                                    <th>Total</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="cake" items="${dto.orderDetails}" varStatus="counter">
                                                    <tr>
                                                        <td style="height: 120px; width: 180px;">
                                                            <img alt="${cake.cakeName}" src="images/${cake.image}" width="100%" height="100%"/>
                                                        </td>
                                                        <td style="text-align: center">${cake.brand}</td>
                                                        <td style="text-align: center">${cake.cakeName}</td>
                                                        <td style="text-align: center">${cake.quantity}</td>
                                                        <td style="text-align: center">${cake.expiration}</td>
                                                        <td style="text-align: center">$<fmt:formatNumber var="totalprice" type="number" groupingUsed = "false" minFractionDigits="0" maxFractionDigits="2" value="${cake.price * cake.quantity}"/>${totalprice}</td>
                                                    </tr>
                                                </c:forEach> 
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </c:if>
                    <c:if test="${empty result}">
                        <h3>No search results found!!!!</h3>
                    </c:if>
                </div>
            </div>
        </div>
        <div id="notify">
            <h1 style="margin-top: 4%">${requestScope.NOTIFICATION}</h1>
        </div>
        <script src="js/notification.js"></script>
    </body>
</html>
