var stompClient = null;

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function () {
        stompClient.subscribe('/topic/public', onChatMessageReceived)
    });
}

function sendMessage() {
    var messageContent = document.getElementById('message').value.trim();
    if(messageContent && stompClient) {
        var chatMessage = {
            sender: user,
            content: messageContent,
        };
        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
        document.getElementById('message').value = '';
    }
}

function onChatMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    var messageElement = document.createElement('li');

    var elem = document.createElement("img");
    elem.setAttribute("src", "files/" + message.sender.avatar + ".png");
    elem.setAttribute("height", "15");
    elem.setAttribute("width", "15");
    messageElement.appendChild(elem);

    var name = " " + message.sender.userInfo.firstName + " " + message.sender.userInfo.lastName;
    messageElement.appendChild(document.createElement('span').appendChild(document.createTextNode(name)));
    messageElement.appendChild(document.createElement('br'));
    messageElement.appendChild(document.createElement('p').appendChild(document.createTextNode(message.content)));

    var messageArea = document.getElementById('messageArea');
    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}
