package com.sts.service;

import java.io.Console;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sts.dao.BooksRepo;
import com.sts.dao.BudgetRepository;
import com.sts.entity.Book;
import com.sts.entity.Budget;
import com.sts.entity.BudgetOutDto;


import jakarta.validation.Valid;
@Service
public class ExpenceTrackerService {
	
	private final BudgetRepository budgetRepository;
		
		
		
	ExpenceTrackerService(BudgetRepository budgetRepository) {
	        this.budgetRepository = budgetRepository;
	    }
	 String messageString = null;
	    boolean errorFlag = false;

	    

	


	

	public BudgetOutDto addBudget(Budget budget) {
		
		BudgetOutDto budgetOut= new BudgetOutDto();
        try {
        	System.out.println(budget+"hi");
             Budget e = this.budgetRepository.save(budget);
            System.out.println(e);

            if (e == null || e.toString().isEmpty()) {
                errorFlag = true;
                messageString = "Something has gone wrong. Please contact Admin or helpservices@metafolic.com";
            } else {
                errorFlag = false;
                messageString = "Successfully Added Budget";
            }
            budgetOut.setBudget(budget);
            budgetOut.setMessageString(messageString);
            budgetOut.setErrorflag(errorFlag);

        } catch (Exception exception) {
        	System.out.println(exception);
            exception.printStackTrace();
        }
        return budgetOut;	}








	public BudgetOutDto viewBudget(String userId) {
		BudgetOutDto budgetOut= new BudgetOutDto();
      List<Budget> budgetList=new ArrayList<>();
      budgetList=(List<Budget>) budgetRepository.findAllByUserId(userId);
      if (budgetList == null || budgetList.toString().isEmpty()) {
          errorFlag = true;
          messageString = "Something has gone wrong. Please contact Admin or helpservices@metafolic.com";
      } else {
          budgetList = addBudgetWarnings(budgetList);
          errorFlag = false;
          messageString = "Successfully fetched Budget list";
      }
      budgetOut.setBudgetList(budgetList);
      budgetOut.setMessageString(messageString);
      budgetOut.setErrorflag(errorFlag);
      
		return budgetOut;
		
		
		
	}

public BudgetOutDto deleteBudget(int budId) {
		

BudgetOutDto budgetOutDto=new BudgetOutDto();
try {
	budgetRepository.deleteById(budId);
	budgetOutDto.setMessageString("Budget deleted successfully");
} catch (Exception e) {
budgetOutDto.setErrorflag(true);
budgetOutDto.setMessageString("Error while deleting");
}
	return budgetOutDto;

	}

public BudgetOutDto getBudgetChartData(String userId) {
	BudgetOutDto budgetOut = new BudgetOutDto();
	try {
		System.out.println("Getting chart data for userId: " + userId);
		List<Budget> budgets = budgetRepository.findAllByUserId(userId);
		System.out.println("Found " + budgets.size() + " budget records for chart");
		
		for (Budget budget : budgets) {
			System.out.println("Budget - Category: " + budget.getCategory() + 
							 ", Amount: " + budget.getAmount() + 
							 ", Month: " + budget.getMonth());
		}
		
		budgetOut.setBudgetList(budgets);
		budgetOut.setErrorflag(false);
		budgetOut.setMessageString("Budget chart data retrieved successfully");
	} catch (Exception e) {
		System.out.println("Error in getBudgetChartData: " + e.getMessage());
		e.printStackTrace();
		budgetOut.setErrorflag(true);
		budgetOut.setMessageString("Error retrieving chart data: " + e.getMessage());
	}
	return budgetOut;
}

public BudgetOutDto getBudgetsByMonth(String userId, Integer month) {
	BudgetOutDto budgetOut = new BudgetOutDto();
	try {
		System.out.println("Getting budgets for userId: " + userId + ", month: " + month);
		List<Budget> budgets = budgetRepository.findAllByUserIdAndMonth(userId, month);
		System.out.println("Found " + budgets.size() + " budget records for month " + month);
		
		for (Budget budget : budgets) {
			System.out.println("Budget - Category: " + budget.getCategory() + 
							 ", Amount: " + budget.getAmount() + 
							 ", Month: " + budget.getMonth());
		}
		
		budgets = addBudgetWarnings(budgets);
		
		budgetOut.setBudgetList(budgets);
		budgetOut.setErrorflag(false);
		budgetOut.setMessageString("Budget data for month " + month + " retrieved successfully");
	} catch (Exception e) {
		System.out.println("Error in getBudgetsByMonth: " + e.getMessage());
		e.printStackTrace();
		budgetOut.setErrorflag(true);
		budgetOut.setMessageString("Error retrieving budget data for month: " + e.getMessage());
	}
	return budgetOut;
}

public BudgetOutDto getBudgetsByFilters(String userId, String month, String category) {
	BudgetOutDto budgetOut = new BudgetOutDto();
	try {
		System.out.println("Getting budgets for userId: " + userId + ", month: " + month + ", category: " + category);
		List<Budget> budgets;
		
		if (month != null && !month.isEmpty() && category != null && !category.isEmpty()) {
			Integer monthInt = Integer.parseInt(month);
			budgets = budgetRepository.findAllByUserIdAndMonthAndCategory(userId, monthInt, category);
			System.out.println("Applied both month and category filters");
		} else if (month != null && !month.isEmpty()) {
			Integer monthInt = Integer.parseInt(month);
			budgets = budgetRepository.findAllByUserIdAndMonth(userId, monthInt);
			System.out.println("Applied month filter only");
		} else if (category != null && !category.isEmpty()) {
			budgets = budgetRepository.findAllByUserIdAndCategory(userId, category);
			System.out.println("Applied category filter only");
		} else {
			budgets = budgetRepository.findAllByUserId(userId);
			System.out.println("No filters applied - getting all budgets");
		}
		
		System.out.println("Found " + budgets.size() + " budget records with applied filters");
		
		budgets = addBudgetWarnings(budgets);
		
		budgetOut.setBudgetList(budgets);
		budgetOut.setErrorflag(false);
		budgetOut.setMessageString("Budget data retrieved successfully with filters");
	} catch (Exception e) {
		System.out.println("Error in getBudgetsByFilters: " + e.getMessage());
		e.printStackTrace();
		budgetOut.setErrorflag(true);
		budgetOut.setMessageString("Error retrieving filtered budget data: " + e.getMessage());
	}
	return budgetOut;
}

private List<Budget> addBudgetWarnings(List<Budget> budgets) {
	int warningCount = 0;
	for (Budget budget : budgets) {
		if (budget.getBudgetLimit() != null && budget.getAmount() != null && 
			budget.getAmount() > budget.getBudgetLimit()) {
			warningCount++;
			System.out.println("WARNING: Budget exceeded for category " + budget.getCategory() + 
							 " - Amount: " + budget.getAmount() + ", Limit: " + budget.getBudgetLimit());
		}
	}
	if (warningCount > 0) {
		System.out.println("TOTAL BUDGET WARNINGS: " + warningCount + " budget(s) exceed their limits");
	}
	return budgets;
}

public String exportBudgetToCSV(String userId, String month, String category) {
	try {
		BudgetOutDto budgetData = getBudgetsByFilters(userId, month, category);
		List<Budget> budgets = budgetData.getBudgetList();
		
		StringBuilder csvContent = new StringBuilder();
		
		csvContent.append("Category,Amount,Month,Budget Limit,Status,Date\n");
		
		for (Budget budget : budgets) {
			csvContent.append(escapeCSV(budget.getCategory())).append(",");
			csvContent.append(budget.getAmount() != null ? budget.getAmount() : "0").append(",");
			csvContent.append(budget.getMonth() != null ? budget.getMonth() : "").append(",");
			csvContent.append(budget.getBudgetLimit() != null ? budget.getBudgetLimit() : "No Limit").append(",");
			
			// Status column
			String status = "Within Limit";
			if (budget.getBudgetLimit() != null && budget.getAmount() != null && 
				budget.getAmount() > budget.getBudgetLimit()) {
				status = "⚠️ Over Budget";
			} else if (budget.getBudgetLimit() == null) {
				status = "No Limit Set";
			}
			csvContent.append(escapeCSV(status)).append(",");
			
			// Date column
			java.util.Date now = new java.util.Date();
			java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			csvContent.append(dateFormat.format(now));
			
			csvContent.append("\n");
		}
		
		System.out.println("CSV export completed successfully for " + budgets.size() + " budget entries");
		return csvContent.toString();
		
	} catch (Exception e) {
		System.out.println("Error in exportBudgetToCSV: " + e.getMessage());
		e.printStackTrace();
		throw new RuntimeException("Failed to export budget data to CSV: " + e.getMessage());
	}
}

public String generateCSVFilename(String month, String category) {
	StringBuilder filename = new StringBuilder("budget_export");
	
	if (month != null && !month.trim().isEmpty()) {
		filename.append("_").append(month.replaceAll("[^a-zA-Z0-9]", ""));
	}
	
	if (category != null && !category.trim().isEmpty() && !category.equals("all")) {
		filename.append("_").append(category.replaceAll("[^a-zA-Z0-9]", ""));
	}
	
	// Add timestamp
	java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss");
	String timestamp = dateFormat.format(new java.util.Date());
	filename.append("_").append(timestamp);
	
	filename.append(".csv");
	return filename.toString();
}

private String escapeCSV(String value) {
	if (value == null) {
		return "";
	}
	// If value contains comma, quote, or newline, wrap in quotes and escape internal quotes
	if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
		return "\"" + value.replace("\"", "\"\"") + "\"";
	}
	return value;
}
	
}
