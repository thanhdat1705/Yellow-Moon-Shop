<%-- 
    Document   : adminPage
    Created on : Sep 13, 2021, 11:29:56 PM
    Author     : NTD
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/simpleLayout.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="js/adminManagement.js"></script>
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
        <title>Admin Page</title>
    </head>
    <body>
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
                                <a href="createNewCake.jsp">Add Cake</a>
                                <a style="margin-left: 20px" href="orderTracking.jsp">Order tracking</a>
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
                    <c:set var="status" value="${requestScope.CURRENTSTATUS}"/>
                    <form action="MainController" id="searchForm">
                        <h2>Cake Management</h2>
                        Search Value: <input style="width: 200px" type="text" name="txtSearchValue" value="${param.txtSearchValue}" placeholder="Input the name of cake"/>
                        <select name="cbCategory" >                            
                            <c:if test="${not empty listCategory}">
                                <c:forEach var="dto" items="${listCategory}" varStatus="counter">
                                    <option value="${dto.id}" ${currentCategory == dto.id? 'selected' : ''}>${dto.name}</option>
                                </c:forEach>
                            </c:if>
                        </select>
                        <select name="cbStatus" >
                            <option value="true" ${status == true ? 'selected' : ''} >Active</option>
                            <option value="false" ${status == false ? 'selected' : ''}>Inactive</option>
                        </select>
                        <input type="submit" value="Search" name="btnAction" /><br/><br/>
                        <span>Range of Price ($):</span>
                        <input id="minMoney" type="text" name="txtMin" value="${param.txtMin}" placeholder="($)0" />
                        <span>---</span>
                        <input id="maxMoney" type="text" name="txtMax" value="${param.txtMax}" placeholder="($)Max"/>
                        <div class="errorTxt"></div>
                    </form><br/>
                </div>
                <div style="border: solid black 1px; height: auto;">
                    <c:set var="totalPage" value="${requestScope.TOTALPAGE}"/>
                    <c:set var="pageIndex" value="${requestScope.CURRENTPAGEINDEX}"/>
                    <c:set var="pageSize" value="${requestScope.CURRENTPAGESIZE}"/>
                    <c:set var="searchValue" value="${param.txtSearchValue}"/>
                    <c:if test="${not empty result}">
                        <form action="MainController">
                            <table border="2">
                                <thead>
                                    <tr>
                                        <th>No.</th>
                                        <th>Name</th>
                                        <th>Image</th>
                                        <th>Price</th>
                                        <th>Brand</th>
                                        <th>Description</th>
                                        <th>Category</th>
                                        <th>Quantity</th>
                                        <th>Create Date</th>
                                        <th>Last Update Date</th>
                                        <th>Expiration Date</th>
                                        <th>Update By</th>
                                        <th>Status</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="dto" items="${result}" varStatus="counter">
                                        <tr>
                                            <td>${(counter.count - 1) + (pageIndex * pageSize) - (pageSize-1)}</td>
                                            <td>
                                                <c:url var="urlRewriting" value="MainController">  
                                                    <c:param name="btnAction" value="ViewDetail"/>
                                                    <c:param name="cakeId" value="${dto.id}"/>
                                                </c:url>
                                                <a href="${urlRewriting}" style="text-decoration: none; color: blue" >${dto.name}</a>
                                            </td>
                                            <td style="height: 120px; width: 180px;">
                                                <img alt="${dto.name}" src="images/${dto.image}" width="100%" height="100%"/>
                                            </td>
                                            <td style="text-align: center">$${dto.price}</td>
                                            <td style="text-align: center">${dto.brand}</td>
                                            <td style="text-align: center; width: 250px">${empty dto.description ? 'No description' : dto.description}</td>
                                            <td style="text-align: center">${dto.category}</td>
                                            <td style="text-align: center">${dto.quantity}</td>
                                            <td style="text-align: center; white-space: nowrap;">${dto.createDate}</td>
                                            <td style="text-align: center; white-space: nowrap;">${dto.lastUpdateDate}</td>
                                            <td style="text-align: center; white-space: nowrap;">${dto.expirationDate}</td>
                                            <td style="text-align: center">${dto.userUpdate}</td>
                                            <td style="text-align: center">${dto.status == true ? 'Active' : 'Inactive'}</td>
                                        </tr>
                                    </c:forEach> 
                                </tbody>
                            </table>
                        </form>
                        <div style="display: flex; justify-content: center; align-items: center; margin-bottom: 20px; margin-top: 20px">
                            <c:forEach var="index" begin="1" end="${totalPage}">
                                <a class="paging-container" 
                                   href="MainController?btnAction=ChangePage&txtPageIndex=${index}&txtSearchValue=${searchValue}&cbCategory=${currentCategory}&txtMin=${param.txtMin}&txtMax=${param.txtMax}&cbStatus=${status}&txtPageSize=${pageSize}">
                                    <span style="color: grey; padding: 5px; padding-left: 15px; padding-right: 15px;" ${index == pageIndex? 'class="paging-content"': ''}>${index}</span>
                                </a>
                            </c:forEach>
                            <!--                            <span style="margin-left: 5px; margin-right: 20px">-----</span>
                                                        <form action="MainController" id="pageSizeForm">
                                                            <select name="cbPageSize" id="cbPageSize" style="font-size: 14px; padding: 5px;" 
                                                                    onchange="return changePageSize(${pageIndex}, ${searchValue}, ${currentCategory}, ${param.txtMin}, ${param.txtMax}, ${status});">
                                                                <option value="5" ${pageSize == 5? 'selected' : ''}>5</option>
                                                                <option value="10" ${pageSize == 10? 'selected' : ''}>10</option>
                                                                <option value="15" ${pageSize == 15? 'selected' : ''}>15</option>
                                                                <option value="20" ${pageSize == 20? 'selected' : ''}>20</option>
                                                            </select>
                                                            <input type="hidden" name="txtPageSize" id="pageSizeDropdown">
                                                            <input type="hidden" name="txtPageIndex" value="${pageIndex}">
                                                            <input type="hidden" name="txtSearchValue" value="${searchValue}">
                                                            <input type="hidden" name="cbCategory" value="${currentCategory}">
                                                            <input type="hidden" name="txtMin" value="${param.txtMin}">
                                                            <input type="hidden" name="txtMax" value="${param.txtMax}">
                                                            <input type="hidden" name="cbStatus" value="${status}">
                                                            <input type="submit" value="ChangePage" name="btnAction">
                                                        </form>-->
                        </div>
                    </c:if>
                    <c:if test="${empty result}">
                        <h3>No search results found!!!!</h3>
                    </c:if>
                </div>
            </div>
        </div>
    </body>
</html>
