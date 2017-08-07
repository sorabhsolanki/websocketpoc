<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tomcat WebSocket</title>
    <script src="js/jquery.min.js"></script>

    <script>
      $(document).ready(function () {
        $("#generalchat").click(function () {
          $("#actionperformed").val("chat");
          $("#myform").attr('action', 'menu');
          $("#myform").submit();
        });

        $("#maketournament").click(function () {
          $("#actionperformed").val("tournament");
          $("#myform").attr('action', 'menu');
          $("#myform").submit();
        });
      });


    </script>
    <style>
        table {
            font-family: arial, sans-serif;
            border-collapse: collapse;
            width: 100%;
        }

        td, th {
            border: 1px solid #dddddd;
            text-align: left;
            padding: 8px;
        }

        tr:nth-child(even) {
            background-color: #dddddd;
        }
    </style>
</head>
<body>
<form id="myform" method="post">
    <input type="button" id="generalchat" value="RealTimeChat"/>
    <input type="button" id="maketournament" value="CreateTournament"/>
    <input type="hidden" id="actionperformed" name="actionperformed"/>
</form>
    <br/>
    Below are the tournament to which you have access.
    <br/>
    <table>
        <tr>
            <th>Tournament Name</th>
            <th>Tournament Admin</th>
            <th>Created_At</th>
            <th>Chat</th>
        </tr>

        <c:forEach items="${tournamentlist}" var="tour">
            <form action="tourchat" method="post">
            <tr>
                <td><input type="text" value="<c:out value="${tour.name}"/>" name="tourName" readonly style="border: 0;"/></td>
                <td><input type="text" value="<c:out value="${tour.adminName}"/>" name="adminName" readonly style="border: 0;"/></td>
                <td><input type="text" value="<c:out value="${tour.createdAt}"/>" name="createdAt" readonly style="border: 0;"/></td>
                <td><input type="submit" value="Chat"/></td>
            </tr>
            </form>
        </c:forEach>
    </table>
<br/>
<form action="logout" method="post">
    <input type="submit" value="logout"/>
</form>
</body>
</html>