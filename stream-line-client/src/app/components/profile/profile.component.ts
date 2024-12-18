import {Component, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import {FormsModule} from "@angular/forms";
import {NgIf} from "@angular/common";
import Swal from "sweetalert2";
import {AuthService} from "../../services/auth.service";
import {UserService} from "../../services/user.service";
import {Profile, User} from "../../models/user";
import {error} from "@angular/compiler-cli/src/transformers/util";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  standalone: true,
  imports: [
    FormsModule,
    NgIf
  ],
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  isEditing: boolean = false;
  userId: string = '';

  user: Profile = {
    username: '',
    email: '',
    phone: '',
    age: 0,
    firstName: '',
    lastName: '',
  };

  constructor(private router: Router,
              private authService: AuthService,
              private userService: UserService) {}

  ngOnInit(): void {
    this.userId = this.authService.getUserFromJwt().id;
    this.userService.getUserById(this.userId).subscribe(res=>{
      this.user = res.data as Profile;
    }, error=>{
      Swal.fire('Error', error.error.message, 'error');
    })
    }

  onEditProfile() {
    this.isEditing = true;
  }

  onCancelEditing() {
    this.isEditing = false;
  }

  onSaveChanges() {
    this.isEditing = false;
    // Save updated profile data (e.g., API call)
    console.log('Profile updated:', this.user);
  }
  
  onChangePassword() {
    // Navigate to change password page or open a modal
    this.router.navigate(['/change-password']);
  }

  onDeleteProfile() {
    Swal.fire({
      title: 'Are you sure?',
      text: 'You won’t be able to recover this account!',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6',
      confirmButtonText: 'Yes, delete it!',
      cancelButtonText: 'Cancel',
    }).then((result) => {
      if (result.isConfirmed) {
        this.deleteProfile()
      }
    });
  }

  private deleteProfile() {
    this.userService.deleteUser(this.userId).subscribe(res=>{
      Swal.fire(
        'Deleted!',
        'Your profile has been deleted.',
        'success'
      ).then(() => {
        this.authService.logout();
        this.router.navigate(['/']);
      });
    },error => {
      Swal.fire(
        'Error!',
        error.error.message,
        'error'
      )
    })
  }
}
