import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  email: string = '';
  password: string = '';
  role: string = 'USER'; // You can make this dynamic if needed

  constructor(private http: HttpClient, private router: Router) {}

  // Method to handle form submission and registration
  onSubmit() {
    const userData = {
      email: this.email,
      password: this.password,
      role: this.role
    };

    // Post the user data to the backend API for registration
    this.http.post('http://localhost:8080/api/users/register', userData, { withCredentials: true })
      .subscribe(
        response => {
          console.log('Registration successful', response);
          this.router.navigate(['/login']); // Redirect to login after successful registration
        },
        error => {
          console.error('Registration failed', error);
        }
      );
  }
}
