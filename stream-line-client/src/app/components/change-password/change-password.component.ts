import { Component } from '@angular/core';
import {FormsModule} from "@angular/forms";
import {Router} from "@angular/router";

@Component({
  selector: 'app-change-password',
  standalone: true,
  imports: [
    FormsModule
  ],
  templateUrl: './change-password.component.html',
  styleUrl: './change-password.component.css'
})
export class ChangePasswordComponent {
  constructor(private router: Router) {
  }
  passwordData = {
    oldPassword: '',
    newPassword: '',
    confirmPassword: ''
  };

  onChangePassword() {
    const { oldPassword, newPassword, confirmPassword } = this.passwordData;

    // Basic validation
    if (!oldPassword || !newPassword || !confirmPassword) {
      alert('All fields are required.');
      return;
    }

    if (newPassword !== confirmPassword) {
      alert('New password and confirm password do not match.');
      return;
    }

    // Simulate password change logic
    // Replace this with a service call to update the password
    console.log('Password changed successfully:', {
      oldPassword,
      newPassword
    });
    alert('Password changed successfully.');

    // Clear the form after successful submission
    this.passwordData = {
      oldPassword: '',
      newPassword: '',
      confirmPassword: ''
    };
  }

  onCancelChangePassword() {
    this.resetForm();

    this.router.navigate(['/profile'])
  }

  private resetForm() {
    // Clear the form and reset the object
    this.passwordData = {
      oldPassword: '',
      newPassword: '',
      confirmPassword: ''
    };
  }
}
