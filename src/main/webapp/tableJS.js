function deleteRow(r){
	var i=r.parentNode.parentNode.rowIndex;
	document.getElementById("Table1").deleteRow(i);
	}
	function addRow(){

	var table=document.getElementById("Table1");
	var index=table.rows.length;
	var row=table.insertRow(index-1);
	var name=row.insertCell(0);
	var countheat=row.insertCell(1);
	var tempT11=row.insertCell(2);
	var tempT21=row.insertCell(3);
	var heightsyst=row.insertCell(4);
	var dh=row.insertCell(5);
	var countglikol=row.insertCell(6);
	var del=row.insertCell(7);

	name.innerHTML="<tr><td><select name='"+index+"system'><option value=\"1\">Отопление</option><option value=\"2\">Вентиляция</option><option value=\"3\">ГВС</option></select></td></tr>";
	countheat.innerHTML="<tr><td><input name=\""+index+"countheat\" type=\"text\" size=\"1\" required></td></tr>";
	tempT11.innerHTML="<tr><td><input name=\""+index+"tempt11\" type=\"text\" size=\"1\" required></td></tr>";
	tempT21.innerHTML="<tr><td><input name=\""+index+"tempt21\" type=\"text\" size=\"1\" required></td></tr>";
	heightsyst.innerHTML="<tr><td><input name=\""+index+"heightsyst\" type=\"text\" size=\"1\" required></td></tr>";
	dh.innerHTML="<tr><td><input name=\""+index+"dh\" type=\"text\" size=\"1\" required></td></tr>";
	countglikol.innerHTML="<tr><td><input name=\""+index+"countglikol\" type=\"text\" size=\"1\" required></td></tr>";
	del.innerHTML="<tr><td><button type=\"button\" onclick=\"deleteRow(this)\"><img src='http://localhost:80/ServletTest/src/main/webapp/deleteButton.jpg' alt='Удалить' width='20' height='20'> </button> </td></tr>";
	}
