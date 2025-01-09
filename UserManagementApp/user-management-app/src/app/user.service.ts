import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiUrl = 'http://localhost:8080/api/users';

  constructor(private http: HttpClient) {}

  // Register user
  register(userData: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, userData, { withCredentials: true });
  }

  // Login user (you can expand this as needed)
  login(userData: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/login`, userData, { withCredentials: true });
  }
}
