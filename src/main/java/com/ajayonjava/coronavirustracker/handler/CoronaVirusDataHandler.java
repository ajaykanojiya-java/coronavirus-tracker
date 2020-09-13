package com.ajayonjava.coronavirustracker.handler;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import com.ajayonjava.coronavirustracker.model.LocationStats;

@Component
public class CoronaVirusDataHandler {

	public Map<String, LocationStats> addCoronaPositiveCount(String csvBodyReader) throws IOException {

		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader()
				.parse(new StringReader(csvBodyReader));
		
		LocationStats stats = null;
		
		Map<String, LocationStats> coronaStats = new HashMap<String, LocationStats>();
		
		for (CSVRecord record : records) {
			
			stats = new LocationStats();
			int totalCases = Integer.parseInt(record.get(record.size() - 1));
			int previousDayCases = Integer.parseInt(record.get(record.size() - 2));

			if (!coronaStats.containsKey(record.get("Country/Region"))) {

				stats.setCountry(record.get("Country/Region"));
				stats.setLatestTotalCases(totalCases);
				stats.setDiffFromPreviousDay(totalCases - previousDayCases);

				coronaStats.put(record.get("Country/Region"), stats);
			} else {

				LocationStats storedStats = coronaStats.get(record.get("Country/Region"));
				storedStats.setLatestTotalCases(storedStats.getLatestTotalCases() + totalCases);
				storedStats
						.setDiffFromPreviousDay(storedStats.getDiffFromPreviousDay() + (totalCases - previousDayCases));
			}

		}

		return coronaStats;
	}

	public Map<String, LocationStats> addCoronaPositiveDeathCount(String csvBodyReader,
			Map<String, LocationStats> input) throws IOException {

		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader()
				.parse(new StringReader(csvBodyReader));
		LocationStats stats = null;

		for (CSVRecord record : records) {
			stats = new LocationStats();
			int totalDeath = Integer.parseInt(record.get(record.size() - 1));
			int previousDayDeath = Integer.parseInt(record.get(record.size() - 2));

			stats = input.get(record.get("Country/Region"));
			stats.setTotalDeath(stats.getTotalDeath()+totalDeath);
			stats.setTotalDeathFromPreviousDay(stats.getTotalDeathFromPreviousDay()+ (totalDeath - previousDayDeath));

		}

		return input;
	}

	public Map<String, LocationStats> addCoronaPositiveRecoveredCount(String csvBodyReader,
			Map<String, LocationStats> input) throws IOException {

		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader()
				.parse(new StringReader(csvBodyReader));
		LocationStats stats = null;

		for (CSVRecord record : records) {
			stats = new LocationStats();
			int totalRecovered = Integer.parseInt(record.get(record.size() - 1));
			int previousDayRecovered = Integer.parseInt(record.get(record.size() - 2));

			stats = input.get(record.get("Country/Region"));
			stats.setTotalRecovered(stats.getTotalRecovered()+totalRecovered);
			stats.setTotalRecoveredFromPrivousDay(stats.getTotalRecoveredFromPrivousDay()+(totalRecovered - previousDayRecovered));
			stats.setTotalActiveCases(stats.getLatestTotalCases()-stats.getTotalDeath()-stats.getTotalRecovered());
		}

		input.entrySet().stream().forEach(e->e.getValue().setTotalActiveCases(e.getValue().getLatestTotalCases()-e.getValue().getTotalDeath()-e.getValue().getTotalRecovered()));
		return input;
	}
	
	
	public Date getLastUpdateDate(String csvBodyReader) throws IOException {
		
		List<String> headers = CSVFormat.DEFAULT.withFirstRecordAsHeader()
				.parse(new StringReader(csvBodyReader)).getHeaderNames();
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
		Date date = null;
		try {
			date = sdf.parse(headers.get(headers.size()-1));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

}
