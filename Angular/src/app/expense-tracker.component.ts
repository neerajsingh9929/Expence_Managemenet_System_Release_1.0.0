import { Component } from '@angular/core';

@Component({
  selector: 'app-expense-tracker',
  templateUrl: './expense-tracker.component.html',
  styleUrls: ['./expense-tracker.component.css']
})
export class ExpenseTrackerComponent {
  budget = {
    id: null,
    month: null,
    amount: null,
    category: '',
    userId: '',
    created_at: new Date(),
    updated_at: new Date()
  };

  categories: string[] = [
    'Food', 'Transport', 'Utilities', 'Entertainment', 'Health',
    'Education', 'Shopping', 'Travel', 'Bills', 'Other'
  ];

  addBudget() {
    // TODO: Implement API call to add budget
    this.budget.created_at = new Date();
    this.budget.updated_at = new Date();
    alert('Budget added! (Demo)');
  }
}
