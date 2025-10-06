package com.sts.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sts.entity.Budget;
import com.sts.entity.BudgetOutDto;
import com.sts.service.ExpenceTrackerService;



@RestController
public class ExpenceTrackerController {
	
	@Autowired
	ExpenceTrackerService expenceTrackerService;
	
	@PostMapping("/addBudget")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<BudgetOutDto> addBudget( @RequestBody Budget budget) {
		
		BudgetOutDto budgetOutDto=expenceTrackerService.addBudget(budget);
		return ResponseEntity.ok().body(budgetOutDto);
		
	}
	@PostMapping("/viewBudget")
	@CrossOrigin(origins = "*")
	public ResponseEntity<BudgetOutDto>viewBudget(@RequestBody String userId)
	{    String cleanUserId = userId.replace("\"", "");

		BudgetOutDto budgetOutDto=expenceTrackerService.viewBudget(cleanUserId);

		return ResponseEntity.ok().body(budgetOutDto);
	}
	@DeleteMapping("/deleteBudget/{id}")
	@CrossOrigin(origins = "*")
	public ResponseEntity<BudgetOutDto> deleteBudget(@PathVariable("id") int budgetId) {
		
		BudgetOutDto budgetOutDto = expenceTrackerService.deleteBudget(budgetId);
		return ResponseEntity.ok().body(budgetOutDto);
	}
	
	@PostMapping("/getBudgetChart")
	@CrossOrigin(origins = "*")
	public ResponseEntity<BudgetOutDto> getBudgetChart(@RequestBody String userId) {
		String cleanUserId = userId.replace("\"", "");
		BudgetOutDto budgetOutDto = expenceTrackerService.getBudgetChartData(cleanUserId);
		return ResponseEntity.ok().body(budgetOutDto);
	}
	
	@PostMapping("/getBudgetsByMonth")
	@CrossOrigin(origins = "*")
	public ResponseEntity<BudgetOutDto> getBudgetsByMonth(@RequestBody Map<String, String> request) {
		String userId = request.get("userId");
		String month = request.get("month");
		BudgetOutDto budgetOutDto = expenceTrackerService.getBudgetsByMonth(userId, Integer.parseInt(month));
		return ResponseEntity.ok().body(budgetOutDto);
	}
	
	@PostMapping("/getBudgetsByFilters")
	@CrossOrigin(origins = "*")
	public ResponseEntity<BudgetOutDto> getBudgetsByFilters(@RequestBody Map<String, String> request) {
		String userId = request.get("userId");
		String month = request.get("month");
		String category = request.get("category");
		BudgetOutDto budgetOutDto = expenceTrackerService.getBudgetsByFilters(userId, month, category);
		return ResponseEntity.ok().body(budgetOutDto);
	}
	
	@PostMapping("/exportBudgetCSV")
	@CrossOrigin(origins = "*")
	public ResponseEntity<byte[]> exportBudgetCSV(@RequestBody Map<String, String> request) {
		String userId = request.get("userId");
		String month = request.get("month");
		String category = request.get("category");
		
		try {
			String csvContent = expenceTrackerService.exportBudgetToCSV(userId, month, category);
			
			// Generate filename
			String filename = expenceTrackerService.generateCSVFilename(month, category);
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.setContentDispositionFormData("attachment", filename);
			
			return ResponseEntity.ok()
				.headers(headers)
				.body(csvContent.getBytes());
				
		} catch (Exception e) {
			System.out.println("Error exporting CSV: " + e.getMessage());
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}
}
