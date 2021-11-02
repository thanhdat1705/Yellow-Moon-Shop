<%-- 
    Document   : shopping
    Created on : Sep 8, 2021, 11:29:58 PM
    Author     : NTD
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
                        txtMin: {
                            number: true,
                            min: 0,
                        },
                        txtMax: {
                            number: true,
                            min: 0,
                        }
                    },
                    messages: {
                        txtMin: {
                            min: "Min price must be greater than or equal to 0."
                        },
                        txtMax: {
                            min: "Max price must be greater than or equal to 0."
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
        <title>Shopping Page</title>
    </head>
    <body onload="showNotification('${requestScope.NOTIFICATIONS_ADDTOCART}', 'notify')">
        <%
            response.setHeader("Cache-Control", "no-cache,no-store");
        %>
        <div class="wrapper">
            <c:set var="result" value="${requestScope.SEARCHRESULT}"/>
            <c:if test="${result == null}">
                <jsp:forward page="MainController?btnAction=Search"></jsp:forward>
            </c:if>
            <div>
                <form action="MainController">
                    <c:set var="wellcomeUser" value="${sessionScope.USERLOGIN}"/>
                    <div class="welcome-container"> 
                        <c:if test="${not empty wellcomeUser}">
                            <div>
                                <h3>Welcome, ${wellcomeUser.name}</h3>
                                <a href="cartPage.jsp">View your cart</a>
                                <a style="margin-left: 20px" href="orderTracking.jsp">Order tracking</a>
                            </div>
                            <div class="welcome-content">
                                <input type="submit" value="Logout" name="btnAction" />
                            </div>
                        </c:if>
                        <c:if test="${empty wellcomeUser}">
                            <div>
                                <h3>Welcome, Quest.</h3>
                                <a href="cartPage.jsp">View your cart</a>
                            </div>
                            <div class="welcome-content">
                                <a href="login.jsp">Login</a>
                                <a href="signUp.jsp">Sign Up</a>
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
                        <h2>Shopping Page</h2>
                        Search Value: <input style="width: 200px" type="text" name="txtSearchValue" value="${param.txtSearchValue}" placeholder="Input the name of cake"/>
                        <select name="cbCategory" >                            
                            <c:if test="${not empty listCategory}">
                                <c:forEach var="dto" items="${listCategory}" varStatus="counter">
                                    <option value="${dto.id}" ${currentCategory == dto.id? 'selected' : ''}>${dto.name}</option>
                                </c:forEach>
                            </c:if>
                        </select>
                        <input type="submit" value="Search" name="btnAction" /><br/><br/>
                        <span>Range of Price ($):</span>
                        <input id="minMoney" type="text" name="txtMin" value="${param.txtMin}" placeholder="($)0" />
                        <span>---</span>
                        <input id="maxMoney" type="text" name="txtMax" value="${param.txtMax}" placeholder="($)Max"/>
                        <div class="errorTxt"></div>
                        <input type="hidden" value="true" name="cbStatus" />
                    </form><br/>
                </div>
                <div style="border: solid black 1px; height: auto; display: flex; flex-direction: column; background-color: bisque;">
                    <c:set var="totalPage" value="${requestScope.TOTALPAGE}"/>
                    <c:set var="currentPageIndex" value="${requestScope.CURRENTPAGEINDEX}"/>
                    <c:set var="pageSize" value="${requestScope.CURRENTPAGESIZE}"/>
                    <c:set var="searchValue" value="${param.txtSearchValue}"/>
                    <c:if test="${not empty result}">
                        <div style="display: flex; flex-wrap: wrap;">
                            <c:forEach var="dto" items="${result}" varStatus="counter">
                                <a class="cake" style="text-decoration: none;" href="MainController?btnAction=ViewDetail&cakeId=${dto.id}">
                                    <div class="image">
                                        <img alt="${dto.name}" src="images/${dto.image}" width="100%" height="100%"/>                                          
                                    </div>
                                    <div class="cakeInfo">
                                        <span class="cakeContent" style="font-weight: bold;">${dto.name} - ${dto.brand}</span>
                                        <span class="cakeContent" style="font-weight: bold">Price: $${dto.price}</span>
                                        <!--                                        <div class="cakeContent" style="display: inline-block; flex-wrap: nowrap;">
                                                                                    <span >Available: ${dto.quantity}</span>
                                                                                    <span >Category: ${dto.category}</span>
                                                                                </div>-->
                                        <span class="cakeContent">Available: ${dto.quantity}</span>
                                        <span class="cakeContent">Category: ${dto.category}</span>
                                        <span class="cakeContent">Expiration Date: ${dto.expirationDate}</span>
                                    </div>
                                </a> 
                            </c:forEach>
                        </div>
                        <div style="display: flex; justify-content: center; margin-bottom: 20px">
                            <c:forEach var="pageIndex" begin="1" end="${totalPage}">
                                <a class="paging-container" href="MainController?btnAction=ChangePage&txtPageIndex=${pageIndex}&txtSearchValue=${searchValue}&cbCategory=${currentCategory}&txtMin=${param.txtMin}&txtMax=${param.txtMax}&cbStatus=true&txtPageSize=${pageSize}">
                                    <span style="color: grey; padding: 5px; padding-left: 15px; padding-right: 15px;" ${pageIndex == currentPageIndex? 'class="paging-content"': ''}>${pageIndex}</span>
                                </a>
                            </c:forEach>
                        </div>
                    </c:if>
                    <c:if test="${empty result}">
                        <h3>No search results found!!!!</h3>
                    </c:if>
                    <div id="notify">
                        <h1 style="margin-top: 4%">${requestScope.NOTIFICATIONS_ADDTOCART}</h1>
                    </div>
                    <script src="js/notification.js"></script>
                </div>
            </div>
        </div>
    </body>
</html>
