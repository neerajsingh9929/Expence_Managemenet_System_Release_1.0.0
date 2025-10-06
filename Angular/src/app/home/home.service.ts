import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HomeService {

  private getMenusUrl = '/getMenus';
  private addBudgetUrl = '/addBudget';
  private viewBudgetUrl = '/viewBudget'; // Append userId when calling
  private deleteBudgetUrl = '/deleteBudget'; // Append budgetId when calling
  private getBudgetChartUrl = '/getBudgetChart'; // Append userId when calling
  constructor( private http: HttpClient) { }



  getMenus(): Observable<any> {
    return this.http.get<any[]>(this.getMenusUrl);
   }
  addBudget(budget: any): Observable<any> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post<any>(this.addBudgetUrl, budget, { headers });
  }
  viewBudget(userId: string): Observable<any> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    // Ensure userId is valid and convert to string if needed
    const validUserId = userId ? userId.toString() : '';
    console.log('ViewBudget - sending userId:', validUserId);
    return this.http.post<any>(this.viewBudgetUrl, JSON.stringify(validUserId), { headers });
  }
    deleteBudget(selectedBudgetId: number) {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.delete<any>(`${this.deleteBudgetUrl}/${selectedBudgetId}`, { headers });
  }
  
  getBudgetChartData(userId: string): Observable<any> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post<any>(this.getBudgetChartUrl, JSON.stringify(userId), { headers });
  }
  
  getBudgetsByMonth(userId: string, month: string): Observable<any> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    const requestBody = { userId: userId, month: month };
    return this.http.post<any>('/getBudgetsByMonth', requestBody, { headers });
  }
  
  getBudgetsByFilters(userId: string, month?: string, category?: string): Observable<any> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    const requestBody: any = { userId: userId };
    
    if (month && month !== '') {
      requestBody.month = month;
    }
    
    if (category && category !== '') {
      requestBody.category = category;
    }
    
    return this.http.post<any>('/getBudgetsByFilters', requestBody, { headers });
  }
  
  exportBudgetCSV(userId: string, month?: string, category?: string): Observable<Blob> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    const requestBody: any = { userId: userId };
    
    if (month && month !== '') {
      requestBody.month = month;
    }
    
    if (category && category !== '') {
      requestBody.category = category;
    }
    
    return this.http.post('/exportBudgetCSV', requestBody, { 
      headers, 
      responseType: 'blob' 
    });
  }
}
