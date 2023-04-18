<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Test</title>       
        <script type="text/javascript">
        function mouseOver(button, cssName) {
            button.className = cssName;
        }    
            
        function fairBuzz() {
          for (var i = 1; i <= 100; i++) {
            if (i % 15 === 0) {
                document.writeln("FAIRBUZZ<br/>");
            } else if (i % 3 === 0) {
                document.write("FAIR<br/>");
            } else if (i % 5 === 0) {
                document.write("BUZZ<br/>");
            } else {
                document.writeln(i);
            }
          }  
        }
        </script>
     <style type="text/css">
        .button
        {  
           color: white; background-color: #003366; font-family: arial; font-size: 10pt; font-weight: bold;
           text-decoration: none; width: 125px; border-radius: 25px; margin-top: 2px;
        }
        .reversebutton
        {  
           color: white; background-color: blue; font-family: arial; font-size: 10pt; font-weight: bold;
           text-decoration: none; width: 125px; border-radius: 25px; margin-top: 2px;
        }

    </style>
   </head>
    <body>
        <h1>Test</h1>
        <input id="button" type="button" tabindex="22" class="button" Value="Test FairBuzz" onClick="fairBuzz();" onMouseOver="mouseOver(this, 'reversebutton');" onMouseOut="mouseOver(this, 'button');"/>
    </body>
</html>
 
