/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsw.laberinto.controller.messages;

/**
 *
 * @author 2086255
 */
public class ServerMessage {

    private Object content;

    public ServerMessage(Object content) {
        setContent(content);
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
