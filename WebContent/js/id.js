var wrapper = new XHR();

function $(id) {
	return document.getElementById(id);
}

function login() {
	var email = $('iemail').value;
	var password = $('ipassword').value;
	
	wrapper.post('/WebService',
		{email:email, password:password},
		{'Content-Type':'application/json'})
	.then(function(data){
		if(data.status == (200)) {
			console.log(data);
			alert("Log in successful")
		}else {
			console.log(data);
			alert("Log in unsucessfully");
		} 
	}).catch(function(error) {
		console.log(error);
		});
}
