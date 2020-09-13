package com.ajayonjava.coronavirustracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ajayonjava.coronavirustracker.model.LocationStats;
import com.ajayonjava.coronavirustracker.service.CoronaVirusDataService;

@Controller
public class HomeController {
	
	@Autowired
	CoronaVirusDataService service; 

	@GetMapping("/")
	public String getVirusData(Model model) {
		
		List<LocationStats> allStats = service.getAllStats();
		
		int totalCases = allStats.stream().mapToInt(stats -> stats.getLatestTotalCases()).sum();
		int newCases = allStats.stream().mapToInt(stat -> stat.getDiffFromPreviousDay()).sum();
		int totalActiveCases = allStats.stream().mapToInt(stats -> stats.getTotalActiveCases()).sum();
		int totalDeathCount = allStats.stream().mapToInt(stats -> stats.getTotalDeath()).sum();
		int totalRecoveries = allStats.stream().mapToInt(stats -> stats.getTotalRecovered()).sum();
		
		int newDeaths = allStats.stream().mapToInt(stats -> stats.getTotalDeathFromPreviousDay()).sum();
		int newRecovered = allStats.stream().mapToInt(stats -> stats.getTotalRecoveredFromPrivousDay()).sum();
		
		model.addAttribute("locationStats", allStats);
		model.addAttribute("totalCases",totalCases);
		model.addAttribute("newCases",newCases);
		model.addAttribute("newDeaths", newDeaths);
		model.addAttribute("newRecovered", newRecovered);
		model.addAttribute("totalActiveCases", totalActiveCases);
		model.addAttribute("totalDeathCount", totalDeathCount);
		model.addAttribute("totalRecoveries", totalRecoveries);
		model.addAttribute("lastUpdatedDate","Total cases reported as of "+service.getLastUpdated());
		
		return "home";
	}
}
