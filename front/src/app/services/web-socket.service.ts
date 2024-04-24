import { Injectable } from '@angular/core';
import SockJS from 'sockjs-client';
import { Client, Message, over } from 'stompjs';
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  socket:WebSocket
  stompClient:Client
  baseUrl = environment.webSocketUrl

  constructor() {
    this.socket = new SockJS(this.baseUrl)
    this.stompClient = over(this.socket)
  }

  public connect() {
     this.stompClient.connect({}, this.onConnected, this.onError)
  }

  private onConnected(frame:any) {
    console.log('Connected:', frame);
  }

  private onError(err:any) {
    console.log('Error:',err);
  }

  public disconnect() {
    this.stompClient.disconnect(this.onDisconnected)
  }

  private onDisconnected() {
    console.log('Disconnected');
  }

  public subscribe(destination:string, callback: (message:Message) => void) {
    this.stompClient.subscribe(destination, callback)
  }

  public sendMessage(destination:string, body:string) {
    this.stompClient.send(destination, { }, body)
  }
}
