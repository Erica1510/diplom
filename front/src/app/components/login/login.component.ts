import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import {AuthService} from "../../services/auth.service";
import {UserService} from "../../services/user.service";
import {User} from "../../interfaces/user";
import {LoginModel} from "../../interfaces/login-model";


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginForm:any

  constructor(
    private fb:FormBuilder,
    private authService:AuthService,
    private messageService:MessageService,
    private userService:UserService,
    private router:Router
    ) {
      this.loginForm = this.fb.group({
        username: ['', [Validators.required, Validators.pattern(/^[a-zA-Z]+(?: [a-zA-Z]+)*$/)]],
        password: ['', [Validators.required, Validators.minLength(6)]]
      })
  }

  get username() {
    return this.loginForm.controls['username']
  }

  get password() {
    return this.loginForm.controls['password']
  }

  submitDetails() {
    const postData = {...this.loginForm.value}
    this.authService.loginUser(postData as LoginModel).subscribe(
      response => {
        const user = response as User
        this.userService.setUser(user)
        sessionStorage.setItem('user', JSON.stringify(user))
        this.router.navigate(['chat'], { replaceUrl: true })
      },
      error => {
        console.log(error);
        this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Something went wrong'})
      }
    )
  }
}
