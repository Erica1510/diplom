import { AbstractControl, ValidationErrors, ValidatorFn } from "@angular/forms";


export const resetPasswordMatchValidator:ValidatorFn = (control: AbstractControl):ValidationErrors | null => {
    const newPass = control.get('newPass')
    const passwordConfirm = control.get('passwordConfirm')

    if(!newPass || !passwordConfirm) {
        return null
    }

    return newPass.value === passwordConfirm.value ? null : { passwordMismatch: true }
}
