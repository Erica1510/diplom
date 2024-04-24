import { AbstractControl, ValidationErrors, ValidatorFn } from "@angular/forms";

export const passwordMatchValidator:ValidatorFn = (control:AbstractControl): ValidationErrors | null => {
    const password = control.get('password')
    const passwordConfirm = control.get('passwordConfirm')
    if(!password || !passwordConfirm) {
        return null
    }

    return password.value === passwordConfirm.value ? null : { passwordMismatch: true }
}
