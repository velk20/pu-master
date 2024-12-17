import { Component } from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {FormBuilder, ReactiveFormsModule, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {ToastrService} from "ngx-toastr";
import {JwtTokenResponse, LoginUser} from "../../models/auth";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgIf
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  constructor(
    private authService: AuthService,
    private fb: FormBuilder,
    private router: Router,
    private toastrService: ToastrService,
  ) {
  }



  loginForm = this.fb.group({
    username: ['', [Validators.required]],
    password: ['', [Validators.required]],
  });

  onSubmit() {

    let loginUser: LoginUser = {
      username: this.loginForm.value.username || '',
      password: this.loginForm.value.password || '',
    }

    this.authService.loginUser(loginUser).subscribe(
      res => {
        this.authService.login(res.data as JwtTokenResponse);
        this.router.navigate(['/dashboard']);
        this.toastrService.success('Login successfully!');
      }
    )
  }

}
