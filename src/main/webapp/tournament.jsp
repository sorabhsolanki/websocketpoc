<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tomcat WebSocket</title>

    <script src="js/jquery.min.js"></script>

    <script>
      $( document ).ready(function() {
        alert('${finalParam}');
      });


    </script>
</head>
<body>
<form id="myform" method="post" action="tournament">
    <input type="text" id= "name" name="name" placeholder="Name Of Tournament"/>
    <input size="40" type="text" id= "invite" name="invite" placeholder="write comma separated user name"/>
    <br/>
    <input type="submit" value="create">
    <br/>
</form>
<br>
</body>
</html>