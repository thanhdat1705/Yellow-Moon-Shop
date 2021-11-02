<%-- 
    Document   : updateCake
    Created on : Sep 13, 2021, 5:31:10 PM
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
                $("#createDatepicker").datepicker({dateFormat: 'dd-mm-yy', maxDate: 0});
                $("#expirationDatepicker").datepicker({dateFormat: 'dd-mm-yy'});
            });

            $(function () {
                $("#updateCakeForm").validate({
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
                            number: true
                        },
                        txtQuantity: {
                            required: true,
                            number: true,
                            min: 0
                        },
                        txtCreateDate: {
                            required: true,
                            date: true
                        },
                        txtExpirationDate: {
                            required: true,
                            date: true
                        },
                        fileImage: {
                            accept: "png|jpe?g|gif"
                        }
                    }
                })
            });
        </script>
        <style>
            #updateCakeForm label.error {
                color: red;
                padding-left: 20px;
                font-style: italic;
            }
        </style>
        <title>Update Page</title>
    </head>
    <body>
        <div class="wrapper">
            <c:set var="viewResult" value="${requestScope.VIEWRESULT}"/>
            <c:set var="listCategory" value="${requestScope.CATEGORYLIST}"/>
            <c:if test="${empty listCategory}">
                <jsp:forward page="MainController?btnAction=GetCategory&btnAction2=UpdatePage"></jsp:forward>
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
                <h2>Update Cake</h2>
                <c:if test="${not empty viewResult}">
                    <c:set var="cake" value="${viewResult}"/>
                    <form action="MainController" enctype="multipart/form-data" method="POST" id="updateCakeForm">
                        <div class="create-container">
                            <div class="create-content">
                                <label>Brand:</label>  
                                <input type="text" name="txtBrand" value="${cake.brand}" /><br/>
                            </div>
                            <div class="create-content">
                                <label>Name:</label> 
                                <input type="text" name="txtName" value="${cake.name}" /><br/>
                            </div>
                            <div class="create-content">
                                <label>Price ($):</label> 
                                <input type="text" name="txtPrice" value="${cake.price}" /><br/>
                            </div>
                            <div class="create-content">
                                <label>Quantity:</label>
                                <input type="text" step="1" min="1" name="txtQuantity" value="${cake.quantity}" size="10" /><br/>
                            </div>
                            <div class="create-content">
                                <label>Category:</label> 
                                <select name="cbCategory" >                            
                                    <c:if test="${not empty listCategory}">
                                        <c:forEach var="dto" items="${listCategory}" varStatus="counter">
                                            <option value="${dto.id}" ${cake.categoryId == dto.id? 'selected' : ''}>${dto.name}</option>
                                        </c:forEach>
                                    </c:if>
                                </select><br/>
                            </div>
                            <div class="create-content">
                                <label>Status</label> 
                                <select name="cbStatus" > 
                                    <option value="True" ${cake.status == true? 'selected' : ''}>Active</option>
                                    <option value="False" ${cake.status == false? 'selected' : ''}>Inactive</option>
                                </select><br/>
                            </div>
                            <div class="create-content">
                                <label>Create Date:</label>
                                <input type="text" id="createDatepicker" name="txtCreateDate" value="${cake.createDate}" readonly="readonly"> <br/>
                            </div>
                            <div class="create-content">
                                <label>Expiration Date:</label>
                                <input type="text" id="expirationDatepicker" name="txtExpirationDate" value="${cake.expirationDate}" readonly="readonly"> <br/>
                            </div>
                            <div class="create-content">
                                <label>Last Update Date:</label>
                                <span>${cake.lastUpdateDate}</span> <br/>
                            </div>
                            <div class="create-content">
                                <label>User update:</label>
                                <span>${cake.userUpdate}</span> <br/>
                            </div>
                            <div class="create-content">
                                <label>Description:</label>
                                <textarea rows="4" cols="100" name="txtDescription">${cake.description}</textarea><br/>
                            </div>
                            <div class="create-content">
                                <label>Image:</label> 
                                <div style="display: flex; flex-direction: column">
                                    <input type="file" name="fileImage" onchange="readURL(this);"/>
                                    <input type="hidden" name="oldImage" value="${cake.image}"/>
                                    <img id="uploadImg" alt="${cake.name}" src="images/${cake.image}" width="280" height="180" />
                                </div>
                            </div>
                        </div>
                        <input type="hidden" name="cakeId" value="${cake.id}" /><br/>
                        <input style="margin-left: 20px; width: 8%;" type="submit" value="Confirm update" name="btnAction"/>
                        <input style="margin-left: 20px; width: 5%;" type="reset" value="Cancel" />
                    </form><br/>
                </c:if>
            </div>
        </div>
    </body>
</html>
