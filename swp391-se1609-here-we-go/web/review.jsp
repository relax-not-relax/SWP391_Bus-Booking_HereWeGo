<%-- 
    Document   : review
    Created on : Oct 24, 2022, 10:01:24 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Review Payment</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <style type="text/css">
            table {
                border: 0
            }
            table td {
                padding: 10px;
            }
        </style>
    </head>
    <body>
        <div align="center">
            <h1>Please Review Before Paying</h1>
            <form action="execute_payment" method="post">
                <table>
                    <tr>
                        <td colspan="2"><b>Transaction Details: </b></td>
                        <td>
                            <input type="hidden" name="paymentId" value="${param.paymentId}"/>
                            <input type="hidden" name="PayerID" value="${param.PayerID}"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Description: </td>
                        <td>${transaction.description}</td>
                    </tr>
                    <tr>
                        <td>Subtotal: </td>
                        <td>${transaction.amount.details.subtotal}$</td>
                    </tr>
                    <tr>
                        <td>Total: </td>
                        <td>${transaction.amount.total}$</td>
                    </tr>
                    <tr><td><br/></td></tr>
                    <tr>
                        <td colspan="2"><b>Payer Information: </b></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>First Name: </td>
                        <td>${payer.firstName}</td>
                    </tr>
                    <tr>
                        <td>Last Name: </td>
                        <td>${payer.lastName}</td>
                    </tr>
                    <tr>
                        <td>Email: </td>
                        <td>${payer.email}</td>
                    </tr>
                    <tr><td><br/></td></tr>
                    <tr>
                        <td colspan="2" align="center">
                            <input type="submit" value="Pay Now"/>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </body>
</html>
