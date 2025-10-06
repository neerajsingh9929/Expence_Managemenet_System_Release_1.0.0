# Expence_Managemenet_System_Release_1.0.0
Here User can track their monthly expenses.
# Personal Expense Tracker Application

A full-stack web application for managing personal budgets and expenses with advanced filtering, warnings, and export capabilities.

## 📋 Table of Contents

- [Features](#features)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Setup Instructions](#setup-instructions)
- [API Endpoints](#api-endpoints)
- [Usage Guide](#usage-guide)
- [Recent Updates](#recent-updates)
- [Troubleshooting](#troubleshooting)
- [Contributing](#contributing)

## ✨ Features

### Core Budget Management
- **Add Budget Entries**: Create budget entries with category, amount, and month
- **View All Budgets**: Display all budget entries in a paginated table format
- **Edit & Delete**: Modify or remove existing budget entries
- **Interactive Dashboard**: Visual representation with charts and statistics

### Advanced Filtering System
- **Category Filter**: Filter budgets by specific categories (Food, Transportation, Entertainment, etc.)
- **Month Filter**: View budgets for specific months
- **Combined Filters**: Apply both category and month filters simultaneously
- **Real-time Updates**: Instant filtering without page refresh

### Budget Limit Management & Warnings
- **Optional Budget Limits**: Set spending limits for categories (optional field)
- **Smart Warnings**: Visual alerts when spending exceeds budget limits
- **Status Indicators**: Clear status display (Within Limit, Over Budget, No Limit Set)
- **Warning-Only Approach**: Users can still add budgets even when exceeding limits

### CSV Export Functionality
- **Backend Processing**: Server-side CSV generation for better performance
- **Filtered Exports**: Export data based on current filters (category, month)
- **Comprehensive Data**: Includes all fields plus status and timestamps
- **Smart Naming**: Auto-generated filenames with filter info and timestamps
- **Multiple Export Options**: Export buttons available in various locations

### User Interface
- **Responsive Design**: Works on desktop and mobile devices
- **Material Design**: Clean, modern UI using Angular Material
- **Loading States**: Visual feedback during data operations
- **Error Handling**: Comprehensive error messages and validation
- **Success Notifications**: Confirmation messages for all actions

## 🛠 Technology Stack

### Frontend
- **Framework**: Angular 15.2.0
- **UI Library**: Angular Material
- **Charts**: Chart.js for data visualization
- **HTTP Client**: Angular HttpClient
- **Routing**: Angular Router
- **Styling**: CSS3 with Material Design principles

### Backend
- **Framework**: Spring Boot
- **Language**: Java
- **Database**: MySQL (configurable)
- **ORM**: JPA/Hibernate
- **API**: RESTful Web Services
- **Security**: CORS enabled
- **Build Tool**: Maven

### Database Schema
```sql
-- Budget table with enhanced structure
CREATE TABLE budget (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL,
    category VARCHAR(255) NOT NULL,
    amount DOUBLE NOT NULL,
    budget_limit DOUBLE NULL,  -- Optional spending limit
    month VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

## 📁 Project Structure

```
firststsproject/
├── src/main/java/com/sts/
│   ├── controller/
│   │   └── ExpenceTrackerController.java    # REST API endpoints
│   ├── service/
│   │   └── ExpenceTrackerService.java       # Business logic & CSV generation
│   ├── entity/
│   │   ├── Budget.java                      # Budget entity with limits
│   │   └── BudgetOutDto.java               # Response DTO
│   ├── dao/
│   │   └── BudgetRepository.java           # Data access with custom queries
│   └── FirststsprojectApplication.java     # Main Spring Boot class
├── Angular/
│   ├── src/app/
│   │   ├── home/
│   │   │   ├── home.component.ts           # Main component with all features
│   │   │   ├── home.component.html         # UI template with filters & export
│   │   │   ├── home.component.css          # Styling with warning styles
│   │   │   └── home.service.ts             # HTTP service with backend calls
│   │   └── DTO/
│   │       ├── BudgetOutDTO.ts             # TypeScript DTOs
│   │       └── ResponseOutDTO.ts
│   ├── proxy.conf.json                     # API proxy configuration
│   ├── package.json                        # Dependencies & scripts
│   └── angular.json                        # Angular configuration
├── pom.xml                                 # Maven dependencies
└── README.md                               # This file
```

## 🚀 Setup Instructions

### Prerequisites
- **Java**: JDK 11 or higher
- **Node.js**: Version 14 or higher
- **npm**: Version 6 or higher
- **MySQL**: Version 8.0 or higher
- **Maven**: Version 3.6 or higher

### Backend Setup

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd firststsproject
   ```

2. **Configure Database**
   - Update `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/expense_tracker
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   ```

3. **Build and Run**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
   - Backend server starts on: `http://localhost:9001`

### Frontend Setup

1. **Navigate to Angular directory**
   ```bash
   cd Angular
   ```

2. **Install dependencies**
   ```bash
   npm install
   ```

3. **Start development server**
   ```bash
   ng serve --proxy-config proxy.conf.json --port 4200
   ```
   - Frontend application available at: `http://localhost:4200`

### Database Setup
```sql
-- Create database
CREATE DATABASE expense_tracker;
USE expense_tracker;

-- The Budget table will be auto-created by JPA/Hibernate
-- with the enhanced schema including budget_limit field
```

## 🔗 API Endpoints

### Budget Management
| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| POST | `/addBudget` | Add new budget entry | Budget object |
| POST | `/viewBudget` | Get all budgets for user | User ID (string) |
| DELETE | `/deleteBudget/{id}` | Delete budget by ID | - |
| POST | `/getBudgetsByMonth` | Filter by month | {userId, month} |
| POST | `/getBudgetsByFilters` | Advanced filtering | {userId, month?, category?} |
| POST | `/exportBudgetCSV` | Export filtered data as CSV | {userId, month?, category?} |

### Additional Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/getMenus` | Get category menu options |
| POST | `/getBudgetChart` | Get chart data for visualization |

### Request/Response Examples

**Add Budget Request:**
```json
{
  "userId": "user123",
  "category": "Food",
  "amount": 500.00,
  "budgetLimit": 800.00,
  "month": "2025-10"
}
```

**Filter Request:**
```json
{
  "userId": "user123",
  "month": "2025-10",
  "category": "Food"
}
```

**CSV Export Response:**
- Content-Type: `application/octet-stream`
- Content-Disposition: `attachment; filename="budget_export_October_Food_20251006_120000.csv"`

## 📖 Usage Guide

### 1. Adding Budget Entries
- Fill in the budget form with category, amount, and month
- Optionally set a budget limit for spending warnings
- Click "Add Budget" to save

### 2. Viewing and Filtering Budgets
- Use the **Month Filter** dropdown to filter by specific months
- Use the **Category Filter** dropdown to filter by categories
- Combine both filters for precise results
- Click **Refresh** to reload data

### 3. Budget Warnings
- Budget entries with limits will show warning indicators when exceeded
- Warning styles: Red background and warning icon (⚠️)
- Status column shows: "Within Limit", "Over Budget", or "No Limit Set"

### 4. CSV Export
- Click **Export CSV** buttons (available in multiple locations)
- Files are generated server-side and downloaded automatically
- Filename includes current filters and timestamp
- CSV contains all data plus calculated status and export timestamp

### 5. Data Management
- Edit budgets by selecting and modifying entries
- Delete unwanted entries using the delete button
- View visual charts and statistics in the dashboard

## 🆕 Recent Updates

### Version 2.3.0 - Backend CSV Export Implementation
- **Backend Processing**: Moved CSV generation from frontend to backend
- **Enhanced Performance**: Server-side processing for large datasets
- **Better Error Handling**: Comprehensive backend error management
- **Improved File Naming**: Intelligent filename generation with filters

### Version 2.2.0 - Budget Warnings System
- **Optional Budget Limits**: Added budgetLimit field to Budget entity
- **Smart Warnings**: Visual alerts without blocking functionality
- **Enhanced UI**: Warning styles and status indicators
- **Flexible Validation**: Users can exceed limits (warning-only approach)

### Version 2.1.0 - Advanced Filtering
- **Category Filtering**: Filter budgets by category
- **Combined Filters**: Month + Category filtering
- **Backend Optimization**: Custom repository queries for filtering
- **Real-time Updates**: Instant filtering without page refresh

## 🛠 Troubleshooting

### Common Issues

1. **"Cannot POST /exportBudgetCSV" Error**
   - **Cause**: Spring Boot server not running
   - **Solution**: Start backend server with `mvn spring-boot:run`
   - **Verify**: Check `http://localhost:9001` is accessible

2. **"Http failure response for /viewBudget: 400 Bad Request"**
   - **Cause**: Invalid or missing user ID
   - **Solution**: Ensure user is properly logged in
   - **Check**: Verify employee object in browser console

3. **Proxy Connection Refused Errors**
   - **Cause**: Backend server not running on port 9001
   - **Solution**: Start Spring Boot application first
   - **Verify**: Run `netstat -an | findstr :9001`

4. **CSV Export Not Working**
   - **Check**: Backend server is running
   - **Verify**: Proxy configuration includes `/exportBudgetCSV`
   - **Debug**: Check browser network tab for request details

5. **Budget Warnings Not Showing**
   - **Check**: Budget limit is set for the category
   - **Verify**: Amount exceeds the budget limit
   - **Debug**: Check browser console for budget data

### Development Tips

1. **Backend Development**
   ```bash
   # Run with debug mode
   mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"
   ```

2. **Frontend Development**
   ```bash
   # Build for production
   ng build --prod
   
   # Run with specific environment
   ng serve --configuration=development
   ```

3. **Database Debugging**
   ```sql
   -- Check budget data
   SELECT * FROM budget WHERE user_id = 'your_user_id';
   
   -- Check budgets with limits
   SELECT * FROM budget WHERE budget_limit IS NOT NULL;
   ```

## 🤝 Contributing

### Development Workflow
1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Code Standards
- **Backend**: Follow Java conventions, use proper annotations
- **Frontend**: Follow Angular style guide, use TypeScript strictly
- **Database**: Use proper naming conventions, add appropriate indexes
- **Documentation**: Update README.md for new features

### Testing
- **Unit Tests**: Write tests for service methods
- **Integration Tests**: Test API endpoints
- **E2E Tests**: Test complete user workflows
- **Manual Testing**: Verify UI/UX functionality

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 📞 Support

For support and questions:
- Create an issue in the repository
- Check the troubleshooting section above
- Review the API documentation

---

**Last Updated**: October 2025  
**Version**: 2.3.0  
**Maintainer**: Development Team
