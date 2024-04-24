import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import {AuthService} from "../../services/auth.service";
import {passwordMatchValidator} from "../../validators/password-match.validator";
import {RegistrationModel} from "../../interfaces/registration-model";
@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  registerForm:any

  constructor(
    private fb: FormBuilder,
    private authService:AuthService,
    private messageService:MessageService,
    private router:Router
  ) {
    this.registerForm = this.fb.group({
      username: ['', [Validators.required, Validators.pattern(/^[a-zA-Z]+(?: [a-zA-Z]+)*$/)]],
      firstName: ['', [Validators.required, Validators.pattern(/^[a-zA-Z]+(?: [a-zA-Z]+)*$/)]],
      lastName: ['', [Validators.required, Validators.pattern(/^[a-zA-Z]+(?: [a-zA-Z]+)*$/)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      passwordConfirm: ['', [Validators.required, Validators.minLength(6)]]
    })
    this.registerForm.setValidators(passwordMatchValidator)
  }

  get username() {
    return this.registerForm.controls['username']
  }

  get email() {
    return this.registerForm.controls['email']
  }

  get password() {
    return this.registerForm.controls['password']
  }
  get firstName() {
    return this.registerForm.controls['firstName']
  }
  get lastName() {
    return this.registerForm.controls['lastName']
  }

  get passwordConfirm() {
    return this.registerForm.controls['passwordConfirm']
  }

  submitDetails() {
    const postData = this.registerForm.getRawValue();
    // console.log(postData);
     // delete postData.passwordConfirm
    this.authService.registerUser(postData as RegistrationModel).subscribe(
      response => {
        this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Registered successfully' })
        this.router.navigateByUrl('login')
      },
      error => {
        console.log(error);

        this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Something went wrong' })
      }
    )
  }
}
