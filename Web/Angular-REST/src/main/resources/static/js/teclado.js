/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

angular.module('Keyboard',[])
    .service('KeyboardService', function ($document){
      
    /*Variables para el teclado*/
      var UP = 'up',
          RIGHT = 'right',
          DOWN = 'down',
          LEFT = 'left';
    
    /*Variables de Iniciaización*/
      var keyboardMap = {
          37: LEFT,
          38: UP,
          39: RIGHT,
          40: DOWN
      };
      
      /*Vinculo con el teclado y htm */
      this.init = function(){
          var temp = this;
          this.keyEventHandlers =[];
          $document.bind('keydown', function (event){
              var key = keyboardMap[event.which];
              
              if(key){
                  event.preventDefault();
                  self._handleKeyEvent(key,event);
              }
          });       
      };
      
      this._handleKeyEvent = function (key,event){
          var callbacks = this.keyEventHandlers;
          if(!callbacks)return;
          event.preventDefault();
          if(callbacks){
              for(var i = 0;i < callbacks.length; i++){
                  var cb = callbacks[i];
                  cb(key,event);
              }
          }
      };
      
      this.on = function (cb){
          this.keyEventHandlers.push(cb);
      };
    });