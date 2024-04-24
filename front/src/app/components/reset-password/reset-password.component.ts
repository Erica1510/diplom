import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import {AuthService} from "../../services/auth.service";
import {resetPasswordMatchValidator} from "../../validators/reset-password-match.validator";
import {PasswordResetModel} from "../../interfaces/password-reset-model";

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent {
    resetForm:any

    constructor(
      private fb:FormBuilder,
      private authService:AuthService,
      private messageService:MessageService,
      private router:Router
      ) {
        this.resetForm = this.fb.group({
          newPass: ['', [Validators.required, Validators.minLength(6)]],
          passwordConfirm: ['', [Validators.required, Validators.minLength(6)]]
        })
        this.resetForm.setValidators(resetPasswordMatchValidator)
    }

    get newPass() {
      return this.resetForm.controls['newPass']
    }

    get passwordConfirm() {
      return this.resetForm.controls['passwordConfirm']
    }

    submitDetails() {
      const postData = {...this.resetForm.value}
      postData['passToken'] = sessionStorage.getItem('passwordResetToken')
      console.log(postData);

      this.authService.resetPassword(postData as PasswordResetModel).subscribe(
        response => {
          sessionStorage.clear()
          this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Password reset successfully'})
          this.router.navigate(['login'])
        },
        error => {
          this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Something went wrong' })
        }
      )
    }
}
