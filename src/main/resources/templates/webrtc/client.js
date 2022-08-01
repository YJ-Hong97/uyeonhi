/**
 * 
 */
 import adapter from 'adapter.js';
 configuration =  null;
 var peerConnection = new RTCPeerConnection(configuration);
 
 var dataChannel = peerConnection.createDataChannel("dataChannel",{reliable:true});
 
 dataChannel.onerror = function(){
	console.log("error",error);
};
 dataChannel.onclose = function(){
	console.log("datachannel is closed");
};

peerConnection.createOffer(function(offer){
	send({
		event:"offer",
		data:offer
	});
	peerConnection.setLocalDescription(offer);
},function(error){
	//handle error
});