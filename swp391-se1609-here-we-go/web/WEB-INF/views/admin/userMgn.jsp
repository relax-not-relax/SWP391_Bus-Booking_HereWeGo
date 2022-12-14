<%-- 
    Document   : userMgn
    Created on : Oct 12, 2022, 9:00:38 PM
    Author     : Admin
--%>

<%@page import="manager.TicketManager"%>
<%@page import="manager.UserManager"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css"
            rel="stylesheet"
            />
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js"></script>
        <link rel="stylesheet" href="<c:url value="/css/style_admin.css"/>"/>
        <link
            rel="stylesheet"
            href="https://unicons.iconscout.com/release/v4.0.0/css/line.css"
            />
        <link
            href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/5.2.0/css/bootstrap.min.css"
            rel="stylesheet"
            />
        <link
            href="https://cdn.datatables.net/1.12.1/css/dataTables.bootstrap5.min.css"
            rel="stylesheet"
            />
        <script src="https://code.jquery.com/jquery-3.5.1.js"></script>
        <script src="https://cdn.datatables.net/1.12.1/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/1.12.1/js/dataTables.bootstrap5.min.js"></script>
        <title>Admin - User Management Panel</title>
    </head>
    <body>
        <div class="overview">
            <div class="title">
                <i class="uil uil-clipboard-notes"></i>
                <span class="text">User Management</span>
            </div>
        </div>
        <div>
            
                <div class="activity">
                    <table id="example" class="table table-hover table-responsive-md" style="width: 100%">

                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Account Creation Date</th>
                                <th>Number Of Tickets Purchased</th>
                                <th>Action</th>
                            </tr>
                        </thead>

                        <tbody>

                            <%
                                ArrayList<model.User> list = UserManager.getListCustomer();
                                for (model.User user : list) {
                            %>

                            <tr>

                                <td>
                                    <%= user.getUserId()%>

                                </td>

                                <td><%= user.getName()%></td>
                                <td><%= user.getDateCreate()%></td>
                                <td><%= TicketManager.countTicketbyId(user.getUserId())%></td>
                                <td>
                                    
                                <form action="<c:url value="/admin/deleteCus.do"/>" method="post">
                                    <button
                                        
                                        class="btn btn-danger"
                                        data-bs-toggle="modal"
                                        data-bs-target="#myModal"
                                        >
                                        Delete
                                    </button>
                                    <input type="hidden" name="usId" value="<%= user.getUserId() %>" />
                                    <div class="modal fade" id="myModal">
                                        <div class="modal-dialog modal-dialog-centered">
                                            <div class="modal-content pay-modal">
                                                <!-- Modal Header -->

                                                <!-- Modal body -->
                                                <div class="modal-body pay-cf-msg">
                                                    Do you want to delete this customer?
                                                </div>

                                                <!-- Modal footer -->
                                                <div class="modal-footer">
                                                    <div class="pay-cf">
                                                        <input
                                                            type="submit"
                                                            value="Yes"
                                                            class="pay-cf-btn"
                                                            data-bs-toggle="modal"
                                                            data-bs-target="#myModalPaying"
                                                            />
                                                        <button
                                                            type="button"
                                                            class="pay-cf-btn"
                                                            data-bs-dismiss="modal"
                                                            >
                                                            NO
                                                        </button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                                </td>
                            </tr>

                            <%
                                }
                            %>
                        </tbody>
                        <tfoot>
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Account Creation Date</th>
                                <th>Number Of Tickets Purchased</th>
                                <th>Action</th>
                            </tr>
                        </tfoot>
                    </table>
                </div>
            
        </div>
    </body>
</html>
