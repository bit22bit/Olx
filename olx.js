var express =require('express');
const app =express();
var mysql=require('mysql');

var bodyparser = require('body-parser');

var connection=mysql.createConnection({
    host:"localhost",
    user:"root",
    password:"",
    database:'olx'
});

app.use(bodyparser.json());
app.use(bodyparser.urlencoded({extended:true}));

//===POST==REQUESTS=================================================================================

app.post('/register/',(req,res,next)=>{

	var data = req.body;
	var password = data.password;
	var email = data.email;

	connection.query("Select * FROM  signup where email = ?", [email], function(err, result, fields){
		connection.on('error', (err) => {
			console.log("[MySQL ERROR]", err);
		});

		if (result && result.length) {
			res.json("User already exist");
		}
		else{
			var insert_cmd = "insert into signup (email,password) values (?,?)";
			values = [email,password];

			console.log("executing: "+ insert_cmd+" "+ values);
			connection.query(insert_cmd,values,(err,result,fields)=>{
				connection.on('error', (err) => {
				console.log("[MySQL ERROR]", err);
				});	
				res.json("Registered!!");
				console.log("Registration Successfull");
			});
		}
	});
});

app.post('/posts/',(req,res,next)=>{

	var data = req.body;
	var heading = data.heading;
	var price = data.price;
	var detail = data.detail;

	connection.query("Select * FROM  posts", function(err, result, fields){
		connection.on('error', (err) => {
			console.log("[MySQL ERROR]", err);
		});
			
		var insert_cmd = "insert into posts (heading,price,detail) values (?,?,?)";
		values = [heading,price,detail];

		console.log("executing: "+ insert_cmd+" "+ values);
		connection.query(insert_cmd,values,(err,result,fields)=>{
			connection.on('error', (err) => {
			console.log("[MySQL ERROR]", err);
			});	
			res.json("Post Added!!");
			console.log("Post Added Successfull");
		});
		
	});
});


app.post('/login/',(req,res,next)=>{
	var data = req.body;
	var password = data.password;
	var email = data.email;

	connection.query("Select * FROM  signup where email = ?", [email], function(err, result, fields){
		connection.on('error', (err) => {
			console.log("[MySQL ERROR]", err);
		});

		if (result && result.length) {
			console.log(result);

			if (password==result[0].password) {
				res.json("User logged in");
				res.end;
			}
			else{
				res.json("Wrong Password");
				res.end;
			}
		}
		else{
			res.json("User not found");
			res.end;
		}
	});
});


//===GET==REQUEST========================================================================
app.get('/posts',(req, res) => {
	connection.query("Select * FROM posts",(err, rows, fields)=>{
		if (!err) {
			res.send(rows);
			console.log(rows);
		}
		else{
			console.log(err);
		}
	})
});

app.get('/',function(req, res) {
	res.write("<h1> Index Page</h1>");
});


var server =app.listen(8080,()=>{
    console.log("Server running at http://localhost:8080");
});