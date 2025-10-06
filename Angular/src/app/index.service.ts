import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, throwError } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class IndexService {

  private baseUrl = '/test';
  private bookUrl = '/book';
  private registerUrl = '/register';
  private loginUrl = '/login';
  constructor(private http: HttpClient) { }

  getData(): Observable<any> {
    return this.http.get(this.baseUrl, { responseType: 'text' });
  }

 
  registerEmployee(employee: any): Observable<any> {
    return this.http.post<any>(this.registerUrl, employee);
  }
  login(employee: any): Observable<any> {
    return this.http.post<any>(this.loginUrl, employee);
  }


  private handleError(error: HttpErrorResponse) {
    console.error('API error:', error);
    return throwError(() => new Error('Something went wrong; please try again later.'));
  }
}
