<%-- 
    Document   : createNewCake
    Created on : Sep 11, 2021, 7:45:48 PM
    Author     : NTD
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="js/uploadImage.js"></script>
        <link href="css/simpleLayout.css" rel="stylesheet" type="text/css">
        <script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.1.min.js"></script>
        <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.9/jquery.validate.js" type="text/javascript"></script>
        <script src="https://ajax.aspnetcdn.com/ajax/jquery.validate/1.9/additional-methods.js" type="text/javascript"></script>
        <link href='https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/ui-lightness/jquery-ui.css' rel='stylesheet'>
        <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js" ></script>
        <script>
            $(function () {
                $("#datepicker").datepicker(
                        {
                            dateFormat: 'dd-mm-yy',
                            minDate: 0
                        }
                );
            });

            $(function () {
                $("#createCakeForm").validate({
                    rules: {
                        txtBrand: {
                            required: true,
                            rangelength: [1, 40]
                        },
                        txtName: {
                            required: true,
                            rangelength: [1, 40]
                        },
                        txtPrice: {
                            required: true,
                            number: true,
                            min: 1
                        },
                        txtQuantity: {
                            required: true,
                            number: true
                        },
                        txtExpirationDate: {
                            required: true,
                            date: true
                        },
                        fileImage: {
                            required: true,
                            accept: "png|jpe?g|gif"
                        }
                    }
                })
            });
        </script>
        <style>
            #createCakeForm label.error {
                color: red;
                padding-left: 20px;
                font-style: italic;
            }
        </style>
        <title>Create Cake Page</title>
    </head>
    <body>
        <div class="wrapper">
            <c:set var="listCategory" value="${requestScope.CATEGORYLIST}"/>
            <c:if test="${empty listCategory}">
                <jsp:forward page="MainController?btnAction=GetCategory"></jsp:forward>
            </c:if>
            <div>
                <form action="MainController">
                    <c:set var="wellcomeUser" value="${sessionScope.USERLOGIN}"/>
                    <div class="welcome-container"> 
                        <c:if test="${not empty wellcomeUser}">
                            <div>
                                <h3>Welcome, ${wellcomeUser.name}</h3>
                                <a href="adminPage.jsp">Back management page</a>
                            </div>
                            <div class="welcome-content">
                                <input type="submit" value="Logout" name="btnAction" />
                            </div>
                        </c:if>
                    </div>
                </form>
            </div>
            <div style="border: solid black 1px;">
                <h2>Shopping Page</h2>
                <form action="MainController" enctype="multipart/form-data" method="POST" id="createCakeForm">
                    <div class="create-container">
                        <div class="create-content">
                            <label>Brand:</label>  
                            <input type="text" name="txtBrand" value="${param.txtBrand}" /><br/>
                        </div>
                        <div class="create-content">
                            <label>Name:</label> 
                            <input type="text" name="txtName" value="${param.txtName}" /><br/>
                        </div>
                        <div class="create-content">
                            <label>Price ($):</label> 
                            <input type="text" name="txtPrice" value="${param.txtPrice}" /><br/>
                        </div>
                        <div class="create-content">
                            <label>Quantity:</label>
                            <input type="text" step="1" min="1" name="txtQuantity" value="1" size="10" /><br/>
                        </div>
                        <div class="create-content">
                            <label>Category:</label> 
                            <select name="cbCategory" >                            
                                <c:if test="${not empty listCategory}">
                                    <c:forEach var="dto" items="${listCategory}" varStatus="counter">
                                        <option value="${dto.id}">${dto.name}</option>
                                    </c:forEach>
                                </c:if>
                            </select><br/>
                        </div>
                        <div class="create-content">
                            <label>Expiration Date:</label>
                            <input type="text" id="datepicker" name="txtExpirationDate" readonly="readonly"> <br/>
                        </div>
                        <div class="create-content">
                            <label>Description:</label>
                            <textarea rows="4" cols="100" name="txtDescription">${param.txtDescription}</textarea><br/>
                        </div>
                        <div class="create-content">
                            <label>Image:</label> 
                            <div style="display: flex; flex-direction: column">
<!--                                    <input type="file" name="fileImage" value="${param.fileImage}" placeholder="Please select an image." 
                                       onchange="document.getElementById('uploadImg').src = window.URL.createObjectURL(this.files[0]),
                                                       document.getElementById('uploadImg').width = 280, document.getElementById('uploadImg').height = 180"/>-->
                                <input type="file" name="fileImage" placeholder="Please select an image." onchange="readURL(this);"/>
                                <img id="uploadImg" />
                            </div>
                        </div>
                    </div>
                    <input style="margin-left: 20px; width: 6%;" type="submit" value="Create new" name="btnAction"/>
                    <input style="margin-left: 20px; width: 5%;" type="reset" value="Reset" />
                </form><br/>
            </div>
        </div>
    </body>
</html>
