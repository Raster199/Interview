var index = 0;
var numberQuestion;
function newOpros(){
  numberQuestion = 1;
  var newOpros = "<div id=\"newOpros\" ><h1>Создание нового опроса</h1>	<button onClick=\"addQuestion()\" type=\"button\">Добавить вопрос</button>	<button onClick=\"sendToServer()\" type=\"button\">Сохранить опрос</button><p></p><form id=\"toServer\"><h2>Задать имя опроса</h2><p><input charset=\"UTF-8\" type=\"text\" name=\"nameSurvey\" required width=100%></p></form></div>"
  $('#content').html(newOpros);

};

function addQuestion(){

  $('#win').removeAttr("style");
  document.getElementById('inWindow').innerHTML = '<p>Наименование вопроса</p><p><input type="text" name="nameQuestion" required size="45"><p>  <p><input type="checkbox" name="isRequired" value="required"> Пользователь обязан ответить на вопрос</p><input type="hidden" id="typeAnswer" name="typeAnswer" ></p><p>Выбрать тип ответа</p><p><input type="button" id="buttonCheck" value="checkbox" onclick="choiceTypeAnswer(\'checkbox\')" ><input type="button" id="buttonRadio" value="radio" onclick="choiceTypeAnswer(\'radio\')"><input type="button" id="buttonText" value="text" onclick="choiceTypeAnswer(\'text\')" ></p><p class="add"><input type="button" value="+" style="display:none;" ></p>';
  $('#inWindow').append('<div id="contentInWindow"></div>')
};

function newQuestion(){
  var str = $("#changeAliasForm").serializeArray();
  var result = "";
  var typeAnswer = "null";
  var num = 0;
  var isRequired = "";

  if($("#changeAliasForm").valid()){

	 $.each(str,function(){
	   
	   if (this.name == "isRequired"){
		  isRequired = "true";
	   }else if (this.value == "checkbox"){
		  typeAnswer = "checkbox";
	   }else if (this.value == "text"){
		  typeAnswer = "text";
	   }else if (this.value == "radio"){
		  typeAnswer = "radio";
	   }
	 });

	 $.each(str,function(){
	   
       if (this.name == "nameQuestion"){
	     result += "<p><input class='styleFormQuestion' type='text' name='question" + numberQuestion + "' value='№ " + numberQuestion + " " + this.value + "' readonly ><input name='isRequired" + numberQuestion + "' value='" + isRequired + "' type='hidden'></p><p class='styleFormContext'>Возможные ответы :</p>";
	   }else if (!(this.name === "typeAnswer") && !(this.name === "isRequired")){
		 num = num + 1;
	     result += "<p><input type='" + typeAnswer + "'><input name='typeAnswer" + numberQuestion + "' value='" + typeAnswer + "' type='hidden'><input class='styleFormAnswer' name='answer" + numberQuestion + "" + num +  "' type='text' value='" + this.value + "' readonly ></p>"
	   }
 	 });
	 
	 numberQuestion = numberQuestion + 1;
     $('#toServer').append(result);
     $('#win').css('display','none');
  }

//$(document).ready(function(){
  /* $("form").validate({
       rules:{
            nameQuestion:{
                required: true,
            },
            typeAnswer:{
                required: true,
            },
       },
       messages:{
            nameQuestion:{
                required: "Это поле обязательно для заполнения",
            },
            typeAnswer:{
                required: "Это поле обязательно для заполнения",
            },
       }
//    });

  });*/  
};

function del(button){
  index = index + 1;
  var type = document.getElementById("contentInWindow");
  var row = button.parentNode;
  type.removeChild(row);
  if ($('input[value="+"]').length < 2){
    $('#buttonText').prop('disabled',false);
    $('#buttonCheck').prop('disabled',false);
    $('#buttonRadio').prop('disabled',false);
  };
};

function choiceTypeAnswer(flag){
  var countAnswer = $('input[value="+"]').length;
  if (countAnswer == 1){
    index = 1;
    $('#buttonText').prop('disabled',true);
    $('#buttonCheck').prop('disabled',true);
    $('#buttonRadio').prop('disabled',true);
  }else{
    index = index + 1;
  }
  
  if (flag == "checkbox"){
	$('#contentInWindow').append('<p id="rowCheckbox"><input type="button" value="+" onclick=choiceTypeAnswer(\'checkbox\')><input type="checkbox"><input type="text" name="answer' + index + '" required placeholder="Наименование ответа"><input type="button" onclick="del(this)" value="del"></p>');	
	$('#typeAnswer').val("checkbox");
  }
  else if (flag == "text"){$('#contentInWindow').append('<p id="rowText"><input type="button" value="+" onclick=choiceTypeAnswer(\'text\')><input type="text" name="answer' + index + '" placeholder="Наименование ответа"><input type="button" onclick="del(this)" value="del"></p>');
	$('#typeAnswer').val("text");
  }
  else if (flag == "radio"){$('#contentInWindow').append('<p id="rowRadio"><input type="button" value="+" onclick=choiceTypeAnswer(\'radio\')><input type="radio" ><input type="text" name="answer' + index + '" required placeholder="Наименование ответа"><input type="button" onclick="del(this)" value="del"></p>');
	$('#typeAnswer').val("radio");
  };

};

function sendToServer(){
  var data = $('#toServer').serialize();

  if($("#toServer").valid()){
	//alert(data);
    $.ajax({
	url: "/Interview/saveTemplate",
	type: "POST",
	data: data
	/*success: success,
	dataType: "json"*/
	});

  };
};

function listSurvey(){
  
    $.ajax({
	  url: "/Interview/listSurvey",
	  type: "GET",
      success: function(html){
        $('#content').html(html);
      }	  
	});
};

function delRowList(button){

	var row = button.parentNode.parentNode;
	var idSurvey = document.getElementById ('tableSurvey').rows[row.rowIndex].cells[1].firstChild.value;
	document.getElementById("tableSurvey").deleteRow(row.rowIndex);

    $.ajax({
	  url: "/Interview/delRowList",
	  type: "GET",
	  data: {"idSurvey": idSurvey}
	});
};

function addEmployee(){
	
	var data = "<h2>Добавить участника опроса</h2>"+
	           "<form id='saveEmployeeForm'>"+
	           "<p>Фамилия</p><input type='text' name='lastname' required>"+
	           "<p>Имя</p><input type='text' name='firstname' required>"+
	           "<p>Отчество</p><input type='text' name='middlename' required>"+
	           "<p>Email</p><input type='text' name='email' required>"+
	           "</form>"+
	           "<p><input type='button' onclick='saveEmployee()' value='Сохранить'></p>"
	$('#content').html(data);
};

function saveEmployee(){
	
	var data = $("#saveEmployeeForm").serializeArray();
	/*var data = $("#saveEmployeeForm").serialize();
	alert(data);*/
	
	if ($("#saveEmployeeForm").valid()){
			
		$.ajax({
		  url: "/Interview/saveEmployee",
		  type: "POST",
		  data: data
		});	
	};
};

function sendSurveyFromEmployee(){
	
	var email = "i.dybtsyn@rtinform.ru";
			
	$.ajax({
	  url: "/Interview/sendSurveyFromEmployee",
	  type: "POST",
	  data: {'email' : email}
	});	
	
};


		






