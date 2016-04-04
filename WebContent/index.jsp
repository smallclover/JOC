<%--
  User: smartclover
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>JavaOnlineComplier</title>
<!--     <form action="complier.action" method="post">
      <textarea name="code">

      </textarea>
      <input type="submit" value="submit">
    </form> -->
   <script type="text/javascript"  src="jquery-2.2.2.min.js"></script>
   <script type="text/javascript">
		function codeData(){
		    var code = document.getElementById("code").value;
		    var aj = $.ajax( {  
		    	     url:'complier.action',// 跳转到 action  
		    	     data:'code='+code,
		    	     type:'post',
		    	     async:false,
		    	     success:function(data) { 
		    	    	 alert(data);
		    	    	 document.getElementById("result").value = data; 
		    	      },  
		    	      error : function() {  
		    	           alert("异常！");  
		    	      }  
		    	 });
			
		}
    </script>
  </head>
  
  <body>
  	<div class="code-box">
  		<textarea id="code" rows="" cols=""></textarea>
  	</div>
  	<div class="result-box">
  		<textarea id="result" rows="" cols="" ></textarea>
  	</div>
  	<input type="button" value="submit" onclick="javascript:codeData();"/>
  </body>
</html>
    