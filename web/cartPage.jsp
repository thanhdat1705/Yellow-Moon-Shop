<%-- 
    Document   : cartPage
    Created on : Sep 15, 2021, 5:46:04 PM
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
        <link rel="stylesheet" href="js/common.js" type="text/javascript">
        <link rel="stylesheet" href="js/notification.js" type="text/javascript">
        <title>Cart Page</title>
    </head>
    <body onload="showNotification('${requestScope.NOTIFICATIONS_ADDTOCART}', 'notify')">
        <div class="wrapper">
            <c:set var="cartList" value="${sessionScope.CART_LIST}"/>
            <c:set var="amountexceedItemList" value="${requestScope.AMOUNTEXCEEDLIST}"/>
            <div>
                <form action="MainController">
                    <c:set var="wellcomeUser" value="${sessionScope.USERLOGIN}"/>
                    <div class="welcome-container"> 
                        <c:if test="${not empty wellcomeUser}">
                            <div>
                                <h3>Welcome, ${wellcomeUser.name}</h3>
                                <a href="shopping.jsp">Back shopping page</a>
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
                                <a href="signUp.jsp">Sign Up</a>
                            </div>
                        </c:if>
                    </div>
                </form>
            </div>
            <div class="cartPage" style="border: solid black 1px;">
                <h2>Your Cart</h2>
                <c:if test="${not empty cartList}">
                    <form action="MainController" method="POST">
                        <c:set var="count" value="${1}"/>
                        <fmt:formatNumber var="totalAllItem" groupingUsed = "false" type="number" maxFractionDigits="2" value="${0}"/>
                        <table border="1">
                            <thead>
                                <tr>
                                    <th>Image</th>
                                    <th>Name</th>
                                    <th>Price/Item</th>
                                    <th>Amount</th>
                                    <th>Total</th>
                                    <th>Delete</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="cart" items="${cartList}" varStatus="counter">
                                    <tr>
                                        <td style="height: 100px; width: 160px;">
                                            <img alt="${cart.cake.name}" src="images/${cart.cake.image}" width="100%" height="100%"/>
                                        </td>
                                        <td>
                                            <c:url var="urlRewriting" value="MainController">  
                                                <c:param name="btnAction" value="ViewDetail"/>
                                                <c:param name="cakeId" value="${cart.cake.id}"/>
                                            </c:url>
                                            <a href="${urlRewriting}" style="text-decoration: none; color: blue" >${cart.cake.name}</a>
                                        </td>
                                        <td style="text-align: center">$<fmt:formatNumber var="price" type="number" groupingUsed = "false" minFractionDigits="2" maxFractionDigits="2" value="${cart.cake.price}"/>${price}</td>
                                        <td style="text-align: center">
                                            <c:set var="amount" value="${'amount'}${count}"/>
                                            <c:set var="defaultamount" value="${'defaultamount'}${count}"/>
                                            <c:set var="prevamount" value="${'prevamount'}${count}"/>
                                            <c:set var="total" value="${'total'}${count}"/>
                                            <c:set var="updateAmount" value="${'upamount'}${count}"/>
                                            <c:set var="change" value="${'change'}${count}"/>

                                            <input id="${change}" name="" value="${0}" type="hidden">
                                            <input id="${prevamount}" name="" value="${cart.quantity}" type="hidden">
                                            <input id="${defaultamount}" name="" value="${cart.quantity}" type="hidden">
                                            <input type="button" name="sub" onclick="decrementValueCS('${amount}', '${defaultamount}', '${prevamount}',
                                                            '${total}', '${updateAmount}', '${change}', ${cart.cake.price}, '${cart.cake.id}',
                                                            '${cart.cake.name}')" value="-">
                                            <input id="${amount}" type="number" min="1" oninput="changeValueOnInputCS('${amount}', '${defaultamount}', '${prevamount}',
                                                   '${total}', '${updateAmount}', '${change}', ${cart.cake.price}, '${cart.cake.id}', 
                                                   '${cart.cake.name}')" value="${cart.quantity}">
                                            <input type="button" name="sum" onclick="incrementValueCS('${amount}', '${defaultamount}', '${prevamount}',
                                                            '${total}', '${updateAmount}', '${change}', ${cart.cake.price}, '${cart.cake.id}',
                                                            '${cart.cake.name}')" value="+">

                                        </td>
                                        <td style="text-align: center">$<span id="${total}"><fmt:formatNumber var="totalprice" type="number" groupingUsed = "false" minFractionDigits="2" maxFractionDigits="2" value="${cart.cake.price * cart.quantity}"/>${totalprice}</span> 
                                        </td>
                                        <td style="text-align: center">
                                            <c:set var="deleteItem" value="deleteItem${count}"/>
                                            <input class="checkbox" id="${deleteItem}" type="checkbox" name="chkItem" value="${cart.cake.id}" onclick="checkDelete('${deleteItem}')"/>
                                        </td>
                                    </tr>
                                <input id="${updateAmount}" type="hidden" name="listCodeAndAmount" value="${cart.cake.id};;;;;${cart.cake.name};;;;;${cart.quantity}">
                                <c:set var="count" value="${count + 1}"/>

                                <fmt:formatNumber var="totalAllItem" type="number" groupingUsed = "false" minFractionDigits="2" maxFractionDigits="2" value="${totalAllItem+totalprice}"/>
                            </c:forEach> 
                            <input id="checkChangeAmount" name="" value="${0}" type="hidden">
                            <input id="checkDeleteItems" name="" value="${0}" type="hidden">
                            <tr>
                                <td></td>
                                <td colspan="7"><span style="margin-right: 5px">Total ($):</span>
                                    <input id="totalAllItem" type="button" value="${totalAllItem}">
                                </td>
                            </tr>
                            </tbody>
                        </table>
                        <div id="CartSubmitButton">
                            <input id="delete" type="button" name="" value="Delete" onclick="popupDeletewindow('deleteWindow')" disabled="true">
                            <input id="confirm" type="button" name="" value="Purchase" onclick="popupConfirmwindow('confirmWindow')">
                        </div>
                        <div id="warningMessage">
                            <div id="warningMessage_content">
                                <div id="deleteWindow">
                                    <h2>Do you want to remove selected items.</h2><br>
                                    <div>
                                        <input class="cancellation" type="button" name="" value="Cancel delete" onclick="closeDeletewindow('deleteWindow')">
                                        <input class="confirm" type="submit" name="btnAction" value="Confirm delete">
                                    </div>
                                </div>
                                <div id="confirmWindow">
                                    <h2>Click confirm to purchase.</h2><br>
                                    <div>
                                        <input class="cancellation" type="button" name="" value="Cancel purchase" onclick="closeConfirmwindow('confirmWindow')">
                                        <input class="confirm" type="submit" name="btnAction" value="Confirm purchase">
                                    </div>
                                </div>
                            </div>
                        </div>  
                    </form>

                </c:if>
                <c:if test="${not empty amountexceedItemList}">
                    <div id="amountExceedItem">
                        <div>
                            <c:forEach var="amountexceedItem" items="${amountexceedItemList}" varStatus="counter">
                                <span style="color: red">${counter.count}. </span><span>${amountexceedItem}</span><br>
                            </c:forEach>
                            <input type="button" name="" value="Ok" onclick="closeItemWindow('amountExceedItem')">
                        </div>
                    </div>
                    <script src="js/CustomerCart.js"></script>
                </c:if>
                <c:if test="${empty cartList}">
                    <h3>You currently have no items in your shopping cart!</h3>
                </c:if>
                <script src="js/common.js"></script>
                <div id="notify">
                    <h1 style="margin-top: 4%">${requestScope.NOTIFICATIONS_ADDTOCART}</h1>
                </div>
                <script src="js/notification.js"></script>
            </div>
        </div>
    </body>
</html>
