import { Component } from '@angular/core';
import {Router, RouterLink, RouterLinkActive} from "@angular/router";
import {NgIf} from "@angular/common";
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-nav',
  standalone: true,
  imports: [
    RouterLinkActive,
    RouterLink,
    NgIf
  ],
  templateUrl: './nav.component.html',
  styleUrl: './nav.component.css'
})
export class NavComponent {
  isLoggedIn: boolean = false;

  constructor(private authService: AuthService,
              private router: Router,) {
  }

  ngOnInit(): void {
    this.authService.getJwtTokenAsObservable().subscribe((jwtToken) => {
      this.isLoggedIn = !!jwtToken;
    });
  }

  onLogout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }

}
