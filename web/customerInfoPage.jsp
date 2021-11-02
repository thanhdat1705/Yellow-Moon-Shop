<%-- 
    Document   : customerInfoPage
    Created on : Sep 19, 2021, 6:21:25 PM
    Author     : NTD
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/simpleLayout.css" rel="stylesheet" type="text/css">
        <script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.1.min.js"></script>
        <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.9/jquery.validate.js" type="text/javascript"></script>
        <script src="https://ajax.aspnetcdn.com/ajax/jquery.validate/1.9/additional-methods.js" type="text/javascript"></script>
        <script>
            $(function () {
                $("#customerForm").validate({
                    rules: {
                        txtName: {
                            required: true,
                            rangelength: [3, 30]
                        },
                        txtPhone: {
                            required: true,
                            phoneUK: true,
                        },
                        txtAddress: {
                            required: true,
                        }
                    }
                })
            });
        </script>
        <style>
            #customerForm label.error {
                color: red;
                padding-left: 20px;
                font-style: italic;
            }
        </style>
        <title>Customer Page</title>
    </head>
    <body>
        <div class="wrapper">
            <c:set var="customerInfo" value="${requestScope.CUSTOMER_INFO}"/>
            <c:set var="listPaymentMethod" value="${requestScope.PAYMENT_METHOD_LIST}"/>
            <c:if test="${empty listPaymentMethod}">
                <jsp:forward page="MainController?btnAction=GetPaymentMethod"></jsp:forward>
            </c:if>
            <c:if test="${empty customerInfo}">
                <jsp:forward page="MainController?btnAction=GetCustomer"></jsp:forward>
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
                        <c:if test="${empty wellcomeUser}">
                            <div>
                                <h3>Welcome, Quest.</h3>
                                <a href="cartPage.jsp">View your cart</a><br/>
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
            <div style="border: solid black 1px;">
                <h2>Customer Information</h2>
                <c:if test="${not empty customerInfo}">
                    <c:if test="${customerInfo != 'no cus'}">
                        <form action="MainController" method="POST">
                            <div>
                                <p><b>Name: </b>${customerInfo.name}</p>
                                <p><b>Phone: </b>${customerInfo.phone}</p>            
                                <p><b>Address: </b>${customerInfo.address}</p>
                                <p style="display: inline; font-weight: 600">Payment method:</p> 
                                <select name="cbPaymentMethod" >                            
                                    <c:if test="${not empty listPaymentMethod}">
                                        <c:forEach var="dto" items="${listPaymentMethod}" varStatus="counter">
                                            <option value="${dto.id}">${dto.name}</option>
                                        </c:forEach>
                                    </c:if>
                                </select><br/>

                            </div>
                            <div style="margin-top: 10px;">
                                <input type="hidden" name="txtName" value="${customerInfo.name}" />
                                <input type="hidden" name="txtPhone" value="${customerInfo.phone}" />
                                <input type="hidden" name="txtAddress" value="${customerInfo.address}"/>
                                <input style="margin-left: 20px; width: 6%;" type="submit" value="Check out" name="btnAction"/>
                                <input style="margin-left: 20px; width: 5%;" type="reset" value="Reset" />
                            </div>
                        </form><br/>
                    </c:if>
                    <c:if test="${customerInfo == 'no cus'}">
                        <form action="MainController" method="POST" id="customerForm">
                            <div class="create-container">
                                <div class="create-content">
                                    <label>Name:</label>  
                                    <input type="text" name="txtName" value="${param.txtName}" /><br/>
                                </div>
                                <div class="create-content">
                                    <label>Phone:</label> 
                                    <input type="text" name="txtPhone" value="${param.txtPhone}" /><br/>
                                </div>
                                <div class="create-content">
                                    <label>Address:</label> 
                                    <input type="text" name="txtAddress" value="${param.txtAddress}" /><br/>
                                </div>
                                <div class="create-content">
                                    <label>Payment method:</label> 
                                    <select name="cbPaymentMethod" >                            
                                        <c:if test="${not empty listPaymentMethod}">
                                            <c:forEach var="dto" items="${listPaymentMethod}" varStatus="counter">
                                                <option value="${dto.id}">${dto.name}</option>
                                            </c:forEach>
                                        </c:if>
                                    </select><br/>
                                </div>
                            </div>
                            <input style="margin-left: 20px; width: 6%;" type="submit" value="Check out" name="btnAction"/>
                            <input style="margin-left: 20px; width: 5%;" type="reset" value="Reset" />
                        </form><br/>
                    </c:if>
                </c:if>
            </div>        
        </div>
    </body>
</html>
