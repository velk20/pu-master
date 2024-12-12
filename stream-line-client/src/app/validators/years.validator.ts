import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export class YearsValidator {
  static minimumAge(min: number): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const value = control.value;
      if (value !== null && value <= min) {
        return { minimumAge: { requiredAge: min, actualAge: value } };
      }
      return null;
    };
  }
}
