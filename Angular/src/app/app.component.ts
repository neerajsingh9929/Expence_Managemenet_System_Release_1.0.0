import { Component } from '@angular/core';
import { IndexService } from './index.service';
import { ResponseOutDTO } from './DTO/ResponseOutDTO';
import { BudgetOutDTO } from './DTO/BudgetOutDTO';
import { Router } from '@angular/router';
declare var bootstrap: any;
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Angular';
  name: string = 'Welcome';
  bookList: any = [];
  messageSuccessString: string = "Registerd Successfully Kindly Login with";
  responseOutDto = new ResponseOutDTO();
  employee: any = {
    employeeName: '',
    employeeId: 0,
    employeeDOB: '',
    employeeProject: '',
    password: ''
  };

  registerDisabled: boolean = false;
  showLogin: boolean = false;
  isHomeRoute: boolean = false;
  budgetOutDto = new BudgetOutDTO();
  constructor(private indexService: IndexService, private router: Router) {}

  ngOnInit() {
    // Only fetch data, do not show any success message on init
    this.indexService.getData().subscribe(data => {
      this.name = data;
    }, error => {
      console.error('Error fetching data', error);
    });

    // Detect route changes to show/hide login/register UI
    this.router.events.subscribe(() => {
      this.isHomeRoute = this.router.url === '/home';
    });
    // Set initial value
    this.isHomeRoute = this.router.url === '/home';
  }

RegisterEmployee()
{
  this.showLogin = false; // Show registration form
  // Basic validation
if(!this.employee.employeeName  || !this.employee.employeeDOB || !this.employee.employeeProject || !this.employee.password) {

    this.responseOutDto.errorflag = true;
    this.responseOutDto.messageString = 'All fields are required';
    return;
  }

  this.indexService.registerEmployee(this.employee).subscribe((data:any)=>{
    this.responseOutDto = data;
    console.log(this.responseOutDto);
    if (this.responseOutDto.errorflag) {
      console.error('Error:', this.responseOutDto.messageString);
    } else {
      // Registration successful: bind employeeId to employee.employeeId and disable Register button
      if (this.responseOutDto.employee && this.responseOutDto.employee.employeeId) {
        this.employee.employeeId = this.responseOutDto.employee.employeeId;
      }
      this.registerDisabled = true;
      console.log('Employee registered successfully:', this.responseOutDto.employee);
    }
  },error=>{
    this.responseOutDto=error.error;
  });

}
reset()
{
  this.employee = {
    employeeName: '',
    employeeId: 0,
    employeeDOB: '',
    employeeProject: '',
    password: ''
  };
  this.responseOutDto = new ResponseOutDTO();
}
  login() {
    this.registerDisabled = false; // Enable Register button on login

    if (!this.employee.employeeId || !this.employee.password) {
      this.responseOutDto.errorflag = true;
      this.responseOutDto.messageString = 'Please enter both Employee Id and Password';
      return;
    }
    // Call backend login API
    this.indexService.login(this.employee).subscribe(
      (data: any) => {
        this.responseOutDto = data;
        console.log(this.responseOutDto);
        this.messageSuccessString=this.responseOutDto.messageString
        console.log(this.messageSuccessString);
        if (this.responseOutDto.errorflag) {
          // Show backend error message (e.g., wrong password)
        } else {
          this.router.navigate(['home'], { state: { employee: this.employee } });
          // Login successful, handle as needed
        }

      },
      (error: any) => {
        this.responseOutDto = error.error;
      }
    );
  }
}
