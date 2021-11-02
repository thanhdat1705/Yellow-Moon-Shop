<%-- 
    Document   : login2
    Created on : Sep 8, 2021, 8:02:31 PM
    Author     : NTD
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/simpleLayout.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="js/notification.js"></script>
        <script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.1.min.js"></script>
        <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.9/jquery.validate.js" type="text/javascript"></script>
        <script src="https://ajax.aspnetcdn.com/ajax/jquery.validate/1.9/additional-methods.js" type="text/javascript"></script>
        <script>
            $(function () {
                $("#loginForm").validate({
                    rules: {
                        txtId: {
                            required: true,
                            rangelength: [1, 20]
                        },
                        txtPassword: {
                            required: true,
                            rangelength: [3, 16]
                        }
                    }
                })
            });
        </script>
        <style>
            #loginForm label.error {
                color: red;
                padding-left: 20px;
                font-style: italic;
            }
        </style>
        <title>Login</title>
    </head>
    <body onload="showNotification('${requestScope.SIGNUP_SUCC}', 'signUpNotify')">
        <div class="authen-layout">
            <div class="sidenav">
                <div class="login-main-text">
                    <h2>Yellow Moon Cake Shop<br> Login Page</h2>
                    <p>Login or register from here to access.</p>
                </div>
            </div>
            <div class="main">
                <div class="col-md-6 col-sm-12">
                    <div class="login-form">
                        <a href="shopping.jsp">Go to Shopping Page</a><br/>
                        <form action="MainController" method="POST" id="loginForm">
                            <c:set var="error" value="${requestScope.LOGINERROR}"/>
                            <c:set var="signSucc" value="${requestScope.SIGNUP_SUCC}"/>
                            <div class="form-group">
                                <label>UserID</label><br>
                                <input type="text" name="txtId" value="${param.txtId}" class="form-control" placeholder="User Id"/>
                            </div>
                            <div class="form-group">
                                <label>Password</label><br>
                                <input type="password" name="txtPassword" value="" class="form-control" placeholder="Password" />
                            </div>
                            <c:if test="${not empty error}">
                                <font color="red">
                                ${error}
                                </font><br/>
                            </c:if>
                            <div style="margin-top: 5px">
                                <input type="submit" value="Login" name="btnAction" />
                                <input type="reset" value="Reset" />
                            </div> 
                        </form>
                        <a href="signUp.jsp">Sign Up</a><br/>
                        <div id="signUpNotify" style="color: green">
                            <h3>${requestScope.SIGNUP_SUCC}</h3>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
