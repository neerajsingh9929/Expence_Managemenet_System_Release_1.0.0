// ...existing code...
import { Component, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import { HomeService } from './home.service';
import { Router } from '@angular/router';
import { BudgetOutDTO } from '../DTO/BudgetOutDTO';
import { Chart, ChartConfiguration, ChartData, ChartType, registerables } from 'chart.js';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements AfterViewInit {
  @ViewChild('pieChart') pieChart!: ElementRef<HTMLCanvasElement>;
  private chart: Chart | null = null;
  Menus: any[] = [];
  employee: any;
  showProfile = false;
  showMenuDrawer = false;
  currentYear = new Date().getFullYear();
  budget = {
    id: null,
    month: null,
    amount: null,
    budgetLimit: null,
    category: '',
    userId: '',
    created_at: new Date(),
    updated_at: new Date()
  };
  budgetList: any[] = [];
  selectedBudgetId: number | null = null;
  
  // Pagination properties
  currentPage: number = 0;
  pageSize: number = 6;
  totalPages: number = 0;
  paginatedBudgetList: any[] = [];
  
  // Display control properties
  showBudgetTable: boolean = false;
  showChart: boolean = false;
  
  // Month filter properties
  selectedMonth: string = '';
  selectedCategory: string = '';
  monthOptions = [
    { value: '1', name: 'January' },
    { value: '2', name: 'February' },
    { value: '3', name: 'March' },
    { value: '4', name: 'April' },
    { value: '5', name: 'May' },
    { value: '6', name: 'June' },
    { value: '7', name: 'July' },
    { value: '8', name: 'August' },
    { value: '9', name: 'September' },
    { value: '10', name: 'October' },
    { value: '11', name: 'November' },
    { value: '12', name: 'December' }
  ];


  viewBudgets() {
    // Validate employee and employeeId
    if (!this.employee || !this.employee.employeeId) {
      console.error('Employee or employeeId is missing:', this.employee);
      this.errorMessage = 'User not logged in properly. Please login again.';
      this.errorMessageFlag = true;
      return;
    }

    this.isLoading = true;
    this.showBudgetTable = true;  // Show the table section
    this.showChart = true;        // Show the chart section
    console.log('Sending userId:', this.employee.employeeId); // Debug log
    this.homeService.viewBudget(this.employee.employeeId.toString()).subscribe({
      next: (data) => {
        console.log('Received data:', data); // Debug log
        this.budgetList = data.budgetList || [];
        this.currentPage = 0;
        this.updatePagination();
        this.isLoading = false;
        
        // Reset selection if no data found
        if (this.budgetList.length === 0) {
          this.selectedBudgetId = null;
        }
        
        // Load chart data after a short delay to ensure DOM is ready
        setTimeout(() => {
          this.loadChartData();
        }, 200);
      },
      error: (error) => {
        console.error('Error fetching budgets:', error); // Debug log
        this.budgetList = [];
        this.paginatedBudgetList = [];
        this.totalPages = 0;
        this.isLoading = false;
      }
    });
  }

  // Update pagination data
  updatePagination() {
    this.totalPages = Math.ceil(this.budgetList.length / this.pageSize);
    const startIndex = this.currentPage * this.pageSize;
    const endIndex = startIndex + this.pageSize;
    this.paginatedBudgetList = this.budgetList.slice(startIndex, endIndex);
  }

  // Navigate to next page
  nextPage() {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      this.updatePagination();
      this.selectedBudgetId = null; // Clear selection on page change
    }
  }

  // Navigate to previous page
  previousPage() {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.updatePagination();
      this.selectedBudgetId = null; // Clear selection on page change
    }
  }

  // Check if there's a next page
  hasNextPage(): boolean {
    return this.currentPage < this.totalPages - 1;
  }

  // Check if there's a previous page
  hasPreviousPage(): boolean {
    return this.currentPage > 0;
  }

  // Get current page info
  getCurrentPageInfo(): string {
    if (this.budgetList.length === 0) return 'No records';
    const startItem = (this.currentPage * this.pageSize) + 1;
    const endItem = Math.min((this.currentPage + 1) * this.pageSize, this.budgetList.length);
    return `${startItem}-${endItem} of ${this.budgetList.length}`;
  }
  months = [
    { value: '01', label: 'Jan' },
    { value: '02', label: 'Feb' },
    { value: '03', label: 'Mar' },
    { value: '04', label: 'Apr' },
    { value: '05', label: 'May' },
    { value: '06', label: 'Jun' },
    { value: '07', label: 'Jul' },
    { value: '08', label: 'Aug' },
    { value: '09', label: 'Sep' },
    { value: '10', label: 'Oct' },
    { value: '11', label: 'Nov' },
    { value: '12', label: 'Dec' }
  ];
  categories: string[] = [
    'Food', 'Transport', 'Utilities', 'Entertainment', 'Health',
    'Education', 'Shopping', 'Travel', 'Bills', 'Other'
  ];
  budgetOutDto = new BudgetOutDTO();
  addbudgetFlag = false;
  successMessage: string = '';
  errorMessage: string = '';
  successMessageFlag = false;
  errorMessageFlag = false;
  warningMessageFlag = false;
  isLoading: boolean = false;
  constructor(private homeService: HomeService, private router: Router) {
    Chart.register(...registerables);
  }

  ngAfterViewInit() {
    // Initialize chart after view is ready
    console.log('View initialized, canvas element:', this.pieChart);
    
    // Test chart creation with sample data if no budget data exists
    if (this.budgetList.length === 0) {
      setTimeout(() => {
        this.createTestChart();
      }, 500);
    }
  }

  private createTestChart() {
    console.log('Creating test chart with INR values...');
    this.createChart(['Food', 'Transport', 'Entertainment'], [1500, 800, 1200]);
  }

  private generateColors(count: number): string[] {
    const baseColors = [
      '#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0', 
      '#9966FF', '#FF9F40', '#FF6F61', '#C9CBCF',
      '#4CAF50', '#FFC107', '#E91E63', '#00BCD4',
      '#8BC34A', '#FF5722', '#9C27B0', '#607D8B',
      '#795548', '#F44336', '#2196F3', '#CDDC39'
    ];
    
    if (count <= baseColors.length) {
      return baseColors.slice(0, count);
    }
    
    // Generate additional colors if needed
    const colors = [...baseColors];
    for (let i = baseColors.length; i < count; i++) {
      const hue = (i * 137.508) % 360; // Golden angle approximation
      colors.push(`hsl(${hue}, 70%, 60%)`);
    }
    
    return colors;
  }


  ngOnInit() {
    this.employee = history.state.employee;
    console.log('Employee received in HomeComponent:', this.employee);
    
    // Check if employee data is available
    if (!this.employee || !this.employee.employeeId) {
      console.error('No employee data found in navigation state');
      // Try to get from localStorage or redirect to login
      const savedEmployee = localStorage.getItem('employee');
      if (savedEmployee) {
        this.employee = JSON.parse(savedEmployee);
        console.log('Employee loaded from localStorage:', this.employee);
      } else {
        this.errorMessage = 'Session expired. Please login again.';
        this.errorMessageFlag = true;
        return;
      }
    }
    
    this.homeService.getMenus().subscribe((data: any) => {
      this.Menus = data;
      if (this.employee && this.employee.employeeId) {
        this.budget.userId = this.employee.employeeId;
      }
      console.log(this.Menus);
    }, error => {
      console.error('Error fetching menus', error);
    });
  }

  toggleProfile() {
    this.showProfile = !this.showProfile;
  }

  toggleMenuDrawer() {
    this.showMenuDrawer = !this.showMenuDrawer;
  }

  addBudget() {
    this.successMessage = '';
    this.errorMessage = '';
    this.successMessageFlag = false;
    this.errorMessageFlag = false;
    this.warningMessageFlag = false;
    this.isLoading = true;
    if (!this.budget.month || !this.budget.amount || !this.budget.category) {
      this.errorMessage = 'Please fill in required fields (Month, Amount, Category).';
      this.errorMessageFlag = true;
      this.isLoading = false;
      return;
    }
    
    // Show warning if amount exceeds budget limit but continue with saving
    let warningMessage = '';
    if (this.budget.budgetLimit && this.budget.amount > this.budget.budgetLimit) {
      warningMessage = ` Warning: Amount exceeds budget limit by ₹${(this.budget.amount - this.budget.budgetLimit).toFixed(2)}!`;
    }
    
    this.homeService.addBudget(this.budget).subscribe({
      next: (data: any) => {
        this.isLoading = false;
        this.budgetOutDto = data;
        this.budget = data.buBudget;
        if (data && data.buBudget && !data.errorflag) {
          const monthLabel = this.months.find(m => Number(m.value) === Number(data.buBudget.month))?.label || data.buBudget.month;
          
          if (warningMessage) {
            this.warningMessageFlag = true;
            this.successMessage = `Amount ₹${data.buBudget.amount} added successfully for ${monthLabel} in ${data.buBudget.category}${warningMessage}`;
          } else {
            this.successMessageFlag = true;
            this.successMessage = `Amount ₹${data.buBudget.amount} added successfully for ${monthLabel} in ${data.buBudget.category}`;
          }

        } else if (data && data.errorflag) {
          this.errorMessageFlag = true;
          this.errorMessage = data.messageString || 'Error adding budget.';
        }
      },
      error: (error) => {
        this.isLoading = false;
        this.errorMessage = 'Error adding budget. Please try again.';
        this.errorMessageFlag = true;
        console.error('Error adding budget', error);
      }

    });

  }

  // Method to handle radio button selection change
  onBudgetSelection(selectedId: number) {
    console.log('Selected budget ID:', selectedId);
    // You can add additional logic here when a budget is selected
    // For example, you might want to enable/disable buttons or show additional details
  }

  // Check if any budget is selected and data is available
  isAnyBudgetSelected(): boolean {
    return this.selectedBudgetId !== null && this.budgetList.length > 0;
  }

  // Delete selected budget
  deleteBudget() {
    if (this.selectedBudgetId === null) return;
    
    if (confirm('Are you sure you want to delete this budget?')) {
      this.isLoading = true;
      // Call your delete service method here
      this.homeService.deleteBudget(this.selectedBudgetId).subscribe({
        next: (response: any) => {
          this.isLoading = false;
          this.successMessage = 'Budget deleted successfully';
          this.successMessageFlag = true;
          this.selectedBudgetId = null;
          this.selectedMonth = ''; // Reset filter to "All months"
          this.selectedCategory = ''; // Reset category filter
          this.viewBudgets(); // Refresh the list
          // Refresh chart after a short delay
          setTimeout(() => {
            this.loadChartData();
          }, 200);
        },
        error: (error: any) => {
          this.isLoading = false;
          this.errorMessage = 'Error deleting budget';
          this.errorMessageFlag = true;
        }
      });
    }
  }

  // Update selected budget
  updateBudget() {
    if (this.selectedBudgetId === null) return;
    
    const selectedBudget = this.budgetList.find(budget => budget.id === this.selectedBudgetId);
    if (selectedBudget) {
      this.budget = {
        ...selectedBudget,
        created_at: new Date(selectedBudget.created_at),
        updated_at: new Date()
      };
      
      this.successMessage = 'Budget loaded for editing. Make changes and click "Add Budget" to update.';
      this.successMessageFlag = true;
      
      console.log('Budget loaded for editing:', selectedBudget);
      
      document.querySelector('.expense-form-card')?.scrollIntoView({ behavior: 'smooth' });
    }
  }

  // Close/Hide the budget table and chart
  closeBudgetTable() {
    this.budgetList = [];
    this.paginatedBudgetList = [];
    this.selectedBudgetId = null;
    this.currentPage = 0;
    this.totalPages = 0;
    this.selectedMonth = '';
    this.selectedCategory = '';
    this.showBudgetTable = false;  // Hide the table section
    this.showChart = false;        // Hide the chart section
    
    // Destroy chart if it exists
    if (this.chart) {
      this.chart.destroy();
      this.chart = null;
    }
    
    console.log('Budget table and chart closed');
  }
  
  // Get month name from month number
  getMonthName(monthNumber: number): string {
    const monthObj = this.monthOptions.find(month => month.value === monthNumber?.toString());
    return monthObj ? monthObj.name : monthNumber?.toString() || '';
  }
  
  // Check if budget limit is exceeded (only if budget limit is set)
  isBudgetExceeded(budget: any): boolean {
    return budget.budgetLimit && budget.amount && budget.amount > budget.budgetLimit;
  }
  
  // Get all budget warnings
  getBudgetWarnings(): any[] {
    return this.budgetList.filter(budget => this.isBudgetExceeded(budget));
  }
  
  // Get total budget warnings count
  getBudgetWarningsCount(): number {
    return this.getBudgetWarnings().length;
  }
  
  // Export budget data to CSV using backend service
  exportToCSV() {
    if (this.budgetList.length === 0) {
      this.errorMessage = 'No data available to export.';
      this.errorMessageFlag = true;
      return;
    }
    
    // Clear any previous messages
    this.successMessageFlag = false;
    this.errorMessageFlag = false;
    this.warningMessageFlag = false;
    
    console.log('Exporting budget data:', this.budgetList.length, 'records');
    console.log('Export filters - Month:', this.selectedMonth, 'Category:', this.selectedCategory);
    
    // Call backend service for CSV export
    this.homeService.exportBudgetCSV(this.employee.employeeId.toString(), this.selectedMonth, this.selectedCategory)
      .subscribe({
        next: (blob: Blob) => {
          console.log('CSV export successful, downloading file...');
          
          // Create download link
          const url = window.URL.createObjectURL(blob);
          const link = document.createElement('a');
          link.href = url;
          
          // Generate filename
          const filename = this.generateCSVFilename();
          link.download = filename;
          
          // Trigger download
          document.body.appendChild(link);
          link.click();
          document.body.removeChild(link);
          window.URL.revokeObjectURL(url);
          
          console.log('Export completed:', filename);
          
          this.successMessage = `Budget data exported successfully! ${this.budgetList.length} records saved to ${filename}`;
          this.successMessageFlag = true;
          
          // Auto-hide success message after 5 seconds
          setTimeout(() => {
            this.successMessageFlag = false;
          }, 5000);
        },
        error: (error) => {
          console.error('CSV export failed:', error);
          this.errorMessage = 'Failed to export budget data. Please try again.';
          this.errorMessageFlag = true;
        }
      });
  }
  

  
  // Generate filename with current date and filters
  private generateCSVFilename(): string {
    const currentDate = new Date();
    const dateStr = currentDate.toISOString().split('T')[0]; // YYYY-MM-DD format
    
    let filename = `budget-export-${dateStr}`;
    
    // Add filter info to filename
    if (this.selectedMonth) {
      const monthName = this.monthOptions.find(m => m.value === this.selectedMonth)?.name || this.selectedMonth;
      filename += `-${monthName}`;
    }
    
    if (this.selectedCategory) {
      filename += `-${this.selectedCategory}`;
    }
    
    return `${filename}.csv`;
  }
  

  
  // Handle month filter change
  onMonthFilterChange(selectedMonth: string) {
    console.log('Month filter changed to:', selectedMonth);
    this.selectedMonth = selectedMonth;
    this.applyFilters();
  }

  // Handle category filter change
  onCategoryFilterChange(selectedCategory: string) {
    console.log('Category filter changed to:', selectedCategory);
    this.selectedCategory = selectedCategory;
    this.applyFilters();
  }

  // Apply both month and category filters
  applyFilters() {
    if (this.selectedMonth || this.selectedCategory) {
      this.viewBudgetsByFilters();
    } else {
      this.viewBudgets();
    }
  }
  
  // Refresh data
  refreshData() {
    console.log('=== REFRESH CLICKED ===');
    console.log('Current month filter:', this.selectedMonth);
    console.log('Current budget list length:', this.budgetList.length);
    
    // Clear all current state
    this.selectedBudgetId = null;
    this.budgetList = [];
    this.paginatedBudgetList = [];
    this.currentPage = 0;
    this.totalPages = 0;
    this.isLoading = true;
    
    // Clear any previous messages
    this.successMessageFlag = false;
    this.errorMessageFlag = false;
    
    // Force a complete refresh based on current filters
    setTimeout(() => {
      if (this.selectedMonth || this.selectedCategory) {
        console.log('Refreshing with filters - Month:', this.selectedMonth, 'Category:', this.selectedCategory);
        this.viewBudgetsByFilters();
      } else {
        console.log('Refreshing all budgets');
        this.viewBudgets();
      }
    }, 100);
    
    console.log('=== REFRESH INITIATED ===');
  }
  
  // View budgets filtered by both month and category
  viewBudgetsByFilters() {
    if (!this.employee?.employeeId) {
      console.error('No employee ID found');
      return;
    }
    
    this.isLoading = true;
    this.showBudgetTable = true;
    this.showChart = true;
    console.log('Fetching budgets with filters - Month:', this.selectedMonth, 'Category:', this.selectedCategory);
    
    // If no filters are applied, get all budgets
    if (!this.selectedMonth && !this.selectedCategory) {
      this.viewBudgets();
      return;
    }
    
    this.homeService.getBudgetsByFilters(this.employee.employeeId, this.selectedMonth, this.selectedCategory).subscribe({
      next: (data) => {
        console.log('Received filtered data:', data);
        this.budgetList = data.budgetList || [];
        this.currentPage = 0;
        this.updatePagination();
        this.isLoading = false;
        
        setTimeout(() => {
          this.loadChartData();
        }, 200);
      },
      error: (error) => {
        console.error('Error fetching filtered budgets:', error);
        this.budgetList = [];
        this.paginatedBudgetList = [];
        this.totalPages = 0;
        this.selectedBudgetId = null;
        this.isLoading = false;
      }
    });
  }

  // View budgets filtered by month
  viewBudgetsByMonth() {
    if (!this.employee?.employeeId) {
      console.error('No employee ID found');
      return;
    }
    
    this.isLoading = true;
    this.showBudgetTable = true;  // Ensure table section is visible
    this.showChart = true;        // Ensure chart section is visible
    console.log('Fetching budgets for month:', this.selectedMonth);
    
    if (!this.selectedMonth) {
      // If no month selected, get all budgets
      this.viewBudgets();
      return;
    }
    
    this.homeService.getBudgetsByMonth(this.employee.employeeId, this.selectedMonth).subscribe({
      next: (data) => {
        console.log('Received month-filtered data:', data);
        this.budgetList = data.budgetList || [];
        this.currentPage = 0;
        this.updatePagination();
        this.isLoading = false;
        
        // Load chart data after a short delay to ensure DOM is ready
        setTimeout(() => {
          this.loadChartData();
        }, 200);
      },
      error: (error) => {
        console.error('Error fetching month-filtered budgets:', error);
        this.budgetList = [];
        this.paginatedBudgetList = [];
        this.totalPages = 0;
        this.selectedBudgetId = null;  // Reset selection when no data
        this.isLoading = false;
      }
    });
  }

  // Load chart data
  loadChartData() {
    if (!this.employee?.employeeId) return;
    
    this.homeService.getBudgetChartData(this.employee.employeeId).subscribe({
      next: (data) => {
        console.log('Chart data received:', data);
        
        // Handle both budget and budgetList properties
        const budgetData = data.budget || data.budgetList;
        
        if (budgetData && Array.isArray(budgetData)) {
          // Group by category and sum amounts
          const categoryTotals: { [key: string]: number } = {};
          
          console.log('Raw budget data from API:', budgetData);
          
          budgetData.forEach((budget: any) => {
            // Handle different possible property names
            const category = budget.category || budget.Category || 'Uncategorized';
            const amount = parseFloat(budget.amount || budget.Amount || '0') || 0;
            
            console.log(`Processing budget: ${category} - ₹${amount}`);
            
            if (amount > 0) { // Only include positive amounts
              if (categoryTotals[category]) {
                categoryTotals[category] += amount;
              } else {
                categoryTotals[category] = amount;
              }
            }
          });
          
          console.log('Final category totals:', categoryTotals);
          console.log('Number of categories:', Object.keys(categoryTotals).length);
          
          // Create or update the chart
          const categories = Object.keys(categoryTotals);
          const amounts = Object.values(categoryTotals);
          
          console.log('Categories for chart:', categories);
          console.log('Amounts for chart:', amounts);
          
          this.createChart(categories, amounts);
        }
      },
      error: (error) => {
        console.error('Error loading chart data:', error);
      }
    });
  }

  private createChart(labels: string[], data: number[]) {
    console.log('Creating chart with labels:', labels, 'data:', data);
    
    if (!this.pieChart) {
      console.error('Canvas element not found');
      return;
    }

    if (this.chart) {
      this.chart.destroy();
    }

    // Use setTimeout to ensure DOM is fully rendered
    setTimeout(() => {
      const ctx = this.pieChart.nativeElement.getContext('2d');
      if (ctx) {
        console.log('Canvas context found, creating chart...');
        // Generate more colors for more categories
        const colors = this.generateColors(labels.length);
        
        this.chart = new Chart(ctx, {
          type: 'pie',
          data: {
            labels: labels,
            datasets: [{
              data: data,
              backgroundColor: colors,
              borderWidth: 2,
              borderColor: '#fff',
              hoverBorderWidth: 3,
              hoverBorderColor: '#333'
            }]
          },
          options: {
            responsive: true,
            maintainAspectRatio: false,
            layout: {
              padding: {
                top: 10,
                bottom: 10,
                left: 10,
                right: 10
              }
            },
            plugins: {
              legend: {
                display: true,
                position: 'right',
                align: 'center',
                maxWidth: 200,
                labels: {
                  usePointStyle: true,
                  padding: 15,
                  font: {
                    size: 12
                  }
                }
              },
              tooltip: {
                callbacks: {
                  label: function(context) {
                    const label = context.label || '';
                    const value = context.parsed || 0;
                    const dataset = context.dataset.data as number[];
                    const total = dataset.reduce((a, b) => a + b, 0);
                    const percentage = ((value / total) * 100).toFixed(1);
                    return `${label}: ₹${value.toFixed(2)} (${percentage}%)`;
                  }
                }
              }
            },
            elements: {
              arc: {
                borderWidth: 2
              }
            }
          }
        });
        console.log('Chart created successfully:', this.chart);
      } else {
        console.error('Could not get canvas context');
      }
    }, 100);
  }


}
