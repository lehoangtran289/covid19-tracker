package com.example.covidreport.services;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.covidreport.models.LocationStats;

@Service
public class CovidDataService {

	private static String COVID_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv";
	private static String DEATH_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Deaths.csv";
	private static String RECOVER_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Recovered.csv";
	private List<LocationStats> list = new ArrayList<>();

	public List<LocationStats> getList() {
		return list;
	}

	@PostConstruct
	@Scheduled(cron = "* * 1 * * *")
	public void fetchCovidData() throws IOException, InterruptedException {
		List<LocationStats> stats = new ArrayList<>();

		HttpClient client = HttpClient.newHttpClient();

		HttpRequest requestConfirmed = HttpRequest.newBuilder().uri(URI.create(COVID_URL)).build();
		HttpResponse<String> responseConfirmed = client.send(requestConfirmed, HttpResponse.BodyHandlers.ofString());

		HttpRequest requestDeaths = HttpRequest.newBuilder().uri(URI.create(DEATH_URL)).build();
		HttpResponse<String> responseDeaths = client.send(requestDeaths, HttpResponse.BodyHandlers.ofString());

		HttpRequest requestRecovered = HttpRequest.newBuilder().uri(URI.create(RECOVER_URL)).build();
		HttpResponse<String> responseRecovered = client.send(requestRecovered, HttpResponse.BodyHandlers.ofString());

		StringReader in1 = new StringReader(responseConfirmed.body());
		StringReader in2 = new StringReader(responseDeaths.body());
		StringReader in3 = new StringReader(responseRecovered.body());

		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in1);
		Iterable<CSVRecord> deaths = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in2);
		Iterable<CSVRecord> recovers = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in3);

		for (CSVRecord record : records) {
			LocationStats locationStats = new LocationStats();
			locationStats.setState(record.get("Province/State"));
			locationStats.setCountry(record.get("Country/Region"));
			locationStats.setTotalCases(Integer.parseInt(record.get(record.size() - 1)));
			locationStats.setDiff(
					Integer.parseInt(record.get(record.size() - 1)) - Integer.parseInt(record.get(record.size() - 2)));

			for (CSVRecord death : deaths) {
				if (death.get("Province/State").equals(record.get("Province/State"))
						&& death.get("Country/Region").equals(record.get("Country/Region"))) {
					locationStats.setTotalDeaths(Integer.parseInt(death.get(death.size() - 1)));
					break;
				}
			}

			for (CSVRecord recover : recovers) {
				if (recover.get("Province/State").equals(record.get("Province/State"))
						&& recover.get("Country/Region").equals(record.get("Country/Region"))) {
					locationStats.setTotalRecovered(Integer.parseInt(recover.get(recover.size() - 1)));
					break;
				}
			}
			stats.add(locationStats);
		}
		Collections.sort(stats);
		Collections.reverse(stats);
		this.list = stats;
	}

}
