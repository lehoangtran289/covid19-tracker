package com.example.covidreport.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.covidreport.models.LocationStats;
import com.example.covidreport.services.CovidDataService;

@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class MainController {

	@Autowired
	private CovidDataService covidDataService;

	@GetMapping("/list")
	public ResponseEntity<List<LocationStats>> getList() {
		List<LocationStats> list = covidDataService.getList();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping("/spring")
	public String home(Model model) throws ParseException {
		List<LocationStats> list = covidDataService.getList();
		int totalCases = list.stream().mapToInt(item -> item.getTotalCases()).sum();
		int totalDeaths = list.stream().mapToInt(item -> item.getTotalDeaths()).sum();
		int totalRecovers = list.stream().mapToInt(item -> item.getTotalRecovered()).sum();
		int totalDiff = list.stream().mapToInt(item -> item.getDiff()).sum();

		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String date = formatter.format(new Date());

		model.addAttribute("list", list);
		model.addAttribute("totalCases", totalCases);
		model.addAttribute("totalDiff", totalDiff);
		model.addAttribute("totalDeaths", totalDeaths);
		model.addAttribute("totalRecovers", totalRecovers);
		model.addAttribute("date", date);
		return "home";
	}
}
