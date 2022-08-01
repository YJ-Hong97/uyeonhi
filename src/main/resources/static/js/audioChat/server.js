/**
 * 
 */

/*var WebSocketServer = require('ws').Server;
var ws = new WebSocketServer({ port: 3000 });

ws.on('listening', function () {
	console.log("Server started with port 3000");
});

ws.on('connection', function (connection) {

	console.log("username is connected");
	connection.send("Hello from server");

	/* Action to do when username send messages 
	connection.on('message', function (message) {
	

		console.log("Message from username....."+message);
	    
	});


	/* Action to do When username try to close the connection 
	connection.on('close', function () {
		console.log("Disconnecting username"); 
	});  


});*/


//require our websocket library 
/*var WebSocketServer = require('ws').Server; 
Object.assign(global, { WebSocket: require('ws') });

//creating a websocket server at port 9090 
var wss = new WebSocketServer({port: 9090}); 

//all connected to the server usernames 
var usernames = {};

//when a username connects to our sever 
wss.on('connection', function(connection) {
  
   console.log("username connected 연결연결");
	
   //when server gets a message from a connected username 
   connection.on('message', function(message) { 
	
	  var data;
		
	  //accepting only JSON messages 
	  try { 
		 data = JSON.parse(message); 
	  } catch (e) { 
		 console.log("Invalid JSON"); 
		 data = {}; 
	  }
		
	  //switching type of the username message 
	  switch (data.type) { 
		 //when a username tries to login 
		 case "login": 
			console.log("username logged", data.name); 
				
			//if anyone is logged in with this username then refuse 
			if(usernames[data.name]) { 
			   sendTo(connection, { 
				  type: "login", 
				  success: false 
			   }); 
			} else { 
			   //save username connection on the server 
			   usernames[data.name] = connection; 
			   connection.name = data.name;
					
			   sendTo(connection, { 
				  type: "login", 
				  success: true 
			   }); 
			} 
				
			break;
				
		 case "offer": 
			//for ex. usernameA wants to call usernameB 
			console.log("Sending offer to: ", data.name); 
				
			//if usernameB exists then send him offer details 
			var conn = usernames[data.name]; 
				
			if(conn != null) { 
			   //setting that usernameA connected with usernameB 
			   connection.otherName = data.name; 
					
			   sendTo(conn, { 
				  type: "offer", 
				  offer: data.offer, 
				  name: connection.name 
			   }); 
			} 
				
			break;
				
		 case "answer": 
			console.log("Sending answer to: ", data.name); 
			//for ex. usernameB answers usernameA 
			var conn = usernames[data.name]; 
				
			if(conn != null) { 
			   connection.otherName = data.name; 
			   sendTo(conn, { 
				  type: "answer", 
				  answer: data.answer 
			   });
			} 
				
			break;
				
		 case "candidate": 
			console.log("Sending candidate to:",data.name); 
			var conn = usernames[data.name];  
				
			if(conn != null) { 
			   sendTo(conn, { 
				  type: "candidate", 
				  candidate: data.candidate 
			   }); 
			} 
				
			break;
				
		 case "leave": 
			console.log("Disconnecting from", data.name); 
			var conn = usernames[data.name]; 
			conn.otherName = null; 
				
			//notify the other username so he can disconnect his peer connection 
			if(conn != null) { 
			   sendTo(conn, { 
				  type: "leave" 
			   }); 
			}  
				
			break;
				
		 default: 
			sendTo(connection, { 
			   type: "error", 
			   message: "Command not found: " + data.type 
			});
				
			break; 
	  }  
   });
	
   //when username exits, for example closes a browser window 
   //this may help if we are still in "offer","answer" or "candidate" state 
   connection.on("close", function() { 
	
	  if(connection.name) { 
		 delete usernames[connection.name]; 
			
		 if(connection.otherName) { 
			console.log("Disconnecting from ", connection.otherName); 
			var conn = usernames[connection.otherName]; 
			conn.otherName = null;  
				
			if(conn != null) { 
			   sendTo(conn, { 
				  type: "leave" 
			  }); 
			}  
		 } 
	  } 
   });  
	
   connection.send("Hello world"); 
}); 
 
function sendTo(connection, message) { 
   connection.send(JSON.stringify(message)); 
}*/

const Socket = require("websocket").server
const http = require("http")

const server = http.createServer((req, res) => { })

server.listen(3000, () => {
	console.log("Listening on port 3000...")

})

const webSocket = new Socket({ httpServer: server })

let usernames = []

webSocket.on('request', (req) => {
	const connection = req.accept()

console.log("connection" , connection);

	connection.on('message', (message) => {
		const data = JSON.parse(message.utf8Data)

console.log("data" , data);

		switch (data.type) {
			case "store_username":

				if (username != null) {
					return
				}

				const newusername = {
					conn: connection,
					username: data.username
				}

				usernames.push(newusername)
				console.log(newusername.username)
				break

			case "store_offer":
				if (username == null)
					return
				username.offer = data.offer
				break

			case "store_candidate":
				if (username == null) {
					return
				}
				if (username.candidates == null)
					username.candidates = []

				username.candidates.push(data.candidate)
				break
			case "send_answer":
				if (username == null) {
					return
				}

				sendData({
					type: "answer",
					answer: data.answer
				}, username.conn)
				break
			case "send_candidate":
				if (username == null) {
					return
				}

				sendData({
					type: "candidate",
					candidate: data.candidate
				}, username.conn)
				break
			case "join_call":
				if (username == null) {
					return
				}

				sendData({
					type: "offer",
					offer: username.offer
				}, connection)

				username.candidates.forEach(candidate => {
					sendData({
						type: "candidate",
						candidate: candidate
					}, connection)

				})

				break
		}

	})
	
	connection.on('close' , (reason , description) =>{
		usernames.forEach(username =>{
			if(username.conn == connection){
				usernames.splice(usernames.indexOf(username),1)
				return
			}
		})
	})
	
})

function sendData(data, conn) {
	conn.send(JSON.stringify(data))

}

function findusername(username) {

	for (let i = 0; i < usernames.length; i++) {
		if (username[i].username == username)
			return usernames[i]

	}
}
