(function () {
    var app = angular.module('modone', []);
    app.controller('plan_control', function ($scope, $http, $log) {
        $scope.arreglo = [];
        $scope.seleccionado = "";

        $scope.loadData = function () {
            var configList = {
                method: "GET",
                url: "blueprints"
            };

            var response = $http(configList);

            response.success(function (data, status, headers, config) {
                $scope.arreglo = data;
            });

            response.error(function (data, status, headers, config) {
                alert("The petition has failed. HTTP Status:" + status);
            });
        };
        // $scope.loadData();

        $scope.charge = function (blueprintname) {
            $http({
                method: 'GET',
                url: '/blueprints/' + blueprintname
            }).success(function (data) {
                //console.log(data);
                var canvas = document.getElementById("grafica");
                var canvasContext = canvas.getContext("2d");
                var svg = document.getElementById("graficaSVG");
                var svgContext;
                switch (data.name) {
                    case 'rayon':
                    case 'poligono1':
                        //Drawing in Canvas
                        canvasContext.beginPath();
                        canvasContext.lineJoin = "round";
                        canvasContext.moveTo(data.points[0].x, data.points[0].y);
                        for (var i = 1; i < data.points.length; i++) {
                            canvasContext.lineTo(data.points[i].x, data.points[i].y);
                        }
                        canvasContext.stroke();
                        //Drawing in SVG
                        svgContext = document.createElementNS("http://www.w3.org/2000/svg", "polyline");
                        var points = "";
                        for (var i = 0; i < data.points.length; i++) {
                            points += " " + data.points[i].x + "," + data.points[i].y;
                        }
                        svgContext.setAttribute('points', points);
                        svgContext.setAttribute('stroke', 'black');
                        svgContext.setAttribute('fill', 'none');
                        svg.appendChild(svgContext);
                        break;
                    case 'larecta':
                        //Drawing in Canvas
                        canvasContext.beginPath();
                        canvasContext.lineJoin = "round";
                        canvasContext.moveTo(data.points[0].x, data.points[0].y);
                        canvasContext.lineTo(data.points[1].x, data.points[1].y);
                        canvasContext.stroke();
                        //Drawing in SVG
                        svgContext = document.createElementNS("http://www.w3.org/2000/svg", "line");
                        svgContext.setAttribute('x1', data.points[0].x);
                        svgContext.setAttribute('y1', data.points[0].y);
                        svgContext.setAttribute('x2', data.points[1].x);
                        svgContext.setAttribute('y2', data.points[1].y);
                        svgContext.setAttribute('stroke', 'black');
                        svg.appendChild(svgContext);
                        break;
                }
            });
        };
    });
})();





