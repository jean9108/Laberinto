(function () {
    var app = angular.module('modone', []);
    var restriccionWhite = null;
    var conectado = false;

    app.controller('laberinto', function ($scope, $http, $log) {
        $scope.name = "";

        $scope.connect = function (name) {
            $http({
                method: 'GET',
                url: '/laberinto/player/' + name + '/connect'
            }).success(function (data, status, headers, config) {
                connect();
                document.getElementById('err').innerHTML = "";
                showServerMessage(data);
            }).error(function (data, status, headers, config) {
                if (name === null || name === "") {
                    document.getElementById('err').innerHTML = "Debe ingresar un nombre para empezar a jugar.";
                } else {
                    document.getElementById('err').innerHTML = data.message;
                }
                disconnect();
            });
        };
        $scope.disconnect = function (name) {
            $http({
                method: 'GET',
                url: '/laberinto/player/' + name + '/disconnect'
            }).success(function (data) {
                disconnect();
                showServerMessage2(data);
            });
        };

        $scope.move = function (name, keyCode, speed) {
            $http({
                method: 'POST',
                url: '/laberinto/player/' + name + '/move/' + keyCode + '/speed/' + speed
            }).success(function (data) {
                showServerMessage2(data);
            });
        };

        window.onkeydown = function (e) {
            var name = document.getElementById('inputName').value;
            var code = e.keyCode ? e.keyCode : e.which;
            if (code > 36 && code < 41 && conectado)
                $scope.move(name, code, 2);
        };

        window.onkeyup = function (e) {
            var name = document.getElementById('inputName').value;
            var code = e.keyCode ? e.keyCode : e.which;
            if (code > 36 && code < 41 && conectado)
                $scope.move(name, code, 0);
        };

        function connect() {
            conectado = true;
            setConnected(conectado);
            console.log("Connected");
        }

        function disconnect() {
            conectado = false;
            setConnected(conectado);
            console.log("Disconnected");
        }

        function init() {
            var btnConnect = document.getElementById('connect');
            btnConnect.onclick = connect;
            var btnDisconnect = document.getElementById('disconnect');
            btnDisconnect.onclick = disconnect;
            disconnect();
        }

        window.onload = init;

        function setConnected(connected) {
            document.getElementById('inputName').disabled = connected;
            document.getElementById('connect').disabled = connected;
            document.getElementById('disconnect').disabled = !connected;
            document.getElementById('canvasDiv').style.visibility = connected ? 'visible' : 'hidden';
        }

        function showServerMessage(message) {
            //var meta = message.meta;
            //var ip = message.ip;
            var canvas = document.getElementById('mapa');
            var ctx = canvas.getContext("2d");
            ctx.clearRect(0, 0, canvas.width, canvas.height);
            var jugadores = message.jugadores;
            var restriccionColored = message.restriccionColored;
            restriccionWhite = clone(message.restriccionWhite);
            render(ctx, jugadores);
            render(ctx, restriccionColored);
            render(ctx, restriccionWhite);
        }

        function showServerMessage2(message) {
            //var meta = message.meta;
            //var ip = message.ip;
            var canvas = document.getElementById('mapa');
            var ctx = canvas.getContext("2d");
            ctx.clearRect(0, 0, canvas.width, canvas.height);
            var jugadores = message[1];
            var restriccionColored = message[0];
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
    }
    );
})();