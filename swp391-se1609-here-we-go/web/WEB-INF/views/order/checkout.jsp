<%-- 
    Document   : checkout
    Created on : Oct 24, 2022, 4:10:26 PM
    Author     : Admin
--%>

<%@page import="model.RouteDetail"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js"></script>
        <link rel="stylesheet" href="<c:url value="/css/checkout.css"/>">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Bebas+Neue&display=swap" rel="stylesheet">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>   
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css"><link href="https://fonts.googleapis.com/css2?family=Bebas+Neue&family=Roboto:wght@700&display=swap" rel="stylesheet">
        <style>
            .pay-ct-dt {
                background-image: url('<c:url value="/images/specialoffer.png"/>');
                background-position: top right;
                background-repeat: no-repeat;
                background-size: 30%;
            }
        </style>
    </head>
    <body>
        <%
            RouteDetail rd = (RouteDetail) request.getAttribute("routeDe");
            int[] listPos = (int[]) request.getAttribute("listPos");
            
            
            request.getSession().setAttribute("RouteDetailForTicket", rd);
            request.getSession().setAttribute("listPosForTicket", listPos);
        %>
        <section class="pay-ct">
            <form action="<c:url value="/order/authorizePayment.do"/>" method="post">
            <div class="container pay-ct-dt">
                <h3 class="pay-pl" style="font-size: 20px;">
                    <%= rd.getDepart()%> <i class="fa fa-long-arrow-right"></i> <%= rd.getDestination()%>
                </h3>
                <h1 class="pay-bs"><%= rd.getCompanyName()%></h1>
                <p class="pay-bs-tp"> <%= rd.getKindBus()%> </p>
                <br />
                <div class="pay-bs-tm">
                    <h4> <%= rd.getStartTime()%> </h4>
                </div>
                <br />
                <div class="pay-pk-p">
                    <h4>Pickup Point</h4>
                    <p><%= rd.getDepartDetail()%></p>
                </div>
                <div class="pay-pk-p">
                    <h4>Drop Point</h4>
                    <p><%= rd.getDestinationDetail()%></p>
                </div>
                <br />

                <div class="pay-trl-dt">
                    <h4>Enter Traveller Details</h4>
                    <small>Please enter name and phone of passenger</small>
                    <%
                        for (int i : listPos) {
                    %>
                    <h6>Seat <%= i%></h6>
                    <div class="pay-trl-dt-form">
                        <div class="pay-trl-dt-ip" style="margin-right: 10px">
                            <p>Name</p>
                            <input
                                name="pasName"
                                value="${LOGIN_CUSTOMER_NAME}"
                                type="text" 
                                placeholder="Full Name"
                                class="form-control"
                                required/>
                        </div>
                        <div class="pay-trl-dt-ip">
                            <p>Phone Number</p>
                            <input type="phone" placeholder="Phone" class="form-control" value="${LOGIN_CUSTOMER_PHONE}" name="pasPhone" required/>
                        </div>
                    </div>
                    <%
                        }
                    %>
                </div>


                <br />
                <br />
                
                    <div class="pay-pr">
                        <div class="pay-pr-ct">
                            <p class="pay-pr-ct1">Ticket Price</p>
                            <p class="pay-pr-ct2"><%=rd.getPrice()%> VND</p>
                            <%
                                float price = (float) ((rd.getPrice() * listPos.length) / 24840);
                                String priceTicket = String.valueOf(price);
                            %>
                            <input type="hidden" name="subTotal" value="<%=priceTicket%>"/>
                        </div>
                        <div class="pay-pr-ct">
                            <p class="pay-pr-ct1">Number Of Tickets</p>
                            <p class="pay-pr-ct2"><%= listPos.length%></p>
                            <input type="hidden" name="numberOfTickets" value="1"/>
                            <input type="hidden" name="productName" value="Order Ticket"/>
                        </div>
                        <!--                        <div class="pay-pr-ct">
                                                    <p class="pay-pr-ct1">Coupon</p>
                                                    <p class="pay-pr-ct2">No Coupon</p>
                                                    <input type="hidden" name="coupon" value="0"/>
                                                </div>-->
                        <div class="pay-pr-ct">
                            <p class="pay-pr-ct1">Total</p>
                            <p class="pay-pr-ct2" style="color: #EA4335"><%= rd.getPrice() * listPos.length%> VND</p>
                            <%
                                float total = (float) ((rd.getPrice() * listPos.length) / 24840);
                                String totalPrice = String.valueOf(total);
                            %> 
                            <input type="hidden" name="totalPrice" value="<%=totalPrice%>"/>
                        </div>
                        <div class="pay-btn">
                            <input
                                type="button"
                                value="BUY NOW"
                                class="button-92 btn btn-secondary"
                                data-bs-toggle="modal"
                                data-bs-target="#myModal"
                                />
                        </div>
                    </div>
                    <!-- The Modal -->
                    <div class="modal fade" id="myModal">
                        <div class="modal-dialog modal-dialog-centered">
                            <div class="modal-content pay-modal">
                                <!-- Modal Header -->

                                <!-- Modal body -->
                                <div class="modal-body pay-cf-msg">
                                    Booking bus tickets on Here We Go?
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
                

            </div>
            </form>
        </section>
    </body>
</html>
