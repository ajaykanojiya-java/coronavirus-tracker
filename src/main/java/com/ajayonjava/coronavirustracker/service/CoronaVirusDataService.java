package com.ajayonjava.coronavirustracker.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ajayonjava.coronavirustracker.handler.CoronaVirusDataHandler;
import com.ajayonjava.coronavirustracker.model.LocationStats;


@Service
public class CoronaVirusDataService {
	
	@Autowired
	CoronaVirusDataHandler dataHandler;
	
	private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

	private static String DEATH_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_global.csv";
	
	private static String RECOVERED_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_recovered_global.csv";
	
	List<LocationStats> allStats = new ArrayList<LocationStats>();
	
	Date lastUpdated;
	
	public List<LocationStats> getAllStats() {
		return allStats;
	}
	
	public Date getLastUpdated() {
		return lastUpdated;
	}

	@PostConstruct //as bean is created then call this method
	//@Scheduled(cron ="* * 3 * * *") // will call this method every first hour of the day
	@Scheduled(fixedDelay = 3*60*1000)
	public void fetchVirusData() throws IOException, InterruptedException {
		
		HttpClient httpClient = HttpClient.newHttpClient();
		HttpRequest request1 = HttpRequest.newBuilder()
				.uri(URI.create(VIRUS_DATA_URL))
				.build();
		
		HttpRequest request2 = HttpRequest.newBuilder()
				.uri(URI.create(DEATH_DATA_URL))
				.build();
		
		HttpRequest request3 = HttpRequest.newBuilder()
				.uri(URI.create(RECOVERED_DATA_URL))
				.build();
		
		
		HttpResponse<String> responseCoronaPostiveData = httpClient.send(request1, HttpResponse.BodyHandlers.ofString());
				
		HttpResponse<String> responseCoronaPostiveDeathData = httpClient.send(request2, HttpResponse.BodyHandlers.ofString());
				
		HttpResponse<String> responseCoronaPostiveRecoveredData = httpClient.send(request3, HttpResponse.BodyHandlers.ofString());
		
		Map<String, LocationStats> coronaStats = dataHandler.addCoronaPositiveRecoveredCount(responseCoronaPostiveRecoveredData.body(), 
				dataHandler.addCoronaPositiveDeathCount(responseCoronaPostiveDeathData.body(), 
						dataHandler.addCoronaPositiveCount(responseCoronaPostiveData.body())));
		
		lastUpdated = dataHandler.getLastUpdateDate(responseCoronaPostiveData.body());
		
		List<LocationStats> newStats = new ArrayList<LocationStats>(coronaStats.values());
		newStats.sort((s1, s2) -> s2.getDiffFromPreviousDay() - s1.getDiffFromPreviousDay());
		
		allStats = newStats;
	}
}
