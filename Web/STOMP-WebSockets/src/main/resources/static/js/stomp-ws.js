var stompClient = null;
var restriccionWhite = null;
var charged = false;
var conectado = false;
function setConnected(connected) {
    document.getElementById('inputName').disabled = connected;
    document.getElementById('connect').disabled = connected;
    document.getElementById('disconnect').disabled = !connected;
    document.getElementById('canvasDiv').style.visibility = connected ? 'visible' : 'hidden';
}

function connect() {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/messages', function (serverMessage) {
            showServerMessage(JSON.parse(serverMessage.body).content);
        });
        connectPlayer();
    });
}

function disconnect() {
    if (stompClient !== null) {
        disconnectPlayer();
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function connectPlayer() {
    var name = document.getElementById('inputName').value;
    stompClient.send("/app/map/player/connect", {}, JSON.stringify({'message': name}));
    conectado = true;
}

function disconnectPlayer() {
    var name = document.getElementById('inputName').value;
    stompClient.send("/app/map/player/disconnect", {}, JSON.stringify({'message': name}));
    restriccionWhite = null;
    charged = false;
    conectado = false;
}

window.onkeydown = function (e) {
    var name = document.getElementById('inputName').value;
    var code = e.keyCode ? e.keyCode : e.which;
    if (code > 36 && code < 41 && conectado)
        stompClient.send("/app/map/player/move", {}, JSON.stringify({'name': name, 'keyCode': code, 'speed': 2}));
};

window.onkeyup = function (e) {
    var name = document.getElementById('inputName').value;
    var code = e.keyCode ? e.keyCode : e.which;
    if (code > 36 && code < 41 && conectado)
        stompClient.send("/app/map/player/move", {}, JSON.stringify({'name': name, 'keyCode': code, 'speed': 0}));
};

function showServerMessage(message) {
    //var meta = message.meta;
    //var ip = message.ip;
    var canvas = document.getElementById('mapa');
    var ctx = canvas.getContext("2d");
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    var jugadores = null;
    var restriccionColored = null;
    if (!charged) {
        restriccionColored = message.restriccionColored;
        jugadores = message.jugadores;
        restriccionWhite = clone(message.restriccionWhite);
        charged = true;
    }
    else {
        restriccionColored = message[0];
        jugadores = message[1];
    }
    render(ctx, jugadores);
    render(ctx, restriccionColored);
    render(ctx, restriccionWhite);
}

function render(ctx, elements) {
    for (var q = 0; q < elements.length; q++) {
        ctx.beginPath();
        ctx.lineJoin = "round";
        var x = elements[q].x, y = elements[q].y;
        var w = elements[q].width, h = elements[q].height;
        var c = elements[q].col;
        ctx.rect(x - w, y - h, 2 * w, 2 * h);
        ctx.fillStyle = c;
        ctx.fill();
        ctx.stroke();
    }
}

function init() {
    //var btnSend = document.getElementById('send');
    //btnSend.onclick = sendMessage;
    var btnConnect = document.getElementById('connect');
    btnConnect.onclick = connect;
    var btnDisconnect = document.getElementById('disconnect');
    btnDisconnect.onclick = disconnect;
    disconnect();
}

function clone(obj) {
    var copy;

    // Handle the 3 simple types, and null or undefined
    if (null == obj || "object" != typeof obj)
        return obj;

    // Handle Date
    if (obj instanceof Date) {
        copy = new Date();
        copy.setTime(obj.getTime());
        return copy;
    }

    // Handle Array
    if (obj instanceof Array) {
        copy = [];
        for (var i = 0, len = obj.length; i < len; i++) {
            copy[i] = clone(obj[i]);
        }
        return copy;
    }

    // Handle Object
    if (obj instanceof Object) {
        copy = {};
        for (var attr in obj) {
            if (obj.hasOwnProperty(attr))
                copy[attr] = clone(obj[attr]);
        }
        return copy;
    }

    throw new Error("Unable to copy obj! Its type isn't supported.");
}

window.onload = init;
